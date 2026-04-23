-- Supprimer l'ancienne table et trigger si existants
DROP TRIGGER IF EXISTS trigger_check_bateau_classe ON inscription;
DROP FUNCTION IF EXISTS check_bateau_classe_compatibility();
DROP TABLE IF EXISTS inscription;

-- Création de la table inscription
CREATE TABLE inscription (
    id SERIAL PRIMARY KEY,
    bateau_id INTEGER NOT NULL,
    classe_course_id INTEGER NOT NULL,
    date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    classement INTEGER,
    points DECIMAL(10, 2),
    
    -- Clés étrangères
    FOREIGN KEY (bateau_id) REFERENCES bateau(id) ON DELETE CASCADE,
    FOREIGN KEY (classe_course_id) REFERENCES classe_course(id) ON DELETE CASCADE,
    
    -- Contrainte d'unicité : un bateau ne peut participer qu'une fois par course
    UNIQUE(bateau_id, classe_course_id)
);

-- Index pour améliorer les performances
CREATE INDEX idx_inscription_bateau ON inscription(bateau_id);
CREATE INDEX idx_inscription_classe_course ON inscription(classe_course_id);

-- Trigger pour vérifier que le bateau correspond à la classe de la course
CREATE OR REPLACE FUNCTION check_bateau_classe_compatibility()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM bateau b
        JOIN classe_bateau cb ON b.classe_bateau_id = cb.id
        JOIN classe_course cc ON NEW.classe_course_id = cc.id
        WHERE b.id = NEW.bateau_id
        AND cb.nom_classe = cc.type_classe
    ) THEN
        RAISE EXCEPTION 'Le bateau n''appartient pas à la classe de la course';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_bateau_classe
BEFORE INSERT ON inscription
FOR EACH ROW
EXECUTE FUNCTION check_bateau_classe_compatibility();
