package com.example.ui.profile

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.firebase.AuthUserState
import com.example.data.firebase.CloudSyncRepository
import com.example.data.firebase.FirebaseAuthManager
import com.example.data.local.UserPreferencesRepository
import com.example.data.model.Cocktail
import com.example.data.model.RecipeIngredient
import com.example.data.repository.CocktailRepository
import com.example.data.repository.InventoryRepository
import com.example.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val cocktailRepository: CocktailRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val inventoryRepository: InventoryRepository? = null,
    private val authManager: FirebaseAuthManager? = null,
    private val cloudSyncRepository: CloudSyncRepository? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.userProgress,
                cocktailRepository.allCocktails,
                cocktailRepository.favoriteCocktails,
                profileRepository.userPhotos
            ) { progress, allCocktails, favorites, photos ->
                val myCreations = allCocktails.filter { it.isCustom }
                Quad(progress, myCreations, favorites, photos)
            }.collect { (progress, myCreations, favorites, photos) ->
                _uiState.update { current ->
                    current.copy(
                        userProgress = progress,
                        myCreations = myCreations,
                        favorites = favorites,
                        photos = photos
                    )
                }
            }
        }

        // Listen to Auth State
        authManager?.let { auth ->
            viewModelScope.launch {
                auth.currentUser.collect { user ->
                    _uiState.update { current ->
                        current.copy(
                            authUser = user,
                            isGuestMode = (user == null)
                        )
                    }
                    if (user != null) {
                        performCloudSync(user.uid, user.email)
                    }
                }
            }
        }
    }

    private fun performCloudSync(uid: String, email: String?) {
        val cloudSync = cloudSyncRepository ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true) }
            val currentProgress = _uiState.value.userProgress
            val currentFavorites = _uiState.value.favorites
            val currentInventory = inventoryRepository?.allIngredients?.firstOrNull() ?: emptyList()

            val result = cloudSync.syncUserData(
                uid = uid,
                email = email,
                localProgress = currentProgress,
                localFavorites = currentFavorites,
                localInventory = currentInventory
            )

            result.onSuccess { profile ->
                val timeStr = SimpleDateFormat("HH:mm", Locale.FRANCE).format(Date(profile.lastSyncedTimestamp))
                _uiState.update {
                    it.copy(
                        isSyncing = false,
                        isCloudSynced = true,
                        lastSyncedTimeText = "Synchronisé à $timeStr",
                        toastMessage = "Progression sauvegardée dans le Cloud !"
                    )
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isSyncing = false,
                        isCloudSynced = false,
                        toastMessage = "Sync Cloud : Mode local conservé"
                    )
                }
            }
        }
    }

    fun addXp(amount: Int) {
        viewModelScope.launch {
            userPreferencesRepository.addXp(amount)
        }
    }

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.SelectTab -> {
                _uiState.update { it.copy(selectedTab = intent.tab) }
            }
            is ProfileIntent.OpenAuthDialog -> {
                _uiState.update { it.copy(showAuthDialog = true) }
            }
            is ProfileIntent.CloseAuthDialog -> {
                _uiState.update { it.copy(showAuthDialog = false) }
            }
            is ProfileIntent.ContinueAsGuest -> {
                _uiState.update { it.copy(showAuthDialog = false, isGuestMode = true) }
            }
            is ProfileIntent.SignInWithEmail -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isAuthLoading = true) }
                    val result = authManager?.signInWithEmail(intent.email, intent.pass)
                    if (result != null && result.isSuccess) {
                        val user = result.getOrNull()
                        _uiState.update {
                            it.copy(
                                authUser = user,
                                isGuestMode = false,
                                isAuthLoading = false,
                                showAuthDialog = false,
                                toastMessage = "Connexion réussie !"
                            )
                        }
                        if (user != null) {
                            performCloudSync(user.uid, user.email)
                        }
                    } else {
                        val errMsg = result?.exceptionOrNull()?.localizedMessage ?: "Erreur de connexion"
                        _uiState.update {
                            it.copy(
                                isAuthLoading = false,
                                toastMessage = "Échec : $errMsg"
                            )
                        }
                    }
                }
            }
            is ProfileIntent.SignUpWithEmail -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isAuthLoading = true) }
                    val result = authManager?.signUpWithEmail(intent.email, intent.pass, intent.pseudo)
                    if (result != null && result.isSuccess) {
                        val user = result.getOrNull()
                        if (intent.pseudo.isNotBlank()) {
                            userPreferencesRepository.setUserName(intent.pseudo)
                        }
                        _uiState.update {
                            it.copy(
                                authUser = user,
                                isGuestMode = false,
                                isAuthLoading = false,
                                showAuthDialog = false,
                                toastMessage = "Compte créé avec succès !"
                            )
                        }
                        if (user != null) {
                            performCloudSync(user.uid, user.email)
                        }
                    } else {
                        val errMsg = result?.exceptionOrNull()?.localizedMessage ?: "Erreur d'inscription"
                        _uiState.update {
                            it.copy(
                                isAuthLoading = false,
                                toastMessage = "Échec : $errMsg"
                            )
                        }
                    }
                }
            }
            is ProfileIntent.SignInWithGoogle -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isAuthLoading = true) }
                    val result = authManager?.signInWithGoogle(intent.activity)
                    if (result != null && result.isSuccess) {
                        val user = result.getOrNull()
                        _uiState.update {
                            it.copy(
                                authUser = user,
                                isGuestMode = false,
                                isAuthLoading = false,
                                showAuthDialog = false,
                                toastMessage = "Connecté via Google !"
                            )
                        }
                        if (user != null) {
                            performCloudSync(user.uid, user.email)
                        }
                    } else {
                        val errMsg = result?.exceptionOrNull()?.localizedMessage ?: "Connexion Google annulée"
                        _uiState.update {
                            it.copy(
                                isAuthLoading = false,
                                toastMessage = errMsg
                            )
                        }
                    }
                }
            }
            is ProfileIntent.SignOut -> {
                authManager?.signOut()
                _uiState.update {
                    it.copy(
                        authUser = null,
                        isGuestMode = true,
                        isCloudSynced = false,
                        toastMessage = "Déconnecté. Vous êtes en mode invité."
                    )
                }
            }
            is ProfileIntent.TriggerManualSync -> {
                val user = _uiState.value.authUser
                if (user != null) {
                    performCloudSync(user.uid, user.email)
                } else {
                    _uiState.update { it.copy(showAuthDialog = true) }
                }
            }
            is ProfileIntent.OpenAddRecipeDialog -> {
                _uiState.update { it.copy(showAddRecipeDialog = true) }
            }
            is ProfileIntent.CloseAddRecipeDialog -> {
                _uiState.update { it.copy(showAddRecipeDialog = false) }
            }
            is ProfileIntent.OpenAddPhotoDialog -> {
                _uiState.update { it.copy(showAddPhotoDialog = true) }
            }
            is ProfileIntent.CloseAddPhotoDialog -> {
                _uiState.update { it.copy(showAddPhotoDialog = false) }
            }
            is ProfileIntent.CreateCustomRecipe -> {
                viewModelScope.launch {
                    val ingredientsList = intent.ingredientsText.lines().filter { it.isNotBlank() }.map { line ->
                        RecipeIngredient(
                            name = line.trim(),
                            details = "Ingrédient maison",
                            amountCl = 3.0,
                            unitAlternative = "1 mesure"
                        )
                    }.ifEmpty {
                        listOf(RecipeIngredient("Gin", "Alcool de base", 4.0, "1 shooter"))
                    }

                    val stepsList = intent.stepsText.lines().filter { it.isNotBlank() }.ifEmpty {
                        listOf("Verser les ingrédients dans le shaker avec des glaçons et servir frais.")
                    }

                    val newCocktail = Cocktail(
                        name = intent.name,
                        subtitle = intent.subtitle.ifEmpty { "Création Personnalisée" },
                        description = intent.description.ifEmpty { "Création originale confectionnée au shaker." },
                        category = intent.category,
                        alcoholPercentage = intent.alcoholPercentage,
                        prepTimeMinutes = intent.prepTime,
                        difficulty = intent.difficulty,
                        flavorProfile = intent.flavorProfile,
                        imageUrl = intent.photoUri ?: "",
                        isCustom = true,
                        ingredients = ingredientsList,
                        steps = stepsList
                    )

                    cocktailRepository.insertCustomCocktail(newCocktail)
                    // Enregistrer automatiquement la photo dans la galerie si prise à la caméra
                    if (!intent.photoUri.isNullOrBlank()) {
                        profileRepository.savePhoto(
                            cocktailName = intent.name,
                            photoUri = intent.photoUri,
                            notes = "Création originale : ${intent.name}"
                        )
                    }
                    userPreferencesRepository.addXp(150)
                    _uiState.update {
                        it.copy(
                            showAddRecipeDialog = false,
                            toastMessage = "Nouvelle recette enregistrée avec photo CameraX ! +150 XP"
                        )
                    }
                }
            }
            is ProfileIntent.SaveCocktailPhoto -> {
                viewModelScope.launch {
                    profileRepository.savePhoto(intent.cocktailName, intent.photoUri, intent.notes)
                    userPreferencesRepository.addXp(120)
                    _uiState.update {
                        it.copy(
                            showAddPhotoDialog = false,
                            toastMessage = "Photo enregistrée dans votre galerie ! +120 XP"
                        )
                    }
                }
            }
            is ProfileIntent.AddExperience -> {
                addXp(intent.amount)
            }
            is ProfileIntent.UpdateProfilePhoto -> {
                viewModelScope.launch {
                    userPreferencesRepository.setProfilePhotoUri(intent.photoUri)
                    _uiState.update { it.copy(toastMessage = "Photo de profil mise à jour !") }
                }
            }
            is ProfileIntent.ClearToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
        }
    }

    private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
}
