package org.example.group34.repo;

import org.example.group34.model.AchievementBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementBadgeRepository extends JpaRepository<AchievementBadge, Long> {
    AchievementBadge findByName(String name);
}
