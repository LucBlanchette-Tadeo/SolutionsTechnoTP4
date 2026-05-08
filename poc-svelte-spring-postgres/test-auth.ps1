#!/usr/bin/env pwsh

# Script de test du système d'authentification
# Usage: .\test-auth.ps1

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Tests d'Authentification" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier que les services sont lancés
Write-Host "1️⃣  Vérification des services..." -ForegroundColor Yellow
Write-Host ""

# Test Backend
Write-Host "   → Test Backend (http://localhost:8080)..." -ForegroundColor Gray
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/bateaux" -ErrorAction Stop -TimeoutSec 5
    Write-Host "   ✓ Backend réactif" -ForegroundColor Green
} catch {
    Write-Host "   ✗ Backend non accessible" -ForegroundColor Red
    Write-Host "     Lancez: cd backend && .\mvnw spring-boot:run" -ForegroundColor Yellow
}

# Test Frontend
Write-Host ""
Write-Host "   → Test Frontend (http://localhost:5173)..." -ForegroundColor Gray
try {
    $response = Invoke-WebRequest -Uri "http://localhost:5173" -ErrorAction Stop -TimeoutSec 5
    Write-Host "   ✓ Frontend réactif" -ForegroundColor Green
} catch {
    Write-Host "   ✗ Frontend non accessible" -ForegroundColor Red
    Write-Host "     Lancez: cd frontend && npm run dev" -ForegroundColor Yellow
}

# Test BD
Write-Host ""
Write-Host "   → Test Base de Données (localhost:5432)..." -ForegroundColor Gray
try {
    $tcpClient = New-Object System.Net.Sockets.TcpClient
    $tcpClient.Connect("localhost", 5432)
    if ($tcpClient.Connected) {
        Write-Host "   ✓ PostgreSQL accessible" -ForegroundColor Green
        $tcpClient.Close()
    }
} catch {
    Write-Host "   ✗ PostgreSQL non accessible" -ForegroundColor Red
    Write-Host "     Lancez: docker-compose -f db/docker-compose.yml up" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "2️⃣  Test d'authentification..." -ForegroundColor Yellow
Write-Host ""

# Test Login avec identifiants corrects
Write-Host "   → Test avec identifiants valides..." -ForegroundColor Gray
try {
    $loginData = @{
        username = "admin"
        password = "Admin123!"
    } | ConvertTo-Json

    $response = Invoke-WebRequest `
        -Uri "http://localhost:8080/api/auth/login" `
        -Method POST `
        -ContentType "application/json" `
        -Body $loginData `
        -ErrorAction Stop

    $result = $response.Content | ConvertFrom-Json
    
    if ($result.success) {
        Write-Host "   ✓ Login réussi (admin)" -ForegroundColor Green
        Write-Host "     Réponse: $($result.message)" -ForegroundColor Gray
    } else {
        Write-Host "   ✗ Login échoué: $($result.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "   ✗ Erreur lors du test de login" -ForegroundColor Red
    Write-Host "     $_" -ForegroundColor Gray
}

# Test Login avec mauvais mot de passe
Write-Host ""
Write-Host "   → Test avec mauvais mot de passe..." -ForegroundColor Gray
try {
    $loginData = @{
        username = "admin"
        password = "wrongpassword"
    } | ConvertTo-Json

    $response = Invoke-WebRequest `
        -Uri "http://localhost:8080/api/auth/login" `
        -Method POST `
        -ContentType "application/json" `
        -Body $loginData `
        -ErrorAction Stop

    $result = $response.Content | ConvertFrom-Json
    Write-Host "   ✓ Rejection correcte: $($result.message)" -ForegroundColor Green
} catch {
    if ($_.Exception.Response.StatusCode -eq 401) {
        Write-Host "   ✓ Rejection 401 (Unauthorized)" -ForegroundColor Green
    } else {
        Write-Host "   ✗ Erreur inattendue" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  ✅ Tests terminés!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Si tous les tests sont verts ✓ :" -ForegroundColor Cyan
Write-Host "  1. Ouvrez http://localhost:5173" -ForegroundColor White
Write-Host "  2. Connectez-vous avec: admin / Admin123!" -ForegroundColor White
Write-Host "  3. Explorez l'application" -ForegroundColor White
Write-Host ""

Read-Host "Appuyez sur Entrée pour quitter"
