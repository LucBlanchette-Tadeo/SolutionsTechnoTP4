package com.example.backend;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "classe_bateau")
public class ClasseBateau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_classe", length = 25)
    private String nomClasse;

    @Column(name = "py")
    private BigDecimal py;

    @Column(name = "tmf")
    private BigDecimal tmf;

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

    public BigDecimal getPy() { return py; }
    public void setPy(BigDecimal py) { this.py = py; }

    public BigDecimal getTmf() { return tmf; }
    public void setTmf(BigDecimal tmf) { this.tmf = tmf; }
}
