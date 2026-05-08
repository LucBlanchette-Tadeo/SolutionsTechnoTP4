🚀 DÉMARRAGE ULTRA-RAPIDE - 5 MINUTES
=====================================

## 1️⃣ Lancer le backend (Terminal 1)

```bash
cd backend
./mvnw spring-boot:run
```

Attendre : "Tomcat started on port(s): 8080"

---

## 2️⃣ Lancer le frontend (Terminal 2)

```bash
cd frontend
npm install          # (si première fois)
npm run dev
```

Attendre : "Local: http://localhost:5173"

---

## 3️⃣ Ouvrir l'application

🌐 **http://localhost:5173**

Login : `admin` / [votre password]

---

## 4️⃣ Enregistrer un résultat (30 secondes)

1. Cliquer sur **🎯 Résultats** (navigation supérieure)
2. Sélectionner une **Course** (ex: "Série 1")
3. Sélectionner un **Bateau** (ex: "Laser" ou "J70")
4. Entrer le **Temps brut** en secondes (ex: 3600)
5. Choisir **Handicap** (PY ou TMF)
6. Cliquer **✓ Enregistrer le résultat**

✅ **Résultat** : Le temps corrigé s'affiche automatiquement !

---

## 5️⃣ Voir le classement (instantané)

Le tableau s'affiche et se met à jour automatiquement avec chaque nouveau résultat.

**Les positions sont triées par TEMPS CORRIGÉ** (pas le temps brut).

---

## 📝 Qu'est-ce qu'il se passe ?

```
Vous entrez :
  Temps brut : 3600 secondes
  Handicap : PY
  Classe : Laser (PY=1050)

Le système calcule :
  3600 × 1000 ÷ 1050 = 3428.57 secondes
  
Affichage :
  Position : 1️⃣
  Temps brut : 01:00:00
  Temps corrigé : 00:57:08 ⭐
```

---

## ❓ Besoin d'aide ?

| Question | Réponse |
|----------|---------|
| Pourquoi mon temps corrigé est plus court ? | Parce que c'est un bateau rapide ! |
| Pourquoi J70 gagne au lieu de Laser ? | Ils sont classés par temps corrigé, pas brut |
| Comment modifier les handicaps ? | Voir HANDICAP_CONFIG.md |
| Ça ne marche pas ? | Voir Troubleshooting dans GUIDE_UTILISATION_HANDICAP.md |

---

## 📚 Documentation complète

👉 Voir **INDEX_DOCUMENTATION.md**

- Débutant ? → GUIDE_UTILISATION_HANDICAP.md
- Dev ? → HANDICAP_SYSTEM.md
- Admin ? → HANDICAP_CONFIG.md

---

## 🎉 C'est tout !

Vous êtes prêt à utiliser le système de handicap automatique ! 🏆
