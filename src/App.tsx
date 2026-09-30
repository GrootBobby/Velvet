import React, { useState, useEffect, useMemo } from 'react';
import { MainTab, Cocktail, InventoryIngredient, UserProfile } from './types';
import { COCKTAILS } from './data/cocktails';
import { storage } from './services/storage';
import { TopBar } from './components/TopBar';
import { BottomNav } from './components/BottomNav';
import { DetailModal } from './components/DetailModal';
import { BacCalculatorModal } from './components/BacCalculatorModal';
import { BarView } from './views/BarView';
import { CatalogView } from './views/CatalogView';
import { PartyView } from './views/PartyView';
import { ProfileView } from './views/ProfileView';

export const App: React.FC = () => {
  const [currentTab, setCurrentTab] = useState<MainTab>('bar');
  const [inventory, setInventory] = useState<InventoryIngredient[]>([]);
  const [favoriteIds, setFavoriteIds] = useState<number[]>([]);
  const [userProfile, setUserProfile] = useState<UserProfile | null>(null);
  const [selectedCocktail, setSelectedCocktail] = useState<Cocktail | null>(null);
  const [isBacModalOpen, setIsBacModalOpen] = useState(false);
  const [isOnline, setIsOnline] = useState<boolean>(
    typeof navigator !== 'undefined' ? navigator.onLine : true
  );

  // Charger les données persistées au démarrage
  useEffect(() => {
    async function loadData() {
      const inv = await storage.getInventory();
      const favs = await storage.getFavoriteIds();
      const prof = await storage.getUserProfile();
      setInventory(inv);
      setFavoriteIds(favs);
      setUserProfile(prof);
    }
    loadData();

    const handleOnline = () => setIsOnline(true);
    const handleOffline = () => setIsOnline(false);

    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);

    return () => {
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, []);

  // Ingrédients possédés en minuscule
  const ownedNames = useMemo(() => {
    return new Set(
      inventory
        .filter((item) => item.isOwned)
        .map((item) => item.name.toLowerCase().trim())
    );
  }, [inventory]);

  // Nombre de cocktails réalisables à 100%
  const craftableCount = useMemo(() => {
    return COCKTAILS.filter((cocktail) => {
      const required = cocktail.ingredients.filter((ing) => !ing.isGarnish);
      if (required.length === 0) return false;
      return required.every((ing) => {
        const name = ing.name.toLowerCase().trim();
        return Array.from(ownedNames).some(
          (owned) => owned.includes(name) || name.includes(owned)
        );
      });
    }).length;
  }, [ownedNames]);

  const handleToggleIngredient = async (name: string) => {
    const updated = await storage.toggleIngredient(name);
    setInventory(updated);
  };

  const handleToggleFavorite = async (id: number) => {
    await storage.toggleFavorite(id);
    const updatedFavs = await storage.getFavoriteIds();
    const updatedProfile = await storage.getUserProfile();
    setFavoriteIds(updatedFavs);
    setUserProfile(updatedProfile);
  };

  const handleCocktailShaken = async (id: number) => {
    await storage.recordCocktailShaken(id);
    const updatedProfile = await storage.getUserProfile();
    setUserProfile(updatedProfile);
  };

  const handleGameCompleted = async (xpEarned: number) => {
    await storage.addXp(xpEarned, 'Partie Undercover complétée');
    const updatedProfile = await storage.getUserProfile();
    setUserProfile(updatedProfile);
  };

  const handleResetData = async () => {
    if (window.confirm('Voulez-vous vraiment réinitialiser vos données de bar locales ?')) {
      localStorage.clear();
      window.location.reload();
    }
  };

  const favoriteCocktails = useMemo(() => {
    const favSet = new Set(favoriteIds);
    return COCKTAILS.filter((c) => favSet.has(c.id));
  }, [favoriteIds]);

  return (
    <div className="min-h-screen bg-velvet-surface text-velvet-text flex flex-col antialiased selection:bg-velvet-primary selection:text-velvet-on-primary">
      {/* Top Bar Navigation */}
      <TopBar
        userProfile={userProfile}
        onOpenBacCalculator={() => setIsBacModalOpen(true)}
        onNavigateToProfile={() => setCurrentTab('profile')}
      />

      {/* Hors-Ligne Notification Bar */}
      {!isOnline && (
        <div className="bg-amber-950/80 border-b border-amber-600/40 text-amber-300 px-4 py-2 text-xs flex items-center justify-between z-30">
          <span>
            📶 <strong>Mode Hors-Ligne Actif :</strong> L'application, les 130 recettes et votre stock fonctionnent sans internet.
          </span>
        </div>
      )}

      {/* Main Content Area */}
      <main className="flex-1 w-full">
        {currentTab === 'bar' && (
          <BarView
            inventory={inventory}
            cocktails={COCKTAILS}
            userProfile={userProfile}
            onToggleIngredient={handleToggleIngredient}
            onSelectCocktail={setSelectedCocktail}
            onNavigateToCatalog={() => setCurrentTab('catalog')}
          />
        )}

        {currentTab === 'catalog' && (
          <CatalogView
            cocktails={COCKTAILS}
            favoriteIds={favoriteIds}
            onToggleFavorite={handleToggleFavorite}
            onSelectCocktail={setSelectedCocktail}
          />
        )}

        {currentTab === 'party' && (
          <PartyView onGameCompleted={handleGameCompleted} />
        )}

        {currentTab === 'profile' && (
          <ProfileView
            userProfile={userProfile}
            favoriteCocktails={favoriteCocktails}
            inventory={inventory}
            onSelectCocktail={setSelectedCocktail}
            onResetData={handleResetData}
          />
        )}
      </main>

      {/* Persistent Bottom Bar with 4 Tabs */}
      <BottomNav
        currentTab={currentTab}
        onTabChange={setCurrentTab}
        craftableCount={craftableCount}
      />

      {/* Cocktail Detail Modal */}
      <DetailModal
        cocktail={selectedCocktail}
        isFavorite={selectedCocktail ? favoriteIds.includes(selectedCocktail.id) : false}
        onClose={() => setSelectedCocktail(null)}
        onToggleFavorite={handleToggleFavorite}
        onCocktailShaken={handleCocktailShaken}
      />

      {/* Blood Alcohol Content Calculator Modal */}
      <BacCalculatorModal
        isOpen={isBacModalOpen}
        onClose={() => setIsBacModalOpen(false)}
      />
    </div>
  );
};
