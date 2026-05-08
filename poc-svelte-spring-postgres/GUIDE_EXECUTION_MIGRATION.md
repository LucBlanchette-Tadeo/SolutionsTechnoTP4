# 🚀 Guide d'exécution - Migration Série/Bateau

## ⚡ Résumé rapide (5 minutes)

```powershell
# 1. Appliquer la migration SQL
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres
.\apply-migration.ps1

# 2. Redémarrer le backend
cd backend
.\mvnw.cmd clean spring-boot:run

# 3. Recharger le frontend
# - Allez à http://localhost:5233
# - Appuyez sur Ctrl+Shift+R (rechargement dur)

# 4. Tester
# - Allez à la page "Courses"
# - Créez une nouvelle série
```

---

## 📋 Guide détaillé étape par étape

### Étape 1 : Vérifier l'état actuel

```powershell
# Ouvrez PowerShell et vérifiez que psql est disponible
psql --version
# Résultat attendu: psql (PostgreSQL) 16.x
```

Si `psql: command not found`, ajoutez PostgreSQL à votre PATH ou utilisez pgAdmin.

### Étape 2 : Appliquer la migration

**Option A : Script PowerShell (recommandé)**
```powershell
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres
.\apply-migration.ps1
# Suivez les instructions
```

**Option B : Manuellement via psql**
```powershell
psql -U poc -d pocdb -h localhost -p 5433 -f fix_serie_schema.sql
```

**Option C : Via pgAdmin**
1. Ouvrez pgAdmin
2. Connectez-vous à `pocdb` (user: `poc`, password: `poc`)
3. Ouvrez l'éditeur de requête (SQL)
4. Collez le contenu de `fix_serie_schema.sql`
5. Exécutez (F5)

**Résultat attendu** :
```
Migration completed successfully!
 status
--------
 OK
(1 row)

 id | nom_serie | classe_bateau_id | nombre_courses | nombre_courses_a_compter
----+-----------+------------------+----------------+---------------------------
```

### Étape 3 : Redémarrer le backend

```powershell
# Arrêtez le backend s'il est en cours (Ctrl+C)

# Redémarrez avec rechargement des dépendances
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\backend
.\mvnw.cmd clean spring-boot:run

# Attendez les messages:
# [INFO] Building backend 0.0.1-SNAPSHOT
# ...
# [INFO] BUILD SUCCESS
# ...
# Tomcat started on port(s): 8080
```

### Étape 4 : Vérifier le frontend

```powershell
# Dans un autre terminal
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\frontend
npm run dev
```

### Étape 5 : Tester dans le navigateur

Allez à `http://localhost:5233` et :

1. **Rechargement dur** : `Ctrl+Shift+R` (Windows) ou `Cmd+Shift+R` (Mac)

2. **Naviguez à "Courses"**

3. **Vérifiez le formulaire** :
   - La dropdown affiche-t-elle "Laser", "Catamaran", etc. ? ✅
   - (Et non "Série 1", "Série 2", etc.)

4. **Créez une série** :
   - Nom : `Laser 2024`
   - Classe bateau : `Laser`
   - Cliquez `✅ Créer`

5. **Vérifiez le résultat** :
   - La série "Laser 2024" apparaît-elle ? ✅
   - Le badge affiche-t-il "Laser" ? ✅

---

## ✅ Checklist de validation

### Base de données
- [ ] Migration SQL appliquée sans erreur
- [ ] Colonne `classe_bateau_id` existe dans la table `serie`
- [ ] Colonne `classe_course_id` n'existe plus dans la table `serie`
- [ ] Contrainte `fk_serie_classe_bateau` existe

```sql
-- Vérifier avec cette requête:
SELECT * FROM information_schema.table_constraints 
WHERE table_name = 'serie' AND constraint_type = 'FOREIGN KEY';
```

### Backend
- [ ] Pas d'erreur à la compilation
- [ ] Pas d'erreur de démarrage
- [ ] Logs montrent "Tomcat started on port(s): 8080"
- [ ] API répond correctement

```bash
# Vérifier l'API:
curl http://localhost:8080/api/series
# Résultat: liste des séries (peut être vide [])
```

