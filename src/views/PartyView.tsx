import React, { useState, useEffect } from 'react';
import { Sparkles, Users, Eye, EyeOff, AlertCircle, Play, RotateCcw, Award, CheckCircle2, Bot } from 'lucide-react';
import confetti from 'canvas-confetti';
import { UndercoverPlayer } from '../types';
import { geminiService, UndercoverPair } from '../services/gemini';

interface PartyViewProps {
  onGameCompleted: (xpEarned: number) => void;
}

type GameStep = 'setup' | 'reveal' | 'debate' | 'vote' | 'result';

export const PartyView: React.FC<PartyViewProps> = ({ onGameCompleted }) => {
  const [step, setStep] = useState<GameStep>('setup');
  const [playerCount, setPlayerCount] = useState<number>(4);
  const [undercoverCount, setUndercoverCount] = useState<number>(1);
  const [includeMrWhite, setIncludeMrWhite] = useState<boolean>(true);
  const [customTheme, setCustomTheme] = useState<string>('');

  const [players, setPlayers] = useState<UndercoverPlayer[]>([]);
  const [currentRevealIndex, setCurrentRevealIndex] = useState<number>(0);
  const [isCardFlipped, setIsCardFlipped] = useState<boolean>(false);
  const [pair, setPair] = useState<UndercoverPair | null>(null);

  const [aiLoading, setAiLoading] = useState(false);
  const [aiError, setAiError] = useState<string | null>(null);
  const [aiClue, setAiClue] = useState<string | null>(null);

  const [debateTimeLeft, setDebateTimeLeft] = useState<number>(60);
  const [isTimerRunning, setIsTimerRunning] = useState<boolean>(false);

  // Initialisation des noms des joueurs
  const [playerNames, setPlayerNames] = useState<string[]>([
    'Joueur 1', 'Joueur 2', 'Joueur 3', 'Joueur 4', 'Joueur 5', 'Joueur 6'
  ]);

  const handleStartGame = async () => {
    setAiLoading(true);
    setAiError(null);

    try {
      // Appel au service Gemini avec détection automatique hors-ligne
      const generatedPair = await geminiService.getUndercoverPair(customTheme);
      setPair(generatedPair);

      // Attribution des rôles
      const names = playerNames.slice(0, playerCount);
      const indices = Array.from({ length: playerCount }, (_, i) => i);
      // Shuffle
      indices.sort(() => Math.random() - 0.5);

      const undercovers = new Set(indices.slice(0, undercoverCount));
      let mrWhiteIndex = -1;
      if (includeMrWhite && playerCount >= 4) {
        mrWhiteIndex = indices[undercoverCount];
      }

      const generatedPlayers: UndercoverPlayer[] = names.map((name, i) => {
        let role: UndercoverPlayer['role'] = 'CITIZEN';
        let word = generatedPair.citizenWord;

        if (undercovers.has(i)) {
          role = 'UNDERCOVER';
          word = generatedPair.undercoverWord;
        } else if (i === mrWhiteIndex) {
          role = 'MR_WHITE';
          word = '??? (Vous êtes Mr. White, bluffez sans mot secret !)';
        }

        return {
          id: `player_${i}`,
          name: name.trim() || `Joueur ${i + 1}`,
          role,
          secretWord: word,
          isEliminated: false,
          votesReceived: 0,
          avatarSeed: i + 1,
        };
      });

      setPlayers(generatedPlayers);
      setCurrentRevealIndex(0);
      setIsCardFlipped(false);
      setStep('reveal');
    } catch (err: any) {
      // Règle stricte : "Connexion internet requise pour l'IA"
      setAiError(err.message || "Connexion internet requise pour l'IA");
    } finally {
      setAiLoading(false);
    }
  };

  const handleNextReveal = () => {
    setIsCardFlipped(false);
    if (currentRevealIndex < players.length - 1) {
      setCurrentRevealIndex(currentRevealIndex + 1);
    } else {
      setStep('debate');
      setDebateTimeLeft(60);
      setIsTimerRunning(true);
    }
  };

  const handleRequestAiClue = async () => {
    setAiLoading(true);
    setAiError(null);
    try {
      const active = players.find((p) => !p.isEliminated);
      if (!active || !pair) return;
      const clue = await geminiService.getUndercoverAiClue(pair.citizenWord, false);
      setAiClue(clue);
    } catch (err: any) {
      setAiError(err.message || "Connexion internet requise pour l'IA");
    } finally {
      setAiLoading(false);
    }
  };

  const handleEliminate = (playerId: string) => {
    const updated = players.map((p) =>
      p.id === playerId ? { ...p, isEliminated: true } : p
    );
    setPlayers(updated);

    // Vérifier condition de fin
    const remainingUndercovers = updated.filter(
      (p) => !p.isEliminated && (p.role === 'UNDERCOVER' || p.role === 'MR_WHITE')
    ).length;
    const remainingCitizens = updated.filter(
      (p) => !p.isEliminated && p.role === 'CITIZEN'
    ).length;

    if (remainingUndercovers === 0 || remainingUndercovers >= remainingCitizens) {
      setStep('result');
      confetti({ particleCount: 80, spread: 70, origin: { y: 0.6 } });
      onGameCompleted(50);
    } else {
      setStep('debate');
      setDebateTimeLeft(60);
      setIsTimerRunning(true);
    }
  };

  return (
    <div className="space-y-6 pb-24 px-4 pt-3 max-w-lg mx-auto text-velvet-text">
      {/* Game Banner Header */}
      <div className="text-center relative">
        <div className="inline-flex p-3 rounded-2xl bg-gradient-to-br from-velvet-primary to-velvet-secondary shadow-neon-magenta text-velvet-on-primary mb-2">
          <Sparkles className="w-6 h-6 animate-pulse" />
        </div>
        <h2 className="font-heading font-black text-2xl text-velvet-text">
          Undercover Cocktail IA
        </h2>
        <p className="text-xs text-velvet-text-muted mt-0.5">
          Le jeu de bluff et de déduction mixologique par excellence.
        </p>
      </div>

      {/* OFFLINE AI ERROR NOTIFICATION BANNER */}
      {aiError && (
        <div className="p-3.5 rounded-xl bg-red-950/50 border border-red-500/50 text-red-200 text-xs flex items-center space-x-2.5 animate-shake">
          <AlertCircle className="w-5 h-5 text-red-400 flex-shrink-0" />
          <div className="flex-1">
            <span className="font-bold block">Erreur d'accès à l'IA :</span>
            <span>{aiError}</span>
          </div>
        </div>
      )}

      {/* STEP 1: SETUP */}
      {step === 'setup' && (
        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-5 space-y-4 shadow-xl">
          <div className="flex items-center space-x-2 border-b border-white/10 pb-3">
            <Users className="w-5 h-5 text-velvet-primary" />
            <h3 className="font-heading font-bold text-base text-velvet-text">
              Configuration de la Partie
            </h3>
          </div>

          <div>
            <div className="flex justify-between text-xs font-semibold mb-1">
              <span>Nombre de Joueurs</span>
              <span className="text-velvet-primary font-bold">{playerCount} joueurs</span>
            </div>
            <input
              type="range"
              min="3"
              max="8"
              value={playerCount}
              onChange={(e) => setPlayerCount(Number(e.target.value))}
              className="w-full accent-velvet-primary"
            />
          </div>

          <div>
            <div className="flex justify-between text-xs font-semibold mb-1">
              <span>Nombre d'Infiltrés (Undercovers)</span>
              <span className="text-velvet-secondary font-bold">{undercoverCount}</span>
            </div>
            <div className="grid grid-cols-2 gap-2">
              <button
                onClick={() => setUndercoverCount(1)}
                className={`py-2 rounded-xl text-xs font-bold border transition-colors ${
                  undercoverCount === 1
                    ? 'bg-velvet-secondary text-velvet-on-secondary border-velvet-secondary'
                    : 'bg-velvet-surface-high border-velvet-glass-border'
                }`}
              >
                1 Infiltré
              </button>
              <button
                onClick={() => setUndercoverCount(2)}
                disabled={playerCount < 5}
                className={`py-2 rounded-xl text-xs font-bold border transition-colors disabled:opacity-30 ${
                  undercoverCount === 2
                    ? 'bg-velvet-secondary text-velvet-on-secondary border-velvet-secondary'
                    : 'bg-velvet-surface-high border-velvet-glass-border'
                }`}
              >
                2 Infiltrés (min. 5j)
              </button>
            </div>
          </div>

          <div className="flex items-center justify-between p-3 rounded-xl bg-velvet-surface-high/60 border border-white/5">
            <div>
              <span className="text-xs font-bold block text-velvet-text">Inclure Mr. White</span>
              <span className="text-[10px] text-velvet-outline block">
                N'a aucun mot secret et doit bluffer totalement
              </span>
            </div>
            <input
              type="checkbox"
              checked={includeMrWhite}
              onChange={(e) => setIncludeMrWhite(e.target.checked)}
              className="w-4 h-4 accent-velvet-tertiary"
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-velvet-text-muted block mb-1">
              Thème personnalisé (optionnel pour l'IA)
            </label>
            <input
              type="text"
              value={customTheme}
              onChange={(e) => setCustomTheme(e.target.value)}
              placeholder="Ex: Cocktails d'été, Shooters épicés..."
              className="w-full px-3.5 py-2.5 rounded-xl bg-velvet-surface-high border border-velvet-glass-border text-xs text-velvet-text focus:outline-none focus:border-velvet-primary"
            />
          </div>

          <button
            onClick={handleStartGame}
            disabled={aiLoading}
            className="w-full py-3 rounded-xl bg-gradient-to-r from-velvet-primary to-velvet-secondary text-velvet-on-primary font-heading font-black text-sm shadow-neon-magenta flex items-center justify-center space-x-2 transition-transform active:scale-95 disabled:opacity-50"
          >
            {aiLoading ? (
              <span>Génération IA en cours...</span>
            ) : (
              <>
                <Bot className="w-4 h-4" />
                <span>Générer la Partie via l'IA Gemini</span>
              </>
            )}
          </button>
        </div>
      )}

      {/* STEP 2: SECRET REVEAL (PASS-THE-PHONE) */}
      {step === 'reveal' && players[currentRevealIndex] && (
        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-6 text-center space-y-5 shadow-2xl">
          <span className="text-xs font-bold text-velvet-outline uppercase tracking-wider block">
            Joueur {currentRevealIndex + 1} sur {players.length}
          </span>

          <h3 className="font-heading font-black text-xl text-velvet-text">
            Passez le téléphone à <span className="text-velvet-primary">{players[currentRevealIndex].name}</span>
          </h3>

          <div
            onClick={() => setIsCardFlipped(!isCardFlipped)}
            className={`min-h-[180px] p-6 rounded-2xl border-2 cursor-pointer transition-all duration-300 flex flex-col items-center justify-center ${
              isCardFlipped
                ? 'bg-gradient-to-br from-velvet-surface-container to-velvet-surface-lowest border-velvet-primary shadow-neon-magenta'
                : 'bg-velvet-surface-highest/60 border-dashed border-velvet-outline/40 hover:border-velvet-primary'
            }`}
          >
            {isCardFlipped ? (
              <>
                <Eye className="w-6 h-6 text-velvet-primary mb-2" />
                <span className="text-[11px] text-velvet-outline uppercase font-bold tracking-wider">
                  Votre mot secret de cocktail :
                </span>
                <span className="text-2xl font-heading font-black text-velvet-primary my-2 block">
                  {players[currentRevealIndex].secretWord}
                </span>
                <span className="text-[10px] text-velvet-text-muted">
                  Mémorisez-le discrètement et touchez pour cacher.
                </span>
              </>
            ) : (
              <>
                <EyeOff className="w-8 h-8 text-velvet-outline mb-2 animate-bounce" />
                <span className="text-sm font-bold text-velvet-text">
                  Touchez pour révéler votre rôle
                </span>
                <span className="text-[10px] text-velvet-outline mt-1">
                  Assurez-vous qu'aucun autre regard n'est posé sur l'écran
                </span>
              </>
            )}
          </div>

          <button
            onClick={handleNextReveal}
            disabled={!isCardFlipped}
            className="w-full py-3 rounded-xl bg-velvet-primary text-velvet-on-primary font-bold text-xs shadow-neon-magenta disabled:opacity-40"
          >
            {currentRevealIndex < players.length - 1
              ? 'Joueur Suivant ➔'
              : 'Commencer le Débat Mixologique ➔'}
          </button>
        </div>
      )}

      {/* STEP 3: DEBATE */}
      {step === 'debate' && (
        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-5 space-y-4">
          <div className="flex items-center justify-between border-b border-white/10 pb-3">
            <h3 className="font-heading font-bold text-base text-velvet-text">
              Tour de Table & Discussion
            </h3>
            <span className="text-xs text-velvet-tertiary font-bold">
              {debateTimeLeft}s restantes
            </span>
          </div>

          <p className="text-xs text-velvet-text-muted leading-relaxed">
            Chaque joueur énonce à tour de rôle un mot ou une description subtile de son cocktail sans dévoiler le nom exact !
          </p>

          <div className="grid grid-cols-2 gap-2 my-2">
            {players.map((p) => (
              <div
                key={p.id}
                className={`p-2.5 rounded-xl border flex items-center justify-between text-xs ${
                  p.isEliminated
                    ? 'bg-red-950/20 border-red-900/40 text-velvet-outline line-through opacity-50'
                    : 'bg-velvet-surface-high border-velvet-glass-border text-velvet-text'
                }`}
              >
                <span className="font-bold truncate">{p.name}</span>
                {p.isEliminated && <span className="text-[10px] text-red-400">Éliminé</span>}
              </div>
            ))}
          </div>

          {/* AI Clue Button */}
          <div className="bg-velvet-surface-container p-3 rounded-xl border border-velvet-primary/30 text-xs">
            <div className="flex items-center justify-between">
              <span className="font-bold text-velvet-secondary flex items-center space-x-1">
                <Bot className="w-3.5 h-3.5" />
                <span>Besoin d'un indice IA ?</span>
              </span>
              <button
                onClick={handleRequestAiClue}
                disabled={aiLoading}
                className="px-2.5 py-1 rounded-lg bg-velvet-primary text-velvet-on-primary font-bold text-[10px]"
              >
                {aiLoading ? 'Génération...' : 'Demander à Gemini'}
              </button>
            </div>
            {aiClue && (
              <p className="mt-2 text-velvet-text-muted italic border-t border-white/5 pt-1.5">
                "{aiClue}"
              </p>
            )}
          </div>

          <button
            onClick={() => setStep('vote')}
            className="w-full py-3 rounded-xl bg-gradient-to-r from-velvet-primary to-velvet-secondary text-velvet-on-primary font-bold text-xs shadow-neon-magenta"
          >
            Passer au Vote d'Élimination ➔
          </button>
        </div>
      )}

      {/* STEP 4: VOTE */}
      {step === 'vote' && (
        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-5 space-y-4">
          <h3 className="font-heading font-bold text-base text-velvet-text text-center">
            Votez pour éliminer un suspect
          </h3>
          <p className="text-xs text-velvet-text-muted text-center">
            Sélectionnez le joueur que vous suspectez d'être l'Undercover ou Mr. White.
          </p>

          <div className="space-y-2">
            {players
              .filter((p) => !p.isEliminated)
              .map((p) => (
                <button
                  key={p.id}
                  onClick={() => handleEliminate(p.id)}
                  className="w-full p-3 rounded-xl bg-velvet-surface-high hover:bg-red-950/40 border border-velvet-glass-border hover:border-red-500/50 flex items-center justify-between text-xs font-bold transition-all text-left group"
                >
                  <span className="text-velvet-text group-hover:text-red-300">{p.name}</span>
                  <span className="text-red-400 opacity-0 group-hover:opacity-100 transition-opacity">
                    Éliminer ✕
                  </span>
                </button>
              ))}
          </div>
        </div>
      )}

      {/* STEP 5: RESULT */}
      {step === 'result' && (
        <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-6 text-center space-y-4 shadow-2xl">
          <Award className="w-12 h-12 text-velvet-tertiary mx-auto animate-bounce" />
          <h3 className="font-heading font-black text-2xl text-velvet-text">
            Fin de la Partie !
          </h3>

          <div className="bg-emerald-950/40 border border-emerald-500/40 p-3 rounded-xl text-emerald-300 text-xs font-semibold">
            🎉 +50 XP Party Games remportés pour tous les joueurs !
          </div>

          <div className="text-left bg-velvet-surface-highest/60 p-4 rounded-xl border border-white/5 space-y-2 text-xs">
            <span className="font-bold text-velvet-secondary block mb-1">
              Révélation des Mots Secrets :
            </span>
            <div className="flex justify-between">
              <span className="text-velvet-outline">Citoyens :</span>
              <span className="font-bold text-velvet-primary">{pair?.citizenWord}</span>
            </div>
            <div className="flex justify-between">
              <span className="text-velvet-outline">Undercover :</span>
              <span className="font-bold text-velvet-secondary">{pair?.undercoverWord}</span>
            </div>
          </div>

          <button
            onClick={() => setStep('setup')}
            className="w-full py-3 rounded-xl bg-velvet-primary text-velvet-on-primary font-bold text-xs shadow-neon-magenta flex items-center justify-center space-x-2"
          >
            <RotateCcw className="w-4 h-4" />
            <span>Rejouer une Nouvelle Partie</span>
          </button>
        </div>
      )}
    </div>
  );
};
