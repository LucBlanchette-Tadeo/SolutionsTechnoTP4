package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SerieController {
    private final SerieService service;
    private final ClasseBateauRepository classeBateauRepository;

    public SerieController(SerieService service, ClasseBateauRepository classeBateauRepository) { 
        this.service = service; 
        this.classeBateauRepository = classeBateauRepository; 
    }

    @GetMapping
    public List<Serie> getAll() { return service.getAll(); }

    @PostMapping
    public Serie create(@RequestBody Serie s) {
        if (s.getNomSerie() == null || s.getNomSerie().isBlank()) {
            throw new RuntimeException("Nom de la série obligatoire");
        }
        
        // ClasseBateau n'est pas obligatoire
        if (s.getClasseBateau() != null && s.getClasseBateau().getId() != null) {
            Long cbid = s.getClasseBateau().getId();
            ClasseBateau cb = classeBateauRepository.findById(cbid).orElseThrow(() -> new RuntimeException("Classe bateau introuvable: " + cbid));
            s.setClasseBateau(cb);
        }
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
        if (s.getNombreCourses() != null) {
            existing.setNombreCourses(s.getNombreCourses());
        }
        if (s.getNombreCoursesACompter() != null) {
            existing.setNombreCoursesACompter(s.getNombreCoursesACompter());
        }
        if (s.getClasseBateau() != null && s.getClasseBateau().getId() != null) {
            ClasseBateau cb = classeBateauRepository.findById(s.getClasseBateau().getId())
                .orElseThrow(() -> new RuntimeException("Classe bateau introuvable: " + s.getClasseBateau().getId()));
            existing.setClasseBateau(cb);
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


