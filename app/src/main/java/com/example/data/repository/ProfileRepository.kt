package com.example.data.repository

import com.example.data.local.ProfileDao
import com.example.data.model.UserCocktailPhoto
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {

    val userProfile: Flow<UserProfile?> = profileDao.getUserProfile()
    val userPhotos: Flow<List<UserCocktailPhoto>> = profileDao.getAllPhotos()

    suspend fun addExperience(xp: Int) {
        profileDao.addXp(xp)
    }

    suspend fun savePhoto(cocktailName: String, photoUri: String, notes: String) {
        profileDao.insertPhoto(
            UserCocktailPhoto(
                cocktailName = cocktailName,
                photoUri = photoUri,
                notes = notes
            )
        )
        // Shaking / photographing a cocktail rewards XP!
        profileDao.addXp(120)
    }

    suspend fun ensureProfile(defaultProfile: UserProfile = UserProfile()) {
        profileDao.insertProfile(defaultProfile)
    }
}
