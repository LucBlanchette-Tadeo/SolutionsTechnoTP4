#!/usr/bin/env pwsh
<#
    .SYNOPSIS
    Script de vérification complète du système de handicap

    .DESCRIPTION
    Vérifie que tous les composants (Docker, Java, Node.js, Frontend, Backend, BDD) sont correctement installés et configurés
#>

Write-Host "`n╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     🔍 VÉRIFICATION SYSTÈME - HANDICAP VOILE             ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════════╝`n" -ForegroundColor Cyan

$failed = 0
$passed = 0

# Fonction pour afficher les résultats
function Test-Component {
    param (
        [string]$name,
        [scriptblock]$test
    )
    
    try {
        $result = & $test
        if ($result) {
            Write-Host "  ✅ $name" -ForegroundColor Green
            $script:passed++
            return $true
        } else {
            Write-Host "  ❌ $name" -ForegroundColor Red
            $script:failed++
            return $false
        }
    } catch {
        Write-Host "  ❌ $name" -ForegroundColor Red
        $script:failed++
        return $false
    }
}

# ============ 1. VÉRIFICATIONS SYSTÈME ============
Write-Host "`n📦 1. PRÉREQUIS SYSTÈME`n" -ForegroundColor Yellow

Test-Component "Docker Desktop" {
    $null = docker --version
    $LASTEXITCODE -eq 0
}

Test-Component "Docker Compose" {
    $null = docker-compose --version
    $LASTEXITCODE -eq 0
}

Test-Component "Java 21" {
    (java -version 2>&1) -match "21"
}

Test-Component "Node.js 20+" {
    [version]$nodeVersion = (node --version).TrimStart('v')
    $nodeVersion.Major -ge 20
}

Test-Component "npm" {
    $null = npm --version
    $LASTEXITCODE -eq 0
}

# ============ 2. VÉRIFICATIONS DES FICHIERS ============
Write-Host "`n📁 2. STRUCTURE DES FICHIERS`n" -ForegroundColor Yellow

$projectRoot = "c:\Users\6292692\Desktop\poc-svelte-spring-postgres"

Test-Component "Répertoire backend" {
    Test-Path "$projectRoot\backend" -PathType Container
}

Test-Component "Répertoire frontend" {
    Test-Path "$projectRoot\frontend" -PathType Container
}

Test-Component "Répertoire db" {
    Test-Path "$projectRoot\db" -PathType Container
}

Test-Component "pom.xml" {
    Test-Path "$projectRoot\backend\pom.xml" -PathType Leaf
}

Test-Component "package.json" {
    Test-Path "$projectRoot\frontend\package.json" -PathType Leaf
}

Test-Component "docker-compose.yml" {
    Test-Path "$projectRoot\db\docker-compose.yml" -PathType Leaf
}

Test-Component "init.sql" {
    Test-Path "$projectRoot\db\init.sql" -PathType Leaf
}

# ============ 3. VÉRIFICATIONS DES ENTITÉS JAVA ============
Write-Host "`n☕ 3. ENTITÉS BACKEND JAVA`n" -ForegroundColor Yellow

Test-Component "RaceResult.java" {
    Test-Path "$projectRoot\backend\src\main\java\com\example\backend\RaceResult.java" -PathType Leaf
}

Test-Component "RaceResultRepository.java" {
    Test-Path "$projectRoot\backend\src\main\java\com\example\backend\RaceResultRepository.java" -PathType Leaf
}

Test-Component "RaceResultService.java" {
    Test-Path "$projectRoot\backend\src\main\java\com\example\backend\RaceResultService.java" -PathType Leaf
}

Test-Component "RaceResultController.java" {
    Test-Path "$projectRoot\backend\src\main\java\com\example\backend\RaceResultController.java" -PathType Leaf
}

Test-Component "HandicapCalculator.java" {
    Test-Path "$projectRoot\backend\src\main\java\com\example\backend\HandicapCalculator.java" -PathType Leaf
}

Test-Component "CorsConfig.java" {
    Test-Path "$projectRoot\backend\src\main\java\com\example\backend\CorsConfig.java" -PathType Leaf
}

# ============ 4. VÉRIFICATIONS DES COMPOSANTS SVELTE ============
Write-Host "`n🎨 4. COMPOSANTS FRONTEND SVELTE`n" -ForegroundColor Yellow

Test-Component "RaceResults.svelte" {
    Test-Path "$projectRoot\frontend\src\lib\RaceResults.svelte" -PathType Leaf
}

Test-Component "resultats.svelte" {
    Test-Path "$projectRoot\frontend\src\routes\resultats.svelte" -PathType Leaf
}

Test-Component "api.ts" {
    Test-Path "$projectRoot\frontend\src\lib\api.ts" -PathType Leaf
}

Test-Component "App.svelte" {
    Test-Path "$projectRoot\frontend\src\App.svelte" -PathType Leaf
}

# ============ 5. VÉRIFICATIONS DE CONFIGURATION ============
Write-Host "`n⚙️  5. FICHIERS DE CONFIGURATION`n" -ForegroundColor Yellow

Test-Component "vite.config.ts" {
    Test-Path "$projectRoot\frontend\vite.config.ts" -PathType Leaf
}

Test-Component "tsconfig.json" {
    Test-Path "$projectRoot\frontend\tsconfig.json" -PathType Leaf
}

