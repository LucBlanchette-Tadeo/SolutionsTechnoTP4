package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BateauService {
    private final BateauRepository repository;

    public BateauService(BateauRepository repository) {
        this.repository = repository;
    }

    public List<Bateau> getAll() {
        return repository.findAll();
    }

    public Bateau save(Bateau bateau) {
        return repository.save(bateau);
    }

    public Bateau getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
