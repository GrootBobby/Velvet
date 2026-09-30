package com.example.ui.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.firebase.CloudSyncRepository
import com.example.data.firebase.FirebaseAuthManager
import com.example.data.local.UserPreferencesRepository
import com.example.data.local.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val authManager: FirebaseAuthManager?,
    private val cloudSyncRepository: CloudSyncRepository?,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    private var localProgress: UserProgress? = null

    init {
        viewModelScope.launch {
            userPreferencesRepository.userProgress.collect { progress ->
                localProgress = progress
                updateAuthState()
            }
        }

        if (authManager != null) {
            viewModelScope.launch {
                authManager.currentUser.collect { user ->
                    updateAuthState(user)
                }
            }
        }
    }

    private fun updateAuthState(user: com.example.data.firebase.AuthUserState? = authManager?.getCurrentUser()) {
        val isGuest = user == null
        val uid = user?.uid ?: ""
        val name = user?.displayName ?: localProgress?.userName?.ifBlank { "Barman" } ?: "Barman"
        val code = if (uid.isNotBlank()) {
            CloudSyncRepository.generateFriendCode(name, uid)
        } else {
            ""
        }

        _uiState.update {
            it.copy(
                isGuestMode = isGuest,
                currentUid = uid,
                myUserName = name,
                myFriendCode = code
            )
        }

        if (!isGuest && uid.isNotBlank()) {
            loadLeaderboard()
        }
    }

    fun processIntent(intent: FriendsIntent) {
        when (intent) {
            is FriendsIntent.LoadLeaderboard -> {
                loadLeaderboard()
            }
            is FriendsIntent.UpdateFriendCodeInput -> {
                _uiState.update { it.copy(friendCodeInput = intent.code) }
            }
            is FriendsIntent.AddFriend -> {
                addFriend()
            }
            is FriendsIntent.ClearMessages -> {
                _uiState.update { it.copy(statusMessage = null, errorMessage = null) }
            }
            is FriendsIntent.OpenAuthDialog -> {
                _uiState.update { it.copy(showAuthDialog = true) }
            }
            is FriendsIntent.DismissAuthDialog -> {
                _uiState.update { it.copy(showAuthDialog = false) }
            }
        }
    }

    private fun loadLeaderboard() {
        val uid = _uiState.value.currentUid
        if (uid.isBlank() || cloudSyncRepository == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = cloudSyncRepository.getFriendsLeaderboard(uid, localProgress)
            result.onSuccess { list ->
                val myEntry = list.find { it.isCurrentUser }
                val code = myEntry?.friendCode?.ifBlank { _uiState.value.myFriendCode } ?: _uiState.value.myFriendCode
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        leaderboard = list,
                        myFriendCode = code
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Erreur de chargement du classement"
                    )
                }
            }
        }
    }

    private fun addFriend() {
        val uid = _uiState.value.currentUid
        val input = _uiState.value.friendCodeInput.trim()
        if (uid.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Vous devez être connecté pour ajouter des amis.") }
            return
        }
        if (input.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Veuillez entrer un code ami (ex: ALEX#1234).") }
            return
        }
        if (cloudSyncRepository == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isAddingFriend = true, errorMessage = null, statusMessage = null) }
            val result = cloudSyncRepository.addFriendByCode(uid, input)
            result.onSuccess { friend ->
                _uiState.update {
                    it.copy(
                        isAddingFriend = false,
                        friendCodeInput = "",
                        statusMessage = "Super ! ${friend.userName} a été ajouté(e) à vos amis !"
                    )
                }
                loadLeaderboard()
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isAddingFriend = false,
                        errorMessage = e.message ?: "Impossible d'ajouter cet ami."
                    )
                }
            }
        }
    }
}
