package org.example.group34.service;

import org.example.group34.model.User;
import org.example.group34.repo.FriendRequestRepository;
import org.example.group34.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendshipService friendshipService;

    // Get Global Leaderboard
    public List<User> getGlobalLeaderboard() {
        return userRepository.findAllByOrderByPointsDesc();
    }

    // Get Friends Leaderboard
    public List<User> getFriendLeaderboard(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found for username: " + username);
        }
        return friendshipService.getFriends(username);
    }
}
