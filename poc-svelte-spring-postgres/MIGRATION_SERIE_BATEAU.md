# 🔄 Migration de la Structure Série/Bateau

## Résumé des changements

La logique métier a été corrigée :

### ❌ Ancien modèle (incorrect)
- **Série** → liée à `ClasseCourse` (nom de course)
- Permettait de créer des séries avec n'importe quel type de course

### ✅ Nouveau modèle (correct)
- **Série** → liée à `ClasseBateau` (type de bateau)
- Une série est maintenant dédiée à une classe de bateau spécifique
- Ex: "Laser 2024" (classe bateau = Laser), "Catamaran Challenge" (classe bateau = Catamaran)

---

## Étapes de migration

### Étape 1 : Appliquer la migration SQL

Connectez-vous à la base PostgreSQL et exécutez :

```bash
# Avec psql
psql -U poc -d pocdb -f fix_serie_schema.sql
```

Ou copiez-collez directement dans pgAdmin :

```sql
-- Migration: Change Serie to use ClasseBateau instead of ClasseCourse
ALTER TABLE public.serie 
DROP CONSTRAINT fk_serie_classe_course;

ALTER TABLE public.serie 
RENAME COLUMN classe_course_id TO classe_bateau_id;

ALTER TABLE public.serie
ADD CONSTRAINT fk_serie_classe_bateau FOREIGN KEY (classe_bateau_id) 
REFERENCES public.classe_bateau(id);

-- Vérification
SELECT * FROM public.serie;
```

**Résultat attendu** : Les colonnes sont renommées, la contrainte de clé étrangère est mise à jour.

### Étape 2 : Arrêter et redémarrer le backend

```powershell
# Arrêtez le backend s'il est en cours d'exécution (Ctrl+C)
# Redémarrez-le
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\backend
.\mvnw.cmd clean spring-boot:run
```

Spring Boot recompilera automatiquement les changements Java.

### Étape 3 : Vérifier le frontend

Le frontend a été mis à jour pour :
- ✅ Afficher les classes de bateau au lieu des classes de course
- ✅ Demander à l'utilisateur de sélectionner une classe de bateau pour créer une série
- ✅ Afficher le nom de la classe de bateau dans le badge de la série

Rechargez le frontend (pas besoin de le redémarrer) : `F5` dans votre navigateur.

---

## Tests de validation

### Test 1 : Créer une nouvelle série

1. Allez à la page **Courses**
2. Dans le formulaire "➕ Créer une nouvelle série" :
   - Entrez un nom : `Laser 2024`
   - Sélectionnez la classe de bateau : `Laser` (au lieu de classe de course)
   - Cliquez sur `✅ Créer`

**Résultat attendu** : La série "Laser 2024" apparaît avec le badge "Laser"

### Test 2 : Affichage des séries existantes

1. Allez à la page **Courses**
2. Vérifiez que le badge de chaque série affiche le **nom du bateau** (ex: "Laser", "Catamaran")

**Résultat attendu** : Les badges affichent correctement les classes de bateau

### Test 3 : Ajouter une course à une série

1. Sélectionnez une série (ex: "Laser 2024")
2. Cliquez sur "➕ Ajouter une course à cette série"
3. Sélectionnez une course dans la dropdown

**Résultat attendu** : Les bateaux Laser peuvent s'inscrire à ces courses

---

## Fichiers modifiés

### Backend
- ✅ `src/main/java/com/example/backend/Serie.java` - Changé `classeCourse` → `classeBateau`
- ✅ `src/main/java/com/example/backend/SerieController.java` - Mis à jour les endpoints

### Frontend
- ✅ `frontend/src/routes/courses.svelte` - Mis à jour les formulaires et affichages

### Base de données
- ✅ `fix_serie_schema.sql` - Script de migration (à appliquer manuellement)

---

## Résolution des problèmes

### Erreur : "Classe bateau introuvable"
**Solution** : Le backend n'a pas redémarré après les changements Java. Redémarrez avec `.\mvnw.cmd clean spring-boot:run`

### Erreur : "constraint fk_serie_classe_course does not exist"
**Solution** : La contrainte a déjà été supprimée. C'est normal si vous relancez le script.

### Les badges affichent encore les noms de courses
**Solution** : Rechargez le navigateur avec `Ctrl+Shift+R` (rechargement dur)

---

## Points importants

⚠️ **Attention** : Si vous aviez déjà des séries en base de données avec des `classe_course_id`, ils doivent être convertis manuellement en `classe_bateau_id` ou supprimés :

```sql
-- Pour voir les séries actuelles
SELECT id, nom_serie, classe_bateau_id FROM public.serie;

-- Pour supprimer les anciennes séries si nécessaire
DELETE FROM public.serie WHERE classe_bateau_id IS NULL;
```

---

## Prochaines étapes

1. ✅ Appliquer la migration SQL
2. ✅ Redémarrer le backend
3. ✅ Recharger le navigateur
4. ✅ Créer une nouvelle série avec une classe de bateau
5. ✅ Vérifier que les cours et inscriptions de bateaux fonctionnent correctement
