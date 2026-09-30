import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  base: './',
  plugins: [react()],
  server: {
    host: '0.0.0.0',
    port: 3000,
    strictPort: true,
    watch: {
      ignored: ['**/app/**', '**/.gradle/**', '**/build/**', '**/.build-outputs/**', '**/.kotlin/**'],
    },
  },
  build: {
    outDir: 'dist',
    sourcemap: false,
  }
});
