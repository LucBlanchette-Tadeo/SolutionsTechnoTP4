# ⚡ LIEN RAPIDE - Authentification

## 🚀 DÉMARRAGE EN 1 CLIC

Voici toutes les commandes pour démarrer rapidement:

### PowerShell (Recommandé)
```powershell
# Aller dans le dossier
cd c:\Users\6242441\Desktop\poc-svelte-spring-postgres

# Lancer tout
.\start.ps1

# Après démarrage, ouvrez:
# http://localhost:5173
```

### Command Prompt
```batch
REM Aller dans le dossier
cd c:\Users\6242441\Desktop\poc-svelte-spring-postgres

REM Lancer tout
start.bat

REM Après démarrage, ouvrez:
REM http://localhost:5173
```

### Manuel (3 terminaux)
```bash
# Terminal 1
cd db && docker-compose up

# Terminal 2
cd backend && .\mvnw spring-boot:run

# Terminal 3
cd frontend && npm run dev
```

---

## 🔑 IDENTIFIANTS

```
Username: admin
Password: Admin123!
```

---

## 🌐 URLS IMPORTANTES

| Service | URL |
|---------|-----|
| Frontend | http://localhost:5173 |
| Backend | http://localhost:8080 |
| API Login | http://localhost:8080/api/auth/login |
| Database | localhost:5432 |

---

## 📚 DOCUMENTATION RAPIDE

1. **[DEMARRAGE_RAPIDE.md](DEMARRAGE_RAPIDE.md)** - 5 min
2. **[AUTHENTIFICATION_RESUME.md](AUTHENTIFICATION_RESUME.md)** - 3 min
3. **[LOGIN_README.md](LOGIN_README.md)** - 15 min
4. **[IDENTIFIANTS.md](IDENTIFIANTS.md)** - 2 min
5. **[CONCLUSION.md](CONCLUSION.md)** - 5 min

---

## 🧪 TEST AUTOMATIQUE

```powershell
.\test-auth.ps1
```

---

## 📁 FICHIERS CLÉS

### Backend
- `backend/src/main/java/com/example/backend/Utilisateur.java`
- `backend/src/main/java/com/example/backend/UtilisateurService.java`
- `backend/src/main/java/com/example/backend/UtilisateurController.java`

### Frontend
- `frontend/src/lib/Login.svelte`
- `frontend/src/App.svelte`

### Base de données
- `db/init.sql`
- Table: `utilisateur`

---

## ✅ CHECKLIST RAPIDE

- [ ] Docker lancé
- [ ] Backend démarre (~20s)
- [ ] Frontend démarre (~10s)
- [ ] http://localhost:5173 accessible
- [ ] Login: admin / Admin123!
- [ ] Page d'accueil affichée

---

## 🆘 AIDE RAPIDE

| Problème | Solution |
|----------|----------|
| Backend ne démarre | `cd backend && .\mvnw spring-boot:run` |
| Frontend blank | `cd frontend && npm run dev` |
| CORS error | Vérifier port 8080 |
| Identifiants faux | Utiliser `admin / Admin123!` exactement |

---

## 🎯 PROCHAINES ÉTAPES

1. Lancez l'app: `.\start.ps1`
2. Connectez-vous: admin / Admin123!
3. Explorez l'application
4. Testez les fonctionnalités
5. Consultez la doc si besoin

---

**Prêt ? C'est parti ! 🚀**

```
.\start.ps1
```

**Puis:** http://localhost:5173

**Identifiants:** admin / Admin123!

---

*Besoin d'aide ? Lisez DEMARRAGE_RAPIDE.md*