Test-Component "svelte.config.js" {
    Test-Path "$projectRoot\frontend\svelte.config.js" -PathType Leaf
}

# ============ 6. VÉRIFICATIONS DES SCRIPTS D'AUTOMATISATION ============
Write-Host "`n🚀 6. SCRIPTS D'AUTOMATISATION`n" -ForegroundColor Yellow

Test-Component "start-all.ps1" {
    Test-Path "$projectRoot\start-all.ps1" -PathType Leaf
}

Test-Component "restart-backend.bat" {
    Test-Path "$projectRoot\restart-backend.bat" -PathType Leaf
}

# ============ 7. VÉRIFICATION DES PORTS ============
Write-Host "`n🔌 7. VÉRIFICATION DES PORTS`n" -ForegroundColor Yellow

$port8080Free = $true
$port5173Free = $true
$port5432Free = $true

try {
    $connections = netstat -ano -p TCP 2>$null
    if ($connections -match ":8080.*LISTENING") { $port8080Free = $false }
    if ($connections -match ":5173.*LISTENING") { $port5173Free = $false }
    if ($connections -match ":5432.*LISTENING") { $port5432Free = $false }
} catch { }

if ($port8080Free) {
    Write-Host "  ✅ Port 8080 disponible" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  ⚠️  Port 8080 est occupé (backend ne pourra pas démarrer)" -ForegroundColor Yellow
}

if ($port5173Free) {
    Write-Host "  ✅ Port 5173 disponible" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  ⚠️  Port 5173 est occupé (frontend ne pourra pas démarrer)" -ForegroundColor Yellow
}

if ($port5432Free) {
    Write-Host "  ✅ Port 5432 disponible" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  ⚠️  Port 5432 est occupé (PostgreSQL ne pourra pas démarrer)" -ForegroundColor Yellow
}

# ============ 8. VÉRIFICATIONS DOCKER ============
Write-Host "`n🐳 8. DOCKER & BASE DE DONNÉES`n" -ForegroundColor Yellow

$dockerRunning = $false
try {
    docker ps >$null 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  ✅ Docker daemon en cours d'exécution" -ForegroundColor Green
        $script:passed++
        $dockerRunning = $true
    } else {
        Write-Host "  ❌ Docker daemon n'est pas démarré" -ForegroundColor Red
        $script:failed++
    }
} catch {
    Write-Host "  ❌ Docker daemon n'est pas accessible" -ForegroundColor Red
    $script:failed++
}

if ($dockerRunning) {
    $psqlRunning = docker ps --format "{{.Names}}" 2>$null | Select-String "postgres"
    if ($psqlRunning) {
        Write-Host "  ✅ PostgreSQL en cours d'exécution" -ForegroundColor Green
        $script:passed++
    } else {
        Write-Host "  ⚠️  PostgreSQL n'est pas en cours d'exécution (normal au premier démarrage)" -ForegroundColor Yellow
    }
}

# ============ 9. VÉRIFICATION DES DÉPENDANCES NPM ============
Write-Host "`n📦 9. DÉPENDANCES NPM`n" -ForegroundColor Yellow

$frontendNodeModules = Test-Path "$projectRoot\frontend\node_modules" -PathType Container
if ($frontendNodeModules) {
    Write-Host "  ✅ node_modules installés" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  ⚠️  node_modules non installés (à faire : 'npm install' dans frontend/)" -ForegroundColor Yellow
}

# ============ 10. VÉRIFICATION BUILD BACKEND ============
Write-Host "`n☕ 10. BUILD MAVEN`n" -ForegroundColor Yellow

$targetDir = Test-Path "$projectRoot\backend\target" -PathType Container
if ($targetDir) {
    Write-Host "  ✅ Backend a été compilé" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  ⚠️  Backend n'a pas encore été compilé (à faire : 'mvnw clean install')" -ForegroundColor Yellow
}

# ============ RESUME FINAL ============
Write-Host "`n╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║                    📊 RESUME                                  ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════════╝`n" -ForegroundColor Cyan

Write-Host "  ✅ Vérifications réussies : $passed" -ForegroundColor Green
Write-Host "  ❌ Vérifications échouées  : $failed`n" -ForegroundColor Red

if ($failed -eq 0) {
    Write-Host "`n🎉 EXCELLENT ! Votre système est prêt !`n" -ForegroundColor Green
    Write-Host "Les 3 prochaines étapes :" -ForegroundColor Cyan
    Write-Host "  1️⃣  Ouvrir 3 terminaux PowerShell" -ForegroundColor Yellow
    Write-Host "  2️⃣  Exécuter les commandes de démarrage (voir DEMARRAGE_COMPLET.md)" -ForegroundColor Yellow
    Write-Host "  3️⃣  Accéder à http://localhost:5173`n" -ForegroundColor Yellow
} elseif ($failed -le 3) {
    Write-Host "`n⚠️  Quelques ajustements sont nécessaires (voir ci-dessus)`n" -ForegroundColor Yellow
} else {
    Write-Host "`n❌ Plusieurs dépendances manquent. Veuillez installer les prérequis.`n" -ForegroundColor Red
}

Write-Host "Documentation complète : DEMARRAGE_COMPLET.md`n" -ForegroundColor Cyan

exit $failed
