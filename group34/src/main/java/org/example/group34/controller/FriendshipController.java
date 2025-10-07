package org.example.group34.controller;

import org.example.group34.model.FriendRequest;
import org.example.group34.model.User;
import org.example.group34.service.FriendRequestService;
import org.example.group34.service.FriendshipService;
import org.example.group34.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendRequestService friendRequestService;
    private final UserService userService;
    private final FriendshipService friendshipService;

    public FriendshipController(FriendRequestService friendRequestService, UserService userService, FriendshipService friendshipService) {
        this.friendRequestService = friendRequestService;
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @GetMapping
    public String showFriendsPage(Model model, Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        // Fetching friends list
        List<User> friends = friendshipService.getFriends(username);
        model.addAttribute("friends", friends);

        // Debugging output
        System.out.println("Current User: " + username);
        for (User friend : friends) {
            System.out.println("Friend: " + friend.getUsername());
        }

        // Fetching pending friend requests
        model.addAttribute("pendingRequests", friendRequestService.getPendingRequests(currentUser));

        // Debugging output
        for (FriendRequest req : friendRequestService.getPendingRequests(currentUser)) {
            System.out.println("Request ID: " + req.getId());
            System.out.println("Sender: " + req.getSender().getUsername());
            System.out.println("Receiver: " + req.getReceiver().getUsername());
        }

        return "friends";
    }

    @PostMapping("/request")
    public String sendFriendRequest(@RequestParam String receiverUsername,
                                    Authentication authentication, Model model) {
        String senderUsername = authentication.getName();
        String message = friendRequestService.sendFriendRequest(senderUsername, receiverUsername);

        model.addAttribute("message", message);
        return showFriendsPage(model, authentication); // Refresh the friends page with the message
    }


    @PostMapping("/accept/{id}")
    public String acceptFriendRequest(@PathVariable int id,Authentication authentication) {
        String currentUsername = authentication.getName();
        friendshipService.acceptFriendRequest(id, currentUsername);
        return "redirect:/friends";
    }

    @PostMapping("/reject/{id}")
    public String declineFriendRequest(@PathVariable int id) {
        friendRequestService.rejectFriendRequest(id);
        return "redirect:/friends";
    }

    @PostMapping("/unfriend/{username}")
    public String unfriend(@PathVariable String username, Authentication authentication) {
        String currentUsername = authentication.getName();
        friendshipService.unfriend(currentUsername, username);
        return "redirect:/friends";
    }

}
