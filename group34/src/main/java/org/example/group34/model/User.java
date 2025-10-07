package org.example.group34.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*.])(?=\\S+$).*$",
            message = "Password must contain at least one uppercase letter, one number, and one special character.")
    @Column(name = "password", nullable = false)
    private String password;

    private String role;

    // New fields for gamification
    private int points;
    private int courseCompletionCount;
    private int currentStreak;
    private String profileBorder;
    private LocalDate lastCourseCompletionDate;

    //Code for profile picture field
    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }


    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    // Getters and setters for new fields
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public int getCourseCompletionCount() {
        return courseCompletionCount;
    }
    public void setCourseCompletionCount(int courseCompletionCount) {
        this.courseCompletionCount = courseCompletionCount;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public LocalDate getLastCourseCompletionDate() {
        return lastCourseCompletionDate;
    }
    public void setLastCourseCompletionDate(LocalDate lastCourseCompletionDate) {
        this.lastCourseCompletionDate = lastCourseCompletionDate;
    }

    public String getProfileBorder() {
        return profileBorder;
    }
    public void setProfileBorder(String profileBorder) {
        this.profileBorder = profileBorder;
    }
}
