package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeIngredient(
    val name: String,
    val details: String = "",
    val amountCl: Double = 0.0,
    val unitAlternative: String = "", // e.g. "3 cuil. à soupe", "1/2 citron"
    val isGarnish: Boolean = false,
    val iconType: String = "liquor" // liquor, nutrition, water_drop, invert_colors, spa
)

@Entity(tableName = "cocktails")
@JsonClass(generateAdapter = true)
data class CocktailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subtitle: String = "",
    val description: String = "",
    val category: String, // "Classiques", "Cocktails", "Shooters", "Mocktails"
    val flavorProfile: String = "", // Profil: Fruité/Botanique, Doux/Crémeux, Acidulé/Électrique, Frais/Herbacé
    val prepTimeMinutes: Int = 3, // Temps: 4 min, 3 min
    val difficulty: String = "Facile", // Difficulté: Facile, Moyen, Expert
    val alcoholPercentage: Double = 0.0, // Alcool: 17.5%, 15.0%, 22.0%, 0.0%
    val ingredients: List<RecipeIngredient> = emptyList(), // Ingrédients
    val steps: List<String> = emptyList(), // Préparation (étapes)
    val garnish: String = "", // Garniture: "3 mûres givrées, brin de menthe"
    val glassware: String = "Verre vintage",
    val shakeSeconds: Int = 12,
    val rating: Double = 4.9,
    val imageUrl: String = "",
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false,
    val vibeTag: String = "Speakeasy Lounge & House"
) {
    val isNonAlcoholic: Boolean
        get() = alcoholPercentage == 0.0 || category.contains("Mocktail", ignoreCase = true)
}

// Alias de type pour assurer la cohérence et la compatibilité globale dans l'application
typealias Cocktail = CocktailEntity
