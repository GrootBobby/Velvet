# Velvet & Shake — Speakeasy Club & Mixology AI

An immersive native Android application built with Jetpack Compose following the **Dark Mode Velvet** design system. The app transforms smartphone mixology with an interactive home bar inventory, Gemini-powered cocktail substitution and music pairing, a curated cocktail & mocktail database with measurement converters, a personal bartender profile with leveling/photo gallery, and a high-stakes speakeasy party game (*Undercover: Nocturne*).

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The architectural direction and user experience parameters have been aligned with the user's responses:

- **Confirmed Decision 1 (Music Platform Integration)**: Cocktail music pairings support a multi-platform selector (Spotify, Deezer, and YouTube) powered by Gemini prompts customized for modern youth tastes (Rap FR/US, Electro, Pop, R&B, Afrobeats) with classic jazz/soul reserved for retro-themed drinks.
- **Confirmed Decision 2 (Measurement Conversions)**: Centilitres (cl) are the primary unit of measure, with instantaneous dynamic toggles to traditional home measures (cuillères à soupe/café, shooters, verres).
- **Confirmed Decision 3 (Undercover Party Debate Timer)**: The secret identity party game features a configurable round discussion timer (slider/stepper from 30s to 180s, defaulting to 60s per turn).
- **Gemini API Key Placement**: Injected securely via `BuildConfig.GEMINI_API_KEY` configured through `.env` and `.env.example`, strictly preventing hardcoded secrets.

---

## 1. Overview & Core Concept

### What It Does
- **Mon Bar Privé & Smart Shopping List**: An interactive inventory where users check off what spirits, mixers, and garnishes they own. The app dynamically filters recipes that can be mixed immediately and computes the *Smart Shopping Recommendation* (e.g. "Buy limes to unlock 12 cocktails").
- **Gemini Mixology AI**:
  - *Substitution Engine*: Suggests intelligent home substitutes or hacks when an ingredient is missing (e.g. homemade simple syrup, balsamic reduction, alternative citrus).
  - *Vibe & Playlist Matcher*: Generates curated music search links on Spotify, Deezer, and YouTube reflecting the drink's sensory profile.
  - *Undercover Theme Weaver*: Generates cohesive secret word pairs for the Undercover social deduction game matching user-defined party themes.
- **Cocktail & Mocktail Directory**: Rich database of classic, signature, and mocktail/detox recipes with step-by-step instructions, shaker timers, and unit converters.
- **Bartender Profile & XP**: Level progression (e.g. "Maître Shaker", "Alchimiste"), local photo gallery for drink creations, and custom recipe creation form.
- **Undercover: Nocturne**: Turn-based pass-the-phone game with role distribution (Civils, Undercover, Mr. White), hold-to-reveal biometric card, and debate timer.

### Target Audience & Persona
Young adults and social cocktail enthusiasts (aged 18–30) hosting or attending parties, who appreciate high-end speakeasy aesthetics, craft drinks, and interactive party experiences without tedious clutter.

---

## 2. User Experience & Visual Design

### Key User Flows
1. **My Bar & Instant Filtering**: Open the app -> view bar stock divided into Spirits, Mixers, and Syrups -> check owned bottles -> see instant recipe match counter ("14 Cocktails ready to shake") -> see the highlight recommendation card ("Buy fresh lime to unlock 12 cocktails").
2. **Cocktail Detail & Interactive Mixology**: Tap a cocktail -> enjoy full-bleed moody photography with neon ambient glow -> toggle units between `cl` and `cuillères/verres` -> tap "Trouver un substitut (IA)" for missing ingredient ideas -> tap "Lancer la Playlist" to open Spotify/Deezer/YouTube -> start shaking with step-by-step instructions.
3. **Undercover Party Mode**: Select number of players (3 to 12) -> adjust role distribution -> pick or generate a party theme -> start game -> pass phone between players -> press and hold fingerprint area to reveal secret word -> start the debate timer.
4. **Profile & Custom Recipe Creation**: Track XP points earned by shaking drinks -> view photo gallery of user creations -> tap "+ Ajouter ma propre recette" to craft custom cocktails.

