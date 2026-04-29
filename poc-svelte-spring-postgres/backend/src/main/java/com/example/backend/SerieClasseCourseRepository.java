package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SerieClasseCourseRepository extends JpaRepository<SerieClasseCourse, Long> {
    List<SerieClasseCourse> findBySerie(Serie serie);
    List<SerieClasseCourse> findByClasseCourse(ClasseCourse classeCourse);
    Optional<SerieClasseCourse> findBySerieAndClasseCourse(Serie serie, ClasseCourse classeCourse);
}
