#!/usr/bin/env pwsh

# Script de démarrage complet du projet Bateau

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Système de Gestion des Bateaux" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier Docker
Write-Host "[1/3] Vérification de Docker..." -ForegroundColor Yellow
try {
    $dockerVersion = docker --version 2>$null
    Write-Host "[OK] Docker trouvé: $dockerVersion" -ForegroundColor Green
} catch {
    Write-Host "[ERREUR] Docker n'est pas installé ou pas accessible" -ForegroundColor Red
    exit 1
}

# Démarrer la base de données
Write-Host ""
Write-Host "[2/3] Démarrage de la base de données PostgreSQL..." -ForegroundColor Yellow
Set-Location db
docker-compose up -d | Out-Null
Start-Sleep -Seconds 3
Set-Location ..
Write-Host "[OK] Base de données démarrée sur localhost:5433" -ForegroundColor Green

# Démarrer le backend
Write-Host ""
Write-Host "[3/3] Démarrage du backend Spring Boot..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Le backend démarre dans une nouvelle fenêtre..." -ForegroundColor Gray

# Lancer le backend dans une nouvelle session PowerShell
$backendProcess = Start-Process pwsh -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\backend'; .\mvnw spring-boot:run" -PassThru

# Attendre le démarrage
Write-Host ""
Write-Host "En attente du démarrage du backend (15 secondes)..." -ForegroundColor Gray
Start-Sleep -Seconds 15

# Démarrer le frontend
Write-Host ""
Write-Host "Démarrage du frontend Svelte..." -ForegroundColor Yellow
Write-Host "Le frontend démarre dans une nouvelle fenêtre..." -ForegroundColor Gray

$frontendProcess = Start-Process pwsh -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\frontend'; npm install; npm run dev" -PassThru

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "✅ Application en cours de démarrage!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Accédez à l'application sur:" -ForegroundColor Cyan
Write-Host "  Frontend: http://localhost:5173" -ForegroundColor White
Write-Host "  Backend API: http://localhost:8080/api" -ForegroundColor White
Write-Host "  Base de données: localhost:5433" -ForegroundColor White
Write-Host ""
Write-Host "🔐 Identifiants de connexion:" -ForegroundColor Cyan
Write-Host "  Utilisateur: admin" -ForegroundColor White
Write-Host "  Mot de passe: Admin123!" -ForegroundColor White
Write-Host ""
Write-Host "Gestion des bateaux: http://localhost:5173/bateaux" -ForegroundColor Cyan
Write-Host ""
Write-Host "Appuyez sur Ctrl+C dans les fenêtres pour arrêter les services" -ForegroundColor Gray
Write-Host ""

Read-Host "Appuyez sur Entrée pour terminer ce script"
