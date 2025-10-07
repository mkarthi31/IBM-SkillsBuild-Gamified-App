package org.example.group34.service;

import org.example.group34.model.Course;
import org.example.group34.repo.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> searchCourses(String keyword, String category) {
        boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());
        boolean hasCategory = (category != null && !category.trim().isEmpty());

        if (hasKeyword && hasCategory) {
            return courseRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                    .stream()
                    .filter(course -> course.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        } else if (hasKeyword) {
            return courseRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        } else if (hasCategory) {
            return courseRepository.findByCategoryIgnoreCase(category);
        } else {
            return courseRepository.findAll();
        }
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course findCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        // If course is present, return it, otherwise throw an exception or handle as needed
        return course.orElseThrow(() -> new IllegalArgumentException("Course not found for ID: " + id));
    }
}
