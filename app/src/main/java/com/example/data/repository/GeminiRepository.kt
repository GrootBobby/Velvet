package com.example.data.repository

import com.example.BuildConfig
import com.example.data.remote.GeminiContent
import com.example.data.remote.GeminiGenerationConfig
import com.example.data.remote.GeminiPart
import com.example.data.remote.GeminiRequest
import com.example.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

data class MusicPairing(
    val title: String,
    val description: String,
    val spotifyQueryUrl: String,
    val deezerQueryUrl: String,
    val youtubeQueryUrl: String
)

class GeminiRepository {

    private val apiService = RetrofitClient.geminiService

    /**
     * 1. IA DE SUBSTITUTION
     * Suggère un substitut réaliste, trouvable dans une cuisine classique.
     */
    suspend fun getIngredientSubstitution(
        cocktailName: String,
        missingIngredient: String
    ): Result<String> = withContext(Dispatchers.IO) {
        val prompt = "Tu es un mixologue expert. Trouve un substitut réaliste, trouvable dans une cuisine classique, pour remplacer l'ingrédient $missingIngredient. Reste bref, donne juste l'alternative et le dosage, sans aucune phrase d'introduction ni absurdité."

        try {
            val responseText = executePrompt(prompt)
            if (responseText.isNotBlank()) {
                Result.success(responseText.trim())
            } else {
                Result.success(getDefaultSubstitutionFallback(missingIngredient))
            }
        } catch (e: Exception) {
            // Intelligent fallback for offline / mock testing
            Result.success(getDefaultSubstitutionFallback(missingIngredient))
        }
    }

    /**
     * 2. L'ACCORD MUSICAL & LA VIBE
     * Génère une ambiance musicale adaptée à l'audience jeune (18-30 ans : Rap FR/US, Pop, Electro, R&B, Afrobeats).
     */
    suspend fun getMusicAccord(
        cocktailName: String,
        vibeTag: String
    ): Result<MusicPairing> = withContext(Dispatchers.IO) {
        val prompt = """
            En tant que DA musical d'un speakeasy branché pour un public jeune (18-30 ans),
            suggère un titre de playlist et un mot-clé de recherche pour le cocktail "$cocktailName" (Vibe: $vibeTag).
            Privilégie les sons actuels (Deep House, Rap FR/US chill, Afrobeats, Electro, Pop moderne).
            Réponds uniquement sous le format :
            TITRE: [Nom stylé de la playlist]
            RECHERCHE: [Mots-clés de recherche musicale précis]
            DESCRIPTION: [Court slogan d'une ligne sur l'ambiance]
        """.trimIndent()

        try {
            val raw = executePrompt(prompt)
            var title = "Speakeasy Vibe Session"
            var searchQuery = "$cocktailName deep house speakeasy"
            var desc = "Immergez votre salon dans une ambiance sonore feutrée"

            raw.lines().forEach { line ->
                when {
                    line.startsWith("TITRE:", ignoreCase = true) ->
                        title = line.substringAfter(":").trim()
                    line.startsWith("RECHERCHE:", ignoreCase = true) ->
                        searchQuery = line.substringAfter(":").trim()
                    line.startsWith("DESCRIPTION:", ignoreCase = true) ->
                        desc = line.substringAfter(":").trim()
                }
            }

            val encodedQuery = URLEncoder.encode(searchQuery, "UTF-8")
            val pairing = MusicPairing(
                title = title,
                description = desc,
                spotifyQueryUrl = "https://open.spotify.com/search/$encodedQuery",
                deezerQueryUrl = "https://www.deezer.com/search/$encodedQuery",
                youtubeQueryUrl = "https://www.youtube.com/results?search_query=$encodedQuery"
            )
            Result.success(pairing)
        } catch (e: Exception) {
            val encodedQuery = URLEncoder.encode("$cocktailName chill speakeasy vibe", "UTF-8")
            Result.success(
                MusicPairing(
                    title = "Speakeasy Velvet Session",
                    description = "Immergez votre lounge dans une onde sonore feutrée",
                    spotifyQueryUrl = "https://open.spotify.com/search/$encodedQuery",
                    deezerQueryUrl = "https://www.deezer.com/search/$encodedQuery",
                    youtubeQueryUrl = "https://www.youtube.com/results?search_query=$encodedQuery"
                )
            )
        }
    }

