# 🧪 Guide de Test Complet - Bateau Manager

## Prérequis
- ✅ Backend Spring Boot tournant sur `http://localhost:8080`
- ✅ Frontend Svelte tournant sur `http://localhost:5173`
- ✅ PostgreSQL en container Docker actif
- ✅ Navigateur moderne (Chrome, Firefox, Safari, Edge)

---

## 📋 Checklist de Test - CRUD Bateaux

### Créer un Bateau
```
1. Aller sur http://localhost:5173
2. Cliquer sur "🚤 Bateaux" dans la navigation
3. Remplir le formulaire:
   - Nom: "Mon Voilier"
   - Numéro de voile: 42
   - Nom du barreur: "Jean Dupont"
   - Classe: Sélectionner "Laser"
4. Cliquer "➕ Créer"
5. ✅ Vérifier le message "✅ Bateau créé avec succès!"
6. ✅ Vérifier que le bateau apparaît dans la liste
```

**Vérification API**:
```bash
curl -X GET http://localhost:8080/api/bateaux | jq '.[] | select(.nomBateau == "Mon Voilier")'
```

---

### Modifier un Bateau
```
1. Dans la liste des bateaux, cliquer "✏️ Modifier" sur un bateau
2. Le formulaire se remplit avec les données du bateau
3. Modifier le "Nom du barreur" en "Jacques Dupont"
4. Cliquer "💾 Mettre à jour"
5. ✅ Vérifier le message "✅ Bateau modifié avec succès!"
6. ✅ Vérifier que la liste affiche le nouveau nom
```

**Vérification API**:
```bash
curl -X GET http://localhost:8080/api/bateaux/9 | jq '.nomBarreur'
```

---

### Supprimer un Bateau
```
1. Dans la liste des bateaux, cliquer "🗑️ Supprimer" sur un bateau
2. Confirmer la suppression
3. ✅ Vérifier le message "✅ Bateau supprimé avec succès!"
4. ✅ Vérifier que le bateau a disparu de la liste
```

**Vérification API**:
```bash
curl -X DELETE http://localhost:8080/api/bateaux/9
# Devrait retourner 500 si bateau n'existe pas
```

---

## 🏆 Checklist de Test - CRUD Séries

### Créer une Série
```
1. Aller sur http://localhost:5173
2. Cliquer sur "🏆 Séries" dans la navigation
3. Remplir le formulaire:
   - Nom: "Série Printemps 2026"
   - Classe de course: Sélectionner "Série 1"
4. Cliquer "➕ Créer"
5. ✅ Vérifier le message "✅ Série créée avec succès!"
6. ✅ Vérifier que la série apparaît dans la liste
```

**Vérification API**:
```bash
curl -X GET http://localhost:8080/api/series | jq '.[] | select(.nomSerie == "Série Printemps 2026")'
```

---

### Modifier une Série
```
1. Dans la liste des séries, cliquer "✏️ Modifier" sur une série
2. Le formulaire se remplit avec les données de la série
3. Modifier le "Nom" en "Série Printemps-Été 2026"
4. Cliquer "💾 Mettre à jour"
5. ✅ Vérifier le message "✅ Série modifiée avec succès!"
6. ✅ Vérifier que la liste affiche le nouveau nom
```

---

### Supprimer une Série
```
1. Dans la liste des séries, cliquer "🗑️ Supprimer" sur une série
2. Confirmer la suppression
3. ✅ Vérifier le message "✅ Série supprimée avec succès!"
4. ✅ Vérifier que la série a disparu de la liste
```

---

## 📚 Checklist de Test - CRUD Classes de Bateau

### Créer une Classe
```
1. Cliquer sur "📋 Classes" dans la navigation
2. Remplir le formulaire:
   - Nom: "Catamaran 20"
3. Cliquer "➕ Créer"
4. ✅ Vérifier le message "✅ Classe créée avec succès!"
5. ✅ Vérifier que la classe apparaît dans la liste
6. ✅ Vérifier que la classe est disponible dans le select "Classe" de la page Bateaux
```

---

## 🎯 Checklist de Test - CRUD Classes de Course

### Créer une Classe de Course
```
1. Cliquer sur "🎯 Classes de Course" dans la navigation
2. Remplir le formulaire:
   - Nom: "Course Estivale"
   - Type: "monotype"
3. Cliquer "➕ Créer"
4. ✅ Vérifier le message "✅ Classe de course créée!"
5. ✅ Vérifier que la classe apparaît dans la liste
6. ✅ Vérifier qu'elle est disponible dans le select "Classe de course" de la page Séries
```

---

## 🔄 Tests d'Intégration (Scénarios Réalistes)

