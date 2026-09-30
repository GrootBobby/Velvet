package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "user_profile")
@JsonClass(generateAdapter = true)
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val username: String = "Alexandre V.",
    val handle: String = "@VelvetMixologist",
    val bio: String = "Alchimiste de saveurs nocturnes 🍸",
    val avatarUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuB1wsRGcfEuaRxMqpPLizeiDbk1ojhfDZmU1zboNM7urLnvLkE36yCYVXWNM_zszUs030oedVYiOazT7C8YlJxb0FCfZFbYFAs2b3Op70moN_Hn4_V2KDfGFWYd7OTNPUdSLHfBJQzRX_itjyn0GU49O8_DvMLx6NQEupXeh5VwP4gnjrpp33xCdHJCKCp7OgyhoTAV3c0UGX2k57hT3BGS6bLOcUcQuOhD8TD5iML1ATcSGyIT8E7UhA",
    val level: Int = 7,
    val title: String = "Mixologue Émérite",
    val currentXp: Int = 1420,
    val nextLevelXp: Int = 2000,
    val nextTitle: String = "Maître Shaker",
    val creationsCount: Int = 4,
    val totalLikes: String = "1.2k"
)

@Entity(tableName = "user_photos")
@JsonClass(generateAdapter = true)
data class UserCocktailPhoto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cocktailName: String,
    val photoUri: String,
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
