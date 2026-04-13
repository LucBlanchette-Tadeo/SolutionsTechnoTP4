package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Utilisateur> findByUsername(String username) {
        return utilisateurRepository.findByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(username);
        if (user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPasswordHash());
        }
        return false;
    }

    public Utilisateur createUser(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        Utilisateur user = new Utilisateur(username, hashedPassword);
        return utilisateurRepository.save(user);
    }
}
