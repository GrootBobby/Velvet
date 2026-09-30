package com.example.ui.party

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.VelvetError
import com.example.ui.theme.VelvetErrorContainer
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetOutline
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetPrimaryContainer
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSecondaryContainer
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLow
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary
import com.example.ui.theme.VelvetTertiaryContainer

@Composable
fun UndercoverScreen(
    uiState: UndercoverUiState,
    onIntent: (UndercoverIntent) -> Unit,
    onExitGame: () -> Unit = { onIntent(UndercoverIntent.RestartConfig) },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            onIntent(UndercoverIntent.ClearToast)
        }
    }

    when (uiState.phase) {
        UndercoverPhase.CONFIG -> {
            UndercoverConfigView(
                uiState = uiState,
                onIntent = onIntent,
                onExitGame = onExitGame,
                modifier = modifier
            )
        }
        UndercoverPhase.PASS_PHONE -> {
            UndercoverPassPhoneView(
                uiState = uiState,
                onIntent = onIntent,
                onExitGame = onExitGame,
                context = context,
                modifier = modifier
            )
        }
        UndercoverPhase.DEBATE -> {
            UndercoverDebateView(
                uiState = uiState,
                onIntent = onIntent,
                onExitGame = onExitGame,
                modifier = modifier
            )
        }
    }
}

