# 🎬 DÉMARRAGE - Système d'Authentification

## ⚡ SUPER RAPIDE (30 secondes)

### Étape 1: Lancer l'app
Double-cliquez sur:
```
start.ps1  (PowerShell)
  OU
start.bat  (Command Prompt)
```

### Étape 2: Attendre
Attendez que les 3 fenêtres se lancent (20 sec environ)

### Étape 3: Ouvrir navigateur
```
http://localhost:5173
```

### Étape 4: Vous connecter
```
📝 Utilisateur: admin
🔑 Mot de passe: Admin123!
```

### Étape 5: Cliquer "Se connecter"
Et voilà ! Vous êtes dans l'app ! 🎉

---

## 🎯 SCREENSHOT ATTENDU

### Page de Login
```
┌─────────────────────────────────────┐
│                                     │
│          Connexion                  │
│                                     │
│  Nom d'utilisateur                  │
│  ┌──────────────────────────────┐   │
│  │ admin                        │   │
│  └──────────────────────────────┘   │
│                                     │
│  Mot de passe                       │
│  ┌──────────────────────────────┐   │
│  │ ••••••••••                   │   │
│  └──────────────────────────────┘   │
│                                     │
│  ┌──────────────────────────────┐   │
│  │  Se connecter                │   │
│  └──────────────────────────────┘   │
│                                     │
│  Données de test:                   │
│  Utilisateur: admin                 │
│  Mot de passe: Admin123!            │
│                                     │
└─────────────────────────────────────┘
```

### Après Login
```
┌────────────────────────────────────────────────┐
│ YRR     [Accueil] [Bateaux] [Classes] ... 👤admin [Déco] │
├────────────────────────────────────────────────┤
│                                                │
│            Bienvenue 🌊                        │
│                                                │
│   Gérez facilement votre flotte de bateaux    │
│                                                │
│  ┌────────────────────┐  ┌────────────────┐   │
│  │ ➜ Voir Bateaux     │  │ ➜ Voir Séries  │   │
│  └────────────────────┘  └────────────────┘   │
│                                                │
└────────────────────────────────────────────────┘
```

---

## ✅ CHECKLIST DÉMARRAGE

- [ ] PowerShell ou Command Prompt ouvert
- [ ] Dans le dossier du projet
- [ ] Lancé `start.ps1` ou `start.bat`
- [ ] Attendu 20 secondes
- [ ] Ouvert http://localhost:5173
- [ ] Entré admin / Admin123!
- [ ] Cliqué "Se connecter"
- [ ] Vu la page d'accueil ✓

---

## 🎮 INTERFACE UTILISATEUR

### Barre de Navigation
```
Logo YRR    |  Accueil | Bateaux | Classes | Séries | Courses  |  👤 admin   [Déco]
```

### Pages Disponibles
- 🏠 **Accueil** - Page d'introduction
- 🚤 **Bateaux** - Gérer les bateaux
- 📋 **Classes** - Gérer les classes
- 🏆 **Séries** - Gérer les séries
- 🏁 **Courses** - Gérer les courses

### Bouton Déconnexion
Cliquez sur [🚪 Déconnexion] pour revenir au login

---

## 🆘 PROBLÈMES COURANTS

### "Page blanche / Page d'erreur"
→ Rafraîchissez le navigateur (F5)
→ Vérifiez que le backend démarre (fenêtre noire)

### "Erreur 'localhost:8080 refusé'"
→ Le backend ne s'est pas lancé
→ Lancez manuellement: `cd backend && .\mvnw spring-boot:run`

### "Erreur 'Identifiants invalides'"
→ Vérifiez que vous tapez: `admin` (exact)
→ Password: `Admin123!` (avec majuscule et caractères spéciaux)

### "Les 3 fenêtres ne s'ouvrent pas"
→ Lancez manuellement dans 3 terminaux différents:
```powershell
# Terminal 1: Base de données
cd db && docker-compose up

# Terminal 2: Backend  
cd backend && .\mvnw spring-boot:run

# Terminal 3: Frontend
cd frontend && npm run dev
```

---

## 🎨 FONCTIONNALITÉS

✅ **Authentification**
- Login avec username/password
- Mots de passe sécurisés (BCrypt)
- Affichage du nom connecté
- Déconnexion facile

✅ **Interface**
- Page login moderne avec gradient
- Navbar avec infos utilisateur
- Navigation intuitive
- Responsive mobile

✅ **Données**
- Sauvegarde dans PostgreSQL
- Persistance session
- Déconnexion = nettoyage

---

## 📞 BESOIN D'AIDE ?

### Documents disponibles:
1. **AUTHENTIFICATION_RESUME.md** - Overview complet
2. **LOGIN_README.md** - Guide détaillé
3. **CHECKLIST_AUTHENTIFICATION.md** - Vérification

### Ligne de commande pour tester:
```powershell
.\test-auth.ps1
```

---

## 🎊 C'EST PRÊT !

Vous avez maintenant un système d'authentification **entièrement fonctionnel** ! 

### Prochaines étapes:
1. Explorez l'application
2. Créez/modifiez des bateaux
3. Gérez vos classes et séries
4. Lancez vos courses !

Bon voyage ! ⛵

---

**Questions?** Consultez les fichiers markdown dans le dossier `/md`
