package com.example.backend;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "race_result", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"classe_course_id", "bateau_id"})
})
public class RaceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classe_course_id", nullable = false)
    private ClasseCourse classeCourse;

    @ManyToOne
    @JoinColumn(name = "bateau_id", nullable = false)
    private Bateau bateau;

    @Column(name = "temps_brut_secondes", nullable = false)
    private BigDecimal tempsBrutSecondes;

    @Column(name = "temps_corrige_secondes")
    private BigDecimal tempsCorrectionSecondes;

    @Column(name = "type_handicap", length = 10)
    private String typeHandicap;

    @Column(name = "position_brute")
    private Integer positionBrute;

    @Column(name = "position_corrigee")
    private Integer positionCorrigee;

    @Column(name = "statut", length = 50)
    private String statut;

    @Column(name = "date_course")
    private LocalDateTime dateCourse;

    // Constructeurs
    public RaceResult() {}

    public RaceResult(ClasseCourse classeCourse, Bateau bateau, BigDecimal tempsBrutSecondes) {
        this.classeCourse = classeCourse;
        this.bateau = bateau;
        this.tempsBrutSecondes = tempsBrutSecondes;
        this.statut = "OK";
        this.dateCourse = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ClasseCourse getClasseCourse() { return classeCourse; }
    public void setClasseCourse(ClasseCourse classeCourse) { this.classeCourse = classeCourse; }

    public Bateau getBateau() { return bateau; }
    public void setBateau(Bateau bateau) { this.bateau = bateau; }

    public BigDecimal getTempsBrutSecondes() { return tempsBrutSecondes; }
    public void setTempsBrutSecondes(BigDecimal tempsBrutSecondes) { this.tempsBrutSecondes = tempsBrutSecondes; }

    public BigDecimal getTempsCorrectionSecondes() { return tempsCorrectionSecondes; }
    public void setTempsCorrectionSecondes(BigDecimal tempsCorrectionSecondes) { this.tempsCorrectionSecondes = tempsCorrectionSecondes; }

    public String getTypeHandicap() { return typeHandicap; }
    public void setTypeHandicap(String typeHandicap) { this.typeHandicap = typeHandicap; }

    public Integer getPositionBrute() { return positionBrute; }
    public void setPositionBrute(Integer positionBrute) { this.positionBrute = positionBrute; }

    public Integer getPositionCorrigee() { return positionCorrigee; }
    public void setPositionCorrigee(Integer positionCorrigee) { this.positionCorrigee = positionCorrigee; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateCourse() { return dateCourse; }
    public void setDateCourse(LocalDateTime dateCourse) { this.dateCourse = dateCourse; }
}
