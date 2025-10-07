package org.example.group34.controller;

import org.example.group34.model.FriendRequest;
import org.example.group34.model.User;
import org.example.group34.service.FriendRequestService;
import org.example.group34.service.FriendshipService;
import org.example.group34.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private FriendRequestService friendRequestService;

    @GetMapping("/search")
    public String searchUsers(@RequestParam(required = false) String query, Model model, Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        List<User> users;

        // If no search term is provided, show all users except the current user
        if (query == null || query.trim().isEmpty()) {
            users = userService.getAllUsers().stream()
                    .filter(user -> !user.getUsername().equals(currentUser.getUsername()))
                    .toList();
        } else {
            users = userService.searchUsers(query).stream()
                    .filter(user -> !user.getUsername().equals(currentUser.getUsername()))
                    .toList();
        }

        // Prepare a map to track friendship/request status
        Map<String, String> userStatuses = new HashMap<>();

        for (User user : users) {
            if (friendshipService.areFriends(currentUser, user)) {
                userStatuses.put(user.getUsername(), "friends");
            } else if (friendRequestService.isRequestPending(currentUser, user)) {
                userStatuses.put(user.getUsername(), "pending");
            } else {
                userStatuses.put(user.getUsername(), "add");
            }
        }

        model.addAttribute("users", users);
        model.addAttribute("userStatuses", userStatuses);

        return "searchUsers";
    }

}

