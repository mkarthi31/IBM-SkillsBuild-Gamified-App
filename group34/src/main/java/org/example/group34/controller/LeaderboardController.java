package org.example.group34.controller;

import org.example.group34.model.User;
import org.example.group34.repo.UserRepository;
import org.example.group34.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class LeaderboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaderboardService leaderboardService;

    @RequestMapping("/leaderboard")
    public String leaderboard(Model model) {

        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }
        User currentUser = userRepository.findByUsername(username);

        // Get Global Leaderboard
        List<User> globalLeaderboard = leaderboardService.getGlobalLeaderboard();


        // Get Friends Leaderboard (if user is authenticated)
        List<User> friendLeaderboard = (username != null)
                ? new ArrayList<>(leaderboardService.getFriendLeaderboard(username))
                : new ArrayList<>(); // Empty list if not authenticated
        //Adding current user to the list of friends
        if (currentUser != null && !friendLeaderboard.contains(currentUser)) {
            friendLeaderboard.add(currentUser);
        }
        //Ordering Leaderboard by points
        friendLeaderboard.sort(Comparator.comparingInt(User::getPoints).reversed());
        // Add both leaderboards to the model
        model.addAttribute("globalLeaderboard", globalLeaderboard);
        model.addAttribute("friendLeaderboard", friendLeaderboard);
        return "leaderboard";
    }

}
