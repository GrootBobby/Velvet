package com.example.ui.bar

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiNature
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.CocktailImage
import com.example.data.model.IngredientCategory
import com.example.data.model.InventoryIngredient
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
fun BarScreen(
    uiState: BarUiState,
    onIntent: (BarIntent) -> Unit,
    onNavigateToCatalog: (categoryFilter: String?) -> Unit,
    onNavigateToCocktail: (Long) -> Unit,
    onNavigateToParty: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            onIntent(BarIntent.ClearToast)
        }
    }

    if (uiState.isLoading) {
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
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. SECTION : Profil Mixologue & Jauge XP
        item {
            MixologistXpCard(
                progress = uiState.userProgress,
                craftableCount = uiState.craftableCocktailsCount,
                onExploreCraftable = { onNavigateToCatalog(null) }
            )
        }

        // 2. SECTION : Accès Rapide
        item {
            QuickAccessSection(
                cocktailsCount = uiState.allCocktails.size,
                onNavigateToCatalog = { onNavigateToCatalog(null) },
                onNavigateToMocktails = { onNavigateToCatalog("Mocktails & 0%") },
                onNavigateToParty = onNavigateToParty
            )
        }

        // 3. SECTION : Carte Intelligente IA 'Course Mixologie'
        item {
            SmartShoppingCard(
                recommendation = uiState.smartShopping,
                onBuyClick = { name -> onIntent(BarIntent.BuySmartIngredient(name)) },
                onExploreRecipes = { onNavigateToCatalog(null) },
                onCocktailClick = onNavigateToCocktail
            )
        }

        // 4. SECTION : Mon Bar Privé & Ingrédients
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (uiState.userProgress.userName.isNotBlank()) "Bar de ${uiState.userProgress.userName}" else "Mon Bar Privé",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = if (uiState.userProgress.userName.isNotBlank()) "Les bouteilles et sirops de ${uiState.userProgress.userName}" else "Cochez ce qui trône sur votre étagère",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = VelvetOnSurfaceVariant
                            )
                        )
                    }

                    // Scan button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(VelvetSurfaceHigh)
                            .clickable {
                                Toast.makeText(context, "Scan de bouteille activé (caméra IA)", Toast.LENGTH_SHORT).show()
                            }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                            .testTag("scan_bottle_button"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "Scan",
                            tint = VelvetPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Scan",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VelvetPrimary
                        )
                    }
                }

                // Category Tabs
                IngredientCategoryTabs(
                    selectedCategory = uiState.selectedCategory,
                    ingredients = uiState.ingredients,
                    onSelect = { onIntent(BarIntent.SelectCategory(it)) }
                )

                // Grid of Ingredients for selected category
                val currentCategoryItems = uiState.ingredients.filter { it.category == uiState.selectedCategory }
                IngredientsList(
                    items = currentCategoryItems,
                    onToggle = { ing -> onIntent(BarIntent.ToggleIngredient(ing.id, ing.isOwned)) }
                )

                // Button : Ajouter une bouteille ou ingrédient
                Button(
                    onClick = { onIntent(BarIntent.OpenAddDialog) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("add_ingredient_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetSurfaceHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Ajouter",
                        tint = VelvetPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ajouter une bouteille ou ingrédient",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    // Dialog: Add ingredient manually
    if (uiState.showAddDialog) {
        AddIngredientDialog(
            defaultCategory = uiState.selectedCategory,
            onDismiss = { onIntent(BarIntent.CloseAddDialog) },
            onConfirm = { name, detail, cat, tag ->
                onIntent(BarIntent.AddIngredient(name, detail, cat, tag))
            }
        )
    }
}

@Composable
private fun MixologistXpCard(
    progress: com.example.data.local.UserProgress,
    craftableCount: Int,
    onExploreCraftable: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Profile & Level
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(54.dp)) {
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuB1wsRGcfEuaRxMqpPLizeiDbk1ojhfDZmU1zboNM7urLnvLkE36yCYVXWNM_zszUs030oedVYiOazT7C8YlJxb0FCfZFbYFAs2b3Op70moN_Hn4_V2KDfGFWYd7OTNPUdSLHfBJQzRX_itjyn0GU49O8_DvMLx6NQEupXeh5VwP4gnjrpp33xCdHJCKCp7OgyhoTAV3c0UGX2k57hT3BGS6bLOcUcQuOhD8TD5iML1ATcSGyIT8E7UhA",
                        contentDescription = "Portrait",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(VelvetSecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = "Premium",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (progress.userName.isNotBlank()) progress.userName else "Alexandre V.",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(VelvetTertiary)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Niv. ${progress.level}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2A1700)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Liquor,
                            contentDescription = null,
                            tint = VelvetPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = progress.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = VelvetPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            // Neon XP Gauge
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "PROGRESSION SHAKER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = VelvetOnSurfaceVariant
                    )
                    Text(
                        text = "${progress.totalXp} / ${progress.nextLevelTargetXp} XP (${(progress.progressFraction * 100).toInt()}%)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetSecondary
                    )
                }

                // Progress Bar
                val progressFraction = progress.progressFraction
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(VelvetSurfaceHighest)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = progressFraction)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(VelvetPrimary, VelvetPrimaryContainer, VelvetSecondary)
                                )
                            )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Prochain grade : ${progress.nextTitle}",
                        fontSize = 11.sp,
                        color = VelvetOnSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = VelvetTertiary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "+120 XP ce soir",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VelvetTertiary
                        )
                    }
                }
            }

            // Ready cocktails strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(VelvetSurfaceContainer)
                    .clickable { onExploreCraftable() }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(VelvetSecondaryContainer.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Liquor,
                            contentDescription = null,
                            tint = VelvetPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "$craftableCount Cocktails",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Prêts à shaker avec vos ingrédients",
                            fontSize = 12.sp,
                            color = VelvetOnSurfaceVariant
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(VelvetPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Voir",
                        tint = VelvetOnPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickAccessSection(
    cocktailsCount: Int,
    onNavigateToCatalog: () -> Unit,
    onNavigateToMocktails: () -> Unit,
    onNavigateToParty: () -> Unit
) {
    val catalogSubtitle = if (cocktailsCount > 0) {
        "$cocktailsCount créations secrètes & signatures"
    } else {
        "100 créations secrètes & signatures"
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Accès Rapide",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = "ATMOSPHÈRE VELVET",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = VelvetPrimary
            )
        }

        // 1. Catalogue Speakeasy
        QuickAccessRow(
            title = "Catalogue Speakeasy",
            subtitle = catalogSubtitle,
            icon = Icons.AutoMirrored.Filled.MenuBook,
            iconBg = VelvetSecondaryContainer.copy(alpha = 0.5f),
            iconTint = VelvetSecondary,
            onClick = onNavigateToCatalog
        )

        // 2. Mocktails & 0%
        QuickAccessRow(
            title = "Mocktails & Sans Alcool",
            subtitle = "0% alcool, 100% vibe & fraîcheur",
            icon = Icons.Default.EmojiNature,
            iconBg = VelvetTertiaryContainer.copy(alpha = 0.35f),
            iconTint = VelvetTertiary,
            onClick = onNavigateToMocktails
        )

        // 3. Jeux de Soirée (LIVE)
        QuickAccessRow(
            title = "Jeux de Soirée",
            subtitle = "Undercover, Qui pourrait, Blind Test",
            icon = Icons.Default.Casino,
            iconBg = VelvetPrimaryContainer.copy(alpha = 0.4f),
            iconTint = VelvetPrimary,
            badge = "LIVE",
            onClick = onNavigateToParty
        )
    }
}

