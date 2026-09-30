import React, { useState } from 'react';
import { X, AlertTriangle, ShieldCheck, Activity } from 'lucide-react';

interface BacCalculatorModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export const BacCalculatorModal: React.FC<BacCalculatorModalProps> = ({ isOpen, onClose }) => {
  if (!isOpen) return null;

  const [gender, setGender] = useState<'homme' | 'femme'>('homme');
  const [weightKg, setWeightKg] = useState<number>(70);
  const [drinksCount, setDrinksCount] = useState<number>(2);
  const [hoursElapsed, setHoursElapsed] = useState<number>(1.5);

  // Formule simplifiée de Widmark :
  // Alcool ingéré (g) = drinksCount * 10g (dose bar standard)
  // BAC = Alcool (g) / (Poids (kg) * r) - (0.015 * heures)
  // r = 0.68 pour homme, 0.55 pour femme
  const r = gender === 'homme' ? 0.68 : 0.55;
  const alcoholGrams = drinksCount * 10;
  const rawBac = alcoholGrams / (weightKg * r) - 0.15 * hoursElapsed;
  const bac = Math.max(0, parseFloat(rawBac.toFixed(2)));

  const isAboveLimit = bac >= 0.5;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-md animate-fade-in">
      <div className="relative w-full max-w-sm bg-velvet-surface border border-velvet-glass-border rounded-2xl p-5 shadow-2xl text-velvet-text">
        <div className="flex items-center justify-between pb-3 border-b border-white/10">
          <div className="flex items-center space-x-2">
            <Activity className="w-5 h-5 text-velvet-tertiary" />
            <h3 className="font-heading font-bold text-base text-velvet-text">
              Calculateur d'Alcoolémie (BAC)
            </h3>
          </div>
          <button onClick={onClose} className="p-1 rounded-lg text-velvet-outline hover:text-velvet-text">
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Estimation Display */}
        <div className={`mt-4 p-4 rounded-xl border text-center transition-all ${
          isAboveLimit
            ? 'bg-red-950/30 border-red-500/50 text-red-300'
            : 'bg-emerald-950/30 border-emerald-500/50 text-emerald-300'
        }`}>
          <span className="text-[11px] font-bold uppercase tracking-wider block">
            Taux d'alcoolémie estimé
          </span>
          <span className="text-4xl font-heading font-black tracking-tight my-1 block">
            {bac.toFixed(2)} <span className="text-lg font-normal">g/L</span>
          </span>
          <div className="flex items-center justify-center space-x-1.5 text-xs font-semibold mt-1">
            {isAboveLimit ? (
              <>
                <AlertTriangle className="w-4 h-4 text-red-400" />
                <span>Limite légale dépassée (0.50 g/L) — Ne conduisez pas !</span>
              </>
            ) : (
              <>
                <ShieldCheck className="w-4 h-4 text-emerald-400" />
                <span>Sous la limite légale standard (restez vigilant)</span>
              </>
            )}
          </div>
        </div>

        {/* Inputs */}
        <div className="space-y-3.5 mt-4 text-xs">
          <div>
            <label className="block text-velvet-text-muted font-semibold mb-1">Genre physiologique</label>
            <div className="grid grid-cols-2 gap-2">
              <button
                onClick={() => setGender('homme')}
                className={`py-2 rounded-xl font-bold border transition-colors ${
                  gender === 'homme'
                    ? 'bg-velvet-primary text-velvet-on-primary border-velvet-primary'
                    : 'bg-velvet-surface-low border-velvet-glass-border text-velvet-text'
                }`}
              >
                Homme (r = 0.68)
              </button>
              <button
                onClick={() => setGender('femme')}
                className={`py-2 rounded-xl font-bold border transition-colors ${
                  gender === 'femme'
                    ? 'bg-velvet-primary text-velvet-on-primary border-velvet-primary'
                    : 'bg-velvet-surface-low border-velvet-glass-border text-velvet-text'
                }`}
              >
                Femme (r = 0.55)
              </button>
            </div>
          </div>

          <div>
            <div className="flex justify-between text-velvet-text-muted font-semibold mb-1">
              <span>Poids corporel</span>
              <span className="text-velvet-secondary font-bold">{weightKg} kg</span>
            </div>
            <input
              type="range"
              min="45"
              max="130"
              value={weightKg}
              onChange={(e) => setWeightKg(Number(e.target.value))}
              className="w-full accent-velvet-primary"
            />
          </div>

          <div>
            <div className="flex justify-between text-velvet-text-muted font-semibold mb-1">
              <span>Verres consommés (doses bar)</span>
              <span className="text-velvet-primary font-bold">{drinksCount} verres</span>
            </div>
            <input
              type="range"
              min="0"
              max="12"
              value={drinksCount}
              onChange={(e) => setDrinksCount(Number(e.target.value))}
              className="w-full accent-velvet-primary"
            />
          </div>

          <div>
            <div className="flex justify-between text-velvet-text-muted font-semibold mb-1">
              <span>Temps écoulé depuis le début</span>
              <span className="text-velvet-tertiary font-bold">{hoursElapsed} heures</span>
            </div>
            <input
              type="range"
              min="0.5"
              max="8"
              step="0.5"
              value={hoursElapsed}
              onChange={(e) => setHoursElapsed(Number(e.target.value))}
              className="w-full accent-velvet-tertiary"
            />
          </div>
        </div>

        <button
          onClick={onClose}
          className="w-full mt-5 py-2.5 rounded-xl bg-velvet-surface-high hover:bg-velvet-surface-highest text-velvet-text font-bold text-xs border border-white/10 transition-colors"
        >
          Fermer
        </button>
      </div>
    </div>
  );
};
