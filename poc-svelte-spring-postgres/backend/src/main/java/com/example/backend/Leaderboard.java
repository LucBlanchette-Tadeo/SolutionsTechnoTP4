package com.example.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard", uniqueConstraints = @UniqueConstraint(columnNames = {"classe_course_id", "bateau_id"}))
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classe_course_id", nullable = false)
    private ClasseCourse classeCourse;

    @ManyToOne
    @JoinColumn(name = "bateau_id", nullable = false)
    private Bateau bateau;

    @Column(name = "position")
    private Integer position;

    @Column(name = "points")
    private Integer points = 0;

    @Column(name = "statut", length = 50)
    private String statut;

    // Constructeurs
    public Leaderboard() {}

    public Leaderboard(ClasseCourse classeCourse, Bateau bateau, Integer position, Integer points, String statut) {
        this.classeCourse = classeCourse;
        this.bateau = bateau;
        this.position = position;
        this.points = points;
        this.statut = statut;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ClasseCourse getClasseCourse() { return classeCourse; }
    public void setClasseCourse(ClasseCourse classeCourse) { this.classeCourse = classeCourse; }

    public Bateau getBateau() { return bateau; }
    public void setBateau(Bateau bateau) { this.bateau = bateau; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
