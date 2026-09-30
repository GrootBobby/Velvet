package com.example.data.local

import com.example.data.model.CocktailEntity
import com.example.data.model.IngredientCategory
import com.example.data.model.InventoryIngredient
import com.example.data.model.RecipeIngredient
import com.example.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Fonction de pré-remplissage (Seed) de la base de données Room.
 * S'exécute au premier lancement de l'application ou si des cocktails / ingrédients doivent être injectés.
 */
suspend fun seedDatabase(database: AppDatabase) = withContext(Dispatchers.IO) {
    val seedCocktails = getSeedCocktails()
    
    // Récupérer les favoris existants pour ne jamais les écraser
    val existingFavIds = try {
        database.cocktailDao().getCocktailsByIds(seedCocktails.map { it.id })
            .filter { it.isFavorite }
            .map { it.id }
            .toSet()
    } catch (_: Exception) {
        emptySet()
    }
    val toInsert = if (existingFavIds.isNotEmpty()) {
        seedCocktails.map { if (existingFavIds.contains(it.id)) it.copy(isFavorite = true) else it }
    } else {
        seedCocktails
    }
    database.cocktailDao().insertAll(toInsert)

    // Extraction dynamique complète de tous les ingrédients uniques depuis l'ensemble des recettes
    val extractedIngredients = extractUniqueIngredients(seedCocktails)
    val existing = try { database.inventoryDao().getAllIngredientsList() } catch (_: Exception) { emptyList() }
    val existingOwned = existing.filter { it.isOwned }.map { it.name.lowercase().trim() }.toSet()
    val toInsertIng = if (existingOwned.isNotEmpty()) {
        extractedIngredients.map { ing ->
            if (existingOwned.contains(ing.name.lowercase().trim())) {
                ing.copy(isOwned = true)
            } else {
                ing
            }
        }
    } else {
        extractedIngredients
    }
    database.inventoryDao().insertAll(toInsertIng)

    // Profil utilisateur local par défaut s'il n'existe pas encore
    try {
        if (database.profileDao().getProfileOnce() == null) {
            database.profileDao().insertProfile(UserProfile())
        }
    } catch (_: Exception) {
        database.profileDao().insertProfile(UserProfile())
    }
}

/**
 * Extrait dynamiquement tous les ingrédients uniques depuis la liste des cocktails,
 * et les classe selon leur catégorie (Spiritueux, Softs, Sirops, Garnitures).
 */
fun extractUniqueIngredients(cocktails: List<CocktailEntity>): List<InventoryIngredient> {
    val uniqueMap = mutableMapOf<String, RecipeIngredient>()
    for (cocktail in cocktails) {
        for (ingredient in cocktail.ingredients) {
            val key = ingredient.name.trim()
            if (!uniqueMap.containsKey(key)) {
                uniqueMap[key] = ingredient
            }
        }
    }

    // Sélection d'ingrédients de base déjà cochés par défaut pour accueillir l'utilisateur
    val starterOwned = setOf(
        "rhum blanc", "vodka", "gin", "tequila blanco", "jus de citron jaune frais",
        "jus de citron vert", "eau gazeuse", "sirop de canne", "sirop de sucre", "menthe fraîche"
    )

    return uniqueMap.values.map { recipeIng ->
        val (category, tag) = categorizeIngredient(recipeIng.name, recipeIng.details, recipeIng.isGarnish)
        val isOwned = starterOwned.contains(recipeIng.name.lowercase().trim())
        InventoryIngredient(
            name = recipeIng.name.trim(),
            brandOrDetail = recipeIng.details.ifEmpty { "Essentiel cocktail" },
            category = category,
            tag = tag,
            isOwned = isOwned
        )
    }.sortedBy { it.name }
}

/**
 * Règle de catégorisation précise pour chaque ingrédient.
 */
fun categorizeIngredient(name: String, details: String, isGarnish: Boolean): Pair<IngredientCategory, String> {
    val n = name.lowercase().trim()
    val d = details.lowercase().trim()

    // 1. SIROPS & SUCRES
    if (("sirop" in n || "sucre" in n || "cordial" in n || "miel" in n || "orgeat" in n || "grenadine" in n) && "tomate" !in n) {
        val tag = when {
            "sucre" in n -> "Sucre"
            "agave" in n -> "Agave"
            "cordial" in n -> "Cordial"
            "orgeat" in n -> "Orgeat"
            "grenadine" in n -> "Grenadine"
            else -> "Sirop"
        }
        return Pair(IngredientCategory.SYRUPS, tag)
    }

    // 2. GARNITURES, HERBES, ÉPICES & BITTERS
    if (isGarnish || "feuille" in n || "menthe" in n || "basilic" in n || "sel" in n || "poivre" in n ||
        "tabasco" in n || "worcestershire" in n || "bitter" in n || "angostura" in n || "fraise" in n || n == "citron vert") {
        val tag = when {
            "menthe" in n || "basilic" in n -> "Herbe"
            "bitter" in n || "angostura" in n -> "Bitter"
            "sel" in n || "poivre" in n || "tabasco" in n || "worcestershire" in n || "sauce" in n -> "Épice"
            "fraise" in n -> "Fruit"
            n == "citron vert" -> "Agrume"
            else -> "Garniture"
        }
        return Pair(IngredientCategory.GARNISHES, tag)
    }

    // 3. SPIRITUEUX & LIQUEURS
    var isSpirit = false
    if ("ginger" !in n) {
        if (n == "gin" || n.startsWith("gin ") || n.endsWith(" gin") || "genièvre" in d) {
            isSpirit = true
        }
    }
    val spiritWords = listOf(
        "vodka", "rhum", "rum", "tequila", "whisky", "whiskey", "bourbon", "scotch",
        "cognac", "pisco", "cachaça", "absinthe", "liqueur", "cointreau", "amaretto", "baileys",
        "kahlúa", "curaçao", "campari", "aperol", "vermouth", "prosecco", "champagne", "lillet",
        "sambuca", "drambuie", "bénédictine", "galliano", "jägermeister", "passoã", "crème de mûre",
        "crème de violette", "chambord", "midori", "jack daniel", "grand marnier", "triple sec", "cherry brandy",
        "get 27", "get 31", "manzana", "fireball", "peachtree"
    )
    if (spiritWords.any { it in n }) {
        isSpirit = true
    }

    if (isSpirit) {
        val tag = when {
            listOf("liqueur", "cointreau", "amaretto", "baileys", "kahlúa", "curaçao", "passoã", "chambord", "midori", "drambuie", "sambuca", "bénédictine", "galliano", "grand marnier", "triple sec", "cherry brandy", "crème de", "get 27", "get 31", "manzana", "peachtree").any { it in n } -> "Liqueur"
            "gin" in n -> "Gin"
            "vodka" in n -> "Vodka"
            "rhum" in n || "rum" in n -> "Rhum"
            "tequila" in n -> "Tequila"
            listOf("whisky", "whiskey", "bourbon", "scotch", "jack", "fireball").any { it in n } -> "Whisky"
            "champagne" in n || "prosecco" in n -> "Bulles"
            listOf("campari", "aperol", "vermouth", "lillet").any { it in n } -> "Apéritif"
            else -> "Spiritueux"
        }
        return Pair(IngredientCategory.SPIRITS, tag)
    }

    // 4. SOFTS & MIXERS (Jus, sodas, eau, thé, café, lait/crème)
    val tag = when {
        "jus" in n || "purée" in n || "cranberry" in n -> "Jus"
        listOf("eau", "tonic", "soda", "cola", "ginger", "limonade", "boisson", "schweppes").any { it in n } -> "Soda"
        listOf("thé", "café", "expresso").any { it in n } -> "Infusion"
        listOf("crème", "lait", "oeuf").any { it in n } -> "Laitier"
        else -> "Soft"
    }
    return Pair(IngredientCategory.MIXERS, tag)
}

/**
 * Liste initiale complète des cocktails pour le seed Room (100 cocktails au total).
 */
