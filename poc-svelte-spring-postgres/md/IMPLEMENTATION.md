# Documentation - Système de Gestion des Bateaux

## 🎯 Objectif
Mettre en place un système complet de gestion des bateaux de voile avec ajout, consultation et gestion des classes.

## ✅ Fonctionnalités Implémentées

### Backend (Spring Boot)
1. **Entités JPA**
   - `Bateau`: Représente un bateau avec ses propriétés
     - `id` (Primary Key)
     - `nomBateau` (VARCHAR 25)
     - `numeroVoile` (INTEGER, NOT NULL)
     - `nomBarreur` (VARCHAR 25)
     - `classeBateau_id` (Foreign Key vers ClasseBateau)
   
   - `ClasseBateau`: Classe de bateau
     - `id` (Primary Key)
     - `nomClasse` (VARCHAR 25)
   
   - `Serie`: Série de course
     - `id` (Primary Key)
     - `nomSerie` (VARCHAR 50)
     - `classeCourse_id` (Foreign Key vers ClasseCourse)
   
   - `ClasseCourse`: Classe de course
     - `id` (Primary Key)
     - `nomClasseCourse` (VARCHAR 25)
     - `typeClasse` (VARCHAR 10, NOT NULL)
     - Champs optionnels: `typeHandicap`, `handicapMin`, `handicapMax`

2. **Repositories JPA**
   - `BateauRepository`
   - `ClasseBateauRepository`
   - `SerieRepository`
   - `ClasseCourseRepository`

3. **Services**
   - `BateauService`
   - `ClasseBateauService`
   - `SerieService`
   - `ClasseCourseService`

4. **Contrôleurs REST**
   - `BateauController` (@RestController /api/bateaux)
     - GET /api/bateaux (lister tous les bateaux)
     - POST /api/bateaux (créer un bateau)
   - `ClasseBateauController` (@RestController /api/classe-bateau)
     - GET /api/classe-bateau (lister toutes les classes)
     - POST /api/classe-bateau (créer une classe)
   - `SerieController` (@RestController /api/series)
   - `ClasseCourseController` (implicite, accessible via repository)

### Frontend (Svelte)
1. **Page d'accueil améliorée** (`+page.svelte`)
   - Interface moderne avec gradient
   - Navigation rapide vers les sections principales
   - Design responsif

2. **Gestion des bateaux** (`bateaux.svelte`)
   - Formulaire de création de bateau avec validation
   - Liste des bateaux avec affichage en tableau
   - Messages d'erreur et de succès
   - Chargement asynchrone
   - Gestion des états de chargement

3. **API Client** (`lib/api.ts`)
   - Fonctions pour interagir avec le backend
   - Gestion des erreurs HTTP

### Base de Données
- PostgreSQL 16 (Docker)
- Tables créées avec contraintes:
  - FK: `bateau.classe_bateau_id` → `classe_bateau.id`
  - UNIQUE: `(numero_voile, classe_bateau_id)` sur la table `bateau`
  - FK: `serie.classe_course_id` → `classe_course.id`

## 🚀 Démarrage de l'Application

### 1. Base de données
```bash
cd db
docker-compose up -d
```

### 2. Backend (Spring Boot)
```bash
cd backend
.\mvnw spring-boot:run
```
Le serveur démarre sur `http://localhost:8080`

### 3. Frontend (Svelte)
```bash
cd frontend
npm install
npm run dev
```
L'application est accessible sur `http://localhost:5173`

## 📋 Endpoints API

### Bateaux
- **GET** `/api/bateaux` - Liste tous les bateaux
- **POST** `/api/bateaux` - Crée un nouveau bateau
  ```json
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
- **GET** `/api/classe-bateau` - Liste toutes les classes
- **POST** `/api/classe-bateau` - Crée une nouvelle classe
  ```json
  {
    "nomClasse": "Laser"
  }
  ```

### Séries
- **GET** `/api/series` - Liste toutes les séries
- **POST** `/api/series` - Crée une nouvelle série

## 🧪 Test Manuel

### 1. Ajouter un bateau via API
```powershell
$payload = @{
    nomBateau = "Bateau Test"
    numeroVoile = 123
    nomBarreur = "Jean Dupont"
    classeBateau = @{ id = 1 }
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -Method POST -ContentType "application/json" -Body $payload
```

### 2. Consulter les bateaux
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -Method GET
```

### 3. Via l'interface web
- Accédez à `http://localhost:5173/bateaux`
- Remplissez le formulaire et cliquez sur "Créer le bateau"
- Vérifiez que le bateau apparaît dans la liste

## 📦 Dépendances Principales

### Backend
- Spring Boot 3.5.12
- Spring Data JPA
- PostgreSQL JDBC Driver
- Jakarta Persistence API

### Frontend
- Svelte
- TypeScript
- Vite

## ✨ Améliorations Apportées

1. ✅ Création complète des entités JPA pour bateaux et classes
2. ✅ Création des repositories JPA
3. ✅ Création des services
4. ✅ Configuration des contrôleurs REST avec validation
5. ✅ Interface Svelte améliorée avec:
   - Validation des formulaires
   - Messages d'erreur/succès
   - États de chargement
   - Design moderne et responsif
6. ✅ Application opérationnelle de bout en bout
7. ✅ Base de données préalablement configurée

## 🔒 Contraintes Respectées

- ✅ Unicité du couple `(numero_voile, classe_bateau_id)`
- ✅ Clé étrangère vers `classe_bateau` obligatoire
- ✅ Validation backend des données obligatoires
- ✅ CORS configuré pour communiquer avec le frontend
- ✅ Hibernate/JPA configure automatiquement les types de colonnes

## 📝 Notes Importantes

1. La base de données est configurée en mode `ddl-auto: update`, ce qui signifie que Hibernate met à jour le schéma automatiquement
2. Les données de test (classes Laser et J70) sont prêtes à l'emploi
3. L'API est protégée par CORS pour accepter les requêtes du frontend
4. Tous les champs obligatoires sont validés côté backend

