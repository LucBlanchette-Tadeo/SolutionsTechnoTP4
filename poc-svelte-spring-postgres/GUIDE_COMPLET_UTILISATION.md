# 📚 Guide d'Utilisation Complet - Système de Handicap

## Table des matières

1. [Première utilisation](#première-utilisation)
2. [Gestion des classes de bateaux](#gestion-des-classes-de-bateaux)
3. [Gestion des bateaux](#gestion-des-bateaux)
4. [Gestion des classes de courses](#gestion-des-classes-de-courses)
5. [Gestion des séries](#gestion-des-séries)
6. [Saisie des résultats](#saisie-des-résultats)
7. [Consultation des résultats](#consultation-des-résultats)
8. [Interprétation des handicaps](#interprétation-des-handicaps)

---

## Première utilisation

### 1. Démarrer l'application

Ouvrez trois terminaux PowerShell :

#### Terminal 1 : Base de données
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\db
docker-compose up

# Affichage attendu :
# postgres_1  | 2024-05-06 17:00:00.000 UTC [1] LOG:  database system is ready to accept connections
```

#### Terminal 2 : Backend
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\backend
mvnw.cmd spring-boot:run

# Affichage attendu :
# 2024-05-06 17:00:05.123  INFO 12345 --- [main] ... Tomcat started on port(s): 8080 (http)
```

#### Terminal 3 : Frontend
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend
npm run dev

# Affichage attendu :
#   Local: http://localhost:5173
```

### 2. Accéder à l'interface

Ouvrez un navigateur et allez à : **http://localhost:5173**

Vous verrez l'écran de connexion.

### 3. Se connecter

```
Utilisateur : admin
Mot de passe : 123456
```

⚠️ **Important** : Changez ce mot de passe immédiatement après votre première connexion !

### 4. Interface principale

Après la connexion, vous verrez le menu principal avec les options :

```
┌─────────────────────────────────────────┐
│         🏖️ Gestion des Courses         │
├─────────────────────────────────────────┤
│  🚤 Bateaux                             │
│  📊 Classes                             │
│  🎯 Résultats                           │
│  📋 Courses                             │
│  👥 Séries                              │
│  🏆 Inscriptions                        │
└─────────────────────────────────────────┘
```

---

## Gestion des classes de bateaux

Les classes de bateaux (ex: Laser, J70) sont les **types de bateaux** avec leurs handicaps.

### Afficher les classes

1. Cliquer sur **"Classes"** dans le menu

Vous verrez un tableau :

```
┌──────┬─────────────┬────────┬─────────┐
│ ID   │ Nom Classe  │ PY     │ TMF     │
├──────┼─────────────┼────────┼─────────┤
│ 1    │ Laser       │ 121.00 │ 1.1900  │
│ 2    │ J70         │ 102.50 │ 1.1450  │
├──────┼─────────────┼────────┼─────────┤
│ Créer une classe                      │
└──────┴─────────────┴────────┴─────────┘
```

### Créer une nouvelle classe

1. Cliquer sur **"Créer une classe"**
2. Remplir le formulaire :

```
┌─────────────────────────────────┐
│  Créer une classe de bateau     │
├─────────────────────────────────┤
│                                 │
│ Nom classe :                    │
│ ┌─────────────────────────────┐ │
│ │ Optimist                    │ │
│ └─────────────────────────────┘ │
│                                 │
│ PY (Portsmouth Yardstick) :     │
│ ┌─────────────────────────────┐ │
│ │ 175.00                      │ │
│ └─────────────────────────────┘ │
│                                 │
│ TMF (Time Multiplying Factor) : │
│ ┌─────────────────────────────┐ │
│ │ 1.3200                      │ │
│ └─────────────────────────────┘ │
│                                 │
│ [Créer]  [Annuler]              │
└─────────────────────────────────┘
```

**Exemple de PY et TMF pour différentes classes :**

| Classe       | PY     | TMF    | Type      |
|:-------------|:-------|:-------|:----------|
| Optimist     | 175.00 | 1.3200 | Monotype  |
| Laser        | 121.00 | 1.1900 | Monotype  |
| J70          | 102.50 | 1.1450 | IMS 50    |
| 49er         | 78.50  | 1.0850 | Navire    |
| Nacra 17     | 87.00  | 1.1100 | Catamaran |

3. Cliquer **"Créer"**

La classe est maintenant disponible pour créer des bateaux.

---

## Gestion des bateaux

Les bateaux sont les **instances concrètes** d'une classe (ex: "Laser - Jean - SV123").

### Afficher les bateaux

1. Cliquer sur **"Bateaux"** dans le menu

Vous verrez un tableau :

```
┌────┬───────────────────┬────────────┬──────────────┬──────────┐
│ ID │ Nom Bateau        │ N° Voile   │ Nom Barreur  │ Classe   │
├────┼───────────────────┼────────────┼──────────────┼──────────┤
│ 1  │ LASER BLU         │ 1          │ Jean         │ Laser    │
│ 2  │ LASER RED         │ 2          │ Marie        │ Laser    │
│ 3  │ J70 VICTOIRE      │ 70         │ Pierre       │ J70      │
├────┼───────────────────┼────────────┼──────────────┼──────────┤
│ Créer un bateau                                                │
└────┴───────────────────┴────────────┴──────────────┴──────────┘
```

### Créer un bateau

1. Cliquer sur **"Créer un bateau"**
2. Remplir le formulaire :

```
┌──────────────────────────────────────┐
│        Créer un bateau               │
├──────────────────────────────────────┤
│                                      │
│ Classe bateau * :                    │
│ ┌──────────────────────────────────┐ │
│ │ ▼ Laser                          │ │
│ │   Optimist                       │ │
│ │   J70                            │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Nom bateau :                         │
│ ┌──────────────────────────────────┐ │
│ │ LASER BLU                        │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Numéro voile * :                     │
│ ┌──────────────────────────────────┐ │
│ │ 1                                │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Nom barreur :                        │
│ ┌──────────────────────────────────┐ │
│ │ Jean                             │ │
│ └──────────────────────────────────┘ │
│                                      │
│ [Créer]  [Annuler]                   │
└──────────────────────────────────────┘
```

3. Cliquer **"Créer"**

---

## Gestion des classes de courses

Les classes de courses sont les **catégories de compétition** (ex: "Laser Loisir", "J70 Championship").

### Afficher les classes de courses

1. Cliquer sur **"Classes"** dans le menu (voir le tableau dans "Classes")

### Créer une classe de course

1. Cliquer sur **"Créer une classe"**
2. Remplir le formulaire :

```
┌──────────────────────────────────────┐
│     Créer une classe de course       │
├──────────────────────────────────────┤
│                                      │
│ Nom classe course * :                │
│ ┌──────────────────────────────────┐ │
│ │ Laser Loisir                     │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Type classe * :                      │
│ ┌──────────────────────────────────┐ │
│ │ ▼ MONOTYPE                       │ │
│ │   JAUGE                          │ │
│ │   CROISIERE                      │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Type handicap * :                    │
│ ┌──────────────────────────────────┐ │
│ │ ▼ PY                             │ │
│ │   TMF                            │ │
│ │   AUTRE                          │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Handicap min :                       │
│ ┌──────────────────────────────────┐ │
│ │ 100.00                           │ │
│ └──────────────────────────────────┘ │
│                                      │
│ Handicap max :                       │
│ ┌──────────────────────────────────┐ │
│ │ 150.00                           │ │
│ └──────────────────────────────────┘ │
│                                      │
│ [Créer]  [Annuler]                   │
└──────────────────────────────────────┘
```

**Explications des champs :**

- **Nom classe course** : Nom visible (ex: "Laser Loisir", "J70 Championship")
- **Type classe** : MONOTYPE (même bateau) ou JAUGE (bateaux différents)
- **Type handicap** : PY ou TMF
- **Handicap min/max** : Limites optionnelles pour le handicap

3. Cliquer **"Créer"**

---

## Gestion des séries

Les séries regroupent les courses d'une même saison ou événement.

### Afficher les séries

1. Cliquer sur **"Séries"** dans le menu

Vous verrez un tableau :

```
┌────┬─────────────────────┬──────────────────────┐
│ ID │ Nom Série           │ Classes              │
├────┼─────────────────────┼──────────────────────┤
│ 1  │ Série d'été 2024    │ Laser Loisir (2)     │
│    │                     │ J70 Championship (1) │
├────┼─────────────────────┼──────────────────────┤
│ Créer une série                                │
└────┴─────────────────────┴──────────────────────┘
```

### Créer une série

1. Cliquer sur **"Créer une série"**
2. Remplir le formulaire :

```
┌──────────────────────────────────────┐
│       Créer une série                │
├──────────────────────────────────────┤
│                                      │
│ Nom série * :                        │
│ ┌──────────────────────────────────┐ │
│ │ Championnat d'été 2024           │ │
│ └──────────────────────────────────┘ │
│                                      │
│ [Créer]  [Annuler]                   │
└──────────────────────────────────────┘
```

3. Cliquer **"Créer"**

### Ajouter des classes à une série

Après création de la série :

```
┌──────────────────────────────────────┐
│    Championnat d'été 2024            │
├──────────────────────────────────────┤
│                                      │
│ Ajouter une classe de course :       │
│ ┌──────────────────────────────────┐ │
│ │ ▼ Laser Loisir                   │ │
│ │   J70 Championship               │ │
│ │   Optimist                       │ │
│ └──────────────────────────────────┘ │
│                                      │
│ [Ajouter]                            │
│                                      │
├──────────────────────────────────────┤
│ Classes de la série :                │
│                                      │
│ • Laser Loisir (1 race)              │
│ • J70 Championship (0 races)         │
│                                      │
└──────────────────────────────────────┘
```

---

## Saisie des résultats

### Accéder à la page résultats

1. Cliquer sur **"Résultats"** dans le menu principal

```
┌────────────────────────────────────────────────┐
│           📊 RÉSULTATS DE COURSE               │
├────────────────────────────────────────────────┤
│                                                │
│ [Ajouter un résultat]  [Exporter]  [Imprimer] │
│                                                │
├────────────────────────────────────────────────┤
│ Aucun résultat pour le moment                  │
│                                                │
└────────────────────────────────────────────────┘
```

### Ajouter un résultat

1. Cliquer sur **"Ajouter un résultat"**

```
┌────────────────────────────────────────────────┐
│     Ajouter un résultat de course              │
├────────────────────────────────────────────────┤
│                                                │
│ Classe course * :                              │
│ ┌──────────────────────────────────────────┐  │
│ │ ▼ Laser Loisir                           │  │
│ │   J70 Championship                       │  │
│ └──────────────────────────────────────────┘  │
│                                                │
│ Bateau * :                                     │
│ ┌──────────────────────────────────────────┐  │
│ │ ▼ 1 - Laser - Jean                       │  │
│ │   2 - Laser - Marie                      │  │
│ │   3 - J70 - Pierre                       │  │
│ └──────────────────────────────────────────┘  │
│                                                │
│ Temps brut (secondes) * :                      │
│ ┌──────────────────────────────────────────┐  │
│ │ 3600                                     │  │
│ └──────────────────────────────────────────┘  │
│                                                │
│ Position brute * :                             │
│ ┌──────────────────────────────────────────┐  │
│ │ 1                                        │  │
│ └──────────────────────────────────────────┘  │
│                                                │
│ Date course (optionnel) :                      │
│ ┌──────────────────────────────────────────┐  │
│ │ 2024-05-06                               │  │
│ └──────────────────────────────────────────┘  │
│                                                │
│ [Ajouter]  [Annuler]                          │
│                                                │
└────────────────────────────────────────────────┘
```

**Exemple avec 3 bateaux :**

| Bateau              | Temps brut (sec) | Position brute |
|:---|---:|---:|
| 1 - Laser - Jean    | 3600 | 1 |
| 2 - Laser - Marie   | 3720 | 2 |
| 3 - J70 - Pierre    | 3500 | 3 |

2. Pour chaque bateau, cliquer **"Ajouter"**

---

## Consultation des résultats

### Affichage des résultats

Après saisie, vous verrez un tableau :

```
┌────┬──────────────────┬──────────────┬────────┬──────────┬──────────┬──────────┐
│ ID │ Bateau           │ Temps brut   │ Pos.   │ Temps    │ Pos.     │ Statut   │
│    │                  │ (secondes)   │ brute  │ corrigé  │ corrigée │          │
├────┼──────────────────┼──────────────┼────────┼──────────┼──────────┼──────────┤
│ 1  │ 1 - Laser - Jean │ 3600         │ 1      │ 436.80   │ 1        │ ✅ OK    │
│ 2  │ 2 - Laser - Marie│ 3720         │ 2      │ 450.72   │ 2        │ ✅ OK    │
│ 3  │ 3 - J70 - Pierre │ 3500         │ 3      │ 358.75   │ 3        │ ✅ OK    │
└────┴──────────────────┴──────────────┴────────┴──────────┴──────────┴──────────┘
```

### Interprétation

**Exemple avec Laser (PY = 121.00) :**

```
Temps brut = 3600 secondes (1 heure)
Temps corrigé = Temps brut × (PY / 1000)
              = 3600 × (121.00 / 1000)
              = 3600 × 0.121
              = 436.80 secondes
```

**Exemple avec J70 (PY = 102.50) :**

```
Temps brut = 3500 secondes
Temps corrigé = 3500 × (102.50 / 1000)
              = 3500 × 0.1025
              = 358.75 secondes
```

Le J70 a un temps corrigé plus court car son PY est plus bas (handicap plus avantageux).

---

## Interprétation des handicaps

### Portsmouth Yardstick (PY)

**Formule** :
```
Temps corrigé = Temps brut × (PY / 1000)
```

**Interprétation** :
- **PY bas** = Handicap avantageux (bateau rapide)
- **PY haut** = Handicap désavantageux (bateau lent)

**Exemple** :
- Laser : PY = 121 → Handicap moins avantageux
- J70 : PY = 102.5 → Handicap plus avantageux (plus rapide)

### Time Multiplying Factor (TMF)

**Formule** :
```
Temps corrigé = Temps brut × TMF
```

**Interprétation** :
- **TMF bas** = Handicap avantageux
- **TMF haut** = Handicap désavantageux

**Exemple** :
- Laser : TMF = 1.1900 → Le temps est multiplié par 1.19
- J70 : TMF = 1.1450 → Le temps est multiplié par 1.145 (moins pénalisé)

### Classement corrigé

Le classement corrigé est toujours **du temps corrigé le plus court au plus long** :

```
Position corrigée 1 → Temps corrigé le plus court (gagnant)
Position corrigée 2 → Deuxième temps corrigé
Position corrigée 3 → Troisième temps corrigé
...
```

**Exemple complet :**

```
Résultats bruts :
Position 1 : J70 - Temps : 3500 sec (plus rapide mais gros handicap)
Position 2 : Laser - Temps : 3600 sec (plus lent mais handicap meilleur)

Résultats corrigés (PY) :
J70   : 3500 × (102.50 / 1000) = 358.75 sec → Position corrigée 1 ✅ GAGNANT
Laser : 3600 × (121.00 / 1000) = 436.80 sec → Position corrigée 2

Le Laser a terminé en premier chronologiquement mais le J70 gagne après application du handicap !
```

---

## 🎯 Résumé des étapes principales

### Première utilisation
1. ✅ Démarrer les 3 services (DB, Backend, Frontend)
2. ✅ Ouvrir http://localhost:5173
3. ✅ Se connecter (admin / 123456)
4. ✅ Changer le mot de passe

### Créer une compétition
1. ✅ Créer les **classes de bateaux** (Laser, J70, etc.) avec PY/TMF
2. ✅ Créer les **bateaux** (instances concrètes)
3. ✅ Créer une **classe de course** (ex: "Laser Loisir")
4. ✅ Créer une **série** (ex: "Championnat d'été")
5. ✅ Ajouter la classe de course à la série

### Organiser une course
1. ✅ Aller à **Résultats**
2. ✅ Ajouter les résultats bruts (temps pour chaque bateau)
3. ✅ **Les handicaps sont calculés automatiquement** ✨
4. ✅ Consulter le classement corrigé

---

**Dernière mise à jour** : Mai 2026  
**Version** : 1.0.0
