package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaderboardService {
    private final LeaderboardRepository repository;

    public LeaderboardService(LeaderboardRepository repository) {
        this.repository = repository;
    }

    public List<Leaderboard> getAll() {
        return repository.findAll();
    }

    public Leaderboard getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Leaderboard save(Leaderboard leaderboard) {
        return repository.save(leaderboard);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Leaderboard> getByClasseCourse(ClasseCourse classeCourse) {
        return repository.findByClasseCourse(classeCourse);
    }

    public List<Leaderboard> getByBateau(Bateau bateau) {
        return repository.findByBateau(bateau);
    }
}
