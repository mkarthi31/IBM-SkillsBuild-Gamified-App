package org.example.group34.repo;

import org.example.group34.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Search for courses by title or description
    List<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    // Filter by category
    List<Course> findByCategoryIgnoreCase(String category);

    Optional<Course> findById(Long id);
}
