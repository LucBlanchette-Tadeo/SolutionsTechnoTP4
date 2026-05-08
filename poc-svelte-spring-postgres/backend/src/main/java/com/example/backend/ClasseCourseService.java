package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClasseCourseService {
    private final ClasseCourseRepository repository;

    public ClasseCourseService(ClasseCourseRepository repository) {
        this.repository = repository;
    }

    public List<ClasseCourse> getAll() {
        return repository.findAll();
    }

    public ClasseCourse save(ClasseCourse classeCourse) {
        return repository.save(classeCourse);
    }

    public ClasseCourse getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
