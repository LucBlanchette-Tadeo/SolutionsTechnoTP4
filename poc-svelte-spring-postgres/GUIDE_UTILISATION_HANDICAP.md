# 🚀 Guide complet : Utiliser le système de Handicap automatique

## 📌 À qui s'adresse ce guide ?

✅ Les **arbitres** qui veulent enregistrer les résultats bruts  
✅ Les **organisateurs** qui vont classer les bateaux  
✅ Les **administrateurs** qui configurent les coefficients  
✅ Les **participants** qui veulent suivre les résultats

---

## 🎬 Démarrage rapide

### Étape 1 : Accéder à l'application

```
🌐 http://localhost:5173
📧 Identifiants : admin / [votre mot de passe]
```

### Étape 2 : Aller à la page Résultats

```
Navigation supérieure → 🎯 Résultats
```

### Étape 3 : Enregistrer un résultat brut

1. **Sélectionner la course**
   - Dropdown : "Sélectionner une course"
   - Choisir parmi les courses existantes (ex. "Série 1")

2. **Sélectionner le bateau**
   - Dropdown : "Sélectionner un bateau"
   - Affiche les bateaux disponibles (ex. "Mon Laser #101")

3. **Saisir le temps brut**
   - Entrer le temps en **secondes**
   - Exemple : 3600 = 1 heure
   - Affichage auto en HH:MM:SS (ex. "01:00:00")

4. **Choisir la formule de handicap**
   - ⭐ **PY** (Portsmouth Yardstick) : Recommandé pour courses officielles
   - ⭐ **TMF** (Time Multiplying Factor) : Pour courses simples

5. **Cliquer sur "Enregistrer le résultat"**

### Étape 4 : Voir les résultats classés

Le tableau affiche automatiquement :
- **Position** : Classement par temps corrigé ✅
- **Bateau** : Nom du bateau
- **Temps brut** : Ce qui a été enregistré
- **Handicap** : PY ou TMF utilisé
- **Temps corrigé** : Le temps final (orange = important)

---

## 💡 Exemples pratiques

### Exemple 1 : Course avec 2 bateaux en PY

**Données de départ** :
```
Classe Laser : PY = 1050
Classe J70   : PY = 1000
```

**Enregistrement** :

```
1️⃣  Course: Série 1
    Bateau: Laser #101
    Temps brut: 3600 secondes (01:00:00)
    Handicap: PY
    ✓ Enregistrer

    → Résultat: 01:00:00 × 1000 ÷ 1050 = 57:08
```

```
2️⃣  Course: Série 1
    Bateau: J70 #201
    Temps brut: 3500 secondes (00:58:20)
    Handicap: PY
    ✓ Enregistrer

    → Résultat: 00:58:20 × 1000 ÷ 1000 = 58:20
```

**Classement final** :

| 🥇 | Laser #101  | 01:00:00 | PY   | **00:57:08** |
|----|-------------|----------|------|--------------|
| 🥈 | J70 #201    | 00:58:20 | PY   | **00:58:20** |

✨ **Résultat** : Le Laser gagne MÊME s'il a mis plus de temps brut !

---

### Exemple 2 : Course mixte avec TMF

**Coefficient TMF** :
```
Bateau léger : TMF = 0.95
Bateau lourd : TMF = 1.05
```

**Résultats** :

```
Bateau léger : 3600 sec × 0.95 = 3420 sec
Bateau lourd : 3600 sec × 1.05 = 3780 sec
```

**Classement** : Bateau léger 🏆

---

## 🎓 Comprendre les formules

### ⭐ PY (Portsmouth Yardstick)

```
Temps Corrigé = (Temps Brut en sec × 1000) / PY
```

**Fonctionnement** :
- PY = 1000 → Bateau de référence
- PY > 1000 → Bateau rapide (temps raccourci)
- PY < 1000 → Bateau lent (temps allongé)

**Exemple** :
- Bateau rapide (PY=1200) : 3600 × 1000 ÷ 1200 = 3000 sec (plus court!)
- Bateau lent (PY=800) : 3600 × 1000 ÷ 800 = 4500 sec (plus long!)

### ⭐ TMF (Time Multiplying Factor)

```
Temps Corrigé = Temps Brut en sec × TMF
```

**Fonctionnement** :
- TMF = 1.0 → Bateau de référence
- TMF < 1.0 → Bateau rapide (temps raccourci)
- TMF > 1.0 → Bateau lent (temps allongé)

**Exemple** :
- Bateau rapide (TMF=0.95) : 3600 × 0.95 = 3420 sec (plus court!)
- Bateau lent (TMF=1.05) : 3600 × 1.05 = 3780 sec (plus long!)

---

## 📊 FAQ - Questions fréquentes

### ❓ Q: Pourquoi mon bateau a un temps PLUS COURT en corrigé ?

**A**: Parce qu'il est handicapé comme "rapide" ! ⚡

- Bateau rapide → coefficient qui **réduit** le temps
- Bateau lent → coefficient qui **augmente** le temps

C'est ça qui rend les courses équitables !

### ❓ Q: Je peux modifier le handicap d'un bateau ?

**A**: Non directement dans l'interface. Il faut :
1. Contacter un administrateur
2. Modifier dans la base de données
3. Les nouveaux résultats utiliseront le coefficient à jour

```sql
UPDATE classe_bateau SET py = 1100 WHERE nom_classe = 'Laser';
```

### ❓ Q: Qu'est-ce que "Position brute" vs "Position corrigée" ?

