package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

enum class IngredientCategory {
    SPIRITS,    // Spiritueux
    MIXERS,     // Softs
    SYRUPS,     // Sirops
    GARNISHES   // Garnitures
}

@Entity(tableName = "inventory_ingredients")
@JsonClass(generateAdapter = true)
data class InventoryIngredient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val brandOrDetail: String,
    val category: IngredientCategory,
    val tag: String, // "Gin 40°", "Vodka", "Tonic", "Bitter", etc.
    val isOwned: Boolean = false
)
