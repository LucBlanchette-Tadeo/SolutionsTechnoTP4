package com.example.backend;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HandicapCalculator {
    /**
     * Calcule le temps corrigé selon la formule PY (Portsmouth Yardstick)
     * Formule : Temps Corrigé = (Temps Brut en secondes × 1000) / PY
     * 
     * @param tempsBrutSecondes Temps brut en secondes
     * @param py Coefficient Portsmouth Yardstick
     * @return Temps corrigé en secondes
     */
    public static BigDecimal calculatePY(BigDecimal tempsBrutSecondes, BigDecimal py) {
        if (py == null || py.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("PY doit être supérieur à 0");
        }
        BigDecimal numerator = tempsBrutSecondes.multiply(new BigDecimal("1000"));
        return numerator.divide(py, 2, RoundingMode.HALF_UP);
    }

    /**
     * Calcule le temps corrigé selon la formule TMF (Time Multiplying Factor)
     * Formule : Temps Corrigé = Temps Brut en secondes × TMF
     * 
     * @param tempsBrutSecondes Temps brut en secondes
     * @param tmf Coefficient Time Multiplying Factor
     * @return Temps corrigé en secondes
     */
    public static BigDecimal calculateTMF(BigDecimal tempsBrutSecondes, BigDecimal tmf) {
        if (tmf == null || tmf.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("TMF doit être supérieur à 0");
        }
        return tempsBrutSecondes.multiply(tmf).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Classe interne pour stocker les données de classement
     */
    public static class RaceResultWithRank {
        public Long raceResultId;
        public String bateauName;
        public BigDecimal tempsBrut;
        public BigDecimal tempsCorrige;
        public Integer positionBrute;
        public Integer positionCorrigee;
        public String typeHandicap;

        public RaceResultWithRank(Long raceResultId, String bateauName, BigDecimal tempsBrut, 
                                BigDecimal tempsCorrige, Integer positionBrute, 
                                Integer positionCorrigee, String typeHandicap) {
            this.raceResultId = raceResultId;
            this.bateauName = bateauName;
            this.tempsBrut = tempsBrut;
            this.tempsCorrige = tempsCorrige;
            this.positionBrute = positionBrute;
            this.positionCorrigee = positionCorrigee;
            this.typeHandicap = typeHandicap;
        }
    }

    /**
     * Calcule les classements finaux à partir des résultats
     * 
     * @param results Liste des RaceResult
     * @return Liste ordonnée par temps corrigé avec positions recalculées
     */
    public static List<RaceResultWithRank> rankResults(List<RaceResult> results) {
        List<RaceResultWithRank> rankedResults = new ArrayList<>();

        // Créer les objets avec rang brut
        int brutRank = 1;
        for (RaceResult result : results) {
            rankedResults.add(new RaceResultWithRank(
                result.getId(),
                result.getBateau().getNomBateau(),
                result.getTempsBrutSecondes(),
                result.getTempsCorrectionSecondes(),
                brutRank,
                null,
                result.getTypeHandicap()
            ));
            brutRank++;
        }

        // Trier par temps corrigé et assigner les positions corrigées
        rankedResults.sort(Comparator.comparing(r -> r.tempsCorrige));
        int correctedRank = 1;
        for (RaceResultWithRank result : rankedResults) {
            result.positionCorrigee = correctedRank++;
        }

        return rankedResults;
    }

    /**
     * Formate un temps en secondes vers le format HH:MM:SS
     * 
     * @param seconds Temps en secondes (BigDecimal)
     * @return Chaîne formatée
     */
    public static String formatTime(BigDecimal seconds) {
        if (seconds == null) return "N/A";
        
        long totalSeconds = seconds.longValue();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long secs = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
