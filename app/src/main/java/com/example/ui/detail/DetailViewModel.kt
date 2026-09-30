package com.example.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CocktailRepository
import com.example.data.repository.GeminiRepository
import com.example.data.repository.ProfileRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.data.local.UserPreferencesRepository

class DetailViewModel(
    private val cocktailRepository: CocktailRepository,
    private val geminiRepository: GeminiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun processIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadCocktail -> {
                viewModelScope.launch {
                    cocktailRepository.getCocktailById(intent.id).collect { cocktail ->
                        _uiState.update { it.copy(cocktail = cocktail) }
                    }
                }
            }
            is DetailIntent.ToggleUnits -> {
                _uiState.update { it.copy(isAlternateUnits = !it.isAlternateUnits) }
            }
            is DetailIntent.ToggleFavorite -> {
                val cocktail = _uiState.value.cocktail ?: return
                viewModelScope.launch {
                    cocktailRepository.toggleFavorite(cocktail.id, cocktail.isFavorite)
                }
            }
            is DetailIntent.RequestAiSubstitution -> {
                val cocktail = _uiState.value.cocktail ?: return
                val targetIngredient = intent.missingIngredient
                    ?: cocktail.ingredients.firstOrNull { !it.isGarnish }?.name
                    ?: "alcool"

                _uiState.update {
                    it.copy(
                        showAiSubstitution = true,
                        selectedMissingIngredient = targetIngredient,
                        isSubstitutionLoading = true
                    )
                }

                viewModelScope.launch {
                    val result = geminiRepository.getIngredientSubstitution(
                        cocktailName = cocktail.name,
                        missingIngredient = targetIngredient
                    )
                    _uiState.update {
                        it.copy(
                            aiSubstitutionText = result.getOrNull(),
                            isSubstitutionLoading = false
                        )
                    }
                }
            }
            is DetailIntent.CloseAiSubstitution -> {
                _uiState.update { it.copy(showAiSubstitution = false) }
            }
            is DetailIntent.RequestPlaylistAccord -> {
                val cocktail = _uiState.value.cocktail ?: return
                _uiState.update { it.copy(showPlaylistPicker = true, isMusicLoading = true) }

                viewModelScope.launch {
                    val result = geminiRepository.getMusicAccord(
                        cocktailName = cocktail.name,
                        vibeTag = cocktail.vibeTag
                    )
                    _uiState.update {
                        it.copy(
                            musicPairing = result.getOrNull(),
                            isMusicLoading = false
                        )
                    }
                }
            }
            is DetailIntent.ClosePlaylistPicker -> {
                _uiState.update { it.copy(showPlaylistPicker = false) }
            }
            is DetailIntent.StartShakerTimer -> {
                val duration = _uiState.value.cocktail?.shakeSeconds ?: 12
                if (duration <= 0) return

                timerJob?.cancel()
                _uiState.update { it.copy(isShakerTimerActive = true, shakerSecondsRemaining = duration) }

                timerJob = viewModelScope.launch {
                    for (sec in duration downTo 1) {
                        _uiState.update { it.copy(shakerSecondsRemaining = sec) }
                        delay(1000)
                    }
                    _uiState.update {
                        it.copy(
                            isShakerTimerActive = false,
                            shakerSecondsRemaining = 0,
                            toastMessage = "Cocktail parfait ! +40 XP gagnés !"
                        )
                    }
                    userPreferencesRepository.addXp(40)
                }
            }
            is DetailIntent.CancelShakerTimer -> {
                timerJob?.cancel()
                _uiState.update { it.copy(isShakerTimerActive = false, shakerSecondsRemaining = 0) }
            }
            is DetailIntent.ClearToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
        }
    }
}
