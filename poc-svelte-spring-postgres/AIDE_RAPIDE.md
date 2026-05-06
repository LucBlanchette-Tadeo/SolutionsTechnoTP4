# ⚡ AIDE RAPIDE - Commandes essentielles

## 🚀 Démarrage en 3 commandes (Ouvrir 3 terminaux PowerShell)

### Terminal 1 : PostgreSQL
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\db
docker-compose up
```

### Terminal 2 : Backend
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\backend
mvnw.cmd spring-boot:run
```

### Terminal 3 : Frontend
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend
npm run dev
```

**Ensuite** : Ouvrir http://localhost:5173

---

## 🔐 Connexion

```
Utilisateur : admin
Mot de passe : 123456
```

⚠️ **Changez le mot de passe après !**

---

## 📊 Utilisation - Workflow complet

### 1. Créer une classe de bateau
```
Menu → Classes
Bouton "Créer une classe"
Remplir : Nom (ex: Laser), PY (121), TMF (1.19)
```

### 2. Créer un bateau
```
Menu → Bateaux
Bouton "Créer un bateau"
Remplir : Classe, Nom, N° voile, Barreur
```

### 3. Créer une classe de course
```
Menu → Classes
Bouton "Créer une classe" (en bas)
Remplir : Nom (ex: Laser Loisir), Type handicap (PY)
```

### 4. Créer une série
```
Menu → Séries
Bouton "Créer une série"
Remplir : Nom (ex: Weekend mai)
Ajouter classes de courses
```

### 5. Entrer résultats
```
Menu → Résultats
Bouton "Ajouter un résultat"
Remplir : Classe, Bateau, Temps brut, Position
Valider
```

**→ Handicaps calculés automatiquement ✨**

---

## 🔧 Dépannage

### Port 8080 occupé ?
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### npm install manquant ?
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend
npm install
npm run dev
```

### PostgreSQL ne démarre pas ?
```powershell
docker-compose down -v
docker-compose up -d
Start-Sleep -Seconds 10
```

### CORS error ?
✅ Déjà configuré dans `CorsConfig.java`  
Assurez-vous que le backend tourne sur 8080

### Page blanche au login ?
- Vérifier la console (F12) pour les erreurs
- S'assurer que le backend répond sur http://localhost:8080/api

---

## 📋 Formules de handicap

### Portsmouth Yardstick (PY)
```
Temps corrigé = Temps brut × (PY / 1000)

Exemple : 3600 sec × (121 / 1000) = 435.6 sec
```

### Time Multiplying Factor (TMF)
```
Temps corrigé = Temps brut × TMF

Exemple : 3600 sec × 1.19 = 4284 sec
```

**Plus le temps corrigé est court, mieux c'est ! ✅**

---

## 🔄 Scripts utiles

### Vérifier la configuration système
```powershell
c:\Users\6292692\Desktop\poc-svelte-spring-postgres\verify-system.ps1
```

### Redémarrer le backend
```powershell
c:\Users\6292692\Desktop\poc-svelte-spring-postgres\restart-backend.bat
```

### Réinitialiser complètement
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\db
docker-compose down -v
docker-compose up -d
Start-Sleep -Seconds 10
# Attendre que PostgreSQL soit prêt
```

---

## 📚 Documentation

| Besoin | Fichier |
|:---|:---|
| Démarrer rapidement | `DEMARRAGE_2MIN.md` |
| Guide complet | `DEMARRAGE_COMPLET.md` |
| Utiliser l'interface | `GUIDE_COMPLET_UTILISATION.md` |
| Résumé du projet | `RECAP_FINAL.md` |
| Navigation | `INDEX.md` |

---

## ✅ Tests rapides après démarrage

### Test 1 : Accès à l'interface
```
Navigateur → http://localhost:5173
Devrait voir le formulaire de connexion ✅
```

### Test 2 : Connexion
```
admin / 123456
Devrait voir le menu principal ✅
```

### Test 3 : Créer une classe
```
Menu → Classes → Créer
Remplir avec : Optimist, PY=175, TMF=1.32
Devrait apparaître dans le tableau ✅
```

### Test 4 : Backend opérationnel
```
Ouvrir : http://localhost:8080/api/race-results
Devrait voir : {"content":[]} ou liste ✅
```

---

## 🆘 Besoin d'aide plus détaillée ?

| Problème | Solution |
|:---|:---|
| "Je ne sais pas par où commencer" | Lire `DEMARRAGE_2MIN.md` (1 min) |
| "Ça ne démarre pas" | Lire `DEMARRAGE_COMPLET.md` section "Dépannage" |
| "Je n'arrive pas à créer une course" | Lire `GUIDE_COMPLET_UTILISATION.md` |
| "Comment fonctionnent les handicaps ?" | Lire `DEMARRAGE_COMPLET.md` section "Formules" |
| "Je veux comprendre l'architecture" | Lire `RECAP_FINAL.md` |

---

## 💾 Données de test incluses

Les données suivantes sont préchargées :

| Classe | PY | TMF |
|:---|---:|---:|
| Laser | 121.00 | 1.1900 |
| J70 | 102.50 | 1.1450 |

Pour ajouter d'autres classes, voir `GUIDE_COMPLET_UTILISATION.md`

---

## 🎯 Raccourcis clavier

| Raccourci | Action |
|:---|:---|
| `Ctrl+F` | Chercher dans cette page |
| `F12` | Ouvrir les outils de développement |
| `Ctrl+Shift+K` | Ouvrir/fermer le terminal |
| `Ctrl+W` | Fermer un onglet |

---

## 📞 Résumé : La seule chose importante

```
TERMINAL 1 : cd db && docker-compose up
TERMINAL 2 : cd backend && mvnw.cmd spring-boot:run
TERMINAL 3 : cd frontend && npm run dev
NAVIGATEUR : http://localhost:5173
LOGIN : admin / 123456
```

C'est tout ! 🚀

---

**Dernière mise à jour** : Mai 2026  
**Version** : 1.0.0  
**Status** : ✅ Prêt
