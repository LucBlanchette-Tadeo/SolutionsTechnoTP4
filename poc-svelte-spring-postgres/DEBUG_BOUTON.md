# 🐛 Dépannage du Bouton Créer

## 🔍 Problème Décrit
"Le bouton créer ne fait rien"

## 🧪 Diagnostic

### 1. Vérifier la Console du Navigateur
1. Ouvrir http://localhost:5173
2. Appuyer sur **F12**
3. Aller à l'onglet **Console**
4. Cliquer sur le bouton "➕ Créer"
5. Vérifier s'il y a des **erreurs rouges** ou **warnings jaunes**

### 2. Vérifier l'Onglet Network
1. Ouvrir F12 → **Network**
2. Remplir le formulaire Bateau
3. Cliquer sur "➕ Créer"
4. Vérifier si une requête **POST /api/bateaux** apparaît
5. Si oui: Vérifier le Status (200 = succès, 400/500 = erreur)
6. Si non: Le formulaire n'envoie pas la requête

### 3. Causes Possibles

#### Cas A: Aucune requête POST n'apparaît
**Cause**: Le formulaire n'est pas soumis
**Solution**: Vérifier que tous les champs sont remplis (validation échoue sinon)

#### Cas B: La requête POST apparaît mais Status 500
**Cause**: Erreur backend
**Solution**: Vérifier les logs du backend (terminal Spring Boot)

#### Cas C: La requête POST réussit (200) mais aucun bateau n'apparaît
**Cause**: Le frontend ne rafraîchit pas la liste
**Solution**: Attendre 3 secondes ou rafraîchir manuellement (F5)

#### Cas D: Message d'erreur rouge
**Cause**: Erreur JavaScript ou API
**Solution**: Lire le message d'erreur dans la console

---

## 🔧 Solutions Rapides à Essayer

### Solution 1: Remplir TOUS les champs
```
Nom du bateau: "Test Boat"
Numéro de voile: "42"
Nom du barreur: "Jean"
Classe: Sélectionner "Laser" dans le dropdown
```

Puis cliquer "➕ Créer"

### Solution 2: Ouvrir la Console (F12)
1. Si vous voyez des erreurs rouges → noter le message
2. Si pas d'erreur → vérifier l'onglet Network

### Solution 3: Tester l'API Directement
```powershell
# Dans PowerShell
$body = @{
  nomBateau = "Test Direct"
  numeroVoile = 100
  nomBarreur = "Test"
  classeBateau = @{ id = 1 }
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body $body | ConvertFrom-Json
```

Si ça fonctionne: Le problème est au frontend  
Si ça échoue: Le problème est au backend

---

## 📝 Checklist de Dépannage

- [ ] Tous les champs du formulaire sont-ils remplis?
- [ ] Le dropdown "Classe" a-t-il une sélection?
- [ ] La console (F12 → Console) affiche-t-elle des erreurs?
- [ ] L'onglet Network (F12 → Network) montre-t-il une requête POST?
- [ ] Le backend écoute sur le port 8080?
- [ ] PostgreSQL est-il actif (Docker)?

---

## 🎯 Prochaines Étapes

**Après avoir ouvert la console (F12) et rempli le formulaire:**

1. Dites-moi ce que vous voyez quand vous cliquez sur "Créer"
2. Dites-moi si vous voyez une requête POST dans Network
3. Dites-moi s'il y a des messages d'erreur en rouge

Avec ces infos, je pourrai corriger le problème exactement!
