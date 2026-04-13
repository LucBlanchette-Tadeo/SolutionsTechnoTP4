package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classe-bateau")
@CrossOrigin(origins = "http://localhost:5173")
public class ClasseBateauController {
    private final ClasseBateauService service;

    public ClasseBateauController(ClasseBateauService service) { this.service = service; }

    @GetMapping
    public List<ClasseBateau> getAll() { return service.getAll(); }

    @PostMapping
    public ClasseBateau create(@RequestBody ClasseBateau c) {
        if (c.getNomClasse() == null || c.getNomClasse().isBlank()) {
            throw new RuntimeException("Nom de la classe obligatoire");
        }
        return service.save(c);
    }

    @PutMapping("/{id}")
    public ClasseBateau update(@PathVariable Long id, @RequestBody ClasseBateau c) {
        ClasseBateau existing = service.getById(id);
        if (existing == null) {
            throw new RuntimeException("Classe bateau introuvable: " + id);
        }
        if (c.getNomClasse() != null && !c.getNomClasse().isBlank()) {
            existing.setNomClasse(c.getNomClasse());
        }
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (service.getById(id) == null) {
            throw new RuntimeException("Classe bateau introuvable: " + id);
        }
        service.delete(id);
    }
}
