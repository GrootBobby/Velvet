package com.example.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Cocktail
import com.example.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState(isLoading = true))
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cocktailRepository.allCocktails.collect { list ->
                _uiState.update { current ->
                    current.copy(
                        cocktails = list,
                        filteredCocktails = applyFilters(
                            cocktails = list,
                            query = current.searchQuery,
                            category = current.selectedCategory,
                            onlyFavs = current.onlyFavorites
                        ),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setInitialCategory(category: String?) {
        if (!category.isNullOrBlank()) {
            processIntent(CatalogIntent.SelectCategory(category))
        }
    }

    fun processIntent(intent: CatalogIntent) {
        when (intent) {
            is CatalogIntent.Search -> {
                _uiState.update { current ->
                    current.copy(
                        searchQuery = intent.query,
                        filteredCocktails = applyFilters(
                            cocktails = current.cocktails,
                            query = intent.query,
                            category = current.selectedCategory,
                            onlyFavs = current.onlyFavorites
                        )
                    )
                }
            }
            is CatalogIntent.SelectCategory -> {
                _uiState.update { current ->
                    val isFav = intent.category == "Favoris"
                    current.copy(
                        selectedCategory = intent.category,
                        onlyFavorites = isFav,
                        filteredCocktails = applyFilters(
                            cocktails = current.cocktails,
                            query = current.searchQuery,
                            category = intent.category,
                            onlyFavs = isFav
                        )
                    )
                }
            }
            is CatalogIntent.ToggleFavoritesOnly -> {
                _uiState.update { current ->
                    val newCategory = if (intent.enabled) "Favoris" else if (current.selectedCategory == "Favoris") "Tous" else current.selectedCategory
                    current.copy(
                        onlyFavorites = intent.enabled,
                        selectedCategory = newCategory,
                        filteredCocktails = applyFilters(
                            cocktails = current.cocktails,
                            query = current.searchQuery,
                            category = newCategory,
                            onlyFavs = intent.enabled
                        )
                    )
                }
            }
            is CatalogIntent.ToggleFavorite -> {
                viewModelScope.launch {
                    cocktailRepository.toggleFavorite(intent.cocktailId, intent.currentFav)
                }
            }
        }
    }

    private fun applyFilters(
        cocktails: List<Cocktail>,
        query: String,
        category: String,
        onlyFavs: Boolean
    ): List<Cocktail> {
        return cocktails.filter { cocktail ->
            val matchesCategory = when (category) {
                "Tous" -> true
                "Classiques" -> cocktail.category.equals("Classiques", ignoreCase = true) || cocktail.category.equals("Classique", ignoreCase = true)
                "Cocktails" -> cocktail.category.equals("Cocktails", ignoreCase = true)
                "Shooters" -> cocktail.category.equals("Shooters", ignoreCase = true) || cocktail.category.startsWith("Shooter", ignoreCase = true)
                "Mocktails" -> cocktail.category.equals("Mocktails", ignoreCase = true) || cocktail.isNonAlcoholic || cocktail.category.startsWith("Mocktail", ignoreCase = true)
                "Favoris" -> cocktail.isFavorite
                else -> cocktail.category.equals(category, ignoreCase = true)
            }
            val matchesFav = (!onlyFavs || cocktail.isFavorite)
            val matchesQuery = (query.isBlank() ||
                    cocktail.name.contains(query, ignoreCase = true) ||
                    cocktail.description.contains(query, ignoreCase = true) ||
                    cocktail.flavorProfile.contains(query, ignoreCase = true) ||
                    cocktail.ingredients.any { it.name.contains(query, ignoreCase = true) })
            matchesCategory && matchesFav && matchesQuery
        }
    }
}
