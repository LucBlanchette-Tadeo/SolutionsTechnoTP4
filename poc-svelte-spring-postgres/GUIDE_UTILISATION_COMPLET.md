# Guide Complet d'Utilisation - Bateau Manager

## 🚀 Démarrage Rapide

### Prérequis
- Docker et Docker Compose
- Node.js (v18+)
- Java 11+ (pour Maven)

### Installation et Lancement

#### Étape 1 : Démarrer la base de données
```bash
cd poc-svelte-spring-postgres
docker-compose -f db/docker-compose.yml up -d
```

#### Étape 2 : Démarrer le backend
```bash
cd backend
./mvnw spring-boot:run
# Windows: mvnw.cmd spring-boot:run
```

Le serveur démarre sur http://localhost:8080

#### Étape 3 : Démarrer le frontend
```bash
cd frontend
npm install  # Premier lancement seulement
npm run dev
```

Le frontend est accessible sur http://localhost:5173

---

## 📱 Interface Utilisateur

### Navigation Principale

```
[Logo] ⛵ Bateau Manager | [🏠 Accueil] [🚤 Bateaux] [📋 Classes] [🏆 Séries] [🏁 Courses]
```

Cliquez sur n'importe quel bouton pour naviguer entre les pages.

---

## 🚤 Page Gestion des Bateaux

### Ajouter un bateau
1. Remplissez le formulaire :
   - **Nom du bateau** : Le nom de votre bateau (ex: "Mon Laser")
   - **Numéro de voile** : Le numéro unique de la voile (ex: 123)
   - **Nom du barreur** : Le nom du skipper/barreur (ex: "Jean Dupont")
   - **Classe** : Sélectionnez une classe dans la liste (ex: Laser, J70, etc.)
2. Cliquez sur **"➕ Créer"**
3. Un message de succès s'affiche et le bateau apparaît dans le tableau

### Modifier un bateau
1. Dans le tableau, trouvez le bateau à modifier
2. Cliquez sur **"✏️ Modifier"**
3. Le formulaire se remplit avec les données du bateau
4. Modifiez les champs désirés
5. Cliquez sur **"💾 Mettre à jour"**
6. Le bateau est mis à jour et le tableau se rafraîchit

### Supprimer un bateau
1. Cliquez sur **"🗑️ Supprimer"** pour le bateau
2. Une boîte de confirmation apparaît
3. Cliquez sur **"OK"** pour confirmer
4. Le bateau est supprimé

### Tableau des bateaux
Affiche pour chaque bateau :
- **ID** : Identifiant unique
- **Nom** : Nom du bateau
- **Voile** : Numéro de la voile
- **Barreur** : Nom du barreur
- **Classe** : Classe du bateau (en badge bleu)
- **Actions** : Boutons Modifier et Supprimer

---

## 📋 Page Gestion des Classes

### Ajouter une classe
1. Entrez le **nom de la classe** (ex: "Laser", "J70", "Catamaran")
2. Cliquez sur **"➕ Créer"**
3. La classe est créée et apparaît dans le tableau

### Modifier une classe
1. Cliquez sur **"✏️ Modifier"**
2. Modifiez le nom
3. Cliquez sur **"💾 Mettre à jour"**

### Supprimer une classe
1. Cliquez sur **"🗑️ Supprimer"**
2. Confirmez la suppression

⚠️ **Attention** : Vous ne pouvez pas supprimer une classe si des bateaux l'utilisent.

---

## 🏆 Page Gestion des Séries

### Ajouter une série
1. Entrez le **nom de la série** (ex: "Série Été 2024")
2. Sélectionnez une **classe de course** associée
3. Cliquez sur **"➕ Créer"**

### Modifier une série
1. Cliquez sur **"✏️ Modifier"**
2. Modifiez le nom et/ou la classe
3. Cliquez sur **"💾 Mettre à jour"**

### Supprimer une série
1. Cliquez sur **"🗑️ Supprimer"**
2. Confirmez

---

## 🏁 Page Gestion des Classes de Course

### Ajouter une classe de course
1. Entrez le **nom** (ex: "Monotype Laser")
2. Choisissez le **type** :
   - **Monotype** : Tous les bateaux identiques
   - **Handicap** : Bateaux de différentes classes avec handicap
3. Cliquez sur **"➕ Créer"**

### Modifier une classe de course
1. Cliquez sur **"✏️ Modifier"**
2. Modifiez le nom et/ou le type
3. Cliquez sur **"💾 Mettre à jour"**

### Supprimer une classe de course
1. Cliquez sur **"🗑️ Supprimer"**
2. Confirmez

---

## 🏠 Page Accueil

Affiche un message de bienvenue avec des accès rapides vers :
- **Voir les Bateaux** : Accès rapide à la gestion des bateaux
- **Voir les Séries** : Accès rapide à la gestion des séries

---

## ⚠️ Messages d'Erreur et Succès

### Messages de Succès (Vert)
```
✅ Bateau créé avec succès!
✅ Bateau modifié avec succès!
✅ Bateau supprimé avec succès!
```

### Messages d'Erreur (Rouge)
```
❌ Erreur: Tous les champs sont obligatoires
❌ Erreur: Le nom est obligatoire
❌ Erreur: Classe bateau introuvable
```

---

## 🔧 Validation des Formulaires

### Bateaux
- **Nom** : Obligatoire
- **Numéro de voile** : Obligatoire, doit être numérique
- **Barreur** : Obligatoire
- **Classe** : Obligatoire, doit exister

### Classes
- **Nom** : Obligatoire, non vide

### Séries
- **Nom** : Obligatoire
- **Classe de course** : Obligatoire, doit exister

