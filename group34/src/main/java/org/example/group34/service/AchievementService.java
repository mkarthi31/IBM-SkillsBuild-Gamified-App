package org.example.group34.service;

import org.example.group34.model.*;
import org.example.group34.repo.AchievementBadgeRepository;
import org.example.group34.repo.UserAchievementRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AchievementService {

    private final UserCourseService userCourseService;
    private final AchievementBadgeRepository achievementBadgeRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final CourseService courseService;
    private final UserService userService;

    public AchievementService(UserCourseService userCourseService,
                              AchievementBadgeRepository achievementBadgeRepository,
                              UserAchievementRepository userAchievementRepository,
                              CourseService courseService,
                              UserService userService) {
        this.userCourseService = userCourseService;
        this.achievementBadgeRepository = achievementBadgeRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.courseService = courseService;
        this.userService = userService;
    }

    // Update achievements when a course's completion status changes
    public boolean updateAchievements(User user) {
        // Count completed courses and update points (100 per course)
        int completedCourses = (int) userCourseService.findUserCoursesByUser(user)
                .stream()
                .filter(UserCourses::isCompleted)
                .count();
        user.setCourseCompletionCount(completedCourses);
        user.setPoints(completedCourses * 100);

        boolean badgeUnlocked = false;

        // Check and award course completion badges
        badgeUnlocked |= checkAndAwardBadge(user, "First Steps", completedCourses >= 1);
        badgeUnlocked |= checkAndAwardBadge(user, "Explorer", completedCourses >= 5);
        badgeUnlocked |= checkAndAwardBadge(user, "Expert Learner", completedCourses >= 10);
        badgeUnlocked |= checkAndAwardBadge(user, "Master of SkillsBuild", completedCourses >= 20);

        // Award full completion badge if completed courses equal total courses
        int totalCourses = courseService.searchCourses("", "").size();
        badgeUnlocked |= checkAndAwardBadge(user, "Full Completionist", completedCourses == totalCourses);

        // Update learning streak based on the last course completion date
        updateLearningStreak(user);

        // Award streak badges
        badgeUnlocked |= checkAndAwardBadge(user, "Consistency Champ", user.getCurrentStreak() >= 7);
        badgeUnlocked |= checkAndAwardBadge(user, "Learning Streak Pro", user.getCurrentStreak() >= 30);

        // Save updated user info
        userService.updateUsername(user, user.getUsername()); // or call a generic save method if available

        return badgeUnlocked;  // Return true if any badge was unlocked
    }

    // Checks a condition and awards a badge if it hasn't been awarded already
    private boolean checkAndAwardBadge(User user, String badgeName, boolean conditionMet) {
        AchievementBadge badge = achievementBadgeRepository.findByName(badgeName);
        if (badge != null && conditionMet) {
            boolean alreadyAwarded = userAchievementRepository.existsByUserAndBadge(user, badge);
            if (!alreadyAwarded) {
                UserAchievement userAchievement = new UserAchievement(user, badge, LocalDateTime.now());
                userAchievementRepository.save(userAchievement);
                return true;  // Badge unlocked
            }
        }
        return false;  // Badge not unlocked
    }

    // Updates the learning streak based on the last course completion date.
    private void updateLearningStreak(User user) {
        LocalDate today = LocalDate.now();
        if (user.getLastCourseCompletionDate() == null) {
            user.setCurrentStreak(1);
        } else {
            LocalDate lastDate = user.getLastCourseCompletionDate();
            if (lastDate.equals(today)) {
                // Already updated today.
            } else if (lastDate.plusDays(1).equals(today)) {
                user.setCurrentStreak(user.getCurrentStreak() + 1);
            } else {
                // Reset streak if a day is missed
                user.setCurrentStreak(1);
            }
        }
        user.setLastCourseCompletionDate(today);
    }
}
