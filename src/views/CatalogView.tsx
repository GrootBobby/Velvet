import React, { useState, useMemo } from 'react';
import { Search, X, SlidersHorizontal, GlassWater } from 'lucide-react';
import { Cocktail, CategoryFilter } from '../types';
import { CocktailCard } from '../components/CocktailCard';

interface CatalogViewProps {
  cocktails: Cocktail[];
  favoriteIds: number[];
  onToggleFavorite: (id: number) => void;
  onSelectCocktail: (cocktail: Cocktail) => void;
}

const FILTERS: CategoryFilter[] = [
  'Tous',
  'Classiques',
  'Cocktails',
  'Shooters',
  'Mocktails',
  'Favoris',
];

export const CatalogView: React.FC<CatalogViewProps> = ({
  cocktails,
  favoriteIds,
  onToggleFavorite,
  onSelectCocktail,
}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [activeFilter, setActiveFilter] = useState<CategoryFilter>('Tous');
  const [sortBy, setSortBy] = useState<'rating' | 'prepTime' | 'alcohol'>('rating');

  const favoriteSet = useMemo(() => new Set(favoriteIds), [favoriteIds]);

  const filteredCocktails = useMemo(() => {
    return cocktails
      .filter((cocktail) => {
        // Filter by category or favorites
        if (activeFilter === 'Favoris') {
          if (!favoriteSet.has(cocktail.id)) return false;
        } else if (activeFilter === 'Mocktails') {
          if (cocktail.alcoholPercentage > 0 && cocktail.category !== 'Mocktails') return false;
        } else if (activeFilter === 'Shooters') {
          if (cocktail.category !== 'Shooters') return false;
        } else if (activeFilter === 'Classiques') {
          // Classiques are standard recipes (e.g. rating >= 4.8 or prepTime <= 3)
          if (cocktail.rating < 4.8 && cocktail.id > 40) return false;
        } else if (activeFilter === 'Cocktails') {
          if (cocktail.category !== 'Cocktails' && cocktail.alcoholPercentage === 0) return false;
        }

        // Search query (name, ingredient, flavor, subtitle)
        if (searchQuery.trim().length > 0) {
          const q = searchQuery.toLowerCase().trim();
          const matchesName = cocktail.name.toLowerCase().includes(q);
          const matchesSubtitle = (cocktail.subtitle || '').toLowerCase().includes(q);
          const matchesFlavor = (cocktail.flavorProfile || '').toLowerCase().includes(q);
          const matchesIngredient = cocktail.ingredients.some((ing) =>
            ing.name.toLowerCase().includes(q)
          );
          if (!matchesName && !matchesSubtitle && !matchesFlavor && !matchesIngredient) {
            return false;
          }
        }

        return true;
      })
      .sort((a, b) => {
        if (sortBy === 'rating') return b.rating - a.rating;
        if (sortBy === 'alcohol') return b.alcoholPercentage - a.alcoholPercentage;
        if (sortBy === 'prepTime') return a.prepTimeMinutes - b.prepTimeMinutes;
        return 0;
      });
  }, [cocktails, activeFilter, searchQuery, favoriteSet, sortBy]);

  return (
    <div className="space-y-4 pb-24 px-4 pt-3 max-w-5xl mx-auto">
      {/* Top Header / Subtitle */}
      <div>
        <h2 className="font-heading font-black text-xl text-velvet-text">
          Catalogue Speakeasy ({filteredCocktails.length})
        </h2>
        <p className="text-xs text-velvet-text-muted">
          130 créations de bar, shooters étagés et mocktails détox.
        </p>
      </div>

      {/* Search Bar */}
      <div className="relative">
        <Search className="w-4 h-4 absolute left-3.5 top-1/2 -translate-y-1/2 text-velvet-outline" />
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Rechercher un cocktail, rhum, citron, menthe..."
          className="w-full pl-10 pr-10 py-2.5 rounded-xl bg-velvet-surface-low border border-velvet-glass-border text-xs text-velvet-text placeholder-velvet-outline focus:outline-none focus:border-velvet-primary transition-colors shadow-sm"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery('')}
            className="absolute right-3 top-1/2 -translate-y-1/2 p-1 text-velvet-outline hover:text-velvet-text"
          >
            <X className="w-3.5 h-3.5" />
          </button>
        )}
      </div>

      {/* Filter Tabs (Tous, Classiques, Cocktails, Shooters, Mocktails, Favoris) */}
      <div className="flex space-x-1.5 overflow-x-auto pb-1 scrollbar-none">
        {FILTERS.map((f) => {
          const isActive = activeFilter === f;
          return (
            <button
              key={f}
              onClick={() => setActiveFilter(f)}
              className={`px-3.5 py-1.5 rounded-xl text-xs font-bold whitespace-nowrap transition-all border ${
                isActive
                  ? 'bg-velvet-primary text-velvet-on-primary border-velvet-primary shadow-neon-magenta scale-105'
                  : 'bg-velvet-surface-low text-velvet-text-muted border-velvet-glass-border hover:bg-velvet-surface-high'
              }`}
            >
              {f === 'Favoris' ? `❤️ ${f} (${favoriteIds.length})` : f}
            </button>
          );
        })}
      </div>

      {/* Sort selection bar */}
      <div className="flex items-center justify-between text-xs text-velvet-outline px-1">
        <span>Trier par :</span>
        <div className="flex space-x-2">
          <button
            onClick={() => setSortBy('rating')}
            className={`font-semibold ${sortBy === 'rating' ? 'text-velvet-primary underline' : 'hover:text-velvet-text'}`}
          >
            Note ★
          </button>
          <span>•</span>
          <button
            onClick={() => setSortBy('alcohol')}
            className={`font-semibold ${sortBy === 'alcohol' ? 'text-velvet-secondary underline' : 'hover:text-velvet-text'}`}
          >
            Degré d'alcool
          </button>
          <span>•</span>
          <button
            onClick={() => setSortBy('prepTime')}
            className={`font-semibold ${sortBy === 'prepTime' ? 'text-velvet-tertiary underline' : 'hover:text-velvet-text'}`}
          >
            Rapidité
          </button>
        </div>
      </div>

      {/* Cocktails Responsive Grid */}
      {filteredCocktails.length > 0 ? (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 sm:gap-4">
          {filteredCocktails.map((cocktail) => (
            <CocktailCard
              key={cocktail.id}
              cocktail={cocktail}
              isFavorite={favoriteSet.has(cocktail.id)}
              onToggleFavorite={(e, id) => {
                e.stopPropagation();
                onToggleFavorite(id);
              }}
              onClick={onSelectCocktail}
            />
          ))}
        </div>
      ) : (
        <div className="py-16 text-center bg-velvet-surface-low/50 rounded-2xl border border-dashed border-white/10 p-6">
          <GlassWater className="w-12 h-12 text-velvet-outline mx-auto mb-3 opacity-30" />
          <h3 className="font-heading font-bold text-base text-velvet-text mb-1">
            Aucun cocktail trouvé
          </h3>
          <p className="text-xs text-velvet-text-muted max-w-sm mx-auto">
            Aucune recette ne correspond à votre filtre "{activeFilter}" ou votre recherche "{searchQuery}".
          </p>
          <button
            onClick={() => {
              setActiveFilter('Tous');
              setSearchQuery('');
            }}
            className="mt-4 px-4 py-2 rounded-xl bg-velvet-surface-high text-xs font-bold text-velvet-primary border border-velvet-primary/30 hover:border-velvet-primary transition-colors"
          >
            Réinitialiser les filtres
          </button>
        </div>
      )}
    </div>
  );
};
