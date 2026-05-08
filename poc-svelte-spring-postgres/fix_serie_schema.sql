-- Migration: Change Serie to use ClasseBateau instead of ClasseCourse
-- This allows Series to filter boats by class, not by course class

-- Drop foreign key constraint
ALTER TABLE public.serie 
DROP CONSTRAINT fk_serie_classe_course;

-- Rename column
ALTER TABLE public.serie 
RENAME COLUMN classe_course_id TO classe_bateau_id;

-- Add new foreign key constraint
ALTER TABLE public.serie
ADD CONSTRAINT fk_serie_classe_bateau FOREIGN KEY (classe_bateau_id) 
REFERENCES public.classe_bateau(id);

-- Verify the change
SELECT * FROM public.serie;
