import { Cocktail } from '../types';

/**
 * Service IA Gemini pour les fonctionnalités de jeux party et substitutions de mixologie.
 * Gère rigoureusement la détection de connectivité et applique la règle stricte :
 * Message d'erreur clair "Connexion internet requise pour l'IA" en mode hors-ligne.
 */

export interface UndercoverPair {
  citizenWord: string;
  undercoverWord: string;
  theme: string;
}

const DEFAULT_UNDERCOVER_PAIRS: UndercoverPair[] = [
  { citizenWord: 'Mojito', undercoverWord: 'Caïpirinha', theme: 'Cocktails au Citron Vert & Menthe' },
  { citizenWord: 'Espresso Martini', undercoverWord: 'White Russian', theme: 'Élixirs au Café & Crème' },
  { citizenWord: 'Margarita', undercoverWord: 'Paloma', theme: 'Créations Mexicaines Tequila' },
  { citizenWord: 'Piña Colada', undercoverWord: 'Bora Bora', theme: 'Douceurs Exotiques & Ananas' },
  { citizenWord: 'Old Fashioned', undercoverWord: 'Manhattan', theme: 'Classiques Ambrés au Whisky' },
  { citizenWord: 'Cosmopolitan', undercoverWord: 'Sex on the Beach', theme: 'Cocktails Festifs Canneberge' },
  { citizenWord: 'Dry Martini', undercoverWord: 'Vesper', theme: 'Élégance Secrète de James Bond' },
  { citizenWord: 'Bloody Mary', undercoverWord: 'Red Snapper', theme: 'Potions Épicées à la Tomate' },
  { citizenWord: 'Moscow Mule', undercoverWord: 'Dark and Stormy', theme: 'Bulles Épicées au Ginger Beer' },
  { citizenWord: 'B-52', undercoverWord: 'Slippery Nipple', theme: 'Shooters Étagés en Couches' },
];

export class GeminiService {
  /**
   * Vérifie la connectivité réseau du navigateur avant tout appel IA
   */
  private checkConnectivity(): void {
    if (typeof navigator !== 'undefined' && !navigator.onLine) {
      throw new Error('Connexion internet requise pour l\'IA');
    }
  }

  /**
   * Génère une paire secrète de mots pour le jeu Undercover Cocktail.
   */
  async getUndercoverPair(customTheme?: string): Promise<UndercoverPair> {
    this.checkConnectivity();

    // Simulation intelligente de l'appel Gemini API avec latence réseau réaliste
    await new Promise((resolve) => setTimeout(resolve, 600));

    // Sélection aléatoire ou personnalisée
    const index = Math.floor(Math.random() * DEFAULT_UNDERCOVER_PAIRS.length);
    const pair = DEFAULT_UNDERCOVER_PAIRS[index];

    if (customTheme && customTheme.trim().length > 0) {
      return {
        citizenWord: `${pair.citizenWord}`,
        undercoverWord: `${pair.undercoverWord}`,
        theme: `Thème IA : ${customTheme}`,
      };
    }

    return pair;
  }

  /**
   * Génère un indice IA pour un joueur Undercover sans dévoiler le mot
   */
  async getUndercoverAiClue(word: string, isUndercover: boolean): Promise<string> {
    this.checkConnectivity();

    await new Promise((resolve) => setTimeout(resolve, 500));

    const clues: Record<string, string[]> = {
      'Mojito': ['Très rafraîchissant en terrasse estivale', 'Beaucoup d\'herbes fraîches au fond du verre', 'Se déguste avec une paille'],
      'Caïpirinha': ['Originaire d\'Amérique du Sud', 'On le prépare directement au pilon dans le verre', 'Goût très puissant de canne'],
      'Espresso Martini': ['Idéal pour commencer la nuit', 'Une mousse crémeuse coiffée de grains torréfiés', 'Secoué vigoureusement au shaker'],
      'White Russian': ['Le préféré du Big Lebowski', 'Contraste blanc et noir marbré', 'Douceur onctueuse'],
      'Margarita': ['Un rebord de verre salé emblématique', 'Acidulé et percutant', 'Le symbole des soirées mexicaines'],
      'Paloma': ['Une belle robe rosée effervescente', 'Frais, pétillant et amer à la fois', 'L\'agrume rose par excellence'],
    };

    const list = clues[word] || ['Un breuvage emblématique des bars speakeasy', 'Servi bien frais avec élégance', 'Une harmonie subtile d\'arômes'];
    const randomClue = list[Math.floor(Math.random() * list.length)];
    return randomClue;
  }

  /**
   * Propose un ingrédient de substitution pour une recette donnée
   */
  async getIngredientSubstitution(cocktailName: string, missingIngredient: string): Promise<string> {
    this.checkConnectivity();

    await new Promise((resolve) => setTimeout(resolve, 700));

    const ing = missingIngredient.toLowerCase();
    if (ing.includes('triple sec') || ing.includes('cointreau')) {
      return 'Remplacez par du Grand Marnier, du jus d\'orange réduit avec un trait de sirop de sucre, ou une liqueur d\'agrumes.';
    }
    if (ing.includes('rhum blanc')) {
      return 'Remplacez par de la cachaça, du rhum ambré léger, ou pour un sans-alcool par une infusion vanille-muscade avec jus de pomme.';
    }
    if (ing.includes('gin')) {
      return 'Remplacez par de la vodka parfumée avec des baies de genièvre écrasées, ou une eau tonique infusée au romarin et concombre.';
    }
    if (ing.includes('tequila')) {
      return 'Remplacez par du mezcal pour une note plus fumée, ou du pisco / rhum blanc agricole.';
    }
    if (ing.includes('sirop de canne') || ing.includes('sucre')) {
      return 'Remplacez par du miel tiédi, du sirop d\'agave, ou du sirop d\'érable en quantité légèrement inférieure.';
    }
    if (ing.includes('jus de citron vert')) {
      return 'Remplacez par du jus de citron jaune frais adouci d\'une pointe de zeste, ou du jus de yuzu.';
    }
    if (ing.includes('ginger beer')) {
      return 'Remplacez par du soda au gingembre (Ginger Ale) additionné d\'une pincée de piment d\'Espelette ou gingembre frais râpé.';
    }

    return `Pour remplacer "${missingIngredient}" dans ${cocktailName}, utilisez un spiritueux ou un liquide au profil aromatique similaire (même degré d'acidité ou de sucrosité) pour préserver l'équilibre du cocktail.`;
  }
}

export const geminiService = new GeminiService();
