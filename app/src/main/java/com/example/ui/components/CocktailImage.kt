package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetSurfaceLowest

/**
 * Composant de chargement d'image asynchrone sécurisé pour les recettes de cocktails.
 * Intègre un indicateur de chargement non-bloquant et un placeholder de repli (icône de bar
 * sur fond sombre) en cas de lenteur réseau ou d'URL indisponible afin d'éviter tout crash.
 */
@Composable
fun CocktailImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    iconSize: Dp = 36.dp
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl.ifBlank { null })
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(VelvetSurfaceLowest),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = VelvetPrimary.copy(alpha = 0.6f),
                    strokeWidth = 2.dp
                )
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(VelvetSurfaceLowest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalBar,
                    contentDescription = contentDescription,
                    tint = VelvetPrimary.copy(alpha = 0.45f),
                    modifier = Modifier.size(iconSize)
                )
            }
        },
        modifier = modifier
    )
}
