# Structure des Pages

## 📊 Page: **Séries** (`series.svelte`)
- **Objective** : Gérer les séries
- **Fonctionnalités** :
  - ✅ Créer une nouvelle série (nom, classe_bateau, nombre de courses)
  - ✅ Lister les séries existantes
  - ✅ Pour chaque série sélectionnée :
    - ✅ Ajouter des `classe_course` (cours/compétitions) dans la série
    - ✅ Lister les `classe_course` liées à la série
    - ✅ Gérer les inscriptions de bateaux pour chaque classe_course

**Base de données** :
- `serie` (id, nom_serie, classe_bateau_id)
- `serie_classe_course` (id, serie_id, classe_course_id, ordre_course)
- `classe_bateau` (classes de bateau)

---

## 🏁 Page: **Courses** (`courses.svelte`)
- **Objective** : Créer et gérer les COURSES
- **Fonctionnalités** :
  - ✅ Créer une nouvelle `classe_course` (course) avec :
    - Nom de la course
    - Type (monotype, flotte, croisière)
    - Type de handicap (PY, TMF, ou aucun)
    - Handicap min/max (optionnel)
  - ✅ Lister les `classe_course` existantes
  - ✅ Pour chaque `classe_course` sélectionnée :
    - ✅ Ajouter les résultats (`race_result`) des bateaux
    - ✅ Afficher le tableau des résultats (temps, positions, etc.)

**Base de données** :
- `classe_course` (id, nom_classe_course, type_classe, type_handicap, handicap_min, handicap_max)
- `race_result` (id, classe_course_id, bateau_id, temps_brut_secondes, temps_corrige_secondes, position_brute, position_corrigee, type_handicap, statut, date_course)

---

## 📈 Page: **Résultats** (À créer - `results.svelte`)
- **Objective** : Ajouter les résultats des courses
- **Fonctionnalités** :
  - Sélectionner une `classe_course` (course)
  - Pour chaque bateau inscrit, ajouter/éditer :
    - Temps brut (secondes)
    - Type de handicap
    - Position brute
    - Position corrigée (calculée automatiquement)
    - Statut (DNF, DNS, etc.)

**Base de données** :
- `race_result` (voir ci-dessus)

---

## Clarification des Termes

| Terme | Table | Définition |
|-------|-------|-----------|
| **Série** | `serie` | Collection de courses regroupées ensemble (ex: "Championnat d'été 2026") |
| **Course** | `classe_course` | Compétition individuelle (ex: "Monotype Laser 2026") |
| **Classe de bateau** | `classe_bateau` | Type de bateau (ex: "Laser", "J70") |
| **Bateau** | `bateau` | Instance d'un bateau (ex: "Laser #42") |
| **Résultat** | `race_result` | Temps et position d'un bateau dans une course |
| **Classe de course** | `classe_course` | Même que "Course" dans ce système |

---

## Flux Utilisateur

1. **Créer des courses** (Page Courses)
   - Aller dans **Courses**
   - Créer une nouvelle `classe_course` (course)

2. **Organiser en séries** (Page Séries)
   - Aller dans **Séries**
   - Créer une nouvelle série
   - Ajouter les `classe_course` (courses) créées à la série

3. **Ajouter les résultats** (Page Résultats - futur)
   - Aller dans **Résultats**
   - Sélectionner une course
   - Ajouter les temps et positions des bateaux

---

## État Actuel de Refactorisation

✅ **Page Séries** : Fonctionnelle (gère les séries et les classe_course liées)
✅ **Page Courses** : Refactorisée (crée et gère les classe_course, ajoute les résultats)
⏳ **Page Résultats** : À créer (gestion dédiée des résultats)
