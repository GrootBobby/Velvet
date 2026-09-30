package com.example.ui.bar

import com.example.data.local.UserPreferencesRepository
import com.example.data.local.UserProgress
import com.example.data.model.Cocktail
import com.example.data.model.IngredientCategory
import com.example.data.model.InventoryIngredient
import com.example.data.repository.SmartShoppingRecommendation

data class BarUiState(
    val userProgress: UserProgress = UserPreferencesRepository.calculateProgress("", false, 0),
    val ingredients: List<InventoryIngredient> = emptyList(),
    val selectedCategory: IngredientCategory = IngredientCategory.SPIRITS,
    val craftableCocktailsCount: Int = 0,
    val smartShopping: SmartShoppingRecommendation? = null,
    val allCocktails: List<Cocktail> = emptyList(),
    val showAddDialog: Boolean = false,
    val isLoading: Boolean = false,
    val toastMessage: String? = null
)

sealed interface BarIntent {
    data class SelectCategory(val category: IngredientCategory) : BarIntent
    data class ToggleIngredient(val id: Long, val currentOwned: Boolean) : BarIntent
    data class AddIngredient(val name: String, val detail: String, val category: IngredientCategory, val tag: String) : BarIntent
    data object OpenAddDialog : BarIntent
    data object CloseAddDialog : BarIntent
    data class BuySmartIngredient(val ingredientName: String) : BarIntent
    data class AddExperience(val amount: Int) : BarIntent
    data object ClearToast : BarIntent
}