### Classes de Course
- **Nom** : Obligatoire
- **Type** : Doit être "monotype" ou "handicap"

---

## 🌐 Architecture Technique

### Frontend
- **Framework** : Svelte
- **Build Tool** : Vite
- **Langage** : TypeScript
- **Styling** : CSS-in-JS (Svelte scoped)
- **API** : Fetch API

### Backend
- **Framework** : Spring Boot 3
- **ORM** : JPA/Hibernate
- **Base de données** : PostgreSQL
- **Port** : 8080
- **CORS** : Activé pour localhost:5173

### API REST
Tous les endpoints sont en JSON et acceptent/retournent du JSON.

```
BASE_URL = http://localhost:8080/api
```

---

## 📊 Modèle de Données

### Entités

#### Bateau
```json
{
  "id": 1,
  "nomBateau": "Mon Laser",
  "numeroVoile": 123,
  "nomBarreur": "Jean Dupont",
  "classeBateau": {
    "id": 1,
    "nomClasse": "Laser"
  }
}
```

#### ClasseBateau
```json
{
  "id": 1,
  "nomClasse": "Laser"
}
```

#### Serie
```json
{
  "id": 1,
  "nomSerie": "Série Été",
  "classeCourse": {
    "id": 1,
    "nomClasseCourse": "Laser Monde",
    "typeClasse": "monotype"
  }
}
```

#### ClasseCourse
```json
{
  "id": 1,
  "nomClasseCourse": "Laser Monde",
  "typeClasse": "monotype"
}
```

---

## 🔗 Relations

```
ClasseBateau (1) <─── (∞) Bateau
ClasseCourse (1) <─── (∞) Serie
```

---

## 🐛 Dépannage

### L'application affiche une page blanche
- Ouvrez la console (F12)
- Vérifiez les erreurs JavaScript
- Assurez-vous que le backend s'exécute
- Rafraîchissez la page (Ctrl+R)

### Erreur "Cannot GET /api/..."
- Vérifiez que le backend s'exécute : http://localhost:8080
- Vérifiez la console du backend pour les erreurs

### La base de données n'est pas accessible
- Vérifiez que Docker s'exécute
- Assurez-vous que le conteneur PostgreSQL est en cours d'exécution :
  ```bash
  docker ps
  ```

### Les modifications ne s'affichent pas
- Rafraîchissez manuellement (Ctrl+R)
- Attendez quelques secondes

---

## 📚 Ressources Utiles

### Documentation
- Svelte : https://svelte.dev
- Spring Boot : https://spring.io/projects/spring-boot
- PostgreSQL : https://www.postgresql.org/docs
- REST API Best Practices : https://restfulapi.net

### Fichiers Importants
- `frontend/src/App.svelte` - Application principale
- `backend/src/main/java/com/example/backend/` - Code du serveur
- `db/docker-compose.yml` - Configuration de la base de données

---

## 💡 Conseils d'Utilisation

1. **Créez d'abord les classes** avant les bateaux
2. **Créez les classes de course** avant les séries
3. **Utilisez des noms significatifs** pour les données
4. **Vérifiez les associations** avant de supprimer
5. **Rafraîchissez régulièrement** si vous avez des doubtes

---

## 🎯 Cas d'Usage

### Scénario 1 : Créer un club de voile
1. Allez à **Classes** et créez toutes les classes (Laser, J70, etc.)
2. Allez à **Courses** et créez les classes de course
3. Allez à **Séries** et créez les séries de courses
4. Allez à **Bateaux** et ajoutez tous les bateaux du club

### Scénario 2 : Préparer une course
1. Allez à **Séries**
2. Créez une nouvelle série (ex: "Regatta 2024")
3. Sélectionnez la classe de course appropriée
4. Les bateaux de cette classe seront disponibles pour les inscriptions

### Scénario 3 : Gérer les participants
1. Allez à **Bateaux**
2. Modifiez le nom du barreur si changement
3. Modifiez la classe si le bateau change de catégorie
4. Supprimez les bateaux qui ne participent plus

---

## 🌟 Fonctionnalités Avancées

### États de Chargement
Pendant une opération, les boutons sont désactivés et affichent :
```
⏳ Traitement...
```

### Annulation d'Édition
Lors de la modification, un bouton "❌ Annuler" apparaît pour revenir au formulaire d'ajout.

### Associations Visuelles
Les associations sont affichées avec des badges colorés :
- Classe en bleu : `#e6f0ff`
- Type en orange : `#fff0e6`

### Responsive Design
L'application s'adapte aux petits écrans (mobile) :
- Navigation adaptée
- Tableaux scrollables
- Boutons en colonne sur mobile

---

## 📞 Support

En cas de problème :
1. Vérifiez les logs (F12 dans le navigateur)
2. Vérifiez les logs du backend (console Java)
3. Assurez-vous que tous les services sont démarrés
4. Consultez le fichier TEST_FONCTIONNALITES.md

---

## ✅ Checklist de Vérification

- [ ] Base de données démarre sans erreur
- [ ] Backend démarre sur port 8080
- [ ] Frontend démarre sur port 5173
- [ ] L'accueil s'affiche correctement
- [ ] Créer une classe fonctionne
- [ ] Créer un bateau fonctionne
- [ ] Modifier un bateau fonctionne
- [ ] Supprimer un bateau fonctionne
- [ ] Navigation entre pages fonctionne
- [ ] Messages d'erreur s'affichent correctement
- [ ] Messages de succès s'affichent correctement

---

## 🎉 Bravo !

Vous êtes maintenant prêt à utiliser **Bateau Manager** ! 
Bonne chance avec votre gestion de flotte de voile ! ⛵
