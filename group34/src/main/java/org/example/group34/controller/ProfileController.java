package org.example.group34.controller;

import org.example.group34.model.AchievementBadge;
import org.example.group34.model.User;
import org.example.group34.model.UserAchievement;
import org.example.group34.service.UserService;
import org.example.group34.repo.AchievementBadgeRepository;
import org.example.group34.repo.UserAchievementRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.example.group34.service.FriendshipService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {
    private final UserService userService;
    private final AchievementBadgeRepository achievementBadgeRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final FriendshipService friendshipService;


    public ProfileController(UserService userService,
                             AchievementBadgeRepository achievementBadgeRepository,
                             UserAchievementRepository userAchievementRepository,
                             FriendshipService friendshipService) {
        this.userService = userService;
        this.achievementBadgeRepository = achievementBadgeRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.friendshipService = friendshipService;
    }
    @GetMapping("/myProfile")
    public String myProfile(Model model) {
        // Get the current user.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        // Get all available badges.
        List<AchievementBadge> allBadges = achievementBadgeRepository.findAll();
        // Get the list of earned badge IDs.
        List<UserAchievement> userAchievements = userAchievementRepository.findByUser(user);
        List<Long> earnedBadgeIds = userAchievements.stream()
                .map(ua -> ua.getBadge().getId())
                .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("allBadges", allBadges);
        model.addAttribute("earnedBadges", earnedBadgeIds);
        int friendCount = friendshipService.getFriends(username).size();
        model.addAttribute("friendCount", friendCount);
        return "myProfile";
    }

    @GetMapping("/profile-image/{userId}")
    @ResponseBody
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("userId") int userId) {
        User user = userService.findById(userId);
        if (user == null || user.getProfilePicture() == null) {
            // Return 404 if user or picture not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"profile.jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(user.getProfilePicture());
    }
}
