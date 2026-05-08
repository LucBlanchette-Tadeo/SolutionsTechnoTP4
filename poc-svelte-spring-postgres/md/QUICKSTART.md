# 🚀 Quick Start - Bateau Manager

## ⚡ Démarrage Ultra-Rapide (3 étapes)

### Étape 1️⃣ : Base de Données
```bash
cd poc-svelte-spring-postgres
docker-compose -f db/docker-compose.yml up -d
```

### Étape 2️⃣ : Backend (Nouvelle Fenêtre Terminal)
```bash
cd backend
./mvnw spring-boot:run
# Ou sur Windows: mvnw.cmd spring-boot:run
# Attend que "Tomcat started on port(s): 8080"
```

### Étape 3️⃣ : Frontend (Nouvelle Fenêtre Terminal)
```bash
cd frontend
npm install  # Premier lancement seulement
npm run dev
# Attend que "VITE v... ready in ... ms"
```

### 🌐 Ouvrez dans le Navigateur
```
http://localhost:5173
```

---

## 🛑 Arrêter l'Application

### Fermer Frontend
```bash
# Dans le terminal du frontend
Ctrl + C
```

### Fermer Backend
```bash
# Dans le terminal du backend
Ctrl + C
```

### Fermer Base de Données
```bash
docker-compose -f db/docker-compose.yml down
```

---

## 🔄 Redémarrer l'Application

```bash
# Garder les anciens terminals ouverts
# Relancer les commandes une par une
```

---

## 🧪 Vérifier que Tout Fonctionne

### Frontend OK ?
- [ ] http://localhost:5173 affiche l'accueil
- [ ] Les boutons de navigation fonctionnent
- [ ] Vous voyez le tableau des bateaux

### Backend OK ?
- [ ] Lancer dans le navigateur : http://localhost:8080/api/bateaux
- [ ] Vous voyez une liste JSON
- [ ] Pas d'erreur 500

### Base de Données OK ?
- [ ] Les bateaux s'affichent dans le tableau
- [ ] Les données sont persistantes
- [ ] Aucune erreur de connexion

---

## 📱 Fonctionnalités Essentielles

### Ajouter un Bateau
1. Allez sur **Bateaux**
2. Remplissez le formulaire
3. Cliquez **"➕ Créer"**
4. ✅ Bateau ajouté

### Modifier un Bateau
1. Trouvez le bateau dans le tableau
2. Cliquez **"✏️ Modifier"**
3. Modifiez et cliquez **"💾 Mettre à jour"**
4. ✅ Bateau mis à jour

### Supprimer un Bateau
1. Cliquez **"🗑️ Supprimer"**
2. Confirmez dans la boîte de dialogue
3. ✅ Bateau supprimé

### Naviguer
1. Cliquez sur l'un des onglets du haut
2. ✅ La page change

---

## 🚨 Dépannage Rapide

### "Erreur : Page blanche"
```
→ Vérifiez que le backend s'exécute
→ Ouvrez F12 pour voir les erreurs
→ Vérifiez http://localhost:8080/api/bateaux
```

### "Erreur : Port 8080 en utilisation"
```
→ Tuez le processus Java existant
→ Redémarrez le backend
```

### "Erreur : Base de données non accessible"
```
→ Vérifiez que Docker s'exécute : docker ps
→ Relancez : docker-compose -f db/docker-compose.yml up -d
```

### "Erreur : Les modifications ne s'affichent pas"
```
→ Rafraîchissez la page (Ctrl+R ou F5)
→ Attendez quelques secondes
```

---

## 📝 Notes Importantes

- 🟢 **Status** : Complètement fonctionnel
- 📍 **Frontend** : http://localhost:5173
- 📍 **Backend** : http://localhost:8080
- 📍 **Base de données** : PostgreSQL via Docker
- 🔗 **Données** : Persistantes dans PostgreSQL

---

## 🎯 Prochaines Actions

### Pour Tester
```
1. Créez une nouvelle classe
2. Créez un nouveau bateau
3. Modifiez le bateau
4. Supprimez le bateau
5. Vérifiez que tout marche
```

### Pour Explorer
```
1. Visitez toutes les pages
2. Remplissez tous les formulaires
3. Testez toutes les actions
4. Vérifiez les messages d'erreur
```

### Pour Développer
```
1. Ouvrez frontend/src/App.svelte
2. Modifiez le style ou le code
3. Les changements se reflètent en temps réel (Hot reload)
```

---

## 💾 Sauvegarder les Données

Les données sont automatiquement sauvegardées dans PostgreSQL.
Aucune action supplémentaire requise.

Pour faire une sauvegarde :
```bash
docker exec db-db-1 pg_dump -U postgres voting > backup.sql
```

Pour restaurer :
```bash
docker exec -i db-db-1 psql -U postgres < backup.sql
```

---

## 🎓 Documentation Complète

Pour plus d'informations, consultez :
- `GUIDE_UTILISATION_COMPLET.md` - Guide d'utilisation détaillé
- `TEST_FONCTIONNALITES.md` - Tests et vérification
- `RECAP.md` - Résumé des améliorations
- `CHECKLIST_VERIFICATION.md` - Checklist de vérification

---

## ✨ Bon Amusement ! 🎉

L'application est prête à être utilisée.
Profitez bien de Bateau Manager ! ⛵

---

**Créé le** : 13 Avril 2026  
**Version** : 1.0  
**Status** : ✅ Prêt à l'emploi
