package com.example.ui.music

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Cocktail
import com.example.ui.theme.VelvetGlassBorder
import com.example.ui.theme.VelvetOnPrimary
import com.example.ui.theme.VelvetOnSurfaceVariant
import com.example.ui.theme.VelvetPrimary
import com.example.ui.theme.VelvetSecondary
import com.example.ui.theme.VelvetSurfaceHigh
import com.example.ui.theme.VelvetSurfaceHighest
import com.example.ui.theme.VelvetSurfaceLowest
import com.example.ui.theme.VelvetTertiary
import java.net.URLEncoder

data class MusicAmbience(
    val styleName: String,
    val subtitle: String,
    val iconEmoji: String,
    val searchKeyword: String,
    val accentColor: Color
)

object MusicAmbienceProvider {
    val SPEAKEASY_JAZZ = MusicAmbience(
        styleName = "Midnight Speakeasy & Jazz",
        subtitle = "Cuivres chaleureux, contrebasse feutrée & blues nocturne",
        iconEmoji = "🎷",
        searchKeyword = "Midnight Speakeasy Jazz",
        accentColor = Color(0xFFD4AF37)
    )

    val URBAN_CHILL = MusicAmbience(
        styleName = "Urban Chill & French Rap",
        subtitle = "Flows mélodiques, trap douce & vibes nocturnes",
        iconEmoji = "🎧",
        searchKeyword = "Urban Chill French Rap",
        accentColor = Color(0xFF8A2BE2)
    )

    val LATIN_BEATS = MusicAmbience(
        styleName = "Latin Beats & Sunset Lounge",
        subtitle = "Rythmes salsa, bossa nova, reggaeton & soleil couchant",
        iconEmoji = "🌴",
        searchKeyword = "Latin Beats Sunset Lounge",
        accentColor = Color(0xFFFF6F00)
    )

    val DEEP_HOUSE = MusicAmbience(
        styleName = "Deep House & Rooftop",
        subtitle = "Basses enveloppantes, coucher de soleil & skyline chic",
        iconEmoji = "🍸",
        searchKeyword = "Deep House Rooftop",
        accentColor = Color(0xFF00CED1)
    )

    val TROPICAL_AFROBEATS = MusicAmbience(
        styleName = "Tropical & Afrobeats",
        subtitle = "Énergie exotique, percussions solaires & saveurs d'été",
        iconEmoji = "🌺",
        searchKeyword = "Tropical Afrobeats",
        accentColor = Color(0xFFFF1493)
    )

    val CLUBBING_ELECTRO = MusicAmbience(
        styleName = "Clubbing Dance & Electro",
        subtitle = "BPM intenses, synthés incisifs, hits club & soirée étudiante",
        iconEmoji = "⚡",
        searchKeyword = "Clubbing Dance Electro",
        accentColor = Color(0xFF00FF7F)
    )

    val RETRO_SYNTHWAVE = MusicAmbience(
        styleName = "Retro Synthwave & Pop 80s",
        subtitle = "Néons pourpres, boîtes à rythmes rétro & nostalgie 80s",
        iconEmoji = "🕹️",
        searchKeyword = "Retro Synthwave Pop 80s",
        accentColor = Color(0xFFFF007F)
    )

    val LOFI_CHILL = MusicAmbience(
        styleName = "Lo-Fi Hip Hop & Chill",
        subtitle = "Détente totale, crépitement de vinyle & douceur acoustique",
        iconEmoji = "☕",
        searchKeyword = "Lo-Fi Hip Hop Chill",
        accentColor = Color(0xFFBA55D3)
    )

    val ALL_AMBIENCES = listOf(
        SPEAKEASY_JAZZ,
        URBAN_CHILL,
        LATIN_BEATS,
        DEEP_HOUSE,
        TROPICAL_AFROBEATS,
        CLUBBING_ELECTRO,
        RETRO_SYNTHWAVE,
        LOFI_CHILL
    )

