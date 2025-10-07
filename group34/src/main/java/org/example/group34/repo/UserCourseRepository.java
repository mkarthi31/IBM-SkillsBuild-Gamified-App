package org.example.group34.repo;

import org.example.group34.model.Course;
import org.example.group34.model.User;
import org.example.group34.model.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourses, Long> {
    List<UserCourses> findByUser(User user);
}
