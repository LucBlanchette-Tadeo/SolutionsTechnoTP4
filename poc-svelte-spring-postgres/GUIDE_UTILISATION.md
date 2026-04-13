# Guide d'Utilisation - Ajouter un Bateau

## 🎯 Objectif
Ce guide explique comment ajouter des bateaux à la base de données du système de gestion des bateaux de voile.

## 📌 Prérequis
- L'application est démarrée et fonctionnelle
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080/api
- Base de données: PostgreSQL sur localhost:5433

## 🌐 Méthode 1: Via l'Interface Web (Recommandée)

### Étapes

1. **Accédez à la page des bateaux**
   - Ouvrez votre navigateur
   - Allez sur: http://localhost:5173/bateaux

2. **Remplissez le formulaire**
   - **Nom du bateau**: Entrez un nom (ex: "Mon Bateau", "Laser 1")
   - **Numéro de voile**: Entrez un numéro unique par classe (ex: 123, 201)
   - **Nom du barreur**: Entrez le nom du skipper/barreur (ex: "Jean Dupont")
   - **Classe du bateau**: Sélectionnez une classe dans le menu déroulant (Laser, J70, etc.)

3. **Créez le bateau**
   - Cliquez sur le bouton **"Créer le bateau"**
   - Attendez la confirmation

4. **Vérification**
   - Un message de succès s'affiche en haut de la page
   - Le bateau apparaît immédiatement dans la liste ci-dessous

### Exemple de Création
```
Nom du bateau: "Bateau de Course"
Numéro de voile: 42
Nom du barreur: "Marie Durand"
Classe du bateau: Laser
↓
Cliquer "Créer le bateau"
↓
✅ "Bateau créé avec succès !"
```

## 💻 Méthode 2: Via l'API REST (PowerShell)

### Exemple 1: Bateau Simple

```powershell
$payload = @{
    nomBateau = "Mon Bateau"
    numeroVoile = 123
    nomBarreur = "Jean Dupont"
    classeBateau = @{ id = 1 }
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" `
  -Method POST `
  -ContentType "application/json" `
  -Body $payload
```

### Exemple 2: Ajouter Plusieurs Bateaux

```powershell
$bateaux = @(
    @{ nom = "Laser 1"; voile = 201; barreur = "Pierre Martin"; classe = 1 },
    @{ nom = "Laser 2"; voile = 202; barreur = "Marie Dupont"; classe = 1 },
    @{ nom = "J70"; voile = 101; barreur = "Luc Bernard"; classe = 2 }
)

foreach ($bateau in $bateaux) {
    $payload = @{
        nomBateau = $bateau.nom
        numeroVoile = $bateau.voile
        nomBarreur = $bateau.barreur
        classeBateau = @{ id = $bateau.classe }
    } | ConvertTo-Json
    
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" `
      -Method POST `
      -ContentType "application/json" `
      -Body $payload
    
    Write-Host "✅ $($bateau.nom) ajouté"
}
```

### Exemple 3: Avec cURL (Bash/Git Bash)

```bash
curl -X POST http://localhost:8080/api/bateaux \
  -H "Content-Type: application/json" \
  -d '{
    "nomBateau": "Mon Bateau",
    "numeroVoile": 999,
    "nomBarreur": "John Doe",
    "classeBateau": { "id": 1 }
  }'
```

## 🔍 Consultation des Bateaux

### Via l'Interface Web
- Accédez à http://localhost:5173/bateaux
- La liste s'affiche automatiquement dans le tableau

### Via l'API

**PowerShell:**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -Method GET | Select-Object -ExpandProperty Content | ConvertFrom-Json
```

**cURL:**
```bash
curl http://localhost:8080/api/bateaux
```

**Formaté (PowerShell):**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -Method GET | `
  Select-Object -ExpandProperty Content | `
  ConvertFrom-Json | `
  Select-Object nomBateau, numeroVoile, nomBarreur, @{Name="classe"; Expression={$_.classeBateau.nomClasse}} | `
  Format-Table