### Frontend
- [ ] Page "Courses" se charge
- [ ] Dropdown "Créer une nouvelle série" affiche les classes de bateau
- [ ] Créer une série fonctionne
- [ ] Badge affiche le nom de la classe bateau

---

## 🆘 Résolution des problèmes

### Problème : "Migration failed: Column classe_course_id does not exist"

**Cause** : La colonne a déjà été supprimée ou la base n'est pas à jour

**Solutions** :
```sql
-- Vérifier l'état actuel
SELECT * FROM information_schema.columns 
WHERE table_name = 'serie';

-- Si classe_bateau_id existe déjà, c'est bon:
SELECT * FROM serie;
```

---

### Problème : "Invalid foreign key fk_serie_classe_bateau"

**Cause** : La table `classe_bateau` n'a pas les bonnes données

**Solution** :
```sql
-- Vérifier les classes de bateau
SELECT id, nom_classe FROM classe_bateau;

-- Vérifier les séries
SELECT id, nom_serie, classe_bateau_id FROM serie;
```

---

### Problème : "Build error in backend"

**Cause** : Les classes Java ne compilent pas

**Solution** :
1. Vérifiez les erreurs dans la console
2. Recherchez `ERROR` dans les logs
3. Assurez-vous que les fichiers Java ont été modifiés :
   - `Serie.java` - contient `classeBateau`
   - `SerieController.java` - contient `ClasseBateauRepository`

---

### Problème : Frontend affiche toujours les anciennes données

**Cause** : Le cache du navigateur n'a pas été vidé

**Solutions** :
1. Rechargement dur : `Ctrl+Shift+R`
2. Vider le cache : `F12` → Application → Clear all
3. Utiliser un navigateur privé / incognito

---

### Problème : "Cannot find ListClasseBateau in api.ts"

**Cause** : L'import manque dans le fichier Svelte

**Solution** :
Vérifiez que le script Svelte contient :
```typescript
import { 
  listSeries,
  createSerie,
  listClasseCourse,
  listClasseBateau,  // ← Doit être présent
  // ...
} from '../lib/api';
```

---

## 📊 Logs attendus

### Backend
```
[INFO] Building backend 0.0.1-SNAPSHOT
[INFO] ...
[INFO] BUILD SUCCESS
[INFO] org.springframework.boot.StartupInfoLogger : Started BackendApplication in 3.245 seconds
[INFO] org.apache.catalina.startup.Catalina : Server startup in [200] ms
[INFO] Tomcat started on port(s): 8080 [http]
```

### Frontend
```
VITE v5.x.x  ready in 234 ms

➜  Local:   http://localhost:5233/
➜  press h to show help
```

### API Test
```bash
curl http://localhost:8080/api/series
# Résultat:
[]
# ou
[{"id":1,"nomSerie":"Laser 2024","classeBateau":{"id":1,"nomClasse":"Laser"}, ...}]
```

---

## 🎯 Prochaines étapes

Après la migration :

1. ✅ Tester la création de séries
2. ✅ Ajouter des courses à une série
3. ✅ Inscrire des bateaux aux courses
4. ✅ Ajouter des résultats

### Commandes de démarrage rapide

```powershell
# Terminal 1 : Backend
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\backend
.\mvnw.cmd spring-boot:run

# Terminal 2 : Frontend
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\frontend
npm run dev

# Terminal 3 : Base de données (Docker)
cd c:\Users\2234344\Desktop\poc-svelte-spring-postgres\db
docker-compose up -d
```

---

## 📞 Support rapide

| Problème | Solution |
|----------|----------|
| Erreur SQL | Relancez `apply-migration.ps1` ou vérifiez pgAdmin |
| Backend ne compile pas | Vérifiez que `Serie.java` et `SerieController.java` ont été modifiés |
| Frontend affiche mal | Rechargement dur: `Ctrl+Shift+R` |
| API ne répond pas | Vérifiez que le backend écoute sur le port 8080 |
| Données confuses | Lisez le fichier `CHANGEMENT_SERIE_BATEAU.md` pour comprendre le nouveau modèle |

---

**✨ Bonne chance avec la migration ! Si vous rencontrez des problèmes, consultez les fichiers de documentation créés dans le dossier racine.**
