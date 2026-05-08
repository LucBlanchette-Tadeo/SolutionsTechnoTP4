package com.example.backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClasseBateauService {
    private final ClasseBateauRepository repository;

    public ClasseBateauService(ClasseBateauRepository repository) {
        this.repository = repository;
    }

    public List<ClasseBateau> getAll() {
        return repository.findAll();
    }

    public ClasseBateau save(ClasseBateau classeBateau) {
        return repository.save(classeBateau);
    }

    public ClasseBateau getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
