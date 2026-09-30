package com.example.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.CocktailImage
import com.example.data.model.Cocktail
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetPrimaryContainer
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceContainer
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLow
import com.example.ui.theme.VelvetTertiary

@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onIntent: (CatalogIntent) -> Unit,
    onCocktailClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("Tous", "Classiques", "Cocktails", "Shooters", "Mocktails", "Favoris")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { onIntent(CatalogIntent.Search(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("catalog_search_bar"),
            placeholder = {
                Text(
                    text = "Rechercher un cocktail, gin, framboise...",
                    fontSize = 13.sp,
                    color = VelvetOnSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Rechercher",
                    tint = VelvetPrimary,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotBlank()) {
                    IconButton(onClick = { onIntent(CatalogIntent.Search("")) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Effacer",
                            tint = VelvetOnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = VelvetSurfaceLow,
                unfocusedContainerColor = VelvetSurfaceLow,
                focusedBorderColor = VelvetPrimary,
                unfocusedBorderColor = VelvetGlassBorder,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                val isSelected = uiState.selectedCategory == category || (category == "Favoris" && uiState.onlyFavorites)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (category == "Favoris" && isSelected) {
                            onIntent(CatalogIntent.SelectCategory("Tous"))
                        } else {
                            onIntent(CatalogIntent.SelectCategory(category))
                        }
                    },
                    modifier = Modifier.testTag("filter_chip_${category.lowercase()}"),
                    leadingIcon = if (category == "Favoris") {
                        {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = if (isSelected) VelvetOnPrimary else VelvetPrimary,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    } else null,
                    label = {
                        Text(
                            text = category,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = VelvetSurfaceLow,
                        selectedContainerColor = if (category == "Favoris") VelvetPrimary else VelvetPrimaryContainer,
                        labelColor = if (category == "Favoris") VelvetPrimary else VelvetOnSurfaceVariant,
                        selectedLabelColor = VelvetOnPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = VelvetGlassBorder,
                        selectedBorderColor = VelvetPrimary,
                        enabled = true,
                        selected = isSelected
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Results count
        Text(
            text = "${uiState.filteredCocktails.size} cocktails disponibles",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = VelvetOnSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = VelvetPrimary)
            }
        } else if (uiState.filteredCocktails.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🍸", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aucun cocktail trouvé",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Essayez un autre mot-clé ou réinitialisez les filtres",
                        color = VelvetOnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.filteredCocktails, key = { it.id }) { cocktail ->
                    CocktailGridCard(
                        cocktail = cocktail,
                        onCardClick = { onCocktailClick(cocktail.id) },
                        onFavoriteClick = { onIntent(CatalogIntent.ToggleFavorite(cocktail.id, cocktail.isFavorite)) }
                    )
                }
            }
        }
    }
}

@Composable
fun CocktailGridCard(
    cocktail: Cocktail,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(18.dp))
            .clickable { onCardClick() }
            .testTag("cocktail_card_${cocktail.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow)
    ) {
        Column {
            // Image with Overlays
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
            ) {
                CocktailImage(
                    imageUrl = cocktail.imageUrl,
                    contentDescription = cocktail.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark gradient scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xAA121317), Color(0xF0121317)),
                                startY = 100f
                            )
                        )
                )

                // Top tag: Category
                val isMocktail = cocktail.alcoholPercentage == 0.0
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isMocktail) Color(0xDD38393E) else Color(0xDD343439)
                        )
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isMocktail) "0% ALCOOL" else cocktail.difficulty.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isMocktail) VelvetTertiary else VelvetPrimary,
                        letterSpacing = 0.5.sp
                    )
                }

                // Top right: Heart favorite
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0x99000000))
                        .clickable { onFavoriteClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (cocktail.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (cocktail.isFavorite) VelvetPrimary else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Bottom title overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Text(
                        text = cocktail.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

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
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${cocktail.prepTimeMinutes} min",
                                fontSize = 11.sp,
                                color = VelvetOnSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = VelvetTertiary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = cocktail.rating.toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VelvetTertiary
                            )
                        }
                    }
                }
            }
        }
    }
}
