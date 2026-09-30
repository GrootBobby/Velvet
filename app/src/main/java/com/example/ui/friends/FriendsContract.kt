package com.example.ui.friends

import com.example.data.firebase.FriendLeaderboardEntry

data class FriendsUiState(
    val isLoading: Boolean = false,
    val isGuestMode: Boolean = true,
    val currentUid: String = "",
    val myFriendCode: String = "",
    val myUserName: String = "",
    val friendCodeInput: String = "",
    val isAddingFriend: Boolean = false,
    val leaderboard: List<FriendLeaderboardEntry> = emptyList(),
    val statusMessage: String? = null,
    val errorMessage: String? = null,
    val showAuthDialog: Boolean = false
)

sealed interface FriendsIntent {
    data object LoadLeaderboard : FriendsIntent
    data class UpdateFriendCodeInput(val code: String) : FriendsIntent
    data object AddFriend : FriendsIntent
    data object ClearMessages : FriendsIntent
    data object OpenAuthDialog : FriendsIntent
    data object DismissAuthDialog : FriendsIntent
}
