package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "http://localhost:5173")
public class SerieController {
    private final SerieService service;
    private final ClasseCourseRepository classeCourseRepository;

    public SerieController(SerieService service, ClasseCourseRepository classeCourseRepository) { 
        this.service = service; 
        this.classeCourseRepository = classeCourseRepository; 
    }

    @GetMapping
    public List<Serie> getAll() { return service.getAll(); }

    @PostMapping
    public Serie create(@RequestBody Serie s) {
        if (s.getNomSerie() == null || s.getNomSerie().isBlank()) {
            throw new RuntimeException("Nom de la série obligatoire");
        }
        if (s.getClasseCourse() == null || s.getClasseCourse().getId() == null) {
            throw new RuntimeException("Classe de course obligatoire");
        }
        Long cid = s.getClasseCourse().getId();
        ClasseCourse cc = classeCourseRepository.findById(cid).orElseThrow(() -> new RuntimeException("Classe course introuvable: " + cid));
        s.setClasseCourse(cc);
        return service.save(s);
    }

    @PutMapping("/{id}")
    public Serie update(@PathVariable Long id, @RequestBody Serie s) {
        Serie existing = service.getById(id);
        if (existing == null) {
            throw new RuntimeException("Série introuvable: " + id);
        }
        if (s.getNomSerie() != null && !s.getNomSerie().isBlank()) {
            existing.setNomSerie(s.getNomSerie());
        }
        if (s.getClasseCourse() != null && s.getClasseCourse().getId() != null) {
            ClasseCourse cc = classeCourseRepository.findById(s.getClasseCourse().getId())
                .orElseThrow(() -> new RuntimeException("Classe course introuvable: " + s.getClasseCourse().getId()));
            existing.setClasseCourse(cc);
        }
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (service.getById(id) == null) {
            throw new RuntimeException("Série introuvable: " + id);
        }
        service.delete(id);
    }
}
