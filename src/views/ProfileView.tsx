import React from 'react';
import { Trophy, Award, Heart, Wine, Download, Trash2, ShieldCheck, Sparkles } from 'lucide-react';
import { UserProfile, Cocktail, InventoryIngredient } from '../types';

interface ProfileViewProps {
  userProfile: UserProfile | null;
  favoriteCocktails: Cocktail[];
  inventory: InventoryIngredient[];
  onSelectCocktail: (cocktail: Cocktail) => void;
  onResetData: () => void;
}

export const ProfileView: React.FC<ProfileViewProps> = ({
  userProfile,
  favoriteCocktails,
  inventory,
  onSelectCocktail,
  onResetData,
}) => {
  const ownedCount = inventory.filter((i) => i.isOwned).length;

  const handleExportData = () => {
    const data = {
      profile: userProfile,
      ownedIngredients: inventory.filter((i) => i.isOwned).map((i) => i.name),
      favorites: favoriteCocktails.map((c) => c.name),
      exportedAt: new Date().toISOString(),
    };
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `velvet_cocktail_backup_${new Date().toISOString().slice(0, 10)}.json`;
    a.click();
    URL.revokeObjectURL(url);
  };

  return (
    <div className="space-y-6 pb-24 px-4 pt-3 max-w-5xl mx-auto text-velvet-text">
      {/* Profile Header Card */}
      <div className="relative overflow-hidden rounded-2xl bg-gradient-to-br from-velvet-surface-low via-velvet-surface-container to-velvet-surface-lowest border border-velvet-primary/30 p-5 shadow-2xl">
        <div className="flex items-center space-x-4">
          <div className="relative">
            <div className="w-16 h-16 rounded-2xl bg-gradient-to-br from-velvet-primary to-velvet-secondary flex items-center justify-center text-velvet-on-primary shadow-neon-magenta text-2xl font-bold">
              🍸
            </div>
            <span className="absolute -bottom-1 -right-1 px-1.5 py-0.2 bg-velvet-tertiary text-velvet-surface-lowest text-[10px] font-black rounded-full border border-velvet-surface">
              Niv. {userProfile?.level || 1}
            </span>
          </div>

          <div className="flex-1">
            <h2 className="font-heading font-black text-xl text-velvet-text">
              {userProfile?.userName || 'Maître Mixologue'}
            </h2>
            <p className="text-xs font-semibold text-velvet-primary">
              {userProfile?.levelTitle || 'Novice du Shaker'}
            </p>

            {/* XP Bar */}
            <div className="mt-2.5">
              <div className="w-full h-2 rounded-full bg-velvet-surface-highest overflow-hidden">
                <div
                  className="h-full bg-gradient-to-r from-velvet-primary to-velvet-tertiary rounded-full transition-all duration-500 shadow-neon-magenta"
                  style={{ width: `${Math.min(100, ((userProfile?.xp || 0) % 100))}%` }}
                />
              </div>
              <div className="flex justify-between text-[10px] text-velvet-outline mt-1 font-semibold">
                <span>{userProfile?.xp || 0} XP au total</span>
                <span>Prochain niveau : {((userProfile?.level || 1) * 100)} XP</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Stats Counter Bar */}
      <div className="grid grid-cols-3 gap-2.5 text-center">
        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-3">
          <Sparkles className="w-4 h-4 mx-auto text-velvet-primary mb-1" />
          <span className="text-lg font-heading font-black text-velvet-text">
            {userProfile?.cocktailsShakenCount || 0}
          </span>
          <span className="text-[10px] text-velvet-outline block uppercase font-semibold">
            Cocktails Secoués
          </span>
        </div>

        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-3">
          <Wine className="w-4 h-4 mx-auto text-velvet-secondary mb-1" />
          <span className="text-lg font-heading font-black text-velvet-secondary">
            {ownedCount}
          </span>
          <span className="text-[10px] text-velvet-outline block uppercase font-semibold">
            Ingrédients en Stock
          </span>
        </div>

        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-3">
          <Heart className="w-4 h-4 mx-auto text-velvet-tertiary mb-1 fill-velvet-tertiary" />
          <span className="text-lg font-heading font-black text-velvet-tertiary">
            {favoriteCocktails.length}
          </span>
          <span className="text-[10px] text-velvet-outline block uppercase font-semibold">
            Cocktails Favoris
          </span>
        </div>
      </div>

      {/* Badges & Succès Grid */}
      <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-4 shadow-md">
        <div className="flex items-center space-x-2 mb-3">
          <Award className="w-5 h-5 text-velvet-primary" />
          <h3 className="font-heading font-bold text-base text-velvet-text">
            Succès & Trophées Speakeasy
          </h3>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-3 gap-2.5">
          {userProfile?.badges.map((badge) => (
            <div
              key={badge.id}
              className={`p-3 rounded-xl border flex flex-col items-center text-center transition-all ${
                badge.unlocked
                  ? 'bg-velvet-primary/10 border-velvet-primary/40 text-velvet-text shadow-sm'
                  : 'bg-velvet-surface-highest/40 border-white/5 opacity-40'
              }`}
            >
              <span className="text-2xl mb-1">{badge.icon}</span>
              <span className="font-bold text-xs leading-tight mb-0.5">{badge.title}</span>
              <span className="text-[10px] text-velvet-outline leading-tight">{badge.description}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Favorites Preview */}
      {favoriteCocktails.length > 0 && (
        <div>
          <div className="flex items-center justify-between mb-3">
            <h3 className="font-heading font-bold text-base text-velvet-text flex items-center space-x-2">
              <Heart className="w-4 h-4 text-velvet-primary fill-velvet-primary" />
              <span>Mes Recettes Préférées ({favoriteCocktails.length})</span>
            </h3>
          </div>

          <div className="flex space-x-3 overflow-x-auto pb-2 scrollbar-none">
            {favoriteCocktails.map((c) => (
              <div
                key={c.id}
                onClick={() => onSelectCocktail(c)}
                className="flex-shrink-0 w-36 bg-velvet-surface-low border border-velvet-glass-border hover:border-velvet-primary/50 rounded-xl overflow-hidden cursor-pointer group transition-all"
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
                </div>
                <div className="p-2">
                  <h4 className="font-heading font-bold text-xs text-velvet-text truncate group-hover:text-velvet-primary">
                    {c.name}
                  </h4>
                  <span className="text-[10px] text-velvet-outline block truncate">
                    {c.prepTimeMinutes} min • {c.alcoholPercentage}% alc.
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* PWA & Offline Storage Controls */}
      <div className="bg-velvet-surface-container border border-white/10 rounded-2xl p-4 text-xs space-y-3">
        <div className="flex items-center space-x-2 text-emerald-400 font-bold">
          <ShieldCheck className="w-5 h-5" />
          <span>Persistance Locale & Mode Hors-Ligne (PWA)</span>
        </div>
        <p className="text-velvet-text-muted text-[11px] leading-relaxed">
          Toutes vos données (ingrédients possédés, favoris, historique d'expérience) sont sauvegardées localement dans votre navigateur via IndexedDB & Cache Service Worker. Elles restent consultables en permanence sans aucune connexion internet.
        </p>

        <div className="flex flex-wrap gap-2 pt-1">
          <button
            onClick={handleExportData}
            className="px-3.5 py-2 rounded-xl bg-velvet-surface-high hover:bg-velvet-surface-highest border border-white/10 text-velvet-text font-bold text-xs flex items-center space-x-1.5 transition-colors"
          >
            <Download className="w-3.5 h-3.5 text-velvet-secondary" />
            <span>Sauvegarder / Exporter les données (JSON)</span>
          </button>

          <button
            onClick={onResetData}
            className="px-3 py-2 rounded-xl bg-red-950/40 hover:bg-red-900/60 border border-red-500/30 text-red-300 font-bold text-xs flex items-center space-x-1.5 transition-colors"
          >
            <Trash2 className="w-3.5 h-3.5" />
            <span>Réinitialiser</span>
          </button>
        </div>
      </div>
    </div>
  );
};
