package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByClasseCourseId(Long classeCourseId);
    
    Optional<Inscription> findByBateauIdAndClasseCourseId(Long bateauId, Long classeCourseId);
    
    @Query("SELECT b FROM Bateau b " +
           "WHERE b.classeBateau.nomClasse = (SELECT cc.typeClasse FROM ClasseCourse cc WHERE cc.id = :classeCourseId) " +
           "AND b.id NOT IN (SELECT i.bateau.id FROM Inscription i WHERE i.classeCourse.id = :classeCourseId)")
    List<Bateau> findAvailableBateauxForCourse(@Param("classeCourseId") Long classeCourseId);
}
