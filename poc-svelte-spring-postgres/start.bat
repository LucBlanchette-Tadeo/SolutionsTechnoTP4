@echo off
REM Script de démarrage complet du projet Bateau

echo.
echo ========================================
echo    Système de Gestion des Bateaux
echo ========================================
echo.

REM Vérifier Docker
echo [1/3] Vérification de Docker...
docker --version >nul 2>&1
if errorlevel 1 (
    echo [ERREUR] Docker n'est pas installé ou pas accessible
    exit /b 1
)
echo [OK] Docker trouvé

REM Démarrer la base de données
echo.
echo [2/3] Démarrage de la base de données PostgreSQL...
cd db
docker-compose up -d >nul 2>&1
timeout /t 3 /nobreak >nul
cd ..
echo [OK] Base de données démarrée sur localhost:5433

REM Démarrer le backend
echo.
echo [3/3] Démarrage du backend Spring Boot...
echo.
echo Le backend démarre dans une nouvelle fenêtre...
start cmd /k "cd backend && .\mvnw spring-boot:run"

REM Attendre que le backend soit prêt
echo.
echo En attente du démarrage du backend (10 secondes)...
timeout /t 10 /nobreak >nul

REM Démarrer le frontend
echo.
echo Démarrage du frontend Svelte...
echo Le frontend démarre dans une nouvelle fenêtre...
start cmd /k "cd frontend && npm install && npm run dev"

echo.
echo ========================================
echo ✅ Application en cours de démarrage!
echo ========================================
echo.
echo Accédez à l'application sur:
echo   Frontend: http://localhost:5173
echo   Backend API: http://localhost:8080/api
echo   Base de données: localhost:5433
echo.
echo Gestion des bateaux: http://localhost:5173/bateaux
echo.
echo Appuyez sur Ctrl+C dans les fenêtres pour arrêter les services
echo.
pause
