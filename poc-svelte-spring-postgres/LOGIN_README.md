# 🔐 Authentification - Guide Rapide

## 👤 Identifiants par Défaut

```
Utilisateur: admin
Mot de passe: Admin123!
```

## 🚀 Démarrage Rapide

### 1. Lancer l'application complète

**Avec PowerShell:**
```powershell
.\start.ps1
```

**Avec Command Prompt:**
```batch
start.bat
```

Cela démarre automatiquement:
- ✅ PostgreSQL (Docker)
- ✅ Backend Spring Boot (port 8080)
- ✅ Frontend Svelte (port 5173)

### 2. Accéder à l'application

1. Ouvrez votre navigateur: **http://localhost:5173**
2. Entrez les identifiants:
   - Username: `admin`
   - Password: `Admin123!`
3. Cliquez sur **Se connecter** 🔓

## 🔒 Système d'Authentification

- **Backend:** Spring Boot avec BCrypt (10 rounds)
- **Frontend:** Svelte avec localStorage
- **Base de données:** PostgreSQL (table `utilisateur`)

### Architecture

```
┌─────────────────┐
│   Navigateur    │
│   (Svelte)      │
└────────┬────────┘
         │ Login (POST)
         ▼
┌─────────────────────────────┐
│  Spring Boot Backend         │
│  /api/auth/login            │
│  - Valide username/password │
│  - Compare avec BCrypt      │
└────────┬────────────────────┘
         │
         ▼
┌─────────────────┐
│  PostgreSQL     │
│  Table: utilisateur
│  - id, username │
│  - password_hash│
└─────────────────┘
```

## 📝 Fonctionnalités

✅ **Sécurité:**
- Mots de passe hachés en BCrypt (jamais stockés en clair)
- Validation côté serveur
- CORS configuré

✅ **Utilisabilité:**
- Interface moderne et responsive
- Affichage du nom d'utilisateur connecté
- Bouton de déconnexion facilement accessible
- Messages d'erreur clairs

✅ **Persistance:**
- Session maintenue via localStorage
- Vérification automatique au rechargement

## 🔧 Dépannage

### Erreur "Identifiants invalides"
- Vérifiez que le backend est bien lancé (http://localhost:8080)
- Vérifiez que PostgreSQL est en cours d'exécution
- Vérifiez l'utilisateur dans la BD: 
  ```sql
  SELECT username FROM utilisateur;
  ```

### La page de login reste blanche
- Ouvrez la console du navigateur (F12 > Console)
- Vérifiez les erreurs CORS
- Vérifiez que le backend écoute sur le port 8080

### Oublié le mot de passe
Réinitialisez directement dans PostgreSQL:
```sql
UPDATE utilisateur 
SET password_hash = '$2a$10$...' 
WHERE username = 'admin';
```

## 📚 Fichiers Principaux

```
/backend
├── src/main/java/com/example/backend/
│   ├── Utilisateur.java          # Entité JPA
│   ├── UtilisateurRepository.java # DAO
│   ├── UtilisateurService.java   # Logique auth
│   └── UtilisateurController.java # API REST
├── pom.xml                        # Dépendances

/frontend
├── src/lib/Login.svelte          # Composant login
├── src/App.svelte                 # App avec auth
└── src/main.ts

/db
└── init.sql                       # Table utilisateur
```

## 🛠️ Ajouter un Nouvel Utilisateur

### Via la base de données (rapide):

```sql
-- D'abord générer le hash du mot de passe
-- Vous pouvez utiliser n'importe quel outil bcrypt en ligne

INSERT INTO utilisateur (username, password_hash) 
VALUES ('newuser', '$2a$10$...');
```

### Via une API (à implémenter):

```javascript
// À ajouter dans UtilisateurController
@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody LoginRequest request) {
    utilisateurService.createUser(request.getUsername(), request.getPassword());
    return ResponseEntity.ok("Utilisateur créé");
}
```

## 📞 Support

Pour des problèmes:
1. Vérifiez les logs du backend: vérifiez la fenêtre Spring Boot
2. Vérifiez les logs du frontend: F12 > Console
3. Vérifiez Docker: `docker ps`
4. Vérifiez PostgreSQL: `psql -U postgres -d bateau -c "SELECT * FROM utilisateur;"`

---

**Dernière mise à jour:** 13 avril 2026
