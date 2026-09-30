// Données complètes des cocktails de Velvet Cocktail (130 recettes avec 41 URLs vérifiées)
import { Cocktail } from "../types";

export const COCKTAILS: Cocktail[] = [
  {
    "id": 1,
    "name": "Midnight Blackberry Bramble",
    "subtitle": "Signature Velvet Speakeasy",
    "description": "Une alchimie ténébreuse de gin infusé aux baies, de mûres sauvages et d'un nappage velouté aux lueurs violettes.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Botanique",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 17.5,
    "ingredients": [
      {
        "name": "Gin infusé aux baies",
        "details": "Genièvre & baies sauvages",
        "amountCl": 4.5,
        "measureDescription": "3 cuil. à soupe",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron jaune frais",
        "details": "Pressé minute pour acidité vive",
        "amountCl": 2.5,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sucre de canne liquide",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Crème de Mûre",
        "details": "Liqueur de mûres sauvages",
        "amountCl": 2.0,
        "measureDescription": "1.5 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Remplir le shaker de glace pilée, ajouter gin, citron, sucre.",
      "Shaker 12s.",
      "Filtrer dans un verre vintage.",
      "Verser la crème de mûre en filet."
    ],
    "garnish": "3 mûres givrées, brin de menthe.",
    "glassware": "Verre vintage taillé",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/twtbh51630406392.jpg",
    "isFavorite": true,
    "vibeTag": "Dim-lit Speakeasy & Deep House"
  },
  {
    "id": 2,
    "name": "Orgasme",
    "subtitle": "Le Shooter Gourmand & Stratifié",
    "description": "Le shooter aphrodisiaque et onctueux par excellence : triple couche veloutée mêlant le café torréfié, l'amande douce et la crème de whisky irlandaise.",
    "category": "Shooters",
    "flavorProfile": "Doux/Crémeux",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Liqueur de café Kahlúa",
        "details": "Café torréfié & vanille",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Amaretto",
        "details": "Liqueur d'amande douce",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Crème irlandaise Baileys",
        "details": "Crème de whisky onctueuse",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser la liqueur de café Kahlúa au fond du verre à shooter.",
      "Faire couler délicatement l'Amaretto avec le dos d'une cuillère de bar pour créer la strate médiane.",
      "Napper enfin délicatement avec le Baileys pour former la dernière couche onctueuse.",
      "Déguster d'un seul trait cul-sec !"
    ],
    "garnish": "Trois strates contrastées.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vr6kle1504886114.jpg",
    "isFavorite": true,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 3,
    "name": "Neon Margarita",
    "subtitle": "Clubbing Edition Électrique",
    "description": "Une Margarita audacieuse aux reflets bleus luminescents, relevée par une tequila vive et un bord délicatement givré.",
    "category": "Cocktails",
    "flavorProfile": "Acidulé/Électrique",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 22.0,
    "ingredients": [
      {
        "name": "Tequila Blanco",
        "details": "100% agave bleu",
        "amountCl": 5.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Curaçao Bleu",
        "details": "Liqueur d'orange bleue",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1 citron vert entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop d'agave",
        "details": "Ambré bio",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Givrer le bord du verre au sel fin.",
      "Shaker tous les ingrédients avec de la glace.",
      "Filtrer dans un verre à Margarita."
    ],
    "garnish": "Rondelle de citron vert.",
    "glassware": "Verre à Margarita",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1556881286-fc6915169721?auto=format&fit=crop&w=800&q=80",
    "isFavorite": true,
    "vibeTag": "Clubbing & Future Bass"
  },
  {
    "id": 4,
    "name": "Velvet Virgin Mojito",
    "subtitle": "Mocktails & Sans Alcool",
    "description": "0% alcool, 100% fraîcheur : menthe fraîche froissée, citron vert tonique et pétillement désaltérant.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Herbacé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Eau gazeuse",
        "details": "Pétillante fraîche",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1 citron vert entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de canne",
        "details": "Sucre de canne",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Feuilles de menthe fraîche",
        "details": "Feuilles entières aromatiques",
        "amountCl": 0.0,
        "measureDescription": "10 unités",
        "isGarnish": false,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Piler doucement la menthe avec le sirop et le citron au fond du verre.",
      "Remplir de glace pilée.",
      "Compléter à l'eau gazeuse et mélanger à la cuillère."
    ],
    "garnish": "Tête de menthe fraîche.",
    "glassware": "Verre Tumbler Highball",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://images.unsplash.com/photo-1544145945-f90425340c7e?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Pop Solaire & Chill House"
  },
  {
    "id": 5,
    "name": "Mojito",
    "subtitle": "Le Classique Cubain Par Excellence",
    "description": "L'emblématique mariage cubain entre la fraîcheur éclatante de la menthe froissée, l'acidité vive du citron vert et le caractère du rhum blanc.",
    "category": "Classiques",
    "flavorProfile": "Frais/Herbacé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum agricole cubain",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Citron vert",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1/2 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de canne",
        "details": "Sucre de canne liquide",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Menthe fraîche",
        "details": "Feuilles réveillées",
        "amountCl": 0.0,
        "measureDescription": "8 à 10 feuilles",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche et pétillante",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Piler doucement la menthe avec le sirop de canne et le citron vert au fond du verre sans déchirer les feuilles.",
      "Ajouter le rhum blanc et remplir le verre aux deux tiers de glace pilée.",
      "Shaker ou remuer vivement à la cuillère de bar pour mélanger les saveurs.",
      "Allonger à l'eau gazeuse fraîche et ajouter un dôme de glace pilée."
    ],
    "garnish": "Menthe.",
    "glassware": "Verre Tumbler Highball",
    "shakeSeconds": 6,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/metwgh1606770327.jpg",
    "isFavorite": true,
    "vibeTag": "Latin Beats & Sunset Lounge"
  },
  {
    "id": 6,
    "name": "Moscow Mule",
    "subtitle": "Timbale Givrée & Gingembre Ardent",
    "description": "Un cocktail culte et ultra-rafraîchissant, alliant la neutralité tranchante de la vodka au piquant vivifiant de la ginger beer artisanale.",
    "category": "Classiques",
    "flavorProfile": "Épicé/Frais",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka de grain pure",
        "amountCl": 5.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1/2 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Ginger Beer",
        "details": "Gingembre fermenté pétillant",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir la timbale en cuivre de glace pilée jusqu'en haut.",
      "Verser la vodka et le jus de citron vert frais direct au verre.",
      "Allonger délicatement avec la ginger beer pétillante et mélanger doucement d'un coup de cuillère."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Timbale en cuivre givrée",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/3pylqc1504370988.jpg",
    "isFavorite": false,
    "vibeTag": "Urban Chill & French Rap"
  },
  {
    "id": 7,
    "name": "Sex on the Beach",
    "subtitle": "L'Élixir Coucher de Soleil",
    "description": "Une création fruitée irrésistible aux nuances orangées et rubis, où la pêche sucrée s'harmonise avec le peps du cranberry.",
    "category": "Classiques",
    "flavorProfile": "Fruité/Sucré",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de pêche",
        "details": "Pêche de vigne douce",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus d'orange pressée",
        "amountCl": 6.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge acidulée",
        "amountCl": 6.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, la liqueur de pêche et le jus d'orange dans un shaker rempli de glaçons.",
      "Shaker avec entrain pendant 8 secondes.",
      "Verser dans un verre highball avec des glaçons, puis napper de jus de cranberry pour un dégradé spectaculaire."
    ],
    "garnish": "Tranche d'orange.",
    "glassware": "Verre Hurricane ou Highball",
    "shakeSeconds": 8,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/szmj2d1504889961.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing Dance & Pop 2000s"
  },
  {
    "id": 8,
    "name": "Piña Colada",
    "subtitle": "Évasion Onctueuse Caribéenne",
    "description": "L'accord parfait de rhum caribéen, de jus d'ananas frais et d'une crème de coco veloutée transportant immédiatement sous les tropiques.",
    "category": "Classiques",
    "flavorProfile": "Doux/Crémeux",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum des îles",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'ananas",
        "details": "Ananas mûr et parfumé",
        "amountCl": 10.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Crème de coco",
        "details": "Lait & crème de coco riche",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser le rhum blanc, le jus d'ananas et la crème de coco dans le shaker ou le bol d'un blender.",
      "Ajouter une pelle de glace pilée et shaker vigoureusement (ou mixer 15 secondes) jusqu'à consistance crémeuse et mousseuse.",
      "Verser sans filtrer dans un grand verre tropical sur lit de glace."
    ],
    "garnish": "Triangle d'ananas.",
    "glassware": "Verre Poco Grande ou Hurricane",
    "shakeSeconds": 14,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/upgsue1668419912.jpg",
    "isFavorite": true,
    "vibeTag": "Tropical Island & Chill Afrobeats"
  },
  {
    "id": 9,
    "name": "Cosmopolitan",
    "subtitle": "Le Glamour Chic New-Yorkais",
    "description": "Raffiné et percutant : un équilibre magistral entre la vodka citronnée, la vivacité du Cointreau et la robe rose rubis du cranberry.",
    "category": "Classiques",
    "flavorProfile": "Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Triple sec d'oranges douces",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge pure",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer tous les ingrédients dans le shaker rempli de cubes de glace réguliers.",
      "Shaker vigoureusement pendant 10 secondes jusqu'à condensation extérieure.",
      "Double-filtrer dans une coupe à martini rafraîchie pour retenir les éclats de glace."
    ],
    "garnish": "Zeste de citron.",
    "glassware": "Verre à Martini taillé",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/kpsajh1504368362.jpg",
    "isFavorite": false,
    "vibeTag": "Nu-Disco & Electro Pop"
  },
  {
    "id": 10,
    "name": "Aperol Spritz",
    "subtitle": "L'Apéritif Vénitien Doré",
    "description": "La dolce vita italienne dans un verre : la douce amertume de l'Aperol sublimée par l'effervescence joyeuse du Prosecco.",
    "category": "Classiques",
    "flavorProfile": "Amer/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 11.0,
    "ingredients": [
      {
        "name": "Aperol",
        "details": "Apéritif amère d'herbes & oranges",
        "amountCl": 6.0,
        "measureDescription": "1/3 du verre",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Prosecco",
        "details": "Vin effervescent italien brut",
        "amountCl": 9.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Eau gazeuse",
        "details": "Trait pétillant",
        "amountCl": 3.0,
        "measureDescription": "Un trait",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un grand verre ballon de gros glaçons.",
      "Verser directement le Prosecco, puis l'Aperol en mouvement circulaire pour une teinte homogène.",
      "Compléter par un trait d'eau gazeuse et remuer délicatement une seule fois."
    ],
    "garnish": "Tranche d'orange.",
    "glassware": "Grand verre ballon à pied",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/j9evx11504373665.jpg",
    "isFavorite": true,
    "vibeTag": "Italian Rooftop & Deep House"
  },
  {
    "id": 11,
    "name": "Espresso Martini",
    "subtitle": "Élixir Nocturne & Crema Soyeuse",
    "description": "Le remontant sophistiqué des nuits londoniennes : café torréfié intense, vodka glacée et mousse onctueuse digne d'un barista.",
    "category": "Classiques",
    "flavorProfile": "Corsé/Café",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de café",
        "details": "Kahlúa ou liqueur artisanale",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Expresso froid",
        "details": "Shot expresso fraîchement extrait",
        "amountCl": 3.0,
        "measureDescription": "1 shot",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne liquide",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Extraire un expresso court serré et le laisser tiédir ou refroidir rapidement.",
      "Verser la vodka, la liqueur de café, l'expresso et le sirop de sucre dans le shaker avec une abondance de glaçons compacts.",
      "Shaker très vigoureusement pendant 12 secondes afin de densifier la mousse en surface.",
      "Double-filtrer sans tarder dans une coupe à martini rafraîchie."
    ],
    "garnish": "3 grains de café.",
    "glassware": "Coupe Martini élégante",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/n0sx531504372951.jpg",
    "isFavorite": true,
    "vibeTag": "Midnight Speakeasy & Lounge Jazz"
  },
  {
    "id": 12,
    "name": "Negroni",
    "subtitle": "L'Amertume Aristocratique Italienne",
    "description": "La trinité légendaire de la mixologie mondiale : une part de gin sec, une part de vermouth doux et une part de bitter Campari écarlate.",
    "category": "Classiques",
    "flavorProfile": "Amer/Herbacé",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 24.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Gin sec botanique",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Campari",
        "details": "Bitter aromatique rouge rubis",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Vermouth Rouge",
        "details": "Vermouth di Torino doux",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Placer un gros cube de glace translucide ou des glaçons denses dans un verre Old Fashioned.",
      "Verser le gin, le Campari et le vermouth rouge dans le verre à mélange ou direct sur la glace.",
      "Mélanger délicatement à la cuillère de bar pendant 30 secondes pour une dilution et un rafraîchissement optimaux.",
      "Exprimer un zeste d'orange pour parfumer les essences et déposer sur la glace."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Verre Old Fashioned Lowball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1470337458703-46ad1756a187?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Velvet Speakeasy & Vinyl Soul"
  },
  {
    "id": 13,
    "name": "Caipirinha",
    "subtitle": "L'Âme Festve du Brésil",
    "description": "L'esprit de Rio de Janeiro condensé : le goût végétal puissant de la Cachaça pur jus de canne allié aux quartiers de citrons écrasés.",
    "category": "Classiques",
    "flavorProfile": "Acide/Sucré",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Cachaça",
        "details": "Eau-de-vie de canne brésilienne",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Citron vert",
        "details": "Coupé en quartiers",
        "amountCl": 3.0,
        "measureDescription": "1 entier coupé",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sucre roux",
        "details": "Cassonade pure",
        "amountCl": 2.0,
        "measureDescription": "2 cuillères",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Laver et couper le citron vert en 8 quartiers en retirant la membrane blanche centrale.",
      "Placer les quartiers et les deux cuillères de sucre roux dans le fond du verre.",
      "Piler fermement avec un pilon sans trop insister sur l'écorce pour ne pas développer d'amertume.",
      "Remplir de glace pilée, verser la Cachaça et remuer de bas en haut."
    ],
    "garnish": "Rondelle de citron vert.",
    "glassware": "Verre Tumbler bas",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/jgvn7p1582484435.jpg",
    "isFavorite": false,
    "vibeTag": "Bossa Nova & Latin Groove"
  },
  {
    "id": 14,
    "name": "Long Island Iced Tea",
    "subtitle": "Le Monument de Puissance",
    "description": "Une alchimie redoutable regroupant 5 spiritueux sous la douceur trompeuse d'un thé glacé pétillant.",
    "category": "Cocktails",
    "flavorProfile": "Puissant",
    "prepTimeMinutes": 4,
    "difficulty": "Expert",
    "alcoholPercentage": 22.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche",
        "amountCl": 1.5,
        "measureDescription": "1 trait généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Rhum",
        "details": "Rhum blanc léger",
        "amountCl": 1.5,
        "measureDescription": "1 trait généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Gin",
        "details": "Gin sec",
        "amountCl": 1.5,
        "measureDescription": "1 trait généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Tequila",
        "details": "Tequila Blanco",
        "amountCl": 1.5,
        "measureDescription": "1 trait généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'orange",
        "amountCl": 1.5,
        "measureDescription": "1 trait généreux",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Cola",
        "details": "Pétillant glacé",
        "amountCl": 8.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, le rhum, le gin, la tequila, le Cointreau et le jus de citron dans un shaker avec de la glace.",
      "Shaker énergiquement pendant 8 secondes.",
      "Filtrer dans un grand verre highball rempli de glaçons.",
      "Allonger au cola pour donner la célèbre couleur dorée de thé froid."
    ],
    "garnish": "Tranche de citron.",
    "glassware": "Verre Highball XXL",
    "shakeSeconds": 8,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/wx7hsg1504370510.jpg",
    "isFavorite": false,
    "vibeTag": "Peak Time EDM & Bass House"
  },
  {
    "id": 15,
    "name": "B-52",
    "subtitle": "Le Shooter Stratifié & Flambé",
    "description": "Le roi des shooters de fin de soirée : trois couches de densités distinctes couronnées d'une flamme bleutée captivante.",
    "category": "Shooters",
    "flavorProfile": "Chaud/Café",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Liqueur de café",
        "details": "Kahlúa dense",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Baileys",
        "details": "Crème de whisky",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Grand Marnier",
        "details": "Liqueur de cognac & orange",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser la liqueur de café directement au fond du verre à shooter.",
      "À l'aide du dos d'une cuillère de bar posée contre la paroi, verser très délicatement le Baileys pour créer la deuxième strate.",
      "Répéter l'opération avec le Grand Marnier pour faire flotter la troisième couche en surface.",
      "Flamber le dessus quelques secondes et boire cul-sec avec une paille."
    ],
    "garnish": "Flamme bleutée sur le dessus.",
    "glassware": "Verre Shooter transparent",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/5a3vg61504372070.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 16,
    "name": "Tequila Sunrise",
    "subtitle": "La Symphonie Lumineuse d'Acapulco",
    "description": "Une icône visuelle et gustative, évoquant un lever de soleil rouge flamboyant grâce à l'effet de pesanteur de la grenadine.",
    "category": "Classiques",
    "flavorProfile": "Fruité",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 11.0,
    "ingredients": [
      {
        "name": "Tequila",
        "details": "Tequila 100% agave",
        "amountCl": 5.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus d'orange",
        "amountCl": 10.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine concentrée",
        "amountCl": 1.5,
        "measureDescription": "1 trait pour le dégradé",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un verre tumbler de glaçons réguliers.",
      "Verser la tequila puis le jus d'orange frais, et remuer légèrement.",
      "Faire couler doucement le sirop de grenadine le long de la paroi : il plonge au fond pour créer le dégradé naturel."
    ],
    "garnish": "Demi-rondelle d'orange et cerise.",
    "glassware": "Verre Highball ou Hurricane",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/quqyqp1480879103.jpg",
    "isFavorite": false,
    "vibeTag": "Retro Synthwave & Pop 80s"
  },
  {
    "id": 17,
    "name": "Pornstar Martini",
    "subtitle": "Sensualité Exotique & Shot de Bulles",
    "description": "Le cocktail moderne le plus populaire au monde : nectar onctueux de fruit de la passion, vanille veloutée et son rituel accompagnement de bulles.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Exotique",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 16.0,
    "ingredients": [
      {
        "name": "Vodka Vanille",
        "details": "Vodka infusée vanille bourbon",
        "amountCl": 4.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Passoã",
        "details": "Liqueur de fruit de la passion",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Purée fruit de la passion",
        "details": "Pulpe naturelle",
        "amountCl": 3.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de vanille",
        "details": "Sirop aromatisé",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Shot de Champagne",
        "details": "À part bien frappé",
        "amountCl": 5.0,
        "measureDescription": "1 shooter à part",
        "isGarnish": true,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser la vodka vanille, le Passoã, la purée de passion et le sirop de vanille dans le shaker avec une abondance de glaçons.",
      "Shaker énergiquement pendant 12 secondes pour créer une mousse dorée dense.",
      "Double-filtrer dans une coupe rafraîchie.",
      "Déposer la demi-passion flottante et servir avec le shot de champagne frais en accompagnement."
    ],
    "garnish": "Demi-fruit de la passion.",
    "glassware": "Coupe Cocktail & Shot",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/xjhjdf1630406071.jpg",
    "isFavorite": true,
    "vibeTag": "Afrobeats & Modern R&B"
  },
  {
    "id": 18,
    "name": "Amaretto Sour",
    "subtitle": "L'Onctuosité Italienne Douce-Amère",
    "description": "Une texture en bouche incomparable : les arômes d'amande et de massepain adoucis par une émulsion soyeuse au citron frais.",
    "category": "Cocktails",
    "flavorProfile": "Doux/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Amaretto",
        "details": "Disaronno d'amande",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1 citron entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne liquide",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Blanc d'oeuf",
        "details": "Pour l'émulsion crémeuse",
        "amountCl": 1.0,
        "measureDescription": "1 blanc frais",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Effectuer d'abord un 'dry shake' (secouer sans glaçons) de tous les ingrédients pour émulsionner parfaitement le blanc d'oeuf.",
      "Ajouter ensuite de gros glaçons dans le shaker et shaker vigoureusement pendant 10 secondes.",
      "Filtrer dans un verre Old Fashioned sur un gros glaçon pour apprécier la collerette de mousse blanche."
    ],
    "garnish": "Cerise au marasquin et tranche d'orange.",
    "glassware": "Verre Old Fashioned",
    "shakeSeconds": 12,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1587888637140-849b25d80ef9?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Smooth Jazz & Lo-Fi Hip Hop"
  },
  {
    "id": 19,
    "name": "Cuba Libre",
    "subtitle": "La Brise Révolutionnaire Caribéenne",
    "description": "Bien plus qu'un simple rhum-coca : l'expression vive des huiles d'écorce de citron vert mariée aux notes boisées d'un bon rhum ambré.",
    "category": "Classiques",
    "flavorProfile": "Simple",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Rhum ambré",
        "details": "Rhum vieilli en fût",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé au verre",
        "amountCl": 1.5,
        "measureDescription": "1/2 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Cola",
        "details": "Cola bien frais",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre highball de glace jusqu'au bord.",
      "Presser un quartier de citron vert et le déposer dans le verre.",
      "Verser le rhum ambré puis allonger au cola direct au verre et remuer doucement à la cuillère."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre Highball classique",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://images.unsplash.com/photo-1595981267035-7b04ca84a82d?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Urban Reggaeton & Latin Pop"
  },
  {
    "id": 20,
    "name": "Blue Lagoon",
    "subtitle": "Le Lagon Bleu Électrique",
    "description": "Une immersion visuelle percutante : la teinte cyan luminescente du curaçao associée au peps tonique du citron et des bulles.",
    "category": "Cocktails",
    "flavorProfile": "Électrique",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Curaçao Bleu",
        "details": "Liqueur d'oranges bleues",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Limonade",
        "details": "Limonade pétillante",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, le curaçao bleu et le jus de citron dans un shaker avec de la glace.",
      "Shaker brièvement pour bien refroidir le mélange.",
      "Filtrer dans un verre rempli de glaçons et compléter avec la limonade fraîche."
    ],
    "garnish": "Rondelle de citron.",
    "glassware": "Verre Tumbler ou Ouragan",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/5wm4zo1582579154.jpg",
    "isFavorite": false,
    "vibeTag": "Future Rave & EDM Festival"
  },
  {
    "id": 21,
    "name": "White Russian",
    "subtitle": "La Gourmandise Culte & Veloutée",
    "description": "Le cocktail culte immortalisé au cinéma : le contraste sublime entre la liqueur de café sombre et le nappage immaculé de crème liquide.",
    "category": "Cocktails",
    "flavorProfile": "Doux/Crémeux",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 16.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de café",
        "details": "Kahlúa concentrée",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Crème liquide",
        "details": "Crème entière fluide",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un verre Old Fashioned de gros glaçons.",
      "Verser la vodka et la liqueur de café directement sur les glaçons et remuer.",
      "Faire couler délicatement la crème liquide sur le dos d'une cuillère pour la faire flotter au sommet."
    ],
    "garnish": "Saupoudré de muscade ou grains de café.",
    "glassware": "Verre Rocks Old Fashioned",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vsrupw1472405732.jpg",
    "isFavorite": false,
    "vibeTag": "Cozy Speakeasy & Retro Cinema"
  },
  {
    "id": 22,
    "name": "Mai Tai",
    "subtitle": "Le Trésor de la Culture Tiki",
    "description": "Un chef-d'œuvre de la mixologie polynésienne réunissant deux rhums de caractère, le parfum d'amande de l'orgeat et la vivacité des agrumes.",
    "category": "Cocktails",
    "flavorProfile": "Exotique",
    "prepTimeMinutes": 4,
    "difficulty": "Expert",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum blanc agricole",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Rhum ambré",
        "details": "Rhum vieux puissant",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'orange",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Sirop d'orgeat",
        "details": "Lait d'amande douce",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer le rhum blanc, le rhum ambré, le Cointreau, le sirop d'orgeat et le jus de citron vert dans le shaker rempli de glace pilée.",
      "Shaker vigoureusement pendant 12 secondes.",
      "Verser l'intégralité du contenu dans un verre Tiki ou Old Fashioned et compléter de glace pilée."
    ],
    "garnish": "Tête de menthe et quartier de citron vert.",
    "glassware": "Verre Tiki ou Double Old Fashioned",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/twyrrp1439907470.jpg",
    "isFavorite": false,
    "vibeTag": "Exotic Beats & Tropical House"
  },
  {
    "id": 23,
    "name": "Shirley Temple",
    "subtitle": "L'Incontournable Mocktail Hollywoodien",
    "description": "0% alcool : le cocktail sans alcool le plus célèbre d'Hollywood associant la fraîcheur épicée du Ginger Ale et le rubis gourmand de la grenadine.",
    "category": "Mocktails",
    "flavorProfile": "Sucré",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Ginger Ale",
        "details": "Soda gingembre doux",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine fruitée",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un grand verre de glaçons.",
      "Verser le jus de citron et le ginger ale bien frais.",
      "Verser délicatement le sirop de grenadine qui se diffuse en un élégant dégradé rubis."
    ],
    "garnish": "Cerise confite.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1536935338788-846bb9981813?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Pop Feel-Good & Acoustic Chills"
  },
  {
    "id": 24,
    "name": "Bora Bora",
    "subtitle": "Mocktail Évasion Paradis Pacifique",
    "description": "0% alcool : une explosion parfumée et désaltérante où l'ananas rencontre le fruit de la passion et une pointe acidulée de citron.",
    "category": "Mocktails",
    "flavorProfile": "Exotique",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de fruit de la passion",
        "details": "Nectar maracuja",
        "amountCl": 4.0,
        "measureDescription": "1/4 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Touche sucrée",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer le jus d'ananas, le jus de passion, le jus de citron et la grenadine dans un shaker avec de la glace.",
      "Shaker avec entrain pendant 10 secondes pour aérer le jus.",
      "Filtrer dans un grand verre sur lit de glace fraîche."
    ],
    "garnish": "Brochette de fruits exotiques.",
    "glassware": "Verre Cocktail Tropical",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/xwuqvw1473201811.jpg",
    "isFavorite": false,
    "vibeTag": "Sunset Chill & Afro House"
  },
  {
    "id": 25,
    "name": "Margarita",
    "subtitle": "L'Icône Mexicaine au Bord Salé",
    "description": "Le grand classique mexicain : l'agave vif de la tequila blanco balancé par les notes d'orange du Cointreau et l'acidité tranchante du citron vert sur bord salé.",
    "category": "Classiques",
    "flavorProfile": "Acide/Salé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Tequila Blanco",
        "details": "100% agave",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'oranges douces & amères",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Fleur de sel",
        "details": "Pour givrer le col",
        "amountCl": 0.0,
        "measureDescription": "Bordure",
        "isGarnish": true,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Frotter le bord du verre avec un quartier de citron vert puis tremper dans une coupelle de fleur de sel.",
      "Verser la tequila blanco, le Cointreau et le jus de citron vert dans un shaker rempli de glace.",
      "Shaker énergiquement pendant 10 secondes pour refroidir et émulsionner.",
      "Filtrer finement dans le verre préparé."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre à Margarita",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
    "isFavorite": true,
    "vibeTag": "Mexican Cantina & Latin Rhythms"
  },
  {
    "id": 26,
    "name": "Daiquiri",
    "subtitle": "La Pureté Caribéenne Frappée",
    "description": "L'archétype du cocktail parfait : trois ingrédients purs où le rhum blanc trouve son équilibre suprême entre fraîcheur citronnée et douceur de canne.",
    "category": "Classiques",
    "flavorProfile": "Acide/Sucré",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum agricole pur jus",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.5,
        "measureDescription": "1 citron vert entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sirop de canne simple 2:1",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser le rhum blanc, le jus de citron vert et le sirop de sucre dans le shaker avec une abondance de glace.",
      "Shaker vigoureusement pendant 12 secondes jusqu'à formation d'un givre dense à l'extérieur.",
      "Filtrer finement dans une coupe à cocktail préalablement refroidie."
    ],
    "garnish": "Rondelle de citron vert.",
    "glassware": "Coupe raffinée",
    "shakeSeconds": 12,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/mrz9091589574515.jpg",
    "isFavorite": false,
    "vibeTag": "Havana Nights & Mambo Beats"
  },
  {
    "id": 27,
    "name": "Gin Tonic",
    "subtitle": "L'Élégance Botanique & Bulles Fines",
    "description": "L'accord intemporel entre les baies de genièvre d'un gin de maître et la subtile amertume de quinine d'un tonic haut de gamme.",
    "category": "Classiques",
    "flavorProfile": "Amer/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 11.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Gin sec botanique",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Tonic",
        "details": "Tonic artisanal Fever-Tree",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un grand verre ballon (copa) de gros glaçons cristallins.",
      "Verser le gin sur les glaçons pour le glacer.",
      "Allonger délicatement avec le tonic frais en inclinant le verre pour préserver la bulle. Remuer une seule fois."
    ],
    "garnish": "Zeste de citron ou concombre.",
    "glassware": "Verre Copa Ballon",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1556679343-c7306c1976bc?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Speakeasy Lounge & Chill Lo-Fi"
  },
  {
    "id": 28,
    "name": "Whiskey Sour",
    "subtitle": "La Velouté Boisée d'un Grand Bourbon",
    "description": "Le monument américain : la puissance chaleureuse du bourbon adoucie par une mousse crémeuse soyeuse et la fraîcheur du citron jaune.",
    "category": "Classiques",
    "flavorProfile": "Acide/Crémeux",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 14.0,
    "ingredients": [
      {
        "name": "Bourbon",
        "details": "Bourbon vieilli pur grain",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.5,
        "measureDescription": "1/2 citron jaune",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Blanc d'oeuf",
        "details": "Pour l'émulsion dense",
        "amountCl": 1.0,
        "measureDescription": "1 blanc frais",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Effectuer d'abord un 'dry shake' (secouer sans glaçons) de tous les ingrédients pour fouetter le blanc d'oeuf.",
      "Ajouter ensuite de gros glaçons et shaker énergiquement pendant 10 secondes.",
      "Double-filtrer dans un verre rocks pour admirer la couronne de mousse nacrée."
    ],
    "garnish": "Cerise amarena.",
    "glassware": "Verre Rocks Old Fashioned",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/hbkfsh1589574990.jpg",
    "isFavorite": true,
    "vibeTag": "Bourbon Lounge & Vintage Vinyl"
  },
  {
    "id": 29,
    "name": "Jägerbomb",
    "subtitle": "L'Explosion Nocturne Énergisante",
    "description": "Le rituel festif culte des clubs : les 56 plantes du Jägermeister plongées en immersion dans une vague énergisante pétillante.",
    "category": "Shooters",
    "flavorProfile": "Festif/Énergétique",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Jägermeister",
        "details": "Liqueur de plantes allemandes",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Boisson énergisante",
        "details": "Pétillante fraîche",
        "amountCl": 6.0,
        "measureDescription": "Mini tumbler",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la boisson énergisante bien fraîche dans un grand verre tumbler.",
      "Remplir un verre à shot de Jägermeister.",
      "Lâcher le shot plein directement au centre du grand verre et boire d'un trait pendant l'effervescence."
    ],
    "garnish": "Effervescence du shot plongé.",
    "glassware": "Verre Tumbler & Shooter",
    "shakeSeconds": 0,
    "rating": 4.6,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/d30z931503565384.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 30,
    "name": "Kamikaze",
    "subtitle": "L'Impact Tranchant Citronné",
    "description": "Un shooter incisif et électrique né dans les bases navales américaines : l'intensité de la vodka adoucie par le triple sec et la fraîcheur du citron vert.",
    "category": "Shooters",
    "flavorProfile": "Acide/Sec",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 2.0,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Triple sec d'oranges",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, le Cointreau et le jus de citron vert dans un shaker avec de la glace.",
      "Shaker vigoureusement pendant 8 secondes pour frapper le liquide.",
      "Filtrer et répartir dans des verres à shooter givrés."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre Shooter givré",
    "shakeSeconds": 8,
    "rating": 4.7,
    "imageUrl": "https://images.unsplash.com/photo-1570598912132-0ba1dc952b7d?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 31,
    "name": "TGV",
    "subtitle": "Le Trio Grande Vitesse",
    "description": "Le shooter français mythique au départ immédiat : l'association sans compromis de Tequila, Gin et Vodka en proportions égales.",
    "category": "Shooters",
    "flavorProfile": "Puissant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 35.0,
    "ingredients": [
      {
        "name": "Tequila",
        "details": "Tequila Blanco",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Gin",
        "details": "Gin sec",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser à parts égales la tequila, le gin et la vodka direct au verre à shooter.",
      "Optionnellement, passer au shaker quelques secondes avec de la glace pour un shot frappé glacial.",
      "Consommer immédiatement cul-sec."
    ],
    "garnish": "Finition frappée pure.",
    "glassware": "Verre Shooter 6cl",
    "shakeSeconds": 4,
    "rating": 4.6,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/mx31hv1487602979.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 32,
    "name": "Madeleine",
    "subtitle": "La Nostalgie Pâtissière en Shooter",
    "description": "Une illusion aromatique saisissante : l'amaretto combiné au triple sec et à l'ananas reproduit fidèlement la saveur de la célèbre madeleine sortie du four.",
    "category": "Shooters",
    "flavorProfile": "Doux/Amande",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Cointreau",
        "details": "Liqueur d'orange",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Amaretto",
        "details": "Liqueur d'amande douce",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser le Cointreau, l'Amaretto et le trait de jus d'ananas dans le shaker avec de la glace.",
      "Shaker vivement pendant 6 secondes pour émulsionner les saveurs.",
      "Filtrer dans un verre à shooter."
    ],
    "garnish": "Voile d'orange.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/rwsyyu1483388181.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 33,
    "name": "Cervelle de Singe",
    "subtitle": "L'Effet Coagulé Visuel & Festif",
    "description": "Le plus célèbre shooter d'Halloween : une suspension crémeuse saisissante causée par la réaction du Baileys dans la liqueur de pêche traversée de grenadine.",
    "category": "Shooters",
    "flavorProfile": "Crémeux/Sanglant",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Liqueur de pêche",
        "details": "Pêche transparente",
        "amountCl": 2.5,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Baileys",
        "details": "Crème de whisky",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Gouttes rouge vif",
        "amountCl": 0.5,
        "measureDescription": "Quelques gouttes",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser la liqueur de pêche au fond du verre à shooter.",
      "À l'aide d'une paille ou d'une pipette, faire couler délicatement le Baileys au centre : il coagule en formant des circonvolutions.",
      "Déposer quelques gouttes de grenadine qui traversent la crème pour un effet spectaculaire."
    ],
    "garnish": "Effet visuel spectaculaire.",
    "glassware": "Verre Shooter transparent",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/rz5aun1504389701.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 34,
    "name": "Old Fashioned",
    "subtitle": "Le Doyen Aristocrate du Bar",
    "description": "La quintessence de l'art du cocktail : un morceau de sucre saturé de bitters, fondu dans la chaleur noble et vanillée d'un grand bourbon américain.",
    "category": "Classiques",
    "flavorProfile": "Corsé/Boisé",
    "prepTimeMinutes": 5,
    "difficulty": "Expert",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Bourbon",
        "details": "Bourbon de réserve pur grain",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Sucre en morceau",
        "details": "Sucre roux de canne",
        "amountCl": 1.0,
        "measureDescription": "1 morceau",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Angostura Bitters",
        "details": "Bitters aromatiques",
        "amountCl": 0.5,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Eau gazeuse",
        "details": "Trait de dilution",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer le morceau de sucre au fond du verre Old Fashioned.",
      "Imbiber avec les 2 traits d'Angostura et le trait d'eau gazeuse, puis écraser au pilon jusqu'à dissolution complète.",
      "Ajouter un gros glaçon translucide et verser la moitié du bourbon. Remuer longuement à la cuillère de bar.",
      "Ajouter le reste du bourbon et de la glace, puis mélanger encore 30 secondes pour une texture enveloppante."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Verre Tumbler lourd en cristal",
    "shakeSeconds": 0,
    "rating": 5.0,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg",
    "isFavorite": true,
    "vibeTag": "Dim-lit Speakeasy & Vinyl Sessions"
  },
  {
    "id": 35,
    "name": "Dark 'n' Stormy",
    "subtitle": "La Tempête Tropicale des Bermudes",
    "description": "L'emblème des marins des Bermudes : le nuage sombre d'un rhum ambré épicé flottant sur les flots houleux d'une ginger beer pétillante.",
    "category": "Cocktails",
    "flavorProfile": "Épicé/Corsé",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Rhum ambré ou épicé",
        "details": "Black Seal Rum des Bermudes",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Ginger Beer",
        "details": "Gingembre fermenté ardent",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre highball de glaçons.",
      "Verser le jus de citron vert puis allonger de ginger beer pétillante.",
      "Faire couler délicatement le rhum épicé sur le dessus à l'aide d'une cuillère pour créer le dégradé de tempête sombre."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/t1tn0s1504374905.jpg",
    "isFavorite": false,
    "vibeTag": "Maritime Storm & Acoustic Rock"
  },
  {
    "id": 36,
    "name": "Paloma",
    "subtitle": "La Reine Pétillante de Jalisco",
    "description": "Le cocktail le plus dégusté au Mexique : l'alliance désaltérante de tequila blanco, d'agrumes vifs et de soda au pamplemousse rose.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Tequila Blanco",
        "details": "100% agave pur",
        "amountCl": 5.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Soda au pamplemousse",
        "details": "Pamplemousse rose pétillant",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Frotter le bord du verre de jus de citron vert et tremper facultativement dans du sel.",
      "Remplir le verre de gros glaçons.",
      "Verser la tequila blanco et le trait de jus de citron vert.",
      "Allonger avec le soda au pamplemousse rose direct au verre et remuer doucement."
    ],
    "garnish": "Tranche de pamplemousse.",
    "glassware": "Verre Highball ou Collins",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/samm5j1513706393.jpg",
    "isFavorite": false,
    "vibeTag": "Summer Sunset & Cumbia House"
  },
  {
    "id": 37,
    "name": "Spritz Saint-Germain",
    "subtitle": "L'Élixir Floral Parisien",
    "description": "L'alternative florale et raffinée au spritz traditionnel : les mille fleurs de sureau sauvage cueillies à la main illuminées par le Prosecco brut.",
    "category": "Cocktails",
    "flavorProfile": "Floral/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 11.0,
    "ingredients": [
      {
        "name": "Liqueur Saint-Germain",
        "details": "Liqueur artisanale de sureau",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Prosecco",
        "details": "Prosecco brut DOC",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un grand verre ballon de glaçons.",
      "Verser la liqueur Saint-Germain puis le Prosecco bien frais.",
      "Compléter avec l'eau gazeuse et remuer délicatement à la cuillère de bar."
    ],
    "garnish": "Tête de menthe.",
    "glassware": "Grand verre à pied",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/125w0o1630407389.jpg",
    "isFavorite": false,
    "vibeTag": "Parisian Rooftop & French Touch"
  },
  {
    "id": 38,
    "name": "Dry Martini",
    "subtitle": "Le Roi Absolu de l'Élégance Sec",
    "description": "Le cocktail le plus sophistiqué de l'histoire : un gin ultra-froid simplement caressé par les herbes aromatiques d'un vermouth sec de précision.",
    "category": "Cocktails",
    "flavorProfile": "Sec/Corsé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin premium",
        "amountCl": 6.0,
        "measureDescription": "1 grand shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vermouth sec",
        "details": "Noilly Prat ou Dolin",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Remplir un verre à mélange de gros glaçons purs.",
      "Verser le gin et le vermouth sec.",
      "Mélanger à la cuillère de bar avec fluidité pendant 30 secondes pour atteindre une clarté et un glaçage parfaits sans troubler le liquide.",
      "Filtrer dans une coupe à martini glacée."
    ],
    "garnish": "Olive verte ou zeste de citron.",
    "glassware": "Coupe à Martini classique",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://images.unsplash.com/photo-1510626176961-4b57d4fbad03?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "James Bond Suite & Cool Jazz"
  },
  {
    "id": 39,
    "name": "Manhattan",
    "subtitle": "L'Héritage New-Yorkais Intemporel",
    "description": "Créé au Manhattan Club en 1870 : le caractère épicé du Rye Whiskey sublimé par la richesse sucrée du vermouth rouge et les bitters.",
    "category": "Cocktails",
    "flavorProfile": "Corsé/Herbacé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Rye Whiskey",
        "details": "Whiskey de seigle épicé",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vermouth Rouge",
        "details": "Vermouth doux aromatisé",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Angostura Bitters",
        "details": "Bitters aromatiques",
        "amountCl": 0.5,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser le Rye Whiskey, le vermouth rouge et les 2 traits d'Angostura dans un verre à mélange avec beaucoup de glace.",
      "Remuer à la cuillère de bar pendant 25 secondes.",
      "Filtrer dans une coupe à cocktail rafraîchie."
    ],
    "garnish": "Cerise amarena.",
    "glassware": "Coupe Cocktail vintage",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/yk70e31606771240.jpg",
    "isFavorite": false,
    "vibeTag": "New York Speakeasy & Classic Soul"
  },
  {
    "id": 40,
    "name": "Bramble",
    "subtitle": "Le Nectar de Mûres Britannique",
    "description": "Créé par Dick Bradsell à Londres : un sour éclatant de gin et citron baigné d'un voile pourpre de crème de mûre sauvage.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Botanique",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Dry Gin classique",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Crème de mûre",
        "details": "Liqueur de mûres sauvages",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café en filet",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Shaker le gin, le jus de citron et le sirop de sucre avec de la glace.",
      "Verser dans un verre Old Fashioned rempli de glace pilée.",
      "Verser la crème de mûre en filet à la fin sur le dessus pour laisser s'infiltrer le dégradé rubis."
    ],
    "garnish": "Mûres fraîches.",
    "glassware": "Verre Old Fashioned",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1514362545857-3bc16c4c7d1b?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Soho Nights & Brit Pop"
  },
  {
    "id": 41,
    "name": "Boulevardier",
    "subtitle": "Le Cousin Parisien du Negroni",
    "description": "Inventé au Harry's New York Bar à Paris dans les années 1920 : la chaleur ronde du bourbon remplace le gin face au Campari et vermouth doux.",
    "category": "Cocktails",
    "flavorProfile": "Amer/Boisé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Bourbon",
        "details": "Bourbon de caractère",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Campari",
        "details": "Bitter aromatique rouge",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Vermouth Rouge",
        "details": "Vermouth italien doux",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser le bourbon, le Campari et le vermouth rouge dans un verre à mélange avec des glaçons.",
      "Remuer à la cuillère de bar pendant 30 secondes pour une dilution maîtrisée.",
      "Filtrer dans un verre Old Fashioned sur un gros glaçon taillé."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Verre Old Fashioned",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/typuyq1439456976.jpg",
    "isFavorite": false,
    "vibeTag": "Art-Déco Speakeasy & Hot Club Jazz"
  },
  {
    "id": 42,
    "name": "Virgin Colada",
    "subtitle": "L'Onctuosité Coco Sans Alcool",
    "description": "0% alcool : tout le plaisir velouté des îles grâce à une mousse riche de crème de coco et de pur jus d'ananas mûri au soleil.",
    "category": "Mocktails",
    "flavorProfile": "Doux/Crémeux",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas doré",
        "amountCl": 12.0,
        "measureDescription": "1/2 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Crème de coco",
        "details": "Crème de coco riche",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Mettre le jus d'ananas, la crème de coco et le sirop de sucre dans le shaker ou le blender avec de la glace pilée.",
      "Mixer ou shaker vigoureusement pendant 15 secondes pour obtenir une mousse dense.",
      "Servir dans un grand verre tropical sur lit de glace."
    ],
    "garnish": "Triangle d'ananas.",
    "glassware": "Verre Hurricane Tropical",
    "shakeSeconds": 15,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1527661591475-527312dd65f5?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Chill Acoustic & Beach Breeze"
  },
  {
    "id": 43,
    "name": "Safe Sex on the Beach",
    "subtitle": "L'Océan Fruité Détox 0%",
    "description": "0% alcool : un délice vibrant combinant le jus d'orange pressé, le velouté de pêche et le nappage rubis acidulé de canneberge.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Sucré",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus d'orange",
        "amountCl": 6.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge pure",
        "amountCl": 6.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de pêche",
        "details": "Sirop doux",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser le jus d'orange et le sirop de pêche dans le shaker avec des glaçons.",
      "Shaker et verser dans un verre haut avec de la glace.",
      "Napper délicatement avec le jus de cranberry pour un dégradé coucher de soleil sans alcool."
    ],
    "garnish": "Tranche d'orange.",
    "glassware": "Verre Highball",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vuquyv1468876052.jpg",
    "isFavorite": false,
    "vibeTag": "Sunset Chill & Tropical Vibes"
  },
  {
    "id": 44,
    "name": "Iced Tea Pêche Maison",
    "subtitle": "L'Infusion Glacée Revigorante",
    "description": "0% alcool : la quintessence du thé glacé maison alliant l'astringence élégante d'un thé noir froid, la douceur de la pêche et le peps du citron jaune.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Thé",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Thé noir froid",
        "details": "Infusion de thé noir pure",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de pêche",
        "details": "Sirop de pêche mûre",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Infuser et refroidir un thé noir parfumé.",
      "Verser le thé, le sirop de pêche et le jus de citron direct au verre sur une abondance de glaçons.",
      "Remuer pour harmoniser la fraîcheur et déguster bien glacé."
    ],
    "garnish": "Tranche de pêche et citron.",
    "glassware": "Verre Tumbler haut",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/qxvypq1468924331.jpg",
    "isFavorite": false,
    "vibeTag": "Summer Terrace & Acoustic Beats"
  },
  {
    "id": 45,
    "name": "Bloody Mary",
    "subtitle": "Le Grand Classique Épicé & Salé",
    "description": "Le plus célèbre des réveils salés : vodka glacée, coulis de tomate épais, traits de Worcestershire et piquant du Tabasco avec sel de céleri.",
    "category": "Cocktails",
    "flavorProfile": "Salé/Épicé",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche",
        "amountCl": 5.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de tomate",
        "details": "Pur jus épais",
        "amountCl": 12.0,
        "measureDescription": "1 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sauce Worcestershire",
        "details": "Sauce anglaise relevée",
        "amountCl": 0.5,
        "measureDescription": "3 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Tabasco",
        "details": "Piment rouge vif",
        "amountCl": 0.2,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Sel de céleri & Poivre",
        "details": "Assaisonnement moulu",
        "amountCl": 0.0,
        "measureDescription": "1 pincée",
        "isGarnish": false,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Verser la vodka, le jus de tomate, le jus de citron, la Worcestershire et le Tabasco dans un verre à mélange avec des glaçons.",
      "Ajouter le sel de céleri et un tour de moulin à poivre noir.",
      "Rouler délicatement d'un shaker à l'autre (ou shaker légèrement) sans trop casser la pulpe de tomate.",
      "Verser avec la glace dans un verre tumbler haut."
    ],
    "garnish": "Branche de céleri.",
    "glassware": "Verre Tumbler Highball",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/t6caa21582485702.jpg",
    "isFavorite": false,
    "vibeTag": "Sunday Brunch & Jazz Chilled"
  },
  {
    "id": 46,
    "name": "Tommy's Margarita",
    "subtitle": "La Pureté Moderne de San Francisco",
    "description": "Créé par Julio Bermejo au Tommy's : la liqueur d'orange est remplacée par le nectar d'agave biologique pour sublimer la tequila 100% agave.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Sucré",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Tequila Reposado",
        "details": "100% agave vieilli en fût",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.5,
        "measureDescription": "1 citron vert entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop d'agave",
        "details": "Nectar d'agave bleu bio",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser la tequila reposado, le jus de citron vert frais et le sirop d'agave dans le shaker avec de gros glaçons.",
      "Shaker vivement pendant 10 secondes pour une dilution pure et texturée.",
      "Filtrer dans un verre Old Fashioned sur un gros cube de glace pure."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre Old Fashioned",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/srpxxp1441209622.jpg",
    "isFavorite": true,
    "vibeTag": "San Francisco Speakeasy & Modern Grooves"
  },
  {
    "id": 47,
    "name": "Pisco Sour",
    "subtitle": "Le Joyau Mythique des Andes",
    "description": "Le trésor national péruvien et chilien : l'eau-de-vie de raisin Pisco sublimée par une mousse d'oeuf aérienne et quelques gouttes aromatiques d'Angostura.",
    "category": "Cocktails",
    "flavorProfile": "Doux/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Pisco",
        "details": "Eau-de-vie de raisin Quebranta",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne 2:1",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Blanc d'oeuf",
        "details": "Pour la collerette mousseuse",
        "amountCl": 1.0,
        "measureDescription": "1 blanc frais",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Angostura Bitters",
        "details": "Gouttes aromatiques de surface",
        "amountCl": 0.2,
        "measureDescription": "3 gouttes",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Placer le Pisco, le citron vert, le sirop et le blanc d'oeuf dans le shaker.",
      "Dry shaker sans glace pendant 10 secondes pour monter une émulsion dense.",
      "Ajouter des glaçons compacts et shaker vigoureusement pendant 12 secondes.",
      "Double-filtrer dans une coupe et déposer 3 gouttes d'Angostura sur la mousse."
    ],
    "garnish": "Gouttes d'Angostura.",
    "glassware": "Coupe à cocktail ou Verre Amara",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/tsssur1439907622.jpg",
    "isFavorite": false,
    "vibeTag": "Andean Sunset & Latin Folk House"
  },
  {
    "id": 48,
    "name": "French 75",
    "subtitle": "Le Canon Pétillant des Années 20",
    "description": "Baptisé d'après le canon français de 75mm : la puissance vivifiante du gin et du citron propulsée par le raffinement d'un champagne brut.",
    "category": "Cocktails",
    "flavorProfile": "Pétillant/Acide",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 14.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne liquide",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Champagne",
        "details": "Brut bien frais",
        "amountCl": 8.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser le gin, le jus de citron et le sirop de sucre dans le shaker avec de la glace.",
      "Shaker vigoureusement pendant 8 secondes.",
      "Filtrer dans une flûte à champagne rafraîchie.",
      "Allonger doucement au champagne frais pour créer une mousse dorée fine."
    ],
    "garnish": "Zeste de citron.",
    "glassware": "Flûte à Champagne",
    "shakeSeconds": 8,
    "rating": 4.9,
    "imageUrl": "https://images.unsplash.com/photo-1597075687490-8f673c6c17f6?auto=format&fit=crop&w=800&q=80",
    "isFavorite": true,
    "vibeTag": "Années Folles & Electro Swing"
  },
  {
    "id": 49,
    "name": "Bellini",
    "subtitle": "L'Aura Vénitienne du Harry's Bar",
    "description": "Inventé à Venise par Giuseppe Cipriani en 1948 : la douceur veloutée de la purée de pêche blanche et l'effervescence délicate du Prosecco.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Purée de pêche",
        "details": "Pêche blanche fraîche",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Prosecco",
        "details": "Prosecco DOC brut",
        "amountCl": 8.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Déposer la purée de pêche fraîche bien froide au fond d'une flûte.",
      "Verser doucement une première moitié de Prosecco et mélanger délicatement à la cuillère.",
      "Compléter avec le reste du Prosecco pour conserver le pétillement sans déborder."
    ],
    "garnish": "Tranche de pêche.",
    "glassware": "Flûte à Champagne vénitienne",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/eaag491504367543.jpg",
    "isFavorite": false,
    "vibeTag": "Venice Canal & Classical Ambient"
  },
  {
    "id": 50,
    "name": "Mimosa",
    "subtitle": "Le Rayon de Soleil du Brunch",
    "description": "Le cocktail du dimanche matin par excellence : moitié pur jus d'orange gorgé de soleil, moitié champagne effervescent.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus d'orange pressée minute",
        "amountCl": 6.0,
        "measureDescription": "1/2 flûte",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Champagne ou Prosecco",
        "details": "Brut bien frappé",
        "amountCl": 6.0,
        "measureDescription": "1/2 flûte",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser le jus d'orange frais pressé et tamisé dans une flûte à champagne.",
      "Allonger délicatement de champagne ou Prosecco très frais en penchant la flûte.",
      "Remuer d'un tour de cuillère léger pour harmoniser sans dissiper les bulles."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Flûte à Champagne",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/juhcuu1504370685.jpg",
    "isFavorite": false,
    "vibeTag": "Morning Acoustic & Sunny Rooftop"
  },
  {
    "id": 51,
    "name": "Gimlet",
    "subtitle": "L'Incisif Historique de la Royal Navy",
    "description": "L'arme anti-scorbut devenue légende : le mariage strict et tranchant entre la puissance du gin et l'acidité confite d'un cordial de citron vert.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Herbacé",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin classique",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cordial au citron vert",
        "details": "Lime cordial type Rose's",
        "amountCl": 2.5,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre à mélange de glace compacte.",
      "Verser le gin et le cordial de citron vert.",
      "Mélanger à la cuillère de bar pendant 25 secondes jusqu'à glaçage.",
      "Filtrer dans une coupe à cocktail rafraîchie."
    ],
    "garnish": "Zeste de citron vert.",
    "glassware": "Coupe à Cocktail vintage",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/3xgldt1513707271.jpg",
    "isFavorite": false,
    "vibeTag": "Noir Cinema & Cool Jazz"
  },
  {
    "id": 52,
    "name": "Black Russian",
    "subtitle": "L'Origine Ténébreuse du White Russian",
    "description": "Créé à l'Hôtel Métropole de Bruxelles en 1949 : la rencontre pure et ténébreuse entre la clarté tranchante de la vodka et la richesse torréfiée du café.",
    "category": "Cocktails",
    "flavorProfile": "Corsé/Café",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka de grain pure",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de café",
        "details": "Kahlúa mexicaine",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Remplir un verre Old Fashioned de gros cubes de glace purs.",
      "Verser la vodka directement sur les glaçons.",
      "Ajouter la liqueur de café et mélanger délicatement pendant 15 secondes."
    ],
    "garnish": "Aucune.",
    "glassware": "Verre Old Fashioned Lowball",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/8oxlqf1606772765.jpg",
    "isFavorite": false,
    "vibeTag": "Deep House & Dark Lounge"
  },
  {
    "id": 53,
    "name": "Sidecar",
    "subtitle": "La Noblesse Boisée du Cognac",
    "description": "Le plus aristocratique des sours : la chaleur boisée d'un cognac français rehaussée par la fraîcheur d'agrumes et un col de sucre caramélisé.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Boisé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Cognac",
        "details": "Cognac VSOP français",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'oranges amères",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sucre fin",
        "details": "Pour la bordure du verre",
        "amountCl": 0.0,
        "measureDescription": "Bordure",
        "isGarnish": true,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Frotter le bord d'une coupe avec un quartier de citron et tremper dans du sucre fin pour créer une fine bordure.",
      "Verser le cognac, le Cointreau et le jus de citron dans le shaker rempli de glace.",
      "Shaker vigoureusement pendant 10 secondes et double-filtrer dans la coupe préparée."
    ],
    "garnish": "Bordure de sucre.",
    "glassware": "Coupe à Cocktail Art-Déco",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/x72sik1606854964.jpg",
    "isFavorite": true,
    "vibeTag": "Parisian Speakeasy & Gypsy Jazz"
  },
  {
    "id": 54,
    "name": "Mint Julep",
    "subtitle": "L'Oasis Givrée du Kentucky Derby",
    "description": "Le rituel du Sud des États-Unis : une timbale en étain recouverte de givre, remplie de glace pilée, de menthe aromatique et d'un bourbon puissant.",
    "category": "Cocktails",
    "flavorProfile": "Frais/Corsé",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Bourbon",
        "details": "Kentucky Straight Bourbon",
        "amountCl": 6.0,
        "measureDescription": "1.5 shooters",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne liquide",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Feuilles de menthe",
        "details": "Menthe fraîche",
        "amountCl": 0.0,
        "measureDescription": "8 à 10 feuilles",
        "isGarnish": false,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Placer les feuilles de menthe et le sirop de sucre dans la timbale en étain.",
      "Piler très doucement pour réveiller les essences végétales sans broyer la tige.",
      "Remplir à mi-hauteur de glace pilée, ajouter la moitié du bourbon et mélanger.",
      "Combler d'une montagne de glace pilée, verser le reste de bourbon et laisser givrer l'extérieur de la timbale."
    ],
    "garnish": "Gros bouquet de menthe.",
    "glassware": "Timbale Julep en étain",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/squyyq1439907312.jpg",
    "isFavorite": false,
    "vibeTag": "Southern Blues & Folk Sessions"
  },
  {
    "id": 55,
    "name": "Irish Coffee",
    "subtitle": "Le Réconfort Légendaire de Shannon",
    "description": "Inventé pour réchauffer les passagers des hydravions en Irlande : whiskey chaud et café serré sous une généreuse couche de crème fouettée froide.",
    "category": "Cocktails",
    "flavorProfile": "Chaud/Café",
    "prepTimeMinutes": 5,
    "difficulty": "Expert",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Whiskey Irlandais",
        "details": "Whiskey doux triplement distillé",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Café chaud",
        "details": "Café filtre ou expresso allongé",
        "amountCl": 9.0,
        "measureDescription": "1/2 mug",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sucre de canne",
        "details": "Sucre roux cassonade",
        "amountCl": 1.0,
        "measureDescription": "1 cuillère",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Crème liquide fouettée",
        "details": "Légèrement montée au fouet",
        "amountCl": 3.0,
        "measureDescription": "Nappage",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Préchauffer le verre spécial avec de l'eau bouillante, puis vider.",
      "Verser le whiskey irlandais et le sucre roux, mélanger jusqu'à dissolution complète.",
      "Verser le café très chaud et remuer une dernière fois.",
      "Faire couler délicatement la crème fraîchement fouettée sur le dos d'une cuillère pour la faire flotter au sommet sans qu'elle ne se mélange."
    ],
    "garnish": "Poudre de cacao.",
    "glassware": "Verre à Irish Coffee avec anse",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://images.unsplash.com/photo-1551024709-8f23befc6f87?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Winter Fireside & Celtic Folk"
  },
  {
    "id": 56,
    "name": "Zombie",
    "subtitle": "Le Monstre Tiki Inoxydable",
    "description": "Créé par Donn Beach en 1934 : une trilogie de rhums caribéens dont un overproof volcanique mariée aux nectars tropicaux et grenadine.",
    "category": "Cocktails",
    "flavorProfile": "Puissant/Exotique",
    "prepTimeMinutes": 4,
    "difficulty": "Expert",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum caribéen",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Rhum ambré",
        "details": "Rhum vieux boisé",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Rhum overproof",
        "details": "Rhum 69° ou plus",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine concentrée",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer tous les ingrédients avec de la glace pilée dans le shaker.",
      "Shaker avec intensité pendant 12 secondes pour frapper ce mélange puissant.",
      "Verser l'intégralité du shaker dans un grand verre Tiki.",
      "Déposer la demi-passion sur la glace pilée."
    ],
    "garnish": "Demi fruit de la passion.",
    "glassware": "Verre Tiki sculpté",
    "shakeSeconds": 12,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/2en3jk1509557725.jpg",
    "isFavorite": false,
    "vibeTag": "Tiki Party & Jungle Drums"
  },
  {
    "id": 57,
    "name": "Tom Collins",
    "subtitle": "La Limonade Botanique Pétillante",
    "description": "L'ancêtre mythique des collins : le peps désaltérant d'une vraie citronnade maison associée à la finesse d'un gin floral allongé d'eau gazeuse.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Pétillant",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 11.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Old Tom ou Dry Gin",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.5,
        "measureDescription": "1/2 citron entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Shaker le gin, le jus de citron et le sirop de sucre avec des glaçons pendant 8 secondes.",
      "Filtrer dans un grand verre highball ou collins rempli de glaçons neufs.",
      "Allonger à l'eau gazeuse fraîche et remuer délicatement."
    ],
    "garnish": "Cerise et tranche de citron.",
    "glassware": "Verre Collins haut",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7cll921606854636.jpg",
    "isFavorite": false,
    "vibeTag": "Summer Garden & Sunny Soul"
  },
  {
    "id": 58,
    "name": "White Lady",
    "subtitle": "La Dame Blanche Épurée des Palaces",
    "description": "Un chef-d'œuvre de pureté créé à Londres dans les années 1920 : l'alliance soyeuse du gin, du triple sec et du citron blanc surmontée d'une collerette mousseuse.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Sec",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 22.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Dry Gin raffiné",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'oranges",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Blanc d'oeuf",
        "details": "Pour la texture velours",
        "amountCl": 1.0,
        "measureDescription": "1 blanc frais",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer le gin, le Cointreau, le citron et le blanc d'oeuf dans le shaker.",
      "Effectuer un dry shake (sans glaçons) pour bien monter l'émulsion crémeuse.",
      "Ajouter la glace et shaker énergiquement pendant 10 secondes.",
      "Double-filtrer dans une coupe cocktail glacée."
    ],
    "garnish": "Zeste de citron.",
    "glassware": "Coupe à Cocktail rafraîchie",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/jofsaz1504352991.jpg",
    "isFavorite": false,
    "vibeTag": "Palace Lounge & Chic Nu-Jazz"
  },
  {
    "id": 59,
    "name": "Aviation",
    "subtitle": "Le Bleu Céleste des Pionniers",
    "description": "Une légende des débuts de l'aviation : une teinte bleu pastel céleste incomparable apportée par la crème de violette et la liqueur de marasquin.",
    "category": "Cocktails",
    "flavorProfile": "Floral/Acide",
    "prepTimeMinutes": 3,
    "difficulty": "Expert",
    "alcoholPercentage": 22.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Gin sec aromatique",
        "amountCl": 4.5,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Liqueur de Marasquin",
        "details": "Luxardo Maraschino",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Crème de violette",
        "details": "Pour la nuance céleste",
        "amountCl": 0.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser le gin, le jus de citron, la liqueur de marasquin et la crème de violette dans le shaker rempli de glace.",
      "Shaker vigoureusement pendant 12 secondes jusqu'à glaçage extérieur.",
      "Double-filtrer dans une coupe à cocktail pour admirer la robe bleu violacé céleste."
    ],
    "garnish": "Cerise au marasquin.",
    "glassware": "Coupe à Cocktail vintage",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/trbplb1606855233.jpg",
    "isFavorite": false,
    "vibeTag": "Early Flight Lounge & Dream Ambient"
  },
  {
    "id": 60,
    "name": "Apple Martini",
    "subtitle": "L'Appletini Vert Fluorescent",
    "description": "Le cocktail culte de Los Angeles des années 90 : la fraîcheur acidulée d'une liqueur de pomme verte Granny Smith vivifiée par la vodka pure.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de pomme verte",
        "details": "Manzana ou Sour Apple",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, la liqueur de pomme verte et le jus de citron dans un shaker rempli de glace.",
      "Shaker vivement pendant 10 secondes.",
      "Filtrer dans un verre à martini pour admirer sa teinte vert fluo éclatante."
    ],
    "garnish": "Tranche de pomme.",
    "glassware": "Verre à Martini",
    "shakeSeconds": 10,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/sbffau1504389764.jpg",
    "isFavorite": false,
    "vibeTag": "Retro 90s Clubbing & Pop Beats"
  },
  {
    "id": 61,
    "name": "Midori Illusion",
    "subtitle": "L'Éclat Émeraude Électrique",
    "description": "L'illusion d'une nuit électrique : liqueur japonaise de melon vert Midori fusionnée à l'ananas tropical et aux agrumes.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Électrique",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Liqueur de melon Midori",
        "details": "Melon vert japonais",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 1.5,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'orange",
        "amountCl": 1.5,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser le Midori, la vodka, le Cointreau, le citron et le jus d'ananas dans un shaker avec des glaçons.",
      "Shaker énergiquement pendant 8 secondes.",
      "Verser avec des glaçons dans un verre highball."
    ],
    "garnish": "Triangle d'ananas.",
    "glassware": "Verre Highball ou Ouragan",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/bmxmyq1630407098.jpg",
    "isFavorite": false,
    "vibeTag": "Tokyo Nightlife & Electro Wave"
  },
  {
    "id": 62,
    "name": "Slippery Nipple",
    "subtitle": "Le Duo Crémeux & Anisé",
    "description": "Un classique des shooters de pub : la fraîcheur anisée et puissante de la Sambuca italienne surmontée d'un nappage onctueux de Baileys.",
    "category": "Shooters",
    "flavorProfile": "Crémeux/Anisé",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Sambuca",
        "details": "Liqueur d'anis blanc",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Baileys",
        "details": "Crème de whisky",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Goutte centrale",
        "amountCl": 0.3,
        "measureDescription": "1 goutte",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser la Sambuca directement au fond du verre à shooter.",
      "À l'aide du dos d'une cuillère de bar posée contre la paroi, verser très délicatement le Baileys pour créer deux strates parfaitement nettes.",
      "Déposer une goutte de grenadine qui coule doucement au centre.",
      "Boire d'un trait cul-sec."
    ],
    "garnish": "Strates contrastées.",
    "glassware": "Verre Shooter transparent",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/l9tgru1551439725.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 63,
    "name": "Blowjob",
    "subtitle": "Le Shooter Gourmand à la Chantilly",
    "description": "Le plus festif et coquin des shooters : café torréfié et crème irlandaise couronnés d'une généreuse pointe de crème chantilly.",
    "category": "Shooters",
    "flavorProfile": "Crémeux/Café",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Liqueur de café",
        "details": "Kahlúa",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Baileys",
        "details": "Crème de whisky",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Crème chantilly",
        "details": "En topping généreux",
        "amountCl": 0.0,
        "measureDescription": "Topping",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser la liqueur de café au fond du shooter.",
      "Superposer délicatement le Baileys avec le dos d'une cuillère.",
      "Recouvrir d'un dôme généreux de crème chantilly.",
      "Règle du jeu : boire sans utiliser les mains !"
    ],
    "garnish": "Chantilly gourmande.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/spuurv1468878783.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 64,
    "name": "Baby Guinness",
    "subtitle": "La Pinte Irlandaise Miniature",
    "description": "Une illusion visuelle d'une pinte de stout Guinness en modèle miniature : liqueur de café sombre et col blanc parfait de crème irlandaise.",
    "category": "Shooters",
    "flavorProfile": "Crémeux/Café",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Liqueur de café",
        "details": "Kahlúa ou Tia Maria",
        "amountCl": 2.5,
        "measureDescription": "3/4 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Baileys",
        "details": "Irish Cream",
        "amountCl": 1.5,
        "measureDescription": "Col blanc",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir le verre à shooter aux 3/4 avec la liqueur de café.",
      "Faire couler délicatement le Baileys sur le dos d'une cuillère de bar pour créer la collerette blanche imitant la mousse d'une Guinness.",
      "Déguster d'un trait."
    ],
    "garnish": "Col blanc miniature.",
    "glassware": "Verre Shooter épais",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/swqurw1454512730.jpg",
    "isFavorite": true,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 65,
    "name": "Blue Hawaiian",
    "subtitle": "Le Paradis Bleu de Waikiki",
    "description": "Créé par Harry Yee à Honolulu en 1957 : la couleur lagon azur du curaçao bleu alliée à l'ananas tropical, au rhum et à la crème de coco.",
    "category": "Cocktails",
    "flavorProfile": "Exotique",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum caribéen",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Curaçao Bleu",
        "details": "Liqueur bleue d'orange",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Crème de coco",
        "details": "Crème de coco riche",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser le rhum blanc, le curaçao bleu, la crème de coco et le jus d'ananas dans le shaker rempli de glace pilée.",
      "Shaker vivement pendant 10 secondes.",
      "Verser sans filtrer dans un grand verre tropical sur lit de glace."
    ],
    "garnish": "Ananas et cerise.",
    "glassware": "Verre Poco Grande ou Ouragan",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/ujoh9x1504882987.jpg",
    "isFavorite": false,
    "vibeTag": "Honolulu Surf & Ukulele Beats"
  },
  {
    "id": 66,
    "name": "Lynchburg Lemonade",
    "subtitle": "La Citronnade Mythique du Tennessee",
    "description": "L'esprit du Tennessee : la rondeur vanillée du Jack Daniel's adoucie par le triple sec et désaltérée d'une limonade pétillante glacée.",
    "category": "Cocktails",
    "flavorProfile": "Frais/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Jack Daniel's",
        "details": "Tennessee Whiskey",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Triple sec",
        "details": "Liqueur d'orange",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Limonade",
        "details": "Limonade fraîche pétillante",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Shaker le Jack Daniel's, le triple sec et le jus de citron avec des glaçons.",
      "Verser dans un bocal Mason jar ou un grand verre rempli de glace.",
      "Allonger de limonade fraîche et remuer doucement."
    ],
    "garnish": "Tranche de citron.",
    "glassware": "Bocal Mason Jar ou Highball",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7rnm8u1504888527.jpg",
    "isFavorite": false,
    "vibeTag": "Southern Rock & Tennessee Nights"
  },
  {
    "id": 67,
    "name": "Virgin Mary",
    "subtitle": "Le Tonic Salé et Revigorant 0%",
    "description": "0% alcool : toute l'énergie épicée du Bloody Mary sans alcool avec du jus de tomate épais, Worcestershire, Tabasco et sel de céleri.",
    "category": "Mocktails",
    "flavorProfile": "Salé/Épicé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de tomate",
        "details": "Pur jus de tomate",
        "amountCl": 15.0,
        "measureDescription": "1 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sauce Worcestershire",
        "details": "Sauce relevée",
        "amountCl": 0.5,
        "measureDescription": "3 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Tabasco",
        "details": "Piment rouge",
        "amountCl": 0.2,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Sel de céleri",
        "details": "Sel aromatique",
        "amountCl": 0.0,
        "measureDescription": "1 pincée",
        "isGarnish": false,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Verser le jus de tomate, le citron, la Worcestershire et le Tabasco dans le shaker avec de la glace.",
      "Ajouter le sel de céleri et shaker brièvement.",
      "Verser dans un verre haut garni de glace."
    ],
    "garnish": "Branche de céleri.",
    "glassware": "Verre Tumbler Highball",
    "shakeSeconds": 6,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/yfhn371504374246.jpg",
    "isFavorite": false,
    "vibeTag": "Detox Morning & Ambient Soul"
  },
  {
    "id": 68,
    "name": "Cendrillon",
    "subtitle": "Le Trio d'Agrumes Féerique 0%",
    "description": "0% alcool : une alliance magique d'oranges fraîches, d'ananas parfumé et de citron vivifiant adoucis d'un filet de grenadine.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus doux",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Sirop de fruits rouges",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 6.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Shaker les jus d'orange, d'ananas, de citron et la grenadine avec des glaçons.",
      "Verser dans un grand verre avec de la glace.",
      "Allonger d'eau gazeuse et remuer délicatement."
    ],
    "garnish": "Tranche d'orange.",
    "glassware": "Verre Tumbler",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/uptxtv1468876415.jpg",
    "isFavorite": false,
    "vibeTag": "Fairytale Sunset & Light Beats"
  },
  {
    "id": 69,
    "name": "Chantaco",
    "subtitle": "La Douceur Basque Fruitée 0%",
    "description": "0% alcool : créé sur la côte basque, un mocktail raffiné mariant le pamplemousse acidulé, l'orange douce et le parfum de fraise.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Doux",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de pamplemousse",
        "details": "Pamplemousse rose",
        "amountCl": 4.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de fraise",
        "details": "Sirop de fraise mûre",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer les jus d'orange, de citron, de pamplemousse et le sirop de fraise dans le shaker avec des glaçons.",
      "Shaker vivement pendant 8 secondes.",
      "Filtrer dans un verre rempli de glaçons."
    ],
    "garnish": "Fraise fraîche.",
    "glassware": "Verre à Cocktail haut",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/mzgaqu1504389248.jpg",
    "isFavorite": false,
    "vibeTag": "Basque Coast & Gentle Acoustic"
  },
  {
    "id": 70,
    "name": "Florida",
    "subtitle": "Le Souffle Pétillant des Vergers 0%",
    "description": "0% alcool : une explosion d'agrumes floridiens pétillants où le pamplemousse et l'orange s'accordent avec une fraîcheur citronnée intense.",
    "category": "Mocktails",
    "flavorProfile": "Fruité",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus rose",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 4.0,
        "measureDescription": "1/4 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 6.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser les jus de pamplemousse, d'orange, de citron et le sirop de sucre dans le shaker avec de la glace.",
      "Shaker énergiquement pendant 8 secondes.",
      "Verser dans un grand verre avec des glaçons et allonger d'eau gazeuse."
    ],
    "garnish": "Zeste de pamplemousse.",
    "glassware": "Verre Highball",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/i3tfn31484430499.jpg",
    "isFavorite": false,
    "vibeTag": "Miami Beach & Chill House"
  },
  {
    "id": 71,
    "name": "Frozen Strawberry Margarita",
    "subtitle": "Le Granité Givré à la Fraise",
    "description": "Une texture granité addictive : tequila blanco, liqueur d'orange et jus de citron vert mixés à grande vitesse avec de vraies fraises et un lit de glace.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Glacé",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Tequila Blanco",
        "details": "100% agave",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'oranges",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Fraises fraîches",
        "details": "Fraises mûres équeutées",
        "amountCl": 10.0,
        "measureDescription": "100g",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer les fraises fraîches, la tequila, le Cointreau, le citron vert et une pleine coupe de glace dans le blender.",
      "Mixer à vitesse maximale pendant 20 secondes jusqu'à consistance onctueuse et glacée.",
      "Verser dans un grand verre à margarita."
    ],
    "garnish": "Fraise.",
    "glassware": "Verre à Margarita Sombrero",
    "shakeSeconds": 20,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/tqyrpw1439905311.jpg",
    "isFavorite": true,
    "vibeTag": "Summer Pool Party & Latin Pop"
  },
  {
    "id": 72,
    "name": "Frozen Daiquiri",
    "subtitle": "L'Avalanche Glacée Tropicale",
    "description": "La version givrée culte du Daiquiri immortalisée à La Floridita : le rhum blanc et le citron vert transformés en un sorbet cocktail rafraîchissant.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Glacé",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum cubain",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.5,
        "measureDescription": "1 citron entier",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser le rhum blanc, le citron vert et le sirop dans le blender.",
      "Ajouter une généreuse coupe de glace pilée.",
      "Mixer jusqu'à obtenir une émulsion glacée dense et crémeuse.",
      "Verser dans une coupe rafraîchie."
    ],
    "garnish": "Rondelle de citron.",
    "glassware": "Coupe à Cocktail large",
    "shakeSeconds": 15,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7oyrj91504884412.jpg",
    "isFavorite": false,
    "vibeTag": "Havana Tropicana & Afro Cuban Jazz"
  },
  {
    "id": 73,
    "name": "Singapore Sling",
    "subtitle": "Le Chef-d'Œuvre du Raffles Hotel",
    "description": "Créé en 1915 au Long Bar de Singapour : une partition complexe d'herbes aromatiques Bénédictine, cerise noire, gin et jus d'ananas mousseux.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Complexe",
    "prepTimeMinutes": 4,
    "difficulty": "Expert",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de cerise",
        "details": "Cherry Heering",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'orange",
        "amountCl": 0.75,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Bénédictine",
        "details": "Liqueur d'herbes séculaires",
        "amountCl": 0.75,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas",
        "amountCl": 12.0,
        "measureDescription": "1 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Grenadine",
        "details": "Sirop de grenadine",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Angostura",
        "details": "Bitters aromatiques",
        "amountCl": 0.2,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser tous les ingrédients dans le shaker avec une abondance de glaçons.",
      "Shaker vigoureusement pendant 12 secondes pour bien aérer le jus d'ananas.",
      "Filtrer dans un verre sling ou highball rempli de glace fraîche."
    ],
    "garnish": "Ananas et cerise.",
    "glassware": "Verre Sling ou Highball haut",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/8cl9sm1582581761.jpg",
    "isFavorite": false,
    "vibeTag": "Colonial Elegance & Asian Lounge"
  },
  {
    "id": 74,
    "name": "Vesper Martini",
    "subtitle": "L'Ordre Mythique de James Bond",
    "description": "Créé par Ian Fleming dans Casino Royale (1953) : 'Trois mesures de Gordon's, une de vodka, une demi de Kina Lillet. Shaker avec de la glace jusqu'à ce que ce soit glacé.'",
    "category": "Cocktails",
    "flavorProfile": "Sec/Puissant",
    "prepTimeMinutes": 3,
    "difficulty": "Expert",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin",
        "amountCl": 6.0,
        "measureDescription": "3 mesures",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vodka",
        "details": "Vodka de grain pure",
        "amountCl": 2.0,
        "measureDescription": "1 mesure",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Lillet Blanc",
        "details": "Apéritif bordelais aromatique",
        "amountCl": 1.0,
        "measureDescription": "1/2 mesure",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Verser le gin, la vodka et le Lillet Blanc dans le shaker avec beaucoup de glace.",
      "Shaker vigoureusement pendant 15 secondes pour obtenir de minuscules éclats de glace cristallins en surface.",
      "Filtrer dans une coupe à cocktail bien profonde."
    ],
    "garnish": "Zeste de citron.",
    "glassware": "Coupe à Cocktail profonde",
    "shakeSeconds": 15,
    "rating": 5.0,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/mtdxpa1504374514.jpg",
    "isFavorite": true,
    "vibeTag": "Casino Royale & Cinematic Brass"
  },
  {
    "id": 75,
    "name": "Rusty Nail",
    "subtitle": "Le Clou Rouillé du Rat Pack",
    "description": "Le cocktail culte de Frank Sinatra et Dean Martin : l'intensité tourbée et fumée d'un Scotch écossais adoucie par le miel de bruyère et les épices de la Drambuie.",
    "category": "Cocktails",
    "flavorProfile": "Corsé/Doux",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Scotch Whisky",
        "details": "Blended Scotch Whisky",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Drambuie",
        "details": "Liqueur de whisky, miel & herbes",
        "amountCl": 2.5,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Remplir un verre Old Fashioned de gros cubes de glace purs.",
      "Verser le Scotch Whisky et la Drambuie.",
      "Mélanger délicatement à la cuillère de bar pendant 20 secondes pour fondre les deux spiritueux."
    ],
    "garnish": "Zeste de citron.",
    "glassware": "Verre Rocks Old Fashioned",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/yqsvtw1478252982.jpg",
    "isFavorite": false,
    "vibeTag": "Rat Pack Vegas & Classic Swing"
  },
  {
    "id": 76,
    "name": "Godfather",
    "subtitle": "L'Alliance Sicilienne Culte",
    "description": "Inspiré par le chef-d'œuvre cinématographique de Coppola : la force brute d'un scotch écossais sublimée par l'amertume suave de l'Amaretto italien.",
    "category": "Cocktails",
    "flavorProfile": "Corsé/Amande",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Scotch Whisky",
        "details": "Scotch ou Bourbon",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Amaretto",
        "details": "Liqueur d'amande douce",
        "amountCl": 2.5,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Placer un gros glaçon taillé dans un verre Old Fashioned.",
      "Verser le Scotch Whisky puis l'Amaretto.",
      "Mélanger à la cuillère de bar pour rafraîchir et harmoniser."
    ],
    "garnish": "Aucune.",
    "glassware": "Verre Old Fashioned Lowball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/e5zgao1582582378.jpg",
    "isFavorite": false,
    "vibeTag": "Sicilian Shadows & Cinema Strings"
  },
  {
    "id": 77,
    "name": "Caipiroska",
    "subtitle": "La Caïpirinha Russe Tranchante",
    "description": "La version slave ultra-populaire de la Caïpirinha : la neutralité limpide de la vodka laisse s'exprimer pleinement les huiles et le jus de citron vert écrasé.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Sucré",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Citron vert",
        "details": "Coupé en morceaux",
        "amountCl": 3.0,
        "measureDescription": "1 entier coupé",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sucre roux",
        "details": "Cassonade pure",
        "amountCl": 2.0,
        "measureDescription": "2 cuillères",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Couper le citron vert en 8 morceaux et les déposer au fond du verre avec le sucre roux.",
      "Piler avec fermeté pour extraire tout le jus sans meurtrir la peau blanche.",
      "Remplir le verre de glace pilée, verser la vodka et remuer vigoureusement de bas en haut."
    ],
    "garnish": "Rondelle de citron.",
    "glassware": "Verre Rocks bas",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/jgvn7p1582484435.jpg",
    "isFavorite": false,
    "vibeTag": "Summer Beach Bar & Deep Vocal House"
  },
  {
    "id": 78,
    "name": "French Martini",
    "subtitle": "L'Élixir Velours Framboise & Ananas",
    "description": "Né à New York dans les années 80 : la noblesse de la liqueur de framboise noire Chambord associée au jus d'ananas crée une somptueuse mousse crémeuse.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Doux",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 4.5,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de framboise Chambord",
        "details": "Framboises noires royales",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus d'ananas",
        "amountCl": 4.5,
        "measureDescription": "1/4 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, la liqueur de framboise Chambord et le jus d'ananas dans le shaker avec des glaçons.",
      "Shaker très énergiquement pendant 12 secondes afin de provoquer une épaisse mousse veloutée.",
      "Double-filtrer dans une coupe à martini rafraîchie."
    ],
    "garnish": "Framboises.",
    "glassware": "Coupe à Martini",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/clth721504373134.jpg",
    "isFavorite": true,
    "vibeTag": "Chic Rooftop & Nu-Disco Groove"
  },
  {
    "id": 79,
    "name": "Gin Fizz",
    "subtitle": "L'Émulsion Pétillante Vivifiante",
    "description": "Le grand classique shaker : gin, citron jaune et sucre secoués jusqu'à glaçage total, puis allongés d'eau gazeuse pour faire jaillir les bulles.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Pétillant",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne liquide",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Très pétillante",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser le gin, le jus de citron et le sirop de sucre dans le shaker avec des glaçons.",
      "Shaker avec vigueur pendant 10 secondes.",
      "Filtrer dans un verre tumbler sans glaçons (ou avec glace) et allonger d'eau gazeuse bien froide."
    ],
    "garnish": "Tranche de citron.",
    "glassware": "Verre Tumbler ou Highball",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/drtihp1606768397.jpg",
    "isFavorite": false,
    "vibeTag": "Summer Garden & Sunny Acoustic"
  },
  {
    "id": 80,
    "name": "Hurricane",
    "subtitle": "La Tempête Tropicale de la Nouvelle-Orléans",
    "description": "Créé chez Pat O'Brien dans le French Quarter : deux rhums puissants emportés dans une tornade de fruits de la passion et d'oranges fraîches.",
    "category": "Cocktails",
    "flavorProfile": "Exotique/Puissant",
    "prepTimeMinutes": 4,
    "difficulty": "Expert",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Rhum blanc",
        "details": "Rhum caribéen",
        "amountCl": 6.0,
        "measureDescription": "1.5 shooters",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Rhum ambré",
        "details": "Rhum vieux puissant",
        "amountCl": 6.0,
        "measureDescription": "1.5 shooters",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de fruit de la passion",
        "details": "Nectar maracuja",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Sirop rouge vif",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer tous les ingrédients dans le shaker avec une abondance de glace.",
      "Shaker intensément pendant 12 secondes.",
      "Verser dans le verre iconique Hurricane garni de glace pilée."
    ],
    "garnish": "Orange et cerise.",
    "glassware": "Verre Ouragan Hurricane",
    "shakeSeconds": 12,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vqws6t1504888857.jpg",
    "isFavorite": false,
    "vibeTag": "Mardi Gras & New Orleans Brass Band"
  },
  {
    "id": 81,
    "name": "Planter's Punch",
    "subtitle": "Le Punch Historique de la Jamaïque",
    "description": "Le grand punch caribéen du XIXe siècle : 'One of sour, two of sweet, three of strong, four of weak' agrémenté de rhum ambré et d'Angostura.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Epicé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Rhum ambré",
        "details": "Rhum vieux jamaïcain",
        "amountCl": 4.5,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 3.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus doux",
        "amountCl": 3.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Grenadine",
        "details": "Sirop de fruits rouges",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Sirop de canne",
        "details": "Sucre de canne",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Angostura",
        "details": "Bitters aromatiques",
        "amountCl": 0.5,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser le rhum ambré, les jus de fruits, les sirops et les 2 traits d'Angostura dans le shaker rempli de glace.",
      "Shaker vivement pendant 10 secondes.",
      "Filtrer dans un grand verre tumbler rempli de glace pilée."
    ],
    "garnish": "Tranche d'orange.",
    "glassware": "Verre Tumbler Highball",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/fdk8a31606854815.jpg",
    "isFavorite": false,
    "vibeTag": "Kingston Reggae & Caribbean Chills"
  },
  {
    "id": 82,
    "name": "Woo Woo",
    "subtitle": "Le Délice Fruité des Discothèques",
    "description": "Le cocktail culte des bars branchés : un trio fruité détonant alliant la fraîcheur de la vodka, la douceur de la pêche et le rubis acidulé du cranberry.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Sucré",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de pêche",
        "details": "Peach Schnapps",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge acidulée",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, la liqueur de pêche et le jus de cranberry dans un shaker avec des glaçons.",
      "Shaker avec énergie pendant 8 secondes.",
      "Filtrer dans un verre highball rempli de glace."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre Highball",
    "shakeSeconds": 8,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/sxvrwv1473344825.jpg",
    "isFavorite": false,
    "vibeTag": "Late Night Club & Nu-Disco"
  },
  {
    "id": 83,
    "name": "Sea Breeze",
    "subtitle": "La Brise Marine Acidulée",
    "description": "L'esprit de la côte Atlantique américaine : la vivacité du pamplemousse rose et la fraîcheur du cranberry portées par une vodka glacée.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Fruité",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge pure",
        "amountCl": 9.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus rose",
        "amountCl": 3.0,
        "measureDescription": "2 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre highball de glaçons.",
      "Verser la vodka, le jus de cranberry et le jus de pamplemousse.",
      "Remuer doucement à la cuillère pour fondre les couleurs et les arômes."
    ],
    "garnish": "Tranche de pamplemousse.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7rfuks1504371562.jpg",
    "isFavorite": false,
    "vibeTag": "Coastal Breeze & Chill Electronic"
  },
  {
    "id": 84,
    "name": "Bay Breeze",
    "subtitle": "La Brise Hawaïenne Canneberge-Ananas",
    "description": "Aussi connu sous le nom d'Hawaiian Sea Breeze : le mariage irrésistible de la douceur de l'ananas tropical et de l'acidité de la canneberge.",
    "category": "Cocktails",
    "flavorProfile": "Doux/Exotique",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 10.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus doux",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser la vodka, le jus de cranberry et le jus d'ananas dans un shaker avec de la glace.",
      "Shaker brièvement pour homogénéiser.",
      "Verser dans un verre highball rempli de glace."
    ],
    "garnish": "Quartier d'ananas.",
    "glassware": "Verre Highball",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/ysuqus1441208583.jpg",
    "isFavorite": false,
    "vibeTag": "Bay Sunrise & Tropic Wave"
  },
  {
    "id": 85,
    "name": "Cape Codder",
    "subtitle": "Le Vodka-Cranberry Emblématique",
    "description": "Nommé d'après la péninsule de Cape Cod réputée pour ses marais de canneberges : le grand classique pur, sec et vivifiant.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Sec",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka de blé pure",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de cranberry",
        "details": "Pur jus de canneberge",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre highball de glace jusqu'en haut.",
      "Verser la vodka directement sur les glaçons.",
      "Allonger de jus de cranberry et remuer délicatement."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/3qpv121504366699.jpg",
    "isFavorite": false,
    "vibeTag": "Cape Cod Atlantic & Acoustic Calm"
  },
  {
    "id": 86,
    "name": "Greyhound",
    "subtitle": "La Lévrière Amère & Citronnée",
    "description": "Documenté pour la première fois en 1930 au Savoy : l'amertume noble du pamplemousse rose mariée à la pureté tranchante d'un gin sec ou d'une vodka.",
    "category": "Cocktails",
    "flavorProfile": "Amer/Acide",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Vodka ou Gin",
        "details": "Au choix : gin botanique ou vodka",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus de pamplemousse rose",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre Old Fashioned ou tumbler de gros glaçons.",
      "Verser la vodka (ou le gin).",
      "Allonger avec le jus de pamplemousse frais et remuer."
    ],
    "garnish": "Tranche de pamplemousse.",
    "glassware": "Verre Tumbler",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/g5upn41513706732.jpg",
    "isFavorite": false,
    "vibeTag": "Vintage Bus & Travel Indie"
  },
  {
    "id": 87,
    "name": "Salty Dog",
    "subtitle": "Le Greyhound au Col Salin",
    "description": "L'évolution marine du Greyhound : le bord givré au sel fin amplifie les saveurs d'agrumes et transforme l'amertume du pamplemousse en gourmandise.",
    "category": "Cocktails",
    "flavorProfile": "Amer/Salé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Vodka ou Gin",
        "details": "Gin ou vodka de qualité",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de pamplemousse",
        "details": "Pamplemousse rose pressé",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Fleur de sel",
        "details": "Pour givrer le col",
        "amountCl": 0.0,
        "measureDescription": "Bordure",
        "isGarnish": true,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Frotter le bord du verre avec un quartier de pamplemousse puis tremper dans une assiette de sel fin.",
      "Remplir délicatement de glaçons sans toucher la bordure salée.",
      "Verser la vodka (ou le gin) et compléter de jus de pamplemousse."
    ],
    "garnish": "Tranche de pamplemousse.",
    "glassware": "Verre Highball ou Rocks",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/4vfge01504890216.jpg",
    "isFavorite": false,
    "vibeTag": "Salty Harbor & Yacht Rock"
  },
  {
    "id": 88,
    "name": "Harvey Wallbanger",
    "subtitle": "Le Surfeur Doré aux Herbes Galliano",
    "description": "Né sur les plages de Californie dans les années 70 : un tournevis (vodka-orange) transformé par le nappage doré et vanillé de la liqueur italienne Galliano.",
    "category": "Cocktails",
    "flavorProfile": "Herbacé/Fruité",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 9.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Galliano",
        "details": "Liqueur d'anis & vanille",
        "amountCl": 1.5,
        "measureDescription": "En float",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Remplir un verre highball de glaçons.",
      "Verser la vodka puis le jus d'orange et mélanger.",
      "Faire flotter délicatement la liqueur Galliano sur le dessus à l'aide d'une cuillère de bar."
    ],
    "garnish": "Tranche d'orange.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7os4gs1606854357.jpg",
    "isFavorite": false,
    "vibeTag": "California 70s & Sunset Funk"
  },
  {
    "id": 89,
    "name": "Rob Roy",
    "subtitle": "Le Manhattan des Highlands Écossais",
    "description": "Créé à l'hôtel Waldorf Astoria de New York en 1894 en hommage au héros écossais : le caractère fumé du Scotch marié au vermouth rouge et aux bitters.",
    "category": "Cocktails",
    "flavorProfile": "Corsé/Herbacé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 30.0,
    "ingredients": [
      {
        "name": "Scotch Whisky",
        "details": "Blended Scotch de qualité",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vermouth Rouge",
        "details": "Vermouth doux aromatique",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Angostura Bitters",
        "details": "Bitters aromatiques",
        "amountCl": 0.5,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser le Scotch, le vermouth rouge et les 2 traits d'Angostura dans un verre à mélange avec beaucoup de glace.",
      "Remuer à la cuillère de bar pendant 30 secondes pour une dilution parfaite.",
      "Filtrer dans une coupe à cocktail rafraîchie."
    ],
    "garnish": "Cerise amarena.",
    "glassware": "Coupe à Cocktail vintage",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/pe1x1c1504735672.jpg",
    "isFavorite": false,
    "vibeTag": "Highland Castle & Celtic Strings"
  },
  {
    "id": 90,
    "name": "Blood and Sand",
    "subtitle": "L'Arène Épique Sang & Sable",
    "description": "Inspiré par le film de corrida de Rudolph Valentino en 1922 : une combinaison insolite et géniale à parts égales de Scotch, liqueur de cerise, vermouth et jus d'orange.",
    "category": "Cocktails",
    "flavorProfile": "Fruité/Corsé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Scotch Whisky",
        "details": "Scotch fruité",
        "amountCl": 2.0,
        "measureDescription": "1 mesure égale",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cherry Brandy",
        "details": "Liqueur de cerise",
        "amountCl": 2.0,
        "measureDescription": "1 mesure égale",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Vermouth Rouge",
        "details": "Vermouth di Torino",
        "amountCl": 2.0,
        "measureDescription": "1 mesure égale",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 2.0,
        "measureDescription": "1 mesure égale",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser les 4 ingrédients à parts rigoureusement égales dans le shaker avec des glaçons.",
      "Shaker vivement pendant 10 secondes.",
      "Filtrer dans une coupe à cocktail."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Coupe à Cocktail",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/pwgtpa1504366376.jpg",
    "isFavorite": false,
    "vibeTag": "Roaring 20s & Dramatic Cinema"
  },
  {
    "id": 91,
    "name": "Between the Sheets",
    "subtitle": "La Passion Audacieuse sous les Draps",
    "description": "Créé au Harry's New York Bar à Paris dans les années 30 : le Sidecar sublimé par l'ajout de rhum blanc caribéen pour une intensité captivante.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Sec",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Cognac",
        "details": "Cognac français",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Rhum blanc",
        "details": "Rhum pur jus",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'oranges",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer le cognac, le rhum blanc, le Cointreau et le jus de citron dans le shaker avec de la glace.",
      "Shaker énergiquement pendant 10 secondes.",
      "Double-filtrer dans une coupe à cocktail rafraîchie."
    ],
    "garnish": "Zeste de citron flamboyant.",
    "glassware": "Coupe à Cocktail",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/nl89tf1518947401.jpg",
    "isFavorite": false,
    "vibeTag": "Midnight Parisian Romance & Soul Jazz"
  },
  {
    "id": 92,
    "name": "Corpse Reviver No. 2",
    "subtitle": "L'Élixir Résurrecteur du Savoy",
    "description": "Le plus réputé des 'réveille-morts' du Savoy Cocktail Book de Harry Craddock : une goutte d'absinthe pour rincer le verre, réhaussant gin, Lillet et Cointreau.",
    "category": "Cocktails",
    "flavorProfile": "Acide/Anisé",
    "prepTimeMinutes": 3,
    "difficulty": "Expert",
    "alcoholPercentage": 25.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin",
        "amountCl": 2.0,
        "measureDescription": "1 part égale",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Lillet Blanc",
        "details": "Apéritif bordelais",
        "amountCl": 2.0,
        "measureDescription": "1 part égale",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Cointreau",
        "details": "Liqueur d'oranges",
        "amountCl": 2.0,
        "measureDescription": "1 part égale",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 part égale",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Absinthe",
        "details": "Pour rincer le verre",
        "amountCl": 0.2,
        "measureDescription": "1 trait de rinçage",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser un trait d'absinthe dans la coupe rafraîchie, faire tourner pour recouvrir la paroi, puis jeter l'excédent.",
      "Verser les 4 autres ingrédients à parts égales dans le shaker avec de la glace.",
      "Shaker vigoureusement pendant 10 secondes et double-filtrer dans la coupe préparée."
    ],
    "garnish": "Zeste de citron.",
    "glassware": "Coupe à Cocktail rafraîchie",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/gifgao1513704334.jpg",
    "isFavorite": false,
    "vibeTag": "Victorian Gothic & Classic Cello"
  },
  {
    "id": 93,
    "name": "Martinez",
    "subtitle": "L'Ancêtre Originel du Martini",
    "description": "Le grand-père direct du Dry Martini et du Manhattan (1884) : le profil doux et complexe du gin associé au vermouth rouge, relevé de marasquin et d'Angostura.",
    "category": "Cocktails",
    "flavorProfile": "Corsé/Doux",
    "prepTimeMinutes": 3,
    "difficulty": "Expert",
    "alcoholPercentage": 28.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Old Tom ou Dry Gin",
        "amountCl": 4.0,
        "measureDescription": "1 grand shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vermouth Rouge",
        "details": "Vermouth di Torino",
        "amountCl": 4.0,
        "measureDescription": "1 grand shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de Marasquin",
        "details": "Luxardo",
        "amountCl": 0.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Angostura",
        "details": "Bitters aromatiques",
        "amountCl": 0.5,
        "measureDescription": "2 traits",
        "isGarnish": false,
        "iconType": "invert_colors"
      }
    ],
    "steps": [
      "Verser le gin, le vermouth rouge, le marasquin et les 2 traits d'Angostura dans un verre à mélange avec beaucoup de glace.",
      "Remuer à la cuillère de bar pendant 30 secondes pour une dilution soyeuse.",
      "Filtrer dans une coupe à cocktail."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Coupe vintage",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/fs6kiq1513708455.jpg",
    "isFavorite": false,
    "vibeTag": "Golden Age San Francisco & Ragtime"
  },
  {
    "id": 94,
    "name": "Penicillin",
    "subtitle": "Le Philtre Tourbé & Épicé de Milk & Honey",
    "description": "Créé par Sam Ross en 2005 à New York : le remède suprême associant scotch blended, citron, miel, gingembre et un voile fumé de scotch d'Islay tourbé en float.",
    "category": "Cocktails",
    "flavorProfile": "Tourbé/Épicé",
    "prepTimeMinutes": 3,
    "difficulty": "Expert",
    "alcoholPercentage": 20.0,
    "ingredients": [
      {
        "name": "Blended Scotch",
        "details": "Scotch doux et fruité",
        "amountCl": 6.0,
        "measureDescription": "1.5 shooters",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de miel",
        "details": "Miel dilué 3:1",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Sirop de gingembre",
        "details": "Gingembre frais cuit",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Islay Scotch tourbé",
        "details": "Single malt fumé en float",
        "amountCl": 1.0,
        "measureDescription": "Nappage",
        "isGarnish": false,
        "iconType": "liquor"
      }
    ],
    "steps": [
      "Shaker le blended scotch, le citron vert, le sirop de miel et le sirop de gingembre avec des glaçons.",
      "Filtrer dans un verre Old Fashioned sur un gros glaçon cristallin.",
      "Faire couler délicatement le scotch tourbé d'Islay sur le dos de la cuillère pour créer le float fumé aromatique."
    ],
    "garnish": "Gingembre confit.",
    "glassware": "Verre Rocks Old Fashioned",
    "shakeSeconds": 12,
    "rating": 5.0,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/hc9b1a1521853096.jpg",
    "isFavorite": true,
    "vibeTag": "Milk & Honey Speakeasy & Smoky Jazz"
  },
  {
    "id": 95,
    "name": "Basil Smash",
    "subtitle": "L'Onde Verte Botanique de Hambourg",
    "description": "Créé par Jörg Meyer au bar Le Lion en 2008 : le vert émeraude vibrant du basilic frais écrasé, vivifié par la force du gin et le citron jaune.",
    "category": "Cocktails",
    "flavorProfile": "Herbacé/Frais",
    "prepTimeMinutes": 4,
    "difficulty": "Moyen",
    "alcoholPercentage": 15.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Gin sec botanique",
        "amountCl": 6.0,
        "measureDescription": "1.5 shooters",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1/2 citron",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre",
        "details": "Sucre de canne",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Feuilles de basilic",
        "details": "Basilic frais entier",
        "amountCl": 0.0,
        "measureDescription": "10 feuilles",
        "isGarnish": false,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Placer les feuilles de basilic, le jus de citron et le sirop de sucre dans le shaker.",
      "Piler vigoureusement pour broyer le basilic et libérer sa sève émeraude.",
      "Ajouter le gin et une abondance de glace, puis shaker avec une énergie maximale.",
      "Double-filtrer au chinois fin dans un verre rocks sur un gros glaçon."
    ],
    "garnish": "Tête de basilic.",
    "glassware": "Verre Tumbler Old Fashioned",
    "shakeSeconds": 12,
    "rating": 4.9,
    "imageUrl": "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Hamburg Speakeasy & Nordic House"
  },
  {
    "id": 96,
    "name": "London Mule",
    "subtitle": "La Variante Britannique au Gingembre",
    "description": "L'équivalent londonien du Moscow Mule : les baies de genièvre du gin remplacent la vodka pour un dialogue aromatique envoûtant avec la ginger beer.",
    "category": "Cocktails",
    "flavorProfile": "Épicé/Frais",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 12.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "London Dry Gin",
        "amountCl": 5.0,
        "measureDescription": "1 shooter généreux",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.5,
        "measureDescription": "1/2 citron vert",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Ginger Beer",
        "details": "Gingembre fermenté épicé",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir une timbale en cuivre ou un grand verre de glace pilée.",
      "Verser le gin et le jus de citron vert frais direct sur la glace.",
      "Allonger avec la ginger beer bien fraîche et remuer doucement à la cuillère de bar."
    ],
    "garnish": "Quartier de citron vert.",
    "glassware": "Timbale en cuivre",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/rj55pl1582476101.jpg",
    "isFavorite": false,
    "vibeTag": "Soho Rooftop & London Underground Beat"
  },
  {
    "id": 97,
    "name": "Roy Rogers",
    "subtitle": "Le Cow-Boy Pétillant 0%",
    "description": "0% alcool : le pendant au cola du Shirley Temple, baptisé en hommage au célèbre cow-boy chantant du cinéma hollywoodien.",
    "category": "Mocktails",
    "flavorProfile": "Sucré/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Cola",
        "details": "Cola bien frais",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine concentrée",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un verre highball de gros glaçons.",
      "Verser le sirop de grenadine au fond du verre.",
      "Allonger doucement de cola très frais direct au verre et remuer délicatement."
    ],
    "garnish": "Cerise au marasquin.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/6bec6v1503563675.jpg",
    "isFavorite": false,
    "vibeTag": "Vintage Diner & Rockabilly"
  },
  {
    "id": 98,
    "name": "Arnold Palmer",
    "subtitle": "L'Accord Parfait Thé & Limonade 0%",
    "description": "0% alcool : popularisé par la légende du golf Arnold Palmer, la rencontre désaltérante suprême à parts égales de thé glacé non sucré et de limonade vive.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Thé",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Thé glacé",
        "details": "Infusion de thé noir froid",
        "amountCl": 8.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Limonade",
        "details": "Citronnade pétillante",
        "amountCl": 8.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un grand verre de glaçons jusqu'en haut.",
      "Verser à parts égales le thé noir glacé et la limonade.",
      "Mélanger délicatement à la cuillère de bar."
    ],
    "garnish": "Tranche de citron.",
    "glassware": "Grand verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/xrsrpr1441247464.jpg",
    "isFavorite": false,
    "vibeTag": "Sunny Fairways & Smooth Acoustic"
  },
  {
    "id": 99,
    "name": "Faux-Groni",
    "subtitle": "Le Negroni Sans Alcool Botanique 0%",
    "description": "0% alcool : l'amertume et la complexité du Negroni reconstituées sans une goutte d'alcool grâce au pamplemousse amer, au raisin blanc et au thé noir corsé.",
    "category": "Mocktails",
    "flavorProfile": "Amer/Herbacé",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de raisin blanc",
        "details": "Pur jus doux",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de pamplemousse amer",
        "details": "Ou bitter sans alcool",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Infusion de thé noir corsé",
        "details": "Thé infusé froid tanique",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Déposer un gros cube de glace pur dans un verre Old Fashioned.",
      "Verser le jus de raisin blanc, le sirop de pamplemousse amer et le thé noir corsé.",
      "Mélanger à la cuillère de bar pendant 30 secondes pour une dilution soyeuse.",
      "Exprimer un zeste d'orange au-dessus du verre."
    ],
    "garnish": "Zeste d'orange.",
    "glassware": "Verre Old Fashioned Lowball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/x8lhp41513703167.jpg",
    "isFavorite": false,
    "vibeTag": "Sober Speakeasy & Vinyl Mood"
  },
  {
    "id": 100,
    "name": "No-Loma",
    "subtitle": "La Paloma Pétillante Détox 0%",
    "description": "0% alcool : toute l'énergie festive de la Paloma mexicaine sans alcool avec du jus de pamplemousse frais, du citron vert, du nectar d'agave et un bord salé.",
    "category": "Mocktails",
    "flavorProfile": "Amer/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus frais rose",
        "amountCl": 5.0,
        "measureDescription": "1 grand shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop d'agave",
        "details": "Nectar d'agave bio",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 8.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Fleur de sel",
        "details": "Pour givrer le col",
        "amountCl": 0.0,
        "measureDescription": "Bordure",
        "isGarnish": true,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Frotter le bord du verre avec du citron vert et tremper dans une coupelle de fleur de sel.",
      "Remplir le verre de glaçons sans toucher la bordure.",
      "Verser le jus de pamplemousse, le jus de citron vert et le sirop d'agave.",
      "Allonger d'eau gazeuse fraîche et remuer doucement."
    ],
    "garnish": "Quartier de pamplemousse.",
    "glassware": "Verre Highball givré",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/samm5j1513706393.jpg",
    "isFavorite": true,
    "vibeTag": "Baja California Sunset & Warm Lo-Fi"
  },
  {
    "id": 101,
    "name": "Virgin Mojito",
    "subtitle": "L'Incontournable Menthe & Citron Vert 0%",
    "description": "0% alcool, 100% fraîcheur : des feuilles de menthe fraîchement froissées, du jus de citron vert pressé minute et du sucre de canne, allongés d'eau gazeuse très fraîche sur glace pilée.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Herbacé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Menthe fraîche",
        "details": "Feuilles entières fraîches",
        "amountCl": 0.0,
        "measureDescription": "8 feuilles",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Pur sucre liquide",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche et pétillante",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer les feuilles de menthe, le jus de citron vert et le sirop de canne au fond du verre.",
      "Piler délicatement sans déchirer les feuilles pour extraire les huiles essentielles.",
      "Remplir le verre de glace pilée jusqu'en haut.",
      "Allonger d'eau gazeuse fraîche et remuer de bas en haut avec la cuillère de bar."
    ],
    "garnish": "Belle tête de menthe fraîche et rondelle de citron vert.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vwxrsw1478251483.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Havana Acoustic"
  },
  {
    "id": 102,
    "name": "Bora Bora",
    "subtitle": "L'Évasion Tropicale Ananas & Passion 0%",
    "description": "0% alcool : un voyage exotique en Polynésie associant la douceur du jus d'ananas, la puissance parfumée du fruit de la passion et une larme de grenadine créant un sublime dégradé coucher de soleil.",
    "category": "Mocktails",
    "flavorProfile": "Exotique/Fruité",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'ananas",
        "details": "Pur jus pressé",
        "amountCl": 10.0,
        "measureDescription": "1/2 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de fruit de la passion",
        "details": "Nectar onctueux",
        "amountCl": 6.0,
        "measureDescription": "2 shooters",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine rouge intense",
        "amountCl": 1.5,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser le jus d'ananas, le jus de passion et le jus de citron dans un shaker rempli de glaçons.",
      "Frapper énergiquement pendant 10 secondes.",
      "Filtrer dans un verre hurricane rempli de glaçons frais.",
      "Faire couler délicatement le sirop de grenadine sur la paroi pour un dégradé étagé."
    ],
    "garnish": "Triangle d'ananas frais et cerise marasquin.",
    "glassware": "Verre Hurricane",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/xwuqvw1473201811.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Tropical Sunset"
  },
  {
    "id": 103,
    "name": "Shirley Temple",
    "subtitle": "Le Charme Hollywoodien Grenadine & Ginger Ale",
    "description": "0% alcool : le classique américain intemporel imaginé dans les années 1930 pour la jeune actrice, mêlant les bulles épicées du Ginger Ale à la douceur rubis de la grenadine.",
    "category": "Mocktails",
    "flavorProfile": "Pétillant/Sucré",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Ginger Ale",
        "details": "Boisson gazeuse au gingembre",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Sirop de fruits rouges",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus de citron jaune",
        "details": "Pressé minute",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un verre tumbler de glaçons généreux.",
      "Verser le sirop de grenadine et le trait de jus de citron.",
      "Compléter avec le Ginger Ale bien frappé.",
      "Mélanger délicatement à l'aide d'une cuillère de bar."
    ],
    "garnish": "Cerise confite au marasquin et zeste de citron.",
    "glassware": "Verre Tumbler",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/qsyqqq1441553437.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Vintage Swing"
  },
  {
    "id": 104,
    "name": "Virgin Piña Colada",
    "subtitle": "Le Velouté Coco & Ananas Gourmand",
    "description": "0% alcool : une caresse crémeuse et voluptueuse où l'onctuosité de la crème de coco s'unit au pur jus d'ananas mûri sous le soleil des Caraïbes.",
    "category": "Mocktails",
    "flavorProfile": "Doux/Crémeux",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'ananas",
        "details": "Pur jus pressé",
        "amountCl": 12.0,
        "measureDescription": "1 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Crème de coco",
        "details": "Onctueuse et dense",
        "amountCl": 5.0,
        "measureDescription": "1 shooter et demi",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Lait de coco",
        "details": "Lait léger",
        "amountCl": 3.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sirop simple",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Mettre tous les ingrédients dans un blender ou shaker avec beaucoup de glace pilée.",
      "Mixer ou shaker vivement pendant 15 secondes pour créer une émulsion soyeuse.",
      "Verser dans un verre tulipe bien froid."
    ],
    "garnish": "Triangle d'ananas frais et cerise rouge.",
    "glassware": "Verre Tulipe Poco Grande",
    "shakeSeconds": 15,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/cpf4j51504371346.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Bossa Nova"
  },
  {
    "id": 105,
    "name": "Safe Sex on the Beach",
    "subtitle": "La Douceur Pêche, Canneberge & Orange",
    "description": "0% alcool : tout le plaisir sensuel et festif du célèbre cocktail de plage, combinant nectar de pêche onctueux, jus d'orange ensoleillé et jus de canneberge tonique.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Acidulé",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de canneberge",
        "details": "Cranberry acidulé",
        "amountCl": 8.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus d'orange pressée",
        "amountCl": 8.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Nectar de pêche",
        "details": "Pêche blanche onctueuse",
        "amountCl": 4.0,
        "measureDescription": "1 shooter et demi",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Sirop fruité",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un verre highball de glaçons.",
      "Verser le nectar de pêche et le jus d'orange, puis remuer brièvement.",
      "Verser délicatement le jus de canneberge pour un effet bicolore élégant.",
      "Terminer par un trait de grenadine qui se dépose au fond."
    ],
    "garnish": "Tranche d'orange fraîche et cerise.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/vuquyv1468876052.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Beach Club Melodies"
  },
  {
    "id": 106,
    "name": "Virgin Mary",
    "subtitle": "Le Grand Cocktail Tomate & Épices Détox",
    "description": "0% alcool : la formule culte et corsée du Bloody Mary sans alcool avec du jus de tomate épais assaisonné de citron, sel de céleri, sauce Worcestershire et Tabasco piquant.",
    "category": "Mocktails",
    "flavorProfile": "Salé/Épicé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de tomate",
        "details": "Pur jus épais de tomate",
        "amountCl": 15.0,
        "measureDescription": "Grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron jaune",
        "details": "Pressé frais",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sauce Worcestershire",
        "details": "Sauce anglaise relevée",
        "amountCl": 0.5,
        "measureDescription": "3 gouttes",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Tabasco",
        "details": "Piment rouge liquide",
        "amountCl": 0.2,
        "measureDescription": "2 gouttes",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sel de céleri",
        "details": "Sel aromatique",
        "amountCl": 0.0,
        "measureDescription": "1 pincée",
        "isGarnish": true,
        "iconType": "spa"
      },
      {
        "name": "Poivre noir",
        "details": "Moulue minute",
        "amountCl": 0.0,
        "measureDescription": "1 tour de moulin",
        "isGarnish": true,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Mettre les glaçons dans un grand verre highball.",
      "Ajouter le jus de citron, la sauce Worcestershire, le Tabasco, le sel de céleri et le poivre.",
      "Verser le jus de tomate bien frais.",
      "Mélanger délicatement à l'aide d'une branche de céleri."
    ],
    "garnish": "Branche de céleri croquante et rondelle de citron.",
    "glassware": "Verre Collins",
    "shakeSeconds": 0,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7p607y1504735343.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Lo-Fi Sunday"
  },
  {
    "id": 107,
    "name": "Florida",
    "subtitle": "L'Accord Trois Agrumes Multivitaminé",
    "description": "0% alcool : un bouquet d'agrumes gorgés de soleil où le pamplemousse rose, l'orange douce et le citron vert s'harmonisent en un cocktail ultra-rafraîchissant.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus rose frais",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sirop simple",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer des glaçons dans le shaker.",
      "Ajouter tous les jus d'agrumes et le sirop de canne.",
      "Frapper vigoureusement pendant 10 secondes.",
      "Passer dans un grand verre sur lit de glaçons frais."
    ],
    "garnish": "Quartier de pamplemousse rose et brin de menthe.",
    "glassware": "Verre Highball",
    "shakeSeconds": 10,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/i3tfn31484430499.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Miami Breezes"
  },
  {
    "id": 108,
    "name": "Cendrillon",
    "subtitle": "La Féerie Fruitée Citron, Ananas & Grenadine",
    "description": "0% alcool : une création féerique associant en parts égales orange, ananas et citron, sublimée par une larme de grenadine et une pointe d'eau pétillante.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 5.0,
        "measureDescription": "1/4 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus doux",
        "amountCl": 5.0,
        "measureDescription": "1/4 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron jaune",
        "details": "Pressé minute",
        "amountCl": 5.0,
        "measureDescription": "1/4 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine rouge intense",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 5.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Shaker les jus d'orange, d'ananas et de citron avec de la glace.",
      "Filtrer dans un verre tulipe rafraîchi.",
      "Verser délicatement le filet de grenadine qui traverse le cocktail.",
      "Allonger d'un trait d'eau gazeuse pour une effervescence féerique."
    ],
    "garnish": "Tranche d'orange et cerise confite.",
    "glassware": "Verre Tulipe",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/uptxtv1468876415.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Fairy Lights"
  },
  {
    "id": 109,
    "name": "Chantaco",
    "subtitle": "Le Joyau Basque Pamplemousse & Fruits Rouges",
    "description": "0% alcool : né sur la Côte Basque dans les années folles, ce cocktail sans alcool raffiné réunit le peps du pamplemousse, la douceur de l'orange et la rondeur du sirop de fraise ou grenadine.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Doux",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus pressé",
        "amountCl": 6.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus rose",
        "amountCl": 4.0,
        "measureDescription": "1 shooter et demi",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron jaune",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Ou sirop de fraise",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer les glaçons dans le shaker.",
      "Verser le jus d'orange, le jus de pamplemousse, le jus de citron et le sirop.",
      "Frapper avec intensité pendant 10 secondes.",
      "Verser dans un verre à cocktail sans glaçons pour une dégustation soyeuse."
    ],
    "garnish": "Rondelle de citron jaune et demi-fraise.",
    "glassware": "Verre à Cocktail",
    "shakeSeconds": 10,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/mzgaqu1504389248.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Atlantic Acoustic"
  },
  {
    "id": 110,
    "name": "Sweet Sunrise",
    "subtitle": "Le Dégradé Solaire Orange & Grenadine",
    "description": "0% alcool : l'alternative sans alcool du Tequila Sunrise, offrant un dégradé spectaculaire d'aube lumineuse entre l'orange fraîchement pressée et la grenadine onctueuse.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Solaire",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus d'orange",
        "details": "Pur jus d'orange fraîche",
        "amountCl": 15.0,
        "measureDescription": "Grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Grenadine concentrée",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un verre highball de gros cubes de glace limpides.",
      "Verser doucement le jus d'orange frais.",
      "Faire couler lentement le sirop de grenadine le long de la paroi interne.",
      "Laisser la grenadine descendre au fond pour former le lever de soleil sans remuer."
    ],
    "garnish": "Tranche d'orange sanguine et zeste.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Golden Hour"
  },
  {
    "id": 111,
    "name": "Apple Mojito",
    "subtitle": "La Pomme Croquante & Menthe Fraîche",
    "description": "0% alcool : une réinterprétation champêtre et gourmande du mojito associant la douceur d'un pur jus de pomme trouble pressé à froid et le parfum de menthe poivrée.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Fruité",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de pomme trouble",
        "details": "Pur jus artisanal",
        "amountCl": 10.0,
        "measureDescription": "1/2 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Menthe fraîche",
        "details": "Feuilles parfumées",
        "amountCl": 0.0,
        "measureDescription": "8 feuilles",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 2.5,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sucre liquide",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche pétillante",
        "amountCl": 6.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Écraser délicatement le citron vert avec les feuilles de menthe et le sirop au fond du verre.",
      "Remplir le verre de glace pilée.",
      "Verser le jus de pomme trouble et compléter d'eau gazeuse.",
      "Mélanger délicatement à la cuillère de bas en haut."
    ],
    "garnish": "Éventail de fines lamelles de pomme Granny Smith et brin de menthe.",
    "glassware": "Verre Highball",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/yswuwp1469090992.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Indie Acoustic"
  },
  {
    "id": 112,
    "name": "Lipton Tonic",
    "subtitle": "Le Thé Glacé Pétillant & Agrumes",
    "description": "0% alcool : une infusion de thé glacé raffiné alliée au pétillement aromatique du Tonic Water et à une touche de pêche blanche pour une soif étanchée avec classe.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Thé/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Thé glacé",
        "details": "Infusion de thé noir froid",
        "amountCl": 12.0,
        "measureDescription": "Grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Tonic Water",
        "details": "Eau tonique amère",
        "amountCl": 8.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de pêche",
        "details": "Sirop de pêche blanche",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Jus de citron jaune",
        "details": "Pressé frais",
        "amountCl": 1.0,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Remplir un grand verre de glaçons.",
      "Verser le thé glacé, le sirop de pêche et le jus de citron.",
      "Compléter avec le Tonic Water bien pétillant.",
      "Remuer délicatement une fois avec la cuillère de bar."
    ],
    "garnish": "Rondelle de citron jaune et branche de romarin.",
    "glassware": "Grand verre Highball",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/nkwr4c1606770558.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - London Rooftop"
  },
  {
    "id": 113,
    "name": "Berry Smash 0%",
    "subtitle": "L'Explosion Mûres, Framboises & Limonade",
    "description": "0% alcool : un écrasé gourmand de baies sauvages avec du jus de canneberge pétillant et une pointe de sirop d'agave naturel.",
    "category": "Mocktails",
    "flavorProfile": "Fruité/Acidulé",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Framboises",
        "details": "Framboises fraîches",
        "amountCl": 0.0,
        "measureDescription": "5 baies",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Mûres",
        "details": "Mûres sauvages fraîches",
        "amountCl": 0.0,
        "measureDescription": "4 baies",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Jus de canneberge",
        "details": "Cranberry pur jus",
        "amountCl": 8.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop d'agave",
        "details": "Nectar bio",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Limonade",
        "details": "Artisanale gazeuse",
        "amountCl": 6.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Écraser les baies de mûres et framboises avec le sirop d'agave au fond du verre.",
      "Remplir le verre de glaçons entiers.",
      "Verser le jus de canneberge et allonger de limonade bien fraîche.",
      "Remuer doucement pour diffuser la robe rouge rubis."
    ],
    "garnish": "Brochette de fruits des bois et tête de menthe.",
    "glassware": "Verre Old Fashioned",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/spvvxp1468924425.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Velvet Beats"
  },
  {
    "id": 114,
    "name": "Ginger Sparkler",
    "subtitle": "L'Éclat Épicé Pomme & Ginger Beer",
    "description": "0% alcool : le kick tonique et ardent du gingembre naturel adouci par la rondeur du pur jus de pomme pressé et une touche de citron vert.",
    "category": "Mocktails",
    "flavorProfile": "Épicé/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Ginger Beer",
        "details": "Sans alcool fermentée",
        "amountCl": 12.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de pomme trouble",
        "details": "Pur jus pressé",
        "amountCl": 6.0,
        "measureDescription": "1 shooter et demi",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de miel",
        "details": "Miel délayé",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un mug en cuivre ou verre tumbler de glace pilée.",
      "Verser le jus de pomme, le jus de citron vert et le sirop de miel.",
      "Compléter avec la Ginger Beer pétillante.",
      "Remuer une fois délicatement."
    ],
    "garnish": "Morceau de gingembre confit et branche de thym.",
    "glassware": "Mug en cuivre ou Tumbler",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/stsuqq1441207660.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Nordic Minimal"
  },
  {
    "id": 115,
    "name": "Green Detox Fizz",
    "subtitle": "Le Concombre Vivifiant & Basilic Frais",
    "description": "0% alcool : un élixir de bien-être ultra-désaltérant mariant la fraîcheur aqueuse du concombre, l'arôme anisé du basilic et la vivacité du citron vert.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Botanique",
    "prepTimeMinutes": 3,
    "difficulty": "Moyen",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Concombre frais",
        "details": "Rondelles découpées",
        "amountCl": 0.0,
        "measureDescription": "4 rondelles",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Basilic frais",
        "details": "Feuilles fraîches",
        "amountCl": 0.0,
        "measureDescription": "5 feuilles",
        "isGarnish": false,
        "iconType": "spa"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 2.5,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sirop simple",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Eau gazeuse",
        "details": "Fraîche et fine",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Écraser les rondelles de concombre et le basilic dans le shaker avec le citron et le sirop.",
      "Ajouter de la glace et shaker brièvement 6 secondes.",
      "Double-filtrer dans un grand verre rempli de glaçons frais.",
      "Allonger d'eau gazeuse fraîche."
    ],
    "garnish": "Ruban de concombre le long du verre et feuille de basilic.",
    "glassware": "Verre Highball",
    "shakeSeconds": 6,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/5jdp5r1487603680.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Botanical Garden"
  },
  {
    "id": 116,
    "name": "Passion Colada",
    "subtitle": "L'Exotisme Mangue, Passion & Lait de Coco",
    "description": "0% alcool : la rencontre solaire entre le velouté de la mangue mûre, le peps acidulé du fruit de la passion et l'onctuosité soyeuse du lait de coco.",
    "category": "Mocktails",
    "flavorProfile": "Exotique/Crémeux",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Nectar de mangue",
        "details": "Pur nectar riche",
        "amountCl": 8.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de fruit de la passion",
        "details": "Jus pur acidulé",
        "amountCl": 5.0,
        "measureDescription": "1 shooter et demi",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Lait de coco",
        "details": "Lait onctueux",
        "amountCl": 4.0,
        "measureDescription": "1 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Placer tous les ingrédients dans le shaker avec des glaçons cubiques.",
      "Frapper vivement pendant 12 secondes pour bien aérer la texture du lait de coco.",
      "Verser dans un verre hurricane rempli de glaçons frais."
    ],
    "garnish": "Demi-fruit de la passion et paille en inox.",
    "glassware": "Verre Hurricane",
    "shakeSeconds": 12,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/6trfve1582473527.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Sunset Tropics"
  },
  {
    "id": 117,
    "name": "Pink Grapefruit Spritz 0%",
    "subtitle": "Le Spritz Vénitien Sans Alcool & Romarin",
    "description": "0% alcool : toute l'élégance de l'apéritif italien réinventée avec du pur jus de pamplemousse rose, un bitter sans alcool, du tonic effervescent et du romarin aromatique.",
    "category": "Mocktails",
    "flavorProfile": "Amer/Pétillant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de pamplemousse",
        "details": "Pur jus pressé rose",
        "amountCl": 8.0,
        "measureDescription": "1/3 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de pamplemousse amer",
        "details": "Ou bitter sans alcool",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Tonic Water",
        "details": "Eau tonique fraîche",
        "amountCl": 10.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Romarin",
        "details": "Branche fraîche",
        "amountCl": 0.0,
        "measureDescription": "1 branche",
        "isGarnish": true,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Remplir un grand verre ballon de gros glaçons.",
      "Verser le jus de pamplemousse et le sirop de bitter.",
      "Compléter avec l'eau tonique fraîche.",
      "Mélanger délicatement à l'aide de la branche de romarin pour libérer les arômes."
    ],
    "garnish": "Tranche de pamplemousse rose et branche de romarin.",
    "glassware": "Grand verre Ballon",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://images.unsplash.com/photo-1560512823-829485b8bf24?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Italian Piazza"
  },
  {
    "id": 118,
    "name": "Watermelon Breeze",
    "subtitle": "La Vague Pastèque Givrée & Canneberge",
    "description": "0% alcool : une vague désaltérante de pur jus de pastèque fraîche mixée, équilibrée par la vivacité de la canneberge et du citron vert pressé.",
    "category": "Mocktails",
    "flavorProfile": "Frais/Fruité",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Jus de pastèque",
        "details": "Pur jus frais mixé",
        "amountCl": 12.0,
        "measureDescription": "1 grand verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de canneberge",
        "details": "Cranberry pur jus",
        "amountCl": 4.0,
        "measureDescription": "1 shooter et demi",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé minute",
        "amountCl": 2.0,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sirop simple",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Placer les jus de pastèque, de canneberge, de citron vert et le sirop dans le shaker avec des glaçons.",
      "Frapper énergiquement pendant 8 secondes.",
      "Verser sans filtrer la pulpe fine dans un verre rempli de glace pilée."
    ],
    "garnish": "Petit triangle de pastèque sur le rebord et feuille de menthe.",
    "glassware": "Verre Tumbler",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://images.unsplash.com/photo-1609951651556-5334e2706168?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Summer Poolside"
  },
  {
    "id": 119,
    "name": "Virgin Moscow Mule",
    "subtitle": "Le Caractère Cuivré Gingembre & Citron Vert",
    "description": "0% alcool : toute l'énergie piquante du Moscow Mule servie dans sa chope de cuivre glacée avec une Ginger Beer corsée, du citron vert et du concombre croquant.",
    "category": "Mocktails",
    "flavorProfile": "Épicé/Vivifiant",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Ginger Beer",
        "details": "Gingembre épicé sans alcool",
        "amountCl": 14.0,
        "measureDescription": "Allonger",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 3.0,
        "measureDescription": "1 grand shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de sucre de canne",
        "details": "Sirop simple",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Concombre frais",
        "details": "Rondelles croquantes",
        "amountCl": 0.0,
        "measureDescription": "2 rondelles",
        "isGarnish": false,
        "iconType": "spa"
      }
    ],
    "steps": [
      "Remplir une chope en cuivre traditionnelle de glace pilée.",
      "Verser le jus de citron vert pressé et le sirop de canne.",
      "Allonger avec la Ginger Beer bien frappée.",
      "Remuer délicatement et insérer les rondelles de concombre."
    ],
    "garnish": "Quartier de citron vert et rondelle de concombre.",
    "glassware": "Chope en cuivre",
    "shakeSeconds": 0,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/3pylqc1504370988.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Copper Beats"
  },
  {
    "id": 120,
    "name": "Coco Mango Dream",
    "subtitle": "L'Élixir Velouté Mangue & Eau de Coco",
    "description": "0% alcool : un délice soyeux et hydratant alliant purée de mangue mûre, eau de coco fraîche naturelle, une pointe de vanille bourbon et un zeste de citron vert.",
    "category": "Mocktails",
    "flavorProfile": "Doux/Exotique",
    "prepTimeMinutes": 3,
    "difficulty": "Facile",
    "alcoholPercentage": 0.0,
    "ingredients": [
      {
        "name": "Nectar de mangue",
        "details": "Purée de mangue mûre",
        "amountCl": 8.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Eau de coco",
        "details": "Eau de coco fraîche",
        "amountCl": 8.0,
        "measureDescription": "1/3 verre",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Jus de citron vert",
        "details": "Pressé frais",
        "amountCl": 1.5,
        "measureDescription": "1 cuil. à soupe",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de vanille",
        "details": "Vanille bourbon liquide",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Mettre tous les ingrédients dans un shaker avec de la glace.",
      "Frapper énergiquement pendant 10 secondes.",
      "Verser dans un verre highball avec des glaçons frais."
    ],
    "garnish": "Pincée de vanille moulue et feuille de menthe.",
    "glassware": "Verre Highball",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/rytuex1598719770.jpg",
    "isFavorite": false,
    "vibeTag": "Chill & Lounge - Deep Tropics"
  },
  {
    "id": 121,
    "name": "Tequila Paf",
    "subtitle": "Le Rituel Culte Taper-Gober",
    "description": "Le shot de fête mythique des soirées étudiantes : sel sur le poignet, tequila frappée sur le comptoir avec du tonic pour une explosion gazeuse instantanée, et quartier de citron vert croqué dans la foulée.",
    "category": "Shooters",
    "flavorProfile": "Piquant/Agreste",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 28.0,
    "ingredients": [
      {
        "name": "Tequila Blanco",
        "details": "Tequila 100% agave",
        "amountCl": 3.0,
        "measureDescription": "2/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Schweppes Tonic",
        "details": "Pétillant bien frais",
        "amountCl": 2.0,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Citron vert",
        "details": "Quartier juteux",
        "amountCl": 0.0,
        "measureDescription": "1 quartier",
        "isGarnish": true,
        "iconType": "nutrition"
      },
      {
        "name": "Sel fin",
        "details": "Pincée sur le dos de la main",
        "amountCl": 0.0,
        "measureDescription": "1 pincée",
        "isGarnish": true,
        "iconType": "grain"
      }
    ],
    "steps": [
      "Lécher le dos de la main entre le pouce et l'index, puis déposer une pincée de sel.",
      "Verser la tequila puis le tonic dans un verre à shooter à fond épais.",
      "Couvrir le verre avec la paume de la main, le frapper fermement deux fois sur le comptoir (« Paf ! »).",
      "Lécher le sel, boire d'un trait le shooter en pleine effervescence, puis mordre dans le quartier de citron vert."
    ],
    "garnish": "Quartier de citron vert frais et sel.",
    "glassware": "Verre Shooter à fond épais",
    "shakeSeconds": 0,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/ek0mlq1504820601.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 122,
    "name": "Kiss Cool",
    "subtitle": "Le Double Effet Glacial",
    "description": "Le cocktail shooter emblématique des clubs français : un shoot bleu lagon électrisant mariant le punch de la vodka, la menthe poivrée glaciale du Get 27 et le bleu hypnotique du Curaçao.",
    "category": "Shooters",
    "flavorProfile": "Mentholé/Glacé",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 24.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Get 27",
        "details": "Liqueur de menthe poivrée",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Curaçao bleu",
        "details": "Liqueur d'orange bleue",
        "amountCl": 0.5,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Remplir un shaker de glaçons.",
      "Verser la vodka, le Get 27 et le curaçao bleu.",
      "Frapper vigoureusement pendant 8 secondes pour refroidir au maximum.",
      "Filtrer dans un verre shooter givré et déguster d'un trait."
    ],
    "garnish": "Givrage bleu sur le rebord.",
    "glassware": "Verre Shooter givré",
    "shakeSeconds": 8,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/dbtylp1493067262.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 123,
    "name": "Tétine",
    "subtitle": "Le Shot Bonbon Acidulé",
    "description": "Le chouchou régressif des nuits étudiantes : un shot rouge bonbon sucré et piquant associant vodka, liqueur de fraise des bois, jus de citron jaune et une pointe de grenadine.",
    "category": "Shooters",
    "flavorProfile": "Fruité/Bonbon",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 19.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka pure",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de fraise",
        "details": "Fraise des bois gourmande",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de citron jaune frais",
        "details": "Pressé minute pour le peps",
        "amountCl": 1.0,
        "measureDescription": "1 cuil. à café",
        "isGarnish": false,
        "iconType": "nutrition"
      },
      {
        "name": "Sirop de grenadine",
        "details": "Pour la couleur rubis intense",
        "amountCl": 0.5,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Mettre tous les ingrédients dans un shaker rempli de glace.",
      "Shaker vivement pendant 6 secondes.",
      "Verser dans un verre à shooter.",
      "Accrocher une friandise tétine acidulée sur le rebord du verre avant de servir."
    ],
    "garnish": "Bonbon tétine acidulé sur le bord.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/fegm621503564966.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 124,
    "name": "Flatliner",
    "subtitle": "L'Électrochoc Pimenté",
    "description": "Un shot audacieux et tranchant à trois étages : la douceur d'une Sambuca blanche, une ligne de démarcation brûlante de Tabasco rouge en suspension, et la puissance d'une Tequila 100% agave.",
    "category": "Shooters",
    "flavorProfile": "Épicé/Puissant",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 34.0,
    "ingredients": [
      {
        "name": "Sambuca",
        "details": "Liqueur d'anis douce",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Tequila Blanco",
        "details": "Tequila blanche pure",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Tabasco",
        "details": "Sauce pimentée rouge",
        "amountCl": 0.2,
        "measureDescription": "4 gouttes en ligne",
        "isGarnish": false,
        "iconType": "grain"
      }
    ],
    "steps": [
      "Verser la Sambuca au fond du verre à shooter.",
      "Déposer avec précaution 4 à 5 gouttes de Tabasco rouge pour créer une ligne horizontale suspendue à la surface de la Sambuca.",
      "Faire couler délicatement la Tequila sur le dos d'une cuillère pour former la strate supérieure transparente.",
      "Admirer la ligne de piment au milieu puis avaler cul-sec !"
    ],
    "garnish": "Ligne rouge de Tabasco en lévitation.",
    "glassware": "Verre Shooter transparent",
    "shakeSeconds": 0,
    "rating": 4.6,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/tysssx1473344692.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 125,
    "name": "Vodka Caramel",
    "subtitle": "La Gourmandise Fondante & Givrée",
    "description": "La liqueur maison incontournable des soirées festives : une vodka soyeuse infusée aux arômes chauds de caramel au beurre salé et de vanille bourbon, servie extra-froide.",
    "category": "Shooters",
    "flavorProfile": "Caramélisé/Chaud",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 22.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche de qualité",
        "amountCl": 3.0,
        "measureDescription": "2/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Sirop de caramel",
        "details": "Caramel beurre salé liquide",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "water_drop"
      },
      {
        "name": "Fleur de sel",
        "details": "Pour exalter les notes grillées",
        "amountCl": 0.1,
        "measureDescription": "1 micro-pincée",
        "isGarnish": false,
        "iconType": "grain"
      }
    ],
    "steps": [
      "Dans un shaker garni de glace pilée, mélanger la vodka, le sirop de caramel et une micro-pincée de fleur de sel.",
      "Shaker vigoureusement 10 secondes pour rendre le mélange glacé et légèrement sirupeux.",
      "Filtrer dans un shooter sortant du congélateur."
    ],
    "garnish": "Rebord caramélisé et touche de fleur de sel.",
    "glassware": "Verre Shooter givré",
    "shakeSeconds": 10,
    "rating": 4.9,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/ryvtsu1441253851.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  },
  {
    "id": 126,
    "name": "Russe Blanc Shooter",
    "subtitle": "Le White Russian Concentré",
    "description": "La version shooter express du classique immortalisé par The Dude : un socle de vodka et Kahlúa frappés, couronné d'un nuage de crème fraîche froide posé à la cuillère.",
    "category": "Shooters",
    "flavorProfile": "Café/Onctueux",
    "prepTimeMinutes": 2,
    "difficulty": "Moyen",
    "alcoholPercentage": 21.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de café Kahlúa",
        "details": "Café noir torréfié",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Crème liquide entière",
        "details": "Crème fraîche bien froide",
        "amountCl": 1.0,
        "measureDescription": "1 nappe",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Mélanger la vodka et la liqueur de café dans un shaker avec de la glace.",
      "Filtrer dans le verre à shooter.",
      "Déposer avec douceur la crème liquide entière sur le dos d'une cuillère de bar pour former un chapeau immaculé.",
      "Déguster d'un seul coup pour sentir le contraste chaud-froid."
    ],
    "garnish": "Grain de café torréfié déposé sur la crème.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/rvyvxs1473482359.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 127,
    "name": "Coup de Foudre",
    "subtitle": "L'Onde de Choc Glaciale & Épicée",
    "description": "Un shot vif qui réveille instantanément l'assemblée : gin aromatique, Get 31 à la menthe blanche arctique et un kick secret de Tabasco ou de gingembre pressé.",
    "category": "Shooters",
    "flavorProfile": "Arctique/Épicé",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 26.0,
    "ingredients": [
      {
        "name": "Gin",
        "details": "Gin sec London Dry",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Get 31",
        "details": "Liqueur de menthe blanche",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Tabasco",
        "details": "Piment rouge vif",
        "amountCl": 0.1,
        "measureDescription": "1 goutte",
        "isGarnish": false,
        "iconType": "grain"
      }
    ],
    "steps": [
      "Frapper le Gin et le Get 31 au shaker avec des glaçons pendant 8 secondes.",
      "Verser dans un verre à shooter.",
      "Ajouter une unique goutte de Tabasco au centre : le frisson glacé est suivi d'une agréable chaleur épicée."
    ],
    "garnish": "Feuille de menthe givrée.",
    "glassware": "Verre Shooter glacé",
    "shakeSeconds": 8,
    "rating": 4.6,
    "imageUrl": "https://images.unsplash.com/photo-1575023782549-62ca0d244b39?auto=format&fit=crop&w=800&q=80",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 128,
    "name": "Melon Ball Shooter",
    "subtitle": "L'Éclat Fluo & Fruité",
    "description": "Le shot star des pistes de danse : une couleur vert émeraude fluorescente inimitable, combinant le parfum envoûtant de melon vert japonais, la fraîcheur du jus d'ananas et la netteté de la vodka.",
    "category": "Shooters",
    "flavorProfile": "Exotique/Sucré",
    "prepTimeMinutes": 2,
    "difficulty": "Facile",
    "alcoholPercentage": 17.0,
    "ingredients": [
      {
        "name": "Midori",
        "details": "Liqueur de melon vert japonais",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Jus d'ananas",
        "details": "Pur jus pressé",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Verser le Midori, la vodka et le jus d'ananas dans un shaker plein de glace.",
      "Shaker avec énergie pendant 8 secondes.",
      "Passer au tamis et verser dans un shooter pour une mousse onctueuse en surface."
    ],
    "garnish": "Bille de melon ou tranche fine d'ananas.",
    "glassware": "Verre Shooter fluo",
    "shakeSeconds": 8,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/tpupvr1478251697.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 129,
    "name": "Fireball Shot",
    "subtitle": "La Flamme Cannelle & Pomme",
    "description": "Le grand classique qui met le feu à la piste : whisky chauffé aux notes intenses de cannelle épicée et de liqueur de pomme verte acidulée style Manzana.",
    "category": "Shooters",
    "flavorProfile": "Cannelle/Chaud",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 27.0,
    "ingredients": [
      {
        "name": "Whisky à la cannelle",
        "details": "Style Fireball",
        "amountCl": 2.5,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Manzana",
        "details": "Liqueur de pomme verte acidulée",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Sirop de canne",
        "details": "Léger liant sucré",
        "amountCl": 0.5,
        "measureDescription": "1 trait",
        "isGarnish": false,
        "iconType": "water_drop"
      }
    ],
    "steps": [
      "Verser le whisky à la cannelle, la Manzana et le trait de sirop de canne dans le shaker avec des glaçons.",
      "Frapper 6 secondes pour rafraîchir sans diluer.",
      "Filtrer dans le shooter et savourer la sensation épicée."
    ],
    "garnish": "Bord givré au sucre à la cannelle.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 6,
    "rating": 4.7,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/yqwuwu1441248116.jpg",
    "isFavorite": false,
    "vibeTag": "Clubbing & Electro"
  },
  {
    "id": 130,
    "name": "Woo Woo Shooter",
    "subtitle": "Le Shot Rubis Festif",
    "description": "La version shot du célèbre cocktail festif : une robe rouge rubis pétillante associant vodka soyeuse, liqueur de pêche gorgée de soleil et jus de canneberge acidulé.",
    "category": "Shooters",
    "flavorProfile": "Fruité/Acidulé",
    "prepTimeMinutes": 1,
    "difficulty": "Facile",
    "alcoholPercentage": 18.0,
    "ingredients": [
      {
        "name": "Vodka",
        "details": "Vodka blanche pure",
        "amountCl": 2.0,
        "measureDescription": "1/2 shooter",
        "isGarnish": false,
        "iconType": "liquor"
      },
      {
        "name": "Liqueur de pêche",
        "details": "Pêche blanche parfumée",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "invert_colors"
      },
      {
        "name": "Jus de cranberry",
        "details": "Canneberge acidulée",
        "amountCl": 1.5,
        "measureDescription": "1/3 shooter",
        "isGarnish": false,
        "iconType": "nutrition"
      }
    ],
    "steps": [
      "Dans un shaker garni de glaçons, verser la vodka, la liqueur de pêche et le jus de cranberry.",
      "Shaker vivement pendant 6 secondes.",
      "Verser dans le shooter et déguster bien frais entre amis !",
      "Boire cul-sec en levant son verre au groupe."
    ],
    "garnish": "Quartier fin de pêche ou canneberge fraîche.",
    "glassware": "Verre Shooter",
    "shakeSeconds": 6,
    "rating": 4.8,
    "imageUrl": "https://www.thecocktaildb.com/images/media/drink/7p607y1504735343.jpg",
    "isFavorite": false,
    "vibeTag": "Soirée Étudiante"
  }
];
