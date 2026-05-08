# 🚤 Système de Gestion des Bateaux de Voile

Une application web moderne pour gérer votre flotte de bateaux de voile, construite avec **Svelte** (frontend) et **Spring Boot** (backend), utilisant **PostgreSQL** pour la persistance des données.

## 🎯 Fonctionnalités

- ✅ **Gestion des bateaux** - Ajout, consultation, et suppression de bateaux
- ✅ **Classification** - Organisation des bateaux par classe (Laser, J70, etc.)
- ✅ **Gestion des courses** - Suivi des séries et classes de courses
- ✅ **API REST complète** - Endpoints JSON pour toutes les opérations
- ✅ **Interface moderne** - Interface web responsive et intuitive
- ✅ **Base de données robuste** - Contraintes et validations intégrées

## 🛠️ Technologies

### Backend
- **Java 21** - Langage principal
- **Spring Boot 3.5.12** - Framework web
- **Spring Data JPA** - Accès aux données
- **PostgreSQL 16** - Base de données
- **Maven** - Gestion des dépendances

### Frontend
- **Svelte** - Framework frontend réactif
- **TypeScript** - Typage statique
- **Vite** - Bundler haute performance
- **CSS3** - Styles modernes et responsifs

### Infrastructure
- **Docker & Docker Compose** - Conteneurisation de PostgreSQL

## 🚀 Démarrage Rapide

### Prérequis
- Docker et Docker Compose installés
- Node.js 16+ (pour Svelte)
- Java 21+ (pour Spring Boot)
- Maven 3.6+ (inclus avec le project)

### Option 1 : Script automatisé (Recommandé)

#### Avec PowerShell:
```powershell
.\start.ps1
```

#### Avec CMD:
```bash
start.bat
```

### Option 2 : Démarrage manuel

#### Terminal 1 - Base de données
```bash
cd db
docker-compose up -d
```

#### Terminal 2 - Backend
```bash
cd backend
.\mvnw spring-boot:run
```

#### Terminal 3 - Frontend
```bash
cd frontend
npm install
npm run dev
```

### Accès à l'application

- **Frontend**: http://localhost:5173
- **Gestion des bateaux**: http://localhost:5173/bateaux
- **API Backend**: http://localhost:8080/api
- **Base de données**: localhost:5433

## 📋 Endpoints API

### Bateaux
```
GET  /api/bateaux              - Liste tous les bateaux
POST /api/bateaux              - Crée un nouveau bateau
```

**Exemple de création:**
```json
POST /api/bateaux
{
  "nomBateau": "Mon Bateau",
  "numeroVoile": 123,
  "nomBarreur": "Jean Dupont",
  "classeBateau": {
    "id": 1
  }
}
```

### Classes de bateau
```
GET  /api/classe-bateau        - Liste toutes les classes
POST /api/classe-bateau        - Crée une nouvelle classe
```

### Séries
```
GET  /api/series               - Liste toutes les séries
POST /api/series               - Crée une nouvelle série
```

## 🗄️ Schéma de la Base de Données

### Table `bateau`
| Colonne | Type | Constraints |
|---------|------|-------------|
| id | SERIAL | PRIMARY KEY |
| nom_bateau | VARCHAR(25) | |
| numero_voile | INTEGER | NOT NULL, UNIQUE (avec classe_bateau_id) |
| nom_barreur | VARCHAR(25) | |
| classe_bateau_id | INTEGER | NOT NULL, FK → classe_bateau |

### Table `classe_bateau`
| Colonne | Type | Constraints |
|---------|------|-------------|
| id | SERIAL | PRIMARY KEY |
| nom_classe | VARCHAR(25) | |

### Autres tables
- `serie` - Séries de courses
- `classe_course` - Classes de courses

## 📊 Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Frontend (Svelte)                 │
│          http://localhost:5173                      │
└────────────────────┬────────────────────────────────┘
                     │
                     │ HTTP/JSON
                     ↓
┌─────────────────────────────────────────────────────┐
│              Backend (Spring Boot)                  │
│          http://localhost:8080/api                  │
│  ┌───────────────────────────────────────────────┐  │
│  │ Controllers → Services → Repositories → JPA  │  │
│  └───────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────┘
                     │
                     │ JDBC
                     ↓
