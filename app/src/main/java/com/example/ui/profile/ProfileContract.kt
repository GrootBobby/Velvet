package com.example.ui.profile

import android.app.Activity
import com.example.data.firebase.AuthUserState
import com.example.data.local.UserPreferencesRepository
import com.example.data.local.UserProgress
import com.example.data.model.Cocktail
import com.example.data.model.UserCocktailPhoto

enum class ProfileTab {
    CREATIONS,
    FAVORITES,
    GALLERY
}

data class ProfileUiState(
    val userProgress: UserProgress = UserPreferencesRepository.calculateProgress("", false, 0),
    val myCreations: List<Cocktail> = emptyList(),
    val favorites: List<Cocktail> = emptyList(),
    val photos: List<UserCocktailPhoto> = emptyList(),
    val selectedTab: ProfileTab = ProfileTab.CREATIONS,
    val showAddRecipeDialog: Boolean = false,
    val showAddPhotoDialog: Boolean = false,
    val showAuthDialog: Boolean = false,
    val authUser: AuthUserState? = null,
    val isGuestMode: Boolean = true,
    val isAuthLoading: Boolean = false,
    val isSyncing: Boolean = false,
    val isCloudSynced: Boolean = false,
    val lastSyncedTimeText: String? = null,
    val toastMessage: String? = null
)

sealed interface ProfileIntent {
    data class SelectTab(val tab: ProfileTab) : ProfileIntent
    data object OpenAddRecipeDialog : ProfileIntent
    data object CloseAddRecipeDialog : ProfileIntent
    data object OpenAddPhotoDialog : ProfileIntent
    data object CloseAddPhotoDialog : ProfileIntent
    data object OpenAuthDialog : ProfileIntent
    data object CloseAuthDialog : ProfileIntent
    data class SignInWithEmail(val email: String, val pass: String) : ProfileIntent
    data class SignUpWithEmail(val email: String, val pass: String, val pseudo: String) : ProfileIntent
    data class SignInWithGoogle(val activity: Activity) : ProfileIntent
    data object SignOut : ProfileIntent
    data object TriggerManualSync : ProfileIntent
    data object ContinueAsGuest : ProfileIntent
    data class CreateCustomRecipe(
        val name: String,
        val subtitle: String,
        val description: String,
        val category: String,
        val alcoholPercentage: Double,
        val prepTime: Int,
        val difficulty: String,
        val flavorProfile: String,
        val ingredientsText: String,
        val stepsText: String,
        val photoUri: String? = null
    ) : ProfileIntent
    data class SaveCocktailPhoto(
        val cocktailName: String,
        val photoUri: String,
        val notes: String
    ) : ProfileIntent
    data class AddExperience(val amount: Int) : ProfileIntent
    data class UpdateProfilePhoto(val photoUri: String) : ProfileIntent
    data object ClearToast : ProfileIntent
}
