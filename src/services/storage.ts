import { Cocktail, InventoryIngredient, UserProfile, Badge } from '../types';
import { COCKTAILS } from '../data/cocktails';

const DB_NAME = 'VelvetCocktailDB';
const DB_VERSION = 1;
const STORAGE_PREFIX = 'velvet_cocktail_';

// Initial Badges
export const INITIAL_BADGES: Badge[] = [
  { id: 'first_shake', title: 'Premier Shaker', description: 'A complété son premier cocktail au shaker', icon: '🍸', unlocked: false },
  { id: 'master_rhum', title: 'Maître du Rhum', description: 'Possède au moins 3 ingrédients à base de rhum', icon: '🏴‍☠️', unlocked: false },
  { id: 'party_starter', title: 'Roi de la Fête', description: 'A lancé une partie de jeux Party Undercover', icon: '🎉', unlocked: false },
  { id: 'curator', title: 'Collectionneur', description: 'A ajouté 5 cocktails en favoris', icon: '❤️', unlocked: false },
  { id: 'alchemist', title: 'Alchimiste No-Low', description: 'A testé une recette de mocktail sans alcool', icon: '🌿', unlocked: false },
  { id: 'level_5', title: 'Mixologue Confirmé', description: 'A atteint le Niveau 5', icon: '⭐', unlocked: false },
];

export function getLevelTitle(level: number): string {
  if (level >= 10) return 'Légende du Bar Speakeasy';
  if (level >= 7) return 'Chef Barman Virtuose';
  if (level >= 5) return 'Mixologue Émérite';
  if (level >= 3) return 'Alchimiste du Shaker';
  if (level >= 2) return 'Apprenti Bartender';
  return 'Novice du Shaker';
}

// Starter ingredients cochés par défaut pour accueillir l'utilisateur
const STARTER_OWNED = new Set([
  'rhum blanc', 'vodka', 'gin', 'tequila blanco', 'jus de citron jaune frais',
  'jus de citron vert', 'eau gazeuse', 'sirop de canne', 'sirop de sucre', 'menthe fraîche'
]);

function categorize(name: string, details: string, isGarnish: boolean): { category: InventoryIngredient['category']; tag: string } {
  const n = name.toLowerCase().trim();
  const d = details.toLowerCase().trim();

  if ((n.includes('sirop') || n.includes('sucre') || n.includes('cordial') || n.includes('miel') || n.includes('orgeat') || n.includes('grenadine')) && !n.includes('tomate')) {
    const tag = n.includes('sucre') ? 'Sucre' : n.includes('agave') ? 'Agave' : n.includes('orgeat') ? 'Orgeat' : n.includes('grenadine') ? 'Grenadine' : 'Sirop';
    return { category: 'Sirops & Sucres', tag };
  }

  if (n.includes('liqueur') || n.includes('curaçao') || n.includes('kahlúa') || n.includes('triple sec') || n.includes('cointreau') || n.includes('amaretto') || n.includes('baileys') || n.includes('sambuca') || n.includes('campari') || n.includes('aperol') || n.includes('vermouth') || n.includes('chambord') || n.includes('get 27') || n.includes('crème de') || n.includes('galliano') || n.includes('chartreuse')) {
    return { category: 'Liqueurs', tag: 'Liqueur' };
  }

  if (n.includes('rhum') || n.includes('vodka') || n.includes('gin') || n.includes('tequila') || n.includes('whiskey') || n.includes('bourbon') || n.includes('scotch') || n.includes('cognac') || n.includes('brandy') || n.includes('cachaça') || n.includes('pisco') || n.includes('mezcal') || n.includes('absinthe') || n.includes('champagne') || n.includes('prosecco')) {
    const tag = n.includes('rhum') ? 'Rhum' : n.includes('vodka') ? 'Vodka' : n.includes('gin') ? 'Gin' : n.includes('tequila') ? 'Tequila' : n.includes('whiskey') || n.includes('bourbon') ? 'Whisky' : 'Spiritueux';
    return { category: 'Spiritueux', tag };
  }

  if (n.includes('jus') || n.includes('purée') || n.includes('fraise') || n.includes('framboise') || n.includes('mûre') || n.includes('orange') || n.includes('citron') || n.includes('ananas') || n.includes('canneberge') || n.includes('cranberry') || n.includes('passion') || n.includes('pamplemousse') || n.includes('pomme') || n.includes('concombre')) {
    return { category: 'Jus & Fruits', tag: 'Fruit / Jus' };
  }

  if (n.includes('soda') || n.includes('eau gazeuse') || n.includes('tonic') || n.includes('ginger beer') || n.includes('ginger ale') || n.includes('cola') || n.includes('limonade') || n.includes('red bull')) {
    return { category: 'Sodas & Eaux', tag: 'Soft' };
  }

  return { category: 'Épices & Décos', tag: isGarnish ? 'Garniture' : 'Aromate' };
}

