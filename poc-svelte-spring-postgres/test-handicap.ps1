# Test du Système de Handicap Automatique
# Ce script teste les endpoints API pour vérifier le calcul des handicaps PY et TMF

$API_URL = "http://localhost:8080/api"

Write-Host "🧪 Tests du Système de Handicap Automatique" -ForegroundColor Cyan
Write-Host "==========================================`n" -ForegroundColor Cyan

# Test 1 : Récupérer les classes de course
Write-Host "📋 Test 1: Récupération des classes de course..." -ForegroundColor Yellow
$courses = Invoke-RestMethod -Uri "$API_URL/classe-course" -Method Get
Write-Host "✅ Classes trouvées:" -ForegroundColor Green
$courses | ForEach-Object { Write-Host "   - ID: $($_.id), Nom: $($_.nomClasseCourse)" }
$courseId = $courses[0].id
Write-Host ""

# Test 2 : Récupérer les bateaux
Write-Host "📋 Test 2: Récupération des bateaux..." -ForegroundColor Yellow
$bateaux = Invoke-RestMethod -Uri "$API_URL/bateaux" -Method Get
Write-Host "✅ Bateaux trouvés:" -ForegroundColor Green
$bateaux | ForEach-Object { Write-Host "   - ID: $($_.id), Nom: $($_.nomBateau) (Classe: $($_.classeBateau.nomClasse), PY: $($_.classeBateau.py))" }
Write-Host ""

# Test 3 : Soumettre un résultat avec handicap PY
if ($bateaux.Count -gt 0 -and $courses.Count -gt 0) {
    $bateau1 = $bateaux[0]
    
    Write-Host "🏁 Test 3: Soumission d'un résultat (Handicap PY)..." -ForegroundColor Yellow
    
    $payload = @{
        classeCourseId = $courseId
        bateauId = $bateau1.id
        tempsBrutSecondes = 3600
        typeHandicap = "PY"
    } | ConvertTo-Json
    
    try {
        $result = Invoke-RestMethod -Uri "$API_URL/race-results/submit" `
            -Method Post `
            -Headers @{ "Content-Type" = "application/json" } `
            -Body $payload
        
        Write-Host "✅ Résultat enregistré:" -ForegroundColor Green
        Write-Host "   Bateau: $($result.bateauName)" -ForegroundColor Green
        Write-Host "   Temps brut: $($result.tempsBrutFormatted)" -ForegroundColor Green
        Write-Host "   Type: $($result.typeHandicap)" -ForegroundColor Green
        Write-Host "   Temps corrigé: $($result.tempsCorrigenFormatted)" -ForegroundColor Cyan
        Write-Host ""
    } catch {
        Write-Host "❌ Erreur: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host ""
    }
}

# Test 4 : Soumettre un résultat avec handicap TMF (si 2ème bateau existe)
if ($bateaux.Count -gt 1 -and $courses.Count -gt 0) {
    $bateau2 = $bateaux[1]
    
    Write-Host "🏁 Test 4: Soumission d'un résultat (Handicap TMF)..." -ForegroundColor Yellow
    
    $payload = @{
        classeCourseId = $courseId
        bateauId = $bateau2.id
        tempsBrutSecondes = 3500
        typeHandicap = "TMF"
    } | ConvertTo-Json
    
    try {
        $result = Invoke-RestMethod -Uri "$API_URL/race-results/submit" `
            -Method Post `
            -Headers @{ "Content-Type" = "application/json" } `
            -Body $payload
        
        Write-Host "✅ Résultat enregistré:" -ForegroundColor Green
        Write-Host "   Bateau: $($result.bateauName)" -ForegroundColor Green
        Write-Host "   Temps brut: $($result.tempsBrutFormatted)" -ForegroundColor Green
        Write-Host "   Type: $($result.typeHandicap)" -ForegroundColor Green
        Write-Host "   Temps corrigé: $($result.tempsCorrigenFormatted)" -ForegroundColor Cyan
        Write-Host ""
    } catch {
        Write-Host "❌ Erreur: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host ""
    }
}

# Test 5 : Récupérer les résultats avec classement
Write-Host "📊 Test 5: Récupération des résultats avec classement..." -ForegroundColor Yellow
try {
    $results = Invoke-RestMethod -Uri "$API_URL/race-results/course/$courseId" -Method Get
    
    if ($results.Count -gt 0) {
        Write-Host "✅ Résultats classés:" -ForegroundColor Green
        Write-Host ""
        Write-Host "Position | Bateau           | Temps Brut  | Handicap | Temps Corrigé" -ForegroundColor Cyan
        Write-Host "---------|------------------|-------------|----------|---------------" -ForegroundColor Cyan
        
        $results | ForEach-Object {
            $pos = [string]$_.positionCorrigee
            $name = $_.bateauName.PadRight(16)
            $tempsBrut = $_.tempsBrut.ToString().PadLeft(11)
            $handicap = $_.typeHandicap.PadLeft(8)
            $tempsCorrige = $_.tempsCorrige.ToString().PadLeft(13)
            
            Write-Host "$($pos.PadRight(8)) | $name | $tempsBrut | $handicap | $tempsCorrige"
        }
        Write-Host ""
    } else {
        Write-Host "⚠️  Aucun résultat trouvé" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Erreur: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Résumé
Write-Host "✅ Tests complétés !" -ForegroundColor Green
Write-Host ""
Write-Host "📌 Résumé:" -ForegroundColor Cyan
Write-Host "   - Les résultats sont classés par temps CORRIGÉ" -ForegroundColor White
Write-Host "   - Formule PY: (Temps Brut × 1000) / PY" -ForegroundColor White
Write-Host "   - Formule TMF: Temps Brut × TMF" -ForegroundColor White
Write-Host "   - Chaque bateau peut avoir un handicap différent pour des résultats équitables" -ForegroundColor White
