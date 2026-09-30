package com.example.ui.detail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ui.music.MusicPlatformSelectorDialog
import com.example.ui.music.ambience
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.CocktailImage
import com.example.data.model.Cocktail
import com.example.data.model.RecipeIngredient
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetPrimaryContainer
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSecondaryContainer
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceContainer
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLow
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary
import com.example.ui.theme.VelvetTertiaryContainer

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onIntent: (DetailIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cocktail = uiState.cocktail
    var showMusicSelector by remember { mutableStateOf(false) }

    if (showMusicSelector && cocktail != null) {
        MusicPlatformSelectorDialog(
            ambience = cocktail.ambience,
            onDismiss = { showMusicSelector = false }
        )
    }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            onIntent(DetailIntent.ClearToast)
        }
    }

    if (cocktail == null) {
        Box(
            modifier = modifier.fillMaxSize().background(VelvetSurface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = VelvetPrimary)
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // 1. HERO VISUAL & ACTIONS
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                CocktailImage(
                    imageUrl = cocktail.imageUrl,
                    contentDescription = cocktail.name,
                    contentScale = ContentScale.Crop,
                    iconSize = 56.dp,
                    modifier = Modifier.fillMaxSize()
                )

                // Speakeasy atmosphere gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x99121317),
                                    Color.Transparent,
                                    VelvetSurface
                                )
                            )
                        )
                )

                // Top Bar overlay with back, share and favorite
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(VelvetSurfaceHigh.copy(alpha = 0.8f))
                            .testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Favorite Button
                        IconButton(
                            onClick = { onIntent(DetailIntent.ToggleFavorite) },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(VelvetSurfaceHigh.copy(alpha = 0.8f))
                                .testTag("detail_favorite_button")
                        ) {
                            Icon(
                                imageVector = if (cocktail.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favori",
                                tint = if (cocktail.isFavorite) VelvetPrimary else Color.White
                            )
                        }

                        // Share Button
                        IconButton(
                            onClick = {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Découvre ce cocktail speakeasy : ${cocktail.name} sur Velvet Cocktail !")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(VelvetSurfaceHigh.copy(alpha = 0.8f))
                                .testTag("detail_share_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.IosShare,
                                contentDescription = "Partager",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Floating badge: "Édition Nocturne"
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(VelvetSurfaceHighest.copy(alpha = 0.9f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(VelvetPrimary)
                        )
                        Text(
                            text = "ÉDITION NOCTURNE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetPrimary,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }

        // 2. HEADER INFO & SENSORY BADGES
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Category & Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = cocktail.subtitle.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(text = "•", color = VelvetOnSurfaceVariant)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = VelvetTertiary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = cocktail.rating.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetTertiary
                        )
                    }
                }

                // Name & Description
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = cocktail.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = VelvetOnSurfaceVariant,
                        lineHeight = 21.sp
                    )
                )

                // Sensory Badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Proof Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(VelvetTertiaryContainer.copy(alpha = 0.3f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalBar,
                                contentDescription = null,
                                tint = VelvetTertiary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${cocktail.alcoholPercentage}% Vol.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VelvetTertiary
                            )
                        }
                    }

                    // Vibe Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(VelvetSecondaryContainer.copy(alpha = 0.4f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Nightlife,
                                contentDescription = null,
                                tint = VelvetSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = cocktail.vibeTag,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VelvetSecondary
                            )
                        }
                    }
                }

                // Metrics Grid: Temps, Difficulté, Profil
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricBox(
                        icon = Icons.Default.Timer,
                        iconTint = VelvetPrimary,
                        label = "TEMPS",
                        value = "${cocktail.prepTimeMinutes} min",
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        icon = Icons.Default.Tune,
                        iconTint = VelvetSecondary,
                        label = "DIFFICULTÉ",
                        value = cocktail.difficulty,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        icon = Icons.Default.Palette,
                        iconTint = VelvetTertiary,
                        label = "PROFIL",
                        value = cocktail.flavorProfile,
                        modifier = Modifier.weight(1f)
                    )
                }

                // SECTION AMBIANCE MUSICALE & PLAYLIST DE DÉGUSTATION
                MusicAmbienceCard(
                    cocktail = cocktail,
                    onClickListen = { showMusicSelector = true }
                )
            }
        }

        // 3. INGRÉDIENTS & CONVERSIONS
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Ingrédients",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(VelvetSurfaceHighest)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${cocktail.ingredients.size} items",
                                fontSize = 10.sp,
                                color = VelvetOnSurfaceVariant
                            )
                        }
                    }
                }

                // Smart Tool Pills (AI & Conversions)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Trouver un substitut (IA)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(VelvetSurfaceHighest)
                            .clickable { onIntent(DetailIntent.RequestAiSubstitution(null)) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("ai_substitution_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = VelvetPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Trouver un substitut (IA)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VelvetPrimary
                            )
                        }
                    }

                    // Bascule dynamique cl <-> Cuillères / Verres
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (uiState.isAlternateUnits) VelvetPrimaryContainer else VelvetSurfaceHighest)
                            .clickable { onIntent(DetailIntent.ToggleUnits) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("convert_units_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = null,
                                tint = if (uiState.isAlternateUnits) VelvetOnPrimary else VelvetOnSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (uiState.isAlternateUnits) "Afficher en cl" else "cl ⇄ Cuillères / Verres",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (uiState.isAlternateUnits) VelvetOnPrimary else VelvetOnSurfaceVariant
                            )
                        }
                    }
                }

                // AI Substitution Banner if open
                AnimatedVisibility(visible = uiState.showAiSubstitution) {
                    AiSubstitutionCard(
                        missingIngredient = uiState.selectedMissingIngredient,
                        substitutionText = uiState.aiSubstitutionText,
                        isLoading = uiState.isSubstitutionLoading,
                        onClose = { onIntent(DetailIntent.CloseAiSubstitution) }
                    )
                }

                // Ingredient List
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    cocktail.ingredients.forEach { ingredient ->
                        IngredientRow(
                            ingredient = ingredient,
                            isAlternateUnits = uiState.isAlternateUnits,
                            onAskSubstitute = {
                                onIntent(DetailIntent.RequestAiSubstitution(ingredient.name))
                            }
                        )
                    }
                }
            }
        }

        // 4. PRÉPARATION PAS-À-PAS AVEC SHAKER TIMER
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Préparation Pas-à-Pas",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    if (cocktail.shakeSeconds > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (uiState.isShakerTimerActive) VelvetPrimaryContainer else VelvetSurfaceHighest)
                                .clickable {
                                    if (uiState.isShakerTimerActive) {
                                        onIntent(DetailIntent.CancelShakerTimer)
                                    } else {
                                        onIntent(DetailIntent.StartShakerTimer)
                                    }
                                }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .testTag("shaker_timer_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassTop,
                                contentDescription = null,
                                tint = if (uiState.isShakerTimerActive) VelvetOnPrimary else VelvetTertiary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (uiState.isShakerTimerActive)
                                    "Secouez ! ${uiState.shakerSecondsRemaining}s"
                                else
                                    "${cocktail.shakeSeconds}s de shake",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (uiState.isShakerTimerActive) VelvetOnPrimary else VelvetTertiary
                            )
                        }
                    }
                }

                // Steps list
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    cocktail.steps.forEachIndexed { index, stepText ->
                        StepRow(
                            stepNumber = index + 1,
                            text = stepText,
                            isHighlighted = (index == 2) // Step 3 highlighted as in HTML
                        )
                    }
                }

                // Verrerie Recommandée Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(VelvetSurfaceLowest)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(VelvetSurfaceHighest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WineBar,
                            contentDescription = null,
                            tint = VelvetPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Verrerie Recommandée",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = cocktail.glassware,
                            fontSize = 12.sp,
                            color = VelvetOnSurfaceVariant
                        )
                    }
                }

                // Garniture Signature Card
                if (cocktail.garnish.isNotBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(VelvetSurfaceLowest)
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(VelvetSurfaceHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Spa,
                                contentDescription = null,
                                tint = VelvetSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Garniture",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = cocktail.garnish,
                                fontSize = 12.sp,
                                color = VelvetOnSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Dialog: Platform Picker for Playlists
    if (uiState.showPlaylistPicker) {
        PlaylistPickerModal(
            musicPairing = uiState.musicPairing,
            isLoading = uiState.isMusicLoading,
            onDismiss = { onIntent(DetailIntent.ClosePlaylistPicker) },
            onOpenUrl = { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
                onIntent(DetailIntent.ClosePlaylistPicker)
            }
        )
    }
}

@Composable
private fun MetricBox(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(VelvetSurfaceLow)
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(14.dp))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(19.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = VelvetOnSurfaceVariant,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun MusicAmbienceCard(
    cocktail: Cocktail,
    onClickListen: () -> Unit
) {
    val ambience = cocktail.ambience

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp))
            .testTag("cocktail_music_ambience_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Stylized Vinyl record icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(Color(0xFF2C243B), Color(0xFF130F1C))))
                            .border(1.5.dp, ambience.accentColor.copy(alpha = 0.7f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(ambience.accentColor.copy(alpha = 0.35f))
                                .border(1.dp, ambience.accentColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = ambience.iconEmoji, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Album,
                                contentDescription = null,
                                tint = ambience.accentColor,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "AMBIANCE MUSICALE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ambience.accentColor,
                                letterSpacing = 1.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = ambience.styleName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = ambience.subtitle,
                            fontSize = 11.sp,
                            color = VelvetOnSurfaceVariant,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                PulsingWaveBars()
            }

            Button(
                onClick = onClickListen,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("listen_tasting_playlist_button"),
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetPrimaryContainer,
                    contentColor = VelvetOnPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Écouter la playlist de dégustation",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PulsingWaveBars() {
    val transition = rememberInfiniteTransition(label = "wave")
    val h1 by transition.animateFloat(
        initialValue = 6f,
        targetValue = 22f,
        animationSpec = infiniteRepeatable(tween(500), repeatMode = RepeatMode.Reverse),
        label = "h1"
    )
    val h2 by transition.animateFloat(
        initialValue = 18f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(400), repeatMode = RepeatMode.Reverse),
        label = "h2"
    )
    val h3 by transition.animateFloat(
        initialValue = 10f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(tween(600), repeatMode = RepeatMode.Reverse),
        label = "h3"
    )

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.height(26.dp)
    ) {
        Box(modifier = Modifier.width(3.dp).height(h1.dp).clip(RoundedCornerShape(2.dp)).background(VelvetPrimary))
        Box(modifier = Modifier.width(3.dp).height(h2.dp).clip(RoundedCornerShape(2.dp)).background(VelvetSecondary))
        Box(modifier = Modifier.width(3.dp).height(h3.dp).clip(RoundedCornerShape(2.dp)).background(VelvetTertiary))
        Box(modifier = Modifier.width(3.dp).height(h1.dp).clip(RoundedCornerShape(2.dp)).background(VelvetPrimary))
    }
}

@Composable
private fun IngredientRow(
    ingredient: RecipeIngredient,
    isAlternateUnits: Boolean,
    onAskSubstitute: () -> Unit
) {
    val icon = when (ingredient.iconType) {
        "nutrition" -> Icons.Default.Eco
        "water_drop" -> Icons.Default.WaterDrop
        "invert_colors" -> Icons.Default.InvertColors
        "spa" -> Icons.Default.Spa
        else -> Icons.Default.Liquor
    }

    val iconTint = when (ingredient.iconType) {
        "nutrition" -> VelvetTertiary
        "water_drop" -> VelvetSecondary
        "invert_colors" -> VelvetPrimary
        "spa" -> VelvetOnSurfaceVariant
        else -> VelvetPrimary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(VelvetSurfaceLow.copy(alpha = 0.8f))
            .clickable { onAskSubstitute() }
            .testTag("ingredient_row_${ingredient.name.replace(" ", "_").lowercase()}")
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(VelvetSurfaceHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = ingredient.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = ingredient.details.ifEmpty { "Toucher pour substitut IA" },
                    fontSize = 11.sp,
                    color = VelvetOnSurfaceVariant
                )
            }
        }

        // Display unit (cl vs traditional measurement)
        val displayQuantity = if (isAlternateUnits && ingredient.unitAlternative.isNotBlank()) {
            ingredient.unitAlternative
        } else if (ingredient.amountCl > 0) {
            "${ingredient.amountCl} cl"
        } else {
            ingredient.unitAlternative.ifEmpty { "Finition" }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = displayQuantity,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (ingredient.isGarnish) VelvetOnSurfaceVariant else iconTint
            )
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Trouver un substitut",
                tint = VelvetPrimary.copy(alpha = 0.7f),
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
private fun AiSubstitutionCard(
    missingIngredient: String?,
    substitutionText: String?,
    isLoading: Boolean,
    onClose: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, VelvetPrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHighest)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = VelvetPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Suggestions du Mixologue IA",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetPrimary
                    )
                }

                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = VelvetOnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            if (isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(
                        color = VelvetPrimary,
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                    Text(
                        text = "Consultation du bar clandestin en cours...",
                        fontSize = 12.sp,
                        color = VelvetOnSurfaceVariant
                    )
                }
            } else {
                Text(
                    text = substitutionText ?: "Pas de Crème de Mûre ? Utilisez du Cassis de Dijon avec une touche de miel, ou du Chambord (liqueur de framboise noire).",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun StepRow(
    stepNumber: Int,
    text: String,
    isHighlighted: Boolean
) {
    val stepBg = if (isHighlighted) VelvetPrimaryContainer else VelvetSurfaceLow
    val badgeBg = if (isHighlighted) VelvetPrimary else VelvetPrimary.copy(alpha = 0.2f)
    val badgeText = if (isHighlighted) VelvetOnPrimary else VelvetPrimary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(stepBg.copy(alpha = if (isHighlighted) 0.15f else 0.8f))
            .border(
                1.dp,
                if (isHighlighted) VelvetPrimary.copy(alpha = 0.6f) else VelvetGlassBorder,
                RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(badgeBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber.toString(),
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = badgeText
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 19.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PlaylistPickerModal(
    musicPairing: com.example.data.repository.MusicPairing?,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onOpenUrl: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VelvetSurfaceHigh,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = null,
                    tint = VelvetPrimary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Accord Musical Velvet",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        text = {
            if (isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(color = VelvetPrimary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Synchronisation de la vibe...",
                        color = VelvetOnSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            } else {
                val pairing = musicPairing ?: com.example.data.repository.MusicPairing(
                    title = "Speakeasy Velvet Session",
                    description = "Afrobeats, Chill Electro & Rap FR/US",
                    spotifyQueryUrl = "https://open.spotify.com/search/speakeasy%20chill%20lounge",
                    deezerQueryUrl = "https://www.deezer.com/search/speakeasy%20chill%20lounge",
                    youtubeQueryUrl = "https://www.youtube.com/results?search_query=speakeasy+chill+lounge"
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = pairing.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = VelvetPrimary
                    )
                    Text(
                        text = pairing.description,
                        fontSize = 12.sp,
                        color = VelvetOnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Choisissez votre plateforme :",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    // Spotify Button
                    Button(
                        onClick = { onOpenUrl(pairing.spotifyQueryUrl) },
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954), contentColor = Color.Black)
                    ) {
                        Text("Écouter sur Spotify", fontWeight = FontWeight.Bold)
                    }

                    // Deezer Button
                    Button(
                        onClick = { onOpenUrl(pairing.deezerQueryUrl) },
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VelvetSecondaryContainer, contentColor = Color.White)
                    ) {
                        Text("Écouter sur Deezer", fontWeight = FontWeight.Bold)
                    }

                    // YouTube Button
                    Button(
                        onClick = { onOpenUrl(pairing.youtubeQueryUrl) },
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000), contentColor = Color.White)
                    ) {
                        Text("Écouter sur YouTube", fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = VelvetOnSurfaceVariant)
            }
        }
    )
}
