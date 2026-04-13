package com.example.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "classe_bateau")
public class ClasseBateau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_classe", length = 25)
    private String nomClasse;

    // Constructeurs
    public ClasseBateau() {}

    public ClasseBateau(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomClasse() { return nomClasse; }
    public void setNomClasse(String nomClasse) { this.nomClasse = nomClasse; }
}
