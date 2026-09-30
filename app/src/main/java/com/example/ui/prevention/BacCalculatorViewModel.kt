package com.example.ui.prevention

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BacCalculatorUiState(
    val gender: Gender = Gender.MALE,
    val weightKgText: String = "70",
    val ageText: String = "25",
    val drinks: List<DrinkItem> = listOf(
        DrinkItem(name = "Cocktail Velvet", volumeMl = 150.0, alcoholDegree = 15.0)
    ),
    val hoursElapsed: Float = 1.0f,
    val isCustomDialogOpen: Boolean = false,
    val customName: String = "",
    val customVolumeText: String = "150",
    val customDegreeText: String = "15",
    val calculatedBac: Double = 0.0,
    val isOverLimit: Boolean = false,
    val totalAlcoholGrams: Double = 0.0,
    val hoursToZero: Double = 0.0
)

sealed interface BacCalculatorIntent {
    data class SetGender(val gender: Gender) : BacCalculatorIntent
    data class SetWeight(val weightText: String) : BacCalculatorIntent
    data class SetAge(val ageText: String) : BacCalculatorIntent
    data class SetHoursElapsed(val hours: Float) : BacCalculatorIntent
    data class AddPresetDrink(val name: String, val volumeMl: Double, val alcoholDegree: Double) : BacCalculatorIntent
    data class RemoveDrink(val drinkId: String) : BacCalculatorIntent
    data object OpenCustomDialog : BacCalculatorIntent
    data object CloseCustomDialog : BacCalculatorIntent
    data class UpdateCustomFields(val name: String, val volumeText: String, val degreeText: String) : BacCalculatorIntent
    data object SubmitCustomDrink : BacCalculatorIntent
    data object ResetAll : BacCalculatorIntent
}

class BacCalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BacCalculatorUiState())
    val uiState: StateFlow<BacCalculatorUiState> = _uiState.asStateFlow()

    init {
        recompute()
    }

    fun processIntent(intent: BacCalculatorIntent) {
        when (intent) {
            is BacCalculatorIntent.SetGender -> {
                _uiState.update { it.copy(gender = intent.gender) }
                recompute()
            }
            is BacCalculatorIntent.SetWeight -> {
                val clean = intent.weightText.filter { it.isDigit() }.take(3)
                _uiState.update { it.copy(weightKgText = clean) }
                recompute()
            }
            is BacCalculatorIntent.SetAge -> {
                val clean = intent.ageText.filter { it.isDigit() }.take(3)
                _uiState.update { it.copy(ageText = clean) }
            }
            is BacCalculatorIntent.SetHoursElapsed -> {
                _uiState.update { it.copy(hoursElapsed = intent.hours) }
                recompute()
            }
            is BacCalculatorIntent.AddPresetDrink -> {
                val newDrink = DrinkItem(
                    name = intent.name,
                    volumeMl = intent.volumeMl,
                    alcoholDegree = intent.alcoholDegree
                )
                _uiState.update { it.copy(drinks = it.drinks + newDrink) }
                recompute()
            }
            is BacCalculatorIntent.RemoveDrink -> {
                _uiState.update { state ->
                    state.copy(drinks = state.drinks.filterNot { it.id == intent.drinkId })
                }
                recompute()
            }
            is BacCalculatorIntent.OpenCustomDialog -> {
                _uiState.update {
                    it.copy(
                        isCustomDialogOpen = true,
                        customName = "Boisson maison",
                        customVolumeText = "150",
                        customDegreeText = "15"
                    )
                }
            }
            is BacCalculatorIntent.CloseCustomDialog -> {
                _uiState.update { it.copy(isCustomDialogOpen = false) }
            }
            is BacCalculatorIntent.UpdateCustomFields -> {
                _uiState.update {
                    it.copy(
                        customName = intent.name,
                        customVolumeText = intent.volumeText.filter { c -> c.isDigit() || c == '.' }.take(5),
                        customDegreeText = intent.degreeText.filter { c -> c.isDigit() || c == '.' }.take(4)
                    )
                }
            }
            is BacCalculatorIntent.SubmitCustomDrink -> {
                val state = _uiState.value
                val vol = state.customVolumeText.toDoubleOrNull() ?: 100.0
                val deg = state.customDegreeText.toDoubleOrNull() ?: 12.0
                val name = state.customName.ifBlank { "Cocktail personnalisé" }
                val newDrink = DrinkItem(
                    name = name,
                    volumeMl = vol,
                    alcoholDegree = deg
                )
                _uiState.update {
                    it.copy(
                        drinks = it.drinks + newDrink,
                        isCustomDialogOpen = false
                    )
                }
                recompute()
            }
            is BacCalculatorIntent.ResetAll -> {
                _uiState.update {
                    BacCalculatorUiState(
                        gender = it.gender,
                        weightKgText = it.weightKgText,
                        ageText = it.ageText,
                        drinks = emptyList(),
                        hoursElapsed = 0.5f
                    )
                }
                recompute()
            }
        }
    }

    private fun recompute() {
        _uiState.update { state ->
            val weight = state.weightKgText.toDoubleOrNull() ?: 70.0
            val bac = BacCalculatorLogic.calculateBac(
                drinks = state.drinks,
                gender = state.gender,
                weightKg = weight,
                hoursElapsed = state.hoursElapsed.toDouble()
            )
            val totalGrams = state.drinks.sumOf { it.pureAlcoholGrams }
            val timeToZero = BacCalculatorLogic.calculateTimeToSober(
                drinks = state.drinks,
                gender = state.gender,
                weightKg = weight,
                hoursElapsed = state.hoursElapsed.toDouble()
            )

            state.copy(
                calculatedBac = bac,
                isOverLimit = bac >= 0.5,
                totalAlcoholGrams = (totalGrams * 10.0).toInt() / 10.0,
                hoursToZero = timeToZero
            )
        }
    }
}
