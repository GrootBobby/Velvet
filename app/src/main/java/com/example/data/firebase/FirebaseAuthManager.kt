package com.example.data.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

data class AuthUserState(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val isAnonymous: Boolean = false
)

class FirebaseAuthManager(private val context: Context) {

    private val auth: FirebaseAuth by lazy {
        FirebaseInitializer.ensureInitialized(context)
        FirebaseAuth.getInstance()
    }

    private val _currentUser = MutableStateFlow<AuthUserState?>(null)
    val currentUser: StateFlow<AuthUserState?> = _currentUser.asStateFlow()

    init {
        try {
            FirebaseInitializer.ensureInitialized(context)
            auth.addAuthStateListener { fbAuth ->
                val user = fbAuth.currentUser
                _currentUser.value = user?.toAuthUserState()
            }
        } catch (e: Exception) {
            Log.w("FirebaseAuthManager", "Auth state listener initialization: ${e.message}")
        }
    }

    fun getCurrentUser(): AuthUserState? {
        return try {
            auth.currentUser?.toAuthUserState()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signInWithEmail(email: String, password: String): Result<AuthUserState> {
        return try {
            val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
            val user = result.user?.toAuthUserState()
                ?: return Result.failure(Exception("Utilisateur non trouvé après connexion"))
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUpWithEmail(email: String, password: String, pseudo: String): Result<AuthUserState> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
            val fbUser = result.user ?: return Result.failure(Exception("Échec de création du compte"))

            if (pseudo.isNotBlank()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(pseudo.trim())
                    .build()
                fbUser.updateProfile(profileUpdates).await()
            }

            val user = fbUser.toAuthUserState()
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithGoogle(activity: Activity, webClientId: String = ""): Result<AuthUserState> {
        return try {
            val credentialManager = CredentialManager.create(activity)
            
            // Build Google ID option
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(if (webClientId.isNotBlank()) webClientId else "dummy-web-client-id.apps.googleusercontent.com")
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = credentialManager.getCredential(activity, request)
            val credential = response.credential

            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(authCredential).await()
                val user = authResult.user?.toAuthUserState()
                    ?: return Result.failure(Exception("Utilisateur Google non trouvé"))
                _currentUser.value = user
                Result.success(user)
            } else {
                Result.failure(Exception("Type de justificatif inattendu"))
            }
        } catch (e: GetCredentialException) {
            Result.failure(Exception("Connexion Google annulée ou indisponible : ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        try {
            auth.signOut()
            _currentUser.value = null
        } catch (e: Exception) {
            Log.w("FirebaseAuthManager", "Sign out error: ${e.message}")
        }
    }

    private fun FirebaseUser.toAuthUserState(): AuthUserState {
        return AuthUserState(
            uid = uid,
            email = email,
            displayName = displayName,
            isAnonymous = isAnonymous
        )
    }
}
