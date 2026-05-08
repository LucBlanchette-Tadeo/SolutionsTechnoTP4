# 🔐 Système d'Authentification - Guide de Démarrage

## ✅ Changements Apportés

### 1. **Backend (Spring Boot)**

#### Nouvelles Classes Créées:

- **`Utilisateur.java`** - Entité JPA pour stocker les utilisateurs
  - Champs: `id`, `username`, `passwordHash`
  - Annotations: `@Entity`, `@Table(name = "utilisateur")`

- **`UtilisateurRepository.java`** - Repository JPA
  - Méthode: `findByUsername(String username)`

- **`UtilisateurService.java`** - Service d'authentification
  - Méthode: `authenticate(String username, String password)` - Vérifie les identifiants
  - Méthode: `createUser(String username, String password)` - Crée un nouvel utilisateur
  - Utilise BCrypt pour le hachage des mots de passe

- **`UtilisateurController.java`** - Contrôleur REST
  - Endpoint: `POST /api/auth/login` - Authentifie un utilisateur
  - Endpoint: `GET /api/auth/verify` - Vérifie la session

- **`DataInitializer.java`** - Initialisation au démarrage
  - Crée automatiquement l'utilisateur `admin` avec le mot de passe `Admin123!`

#### Dépendances Ajoutées:
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

#### Modification Base de Données:
- Nouvelle table `utilisateur` avec colonnes `username` et `password_hash`

### 2. **Frontend (Svelte)**

#### Nouveaux Composants:

- **`src/lib/Login.svelte`** - Page de connexion
  - Interface utilisateur élégante avec gradient
  - Champs: username et password
  - Gestion des erreurs
  - Affichage des identifiants de test
  - Appel à l'API `/api/auth/login`

#### Modifications d'App.svelte:

- Ajout de l'état d'authentification (`isAuthenticated`, `currentUsername`)
- Import du composant `Login`
- Vérification de la session au démarrage (localStorage)
- Affichage conditionnel: page de login si non authentifié
- Nouvelle section utilisateur dans la navbar:
  - Affichage du nom d'utilisateur connecté
  - Bouton de déconnexion
- Fonction `handleLoginSuccess()` et `handleLogout()`
- Styles CSS pour le bouton de déconnexion et la section utilisateur

## 🚀 Utilisation

### Identifiants par Défaut:
```
Nom d'utilisateur: admin
Mot de passe:      Admin123!
```

### Démarrage:

1. **Lancer la base de données PostgreSQL:**
   ```powershell
   docker-compose -f db/docker-compose.yml up
   ```

2. **Démarrer le backend:**
   ```powershell
   cd backend
   mvn spring-boot:run
   ```
   Vous verrez: `✓ Utilisateur admin créé avec le mot de passe: Admin123!`

3. **Démarrer le frontend:**
   ```powershell
   cd frontend
   npm run dev
   ```

4. **Accéder à l'application:**
   - http://localhost:5173
   - Entrez les identifiants par défaut
   - Cliquez sur "Se connecter"

## 🔒 Fonctionnalités de Sécurité

- ✅ Mots de passe hachés avec BCrypt (10 rounds)
- ✅ Stockage sécurisé en base de données
- ✅ Validation côté serveur
- ✅ Gestion de session avec localStorage
- ✅ CORS configuré pour le frontend
- ✅ Déconnexion sécurisée (nettoyage du localStorage)

## 📝 Notes Importantes

- L'utilisateur admin est créé automatiquement au démarrage du backend
- Si l'admin existe déjà, il n'est pas recréé
- Le localStorage stocke uniquement un flag d'authentification, pas les mots de passe
- La validation des identifiants se fait toujours côté serveur

## 🔧 Ajouter un Nouvel Utilisateur

Pour ajouter d'autres utilisateurs, vous pouvez créer un endpoint d'enregistrement ou accéder à la base de données directement.

### Via l'API (à implémenter):
```javascript
POST /api/auth/register
{
  "username": "newuser",
  "password": "SecurePass123!"
}
```

### Via la base de données:
```sql
-- Ne pas ajouter directement, utiliser l'API pour hasher le mot de passe
-- Les mots de passe doivent être au format BCrypt
```

## 🎨 Personnalisation

Vous pouvez personnaliser:
- Couleurs du login dans `src/lib/Login.svelte`
- Styles de la navbar dans `src/App.svelte`
- Messages d'erreur et textes

---
**Dernière mise à jour:** 13 avril 2026