### Scénario 1: Créer une Série Complète
```
Étapes:
1. Créer une "Classe de Bateau" → "Catamaran Racing"
2. Créer une "Classe de Course" → "Racing Estival"
3. Créer une "Série" → "Estival 2026" liée à "Racing Estival"
4. Créer 3 "Bateaux" liés à "Catamaran Racing"

Vérifications:
✅ Toutes les créations réussissent
✅ Les listes se mettent à jour correctement
✅ Les relations entre entités sont conservées
```

---

### Scénario 2: Tester les Erreurs
```
Créer un Bateau sans classe:
1. Laisser le select "Classe" vide
2. Cliquer "➕ Créer"
3. ✅ Vérifier l'erreur "Tous les champs sont obligatoires"

Créer une Série sans classe de course:
1. Remplir le nom mais pas la classe de course
2. Cliquer "➕ Créer"
3. ✅ Vérifier l'erreur "Tous les champs sont obligatoires"
```

---

### Scénario 3: Tester les Modifications en Cascade
```
Créer:
1. Classe de bateau "Laser"
2. Bateau "Mon Laser" de classe "Laser"

Modifier:
1. Aller sur le bateau "Mon Laser"
2. Cliquer "✏️ Modifier"
3. Changer la classe en "J70"
4. Cliquer "💾 Mettre à jour"

Vérifications:
✅ Le bateau est bien modifié
✅ La classe affichée est maintenant "J70"
✅ Aucune erreur de constraint en base
```

---

## 🌐 Tests d'API Directe (PowerShell/curl)

### GET - Lister tous les bateaux
```bash
curl -X GET http://localhost:8080/api/bateaux
```

### POST - Créer un bateau
```bash
curl -X POST http://localhost:8080/api/bateaux \
  -H "Content-Type: application/json" \
  -d '{
    "nomBateau": "Bateau Test",
    "numeroVoile": 100,
    "nomBarreur": "Test",
    "classeBateau": {"id": 1}
  }'
```

### PUT - Modifier un bateau (ID 9)
```bash
curl -X PUT http://localhost:8080/api/bateaux/9 \
  -H "Content-Type: application/json" \
  -d '{
    "nomBateau": "Bateau Modifié"
  }'
```

### DELETE - Supprimer un bateau (ID 9)
```bash
curl -X DELETE http://localhost:8080/api/bateaux/9
```

---

## 🗄️ Vérification de la Base de Données

### Connexion à PostgreSQL
```bash
docker exec db-db-1 psql -U poc -d pocdb
```

### Vérifier les bateaux
```sql
SELECT id, nom_bateau, numero_voile, nom_barreur, classe_bateau_id FROM bateau;
```

### Vérifier les séries
```sql
SELECT id, nom_serie, classe_course_id, nombre_courses FROM serie;
```

### Vérifier les classes de bateau
```sql
SELECT id, nom_classe FROM classe_bateau;
```

### Vérifier les classes de course
```sql
SELECT id, nom_classe_course, type_classe FROM classe_course;
```

---

## 📊 Métriques de Succès

| Fonctionnalité | Statut | Notes |
|---|---|---|
| Créer bateau via UI | ✅ | Testé et validé |
| Modifier bateau via UI | ✅ | Testé et validé |
| Supprimer bateau via UI | ✅ | Testé et validé |
| Créer série via UI | ✅ | Testé et validé |
| Modifier série via UI | ✅ | Testé et validé |
| Supprimer série via UI | ✅ | Testé et validé |
| Créer classe bateau via UI | ✅ | Testé et validé |
| Créer classe course via UI | ✅ | Testé et validé |
| API POST /bateaux | ✅ | Testé et validé |
| API POST /series | ✅ | Testé et validé |
| CORS fonctionnel | ✅ | Testé et validé |
| Erreurs gérées | ✅ | Messages affichés |

---

## 🐛 Dépannage

### Le frontend affiche "Aucun bateau"
- ✅ Vérifier que le backend écoute sur 8080
- ✅ Ouvrir la console du navigateur (F12) et vérifier les erreurs CORS
- ✅ Vérifier que PostgreSQL est actif: `docker ps`

### Les requêtes API retournent 500
- ✅ Vérifier les logs du backend (terminal Spring Boot)
- ✅ Vérifier que les classes Java ont les bonnes annotations `@Entity`, `@Column`, etc.
- ✅ Vérifier que la base de données est accessible

### Les modifications ne s'affichent pas
- ✅ Rafraîchir la page (F5 ou Ctrl+R)
- ✅ Vérifier que le `loading` flag passe à `false`
- ✅ Vérifier la console réseau (F12 > Network)

---

**🎯 Objectif**: Tous les tests doivent passer avec un ✅  
**📅 Date**: 13 Avril 2026  
**👤 Statut**: Prêt pour UAT et Production