// -------------------------------------------------------------
// 1. CONFIGURATION VIEW (100% LOCAL & INSTANTANÉ)
// -------------------------------------------------------------
@Composable
private fun UndercoverConfigView(
    uiState: UndercoverUiState,
    onIntent: (UndercoverIntent) -> Unit,
    onExitGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(VelvetSurfaceHigh)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(VelvetTertiary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SALON PRIVÉ • 100% LOCAL & HORS-LIGNE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetTertiary,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Undercover",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Button(
                        onClick = onExitGame,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VelvetSurfaceHighest,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("undercover_exit_to_hub_button")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Quitter", modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Hub Jeux", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text(
                    text = "Chaque joueur reçoit un mot secret. Démasquez les infiltrés et Mr. White au fil des discussions !",
                    fontSize = 13.sp,
                    color = VelvetOnSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
        }

        // Atmospheric Local Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDnm7zhs5zhGUfuI5PP0WR3SutnaKTmBxbmMAWoSuIsa74_I407PeN3c7pWGNH0atpqLvVJWejeFf1FmQ4S_IkXsP1Ua6c4W0nd3FgX2S7x1mrUg-RFqiZ57Y5jJYCO0QcrHPXNSEKPfCVa_XbG_F2ETqlZ3rCiqau9WEe2wVtW48iirVn13xfZQoILVOCSem8KfKAStLDkbZQtaHJWczxX0ynBBSRlUHH54gZn2Sv6sEhdY4kaolEnhg",
                    contentDescription = "Speakeasy lounge",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xDD121317))
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = VelvetTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "200 Paires de Mots Locales",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(VelvetSurfaceLowest)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.FlashOn, contentDescription = null, tint = VelvetTertiary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Instantané",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = VelvetTertiary
                            )
                        }
                    }
                }
            }
        }

        // Effectif & Équilibre (Player Count & Roles)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(VelvetSurfaceHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Effectif & Équilibre",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Distribution automatique des rôles",
                                    fontSize = 11.sp,
                                    color = VelvetOnSurfaceVariant
                                )
                            }
                        }

                        // Stepper (- / count / +)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(VelvetSurfaceLowest)
                                .padding(4.dp)
                        ) {
                            IconButton(
                                onClick = { onIntent(UndercoverIntent.SetPlayerCount(uiState.totalPlayers - 1)) },
                                modifier = Modifier.size(28.dp).testTag("stepper_minus_player")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Moins",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }

                            Text(
                                text = uiState.totalPlayers.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = VelvetPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = { onIntent(UndercoverIntent.SetPlayerCount(uiState.totalPlayers + 1)) },
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(VelvetPrimaryContainer)
                                    .testTag("stepper_plus_player")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Plus",
                                    tint = VelvetOnPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    // Bento Grid : Civils, Infiltré, Mr. White
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RoleMetricCard(
                            role = "CIVILS",
                            count = uiState.civilsCount,
                            subtext = "Mot officiel",
                            icon = Icons.Default.LocalBar,
                            iconTint = VelvetSecondary,
                            modifier = Modifier.weight(1f)
                        )
                        RoleMetricCard(
                            role = "INFILTRÉ",
                            count = uiState.undercoverCount,
                            subtext = "Mot approchant",
                            icon = Icons.Default.VisibilityOff,
                            iconTint = VelvetPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        RoleMetricCard(
                            role = "MR. WHITE",
                            count = uiState.whiteCount,
                            subtext = "Aucun mot",
                            icon = Icons.Default.Lock,
                            iconTint = VelvetTertiary,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Saisie des Prénoms de chaque joueur
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(VelvetSurfaceLowest)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = VelvetPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Prénoms des Joueurs (${uiState.playerNames.size})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "Min. 3 requis",
                                fontSize = 10.sp,
                                color = VelvetPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = "Ces prénoms apparaîtront lors du passage de téléphone.",
                            fontSize = 10.sp,
                            color = VelvetOnSurfaceVariant
                        )

                        uiState.playerNames.forEachIndexed { index, name ->
                            OutlinedTextField(
                                value = name,
                                onValueChange = { onIntent(UndercoverIntent.SetPlayerName(index, it)) },
                                label = { Text("Joueur ${index + 1}") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = VelvetPrimary,
                                    unfocusedBorderColor = VelvetGlassBorder,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("player_name_input_$index")
                            )
                        }
                    }

                    // Chrono Débat (Configurable de 30s à 180s)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(VelvetSurfaceLowest)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = VelvetTertiary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Chrono Débat : ${uiState.debateTimerSeconds}s",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Temps de discussion par manche",
                                        fontSize = 11.sp,
                                        color = VelvetOnSurfaceVariant
                                    )
                                }
                            }

                            Switch(
                                checked = uiState.isTimerEnabled,
                                onCheckedChange = { onIntent(UndercoverIntent.ToggleTimerEnabled(it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = VelvetOnPrimary,
                                    checkedTrackColor = VelvetPrimaryContainer,
                                    uncheckedThumbColor = VelvetOnSurfaceVariant,
                                    uncheckedTrackColor = VelvetSurfaceHighest
                                )
                            )
                        }

                        if (uiState.isTimerEnabled) {
                            Slider(
                                value = uiState.debateTimerSeconds.toFloat(),
                                onValueChange = { onIntent(UndercoverIntent.SetDebateDuration(it.toInt())) },
                                valueRange = 30f..180f,
                                steps = 9,
                                colors = SliderDefaults.colors(
                                    thumbColor = VelvetTertiary,
                                    activeTrackColor = VelvetTertiary
                                ),
                                modifier = Modifier.testTag("debate_timer_slider")
                            )
                        }
                    }
                }
            }
        }

        // Launch Game CTA
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { onIntent(UndercoverIntent.StartGame) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("launch_game_button"),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetPrimaryContainer,
                        contentColor = VelvetOnPrimary
                    )
                ) {
                    Text(
                        text = "Lancer la partie & Distribuer",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = VelvetOutline,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Attribution secrète immédiate hors-ligne",
                        fontSize = 11.sp,
                        color = VelvetOutline
                    )
                }
            }
        }
    }
}

