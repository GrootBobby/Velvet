package com.example.ui.party

enum class UndercoverPhase {
    CONFIG,
    PASS_PHONE,
    DEBATE
}

enum class PlayerRole(val label: String, val description: String) {
    CIVIL("Civil", "Mot officiel"),
    UNDERCOVER("Infiltré", "Mot approchant"),
    MR_WHITE("Mr. White", "Aucun mot")
}

data class UndercoverPlayer(
    val id: Int,
    val name: String,
    val role: PlayerRole,
    val secretWord: String,
    val avatarUrl: String,
    val isEliminated: Boolean = false
)

data class UndercoverUiState(
    val phase: UndercoverPhase = UndercoverPhase.CONFIG,
    val totalPlayers: Int = 4,
    val civilsCount: Int = 3,
    val undercoverCount: Int = 1,
    val whiteCount: Int = 0,
    val playerNames: List<String> = listOf("Alex", "Camille", "Thomas", "Elena"),
    val debateTimerSeconds: Int = 60,
    val isTimerEnabled: Boolean = true,
    val civilWord: String = "",
    val undercoverWord: String = "",
    val players: List<UndercoverPlayer> = emptyList(),
    val activePlayerIndex: Int = 0,
    val isRoleRevealed: Boolean = false,
    val areImpostorsRevealed: Boolean = false,
    val isDebateTimerRunning: Boolean = false,
    val debateTimeRemaining: Int = 60,
    val toastMessage: String? = null
)

sealed interface UndercoverIntent {
    data class SetPlayerCount(val count: Int) : UndercoverIntent
    data class SetPlayerName(val index: Int, val name: String) : UndercoverIntent
    data class SetDebateDuration(val seconds: Int) : UndercoverIntent
    data class ToggleTimerEnabled(val enabled: Boolean) : UndercoverIntent
    data object StartGame : UndercoverIntent
    data class SetRoleRevealState(val isRevealed: Boolean) : UndercoverIntent
    data object NextPlayer : UndercoverIntent
    data object StartDebateTimer : UndercoverIntent
    data object PauseDebateTimer : UndercoverIntent
    data object ResetDebateTimer : UndercoverIntent
    data class TogglePlayerEliminated(val playerId: Int) : UndercoverIntent
    data object RevealImpostors : UndercoverIntent
    data object RestartConfig : UndercoverIntent
    data object QuitGame : UndercoverIntent
    data object ClearToast : UndercoverIntent
}
