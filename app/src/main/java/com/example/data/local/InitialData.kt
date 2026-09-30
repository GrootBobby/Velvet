package com.example.data.local

import com.example.data.model.CocktailEntity
import com.example.data.model.InventoryIngredient

object InitialData {
    /**
     * Extrait dynamiquement tous les ingrédients uniques depuis l'ensemble des 100 cocktails.
     */
    fun getInitialIngredients(): List<InventoryIngredient> =
        extractUniqueIngredients(getSeedCocktails())

    fun getInitialCocktails(): List<CocktailEntity> = getSeedCocktails()
}
