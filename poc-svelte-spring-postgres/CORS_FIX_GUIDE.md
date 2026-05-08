# Guide de Correction CORS - ✅ Complété

## Changements effectués

### 1. **Configuration CORS mise à jour** (`CorsConfig.java`)

La configuration CORS dans le backend Spring Boot a été corrigée pour autoriser les ports de développement Svelte :

- ✅ Port 5173 (port Vite par défaut)
- ✅ Port 5233 (port frontend configuré)
- ✅ Port 5234 (port alternative)

Tous les ports acceptent :
- Toutes les méthodes HTTP (GET, POST, PUT, DELETE, PATCH, OPTIONS)
- Tous les headers
- Les credentials (cookies)
- Une durée de vie maximale de 3600 secondes

## Instructions de redémarrage

### Étape 1 : Arrêter les serveurs actuels
- Si le backend est en cours d'exécution, pressez `Ctrl+C` dans le terminal PowerShell
- Si le frontend est en cours d'exécution, pressez `Ctrl+C` dans un autre terminal

### Étape 2 : Redémarrer le backend
```powershell
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\backend
.\mvnw.cmd spring-boot:run
```

**Attendez** que vous voyiez le message : `Tomcat started on port(s): 8080`

### Étape 3 : Redémarrer le frontend (dans un autre terminal)
```powershell
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\frontend
npm run dev
```

**Attendez** que vous voyiez : `VITE v... ready in ... ms`

## Tests de validation

### Test 1 : Accéder au frontend
- Allez à `http://localhost:5233` (ou le port indiqué)
- Vous devriez voir la page d'accueil

### Test 2 : Tester la page de connexion
1. Cliquez sur "Connexion" dans le menu
2. Entrez les identifiants :
   - **Email** : admin@test.com
   - **Mot de passe** : password123
3. Cliquez sur "Connexion"

**Résultat attendu** : Vous devriez être redirigé vers la page principale (plus d'erreur "Failed to fetch")

### Test 3 : Tester la page Courses
1. Cliquez sur "Courses" dans le menu
2. Vous devriez voir la liste des Séries

**Résultat attendu** : Données chargées correctement depuis le backend

### Test 4 : Vérifier la console du navigateur
1. Ouvrez les outils de développement (`F12`)
2. Allez à l'onglet **Network**
3. Effectuez une action qui appelle le backend (ex: cliquez sur un lien)

**Résultat attendu** : Les requêtes `http://localhost:8080/api/...` répondent avec un statut 200 et pas d'erreur CORS

## Cas d'erreur et solutions

### Erreur : "Access to XMLHttpRequest has been blocked by CORS policy"
**Solution** : Le backend n'a pas redémarré avec la nouvelle configuration. Relancez le backend.

### Erreur : "Failed to fetch"
**Solutions** :
1. Vérifiez que le backend s'exécute sur `http://localhost:8080`
2. Vérifiez que le frontend s'exécute sur `http://localhost:5233`
3. Redémarrez les deux serveurs

### Erreur : "Cannot GET /api/..."
**Solution** : Le endpoint n'existe pas. Vérifiez les logs du backend pour voir quel endpoint a été appelé.

## Fichiers modifiés

- ✅ `backend/src/main/java/com/example/backend/CorsConfig.java` - Configuration CORS mise à jour
- ✅ `frontend/vite.config.ts` - Port frontend configuré (déjà fait)
- ✅ `frontend/src/lib/api.ts` - Base URL correcte (déjà définie)

## Prochaines étapes

1. Redémarrez le backend et le frontend en suivant les instructions ci-dessus
2. Testez la connexion avec les identifiants fournis
3. Testez la navigation entre les pages (Courses, Résultats, etc.)
4. Vérifiez la console du navigateur pour les erreurs

## Contacts de support

Si vous rencontrez des problèmes :
1. Vérifiez les logs du backend (console PowerShell)
2. Vérifiez la console du navigateur (`F12`)
3. Vérifiez que les services (backend, frontend, base de données) sont tous actifs
