package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classe-course")
@CrossOrigin(origins = "http://localhost:5173")
public class ClasseCourseController {
    private final ClasseCourseService service;

    public ClasseCourseController(ClasseCourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClasseCourse> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ClasseCourse create(@RequestBody ClasseCourse c) {
        if (c.getNomClasseCourse() == null || c.getNomClasseCourse().isBlank()) {
            throw new RuntimeException("Nom de la classe obligatoire");
        }
        if (c.getTypeClasse() == null || c.getTypeClasse().isBlank()) {
            throw new RuntimeException("Type de classe obligatoire");
        }
        return service.save(c);
    }

    @PutMapping("/{id}")
    public ClasseCourse update(@PathVariable Long id, @RequestBody ClasseCourse c) {
        ClasseCourse existing = service.getById(id);
        if (existing == null) {
            throw new RuntimeException("Classe de course introuvable: " + id);
        }
        if (c.getNomClasseCourse() != null && !c.getNomClasseCourse().isBlank()) {
            existing.setNomClasseCourse(c.getNomClasseCourse());
        }
        if (c.getTypeClasse() != null && !c.getTypeClasse().isBlank()) {
            existing.setTypeClasse(c.getTypeClasse());
        }
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (service.getById(id) == null) {
            throw new RuntimeException("Classe de course introuvable: " + id);
        }
        service.delete(id);
    }
}
