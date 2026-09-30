package com.example.ui.bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.InitialData
import com.example.data.local.UserPreferencesRepository
import com.example.data.local.UserProgress
import com.example.data.model.Cocktail
import com.example.data.model.IngredientCategory
import com.example.data.model.InventoryIngredient
import com.example.data.repository.CocktailRepository
import com.example.data.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BarViewModel(
    private val inventoryRepository: InventoryRepository,
    private val cocktailRepository: CocktailRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarUiState(isLoading = true))
    val uiState: StateFlow<BarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Seed initial data if first launch
            inventoryRepository.ensureInitialData(InitialData.getInitialIngredients())
            cocktailRepository.ensureInitialData(InitialData.getInitialCocktails())
        }

        // Combine DB & DataStore flows into reactive state
        viewModelScope.launch {
            combine(
                inventoryRepository.allIngredients,
                cocktailRepository.allCocktails,
                userPreferencesRepository.userProgress
            ) { ingredients, cocktails, progress ->
                Triple(ingredients, cocktails, progress)
            }.collect { (ingredients, cocktails, progress) ->
                recomputeState(ingredients, cocktails, progress)
            }
        }
    }

    private fun recomputeState(
        ingredients: List<InventoryIngredient>,
        cocktails: List<Cocktail>,
        progress: UserProgress
    ) {
        val ownedNames = ingredients.filter { it.isOwned }.map { it.name.lowercase().trim() }

        val craftableCount = cocktails.count { cocktail ->
            inventoryRepository.isCocktailCraftable(cocktail, ownedNames)
        }

        val smartShopping = inventoryRepository.computeSmartShoppingRecommendation(
            allCocktails = cocktails,
            allIngredients = ingredients
        )

        _uiState.update { current ->
            current.copy(
                ingredients = ingredients,
                allCocktails = cocktails,
                userProgress = progress,
                craftableCocktailsCount = maxOf(craftableCount, 3),
                smartShopping = smartShopping,
                isLoading = false
            )
        }
    }

    fun addXp(amount: Int) {
        viewModelScope.launch {
            userPreferencesRepository.addXp(amount)
        }
    }

    fun processIntent(intent: BarIntent) {
        when (intent) {
            is BarIntent.SelectCategory -> {
                _uiState.update { it.copy(selectedCategory = intent.category) }
            }
            is BarIntent.ToggleIngredient -> {
                viewModelScope.launch {
                    inventoryRepository.toggleOwned(intent.id, intent.currentOwned)
                }
            }
            is BarIntent.AddIngredient -> {
                viewModelScope.launch {
                    inventoryRepository.addIngredient(
                        name = intent.name,
                        detail = intent.detail,
                        category = intent.category,
                        tag = intent.tag
                    )
                    userPreferencesRepository.addXp(30)
                    _uiState.update {
                        it.copy(
                            showAddDialog = false,
                            toastMessage = "${intent.name} ajouté à votre bar ! +30 XP"
                        )
                    }
                }
            }
            is BarIntent.OpenAddDialog -> {
                _uiState.update { it.copy(showAddDialog = true) }
            }
            is BarIntent.CloseAddDialog -> {
                _uiState.update { it.copy(showAddDialog = false) }
            }
            is BarIntent.BuySmartIngredient -> {
                viewModelScope.launch {
                    val ing = _uiState.value.ingredients.find {
                        it.name.contains(intent.ingredientName, ignoreCase = true)
                    }
                    if (ing != null) {
                        inventoryRepository.toggleOwned(ing.id, false)
                        userPreferencesRepository.addXp(50)
                        _uiState.update { it.copy(toastMessage = "${ing.name} ajouté à votre bar ! +50 XP") }
                    } else {
                        inventoryRepository.addIngredient(
                            name = intent.ingredientName,
                            detail = "Acheté pour vos cocktails",
                            category = IngredientCategory.MIXERS,
                            tag = "Agrumes"
                        )
                        userPreferencesRepository.addXp(50)
                        _uiState.update { it.copy(toastMessage = "${intent.ingredientName} ajouté ! +50 XP") }
                    }
                }
            }
            is BarIntent.AddExperience -> {
                addXp(intent.amount)
            }
            is BarIntent.ClearToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
        }
    }
}
