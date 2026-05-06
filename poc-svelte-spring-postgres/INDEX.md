# 📚 INDEX DOCUMENTATION - Système de Handicap

Bienvenue ! 👋 Ce document vous guide vers les bons fichiers selon vos besoins.

---

## ⚡ Je veux démarrer rapidement (2 minutes)

👉 **Lire** : [`DEMARRAGE_2MIN.md`](./DEMARRAGE_2MIN.md)

Contient :
- Les 3 commandes à exécuter
- Les identifiants de connexion
- C'est tout !

---

## 📖 Je veux une explication complète

👉 **Lire** : [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md)

Contient (15 pages) :
- Vue d'ensemble du projet
- Prérequis détaillés
- Configuration initiale
- Démarrage de chaque service
- Utilisation complète du système
- Dépannage exhaustif avec solutions
- Formules de handicap avec exemples
- Architecture du système

---

## 🎓 Je veux apprendre à utiliser l'interface

👉 **Lire** : [`GUIDE_COMPLET_UTILISATION.md`](./GUIDE_COMPLET_UTILISATION.md)

Contient (20 pages) :
- Première utilisation pas à pas
- Gestion des classes de bateaux
- Gestion des bateaux
- Gestion des classes de courses
- Gestion des séries
- Saisie des résultats
- Consultation et interprétation des résultats
- Screenshots textuels pour chaque écran
- Exemples concrets avec chiffres
- Tableau des formules

---

## 🔧 Je veux vérifier que mon système est prêt

👉 **Exécuter** : `verify-system.ps1`

```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres
powershell -ExecutionPolicy Bypass -File .\verify-system.ps1
```

Vérifie :
- ✅ Docker, Java, Node.js, npm
- ✅ Tous les fichiers critiques
- ✅ Disponibilité des ports
- ✅ État de PostgreSQL
- ✅ Dépendances npm/maven

---

## 📋 Je veux un résumé complet du projet

👉 **Lire** : [`RECAP_FINAL.md`](./RECAP_FINAL.md)

Contient (20 pages) :
- Objectif du projet
- Fonctionnalités implémentées
- Architecture complète (diagramme)
- Tous les fichiers créés/modifiés
- Vérification du système
- Démarrage rapide
- Formules implémentées
- Cas d'usage principales
- Checklist de validation
- Prochaines étapes optionnelles

---

## 🎯 Parcours recommandé selon votre profil

### Profil : Je dois démarrer l'application MAINTENANT

1. Exécuter : `verify-system.ps1` (2 min)
2. Lire : [`DEMARRAGE_2MIN.md`](./DEMARRAGE_2MIN.md) (1 min)
3. Ouvrir 3 terminaux et exécuter les 3 commandes (2 min)
4. Accéder à `http://localhost:5173` et se connecter

⏱️ **Temps total : 5 minutes**

---

### Profil : Je veux comprendre comment ça marche

1. Lire : [`RECAP_FINAL.md`](./RECAP_FINAL.md) (10 min) - Architecture + Features
2. Lire : [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md) (15 min) - Démarrage + Dépannage
3. Lire : [`GUIDE_COMPLET_UTILISATION.md`](./GUIDE_COMPLET_UTILISATION.md) (15 min) - Interface

⏱️ **Temps total : 40 minutes**

---

### Profil : Je vais utiliser l'application au quotidien

1. Lire : [`DEMARRAGE_2MIN.md`](./DEMARRAGE_2MIN.md) (1 min)
2. Lire : [`GUIDE_COMPLET_UTILISATION.md`](./GUIDE_COMPLET_UTILISATION.md) (20 min)
3. Garder [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md) en signet pour dépannage

⏱️ **Temps total : 25 minutes**

---

### Profil : Je dois maintenir le code

1. Lire : [`RECAP_FINAL.md`](./RECAP_FINAL.md) - Architecture & fichiers
2. Consulter les fichiers techniques :
   - `backend/src/main/java/com/example/backend/` - Logique métier
   - `frontend/src/` - Interface utilisateur
   - `db/init.sql` - Schéma base de données
3. Lire : [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md) - Démarrage dev

⏱️ **Temps total : 30 minutes**

---

## 📁 Structure des fichiers critiques

