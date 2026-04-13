package com.example.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bateaux")
@CrossOrigin(origins = "http://localhost:5173")
public class BateauController {
    private final BateauService service;
    private final ClasseBateauRepository classeRepo;

    public BateauController(BateauService service, ClasseBateauRepository classeRepo) {
        this.service = service;
        this.classeRepo = classeRepo;
    }

    @GetMapping
    public List<Bateau> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Bateau create(@RequestBody Bateau b) {
        if (b.getClasseBateau() == null || b.getClasseBateau().getId() == null) {
            throw new RuntimeException("Classe de bateau obligatoire");
        }
        if (b.getNumeroVoile() == null) {
            throw new RuntimeException("Numéro de voile obligatoire");
        }
        Long cid = b.getClasseBateau().getId();
        ClasseBateau cb = classeRepo.findById(cid).orElseThrow(() -> new RuntimeException("Classe bateau introuvable: " + cid));
        b.setClasseBateau(cb);
        return service.save(b);
    }

    @PutMapping("/{id}")
    public Bateau update(@PathVariable Long id, @RequestBody Bateau b) {
        Bateau existing = service.getById(id);
        if (existing == null) {
            throw new RuntimeException("Bateau introuvable: " + id);
        }
        if (b.getNomBateau() != null) existing.setNomBateau(b.getNomBateau());
        if (b.getNumeroVoile() != null) existing.setNumeroVoile(b.getNumeroVoile());
        if (b.getNomBarreur() != null) existing.setNomBarreur(b.getNomBarreur());
        if (b.getClasseBateau() != null && b.getClasseBateau().getId() != null) {
            ClasseBateau cb = classeRepo.findById(b.getClasseBateau().getId())
                .orElseThrow(() -> new RuntimeException("Classe bateau introuvable: " + b.getClasseBateau().getId()));
            existing.setClasseBateau(cb);
        }
        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (service.getById(id) == null) {
            throw new RuntimeException("Bateau introuvable: " + id);
        }
        service.delete(id);
    }
}