    fun getAmbienceForCocktail(cocktail: Cocktail): MusicAmbience {
        val vibe = cocktail.vibeTag.lowercase()
        val name = cocktail.name.lowercase()
        val category = cocktail.category.lowercase()

        return when {
            // Lo-Fi Hip Hop & Chill (Virgin, sans alcool, mocktails, sober, lo-fi)
            name.contains("virgin") || category.contains("sans alcool") || category.contains("mocktail") ||
            vibe.contains("sober") || vibe.contains("lo-fi") || vibe.contains("ambient") || vibe.contains("detox") -> LOFI_CHILL

            // Direct matches based on vibeTag
            vibe.contains("étudiante") || vibe.contains("etudiante") || vibe.contains("student") -> CLUBBING_ELECTRO
            vibe.contains("cuban") || vibe.contains("havana") || vibe.contains("latin") || vibe.contains("cumbia") || vibe.contains("mambo") || vibe.contains("reggaeton") || vibe.contains("mexican") || vibe.contains("bossa nova") -> LATIN_BEATS
            vibe.contains("afro") || vibe.contains("tropical") || vibe.contains("tiki") || vibe.contains("island") || vibe.contains("caribbean") || vibe.contains("reggae") -> TROPICAL_AFROBEATS
            vibe.contains("jazz") || vibe.contains("speakeasy") || vibe.contains("blues") || vibe.contains("swing") || vibe.contains("bourbon") || vibe.contains("noir") -> SPEAKEASY_JAZZ
            vibe.contains("synthwave") || vibe.contains("80s") || vibe.contains("90s") || vibe.contains("rockabilly") -> RETRO_SYNTHWAVE
            vibe.contains("electro") || vibe.contains("clubbing") || vibe.contains("festival") || vibe.contains("edm") || vibe.contains("rave") || vibe.contains("shots") || vibe.contains("dance") -> CLUBBING_ELECTRO
            vibe.contains("urban") || vibe.contains("rap") || vibe.contains("hip hop") || vibe.contains("trap") || vibe.contains("r&b") || vibe.contains("soul") -> URBAN_CHILL
            vibe.contains("deep house") || vibe.contains("house") || vibe.contains("rooftop") || vibe.contains("nu-disco") || vibe.contains("lounge") -> DEEP_HOUSE

            // Fallbacks based on name
            name.contains("mojito") || name.contains("margarita") || name.contains("caipirinha") || name.contains("paloma") || name.contains("cuba libre") -> LATIN_BEATS
            name.contains("mai tai") || name.contains("zombie") || name.contains("piña") || name.contains("tiki") || name.contains("tropical") -> TROPICAL_AFROBEATS
            name.contains("cosmopolitan") || name.contains("long island") || name.contains("kamikaze") || name.contains("b-52") -> CLUBBING_ELECTRO
            name.contains("spritz") || name.contains("gin tonic") || name.contains("mule") || name.contains("collins") -> DEEP_HOUSE
            name.contains("sex on the beach") || name.contains("blue lagoon") || name.contains("sunrise") -> RETRO_SYNTHWAVE
            name.contains("espresso") || name.contains("dark 'n stormy") || name.contains("sidecar") -> URBAN_CHILL
            name.contains("virgin") || category.contains("sans alcool") -> LOFI_CHILL
            name.contains("old fashioned") || name.contains("manhattan") || name.contains("negroni") || name.contains("sazerac") -> SPEAKEASY_JAZZ

            // Fallbacks based on category
            category.contains("speakeasy") -> SPEAKEASY_JAZZ
            category.contains("clubbing") -> CLUBBING_ELECTRO
            category.contains("shooter") -> CLUBBING_ELECTRO
            category.contains("sans alcool") || category.contains("mocktail") -> LOFI_CHILL
            else -> DEEP_HOUSE
        }
    }
}

val Cocktail.ambience: MusicAmbience
    get() = MusicAmbienceProvider.getAmbienceForCocktail(this)

enum class StreamingPlatform(
    val displayName: String,
    val subtitle: String,
    val brandColor: Color,
    val iconEmoji: String,
    val testTag: String
) {
    SPOTIFY(
        displayName = "Spotify",
        subtitle = "Ouvrir la playlist sur Spotify",
        brandColor = Color(0xFF1DB954),
        iconEmoji = "🟢",
        testTag = "streaming_platform_spotify"
    ),
    DEEZER(
        displayName = "Deezer",
        subtitle = "Écouter la playlist sur Deezer",
        brandColor = Color(0xFFA238FF),
        iconEmoji = "🟣",
        testTag = "streaming_platform_deezer"
    ),
    YOUTUBE_MUSIC(
        displayName = "YouTube Music",
        subtitle = "Lancer la playlist sur YouTube Music",
        brandColor = Color(0xFFFF0000),
        iconEmoji = "🔴",
        testTag = "streaming_platform_youtube_music"
    )
}

