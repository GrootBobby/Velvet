export type CategoryFilter = 'Tous' | 'Classiques' | 'Cocktails' | 'Shooters' | 'Mocktails' | 'Favoris';

export type MainTab = 'bar' | 'catalog' | 'party' | 'profile';

export interface RecipeIngredient {
  name: string;
  details: string;
  amountCl: number;
  measureDescription: string;
  isGarnish: boolean;
  iconType: string;
}

export interface Cocktail {
  id: number;
  name: string;
  subtitle: string;
  description: string;
  category: string;
  flavorProfile: string;
  prepTimeMinutes: number;
  difficulty: string;
  alcoholPercentage: number;
  ingredients: RecipeIngredient[];
  steps: string[];
  garnish: string;
  glassware: string;
  shakeSeconds: number;
  rating: number;
  imageUrl: string;
  isFavorite: boolean;
  vibeTag: string;
}

export interface InventoryIngredient {
  name: string;
  brandOrDetail: string;
  category: 'Spiritueux' | 'Liqueurs' | 'Jus & Fruits' | 'Sirops & Sucres' | 'Épices & Décos' | 'Sodas & Eaux';
  tag: string;
  isOwned: boolean;
}

export interface UserProfile {
  userName: string;
  levelTitle: string;
  xp: number;
  level: number;
  cocktailsShakenCount: number;
  badges: Badge[];
}

export interface Badge {
  id: string;
  title: string;
  description: string;
  icon: string;
  unlocked: boolean;
  unlockedAt?: string;
}

export interface UndercoverPlayer {
  id: string;
  name: string;
  role: 'CITIZEN' | 'UNDERCOVER' | 'MR_WHITE';
  secretWord: string;
  isEliminated: boolean;
  votesReceived: number;
  avatarSeed: number;
}
