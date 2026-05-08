package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/race-results")
public class RaceResultController {
    @Autowired
    private RaceResultService raceResultService;

    /**
     * Soumet un résultat de course brut et calcule automatiquement le handicap
     * 
     * POST /api/race-results/submit
     * Body: {
     *   "classeCourseId": 1,
     *   "bateauId": 1,
     *   "tempsBrutSecondes": 3600,
     *   "typeHandicap": "PY" ou "TMF"
     * }
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitRaceResult(@RequestBody Map<String, Object> payload) {
        try {
            Long classeCourseId = ((Number) payload.get("classeCourseId")).longValue();
            Long bateauId = ((Number) payload.get("bateauId")).longValue();
            BigDecimal tempsBrutSecondes = new BigDecimal(payload.get("tempsBrutSecondes").toString());
            String typeHandicap = (String) payload.get("typeHandicap");

            RaceResult result = raceResultService.submitRaceResult(classeCourseId, bateauId, tempsBrutSecondes, typeHandicap);
            
            return ResponseEntity.ok(new RaceResultDTO(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Récupère tous les résultats d'une course avec classement
     * 
     * GET /api/race-results/course/1
     */
    @GetMapping("/course/{classeCourseId}")
    public ResponseEntity<?> getRaceResults(@PathVariable Long classeCourseId) {
        try {
            List<HandicapCalculator.RaceResultWithRank> results = 
                raceResultService.getRaceResultsWithRanking(classeCourseId);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Récupère un résultat spécifique
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRaceResult(@PathVariable Long id) {
        return raceResultService.getRaceResult(id)
            .map(result -> ResponseEntity.ok(new RaceResultDTO(result)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Supprime un résultat
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRaceResult(@PathVariable Long id) {
        raceResultService.deleteRaceResult(id);
        return ResponseEntity.ok(Map.of("message", "Résultat supprimé"));
    }

    /**
     * DTO pour la réponse API
     */
    public static class RaceResultDTO {
        public Long id;
        public Long classeCourseId;
        public Long bateauId;
        public String bateauName;
        public BigDecimal tempsBrut;
        public String tempsBrutFormatted;
        public BigDecimal tempsCorrige;
        public String tempsCorrigenFormatted;
        public String typeHandicap;
        public String statut;

        public RaceResultDTO(RaceResult result) {
            this.id = result.getId();
            this.classeCourseId = result.getClasseCourse().getId();
            this.bateauId = result.getBateau().getId();
            this.bateauName = result.getBateau().getNomBateau();
            this.tempsBrut = result.getTempsBrutSecondes();
            this.tempsBrutFormatted = HandicapCalculator.formatTime(result.getTempsBrutSecondes());
            this.tempsCorrige = result.getTempsCorrectionSecondes();
            this.tempsCorrigenFormatted = HandicapCalculator.formatTime(result.getTempsCorrectionSecondes());
            this.typeHandicap = result.getTypeHandicap();
            this.statut = result.getStatut();
        }
    }
}