```

## 📋 Données Requises

### Champs Obligatoires
| Champ | Type | Description | Exemple |
|-------|------|-------------|---------|
| **numeroVoile** | Entier | Numéro unique par classe | 42, 123, 201 |
| **classeBateau** | Objet | ID de la classe | `{ "id": 1 }` |

### Champs Optionnels
| Champ | Type | Description | Exemple |
|-------|------|-------------|---------|
| nomBateau | Texte | Nom du bateau | "Mon Bateau" |
| nomBarreur | Texte | Nom du skipper | "Jean Dupont" |

## ✔️ Validations et Contraintes

### Au niveau de l'application
- ✅ Tous les champs du formulaire sont obligatoires
- ✅ La classe doit exister dans la base de données
- ✅ Le numéro de voile doit être unique par classe
- ✅ Des messages d'erreur explicites sont affichés

### Au niveau de la base de données
- ✅ Contrainte UNIQUE sur (numero_voile, classe_bateau_id)
- ✅ Clé étrangère vers la table classe_bateau
- ✅ Contrôle d'intégrité référentielle

## ⚠️ Erreurs Courantes et Solutions

### Erreur: "Classe de bateau introuvable"
**Cause**: La classe spécifiée n'existe pas
**Solution**: 
1. Vérifiez l'ID de la classe
2. Utilisez une classe existante (1=Laser, 2=J70)
3. Créez une nouvelle classe si nécessaire

### Erreur: "Le numéro de voile existe déjà"
**Cause**: Ce numéro est déjà utilisé dans la même classe
**Solution**: Choisissez un autre numéro de voile

### Erreur: "Tous les champs sont obligatoires"
**Cause**: Un ou plusieurs champs sont vides
**Solution**: Remplissez tous les champs du formulaire

### Erreur: "Impossible de se connecter au serveur"
**Cause**: Le backend n'est pas actif
**Solution**: 
1. Redémarrez le backend avec: `.\mvnw spring-boot:run`
2. Vérifiez que le port 8080 n'est pas bloqué

## 🔄 Workflow Complet

```
1. Accédez à http://localhost:5173/bateaux
        ↓
2. Remplissez les informations du bateau
        ↓
3. Cliquez "Créer le bateau"
        ↓
4. Frontend envoie: POST /api/bateaux
        ↓
5. Backend valide et sauvegarde
        ↓
6. PostgreSQL enregistre en base de données
        ↓
7. Réponse JSON retournée
        ↓
8. Frontend met à jour la liste
        ↓
9. Bateau visible dans le tableau
```

## 📊 Exemple de Réponse API

### Demande
```json
POST /api/bateaux
{
  "nomBateau": "Mon Bateau",
  "numeroVoile": 123,
  "nomBarreur": "Jean Dupont",
  "classeBateau": {
    "id": 1
  }
}
```

### Réponse (201 Created)
```json
{
  "id": 5,
  "nomBateau": "Mon Bateau",
  "numeroVoile": 123,
  "nomBarreur": "Jean Dupont",
  "classeBateau": {
    "id": 1,
    "nomClasse": "Laser"
  }
}
```

## 🎓 Conseils Pratiques

1. **Organisez par classe**: Utilisez un système de numérotation cohérent (ex: 200-299 pour Laser, 100-199 pour J70)
2. **Noms explicites**: Utilisez des noms qui identifient facilement le bateau
3. **Sauvegarde**: L'application sauvegarde automatiquement en base de données
4. **Modification**: Pour modifier un bateau, actuellement il faut utiliser l'API directement

## 📞 Support

En cas de problème:
1. Vérifiez que tous les services sont actifs (backend, BD, frontend)
2. Consultez les logs dans les fenêtres de terminal
3. Vérifiez la section "Troubleshooting" du README.md

## 📚 Ressources Supplémentaires

- **README.md** - Guide général du projet
- **IMPLEMENTATION.md** - Documentation technique
- **RESUME.md** - Résumé des fonctionnalités

---

**Dernière mise à jour**: 13 avril 2026
**Version**: 1.0