fun launchStreamingPlatform(context: Context, platform: StreamingPlatform, ambience: MusicAmbience) {
    val encodedStyle = try {
        URLEncoder.encode(ambience.styleName, "UTF-8")
    } catch (_: Exception) {
        ambience.styleName.replace(" ", "%20")
    }

    when (platform) {
        StreamingPlatform.SPOTIFY -> {
            val appUri = Uri.parse("spotify:search:$encodedStyle")
            val webUri = Uri.parse("https://open.spotify.com/search/playlists/$encodedStyle")
            val appIntent = Intent(Intent.ACTION_VIEW, appUri).apply {
                setPackage("com.spotify.music")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(appIntent)
            } catch (_: Exception) {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, webUri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                } catch (_: Exception) {
                    Toast.makeText(context, "Impossible d'ouvrir Spotify", Toast.LENGTH_SHORT).show()
                }
            }
        }
        StreamingPlatform.DEEZER -> {
            val appUri = Uri.parse("deezer://www.deezer.com/search/$encodedStyle")
            val webUri = Uri.parse("https://www.deezer.com/search/$encodedStyle")
            val appIntent = Intent(Intent.ACTION_VIEW, appUri).apply {
                setPackage("deezer.android.app")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(appIntent)
            } catch (_: Exception) {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, webUri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                } catch (_: Exception) {
                    Toast.makeText(context, "Impossible d'ouvrir Deezer", Toast.LENGTH_SHORT).show()
                }
            }
        }
        StreamingPlatform.YOUTUBE_MUSIC -> {
            val queryEncoded = try {
                URLEncoder.encode("${ambience.styleName} playlist", "UTF-8")
            } catch (_: Exception) {
                "${ambience.styleName.replace(" ", "%20")}%20playlist"
            }
            val ytWebUri = Uri.parse("https://music.youtube.com/search?q=$queryEncoded")
            val appIntent = Intent(Intent.ACTION_VIEW, ytWebUri).apply {
                setPackage("com.google.android.apps.youtube.music")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(appIntent)
            } catch (_: Exception) {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, ytWebUri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                } catch (_: Exception) {
                    Toast.makeText(context, "Impossible d'ouvrir YouTube Music", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun MusicPlatformSelectorDialog(
    ambience: MusicAmbience,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VelvetSurfaceHigh,
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(ambience.accentColor.copy(alpha = 0.5f), Color(0xFF1E1528)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = ambience.iconEmoji, fontSize = 20.sp)
                    }

                    Column {
                        Text(
                            text = "Plateforme Musicale",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = ambience.styleName,
                            fontSize = 12.sp,
                            color = ambience.accentColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(VelvetSurfaceHighest)
                        .clickable { onDismiss() }
                        .testTag("streaming_platform_close_icon"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = VelvetOnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Choisissez votre application de streaming pour lancer la playlist :",
                    fontSize = 13.sp,
                    color = VelvetOnSurfaceVariant
                )

                StreamingPlatform.entries.forEach { platform ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, VelvetGlassBorder, RoundedCornerShape(16.dp))
                            .clickable {
                                launchStreamingPlatform(context, platform, ambience)
                                onDismiss()
                            }
                            .testTag(platform.testTag),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = VelvetSurfaceLowest)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(platform.brandColor.copy(alpha = 0.2f))
                                    .border(1.dp, platform.brandColor.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = platform.iconEmoji,
                                    fontSize = 18.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = platform.displayName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = platform.subtitle,
                                    fontSize = 11.sp,
                                    color = VelvetOnSurfaceVariant
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                contentDescription = null,
                                tint = platform.brandColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("streaming_platform_cancel")
            ) {
                Text("Fermer", color = VelvetOnSurfaceVariant)
            }
        }
    )
}
