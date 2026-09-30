package com.example.ui.party

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.GeminiRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class UndercoverViewModel(
    @Suppress("UNUSED_PARAMETER") geminiRepository: GeminiRepository? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(UndercoverUiState())
    val uiState: StateFlow<UndercoverUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    private val defaultPlayerNames = listOf(
        "Alex", "Camille", "Thomas", "Elena", "Julien",
        "Sarah", "Hugo", "Chloé", "Maxime", "Inès", "Lucas", "Emma"
    )

    private val playerAvatars = listOf(
        "https://lh3.googleusercontent.com/aida-public/AB6AXuB1wsRGcfEuaRxMqpPLizeiDbk1ojhfDZmU1zboNM7urLnvLkE36yCYVXWNM_zszUs030oedVYiOazT7C8YlJxb0FCfZFbYFAs2b3Op70moN_Hn4_V2KDfGFWYd7OTNPUdSLHfBJQzRX_itjyn0GU49O8_DvMLx6NQEupXeh5VwP4gnjrpp33xCdHJCKCp7OgyhoTAV3c0UGX2k57hT3BGS6bLOcUcQuOhD8TD5iML1ATcSGyIT8E7UhA",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDGObPc_kyDYyMpBAVMkoFTyBsRvuBCZJrocWZuTx6Ek1ZaQrdS2K0zoKUNlJjhFJZqV-3_E8WQIRvgRBWCXhrgxyUgpvnFdNLSBgrWJvLi5e2hsLPdXr_-tmezY3aG7MCrJ6ZruSA7hOtf9qHgATa9wf2Ak4CfS4JhhSwP2Z1fv0dEsqTdxzmL-Zt1RkSzDKaVLTre5wE4_CyOiSAXXZ5MXZ3A2F_y_3OfRkbJVWSmENfozCWJIpkXsA",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuC1_jCnZkeu4N_J_2msCiEJkoL6pscBNuLS6xkNLlkosotzZqeuDJ_1Dlf5pMaOT1Oy0RGrdBLI-b8pT9yHxbGh4S4tpn62en3x0WScvHW0V2Cwx9Gmt0vs-SQh9Uve1v6uIQ-f-b8eO3Zx5cKvmVjjl2g7E-XjsNZ-r9sPvUrW4RmqGeGtKzrYUcKTTtGbBzR_2xRvGs7Bz891WFlNLnsaJZsteARgfZkiYgJSBYQwb1_V-GOUMn7tUg",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuC7VbwE_pcwJl2Y5S1XBHPslWhjDKj5TwrwFinyuNHqFBz5O28gbTpKkHE_i3sW5uMy967kmoEF5fXkoSMAOQP-0OUS4I7kQP8SOTY-VRbPC28CuWK6fF5QUTHbqbBORWoInXYojpOm8XfTDD9DmRtwseI6JfPzLbrgp8uJCZ2qpG9jMkdUHAFcGc4FR4rK9yQgMbK74Idw_JPSrkrZkVfhPd4V3-EsgmZ4iwARlZbu_IVakqe4QX1bEg"
    )

    fun processIntent(intent: UndercoverIntent) {
        when (intent) {
            is UndercoverIntent.SetPlayerCount -> {
                val count = intent.count.coerceIn(3, 12)
                val (civils, undercover, white) = calculateRoles(count)
                _uiState.update { current ->
                    val updatedNames = current.playerNames.toMutableList()
                    while (updatedNames.size < count) {
                        val nextDefault = defaultPlayerNames.getOrElse(updatedNames.size) { "Joueur ${updatedNames.size + 1}" }
                        updatedNames.add(nextDefault)
                    }
                    current.copy(
                        totalPlayers = count,
                        civilsCount = civils,
                        undercoverCount = undercover,
                        whiteCount = white,
                        playerNames = updatedNames.take(count)
                    )
                }
            }
            is UndercoverIntent.SetPlayerName -> {
                val currentNames = _uiState.value.playerNames.toMutableList()
                if (intent.index in currentNames.indices) {
                    currentNames[intent.index] = intent.name
                    _uiState.update { it.copy(playerNames = currentNames) }
                }
            }
            is UndercoverIntent.SetDebateDuration -> {
                val clamped = intent.seconds.coerceIn(30, 180)
                _uiState.update { it.copy(debateTimerSeconds = clamped, debateTimeRemaining = clamped) }
            }
            is UndercoverIntent.ToggleTimerEnabled -> {
                _uiState.update { it.copy(isTimerEnabled = intent.enabled) }
            }
            is UndercoverIntent.StartGame -> {
                // 1. Piocher une paire aléatoire dans undercoverPairsList
                val pair = PartyGamesData.undercoverPairsList.random()

                // 2. Tirer au sort l'attribution avec un booléen (Random.nextBoolean())
                val (civilWord, undercoverWord) = if (Random.nextBoolean()) {
                    pair.first to pair.second
                } else {
                    pair.second to pair.first
                }

                val (civils, undercover, white) = calculateRoles(_uiState.value.totalPlayers)

                // 3. Distribuer ensuite civilWord à la majorité des joueurs et undercoverWord à l'imposteur (et mot vide pour Mr. White)
                val playersList = buildPlayerRoster(
                    total = _uiState.value.totalPlayers,
                    civils = civils,
                    undercover = undercover,
                    white = white,
                    cWord = civilWord,
                    uWord = undercoverWord
                )

                _uiState.update {
                    it.copy(
                        civilWord = civilWord,
                        undercoverWord = undercoverWord,
                        civilsCount = civils,
                        undercoverCount = undercover,
                        whiteCount = white,
                        players = playersList,
                        activePlayerIndex = 0,
                        isRoleRevealed = false,
                        areImpostorsRevealed = false,
                        phase = UndercoverPhase.PASS_PHONE,
                        debateTimeRemaining = it.debateTimerSeconds
                    )
                }
            }
            is UndercoverIntent.SetRoleRevealState -> {
                _uiState.update { it.copy(isRoleRevealed = intent.isRevealed) }
            }
            is UndercoverIntent.NextPlayer -> {
                val current = _uiState.value
                val nextIdx = current.activePlayerIndex + 1
                if (nextIdx < current.players.size) {
                    _uiState.update {
                        it.copy(
                            activePlayerIndex = nextIdx,
                            isRoleRevealed = false
                        )
                    }
                } else {
                    // All players saw their secret words! Proceed to debate
                    _uiState.update {
                        it.copy(
                            phase = UndercoverPhase.DEBATE,
                            debateTimeRemaining = it.debateTimerSeconds,
                            isDebateTimerRunning = it.isTimerEnabled,
                            areImpostorsRevealed = false
                        )
                    }
                    if (_uiState.value.isTimerEnabled) {
                        startTimer()
                    }
                }
            }
            is UndercoverIntent.StartDebateTimer -> {
                startTimer()
            }
            is UndercoverIntent.PauseDebateTimer -> {
                timerJob?.cancel()
                _uiState.update { it.copy(isDebateTimerRunning = false) }
            }
            is UndercoverIntent.ResetDebateTimer -> {
                timerJob?.cancel()
                _uiState.update {
                    it.copy(
                        isDebateTimerRunning = false,
                        debateTimeRemaining = it.debateTimerSeconds
                    )
                }
            }
            is UndercoverIntent.TogglePlayerEliminated -> {
                val updated = _uiState.value.players.map { player ->
                    if (player.id == intent.playerId) {
                        player.copy(isEliminated = !player.isEliminated)
                    } else player
                }
                _uiState.update { it.copy(players = updated) }
            }
            is UndercoverIntent.RevealImpostors -> {
                _uiState.update { it.copy(areImpostorsRevealed = !it.areImpostorsRevealed) }
            }
            is UndercoverIntent.QuitGame, is UndercoverIntent.RestartConfig -> {
                timerJob?.cancel()
                _uiState.update {
                    it.copy(
                        phase = UndercoverPhase.CONFIG,
                        isDebateTimerRunning = false,
                        activePlayerIndex = 0,
                        isRoleRevealed = false,
                        areImpostorsRevealed = false
                    )
                }
            }
            is UndercoverIntent.ClearToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(isDebateTimerRunning = true) }
        timerJob = viewModelScope.launch {
            while (_uiState.value.debateTimeRemaining > 0) {
                delay(1000)
                _uiState.update { it.copy(debateTimeRemaining = it.debateTimeRemaining - 1) }
            }
            _uiState.update {
                it.copy(
                    isDebateTimerRunning = false,
                    toastMessage = "Temps écoulé ! Place aux votes et éliminations !"
                )
            }
        }
    }

    private fun calculateRoles(total: Int): Triple<Int, Int, Int> {
        return when {
            total <= 4 -> Triple(total - 1, 1, 0)
            total <= 6 -> Triple(total - 2, 1, 1)
            total <= 8 -> Triple(total - 3, 2, 1)
            else -> Triple(total - 4, 2, 2)
        }
    }

    private fun buildPlayerRoster(
        total: Int,
        civils: Int,
        undercover: Int,
        white: Int,
        cWord: String,
        uWord: String
    ): List<UndercoverPlayer> {
        val customNames = _uiState.value.playerNames
        val rolesPool = mutableListOf<PlayerRole>()
        repeat(civils) { rolesPool.add(PlayerRole.CIVIL) }
        repeat(undercover) { rolesPool.add(PlayerRole.UNDERCOVER) }
        repeat(white) { rolesPool.add(PlayerRole.MR_WHITE) }
        rolesPool.shuffle()

        return (0 until total).map { index ->
            val role = rolesPool.getOrElse(index) { PlayerRole.CIVIL }
            val word = when (role) {
                PlayerRole.CIVIL -> cWord
                PlayerRole.UNDERCOVER -> uWord
                PlayerRole.MR_WHITE -> ""
            }
            val assignedName = customNames.getOrNull(index)?.trim()?.ifBlank { null }
                ?: defaultPlayerNames.getOrElse(index) { "Joueur ${index + 1}" }

            UndercoverPlayer(
                id = index + 1,
                name = assignedName,
                role = role,
                secretWord = word,
                avatarUrl = playerAvatars[index % playerAvatars.size]
            )
        }
    }
}
