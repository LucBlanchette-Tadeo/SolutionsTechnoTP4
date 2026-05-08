#!/usr/bin/env pwsh
<#
    Verification system for handicap application
#>

Write-Host "`n============================================================" -ForegroundColor Cyan
Write-Host "    CHECK SYSTEM - Handicap Sailing Application         " -ForegroundColor Cyan
Write-Host "============================================================`n" -ForegroundColor Cyan

$failed = 0
$passed = 0

function Test-Component {
    param ([string]$name, [scriptblock]$test)
    
    try {
        $result = & $test
        if ($result) {
            Write-Host "  OK  $name" -ForegroundColor Green
            $script:passed++
            return $true
        } else {
            Write-Host "  KO  $name" -ForegroundColor Red
            $script:failed++
            return $false
        }
    } catch {
        Write-Host "  KO  $name" -ForegroundColor Red
        $script:failed++
        return $false
    }
}

# ============ 1. SYSTEM REQUIREMENTS ============
Write-Host "`n1. SYSTEM REQUIREMENTS`n" -ForegroundColor Yellow

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

# ============ 2. DIRECTORY STRUCTURE ============
Write-Host "`n2. DIRECTORY STRUCTURE`n" -ForegroundColor Yellow

$projectRoot = "c:\Users\6292692\Desktop\poc-svelte-spring-postgres"

Test-Component "backend folder" {
    Test-Path "$projectRoot\backend" -PathType Container
}

Test-Component "frontend folder" {
    Test-Path "$projectRoot\frontend" -PathType Container
}

Test-Component "db folder" {
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

# ============ 3. BACKEND JAVA FILES ============
Write-Host "`n3. BACKEND JAVA FILES`n" -ForegroundColor Yellow

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

# ============ 4. FRONTEND SVELTE COMPONENTS ============
Write-Host "`n4. FRONTEND SVELTE COMPONENTS`n" -ForegroundColor Yellow

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

# ============ 5. CONFIG FILES ============
Write-Host "`n5. CONFIGURATION FILES`n" -ForegroundColor Yellow

Test-Component "vite.config.ts" {
    Test-Path "$projectRoot\frontend\vite.config.ts" -PathType Leaf
}

Test-Component "tsconfig.json" {
    Test-Path "$projectRoot\frontend\tsconfig.json" -PathType Leaf
}

Test-Component "svelte.config.js" {
    Test-Path "$projectRoot\frontend\svelte.config.js" -PathType Leaf
}

# ============ 6. AUTOMATION SCRIPTS ============
Write-Host "`n6. AUTOMATION SCRIPTS`n" -ForegroundColor Yellow

Test-Component "start-all.ps1" {
    Test-Path "$projectRoot\start-all.ps1" -PathType Leaf
}

Test-Component "restart-backend.bat" {
    Test-Path "$projectRoot\restart-backend.bat" -PathType Leaf
}

# ============ 7. PORTS AVAILABILITY ============
Write-Host "`n7. PORTS AVAILABILITY`n" -ForegroundColor Yellow

$port8080Free = $true
$port5173Free = $true
$port5432Free = $true

try {
    $netstat = netstat -ano -p TCP 2>$null
    if ($netstat -match ":8080.*LISTENING") { $port8080Free = $false }
    if ($netstat -match ":5173.*LISTENING") { $port5173Free = $false }
    if ($netstat -match ":5432.*LISTENING") { $port5432Free = $false }
} catch { }

if ($port8080Free) {
    Write-Host "  OK  Port 8080 available" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  WARNING  Port 8080 in use" -ForegroundColor Yellow
}

if ($port5173Free) {
    Write-Host "  OK  Port 5173 available" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  WARNING  Port 5173 in use" -ForegroundColor Yellow
}

if ($port5432Free) {
    Write-Host "  OK  Port 5432 available" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  WARNING  Port 5432 in use" -ForegroundColor Yellow
}

# ============ 8. DOCKER STATUS ============
Write-Host "`n8. DOCKER & DATABASE`n" -ForegroundColor Yellow

try {
    docker ps >$null 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  OK  Docker daemon running" -ForegroundColor Green
        $script:passed++
        
        $psql = docker ps --format "{{.Names}}" 2>$null | Select-String "postgres"
        if ($psql) {
            Write-Host "  OK  PostgreSQL running" -ForegroundColor Green
            $script:passed++
        } else {
            Write-Host "  INFO  PostgreSQL not running (expected at first start)" -ForegroundColor Yellow
        }
    } else {
        Write-Host "  KO  Docker daemon not accessible" -ForegroundColor Red
        $script:failed++
    }
} catch {
    Write-Host "  KO  Docker daemon not accessible" -ForegroundColor Red
    $script:failed++
}

# ============ 9. NPM DEPENDENCIES ============
Write-Host "`n9. NPM DEPENDENCIES`n" -ForegroundColor Yellow

if (Test-Path "$projectRoot\frontend\node_modules" -PathType Container) {
    Write-Host "  OK  node_modules installed" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  INFO  node_modules not installed (run 'npm install' in frontend/)" -ForegroundColor Yellow
}

# ============ 10. MAVEN BUILD ============
Write-Host "`n10. MAVEN BUILD`n" -ForegroundColor Yellow

if (Test-Path "$projectRoot\backend\target" -PathType Container) {
    Write-Host "  OK  Backend compiled" -ForegroundColor Green
    $script:passed++
} else {
    Write-Host "  INFO  Backend not compiled (run 'mvnw clean install')" -ForegroundColor Yellow
}

# ============ FINAL SUMMARY ============
Write-Host "`n============================================================" -ForegroundColor Cyan
Write-Host "                        SUMMARY                           " -ForegroundColor Cyan
Write-Host "============================================================`n" -ForegroundColor Cyan

Write-Host "  OK  Passed : $passed" -ForegroundColor Green
Write-Host "  KO  Failed : $failed`n" -ForegroundColor Red

if ($failed -eq 0) {
    Write-Host "SUCCESS! Your system is ready!`n" -ForegroundColor Green
    Write-Host "Next steps:" -ForegroundColor Cyan
    Write-Host "  1. Open 3 PowerShell terminals" -ForegroundColor Yellow
    Write-Host "  2. Run startup commands (see DEMARRAGE_COMPLET.md)" -ForegroundColor Yellow
    Write-Host "  3. Access http://localhost:5173`n" -ForegroundColor Yellow
} elseif ($failed -le 3) {
    Write-Host "Some adjustments needed (see above)`n" -ForegroundColor Yellow
} else {
    Write-Host "Please install missing dependencies`n" -ForegroundColor Red
}

Write-Host "Full documentation : DEMARRAGE_COMPLET.md`n" -ForegroundColor Cyan

exit $failed
