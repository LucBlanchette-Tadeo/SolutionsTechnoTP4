package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    @Autowired
    private BateauRepository bateauRepository;
    
    @Autowired
    private ClasseCourseRepository classeCourseRepository;

    // Récupérer toutes les inscriptions d'une course, triées par classement
    public List<Inscription> getInscriptionsByCourse(Long classeCourseId) {
        List<Inscription> inscriptions = inscriptionRepository.findByClasseCourseId(classeCourseId);
        // Trier par classement (NULL à la fin, ordre croissant)
        inscriptions.sort((a, b) -> {
            if (a.getClassement() == null && b.getClassement() == null) return 0;
            if (a.getClassement() == null) return 1;
            if (b.getClassement() == null) return -1;
            return a.getClassement().compareTo(b.getClassement());
        });
        return inscriptions;
    }

    // Récupérer les bateaux disponibles pour une course
    public List<Bateau> getAvailableBateauxForCourse(Long classeCourseId) {
        return inscriptionRepository.findAvailableBateauxForCourse(classeCourseId);
    }

    // Ajouter un bateau à une course
    public Inscription addBateauToCourse(Long bateauId, Long classeCourseId) {
        // Vérifier que le bateau et la course existent
        Bateau bateau = bateauRepository.findById(bateauId)
            .orElseThrow(() -> new RuntimeException("Bateau non trouvé"));
        ClasseCourse classeCourse = classeCourseRepository.findById(classeCourseId)
            .orElseThrow(() -> new RuntimeException("Course non trouvée"));

        // Vérifier que le bateau n'est pas déjà inscrit à cette course
        Optional<Inscription> existante = inscriptionRepository.findByBateauIdAndClasseCourseId(bateauId, classeCourseId);
        if (existante.isPresent()) {
            throw new RuntimeException("Ce bateau est déjà inscrit à cette course");
        }

        // Vérifier que la classe du bateau correspond au type de la course
        if (!bateau.getClasseBateau().getNomClasse().equals(classeCourse.getTypeClasse())) {
            throw new RuntimeException("La classe du bateau ne correspond pas au type de la course");
        }

        // Créer et sauvegarder l'inscription
        Inscription inscription = new Inscription(bateau, classeCourse);
        return inscriptionRepository.save(inscription);
    }

    // Retirer un bateau d'une course
    public void removeBateauFromCourse(Long inscriptionId) {
        inscriptionRepository.deleteById(inscriptionId);
    }

    // Mettre à jour le classement d'une inscription
    public Inscription updateClassement(Long inscriptionId, Integer classement) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
            .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        inscription.setClassement(classement);
        return inscriptionRepository.save(inscription);
    }
}
