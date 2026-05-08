# 📚 INDEX - Documentation du Système de Handicap

Bienvenue ! Vous trouverez ici tous les documents relatifs à l'implémentation du système de handicap automatique pour les courses de voile.

---

## 🎯 Commencer par où ?

### 👤 Je suis un **utilisateur final** (arbitre, organisateur, participant)

1. **Lire d'abord** : [Guide d'utilisation complet](./GUIDE_UTILISATION_HANDICAP.md)
   - 📱 Comment enregistrer des résultats
   - 📊 Comment voir le classement
   - ❓ FAQ et troubleshooting

2. **Comprendre** : [Configuration des handicaps](./HANDICAP_CONFIG.md)
   - 🧮 Formules PY et TMF expliquées
   - 📈 Exemples concrets
   - ⚙️ Coefficients des classes

3. **Référence rapide** : [Résumé des modifications](./RESUME_MODIFICATIONS.md)
   - 📊 Flux de données
   - 📡 Endpoints API
   - ✨ Nouvelles fonctionnalités

---

### 👨‍💻 Je suis un **développeur**

1. **Lire d'abord** : [Architecture système](./HANDICAP_SYSTEM.md)
   - 🏗️ Structure générale
   - 🔗 Relations entre composants
   - 📝 Classes Java et modèles

2. **Détails techniques** : [Résumé des modifications](./RESUME_MODIFICATIONS.md)
   - 🗂️ Structure des fichiers
   - 🔄 Flux de données
   - 💾 Schéma base de données

3. **Tester** : [Script de test](./test-handicap.ps1)
   - ✅ Validation API
   - 📊 Test des formules

---

### ⚙️ Je suis un **administrateur**

1. **Déploiement** : Voir section "Installation" dans [Architecture système](./HANDICAP_SYSTEM.md)
   - 🚀 Lancer backend
   - 🎨 Lancer frontend
   - 💾 Initialiser BD

2. **Configuration** : [Configuration des handicaps](./HANDICAP_CONFIG.md)
   - 📊 Modifier les coefficients PY/TMF
   - 🔄 Mettre à jour les données
   - 📋 Checklists

3. **Maintenance** : [Architecture système](./HANDICAP_SYSTEM.md)
   - 🔍 Dépannage
   - 📊 Statistiques
   - 🔐 Notes de sécurité

---

## 📄 Index complet des documents

### 🎓 Guides utilisateur

