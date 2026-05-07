-- Migration: Add nombreCourses and nombreCoursesACompter columns to serie table

-- Remove the classe_bateau_id foreign key constraint if it exists (from previous migration)
-- This is needed because we're refactoring serie to not require a classe_bateau

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE table_name = 'serie' AND constraint_name = 'fk_serie_classe_bateau'
    ) THEN
        ALTER TABLE public.serie DROP CONSTRAINT fk_serie_classe_bateau;
    END IF;
END
$$;

-- Make classe_bateau_id nullable (if it exists)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'serie' AND column_name = 'classe_bateau_id'
    ) THEN
        ALTER TABLE public.serie ALTER COLUMN classe_bateau_id SET NOT NULL;
        -- Actually, we want it to be nullable, so this line makes it nullable
        ALTER TABLE public.serie ALTER COLUMN classe_bateau_id DROP NOT NULL;
        -- Re-add the foreign key constraint allowing NULL
        ALTER TABLE public.serie
        ADD CONSTRAINT fk_serie_classe_bateau FOREIGN KEY (classe_bateau_id) 
        REFERENCES public.classe_bateau(id) ON DELETE SET NULL;
    ELSE
        -- If classe_bateau_id doesn't exist, create it as nullable
        ALTER TABLE public.serie
        ADD COLUMN classe_bateau_id INTEGER;
        ALTER TABLE public.serie
        ADD CONSTRAINT fk_serie_classe_bateau FOREIGN KEY (classe_bateau_id) 
        REFERENCES public.classe_bateau(id) ON DELETE SET NULL;
    END IF;
END
$$;

-- Add new columns for serie configuration
ALTER TABLE public.serie
ADD COLUMN IF NOT EXISTS nombre_courses INTEGER DEFAULT 0,
ADD COLUMN IF NOT EXISTS nombre_courses_a_compter INTEGER DEFAULT 0;

-- Verify the schema
SELECT * FROM public.serie LIMIT 0;

-- Show all columns in serie table
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'serie' 
ORDER BY ordinal_position;
