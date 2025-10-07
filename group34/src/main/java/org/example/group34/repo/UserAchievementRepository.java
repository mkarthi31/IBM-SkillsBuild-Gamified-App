package org.example.group34.repo;

import org.example.group34.model.User;
import org.example.group34.model.UserAchievement;
import org.example.group34.model.AchievementBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    boolean existsByUserAndBadge(User user, AchievementBadge badge);
    List<UserAchievement> findByUser(User user);
}
