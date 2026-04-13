package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public void run(String... args) throws Exception {
        // Créer l'utilisateur admin par défaut s'il n'existe pas
        if (utilisateurService.findByUsername("admin").isEmpty()) {
            utilisateurService.createUser("admin", "Admin123!");
            System.out.println("✅ Utilisateur admin créé avec succès (password: Admin123!)");
        } else {
            System.out.println("✅ Utilisateur admin déjà existant");
        }
    }
}
