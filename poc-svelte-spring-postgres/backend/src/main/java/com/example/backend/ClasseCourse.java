package com.example.backend;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "classe_course")
public class ClasseCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_classe_course", length = 25, nullable = false)
    private String nomClasseCourse;

    @Column(name = "type_classe", length = 10, nullable = false)
    private String typeClasse;

    @Column(name = "type_handicap", length = 10)
    private String typeHandicap;

    @Column(name = "handicap_min")
    private BigDecimal handicapMin;

    @Column(name = "handicap_max")
    private BigDecimal handicapMax;

    // Constructeurs
    public ClasseCourse() {}

    public ClasseCourse(String nomClasseCourse, String typeClasse) {
        this.nomClasseCourse = nomClasseCourse;
        this.typeClasse = typeClasse;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomClasseCourse() { return nomClasseCourse; }
    public void setNomClasseCourse(String nomClasseCourse) { this.nomClasseCourse = nomClasseCourse; }

    public String getTypeClasse() { return typeClasse; }
    public void setTypeClasse(String typeClasse) { this.typeClasse = typeClasse; }

    public String getTypeHandicap() { return typeHandicap; }
    public void setTypeHandicap(String typeHandicap) { this.typeHandicap = typeHandicap; }

    public BigDecimal getHandicapMin() { return handicapMin; }
    public void setHandicapMin(BigDecimal handicapMin) { this.handicapMin = handicapMin; }

    public BigDecimal getHandicapMax() { return handicapMax; }
    public void setHandicapMax(BigDecimal handicapMax) { this.handicapMax = handicapMax; }
}
