package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SerieService {
    private final SerieRepository repository;

    public SerieService(SerieRepository repository) {
        this.repository = repository;
    }

    public List<Serie> getAll() {
        return repository.findAll();
    }

    public Serie save(Serie serie) {
        return repository.save(serie);
    }

    public Serie getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
