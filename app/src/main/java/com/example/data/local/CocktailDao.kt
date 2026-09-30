package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails ORDER BY id ASC")
    fun getAllCocktails(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM cocktails WHERE id = :id")
    fun getCocktailById(id: Long): Flow<CocktailEntity?>

    @Query("SELECT * FROM cocktails WHERE id IN (:ids)")
    suspend fun getCocktailsByIds(ids: List<Long>): List<CocktailEntity>

    @Query("SELECT * FROM cocktails WHERE isFavorite = 1")
    fun getFavoriteCocktails(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM cocktails WHERE isCustom = 1")
    fun getCustomCocktails(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM cocktails WHERE category = :category")
    fun getCocktailsByCategory(category: String): Flow<List<CocktailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: CocktailEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cocktails: List<CocktailEntity>)

    @Update
    suspend fun updateCocktail(cocktail: CocktailEntity)

    @Query("UPDATE cocktails SET isFavorite = :isFav WHERE id = :id")
    suspend fun setFavorite(id: Long, isFav: Boolean)

    @Query("SELECT COUNT(*) FROM cocktails")
    suspend fun getCocktailCount(): Int

    @Query("DELETE FROM cocktails")
    suspend fun clearAll()
}
