#!/usr/bin/env pwsh
# Migration Script - Apply Serie/Bateau schema changes

Write-Host "🔄 Migration: Serie/Bateau Structure Update" -ForegroundColor Cyan
Write-Host "=" * 60

# Configuration
$PG_USER = "poc"
$PG_DB = "pocdb"
$PG_HOST = "localhost"
$PG_PORT = "5433"

Write-Host "`n📋 Configuration:" -ForegroundColor Yellow
Write-Host "  Utilisateur: $PG_USER"
Write-Host "  Base de données: $PG_DB"
Write-Host "  Host: $PG_HOST"
Write-Host "  Port: $PG_PORT"

# SQL Migration Script
$SQL_MIGRATION = @"
-- Migration: Change Serie to use ClasseBateau instead of ClasseCourse
-- This allows Series to filter boats by class, not by course class

BEGIN TRANSACTION;

-- Drop old foreign key constraint
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
SELECT 'Migration completed successfully!' as status;
SELECT * FROM public.serie;

COMMIT;
"@

# Write migration to temp file
$TEMP_SQL = "$env:TEMP\migration_serie_bateau.sql"
$SQL_MIGRATION | Out-File -FilePath $TEMP_SQL -Encoding UTF8

Write-Host "`n🔍 Vérification de la connexion PostgreSQL..." -ForegroundColor Yellow

# Test connection
$TEST_CONN = & psql -U $PG_USER -d $PG_DB -h $PG_HOST -p $PG_PORT -c "SELECT 1;" 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Impossible de se connecter à PostgreSQL" -ForegroundColor Red
    Write-Host "Erreur: $TEST_CONN" -ForegroundColor Red
    Write-Host "`nVérifiez que:" -ForegroundColor Yellow
    Write-Host "  1. PostgreSQL est démarré"
    Write-Host "  2. Les identifiants sont corrects (utilisateur: $PG_USER)"
    Write-Host "  3. La base de données '$PG_DB' existe"
    exit 1
}

Write-Host "✅ Connexion PostgreSQL OK" -ForegroundColor Green

Write-Host "`n⚠️  Attention:" -ForegroundColor Yellow
Write-Host "  Cette migration va modifier la structure de la table 'serie'"
Write-Host "  - Suppression de la contrainte: fk_serie_classe_course"
Write-Host "  - Renommage de la colonne: classe_course_id → classe_bateau_id"
Write-Host "  - Création de nouvelle contrainte: fk_serie_classe_bateau"

$CONFIRMATION = Read-Host "`nContinuer? (y/n)"
if ($CONFIRMATION -ne "y" -and $CONFIRMATION -ne "Y") {
    Write-Host "Migration annulée." -ForegroundColor Yellow
    exit 0
}

Write-Host "`n🚀 Application de la migration..." -ForegroundColor Cyan

# Apply migration
$RESULT = & psql -U $PG_USER -d $PG_DB -h $PG_HOST -p $PG_PORT -f $TEMP_SQL 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n❌ Erreur lors de la migration:" -ForegroundColor Red
    Write-Host $RESULT -ForegroundColor Red
    Write-Host "`n💡 Solutions possibles:" -ForegroundColor Yellow
    Write-Host "  1. La table 'serie' n'existe pas"
    Write-Host "  2. La contrainte a déjà été supprimée"
    Write-Host "  3. Les données en base sont incompatibles"
    exit 1
}

Write-Host "`n✅ Migration appliquée avec succès!" -ForegroundColor Green
Write-Host $RESULT -ForegroundColor Green

Write-Host "`n📋 Étapes suivantes:" -ForegroundColor Cyan
Write-Host "  1. Redémarrer le backend: .\mvnw.cmd clean spring-boot:run"
Write-Host "  2. Recharger le frontend (Ctrl+Shift+R dans le navigateur)"
Write-Host "  3. Tester la création d'une nouvelle série"

# Cleanup
Remove-Item $TEMP_SQL -Force -ErrorAction SilentlyContinue

Write-Host "`n✨ Migration terminée!" -ForegroundColor Green
Write-Host "=" * 60
