import React, { useState } from 'react';
import { Heart, Star, Clock, GlassWater } from 'lucide-react';
import { Cocktail } from '../types';

interface CocktailCardProps {
  cocktail: Cocktail;
  isFavorite: boolean;
  onToggleFavorite: (e: React.MouseEvent, id: number) => void;
  onClick: (cocktail: Cocktail) => void;
}

export const CocktailCard: React.FC<CocktailCardProps> = ({
  cocktail,
  isFavorite,
  onToggleFavorite,
  onClick,
}) => {
  const [imageError, setImageError] = useState(false);
  const [imageLoaded, setImageLoaded] = useState(false);

  const isMocktail = cocktail.alcoholPercentage === 0;

  return (
    <div
      onClick={() => onClick(cocktail)}
      className="group relative rounded-2xl overflow-hidden bg-velvet-surface-low border border-velvet-glass-border hover:border-velvet-primary/50 transition-all duration-300 cursor-pointer shadow-lg hover:shadow-neon-magenta/20 flex flex-col aspect-[0.85]"
    >
      {/* Cocktail Image with Fallback Placeholder */}
      <div className="relative w-full h-full overflow-hidden bg-velvet-surface-lowest">
        {!imageError && cocktail.imageUrl ? (
          <>
            {!imageLoaded && (
              <div className="absolute inset-0 flex items-center justify-center bg-velvet-surface-lowest animate-pulse">
                <GlassWater className="w-8 h-8 text-velvet-primary/30 animate-spin" />
              </div>
            )}
            <img
              src={cocktail.imageUrl}
              alt={cocktail.name}
              loading="lazy"
              onLoad={() => setImageLoaded(true)}
              onError={() => setImageError(true)}
              className={`w-full h-full object-cover transition-transform duration-500 group-hover:scale-105 ${
                imageLoaded ? 'opacity-100' : 'opacity-0'
              }`}
            />
          </>
        ) : (
          <div className="w-full h-full flex flex-col items-center justify-center bg-velvet-surface-lowest p-4 text-center">
            <GlassWater className="w-10 h-10 text-velvet-primary/40 mb-2" />
            <span className="text-[11px] text-velvet-text-muted font-medium line-clamp-1">
              {cocktail.name}
            </span>
          </div>
        )}

        {/* Gradient Scrim Overlay */}
        <div className="absolute inset-0 bg-gradient-to-t from-velvet-surface via-velvet-surface/60 to-transparent pointer-events-none" />

        {/* Top Badges: Category / Alcohol & Favorite Heart */}
        <div className="absolute top-2.5 inset-x-2.5 flex items-center justify-between z-10">
          <div className="flex flex-wrap gap-1">
            <span
              className={`px-2 py-0.5 rounded-full text-[10px] font-bold tracking-wider uppercase backdrop-blur-md shadow-sm ${
                isMocktail
                  ? 'bg-emerald-500/80 text-white'
                  : cocktail.category === 'Shooters'
                  ? 'bg-amber-500/80 text-white'
                  : 'bg-velvet-primary-container/80 text-white'
              }`}
            >
              {isMocktail ? '0% Mocktail' : cocktail.category}
            </span>
          </div>

          <button
            onClick={(e) => onToggleFavorite(e, cocktail.id)}
            className={`p-2 rounded-full backdrop-blur-md transition-transform duration-200 active:scale-75 ${
              isFavorite
                ? 'bg-velvet-primary text-velvet-on-primary shadow-neon-magenta'
                : 'bg-velvet-surface-lowest/70 text-velvet-text-muted hover:text-velvet-primary'
            }`}
            title={isFavorite ? 'Retirer des favoris' : 'Ajouter aux favoris'}
          >
            <Heart className={`w-3.5 h-3.5 ${isFavorite ? 'fill-current' : ''}`} />
          </button>
        </div>

        {/* Bottom Content Area */}
        <div className="absolute bottom-0 inset-x-0 p-3 z-10 flex flex-col justify-end">
          <div className="flex items-center space-x-1.5 text-amber-400 text-xs font-semibold mb-1">
            <Star className="w-3.5 h-3.5 fill-current" />
            <span>{cocktail.rating.toFixed(1)}</span>
            <span className="text-velvet-outline text-[11px]">• {cocktail.flavorProfile}</span>
          </div>

          <h3 className="font-heading font-bold text-sm text-velvet-text line-clamp-1 group-hover:text-velvet-primary transition-colors">
            {cocktail.name}
          </h3>

          <p className="text-[11px] text-velvet-text-muted line-clamp-1 mt-0.5">
            {cocktail.subtitle || cocktail.description}
          </p>

          <div className="flex items-center justify-between mt-2 pt-2 border-t border-white/10 text-[10px] text-velvet-outline">
            <div className="flex items-center space-x-1">
              <Clock className="w-3 h-3 text-velvet-tertiary" />
              <span>{cocktail.prepTimeMinutes} min</span>
            </div>
            <span className="font-semibold text-velvet-secondary">
              {cocktail.alcoholPercentage > 0 ? `${cocktail.alcoholPercentage}°` : '0% alc.'}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};
