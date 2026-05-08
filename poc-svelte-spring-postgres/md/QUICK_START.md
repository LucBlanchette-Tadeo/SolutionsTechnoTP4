# 🧪 Quick Start - Tester Rapidement

## 🚀 Lancer l'Application (Si Pas Encore Lancée)

### Terminal 1 - Backend
```powershell
cd c:\Users\6242441\Desktop\poc-svelte-spring-postgres\backend
.\mvnw.cmd clean spring-boot:run
# Attendre 30-45 secondes pour que le serveur démarre
```

### Terminal 2 - Frontend
```powershell
cd c:\Users\6242441\Desktop\poc-svelte-spring-postgres\frontend
npm run dev
# Le serveur devrait démarrer sur http://localhost:5173
```

### Terminal 3 (Optionnel) - Base de Données
```powershell
# Vérifier que Docker est en cours d'exécution
docker ps
# Si pas lancé:
cd c:\Users\6242441\Desktop\poc-svelte-spring-postgres\db
docker-compose up -d
```

---

## ✅ Vérifications Rapides

### 1. Backend Répond?
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/classe-bateau -Method GET | Select-Object StatusCode
# Devrait retourner: 200
```

### 2. Frontend Accessible?
```powershell
Invoke-WebRequest -Uri http://localhost:5173 -Method GET | Select-Object StatusCode
# Devrait retourner: 200
```

### 3. Créer un Bateau via API
```powershell
$body = @{
  nomBateau = "Test Rapide"
  numeroVoile = 100
  nomBarreur = "Testeur"
  classeBateau = @{ id = 1 }
} | ConvertTo-Json

Invoke-WebRequest -Uri http://localhost:8080/api/bateaux `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $body | ConvertFrom-Json
```

### 4. Créer une Série via API
```powershell
$body = @{
  nomSerie = "Test Rapide Serie"
  classeCourse = @{ id = 3 }
} | ConvertTo-Json

Invoke-WebRequest -Uri http://localhost:8080/api/series `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $body | ConvertFrom-Json
```

### 5. Lister les Bateaux
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/bateaux -Method GET | ConvertFrom-Json | Format-Table -AutoSize
```

### 6. Lister les Séries
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/series -Method GET | ConvertFrom-Json | Format-Table -AutoSize
```

---

## 🌐 Ouvrir les URLs

### Frontend
```
http://localhost:5173
```

### API Documentation (Swagger/OpenAPI) - Si disponible
```
http://localhost:8080/swagger-ui.html
```

### Accès Direct à une Ressource
```
http://localhost:8080/api/bateaux
http://localhost:8080/api/series
http://localhost:8080/api/classe-bateau
http://localhost:8080/api/classe-course
```

---

## 🧬 Tests de Scénarios

### Scénario 1: Créer et Lister
```powershell
# 1. Créer
curl -X POST http://localhost:8080/api/bateaux `
  -H "Content-Type: application/json" `
  -d '{
    "nomBateau":"Bateau Test",
    "numeroVoile":123,
    "nomBarreur":"Toto",
    "classeBateau":{"id":1}
  }'

# 2. Lister
curl -X GET http://localhost:8080/api/bateaux

# 3. Vérifier en UI
# Ouvrir http://localhost:5173, aller à "Bateaux"
```

### Scénario 2: Créer une Série Complète
```powershell
# 1. Créer classe bateau
$class = @{ nomClasse = "Test Boat Class" } | ConvertTo-Json
$classResp = Invoke-WebRequest -Uri http://localhost:8080/api/classe-bateau `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $class | ConvertFrom-Json
$classId = $classResp.id

# 2. Créer classe course
$course = @{ nomClasseCourse = "Test Course"; typeClasse = "monotype" } | ConvertTo-Json
$courseResp = Invoke-WebRequest -Uri http://localhost:8080/api/classe-course `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $course | ConvertFrom-Json
$courseId = $courseResp.id

# 3. Créer série
$serie = @{ nomSerie = "Test Serie"; classeCourse = @{ id = $courseId } } | ConvertTo-Json
Invoke-WebRequest -Uri http://localhost:8080/api/series `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $serie

# 4. Créer bateau
$bateau = @{
  nomBateau = "Mon Bateau Test"
  numeroVoile = 999
  nomBarreur = "Test User"
  classeBateau = @{ id = $classId }
} | ConvertTo-Json
Invoke-WebRequest -Uri http://localhost:8080/api/bateaux `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $bateau
```

---

## 📊 Vérifications Base de Données

### Connexion à PostgreSQL
```powershell
docker exec db-db-1 psql -U poc -d pocdb
```

### Requêtes Utiles (Une Fois Connecté)
```sql
-- Compter les bateaux
SELECT COUNT(*) FROM bateau;

-- Compter les séries
SELECT COUNT(*) FROM serie;

-- Voir tous les bateaux
SELECT * FROM bateau;

-- Voir toutes les séries
SELECT * FROM serie;

-- Voir les 5 derniers bateaux
SELECT * FROM bateau ORDER BY id DESC LIMIT 5;

-- Quitter psql
\q
```

---

## 🔍 Débugage

### Voir les Logs du Backend
```
Regarder le terminal du backend (Spring Boot)
Les erreurs s'affichent en temps réel
```

### Ouvrir la Console Frontend
```
1. Ouvrir http://localhost:5173
2. Appuyer sur F12 (ou Ctrl+Shift+I)
3. Aller à l'onglet "Console"
4. Vérifier les erreurs en rouge
5. Aller à l'onglet "Network" pour voir les requêtes API
```

### Arrêter tout Proprement
```powershell
# Backend
Ctrl+C dans le terminal backend

# Frontend
Ctrl+C dans le terminal frontend

# Docker (si nécessaire)
docker-compose down
```

---

## 📝 Fichiers Importants

### Frontend
- `frontend/src/App.svelte` - Page principale (tout le CRUD)
- `frontend/src/lib/api.ts` - Fonctions API
- `frontend/vite.config.ts` - Configuration Vite

### Backend
- `backend/src/main/java/com/example/backend/Bateau.java` - Entité Bateau
- `backend/src/main/java/com/example/backend/Serie.java` - Entité Série (CORRIGÉE!)
- `backend/src/main/java/com/example/backend/BateauController.java` - API Bateaux
- `backend/src/main/java/com/example/backend/SerieController.java` - API Séries

### Configuration
- `backend/src/main/resources/application.properties` - Config Spring Boot
- `db/docker-compose.yml` - Configuration Docker PostgreSQL

---

## 🎯 Objectif Atteint

✅ Création de bateau fonctionnelle  
✅ Création de série fonctionnelle  
✅ Tous les CRUD fonctionnels  
✅ Frontend réactif  
✅ API REST complète  
✅ Base de données intègre  

---

**Vous êtes Prêt! 🚀**

Ouvrez http://localhost:5173 et testez!
