/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        velvet: {
          surface: '#121317',
          'surface-lowest': '#0D0E12',
          'surface-low': '#1A1B20',
          'surface-container': '#1F1F24',
          'surface-high': '#292A2E',
          'surface-highest': '#343439',
          primary: '#FBABFF',
          'primary-container': '#E14EF6',
          'on-primary': '#580065',
          secondary: '#E0B6FF',
          'secondary-container': '#6D11AD',
          tertiary: '#FFB95F',
          'tertiary-container': '#CA8100',
          text: '#E3E2E8',
          'text-muted': '#D6C0D3',
          outline: '#9F8B9D',
          'glass-border': 'rgba(255, 255, 255, 0.12)',
        }
      },
      boxShadow: {
        'neon-magenta': '0 0 20px rgba(251, 171, 255, 0.35)',
        'neon-violet': '0 0 20px rgba(224, 182, 255, 0.35)',
        'neon-amber': '0 0 20px rgba(255, 185, 95, 0.35)',
      },
      aspectRatio: {
        'card': '0.85',
      }
    },
  },
  plugins: [],
}
