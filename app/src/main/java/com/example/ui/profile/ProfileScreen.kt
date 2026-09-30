package com.example.ui.profile

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.ui.camera.CameraCaptureDialog
import androidx.compose.material.icons.filled.Blender
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.ui.theme.VelvetSecondaryContainer
import com.example.ui.components.DEFAULT_PROFILE_AVATAR
import com.example.ui.components.ProfilePhotoSelectionDialog
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceContainer
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLow
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary
import com.example.ui.theme.VelvetTertiaryContainer

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onIntent: (ProfileIntent) -> Unit,
    onCocktailClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showPhotoSelectionDialog by remember { mutableStateOf(false) }

    if (showPhotoSelectionDialog) {
        ProfilePhotoSelectionDialog(
            onDismiss = { showPhotoSelectionDialog = false },
            onPhotoSelected = { uriString ->
                onIntent(ProfileIntent.UpdateProfilePhoto(uriString))
            }
        )
    }

    if (uiState.showAuthDialog) {
        AuthDialog(
            isAuthLoading = uiState.isAuthLoading,
            onDismiss = { onIntent(ProfileIntent.CloseAuthDialog) },
            onSignInEmail = { email, pass ->
                onIntent(ProfileIntent.SignInWithEmail(email, pass))
            },
            onSignUpEmail = { email, pass, pseudo ->
                onIntent(ProfileIntent.SignUpWithEmail(email, pass, pseudo))
            },
            onSignInGoogle = {
                val activity = context as? Activity
                if (activity != null) {
                    onIntent(ProfileIntent.SignInWithGoogle(activity))
                }
            },
            onContinueGuest = {
                onIntent(ProfileIntent.ContinueAsGuest)
            }
        )
    }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            onIntent(ProfileIntent.ClearToast)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Profile Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetSurfaceContainer.copy(alpha = 0.85f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Clickable Avatar with edit badge
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clickable { showPhotoSelectionDialog = true }
                                .testTag("profile_avatar_image")
                        ) {
                            AsyncImage(
                                model = uiState.userProgress.profilePhotoUri?.ifBlank { null } ?: DEFAULT_PROFILE_AVATAR,
                                contentDescription = uiState.userProgress.userName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(2.dp, VelvetPrimary.copy(alpha = 0.8f), CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.BottomEnd)
                                    .clip(CircleShape)
                                    .background(VelvetPrimaryContainer)
                                    .border(1.5.dp, VelvetSurface, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Changer la photo",
                                    tint = VelvetOnPrimary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (uiState.userProgress.userName.isNotBlank()) uiState.userProgress.userName else "Alexandre V.",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Vérifié",
                                    tint = VelvetPrimary,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                            Text(
                                text = if (uiState.userProgress.userName.isNotBlank()) "@${uiState.userProgress.userName.filter { !it.isWhitespace() }.lowercase()}" else "@VelvetMixologist",
                                fontSize = 12.sp,
                                color = VelvetPrimary.copy(alpha = 0.9f),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Alchimiste de saveurs nocturnes 🍸 • Bar 100% Hors-ligne",
                                fontSize = 12.sp,
                                color = VelvetOnSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }

                    // Quick Stats Strip (Offline: Créations, Favoris, XP Total - Likes definitively removed)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(VelvetSurfaceLow.copy(alpha = 0.7f))
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ProfileStatItem(
                            number = uiState.myCreations.size.toString(),
                            label = "CRÉATIONS",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        ProfileStatItem(
                            number = uiState.favorites.size.toString(),
                            label = "FAVORIS",
                            color = VelvetSecondary
                        )
                        ProfileStatItem(
                            number = "${uiState.userProgress.totalXp} XP",
                            label = "XP TOTAL",
                            color = VelvetTertiary
                        )
                    }
                }
            }
        }

        // 2. Level & Badges Banner (Functional XP DataStore)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow.copy(alpha = 0.9f))
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
                                imageVector = Icons.Default.MilitaryTech,
                                contentDescription = null,
                                tint = VelvetTertiary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${uiState.userProgress.title} • Niv. ${uiState.userProgress.level}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = "${uiState.userProgress.totalXp} / ${uiState.userProgress.nextLevelTargetXp} XP (${(uiState.userProgress.progressFraction * 100).toInt()}%)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetPrimary
                        )
                    }

                    // XP Progress bar
                    val progressFraction = uiState.userProgress.progressFraction
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
                                        listOf(VelvetPrimaryContainer, VelvetPrimary, VelvetTertiary)
                                    )
                                )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Prochain grade : ${uiState.userProgress.nextTitle}",
                            fontSize = 11.sp,
                            color = VelvetOnSurfaceVariant
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = VelvetTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Section Compte & Synchronisation Cloud
        item {
            AccountSyncSection(
                uiState = uiState,
                onOpenAuth = { onIntent(ProfileIntent.OpenAuthDialog) },
                onManualSync = { onIntent(ProfileIntent.TriggerManualSync) },
                onSignOut = { onIntent(ProfileIntent.SignOut) }
            )
        }

        // 3. Primary Action Buttons : Ajouter Recette & Photo
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onIntent(ProfileIntent.OpenAddRecipeDialog) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("add_custom_recipe_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetPrimaryContainer,
                        contentColor = VelvetOnPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Blender,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+ Ma Recette",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { onIntent(ProfileIntent.OpenAddPhotoDialog) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("add_cocktail_photo_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetSurfaceHigh,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = null,
                        tint = VelvetTertiary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+ Photo Galerie",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // 4. Segmented Tabs
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(VelvetSurfaceLow)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ProfileTabButton(
                    label = "Mes Créations (${uiState.myCreations.size})",
                    icon = Icons.Default.AutoAwesome,
                    isSelected = uiState.selectedTab == ProfileTab.CREATIONS,
                    onClick = { onIntent(ProfileIntent.SelectTab(ProfileTab.CREATIONS)) },
                    modifier = Modifier.weight(1f)
                )

                ProfileTabButton(
                    label = "Favoris (${uiState.favorites.size})",
                    icon = Icons.Default.Bookmark,
                    isSelected = uiState.selectedTab == ProfileTab.FAVORITES,
                    onClick = { onIntent(ProfileIntent.SelectTab(ProfileTab.FAVORITES)) },
                    modifier = Modifier.weight(1f)
                )

                ProfileTabButton(
                    label = "Galerie (${uiState.photos.size})",
                    icon = Icons.Default.PhotoLibrary,
                    isSelected = uiState.selectedTab == ProfileTab.GALLERY,
                    onClick = { onIntent(ProfileIntent.SelectTab(ProfileTab.GALLERY)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 5. Grid Content depending on active tab
        when (uiState.selectedTab) {
            ProfileTab.CREATIONS -> {
                item {
                    CocktailsGridSection(
                        items = uiState.myCreations,
                        emptyTitle = "Aucune création pour l'instant",
                        emptySubtitle = "Créez votre première recette originale avec CameraX !",
                        onAddRecipe = { onIntent(ProfileIntent.OpenAddRecipeDialog) },
                        onCocktailClick = onCocktailClick
                    )
                }
            }
            ProfileTab.FAVORITES -> {
                item {
                    CocktailsGridSection(
                        items = uiState.favorites,
                        emptyTitle = "Aucun favori enregistré",
                        emptySubtitle = "Explorez le catalogue et ajoutez vos coups de cœur !",
                        onCocktailClick = onCocktailClick
                    )
                }
            }
            ProfileTab.GALLERY -> {
                item {
                    PhotosGridSection(
                        photos = uiState.photos,
                        onAddPhoto = { onIntent(ProfileIntent.OpenAddPhotoDialog) }
                    )
                }
            }
        }
    }

    // Modal: Add Custom Recipe
    if (uiState.showAddRecipeDialog) {
        AddRecipeDialog(
            onDismiss = { onIntent(ProfileIntent.CloseAddRecipeDialog) },
            onConfirm = { name, subtitle, desc, cat, alc, prep, diff, flav, ing, steps, photoUri ->
                onIntent(
                    ProfileIntent.CreateCustomRecipe(
                        name = name,
                        subtitle = subtitle,
                        description = desc,
                        category = cat,
                        alcoholPercentage = alc,
                        prepTime = prep,
                        difficulty = diff,
                        flavorProfile = flav,
                        ingredientsText = ing,
                        stepsText = steps,
                        photoUri = photoUri
                    )
                )
            }
        )
    }

    // Modal: Add Photo to Gallery
    if (uiState.showAddPhotoDialog) {
        AddPhotoDialog(
            cocktails = uiState.myCreations,
            onDismiss = { onIntent(ProfileIntent.CloseAddPhotoDialog) },
            onConfirm = { cocktailName, uri, notes ->
                onIntent(ProfileIntent.SaveCocktailPhoto(cocktailName, uri, notes))
            }
        )
    }
}

@Composable
private fun ProfileStatItem(
    number: String,
    label: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = VelvetOnSurfaceVariant,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun ProfileTabButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) VelvetSurfaceHighest else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) VelvetPrimary else VelvetOnSurfaceVariant,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.onSurface else VelvetOnSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun CocktailsGridSection(
    items: List<Cocktail>,
    emptyTitle: String = "Aucune création pour l'instant",
    emptySubtitle: String = "Créez votre première recette originale avec photo !",
    onAddRecipe: (() -> Unit)? = null,
    onCocktailClick: (Long) -> Unit
) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "🍸", fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = emptyTitle,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
                Text(
                    text = emptySubtitle,
                    color = VelvetOnSurfaceVariant,
                    fontSize = 12.sp
                )
                if (onAddRecipe != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onAddRecipe,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VelvetPrimaryContainer,
                            contentColor = VelvetOnPrimary
                        )
                    ) {
                        Text("+ Créer ma recette", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        return
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.chunked(2).forEach { rowPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowPair.forEach { cocktail ->
                    ProfileCocktailCard(
                        cocktail = cocktail,
                        onClick = { onCocktailClick(cocktail.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowPair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ProfileCocktailCard(
    cocktail: Cocktail,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.85f)
            ) {
                if (cocktail.imageUrl.isNotBlank()) {
                    CocktailImage(
                        imageUrl = cocktail.imageUrl,
                        contentDescription = cocktail.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(VelvetSurfaceHigh, VelvetSurfaceLow)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalBar,
                            contentDescription = null,
                            tint = VelvetPrimary.copy(alpha = 0.7f),
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xDD121317))
                            )
                        )
                )

                // Top tag: Speakeasy / Clubbing / Lounge Jazz
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(VelvetSurfaceHighest.copy(alpha = 0.85f))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (cocktail.isCustom) "CRÉATION" else cocktail.difficulty.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetPrimary
                    )
                }

                // Favorite indicator
                if (cocktail.isFavorite) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0x99000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favori",
                            tint = VelvetPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                // Bottom title & metrics
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Text(
                        text = cocktail.name,
                        fontSize = 13.sp,
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
                                fontSize = 10.sp,
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
                                fontSize = 10.sp,
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

@Composable
private fun PhotosGridSection(
    photos: List<com.example.data.model.UserCocktailPhoto>,
    onAddPhoto: () -> Unit
) {
    if (photos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "📸", fontSize = 36.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Aucune photo pour l'instant",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
                Text(
                    text = "Capturez vos réalisations et gagnez +120 XP !",
                    color = VelvetOnSurfaceVariant,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onAddPhoto,
                    colors = ButtonDefaults.buttonColors(containerColor = VelvetPrimaryContainer, contentColor = VelvetOnPrimary)
                ) {
                    Text("+ Ajouter une photo", fontWeight = FontWeight.Bold)
                }
            }
        }
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            photos.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { photo ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column {
                                AsyncImage(
                                    model = photo.photoUri,
                                    contentDescription = photo.cocktailName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                )
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = photo.cocktailName,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                    if (photo.notes.isNotBlank()) {
                                        Text(
                                            text = photo.notes,
                                            fontSize = 10.sp,
                                            color = VelvetOnSurfaceVariant,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun AddRecipeDialog(
    onDismiss: () -> Unit,
    onConfirm: (
        name: String,
        subtitle: String,
        description: String,
        category: String,
        alcoholPercentage: Double,
        prepTime: Int,
        difficulty: String,
        flavorProfile: String,
        ingredients: String,
        steps: String,
        photoUri: String?
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Cocktails") }
    var prepTime by remember { mutableStateOf("4") }
    var difficulty by remember { mutableStateOf("Moyen") }
    var flavorProfile by remember { mutableStateOf("Fruité/Velours") }
    var ingredientsText by remember { mutableStateOf("Gin 4cl\nSirop de canne 1.5cl\nJus de citron 2cl") }
    var stepsText by remember { mutableStateOf("Secouer avec de la glace 10s\nFiltrer dans une coupe") }
    var capturedPhotoUri by remember { mutableStateOf<String?>(null) }
    var showCamera by remember { mutableStateOf(false) }

    if (showCamera) {
        CameraCaptureDialog(
            onDismiss = { showCamera = false },
            onPhotoCaptured = { uri ->
                capturedPhotoUri = uri
                showCamera = false
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VelvetSurfaceHigh,
        title = {
            Text(
                text = "Créer ma propre recette",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    Text(
                        text = "Photo du cocktail (CameraX) :",
                        fontSize = 12.sp,
                        color = VelvetOnSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (capturedPhotoUri != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, VelvetPrimary, RoundedCornerShape(14.dp))
                        ) {
                            AsyncImage(
                                model = capturedPhotoUri,
                                contentDescription = "Photo capturée",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Button(
                                    onClick = { showCamera = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.75f)),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Reprendre", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = { capturedPhotoUri = null },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f)),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { showCamera = true }
                                .testTag("open_camera_for_recipe_button"),
                            colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow),
                            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(VelvetGlassBorder)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Prendre une photo",
                                    tint = VelvetPrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Prendre une photo en direct (CameraX)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = "Aperçu en direct & enregistrement local",
                                    fontSize = 10.sp,
                                    color = VelvetOnSurfaceVariant
                                )
                            }
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nom du cocktail") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("custom_cocktail_name_input")
                    )
                }
                item {
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text("Sous-titre (ex: Édition Salon Privé)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description des arômes") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = ingredientsText,
                        onValueChange = { ingredientsText = it },
                        label = { Text("Ingrédients (1 par ligne)") },
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = stepsText,
                        onValueChange = { stepsText = it },
                        label = { Text("Étapes de préparation (1 par ligne)") },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            name.trim(),
                            subtitle.trim(),
                            description.trim(),
                            category,
                            18.0,
                            prepTime.toIntOrNull() ?: 4,
                            difficulty,
                            flavorProfile,
                            ingredientsText,
                            stepsText,
                            capturedPhotoUri
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VelvetPrimary, contentColor = VelvetOnPrimary),
                modifier = Modifier.testTag("save_custom_cocktail_button")
            ) {
                Text("Enregistrer (+150 XP)", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = VelvetOnSurfaceVariant)
            }
        }
    )
}

@Composable
fun AddPhotoDialog(
    cocktails: List<Cocktail>,
    onDismiss: () -> Unit,
    onConfirm: (cocktailName: String, uri: String, notes: String) -> Unit
) {
    var cocktailName by remember { mutableStateOf(cocktails.firstOrNull()?.name ?: "Mon Cocktail") }
    var photoUri by remember { mutableStateOf<String?>(null) }
    var notes by remember { mutableStateOf("Réussi avec des glaçons cristallins et garniture fraîche !") }
    var showCamera by remember { mutableStateOf(false) }

    if (showCamera) {
        CameraCaptureDialog(
            onDismiss = { showCamera = false },
            onPhotoCaptured = { uri ->
                photoUri = uri
                showCamera = false
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VelvetSurfaceHigh,
        title = {
            Text(
                text = "Capturer pour ma galerie",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = cocktailName,
                    onValueChange = { cocktailName = it },
                    label = { Text("Nom du cocktail associé") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes de dégustation") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Photo capturée (CameraX) :",
                    fontSize = 11.sp,
                    color = VelvetOnSurfaceVariant
                )

                if (photoUri != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = photoUri,
                            contentDescription = "Aperçu de la capture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Button(
                            onClick = { showCamera = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.75f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reprendre", fontSize = 11.sp)
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { showCamera = true }
                            .testTag("open_camera_for_gallery_button"),
                        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(VelvetGlassBorder))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = VelvetPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Ouvrir l'appareil photo (CameraX)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Flux vidéo en direct & capture HD",
                                fontSize = 10.sp,
                                color = VelvetOnSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val finalUri = photoUri
                    if (cocktailName.isNotBlank() && !finalUri.isNullOrBlank()) {
                        onConfirm(cocktailName.trim(), finalUri, notes.trim())
                    }
                },
                enabled = !photoUri.isNullOrBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = VelvetPrimary, contentColor = VelvetOnPrimary)
            ) {
                Text("Valider (+120 XP)", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = VelvetOnSurfaceVariant)
            }
        }
    )
}

@Composable
private fun AccountSyncSection(
    uiState: ProfileUiState,
    onOpenAuth: () -> Unit,
    onManualSync: () -> Unit,
    onSignOut: () -> Unit
) {
    val isConnected = uiState.authUser != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp))
            .testTag("account_sync_section_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLow.copy(alpha = 0.95f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(if (isConnected) Color(0xFF00E676).copy(alpha = 0.15f) else VelvetPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isConnected) Icons.Default.CloudDone else Icons.Default.CloudQueue,
                            contentDescription = null,
                            tint = if (isConnected) Color(0xFF00E676) else VelvetPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "COMPTE & SYNCHRONISATION",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isConnected) Color(0xFF00E676) else VelvetPrimary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = if (isConnected) {
                                uiState.authUser?.email?.ifBlank { null } ?: uiState.authUser?.displayName ?: "Compte Cloud Connecté"
                            } else {
                                "Mode Invité (Stockage Local)"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (isConnected) {
                    // Indicateur Vert de statut Cloud
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF00E676).copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("cloud_sync_indicator")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00E676))
                        )
                        Text(
                            text = "Cloud OK",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00E676)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(VelvetSurfaceHighest)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Non synchronisé",
                            fontSize = 10.sp,
                            color = VelvetOnSurfaceVariant
                        )
                    }
                }
            }

            if (!isConnected) {
                Text(
                    text = "Sauvegardez votre niveau, XP, cocktails favoris et bar personnel dans le Cloud pour les restaurer si le stockage local est vidé.",
                    fontSize = 12.sp,
                    color = VelvetOnSurfaceVariant,
                    lineHeight = 16.sp
                )

                Button(
                    onClick = onOpenAuth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("open_auth_dialog_button"),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetPrimaryContainer,
                        contentColor = VelvetOnPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Créer un compte / Se connecter pour sauvegarder ma progression",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Données sauvegardées dans le Cloud",
                        fontSize = 12.sp,
                        color = Color(0xFF00E676),
                        fontWeight = FontWeight.SemiBold
                    )

                    // Bouton de synchronisation manuelle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onManualSync() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("manual_sync_button")
                    ) {
                        if (uiState.isSyncing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(13.dp),
                                strokeWidth = 2.dp,
                                color = VelvetPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Synchroniser",
                                tint = VelvetPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (uiState.isSyncing) "Sync en cours..." else (uiState.lastSyncedTimeText ?: "Synchroniser"),
                            fontSize = 11.sp,
                            color = VelvetPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                OutlinedButton(
                    onClick = onSignOut,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .testTag("auth_sign_out_button"),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Déconnexion",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthDialog(
    isAuthLoading: Boolean,
    onDismiss: () -> Unit,
    onSignInEmail: (String, String) -> Unit,
    onSignUpEmail: (String, String, String) -> Unit,
    onSignInGoogle: () -> Unit,
    onContinueGuest: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var pseudo by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VelvetSurfaceHigh,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.testTag("auth_dialog"),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VelvetPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = VelvetPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = if (isSignUp) "Créer un compte Cloud" else "Connexion Cloud",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(VelvetSurfaceHighest)
                        .clickable { onDismiss() }
                        .testTag("auth_dialog_close"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = VelvetOnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Mode Toggle (Connexion vs Inscription)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(VelvetSurfaceLowest)
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (!isSignUp) VelvetPrimaryContainer else Color.Transparent)
                            .clickable {
                                isSignUp = false
                                localError = null
                            }
                            .padding(vertical = 8.dp)
                            .testTag("auth_tab_signin"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Connexion",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isSignUp) VelvetOnPrimary else VelvetOnSurfaceVariant
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSignUp) VelvetPrimaryContainer else Color.Transparent)
                            .clickable {
                                isSignUp = true
                                localError = null
                            }
                            .padding(vertical = 8.dp)
                            .testTag("auth_tab_signup"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Inscription",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSignUp) VelvetOnPrimary else VelvetOnSurfaceVariant
                        )
                    }
                }

                if (localError != null) {
                    Text(
                        text = localError!!,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                if (isSignUp) {
                    OutlinedTextField(
                        value = pseudo,
                        onValueChange = { pseudo = it },
                        label = { Text("Pseudo / Prénom") },
                        placeholder = { Text("Ex: Alexandre") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = VelvetPrimary)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_pseudo_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder
                        )
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Adresse Email") },
                    placeholder = { Text("nom@exemple.com") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = VelvetPrimary)
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_email_input"),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mot de passe") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = VelvetPrimary)
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (isPasswordVisible) "Masquer" else "Afficher",
                                tint = VelvetOnSurfaceVariant
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_password_input"),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder
                    )
                )

                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            localError = "Veuillez renseigner tous les champs."
                            return@Button
                        }
                        if (password.length < 6) {
                            localError = "Le mot de passe doit comporter au moins 6 caractères."
                            return@Button
                        }
                        localError = null
                        if (isSignUp) {
                            onSignUpEmail(email.trim(), password, pseudo.trim())
                        } else {
                            onSignInEmail(email.trim(), password)
                        }
                    },
                    enabled = !isAuthLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("auth_submit_button"),
                    shape = RoundedCornerShape(23.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetPrimary,
                        contentColor = VelvetOnPrimary
                    )
                ) {
                    if (isAuthLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = VelvetOnPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = if (isSignUp) "Créer mon compte Cloud" else "Se connecter",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // Séparateur OU
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(VelvetGlassBorder))
                    Text(
                        text = "OU",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetOnSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(VelvetGlassBorder))
                }

                // Bouton Google Sign-In
                Button(
                    onClick = onSignInGoogle,
                    enabled = !isAuthLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("auth_google_button"),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VelvetSurfaceLowest,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, VelvetGlassBorder)
                ) {
                    Text(text = "G", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color(0xFF4285F4))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Continuer avec Google",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Mode Invité
                TextButton(
                    onClick = onContinueGuest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_guest_continue_button")
                ) {
                    Text(
                        text = "Continuer en Mode Invité (local sans compte)",
                        fontSize = 11.sp,
                        color = VelvetOnSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {}
    )
}

