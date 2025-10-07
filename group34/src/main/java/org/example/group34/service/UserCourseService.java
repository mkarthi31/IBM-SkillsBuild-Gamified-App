package org.example.group34.service;

import org.example.group34.model.Course;
import org.example.group34.model.User;
import org.example.group34.model.UserCourses;
import org.example.group34.repo.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCourseService {
    private final UserCourseRepository userCourseRepository;


    @Autowired
    public UserCourseService(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    public UserCourses save(UserCourses userCourse) {
        return userCourseRepository.save(userCourse);
    }

    public List<Course> findCoursesByUser(User user) {
        List<UserCourses> userCoursesList = userCourseRepository.findByUser(user);  // Fetch UserCourses by user
        List<Course> courses = new ArrayList<>();
        for (UserCourses userCourse : userCoursesList) {
            courses.add(userCourse.getCourse());  // Extract the course from each UserCourses
        }
        return courses;  // Return list of courses
    }

    // Returns the full list of UserCourses objects for the given user.
    public List<UserCourses> findUserCoursesByUser(User user) {
        return userCourseRepository.findByUser(user);
    }

    // Returns a specific UserCourses object by its ID.
    public UserCourses findById(Long id) {
        return userCourseRepository.findById(id).orElse(null);
    }

    //check if user is enrolled in a course
    public boolean isCourseEnrolled(User user, Course course) {
        List<UserCourses> userCourses = userCourseRepository.findByUser(user);
        for (UserCourses userCourse : userCourses) {
            if (userCourse.getCourse().equals(course)) {
                return true; // User is already enrolled in the course
            }
        }
        return false; // User is not enrolled
    }


}
