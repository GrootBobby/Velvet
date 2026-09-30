package com.example.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetPrimaryContainer
import com.example.ui.theme.VelvetSecondaryContainer
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary

@Composable
fun OnboardingScreen(
    onComplete: (firstName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var firstName by remember { mutableStateOf("") }
    val isValid = firstName.trim().isNotBlank()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VelvetSurface)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Neon Martini Logo Icon
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(VelvetSecondaryContainer.copy(alpha = 0.4f))
                    .border(2.dp, VelvetPrimary.copy(alpha = 0.7f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍸",
                    fontSize = 42.sp
                )
            }

            // Tagline & Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(VelvetSurfaceHigh)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "VELVET SPEAKEASY CLUB",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VelvetPrimary,
                        letterSpacing = 2.sp
                    )
                }

                Text(
                    text = "Bienvenue au Bar Privé",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "L'accès exclusif à vos cocktails et recettes clandestines. Comment doit-on vous appeler ?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = VelvetOnSurfaceVariant,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            // Input Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VelvetGlassBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "VOTRE PRÉNOM",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = VelvetTertiary
                    )

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_firstname_input"),
                        placeholder = {
                            Text(
                                text = "Ex: Alexandre, Camille, Thomas...",
                                color = VelvetOnSurfaceVariant
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocalBar,
                                contentDescription = null,
                                tint = VelvetPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = VelvetSurfaceLowest,
                            unfocusedContainerColor = VelvetSurfaceLowest,
                            focusedBorderColor = VelvetPrimary,
                            unfocusedBorderColor = VelvetGlassBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = VelvetTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "+50 XP de bienvenue offerts pour votre inauguration",
                            fontSize = 11.sp,
                            color = VelvetTertiary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // CTA Button
            Button(
                onClick = {
                    if (isValid) {
                        onComplete(firstName.trim())
                    }
                },
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("onboarding_submit_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VelvetPrimaryContainer,
                    contentColor = VelvetOnPrimary,
                    disabledContainerColor = VelvetSurfaceHigh,
                    disabledContentColor = VelvetOnSurfaceVariant
                )
            ) {
                Text(
                    text = "Inaugurer mon Bar Privé",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
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