export function extractAllIngredients(): InventoryIngredient[] {
  const map = new Map<string, { name: string; details: string; isGarnish: boolean }>();
  for (const c of COCKTAILS) {
    for (const ing of c.ingredients) {
      const key = ing.name.trim();
      if (!map.has(key)) {
        map.set(key, { name: ing.name.trim(), details: ing.details, isGarnish: ing.isGarnish });
      }
    }
  }

  const list: InventoryIngredient[] = [];
  map.forEach((item) => {
    const { category, tag } = categorize(item.name, item.details, item.isGarnish);
    const isOwned = STARTER_OWNED.has(item.name.toLowerCase().trim());
    list.push({
      name: item.name,
      brandOrDetail: item.details || 'Essentiel cocktail',
      category,
      tag,
      isOwned,
    });
  });

  return list.sort((a, b) => a.name.localeCompare(b.name));
}

// ----------------------------------------------------
// IndexedDB + LocalStorage Offline Persistence Engine
// ----------------------------------------------------
class StorageService {
  private dbPromise: Promise<IDBDatabase | null>;

  constructor() {
    this.dbPromise = this.openDb();
  }

  private openDb(): Promise<IDBDatabase | null> {
    if (typeof window === 'undefined' || !window.indexedDB) {
      return Promise.resolve(null);
    }
    return new Promise((resolve) => {
      try {
        const req = indexedDB.open(DB_NAME, DB_VERSION);
        req.onupgradeneeded = (e) => {
          const db = (e.target as IDBOpenDBRequest).result;
          if (!db.objectStoreNames.contains('keyval')) {
            db.createObjectStore('keyval');
          }
        };
        req.onsuccess = () => resolve(req.result);
        req.onerror = () => resolve(null);
      } catch {
        resolve(null);
      }
    });
  }

  private async get<T>(key: string, defaultValue: T): Promise<T> {
    const db = await this.dbPromise;
    if (db) {
      return new Promise((resolve) => {
        try {
          const tx = db.transaction('keyval', 'readonly');
          const store = tx.objectStore('keyval');
          const req = store.get(key);
          req.onsuccess = () => resolve(req.result !== undefined ? req.result : defaultValue);
          req.onerror = () => resolve(this.getFallback(key, defaultValue));
        } catch {
          resolve(this.getFallback(key, defaultValue));
        }
      });
    }
    return this.getFallback(key, defaultValue);
  }

  private async set<T>(key: string, value: T): Promise<void> {
    const db = await this.dbPromise;
    this.setFallback(key, value);
    if (db) {
      new Promise<void>((resolve) => {
        try {
          const tx = db.transaction('keyval', 'readwrite');
          const store = tx.objectStore('keyval');
          store.put(value, key);
          tx.oncomplete = () => resolve();
          tx.onerror = () => resolve();
        } catch {
          resolve();
        }
      });
    }
  }

  private getFallback<T>(key: string, defaultValue: T): T {
    try {
      const item = localStorage.getItem(STORAGE_PREFIX + key);
      return item ? JSON.parse(item) : defaultValue;
    } catch {
      return defaultValue;
    }
  }

