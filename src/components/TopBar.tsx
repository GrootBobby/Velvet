import React, { useState, useEffect } from 'react';
import { GlassWater, Wifi, WifiOff, Activity, Trophy } from 'lucide-react';
import { UserProfile } from '../types';

interface TopBarProps {
  userProfile: UserProfile | null;
  onOpenBacCalculator: () => void;
  onNavigateToProfile: () => void;
}

export const TopBar: React.FC<TopBarProps> = ({
  userProfile,
  onOpenBacCalculator,
  onNavigateToProfile,
}) => {
  const [isOnline, setIsOnline] = useState<boolean>(
    typeof navigator !== 'undefined' ? navigator.onLine : true
  );

  useEffect(() => {
    const handleOnline = () => setIsOnline(true);
    const handleOffline = () => setIsOnline(false);

    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);

    return () => {
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, []);

  return (
    <header className="sticky top-0 z-40 bg-velvet-surface/90 backdrop-blur-md border-b border-velvet-glass-border px-4 py-3">
      <div className="max-w-5xl mx-auto flex items-center justify-between">
        {/* Brand */}
        <div className="flex items-center space-x-2.5">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-velvet-primary to-velvet-secondary flex items-center justify-center shadow-neon-magenta">
            <GlassWater className="w-5 h-5 text-velvet-on-primary" />
          </div>
          <div>
            <h1 className="font-heading font-bold text-lg leading-tight tracking-wide text-transparent bg-clip-text bg-gradient-to-r from-velvet-primary via-velvet-secondary to-velvet-tertiary">
              Velvet & Shake
            </h1>
            <p className="text-[10px] text-velvet-text-muted uppercase tracking-widest font-medium">
              Speakeasy Bar & Party
            </p>
          </div>
        </div>

        {/* Actions & Status */}
        <div className="flex items-center space-x-2">
          {/* Online/Offline Status Indicator */}
          <div
            title={isOnline ? 'En ligne (IA disponible)' : 'Mode Hors-Ligne (PWA active)'}
            className={`px-2 py-1 rounded-full text-[11px] font-semibold flex items-center space-x-1.5 transition-colors border ${
              isOnline
                ? 'bg-emerald-950/40 text-emerald-400 border-emerald-800/50'
                : 'bg-amber-950/40 text-amber-400 border-amber-800/50 animate-pulse'
            }`}
          >
            {isOnline ? (
              <>
                <Wifi className="w-3 h-3" />
                <span className="hidden sm:inline">En ligne</span>
              </>
            ) : (
              <>
                <WifiOff className="w-3 h-3" />
                <span>Hors-ligne</span>
              </>
            )}
          </div>

          {/* BAC / Prevention Button */}
          <button
            onClick={onOpenBacCalculator}
            title="Calculateur d'alcoolémie préventif"
            className="p-2 rounded-xl bg-velvet-surface-low border border-velvet-glass-border text-velvet-tertiary hover:bg-velvet-surface-high transition-colors flex items-center space-x-1 text-xs font-semibold"
          >
            <Activity className="w-4 h-4 text-velvet-tertiary" />
            <span className="hidden md:inline">Alcoolémie</span>
          </button>

          {/* User Profile Badge */}
          <button
            onClick={onNavigateToProfile}
            className="flex items-center space-x-1.5 pl-2 pr-2.5 py-1 rounded-xl bg-velvet-surface-low border border-velvet-primary/30 hover:border-velvet-primary transition-all text-left"
          >
            <div className="w-6 h-6 rounded-full bg-gradient-to-br from-velvet-primary to-velvet-secondary flex items-center justify-center text-[10px] font-bold text-velvet-on-primary">
              <Trophy className="w-3.5 h-3.5" />
            </div>
            <div className="text-left">
              <span className="text-[10px] block leading-none text-velvet-primary font-bold">
                Niv. {userProfile?.level || 1}
              </span>
            </div>
          </button>
        </div>
      </div>
    </header>
  );
};
