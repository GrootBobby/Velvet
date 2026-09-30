package com.example.data.repository

import com.example.data.local.CocktailDao
import com.example.data.local.getSeedCocktails
import com.example.data.model.CocktailEntity
import kotlinx.coroutines.flow.Flow

class CocktailRepository(private val cocktailDao: CocktailDao) {

    val allCocktails: Flow<List<CocktailEntity>> = cocktailDao.getAllCocktails()
    val favoriteCocktails: Flow<List<CocktailEntity>> = cocktailDao.getFavoriteCocktails()
    val customCocktails: Flow<List<CocktailEntity>> = cocktailDao.getCustomCocktails()

    fun getCocktailById(id: Long): Flow<CocktailEntity?> = cocktailDao.getCocktailById(id)

    fun getCocktailsByCategory(category: String): Flow<List<CocktailEntity>> =
        cocktailDao.getCocktailsByCategory(category)

    suspend fun toggleFavorite(id: Long, currentFav: Boolean) {
        cocktailDao.setFavorite(id, !currentFav)
    }

    suspend fun insertCustomCocktail(cocktail: CocktailEntity): Long {
        return cocktailDao.insertCocktail(cocktail.copy(isCustom = true))
    }

    suspend fun ensureInitialData(initialList: List<CocktailEntity>) {
        if (cocktailDao.getCocktailCount() < initialList.size) {
            cocktailDao.insertAll(initialList)
        }
    }

    suspend fun seedDatabase() {
        val seed = getSeedCocktails()
        if (cocktailDao.getCocktailCount() < seed.size) {
            cocktailDao.insertAll(seed)
        }
    }

    suspend fun markFavorites(ids: List<Long>) {
        ids.forEach { id ->
            cocktailDao.setFavorite(id, true)
        }
    }

    suspend fun getCocktailNamesByIds(ids: List<Long>): List<String> {
        if (ids.isEmpty()) return emptyList()
        return try {
            cocktailDao.getCocktailsByIds(ids).map { it.name }
        } catch (_: Exception) {
            emptyList()
        }
    }
}
