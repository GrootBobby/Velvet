package com.example.data.firebase

import android.content.Context
import android.util.Log
import com.example.data.local.UserPreferencesRepository
import com.example.data.local.UserProgress
import com.example.data.model.Cocktail
import com.example.data.model.InventoryIngredient
import com.example.data.repository.CocktailRepository
import com.example.data.repository.InventoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

data class UserCloudProfile(
    val uid: String = "",
    val email: String? = null,
    val userName: String = "",
    val level: Int = 1,
    val totalXp: Int = 0,
    val favoriteCocktailIds: List<Long> = emptyList(),
    val ownedIngredients: List<String> = emptyList(),
    val friendCode: String = "",
    val friendsUids: List<String> = emptyList(),
    val lastSyncedTimestamp: Long = System.currentTimeMillis()
)

data class FriendLeaderboardEntry(
    val uid: String,
    val userName: String,
    val level: Int,
    val totalXp: Int,
    val friendCode: String,
    val favoriteCocktailIds: List<Long> = emptyList(),
    val favoriteCocktailNames: List<String> = emptyList(),
    val isCurrentUser: Boolean = false
)

class CloudSyncRepository(
    private val context: Context,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val cocktailRepository: CocktailRepository,
    private val inventoryRepository: InventoryRepository
) {
    private val TAG = "CloudSyncRepository"

    private val firestore: FirebaseFirestore by lazy {
        FirebaseInitializer.ensureInitialized(context)
        FirebaseFirestore.getInstance()
    }

    companion object {
        fun generateFriendCode(userName: String, uid: String): String {
            val cleanName = userName.trim().filter { it.isLetterOrDigit() }.uppercase()
            val base = if (cleanName.isNotBlank()) cleanName.take(6) else "VELVET"
            val hashPart = (uid.hashCode().let { if (it < 0) -it else it } % 9000 + 1000)
            return "$base#$hashPart"
        }
    }

    suspend fun syncUserData(
        uid: String,
        email: String?,
        localProgress: UserProgress,
        localFavorites: List<Cocktail>,
        localInventory: List<InventoryIngredient>
    ): Result<UserCloudProfile> {
        return try {
            FirebaseInitializer.ensureInitialized(context)
            val docRef = firestore.collection("users").document(uid)
            val snapshot = docRef.get().await()

            val cloudProfile = if (snapshot.exists()) {
                val data = snapshot.data ?: emptyMap<String, Any>()
                @Suppress("UNCHECKED_CAST")
                val favIds = (data["favoriteCocktailIds"] as? List<*>)?.mapNotNull {
                    when (it) {
                        is Long -> it
                        is Number -> it.toLong()
                        is String -> it.toLongOrNull()
                        else -> null
                    }
                } ?: emptyList()

                @Suppress("UNCHECKED_CAST")
                val ingredients = (data["ownedIngredients"] as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()

                @Suppress("UNCHECKED_CAST")
                val friends = (data["friendsUids"] as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()

                UserCloudProfile(
                    uid = uid,
                    email = data["email"] as? String ?: email,
                    userName = data["userName"] as? String ?: "",
                    level = (data["level"] as? Number)?.toInt() ?: 1,
                    totalXp = (data["totalXp"] as? Number)?.toInt() ?: 0,
                    favoriteCocktailIds = favIds,
                    ownedIngredients = ingredients,
                    friendCode = data["friendCode"] as? String ?: "",
                    friendsUids = friends,
                    lastSyncedTimestamp = (data["lastSyncedTimestamp"] as? Number)?.toLong() ?: System.currentTimeMillis()
                )
            } else {
                null
            }

            // Intelligent Fusion: merge local + cloud without losing any progress
            val localFavoriteIds = localFavorites.map { it.id }
            val localOwnedNames = localInventory.filter { it.isOwned }.map { it.name.trim() }

            val mergedXp = if (cloudProfile != null) maxOf(localProgress.totalXp, cloudProfile.totalXp) else localProgress.totalXp
            val mergedUserName = when {
                localProgress.userName.isNotBlank() -> localProgress.userName
                cloudProfile != null && cloudProfile.userName.isNotBlank() -> cloudProfile.userName
                else -> ""
            }
            val mergedFavoriteIds = (localFavoriteIds + (cloudProfile?.favoriteCocktailIds ?: emptyList())).distinct()
            val mergedOwnedIngredients = (localOwnedNames + (cloudProfile?.ownedIngredients ?: emptyList())).distinct()
            val calculatedLevel = UserPreferencesRepository.calculateProgress(mergedUserName, true, mergedXp).level

            val finalFriendCode = if (!cloudProfile?.friendCode.isNullOrBlank()) {
                cloudProfile!!.friendCode
            } else {
                generateFriendCode(mergedUserName, uid)
            }
            val finalFriendsUids = cloudProfile?.friendsUids ?: emptyList()

            val mergedProfile = UserCloudProfile(
                uid = uid,
                email = email ?: cloudProfile?.email,
                userName = mergedUserName,
                level = calculatedLevel,
                totalXp = mergedXp,
                favoriteCocktailIds = mergedFavoriteIds,
                ownedIngredients = mergedOwnedIngredients,
                friendCode = finalFriendCode,
                friendsUids = finalFriendsUids,
                lastSyncedTimestamp = System.currentTimeMillis()
            )

            // 1. Write merged data back to Firestore collection `users`
            val docData = hashMapOf(
                "uid" to mergedProfile.uid,
                "email" to (mergedProfile.email ?: ""),
                "userName" to mergedProfile.userName,
                "level" to mergedProfile.level,
                "totalXp" to mergedProfile.totalXp,
                "favoriteCocktailIds" to mergedProfile.favoriteCocktailIds,
                "ownedIngredients" to mergedProfile.ownedIngredients,
                "friendCode" to mergedProfile.friendCode,
                "friendsUids" to mergedProfile.friendsUids,
                "lastSyncedTimestamp" to mergedProfile.lastSyncedTimestamp
            )
            docRef.set(docData, SetOptions.merge()).await()

            // 2. Synchronize merged data into local Room database & preferences
            userPreferencesRepository.updateFromCloud(mergedProfile.userName, mergedProfile.totalXp)
            if (mergedFavoriteIds.isNotEmpty()) {
                cocktailRepository.markFavorites(mergedFavoriteIds)
            }
            if (mergedOwnedIngredients.isNotEmpty()) {
                inventoryRepository.markIngredientsAsOwned(mergedOwnedIngredients.toSet())
            }

            Log.d(TAG, "Cloud sync successful for user $uid with friend code $finalFriendCode")
            Result.success(mergedProfile)
        } catch (e: Exception) {
            Log.w(TAG, "Cloud sync error: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun addFriendByCode(currentUid: String, friendCodeInput: String): Result<FriendLeaderboardEntry> {
        return try {
            FirebaseInitializer.ensureInitialized(context)
            val code = friendCodeInput.trim().uppercase()
            if (code.isBlank()) {
                return Result.failure(IllegalArgumentException("Veuillez saisir un code ami valide."))
            }

            val querySnapshot = firestore.collection("users")
                .whereEqualTo("friendCode", code)
                .limit(1)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                return Result.failure(IllegalArgumentException("Aucun utilisateur trouvé avec le code ami '$code'."))
            }

            val friendDoc = querySnapshot.documents.first()
            val friendUid = friendDoc.id

            if (friendUid == currentUid) {
                return Result.failure(IllegalArgumentException("Vous ne pouvez pas vous ajouter vous-même en ami !"))
            }

            // Retrieve current user document to check current friends
            val currentUserDoc = firestore.collection("users").document(currentUid).get().await()
            val currentFriends = (currentUserDoc.get("friendsUids") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()

            if (currentFriends.contains(friendUid)) {
                return Result.failure(IllegalArgumentException("Ce barman est déjà dans votre liste d'amis !"))
            }

            // Update current user's friends list
            val updatedFriends = (currentFriends + friendUid).distinct()
            firestore.collection("users").document(currentUid)
                .set(mapOf("friendsUids" to updatedFriends), SetOptions.merge())
                .await()

            // Also reciprocity: add current user to friend's list
            val friendExistingFriends = (friendDoc.get("friendsUids") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()
            if (!friendExistingFriends.contains(currentUid)) {
                firestore.collection("users").document(friendUid)
                    .set(mapOf("friendsUids" to (friendExistingFriends + currentUid).distinct()), SetOptions.merge())
                    .await()
            }

            val favIds = (friendDoc.get("favoriteCocktailIds") as? List<*>)?.mapNotNull {
                when (it) {
                    is Long -> it
                    is Number -> it.toLong()
                    is String -> it.toLongOrNull()
                    else -> null
                }
            } ?: emptyList()

            val favNames = cocktailRepository.getCocktailNamesByIds(favIds)

            val entry = FriendLeaderboardEntry(
                uid = friendUid,
                userName = friendDoc.getString("userName") ?: "Ami",
                level = (friendDoc.get("level") as? Number)?.toInt() ?: 1,
                totalXp = (friendDoc.get("totalXp") as? Number)?.toInt() ?: 0,
                friendCode = friendDoc.getString("friendCode") ?: code,
                favoriteCocktailIds = favIds,
                favoriteCocktailNames = favNames.take(3),
                isCurrentUser = false
            )

            Result.success(entry)
        } catch (e: Exception) {
            Log.w(TAG, "Error adding friend: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getFriendsLeaderboard(
        currentUid: String,
        localProgress: UserProgress? = null
    ): Result<List<FriendLeaderboardEntry>> {
        return try {
            FirebaseInitializer.ensureInitialized(context)
            val list = mutableListOf<FriendLeaderboardEntry>()

            val currentUserDoc = firestore.collection("users").document(currentUid).get().await()

            val (myUserName, myLevel, myXp, myCode, friendsUids, myFavIds) = if (currentUserDoc.exists()) {
                val data = currentUserDoc.data ?: emptyMap<String, Any>()
                val name = data["userName"] as? String ?: localProgress?.userName ?: "Moi"
                val xp = (data["totalXp"] as? Number)?.toInt() ?: localProgress?.totalXp ?: 0
                val lvl = (data["level"] as? Number)?.toInt() ?: localProgress?.level ?: 1
                val code = data["friendCode"] as? String ?: generateFriendCode(name, currentUid)
                @Suppress("UNCHECKED_CAST")
                val friends = (data["friendsUids"] as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()
                @Suppress("UNCHECKED_CAST")
                val favs = (data["favoriteCocktailIds"] as? List<*>)?.mapNotNull {
                    when (it) {
                        is Long -> it
                        is Number -> it.toLong()
                        is String -> it.toLongOrNull()
                        else -> null
                    }
                } ?: emptyList()
                Tuple6(name, lvl, xp, code, friends, favs)
            } else {
                val name = localProgress?.userName?.ifBlank { "Moi" } ?: "Moi"
                val xp = localProgress?.totalXp ?: 0
                val lvl = localProgress?.level ?: 1
                val code = generateFriendCode(name, currentUid)
                Tuple6(name, lvl, xp, code, emptyList<String>(), emptyList<Long>())
            }

            val myFavNames = cocktailRepository.getCocktailNamesByIds(myFavIds)

            // 1. Add current user
            list.add(
                FriendLeaderboardEntry(
                    uid = currentUid,
                    userName = myUserName,
                    level = myLevel,
                    totalXp = myXp,
                    friendCode = myCode,
                    favoriteCocktailIds = myFavIds,
                    favoriteCocktailNames = myFavNames.take(3),
                    isCurrentUser = true
                )
            )

            // 2. Add friends
            for (fUid in friendsUids) {
                if (fUid == currentUid) continue
                try {
                    val fDoc = firestore.collection("users").document(fUid).get().await()
                    if (fDoc.exists()) {
                        val fData = fDoc.data ?: emptyMap<String, Any>()
                        val fName = fData["userName"] as? String ?: "Ami"
                        val fXp = (fData["totalXp"] as? Number)?.toInt() ?: 0
                        val fLvl = (fData["level"] as? Number)?.toInt() ?: 1
                        val fCode = fData["friendCode"] as? String ?: ""
                        @Suppress("UNCHECKED_CAST")
                        val fFavs = (fData["favoriteCocktailIds"] as? List<*>)?.mapNotNull {
                            when (it) {
                                is Long -> it
                                is Number -> it.toLong()
                                is String -> it.toLongOrNull()
                                else -> null
                            }
                        } ?: emptyList()

                        val fFavNames = cocktailRepository.getCocktailNamesByIds(fFavs)

                        list.add(
                            FriendLeaderboardEntry(
                                uid = fUid,
                                userName = fName,
                                level = fLvl,
                                totalXp = fXp,
                                friendCode = fCode,
                                favoriteCocktailIds = fFavs,
                                favoriteCocktailNames = fFavNames.take(3),
                                isCurrentUser = false
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to load friend $fUid", e)
                }
            }

            // 3. Sort by totalXp descending
            list.sortByDescending { it.totalXp }

            Result.success(list)
        } catch (e: Exception) {
            Log.w(TAG, "Error fetching friends leaderboard: ${e.message}")
            Result.failure(e)
        }
    }
}

private data class Tuple6<A, B, C, D, E, F>(
    val a: A, val b: B, val c: C, val d: D, val e: E, val f: F
)