**A**: 
- **Position brute** = Classement sur les temps bruts (avant correction)
- **Position corrigée** = Classement sur les temps corrigés (résultat final)

Le tableau montre TOUJOURS le classement corrigé (c'est celui qui compte!)

### ❓ Q: Peux-je utiliser PY et TMF dans la même course ?

**A**: OUI ! Tu peux même mélanger :
- Bateau A : Enregistrer en PY
- Bateau B : Enregistrer en TMF
- Ils se classent ensemble dans le tableau final

(C'est peu courant mais techniquement possible)

### ❓ Q: Et si j'enregistre mal le temps brut ?

**A**: Pas de souci ! Cliquer sur 🗑️ pour supprimer le résultat, puis remplir le formulaire à nouveau.

### ❓ Q: Le classement se met à jour en temps réel ?

**A**: OUI ! Dès que tu ajoutes/supprimes un résultat, le tableau se réorganise automatiquement.

---

## 🔧 Troubleshooting

### 🔴 Message : "Veuillez sélectionner une course et un bateau"

**Cause** : Tu as oublié de choisir l'une ou l'autre  
**Solution** : Complète les deux dropdowns avant de cliquer

### 🔴 Message : "PY non défini pour cette classe de bateau"

**Cause** : Le coefficient PY est absent de la base de données  
**Solution** : Contact administrateur pour ajouter le coefficient

```sql
UPDATE classe_bateau SET py = 1050 WHERE nom_classe = 'Laser';
```

### 🔴 Erreur : "ClasseCourse non trouvée"

**Cause** : La course sélectionnée n'existe pas ou a été supprimée  
**Solution** : Créer la course d'abord (page "Courses") puis revenir ici

### 🔴 Le tableau ne s'affiche pas

**Cause** : Aucun résultat n'a été enregistré pour cette course  
**Solution** : Enregistrer au moins un résultat via le formulaire

### 🔴 Le backend ne répond pas

**Cause** : Serveur Spring pas en cours d'exécution  
**Solution** :
```bash
cd backend
./mvnw spring-boot:run
```

---

## 📱 Utilisation sur mobile

L'interface est **responsive** et fonctionne sur téléphone !

**Sur petit écran** :
- Les champs se mettent en pile (l'un après l'autre)
- Les boutons restent accessibles
- Le tableau se scrolle horizontalement si nécessaire

---

## 🎨 Astuces UI/UX

### Saisie rapide du temps

Au lieu de calculer les secondes, tu peux utiliser ce convertisseur :

```
1 minute   = 60 secondes
5 minutes  = 300 secondes
10 min     = 600 secondes
30 min     = 1800 secondes
1 heure    = 3600 secondes
1h 30min   = 5400 secondes
2 heures   = 7200 secondes
```

### Copier un résultat existant

Si tu dois enregistrer plusieurs bateaux avec un temps similaire :
1. Enregistrer le premier
2. Changer juste le bateau dans le formulaire
3. Ajuster le temps légèrement
4. Enregistrer

### Suivre un bateau en direct

Tu veux voir comment un bateau spécifique se classe ?
1. Enregistrer ses résultats progressivement
2. Regarder sa position changer dans le tableau
3. Compare avec les autres

---

## ⚙️ Configuration des handicaps

### Voir les coefficients actuels

Pour voir les PY et TMF assignés à chaque classe :

```sql
SELECT nom_classe, py, tmf FROM classe_bateau;
```

**Output attendu** :
```
nom_classe | py     | tmf
-----------|--------|-------
Laser      | 1050   | 0.9800
J70        | 1000   | 0.9900
```

### Modifier les coefficients

Un administrateur peut mettre à jour les coefficients directement en SQL :

```sql
-- Mise à jour PY pour Laser
UPDATE classe_bateau SET py = 1075 WHERE nom_classe = 'Laser';

-- Mise à jour TMF pour J70
UPDATE classe_bateau SET tmf = 0.9850 WHERE nom_classe = 'J70';
```

*(À l'avenir : Une interface d'admin sera ajoutée)*

---

## 📖 Ressources

### En savoir plus sur les handicaps

- **Portsmouth Yardstick** : https://en.wikipedia.org/wiki/Portsmouth_Yardstick
- **Racing rules of sailing** : https://www.iyru.org/
- **Calcul manuel** : Voir HANDICAP_CONFIG.md

### Documents dans le projet

- 📄 **HANDICAP_SYSTEM.md** : Architecture technique
- 📄 **HANDICAP_CONFIG.md** : Configuration des coefficients
- 📄 **test-handicap.ps1** : Script de test API

---

## ✅ Checklist avant une vraie course

- [ ] Tous les bateaux ont un coefficient PY/TMF défini
- [ ] Les coefficients correspondent aux règlements utilisés
- [ ] Test : Enregistrer 2-3 résultats et vérifier le classement
- [ ] Vérifier que le backend est accessible
- [ ] Vérifier que la base de données est à jour
- [ ] Communiquer aux participants la formule utilisée
- [ ] Imprimer/exporter les résultats finals

---

## 🎯 Prochaines étapes

Fonctionnalités **futures** à envisager :

- 📱 Export des résultats en PDF
- 📊 Graphiques de performance
- 🏆 Classement général multi-courses
- 🔐 Contrôle d'accès par rôle
- ⚙️ Interface d'admin pour coefficients
- 📅 Historique des courses
- 🔔 Notifications en temps réel

---

**Version** : 1.0  
**Dernière mise à jour** : Mai 2026  
**Support** : Contacter l'administrateur système
