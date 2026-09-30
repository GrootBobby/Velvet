package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "velvet_user_preferences")

data class UserProgress(
    val userName: String,
    val isOnboardingCompleted: Boolean,
    val totalXp: Int,
    val level: Int,
    val title: String,
    val currentLevelBaseXp: Int,
    val nextLevelTargetXp: Int,
    val progressFraction: Float,
    val nextTitle: String,
    val profilePhotoUri: String? = null
)

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_first_name")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val TOTAL_XP = intPreferencesKey("user_total_xp")
        val PROFILE_PHOTO_URI = stringPreferencesKey("profile_photo_uri")
    }

    val userProgress: Flow<UserProgress> = context.dataStore.data.map { preferences ->
        val userName = preferences[PreferencesKeys.USER_NAME] ?: ""
        val isCompleted = preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
        val totalXp = preferences[PreferencesKeys.TOTAL_XP] ?: 0
        val photoUri = preferences[PreferencesKeys.PROFILE_PHOTO_URI]

        calculateProgress(userName, isCompleted, totalXp, photoUri)
    }

    suspend fun setProfilePhotoUri(uriString: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PROFILE_PHOTO_URI] = uriString
        }
    }

    suspend fun completeOnboarding(firstName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = firstName.trim()
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = true
            // If total XP is 0, award initial welcome XP!
            val currentXp = preferences[PreferencesKeys.TOTAL_XP] ?: 0
            if (currentXp == 0) {
                preferences[PreferencesKeys.TOTAL_XP] = 50 // Welcome bonus
            }
        }
    }

    suspend fun addXp(amount: Int) {
        if (amount <= 0) return
        context.dataStore.edit { preferences ->
            val currentXp = preferences[PreferencesKeys.TOTAL_XP] ?: 0
            preferences[PreferencesKeys.TOTAL_XP] = currentXp + amount
        }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name.trim()
        }
    }

    suspend fun updateFromCloud(cloudUserName: String, cloudXp: Int) {
        context.dataStore.edit { preferences ->
            if (cloudUserName.isNotBlank()) {
                val current = preferences[PreferencesKeys.USER_NAME] ?: ""
                if (current.isBlank()) {
                    preferences[PreferencesKeys.USER_NAME] = cloudUserName.trim()
                }
            }
            val currentXp = preferences[PreferencesKeys.TOTAL_XP] ?: 0
            if (cloudXp > currentXp) {
                preferences[PreferencesKeys.TOTAL_XP] = cloudXp
            }
        }
    }

    companion object {
        fun calculateProgress(
            userName: String,
            isOnboardingCompleted: Boolean,
            totalXp: Int,
            profilePhotoUri: String? = null
        ): UserProgress {
            // Level thresholds and titles
            val levels = listOf(
                LevelThreshold(1, 0, 200, "Apprenti Shaker", "Mixologue Amateur"),
                LevelThreshold(2, 200, 500, "Mixologue Amateur", "Alchimiste Nocturne"),
                LevelThreshold(3, 500, 900, "Alchimiste Nocturne", "Maître Shaker"),
                LevelThreshold(4, 900, 1400, "Maître Shaker", "Mixologue Émérite"),
                LevelThreshold(5, 1400, 2000, "Mixologue Émérite", "Grand Chambellan"),
                LevelThreshold(6, 2000, 2800, "Grand Chambellan", "Légende du Speakeasy"),
                LevelThreshold(7, 2800, 3800, "Légende du Speakeasy", "Grand Maître Alchimiste"),
                LevelThreshold(8, 3800, 5000, "Grand Maître Alchimiste", "Légende Suprême")
            )

            val currentLevel = levels.firstOrNull { totalXp in it.minXp until it.maxXp }
                ?: if (totalXp >= 5000) {
                    LevelThreshold(9, 5000, 10000, "Légende Suprême", "Dieu du Cocktail")
                } else {
                    levels.first()
                }

            val range = currentLevel.maxXp - currentLevel.minXp
            val progressInRange = totalXp - currentLevel.minXp
            val fraction = (progressInRange.toFloat() / range.toFloat()).coerceIn(0f, 1f)

            return UserProgress(
                userName = userName,
                isOnboardingCompleted = isOnboardingCompleted,
                totalXp = totalXp,
                level = currentLevel.level,
                title = currentLevel.title,
                currentLevelBaseXp = currentLevel.minXp,
                nextLevelTargetXp = currentLevel.maxXp,
                progressFraction = fraction,
                nextTitle = currentLevel.nextTitle,
                profilePhotoUri = profilePhotoUri
            )
        }
    }

    private data class LevelThreshold(
        val level: Int,
        val minXp: Int,
        val maxXp: Int,
        val title: String,
        val nextTitle: String
    )
}