```
poc-svelte-spring-postgres/
│
├── 📚 DOCUMENTATION (Lire en premier)
│   ├── DEMARRAGE_2MIN.md              ← COMMENCER ICI
│   ├── DEMARRAGE_COMPLET.md           ← Guide complet
│   ├── GUIDE_COMPLET_UTILISATION.md   ← Interface pas à pas
│   ├── RECAP_FINAL.md                 ← Résumé du projet
│   └── INDEX.md                       ← Ce fichier
│
├── 🚀 SCRIPTS DE DÉMARRAGE
│   ├── start-all.ps1                  ← Lance tout (3 services)
│   ├── restart-backend.bat            ← Redémarre backend
│   └── verify-system.ps1              ← Vérifie la configuration
│
├── ☕ BACKEND (Spring Boot)
│   ├── pom.xml                        ← Dépendances Maven
│   ├── mvnw.cmd                       ← Maven wrapper
│   └── src/main/java/com/example/backend/
│       ├── RaceResult.java            ← Entité résultats
│       ├── RaceResultService.java     ← Logique métier + calcul
│       ├── HandicapCalculator.java    ← Formules PY/TMF
│       ├── RaceResultController.java  ← API REST
│       └── CorsConfig.java            ← Configuration CORS
│
├── 🎨 FRONTEND (Svelte)
│   ├── package.json                   ← Dépendances npm
│   ├── vite.config.ts                 ← Configuration Vite
│   └── src/
│       ├── App.svelte                 ← Composant principal
│       ├── lib/
│       │   ├── api.ts                 ← Appels API
│       │   └── RaceResults.svelte    ← Composant saisie
│       └── routes/
│           └── resultats.svelte       ← Page résultats
│
├── 🐳 BASE DE DONNÉES (PostgreSQL)
│   ├── docker-compose.yml             ← Config Docker
│   └── init.sql                       ← Schéma + données
│
└── 🔧 FICHIERS UTILITAIRES
    └── fix_handicap.sql               ← Script de correction
```

---

## 🎯 Questions fréquentes rapides

### Comment démarrer l'application ?
👉 [`DEMARRAGE_2MIN.md`](./DEMARRAGE_2MIN.md) - 3 commandes, c'est tout !

### Ça ne marche pas, comment ça se dépanne ?
👉 [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md) - Section "Dépannage" (15 solutions)

### Comment créer une course et calculer les handicaps ?
👉 [`GUIDE_COMPLET_UTILISATION.md`](./GUIDE_COMPLET_UTILISATION.md) - Section "Saisie des résultats"

### Quels sont les identifiants de connexion ?
👉 [`DEMARRAGE_2MIN.md`](./DEMARRAGE_2MIN.md) - Identifiants : admin / 123456

### Comment fonctionne le calcul PY/TMF ?
👉 [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md) - Section "Formules utilisées"

### Quels fichiers ont été créés/modifiés ?
👉 [`RECAP_FINAL.md`](./RECAP_FINAL.md) - Section "Fichiers modifiés/créés"

### Où est le code source ?
👉 Backend : `backend/src/main/java/com/example/backend/`  
👉 Frontend : `frontend/src/`

### Comment changer le mot de passe admin ?
👉 [`GUIDE_COMPLET_UTILISATION.md`](./GUIDE_COMPLET_UTILISATION.md) - Première utilisation

---

## 🔗 Fichiers supplémentaires

Aussi disponibles pour référence :
- `HANDICAP_SYSTEM.md` - Architecture technique détaillée
- `HANDICAP_CONFIG.md` - Configuration avancée
- `START_HANDICAP.md` - Variantes de démarrage
- Et bien d'autres...

Consulter l'index des fichiers du répertoire principal si besoin.

---

## ✅ Checklist avant de commencer

Avant de démarrer, assurez-vous :

- [ ] Docker Desktop est installé et en cours d'exécution
- [ ] Java 21 est installé
- [ ] Node.js 20+ est installé
- [ ] npm est fonctionnel
- [ ] Vous avez exécuté `verify-system.ps1` avec succès
- [ ] Vous avez lu le fichier approprié pour votre besoin

---

## 🎓 Architecture globale (vue simplifiée)

```
┌─────────────┐
│  Navigateur │
│ localhost   │
│    5173     │
└──────┬──────┘
       │
       ↓ HTTPS
┌─────────────────────┐
│  Frontend (Svelte)  │
│ - Login            │
│ - Créer résultats  │
│ - Voir résultats   │
│ - Calc handicaps   │
└──────┬──────────────┘
       │
       ↓ HTTP REST (localhost:8080)
┌──────────────────────┐
│ Backend (Spring Boot)│
│ - Authentification  │
│ - Calcul PY/TMF     │
│ - Gestion données   │
└──────┬───────────────┘
       │
       ↓ JDBC
┌──────────────────────┐
│ PostgreSQL (Docker)  │
│ - Utilisateurs      │
│ - Bateaux          │
│ - Résultats        │
└──────────────────────┘
```

---

## 📞 Aide supplémentaire

Si vous êtes bloqué(e) :

1. **Vérifier le système** : `verify-system.ps1`
2. **Lire les logs** : Vérifier ce qui s'affiche dans les 3 terminaux
3. **Chercher dans la documentation** : Utiliser Ctrl+F sur les fichiers
4. **Dépannage** : [`DEMARRAGE_COMPLET.md`](./DEMARRAGE_COMPLET.md) section "Dépannage"
5. **Reset complet** : 
   ```powershell
   docker-compose down -v
   docker-compose up -d
   # Puis redémarrer le backend
   ```

---

**Bon courage ! 🚀**

L'application est entièrement fonctionnelle et prête à être utilisée.

Vous avez des questions ? Consultez les documents recommandés ci-dessus.

---

**Version** : 1.0.0  
**Dernière mise à jour** : Mai 2026  
**Status** : ✅ Prêt pour utilisation
