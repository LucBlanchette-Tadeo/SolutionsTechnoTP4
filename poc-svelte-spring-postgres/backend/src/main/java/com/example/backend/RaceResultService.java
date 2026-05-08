package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RaceResultService {
    @Autowired
    private RaceResultRepository raceResultRepository;

    @Autowired
    private ClasseCourseRepository classeCourseRepository;

    @Autowired
    private BateauRepository bateauRepository;

    /**
     * Enregistre un résultat brut et calcule automatiquement le handicap
     * 
     * @param classeCourseId ID de la classe de course
     * @param bateauId ID du bateau
     * @param tempsBrutSecondes Temps brut en secondes
     * @param typeHandicap Type de handicap ("PY" ou "TMF")
     * @return Résultat de course complété
     */
    public RaceResult submitRaceResult(Long classeCourseId, Long bateauId, 
                                       BigDecimal tempsBrutSecondes, String typeHandicap) {
        // Récupérer les entités
        ClasseCourse classeCourse = classeCourseRepository.findById(classeCourseId)
            .orElseThrow(() -> new RuntimeException("ClasseCourse non trouvée"));
        
        Bateau bateau = bateauRepository.findById(bateauId)
            .orElseThrow(() -> new RuntimeException("Bateau non trouvé"));

        // Calculer le temps corrigé
        BigDecimal tempsCorrige;
        if ("PY".equalsIgnoreCase(typeHandicap)) {
            BigDecimal py = bateau.getClasseBateau().getPy();
            if (py == null || py.compareTo(BigDecimal.ZERO) <= 0) {
                // Valeur par défaut si non défini
                py = new BigDecimal("1000");
                bateau.getClasseBateau().setPy(py);
            }
            tempsCorrige = HandicapCalculator.calculatePY(tempsBrutSecondes, py);
        } else if ("TMF".equalsIgnoreCase(typeHandicap)) {
            BigDecimal tmf = bateau.getClasseBateau().getTmf();
            if (tmf == null || tmf.compareTo(BigDecimal.ZERO) <= 0) {
                // Valeur par défaut si non défini
                tmf = new BigDecimal("1.0000");
                bateau.getClasseBateau().setTmf(tmf);
            }
            tempsCorrige = HandicapCalculator.calculateTMF(tempsBrutSecondes, tmf);
        } else {
            throw new IllegalArgumentException("Type de handicap invalide : " + typeHandicap);
        }

        // Créer ou mettre à jour le résultat
        Optional<RaceResult> existingResult = raceResultRepository
            .findByClasseCourseIdAndBateauId(classeCourseId, bateauId);
        
        RaceResult result;
        if (existingResult.isPresent()) {
            result = existingResult.get();
        } else {
            result = new RaceResult(classeCourse, bateau, tempsBrutSecondes);
        }

        result.setTempsBrutSecondes(tempsBrutSecondes);
        result.setTempsCorrectionSecondes(tempsCorrige);
        result.setTypeHandicap(typeHandicap);
        result.setStatut("OK");

        return raceResultRepository.save(result);
    }

    /**
     * Récupère tous les résultats d'une course avec classement corrigé
     * 
     * @param classeCourseId ID de la classe de course
     * @return Liste des résultats avec positions
     */
    public List<HandicapCalculator.RaceResultWithRank> getRaceResultsWithRanking(Long classeCourseId) {
        List<RaceResult> results = raceResultRepository.findByClasseCourseId(classeCourseId);
        results.sort((a, b) -> a.getTempsCorrectionSecondes().compareTo(b.getTempsCorrectionSecondes()));
        return HandicapCalculator.rankResults(results);
    }

    /**
     * Récupère un résultat spécifique
     */
    public Optional<RaceResult> getRaceResult(Long id) {
        return raceResultRepository.findById(id);
    }

    /**
     * Supprime un résultat
     */
    public void deleteRaceResult(Long id) {
        raceResultRepository.deleteById(id);
    }
}