@Composable
private fun RoleMetricCard(
    role: String,
    count: Int,
    subtext: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(VelvetSurfaceLowest)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = role,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = iconTint,
                letterSpacing = 0.5.sp
            )
            Text(
                text = count.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtext,
                fontSize = 9.sp,
                color = VelvetOnSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

// -------------------------------------------------------------
// 2. PASS THE PHONE CONFIDENTIAL VIEW
// -------------------------------------------------------------
@Composable
private fun UndercoverPassPhoneView(
    uiState: UndercoverUiState,
    onIntent: (UndercoverIntent) -> Unit,
    onExitGame: () -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    val activePlayer = uiState.players.getOrNull(uiState.activePlayerIndex) ?: return
    val totalCount = uiState.players.size
    val step = uiState.activePlayerIndex + 1
    val isLast = step == totalCount
    val nextPlayerName = uiState.players.getOrNull(uiState.activePlayerIndex + 1)?.name ?: "la table"

    fun triggerVibration() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? android.os.VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(40)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Enveloppe Scellée Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(VelvetSurfaceLow)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = VelvetPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ENVELOPPE SCELLÉE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetPrimary,
                        letterSpacing = 1.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(VelvetSurfaceHighest)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Étape $step sur $totalCount",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetSecondary
                        )
                    }

                    // Bouton Quitter
                    Button(
                        onClick = onExitGame,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VelvetErrorContainer.copy(alpha = 0.5f),
                            contentColor = VelvetError
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier
                            .height(28.dp)
                            .testTag("quit_undercover_pass_button")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Quitter", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Quitter", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Active Player Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar
                    AsyncImage(
                        model = activePlayer.avatarUrl,
                        contentDescription = activePlayer.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .border(2.dp, VelvetPrimary.copy(alpha = 0.6f), CircleShape)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "À TON TOUR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetOnSurfaceVariant,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = activePlayer.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Caution Warning Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(VelvetErrorContainer.copy(alpha = 0.35f))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = VelvetError,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Assure-toi que personne ne regarde ton écran.",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VelvetError
                            )
                        }
                    }

                    // Tactile Biometric Press-and-Hold Reveal Box
                    val isRevealed = uiState.isRoleRevealed
                    val pulseScale by animateFloatAsState(
                        targetValue = if (isRevealed) 1.15f else 1.0f,
                        label = "pulse"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(VelvetSurfaceLowest)
                            .border(
                                1.dp,
                                if (isRevealed) VelvetPrimary else VelvetGlassBorder,
                                RoundedCornerShape(14.dp)
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        triggerVibration()
                                        onIntent(UndercoverIntent.SetRoleRevealState(true))
                                        tryAwaitRelease()
                                        triggerVibration()
                                        onIntent(UndercoverIntent.SetRoleRevealState(false))
                                    }
                                )
                            }
                            .testTag("hold_to_reveal_box"),
                        contentAlignment = Alignment.Center
                    ) {
                        // Blurred Secret Content
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .blur(if (isRevealed) 0.dp else 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            when (activePlayer.role) {
                                PlayerRole.CIVIL -> {
                                    Text(
                                        text = "CIVIL",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VelvetSecondary,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = activePlayer.secretWord,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = VelvetPrimary
                                    )
                                    Text(
                                        text = "Tu as le mot officiel !",
                                        fontSize = 11.sp,
                                        color = VelvetOnSurfaceVariant
                                    )
                                }
                                PlayerRole.UNDERCOVER -> {
                                    Text(
                                        text = "INFILTRÉ",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VelvetError,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = activePlayer.secretWord,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = VelvetPrimary
                                    )
                                    Text(
                                        text = "Fais croire que ton mot est le mot officiel !",
                                        fontSize = 11.sp,
                                        color = VelvetOnSurfaceVariant
                                    )
                                }
                                PlayerRole.MR_WHITE -> {
                                    Text(
                                        text = "MR. WHITE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VelvetTertiary,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = "AUCUN MOT",
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = VelvetTertiary
                                    )
                                    Text(
                                        text = "Écoute bien et devine le mot des autres !",
                                        fontSize = 11.sp,
                                        color = VelvetOnSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Fingerprint prompt overlay if hidden
                        if (!isRevealed) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(VelvetSurfaceLowest.copy(alpha = 0.85f)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .scale(pulseScale)
                                        .clip(CircleShape)
                                        .background(VelvetSecondaryContainer.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Fingerprint,
                                        contentDescription = "Maintenir",
                                        tint = VelvetPrimary,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Maintenir pour révéler mon mot",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Relâche pour masquer instantanément",
                                    fontSize = 11.sp,
                                    color = VelvetOutline
                                )
                            }
                        }
                    }
                }
            }
        }

        // Handover CTA Button
        item {
            Button(
                onClick = { onIntent(UndercoverIntent.NextPlayer) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("pass_phone_next_button"),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetPrimaryContainer,
                    contentColor = VelvetOnPrimary
                )
            ) {
                Text(
                    text = if (isLast) "Lancer la discussion & le débat" else "Passer le téléphone à $nextPlayerName",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// 3. DEBATE & DISCUSSION VIEW
// -------------------------------------------------------------
@Composable
private fun UndercoverDebateView(
    uiState: UndercoverUiState,
    onIntent: (UndercoverIntent) -> Unit,
    onExitGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    val minutes = uiState.debateTimeRemaining / 60
    val seconds = uiState.debateTimeRemaining % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header with Quit Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(VelvetSurfaceLow)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DÉBAT & DÉDUCTION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = VelvetPrimary,
                    letterSpacing = 1.sp
                )

                Button(
                    onClick = onExitGame,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetErrorContainer.copy(alpha = 0.5f),
                        contentColor = VelvetError
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier
                        .height(28.dp)
                        .testTag("quit_undercover_debate_button")
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Quitter", modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Quitter", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Debate Countdown Timer Card
        if (uiState.isTimerEnabled) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, VelvetTertiary.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "CHRONOMÈTRE DE DÉBAT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetTertiary,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = timeFormatted,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (uiState.debateTimeRemaining <= 10) VelvetError else VelvetPrimary
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            IconButton(
                                onClick = {
                                    if (uiState.isDebateTimerRunning) {
                                        onIntent(UndercoverIntent.PauseDebateTimer)
                                    } else {
                                        onIntent(UndercoverIntent.StartDebateTimer)
                                    }
                                },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(VelvetPrimaryContainer)
                                    .testTag("debate_play_pause_button")
                            ) {
                                Icon(
                                    imageVector = if (uiState.isDebateTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Lecture / Pause",
                                    tint = VelvetOnPrimary
                                )
                            }

                            IconButton(
                                onClick = { onIntent(UndercoverIntent.ResetDebateTimer) },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(VelvetSurfaceHighest)
                                    .testTag("debate_reset_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Réinitialiser",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // Player Elimination Roster
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Joueurs en lice (touchez pour éliminer) :",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                uiState.players.forEach { player ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (player.isEliminated) VelvetSurfaceLowest else VelvetSurfaceLow)
                            .clickable { onIntent(UndercoverIntent.TogglePlayerEliminated(player.id)) }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = player.avatarUrl,
                                contentDescription = player.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = player.name,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = if (player.isEliminated) VelvetOutline else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (player.isEliminated) VelvetErrorContainer else VelvetSurfaceHighest)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (player.isEliminated) "ÉLIMINÉ" else "EN JEU",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (player.isEliminated) VelvetError else VelvetPrimary
                            )
                        }
                    }
                }
            }
        }

        // Révéler les Imposteurs Section (Demandée)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { onIntent(UndercoverIntent.RevealImpostors) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("reveal_impostors_button"),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.areImpostorsRevealed) VelvetSecondaryContainer else VelvetTertiaryContainer,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = if (uiState.areImpostorsRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uiState.areImpostorsRevealed) "Masquer la vérité" else "Révéler les Imposteurs & Mots",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                AnimatedVisibility(visible = uiState.areImpostorsRevealed) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, VelvetTertiary, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "🎭 VÉRITÉ DU JEU",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                color = VelvetTertiary,
                                letterSpacing = 1.sp
                            )

                            // Mots secrets
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(VelvetSurfaceLowest)
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Mot Civil", fontSize = 10.sp, color = VelvetSecondary, fontWeight = FontWeight.Bold)
                                    Text(uiState.civilWord, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(30.dp)
                                        .background(VelvetGlassBorder)
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Mot Infiltré", fontSize = 10.sp, color = VelvetError, fontWeight = FontWeight.Bold)
                                    Text(uiState.undercoverWord, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }

                            // Rôles de chaque joueur
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                uiState.players.forEach { p ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(VelvetSurfaceLowest.copy(alpha = 0.6f))
                                            .padding(horizontal = 10.dp, vertical = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = p.name,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )

                                        val roleBadgeColor = when (p.role) {
                                            PlayerRole.CIVIL -> VelvetSecondary
                                            PlayerRole.UNDERCOVER -> VelvetError
                                            PlayerRole.MR_WHITE -> VelvetTertiary
                                        }

                                        Text(
                                            text = "${p.role.label} (${if (p.secretWord.isNotBlank()) p.secretWord else "Aucun mot"})",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = roleBadgeColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Actions de fin de partie
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onIntent(UndercoverIntent.RestartConfig) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("restart_undercover_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetSurfaceHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Rejouer", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onExitGame,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("quit_undercover_to_hub_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetErrorContainer.copy(alpha = 0.5f),
                        contentColor = VelvetError
                    )
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Quitter", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
