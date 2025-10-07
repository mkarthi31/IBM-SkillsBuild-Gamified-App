// LockerController.java
package org.example.group34.controller;

import org.example.group34.model.BorderOption;
import org.example.group34.model.User;
import org.example.group34.model.UserAchievement;
import org.example.group34.repo.UserAchievementRepository;
import org.example.group34.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LockerController {

    private final UserService userService;
    private final UserAchievementRepository userAchievementRepository;

    public LockerController(UserService userService, UserAchievementRepository userAchievementRepository) {
        this.userService = userService;
        this.userAchievementRepository = userAchievementRepository;
    }

    @GetMapping("/locker")
    public String myLocker(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        // Get the user's achievements
        List<UserAchievement> achievements = userAchievementRepository.findByUser(user);

        // Determine unlocked borders based on achievement badge names.
        boolean unlockedFirstSteps = achievements.stream()
                .anyMatch(ua -> "First Steps".equalsIgnoreCase(ua.getBadge().getName()));
        if (!unlockedFirstSteps) {
            // Redirect or show a message if the user hasn’t completed at least 1 course.
            model.addAttribute("error", "You need to complete 1 course to unlock the locker.");
            return "lockerLocked"; // Create a simple view that informs the user (or redirect to profile)
        }

        boolean unlockedMaster = achievements.stream()
                .anyMatch(ua -> "Master of SkillsBuild".equalsIgnoreCase(ua.getBadge().getName()));
        boolean unlockedFull = achievements.stream()
                .anyMatch(ua -> "Full Completionist".equalsIgnoreCase(ua.getBadge().getName()));

        List<BorderOption> borderOptions = new ArrayList<>();
        // Three basic options (always unlocked if First Steps is achieved)
        borderOptions.add(new BorderOption("blue", true));
        borderOptions.add(new BorderOption("red", true));
        borderOptions.add(new BorderOption("green", true));
        // Silver border locked until "Master of SkillsBuild" achievement
        borderOptions.add(new BorderOption("silver", unlockedMaster));
        // Gold border locked until "Full Completionist" achievement
        borderOptions.add(new BorderOption("gold", unlockedFull));

        model.addAttribute("borderOptions", borderOptions);
        model.addAttribute("selectedBorder", user.getProfileBorder());
        return "locker";
    }

    @PostMapping("/locker/select")
    public String selectBorder(@RequestParam("borderColor") String borderColor, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        // (Optional: Validate that the borderColor is allowed for this user)
        user.setProfileBorder(borderColor);
        userService.saveUser(user);
        return "redirect:/locker";
    }
}