| Document | Audience | Durée | Contenu |
|----------|----------|-------|---------|
| [Guide d'utilisation complet](./GUIDE_UTILISATION_HANDICAP.md) | Utilisateurs finaux | 15 min | Tutoriel, FAQ, astuces |
| [Configuration des handicaps](./HANDICAP_CONFIG.md) | Administrateurs | 10 min | Formules, exemples, mise à jour |

### 👨‍💻 Guides techniques

| Document | Audience | Durée | Contenu |
|----------|----------|-------|---------|
| [Architecture système](./HANDICAP_SYSTEM.md) | Développeurs | 20 min | Architecture, API, déploiement |
| [Résumé des modifications](./RESUME_MODIFICATIONS.md) | Développeurs | 15 min | Fichiers, formules, flux données |

### 🧪 Tests et validation

| Document | Audience | Durée | Contenu |
|----------|----------|-------|---------|
| [test-handicap.ps1](./test-handicap.ps1) | Administrateurs/Dev | 5 min | Script automatisé PowerShell |

---

## 🗺️ Structure de la documentation

```
📚 Documentation Handicap
│
├── 📖 GUIDE_UTILISATION_HANDICAP.md
│   ├── Démarrage rapide (5 min)
│   ├── Exemples pratiques (10 min)
│   ├── Formules expliquées (8 min)
│   ├── FAQ (10 min)
│   ├── Troubleshooting (5 min)
│   └── Configuration des handicaps (5 min)
│
├── 🔧 HANDICAP_CONFIG.md
│   ├── Classes de bateaux (données)
│   ├── Modifier les coefficients (SQL)
│   ├── Comprendre les formules (explications)
│   ├── Utiliser deux formules (guide)
│   ├── Mise à jour annuelle (procédure)
│   └── FAQ technique (questions)
│
├── 🏗️ HANDICAP_SYSTEM.md
│   ├── Résumé des features (liste)
│   ├── Modifications apportées (liste détaillée)
│   ├── Installation et déploiement (guide)
│   ├── API Endpoints (documentation)
│   ├── Interface utilisateur (description)
│   ├── Exemple d'utilisation (scenario)
│   ├── Dépannage (FAQ)
│   ├── Notes de sécurité (security)
│   ├── Prochaines étapes (roadmap)
│   └── Checklist (validation)
│
├── 📊 RESUME_MODIFICATIONS.md
│   ├── Objectif réalisé (summary)
│   ├── Structure des fichiers (arborescence)
│   ├── Flux de données (diagramme)
│   ├── Base de données (schéma SQL)
│   ├── Formules implémentées (code)
│   ├── Endpoints API (tableau)
│   ├── Interface utilisateur (mockup)
│   ├── Statistiques du projet (nombres)
│   ├── Checklist de validation (✅)
│   ├── Améliorations futures (roadmap)
│   └── Pour les développeurs (guide dev)
│
└── 🧪 test-handicap.ps1
    ├── Tests des endpoints API
    ├── Soumission de résultats
    ├── Récupération des classements
    └── Affichage formaté des résultats
```

---

## 🔍 Accès rapide par sujet

### 📊 Je veux savoir comment ça marche

→ [Exemple d'utilisation](./HANDICAP_SYSTEM.md#-exemple-dutilisation)  
→ [Interface utilisateur](./RESUME_MODIFICATIONS.md#-interface-utilisateur)  
→ [Flux de données](./RESUME_MODIFICATIONS.md#-flux-de-données)  

### 🧮 Je veux comprendre les formules

→ [Formules PY et TMF](./GUIDE_UTILISATION_HANDICAP.md#-comprendre-les-formules)  
→ [Formules implémentées](./RESUME_MODIFICATIONS.md#-formules-implémentées)  
→ [Comprendre les handicaps](./HANDICAP_CONFIG.md#-comprendre-les-coefficients)  

### 🚀 Je veux déployer le système

→ [Installation et déploiement](./HANDICAP_SYSTEM.md#-installation-et-déploiement)  
→ [Démarrage rapide du guide](./GUIDE_UTILISATION_HANDICAP.md#-démarrage-rapide)  

### 💾 Je veux modifier les coefficients

→ [Modifier les coefficients](./HANDICAP_CONFIG.md#-modifier-les-coefficients)  
→ [Configuration des handicaps](./HANDICAP_CONFIG.md)  

### 🐛 J'ai une erreur

→ [Troubleshooting](./GUIDE_UTILISATION_HANDICAP.md#-troubleshooting)  
→ [Dépannage](./HANDICAP_SYSTEM.md#-dépannage)  

### 📡 Je veux tester l'API

→ [Endpoints API](./HANDICAP_SYSTEM.md#-api-endpoints)  
→ [Exemple d'appel API](./RESUME_MODIFICATIONS.md#exemple-dappel-api)  
→ [Script de test](./test-handicap.ps1)  

### 💻 Je veux modifier le code

→ [Pour les développeurs](./RESUME_MODIFICATIONS.md#-pour-les-développeurs)  
→ [Architecture système](./HANDICAP_SYSTEM.md#-modifications-apportées)  
→ [Structure des fichiers](./RESUME_MODIFICATIONS.md#-structure-des-fichiers-modifiéscrés)  

---

## 📋 Tâches couantes

### ✅ Enregistrer un résultat brut

**Temps estimé** : 2 minutes

1. Ouvrir la page "Résultats" (🎯 dans la navigation)
2. Sélectionner la course
3. Sélectionner le bateau
4. Entrer le temps brut en secondes
5. Choisir la formule (PY ou TMF)
6. Cliquer "Enregistrer"

👉 **Documentation** : [Démarrage rapide](./GUIDE_UTILISATION_HANDICAP.md#-démarrage-rapide)

---

### ✅ Voir le classement finalement

**Temps estimé** : 1 minute

1. Aller à la page "Résultats"
2. Le tableau s'affiche automatiquement
3. Les positions sont triées par **temps corrigé** (orange)
4. Comparer les positions brutes vs corrigées

👉 **Documentation** : [Exemple 1](./GUIDE_UTILISATION_HANDICAP.md#exemple-1--course-avec-2-bateaux-en-py)

---

### ✅ Modifier un coefficient PY/TMF

**Temps estimé** : 5 minutes

1. Accéder à la base PostgreSQL
2. Exécuter une commande SQL
3. Valider avec une course de test

👉 **Documentation** : [Modifier les coefficients](./HANDICAP_CONFIG.md#-modifier-les-coefficients)

---

### ✅ Tester le système complètement

**Temps estimé** : 10 minutes

1. Vérifier que le backend est en marche
2. Vérifier que le frontend est en marche
3. Exécuter le script `test-handicap.ps1`
4. Vérifier les résultats

👉 **Documentation** : [test-handicap.ps1](./test-handicap.ps1)

---

### ✅ Ajouter un nouveau type de handicap

**Temps estimé** : 30 minutes (développeur)

1. Créer une méthode dans `HandicapCalculator.java`
2. Ajouter le type dans `RaceResultService.java`
3. Ajouter le bouton radio dans `RaceResults.svelte`
4. Tester

👉 **Documentation** : [Pour les développeurs](./RESUME_MODIFICATIONS.md#-pour-les-développeurs)

---

## 🎓 Niveaux de compréhension

### 🟢 Débutant (15 minutes)

1. Lire [Démarrage rapide](./GUIDE_UTILISATION_HANDICAP.md#-démarrage-rapide)
2. Voir [Exemple 1](./GUIDE_UTILISATION_HANDICAP.md#exemple-1--course-avec-2-bateaux-en-py)
3. Enregistrer un premier résultat

### 🟡 Intermédiaire (45 minutes)

1. Lire le [Guide complet](./GUIDE_UTILISATION_HANDICAP.md)
2. Comprendre les [Formules](./GUIDE_UTILISATION_HANDICAP.md#-comprendre-les-formules)
3. Consulter [FAQ](./GUIDE_UTILISATION_HANDICAP.md#-faq---questions-fréquentes)
4. Tester avec plusieurs résultats

### 🔴 Avancé (2 heures)

1. Lire l'[Architecture système](./HANDICAP_SYSTEM.md)
2. Comprendre le [Flux de données](./RESUME_MODIFICATIONS.md#-flux-de-données)
3. Étudier le [Code source](./RESUME_MODIFICATIONS.md#-structure-des-fichiers-modifiéscrés)
4. Exécuter les [Tests](./test-handicap.ps1)
5. Envisager des [Améliorations](./RESUME_MODIFICATIONS.md#-améliorations-possibles)

---

## 💬 Questions fréquentes sur la documentation

### Q: Par où je commence ?

**A**: Si vous êtes un **utilisateur** → [Guide d'utilisation](./GUIDE_UTILISATION_HANDICAP.md)  
Si vous êtes un **développeur** → [Architecture système](./HANDICAP_SYSTEM.md)  
Si vous êtes un **administrateur** → [Configuration](./HANDICAP_CONFIG.md)

### Q: Où sont les formules ?

**A**: 
- Explications : [Configuration](./HANDICAP_CONFIG.md#-comprendre-les-coefficients)
- Code : [Résumé](./RESUME_MODIFICATIONS.md#-formules-implémentées)
- Exemples : [Guide d'utilisation](./GUIDE_UTILISATION_HANDICAP.md#-comprendre-les-formules)

### Q: Comment tester ?

**A**: Voir [test-handicap.ps1](./test-handicap.ps1) ou [Architecture](./HANDICAP_SYSTEM.md#-exemple-dutilisation)

### Q: J'ai une erreur, quoi faire ?

**A**: Consulter [Troubleshooting](./GUIDE_UTILISATION_HANDICAP.md#-troubleshooting) ou [Dépannage](./HANDICAP_SYSTEM.md#-dépannage)

### Q: Où modifier le code ?

**A**: Structure des fichiers dans [Résumé](./RESUME_MODIFICATIONS.md#-structure-des-fichiers-modifiéscrés)

---

## 📞 Support et contact

- 📧 **Email** : contact@example.com
- 💬 **Issues** : GitHub Issues du projet
- 📚 **Documentation** : Voir les fichiers .md dans le répertoire racine
- 🧪 **Tests** : Exécuter `test-handicap.ps1`

---

## 📝 Informations générales

| Aspect | Détail |
|--------|--------|
| **Version** | 1.0.0 |
| **Date** | Mai 2026 |
| **Statut** | ✅ Production Ready |
| **Langage** | Java, Svelte, SQL |
| **Framework** | Spring Boot, Vite |
| **Base de données** | PostgreSQL |
| **Licence** | Propriétaire |

---

## 🗺️ Roadmap des documents

- [x] Guide d'utilisation complet
- [x] Configuration des handicaps
- [x] Architecture système
- [x] Résumé des modifications
- [x] Script de test
- [x] Index de documentation
- [ ] Vidéo tutorielle (futur)
- [ ] Wiki externe (futur)
- [ ] API documentation OpenAPI (futur)

---

## 🎯 Prochaines étapes

1. **Lire** le document approprié à votre rôle (see above)
2. **Tester** le système avec les exemples
3. **Adapter** selon vos besoins spécifiques
4. **Documenter** vos modifications
5. **Partager** les retours et idées

---

**Bonne lecture et bon usage du système ! 🏆**

*Dernière mise à jour : Mai 2026*
