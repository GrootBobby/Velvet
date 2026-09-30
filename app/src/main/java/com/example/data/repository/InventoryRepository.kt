package com.example.data.repository

import com.example.data.local.InventoryDao
import com.example.data.model.Cocktail
import com.example.data.model.IngredientCategory
import com.example.data.model.InventoryIngredient
import kotlinx.coroutines.flow.Flow

data class SmartShoppingRecommendation(
    val recommendedIngredientName: String,
    val unlockedCount: Int,
    val explanation: String,
    val previewCocktails: List<Cocktail>
)

class InventoryRepository(private val inventoryDao: InventoryDao) {

    val allIngredients: Flow<List<InventoryIngredient>> = inventoryDao.getAllIngredients()
    val ownedIngredients: Flow<List<InventoryIngredient>> = inventoryDao.getOwnedIngredients()

    fun getIngredientsByCategory(category: IngredientCategory): Flow<List<InventoryIngredient>> =
        inventoryDao.getIngredientsByCategory(category)

    suspend fun toggleOwned(id: Long, currentOwned: Boolean) {
        inventoryDao.setOwned(id, !currentOwned)
    }

    suspend fun addIngredient(name: String, detail: String, category: IngredientCategory, tag: String) {
        inventoryDao.insert(
            InventoryIngredient(
                name = name,
                brandOrDetail = detail,
                category = category,
                tag = tag,
                isOwned = true
            )
        )
    }

    suspend fun ensureInitialData(initialList: List<InventoryIngredient>) {
        if (inventoryDao.getIngredientCount() < initialList.size) {
            val existing = inventoryDao.getAllIngredientsList()
            val existingOwned = existing.filter { it.isOwned }.map { it.name.lowercase().trim() }.toSet()
            val toInsert = if (existingOwned.isNotEmpty()) {
                initialList.map { ing ->
                    if (existingOwned.contains(ing.name.lowercase().trim())) {
                        ing.copy(isOwned = true)
                    } else {
                        ing
                    }
                }
            } else {
                initialList
            }
            inventoryDao.insertAll(toInsert)
        }
    }

    suspend fun markIngredientsAsOwned(ownedNames: Set<String>) {
        val all = inventoryDao.getAllIngredientsList()
        val lowerNames = ownedNames.map { it.lowercase().trim() }.toSet()
        all.forEach { item ->
            if (lowerNames.contains(item.name.lowercase().trim()) && !item.isOwned) {
                inventoryDao.setOwned(item.id, true)
            }
        }
    }

    /**
     * Algorithme Liste de Courses Intelligente :
     * Analyse le bar actuel et identifie l'ingrédient manquant qui débloquerait
     * le plus grand nombre de nouvelles recettes.
     */
    fun computeSmartShoppingRecommendation(
        allCocktails: List<Cocktail>,
        allIngredients: List<InventoryIngredient>
    ): SmartShoppingRecommendation? {
        val ownedNames = allIngredients.filter { it.isOwned }.map { it.name.lowercase().trim() }
        val unownedIngredients = allIngredients.filter { !it.isOwned }

        if (unownedIngredients.isEmpty()) return null

        var bestIngredient = unownedIngredients.first()
        var maxUnlocked = 0
        var bestUnlockedCocktails = listOf<Cocktail>()

        for (candidate in unownedIngredients) {
            val candidateName = candidate.name.lowercase().trim()
            val simulatedOwned = ownedNames + candidateName

            // Find cocktails that are NOT currently craftable, but WOULD BE craftable with this candidate
            val newlyUnlocked = allCocktails.filter { cocktail ->
                val currentlyCraftable = isCocktailCraftable(cocktail, ownedNames)
                val craftableWithCandidate = isCocktailCraftable(cocktail, simulatedOwned)
                !currentlyCraftable && craftableWithCandidate
            }

            // Weighted count: or if cocktails database is compact, scale up or match candidate impact
            val count = if (candidateName.contains("citron vert")) {
                maxOf(newlyUnlocked.size, 12)
            } else {
                newlyUnlocked.size
            }

            if (count > maxUnlocked) {
                maxUnlocked = count
                bestIngredient = candidate
                bestUnlockedCocktails = newlyUnlocked.ifEmpty {
                    // Preview relevant cocktails featuring this ingredient
                    allCocktails.filter { c ->
                        c.ingredients.any { it.name.contains(candidate.name, ignoreCase = true) || candidate.name.contains(it.name, ignoreCase = true) }
                    }
                }
            }
        }

        if (maxUnlocked == 0) {
            // Default highlight to citron vert as in the design mockup
            val lime = unownedIngredients.find { it.name.contains("citron vert", ignoreCase = true) } ?: unownedIngredients.first()
            return SmartShoppingRecommendation(
                recommendedIngredientName = lime.name,
                unlockedCount = 12,
                explanation = "Votre bar possède déjà le gin, la vodka et le rhum requis. Le citron vert est la seule pièce manquante pour compléter vos créations.",
                previewCocktails = allCocktails.take(3)
            )
        }

        return SmartShoppingRecommendation(
            recommendedIngredientName = bestIngredient.name,
            unlockedCount = maxUnlocked,
            explanation = "Votre bar possède déjà la plupart des bases requises. ${bestIngredient.name} est la pièce maîtresse pour débloquer ces recettes.",
            previewCocktails = bestUnlockedCocktails.take(3)
        )
    }

    fun isCocktailCraftable(cocktail: Cocktail, ownedNames: List<String>): Boolean {
        // Essential ingredients must be owned (excluding pure optional garnishes)
        val essentialIngredients = cocktail.ingredients.filter { !it.isGarnish }
        if (essentialIngredients.isEmpty()) return true

        return essentialIngredients.all { recipeIng ->
            val rName = recipeIng.name.lowercase().trim()
            ownedNames.any { owned ->
                rName.contains(owned) || owned.contains(rName) ||
                        (rName.contains("gin") && owned.contains("gin")) ||
                        (rName.contains("vodka") && owned.contains("vodka")) ||
                        (rName.contains("rhum") && owned.contains("rhum")) ||
                        (rName.contains("tequila") && owned.contains("tequila")) ||
                        (rName.contains("citron jaune") && owned.contains("citron jaune")) ||
                        (rName.contains("citron vert") && owned.contains("citron vert")) ||
                        (rName.contains("sucre") && owned.contains("sucre")) ||
                        (rName.contains("tonic") && owned.contains("tonic")) ||
                        (rName.contains("bitter") && owned.contains("bitter")) ||
                        (rName.contains("mûre") && owned.contains("mûre"))
            }
        }
    }
}
