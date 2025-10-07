package org.example.group34.controller;

import org.example.group34.model.User;
import org.example.group34.model.UserCourses;
import org.example.group34.service.CourseService;
import org.example.group34.service.UserCourseService;
import org.example.group34.service.UserService;
import org.example.group34.service.AchievementService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserCourseController {

    private final UserService userService;
    private final UserCourseService userCourseService;
    private final CourseService courseService;
    private final AchievementService achievementService;

    public UserCourseController(UserService userService, UserCourseService userCourseService,
                                CourseService courseService, AchievementService achievementService) {
        this.userService = userService;
        this.userCourseService = userCourseService;
        this.courseService = courseService;
        this.achievementService = achievementService;
    }

    @RequestMapping("/myCourses")
    public String myCourses(Model model) {
        // Get the current user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByUsername(username);

        if (user != null) {
            List<UserCourses> userCourses = userCourseService.findUserCoursesByUser(user);
            model.addAttribute("userCourses", userCourses);
        }

        return "myCourses";
    }

    @PostMapping("/userCourses/updateProgress")
    public String updateProgress(@RequestParam("userCourseId") Long userCourseId,
                                 @RequestParam("progress") int progress,
                                 RedirectAttributes redirectAttributes) {
        // Find the UserCourse by ID
        UserCourses userCourse = userCourseService.findById(userCourseId);
        if (userCourse != null) {
            boolean wasCompleted = userCourse.isCompleted();
            userCourse.setProgress(progress);
            userCourse.setCompleted(progress >= 100);
            userCourseService.save(userCourse);

            User user = userCourse.getUser();
            // Update the user's course completion count
            if (!wasCompleted && userCourse.isCompleted()) {
                user.setCourseCompletionCount(user.getCourseCompletionCount() + 1);
            } else if (wasCompleted && !userCourse.isCompleted()) {
                user.setCourseCompletionCount(user.getCourseCompletionCount() - 1);
            }
            userService.updateUsername(user, user.getUsername());

            // Update the user's streak
            userService.updateStreak(user);

            // Check if a badge was unlocked
            boolean badgeUnlocked = achievementService.updateAchievements(user);
            if (badgeUnlocked) {
                redirectAttributes.addFlashAttribute("badgeUnlocked", "A new badge was unlocked!");
            }
        }

        return "redirect:/myCourses";
    }
}
