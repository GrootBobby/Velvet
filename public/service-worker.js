const CACHE_NAME = 'velvet-cocktail-v1';
const IMAGE_CACHE_NAME = 'velvet-cocktail-images-v1';

// Fichiers critiques essentiels à pré-cacher pour le démarrage 100% hors-ligne
const PRECACHE_ASSETS = [
  '/',
  '/index.html',
  '/manifest.json',
  '/icons/icon.svg',
  '/icons/icon-192.svg',
  '/icons/icon-512.svg'
];

// Installation : Mise en cache des ressources statiques essentielles
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      console.log('[ServiceWorker] Pré-mise en cache des ressources de base');
      return cache.addAll(PRECACHE_ASSETS).catch((err) => {
        console.warn('[ServiceWorker] Erreur pré-cache non bloquante :', err);
      });
    }).then(() => self.skipWaiting())
  );
});

// Activation : Nettoyage des anciens caches et prise de contrôle immédiate
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((name) => {
          if (name !== CACHE_NAME && name !== IMAGE_CACHE_NAME) {
            console.log('[ServiceWorker] Suppression de l\'ancien cache :', name);
            return caches.delete(name);
          }
        })
      );
    }).then(() => self.clients.claim())
  );
});

// Interception des requêtes HTTP (Stratégies de mise en cache intelligentes)
self.addEventListener('fetch', (event) => {
  const request = event.request;
  const url = new URL(request.url);

  // Uniquement les requêtes GET
  if (request.method !== 'GET') return;

  // 1. Stratégie Cache-First avec remplissage dynamique pour les images de cocktails distantes
  if (
    request.destination === 'image' ||
    url.hostname.includes('thecocktaildb.com') ||
    url.hostname.includes('unsplash.com') ||
    url.pathname.match(/\.(png|jpg|jpeg|svg|webp|gif)$/)
  ) {
    event.respondWith(
      caches.open(IMAGE_CACHE_NAME).then(async (cache) => {
        const cachedResponse = await cache.match(request);
        if (cachedResponse) {
          // Retourne la version du cache immédiatement
          return cachedResponse;
        }

        // Sinon, télécharge depuis le réseau et stocke dans le cache d'images pour le mode hors-ligne
        try {
          const networkResponse = await fetch(request);
          if (networkResponse && networkResponse.status === 200) {
            cache.put(request, networkResponse.clone());
          }
          return networkResponse;
        } catch (error) {
          // En cas de panne réseau et pas de cache d'image, retourne une réponse vide ou placeholder
          console.warn('[ServiceWorker] Image hors-ligne introuvable :', url.href);
          return new Response(
            '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" fill="#FBABFF"><path d="M11 13v6H6v2h12v-2h-5v-6l8-8V3H3v2l8 8z"/></svg>',
            { headers: { 'Content-Type': 'image/svg+xml' } }
          );
        }
      })
    );
    return;
  }

  // 2. Stratégie Stale-While-Revalidate pour les pages et les scripts (JS/CSS/HTML)
  event.respondWith(
    caches.match(request).then((cachedResponse) => {
      const fetchPromise = fetch(request)
        .then((networkResponse) => {
          if (networkResponse && networkResponse.status === 200) {
            caches.open(CACHE_NAME).then((cache) => {
              cache.put(request, networkResponse.clone());
            });
          }
          return networkResponse;
        })
        .catch(() => {
          // Si le réseau échoue et qu'on a du cache, le cache a déjà été retourné
          return cachedResponse;
        });

      return cachedResponse || fetchPromise;
    })
  );
});