fun getSeedCocktails(): List<CocktailEntity> = listOf(
    // 1. Signature Speakeasy de référence
    CocktailEntity(
        id = 1,
        name = "Midnight Blackberry Bramble",
        subtitle = "Signature Velvet Speakeasy",
        description = "Une alchimie ténébreuse de gin infusé aux baies, de mûres sauvages et d'un nappage velouté aux lueurs violettes.",
        category = "Cocktails",
        flavorProfile = "Fruité/Botanique",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 17.5,
        ingredients = listOf(
            RecipeIngredient("Gin infusé aux baies", "Genièvre & baies sauvages", 4.5, "3 cuil. à soupe", false, "liquor"),
            RecipeIngredient("Jus de citron jaune frais", "Pressé minute pour acidité vive", 2.5, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sucre de canne liquide", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Crème de Mûre", "Liqueur de mûres sauvages", 2.0, "1.5 cuil. à soupe", false, "invert_colors")
        ),
        steps = listOf(
            "Remplir le shaker de glace pilée, ajouter gin, citron, sucre.",
            "Shaker 12s.",
            "Filtrer dans un verre vintage.",
            "Verser la crème de mûre en filet."
        ),
        garnish = "3 mûres givrées, brin de menthe.",
        glassware = "Verre vintage taillé",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/twtbh51630406392.jpg",
        isFavorite = true,
        vibeTag = "Dim-lit Speakeasy & Deep House"
    ),

    // 2. Orgasme (Shooter Gourmand & Stratifié)
    CocktailEntity(
        id = 2,
        name = "Orgasme",
        subtitle = "Le Shooter Gourmand & Stratifié",
        description = "Le shooter aphrodisiaque et onctueux par excellence : triple couche veloutée mêlant le café torréfié, l'amande douce et la crème de whisky irlandaise.",
        category = "Shooters",
        flavorProfile = "Doux/Crémeux",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur de café Kahlúa", "Café torréfié & vanille", 1.5, "1/3 shooter", false, "liquor"),
            RecipeIngredient("Amaretto", "Liqueur d'amande douce", 1.5, "1/3 shooter", false, "liquor"),
            RecipeIngredient("Crème irlandaise Baileys", "Crème de whisky onctueuse", 1.5, "1/3 shooter", false, "invert_colors")
        ),
        steps = listOf(
            "Verser la liqueur de café Kahlúa au fond du verre à shooter.",
            "Faire couler délicatement l'Amaretto avec le dos d'une cuillère de bar pour créer la strate médiane.",
            "Napper enfin délicatement avec le Baileys pour former la dernière couche onctueuse.",
            "Déguster d'un seul trait cul-sec !"
        ),
        garnish = "Trois strates contrastées.",
        glassware = "Verre Shooter",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vr6kle1504886114.jpg",
        isFavorite = true,
        vibeTag = "Soirée Étudiante"
    ),

    // 3. Clubbing Électrique
    CocktailEntity(
        id = 3,
        name = "Neon Margarita",
        subtitle = "Clubbing Edition Électrique",
        description = "Une Margarita audacieuse aux reflets bleus luminescents, relevée par une tequila vive et un bord délicatement givré.",
        category = "Cocktails",
        flavorProfile = "Acidulé/Électrique",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 22.0,
        ingredients = listOf(
            RecipeIngredient("Tequila Blanco", "100% agave bleu", 5.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Curaçao Bleu", "Liqueur d'orange bleue", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 3.0, "1 citron vert entier", false, "nutrition"),
            RecipeIngredient("Sirop d'agave", "Ambré bio", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Givrer le bord du verre au sel fin.",
            "Shaker tous les ingrédients avec de la glace.",
            "Filtrer dans un verre à Margarita."
        ),
        garnish = "Rondelle de citron vert.",
        glassware = "Verre à Margarita",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1556881286-fc6915169721?auto=format&fit=crop&w=800&q=80",
        isFavorite = true,
        vibeTag = "Clubbing & Future Bass"
    ),

    // 4. Mocktails & Sans Alcool
    CocktailEntity(
        id = 4,
        name = "Velvet Virgin Mojito",
        subtitle = "Mocktails & Sans Alcool",
        description = "0% alcool, 100% fraîcheur : menthe fraîche froissée, citron vert tonique et pétillement désaltérant.",
        category = "Mocktails",
        flavorProfile = "Frais/Herbacé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Eau gazeuse", "Pétillante fraîche", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 3.0, "1 citron vert entier", false, "nutrition"),
            RecipeIngredient("Sirop de canne", "Sucre de canne", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Feuilles de menthe fraîche", "Feuilles entières aromatiques", 0.0, "10 unités", false, "spa")
        ),
        steps = listOf(
            "Piler doucement la menthe avec le sirop et le citron au fond du verre.",
            "Remplir de glace pilée.",
            "Compléter à l'eau gazeuse et mélanger à la cuillère."
        ),
        garnish = "Tête de menthe fraîche.",
        glassware = "Verre Tumbler Highball",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://images.unsplash.com/photo-1544145945-f90425340c7e?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Pop Solaire & Chill House"
    ),

    // 5. Mojito
    CocktailEntity(
        id = 5,
        name = "Mojito",
        subtitle = "Le Classique Cubain Par Excellence",
        description = "L'emblématique mariage cubain entre la fraîcheur éclatante de la menthe froissée, l'acidité vive du citron vert et le caractère du rhum blanc.",
        category = "Classiques",
        flavorProfile = "Frais/Herbacé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum agricole cubain", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Citron vert", "Pressé minute", 3.0, "1/2 citron vert", false, "nutrition"),
            RecipeIngredient("Sirop de canne", "Sucre de canne liquide", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Menthe fraîche", "Feuilles réveillées", 0.0, "8 à 10 feuilles", false, "spa"),
            RecipeIngredient("Eau gazeuse", "Fraîche et pétillante", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Piler doucement la menthe avec le sirop de canne et le citron vert au fond du verre sans déchirer les feuilles.",
            "Ajouter le rhum blanc et remplir le verre aux deux tiers de glace pilée.",
            "Shaker ou remuer vivement à la cuillère de bar pour mélanger les saveurs.",
            "Allonger à l'eau gazeuse fraîche et ajouter un dôme de glace pilée."
        ),
        garnish = "Menthe.",
        glassware = "Verre Tumbler Highball",
        shakeSeconds = 6,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/metwgh1606770327.jpg",
        isFavorite = true,
        vibeTag = "Latin Beats & Sunset Lounge"
    ),

    // 6. Moscow Mule
    CocktailEntity(
        id = 6,
        name = "Moscow Mule",
        subtitle = "Timbale Givrée & Gingembre Ardent",
        description = "Un cocktail culte et ultra-rafraîchissant, alliant la neutralité tranchante de la vodka au piquant vivifiant de la ginger beer artisanale.",
        category = "Classiques",
        flavorProfile = "Épicé/Frais",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka de grain pure", 5.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.5, "1/2 citron vert", false, "nutrition"),
            RecipeIngredient("Ginger Beer", "Gingembre fermenté pétillant", 12.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Remplir la timbale en cuivre de glace pilée jusqu'en haut.",
            "Verser la vodka et le jus de citron vert frais direct au verre.",
            "Allonger délicatement avec la ginger beer pétillante et mélanger doucement d'un coup de cuillère."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Timbale en cuivre givrée",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/3pylqc1504370988.jpg",
        isFavorite = false,
        vibeTag = "Urban Chill & French Rap"
    ),

    // 7. Sex on the Beach
    CocktailEntity(
        id = 7,
        name = "Sex on the Beach",
        subtitle = "L'Élixir Coucher de Soleil",
        description = "Une création fruitée irrésistible aux nuances orangées et rubis, où la pêche sucrée s'harmonise avec le peps du cranberry.",
        category = "Classiques",
        flavorProfile = "Fruité/Sucré",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de pêche", "Pêche de vigne douce", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus d'orange", "Pur jus d'orange pressée", 6.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Jus de cranberry", "Canneberge acidulée", 6.0, "1/2 verre", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, la liqueur de pêche et le jus d'orange dans un shaker rempli de glaçons.",
            "Shaker avec entrain pendant 8 secondes.",
            "Verser dans un verre highball avec des glaçons, puis napper de jus de cranberry pour un dégradé spectaculaire."
        ),
        garnish = "Tranche d'orange.",
        glassware = "Verre Hurricane ou Highball",
        shakeSeconds = 8,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/szmj2d1504889961.jpg",
        isFavorite = false,
        vibeTag = "Clubbing Dance & Pop 2000s"
    ),

    // 8. Piña Colada
    CocktailEntity(
        id = 8,
        name = "Piña Colada",
        subtitle = "Évasion Onctueuse Caribéenne",
        description = "L'accord parfait de rhum caribéen, de jus d'ananas frais et d'une crème de coco veloutée transportant immédiatement sous les tropiques.",
        category = "Classiques",
        flavorProfile = "Doux/Crémeux",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum des îles", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus d'ananas", "Ananas mûr et parfumé", 10.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Crème de coco", "Lait & crème de coco riche", 4.0, "2 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Verser le rhum blanc, le jus d'ananas et la crème de coco dans le shaker ou le bol d'un blender.",
            "Ajouter une pelle de glace pilée et shaker vigoureusement (ou mixer 15 secondes) jusqu'à consistance crémeuse et mousseuse.",
            "Verser sans filtrer dans un grand verre tropical sur lit de glace."
        ),
        garnish = "Triangle d'ananas.",
        glassware = "Verre Poco Grande ou Hurricane",
        shakeSeconds = 14,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/upgsue1668419912.jpg",
        isFavorite = true,
        vibeTag = "Tropical Island & Chill Afrobeats"
    ),

    // 9. Cosmopolitan
    CocktailEntity(
        id = 9,
        name = "Cosmopolitan",
        subtitle = "Le Glamour Chic New-Yorkais",
        description = "Raffiné et percutant : un équilibre magistral entre la vodka citronnée, la vivacité du Cointreau et la robe rose rubis du cranberry.",
        category = "Classiques",
        flavorProfile = "Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Cointreau", "Triple sec d'oranges douces", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de cranberry", "Canneberge pure", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Placer tous les ingrédients dans le shaker rempli de cubes de glace réguliers.",
            "Shaker vigoureusement pendant 10 secondes jusqu'à condensation extérieure.",
            "Double-filtrer dans une coupe à martini rafraîchie pour retenir les éclats de glace."
        ),
        garnish = "Zeste de citron.",
        glassware = "Verre à Martini taillé",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/kpsajh1504368362.jpg",
        isFavorite = false,
        vibeTag = "Nu-Disco & Electro Pop"
    ),

    // 10. Aperol Spritz
    CocktailEntity(
        id = 10,
        name = "Aperol Spritz",
        subtitle = "L'Apéritif Vénitien Doré",
        description = "La dolce vita italienne dans un verre : la douce amertume de l'Aperol sublimée par l'effervescence joyeuse du Prosecco.",
        category = "Classiques",
        flavorProfile = "Amer/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 11.0,
        ingredients = listOf(
            RecipeIngredient("Aperol", "Apéritif amère d'herbes & oranges", 6.0, "1/3 du verre", false, "invert_colors"),
            RecipeIngredient("Prosecco", "Vin effervescent italien brut", 9.0, "1/2 verre", false, "liquor"),
            RecipeIngredient("Eau gazeuse", "Trait pétillant", 3.0, "Un trait", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un grand verre ballon de gros glaçons.",
            "Verser directement le Prosecco, puis l'Aperol en mouvement circulaire pour une teinte homogène.",
            "Compléter par un trait d'eau gazeuse et remuer délicatement une seule fois."
        ),
        garnish = "Tranche d'orange.",
        glassware = "Grand verre ballon à pied",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/j9evx11504373665.jpg",
        isFavorite = true,
        vibeTag = "Italian Rooftop & Deep House"
    ),

    // 11. Espresso Martini
    CocktailEntity(
        id = 11,
        name = "Espresso Martini",
        subtitle = "Élixir Nocturne & Crema Soyeuse",
        description = "Le remontant sophistiqué des nuits londoniennes : café torréfié intense, vodka glacée et mousse onctueuse digne d'un barista.",
        category = "Classiques",
        flavorProfile = "Corsé/Café",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de café", "Kahlúa ou liqueur artisanale", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Expresso froid", "Shot expresso fraîchement extrait", 3.0, "1 shot", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne liquide", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Extraire un expresso court serré et le laisser tiédir ou refroidir rapidement.",
            "Verser la vodka, la liqueur de café, l'expresso et le sirop de sucre dans le shaker avec une abondance de glaçons compacts.",
            "Shaker très vigoureusement pendant 12 secondes afin de densifier la mousse en surface.",
            "Double-filtrer sans tarder dans une coupe à martini rafraîchie."
        ),
        garnish = "3 grains de café.",
        glassware = "Coupe Martini élégante",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/n0sx531504372951.jpg",
        isFavorite = true,
        vibeTag = "Midnight Speakeasy & Lounge Jazz"
    ),

    // 12. Negroni
    CocktailEntity(
        id = 12,
        name = "Negroni",
        subtitle = "L'Amertume Aristocratique Italienne",
        description = "La trinité légendaire de la mixologie mondiale : une part de gin sec, une part de vermouth doux et une part de bitter Campari écarlate.",
        category = "Classiques",
        flavorProfile = "Amer/Herbacé",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 24.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Gin sec botanique", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Campari", "Bitter aromatique rouge rubis", 3.0, "1 shooter", false, "invert_colors"),
            RecipeIngredient("Vermouth Rouge", "Vermouth di Torino doux", 3.0, "1 shooter", false, "liquor")
        ),
        steps = listOf(
            "Placer un gros cube de glace translucide ou des glaçons denses dans un verre Old Fashioned.",
            "Verser le gin, le Campari et le vermouth rouge dans le verre à mélange ou direct sur la glace.",
            "Mélanger délicatement à la cuillère de bar pendant 30 secondes pour une dilution et un rafraîchissement optimaux.",
            "Exprimer un zeste d'orange pour parfumer les essences et déposer sur la glace."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Verre Old Fashioned Lowball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1470337458703-46ad1756a187?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Velvet Speakeasy & Vinyl Soul"
    ),

    // 13. Caipirinha
    CocktailEntity(
        id = 13,
        name = "Caipirinha",
        subtitle = "L'Âme Festve du Brésil",
        description = "L'esprit de Rio de Janeiro condensé : le goût végétal puissant de la Cachaça pur jus de canne allié aux quartiers de citrons écrasés.",
        category = "Classiques",
        flavorProfile = "Acide/Sucré",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Cachaça", "Eau-de-vie de canne brésilienne", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Citron vert", "Coupé en quartiers", 3.0, "1 entier coupé", false, "nutrition"),
            RecipeIngredient("Sucre roux", "Cassonade pure", 2.0, "2 cuillères", false, "water_drop")
        ),
        steps = listOf(
            "Laver et couper le citron vert en 8 quartiers en retirant la membrane blanche centrale.",
            "Placer les quartiers et les deux cuillères de sucre roux dans le fond du verre.",
            "Piler fermement avec un pilon sans trop insister sur l'écorce pour ne pas développer d'amertume.",
            "Remplir de glace pilée, verser la Cachaça et remuer de bas en haut."
        ),
        garnish = "Rondelle de citron vert.",
        glassware = "Verre Tumbler bas",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/jgvn7p1582484435.jpg",
        isFavorite = false,
        vibeTag = "Bossa Nova & Latin Groove"
    ),

    // 14. Long Island Iced Tea
    CocktailEntity(
        id = 14,
        name = "Long Island Iced Tea",
        subtitle = "Le Monument de Puissance",
        description = "Une alchimie redoutable regroupant 5 spiritueux sous la douceur trompeuse d'un thé glacé pétillant.",
        category = "Cocktails",
        flavorProfile = "Puissant",
        prepTimeMinutes = 4,
        difficulty = "Expert",
        alcoholPercentage = 22.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche", 1.5, "1 trait généreux", false, "liquor"),
            RecipeIngredient("Rhum", "Rhum blanc léger", 1.5, "1 trait généreux", false, "liquor"),
            RecipeIngredient("Gin", "Gin sec", 1.5, "1 trait généreux", false, "liquor"),
            RecipeIngredient("Tequila", "Tequila Blanco", 1.5, "1 trait généreux", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'orange", 1.5, "1 trait généreux", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Cola", "Pétillant glacé", 8.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, le rhum, le gin, la tequila, le Cointreau et le jus de citron dans un shaker avec de la glace.",
            "Shaker énergiquement pendant 8 secondes.",
            "Filtrer dans un grand verre highball rempli de glaçons.",
            "Allonger au cola pour donner la célèbre couleur dorée de thé froid."
        ),
        garnish = "Tranche de citron.",
        glassware = "Verre Highball XXL",
        shakeSeconds = 8,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/wx7hsg1504370510.jpg",
        isFavorite = false,
        vibeTag = "Peak Time EDM & Bass House"
    ),

    // 15. B-52
    CocktailEntity(
        id = 15,
        name = "B-52",
        subtitle = "Le Shooter Stratifié & Flambé",
        description = "Le roi des shooters de fin de soirée : trois couches de densités distinctes couronnées d'une flamme bleutée captivante.",
        category = "Shooters",
        flavorProfile = "Chaud/Café",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur de café", "Kahlúa dense", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Baileys", "Crème de whisky", 1.5, "1/3 shooter", false, "water_drop"),
            RecipeIngredient("Grand Marnier", "Liqueur de cognac & orange", 1.5, "1/3 shooter", false, "liquor")
        ),
        steps = listOf(
            "Verser la liqueur de café directement au fond du verre à shooter.",
            "À l'aide du dos d'une cuillère de bar posée contre la paroi, verser très délicatement le Baileys pour créer la deuxième strate.",
            "Répéter l'opération avec le Grand Marnier pour faire flotter la troisième couche en surface.",
            "Flamber le dessus quelques secondes et boire cul-sec avec une paille."
        ),
        garnish = "Flamme bleutée sur le dessus.",
        glassware = "Verre Shooter transparent",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/5a3vg61504372070.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 16. Tequila Sunrise
    CocktailEntity(
        id = 16,
        name = "Tequila Sunrise",
        subtitle = "La Symphonie Lumineuse d'Acapulco",
        description = "Une icône visuelle et gustative, évoquant un lever de soleil rouge flamboyant grâce à l'effet de pesanteur de la grenadine.",
        category = "Classiques",
        flavorProfile = "Fruité",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 11.0,
        ingredients = listOf(
            RecipeIngredient("Tequila", "Tequila 100% agave", 5.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus d'orange", "Pur jus d'orange", 10.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine concentrée", 1.5, "1 trait pour le dégradé", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un verre tumbler de glaçons réguliers.",
            "Verser la tequila puis le jus d'orange frais, et remuer légèrement.",
            "Faire couler doucement le sirop de grenadine le long de la paroi : il plonge au fond pour créer le dégradé naturel."
        ),
        garnish = "Demi-rondelle d'orange et cerise.",
        glassware = "Verre Highball ou Hurricane",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/quqyqp1480879103.jpg",
        isFavorite = false,
        vibeTag = "Retro Synthwave & Pop 80s"
    ),

    // 17. Pornstar Martini
    CocktailEntity(
        id = 17,
        name = "Pornstar Martini",
        subtitle = "Sensualité Exotique & Shot de Bulles",
        description = "Le cocktail moderne le plus populaire au monde : nectar onctueux de fruit de la passion, vanille veloutée et son rituel accompagnement de bulles.",
        category = "Cocktails",
        flavorProfile = "Fruité/Exotique",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 16.0,
        ingredients = listOf(
            RecipeIngredient("Vodka Vanille", "Vodka infusée vanille bourbon", 4.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Passoã", "Liqueur de fruit de la passion", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Purée fruit de la passion", "Pulpe naturelle", 3.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de vanille", "Sirop aromatisé", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Shot de Champagne", "À part bien frappé", 5.0, "1 shooter à part", true, "liquor")
        ),
        steps = listOf(
            "Verser la vodka vanille, le Passoã, la purée de passion et le sirop de vanille dans le shaker avec une abondance de glaçons.",
            "Shaker énergiquement pendant 12 secondes pour créer une mousse dorée dense.",
            "Double-filtrer dans une coupe rafraîchie.",
            "Déposer la demi-passion flottante et servir avec le shot de champagne frais en accompagnement."
        ),
        garnish = "Demi-fruit de la passion.",
        glassware = "Coupe Cocktail & Shot",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/xjhjdf1630406071.jpg",
        isFavorite = true,
        vibeTag = "Afrobeats & Modern R&B"
    ),

    // 18. Amaretto Sour
    CocktailEntity(
        id = 18,
        name = "Amaretto Sour",
        subtitle = "L'Onctuosité Italienne Douce-Amère",
        description = "Une texture en bouche incomparable : les arômes d'amande et de massepain adoucis par une émulsion soyeuse au citron frais.",
        category = "Cocktails",
        flavorProfile = "Doux/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Amaretto", "Disaronno d'amande", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 3.0, "1 citron entier", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne liquide", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Blanc d'oeuf", "Pour l'émulsion crémeuse", 1.0, "1 blanc frais", false, "water_drop")
        ),
        steps = listOf(
            "Effectuer d'abord un 'dry shake' (secouer sans glaçons) de tous les ingrédients pour émulsionner parfaitement le blanc d'oeuf.",
            "Ajouter ensuite de gros glaçons dans le shaker et shaker vigoureusement pendant 10 secondes.",
            "Filtrer dans un verre Old Fashioned sur un gros glaçon pour apprécier la collerette de mousse blanche."
        ),
        garnish = "Cerise au marasquin et tranche d'orange.",
        glassware = "Verre Old Fashioned",
        shakeSeconds = 12,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1587888637140-849b25d80ef9?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Smooth Jazz & Lo-Fi Hip Hop"
    ),

    // 19. Cuba Libre
    CocktailEntity(
        id = 19,
        name = "Cuba Libre",
        subtitle = "La Brise Révolutionnaire Caribéenne",
        description = "Bien plus qu'un simple rhum-coca : l'expression vive des huiles d'écorce de citron vert mariée aux notes boisées d'un bon rhum ambré.",
        category = "Classiques",
        flavorProfile = "Simple",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Rhum ambré", "Rhum vieilli en fût", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé au verre", 1.5, "1/2 citron vert", false, "nutrition"),
            RecipeIngredient("Cola", "Cola bien frais", 12.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre highball de glace jusqu'au bord.",
            "Presser un quartier de citron vert et le déposer dans le verre.",
            "Verser le rhum ambré puis allonger au cola direct au verre et remuer doucement à la cuillère."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre Highball classique",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://images.unsplash.com/photo-1595981267035-7b04ca84a82d?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Urban Reggaeton & Latin Pop"
    ),

    // 20. Blue Lagoon
    CocktailEntity(
        id = 20,
        name = "Blue Lagoon",
        subtitle = "Le Lagon Bleu Électrique",
        description = "Une immersion visuelle percutante : la teinte cyan luminescente du curaçao associée au peps tonique du citron et des bulles.",
        category = "Cocktails",
        flavorProfile = "Électrique",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Curaçao Bleu", "Liqueur d'oranges bleues", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Limonade", "Limonade pétillante", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, le curaçao bleu et le jus de citron dans un shaker avec de la glace.",
            "Shaker brièvement pour bien refroidir le mélange.",
            "Filtrer dans un verre rempli de glaçons et compléter avec la limonade fraîche."
        ),
        garnish = "Rondelle de citron.",
        glassware = "Verre Tumbler ou Ouragan",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/5wm4zo1582579154.jpg",
        isFavorite = false,
        vibeTag = "Future Rave & EDM Festival"
    ),

    // 21. White Russian
    CocktailEntity(
        id = 21,
        name = "White Russian",
        subtitle = "La Gourmandise Culte & Veloutée",
        description = "Le cocktail culte immortalisé au cinéma : le contraste sublime entre la liqueur de café sombre et le nappage immaculé de crème liquide.",
        category = "Cocktails",
        flavorProfile = "Doux/Crémeux",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 16.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Liqueur de café", "Kahlúa concentrée", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Crème liquide", "Crème entière fluide", 3.0, "1 shooter", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un verre Old Fashioned de gros glaçons.",
            "Verser la vodka et la liqueur de café directement sur les glaçons et remuer.",
            "Faire couler délicatement la crème liquide sur le dos d'une cuillère pour la faire flotter au sommet."
        ),
        garnish = "Saupoudré de muscade ou grains de café.",
        glassware = "Verre Rocks Old Fashioned",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vsrupw1472405732.jpg",
        isFavorite = false,
        vibeTag = "Cozy Speakeasy & Retro Cinema"
    ),

    // 22. Mai Tai
    CocktailEntity(
        id = 22,
        name = "Mai Tai",
        subtitle = "Le Trésor de la Culture Tiki",
        description = "Un chef-d'œuvre de la mixologie polynésienne réunissant deux rhums de caractère, le parfum d'amande de l'orgeat et la vivacité des agrumes.",
        category = "Cocktails",
        flavorProfile = "Exotique",
        prepTimeMinutes = 4,
        difficulty = "Expert",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum blanc agricole", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Rhum ambré", "Rhum vieux puissant", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'orange", 1.5, "1 cuil. à café", false, "invert_colors"),
            RecipeIngredient("Sirop d'orgeat", "Lait d'amande douce", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.0, "1/2 citron vert", false, "nutrition")
        ),
        steps = listOf(
            "Placer le rhum blanc, le rhum ambré, le Cointreau, le sirop d'orgeat et le jus de citron vert dans le shaker rempli de glace pilée.",
            "Shaker vigoureusement pendant 12 secondes.",
            "Verser l'intégralité du contenu dans un verre Tiki ou Old Fashioned et compléter de glace pilée."
        ),
        garnish = "Tête de menthe et quartier de citron vert.",
        glassware = "Verre Tiki ou Double Old Fashioned",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/twyrrp1439907470.jpg",
        isFavorite = false,
        vibeTag = "Exotic Beats & Tropical House"
    ),

    // 23. Shirley Temple
    CocktailEntity(
        id = 23,
        name = "Shirley Temple",
        subtitle = "L'Incontournable Mocktail Hollywoodien",
        description = "0% alcool : le cocktail sans alcool le plus célèbre d'Hollywood associant la fraîcheur épicée du Ginger Ale et le rubis gourmand de la grenadine.",
        category = "Mocktails",
        flavorProfile = "Sucré",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Ginger Ale", "Soda gingembre doux", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine fruitée", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un grand verre de glaçons.",
            "Verser le jus de citron et le ginger ale bien frais.",
            "Verser délicatement le sirop de grenadine qui se diffuse en un élégant dégradé rubis."
        ),
        garnish = "Cerise confite.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1536935338788-846bb9981813?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Pop Feel-Good & Acoustic Chills"
    ),

    // 24. Bora Bora
    CocktailEntity(
        id = 24,
        name = "Bora Bora",
        subtitle = "Mocktail Évasion Paradis Pacifique",
        description = "0% alcool : une explosion parfumée et désaltérante où l'ananas rencontre le fruit de la passion et une pointe acidulée de citron.",
        category = "Mocktails",
        flavorProfile = "Exotique",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus de fruit de la passion", "Nectar maracuja", 4.0, "1/4 verre", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Touche sucrée", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Placer le jus d'ananas, le jus de passion, le jus de citron et la grenadine dans un shaker avec de la glace.",
            "Shaker avec entrain pendant 10 secondes pour aérer le jus.",
            "Filtrer dans un grand verre sur lit de glace fraîche."
        ),
        garnish = "Brochette de fruits exotiques.",
        glassware = "Verre Cocktail Tropical",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/xwuqvw1473201811.jpg",
        isFavorite = false,
        vibeTag = "Sunset Chill & Afro House"
    ),

    // 25. Margarita
    CocktailEntity(
        id = 25,
        name = "Margarita",
        subtitle = "L'Icône Mexicaine au Bord Salé",
        description = "Le grand classique mexicain : l'agave vif de la tequila blanco balancé par les notes d'orange du Cointreau et l'acidité tranchante du citron vert sur bord salé.",
        category = "Classiques",
        flavorProfile = "Acide/Salé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Tequila Blanco", "100% agave", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'oranges douces & amères", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.0, "1/2 citron vert", false, "nutrition"),
            RecipeIngredient("Fleur de sel", "Pour givrer le col", 0.0, "Bordure", true, "spa")
        ),
        steps = listOf(
            "Frotter le bord du verre avec un quartier de citron vert puis tremper dans une coupelle de fleur de sel.",
            "Verser la tequila blanco, le Cointreau et le jus de citron vert dans un shaker rempli de glace.",
            "Shaker énergiquement pendant 10 secondes pour refroidir et émulsionner.",
            "Filtrer finement dans le verre préparé."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre à Margarita",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
        isFavorite = true,
        vibeTag = "Mexican Cantina & Latin Rhythms"
    ),

    // 26. Daiquiri
    CocktailEntity(
        id = 26,
        name = "Daiquiri",
        subtitle = "La Pureté Caribéenne Frappée",
        description = "L'archétype du cocktail parfait : trois ingrédients purs où le rhum blanc trouve son équilibre suprême entre fraîcheur citronnée et douceur de canne.",
        category = "Classiques",
        flavorProfile = "Acide/Sucré",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum agricole pur jus", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.5, "1 citron vert entier", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sirop de canne simple 2:1", 1.5, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Verser le rhum blanc, le jus de citron vert et le sirop de sucre dans le shaker avec une abondance de glace.",
            "Shaker vigoureusement pendant 12 secondes jusqu'à formation d'un givre dense à l'extérieur.",
            "Filtrer finement dans une coupe à cocktail préalablement refroidie."
        ),
        garnish = "Rondelle de citron vert.",
        glassware = "Coupe raffinée",
        shakeSeconds = 12,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/mrz9091589574515.jpg",
        isFavorite = false,
        vibeTag = "Havana Nights & Mambo Beats"
    ),

    // 27. Gin Tonic
    CocktailEntity(
        id = 27,
        name = "Gin Tonic",
        subtitle = "L'Élégance Botanique & Bulles Fines",
        description = "L'accord intemporel entre les baies de genièvre d'un gin de maître et la subtile amertume de quinine d'un tonic haut de gamme.",
        category = "Classiques",
        flavorProfile = "Amer/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 11.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Gin sec botanique", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Tonic", "Tonic artisanal Fever-Tree", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un grand verre ballon (copa) de gros glaçons cristallins.",
            "Verser le gin sur les glaçons pour le glacer.",
            "Allonger délicatement avec le tonic frais en inclinant le verre pour préserver la bulle. Remuer une seule fois."
        ),
        garnish = "Zeste de citron ou concombre.",
        glassware = "Verre Copa Ballon",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1556679343-c7306c1976bc?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Speakeasy Lounge & Chill Lo-Fi"
    ),

    // 28. Whiskey Sour
    CocktailEntity(
        id = 28,
        name = "Whiskey Sour",
        subtitle = "La Velouté Boisée d'un Grand Bourbon",
        description = "Le monument américain : la puissance chaleureuse du bourbon adoucie par une mousse crémeuse soyeuse et la fraîcheur du citron jaune.",
        category = "Classiques",
        flavorProfile = "Acide/Crémeux",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 14.0,
        ingredients = listOf(
            RecipeIngredient("Bourbon", "Bourbon vieilli pur grain", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.5, "1/2 citron jaune", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Blanc d'oeuf", "Pour l'émulsion dense", 1.0, "1 blanc frais", false, "water_drop")
        ),
        steps = listOf(
            "Effectuer d'abord un 'dry shake' (secouer sans glaçons) de tous les ingrédients pour fouetter le blanc d'oeuf.",
            "Ajouter ensuite de gros glaçons et shaker énergiquement pendant 10 secondes.",
            "Double-filtrer dans un verre rocks pour admirer la couronne de mousse nacrée."
        ),
        garnish = "Cerise amarena.",
        glassware = "Verre Rocks Old Fashioned",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/hbkfsh1589574990.jpg",
        isFavorite = true,
        vibeTag = "Bourbon Lounge & Vintage Vinyl"
    ),

    // 29. Jägerbomb
    CocktailEntity(
        id = 29,
        name = "Jägerbomb",
        subtitle = "L'Explosion Nocturne Énergisante",
        description = "Le rituel festif culte des clubs : les 56 plantes du Jägermeister plongées en immersion dans une vague énergisante pétillante.",
        category = "Shooters",
        flavorProfile = "Festif/Énergétique",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Jägermeister", "Liqueur de plantes allemandes", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Boisson énergisante", "Pétillante fraîche", 6.0, "Mini tumbler", false, "nutrition")
        ),
        steps = listOf(
            "Verser la boisson énergisante bien fraîche dans un grand verre tumbler.",
            "Remplir un verre à shot de Jägermeister.",
            "Lâcher le shot plein directement au centre du grand verre et boire d'un trait pendant l'effervescence."
        ),
        garnish = "Effervescence du shot plongé.",
        glassware = "Verre Tumbler & Shooter",
        shakeSeconds = 0,
        rating = 4.6,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/d30z931503565384.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 30. Kamikaze
    CocktailEntity(
        id = 30,
        name = "Kamikaze",
        subtitle = "L'Impact Tranchant Citronné",
        description = "Un shooter incisif et électrique né dans les bases navales américaines : l'intensité de la vodka adoucie par le triple sec et la fraîcheur du citron vert.",
        category = "Shooters",
        flavorProfile = "Acide/Sec",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 2.0, "1/3 shooter", false, "liquor"),
            RecipeIngredient("Cointreau", "Triple sec d'oranges", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.5, "1/3 shooter", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, le Cointreau et le jus de citron vert dans un shaker avec de la glace.",
            "Shaker vigoureusement pendant 8 secondes pour frapper le liquide.",
            "Filtrer et répartir dans des verres à shooter givrés."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre Shooter givré",
        shakeSeconds = 8,
        rating = 4.7,
        imageUrl = "https://images.unsplash.com/photo-1570598912132-0ba1dc952b7d?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 31. TGV
    CocktailEntity(
        id = 31,
        name = "TGV",
        subtitle = "Le Trio Grande Vitesse",
        description = "Le shooter français mythique au départ immédiat : l'association sans compromis de Tequila, Gin et Vodka en proportions égales.",
        category = "Shooters",
        flavorProfile = "Puissant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 35.0,
        ingredients = listOf(
            RecipeIngredient("Tequila", "Tequila Blanco", 1.5, "1/3 shooter", false, "liquor"),
            RecipeIngredient("Gin", "Gin sec", 1.5, "1/3 shooter", false, "liquor"),
            RecipeIngredient("Vodka", "Vodka pure", 1.5, "1/3 shooter", false, "liquor")
        ),
        steps = listOf(
            "Verser à parts égales la tequila, le gin et la vodka direct au verre à shooter.",
            "Optionnellement, passer au shaker quelques secondes avec de la glace pour un shot frappé glacial.",
            "Consommer immédiatement cul-sec."
        ),
        garnish = "Finition frappée pure.",
        glassware = "Verre Shooter 6cl",
        shakeSeconds = 4,
        rating = 4.6,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/mx31hv1487602979.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 32. Madeleine
    CocktailEntity(
        id = 32,
        name = "Madeleine",
        subtitle = "La Nostalgie Pâtissière en Shooter",
        description = "Une illusion aromatique saisissante : l'amaretto combiné au triple sec et à l'ananas reproduit fidèlement la saveur de la célèbre madeleine sortie du four.",
        category = "Shooters",
        flavorProfile = "Doux/Amande",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Cointreau", "Liqueur d'orange", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Amaretto", "Liqueur d'amande douce", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Jus d'ananas", "Pur jus", 1.5, "1/3 shooter", false, "nutrition")
        ),
        steps = listOf(
            "Verser le Cointreau, l'Amaretto et le trait de jus d'ananas dans le shaker avec de la glace.",
            "Shaker vivement pendant 6 secondes pour émulsionner les saveurs.",
            "Filtrer dans un verre à shooter."
        ),
        garnish = "Voile d'orange.",
        glassware = "Verre Shooter",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/rwsyyu1483388181.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 33. Cervelle de Singe
    CocktailEntity(
        id = 33,
        name = "Cervelle de Singe",
        subtitle = "L'Effet Coagulé Visuel & Festif",
        description = "Le plus célèbre shooter d'Halloween : une suspension crémeuse saisissante causée par la réaction du Baileys dans la liqueur de pêche traversée de grenadine.",
        category = "Shooters",
        flavorProfile = "Crémeux/Sanglant",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur de pêche", "Pêche transparente", 2.5, "1/2 shooter", false, "invert_colors"),
            RecipeIngredient("Baileys", "Crème de whisky", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Sirop de grenadine", "Gouttes rouge vif", 0.5, "Quelques gouttes", false, "water_drop")
        ),
        steps = listOf(
            "Verser la liqueur de pêche au fond du verre à shooter.",
            "À l'aide d'une paille ou d'une pipette, faire couler délicatement le Baileys au centre : il coagule en formant des circonvolutions.",
            "Déposer quelques gouttes de grenadine qui traversent la crème pour un effet spectaculaire."
        ),
        garnish = "Effet visuel spectaculaire.",
        glassware = "Verre Shooter transparent",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/rz5aun1504389701.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 34. Old Fashioned
    CocktailEntity(
        id = 34,
        name = "Old Fashioned",
        subtitle = "Le Doyen Aristocrate du Bar",
        description = "La quintessence de l'art du cocktail : un morceau de sucre saturé de bitters, fondu dans la chaleur noble et vanillée d'un grand bourbon américain.",
        category = "Classiques",
        flavorProfile = "Corsé/Boisé",
        prepTimeMinutes = 5,
        difficulty = "Expert",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Bourbon", "Bourbon de réserve pur grain", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Sucre en morceau", "Sucre roux de canne", 1.0, "1 morceau", false, "water_drop"),
            RecipeIngredient("Angostura Bitters", "Bitters aromatiques", 0.5, "2 traits", false, "invert_colors"),
            RecipeIngredient("Eau gazeuse", "Trait de dilution", 1.0, "1 trait", false, "nutrition")
        ),
        steps = listOf(
            "Placer le morceau de sucre au fond du verre Old Fashioned.",
            "Imbiber avec les 2 traits d'Angostura et le trait d'eau gazeuse, puis écraser au pilon jusqu'à dissolution complète.",
            "Ajouter un gros glaçon translucide et verser la moitié du bourbon. Remuer longuement à la cuillère de bar.",
            "Ajouter le reste du bourbon et de la glace, puis mélanger encore 30 secondes pour une texture enveloppante."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Verre Tumbler lourd en cristal",
        shakeSeconds = 0,
        rating = 5.0,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg",
        isFavorite = true,
        vibeTag = "Dim-lit Speakeasy & Vinyl Sessions"
    ),

    // 35. Dark 'n' Stormy
    CocktailEntity(
        id = 35,
        name = "Dark 'n' Stormy",
        subtitle = "La Tempête Tropicale des Bermudes",
        description = "L'emblème des marins des Bermudes : le nuage sombre d'un rhum ambré épicé flottant sur les flots houleux d'une ginger beer pétillante.",
        category = "Cocktails",
        flavorProfile = "Épicé/Corsé",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Rhum ambré ou épicé", "Black Seal Rum des Bermudes", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Ginger Beer", "Gingembre fermenté ardent", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre highball de glaçons.",
            "Verser le jus de citron vert puis allonger de ginger beer pétillante.",
            "Faire couler délicatement le rhum épicé sur le dessus à l'aide d'une cuillère pour créer le dégradé de tempête sombre."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/t1tn0s1504374905.jpg",
        isFavorite = false,
        vibeTag = "Maritime Storm & Acoustic Rock"
    ),

    // 36. Paloma
    CocktailEntity(
        id = 36,
        name = "Paloma",
        subtitle = "La Reine Pétillante de Jalisco",
        description = "Le cocktail le plus dégusté au Mexique : l'alliance désaltérante de tequila blanco, d'agrumes vifs et de soda au pamplemousse rose.",
        category = "Cocktails",
        flavorProfile = "Acide/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Tequila Blanco", "100% agave pur", 5.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Soda au pamplemousse", "Pamplemousse rose pétillant", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.0, "1 trait", false, "nutrition")
        ),
        steps = listOf(
            "Frotter le bord du verre de jus de citron vert et tremper facultativement dans du sel.",
            "Remplir le verre de gros glaçons.",
            "Verser la tequila blanco et le trait de jus de citron vert.",
            "Allonger avec le soda au pamplemousse rose direct au verre et remuer doucement."
        ),
        garnish = "Tranche de pamplemousse.",
        glassware = "Verre Highball ou Collins",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/samm5j1513706393.jpg",
        isFavorite = false,
        vibeTag = "Summer Sunset & Cumbia House"
    ),

    // 37. Spritz Saint-Germain
    CocktailEntity(
        id = 37,
        name = "Spritz Saint-Germain",
        subtitle = "L'Élixir Floral Parisien",
        description = "L'alternative florale et raffinée au spritz traditionnel : les mille fleurs de sureau sauvage cueillies à la main illuminées par le Prosecco brut.",
        category = "Cocktails",
        flavorProfile = "Floral/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 11.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur Saint-Germain", "Liqueur artisanale de sureau", 4.0, "1 shooter", false, "invert_colors"),
            RecipeIngredient("Prosecco", "Prosecco brut DOC", 6.0, "1/3 verre", false, "liquor"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 6.0, "1/3 verre", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un grand verre ballon de glaçons.",
            "Verser la liqueur Saint-Germain puis le Prosecco bien frais.",
            "Compléter avec l'eau gazeuse et remuer délicatement à la cuillère de bar."
        ),
        garnish = "Tête de menthe.",
        glassware = "Grand verre à pied",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/125w0o1630407389.jpg",
        isFavorite = false,
        vibeTag = "Parisian Rooftop & French Touch"
    ),

    // 38. Dry Martini
    CocktailEntity(
        id = 38,
        name = "Dry Martini",
        subtitle = "Le Roi Absolu de l'Élégance Sec",
        description = "Le cocktail le plus sophistiqué de l'histoire : un gin ultra-froid simplement caressé par les herbes aromatiques d'un vermouth sec de précision.",
        category = "Cocktails",
        flavorProfile = "Sec/Corsé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin premium", 6.0, "1 grand shooter", false, "liquor"),
            RecipeIngredient("Vermouth sec", "Noilly Prat ou Dolin", 1.0, "1 cuil. à café", false, "liquor")
        ),
        steps = listOf(
            "Remplir un verre à mélange de gros glaçons purs.",
            "Verser le gin et le vermouth sec.",
            "Mélanger à la cuillère de bar avec fluidité pendant 30 secondes pour atteindre une clarté et un glaçage parfaits sans troubler le liquide.",
            "Filtrer dans une coupe à martini glacée."
        ),
        garnish = "Olive verte ou zeste de citron.",
        glassware = "Coupe à Martini classique",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://images.unsplash.com/photo-1510626176961-4b57d4fbad03?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "James Bond Suite & Cool Jazz"
    ),

    // 39. Manhattan
    CocktailEntity(
        id = 39,
        name = "Manhattan",
        subtitle = "L'Héritage New-Yorkais Intemporel",
        description = "Créé au Manhattan Club en 1870 : le caractère épicé du Rye Whiskey sublimé par la richesse sucrée du vermouth rouge et les bitters.",
        category = "Cocktails",
        flavorProfile = "Corsé/Herbacé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Rye Whiskey", "Whiskey de seigle épicé", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Vermouth Rouge", "Vermouth doux aromatisé", 2.0, "1 cuil. à soupe", false, "liquor"),
            RecipeIngredient("Angostura Bitters", "Bitters aromatiques", 0.5, "2 traits", false, "invert_colors")
        ),
        steps = listOf(
            "Verser le Rye Whiskey, le vermouth rouge et les 2 traits d'Angostura dans un verre à mélange avec beaucoup de glace.",
            "Remuer à la cuillère de bar pendant 25 secondes.",
            "Filtrer dans une coupe à cocktail rafraîchie."
        ),
        garnish = "Cerise amarena.",
        glassware = "Coupe Cocktail vintage",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/yk70e31606771240.jpg",
        isFavorite = false,
        vibeTag = "New York Speakeasy & Classic Soul"
    ),

    // 40. Bramble
    CocktailEntity(
        id = 40,
        name = "Bramble",
        subtitle = "Le Nectar de Mûres Britannique",
        description = "Créé par Dick Bradsell à Londres : un sour éclatant de gin et citron baigné d'un voile pourpre de crème de mûre sauvage.",
        category = "Cocktails",
        flavorProfile = "Fruité/Botanique",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Dry Gin classique", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Crème de mûre", "Liqueur de mûres sauvages", 1.5, "1 cuil. à café en filet", false, "invert_colors")
        ),
        steps = listOf(
            "Shaker le gin, le jus de citron et le sirop de sucre avec de la glace.",
            "Verser dans un verre Old Fashioned rempli de glace pilée.",
            "Verser la crème de mûre en filet à la fin sur le dessus pour laisser s'infiltrer le dégradé rubis."
        ),
        garnish = "Mûres fraîches.",
        glassware = "Verre Old Fashioned",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1514362545857-3bc16c4c7d1b?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Soho Nights & Brit Pop"
    ),

    // 41. Boulevardier
    CocktailEntity(
        id = 41,
        name = "Boulevardier",
        subtitle = "Le Cousin Parisien du Negroni",
        description = "Inventé au Harry's New York Bar à Paris dans les années 1920 : la chaleur ronde du bourbon remplace le gin face au Campari et vermouth doux.",
        category = "Cocktails",
        flavorProfile = "Amer/Boisé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Bourbon", "Bourbon de caractère", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Campari", "Bitter aromatique rouge", 3.0, "1 shooter", false, "invert_colors"),
            RecipeIngredient("Vermouth Rouge", "Vermouth italien doux", 3.0, "1 shooter", false, "liquor")
        ),
        steps = listOf(
            "Verser le bourbon, le Campari et le vermouth rouge dans un verre à mélange avec des glaçons.",
            "Remuer à la cuillère de bar pendant 30 secondes pour une dilution maîtrisée.",
            "Filtrer dans un verre Old Fashioned sur un gros glaçon taillé."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Verre Old Fashioned",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/typuyq1439456976.jpg",
        isFavorite = false,
        vibeTag = "Art-Déco Speakeasy & Hot Club Jazz"
    ),

    // 42. Virgin Colada
    CocktailEntity(
        id = 42,
        name = "Virgin Colada",
        subtitle = "L'Onctuosité Coco Sans Alcool",
        description = "0% alcool : tout le plaisir velouté des îles grâce à une mousse riche de crème de coco et de pur jus d'ananas mûri au soleil.",
        category = "Mocktails",
        flavorProfile = "Doux/Crémeux",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas doré", 12.0, "1/2 grand verre", false, "nutrition"),
            RecipeIngredient("Crème de coco", "Crème de coco riche", 4.0, "2 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Mettre le jus d'ananas, la crème de coco et le sirop de sucre dans le shaker ou le blender avec de la glace pilée.",
            "Mixer ou shaker vigoureusement pendant 15 secondes pour obtenir une mousse dense.",
            "Servir dans un grand verre tropical sur lit de glace."
        ),
        garnish = "Triangle d'ananas.",
        glassware = "Verre Hurricane Tropical",
        shakeSeconds = 15,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1527661591475-527312dd65f5?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Chill Acoustic & Beach Breeze"
    ),

    // 43. Safe Sex on the Beach
    CocktailEntity(
        id = 43,
        name = "Safe Sex on the Beach",
        subtitle = "L'Océan Fruité Détox 0%",
        description = "0% alcool : un délice vibrant combinant le jus d'orange pressé, le velouté de pêche et le nappage rubis acidulé de canneberge.",
        category = "Mocktails",
        flavorProfile = "Fruité/Sucré",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus d'orange", 6.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Jus de cranberry", "Canneberge pure", 6.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Sirop de pêche", "Sirop doux", 2.0, "1 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Verser le jus d'orange et le sirop de pêche dans le shaker avec des glaçons.",
            "Shaker et verser dans un verre haut avec de la glace.",
            "Napper délicatement avec le jus de cranberry pour un dégradé coucher de soleil sans alcool."
        ),
        garnish = "Tranche d'orange.",
        glassware = "Verre Highball",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vuquyv1468876052.jpg",
        isFavorite = false,
        vibeTag = "Sunset Chill & Tropical Vibes"
    ),

    // 44. Iced Tea Pêche Maison
    CocktailEntity(
        id = 44,
        name = "Iced Tea Pêche Maison",
        subtitle = "L'Infusion Glacée Revigorante",
        description = "0% alcool : la quintessence du thé glacé maison alliant l'astringence élégante d'un thé noir froid, la douceur de la pêche et le peps du citron jaune.",
        category = "Mocktails",
        flavorProfile = "Frais/Thé",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Thé noir froid", "Infusion de thé noir pure", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Sirop de pêche", "Sirop de pêche mûre", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Infuser et refroidir un thé noir parfumé.",
            "Verser le thé, le sirop de pêche et le jus de citron direct au verre sur une abondance de glaçons.",
            "Remuer pour harmoniser la fraîcheur et déguster bien glacé."
        ),
        garnish = "Tranche de pêche et citron.",
        glassware = "Verre Tumbler haut",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/qxvypq1468924331.jpg",
        isFavorite = false,
        vibeTag = "Summer Terrace & Acoustic Beats"
    ),

    // 45. Bloody Mary
    CocktailEntity(
        id = 45,
        name = "Bloody Mary",
        subtitle = "Le Grand Classique Épicé & Salé",
        description = "Le plus célèbre des réveils salés : vodka glacée, coulis de tomate épais, traits de Worcestershire et piquant du Tabasco avec sel de céleri.",
        category = "Cocktails",
        flavorProfile = "Salé/Épicé",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche", 5.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus de tomate", "Pur jus épais", 12.0, "1 grand verre", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sauce Worcestershire", "Sauce anglaise relevée", 0.5, "3 traits", false, "invert_colors"),
            RecipeIngredient("Tabasco", "Piment rouge vif", 0.2, "2 traits", false, "invert_colors"),
            RecipeIngredient("Sel de céleri & Poivre", "Assaisonnement moulu", 0.0, "1 pincée", false, "spa")
        ),
        steps = listOf(
            "Verser la vodka, le jus de tomate, le jus de citron, la Worcestershire et le Tabasco dans un verre à mélange avec des glaçons.",
            "Ajouter le sel de céleri et un tour de moulin à poivre noir.",
            "Rouler délicatement d'un shaker à l'autre (ou shaker légèrement) sans trop casser la pulpe de tomate.",
            "Verser avec la glace dans un verre tumbler haut."
        ),
        garnish = "Branche de céleri.",
        glassware = "Verre Tumbler Highball",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/t6caa21582485702.jpg",
        isFavorite = false,
        vibeTag = "Sunday Brunch & Jazz Chilled"
    ),

    // 46. Tommy's Margarita
    CocktailEntity(
        id = 46,
        name = "Tommy's Margarita",
        subtitle = "La Pureté Moderne de San Francisco",
        description = "Créé par Julio Bermejo au Tommy's : la liqueur d'orange est remplacée par le nectar d'agave biologique pour sublimer la tequila 100% agave.",
        category = "Cocktails",
        flavorProfile = "Acide/Sucré",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Tequila Reposado", "100% agave vieilli en fût", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.5, "1 citron vert entier", false, "nutrition"),
            RecipeIngredient("Sirop d'agave", "Nectar d'agave bleu bio", 1.5, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Verser la tequila reposado, le jus de citron vert frais et le sirop d'agave dans le shaker avec de gros glaçons.",
            "Shaker vivement pendant 10 secondes pour une dilution pure et texturée.",
            "Filtrer dans un verre Old Fashioned sur un gros cube de glace pure."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre Old Fashioned",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/srpxxp1441209622.jpg",
        isFavorite = true,
        vibeTag = "San Francisco Speakeasy & Modern Grooves"
    ),

    // 47. Pisco Sour
    CocktailEntity(
        id = 47,
        name = "Pisco Sour",
        subtitle = "Le Joyau Mythique des Andes",
        description = "Le trésor national péruvien et chilien : l'eau-de-vie de raisin Pisco sublimée par une mousse d'oeuf aérienne et quelques gouttes aromatiques d'Angostura.",
        category = "Cocktails",
        flavorProfile = "Doux/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Pisco", "Eau-de-vie de raisin Quebranta", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 3.0, "1 citron vert", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne 2:1", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Blanc d'oeuf", "Pour la collerette mousseuse", 1.0, "1 blanc frais", false, "water_drop"),
            RecipeIngredient("Angostura Bitters", "Gouttes aromatiques de surface", 0.2, "3 gouttes", false, "invert_colors")
        ),
        steps = listOf(
            "Placer le Pisco, le citron vert, le sirop et le blanc d'oeuf dans le shaker.",
            "Dry shaker sans glace pendant 10 secondes pour monter une émulsion dense.",
            "Ajouter des glaçons compacts et shaker vigoureusement pendant 12 secondes.",
            "Double-filtrer dans une coupe et déposer 3 gouttes d'Angostura sur la mousse."
        ),
        garnish = "Gouttes d'Angostura.",
        glassware = "Coupe à cocktail ou Verre Amara",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/tsssur1439907622.jpg",
        isFavorite = false,
        vibeTag = "Andean Sunset & Latin Folk House"
    ),

    // 48. French 75
    CocktailEntity(
        id = 48,
        name = "French 75",
        subtitle = "Le Canon Pétillant des Années 20",
        description = "Baptisé d'après le canon français de 75mm : la puissance vivifiante du gin et du citron propulsée par le raffinement d'un champagne brut.",
        category = "Cocktails",
        flavorProfile = "Pétillant/Acide",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 14.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne liquide", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Champagne", "Brut bien frais", 8.0, "Allonger", false, "liquor")
        ),
        steps = listOf(
            "Verser le gin, le jus de citron et le sirop de sucre dans le shaker avec de la glace.",
            "Shaker vigoureusement pendant 8 secondes.",
            "Filtrer dans une flûte à champagne rafraîchie.",
            "Allonger doucement au champagne frais pour créer une mousse dorée fine."
        ),
        garnish = "Zeste de citron.",
        glassware = "Flûte à Champagne",
        shakeSeconds = 8,
        rating = 4.9,
        imageUrl = "https://images.unsplash.com/photo-1597075687490-8f673c6c17f6?auto=format&fit=crop&w=800&q=80",
        isFavorite = true,
        vibeTag = "Années Folles & Electro Swing"
    ),

    // 49. Bellini
    CocktailEntity(
        id = 49,
        name = "Bellini",
        subtitle = "L'Aura Vénitienne du Harry's Bar",
        description = "Inventé à Venise par Giuseppe Cipriani en 1948 : la douceur veloutée de la purée de pêche blanche et l'effervescence délicate du Prosecco.",
        category = "Cocktails",
        flavorProfile = "Fruité/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Purée de pêche", "Pêche blanche fraîche", 4.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Prosecco", "Prosecco DOC brut", 8.0, "Allonger", false, "liquor")
        ),
        steps = listOf(
            "Déposer la purée de pêche fraîche bien froide au fond d'une flûte.",
            "Verser doucement une première moitié de Prosecco et mélanger délicatement à la cuillère.",
            "Compléter avec le reste du Prosecco pour conserver le pétillement sans déborder."
        ),
        garnish = "Tranche de pêche.",
        glassware = "Flûte à Champagne vénitienne",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/eaag491504367543.jpg",
        isFavorite = false,
        vibeTag = "Venice Canal & Classical Ambient"
    ),

    // 50. Mimosa
    CocktailEntity(
        id = 50,
        name = "Mimosa",
        subtitle = "Le Rayon de Soleil du Brunch",
        description = "Le cocktail du dimanche matin par excellence : moitié pur jus d'orange gorgé de soleil, moitié champagne effervescent.",
        category = "Cocktails",
        flavorProfile = "Fruité/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus d'orange pressée minute", 6.0, "1/2 flûte", false, "nutrition"),
            RecipeIngredient("Champagne ou Prosecco", "Brut bien frappé", 6.0, "1/2 flûte", false, "liquor")
        ),
        steps = listOf(
            "Verser le jus d'orange frais pressé et tamisé dans une flûte à champagne.",
            "Allonger délicatement de champagne ou Prosecco très frais en penchant la flûte.",
            "Remuer d'un tour de cuillère léger pour harmoniser sans dissiper les bulles."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Flûte à Champagne",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/juhcuu1504370685.jpg",
        isFavorite = false,
        vibeTag = "Morning Acoustic & Sunny Rooftop"
    ),

    // 51. Gimlet
    CocktailEntity(
        id = 51,
        name = "Gimlet",
        subtitle = "L'Incisif Historique de la Royal Navy",
        description = "L'arme anti-scorbut devenue légende : le mariage strict et tranchant entre la puissance du gin et l'acidité confite d'un cordial de citron vert.",
        category = "Cocktails",
        flavorProfile = "Acide/Herbacé",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin classique", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Cordial au citron vert", "Lime cordial type Rose's", 2.5, "1/2 shooter", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre à mélange de glace compacte.",
            "Verser le gin et le cordial de citron vert.",
            "Mélanger à la cuillère de bar pendant 25 secondes jusqu'à glaçage.",
            "Filtrer dans une coupe à cocktail rafraîchie."
        ),
        garnish = "Zeste de citron vert.",
        glassware = "Coupe à Cocktail vintage",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/3xgldt1513707271.jpg",
        isFavorite = false,
        vibeTag = "Noir Cinema & Cool Jazz"
    ),

    // 52. Black Russian
    CocktailEntity(
        id = 52,
        name = "Black Russian",
        subtitle = "L'Origine Ténébreuse du White Russian",
        description = "Créé à l'Hôtel Métropole de Bruxelles en 1949 : la rencontre pure et ténébreuse entre la clarté tranchante de la vodka et la richesse torréfiée du café.",
        category = "Cocktails",
        flavorProfile = "Corsé/Café",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka de grain pure", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Liqueur de café", "Kahlúa mexicaine", 2.0, "1 cuil. à soupe", false, "invert_colors")
        ),
        steps = listOf(
            "Remplir un verre Old Fashioned de gros cubes de glace purs.",
            "Verser la vodka directement sur les glaçons.",
            "Ajouter la liqueur de café et mélanger délicatement pendant 15 secondes."
        ),
        garnish = "Aucune.",
        glassware = "Verre Old Fashioned Lowball",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/8oxlqf1606772765.jpg",
        isFavorite = false,
        vibeTag = "Deep House & Dark Lounge"
    ),

    // 53. Sidecar
    CocktailEntity(
        id = 53,
        name = "Sidecar",
        subtitle = "La Noblesse Boisée du Cognac",
        description = "Le plus aristocratique des sours : la chaleur boisée d'un cognac français rehaussée par la fraîcheur d'agrumes et un col de sucre caramélisé.",
        category = "Cocktails",
        flavorProfile = "Acide/Boisé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Cognac", "Cognac VSOP français", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'oranges amères", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Sucre fin", "Pour la bordure du verre", 0.0, "Bordure", true, "water_drop")
        ),
        steps = listOf(
            "Frotter le bord d'une coupe avec un quartier de citron et tremper dans du sucre fin pour créer une fine bordure.",
            "Verser le cognac, le Cointreau et le jus de citron dans le shaker rempli de glace.",
            "Shaker vigoureusement pendant 10 secondes et double-filtrer dans la coupe préparée."
        ),
        garnish = "Bordure de sucre.",
        glassware = "Coupe à Cocktail Art-Déco",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/x72sik1606854964.jpg",
        isFavorite = true,
        vibeTag = "Parisian Speakeasy & Gypsy Jazz"
    ),

    // 54. Mint Julep
    CocktailEntity(
        id = 54,
        name = "Mint Julep",
        subtitle = "L'Oasis Givrée du Kentucky Derby",
        description = "Le rituel du Sud des États-Unis : une timbale en étain recouverte de givre, remplie de glace pilée, de menthe aromatique et d'un bourbon puissant.",
        category = "Cocktails",
        flavorProfile = "Frais/Corsé",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Bourbon", "Kentucky Straight Bourbon", 6.0, "1.5 shooters", false, "liquor"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne liquide", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Feuilles de menthe", "Menthe fraîche", 0.0, "8 à 10 feuilles", false, "spa")
        ),
        steps = listOf(
            "Placer les feuilles de menthe et le sirop de sucre dans la timbale en étain.",
            "Piler très doucement pour réveiller les essences végétales sans broyer la tige.",
            "Remplir à mi-hauteur de glace pilée, ajouter la moitié du bourbon et mélanger.",
            "Combler d'une montagne de glace pilée, verser le reste de bourbon et laisser givrer l'extérieur de la timbale."
        ),
        garnish = "Gros bouquet de menthe.",
        glassware = "Timbale Julep en étain",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/squyyq1439907312.jpg",
        isFavorite = false,
        vibeTag = "Southern Blues & Folk Sessions"
    ),

    // 55. Irish Coffee
    CocktailEntity(
        id = 55,
        name = "Irish Coffee",
        subtitle = "Le Réconfort Légendaire de Shannon",
        description = "Inventé pour réchauffer les passagers des hydravions en Irlande : whiskey chaud et café serré sous une généreuse couche de crème fouettée froide.",
        category = "Cocktails",
        flavorProfile = "Chaud/Café",
        prepTimeMinutes = 5,
        difficulty = "Expert",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Whiskey Irlandais", "Whiskey doux triplement distillé", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Café chaud", "Café filtre ou expresso allongé", 9.0, "1/2 mug", false, "nutrition"),
            RecipeIngredient("Sucre de canne", "Sucre roux cassonade", 1.0, "1 cuillère", false, "water_drop"),
            RecipeIngredient("Crème liquide fouettée", "Légèrement montée au fouet", 3.0, "Nappage", false, "water_drop")
        ),
        steps = listOf(
            "Préchauffer le verre spécial avec de l'eau bouillante, puis vider.",
            "Verser le whiskey irlandais et le sucre roux, mélanger jusqu'à dissolution complète.",
            "Verser le café très chaud et remuer une dernière fois.",
            "Faire couler délicatement la crème fraîchement fouettée sur le dos d'une cuillère pour la faire flotter au sommet sans qu'elle ne se mélange."
        ),
        garnish = "Poudre de cacao.",
        glassware = "Verre à Irish Coffee avec anse",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://images.unsplash.com/photo-1551024709-8f23befc6f87?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Winter Fireside & Celtic Folk"
    ),

    // 56. Zombie
    CocktailEntity(
        id = 56,
        name = "Zombie",
        subtitle = "Le Monstre Tiki Inoxydable",
        description = "Créé par Donn Beach en 1934 : une trilogie de rhums caribéens dont un overproof volcanique mariée aux nectars tropicaux et grenadine.",
        category = "Cocktails",
        flavorProfile = "Puissant/Exotique",
        prepTimeMinutes = 4,
        difficulty = "Expert",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum caribéen", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Rhum ambré", "Rhum vieux boisé", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Rhum overproof", "Rhum 69° ou plus", 1.0, "1 trait", false, "liquor"),
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas", 4.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine concentrée", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Placer tous les ingrédients avec de la glace pilée dans le shaker.",
            "Shaker avec intensité pendant 12 secondes pour frapper ce mélange puissant.",
            "Verser l'intégralité du shaker dans un grand verre Tiki.",
            "Déposer la demi-passion sur la glace pilée."
        ),
        garnish = "Demi fruit de la passion.",
        glassware = "Verre Tiki sculpté",
        shakeSeconds = 12,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/2en3jk1509557725.jpg",
        isFavorite = false,
        vibeTag = "Tiki Party & Jungle Drums"
    ),

    // 57. Tom Collins
    CocktailEntity(
        id = 57,
        name = "Tom Collins",
        subtitle = "La Limonade Botanique Pétillante",
        description = "L'ancêtre mythique des collins : le peps désaltérant d'une vraie citronnade maison associée à la finesse d'un gin floral allongé d'eau gazeuse.",
        category = "Cocktails",
        flavorProfile = "Acide/Pétillant",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 11.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Old Tom ou Dry Gin", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.5, "1/2 citron entier", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Shaker le gin, le jus de citron et le sirop de sucre avec des glaçons pendant 8 secondes.",
            "Filtrer dans un grand verre highball ou collins rempli de glaçons neufs.",
            "Allonger à l'eau gazeuse fraîche et remuer délicatement."
        ),
        garnish = "Cerise et tranche de citron.",
        glassware = "Verre Collins haut",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7cll921606854636.jpg",
        isFavorite = false,
        vibeTag = "Summer Garden & Sunny Soul"
    ),

    // 58. White Lady
    CocktailEntity(
        id = 58,
        name = "White Lady",
        subtitle = "La Dame Blanche Épurée des Palaces",
        description = "Un chef-d'œuvre de pureté créé à Londres dans les années 1920 : l'alliance soyeuse du gin, du triple sec et du citron blanc surmontée d'une collerette mousseuse.",
        category = "Cocktails",
        flavorProfile = "Acide/Sec",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 22.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Dry Gin raffiné", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'oranges", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Blanc d'oeuf", "Pour la texture velours", 1.0, "1 blanc frais", false, "water_drop")
        ),
        steps = listOf(
            "Placer le gin, le Cointreau, le citron et le blanc d'oeuf dans le shaker.",
            "Effectuer un dry shake (sans glaçons) pour bien monter l'émulsion crémeuse.",
            "Ajouter la glace et shaker énergiquement pendant 10 secondes.",
            "Double-filtrer dans une coupe cocktail glacée."
        ),
        garnish = "Zeste de citron.",
        glassware = "Coupe à Cocktail rafraîchie",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/jofsaz1504352991.jpg",
        isFavorite = false,
        vibeTag = "Palace Lounge & Chic Nu-Jazz"
    ),

    // 59. Aviation
    CocktailEntity(
        id = 59,
        name = "Aviation",
        subtitle = "Le Bleu Céleste des Pionniers",
        description = "Une légende des débuts de l'aviation : une teinte bleu pastel céleste incomparable apportée par la crème de violette et la liqueur de marasquin.",
        category = "Cocktails",
        flavorProfile = "Floral/Acide",
        prepTimeMinutes = 3,
        difficulty = "Expert",
        alcoholPercentage = 22.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Gin sec aromatique", 4.5, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Liqueur de Marasquin", "Luxardo Maraschino", 1.5, "1 cuil. à café", false, "invert_colors"),
            RecipeIngredient("Crème de violette", "Pour la nuance céleste", 0.5, "1 cuil. à café", false, "invert_colors")
        ),
        steps = listOf(
            "Verser le gin, le jus de citron, la liqueur de marasquin et la crème de violette dans le shaker rempli de glace.",
            "Shaker vigoureusement pendant 12 secondes jusqu'à glaçage extérieur.",
            "Double-filtrer dans une coupe à cocktail pour admirer la robe bleu violacé céleste."
        ),
        garnish = "Cerise au marasquin.",
        glassware = "Coupe à Cocktail vintage",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/trbplb1606855233.jpg",
        isFavorite = false,
        vibeTag = "Early Flight Lounge & Dream Ambient"
    ),

    // 60. Apple Martini
    CocktailEntity(
        id = 60,
        name = "Apple Martini",
        subtitle = "L'Appletini Vert Fluorescent",
        description = "Le cocktail culte de Los Angeles des années 90 : la fraîcheur acidulée d'une liqueur de pomme verte Granny Smith vivifiée par la vodka pure.",
        category = "Cocktails",
        flavorProfile = "Fruité/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de pomme verte", "Manzana ou Sour Apple", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, la liqueur de pomme verte et le jus de citron dans un shaker rempli de glace.",
            "Shaker vivement pendant 10 secondes.",
            "Filtrer dans un verre à martini pour admirer sa teinte vert fluo éclatante."
        ),
        garnish = "Tranche de pomme.",
        glassware = "Verre à Martini",
        shakeSeconds = 10,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/sbffau1504389764.jpg",
        isFavorite = false,
        vibeTag = "Retro 90s Clubbing & Pop Beats"
    ),

    // 61. Midori Illusion
    CocktailEntity(
        id = 61,
        name = "Midori Illusion",
        subtitle = "L'Éclat Émeraude Électrique",
        description = "L'illusion d'une nuit électrique : liqueur japonaise de melon vert Midori fusionnée à l'ananas tropical et aux agrumes.",
        category = "Cocktails",
        flavorProfile = "Fruité/Électrique",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur de melon Midori", "Melon vert japonais", 4.0, "1 shooter", false, "invert_colors"),
            RecipeIngredient("Vodka", "Vodka pure", 1.5, "1 trait", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'orange", 1.5, "1 trait", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas", 4.0, "2 cuil. à soupe", false, "nutrition")
        ),
        steps = listOf(
            "Verser le Midori, la vodka, le Cointreau, le citron et le jus d'ananas dans un shaker avec des glaçons.",
            "Shaker énergiquement pendant 8 secondes.",
            "Verser avec des glaçons dans un verre highball."
        ),
        garnish = "Triangle d'ananas.",
        glassware = "Verre Highball ou Ouragan",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/bmxmyq1630407098.jpg",
        isFavorite = false,
        vibeTag = "Tokyo Nightlife & Electro Wave"
    ),

    // 62. Slippery Nipple
    CocktailEntity(
        id = 62,
        name = "Slippery Nipple",
        subtitle = "Le Duo Crémeux & Anisé",
        description = "Un classique des shooters de pub : la fraîcheur anisée et puissante de la Sambuca italienne surmontée d'un nappage onctueux de Baileys.",
        category = "Shooters",
        flavorProfile = "Crémeux/Anisé",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Sambuca", "Liqueur d'anis blanc", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Baileys", "Crème de whisky", 2.0, "1/2 shooter", false, "water_drop"),
            RecipeIngredient("Sirop de grenadine", "Goutte centrale", 0.3, "1 goutte", false, "water_drop")
        ),
        steps = listOf(
            "Verser la Sambuca directement au fond du verre à shooter.",
            "À l'aide du dos d'une cuillère de bar posée contre la paroi, verser très délicatement le Baileys pour créer deux strates parfaitement nettes.",
            "Déposer une goutte de grenadine qui coule doucement au centre.",
            "Boire d'un trait cul-sec."
        ),
        garnish = "Strates contrastées.",
        glassware = "Verre Shooter transparent",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/l9tgru1551439725.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 63. Blowjob
    CocktailEntity(
        id = 63,
        name = "Blowjob",
        subtitle = "Le Shooter Gourmand à la Chantilly",
        description = "Le plus festif et coquin des shooters : café torréfié et crème irlandaise couronnés d'une généreuse pointe de crème chantilly.",
        category = "Shooters",
        flavorProfile = "Crémeux/Café",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur de café", "Kahlúa", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Baileys", "Crème de whisky", 1.5, "1/3 shooter", false, "water_drop"),
            RecipeIngredient("Crème chantilly", "En topping généreux", 0.0, "Topping", false, "water_drop")
        ),
        steps = listOf(
            "Verser la liqueur de café au fond du shooter.",
            "Superposer délicatement le Baileys avec le dos d'une cuillère.",
            "Recouvrir d'un dôme généreux de crème chantilly.",
            "Règle du jeu : boire sans utiliser les mains !"
        ),
        garnish = "Chantilly gourmande.",
        glassware = "Verre Shooter",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/spuurv1468878783.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 64. Baby Guinness
    CocktailEntity(
        id = 64,
        name = "Baby Guinness",
        subtitle = "La Pinte Irlandaise Miniature",
        description = "Une illusion visuelle d'une pinte de stout Guinness en modèle miniature : liqueur de café sombre et col blanc parfait de crème irlandaise.",
        category = "Shooters",
        flavorProfile = "Crémeux/Café",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Liqueur de café", "Kahlúa ou Tia Maria", 2.5, "3/4 shooter", false, "invert_colors"),
            RecipeIngredient("Baileys", "Irish Cream", 1.5, "Col blanc", false, "water_drop")
        ),
        steps = listOf(
            "Remplir le verre à shooter aux 3/4 avec la liqueur de café.",
            "Faire couler délicatement le Baileys sur le dos d'une cuillère de bar pour créer la collerette blanche imitant la mousse d'une Guinness.",
            "Déguster d'un trait."
        ),
        garnish = "Col blanc miniature.",
        glassware = "Verre Shooter épais",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/swqurw1454512730.jpg",
        isFavorite = true,
        vibeTag = "Clubbing & Electro"
    ),

    // 65. Blue Hawaiian
    CocktailEntity(
        id = 65,
        name = "Blue Hawaiian",
        subtitle = "Le Paradis Bleu de Waikiki",
        description = "Créé par Harry Yee à Honolulu en 1957 : la couleur lagon azur du curaçao bleu alliée à l'ananas tropical, au rhum et à la crème de coco.",
        category = "Cocktails",
        flavorProfile = "Exotique",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum caribéen", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Curaçao Bleu", "Liqueur bleue d'orange", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Crème de coco", "Crème de coco riche", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas", 6.0, "1/3 verre", false, "nutrition")
        ),
        steps = listOf(
            "Verser le rhum blanc, le curaçao bleu, la crème de coco et le jus d'ananas dans le shaker rempli de glace pilée.",
            "Shaker vivement pendant 10 secondes.",
            "Verser sans filtrer dans un grand verre tropical sur lit de glace."
        ),
        garnish = "Ananas et cerise.",
        glassware = "Verre Poco Grande ou Ouragan",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/ujoh9x1504882987.jpg",
        isFavorite = false,
        vibeTag = "Honolulu Surf & Ukulele Beats"
    ),

    // 66. Lynchburg Lemonade
    CocktailEntity(
        id = 66,
        name = "Lynchburg Lemonade",
        subtitle = "La Citronnade Mythique du Tennessee",
        description = "L'esprit du Tennessee : la rondeur vanillée du Jack Daniel's adoucie par le triple sec et désaltérée d'une limonade pétillante glacée.",
        category = "Cocktails",
        flavorProfile = "Frais/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Jack Daniel's", "Tennessee Whiskey", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Triple sec", "Liqueur d'orange", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Limonade", "Limonade fraîche pétillante", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Shaker le Jack Daniel's, le triple sec et le jus de citron avec des glaçons.",
            "Verser dans un bocal Mason jar ou un grand verre rempli de glace.",
            "Allonger de limonade fraîche et remuer doucement."
        ),
        garnish = "Tranche de citron.",
        glassware = "Bocal Mason Jar ou Highball",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7rnm8u1504888527.jpg",
        isFavorite = false,
        vibeTag = "Southern Rock & Tennessee Nights"
    ),

    // 67. Virgin Mary
    CocktailEntity(
        id = 67,
        name = "Virgin Mary",
        subtitle = "Le Tonic Salé et Revigorant 0%",
        description = "0% alcool : toute l'énergie épicée du Bloody Mary sans alcool avec du jus de tomate épais, Worcestershire, Tabasco et sel de céleri.",
        category = "Mocktails",
        flavorProfile = "Salé/Épicé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de tomate", "Pur jus de tomate", 15.0, "1 grand verre", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sauce Worcestershire", "Sauce relevée", 0.5, "3 traits", false, "invert_colors"),
            RecipeIngredient("Tabasco", "Piment rouge", 0.2, "2 traits", false, "invert_colors"),
            RecipeIngredient("Sel de céleri", "Sel aromatique", 0.0, "1 pincée", false, "spa")
        ),
        steps = listOf(
            "Verser le jus de tomate, le citron, la Worcestershire et le Tabasco dans le shaker avec de la glace.",
            "Ajouter le sel de céleri et shaker brièvement.",
            "Verser dans un verre haut garni de glace."
        ),
        garnish = "Branche de céleri.",
        glassware = "Verre Tumbler Highball",
        shakeSeconds = 6,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/yfhn371504374246.jpg",
        isFavorite = false,
        vibeTag = "Detox Morning & Ambient Soul"
    ),

    // 68. Cendrillon
    CocktailEntity(
        id = 68,
        name = "Cendrillon",
        subtitle = "Le Trio d'Agrumes Féerique 0%",
        description = "0% alcool : une alliance magique d'oranges fraîches, d'ananas parfumé et de citron vivifiant adoucis d'un filet de grenadine.",
        category = "Mocktails",
        flavorProfile = "Fruité/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 4.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus d'ananas", "Pur jus doux", 4.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Sirop de fruits rouges", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 6.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Shaker les jus d'orange, d'ananas, de citron et la grenadine avec des glaçons.",
            "Verser dans un grand verre avec de la glace.",
            "Allonger d'eau gazeuse et remuer délicatement."
        ),
        garnish = "Tranche d'orange.",
        glassware = "Verre Tumbler",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/uptxtv1468876415.jpg",
        isFavorite = false,
        vibeTag = "Fairytale Sunset & Light Beats"
    ),

    // 69. Chantaco
    CocktailEntity(
        id = 69,
        name = "Chantaco",
        subtitle = "La Douceur Basque Fruitée 0%",
        description = "0% alcool : créé sur la côte basque, un mocktail raffiné mariant le pamplemousse acidulé, l'orange douce et le parfum de fraise.",
        category = "Mocktails",
        flavorProfile = "Fruité/Doux",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 4.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition"),
            RecipeIngredient("Jus de pamplemousse", "Pamplemousse rose", 4.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de fraise", "Sirop de fraise mûre", 2.0, "1 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Placer les jus d'orange, de citron, de pamplemousse et le sirop de fraise dans le shaker avec des glaçons.",
            "Shaker vivement pendant 8 secondes.",
            "Filtrer dans un verre rempli de glaçons."
        ),
        garnish = "Fraise fraîche.",
        glassware = "Verre à Cocktail haut",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/mzgaqu1504389248.jpg",
        isFavorite = false,
        vibeTag = "Basque Coast & Gentle Acoustic"
    ),

    // 70. Florida
    CocktailEntity(
        id = 70,
        name = "Florida",
        subtitle = "Le Souffle Pétillant des Vergers 0%",
        description = "0% alcool : une explosion d'agrumes floridiens pétillants où le pamplemousse et l'orange s'accordent avec une fraîcheur citronnée intense.",
        category = "Mocktails",
        flavorProfile = "Fruité",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de pamplemousse", "Pur jus rose", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 4.0, "1/4 verre", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 6.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Verser les jus de pamplemousse, d'orange, de citron et le sirop de sucre dans le shaker avec de la glace.",
            "Shaker énergiquement pendant 8 secondes.",
            "Verser dans un grand verre avec des glaçons et allonger d'eau gazeuse."
        ),
        garnish = "Zeste de pamplemousse.",
        glassware = "Verre Highball",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/i3tfn31484430499.jpg",
        isFavorite = false,
        vibeTag = "Miami Beach & Chill House"
    ),

    // 71. Frozen Strawberry Margarita
    CocktailEntity(
        id = 71,
        name = "Frozen Strawberry Margarita",
        subtitle = "Le Granité Givré à la Fraise",
        description = "Une texture granité addictive : tequila blanco, liqueur d'orange et jus de citron vert mixés à grande vitesse avec de vraies fraises et un lit de glace.",
        category = "Cocktails",
        flavorProfile = "Fruité/Glacé",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Tequila Blanco", "100% agave", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'oranges", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Fraises fraîches", "Fraises mûres équeutées", 10.0, "100g", false, "nutrition")
        ),
        steps = listOf(
            "Placer les fraises fraîches, la tequila, le Cointreau, le citron vert et une pleine coupe de glace dans le blender.",
            "Mixer à vitesse maximale pendant 20 secondes jusqu'à consistance onctueuse et glacée.",
            "Verser dans un grand verre à margarita."
        ),
        garnish = "Fraise.",
        glassware = "Verre à Margarita Sombrero",
        shakeSeconds = 20,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/tqyrpw1439905311.jpg",
        isFavorite = true,
        vibeTag = "Summer Pool Party & Latin Pop"
    ),

    // 72. Frozen Daiquiri
    CocktailEntity(
        id = 72,
        name = "Frozen Daiquiri",
        subtitle = "L'Avalanche Glacée Tropicale",
        description = "La version givrée culte du Daiquiri immortalisée à La Floridita : le rhum blanc et le citron vert transformés en un sorbet cocktail rafraîchissant.",
        category = "Cocktails",
        flavorProfile = "Acide/Glacé",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum cubain", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.5, "1 citron entier", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.5, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Verser le rhum blanc, le citron vert et le sirop dans le blender.",
            "Ajouter une généreuse coupe de glace pilée.",
            "Mixer jusqu'à obtenir une émulsion glacée dense et crémeuse.",
            "Verser dans une coupe rafraîchie."
        ),
        garnish = "Rondelle de citron.",
        glassware = "Coupe à Cocktail large",
        shakeSeconds = 15,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7oyrj91504884412.jpg",
        isFavorite = false,
        vibeTag = "Havana Tropicana & Afro Cuban Jazz"
    ),

    // 73. Singapore Sling
    CocktailEntity(
        id = 73,
        name = "Singapore Sling",
        subtitle = "Le Chef-d'Œuvre du Raffles Hotel",
        description = "Créé en 1915 au Long Bar de Singapour : une partition complexe d'herbes aromatiques Bénédictine, cerise noire, gin et jus d'ananas mousseux.",
        category = "Cocktails",
        flavorProfile = "Fruité/Complexe",
        prepTimeMinutes = 4,
        difficulty = "Expert",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de cerise", "Cherry Heering", 1.5, "1 cuil. à café", false, "invert_colors"),
            RecipeIngredient("Cointreau", "Liqueur d'orange", 0.75, "1 trait", false, "invert_colors"),
            RecipeIngredient("Bénédictine", "Liqueur d'herbes séculaires", 0.75, "1 trait", false, "invert_colors"),
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas", 12.0, "1 grand verre", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Grenadine", "Sirop de grenadine", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Angostura", "Bitters aromatiques", 0.2, "1 trait", false, "invert_colors")
        ),
        steps = listOf(
            "Verser tous les ingrédients dans le shaker avec une abondance de glaçons.",
            "Shaker vigoureusement pendant 12 secondes pour bien aérer le jus d'ananas.",
            "Filtrer dans un verre sling ou highball rempli de glace fraîche."
        ),
        garnish = "Ananas et cerise.",
        glassware = "Verre Sling ou Highball haut",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/8cl9sm1582581761.jpg",
        isFavorite = false,
        vibeTag = "Colonial Elegance & Asian Lounge"
    ),

    // 74. Vesper Martini
    CocktailEntity(
        id = 74,
        name = "Vesper Martini",
        subtitle = "L'Ordre Mythique de James Bond",
        description = "Créé par Ian Fleming dans Casino Royale (1953) : 'Trois mesures de Gordon's, une de vodka, une demi de Kina Lillet. Shaker avec de la glace jusqu'à ce que ce soit glacé.'",
        category = "Cocktails",
        flavorProfile = "Sec/Puissant",
        prepTimeMinutes = 3,
        difficulty = "Expert",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin", 6.0, "3 mesures", false, "liquor"),
            RecipeIngredient("Vodka", "Vodka de grain pure", 2.0, "1 mesure", false, "liquor"),
            RecipeIngredient("Lillet Blanc", "Apéritif bordelais aromatique", 1.0, "1/2 mesure", false, "liquor")
        ),
        steps = listOf(
            "Verser le gin, la vodka et le Lillet Blanc dans le shaker avec beaucoup de glace.",
            "Shaker vigoureusement pendant 15 secondes pour obtenir de minuscules éclats de glace cristallins en surface.",
            "Filtrer dans une coupe à cocktail bien profonde."
        ),
        garnish = "Zeste de citron.",
        glassware = "Coupe à Cocktail profonde",
        shakeSeconds = 15,
        rating = 5.0,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/mtdxpa1504374514.jpg",
        isFavorite = true,
        vibeTag = "Casino Royale & Cinematic Brass"
    ),

    // 75. Rusty Nail
    CocktailEntity(
        id = 75,
        name = "Rusty Nail",
        subtitle = "Le Clou Rouillé du Rat Pack",
        description = "Le cocktail culte de Frank Sinatra et Dean Martin : l'intensité tourbée et fumée d'un Scotch écossais adoucie par le miel de bruyère et les épices de la Drambuie.",
        category = "Cocktails",
        flavorProfile = "Corsé/Doux",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Scotch Whisky", "Blended Scotch Whisky", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Drambuie", "Liqueur de whisky, miel & herbes", 2.5, "1/2 shooter", false, "invert_colors")
        ),
        steps = listOf(
            "Remplir un verre Old Fashioned de gros cubes de glace purs.",
            "Verser le Scotch Whisky et la Drambuie.",
            "Mélanger délicatement à la cuillère de bar pendant 20 secondes pour fondre les deux spiritueux."
        ),
        garnish = "Zeste de citron.",
        glassware = "Verre Rocks Old Fashioned",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/yqsvtw1478252982.jpg",
        isFavorite = false,
        vibeTag = "Rat Pack Vegas & Classic Swing"
    ),

    // 76. Godfather
    CocktailEntity(
        id = 76,
        name = "Godfather",
        subtitle = "L'Alliance Sicilienne Culte",
        description = "Inspiré par le chef-d'œuvre cinématographique de Coppola : la force brute d'un scotch écossais sublimée par l'amertume suave de l'Amaretto italien.",
        category = "Cocktails",
        flavorProfile = "Corsé/Amande",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Scotch Whisky", "Scotch ou Bourbon", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Amaretto", "Liqueur d'amande douce", 2.5, "1/2 shooter", false, "liquor")
        ),
        steps = listOf(
            "Placer un gros glaçon taillé dans un verre Old Fashioned.",
            "Verser le Scotch Whisky puis l'Amaretto.",
            "Mélanger à la cuillère de bar pour rafraîchir et harmoniser."
        ),
        garnish = "Aucune.",
        glassware = "Verre Old Fashioned Lowball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/e5zgao1582582378.jpg",
        isFavorite = false,
        vibeTag = "Sicilian Shadows & Cinema Strings"
    ),

    // 77. Caipiroska
    CocktailEntity(
        id = 77,
        name = "Caipiroska",
        subtitle = "La Caïpirinha Russe Tranchante",
        description = "La version slave ultra-populaire de la Caïpirinha : la neutralité limpide de la vodka laisse s'exprimer pleinement les huiles et le jus de citron vert écrasé.",
        category = "Cocktails",
        flavorProfile = "Acide/Sucré",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Citron vert", "Coupé en morceaux", 3.0, "1 entier coupé", false, "nutrition"),
            RecipeIngredient("Sucre roux", "Cassonade pure", 2.0, "2 cuillères", false, "water_drop")
        ),
        steps = listOf(
            "Couper le citron vert en 8 morceaux et les déposer au fond du verre avec le sucre roux.",
            "Piler avec fermeté pour extraire tout le jus sans meurtrir la peau blanche.",
            "Remplir le verre de glace pilée, verser la vodka et remuer vigoureusement de bas en haut."
        ),
        garnish = "Rondelle de citron.",
        glassware = "Verre Rocks bas",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/jgvn7p1582484435.jpg",
        isFavorite = false,
        vibeTag = "Summer Beach Bar & Deep Vocal House"
    ),

    // 78. French Martini
    CocktailEntity(
        id = 78,
        name = "French Martini",
        subtitle = "L'Élixir Velours Framboise & Ananas",
        description = "Né à New York dans les années 80 : la noblesse de la liqueur de framboise noire Chambord associée au jus d'ananas crée une somptueuse mousse crémeuse.",
        category = "Cocktails",
        flavorProfile = "Fruité/Doux",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 4.5, "1 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de framboise Chambord", "Framboises noires royales", 1.5, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus d'ananas", "Pur jus d'ananas", 4.5, "1/4 verre", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, la liqueur de framboise Chambord et le jus d'ananas dans le shaker avec des glaçons.",
            "Shaker très énergiquement pendant 12 secondes afin de provoquer une épaisse mousse veloutée.",
            "Double-filtrer dans une coupe à martini rafraîchie."
        ),
        garnish = "Framboises.",
        glassware = "Coupe à Martini",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/clth721504373134.jpg",
        isFavorite = true,
        vibeTag = "Chic Rooftop & Nu-Disco Groove"
    ),

    // 79. Gin Fizz
    CocktailEntity(
        id = 79,
        name = "Gin Fizz",
        subtitle = "L'Émulsion Pétillante Vivifiante",
        description = "Le grand classique shaker : gin, citron jaune et sucre secoués jusqu'à glaçage total, puis allongés d'eau gazeuse pour faire jaillir les bulles.",
        category = "Cocktails",
        flavorProfile = "Acide/Pétillant",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 3.0, "1 citron", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne liquide", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Très pétillante", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Verser le gin, le jus de citron et le sirop de sucre dans le shaker avec des glaçons.",
            "Shaker avec vigueur pendant 10 secondes.",
            "Filtrer dans un verre tumbler sans glaçons (ou avec glace) et allonger d'eau gazeuse bien froide."
        ),
        garnish = "Tranche de citron.",
        glassware = "Verre Tumbler ou Highball",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/drtihp1606768397.jpg",
        isFavorite = false,
        vibeTag = "Summer Garden & Sunny Acoustic"
    ),

    // 80. Hurricane
    CocktailEntity(
        id = 80,
        name = "Hurricane",
        subtitle = "La Tempête Tropicale de la Nouvelle-Orléans",
        description = "Créé chez Pat O'Brien dans le French Quarter : deux rhums puissants emportés dans une tornade de fruits de la passion et d'oranges fraîches.",
        category = "Cocktails",
        flavorProfile = "Exotique/Puissant",
        prepTimeMinutes = 4,
        difficulty = "Expert",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Rhum blanc", "Rhum caribéen", 6.0, "1.5 shooters", false, "liquor"),
            RecipeIngredient("Rhum ambré", "Rhum vieux puissant", 6.0, "1.5 shooters", false, "liquor"),
            RecipeIngredient("Jus de fruit de la passion", "Nectar maracuja", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Sirop rouge vif", 1.5, "1 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Placer tous les ingrédients dans le shaker avec une abondance de glace.",
            "Shaker intensément pendant 12 secondes.",
            "Verser dans le verre iconique Hurricane garni de glace pilée."
        ),
        garnish = "Orange et cerise.",
        glassware = "Verre Ouragan Hurricane",
        shakeSeconds = 12,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vqws6t1504888857.jpg",
        isFavorite = false,
        vibeTag = "Mardi Gras & New Orleans Brass Band"
    ),

    // 81. Planter's Punch
    CocktailEntity(
        id = 81,
        name = "Planter's Punch",
        subtitle = "Le Punch Historique de la Jamaïque",
        description = "Le grand punch caribéen du XIXe siècle : 'One of sour, two of sweet, three of strong, four of weak' agrémenté de rhum ambré et d'Angostura.",
        category = "Cocktails",
        flavorProfile = "Fruité/Epicé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Rhum ambré", "Rhum vieux jamaïcain", 4.5, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 3.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus d'ananas", "Pur jus doux", 3.0, "2 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.0, "1 cuil. à café", false, "nutrition"),
            RecipeIngredient("Grenadine", "Sirop de fruits rouges", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Sirop de canne", "Sucre de canne", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Angostura", "Bitters aromatiques", 0.5, "2 traits", false, "invert_colors")
        ),
        steps = listOf(
            "Verser le rhum ambré, les jus de fruits, les sirops et les 2 traits d'Angostura dans le shaker rempli de glace.",
            "Shaker vivement pendant 10 secondes.",
            "Filtrer dans un grand verre tumbler rempli de glace pilée."
        ),
        garnish = "Tranche d'orange.",
        glassware = "Verre Tumbler Highball",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/fdk8a31606854815.jpg",
        isFavorite = false,
        vibeTag = "Kingston Reggae & Caribbean Chills"
    ),

    // 82. Woo Woo
    CocktailEntity(
        id = 82,
        name = "Woo Woo",
        subtitle = "Le Délice Fruité des Discothèques",
        description = "Le cocktail culte des bars branchés : un trio fruité détonant alliant la fraîcheur de la vodka, la douceur de la pêche et le rubis acidulé du cranberry.",
        category = "Cocktails",
        flavorProfile = "Fruité/Sucré",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de pêche", "Peach Schnapps", 2.0, "1 cuil. à soupe", false, "invert_colors"),
            RecipeIngredient("Jus de cranberry", "Canneberge acidulée", 6.0, "1/3 verre", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, la liqueur de pêche et le jus de cranberry dans un shaker avec des glaçons.",
            "Shaker avec énergie pendant 8 secondes.",
            "Filtrer dans un verre highball rempli de glace."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre Highball",
        shakeSeconds = 8,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/sxvrwv1473344825.jpg",
        isFavorite = false,
        vibeTag = "Late Night Club & Nu-Disco"
    ),

    // 83. Sea Breeze
    CocktailEntity(
        id = 83,
        name = "Sea Breeze",
        subtitle = "La Brise Marine Acidulée",
        description = "L'esprit de la côte Atlantique américaine : la vivacité du pamplemousse rose et la fraîcheur du cranberry portées par une vodka glacée.",
        category = "Cocktails",
        flavorProfile = "Acide/Fruité",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus de cranberry", "Canneberge pure", 9.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Jus de pamplemousse", "Pur jus rose", 3.0, "2 cuil. à soupe", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre highball de glaçons.",
            "Verser la vodka, le jus de cranberry et le jus de pamplemousse.",
            "Remuer doucement à la cuillère pour fondre les couleurs et les arômes."
        ),
        garnish = "Tranche de pamplemousse.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7rfuks1504371562.jpg",
        isFavorite = false,
        vibeTag = "Coastal Breeze & Chill Electronic"
    ),

    // 84. Bay Breeze
    CocktailEntity(
        id = 84,
        name = "Bay Breeze",
        subtitle = "La Brise Hawaïenne Canneberge-Ananas",
        description = "Aussi connu sous le nom d'Hawaiian Sea Breeze : le mariage irrésistible de la douceur de l'ananas tropical et de l'acidité de la canneberge.",
        category = "Cocktails",
        flavorProfile = "Doux/Exotique",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 10.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 4.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Jus de cranberry", "Canneberge", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus d'ananas", "Pur jus doux", 6.0, "1/3 verre", false, "nutrition")
        ),
        steps = listOf(
            "Verser la vodka, le jus de cranberry et le jus d'ananas dans un shaker avec de la glace.",
            "Shaker brièvement pour homogénéiser.",
            "Verser dans un verre highball rempli de glace."
        ),
        garnish = "Quartier d'ananas.",
        glassware = "Verre Highball",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/ysuqus1441208583.jpg",
        isFavorite = false,
        vibeTag = "Bay Sunrise & Tropic Wave"
    ),

    // 85. Cape Codder
    CocktailEntity(
        id = 85,
        name = "Cape Codder",
        subtitle = "Le Vodka-Cranberry Emblématique",
        description = "Nommé d'après la péninsule de Cape Cod réputée pour ses marais de canneberges : le grand classique pur, sec et vivifiant.",
        category = "Cocktails",
        flavorProfile = "Acide/Sec",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka de blé pure", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de cranberry", "Pur jus de canneberge", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre highball de glace jusqu'en haut.",
            "Verser la vodka directement sur les glaçons.",
            "Allonger de jus de cranberry et remuer délicatement."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/3qpv121504366699.jpg",
        isFavorite = false,
        vibeTag = "Cape Cod Atlantic & Acoustic Calm"
    ),

    // 86. Greyhound
    CocktailEntity(
        id = 86,
        name = "Greyhound",
        subtitle = "La Lévrière Amère & Citronnée",
        description = "Documenté pour la première fois en 1930 au Savoy : l'amertume noble du pamplemousse rose mariée à la pureté tranchante d'un gin sec ou d'une vodka.",
        category = "Cocktails",
        flavorProfile = "Amer/Acide",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Vodka ou Gin", "Au choix : gin botanique ou vodka", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de pamplemousse", "Pur jus de pamplemousse rose", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre Old Fashioned ou tumbler de gros glaçons.",
            "Verser la vodka (ou le gin).",
            "Allonger avec le jus de pamplemousse frais et remuer."
        ),
        garnish = "Tranche de pamplemousse.",
        glassware = "Verre Tumbler",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/g5upn41513706732.jpg",
        isFavorite = false,
        vibeTag = "Vintage Bus & Travel Indie"
    ),

    // 87. Salty Dog
    CocktailEntity(
        id = 87,
        name = "Salty Dog",
        subtitle = "Le Greyhound au Col Salin",
        description = "L'évolution marine du Greyhound : le bord givré au sel fin amplifie les saveurs d'agrumes et transforme l'amertume du pamplemousse en gourmandise.",
        category = "Cocktails",
        flavorProfile = "Amer/Salé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Vodka ou Gin", "Gin ou vodka de qualité", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de pamplemousse", "Pamplemousse rose pressé", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Fleur de sel", "Pour givrer le col", 0.0, "Bordure", true, "spa")
        ),
        steps = listOf(
            "Frotter le bord du verre avec un quartier de pamplemousse puis tremper dans une assiette de sel fin.",
            "Remplir délicatement de glaçons sans toucher la bordure salée.",
            "Verser la vodka (ou le gin) et compléter de jus de pamplemousse."
        ),
        garnish = "Tranche de pamplemousse.",
        glassware = "Verre Highball ou Rocks",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/4vfge01504890216.jpg",
        isFavorite = false,
        vibeTag = "Salty Harbor & Yacht Rock"
    ),

    // 88. Harvey Wallbanger
    CocktailEntity(
        id = 88,
        name = "Harvey Wallbanger",
        subtitle = "Le Surfeur Doré aux Herbes Galliano",
        description = "Né sur les plages de Californie dans les années 70 : un tournevis (vodka-orange) transformé par le nappage doré et vanillé de la liqueur italienne Galliano.",
        category = "Cocktails",
        flavorProfile = "Herbacé/Fruité",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 9.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Galliano", "Liqueur d'anis & vanille", 1.5, "En float", false, "invert_colors")
        ),
        steps = listOf(
            "Remplir un verre highball de glaçons.",
            "Verser la vodka puis le jus d'orange et mélanger.",
            "Faire flotter délicatement la liqueur Galliano sur le dessus à l'aide d'une cuillère de bar."
        ),
        garnish = "Tranche d'orange.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7os4gs1606854357.jpg",
        isFavorite = false,
        vibeTag = "California 70s & Sunset Funk"
    ),

    // 89. Rob Roy
    CocktailEntity(
        id = 89,
        name = "Rob Roy",
        subtitle = "Le Manhattan des Highlands Écossais",
        description = "Créé à l'hôtel Waldorf Astoria de New York en 1894 en hommage au héros écossais : le caractère fumé du Scotch marié au vermouth rouge et aux bitters.",
        category = "Cocktails",
        flavorProfile = "Corsé/Herbacé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 30.0,
        ingredients = listOf(
            RecipeIngredient("Scotch Whisky", "Blended Scotch de qualité", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Vermouth Rouge", "Vermouth doux aromatique", 2.0, "1 cuil. à soupe", false, "liquor"),
            RecipeIngredient("Angostura Bitters", "Bitters aromatiques", 0.5, "2 traits", false, "invert_colors")
        ),
        steps = listOf(
            "Verser le Scotch, le vermouth rouge et les 2 traits d'Angostura dans un verre à mélange avec beaucoup de glace.",
            "Remuer à la cuillère de bar pendant 30 secondes pour une dilution parfaite.",
            "Filtrer dans une coupe à cocktail rafraîchie."
        ),
        garnish = "Cerise amarena.",
        glassware = "Coupe à Cocktail vintage",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/pe1x1c1504735672.jpg",
        isFavorite = false,
        vibeTag = "Highland Castle & Celtic Strings"
    ),

    // 90. Blood and Sand
    CocktailEntity(
        id = 90,
        name = "Blood and Sand",
        subtitle = "L'Arène Épique Sang & Sable",
        description = "Inspiré par le film de corrida de Rudolph Valentino en 1922 : une combinaison insolite et géniale à parts égales de Scotch, liqueur de cerise, vermouth et jus d'orange.",
        category = "Cocktails",
        flavorProfile = "Fruité/Corsé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Scotch Whisky", "Scotch fruité", 2.0, "1 mesure égale", false, "liquor"),
            RecipeIngredient("Cherry Brandy", "Liqueur de cerise", 2.0, "1 mesure égale", false, "invert_colors"),
            RecipeIngredient("Vermouth Rouge", "Vermouth di Torino", 2.0, "1 mesure égale", false, "liquor"),
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 2.0, "1 mesure égale", false, "nutrition")
        ),
        steps = listOf(
            "Verser les 4 ingrédients à parts rigoureusement égales dans le shaker avec des glaçons.",
            "Shaker vivement pendant 10 secondes.",
            "Filtrer dans une coupe à cocktail."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Coupe à Cocktail",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/pwgtpa1504366376.jpg",
        isFavorite = false,
        vibeTag = "Roaring 20s & Dramatic Cinema"
    ),

    // 91. Between the Sheets
    CocktailEntity(
        id = 91,
        name = "Between the Sheets",
        subtitle = "La Passion Audacieuse sous les Draps",
        description = "Créé au Harry's New York Bar à Paris dans les années 30 : le Sidecar sublimé par l'ajout de rhum blanc caribéen pour une intensité captivante.",
        category = "Cocktails",
        flavorProfile = "Acide/Sec",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Cognac", "Cognac français", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Rhum blanc", "Rhum pur jus", 3.0, "1 shooter", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'oranges", 3.0, "1 shooter", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 1.5, "1 cuil. à soupe", false, "nutrition")
        ),
        steps = listOf(
            "Placer le cognac, le rhum blanc, le Cointreau et le jus de citron dans le shaker avec de la glace.",
            "Shaker énergiquement pendant 10 secondes.",
            "Double-filtrer dans une coupe à cocktail rafraîchie."
        ),
        garnish = "Zeste de citron flamboyant.",
        glassware = "Coupe à Cocktail",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/nl89tf1518947401.jpg",
        isFavorite = false,
        vibeTag = "Midnight Parisian Romance & Soul Jazz"
    ),

    // 92. Corpse Reviver No. 2
    CocktailEntity(
        id = 92,
        name = "Corpse Reviver No. 2",
        subtitle = "L'Élixir Résurrecteur du Savoy",
        description = "Le plus réputé des 'réveille-morts' du Savoy Cocktail Book de Harry Craddock : une goutte d'absinthe pour rincer le verre, réhaussant gin, Lillet et Cointreau.",
        category = "Cocktails",
        flavorProfile = "Acide/Anisé",
        prepTimeMinutes = 3,
        difficulty = "Expert",
        alcoholPercentage = 25.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin", 2.0, "1 part égale", false, "liquor"),
            RecipeIngredient("Lillet Blanc", "Apéritif bordelais", 2.0, "1 part égale", false, "liquor"),
            RecipeIngredient("Cointreau", "Liqueur d'oranges", 2.0, "1 part égale", false, "invert_colors"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1 part égale", false, "nutrition"),
            RecipeIngredient("Absinthe", "Pour rincer le verre", 0.2, "1 trait de rinçage", false, "invert_colors")
        ),
        steps = listOf(
            "Verser un trait d'absinthe dans la coupe rafraîchie, faire tourner pour recouvrir la paroi, puis jeter l'excédent.",
            "Verser les 4 autres ingrédients à parts égales dans le shaker avec de la glace.",
            "Shaker vigoureusement pendant 10 secondes et double-filtrer dans la coupe préparée."
        ),
        garnish = "Zeste de citron.",
        glassware = "Coupe à Cocktail rafraîchie",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/gifgao1513704334.jpg",
        isFavorite = false,
        vibeTag = "Victorian Gothic & Classic Cello"
    ),

    // 93. Martinez
    CocktailEntity(
        id = 93,
        name = "Martinez",
        subtitle = "L'Ancêtre Originel du Martini",
        description = "Le grand-père direct du Dry Martini et du Manhattan (1884) : le profil doux et complexe du gin associé au vermouth rouge, relevé de marasquin et d'Angostura.",
        category = "Cocktails",
        flavorProfile = "Corsé/Doux",
        prepTimeMinutes = 3,
        difficulty = "Expert",
        alcoholPercentage = 28.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Old Tom ou Dry Gin", 4.0, "1 grand shooter", false, "liquor"),
            RecipeIngredient("Vermouth Rouge", "Vermouth di Torino", 4.0, "1 grand shooter", false, "liquor"),
            RecipeIngredient("Liqueur de Marasquin", "Luxardo", 0.5, "1 cuil. à café", false, "invert_colors"),
            RecipeIngredient("Angostura", "Bitters aromatiques", 0.5, "2 traits", false, "invert_colors")
        ),
        steps = listOf(
            "Verser le gin, le vermouth rouge, le marasquin et les 2 traits d'Angostura dans un verre à mélange avec beaucoup de glace.",
            "Remuer à la cuillère de bar pendant 30 secondes pour une dilution soyeuse.",
            "Filtrer dans une coupe à cocktail."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Coupe vintage",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/fs6kiq1513708455.jpg",
        isFavorite = false,
        vibeTag = "Golden Age San Francisco & Ragtime"
    ),

    // 94. Penicillin
    CocktailEntity(
        id = 94,
        name = "Penicillin",
        subtitle = "Le Philtre Tourbé & Épicé de Milk & Honey",
        description = "Créé par Sam Ross en 2005 à New York : le remède suprême associant scotch blended, citron, miel, gingembre et un voile fumé de scotch d'Islay tourbé en float.",
        category = "Cocktails",
        flavorProfile = "Tourbé/Épicé",
        prepTimeMinutes = 3,
        difficulty = "Expert",
        alcoholPercentage = 20.0,
        ingredients = listOf(
            RecipeIngredient("Blended Scotch", "Scotch doux et fruité", 6.0, "1.5 shooters", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Sirop de miel", "Miel dilué 3:1", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Sirop de gingembre", "Gingembre frais cuit", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Islay Scotch tourbé", "Single malt fumé en float", 1.0, "Nappage", false, "liquor")
        ),
        steps = listOf(
            "Shaker le blended scotch, le citron vert, le sirop de miel et le sirop de gingembre avec des glaçons.",
            "Filtrer dans un verre Old Fashioned sur un gros glaçon cristallin.",
            "Faire couler délicatement le scotch tourbé d'Islay sur le dos de la cuillère pour créer le float fumé aromatique."
        ),
        garnish = "Gingembre confit.",
        glassware = "Verre Rocks Old Fashioned",
        shakeSeconds = 12,
        rating = 5.0,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/hc9b1a1521853096.jpg",
        isFavorite = true,
        vibeTag = "Milk & Honey Speakeasy & Smoky Jazz"
    ),

    // 95. Basil Smash
    CocktailEntity(
        id = 95,
        name = "Basil Smash",
        subtitle = "L'Onde Verte Botanique de Hambourg",
        description = "Créé par Jörg Meyer au bar Le Lion en 2008 : le vert émeraude vibrant du basilic frais écrasé, vivifié par la force du gin et le citron jaune.",
        category = "Cocktails",
        flavorProfile = "Herbacé/Frais",
        prepTimeMinutes = 4,
        difficulty = "Moyen",
        alcoholPercentage = 15.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Gin sec botanique", 6.0, "1.5 shooters", false, "liquor"),
            RecipeIngredient("Jus de citron", "Pressé minute", 2.0, "1/2 citron", false, "nutrition"),
            RecipeIngredient("Sirop de sucre", "Sucre de canne", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Feuilles de basilic", "Basilic frais entier", 0.0, "10 feuilles", false, "spa")
        ),
        steps = listOf(
            "Placer les feuilles de basilic, le jus de citron et le sirop de sucre dans le shaker.",
            "Piler vigoureusement pour broyer le basilic et libérer sa sève émeraude.",
            "Ajouter le gin et une abondance de glace, puis shaker avec une énergie maximale.",
            "Double-filtrer au chinois fin dans un verre rocks sur un gros glaçon."
        ),
        garnish = "Tête de basilic.",
        glassware = "Verre Tumbler Old Fashioned",
        shakeSeconds = 12,
        rating = 4.9,
        imageUrl = "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Hamburg Speakeasy & Nordic House"
    ),

    // 96. London Mule
    CocktailEntity(
        id = 96,
        name = "London Mule",
        subtitle = "La Variante Britannique au Gingembre",
        description = "L'équivalent londonien du Moscow Mule : les baies de genièvre du gin remplacent la vodka pour un dialogue aromatique envoûtant avec la ginger beer.",
        category = "Cocktails",
        flavorProfile = "Épicé/Frais",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 12.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "London Dry Gin", 5.0, "1 shooter généreux", false, "liquor"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.5, "1/2 citron vert", false, "nutrition"),
            RecipeIngredient("Ginger Beer", "Gingembre fermenté épicé", 12.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Remplir une timbale en cuivre ou un grand verre de glace pilée.",
            "Verser le gin et le jus de citron vert frais direct sur la glace.",
            "Allonger avec la ginger beer bien fraîche et remuer doucement à la cuillère de bar."
        ),
        garnish = "Quartier de citron vert.",
        glassware = "Timbale en cuivre",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/rj55pl1582476101.jpg",
        isFavorite = false,
        vibeTag = "Soho Rooftop & London Underground Beat"
    ),

    // 97. Roy Rogers
    CocktailEntity(
        id = 97,
        name = "Roy Rogers",
        subtitle = "Le Cow-Boy Pétillant 0%",
        description = "0% alcool : le pendant au cola du Shirley Temple, baptisé en hommage au célèbre cow-boy chantant du cinéma hollywoodien.",
        category = "Mocktails",
        flavorProfile = "Sucré/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Cola", "Cola bien frais", 12.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine concentrée", 2.0, "1 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un verre highball de gros glaçons.",
            "Verser le sirop de grenadine au fond du verre.",
            "Allonger doucement de cola très frais direct au verre et remuer délicatement."
        ),
        garnish = "Cerise au marasquin.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/6bec6v1503563675.jpg",
        isFavorite = false,
        vibeTag = "Vintage Diner & Rockabilly"
    ),

    // 98. Arnold Palmer
    CocktailEntity(
        id = 98,
        name = "Arnold Palmer",
        subtitle = "L'Accord Parfait Thé & Limonade 0%",
        description = "0% alcool : popularisé par la légende du golf Arnold Palmer, la rencontre désaltérante suprême à parts égales de thé glacé non sucré et de limonade vive.",
        category = "Mocktails",
        flavorProfile = "Frais/Thé",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Thé glacé", "Infusion de thé noir froid", 8.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Limonade", "Citronnade pétillante", 8.0, "1/2 verre", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un grand verre de glaçons jusqu'en haut.",
            "Verser à parts égales le thé noir glacé et la limonade.",
            "Mélanger délicatement à la cuillère de bar."
        ),
        garnish = "Tranche de citron.",
        glassware = "Grand verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/xrsrpr1441247464.jpg",
        isFavorite = false,
        vibeTag = "Sunny Fairways & Smooth Acoustic"
    ),

    // 99. Faux-Groni
    CocktailEntity(
        id = 99,
        name = "Faux-Groni",
        subtitle = "Le Negroni Sans Alcool Botanique 0%",
        description = "0% alcool : l'amertume et la complexité du Negroni reconstituées sans une goutte d'alcool grâce au pamplemousse amer, au raisin blanc et au thé noir corsé.",
        category = "Mocktails",
        flavorProfile = "Amer/Herbacé",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de raisin blanc", "Pur jus doux", 3.0, "1 shooter", false, "nutrition"),
            RecipeIngredient("Sirop de pamplemousse amer", "Ou bitter sans alcool", 3.0, "1 shooter", false, "water_drop"),
            RecipeIngredient("Infusion de thé noir corsé", "Thé infusé froid tanique", 3.0, "1 shooter", false, "nutrition")
        ),
        steps = listOf(
            "Déposer un gros cube de glace pur dans un verre Old Fashioned.",
            "Verser le jus de raisin blanc, le sirop de pamplemousse amer et le thé noir corsé.",
            "Mélanger à la cuillère de bar pendant 30 secondes pour une dilution soyeuse.",
            "Exprimer un zeste d'orange au-dessus du verre."
        ),
        garnish = "Zeste d'orange.",
        glassware = "Verre Old Fashioned Lowball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/x8lhp41513703167.jpg",
        isFavorite = false,
        vibeTag = "Sober Speakeasy & Vinyl Mood"
    ),

    // 100. No-Loma
    CocktailEntity(
        id = 100,
        name = "No-Loma",
        subtitle = "La Paloma Pétillante Détox 0%",
        description = "0% alcool : toute l'énergie festive de la Paloma mexicaine sans alcool avec du jus de pamplemousse frais, du citron vert, du nectar d'agave et un bord salé.",
        category = "Mocktails",
        flavorProfile = "Amer/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de pamplemousse", "Pur jus frais rose", 5.0, "1 grand shooter", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 1.0, "1 trait", false, "nutrition"),
            RecipeIngredient("Sirop d'agave", "Nectar d'agave bio", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 8.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Fleur de sel", "Pour givrer le col", 0.0, "Bordure", true, "spa")
        ),
        steps = listOf(
            "Frotter le bord du verre avec du citron vert et tremper dans une coupelle de fleur de sel.",
            "Remplir le verre de glaçons sans toucher la bordure.",
            "Verser le jus de pamplemousse, le jus de citron vert et le sirop d'agave.",
            "Allonger d'eau gazeuse fraîche et remuer doucement."
        ),
        garnish = "Quartier de pamplemousse.",
        glassware = "Verre Highball givré",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/samm5j1513706393.jpg",
        isFavorite = true,
        vibeTag = "Baja California Sunset & Warm Lo-Fi"
    ),

    // 101. Virgin Mojito
    CocktailEntity(
        id = 101,
        name = "Virgin Mojito",
        subtitle = "L'Incontournable Menthe & Citron Vert 0%",
        description = "0% alcool, 100% fraîcheur : des feuilles de menthe fraîchement froissées, du jus de citron vert pressé minute et du sucre de canne, allongés d'eau gazeuse très fraîche sur glace pilée.",
        category = "Mocktails",
        flavorProfile = "Frais/Herbacé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Menthe fraîche", "Feuilles entières fraîches", 0.0, "8 feuilles", false, "spa"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 3.0, "1 shooter", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Pur sucre liquide", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche et pétillante", 12.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Placer les feuilles de menthe, le jus de citron vert et le sirop de canne au fond du verre.",
            "Piler délicatement sans déchirer les feuilles pour extraire les huiles essentielles.",
            "Remplir le verre de glace pilée jusqu'en haut.",
            "Allonger d'eau gazeuse fraîche et remuer de bas en haut avec la cuillère de bar."
        ),
        garnish = "Belle tête de menthe fraîche et rondelle de citron vert.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vwxrsw1478251483.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Havana Acoustic"
    ),

    // 102. Bora Bora
    CocktailEntity(
        id = 102,
        name = "Bora Bora",
        subtitle = "L'Évasion Tropicale Ananas & Passion 0%",
        description = "0% alcool : un voyage exotique en Polynésie associant la douceur du jus d'ananas, la puissance parfumée du fruit de la passion et une larme de grenadine créant un sublime dégradé coucher de soleil.",
        category = "Mocktails",
        flavorProfile = "Exotique/Fruité",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'ananas", "Pur jus pressé", 10.0, "1/2 grand verre", false, "nutrition"),
            RecipeIngredient("Jus de fruit de la passion", "Nectar onctueux", 6.0, "2 shooters", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine rouge intense", 1.5, "1 trait", false, "water_drop")
        ),
        steps = listOf(
            "Verser le jus d'ananas, le jus de passion et le jus de citron dans un shaker rempli de glaçons.",
            "Frapper énergiquement pendant 10 secondes.",
            "Filtrer dans un verre hurricane rempli de glaçons frais.",
            "Faire couler délicatement le sirop de grenadine sur la paroi pour un dégradé étagé."
        ),
        garnish = "Triangle d'ananas frais et cerise marasquin.",
        glassware = "Verre Hurricane",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/xwuqvw1473201811.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Tropical Sunset"
    ),

    // 103. Shirley Temple
    CocktailEntity(
        id = 103,
        name = "Shirley Temple",
        subtitle = "Le Charme Hollywoodien Grenadine & Ginger Ale",
        description = "0% alcool : le classique américain intemporel imaginé dans les années 1930 pour la jeune actrice, mêlant les bulles épicées du Ginger Ale à la douceur rubis de la grenadine.",
        category = "Mocktails",
        flavorProfile = "Pétillant/Sucré",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Ginger Ale", "Boisson gazeuse au gingembre", 12.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Sirop de fruits rouges", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Jus de citron jaune", "Pressé minute", 1.0, "1 trait", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un verre tumbler de glaçons généreux.",
            "Verser le sirop de grenadine et le trait de jus de citron.",
            "Compléter avec le Ginger Ale bien frappé.",
            "Mélanger délicatement à l'aide d'une cuillère de bar."
        ),
        garnish = "Cerise confite au marasquin et zeste de citron.",
        glassware = "Verre Tumbler",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/qsyqqq1441553437.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Vintage Swing"
    ),

    // 104. Virgin Piña Colada
    CocktailEntity(
        id = 104,
        name = "Virgin Piña Colada",
        subtitle = "Le Velouté Coco & Ananas Gourmand",
        description = "0% alcool : une caresse crémeuse et voluptueuse où l'onctuosité de la crème de coco s'unit au pur jus d'ananas mûri sous le soleil des Caraïbes.",
        category = "Mocktails",
        flavorProfile = "Doux/Crémeux",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'ananas", "Pur jus pressé", 12.0, "1 grand verre", false, "nutrition"),
            RecipeIngredient("Crème de coco", "Onctueuse et dense", 5.0, "1 shooter et demi", false, "nutrition"),
            RecipeIngredient("Lait de coco", "Lait léger", 3.0, "1 shooter", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sirop simple", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Mettre tous les ingrédients dans un blender ou shaker avec beaucoup de glace pilée.",
            "Mixer ou shaker vivement pendant 15 secondes pour créer une émulsion soyeuse.",
            "Verser dans un verre tulipe bien froid."
        ),
        garnish = "Triangle d'ananas frais et cerise rouge.",
        glassware = "Verre Tulipe Poco Grande",
        shakeSeconds = 15,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/cpf4j51504371346.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Bossa Nova"
    ),

    // 105. Safe Sex on the Beach
    CocktailEntity(
        id = 105,
        name = "Safe Sex on the Beach",
        subtitle = "La Douceur Pêche, Canneberge & Orange",
        description = "0% alcool : tout le plaisir sensuel et festif du célèbre cocktail de plage, combinant nectar de pêche onctueux, jus d'orange ensoleillé et jus de canneberge tonique.",
        category = "Mocktails",
        flavorProfile = "Fruité/Acidulé",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de canneberge", "Cranberry acidulé", 8.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Jus d'orange", "Pur jus d'orange pressée", 8.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Nectar de pêche", "Pêche blanche onctueuse", 4.0, "1 shooter et demi", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Sirop fruité", 1.0, "1 trait", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un verre highball de glaçons.",
            "Verser le nectar de pêche et le jus d'orange, puis remuer brièvement.",
            "Verser délicatement le jus de canneberge pour un effet bicolore élégant.",
            "Terminer par un trait de grenadine qui se dépose au fond."
        ),
        garnish = "Tranche d'orange fraîche et cerise.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/vuquyv1468876052.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Beach Club Melodies"
    ),

    // 106. Virgin Mary
    CocktailEntity(
        id = 106,
        name = "Virgin Mary",
        subtitle = "Le Grand Cocktail Tomate & Épices Détox",
        description = "0% alcool : la formule culte et corsée du Bloody Mary sans alcool avec du jus de tomate épais assaisonné de citron, sel de céleri, sauce Worcestershire et Tabasco piquant.",
        category = "Mocktails",
        flavorProfile = "Salé/Épicé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de tomate", "Pur jus épais de tomate", 15.0, "Grand verre", false, "nutrition"),
            RecipeIngredient("Jus de citron jaune", "Pressé frais", 1.5, "1 cuil. à café", false, "nutrition"),
            RecipeIngredient("Sauce Worcestershire", "Sauce anglaise relevée", 0.5, "3 gouttes", false, "nutrition"),
            RecipeIngredient("Tabasco", "Piment rouge liquide", 0.2, "2 gouttes", false, "nutrition"),
            RecipeIngredient("Sel de céleri", "Sel aromatique", 0.0, "1 pincée", true, "spa"),
            RecipeIngredient("Poivre noir", "Moulue minute", 0.0, "1 tour de moulin", true, "spa")
        ),
        steps = listOf(
            "Mettre les glaçons dans un grand verre highball.",
            "Ajouter le jus de citron, la sauce Worcestershire, le Tabasco, le sel de céleri et le poivre.",
            "Verser le jus de tomate bien frais.",
            "Mélanger délicatement à l'aide d'une branche de céleri."
        ),
        garnish = "Branche de céleri croquante et rondelle de citron.",
        glassware = "Verre Collins",
        shakeSeconds = 0,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7p607y1504735343.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Lo-Fi Sunday"
    ),

    // 107. Florida
    CocktailEntity(
        id = 107,
        name = "Florida",
        subtitle = "L'Accord Trois Agrumes Multivitaminé",
        description = "0% alcool : un bouquet d'agrumes gorgés de soleil où le pamplemousse rose, l'orange douce et le citron vert s'harmonisent en un cocktail ultra-rafraîchissant.",
        category = "Mocktails",
        flavorProfile = "Fruité/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de pamplemousse", "Pur jus rose frais", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus d'ananas", "Pur jus", 4.0, "1 shooter", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sirop simple", 1.5, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Placer des glaçons dans le shaker.",
            "Ajouter tous les jus d'agrumes et le sirop de canne.",
            "Frapper vigoureusement pendant 10 secondes.",
            "Passer dans un grand verre sur lit de glaçons frais."
        ),
        garnish = "Quartier de pamplemousse rose et brin de menthe.",
        glassware = "Verre Highball",
        shakeSeconds = 10,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/i3tfn31484430499.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Miami Breezes"
    ),

    // 108. Cendrillon
    CocktailEntity(
        id = 108,
        name = "Cendrillon",
        subtitle = "La Féerie Fruitée Citron, Ananas & Grenadine",
        description = "0% alcool : une création féerique associant en parts égales orange, ananas et citron, sublimée par une larme de grenadine et une pointe d'eau pétillante.",
        category = "Mocktails",
        flavorProfile = "Fruité/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 5.0, "1/4 verre", false, "nutrition"),
            RecipeIngredient("Jus d'ananas", "Pur jus doux", 5.0, "1/4 verre", false, "nutrition"),
            RecipeIngredient("Jus de citron jaune", "Pressé minute", 5.0, "1/4 verre", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine rouge intense", 1.0, "1 trait", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 5.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Shaker les jus d'orange, d'ananas et de citron avec de la glace.",
            "Filtrer dans un verre tulipe rafraîchi.",
            "Verser délicatement le filet de grenadine qui traverse le cocktail.",
            "Allonger d'un trait d'eau gazeuse pour une effervescence féerique."
        ),
        garnish = "Tranche d'orange et cerise confite.",
        glassware = "Verre Tulipe",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/uptxtv1468876415.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Fairy Lights"
    ),

    // 109. Chantaco
    CocktailEntity(
        id = 109,
        name = "Chantaco",
        subtitle = "Le Joyau Basque Pamplemousse & Fruits Rouges",
        description = "0% alcool : né sur la Côte Basque dans les années folles, ce cocktail sans alcool raffiné réunit le peps du pamplemousse, la douceur de l'orange et la rondeur du sirop de fraise ou grenadine.",
        category = "Mocktails",
        flavorProfile = "Fruité/Doux",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus pressé", 6.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus de pamplemousse", "Pur jus rose", 4.0, "1 shooter et demi", false, "nutrition"),
            RecipeIngredient("Jus de citron jaune", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Ou sirop de fraise", 2.0, "1 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Placer les glaçons dans le shaker.",
            "Verser le jus d'orange, le jus de pamplemousse, le jus de citron et le sirop.",
            "Frapper avec intensité pendant 10 secondes.",
            "Verser dans un verre à cocktail sans glaçons pour une dégustation soyeuse."
        ),
        garnish = "Rondelle de citron jaune et demi-fraise.",
        glassware = "Verre à Cocktail",
        shakeSeconds = 10,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/mzgaqu1504389248.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Atlantic Acoustic"
    ),

    // 110. Sweet Sunrise
    CocktailEntity(
        id = 110,
        name = "Sweet Sunrise",
        subtitle = "Le Dégradé Solaire Orange & Grenadine",
        description = "0% alcool : l'alternative sans alcool du Tequila Sunrise, offrant un dégradé spectaculaire d'aube lumineuse entre l'orange fraîchement pressée et la grenadine onctueuse.",
        category = "Mocktails",
        flavorProfile = "Fruité/Solaire",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus d'orange", "Pur jus d'orange fraîche", 15.0, "Grand verre", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Grenadine concentrée", 2.0, "1 cuil. à soupe", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un verre highball de gros cubes de glace limpides.",
            "Verser doucement le jus d'orange frais.",
            "Faire couler lentement le sirop de grenadine le long de la paroi interne.",
            "Laisser la grenadine descendre au fond pour former le lever de soleil sans remuer."
        ),
        garnish = "Tranche d'orange sanguine et zeste.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Golden Hour"
    ),

    // 111. Apple Mojito
    CocktailEntity(
        id = 111,
        name = "Apple Mojito",
        subtitle = "La Pomme Croquante & Menthe Fraîche",
        description = "0% alcool : une réinterprétation champêtre et gourmande du mojito associant la douceur d'un pur jus de pomme trouble pressé à froid et le parfum de menthe poivrée.",
        category = "Mocktails",
        flavorProfile = "Frais/Fruité",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de pomme trouble", "Pur jus artisanal", 10.0, "1/2 verre", false, "nutrition"),
            RecipeIngredient("Menthe fraîche", "Feuilles parfumées", 0.0, "8 feuilles", false, "spa"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 2.5, "1 shooter", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sucre liquide", 1.5, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche pétillante", 6.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Écraser délicatement le citron vert avec les feuilles de menthe et le sirop au fond du verre.",
            "Remplir le verre de glace pilée.",
            "Verser le jus de pomme trouble et compléter d'eau gazeuse.",
            "Mélanger délicatement à la cuillère de bas en haut."
        ),
        garnish = "Éventail de fines lamelles de pomme Granny Smith et brin de menthe.",
        glassware = "Verre Highball",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/yswuwp1469090992.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Indie Acoustic"
    ),

    // 112. Lipton Tonic
    CocktailEntity(
        id = 112,
        name = "Lipton Tonic",
        subtitle = "Le Thé Glacé Pétillant & Agrumes",
        description = "0% alcool : une infusion de thé glacé raffiné alliée au pétillement aromatique du Tonic Water et à une touche de pêche blanche pour une soif étanchée avec classe.",
        category = "Mocktails",
        flavorProfile = "Frais/Thé/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Thé glacé", "Infusion de thé noir froid", 12.0, "Grand verre", false, "nutrition"),
            RecipeIngredient("Tonic Water", "Eau tonique amère", 8.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Sirop de pêche", "Sirop de pêche blanche", 1.5, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Jus de citron jaune", "Pressé frais", 1.0, "1 trait", false, "nutrition")
        ),
        steps = listOf(
            "Remplir un grand verre de glaçons.",
            "Verser le thé glacé, le sirop de pêche et le jus de citron.",
            "Compléter avec le Tonic Water bien pétillant.",
            "Remuer délicatement une fois avec la cuillère de bar."
        ),
        garnish = "Rondelle de citron jaune et branche de romarin.",
        glassware = "Grand verre Highball",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/nkwr4c1606770558.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - London Rooftop"
    ),

    // 113. Berry Smash 0%
    CocktailEntity(
        id = 113,
        name = "Berry Smash 0%",
        subtitle = "L'Explosion Mûres, Framboises & Limonade",
        description = "0% alcool : un écrasé gourmand de baies sauvages avec du jus de canneberge pétillant et une pointe de sirop d'agave naturel.",
        category = "Mocktails",
        flavorProfile = "Fruité/Acidulé",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Framboises", "Framboises fraîches", 0.0, "5 baies", false, "spa"),
            RecipeIngredient("Mûres", "Mûres sauvages fraîches", 0.0, "4 baies", false, "spa"),
            RecipeIngredient("Jus de canneberge", "Cranberry pur jus", 8.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Sirop d'agave", "Nectar bio", 1.5, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Limonade", "Artisanale gazeuse", 6.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Écraser les baies de mûres et framboises avec le sirop d'agave au fond du verre.",
            "Remplir le verre de glaçons entiers.",
            "Verser le jus de canneberge et allonger de limonade bien fraîche.",
            "Remuer doucement pour diffuser la robe rouge rubis."
        ),
        garnish = "Brochette de fruits des bois et tête de menthe.",
        glassware = "Verre Old Fashioned",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/spvvxp1468924425.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Velvet Beats"
    ),

    // 114. Ginger Sparkler
    CocktailEntity(
        id = 114,
        name = "Ginger Sparkler",
        subtitle = "L'Éclat Épicé Pomme & Ginger Beer",
        description = "0% alcool : le kick tonique et ardent du gingembre naturel adouci par la rondeur du pur jus de pomme pressé et une touche de citron vert.",
        category = "Mocktails",
        flavorProfile = "Épicé/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Ginger Beer", "Sans alcool fermentée", 12.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Jus de pomme trouble", "Pur jus pressé", 6.0, "1 shooter et demi", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de miel", "Miel délayé", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un mug en cuivre ou verre tumbler de glace pilée.",
            "Verser le jus de pomme, le jus de citron vert et le sirop de miel.",
            "Compléter avec la Ginger Beer pétillante.",
            "Remuer une fois délicatement."
        ),
        garnish = "Morceau de gingembre confit et branche de thym.",
        glassware = "Mug en cuivre ou Tumbler",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/stsuqq1441207660.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Nordic Minimal"
    ),

    // 115. Green Detox Fizz
    CocktailEntity(
        id = 115,
        name = "Green Detox Fizz",
        subtitle = "Le Concombre Vivifiant & Basilic Frais",
        description = "0% alcool : un élixir de bien-être ultra-désaltérant mariant la fraîcheur aqueuse du concombre, l'arôme anisé du basilic et la vivacité du citron vert.",
        category = "Mocktails",
        flavorProfile = "Frais/Botanique",
        prepTimeMinutes = 3,
        difficulty = "Moyen",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Concombre frais", "Rondelles découpées", 0.0, "4 rondelles", false, "spa"),
            RecipeIngredient("Basilic frais", "Feuilles fraîches", 0.0, "5 feuilles", false, "spa"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 2.5, "1 shooter", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sirop simple", 1.5, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Eau gazeuse", "Fraîche et fine", 10.0, "Allonger", false, "nutrition")
        ),
        steps = listOf(
            "Écraser les rondelles de concombre et le basilic dans le shaker avec le citron et le sirop.",
            "Ajouter de la glace et shaker brièvement 6 secondes.",
            "Double-filtrer dans un grand verre rempli de glaçons frais.",
            "Allonger d'eau gazeuse fraîche."
        ),
        garnish = "Ruban de concombre le long du verre et feuille de basilic.",
        glassware = "Verre Highball",
        shakeSeconds = 6,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/5jdp5r1487603680.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Botanical Garden"
    ),

    // 116. Passion Colada
    CocktailEntity(
        id = 116,
        name = "Passion Colada",
        subtitle = "L'Exotisme Mangue, Passion & Lait de Coco",
        description = "0% alcool : la rencontre solaire entre le velouté de la mangue mûre, le peps acidulé du fruit de la passion et l'onctuosité soyeuse du lait de coco.",
        category = "Mocktails",
        flavorProfile = "Exotique/Crémeux",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Nectar de mangue", "Pur nectar riche", 8.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus de fruit de la passion", "Jus pur acidulé", 5.0, "1 shooter et demi", false, "nutrition"),
            RecipeIngredient("Lait de coco", "Lait onctueux", 4.0, "1 shooter", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 1.0, "1 cuil. à café", false, "nutrition")
        ),
        steps = listOf(
            "Placer tous les ingrédients dans le shaker avec des glaçons cubiques.",
            "Frapper vivement pendant 12 secondes pour bien aérer la texture du lait de coco.",
            "Verser dans un verre hurricane rempli de glaçons frais."
        ),
        garnish = "Demi-fruit de la passion et paille en inox.",
        glassware = "Verre Hurricane",
        shakeSeconds = 12,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/6trfve1582473527.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Sunset Tropics"
    ),

    // 117. Pink Grapefruit Spritz 0%
    CocktailEntity(
        id = 117,
        name = "Pink Grapefruit Spritz 0%",
        subtitle = "Le Spritz Vénitien Sans Alcool & Romarin",
        description = "0% alcool : toute l'élégance de l'apéritif italien réinventée avec du pur jus de pamplemousse rose, un bitter sans alcool, du tonic effervescent et du romarin aromatique.",
        category = "Mocktails",
        flavorProfile = "Amer/Pétillant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de pamplemousse", "Pur jus pressé rose", 8.0, "1/3 grand verre", false, "nutrition"),
            RecipeIngredient("Sirop de pamplemousse amer", "Ou bitter sans alcool", 2.0, "1 cuil. à soupe", false, "water_drop"),
            RecipeIngredient("Tonic Water", "Eau tonique fraîche", 10.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Romarin", "Branche fraîche", 0.0, "1 branche", true, "spa")
        ),
        steps = listOf(
            "Remplir un grand verre ballon de gros glaçons.",
            "Verser le jus de pamplemousse et le sirop de bitter.",
            "Compléter avec l'eau tonique fraîche.",
            "Mélanger délicatement à l'aide de la branche de romarin pour libérer les arômes."
        ),
        garnish = "Tranche de pamplemousse rose et branche de romarin.",
        glassware = "Grand verre Ballon",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://images.unsplash.com/photo-1560512823-829485b8bf24?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Italian Piazza"
    ),

    // 118. Watermelon Breeze
    CocktailEntity(
        id = 118,
        name = "Watermelon Breeze",
        subtitle = "La Vague Pastèque Givrée & Canneberge",
        description = "0% alcool : une vague désaltérante de pur jus de pastèque fraîche mixée, équilibrée par la vivacité de la canneberge et du citron vert pressé.",
        category = "Mocktails",
        flavorProfile = "Frais/Fruité",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Jus de pastèque", "Pur jus frais mixé", 12.0, "1 grand verre", false, "nutrition"),
            RecipeIngredient("Jus de canneberge", "Cranberry pur jus", 4.0, "1 shooter et demi", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé minute", 2.0, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sirop simple", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Placer les jus de pastèque, de canneberge, de citron vert et le sirop dans le shaker avec des glaçons.",
            "Frapper énergiquement pendant 8 secondes.",
            "Verser sans filtrer la pulpe fine dans un verre rempli de glace pilée."
        ),
        garnish = "Petit triangle de pastèque sur le rebord et feuille de menthe.",
        glassware = "Verre Tumbler",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://images.unsplash.com/photo-1609951651556-5334e2706168?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Summer Poolside"
    ),

    // 119. Virgin Moscow Mule
    CocktailEntity(
        id = 119,
        name = "Virgin Moscow Mule",
        subtitle = "Le Caractère Cuivré Gingembre & Citron Vert",
        description = "0% alcool : toute l'énergie piquante du Moscow Mule servie dans sa chope de cuivre glacée avec une Ginger Beer corsée, du citron vert et du concombre croquant.",
        category = "Mocktails",
        flavorProfile = "Épicé/Vivifiant",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Ginger Beer", "Gingembre épicé sans alcool", 14.0, "Allonger", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 3.0, "1 grand shooter", false, "nutrition"),
            RecipeIngredient("Sirop de sucre de canne", "Sirop simple", 1.0, "1 cuil. à café", false, "water_drop"),
            RecipeIngredient("Concombre frais", "Rondelles croquantes", 0.0, "2 rondelles", false, "spa")
        ),
        steps = listOf(
            "Remplir une chope en cuivre traditionnelle de glace pilée.",
            "Verser le jus de citron vert pressé et le sirop de canne.",
            "Allonger avec la Ginger Beer bien frappée.",
            "Remuer délicatement et insérer les rondelles de concombre."
        ),
        garnish = "Quartier de citron vert et rondelle de concombre.",
        glassware = "Chope en cuivre",
        shakeSeconds = 0,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/3pylqc1504370988.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Copper Beats"
    ),

    // 120. Coco Mango Dream
    CocktailEntity(
        id = 120,
        name = "Coco Mango Dream",
        subtitle = "L'Élixir Velouté Mangue & Eau de Coco",
        description = "0% alcool : un délice soyeux et hydratant alliant purée de mangue mûre, eau de coco fraîche naturelle, une pointe de vanille bourbon et un zeste de citron vert.",
        category = "Mocktails",
        flavorProfile = "Doux/Exotique",
        prepTimeMinutes = 3,
        difficulty = "Facile",
        alcoholPercentage = 0.0,
        ingredients = listOf(
            RecipeIngredient("Nectar de mangue", "Purée de mangue mûre", 8.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Eau de coco", "Eau de coco fraîche", 8.0, "1/3 verre", false, "nutrition"),
            RecipeIngredient("Jus de citron vert", "Pressé frais", 1.5, "1 cuil. à soupe", false, "nutrition"),
            RecipeIngredient("Sirop de vanille", "Vanille bourbon liquide", 1.0, "1 cuil. à café", false, "water_drop")
        ),
        steps = listOf(
            "Mettre tous les ingrédients dans un shaker avec de la glace.",
            "Frapper énergiquement pendant 10 secondes.",
            "Verser dans un verre highball avec des glaçons frais."
        ),
        garnish = "Pincée de vanille moulue et feuille de menthe.",
        glassware = "Verre Highball",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/rytuex1598719770.jpg",
        isFavorite = false,
        vibeTag = "Chill & Lounge - Deep Tropics"
    ),

    // 121. Tequila Paf
    CocktailEntity(
        id = 121,
        name = "Tequila Paf",
        subtitle = "Le Rituel Culte Taper-Gober",
        description = "Le shot de fête mythique des soirées étudiantes : sel sur le poignet, tequila frappée sur le comptoir avec du tonic pour une explosion gazeuse instantanée, et quartier de citron vert croqué dans la foulée.",
        category = "Shooters",
        flavorProfile = "Piquant/Agreste",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 28.0,
        ingredients = listOf(
            RecipeIngredient("Tequila Blanco", "Tequila 100% agave", 3.0, "2/3 shooter", false, "liquor"),
            RecipeIngredient("Schweppes Tonic", "Pétillant bien frais", 2.0, "1/3 shooter", false, "water_drop"),
            RecipeIngredient("Citron vert", "Quartier juteux", 0.0, "1 quartier", true, "nutrition"),
            RecipeIngredient("Sel fin", "Pincée sur le dos de la main", 0.0, "1 pincée", true, "grain")
        ),
        steps = listOf(
            "Lécher le dos de la main entre le pouce et l'index, puis déposer une pincée de sel.",
            "Verser la tequila puis le tonic dans un verre à shooter à fond épais.",
            "Couvrir le verre avec la paume de la main, le frapper fermement deux fois sur le comptoir (« Paf ! »).",
            "Lécher le sel, boire d'un trait le shooter en pleine effervescence, puis mordre dans le quartier de citron vert."
        ),
        garnish = "Quartier de citron vert frais et sel.",
        glassware = "Verre Shooter à fond épais",
        shakeSeconds = 0,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/ek0mlq1504820601.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 122. Kiss Cool
    CocktailEntity(
        id = 122,
        name = "Kiss Cool",
        subtitle = "Le Double Effet Glacial",
        description = "Le cocktail shooter emblématique des clubs français : un shoot bleu lagon électrisant mariant le punch de la vodka, la menthe poivrée glaciale du Get 27 et le bleu hypnotique du Curaçao.",
        category = "Shooters",
        flavorProfile = "Mentholé/Glacé",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 24.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Get 27", "Liqueur de menthe poivrée", 2.0, "1/2 shooter", false, "invert_colors"),
            RecipeIngredient("Curaçao bleu", "Liqueur d'orange bleue", 0.5, "1 trait", false, "water_drop")
        ),
        steps = listOf(
            "Remplir un shaker de glaçons.",
            "Verser la vodka, le Get 27 et le curaçao bleu.",
            "Frapper vigoureusement pendant 8 secondes pour refroidir au maximum.",
            "Filtrer dans un verre shooter givré et déguster d'un trait."
        ),
        garnish = "Givrage bleu sur le rebord.",
        glassware = "Verre Shooter givré",
        shakeSeconds = 8,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/dbtylp1493067262.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 123. Tétine
    CocktailEntity(
        id = 123,
        name = "Tétine",
        subtitle = "Le Shot Bonbon Acidulé",
        description = "Le chouchou régressif des nuits étudiantes : un shot rouge bonbon sucré et piquant associant vodka, liqueur de fraise des bois, jus de citron jaune et une pointe de grenadine.",
        category = "Shooters",
        flavorProfile = "Fruité/Bonbon",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 19.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka pure", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de fraise", "Fraise des bois gourmande", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Jus de citron jaune frais", "Pressé minute pour le peps", 1.0, "1 cuil. à café", false, "nutrition"),
            RecipeIngredient("Sirop de grenadine", "Pour la couleur rubis intense", 0.5, "1 trait", false, "water_drop")
        ),
        steps = listOf(
            "Mettre tous les ingrédients dans un shaker rempli de glace.",
            "Shaker vivement pendant 6 secondes.",
            "Verser dans un verre à shooter.",
            "Accrocher une friandise tétine acidulée sur le rebord du verre avant de servir."
        ),
        garnish = "Bonbon tétine acidulé sur le bord.",
        glassware = "Verre Shooter",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/fegm621503564966.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 124. Flatliner
    CocktailEntity(
        id = 124,
        name = "Flatliner",
        subtitle = "L'Électrochoc Pimenté",
        description = "Un shot audacieux et tranchant à trois étages : la douceur d'une Sambuca blanche, une ligne de démarcation brûlante de Tabasco rouge en suspension, et la puissance d'une Tequila 100% agave.",
        category = "Shooters",
        flavorProfile = "Épicé/Puissant",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 34.0,
        ingredients = listOf(
            RecipeIngredient("Sambuca", "Liqueur d'anis douce", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Tequila Blanco", "Tequila blanche pure", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Tabasco", "Sauce pimentée rouge", 0.2, "4 gouttes en ligne", false, "grain")
        ),
        steps = listOf(
            "Verser la Sambuca au fond du verre à shooter.",
            "Déposer avec précaution 4 à 5 gouttes de Tabasco rouge pour créer une ligne horizontale suspendue à la surface de la Sambuca.",
            "Faire couler délicatement la Tequila sur le dos d'une cuillère pour former la strate supérieure transparente.",
            "Admirer la ligne de piment au milieu puis avaler cul-sec !"
        ),
        garnish = "Ligne rouge de Tabasco en lévitation.",
        glassware = "Verre Shooter transparent",
        shakeSeconds = 0,
        rating = 4.6,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/tysssx1473344692.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 125. Vodka Caramel
    CocktailEntity(
        id = 125,
        name = "Vodka Caramel",
        subtitle = "La Gourmandise Fondante & Givrée",
        description = "La liqueur maison incontournable des soirées festives : une vodka soyeuse infusée aux arômes chauds de caramel au beurre salé et de vanille bourbon, servie extra-froide.",
        category = "Shooters",
        flavorProfile = "Caramélisé/Chaud",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 22.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche de qualité", 3.0, "2/3 shooter", false, "liquor"),
            RecipeIngredient("Sirop de caramel", "Caramel beurre salé liquide", 1.5, "1/3 shooter", false, "water_drop"),
            RecipeIngredient("Fleur de sel", "Pour exalter les notes grillées", 0.1, "1 micro-pincée", false, "grain")
        ),
        steps = listOf(
            "Dans un shaker garni de glace pilée, mélanger la vodka, le sirop de caramel et une micro-pincée de fleur de sel.",
            "Shaker vigoureusement 10 secondes pour rendre le mélange glacé et légèrement sirupeux.",
            "Filtrer dans un shooter sortant du congélateur."
        ),
        garnish = "Rebord caramélisé et touche de fleur de sel.",
        glassware = "Verre Shooter givré",
        shakeSeconds = 10,
        rating = 4.9,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/ryvtsu1441253851.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    ),

    // 126. Russe Blanc Shooter
    CocktailEntity(
        id = 126,
        name = "Russe Blanc Shooter",
        subtitle = "Le White Russian Concentré",
        description = "La version shooter express du classique immortalisé par The Dude : un socle de vodka et Kahlúa frappés, couronné d'un nuage de crème fraîche froide posé à la cuillère.",
        category = "Shooters",
        flavorProfile = "Café/Onctueux",
        prepTimeMinutes = 2,
        difficulty = "Moyen",
        alcoholPercentage = 21.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de café Kahlúa", "Café noir torréfié", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Crème liquide entière", "Crème fraîche bien froide", 1.0, "1 nappe", false, "water_drop")
        ),
        steps = listOf(
            "Mélanger la vodka et la liqueur de café dans un shaker avec de la glace.",
            "Filtrer dans le verre à shooter.",
            "Déposer avec douceur la crème liquide entière sur le dos d'une cuillère de bar pour former un chapeau immaculé.",
            "Déguster d'un seul coup pour sentir le contraste chaud-froid."
        ),
        garnish = "Grain de café torréfié déposé sur la crème.",
        glassware = "Verre Shooter",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/rvyvxs1473482359.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 127. Coup de Foudre
    CocktailEntity(
        id = 127,
        name = "Coup de Foudre",
        subtitle = "L'Onde de Choc Glaciale & Épicée",
        description = "Un shot vif qui réveille instantanément l'assemblée : gin aromatique, Get 31 à la menthe blanche arctique et un kick secret de Tabasco ou de gingembre pressé.",
        category = "Shooters",
        flavorProfile = "Arctique/Épicé",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 26.0,
        ingredients = listOf(
            RecipeIngredient("Gin", "Gin sec London Dry", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Get 31", "Liqueur de menthe blanche", 2.0, "1/2 shooter", false, "invert_colors"),
            RecipeIngredient("Tabasco", "Piment rouge vif", 0.1, "1 goutte", false, "grain")
        ),
        steps = listOf(
            "Frapper le Gin et le Get 31 au shaker avec des glaçons pendant 8 secondes.",
            "Verser dans un verre à shooter.",
            "Ajouter une unique goutte de Tabasco au centre : le frisson glacé est suivi d'une agréable chaleur épicée."
        ),
        garnish = "Feuille de menthe givrée.",
        glassware = "Verre Shooter glacé",
        shakeSeconds = 8,
        rating = 4.6,
        imageUrl = "https://images.unsplash.com/photo-1575023782549-62ca0d244b39?auto=format&fit=crop&w=800&q=80",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 128. Melon Ball Shooter
    CocktailEntity(
        id = 128,
        name = "Melon Ball Shooter",
        subtitle = "L'Éclat Fluo & Fruité",
        description = "Le shot star des pistes de danse : une couleur vert émeraude fluorescente inimitable, combinant le parfum envoûtant de melon vert japonais, la fraîcheur du jus d'ananas et la netteté de la vodka.",
        category = "Shooters",
        flavorProfile = "Exotique/Sucré",
        prepTimeMinutes = 2,
        difficulty = "Facile",
        alcoholPercentage = 17.0,
        ingredients = listOf(
            RecipeIngredient("Midori", "Liqueur de melon vert japonais", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Vodka", "Vodka blanche pure", 1.5, "1/3 shooter", false, "liquor"),
            RecipeIngredient("Jus d'ananas", "Pur jus pressé", 1.5, "1/3 shooter", false, "nutrition")
        ),
        steps = listOf(
            "Verser le Midori, la vodka et le jus d'ananas dans un shaker plein de glace.",
            "Shaker avec énergie pendant 8 secondes.",
            "Passer au tamis et verser dans un shooter pour une mousse onctueuse en surface."
        ),
        garnish = "Bille de melon ou tranche fine d'ananas.",
        glassware = "Verre Shooter fluo",
        shakeSeconds = 8,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/tpupvr1478251697.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 129. Fireball Shot
    CocktailEntity(
        id = 129,
        name = "Fireball Shot",
        subtitle = "La Flamme Cannelle & Pomme",
        description = "Le grand classique qui met le feu à la piste : whisky chauffé aux notes intenses de cannelle épicée et de liqueur de pomme verte acidulée style Manzana.",
        category = "Shooters",
        flavorProfile = "Cannelle/Chaud",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 27.0,
        ingredients = listOf(
            RecipeIngredient("Whisky à la cannelle", "Style Fireball", 2.5, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Manzana", "Liqueur de pomme verte acidulée", 2.0, "1/2 shooter", false, "invert_colors"),
            RecipeIngredient("Sirop de canne", "Léger liant sucré", 0.5, "1 trait", false, "water_drop")
        ),
        steps = listOf(
            "Verser le whisky à la cannelle, la Manzana et le trait de sirop de canne dans le shaker avec des glaçons.",
            "Frapper 6 secondes pour rafraîchir sans diluer.",
            "Filtrer dans le shooter et savourer la sensation épicée."
        ),
        garnish = "Bord givré au sucre à la cannelle.",
        glassware = "Verre Shooter",
        shakeSeconds = 6,
        rating = 4.7,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/yqwuwu1441248116.jpg",
        isFavorite = false,
        vibeTag = "Clubbing & Electro"
    ),

    // 130. Woo Woo Shooter
    CocktailEntity(
        id = 130,
        name = "Woo Woo Shooter",
        subtitle = "Le Shot Rubis Festif",
        description = "La version shot du célèbre cocktail festif : une robe rouge rubis pétillante associant vodka soyeuse, liqueur de pêche gorgée de soleil et jus de canneberge acidulé.",
        category = "Shooters",
        flavorProfile = "Fruité/Acidulé",
        prepTimeMinutes = 1,
        difficulty = "Facile",
        alcoholPercentage = 18.0,
        ingredients = listOf(
            RecipeIngredient("Vodka", "Vodka blanche pure", 2.0, "1/2 shooter", false, "liquor"),
            RecipeIngredient("Liqueur de pêche", "Pêche blanche parfumée", 1.5, "1/3 shooter", false, "invert_colors"),
            RecipeIngredient("Jus de cranberry", "Canneberge acidulée", 1.5, "1/3 shooter", false, "nutrition")
        ),
        steps = listOf(
            "Dans un shaker garni de glaçons, verser la vodka, la liqueur de pêche et le jus de cranberry.",
            "Shaker vivement pendant 6 secondes.",
            "Verser dans le shooter et déguster bien frais entre amis !",
            "Boire cul-sec en levant son verre au groupe."
        ),
        garnish = "Quartier fin de pêche ou canneberge fraîche.",
        glassware = "Verre Shooter",
        shakeSeconds = 6,
        rating = 4.8,
        imageUrl = "https://www.thecocktaildb.com/images/media/drink/7p607y1504735343.jpg",
        isFavorite = false,
        vibeTag = "Soirée Étudiante"
    )
)



