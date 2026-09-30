package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSurface

@Composable
fun VelvetTopBar(
    title: String? = null,
    avatarUrl: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onFriendsClick: (() -> Unit)? = null,
    onBacCalculatorClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(VelvetSurface)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Retour",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            } else if (onFriendsClick != null) {
                IconButton(
                    onClick = onFriendsClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("top_bar_friends_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Amis & Classement",
                        tint = VelvetPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }

            // Neon Mini Cocktail Icon
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF291833)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍸",
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            } else {
                Text(
                    text = "VELVET",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp,
                        color = VelvetPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (onBacCalculatorClick != null) {
                IconButton(
                    onClick = onBacCalculatorClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("top_bar_bac_calculator_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.HealthAndSafety,
                        contentDescription = "Calculateur d'Alcoolémie (Prévention)",
                        tint = VelvetSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }

            // User Profile Avatar with glowing neon ring (clickable to change photo)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, VelvetPrimary.copy(alpha = 0.8f), CircleShape)
                    .clickable { onProfileClick() }
                    .testTag("top_bar_profile_avatar"),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = avatarUrl?.ifBlank { null } ?: DEFAULT_PROFILE_AVATAR,
                    contentDescription = "Photo de profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
