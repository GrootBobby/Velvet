package com.example.ui.friends

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.firebase.FriendLeaderboardEntry
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary

@Composable
fun FriendsScreen(
    uiState: FriendsUiState,
    onIntent: (FriendsIntent) -> Unit,
    onOpenAuth: () -> Unit,
    onCocktailClick: ((Long) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            onIntent(FriendsIntent.ClearMessages)
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { err ->
            Toast.makeText(context, err, Toast.LENGTH_LONG).show()
            onIntent(FriendsIntent.ClearMessages)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Header Title & Refresh
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = null,
                            tint = VelvetPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Amis & Classement",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    Text(
                        text = "Défiez vos proches et comparez vos niveaux de shaker",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = VelvetOnSurfaceVariant
                        )
                    )
                }

                if (!uiState.isGuestMode) {
                    IconButton(
                        onClick = { onIntent(FriendsIntent.LoadLeaderboard) },
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("refresh_friends_leaderboard_button")
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = VelvetPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Actualiser le classement",
                                tint = VelvetPrimary
                            )
                        }
                    }
                }
            }
        }

        // 1. GUEST MODE NOTICE
        if (uiState.isGuestMode) {
            item {
                GuestModeCard(onOpenAuth = onOpenAuth)
            }
        } else {
            // 2. CONNECTED MODE: MY FRIEND CODE CARD
            item {
                MyFriendCodeCard(
                    friendCode = uiState.myFriendCode,
                    onCopyCode = {
                        clipboardManager.setText(AnnotatedString(uiState.myFriendCode))
                        Toast.makeText(context, "Code ami copié dans le presse-papier !", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // 3. ADD FRIEND INPUT SECTION
            item {
                AddFriendCard(
                    friendCodeInput = uiState.friendCodeInput,
                    isAdding = uiState.isAddingFriend,
                    onInputChange = { onIntent(FriendsIntent.UpdateFriendCodeInput(it)) },
                    onAddFriend = { onIntent(FriendsIntent.AddFriend) }
                )
            }

            // 4. LEADERBOARD HEADER
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CLASSEMENT DU BAR",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetPrimary,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "${uiState.leaderboard.size} barman(s)",
                        fontSize = 12.sp,
                        color = VelvetOnSurfaceVariant
                    )
                }
            }

            // 5. LEADERBOARD ENTRIES
            if (uiState.leaderboard.isEmpty() && !uiState.isLoading) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🍸", fontSize = 32.sp)
                            Text(
                                text = "Aucun ami ajouté pour l'instant",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Partagez votre Code Ami ou entrez celui de vos amis pour lancer la compétition !",
                                fontSize = 12.sp,
                                color = VelvetOnSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                itemsIndexed(uiState.leaderboard, key = { _, item -> item.uid }) { index, entry ->
                    LeaderboardEntryCard(
                        rank = index + 1,
                        entry = entry,
                        onCocktailClick = onCocktailClick
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun GuestModeCard(
    onOpenAuth: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp))
            .testTag("friends_guest_mode_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E193C))
                    .border(1.dp, VelvetPrimary.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = VelvetPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Mode Invité",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Connectez-vous pour ajouter vos amis de soirée et comparer vos niveaux !",
                fontSize = 13.sp,
                color = VelvetOnSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 18.sp
            )

            Button(
                onClick = onOpenAuth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("friends_guest_login_button"),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetPrimary,
                    contentColor = VelvetOnPrimary
                )
            ) {
                Text(
                    text = "Se connecter / Créer un compte",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun MyFriendCodeCard(
    friendCode: String,
    onCopyCode: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, VelvetPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            .testTag("my_friend_code_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VOTRE CODE AMI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VelvetPrimary,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Prêt à partager",
                    fontSize = 11.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(VelvetSurfaceLowest)
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = friendCode.ifBlank { "CHARGEMENT..." },
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = VelvetTertiary,
                    letterSpacing = 2.sp,
                    modifier = Modifier.testTag("my_friend_code_display")
                )

                IconButton(
                    onClick = onCopyCode,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("copy_friend_code_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copier le code ami",
                        tint = VelvetPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = "Donnez ce code à vos amis pour qu'ils vous ajoutent dans leur classement !",
                fontSize = 11.sp,
                color = VelvetOnSurfaceVariant
            )
        }
    }
}

@Composable
private fun AddFriendCard(
    friendCodeInput: String,
    isAdding: Boolean,
    onInputChange: (String) -> Unit,
    onAddFriend: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp))
            .testTag("add_friend_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "AJOUTER UN AMI",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VelvetPrimary,
                letterSpacing = 1.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = friendCodeInput,
                    onValueChange = onInputChange,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("friend_code_input"),
                    placeholder = {
                        Text(
                            text = "Ex: ALEX#1234",
                            fontSize = 13.sp,
                            color = VelvetOnSurfaceVariant
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder,
                        focusedContainerColor = VelvetSurfaceLowest,
                        unfocusedContainerColor = VelvetSurfaceLowest
                    )
                )

                Button(
                    onClick = onAddFriend,
                    enabled = friendCodeInput.isNotBlank() && !isAdding,
                    modifier = Modifier
                        .height(52.dp)
                        .testTag("add_friend_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetPrimary,
                        contentColor = VelvetOnPrimary
                    )
                ) {
                    if (isAdding) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = VelvetOnPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = "Ajouter l'ami",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ajouter", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LeaderboardEntryCard(
    rank: Int,
    entry: FriendLeaderboardEntry,
    onCocktailClick: ((Long) -> Unit)?
) {
    val rankBadgeColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> VelvetOnSurfaceVariant
    }

    val rankEmoji = when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "#$rank"
    }

    val isSelf = entry.isCurrentUser

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = if (isSelf) 1.5.dp else 1.dp,
                color = if (isSelf) VelvetPrimary else VelvetGlassBorder,
                shape = RoundedCornerShape(18.dp)
            )
            .testTag("leaderboard_item_${entry.uid}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelf) VelvetSurfaceHighest else VelvetSurfaceHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Rank Badge
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (rank <= 3) {
                                    Brush.radialGradient(
                                        listOf(rankBadgeColor.copy(alpha = 0.35f), Color(0xFF1E1528))
                                    )
                                } else {
                                    Brush.radialGradient(listOf(VelvetSurfaceLowest, Color(0xFF15101F)))
                                }
                            )
                            .border(1.dp, rankBadgeColor.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = rankEmoji,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (rank <= 3) 16.sp else 13.sp,
                            color = rankBadgeColor
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = entry.userName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (isSelf) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(VelvetPrimary.copy(alpha = 0.25f))
                                        .border(0.5.dp, VelvetPrimary, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "VOUS",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = VelvetPrimary
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Code: ${entry.friendCode.ifBlank { "N/A" }}",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = VelvetOnSurfaceVariant
                        )
                    }
                }

                // Level & XP Pill
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Niveau ${entry.level}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = VelvetSecondary
                    )
                    Text(
                        text = "${entry.totalXp} XP",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = VelvetTertiary
                    )
                }
            }

            // Top 3 Favorite Cocktails
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(VelvetSurfaceLowest)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalBar,
                    contentDescription = null,
                    tint = VelvetPrimary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))

                if (entry.favoriteCocktailNames.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        entry.favoriteCocktailNames.take(3).forEachIndexed { i, name ->
                            val cocktailId = entry.favoriteCocktailIds.getOrNull(i)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(VelvetSurfaceHighest)
                                    .clickable(enabled = cocktailId != null && onCocktailClick != null) {
                                        if (cocktailId != null && onCocktailClick != null) {
                                            onCocktailClick(cocktailId)
                                        }
                                    }
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = name,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Aucun cocktail favori enregistré",
                        fontSize = 11.sp,
                        color = VelvetOnSurfaceVariant
                    )
                }
            }
        }
    }
}
