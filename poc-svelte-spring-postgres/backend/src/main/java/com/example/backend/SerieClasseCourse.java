package com.example.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "serie_classe_course", uniqueConstraints = @UniqueConstraint(columnNames = {"serie_id", "classe_course_id"}))
public class SerieClasseCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "classe_course_id", nullable = false)
    private ClasseCourse classeCourse;

    @Column(name = "ordre_course")
    private Integer ordreCourse;

    // Constructeurs
    public SerieClasseCourse() {}

    public SerieClasseCourse(Serie serie, ClasseCourse classeCourse, Integer ordreCourse) {
        this.serie = serie;
        this.classeCourse = classeCourse;
        this.ordreCourse = ordreCourse;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Serie getSerie() { return serie; }
    public void setSerie(Serie serie) { this.serie = serie; }

    public ClasseCourse getClasseCourse() { return classeCourse; }
    public void setClasseCourse(ClasseCourse classeCourse) { this.classeCourse = classeCourse; }

    public Integer getOrdreCourse() { return ordreCourse; }
    public void setOrdreCourse(Integer ordreCourse) { this.ordreCourse = ordreCourse; }
}
