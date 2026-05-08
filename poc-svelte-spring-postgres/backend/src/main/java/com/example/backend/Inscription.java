package com.example.backend;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscription", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"bateau_id", "classe_course_id"})
})
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bateau_id", nullable = false)
    private Bateau bateau;

    @ManyToOne
    @JoinColumn(name = "classe_course_id", nullable = false)
    private ClasseCourse classeCourse;

    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;

    @Column(name = "classement")
    private Integer classement;

    @Column(name = "points")
    private Double points;

    // Constructeurs
    public Inscription() {}

    public Inscription(Bateau bateau, ClasseCourse classeCourse) {
        this.bateau = bateau;
        this.classeCourse = classeCourse;
        this.dateInscription = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Bateau getBateau() { return bateau; }
    public void setBateau(Bateau bateau) { this.bateau = bateau; }

    public ClasseCourse getClasseCourse() { return classeCourse; }
    public void setClasseCourse(ClasseCourse classeCourse) { this.classeCourse = classeCourse; }

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }

    public Integer getClassement() { return classement; }
    public void setClassement(Integer classement) { this.classement = classement; }

    public Double getPoints() { return points; }
    public void setPoints(Double points) { this.points = points; }
}
