import React, { useState, useMemo } from 'react';
import { Wine, Search, Check, Sparkles, Trophy, ChevronRight, GlassWater } from 'lucide-react';
import { Cocktail, InventoryIngredient, UserProfile } from '../types';

interface BarViewProps {
  inventory: InventoryIngredient[];
  cocktails: Cocktail[];
  userProfile: UserProfile | null;
  onToggleIngredient: (name: string) => void;
  onSelectCocktail: (cocktail: Cocktail) => void;
  onNavigateToCatalog: () => void;
}

const CATEGORIES: Array<InventoryIngredient['category'] | 'Tous'> = [
  'Tous',
  'Spiritueux',
  'Liqueurs',
  'Jus & Fruits',
  'Sirops & Sucres',
  'Sodas & Eaux',
  'Épices & Décos',
];

export const BarView: React.FC<BarViewProps> = ({
  inventory,
  cocktails,
  userProfile,
  onToggleIngredient,
  onSelectCocktail,
  onNavigateToCatalog,
}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState<InventoryIngredient['category'] | 'Tous'>('Tous');

  // Owned ingredient names normalized
  const ownedNames = useMemo(() => {
    return new Set(
      inventory
        .filter((item) => item.isOwned)
        .map((item) => item.name.toLowerCase().trim())
    );
  }, [inventory]);

  // Cocktails craftable à 100% avec les ingrédients possédés
  const craftableCocktails = useMemo(() => {
    return cocktails.filter((cocktail) => {
      const required = cocktail.ingredients.filter((ing) => !ing.isGarnish);
      if (required.length === 0) return false;
      return required.every((ing) => {
        const name = ing.name.toLowerCase().trim();
        return Array.from(ownedNames).some(
          (owned) => owned.includes(name) || name.includes(owned)
        );
      });
    });
  }, [cocktails, ownedNames]);

  // Filtered ingredients
  const filteredIngredients = useMemo(() => {
    return inventory.filter((item) => {
      const matchesCategory =
        selectedCategory === 'Tous' || item.category === selectedCategory;
      const matchesSearch =
        searchQuery === '' ||
        item.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        item.brandOrDetail.toLowerCase().includes(searchQuery.toLowerCase());
      return matchesCategory && matchesSearch;
    });
  }, [inventory, selectedCategory, searchQuery]);

  const ownedCount = inventory.filter((i) => i.isOwned).length;

  return (
    <div className="space-y-6 pb-24 px-4 pt-3 max-w-5xl mx-auto">
      {/* Profile & XP Status Card */}
      <div className="relative overflow-hidden rounded-2xl bg-gradient-to-br from-velvet-surface-low via-velvet-surface-container to-velvet-surface-lowest border border-velvet-primary/30 p-4 shadow-xl">
        <div className="absolute top-0 right-0 w-32 h-32 bg-velvet-primary/10 rounded-full blur-2xl pointer-events-none" />

        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-velvet-primary to-velvet-secondary flex items-center justify-center shadow-neon-magenta text-velvet-on-primary">
              <Trophy className="w-6 h-6" />
            </div>
            <div>
              <span className="text-[10px] font-bold text-velvet-primary tracking-wider uppercase">
                Progression Mixologie
              </span>
              <h2 className="font-heading font-black text-lg text-velvet-text">
                Niveau {userProfile?.level || 1} — {userProfile?.levelTitle || 'Novice du Shaker'}
              </h2>
            </div>
          </div>

          <div className="text-right">
            <span className="text-xs font-black text-velvet-tertiary">
              {userProfile?.xp || 0} XP
            </span>
          </div>
        </div>

        {/* XP Progress Bar */}
        <div className="mt-3">
          <div className="w-full h-2 rounded-full bg-velvet-surface-highest overflow-hidden">
            <div
              className="h-full bg-gradient-to-r from-velvet-primary via-velvet-secondary to-velvet-tertiary rounded-full transition-all duration-500 shadow-neon-magenta"
              style={{ width: `${Math.min(100, ((userProfile?.xp || 0) % 100))}%` }}
            />
          </div>
          <div className="flex justify-between text-[10px] text-velvet-outline mt-1 font-medium">
            <span>+25 XP par cocktail secoué</span>
            <span>Objectif prochain niveau : {((userProfile?.level || 1) * 100)} XP</span>
          </div>
        </div>
      </div>

      {/* Craftable Cocktails Banner & Carousel */}
      <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-4 shadow-md">
        <div className="flex items-center justify-between mb-3">
          <div className="flex items-center space-x-2">
            <Sparkles className="w-5 h-5 text-velvet-tertiary animate-pulse" />
            <h3 className="font-heading font-bold text-base text-velvet-text">
              Cocktails Réalisables ({craftableCocktails.length})
            </h3>
          </div>
          <button
            onClick={onNavigateToCatalog}
            className="text-xs text-velvet-primary hover:text-velvet-secondary flex items-center space-x-1 font-semibold"
          >
            <span>Voir tout</span>
            <ChevronRight className="w-4 h-4" />
          </button>
        </div>

        <p className="text-xs text-velvet-text-muted mb-3">
          {craftableCocktails.length > 0
            ? `Vous avez tous les ingrédients nécessaires pour préparer ces ${craftableCocktails.length} recettes dès maintenant !`
            : "Cochez vos ingrédients en stock ci-dessous pour débloquer des recettes prêtes à l'emploi."}
        </p>

        {craftableCocktails.length > 0 ? (
          <div className="flex space-x-3 overflow-x-auto pb-2 scrollbar-none">
            {craftableCocktails.slice(0, 10).map((c) => (
              <div
                key={c.id}
                onClick={() => onSelectCocktail(c)}
                className="flex-shrink-0 w-36 bg-velvet-surface-lowest border border-velvet-glass-border hover:border-velvet-primary/50 rounded-xl overflow-hidden cursor-pointer group transition-all"
              >
                <div className="relative w-full h-24 bg-velvet-surface-high overflow-hidden">
                  <img
                    src={c.imageUrl}
                    alt={c.name}
                    className="w-full h-full object-cover group-hover:scale-105 transition-transform"
                    onError={(e) => {
                      (e.target as HTMLElement).style.display = 'none';
                    }}
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-velvet-surface-lowest to-transparent" />
                  <span className="absolute bottom-1 right-1.5 px-1.5 py-0.5 rounded text-[9px] font-bold bg-velvet-tertiary text-velvet-surface-lowest">
                    100% prêt
                  </span>
                </div>
                <div className="p-2">
                  <h4 className="font-heading font-bold text-xs text-velvet-text truncate group-hover:text-velvet-primary">
                    {c.name}
                  </h4>
                  <span className="text-[10px] text-velvet-outline block truncate">
                    {c.prepTimeMinutes} min • {c.category}
                  </span>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="py-6 text-center bg-velvet-surface-lowest/60 rounded-xl border border-dashed border-white/10">
            <GlassWater className="w-8 h-8 text-velvet-outline mx-auto mb-1.5 opacity-40" />
            <span className="text-xs text-velvet-outline block">
              Aucun cocktail réalisable à 100% avec le stock actuel.
            </span>
            <span className="text-[11px] text-velvet-tertiary font-semibold block mt-1">
              Activez des ingrédients ci-dessous pour voir les suggestions.
            </span>
          </div>
        )}
      </div>

      {/* Inventory Section Header & Counter */}
      <div>
        <div className="flex items-center justify-between mb-3">
          <div>
            <h3 className="font-heading font-bold text-lg text-velvet-text flex items-center space-x-2">
              <Wine className="w-5 h-5 text-velvet-primary" />
              <span>Mon Stock d'Ingrédients</span>
            </h3>
            <span className="text-xs text-velvet-text-muted">
              {ownedCount} ingrédient{ownedCount > 1 ? 's' : ''} possédé{ownedCount > 1 ? 's' : ''} sur {inventory.length}
            </span>
          </div>
        </div>

        {/* Search Input */}
        <div className="relative mb-3">
          <Search className="w-4 h-4 absolute left-3.5 top-1/2 -translate-y-1/2 text-velvet-outline" />
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Rechercher un ingrédient en stock..."
            className="w-full pl-10 pr-4 py-2.5 rounded-xl bg-velvet-surface-low border border-velvet-glass-border text-xs text-velvet-text placeholder-velvet-outline focus:outline-none focus:border-velvet-primary transition-colors"
          />
        </div>

        {/* Category Pills */}
        <div className="flex space-x-1.5 overflow-x-auto pb-2 scrollbar-none mb-3">
          {CATEGORIES.map((cat) => (
            <button
              key={cat}
              onClick={() => setSelectedCategory(cat)}
              className={`px-3 py-1.5 rounded-xl text-xs font-semibold whitespace-nowrap transition-colors border ${
                selectedCategory === cat
                  ? 'bg-velvet-primary text-velvet-on-primary border-velvet-primary shadow-neon-magenta'
                  : 'bg-velvet-surface-low text-velvet-text-muted border-velvet-glass-border hover:bg-velvet-surface-high'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>

        {/* Ingredient Checkbox Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2">
          {filteredIngredients.map((item) => (
            <div
              key={item.name}
              onClick={() => onToggleIngredient(item.name)}
              className={`flex items-center justify-between p-3 rounded-xl border transition-all cursor-pointer select-none ${
                item.isOwned
                  ? 'bg-velvet-primary/10 border-velvet-primary/40 text-velvet-text'
                  : 'bg-velvet-surface-low border-velvet-glass-border text-velvet-text-muted hover:border-velvet-primary/20'
              }`}
            >
              <div className="flex items-center space-x-2.5 truncate">
                <div
                  className={`w-5 h-5 rounded-md flex items-center justify-center transition-colors border flex-shrink-0 ${
                    item.isOwned
                      ? 'bg-velvet-primary border-velvet-primary text-velvet-on-primary shadow-neon-magenta'
                      : 'border-velvet-outline/40'
                  }`}
                >
                  {item.isOwned && <Check className="w-3.5 h-3.5 stroke-[3]" />}
                </div>
                <div className="truncate">
                  <span className={`text-xs font-bold block truncate ${item.isOwned ? 'text-velvet-text' : 'text-velvet-text-muted'}`}>
                    {item.name}
                  </span>
                  <span className="text-[10px] text-velvet-outline block truncate">
                    {item.brandOrDetail}
                  </span>
                </div>
              </div>

              <span className="text-[10px] px-2 py-0.5 rounded-md font-semibold bg-velvet-surface-highest/60 text-velvet-secondary flex-shrink-0 ml-2">
                {item.tag}
              </span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
