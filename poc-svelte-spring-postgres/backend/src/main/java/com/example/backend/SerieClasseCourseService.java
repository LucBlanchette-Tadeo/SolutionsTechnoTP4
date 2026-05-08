package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SerieClasseCourseService {
    private final SerieClasseCourseRepository repository;

    public SerieClasseCourseService(SerieClasseCourseRepository repository) {
        this.repository = repository;
    }

    public List<SerieClasseCourse> getAll() {
        return repository.findAll();
    }

    public SerieClasseCourse getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public SerieClasseCourse save(SerieClasseCourse scc) {
        return repository.save(scc);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<SerieClasseCourse> getBySerie(Serie serie) {
        return repository.findBySerie(serie);
    }

    public List<SerieClasseCourse> getByClasseCourse(ClasseCourse classeCourse) {
        return repository.findByClasseCourse(classeCourse);
    }
}
