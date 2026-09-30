package com.example.ui.party

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VelvetError
import com.example.ui.theme.VelvetErrorContainer
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
import kotlinx.coroutines.launch
import kotlin.random.Random

// -------------------------------------------------------------
// TOP BAR COMMUNE AVEC BOUTON "QUITTER LA PARTIE" (CROIX)
// -------------------------------------------------------------
@Composable
fun GameHeaderBar(
    title: String,
    badgeText: String,
    badgeColor: Color,
    onExitToHub: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VelvetSurfaceLow)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(badgeColor.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = badgeText,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeColor
                )
            }
        }

        // Bouton Quitter la partie (icône croix clearly visible)
        Button(
            onClick = onExitToHub,
            colors = ButtonDefaults.buttonColors(
                containerColor = VelvetErrorContainer.copy(alpha = 0.5f),
                contentColor = VelvetError
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier
                .height(34.dp)
                .testTag("quit_game_to_hub_button")
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Quitter la partie",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Quitter", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// =============================================================
// 1. ACTION OU VÉRITÉ : SPÉCIAL BAR
// =============================================================
@Composable
fun ActionOuVeriteScreen(
    onExitToHub: () -> Unit
) {
    BackHandler { onExitToHub() }

    val allItems = PartyGamesData.actionVeriteList
    var currentItem by remember { mutableStateOf(allItems.random()) }
    var selectedFilter by remember { mutableStateOf<TruthOrDareType?>(null) }
    var cardIndex by remember { mutableIntStateOf(1) }

    fun drawNext() {
        val pool = when (selectedFilter) {
            TruthOrDareType.ACTION -> allItems.filter { it.type == TruthOrDareType.ACTION }
            TruthOrDareType.VERITE -> allItems.filter { it.type == TruthOrDareType.VERITE }
            null -> allItems
        }
        currentItem = pool.random()
        cardIndex++
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = VelvetSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameHeaderBar(
                title = "Action ou Vérité",
                badgeText = "SPÉCIAL BAR",
                badgeColor = VelvetPrimary,
                onExitToHub = onExitToHub
            )

            // Sélecteur de mode (Tous / Action / Vérité)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(VelvetSurfaceLow)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    null to "Aléatoire",
                    TruthOrDareType.VERITE to "Vérités 🤫",
                    TruthOrDareType.ACTION to "Actions ⚡"
                ).forEach { (type, label) ->
                    val isSelected = selectedFilter == type
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) VelvetPrimaryContainer else Color.Transparent)
                            .clickable {
                                selectedFilter = type
                                drawNext()
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) VelvetOnPrimary else VelvetOnSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.1f))

            // Carte de jeu principale
            AnimatedContent(
                targetState = currentItem to cardIndex,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "aov_anim"
            ) { (item, _) ->
                val isAction = item.type == TruthOrDareType.ACTION
                val cardBorder = if (isAction) VelvetSecondary else VelvetPrimary
                val tagColor = if (isAction) VelvetSecondary else VelvetPrimary
                val tagBg = if (isAction) VelvetSecondaryContainer.copy(alpha = 0.35f) else VelvetPrimaryContainer.copy(alpha = 0.35f)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, cardBorder.copy(alpha = 0.6f), RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(tagBg)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (isAction) "⚡ ACTION DU BAR" else "🤫 VÉRITÉ NOCTURNE",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = tagColor,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = item.text,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp
                        )

                        Text(
                            text = "Si le joueur refuse : 2 gorgées de pénalité !",
                            fontSize = 11.sp,
                            color = VelvetTertiary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.2f))

            // Bouton Suivant
            Button(
                onClick = { drawNext() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("aov_next_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetPrimaryContainer,
                    contentColor = VelvetOnPrimary
                )
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tirer le suivant", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// =============================================================
// 2. QUI POURRAIT ?
// =============================================================
@Composable
fun QuiPourraitScreen(
    onExitToHub: () -> Unit
) {
    BackHandler { onExitToHub() }

    val allQuestions = PartyGamesData.quiPourraitList
    var currentIndex by remember { mutableIntStateOf(Random.nextInt(allQuestions.size)) }
    var countPlayed by remember { mutableIntStateOf(1) }

    fun nextQuestion() {
        currentIndex = (currentIndex + 1) % allQuestions.size
        countPlayed++
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = VelvetSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameHeaderBar(
                title = "Qui pourrait ?",
                badgeText = "DILEMMES DE SOIRÉE",
                badgeColor = VelvetTertiary,
                onExitToHub = onExitToHub
            )

            // Consigne
            Text(
                text = "À 3, tout le monde pointe du doigt le coupable ! La personne la plus désignée boit 1 gorgée.",
                fontSize = 12.sp,
                color = VelvetOnSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(0.1f))

            // Carte principale
            AnimatedContent(
                targetState = currentIndex to countPlayed,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "qui_pourrait_anim"
            ) { (idx, _) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, VelvetTertiary.copy(alpha = 0.5f), RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "👉 🍸 👈", fontSize = 32.sp)

                        Text(
                            text = allQuestions[idx],
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )

                        Text(
                            text = "Carte #$countPlayed",
                            fontSize = 11.sp,
                            color = VelvetTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.2f))

            // Bouton Suivant
            Button(
                onClick = { nextQuestion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("qui_pourrait_next_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetTertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Dilemme Suivant", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

// =============================================================
// 3. JE N'AI JAMAIS
// =============================================================
@Composable
fun JeNaiJamaisScreen(
    onExitToHub: () -> Unit
) {
    BackHandler { onExitToHub() }

    val list = PartyGamesData.jeNaiJamaisList
    var currentIndex by remember { mutableIntStateOf(Random.nextInt(list.size)) }
    var countPlayed by remember { mutableIntStateOf(1) }

    fun nextCard() {
        currentIndex = (currentIndex + 1) % list.size
        countPlayed++
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = VelvetSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameHeaderBar(
                title = "Je n'ai jamais",
                badgeText = "CONFESSIONS & GORGÉES",
                badgeColor = VelvetSecondary,
                onExitToHub = onExitToHub
            )

            Text(
                text = "Ceux qui l'ont DÉJÀ fait boivent 1 gorgée !",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = VelvetSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.1f))

            AnimatedContent(
                targetState = currentIndex to countPlayed,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "je_nai_jamais_anim"
            ) { (idx, _) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, VelvetSecondary.copy(alpha = 0.5f), RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "🥂", fontSize = 36.sp)

                        Text(
                            text = list[idx],
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(VelvetSurfaceLowest)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Carte #$countPlayed",
                                fontSize = 11.sp,
                                color = VelvetOnSurfaceVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.2f))

            Button(
                onClick = { nextCard() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("je_nai_jamais_next_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetSecondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Carte Suivante", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

// =============================================================
// 4. ROULETTE DU BARMAN
// =============================================================
@Composable
fun RouletteBarmanScreen(
    onExitToHub: () -> Unit
) {
    BackHandler { onExitToHub() }

    val rules = PartyGamesData.rouletteRules
    var currentRule by remember { mutableStateOf(rules.random()) }
    var isSpinning by remember { mutableStateOf(false) }
    var spinCount by remember { mutableIntStateOf(0) }
    val rotationAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    fun spinRoulette() {
        if (isSpinning) return
        isSpinning = true
        scope.launch {
            val extraSpins = 360f * 3 + Random.nextInt(360)
            rotationAnim.animateTo(
                targetValue = rotationAnim.value + extraSpins,
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
            currentRule = rules.random()
            spinCount++
            isSpinning = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = VelvetSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameHeaderBar(
                title = "Roulette du Barman",
                badgeText = "ROUE DU DESTIN",
                badgeColor = VelvetPrimary,
                onExitToHub = onExitToHub
            )

            Text(
                text = "Appuyez sur le bouton central pour déclencher la sentence du barman !",
                fontSize = 12.sp,
                color = VelvetOnSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.1f))

            // Roue / Bouton central animé
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(VelvetPrimaryContainer, VelvetSurfaceLow)
                        )
                    )
                    .border(4.dp, VelvetPrimary, CircleShape)
                    .clickable { spinRoulette() }
                    .testTag("spin_roulette_center_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Casino,
                    contentDescription = "Lancer",
                    tint = VelvetPrimary,
                    modifier = Modifier
                        .size(80.dp)
                        .rotate(rotationAnim.value)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 14.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isSpinning) "Tirage..." else "TOURNER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Affichage de la consigne
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, VelvetPrimary.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                when (currentRule.type) {
                                    "BOIRE" -> VelvetErrorContainer.copy(alpha = 0.6f)
                                    "DISTRIBUER" -> VelvetSecondaryContainer.copy(alpha = 0.6f)
                                    "BONUS" -> VelvetPrimaryContainer.copy(alpha = 0.6f)
                                    else -> VelvetTertiaryContainer.copy(alpha = 0.6f)
                                }
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentRule.type,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = currentRule.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = VelvetPrimary,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = currentRule.instruction,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.2f))

            // Bouton secondaire Lancer
            Button(
                onClick = { spinRoulette() },
                enabled = !isSpinning,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("spin_roulette_bottom_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetPrimaryContainer,
                    contentColor = VelvetOnPrimary
                )
            ) {
                Icon(imageVector = Icons.Default.Casino, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isSpinning) "La roulette tourne..." else "Lancer la Roulette du Barman", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
