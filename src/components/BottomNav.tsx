import React from 'react';
import { Wine, BookOpen, Sparkles, User } from 'lucide-react';
import { MainTab } from '../types';

interface BottomNavProps {
  currentTab: MainTab;
  onTabChange: (tab: MainTab) => void;
  craftableCount?: number;
}

export const BottomNav: React.FC<BottomNavProps> = ({
  currentTab,
  onTabChange,
  craftableCount = 0,
}) => {
  const tabs = [
    {
      id: 'bar' as MainTab,
      label: 'Mon Bar',
      icon: Wine,
      badge: craftableCount > 0 ? `${craftableCount}` : undefined,
    },
    {
      id: 'catalog' as MainTab,
      label: 'Recettes',
      icon: BookOpen,
    },
    {
      id: 'party' as MainTab,
      label: 'Jeux Party',
      icon: Sparkles,
      highlight: true,
    },
    {
      id: 'profile' as MainTab,
      label: 'Profil',
      icon: User,
    },
  ];

  return (
    <nav className="fixed bottom-0 left-0 right-0 z-40 bg-velvet-surface-lowest/95 backdrop-blur-lg border-t border-velvet-glass-border safe-area-pb">
      <div className="max-w-md mx-auto grid grid-cols-4 px-2 py-2">
        {tabs.map((tab) => {
          const Icon = tab.icon;
          const isActive = currentTab === tab.id;

          return (
            <button
              key={tab.id}
              onClick={() => onTabChange(tab.id)}
              className={`relative flex flex-col items-center justify-center py-1.5 px-1 rounded-xl transition-all duration-200 ${
                isActive
                  ? 'text-velvet-primary font-bold'
                  : 'text-velvet-text-muted hover:text-velvet-text font-medium'
              }`}
            >
              {/* Active Glow Pill */}
              {isActive && (
                <div className="absolute inset-x-2 -top-1 h-0.5 bg-gradient-to-r from-velvet-primary to-velvet-secondary rounded-full shadow-neon-magenta" />
              )}

              <div className="relative">
                <Icon
                  className={`w-5 h-5 transition-transform duration-200 ${
                    isActive ? 'scale-110 text-velvet-primary' : 'text-velvet-outline'
                  }`}
                />
                {tab.badge && (
                  <span className="absolute -top-1.5 -right-2 px-1 py-0.2 bg-velvet-tertiary text-velvet-surface-lowest text-[9px] font-extrabold rounded-full min-w-[14px] text-center">
                    {tab.badge}
                  </span>
                )}
              </div>

              <span className="text-[11px] mt-1 tracking-tight truncate max-w-[70px]">
                {tab.label}
              </span>
            </button>
          );
        })}
      </div>
    </nav>
  );
};
