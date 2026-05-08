# Configuration des Coefficients de Handicap

## 📊 Classes de bateaux avec leurs coefficients

### Format de la base de données

Les coefficients sont stockés dans la table `classe_bateau` :

```sql
CREATE TABLE classe_bateau (
    id SERIAL PRIMARY KEY,
    nom_classe VARCHAR(25) NOT NULL,
    py NUMERIC(10, 2),              -- Portsmouth Yardstick
    tmf NUMERIC(10, 4)              -- Time Multiplying Factor
);
```

### Exemples de coefficients standard

| Classe        | PY     | TMF    | Description |
|---------------|--------|--------|-------------|
| Laser         | 1050   | 0.9800 | Monoplace olympique |
| J70           | 1000   | 0.9900 | Multi équipage 7m |
| Finn          | 1100   | 0.9700 | Monoplace gréement complet |
| 49er          | 950    | 1.0100 | Double haut-performance |
| Moth          | 900    | 1.0300 | Hydrofoil |
| Catamaran     | 850    | 1.0500 | Hautes performances |

## 🔧 Modifier les coefficients

### Via SQL

Pour mettre à jour un coefficient PY :

```sql
UPDATE classe_bateau 
SET py = 1050.00 
WHERE nom_classe = 'Laser';
```

Pour mettre à jour un coefficient TMF :

```sql
UPDATE classe_bateau 
SET tmf = 0.9800 
WHERE nom_classe = 'Laser';
```

### Via l'API (endpoint à ajouter - future amélioration)

```http
PATCH /api/classe-bateau/1
Content-Type: application/json

{
  "py": 1050.00,
  "tmf": 0.9800
}
```

## 📈 Comprendre les coefficients

### Portsmouth Yardstick (PY)

**Formule** : `Temps Corrigé = (Temps Brut × 1000) / PY`

- Plus la **valeur PY est basse**, plus le temps corrigé est long
- Plus la **valeur PY est haute**, plus le temps corrigé est court
- Les bateaux **rapides** ont un PY **élevé**
- Les bateaux **lents** ont un PY **faible**

**Exemple** :
- Bateau Laser (PY=1050) : 3600 sec × 1000 / 1050 = **3428.57 sec** ⚡ Plus rapide
- Bateau Catamaran (PY=850) : 3600 sec × 1000 / 850 = **4235.29 sec** 🐢 Plus lent

### Time Multiplying Factor (TMF)

**Formule** : `Temps Corrigé = Temps Brut × TMF`

- Plus la **valeur TMF est basse**, plus le temps corrigé est court
- Plus la **valeur TMF est haute**, plus le temps corrigé est long
- Les bateaux **rapides** ont un TMF **bas**
- Les bateaux **lents** ont un TMF **élevé**

**Exemple** :
- Bateau Laser (TMF=0.98) : 3600 sec × 0.98 = **3528 sec** ⚡ Plus rapide
- Bateau Catamaran (TMF=1.05) : 3600 sec × 1.05 = **3780 sec** 🐢 Plus lent

## 🎯 Utiliser les deux formules dans une même course

### Avantages et inconvénients

| Aspect | PY | TMF |
|--------|----|----|
| Complexité | Modérée | Simple |
| Précision | Bonne | Bonne |
| Utilisation | International | National |
| Calcul | Division | Multiplication |

### Recommandations

- **Courses internationales** : Utiliser PY (Portsmouth Yardstick)
- **Courses nationales** : Utiliser TMF ou PY selon les règlements
- **Courses mixtes classes** : Privilégier PY pour plus de fiabilité

## 📋 Checklist de configuration

- [ ] Vérifier les coefficients PY pour chaque classe
- [ ] Vérifier les coefficients TMF pour chaque classe
- [ ] Tester avec une course de référence
- [ ] Valider que les résultats sont équitables
- [ ] Documenter les coefficients utilisés
- [ ] Mettre à jour annuellement selon les statistiques

## 🔄 Mise à jour des coefficients

Les coefficients PY sont **officiellement publiés** par les organismes de voile (IYU - International Yacht Racing Union). 

Pour mettre à jour les coefficients :

1. Consulter les sources officielles : https://www.iyru.org/
2. Exécuter la requête SQL de mise à jour
3. Valider avec une course de test
4. Documenter la date de mise à jour

### Exemple de migration

```sql
-- Migration du 2026-05-06
BEGIN;

UPDATE classe_bateau SET py = 1050.00 WHERE nom_classe = 'Laser';
UPDATE classe_bateau SET py = 1000.00 WHERE nom_classe = 'J70';
UPDATE classe_bateau SET py = 1100.00 WHERE nom_classe = 'Finn';

UPDATE classe_bateau SET tmf = 0.9800 WHERE nom_classe = 'Laser';
UPDATE classe_bateau SET tmf = 0.9900 WHERE nom_classe = 'J70';
UPDATE classe_bateau SET tmf = 0.9700 WHERE nom_classe = 'Finn';

COMMIT;
```

## ❓ FAQ

### Q: Pourquoi dois-je utiliser des handicaps ?

**A**: Les handicaps permettent aux bateaux de **différentes classes et performances** de concourir équitablement. Sans handicap, les bateaux les plus rapides gagneraient toujours.

### Q: Quel handicap choisir pour ma course ?

**A**: 
- Si c'est une **compétition officielle** : Suivre les règlements (généralement PY)
- Si c'est une **course amicale** : Choisir TMF pour la simplicité
- Si vous mixez les classes : Préférer PY

### Q: Comment je sais si mes coefficients sont justes ?

**A**: Vérifiez que sur plusieurs courses :
- Chaque classe gagne approximativement **proportionnellement** au nombre d'inscrits
- Aucune classe n'est systématiquement favorisée
- Les bateaux individuels montrent de la variation

### Q: Puis-je modifier les coefficients pour une seule course ?

**A**: Actuellement non, ils sont globaux. À l'avenir, on pourrait ajouter des **coefficients par course**.

---

**Version** : 1.0  
**Dernière mise à jour** : Mai 2026