  private setFallback<T>(key: string, value: T): void {
    try {
      localStorage.setItem(STORAGE_PREFIX + key, JSON.stringify(value));
    } catch {
      // Ignored in restricted environments
    }
  }

  // --- Inventory Methods ---
  async getInventory(): Promise<InventoryIngredient[]> {
    const stored = await this.get<InventoryIngredient[]>('inventory', []);
    if (!stored || stored.length === 0) {
      const initial = extractAllIngredients();
      await this.set('inventory', initial);
      return initial;
    }
    return stored;
  }

  async saveInventory(inventory: InventoryIngredient[]): Promise<void> {
    await this.set('inventory', inventory);
  }

  async toggleIngredient(name: string): Promise<InventoryIngredient[]> {
    const inventory = await this.getInventory();
    const updated = inventory.map((item) =>
      item.name.toLowerCase().trim() === name.toLowerCase().trim()
        ? { ...item, isOwned: !item.isOwned }
        : item
    );
    await this.saveInventory(updated);
    return updated;
  }

  // --- Favorites Methods ---
  async getFavoriteIds(): Promise<number[]> {
    return this.get<number[]>('favorite_ids', [1, 5, 12, 18, 23]);
  }

  async toggleFavorite(id: number): Promise<boolean> {
    const ids = await this.getFavoriteIds();
    const set = new Set(ids);
    const isNowFav = !set.has(id);
    if (isNowFav) {
      set.add(id);
      await this.addXp(15, 'Ajout d’un cocktail en favori');
    } else {
      set.delete(id);
    }
    const updated = Array.from(set);
    await this.set('favorite_ids', updated);
    return isNowFav;
  }

  // --- Profile & XP Methods ---
  async getUserProfile(): Promise<UserProfile> {
    const defaultProfile: UserProfile = {
      userName: 'Maître Mixologue',
      levelTitle: 'Novice du Shaker',
      xp: 120,
      level: 1,
      cocktailsShakenCount: 0,
      badges: INITIAL_BADGES,
    };
    const profile = await this.get<UserProfile>('user_profile', defaultProfile);
    profile.levelTitle = getLevelTitle(profile.level);
    return profile;
  }

  async saveUserProfile(profile: UserProfile): Promise<void> {
    await this.set('user_profile', profile);
  }

  async addXp(
    amount: number,
    reason?: string
  ): Promise<{ newXp: number; levelUp: boolean; newLevel: number; newTitle: string }> {
    const profile = await this.getUserProfile();
    const oldLevel = profile.level;
    profile.xp += amount;

    // Formula: level = floor(xp / 100) + 1
    const newLevel = Math.max(1, Math.floor(profile.xp / 100) + 1);
    const levelUp = newLevel > oldLevel;
    profile.level = newLevel;
    profile.levelTitle = getLevelTitle(newLevel);

    if (levelUp && newLevel >= 5) {
      profile.badges = profile.badges.map((b) =>
        b.id === 'level_5' ? { ...b, unlocked: true, unlockedAt: new Date().toISOString() } : b
      );
    }

    await this.saveUserProfile(profile);
    return { newXp: profile.xp, levelUp, newLevel, newTitle: profile.levelTitle };
  }

  async recordCocktailShaken(cocktailId: number): Promise<{ xpGained: number; totalShaken: number }> {
    const profile = await this.getUserProfile();
    profile.cocktailsShakenCount = (profile.cocktailsShakenCount || 0) + 1;
    profile.badges = profile.badges.map((b) =>
      b.id === 'first_shake' ? { ...b, unlocked: true, unlockedAt: new Date().toISOString() } : b
    );
    await this.saveUserProfile(profile);
    await this.addXp(25, 'Cocktail secoué avec succès');
    return { xpGained: 25, totalShaken: profile.cocktailsShakenCount };
  }
}

export const storage = new StorageService();
