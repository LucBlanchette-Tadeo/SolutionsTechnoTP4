package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/serie-classe-course")
@CrossOrigin(origins = "http://localhost:5173")
public class SerieClasseCourseController {
    private final SerieClasseCourseService service;
    private final SerieRepository serieRepository;
    private final ClasseCourseRepository classeCourseRepository;

    public SerieClasseCourseController(SerieClasseCourseService service, SerieRepository serieRepository, ClasseCourseRepository classeCourseRepository) {
        this.service = service;
        this.serieRepository = serieRepository;
        this.classeCourseRepository = classeCourseRepository;
    }

    @GetMapping
    public List<SerieClasseCourse> getAll() {
        return service.getAll();
    }

    @GetMapping("/serie/{serieId}")
    public List<SerieClasseCourse> getBySerie(@PathVariable Long serieId) {
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Série introuvable: " + serieId));
        return service.getBySerie(serie);
    }

    @PostMapping
    public SerieClasseCourse create(@RequestBody SerieClasseCourse scc) {
        if (scc.getSerie() == null || scc.getSerie().getId() == null) {
            throw new RuntimeException("Série obligatoire");
        }
        if (scc.getClasseCourse() == null || scc.getClasseCourse().getId() == null) {
            throw new RuntimeException("Classe de course obligatoire");
        }

        Serie serie = serieRepository.findById(scc.getSerie().getId())
            .orElseThrow(() -> new RuntimeException("Série introuvable: " + scc.getSerie().getId()));
        ClasseCourse cc = classeCourseRepository.findById(scc.getClasseCourse().getId())
            .orElseThrow(() -> new RuntimeException("Classe course introuvable: " + scc.getClasseCourse().getId()));

        scc.setSerie(serie);
        scc.setClasseCourse(cc);
        return service.save(scc);
    }

    @PutMapping("/{id}")
    public SerieClasseCourse update(@PathVariable Long id, @RequestBody SerieClasseCourse scc) {
        SerieClasseCourse existing = service.getById(id);
        if (existing == null) {
            throw new RuntimeException("Liaison introuvable: " + id);
        }
        if (scc.getOrdreCourse() != null) {
            existing.setOrdreCourse(scc.getOrdreCourse());
        }
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (service.getById(id) == null) {
            throw new RuntimeException("Liaison introuvable: " + id);
        }
        service.delete(id);
    }
}
