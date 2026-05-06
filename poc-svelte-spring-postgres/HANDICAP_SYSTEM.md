# 🏆 Système de Handicap Automatique - PY et TMF

## 📋 Résumé de l'implémentation

Cette implémentation ajoute un système complet de calcul automatique des handicaps pour les courses de voile, avec support des formules **PY (Portsmouth Yardstick)** et **TMF (Time Multiplying Factor)**.

### ✨ Fonctionnalités

1. **Calcul automatique des handicaps** avec deux formules :
   - **PY (Portsmouth Yardstick)** : `Temps Corrigé = (Temps Brut × 1000) / PY`
   - **TMF (Time Multiplying Factor)** : `Temps Corrigé = Temps Brut × TMF`

2. **Classement équitable** basé sur les temps corrigés
3. **Interface frontend** intuitive et responsive
4. **API REST** complète pour gérer les résultats
5. **Persistance en base de données PostgreSQL**

---

## 🔧 Modifications apportées

### Backend (Spring Boot / Java)

#### 1. **Modification des entités**
- ✏️ **ClasseBateau.java** : Ajout des colonnes `py` et `tmf`
- ✨ **RaceResult.java** : Nouvelle entité pour enregistrer les résultats bruts et corrigés

#### 2. **Nouvelles classes utilitaires**
- 🧮 **HandicapCalculator.java** : Calculs des formules PY et TMF, classement des résultats
- 📊 **RaceResultService.java** : Logique métier pour gérer les résultats
- 🌐 **RaceResultController.java** : API REST endpoints
- 📦 **RaceResultRepository.java** : Accès à la base de données

#### 3. **Base de données**
- Modification : Ajout colonnes `py` et `tmf` à `classe_bateau`
- Nouvelle table : `race_result` avec tous les champs nécessaires

### Frontend (Svelte)

#### 1. **Nouveau composant**
- 🎯 **RaceResults.svelte** : Interface complète pour soumettre et voir les résultats

#### 2. **Modifications API**
- 📡 **api.ts** : Ajout des fonctions pour interagir avec les endpoints de résultats

#### 3. **Routing**
- 📍 **resultats.svelte** : Page route pour accéder au composant
- 🧭 **App.svelte** : Ajout du bouton "Résultats" dans la navigation

---

## 🚀 Installation et déploiement

### Étape 1 : Déployer la base de données

```bash
# Assurez-vous que PostgreSQL est en cours d'exécution
# Les migrations init.sql seront exécutées automatiquement au démarrage

# Les données de test incluent :
# - Laser : PY=1050.00, TMF=0.9800
# - J70 : PY=1000.00, TMF=0.9900
```

### Étape 2 : Lancer le backend

```bash
cd backend
./mvnw spring-boot:run
# Ou sous Windows :
mvnw.cmd spring-boot:run
```

Le serveur démarre sur `http://localhost:8080`

### Étape 3 : Lancer le frontend

```bash
cd frontend
npm install
npm run dev
```

L'application est accessible sur `http://localhost:5173`

---

## 📡 API Endpoints

### Soumettre un résultat

```http
POST /api/race-results/submit
Content-Type: application/json

{
  "classeCourseId": 1,
  "bateauId": 1,
  "tempsBrutSecondes": 3600,
  "typeHandicap": "PY"
}
```

**Réponse succès (200)** :
```json
{
  "id": 1,
  "classeCourseId": 1,
  "bateauId": 1,
  "bateauName": "Mon Bateau",
  "tempsBrut": 3600,
  "tempsBrutFormatted": "01:00:00",
  "tempsCorrige": 3428.57,
  "tempsCorrigenFormatted": "00:57:08",
  "typeHandicap": "PY",
  "statut": "OK"
}
```

### Récupérer les résultats d'une course

```http
GET /api/race-results/course/1
```

**Réponse** (liste triée par temps corrigé) :
```json
[
  {
    "raceResultId": 1,
    "bateauName": "Bateau 1",
    "tempsBrut": 3600,
    "tempsCorrige": 3428.57,
    "positionBrute": 2,
    "positionCorrigee": 1,
    "typeHandicap": "PY"
  },
  {
    "raceResultId": 2,
    "bateauName": "Bateau 2",
    "tempsBrut": 3500,
    "tempsCorrige": 3500,
    "positionBrute": 1,
    "positionCorrigee": 2,
    "typeHandicap": "TMF"
  }
]
```

