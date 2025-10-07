package org.example.group34.controller;

import org.example.group34.model.User;
import org.example.group34.repo.CourseRepository;
import org.example.group34.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            model.addAttribute("username", userDetails.getUsername());
            User user = userRepository.findByUsername(userDetails.getUsername());
            boolean lockerUnlocked = user.getCourseCompletionCount() >= 1;
            model.addAttribute("lockerUnlocked", lockerUnlocked);
        } else {
            model.addAttribute("username", "Guest");
            model.addAttribute("lockerUnlocked", false);
        }
        return "welcome";
    }
}