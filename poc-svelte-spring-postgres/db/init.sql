-- Création de la table utilisateur
CREATE TABLE IF NOT EXISTS utilisateur (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Insertion de l'utilisateur admin
-- Sera créé par DataInitializer au démarrage avec le mot de passe correct
-- (pas d'insertion SQL pour éviter les problèmes de hash)

-- Création de la table classe_bateau
CREATE TABLE IF NOT EXISTS classe_bateau (
    id SERIAL PRIMARY KEY,
    nom_classe VARCHAR(25) NOT NULL
);

-- Création de la table bateau
CREATE TABLE IF NOT EXISTS bateau (
    id SERIAL PRIMARY KEY,
    nom_bateau VARCHAR(25),
    numero_voile INTEGER NOT NULL,
    nom_barreur VARCHAR(25),
    classe_bateau_id INTEGER NOT NULL,

    CONSTRAINT fk_bateau_classe
        FOREIGN KEY (classe_bateau_id)
        REFERENCES classe_bateau(id),

    CONSTRAINT unique_voile_par_classe
        UNIQUE (numero_voile, classe_bateau_id)
);

-- Création de la table classe_course
CREATE TABLE IF NOT EXISTS classe_course (
    id SERIAL PRIMARY KEY,
    nom_classe_course VARCHAR(25) NOT NULL,
    type_classe VARCHAR(10) NOT NULL,
    type_handicap VARCHAR(10),
    handicap_min NUMERIC,
    handicap_max NUMERIC
);

-- Création de la table serie
CREATE TABLE IF NOT EXISTS serie (
    id SERIAL PRIMARY KEY,
    nom_serie VARCHAR(50),
    classe_course_id INTEGER NOT NULL,

    CONSTRAINT fk_serie_classe_course
        FOREIGN KEY (classe_course_id)
        REFERENCES classe_course(id)
);

-- Insertion de données de test
INSERT INTO classe_bateau (nom_classe) VALUES ('Laser') ON CONFLICT DO NOTHING;
INSERT INTO classe_bateau (nom_classe) VALUES ('J70') ON CONFLICT DO NOTHING;
INSERT INTO classe_course (nom_classe_course, type_classe) VALUES ('Série 1', 'monotype') ON CONFLICT DO NOTHING;
INSERT INTO classe_course (nom_classe_course, type_classe) VALUES ('Série 2', 'monotype') ON CONFLICT DO NOTHING;