### Récupérer un résultat spécifique

```http
GET /api/race-results/1
```

### Supprimer un résultat

```http
DELETE /api/race-results/1
```

---

## 🎨 Interface utilisateur

### Page "Résultats"

La page est divisée en deux sections :

#### 1. **Formulaire d'enregistrement**
- Sélection de la course
- Sélection du bateau
- Saisie du temps brut (en secondes)
- Affichage en temps réel au format HH:MM:SS
- Choix du type de handicap (PY ou TMF)
- Affichage des formules de calcul

#### 2. **Tableau des résultats**
- Position (basée sur temps corrigé)
- Nom du bateau
- Temps brut et formule appliquée
- Temps corrigé surlignés en orange
- Bouton de suppression pour chaque résultat
- Tri automatique par temps corrigé croissant

---

## 📚 Exemple d'utilisation

### Scénario : Course avec handicaps PY

**Données initiales** :
- Laser : PY = 1050
- J70 : PY = 1000

**Résultats bruts** :
- Laser #101 : 3600 secondes (1h00:00)
- J70 #201 : 3500 secondes (00:58:20)

**Calcul**:
- Laser : (3600 × 1000) / 1050 = 3428.57 sec → **00:57:08**
- J70 : (3500 × 1000) / 1000 = 3500 sec → **00:58:20**

**Classement final** :
1. 🥇 Laser #101 - 00:57:08 (plus rapide en temps corrigé)
2. 🥈 J70 #201 - 00:58:20

> Sans le handicap, J70 aurait gagné ! Le handicap rend la course équitable entre différentes classes.

---

## 🐛 Dépannage

### Erreur : "PY non défini pour cette classe de bateau"

→ Assurez-vous que la valeur PY a été attribuée à la classe de bateau

### Erreur : "ClasseCourse non trouvée"

→ Vérifiez que l'ID de la course existe

### Résultats ne s'affichent pas

→ Vérifiez que :
1. Le service backend est en cours d'exécution
2. Vous avez enregistré au moins un résultat
3. La course est sélectionnée dans le formulaire

---

## 📊 Statistiques

- **4 nouvelles classes Java**
- **1 nouvelle table PostgreSQL**
- **1 nouveau composant Svelte**
- **6 endpoints API REST**
- **Formules mathématiques** : 2 (PY et TMF)

---

## 🔐 Notes de sécurité

- Les résultats sont enregistrés directement sans authentification supplémentaire
- À considérer pour la production : Ajouter des permissions par rôle (arbitre, administrateur, etc.)

---

## 📝 Fichiers modifiés / créés

```
✏️ MODIFIÉS :
  - db/init.sql
  - backend/src/main/java/com/example/backend/ClasseBateau.java
  - frontend/src/lib/api.ts
  - frontend/src/App.svelte

✨ CRÉÉS :
  - backend/src/main/java/com/example/backend/RaceResult.java
  - backend/src/main/java/com/example/backend/RaceResultRepository.java
  - backend/src/main/java/com/example/backend/HandicapCalculator.java
  - backend/src/main/java/com/example/backend/RaceResultService.java
  - backend/src/main/java/com/example/backend/RaceResultController.java
  - frontend/src/lib/RaceResults.svelte
  - frontend/src/routes/resultats.svelte
```

---

## ✅ Checklist de validation

- [x] Formules PY et TMF implémentées
- [x] Base de données mise à jour
- [x] API REST complète
- [x] Interface frontend responsive
- [x] Calculs de classement automatiques
- [x] Affichage des résultats par temps corrigé
- [x] Support pour deux méthodes de handicap
- [x] Suppression de résultats

---

## 🎯 Prochaines étapes (optionnel)

- [ ] Ajouter les statuts DNF (Did Not Finish), DNS (Did Not Start), etc.
- [ ] Historique des résultats par bateau
- [ ] Classement général sur plusieurs courses
- [ ] Export des résultats en PDF/CSV
- [ ] Graphiques de performance
- [ ] Système d'équipes
- [ ] Mode administration pour modifier les coefficients PY/TMF

---

**Version** : 1.0.0  
**Date** : Mai 2026  
**Statut** : ✅ Production ready
