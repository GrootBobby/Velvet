import React, { useState } from 'react';
import { ArrowLeft, Heart, Star, Clock, GlassWater, Sparkles, AlertCircle, Music, Check, RefreshCw } from 'lucide-react';
import { Cocktail } from '../types';
import { ShakeTimer } from './ShakeTimer';
import { geminiService } from '../services/gemini';

interface DetailModalProps {
  cocktail: Cocktail | null;
  isFavorite: boolean;
  onClose: () => void;
  onToggleFavorite: (id: number) => void;
  onCocktailShaken: (id: number) => void;
}

export const DetailModal: React.FC<DetailModalProps> = ({
  cocktail,
  isFavorite,
  onClose,
  onToggleFavorite,
  onCocktailShaken,
}) => {
  if (!cocktail) return null;

  const [checkedIngredients, setCheckedIngredients] = useState<Record<string, boolean>>({});
  const [substitutionTarget, setSubstitutionTarget] = useState<string>('');
  const [substitutionResult, setSubstitutionResult] = useState<string | null>(null);
  const [substitutionLoading, setSubstitutionLoading] = useState(false);
  const [substitutionError, setSubstitutionError] = useState<string | null>(null);

  const toggleCheck = (name: string) => {
    setCheckedIngredients((prev) => ({ ...prev, [name]: !prev[name] }));
  };

  const handleAskSubstitution = async () => {
    if (!substitutionTarget) return;

    setSubstitutionLoading(true);
    setSubstitutionResult(null);
    setSubstitutionError(null);

    try {
      const advice = await geminiService.getIngredientSubstitution(
        cocktail.name,
        substitutionTarget
      );
      setSubstitutionResult(advice);
    } catch (err: any) {
      setSubstitutionError(err.message || "Connexion internet requise pour l'IA");
    } finally {
      setSubstitutionLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 overflow-y-auto bg-black/80 backdrop-blur-md flex justify-center animate-fade-in">
      <div className="relative w-full max-w-lg min-h-screen bg-velvet-surface text-velvet-text pb-20 shadow-2xl flex flex-col">
        {/* Top Floating Controls */}
        <div className="absolute top-4 inset-x-4 flex items-center justify-between z-30">
          <button
            onClick={onClose}
            className="p-2.5 rounded-full bg-velvet-surface-lowest/70 backdrop-blur-md text-velvet-text border border-white/10 hover:bg-velvet-surface-high transition-colors"
          >
            <ArrowLeft className="w-5 h-5" />
          </button>

          <button
            onClick={() => onToggleFavorite(cocktail.id)}
            className={`p-2.5 rounded-full backdrop-blur-md border border-white/10 transition-transform active:scale-75 ${
              isFavorite
                ? 'bg-velvet-primary text-velvet-on-primary shadow-neon-magenta'
                : 'bg-velvet-surface-lowest/70 text-velvet-text hover:text-velvet-primary'
            }`}
          >
            <Heart className={`w-5 h-5 ${isFavorite ? 'fill-current' : ''}`} />
          </button>
        </div>

        {/* Hero Visual */}
        <div className="relative w-full h-80 bg-velvet-surface-lowest overflow-hidden flex-shrink-0">
          {cocktail.imageUrl ? (
            <img
              src={cocktail.imageUrl}
              alt={cocktail.name}
              className="w-full h-full object-cover"
            />
          ) : (
            <div className="w-full h-full flex items-center justify-center">
              <GlassWater className="w-16 h-16 text-velvet-primary/30" />
            </div>
          )}

          {/* Speakeasy dark gradient overlay */}
          <div className="absolute inset-0 bg-gradient-to-t from-velvet-surface via-velvet-surface/40 to-transparent" />

          {/* Vibe Music Tag */}
          {cocktail.vibeTag && (
            <div className="absolute bottom-4 left-4 right-4 flex items-center space-x-2 bg-velvet-surface-low/80 backdrop-blur-md border border-velvet-primary/20 px-3 py-1.5 rounded-xl">
              <Music className="w-4 h-4 text-velvet-primary animate-pulse flex-shrink-0" />
              <span className="text-xs font-semibold text-velvet-secondary truncate">
                Ambiance : {cocktail.vibeTag}
              </span>
            </div>
          )}
        </div>

        {/* Recipe Content Body */}
        <div className="px-5 pt-2 flex flex-col space-y-6">
          {/* Header & Titles */}
          <div>
            <div className="flex items-center space-x-2 text-xs font-bold text-amber-400 mb-1">
              <Star className="w-4 h-4 fill-current" />
              <span>{cocktail.rating.toFixed(1)} / 5.0</span>
              <span className="text-velvet-outline">• {cocktail.category}</span>
            </div>

            <h1 className="font-heading font-black text-2xl text-velvet-text">
              {cocktail.name}
            </h1>
            <p className="text-sm font-medium text-velvet-primary mt-0.5">
              {cocktail.subtitle}
            </p>
            <p className="text-xs text-velvet-text-muted mt-2 leading-relaxed">
              {cocktail.description}
            </p>
          </div>

          {/* Metrics Pill Grid */}
          <div className="grid grid-cols-4 gap-2 text-center">
            <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-2.5">
              <Clock className="w-4 h-4 mx-auto text-velvet-tertiary mb-1" />
              <span className="block text-[10px] text-velvet-outline uppercase font-semibold">Temps</span>
              <span className="text-xs font-bold text-velvet-text">{cocktail.prepTimeMinutes} min</span>
            </div>

            <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-2.5">
              <GlassWater className="w-4 h-4 mx-auto text-velvet-primary mb-1" />
              <span className="block text-[10px] text-velvet-outline uppercase font-semibold">Alcool</span>
              <span className="text-xs font-bold text-velvet-secondary">
                {cocktail.alcoholPercentage > 0 ? `${cocktail.alcoholPercentage}%` : '0%'}
              </span>
            </div>

            <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-2.5">
              <Sparkles className="w-4 h-4 mx-auto text-velvet-secondary mb-1" />
              <span className="block text-[10px] text-velvet-outline uppercase font-semibold">Niveau</span>
              <span className="text-xs font-bold text-velvet-text">{cocktail.difficulty}</span>
            </div>

            <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-xl p-2.5">
              <span className="block text-sm mb-0.5">🍸</span>
              <span className="block text-[10px] text-velvet-outline uppercase font-semibold">Verre</span>
              <span className="text-[11px] font-bold text-velvet-text truncate block">{cocktail.glassware}</span>
            </div>
          </div>

          {/* Interactive Shake Timer */}
          <ShakeTimer
            initialSeconds={cocktail.shakeSeconds || 10}
            cocktailName={cocktail.name}
            onCompleted={() => onCocktailShaken(cocktail.id)}
          />

          {/* Ingrédients */}
          <div>
            <div className="flex items-center justify-between mb-3">
              <h3 className="font-heading font-bold text-base text-velvet-text flex items-center space-x-2">
                <span>Ingrédients Requis</span>
                <span className="text-xs px-2 py-0.5 rounded-full bg-velvet-surface-high text-velvet-secondary">
                  {cocktail.ingredients.length}
                </span>
              </h3>
              <span className="text-[11px] text-velvet-outline">Cochez pour préparer</span>
            </div>

            <div className="space-y-2">
              {cocktail.ingredients.map((ing, i) => {
                const isChecked = !!checkedIngredients[ing.name];
                return (
                  <div
                    key={i}
                    onClick={() => toggleCheck(ing.name)}
                    className={`flex items-center justify-between p-3 rounded-xl border transition-all cursor-pointer select-none ${
                      isChecked
                        ? 'bg-velvet-primary/10 border-velvet-primary/40 text-velvet-text'
                        : 'bg-velvet-surface-low border-velvet-glass-border hover:border-velvet-primary/20'
                    }`}
                  >
                    <div className="flex items-center space-x-3">
                      <div
                        className={`w-5 h-5 rounded-md flex items-center justify-center transition-colors border ${
                          isChecked
                            ? 'bg-velvet-primary border-velvet-primary text-velvet-on-primary'
                            : 'border-velvet-outline/40'
                        }`}
                      >
                        {isChecked && <Check className="w-3.5 h-3.5 stroke-[3]" />}
                      </div>

                      <div>
                        <span className={`text-sm font-semibold block ${isChecked ? 'line-through text-velvet-outline' : 'text-velvet-text'}`}>
                          {ing.name}
                        </span>
                        {ing.details && (
                          <span className="text-[11px] text-velvet-outline block">
                            {ing.details}
                          </span>
                        )}
                      </div>
                    </div>

                    <span className="text-xs font-bold text-velvet-secondary bg-velvet-surface-highest/60 px-2 py-1 rounded-lg">
                      {ing.measureDescription || `${ing.amountCl} cl`}
                    </span>
                  </div>
                );
              })}
            </div>
          </div>

          {/* Étapes de Préparation */}
          <div>
            <h3 className="font-heading font-bold text-base text-velvet-text mb-3">
              Étapes de Préparation
            </h3>

            <div className="space-y-3">
              {cocktail.steps.map((step, i) => (
                <div key={i} className="flex items-start space-x-3 bg-velvet-surface-low/60 p-3 rounded-xl border border-velvet-glass-border">
                  <span className="w-6 h-6 rounded-full bg-gradient-to-br from-velvet-primary to-velvet-secondary text-velvet-on-primary font-bold text-xs flex items-center justify-center flex-shrink-0 mt-0.5">
                    {i + 1}
                  </span>
                  <p className="text-xs leading-relaxed text-velvet-text-muted">
                    {step}
                  </p>
                </div>
              ))}
            </div>
          </div>

          {/* Garnish info */}
          {cocktail.garnish && (
            <div className="bg-velvet-surface-low p-3 rounded-xl border border-velvet-glass-border flex items-center space-x-2 text-xs">
              <span className="text-lg">🌿</span>
              <div>
                <span className="font-bold text-velvet-tertiary">Garniture recommandée : </span>
                <span className="text-velvet-text">{cocktail.garnish}</span>
              </div>
            </div>
          )}

          {/* Substitution IA Gemini */}
          <div className="bg-velvet-surface-container border border-velvet-primary/30 rounded-2xl p-4">
            <div className="flex items-center space-x-2 mb-2">
              <Sparkles className="w-4 h-4 text-velvet-primary animate-pulse" />
              <h4 className="font-heading font-bold text-sm text-velvet-primary">
                Assistant Remplacement IA (Gemini)
              </h4>
            </div>

            <p className="text-[11px] text-velvet-text-muted mb-3">
              Un ingrédient vous manque ? L'IA de Velvet Cocktail vous suggère une alternative équivalente pour garder l'harmonie des saveurs.
            </p>

            <div className="flex items-center space-x-2">
              <select
                value={substitutionTarget}
                onChange={(e) => setSubstitutionTarget(e.target.value)}
                className="flex-1 bg-velvet-surface-low border border-velvet-glass-border rounded-xl px-3 py-2 text-xs text-velvet-text focus:outline-none focus:border-velvet-primary"
              >
                <option value="">Sélectionnez un ingrédient manquant...</option>
                {cocktail.ingredients.map((ing, i) => (
                  <option key={i} value={ing.name}>
                    {ing.name}
                  </option>
                ))}
              </select>

              <button
                onClick={handleAskSubstitution}
                disabled={!substitutionTarget || substitutionLoading}
                className="px-3 py-2 rounded-xl bg-gradient-to-r from-velvet-primary to-velvet-secondary text-velvet-on-primary font-bold text-xs shadow-neon-magenta disabled:opacity-40 flex items-center space-x-1"
              >
                {substitutionLoading ? (
                  <RefreshCw className="w-3.5 h-3.5 animate-spin" />
                ) : (
                  <span>Demander</span>
                )}
              </button>
            </div>

            {/* Error Message (Hors-ligne) */}
            {substitutionError && (
              <div className="mt-3 p-3 rounded-xl bg-red-950/40 border border-red-500/40 text-red-300 text-xs flex items-center space-x-2">
                <AlertCircle className="w-4 h-4 flex-shrink-0 text-red-400" />
                <span className="font-medium">{substitutionError}</span>
              </div>
            )}

            {/* Success Advice */}
            {substitutionResult && (
              <div className="mt-3 p-3 rounded-xl bg-velvet-surface-low border border-velvet-primary/40 text-xs text-velvet-text">
                <span className="font-bold text-velvet-secondary block mb-1">
                  💡 Conseil Mixologue IA :
                </span>
                <p className="leading-relaxed text-velvet-text-muted">
                  {substitutionResult}
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