@Composable
private fun QuickAccessRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconTint: Color,
    badge: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VelvetSurfaceLow)
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(VelvetPrimary)
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = badge,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = VelvetOnPrimary
                            )
                        }
                    }
                }
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = VelvetOnSurfaceVariant
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = VelvetOnSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SmartShoppingCard(
    recommendation: com.example.data.repository.SmartShoppingRecommendation?,
    onBuyClick: (String) -> Unit,
    onExploreRecipes: () -> Unit,
    onCocktailClick: (Long) -> Unit
) {
    if (recommendation == null) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, VelvetTertiary.copy(alpha = 0.35f), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header tag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(VelvetTertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF472A00),
                        modifier = Modifier.size(15.dp)
                    )
                }
                Text(
                    text = "SUGGESTION MIXOLOGIE IA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = VelvetTertiary
                )
            }

            // Title with bold highlight
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "💡 Achète du ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = recommendation.recommendedIngredientName,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = VelvetTertiary,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "pour débloquer ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${recommendation.unlockedCount} cocktails !",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = VelvetPrimary
                    )
                }
            }

            Text(
                text = recommendation.explanation,
                fontSize = 13.sp,
                color = VelvetOnSurfaceVariant,
                lineHeight = 18.sp
            )

            // Cocktails thumbnails row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recommendation.previewCocktails) { cocktail ->
                    Column(
                        modifier = Modifier
                            .width(88.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(VelvetSurfaceLowest)
                            .clickable { onCocktailClick(cocktail.id) }
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CocktailImage(
                            imageUrl = cocktail.imageUrl,
                            contentDescription = cocktail.name,
                            contentScale = ContentScale.Crop,
                            iconSize = 24.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = cocktail.name,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "+ ${cocktail.ingredients.firstOrNull()?.name?.take(8) ?: "Secret"}",
                            fontSize = 10.sp,
                            color = VelvetTertiary,
                            maxLines = 1
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .size(width = 64.dp, height = 90.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(VelvetSurfaceHighest)
                            .clickable { onExploreRecipes() },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "+9",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = VelvetPrimary
                            )
                            Text(
                                text = "autres",
                                fontSize = 10.sp,
                                color = VelvetOnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // CTAs
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { onBuyClick(recommendation.recommendedIngredientName) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("buy_smart_ingredient_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetTertiary,
                        contentColor = Color(0xFF2A1700)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ajouter ${recommendation.recommendedIngredientName} au panier",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                TextButton(
                    onClick = onExploreRecipes,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Explorer les ${recommendation.unlockedCount} recettes débloquables",
                        color = VelvetSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientCategoryTabs(
    selectedCategory: IngredientCategory,
    ingredients: List<InventoryIngredient>,
    onSelect: (IngredientCategory) -> Unit
) {
    val countSpirits = ingredients.count { it.category == IngredientCategory.SPIRITS }
    val countMixers = ingredients.count { it.category == IngredientCategory.MIXERS }
    val countSyrups = ingredients.count { it.category == IngredientCategory.SYRUPS }
    val countGarnishes = ingredients.count { it.category == IngredientCategory.GARNISHES }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(VelvetSurfaceLow)
            .padding(4.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CategoryTabButton(
            label = "Spiritueux ($countSpirits)",
            isSelected = selectedCategory == IngredientCategory.SPIRITS,
            onClick = { onSelect(IngredientCategory.SPIRITS) }
        )
        CategoryTabButton(
            label = "Softs ($countMixers)",
            isSelected = selectedCategory == IngredientCategory.MIXERS,
            onClick = { onSelect(IngredientCategory.MIXERS) }
        )
        CategoryTabButton(
            label = "Sirops ($countSyrups)",
            isSelected = selectedCategory == IngredientCategory.SYRUPS,
            onClick = { onSelect(IngredientCategory.SYRUPS) }
        )
        CategoryTabButton(
            label = "Garnitures ($countGarnishes)",
            isSelected = selectedCategory == IngredientCategory.GARNISHES,
            onClick = { onSelect(IngredientCategory.GARNISHES) }
        )
    }
}

@Composable
private fun CategoryTabButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) VelvetPrimaryContainer else Color.Transparent,
        label = "tab_bg"
    )
    val textColor = if (isSelected) VelvetOnPrimary else VelvetOnSurfaceVariant

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor,
            maxLines = 1
        )
    }
}

@Composable
private fun IngredientsList(
    items: List<InventoryIngredient>,
    onToggle: (InventoryIngredient) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Group items in pairs of 2
        items.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pair.forEach { ingredient ->
                    IngredientCard(
                        ingredient = ingredient,
                        onToggle = { onToggle(ingredient) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun IngredientCard(
    ingredient: InventoryIngredient,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOwned = ingredient.isOwned

    Card(
        modifier = modifier
            .height(96.dp)
            .border(
                1.dp,
                if (isOwned) VelvetPrimary.copy(alpha = 0.4f) else VelvetGlassBorder,
                RoundedCornerShape(16.dp)
            )
            .clickable { onToggle() }
            .testTag("ingredient_card_${ingredient.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isOwned) VelvetSurfaceHigh else VelvetSurfaceLowest.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isOwned) VelvetSecondaryContainer.copy(alpha = 0.5f) else VelvetSurfaceHighest)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = ingredient.tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isOwned) VelvetSecondary else VelvetOnSurfaceVariant
                    )
                }

                Icon(
                    imageVector = if (isOwned) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = if (isOwned) "Possédé" else "Non possédé",
                    tint = if (isOwned) VelvetPrimary else VelvetOnSurfaceVariant,
                    modifier = Modifier.size(19.dp)
                )
            }

            Column {
                Text(
                    text = ingredient.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isOwned) MaterialTheme.colorScheme.onSurface else VelvetOnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = ingredient.brandOrDetail,
                    fontSize = 11.sp,
                    color = VelvetOnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AddIngredientDialog(
    defaultCategory: IngredientCategory,
    onDismiss: () -> Unit,
    onConfirm: (name: String, detail: String, category: IngredientCategory, tag: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(defaultCategory) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VelvetSurfaceHigh,
        title = {
            Text(
                text = "Ajouter un ingrédient",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom (ex: Mezcal Artesanal)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = detail,
                    onValueChange = { detail = it },
                    label = { Text("Détail / Marque (ex: 70cl • 100% plein)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tag,
                    onValueChange = { tag = it },
                    label = { Text("Tag court (ex: Agave, Bitter, Agrumes)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Category selector
                Text(
                    text = "Catégorie :",
                    fontSize = 12.sp,
                    color = VelvetOnSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = category == IngredientCategory.SPIRITS,
                        onClick = { category = IngredientCategory.SPIRITS },
                        colors = RadioButtonDefaults.colors(selectedColor = VelvetPrimary)
                    )
                    Text("Spiritueux", fontSize = 12.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    RadioButton(
                        selected = category == IngredientCategory.MIXERS,
                        onClick = { category = IngredientCategory.MIXERS },
                        colors = RadioButtonDefaults.colors(selectedColor = VelvetPrimary)
                    )
                    Text("Softs", fontSize = 12.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    RadioButton(
                        selected = category == IngredientCategory.SYRUPS,
                        onClick = { category = IngredientCategory.SYRUPS },
                        colors = RadioButtonDefaults.colors(selectedColor = VelvetPrimary)
                    )
                    Text("Sirops", fontSize = 12.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    RadioButton(
                        selected = category == IngredientCategory.GARNISHES,
                        onClick = { category = IngredientCategory.GARNISHES },
                        colors = RadioButtonDefaults.colors(selectedColor = VelvetPrimary)
                    )
                    Text("Garnitures", fontSize = 12.sp, color = Color.White)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val computedTag = if (tag.isNotBlank()) tag else "Bar"
                        onConfirm(name.trim(), detail.trim().ifEmpty { "En stock" }, category, computedTag)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VelvetPrimary, contentColor = VelvetOnPrimary)
            ) {
                Text("Ajouter", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = VelvetOnSurfaceVariant)
            }
        }
    )
}
