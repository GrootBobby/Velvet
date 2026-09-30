package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.RecipeIngredient
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val ingredientsListType = Types.newParameterizedType(List::class.java, RecipeIngredient::class.java)
    private val stringListType = Types.newParameterizedType(List::class.java, String::class.java)

    private val ingredientsAdapter = moshi.adapter<List<RecipeIngredient>>(ingredientsListType)
    private val stringListAdapter = moshi.adapter<List<String>>(stringListType)

    @TypeConverter
    fun fromIngredientList(value: List<RecipeIngredient>?): String {
        return value?.let { ingredientsAdapter.toJson(it) } ?: "[]"
    }

    @TypeConverter
    fun toIngredientList(value: String?): List<RecipeIngredient> {
        return if (value.isNullOrEmpty()) emptyList() else ingredientsAdapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return value?.let { stringListAdapter.toJson(it) } ?: "[]"
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        return if (value.isNullOrEmpty()) emptyList() else stringListAdapter.fromJson(value) ?: emptyList()
    }
}