    /**
     * 3. JEU UNDERCOVER : GÉNÉRATION IA DES MOTS
     * Génère une paire de mots secrets en lien direct avec le thème de soirée saisi.
     */
    suspend fun generateUndercoverWords(
        theme: String
    ): Result<Pair<String, String>> = withContext(Dispatchers.IO) {
        val prompt = """
            Tu es l'arbitre du jeu social "Undercover" pour une soirée festive autour des cocktails et du speakeasy.
            Le thème de la soirée est : "$theme".
            Génère une paire de 2 mots en français très proches et subtils (un pour les Civils, un pour l'Infiltré).
            Exemple : CIVILS: ABSINTHE / INFILTRÉ: CHARTREUSE
            Réponds UNIQUEMENT sous ce format strict :
            CIVILS: [Mot 1 en 1 seul mot majuscule]
            INFILTRE: [Mot 2 en 1 seul mot majuscule]
        """.trimIndent()

        try {
            val responseText = executePrompt(prompt)
            var civilWord = ""
            var undercoverWord = ""

            responseText.lines().forEach { line ->
                if (line.startsWith("CIVILS:", ignoreCase = true)) {
                    civilWord = line.substringAfter(":").trim().uppercase()
                } else if (line.startsWith("INFILTRE:", ignoreCase = true) || line.startsWith("INFILTRÉ:", ignoreCase = true)) {
                    undercoverWord = line.substringAfter(":").trim().uppercase()
                }
            }

            if (civilWord.isNotBlank() && undercoverWord.isNotBlank()) {
                Result.success(Pair(civilWord, undercoverWord))
            } else {
                Result.success(getFallbackWords(theme))
            }
        } catch (e: Exception) {
            Result.success(getFallbackWords(theme))
        }
    }

    private suspend fun executePrompt(prompt: String): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            throw IllegalStateException("API key not configured in secrets")
        }

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(GeminiPart(text = prompt))
                )
            ),
            generationConfig = GeminiGenerationConfig(
                temperature = 0.7f,
                maxOutputTokens = 600
            )
        )

        val response = apiService.generateContent(apiKey = apiKey, request = request)
        return response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: ""
    }

    private fun getDefaultSubstitutionFallback(ingredient: String): String {
        val lower = ingredient.lowercase()
        return when {
            lower.contains("mûre") || lower.contains("cassis") ->
                "Crème de cassis ou coulis de fruits rouges : 1.5 cl."
            lower.contains("citron vert") || lower.contains("lime") ->
                "Jus de citron jaune : même dosage (2.5 cl) avec un zeste."
            lower.contains("sucre") || lower.contains("sirop") ->
                "1 cuillère à café de miel liquide ou 1 cuillère à café de sucre dissous dans 1 cl d'eau tiède."
            lower.contains("ginger beer") ->
                "Limonade ou eau gazeuse avec une tranche de gingembre frais écrasé : 10 cl."
            lower.contains("tonic") ->
                "Eau gazeuse avec un trait de jus de citron ou pamplemousse : 10 cl."
            lower.contains("bourbon") || lower.contains("whiskey") ->
                "Rhum ambré ou Cognac : même dosage (5 cl)."
            lower.contains("vodka") ->
                "Gin ou Tequila blanche : même dosage (4 cl)."
            lower.contains("cointreau") || lower.contains("triple sec") ->
                "Jus d'orange frais réduit avec un zeste et une pincée de sucre : 2 cl."
            else ->
                "Équivalent aromatique le plus proche de votre placard : même dosage."
        }
    }

    private fun getFallbackWords(theme: String): Pair<String, String> {
        val lower = theme.lowercase()
        return when {
            lower.contains("interdit") || lower.contains("prohib") -> Pair("ABSINTHE", "CHARTREUSE")
            lower.contains("braquage") || lower.contains("vol") -> Pair("COFFRE", "CASIER")
            lower.contains("espion") || lower.contains("agent") -> Pair("MICRO", "RADAR")
            lower.contains("fruit") || lower.contains("exotique") -> Pair("MARACUJA", "MANGUE")
            lower.contains("glace") || lower.contains("froid") -> Pair("PILÉE", "CRISTAL")
            else -> Pair("MOJITO", "DAIQUIRI")
        }
    }
}
