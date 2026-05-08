package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {
    List<RaceResult> findByClasseCourseId(Long classeCourseId);
    
    Optional<RaceResult> findByClasseCourseIdAndBateauId(Long classeCourseId, Long bateauId);
    
    List<RaceResult> findByBateauIdOrderByDateCourseDesc(Long bateauId);
}
