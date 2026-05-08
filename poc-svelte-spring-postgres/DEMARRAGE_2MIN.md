# DEMARRAGE RAPIDE - 2 MINUTES

## ⚡ Démarrage en 3 terminaux

### Prérequis vérifiés ✅
- Docker Desktop : OK
- Java 21 : OK
- Node.js 20+ : OK
- npm : OK
- Tous les fichiers : OK

---

## 🚀 LANCEZ CES 3 COMMANDES (chacune dans un terminal PowerShell)

### Terminal 1 : Base de données
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\db
docker-compose up
```
⏳ Attendre : "database system is ready to accept connections"

---

### Terminal 2 : Backend (Java Spring Boot)
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\backend
mvnw.cmd spring-boot:run
```
⏳ Attendre : "Tomcat started on port(s): 8080"

---

### Terminal 3 : Frontend (Svelte)
```powershell
cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend
npm run dev
```
⏳ Attendre : "Local: http://localhost:5173"

---

## 🌐 Ouvrir l'application

Ouvrir votre navigateur et aller à :
```
http://localhost:5173
```

---

## 🔐 Identifiants de connexion

```
Utilisateur : admin
Mot de passe : 123456
```

⚠️ **Changez le mot de passe après la première connexion !**

---

## ✨ Vous êtes prêt !

Après la connexion, vous pouvez :
- ✅ Créer des classes de bateaux (Laser, J70, etc.)
- ✅ Créer des bateaux avec numéros de voile
- ✅ Créer des classes de courses (Laser Loisir, J70 Championship, etc.)
- ✅ Créer des séries (championnat, weekend, etc.)
- ✅ Entrer les résultats de courses
- ✅ Voir les **handicaps calculés automatiquement** ✨

---

## 🎓 Pour plus de détails

Consulter les fichiers :
- **DEMARRAGE_COMPLET.md** - Guide complet avec dépannage
- **GUIDE_COMPLET_UTILISATION.md** - Utilisation étape par étape
- **HANDICAP_SYSTEM.md** - Détails techniques du système de handicap

---

**⏱️ Temps estimé : 2 minutes pour tout démarrer**