### Visual Identity & Theme: "Dark Mode Velvet"
- **Canvas & Surfaces**: Onyx canvas (`#121317`), Slate Obsidian (`#1a1b20`), Midnight Charcoal (`#1f1f24`, `#292a2e`).
- **Luminescent Accents**: Electric Magenta (`#fbabff`, `#e14ef6`), Velvet Violet (`#e0b6ff`, `#6d11ad`), Warm Amber (`#ffb95f`, `#ca8100`).
- **Typography**: Display headings in **Syne** (artistic, luxury speakeasy character), body and functional labels in **Plus Jakarta Sans** (clean, high-legibility geometric sans).
- **Tactile Styling**: Glassmorphic frosted containers with 1px translucent borders, soft neon underglows, pill-shaped tags, and haptic-friendly 48dp+ interactive targets.

---

## 3. Key Product Decisions & Trade-Offs

- **Architecture (MVI + Repository Pattern)**:
  - *Chosen Approach*: Unidirectional Data Flow with MVI (Model-View-Intent). ViewModels expose immutable `StateFlow<UiState>` and process explicit user `Intent`s.
  - *Why*: Guarantees predictable state transitions across complex asynchronous flows (Gemini API generation, Room database emissions, timer updates).
- **Data Persistence (Room Local Database)**:
  - *Chosen Approach*: Android Room DB with SQLite for local persistence of cocktails, user inventory checkmarks, favorite recipes, custom user cocktails, and user XP/badges.
  - *Why*: Instant offline availability, reactive `Flow` updates, zero network latency for home bar operations.
- **Direct Gemini REST Service with Retrofit**:
  - *Chosen Approach*: Direct REST calls using `gemini-3.5-flash` with structured prompts, 60s OkHttp timeouts, and `BuildConfig.GEMINI_API_KEY`.
  - *Why*: High speed, zero mandatory cloud console configuration steps for the user, and robust structured outputs for substitutions, playlists, and game words.

---

## 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────┐
│                   JETPACK COMPOSE UI                   │
│  BarScreen  │  DetailScreen  │  UndercoverScreen  │ ...│
└───────────────────────────▲────────────────────────────┘
                            │ (StateFlow / Events)
┌───────────────────────────┴────────────────────────────┐
│                    MVI VIEWMODELS                      │
│     BarViewModel   │   DetailViewModel   │  GameVM     │
└─────────────▲──────────────────────────▲───────────────┘
              │                          │
┌─────────────┴───────────────┐ ┌────────┴───────────────┐
│       ROOM REPOSITORY       │ │   GEMINI AI REPOSITORY │
│  CocktailDao / AppDatabase  │ │ Retrofit / GeminiApi   │
│  (Cocktails, Inventory, XP) │ │ (Substitutes, Playlists)│
└─────────────────────────────┘ └────────────────────────┘
```

### Entity & State Model
- **`CocktailEntity`**: Id, name, subtitle, category (Classic, Speakeasy, Mocktail), alcoholVolume, prepTime, difficulty, flavorProfile, glassType, ingredientsJson, instructionsJson, isFavorite, isCustom, userPhotoUri.
- **`IngredientEntity`**: Id, name, category (Spirit, Mixer, Garnish), isOwned, volumeDescription.
- **`UserProfileEntity`**: Level, currentXp, cocktailsShakenCount.
- **`UndercoverGameState`**: Players list, activePlayerIndex, currentPhase (Config, PassPhone, Debate, Voting), roundTimeRemaining, secretTheme, civilWord, undercoverWord.

### State Transitions & Handlers
- **Bar Screen**: Checks toggle inventory -> repository persists state -> DAO emits updated owned ingredients -> reactive recalculation of unlockable cocktails and top missing ingredient.
- **AI Substitution Sheet**: User clicks missing ingredient -> triggers `GeminiRepository.suggestSubstitutions()` -> returns mixologist advice and pantry alternatives.
- **Music Accord**: Generates deep links for Spotify (`spotify:search:...`), Deezer (`deezer://...`), and YouTube (`https://youtube.com/results?...`).
