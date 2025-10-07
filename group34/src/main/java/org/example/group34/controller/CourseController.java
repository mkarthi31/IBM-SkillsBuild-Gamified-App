package org.example.group34.controller;

import org.example.group34.model.Course;
import org.example.group34.model.User;
import org.example.group34.model.UserCourses;
import org.example.group34.service.CourseService;
import org.example.group34.service.UserCourseService;
import org.example.group34.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;
    private final UserCourseService userCourseService; // Add the userCourseService for saving the relation

    @Autowired
    public CourseController(CourseService courseService, UserService userService, UserCourseService userCourseService) {
        this.courseService = courseService;
        this.userService = userService;
        this.userCourseService = userCourseService;  // Inject userCourseService
    }

    @GetMapping
    public String searchCourses(@RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String category,
                                Model model) {

        //Get logged-in user details
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        // Get the list of courses the user is already enrolled in
        List<Course> courses = courseService.searchCourses(keyword, category);
        List<UserCourses> userCourses = userCourseService.findUserCoursesByUser(user);
        List<Long> enrolledCourseIds = new ArrayList<>();

        // Get the list of IDs of courses the user is enrolled in
        for (UserCourses userCourse : userCourses) {
            enrolledCourseIds.add(userCourse.getCourse().getId());
        }



        model.addAttribute("courses", courses);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);
        return "courseSearch";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam("courseId") Long courseId, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByUsername(username);
        if (user != null) {
            Course course = courseService.findCourseById(courseId);
            if (course != null) {
                // Check if the user is already enrolled in this course
                boolean alreadyEnrolled = userCourseService.isCourseEnrolled(user, course);
                if (!alreadyEnrolled) {
                    // Enroll the user in the course if not already enrolled
                    UserCourses userCourse = new UserCourses(user, course, false); // Default to not completed
                    userCourseService.save(userCourse);
                    model.addAttribute("enrolled", true);
                } else {
                    model.addAttribute("enrolled", false); // Notify the user that they are already enrolled
                }
            }
        }
        return "redirect:/courses"; // Redirect back to the course search page
    }



}
