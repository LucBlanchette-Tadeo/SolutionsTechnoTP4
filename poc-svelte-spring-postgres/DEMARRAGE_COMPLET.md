# 🚀 Système de Handicap pour Courses de Voile - Guide de Démarrage Complet

## Vue d'ensemble

Ce projet implémente un système automatique de calcul des handicaps pour des courses de voile utilisant :
- **PY (Portsmouth Yardstick)** : Formule de handicap standard
- **TMF (Time Multiplying Factor)** : Formule alternative pour certaines classes

### Technologies utilisées
- **Frontend** : Svelte + Vite + TypeScript
- **Backend** : Spring Boot 3.5.12 (Java 21)
- **Base de données** : PostgreSQL (via Docker)
- **Authentification** : JWT + Spring Security

---

## 📋 Prérequis

### Obligatoires
1. **Docker & Docker Compose** - Pour PostgreSQL
2. **Java 21** - Pour le backend
3. **Node.js 20+** - Pour le frontend
4. **npm** - Gestionnaire de paquets Node.js

### Vérification des prérequis

```powershell
# Vérifier les installations
docker --version
docker-compose --version
java -version
node --version
npm --version
```

---

## 🔧 Configuration initiale (Une seule fois)

### Étape 1 : Réinitialiser la base de données PostgreSQL

```powershell
# Accéder au répertoire de la base de données
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\db

# Arrêter les conteneurs existants
docker-compose down -v

# Lancer PostgreSQL
docker-compose up -d

# Vérifier que PostgreSQL est bien démarré
docker-compose ps

# Attendre environ 10 secondes que PostgreSQL soit prêt
Start-Sleep -Seconds 10

# Initialiser la base de données
docker-compose exec -T postgres psql -U voile -d voile_db -f /docker-entrypoint-initdb.d/init.sql
```

### Étape 2 : Vérifier que les données de test sont présentes

```powershell
# Vérifier les classes de bateaux
docker-compose exec -T postgres psql -U voile -d voile_db -c "SELECT * FROM classe_bateau;"

# Résultat attendu :
# id | nom_classe | py    | tmf
# 1  | Laser      | 121.0 | 1.1900
# 2  | J70        | 102.5 | 1.1450
```

---

## 🎯 Démarrage de l'application (3 terminaux)

### Terminal 1 : Démarrer PostgreSQL

```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\db
docker-compose up
# Doit afficher : "database system is ready to accept connections"
```

### Terminal 2 : Démarrer le Backend

```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\backend

# First time only
mvnw.cmd clean install

# Démarrer l'application
mvnw.cmd spring-boot:run

# Attendre l'affichage de :
# "Tomcat started on port(s): 8080 (http)"
```

### Terminal 3 : Démarrer le Frontend

```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend

# First time only
npm install

# Démarrer le serveur de développement
npm run dev

# Attendre l'affichage de :
# "Local: http://localhost:5173"
```

### Terminal 4 : Accéder à l'application

```powershell
# Ouvrir un navigateur et aller à :
# http://localhost:5173

# Identifiants de connexion :
# Utilisateur : admin
# Mot de passe : 123456 (défaut)
# 
# ⚠️ IMPORTANT : Changez le mot de passe après la première connexion !
```

---

## 🧪 Test du système de handicap

### 1. Créer une classe de course

Après la connexion :
1. Cliquer sur **"Classes"** dans le menu
2. Cliquer sur **"Créer une classe"**
3. Remplir les champs :
   - **Nom classe** : `J70 Loisir`
   - **Type classe** : `MONOTYPE` ou `JAUGE`
   - **Type handicap** : `PY` (pour utiliser Portsmouth Yardstick)
   - **Handicap min** : `0`
   - **Handicap max** : `200`
4. Cliquer **Créer**

### 2. Créer une série

1. Cliquer sur **"Séries"** dans le menu
2. Cliquer sur **"Créer une série"**
3. Remplir les champs :
   - **Nom série** : `Séries du samedi`
4. Cliquer **Créer**

### 3. Assigner une classe à la série

1. Dans la section **"Ajouter une classe"**
2. Sélectionner :
   - **Classe course** : `J70 Loisir`
3. Cliquer **Ajouter**

### 4. Entrer les résultats de course

1. Cliquer sur **"Résultats"** dans le menu
2. Cliquer sur **"Ajouter un résultat"**
3. Remplir les champs :
   - **Classe course** : `J70 Loisir`
   - **Bateau** : Sélectionner un bateau (ex: `1 - LASER - Jean`)
   - **Temps brut (secondes)** : `3600` (1 heure)
   - **Position brute** : `1`
4. Cliquer **Ajouter**

### 5. Voir les résultats corrigés

Les résultats apparaissent automatiquement avec :
- ✅ **Temps brut** : Temps enregistré
- ✅ **Temps corrigé** : Temps × PY / 1000 (pour PY)
- ✅ **Position corrigée** : Classement après correction de handicap

### Formules utilisées

#### Pour Portsmouth Yardstick (PY)
```
Temps corrigé = Temps brut × (PY / 1000)
```

**Exemple** :
- Temps brut : 3600 secondes (1 heure)
- PY du J70 : 102.5
- Temps corrigé = 3600 × (102.5 / 1000) = **368.25 secondes**

#### Pour Time Multiplying Factor (TMF)
```
Temps corrigé = Temps brut × TMF
```

