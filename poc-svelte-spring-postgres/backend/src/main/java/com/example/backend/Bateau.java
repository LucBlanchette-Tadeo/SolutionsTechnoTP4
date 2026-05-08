package com.example.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "bateau")
public class Bateau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_bateau", length = 25)
    private String nomBateau;

    @Column(name = "numero_voile", nullable = false)
    private Integer numeroVoile;

    @Column(name = "nom_barreur", length = 25)
    private String nomBarreur;

    @ManyToOne
    @JoinColumn(name = "classe_bateau_id", nullable = false)
    private ClasseBateau classeBateau;

    // Constructeurs
    public Bateau() {}

    public Bateau(String nomBateau, Integer numeroVoile, String nomBarreur, ClasseBateau classeBateau) {
        this.nomBateau = nomBateau;
        this.numeroVoile = numeroVoile;
        this.nomBarreur = nomBarreur;
        this.classeBateau = classeBateau;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomBateau() { return nomBateau; }
    public void setNomBateau(String nomBateau) { this.nomBateau = nomBateau; }

    public Integer getNumeroVoile() { return numeroVoile; }
    public void setNumeroVoile(Integer numeroVoile) { this.numeroVoile = numeroVoile; }

    public String getNomBarreur() { return nomBarreur; }
    public void setNomBarreur(String nomBarreur) { this.nomBarreur = nomBarreur; }

    public ClasseBateau getClasseBateau() { return classeBateau; }
    public void setClasseBateau(ClasseBateau classeBateau) { this.classeBateau = classeBateau; }
}
