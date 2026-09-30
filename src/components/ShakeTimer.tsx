import React, { useState, useEffect, useRef } from 'react';
import { Play, Pause, RotateCcw, Sparkles, CheckCircle2 } from 'lucide-react';
import confetti from 'canvas-confetti';

interface ShakeTimerProps {
  initialSeconds: number;
  cocktailName: string;
  onCompleted: () => void;
}

export const ShakeTimer: React.FC<ShakeTimerProps> = ({
  initialSeconds,
  cocktailName,
  onCompleted,
}) => {
  const duration = initialSeconds > 0 ? initialSeconds : 10;
  const [timeLeft, setTimeLeft] = useState(duration);
  const [isRunning, setIsRunning] = useState(false);
  const [isFinished, setIsFinished] = useState(false);
  const timerRef = useRef<number | null>(null);

  useEffect(() => {
    setTimeLeft(duration);
    setIsRunning(false);
    setIsFinished(false);
  }, [duration]);

  useEffect(() => {
    if (isRunning && timeLeft > 0) {
      timerRef.current = window.setInterval(() => {
        setTimeLeft((prev) => {
          if (prev <= 1) {
            handleComplete();
            return 0;
          }
          // Vibration haptique sur mobile pendant le shake
          if (typeof navigator !== 'undefined' && 'vibrate' in navigator) {
            navigator.vibrate(40);
          }
          return prev - 1;
        });
      }, 1000);
    } else {
      if (timerRef.current) clearInterval(timerRef.current);
    }

    return () => {
      if (timerRef.current) clearInterval(timerRef.current);
    };
  }, [isRunning, timeLeft]);

  const handleComplete = () => {
    setIsRunning(false);
    setIsFinished(true);

    // Audio synthèse Web Audio API
    try {
      const audioCtx = new (window.AudioContext || (window as any).webkitAudioContext)();
      const osc = audioCtx.createOscillator();
      const gain = audioCtx.createGain();
      osc.type = 'triangle';
      osc.frequency.setValueAtTime(587.33, audioCtx.currentTime); // D5
      osc.frequency.exponentialRampToValueAtTime(880, audioCtx.currentTime + 0.3); // A5
      gain.gain.setValueAtTime(0.3, audioCtx.currentTime);
      gain.gain.exponentialRampToValueAtTime(0.01, audioCtx.currentTime + 0.5);
      osc.connect(gain);
      gain.connect(audioCtx.destination);
      osc.start();
      osc.stop(audioCtx.currentTime + 0.5);
    } catch {
      // Ignoré si audio désactivé
    }

    // Vibration de succès
    if (typeof navigator !== 'undefined' && 'vibrate' in navigator) {
      navigator.vibrate([100, 60, 200]);
    }

    // Confetti
    confetti({
      particleCount: 60,
      spread: 60,
      origin: { y: 0.7 },
      colors: ['#FBABFF', '#E0B6FF', '#FFB95F'],
    });

    onCompleted();
  };

  const handleToggle = () => {
    if (isFinished) {
      setTimeLeft(duration);
      setIsFinished(false);
      setIsRunning(true);
    } else {
      setIsRunning(!isRunning);
    }
  };

  const handleReset = () => {
    setIsRunning(false);
    setIsFinished(false);
    setTimeLeft(duration);
  };

  const progress = ((duration - timeLeft) / duration) * 100;

  return (
    <div className="bg-velvet-surface-low border border-velvet-glass-border rounded-2xl p-4 flex flex-col items-center shadow-lg relative overflow-hidden">
      {/* Background glow when shaking */}
      {isRunning && (
        <div className="absolute inset-0 bg-gradient-to-r from-velvet-primary/10 via-velvet-secondary/15 to-velvet-tertiary/10 animate-pulse pointer-events-none" />
      )}

      <div className="flex items-center justify-between w-full mb-3">
        <div className="flex items-center space-x-2">
          <Sparkles className="w-4 h-4 text-velvet-primary animate-spin" />
          <h4 className="font-heading font-bold text-sm text-velvet-text">
            Chronomètre Shaker Speakeasy
          </h4>
        </div>
        <span className="text-xs text-velvet-secondary font-semibold">
          {duration}s recommandées
        </span>
      </div>

      {/* Circular Timer Visual */}
      <div className="relative w-32 h-32 flex items-center justify-center my-2">
        <svg className="w-full h-full -rotate-90 transform" viewBox="0 0 100 100">
          <circle
            cx="50"
            cy="50"
            r="42"
            className="stroke-velvet-surface-highest"
            strokeWidth="8"
            fill="transparent"
          />
          <circle
            cx="50"
            cy="50"
            r="42"
            className="stroke-velvet-primary transition-all duration-300"
            strokeWidth="8"
            strokeDasharray={264}
            strokeDashoffset={264 - (264 * progress) / 100}
            strokeLinecap="round"
            fill="transparent"
          />
        </svg>

        <div className="absolute inset-0 flex flex-col items-center justify-center">
          {isFinished ? (
            <div className="flex flex-col items-center animate-bounce">
              <CheckCircle2 className="w-8 h-8 text-emerald-400" />
              <span className="text-[10px] font-bold text-emerald-400 uppercase mt-0.5">Parfait !</span>
            </div>
          ) : (
            <>
              <span className={`text-3xl font-heading font-black tracking-tight ${isRunning ? 'animate-bounce text-velvet-primary' : 'text-velvet-text'}`}>
                {timeLeft}s
              </span>
              <span className="text-[10px] uppercase font-bold text-velvet-text-muted tracking-wider">
                {isRunning ? 'Secouez !' : 'Prêt'}
              </span>
            </>
          )}
        </div>
      </div>

      {isFinished && (
        <div className="bg-emerald-950/40 border border-emerald-500/30 text-emerald-300 text-xs px-3 py-1.5 rounded-xl font-semibold my-2 text-center animate-fade-in">
          🎉 +25 XP Mixologie remportés pour {cocktailName} !
        </div>
      )}

      {/* Timer Controls */}
      <div className="flex items-center space-x-3 mt-2">
        <button
          onClick={handleToggle}
          className={`px-5 py-2 rounded-xl font-bold text-xs flex items-center space-x-1.5 shadow-md transition-all active:scale-95 ${
            isFinished
              ? 'bg-velvet-primary text-velvet-on-primary hover:bg-velvet-primary-container'
              : isRunning
              ? 'bg-amber-500 text-velvet-surface-lowest hover:bg-amber-400'
              : 'bg-velvet-primary text-velvet-on-primary hover:bg-velvet-primary-container shadow-neon-magenta'
          }`}
        >
          {isFinished ? (
            <>
              <RotateCcw className="w-3.5 h-3.5" />
              <span>Recommencer</span>
            </>
          ) : isRunning ? (
            <>
              <Pause className="w-3.5 h-3.5" />
              <span>Pause</span>
            </>
          ) : (
            <>
              <Play className="w-3.5 h-3.5 fill-current" />
              <span>Lancer le Shaker</span>
            </>
          )}
        </button>

        <button
          onClick={handleReset}
          disabled={!isRunning && timeLeft === duration}
          className="p-2 rounded-xl bg-velvet-surface-high text-velvet-text-muted hover:text-velvet-text disabled:opacity-30 disabled:pointer-events-none transition-colors"
          title="Réinitialiser"
        >
          <RotateCcw className="w-4 h-4" />
        </button>
      </div>
    </div>
  );
};
