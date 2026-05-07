package com.example.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "serie")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_serie", length = 50, nullable = false, unique = true)
    private String nomSerie;

    @ManyToOne
    @JoinColumn(name = "classe_bateau_id", nullable = true)
    private ClasseBateau classeBateau;

    @Column(name = "nombre_courses", nullable = false)
    private Integer nombreCourses = 0;

    @Column(name = "nombre_courses_a_compter", nullable = false)
    private Integer nombreCoursesACompter = 0;

    // Constructeurs
    public Serie() {}

    public Serie(String nomSerie) {
        this.nomSerie = nomSerie;
        this.classeBateau = null;
        this.nombreCourses = 0;
        this.nombreCoursesACompter = 0;
    }

    public Serie(String nomSerie, Integer nombreCourses, Integer nombreCoursesACompter) {
        this.nomSerie = nomSerie;
        this.classeBateau = null;
        this.nombreCourses = nombreCourses != null ? nombreCourses : 0;
        this.nombreCoursesACompter = nombreCoursesACompter != null ? nombreCoursesACompter : 0;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomSerie() { return nomSerie; }
    public void setNomSerie(String nomSerie) { this.nomSerie = nomSerie; }

    public ClasseBateau getClasseBateau() { return classeBateau; }
    public void setClasseBateau(ClasseBateau classeBateau) { this.classeBateau = classeBateau; }

    public Integer getNombreCourses() { return nombreCourses; }
    public void setNombreCourses(Integer nombreCourses) { this.nombreCourses = nombreCourses; }

    public Integer getNombreCoursesACompter() { return nombreCoursesACompter; }
    public void setNombreCoursesACompter(Integer nombreCoursesACompter) { this.nombreCoursesACompter = nombreCoursesACompter; }
}
