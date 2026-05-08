@echo off
REM Redémarrer le backend Spring Boot avec une base de données fraîche

echo.
echo ============================================
echo Redémarrage du Backend Spring Boot
echo ============================================
echo.

cd /d "c:\Users\6292692\Desktop\poc-svelte-spring-postgres\backend"

echo Compilation et démarrage du backend...
echo.

call mvnw.cmd clean spring-boot:run

pause
