# Script complet pour démarrer le système de handicap

Write-Host "╔════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║  Démarrage du Système de Handicap Automatique              ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

$rootDir = "c:\Users\6292692\Desktop\poc-svelte-spring-postgres"

# Étape 1 : Vérifier PostgreSQL
Write-Host "📊 Étape 1 : Vérification de PostgreSQL..." -ForegroundColor Yellow
$postgresRunning = docker ps --filter "name=db-db" --format "table {{.Names}}" 2>$null
if ($postgresRunning) {
    Write-Host "✓ PostgreSQL est en marche" -ForegroundColor Green
} else {
    Write-Host "⚠ PostgreSQL n'est pas en marche, démarrage..." -ForegroundColor Yellow
    Set-Location "$rootDir\db"
    docker-compose up -d
    Start-Sleep -Seconds 5
    Write-Host "✓ PostgreSQL démarré" -ForegroundColor Green
}
Write-Host ""

# Étape 2 : Avertissement
Write-Host "📢 IMPORTANT :" -ForegroundColor Yellow
Write-Host "   Les 3 terminaux doivent être lancés SÉPARÉMENT :" -ForegroundColor White
Write-Host ""
Write-Host "   Terminal 1 : Backend Spring Boot (port 8080)" -ForegroundColor Cyan
Write-Host "   Terminal 2 : Frontend Svelte/Vite (port 5173)" -ForegroundColor Cyan
Write-Host "   Terminal 3 : Navigateur web (http://localhost:5173)" -ForegroundColor Cyan
Write-Host ""

# Étape 3 : Démarrage du backend
Write-Host "🦁 Étape 2 : Préparation du Backend..." -ForegroundColor Yellow
Set-Location "$rootDir\backend"
Write-Host "   Compilation du projet Maven..." -ForegroundColor Gray

Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "Lancement du BACKEND (port 8080)" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
Write-Host "Attendez le message :" -ForegroundColor Yellow
Write-Host "'Tomcat started on port(s): 8080'" -ForegroundColor Yellow
Write-Host ""

# Exécuter le backend
& .\mvnw.cmd clean spring-boot:run

Write-Host ""
Write-Host "⚠️  Le backend s'est arrêté. Pour continuer :" -ForegroundColor Yellow
Write-Host "   1. Garder ce terminal ouvert" -ForegroundColor White
Write-Host "   2. Ouvrir un NOUVEAU terminal" -ForegroundColor White
Write-Host "   3. Exécuter : cd c:\Users\6292692\Desktop\poc-svelte-spring-postgres\frontend && npm run dev" -ForegroundColor White
