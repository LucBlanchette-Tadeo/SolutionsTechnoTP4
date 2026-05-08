# 🔑 IDENTIFIANTS - SYSTÈME D'AUTHENTIFICATION

## ⚠️ À CONSERVER EN LIEU SÛR

```
┌────────────────────────────────┐
│  IDENTIFIANTS PAR DÉFAUT       │
├────────────────────────────────┤
│                                │
│  Utilisateur: admin            │
│  Mot de passe: Admin123!       │
│                                │
│  (Accepte la casse exacte)     │
│                                │
└────────────────────────────────┘
```

---

## ✅ OÙ UTILISER CES IDENTIFIANTS

### Application Web
```
URL: http://localhost:5173
Remplir le formulaire login avec:
- Username: admin
- Password: Admin123!
Cliquer: Se connecter
```

### Tests Automatiques
```powershell
.\test-auth.ps1
(Teste auto avec ces identifiants)
```

### Commandes SQL
```sql
-- Vérifier l'utilisateur
SELECT username FROM utilisateur WHERE username = 'admin';

-- Vérifier le hash
SELECT password_hash FROM utilisateur WHERE username = 'admin';

-- Modifier le mot de passe (si oublié)
UPDATE utilisateur SET password_hash = '<nouveau_hash_bcrypt>' WHERE username = 'admin';
```

---

## 🔐 SÉCURITÉ

⚠️ **IMPORTANT:**
- Ne partagez PAS cet identifiant en production
- Changez le mot de passe en production
- Utilisez une base de données sécurisée
- Activez HTTPS en production

### En Production (À FAIRE):
1. Créer nouvel utilisateur admin avec mot de passe fort
2. Désactiver cet utilisateur test
3. Ajouter authentification serveur additionnelle
4. Implémenter JWT avec refresh tokens
5. Ajouter 2FA

---

## 🛠️ SI OUBLIÉ / PERDU

### Réinitialiser le mot de passe via SQL

1. Générer un hash BCrypt pour le nouveau mot de passe
   - Utilisez un outil en ligne ou une CLI
   - Exemple: `bcrypt-generator.com`

2. Mettre à jour la base:
```sql
UPDATE utilisateur 
SET password_hash = '$2a$10$...' 
WHERE username = 'admin';
```

### Réinitialiser via Java

```java
// Dans UtilisateurService
UtilisateurService service = new UtilisateurService();
service.createUser("admin", "NewPassword123!");
```

### Réinitialiser via Application

À implémenter: `/api/auth/reset-password`

---

## 📝 LOG D'ACCÈS

| Date/Heure | Utilisateur | Action | Status |
|-----------|-------------|--------|--------|
| | admin | Login | ✓ |
| | admin | Logout | ✓ |
| | | | |

*(À jour manuellement ou via logs serveur)*

---

## 🔍 VÉRIFICATIONS RAPIDES

### L'utilisateur existe ?
```powershell
# Accès direct BD
docker exec -it <container_name> psql -U postgres -d bateau -c "SELECT * FROM utilisateur;"
```

### Le hash est bon ?
```
Utilisateur: admin
Hash stocké: $2a$10$...(60 caractères)
Type: BCrypt
Rounds: 10
```

### L'API répond ?
```powershell
curl -X POST http://localhost:8080/api/auth/login `
  -Header "Content-Type: application/json" `
  -Body '{"username":"admin","password":"Admin123!"}'

# Réponse attendue:
# {"success":true,"message":"Authentification réussie","username":"admin"}
```

---

## 💾 SAUVEGARDE

### Exporter la base de données
```bash
docker exec <container> pg_dump -U postgres bateau > backup.sql
```

### Restaurer la base de données
```bash
docker exec -i <container> psql -U postgres < backup.sql
```

---

## 📋 CHECKLIST

- [ ] Identifiants notés dans un endroit sûr
- [ ] Accès confirmé à http://localhost:5173
- [ ] Login réussi au moins une fois
- [ ] Logout fonctionnel
- [ ] Rechargement page: reste connecté
- [ ] Base de données: backup fait

---

## 🚨 URGENT À FAIRE

### Avant mise en production:

- [ ] Changer le mot de passe de admin
- [ ] Ajouter utilisateurs réels avec mots de passe forts
- [ ] Implémenter JWT
- [ ] Activer HTTPS
- [ ] Configurer CORS production
- [ ] Ajouter rate limiting
- [ ] Ajouter logs/audit
- [ ] Implémenter 2FA

---

## 🔄 GESTION DES UTILISATEURS

### Ajouter un utilisateur (via BD)

```sql
-- Générer hash BCrypt d'abord!
-- Password example: SecurePass123!

INSERT INTO utilisateur (username, password_hash)
VALUES ('newuser', '$2a$10$...');
```

### Ajouter un utilisateur (via API)

À implémenter dans `UtilisateurController`:
```java
@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody LoginRequest req) {
    utilisateurService.createUser(req.getUsername(), req.getPassword());
    return ResponseEntity.ok("Créé");
}
```

### Supprimer un utilisateur

```sql
DELETE FROM utilisateur WHERE username = 'username';
```

### Lister les utilisateurs

```sql
SELECT id, username FROM utilisateur;
```

### Modifier un mot de passe

```java
// Via API (à ajouter)
@PostMapping("/change-password")
public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req) {
    // Vérifier ancien mdp
    // Définir nouveau mdp
}
```

---

## 📊 STRUCTURE TABLE

```sql
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);
```

| Colonne | Type | Contrainte | Description |
|---------|------|-----------|-------------|
| id | SERIAL | PRIMARY KEY | ID unique |
| username | VARCHAR(50) | NOT NULL, UNIQUE | Nom unique |
| password_hash | VARCHAR(255) | NOT NULL | Hash BCrypt |

---

## 🎯 RÉSUMÉ RAPIDE

| Élément | Valeur |
|--------|--------|
| **Utilisateur** | admin |
| **Mot de passe** | Admin123! |
| **URL Login** | http://localhost:5173 |
| **API** | http://localhost:8080/api/auth/login |
| **BD** | PostgreSQL:5432 |
| **Table** | utilisateur |
| **Sécurité** | BCrypt 10 rounds |

---

**Conservez ces informations en sécurité ! 🔐**

Date créé: 13 avril 2026
