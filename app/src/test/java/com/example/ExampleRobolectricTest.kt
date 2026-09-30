package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.getSeedCocktails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Velvet Cocktail", appName)
  }

  @Test
  fun `verify seed cocktails list`() {
    val seedCocktails = getSeedCocktails()
    assertTrue("Seed cocktails list should contain at least 130 recipes", seedCocktails.size >= 130)
    val names = seedCocktails.map { it.name }
    assertTrue(names.contains("Midnight Blackberry Bramble"))
    assertTrue(names.contains("Orgasme") || names.contains("L'Orgasme"))
    assertTrue(names.contains("Neon Margarita"))
    assertTrue(names.contains("Velvet Virgin Mojito"))
    assertTrue(names.contains("Mojito"))
    assertTrue(names.contains("Margarita"))
    assertTrue(names.contains("Bloody Mary"))
    assertTrue(names.contains("French 75"))
    assertTrue(names.contains("Zombie"))
    assertTrue(names.contains("Florida"))
    assertTrue(names.contains("Frozen Strawberry Margarita"))
    assertTrue(names.contains("Vesper Martini"))
    assertTrue(names.contains("Penicillin"))
    assertTrue(names.contains("Basil Smash"))
    assertTrue(names.contains("No-Loma"))
    assertTrue(names.contains("Tequila Paf"))
    assertTrue(names.contains("Kiss Cool"))
    assertTrue(names.contains("Flatliner"))

    // Verify exactly 20 shooters with appropriate category "Shooters" and vibe
    val shooters = seedCocktails.filter { it.category == "Shooters" }
    assertEquals(20, shooters.size)
    shooters.forEach { shooter ->
      assertTrue(
        "Shooter ${shooter.name} should have Clubbing & Electro or Soirée Étudiante vibe",
        shooter.vibeTag == "Clubbing & Electro" || shooter.vibeTag == "Soirée Étudiante"
      )
    }

    // Verify Classiques category contains 16 standard recipes
    val classicsList = seedCocktails.filter { it.category == "Classiques" }.map { it.name }
    assertEquals(16, classicsList.size)
    assertTrue(classicsList.contains("Mojito"))
    assertTrue(classicsList.contains("Margarita"))
    assertTrue(classicsList.contains("Old Fashioned"))
    assertTrue(classicsList.contains("Moscow Mule"))
    assertTrue(classicsList.contains("Cosmopolitan"))

    // Verify Cocktails category contains 60 recipes including creations/signatures
    val cocktailsList = seedCocktails.filter { it.category == "Cocktails" }.map { it.name }
    assertEquals(60, cocktailsList.size)
    assertTrue(cocktailsList.contains("Midnight Blackberry Bramble"))

    // Verify strict 4-category partitioning
    val uniqueCategories = seedCocktails.map { it.category }.distinct().toSet()
    assertEquals(setOf("Classiques", "Cocktails", "Shooters", "Mocktails"), uniqueCategories)
    assertEquals(16, seedCocktails.count { it.category == "Classiques" })
    assertEquals(60, seedCocktails.count { it.category == "Cocktails" })
    assertEquals(20, seedCocktails.count { it.category == "Shooters" })
    assertEquals(34, seedCocktails.count { it.category == "Mocktails" })
  }

  @Test
  fun `verify extracted unique ingredients from cocktails`() {
    val seedCocktails = getSeedCocktails()
    val ingredients = com.example.data.local.extractUniqueIngredients(seedCocktails)
    assertTrue("Should extract more than 100 unique ingredients", ingredients.size >= 100)
    assertTrue(ingredients.any { it.category == com.example.data.model.IngredientCategory.SPIRITS })
    assertTrue(ingredients.any { it.category == com.example.data.model.IngredientCategory.MIXERS })
    assertTrue(ingredients.any { it.category == com.example.data.model.IngredientCategory.SYRUPS })
    assertTrue(ingredients.any { it.category == com.example.data.model.IngredientCategory.GARNISHES })
  }
}
