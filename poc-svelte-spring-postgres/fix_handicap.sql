-- Script pour corriger les données de handicap

-- Vérifier les données actuelles
SELECT id, nom_classe, py, tmf FROM classe_bateau;

-- Mettre à jour les données avec les coefficients PY et TMF
UPDATE classe_bateau SET py = 1050.00, tmf = 0.9800 WHERE nom_classe = 'Laser';
UPDATE classe_bateau SET py = 1000.00, tmf = 0.9900 WHERE nom_classe = 'J70';

-- Pour toute autre classe de bateau existante, ajouter des valeurs par défaut
UPDATE classe_bateau SET py = 1000.00, tmf = 1.0000 WHERE py IS NULL OR py = 0;

-- Vérifier le résultat
SELECT id, nom_classe, py, tmf FROM classe_bateau;
