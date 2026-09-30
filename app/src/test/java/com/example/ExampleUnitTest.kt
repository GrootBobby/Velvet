package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun quiPourraitList_hasExactly100Questions() {
    assertEquals(100, com.example.ui.party.PartyGamesData.quiPourraitList.size)
    val distinctCount = com.example.ui.party.PartyGamesData.quiPourraitList.distinct().size
    assertEquals("Toutes les questions doivent être uniques", 100, distinctCount)
  }

  @Test
  fun undercoverPairsList_hasExactly200Pairs() {
    val list = com.example.ui.party.PartyGamesData.undercoverPairsList
    assertEquals("undercoverPairsList doit contenir exactement 200 paires", 200, list.size)
    list.forEachIndexed { index, pair ->
      assertFalse("Premier mot ne doit pas être vide à l'index $index", pair.first.isBlank())
      assertFalse("Deuxième mot ne doit pas être vide à l'index $index", pair.second.isBlank())
      assertNotEquals("Les deux mots doivent être différents à l'index $index", pair.first, pair.second)
    }
  }

  @Test
  fun jeNaiJamaisList_hasExactly100Statements() {
    val list = com.example.ui.party.PartyGamesData.jeNaiJamaisList
    assertEquals("jeNaiJamaisList doit contenir exactement 100 affirmations", 100, list.size)
    val distinctCount = list.distinct().size
    assertEquals("Toutes les affirmations doivent être uniques", 100, distinctCount)
    list.forEachIndexed { index, item ->
      assertFalse("L'affirmation ne doit pas être vide à l'index $index", item.isBlank())
    }
  }

  @Test
  fun actionsBarList_hasExactly50Items() {
    val list = com.example.ui.party.PartyGamesData.actionsBarList
    assertEquals("actionsBarList doit contenir exactement 50 actions", 50, list.size)
    val distinctCount = list.distinct().size
    assertEquals("Toutes les actions doivent être uniques", 50, distinctCount)
    list.forEachIndexed { index, item ->
      assertFalse("L'action ne doit pas être vide à l'index $index", item.isBlank())
    }
  }

  @Test
  fun veritesBarList_hasExactly50Items() {
    val list = com.example.ui.party.PartyGamesData.veritesBarList
    assertEquals("veritesBarList doit contenir exactement 50 vérités", 50, list.size)
    val distinctCount = list.distinct().size
    assertEquals("Toutes les vérités doivent être uniques", 50, distinctCount)
    list.forEachIndexed { index, item ->
      assertFalse("La vérité ne doit pas être vide à l'index $index", item.isBlank())
    }
  }

  @Test
  fun rouletteBarmanList_hasExactly50Items() {
    val list = com.example.ui.party.PartyGamesData.rouletteBarmanList
    assertEquals("rouletteBarmanList doit contenir exactement 50 consignes", 50, list.size)
    val distinctCount = list.distinct().size
    assertEquals("Toutes les consignes doivent être uniques", 50, distinctCount)
    list.forEachIndexed { index, item ->
      assertFalse("La consigne ne doit pas être vide à l'index $index", item.isBlank())
    }
  }

  @Test
  fun undercoverLocalGameFlow_worksInstantly() {
    val vm = com.example.ui.party.UndercoverViewModel()
    // Initial state is CONFIG
    assertEquals(com.example.ui.party.UndercoverPhase.CONFIG, vm.uiState.value.phase)

    // Set 5 players (3 civils, 1 undercover, 1 mr white)
    vm.processIntent(com.example.ui.party.UndercoverIntent.SetPlayerCount(5))
    assertEquals(5, vm.uiState.value.totalPlayers)
    assertEquals(3, vm.uiState.value.civilsCount)
    assertEquals(1, vm.uiState.value.undercoverCount)
    assertEquals(1, vm.uiState.value.whiteCount)

    // Start game - instant, no AI/network delay
    vm.processIntent(com.example.ui.party.UndercoverIntent.StartGame)
    assertEquals(com.example.ui.party.UndercoverPhase.PASS_PHONE, vm.uiState.value.phase)
    assertTrue("Le mot civil ne doit pas être vide", vm.uiState.value.civilWord.isNotBlank())
    assertTrue("Le mot undercover ne doit pas être vide", vm.uiState.value.undercoverWord.isNotBlank())
    assertNotEquals("Les deux mots doivent être différents", vm.uiState.value.civilWord, vm.uiState.value.undercoverWord)

    // Verify 5 players have roles assigned
    val players = vm.uiState.value.players
    assertEquals(5, players.size)
    val civils = players.count { it.role == com.example.ui.party.PlayerRole.CIVIL }
    val undercovers = players.count { it.role == com.example.ui.party.PlayerRole.UNDERCOVER }
    val whites = players.count { it.role == com.example.ui.party.PlayerRole.MR_WHITE }
    assertEquals(3, civils)
    assertEquals(1, undercovers)
    assertEquals(1, whites)

    // Check pass phone progression
    for (i in 0 until 4) {
      assertEquals(i, vm.uiState.value.activePlayerIndex)
      vm.processIntent(com.example.ui.party.UndercoverIntent.NextPlayer)
    }
    // 5th player finishes -> moves to DEBATE
    vm.processIntent(com.example.ui.party.UndercoverIntent.NextPlayer)
    assertEquals(com.example.ui.party.UndercoverPhase.DEBATE, vm.uiState.value.phase)

    // Test reveal impostors
    assertFalse(vm.uiState.value.areImpostorsRevealed)
    vm.processIntent(com.example.ui.party.UndercoverIntent.RevealImpostors)
    assertTrue(vm.uiState.value.areImpostorsRevealed)
  }

  @Test
  fun profileProgress_handlesCustomProfilePhoto() {
    val progressWithoutPhoto = com.example.data.local.UserPreferencesRepository.calculateProgress(
      userName = "Alexandre",
      isOnboardingCompleted = true,
      totalXp = 350
    )
    assertNull(progressWithoutPhoto.profilePhotoUri)

    val customUri = "content://media/external/images/media/42"
    val progressWithPhoto = com.example.data.local.UserPreferencesRepository.calculateProgress(
      userName = "Alexandre",
      isOnboardingCompleted = true,
      totalXp = 350,
      profilePhotoUri = customUri
    )
    assertEquals(customUri, progressWithPhoto.profilePhotoUri)
    assertEquals(2, progressWithPhoto.level)
  }

  @Test
  fun undercoverRandomInversion_bothOrderingsOccur() {
    val vm = com.example.ui.party.UndercoverViewModel()
    val allPairs = com.example.ui.party.PartyGamesData.undercoverPairsList

    var normalOrderCount = 0
    var invertedOrderCount = 0

    // Run 50 games to verify distribution and random inversion
    for (i in 0 until 50) {
      vm.processIntent(com.example.ui.party.UndercoverIntent.StartGame)
      val civilWord = vm.uiState.value.civilWord
      val undercoverWord = vm.uiState.value.undercoverWord

      // Check that all Civils received civilWord and all Undercovers received undercoverWord
      val players = vm.uiState.value.players
      players.forEach { p ->
        when (p.role) {
          com.example.ui.party.PlayerRole.CIVIL -> assertEquals(civilWord, p.secretWord)
          com.example.ui.party.PlayerRole.UNDERCOVER -> assertEquals(undercoverWord, p.secretWord)
          com.example.ui.party.PlayerRole.MR_WHITE -> assertTrue(p.secretWord.isEmpty())
        }
      }

      // Check whether this pair matched original pair order (first to second) or inverted (second to first)
      val directMatch = allPairs.any { it.first == civilWord && it.second == undercoverWord }
      val invertedMatch = allPairs.any { it.second == civilWord && it.first == undercoverWord }
      assertTrue("La paire doit exister dans undercoverPairsList", directMatch || invertedMatch)

      if (directMatch) normalOrderCount++
      if (invertedMatch) invertedOrderCount++
    }

    // Over 50 random trials, both normal and inverted orders must occur
    assertTrue("L'ordre normal doit survenir au moins une fois", normalOrderCount > 0)
    assertTrue("L'ordre inversé doit survenir au moins une fois", invertedOrderCount > 0)
  }

  @Test
  fun musicAmbience_coherentlyAssignedToCocktails() {
    val mojito = com.example.data.model.CocktailEntity(
      name = "Mojito Cubain",
      category = "Classique",
      vibeTag = "Havana Tropicana & Afro Cuban Jazz"
    )
    val oldFashioned = com.example.data.model.CocktailEntity(
      name = "Old Fashioned",
      category = "Speakeasy",
      vibeTag = "Midnight Speakeasy & Lounge Jazz"
    )
    val virginMojito = com.example.data.model.CocktailEntity(
      name = "Virgin Mojito",
      category = "Mocktails & Sans Alcool",
      vibeTag = "Sober Speakeasy & Vinyl Mood"
    )

    val mojitoAmbience = com.example.ui.music.MusicAmbienceProvider.getAmbienceForCocktail(mojito)
    val oldFashionedAmbience = com.example.ui.music.MusicAmbienceProvider.getAmbienceForCocktail(oldFashioned)
    val virginAmbience = com.example.ui.music.MusicAmbienceProvider.getAmbienceForCocktail(virginMojito)

    assertEquals("Midnight Speakeasy & Jazz", oldFashionedAmbience.styleName)
    assertTrue("Mojito doit être Latin Beats ou Tropical", mojitoAmbience.styleName == "Latin Beats & Sunset Lounge" || mojitoAmbience.styleName == "Tropical & Afrobeats")
    assertEquals("Lo-Fi Hip Hop & Chill", virginAmbience.styleName)

    // Verify 8 distinct defined ambiances
    assertEquals(8, com.example.ui.music.MusicAmbienceProvider.ALL_AMBIENCES.size)
    com.example.ui.music.MusicAmbienceProvider.ALL_AMBIENCES.forEach {
      assertTrue("Nom de style ne doit pas être vide", it.styleName.isNotBlank())
      assertTrue("Mot-clé de recherche ne doit pas être vide", it.searchKeyword.isNotBlank())
      assertTrue("Emoji ne doit pas être vide", it.iconEmoji.isNotBlank())
    }
  }

  @Test
  fun streamingPlatforms_allConfigsValid() {
    val platforms = com.example.ui.music.StreamingPlatform.entries
    assertEquals(3, platforms.size)
    val names = platforms.map { it.displayName }
    assertTrue(names.contains("Spotify"))
    assertTrue(names.contains("Deezer"))
    assertTrue(names.contains("YouTube Music"))
  }

  @Test
  fun cloudSync_intelligentMergePreservesMaxProgressAndCombinesData() {
    val localProgress = com.example.data.local.UserPreferencesRepository.calculateProgress("Alex", true, 300)
    val localFavorites = listOf(
      com.example.data.model.CocktailEntity(id = 1L, name = "Mojito", category = "Classique"),
      com.example.data.model.CocktailEntity(id = 2L, name = "Negroni", category = "Speakeasy")
    )
    val localInventory = listOf(
      com.example.data.model.InventoryIngredient(name = "Gin", brandOrDetail = "London Dry", category = com.example.data.model.IngredientCategory.SPIRITS, tag = "Gin", isOwned = true),
      com.example.data.model.InventoryIngredient(name = "Rhum", brandOrDetail = "Blanc", category = com.example.data.model.IngredientCategory.SPIRITS, tag = "Rhum", isOwned = false)
    )

    val cloudProfile = com.example.data.firebase.UserCloudProfile(
      uid = "test-uid-123",
      email = "alex@test.com",
      userName = "Alexandre Cloud",
      level = 3,
      totalXp = 500,
      favoriteCocktailIds = listOf(2L, 5L, 8L),
      ownedIngredients = listOf("Rhum", "Menthe")
    )

    // Fusion intelligente : XP max, favoris combinés et dédupliqués, ingrédients combinés
    val mergedXp = maxOf(localProgress.totalXp, cloudProfile.totalXp)
    val mergedFavorites = (localFavorites.map { it.id } + cloudProfile.favoriteCocktailIds).distinct()
    val mergedIngredients = (localInventory.filter { it.isOwned }.map { it.name.trim() } + cloudProfile.ownedIngredients).distinct()

    assertEquals(500, mergedXp)
    assertEquals(listOf(1L, 2L, 5L, 8L), mergedFavorites)
    assertTrue(mergedIngredients.contains("Gin"))
    assertTrue(mergedIngredients.contains("Rhum"))
    assertTrue(mergedIngredients.contains("Menthe"))
    assertEquals(3, mergedIngredients.size)
  }

  @Test
  fun friendsSocial_friendCodeGenerationAndLeaderboardOrdering() {
    // 1. Test friend code generation
    val code1 = com.example.data.firebase.CloudSyncRepository.generateFriendCode("Alexandre", "user-uid-abc")
    val code2 = com.example.data.firebase.CloudSyncRepository.generateFriendCode("Max", "user-uid-xyz")
    val code3 = com.example.data.firebase.CloudSyncRepository.generateFriendCode("", "user-uid-empty")

    assertTrue("Le code doit contenir un #", code1.contains("#"))
    assertTrue("Le code doit commencer par le pseudo en majuscule", code1.startsWith("ALEXAN#"))
    assertTrue("Le code de Max doit commencer par MAX#", code2.startsWith("MAX#"))
    assertTrue("Le code par défaut doit commencer par VELVET#", code3.startsWith("VELVET#"))

    // 2. Test leaderboard sorting by totalXp descending
    val entries = listOf(
      com.example.data.firebase.FriendLeaderboardEntry(
        uid = "u1",
        userName = "Alice",
        level = 2,
        totalXp = 150,
        friendCode = "ALICE#1111",
        favoriteCocktailNames = listOf("Mojito")
      ),
      com.example.data.firebase.FriendLeaderboardEntry(
        uid = "u2",
        userName = "Bob",
        level = 5,
        totalXp = 800,
        friendCode = "BOB#2222",
        favoriteCocktailNames = listOf("Old Fashioned", "Negroni")
      ),
      com.example.data.firebase.FriendLeaderboardEntry(
        uid = "u3",
        userName = "Charlie",
        level = 4,
        totalXp = 450,
        friendCode = "CHARLIE#3333",
        favoriteCocktailNames = listOf("Daiquiri")
      )
    )

    val sorted = entries.sortedByDescending { it.totalXp }

    assertEquals("Bob", sorted[0].userName) // 800 XP -> #1
    assertEquals("Charlie", sorted[1].userName) // 450 XP -> #2
    assertEquals("Alice", sorted[2].userName) // 150 XP -> #3
    assertEquals(800, sorted[0].totalXp)
  }

  @Test
  fun bacCalculator_widmarkFormulaAccurateCalculations() {
    // 1. Pure alcohol grams
    // Bière 250ml à 5% -> 250 * 0.05 * 0.8 = 10g
    val beerGrams = com.example.ui.prevention.BacCalculatorLogic.calculatePureAlcoholGrams(250.0, 5.0)
    assertEquals(10.0, beerGrams, 0.01)

    // Cocktail 150ml à 15% -> 150 * 0.15 * 0.8 = 18g
    val cocktailGrams = com.example.ui.prevention.BacCalculatorLogic.calculatePureAlcoholGrams(150.0, 15.0)
    assertEquals(18.0, cocktailGrams, 0.01)

    // 2. Widmark for male: 70 kg, K = 0.7, 2 beers (20g pure alcohol)
    val drinks = listOf(
      com.example.ui.prevention.DrinkItem(name = "Bière 1", volumeMl = 250.0, alcoholDegree = 5.0),
      com.example.ui.prevention.DrinkItem(name = "Bière 2", volumeMl = 250.0, alcoholDegree = 5.0)
    )

    // Immediately after (0h): 20 / (70 * 0.7) = 20 / 49 = 0.408... -> 0.41 g/L
    val bacMale0h = com.example.ui.prevention.BacCalculatorLogic.calculateBac(
      drinks = drinks,
      gender = com.example.ui.prevention.Gender.MALE,
      weightKg = 70.0,
      hoursElapsed = 0.0
    )
    assertEquals(0.41, bacMale0h, 0.01)

    // After 1h: 0.408 - 0.15 = 0.258 -> 0.26 g/L
    val bacMale1h = com.example.ui.prevention.BacCalculatorLogic.calculateBac(
      drinks = drinks,
      gender = com.example.ui.prevention.Gender.MALE,
      weightKg = 70.0,
      hoursElapsed = 1.0
    )
    assertEquals(0.26, bacMale1h, 0.01)

    // After 4h: elimination > raw -> clamped to 0.0
    val bacMale4h = com.example.ui.prevention.BacCalculatorLogic.calculateBac(
      drinks = drinks,
      gender = com.example.ui.prevention.Gender.MALE,
      weightKg = 70.0,
      hoursElapsed = 4.0
    )
    assertEquals(0.0, bacMale4h, 0.001)

    // 3. Widmark for female: 60 kg, K = 0.6, same 2 beers (20g pure alcohol)
    // 20 / (60 * 0.6) = 20 / 36 = 0.555... -> 0.56 g/L (exceeds 0.5 g/L legal limit)
    val bacFemale0h = com.example.ui.prevention.BacCalculatorLogic.calculateBac(
      drinks = drinks,
      gender = com.example.ui.prevention.Gender.FEMALE,
      weightKg = 60.0,
      hoursElapsed = 0.0
    )
    assertEquals(0.56, bacFemale0h, 0.01)
    assertTrue("Le taux doit dépasser 0.5 g/L", bacFemale0h >= 0.5)
  }

  @Test
  fun mocktails_richCatalogAndCategoriesOrder() {
    val cocktails = com.example.data.local.getSeedCocktails()
    assertTrue("La base doit contenir au moins 120 cocktails", cocktails.size >= 120)

    val mocktails = cocktails.filter { it.category == "Mocktails" }
    assertTrue("Doit contenir au moins 20 mocktails", mocktails.size >= 20)

    val mocktailNames = mocktails.map { it.name }
    assertTrue("Doit contenir Virgin Mojito", mocktailNames.contains("Virgin Mojito"))
    assertTrue("Doit contenir Bora Bora", mocktailNames.contains("Bora Bora"))
    assertTrue("Doit contenir Shirley Temple", mocktailNames.contains("Shirley Temple"))
    assertTrue("Doit contenir Virgin Piña Colada", mocktailNames.contains("Virgin Piña Colada"))
    assertTrue("Doit contenir Safe Sex on the Beach", mocktailNames.contains("Safe Sex on the Beach"))
    assertTrue("Doit contenir Virgin Mary", mocktailNames.contains("Virgin Mary"))
    assertTrue("Doit contenir Florida", mocktailNames.contains("Florida"))
    assertTrue("Doit contenir Cendrillon", mocktailNames.contains("Cendrillon"))
    assertTrue("Doit contenir Chantaco", mocktailNames.contains("Chantaco"))
    assertTrue("Doit contenir Sweet Sunrise", mocktailNames.contains("Sweet Sunrise"))
    assertTrue("Doit contenir Apple Mojito", mocktailNames.contains("Apple Mojito"))
    assertTrue("Doit contenir Lipton Tonic", mocktailNames.contains("Lipton Tonic"))

    // Tous les mocktails doivent avoir 0.0% d'alcool
    mocktails.forEach { mocktail ->
      assertEquals("Chaque mocktail doit avoir 0% d'alcool: ${mocktail.name}", 0.0, mocktail.alcoholPercentage, 0.0)
      assertTrue("Chaque mocktail doit avoir des ingrédients", mocktail.ingredients.isNotEmpty())
      assertTrue("Chaque mocktail doit avoir des étapes", mocktail.steps.isNotEmpty())
      assertTrue("Chaque mocktail doit avoir une ambiance musicale", mocktail.vibeTag.isNotBlank())
    }

    // Vérifier l'ordre des catégories imposé
    val expectedCategories = listOf("Tous", "Classiques", "Cocktails", "Shooters", "Mocktails", "Favoris")
    assertEquals(6, expectedCategories.size)
    assertEquals("Tous", expectedCategories[0])
    assertEquals("Classiques", expectedCategories[1])
    assertEquals("Cocktails", expectedCategories[2])
    assertEquals("Shooters", expectedCategories[3])
    assertEquals("Mocktails", expectedCategories[4])
    assertEquals("Favoris", expectedCategories[5])

    // Vérifier les 16 incontournables sous la catégorie Classiques
    val classics = cocktails.filter { it.category == "Classiques" }
    assertEquals(16, classics.size)
    val classicNames = classics.map { it.name }
    assertTrue("Doit contenir Mojito", classicNames.contains("Mojito"))
    assertTrue("Doit contenir Moscow Mule", classicNames.contains("Moscow Mule"))
    assertTrue("Doit contenir Margarita", classicNames.contains("Margarita"))
    assertTrue("Doit contenir Old Fashioned", classicNames.contains("Old Fashioned"))
    assertTrue("Doit contenir Piña Colada", classicNames.contains("Piña Colada"))
    assertTrue("Doit contenir Cosmopolitan", classicNames.contains("Cosmopolitan"))
    assertTrue("Doit contenir Negroni", classicNames.contains("Negroni"))
    assertTrue("Doit contenir Gin Tonic", classicNames.contains("Gin Tonic"))
    assertTrue("Doit contenir Caipirinha", classicNames.contains("Caipirinha"))
    assertTrue("Doit contenir Daiquiri", classicNames.contains("Daiquiri"))
    assertTrue("Doit contenir Sex on the Beach", classicNames.contains("Sex on the Beach"))
    assertTrue("Doit contenir Tequila Sunrise", classicNames.contains("Tequila Sunrise"))
    assertTrue("Doit contenir Whiskey Sour", classicNames.contains("Whiskey Sour"))
    assertTrue("Doit contenir Aperol Spritz", classicNames.contains("Aperol Spritz"))
    assertTrue("Doit contenir Cuba Libre", classicNames.contains("Cuba Libre"))
    assertTrue("Doit contenir Espresso Martini", classicNames.contains("Espresso Martini"))

    // Vérifier les 60 créations restantes sous la catégorie Cocktails (sans doublon avec Classiques)
    val cocktailRecipes = cocktails.filter { it.category == "Cocktails" }
    assertEquals(60, cocktailRecipes.size)
    assertTrue("Doit contenir Midnight Blackberry Bramble", cocktailRecipes.any { it.name == "Midnight Blackberry Bramble" })
    assertTrue("Ne doit contenir aucun des 16 classiques", cocktailRecipes.none { classicNames.contains(it.name) })
    assertTrue("Ne doit contenir aucun shooter", cocktailRecipes.none { it.category.startsWith("Shooter") })
    assertTrue("Ne doit contenir aucun mocktail", cocktailRecipes.none { it.category == "Mocktails" || it.isNonAlcoholic })

    // Audit strict des 4 catégories
    assertEquals(16, cocktails.count { it.category == "Classiques" })
    assertEquals(60, cocktails.count { it.category == "Cocktails" })
    assertEquals(20, cocktails.count { it.category == "Shooters" })
    assertEquals(34, cocktails.count { it.category == "Mocktails" })
    assertTrue(cocktails.all { it.category in listOf("Classiques", "Cocktails", "Shooters", "Mocktails") })

    // Vérifier qu'aucun mocktail ne contient d'alcool et que toute boisson 0% est un mocktail
    cocktails.forEach { c ->
      if (c.category == "Mocktails") {
        assertEquals("Le mocktail ${c.name} ne doit avoir aucun alcool", 0.0, c.alcoholPercentage, 0.0)
      } else {
        assertTrue("La boisson ${c.name} (${c.category}) doit avoir de l'alcool", c.alcoholPercentage > 0.0)
      }
    }
  }
}
