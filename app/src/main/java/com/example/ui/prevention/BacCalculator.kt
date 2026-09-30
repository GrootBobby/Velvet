package com.example.ui.prevention

import java.util.UUID
import kotlin.math.max
import kotlin.math.roundToInt

enum class Gender {
    MALE,
    FEMALE
}

data class DrinkItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val volumeMl: Double,
    val alcoholDegree: Double
) {
    val pureAlcoholGrams: Double
        get() = BacCalculatorLogic.calculatePureAlcoholGrams(volumeMl, alcoholDegree)
}

object BacCalculatorLogic {
    /**
     * Masse d'alcool pur (en grammes) = Volume (ml) × (Degré / 100) × 0.8
     */
    fun calculatePureAlcoholGrams(volumeMl: Double, alcoholDegree: Double): Double {
        if (volumeMl <= 0 || alcoholDegree <= 0) return 0.0
        return volumeMl * (alcoholDegree / 100.0) * 0.8
    }

    /**
     * Formule de Widmark :
     * K = 0.7 (Homme), 0.6 (Femme)
     * Taux brut (g/L) = Masse d'alcool / (Poids × K)
     * Taux final estimé = max(0.0, Taux brut - (0.15 × Temps écoulé en heures))
     */
    fun calculateBac(
        drinks: List<DrinkItem>,
        gender: Gender,
        weightKg: Double,
        hoursElapsed: Double
    ): Double {
        val safeWeight = if (weightKg <= 0.0) 70.0 else weightKg
        val safeHours = max(0.0, hoursElapsed)
        val k = if (gender == Gender.MALE) 0.7 else 0.6

        val totalPureAlcoholGrams = drinks.sumOf { calculatePureAlcoholGrams(it.volumeMl, it.alcoholDegree) }
        if (totalPureAlcoholGrams <= 0.0) return 0.0

        val rawBac = totalPureAlcoholGrams / (safeWeight * k)
        val finalBac = max(0.0, rawBac - (0.15 * safeHours))

        // Round to 2 decimal places
        return (finalBac * 100.0).roundToInt() / 100.0
    }

    /**
     * Calcule le temps nécessaire en heures pour éliminer tout l'alcool (retour à 0.0 g/L).
     */
    fun calculateTimeToSober(
        drinks: List<DrinkItem>,
        gender: Gender,
        weightKg: Double,
        hoursElapsed: Double
    ): Double {
        val currentBac = calculateBac(drinks, gender, weightKg, hoursElapsed)
        if (currentBac <= 0.0) return 0.0
        val remainingHours = currentBac / 0.15
        return (remainingHours * 10.0).roundToInt() / 10.0
    }
}
