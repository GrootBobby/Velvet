package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.IngredientCategory
import com.example.data.model.InventoryIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory_ingredients ORDER BY id ASC")
    fun getAllIngredients(): Flow<List<InventoryIngredient>>

    @Query("SELECT * FROM inventory_ingredients WHERE category = :category ORDER BY id ASC")
    fun getIngredientsByCategory(category: IngredientCategory): Flow<List<InventoryIngredient>>

    @Query("SELECT * FROM inventory_ingredients WHERE isOwned = 1")
    fun getOwnedIngredients(): Flow<List<InventoryIngredient>>

    @Query("SELECT * FROM inventory_ingredients ORDER BY id ASC")
    suspend fun getAllIngredientsList(): List<InventoryIngredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients: List<InventoryIngredient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: InventoryIngredient): Long

    @Update
    suspend fun update(ingredient: InventoryIngredient)

    @Query("UPDATE inventory_ingredients SET isOwned = :isOwned WHERE id = :id")
    suspend fun setOwned(id: Long, isOwned: Boolean)

    @Query("SELECT COUNT(*) FROM inventory_ingredients")
    suspend fun getIngredientCount(): Int
}
