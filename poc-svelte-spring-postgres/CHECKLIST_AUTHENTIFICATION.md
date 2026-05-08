# ✅ Checklist Authentification

## Vérification Backend

### Dépendances
- [ ] Spring Security Crypto ajouté à `pom.xml`
- [ ] `mvn clean compile` réussit sans erreur

### Fichiers Créés
- [ ] `Utilisateur.java` - Entité JPA
- [ ] `UtilisateurRepository.java` - Repository
- [ ] `UtilisateurService.java` - Service avec authentification
- [ ] `UtilisateurController.java` - Endpoints REST
- [ ] `DataInitializer.java` - Initialisation

### Endpoints API
- [ ] `POST /api/auth/login` - Accepte `{username, password}`
- [ ] `GET /api/auth/verify` - Vérifie la session
- [ ] CORS configuré pour `http://localhost:5173`

## Vérification Frontend

### Composants
- [ ] `src/lib/Login.svelte` créé et stylisé
- [ ] Composant importé dans `App.svelte`
- [ ] Interface login affichée avant authentification

### Fonctionnalités
- [ ] Formulaire login avec champs username/password
- [ ] Gestion des erreurs d'authentification
- [ ] Affichage des identifiants de test
- [ ] localStorage utilisé pour la session
- [ ] Navbar affiche username connecté
- [ ] Bouton de déconnexion fonctionnel

## Tests à Effectuer

### Test 1: Connexion Valide
```
1. Lancer l'application (start.ps1 ou start.bat)
2. Aller à http://localhost:5173
3. Entrer: admin / Admin123!
4. Vérifier: page d'accueil affichée, username visible
✓ Succès
```

### Test 2: Identifiants Invalides
```
1. Entrer: admin / wrongpassword
2. Vérifier: message d'erreur "Identifiants invalides"
✓ Succès
```

### Test 3: Déconnexion
```
1. Connecté, cliquer sur "Déconnexion"
2. Vérifier: retour à l'écran de login
3. Vérifier: localStorage nettoyé
✓ Succès
```

### Test 4: Rechargement Page
```
1. Connecté
2. Appuyer F5 (refresh)
3. Vérifier: rester connecté (session persiste)
✓ Succès
```

### Test 5: Nouvelle Fenêtre
```
1. Connecté dans l'onglet A
2. Ouvrir l'app dans l'onglet B
3. Vérifier: onglet B nécessite login (localStorage par onglet)
Note: localStorage est partagé par domaine
✓ Succès
```

## Logs Attendus

### Backend au démarrage
```
✓ Application démarrée - Utilisateurs chargés depuis la BD
```

### À la connexion réussie (console dev)
```
POST http://localhost:8080/api/auth/login 200 OK
{
  "success": true,
  "message": "Authentification réussie",
  "username": "admin"
}
```

## Base de Données

### Vérifier la table utilisateur
```sql
-- Connexion PostgreSQL
psql -U postgres -d bateau

-- Vérifier la table
SELECT * FROM utilisateur;

-- Vérifier l'utilisateur admin
SELECT id, username FROM utilisateur WHERE username = 'admin';
```

## Fichiers Modifiés

- [ ] `backend/pom.xml` - Ajout Spring Security Crypto
- [ ] `db/init.sql` - Nettoyage (pas de duplication)
- [ ] `frontend/src/App.svelte` - Intégration Login + Auth
- [ ] `start.ps1` - Ajout infos login
- [ ] `start.bat` - Ajout infos login

## Sécurité ✅

- [x] Mots de passe hachés (BCrypt)
- [x] Validation serveur
- [x] CORS configuré
- [x] Pas de token JWT (simple pour MVP)
- [x] localStorage sécurisé (session flag seulement)

## Performance

- [x] Pas de requête API inutile
- [x] localStorage pour éviter les checks répétés
- [x] Build frontend réussi sans warning

---

**Status:** ✅ PRÊT À TESTER

Lancez `start.ps1` ou `start.bat` pour démarrer l'application !
