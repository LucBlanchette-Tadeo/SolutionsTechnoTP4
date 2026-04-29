package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    List<Leaderboard> findByClasseCourse(ClasseCourse classeCourse);
    List<Leaderboard> findByBateau(Bateau bateau);
    Optional<Leaderboard> findByClasseCourseAndBateau(ClasseCourse classeCourse, Bateau bateau);
}
