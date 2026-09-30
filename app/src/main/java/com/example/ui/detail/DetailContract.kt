package com.example.ui.detail

import com.example.data.model.Cocktail
import com.example.data.repository.MusicPairing

data class DetailUiState(
    val cocktail: Cocktail? = null,
    val isAlternateUnits: Boolean = false, // false: Centilitres (cl), true: Cuillères / Verres / Shooters
    val showAiSubstitution: Boolean = false,
    val selectedMissingIngredient: String? = null,
    val aiSubstitutionText: String? = null,
    val isSubstitutionLoading: Boolean = false,
    val showPlaylistPicker: Boolean = false,
    val musicPairing: MusicPairing? = null,
    val isMusicLoading: Boolean = false,
    val isShakerTimerActive: Boolean = false,
    val shakerSecondsRemaining: Int = 0,
    val toastMessage: String? = null
)

sealed interface DetailIntent {
    data class LoadCocktail(val id: Long) : DetailIntent
    data object ToggleUnits : DetailIntent
    data object ToggleFavorite : DetailIntent
    data class RequestAiSubstitution(val missingIngredient: String? = null) : DetailIntent
    data object CloseAiSubstitution : DetailIntent
    data object RequestPlaylistAccord : DetailIntent
    data object ClosePlaylistPicker : DetailIntent
    data object StartShakerTimer : DetailIntent
    data object CancelShakerTimer : DetailIntent
    data object ClearToast : DetailIntent
}
