package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaderboardController {
    private final LeaderboardService service;
    private final ClasseCourseRepository classeCourseRepository;
    private final BateauRepository bateauRepository;

    public LeaderboardController(LeaderboardService service, ClasseCourseRepository classeCourseRepository, BateauRepository bateauRepository) {
        this.service = service;
        this.classeCourseRepository = classeCourseRepository;
        this.bateauRepository = bateauRepository;
    }

    @GetMapping
    public List<Leaderboard> getAll() {
        return service.getAll();
    }

    @GetMapping("/course/{classeCourseId}")
    public List<Leaderboard> getByClasseCourse(@PathVariable Long classeCourseId) {
        ClasseCourse cc = classeCourseRepository.findById(classeCourseId)
            .orElseThrow(() -> new RuntimeException("Classe course introuvable: " + classeCourseId));
        return service.getByClasseCourse(cc);
    }

    @PostMapping
    public Leaderboard create(@RequestBody Leaderboard leaderboard) {
        if (leaderboard.getClasseCourse() == null || leaderboard.getClasseCourse().getId() == null) {
            throw new RuntimeException("Classe de course obligatoire");
        }
        if (leaderboard.getBateau() == null || leaderboard.getBateau().getId() == null) {
            throw new RuntimeException("Bateau obligatoire");
        }

        ClasseCourse cc = classeCourseRepository.findById(leaderboard.getClasseCourse().getId())
            .orElseThrow(() -> new RuntimeException("Classe course introuvable: " + leaderboard.getClasseCourse().getId()));
        Bateau b = bateauRepository.findById(leaderboard.getBateau().getId())
            .orElseThrow(() -> new RuntimeException("Bateau introuvable: " + leaderboard.getBateau().getId()));

        leaderboard.setClasseCourse(cc);
        leaderboard.setBateau(b);
        return service.save(leaderboard);
    }

    @PutMapping("/{id}")
    public Leaderboard update(@PathVariable Long id, @RequestBody Leaderboard leaderboard) {
        Leaderboard existing = service.getById(id);
        if (existing == null) {
            throw new RuntimeException("Leaderboard introuvable: " + id);
        }
        if (leaderboard.getPosition() != null) {
            existing.setPosition(leaderboard.getPosition());
        }
        if (leaderboard.getPoints() != null) {
            existing.setPoints(leaderboard.getPoints());
        }
        if (leaderboard.getStatut() != null) {
            existing.setStatut(leaderboard.getStatut());
        }
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (service.getById(id) == null) {
            throw new RuntimeException("Leaderboard introuvable: " + id);
        }
        service.delete(id);
    }
}
