-- Migration: Rendre classe_course_id nullable dans la table serie
-- Date: 2026-05-08
-- Raison: Une série peut être créée sans classe de course au départ

BEGIN;

-- Modifier la colonne classe_course_id pour la rendre nullable
ALTER TABLE serie 
ALTER COLUMN classe_course_id DROP NOT NULL;

-- Optionnel: ajouter les colonnes manquantes si elles n'existent pas
ALTER TABLE serie 
ADD COLUMN IF NOT EXISTS nombre_courses INTEGER DEFAULT 0 NOT NULL,
ADD COLUMN IF NOT EXISTS nombre_courses_a_compter INTEGER DEFAULT 0 NOT NULL,
ADD COLUMN IF NOT EXISTS classe_bateau_id BIGINT;

-- Ajouter la contrainte de clé étrangère pour classe_bateau_id si elle n'existe pas
ALTER TABLE serie
ADD CONSTRAINT IF NOT EXISTS fk_serie_classe_bateau
    FOREIGN KEY (classe_bateau_id)
    REFERENCES classe_bateau(id);

COMMIT;
