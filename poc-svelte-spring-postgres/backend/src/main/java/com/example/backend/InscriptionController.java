package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscriptions")
@CrossOrigin(origins = "*")
public class InscriptionController {
    @Autowired
    private InscriptionService inscriptionService;

    // Récupérer les inscriptions d'une course
    @GetMapping("/course/{classeCourseId}")
    public ResponseEntity<List<Inscription>> getInscriptionsByCourse(@PathVariable Long classeCourseId) {
        List<Inscription> inscriptions = inscriptionService.getInscriptionsByCourse(classeCourseId);
        return ResponseEntity.ok(inscriptions);
    }

    // Récupérer les bateaux disponibles pour une course
    @GetMapping("/disponibles/{classeCourseId}")
    public ResponseEntity<List<Bateau>> getAvailableBateaux(@PathVariable Long classeCourseId) {
        List<Bateau> bateaux = inscriptionService.getAvailableBateauxForCourse(classeCourseId);
        return ResponseEntity.ok(bateaux);
    }

    // Ajouter un bateau à une course
    @PostMapping
    public ResponseEntity<?> addBateau(@RequestBody Map<String, Long> payload) {
        try {
            Long bateauId = payload.get("bateauId");
            Long classeCourseId = payload.get("classeCourseId");
            Inscription inscription = inscriptionService.addBateauToCourse(bateauId, classeCourseId);
            return ResponseEntity.ok(inscription);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Retirer un bateau d'une course
    @DeleteMapping("/{inscriptionId}")
    public ResponseEntity<?> removeBateau(@PathVariable Long inscriptionId) {
        try {
            inscriptionService.removeBateauFromCourse(inscriptionId);
            return ResponseEntity.ok(Map.of("message", "Bateau retiré avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Mettre à jour le classement d'une inscription
    @PatchMapping("/{inscriptionId}/classement")
    public ResponseEntity<?> updateClassement(@PathVariable Long inscriptionId, @RequestBody Map<String, Integer> payload) {
        try {
            Integer classement = payload.get("classement");
            Inscription inscription = inscriptionService.updateClassement(inscriptionId, classement);
            return ResponseEntity.ok(inscription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
