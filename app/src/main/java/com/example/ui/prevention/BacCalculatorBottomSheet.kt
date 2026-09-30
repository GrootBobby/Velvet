package com.example.ui.prevention

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSurface
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BacCalculatorBottomSheet(
    uiState: BacCalculatorUiState,
    onIntent: (BacCalculatorIntent) -> Unit,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = VelvetSurface,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF231C38)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HealthAndSafety,
                                contentDescription = null,
                                tint = VelvetSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Calculateur d'Alcoolémie",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "Mode Prévention • Formule de Widmark",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = VelvetSecondary
                                )
                            )
                        }
                    }

                    Row {
                        IconButton(
                            onClick = { onIntent(BacCalculatorIntent.ResetAll) },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Réinitialiser",
                                tint = VelvetOnSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = onDismissRequest,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fermer",
                                tint = VelvetOnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // 1. RESULT HERO DISPLAY
            item {
                val isRed = uiState.calculatedBac >= 0.5
                val resultColor = if (isRed) Color(0xFFFF3B30) else Color(0xFF4CAF50)
                val statusText = if (isRed) {
                    "DÉPASSEMENT DU SEUIL LÉGAL (≥ 0.5 g/L)\nNE PRENEZ PAS LE VOLANT !"
                } else if (uiState.calculatedBac > 0.0) {
                    "En dessous du seuil légal (0.5 g/L)\nRestez vigilant"
                } else {
                    "Taux nul estimé (0.00 g/L)"
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .border(1.5.dp, resultColor.copy(alpha = 0.7f), RoundedCornerShape(22.dp))
                        .testTag("bac_result_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "ESTIMATION ALCOOLÉMIE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetOnSurfaceVariant,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = String.format(Locale.US, "%.2f g/L", uiState.calculatedBac),
                            fontSize = 42.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = resultColor,
                            modifier = Modifier.testTag("bac_result_text")
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(resultColor.copy(alpha = 0.15f))
                                .border(1.dp, resultColor.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = statusText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = resultColor,
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }

                        // Sub statistics
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${uiState.totalAlcoholGrams} g",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Alcool pur",
                                    fontSize = 10.sp,
                                    color = VelvetOnSurfaceVariant
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(28.dp)
                                    .background(VelvetGlassBorder)
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${uiState.drinks.size}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Verre(s)",
                                    fontSize = 10.sp,
                                    color = VelvetOnSurfaceVariant
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(28.dp)
                                    .background(VelvetGlassBorder)
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = if (uiState.hoursToZero > 0) "~${uiState.hoursToZero}h" else "0h",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = VelvetTertiary
                                )
                                Text(
                                    text = "Retour à 0.0",
                                    fontSize = 10.sp,
                                    color = VelvetOnSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // 2. PHYSIOLOGICAL PROFILE
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "PROFIL PHYSIOLOGIQUE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetPrimary,
                            letterSpacing = 1.sp
                        )

                        // Sexe Radio Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sexe :",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.width(60.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { onIntent(BacCalculatorIntent.SetGender(Gender.MALE)) }
                                    .padding(end = 16.dp)
                            ) {
                                RadioButton(
                                    selected = uiState.gender == Gender.MALE,
                                    onClick = { onIntent(BacCalculatorIntent.SetGender(Gender.MALE)) },
                                    colors = RadioButtonDefaults.colors(selectedColor = VelvetPrimary)
                                )
                                Text(
                                    text = "Homme (K=0.7)",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    onIntent(BacCalculatorIntent.SetGender(Gender.FEMALE))
                                }
                            ) {
                                RadioButton(
                                    selected = uiState.gender == Gender.FEMALE,
                                    onClick = { onIntent(BacCalculatorIntent.SetGender(Gender.FEMALE)) },
                                    colors = RadioButtonDefaults.colors(selectedColor = VelvetPrimary)
                                )
                                Text(
                                    text = "Femme (K=0.6)",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Poids & Âge TextFields
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = uiState.weightKgText,
                                onValueChange = { onIntent(BacCalculatorIntent.SetWeight(it)) },
                                label = { Text("Poids (kg)") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("bac_weight_input"),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = VelvetPrimary,
                                    unfocusedBorderColor = VelvetGlassBorder,
                                    focusedContainerColor = VelvetSurfaceLowest,
                                    unfocusedContainerColor = VelvetSurfaceLowest
                                )
                            )

                            OutlinedTextField(
                                value = uiState.ageText,
                                onValueChange = { onIntent(BacCalculatorIntent.SetAge(it)) },
                                label = { Text("Âge") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("bac_age_input"),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = VelvetPrimary,
                                    unfocusedBorderColor = VelvetGlassBorder,
                                    focusedContainerColor = VelvetSurfaceLowest,
                                    unfocusedContainerColor = VelvetSurfaceLowest
                                )
                            )
                        }
                    }
                }
            }

            // 3. TIME ELAPSED SLIDER
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = VelvetSurfaceHigh)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TEMPS ÉCOULÉ DEPUIS LE 1ER VERRE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VelvetPrimary,
                                letterSpacing = 1.sp
                            )

                            val hours = uiState.hoursElapsed
                            val wholeHours = hours.toInt()
                            val minutes = ((hours - wholeHours) * 60).roundToInt()
                            val timeStr = if (minutes == 0) "${wholeHours}h" else "${wholeHours}h ${minutes}min"

                            Text(
                                text = timeStr,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = VelvetTertiary
                            )
                        }

                        Slider(
                            value = uiState.hoursElapsed,
                            onValueChange = { onIntent(BacCalculatorIntent.SetHoursElapsed(it)) },
                            valueRange = 0f..8f,
                            steps = 15, // increments of 0.5h
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("bac_hours_slider"),
                            colors = SliderDefaults.colors(
                                thumbColor = VelvetPrimary,
                                activeTrackColor = VelvetPrimary,
                                inactiveTrackColor = VelvetSurfaceLowest
                            )
                        )

                        Text(
                            text = "L'organisme élimine environ 0.15 g/L d'alcool par heure.",
                            fontSize = 11.sp,
                            color = VelvetOnSurfaceVariant
                        )
                    }
                }
            }

            // 4. DRINKS CONSUMPTION
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CONSOMMATIONS (${uiState.drinks.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VelvetPrimary,
                            letterSpacing = 1.sp
                        )

                        TextButton(
                            onClick = { onIntent(BacCalculatorIntent.OpenCustomDialog) },
                            modifier = Modifier.testTag("bac_add_custom_drink_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = VelvetPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Personnalisé", color = VelvetPrimary, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Quick presets chips
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DrinkPresetChip(
                            label = "🍺 Bière 25cl (5%)",
                            onClick = {
                                onIntent(BacCalculatorIntent.AddPresetDrink("Bière 25cl", 250.0, 5.0))
                            }
                        )
                        DrinkPresetChip(
                            label = "🍷 Vin 12.5cl (12%)",
                            onClick = {
                                onIntent(BacCalculatorIntent.AddPresetDrink("Vin 12.5cl", 125.0, 12.0))
                            }
                        )
                        DrinkPresetChip(
                            label = "🍸 Cocktail 15cl (15%)",
                            onClick = {
                                onIntent(BacCalculatorIntent.AddPresetDrink("Cocktail 15cl", 150.0, 15.0))
                            }
                        )
                        DrinkPresetChip(
                            label = "🥃 Shot 4cl (40%)",
                            onClick = {
                                onIntent(BacCalculatorIntent.AddPresetDrink("Shot 4cl", 40.0, 40.0))
                            }
                        )
                    }
                }
            }

            // List of drinks
            if (uiState.drinks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(VelvetSurfaceLowest)
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aucun verre ajouté. Cliquez sur les boutons ci-dessus pour ajouter des boissons.",
                            fontSize = 12.sp,
                            color = VelvetOnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(uiState.drinks, key = { it.id }) { drink ->
                    DrinkRowItem(
                        drink = drink,
                        onDelete = { onIntent(BacCalculatorIntent.RemoveDrink(drink.id)) }
                    )
                }
            }

            // 5. MANDATORY LEGAL WARNING BANNER
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, Color(0xFFFF9800), RoundedCornerShape(16.dp))
                        .testTag("bac_mandatory_warning_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF26180B))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Avertissement Légal",
                            tint = Color(0xFFFF9800),
                            modifier = Modifier
                                .size(24.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "ATTENTION : Ce résultat est une estimation mathématique fournie à titre purement indicatif. L'absorption varie selon les individus et le repas. Seul un éthylotest certifié ou une prise de sang fait foi. Si vous avez bu, ne prenez pas le volant.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFFFCC80),
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Custom Drink Dialog
    if (uiState.isCustomDialogOpen) {
        CustomDrinkDialog(
            name = uiState.customName,
            volumeText = uiState.customVolumeText,
            degreeText = uiState.customDegreeText,
            onUpdateFields = { name, vol, deg ->
                onIntent(BacCalculatorIntent.UpdateCustomFields(name, vol, deg))
            },
            onDismiss = { onIntent(BacCalculatorIntent.CloseCustomDialog) },
            onConfirm = { onIntent(BacCalculatorIntent.SubmitCustomDrink) }
        )
    }
}

@Composable
private fun DrinkPresetChip(
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(VelvetSurfaceLowest)
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .testTag("bac_preset_chip_$label")
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun DrinkRowItem(
    drink: DrinkItem,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(VelvetSurfaceHigh)
            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.LocalBar,
                contentDescription = null,
                tint = VelvetPrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = drink.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${drink.volumeMl.toInt()} ml • ${drink.alcoholDegree}% vol. (${String.format(Locale.US, "%.1f", drink.pureAlcoholGrams)}g d'alcool)",
                    fontSize = 11.sp,
                    color = VelvetOnSurfaceVariant
                )
            }
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(36.dp)
                .testTag("bac_remove_drink_${drink.id}")
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Supprimer le verre",
                tint = Color(0xFFFF5252),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun CustomDrinkDialog(
    name: String,
    volumeText: String,
    degreeText: String,
    onUpdateFields: (String, String, String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Ajouter un verre personnalisé",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { onUpdateFields(it, volumeText, degreeText) },
                    label = { Text("Nom du cocktail / boisson") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder
                    ),
                    modifier = Modifier.testTag("custom_drink_name_input")
                )

                OutlinedTextField(
                    value = volumeText,
                    onValueChange = { onUpdateFields(name, it, degreeText) },
                    label = { Text("Volume (en ml)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder
                    ),
                    modifier = Modifier.testTag("custom_drink_volume_input")
                )

                OutlinedTextField(
                    value = degreeText,
                    onValueChange = { onUpdateFields(name, volumeText, it) },
                    label = { Text("Degré d'alcool (%)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VelvetPrimary,
                        unfocusedBorderColor = VelvetGlassBorder
                    ),
                    modifier = Modifier.testTag("custom_drink_degree_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = VelvetPrimary),
                modifier = Modifier.testTag("custom_drink_confirm_button")
            ) {
                Text("Ajouter", color = VelvetOnPrimary, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = VelvetOnSurfaceVariant)
            }
        },
        containerColor = VelvetSurfaceHigh
    )
}