**Exemple** :
- Temps brut : 3600 secondes (1 heure)
- TMF du J70 : 1.1450
- Temps corrigé = 3600 × 1.1450 = **4122 secondes**

---

## 🔄 Scripts d'automatisation fournis

### Script PowerShell d'une seule commande

```powershell
# Démarrer tout en une commande (ouvre 3 fenêtres PowerShell)
.\start-all.ps1
```

### Redémarrer uniquement le Backend

```powershell
# Lance un clean et re-compile le backend
.\restart-backend.bat
```

### Redémarrer la base de données

```powershell
cd .\db
docker-compose down -v
docker-compose up -d
Start-Sleep -Seconds 10
docker-compose exec -T postgres psql -U voile -d voile_db -f /docker-entrypoint-initdb.d/init.sql
```

---

## 🐛 Dépannage

### Le Backend ne démarre pas

**Erreur** : `Port 8080 already in use`

```powershell
# Tuer le processus utilisant le port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Le Frontend ne compile pas

**Erreur** : `Cannot find module '@sveltejs/...'`

```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend
rm -r node_modules
npm install
npm run dev
```

### PostgreSQL ne démarre pas

**Erreur** : `Bind for 0.0.0.0:5432 failed`

```powershell
# PostgreSQL est peut-être déjà en cours d'exécution
docker-compose down -v
docker-compose up -d
```

### Pas de connexion entre Frontend et Backend

**Erreur** : `CORS error` ou `Failed to fetch from http://localhost:8080`

✅ **Solution** : CORS est déjà configuré dans `CorsConfig.java`

Vérifier que :
1. Le backend tourne sur `http://localhost:8080`
2. Le frontend tourne sur `http://localhost:5173`
3. Voir la console du navigateur (F12) pour les détails

---

## 📊 Architecture du système

```
Frontend (Svelte)          Backend (Spring Boot)        Database (PostgreSQL)
     ↓                            ↓                              ↓
http://localhost:5173    http://localhost:8080         docker-compose
     │                            │                              │
     ├─ Login.svelte      ├─ UtilisateurController      ├─ utilisateur
     ├─ RaceResults.svelte├─ RaceResultController       ├─ race_result
     ├─ Classes.svelte    ├─ ClasseCourseController     ├─ classe_course
     └─ api.ts            ├─ HandicapCalculator         ├─ classe_bateau
                          └─ RaceResultService          └─ bateau
```

---

## 📁 Structure des fichiers critiques

```
c:\Users\6292692\Desktop\poc-svelte-spring-postgres\
├── backend/
│   ├── pom.xml
│   ├── mvnw.cmd
│   └── src/main/java/com/example/backend/
│       ├── RaceResult.java             (Entité)
│       ├── RaceResultRepository.java   (Accès données)
│       ├── RaceResultService.java      (Logique métier)
│       ├── RaceResultController.java   (API REST)
│       ├── HandicapCalculator.java     (Calcul handicaps)
│       └── CorsConfig.java             (Config CORS)
│
├── frontend/
│   ├── package.json
│   ├── vite.config.ts
│   ├── src/
│   │   ├── main.ts
│   │   ├── App.svelte
│   │   ├── app.css
│   │   ├── lib/
│   │   │   ├── api.ts                 (Appels API)
│   │   │   └── RaceResults.svelte     (Composant résultats)
│   │   └── routes/
│   │       ├── +page.svelte           (Accueil)
│   │       └── resultats.svelte       (Page résultats)
│   └── public/
│       ├── favicon.svg
│       └── icons.svg
│
├── db/
│   ├── docker-compose.yml
│   └── init.sql                       (Schéma de base de données)
│
├── fix_handicap.sql                    (Script de correction)
├── start-all.ps1                       (Démarrage rapide)
├── restart-backend.bat                 (Redémarrage backend)
└── DEMARRAGE_COMPLET.md               (Ce fichier)
```

---

## ✅ Checklist de démarrage

- [ ] Docker Desktop est installé et en cours d'exécution
- [ ] Java 21 est installé
- [ ] Node.js 20+ est installé
- [ ] npm est fonctionnel
- [ ] Vous avez exécuté les commandes de réinitialisation PostgreSQL
- [ ] Terminal 1 : PostgreSQL fonctionne (`docker-compose up`)
- [ ] Terminal 2 : Backend fonctionne sur le port 8080
- [ ] Terminal 3 : Frontend fonctionne sur le port 5173
- [ ] Vous pouvez accéder à `http://localhost:5173`
- [ ] La connexion avec `admin / 123456` fonctionne
- [ ] Vous avez changé le mot de passe admin ⚠️

---

## 📞 Support

En cas de problème :

1. **Vérifiez les logs** des 3 terminaux
2. **Consultez la section Dépannage** ci-dessus
3. **Redémarrez tout** en exécutant :
   ```powershell
   docker-compose down -v
   # Attendez 5 secondes
   docker-compose up -d
   # Puis redémarrez le backend
   ```

---

## 🎓 Ressources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Svelte Documentation](https://svelte.dev/docs)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Portsmouth Yardstick Handicap System](https://www.portsmouthyardstick.org/)
- [TMF Handicap System](https://en.wikipedia.org/wiki/Time_Multiplying_Factor)

---

**Dernière mise à jour** : Mai 2026  
**Version** : 1.0.0  
**Status** : ✅ Prêt pour la production