┌─────────────────────────────────────────────────────┐
│           PostgreSQL (localhost:5433)               │
│   POSTGRES_USER: poc                                │
│   POSTGRES_PASSWORD: poc                            │
│   POSTGRES_DB: pocdb                                │
└─────────────────────────────────────────────────────┘
```

## 🧪 Test de l'API

### Via PowerShell
```powershell
# Obtenir tous les bateaux
Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -Method GET

# Créer un bateau
$payload = @{
    nomBateau = "Mon Bateau"
    numeroVoile = 999
    nomBarreur = "John Doe"
    classeBateau = @{ id = 1 }
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -Method POST `
  -ContentType "application/json" `
  -Body $payload
```

### Via cURL
```bash
# Obtenir tous les bateaux
curl http://localhost:8080/api/bateaux

# Créer un bateau
curl -X POST http://localhost:8080/api/bateaux \
  -H "Content-Type: application/json" \
  -d '{
    "nomBateau": "Mon Bateau",
    "numeroVoile": 999,
    "nomBarreur": "John Doe",
    "classeBateau": { "id": 1 }
  }'
```

## 📁 Structure du Projet

```
poc-svelte-spring-postgres/
├── backend/                    # Application Spring Boot
│   ├── src/
│   │   ├── main/java/         # Code source Java
│   │   └── main/resources/    # Configuration
│   ├── pom.xml               # Dépendances Maven
│   └── mvnw                  # Maven wrapper
│
├── frontend/                   # Application Svelte
│   ├── src/
│   │   ├── routes/           # Pages (bateaux.svelte, etc.)
│   │   ├── lib/              # API client
│   │   └── App.svelte        # Composant principal
│   ├── package.json          # Dépendances npm
│   └── vite.config.ts        # Configuration Vite
│
├── db/                         # Base de données
│   ├── docker-compose.yml    # Configuration PostgreSQL
│   └── init.sql              # Script d'initialisation
│
├── start.bat                  # Script de démarrage (Windows CMD)
├── start.ps1                  # Script de démarrage (Windows PowerShell)
├── README.md                  # Ce fichier
├── IMPLEMENTATION.md          # Documentation technique
└── RESUME.md                  # Résumé de l'implémentation
```

## 🔍 Validation et Contraintes

### Niveau Base de Données
- ✅ Clé étrangère: `bateau.classe_bateau_id` → `classe_bateau.id`
- ✅ Contrainte unique: `(numero_voile, classe_bateau_id)` sur bateau
- ✅ Champs obligatoires: `numero_voile`, `classe_bateau_id`

### Niveau Backend
- ✅ Validation de la classe obligatoire
- ✅ Validation du numéro de voile obligatoire
- ✅ Vérification de l'existence de la classe
- ✅ Gestion des erreurs avec messages explicites

### Niveau Frontend
- ✅ Validation des champs du formulaire
- ✅ Messages d'erreur et de succès
- ✅ État de chargement des requêtes
- ✅ Gestion des erreurs API

## 🆘 Troubleshooting

### Le port 8080 est déjà utilisé
```powershell
# Trouver et arrêter le processus
netstat -ano | Select-String ":8080"
taskkill /PID <PID> /F
```

### La base de données ne démarre pas
```bash
# Vérifier les containers Docker
docker-compose ps

# Voir les logs
docker-compose logs db

# Redémarrer
docker-compose restart db
```

### Problème de connexion frontend → backend
- Vérifier que le backend est en cours d'exécution: http://localhost:8080/api/bateaux
- Vérifier les paramètres VITE_API_URL dans le frontend
- Vérifier les CORS du backend

## 📚 Documentation Complémentaire

- **IMPLEMENTATION.md** - Documentation technique détaillée
- **RESUME.md** - Résumé des tâches complétées

## 🎓 Apprentissage

Ce projet démontre:
- Architecture full-stack (frontend + backend + base de données)
- Utilisation de Spring Boot et Spring Data JPA
- Développement frontend avec Svelte/TypeScript
- Gestion des données avec PostgreSQL
- Communication API REST
- Containerisation avec Docker

## 📝 Licence

Ce projet est fourni à titre d'exemple pédagogique.

## 👥 Support

Pour toute question ou problème, consultez:
1. Les fichiers de documentation (IMPLEMENTATION.md, RESUME.md)
2. Les logs de l'application (dans les fenêtres de terminal)
3. Les endpoints API

---

**Statut**: ✅ Complètement opérationnel et testé
**Dernière mise à jour**: 13 avril 2026

