package com.example.ui.party

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
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

enum class PartyGameMode {
    HUB,
    UNDERCOVER,
    ACTION_OU_VERITE,
    QUI_POURRAIT,
    JE_NAI_JAMAIS,
    ROULETTE_BARMAN
}

@Composable
fun PartyHubScreen(
    undercoverUiState: UndercoverUiState,
    onUndercoverIntent: (UndercoverIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeGame by remember { mutableStateOf(PartyGameMode.HUB) }

    androidx.activity.compose.BackHandler(enabled = activeGame != PartyGameMode.HUB) {
        if (activeGame == PartyGameMode.UNDERCOVER) {
            onUndercoverIntent(UndercoverIntent.QuitGame)
        }
        activeGame = PartyGameMode.HUB
    }

    AnimatedContent(
        targetState = activeGame,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "party_hub_navigation"
    ) { mode ->
        when (mode) {
            PartyGameMode.HUB -> {
                PartyHubHomeView(
                    onSelectGame = { activeGame = it },
                    modifier = modifier
                )
            }
            PartyGameMode.UNDERCOVER -> {
                UndercoverScreen(
                    uiState = undercoverUiState,
                    onIntent = onUndercoverIntent,
                    onExitGame = {
                        onUndercoverIntent(UndercoverIntent.QuitGame)
                        activeGame = PartyGameMode.HUB
                    },
                    modifier = modifier
                )
            }
            PartyGameMode.ACTION_OU_VERITE -> {
                ActionOuVeriteScreen(
                    onExitToHub = { activeGame = PartyGameMode.HUB }
                )
            }
            PartyGameMode.QUI_POURRAIT -> {
                QuiPourraitScreen(
                    onExitToHub = { activeGame = PartyGameMode.HUB }
                )
            }
            PartyGameMode.JE_NAI_JAMAIS -> {
                JeNaiJamaisScreen(
                    onExitToHub = { activeGame = PartyGameMode.HUB }
                )
            }
            PartyGameMode.ROULETTE_BARMAN -> {
                RouletteBarmanScreen(
                    onExitToHub = { activeGame = PartyGameMode.HUB }
                )
            }
        }
    }
}

@Composable
private fun PartyHubHomeView(
    onSelectGame: (PartyGameMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VelvetSurface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, VelvetGlassBorder, RoundedCornerShape(22.dp)),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        VelvetSurfaceHigh,
                                        VelvetSurfaceLowest
                                    )
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(VelvetPrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Casino,
                                        contentDescription = null,
                                        tint = VelvetOnPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Speakeasy Party Hub",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "5 jeux interactifs pour vos soirées",
                                        fontSize = 11.sp,
                                        color = VelvetPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            Text(
                                text = "Choisissez un jeu, lancez le tour et défiez vos invités autour d'un bon cocktail !",
                                fontSize = 12.sp,
                                color = VelvetOnSurfaceVariant,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // 1. UNDERCOVER SPEAKEASY (Featured)
            item {
                PartyGameCard(
                    title = "Undercover Speakeasy",
                    tag = "POPULAIRE • 3-12 JOUEURS",
                    tagColor = VelvetPrimary,
                    tagBg = VelvetPrimaryContainer.copy(alpha = 0.35f),
                    description = "Chaque joueur reçoit un mot secret. Un imposteur s'est infiltré : démasquez-le avant la fin du chrono !",
                    icon = Icons.Default.TheaterComedy,
                    accentColor = VelvetPrimary,
                    testTag = "game_card_undercover",
                    onClick = { onSelectGame(PartyGameMode.UNDERCOVER) }
                )
            }

            // 2. ACTION OU VÉRITÉ : SPÉCIAL BAR
            item {
                PartyGameCard(
                    title = "Action ou Vérité : Spécial Bar",
                    tag = "DÉFIS & CONFESSIONS",
                    tagColor = VelvetSecondary,
                    tagBg = VelvetSecondaryContainer.copy(alpha = 0.35f),
                    description = "Gages déjantés et questions piquantes spécialement conçues pour animer les dégustations de cocktails.",
                    icon = Icons.Default.FlashOn,
                    accentColor = VelvetSecondary,
                    testTag = "game_card_action_verite",
                    onClick = { onSelectGame(PartyGameMode.ACTION_OU_VERITE) }
                )
            }

            // 3. QUI POURRAIT ?
            item {
                PartyGameCard(
                    title = "Qui pourrait ?",
                    tag = "DILEMMES DE SOIRÉE",
                    tagColor = VelvetTertiary,
                    tagBg = VelvetTertiaryContainer.copy(alpha = 0.35f),
                    description = "À 3, tout le monde pointe le coupable du doigt. La personne la plus désignée boit 1 gorgée !",
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    accentColor = VelvetTertiary,
                    testTag = "game_card_qui_pourrait",
                    onClick = { onSelectGame(PartyGameMode.QUI_POURRAIT) }
                )
            }

            // 4. JE N'AI JAMAIS
            item {
                PartyGameCard(
                    title = "Je n'ai jamais",
                    tag = "ANECDOTES & GORGÉES",
                    tagColor = VelvetSecondary,
                    tagBg = VelvetSecondaryContainer.copy(alpha = 0.35f),
                    description = "Découvrez les pires secrets et anecdotes de vos amis. Ceux qui l'ont déjà fait boivent !",
                    icon = Icons.Default.LocalBar,
                    accentColor = VelvetSecondary,
                    testTag = "game_card_je_nai_jamais",
                    onClick = { onSelectGame(PartyGameMode.JE_NAI_JAMAIS) }
                )
            }

            // 5. ROULETTE DU BARMAN
            item {
                PartyGameCard(
                    title = "Roulette du Barman",
                    tag = "LE HASARD DU MIXOLOGUE",
                    tagColor = VelvetPrimary,
                    tagBg = VelvetPrimaryContainer.copy(alpha = 0.35f),
                    description = "Faites tourner la roue : gorgées, distributions, immunités royales ou cul-sec immédiat !",
                    icon = Icons.Default.Casino,
                    accentColor = VelvetPrimary,
                    testTag = "game_card_roulette_barman",
                    onClick = { onSelectGame(PartyGameMode.ROULETTE_BARMAN) }
                )
            }
        }
    }
}

@Composable
private fun PartyGameCard(
    title: String,
    tag: String,
    tagColor: Color,
    tagBg: Color,
    description: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(accentColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(tagBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = tag,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = tagColor
                            )
                        }
                    }
                }

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetPrimaryContainer,
                        contentColor = VelvetOnPrimary
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("Jouer", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Text(
                text = description,
                fontSize = 12.sp,
                color = VelvetOnSurfaceVariant,
                lineHeight = 17.sp
            )
        }
    }
}
