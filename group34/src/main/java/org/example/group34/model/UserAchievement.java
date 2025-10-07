package org.example.group34.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who earned the badge
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // The badge earned
    @ManyToOne
    @JoinColumn(name = "achievement_badge_id")
    private AchievementBadge badge;

    // When the badge was earned
    private LocalDateTime achievedAt;

    public UserAchievement() {}

    public UserAchievement(User user, AchievementBadge badge, LocalDateTime achievedAt) {
        this.user = user;
        this.badge = badge;
        this.achievedAt = achievedAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public AchievementBadge getBadge() {
        return badge;
    }
    public void setBadge(AchievementBadge badge) {
        this.badge = badge;
    }

    public LocalDateTime getAchievedAt() {
        return achievedAt;
    }
    public void setAchievedAt(LocalDateTime achievedAt) {
        this.achievedAt = achievedAt;
    }
}
