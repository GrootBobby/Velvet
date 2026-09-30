package com.example.data.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

object FirebaseInitializer {
    private const val TAG = "FirebaseInitializer"

    fun ensureInitialized(context: Context): Boolean {
        return try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                // Initialize default fallback options if google-services.json was not packaged yet
                val options = FirebaseOptions.Builder()
                    .setApplicationId("com.aistudio.velvetcocktail.vxqk")
                    .setProjectId("velvetcocktail-aistudio")
                    .setApiKey("AIzaSyVelvetCocktailStudioPlaceholderKey")
                    .build()
                FirebaseApp.initializeApp(context.applicationContext, options)
                Log.d(TAG, "FirebaseApp initialized with fallback options")
            }
            true
        } catch (e: Exception) {
            Log.w(TAG, "FirebaseApp initialization: ${e.message}")
            false
        }
    }
}
