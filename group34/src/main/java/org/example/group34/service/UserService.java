package org.example.group34.service;

import org.example.group34.model.User;
import org.example.group34.repo.FriendRequestRepository;
import org.example.group34.repo.FriendshipRepository;
import org.example.group34.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendRequestRepository friendRequestRepository;
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    public UserService(UserRepository userRepository, FriendRequestRepository friendRequestRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String rawPassword) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRole("USER");
        newUser.setPoints(0);
        newUser.setCourseCompletionCount(0);
        newUser.setCurrentStreak(0);
        newUser.setLastCourseCompletionDate(null); // Initially no completion date
        userRepository.save(newUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //find a list of all user (used in find all friends)
    public List<User> findAll(String query) {
        return userRepository.findByUsernameContainingIgnoreCase(query);
    }

    //default list of all users when on search user
    public List<User> searchUsers(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return userRepository.findAll(); // Return all users if search is empty
        }
        return userRepository.findByUsernameContainingIgnoreCase(searchQuery);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    //check if a friend request is pending in either direction
    public boolean isFriendRequestPending(User user1, User user2) {
        return friendRequestRepository.existsByReceiverAndSender(user1, user2) ||
                friendRequestRepository.existsByReceiverAndSender(user2, user1);
    }


    public void updateUsername(User user, String newUsername) {
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void updateStreak(User user) {
        LocalDate today = LocalDate.now();
        if (user.getLastCourseCompletionDate() == null || !user.getLastCourseCompletionDate().equals(today.minusDays(1))) {
            user.setCurrentStreak(1);  // Start new streak if no progress or missed a day
        } else {
            user.setCurrentStreak(user.getCurrentStreak() + 1); // Continue the streak
        }
        user.setLastCourseCompletionDate(today);  // Update the last course completion date to today
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }





}
