package com.example.ui.catalog

import com.example.data.model.Cocktail

data class CatalogUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val filteredCocktails: List<Cocktail> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "Tous",
    val onlyFavorites: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface CatalogIntent {
    data class Search(val query: String) : CatalogIntent
    data class SelectCategory(val category: String) : CatalogIntent
    data class ToggleFavoritesOnly(val enabled: Boolean) : CatalogIntent
    data class ToggleFavorite(val cocktailId: Long, val currentFav: Boolean) : CatalogIntent
}
