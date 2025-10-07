package org.example.group34.model;

import jakarta.persistence.*;

@Entity
public class AchievementBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Badge name (e.g., "First Steps")
    private String name;
    // Description with unlock requirements (e.g., "Complete 1 course")
    private String description;
    // File name for the colored badge image
    private String imageColor;
    // File name for the grey (locked) badge image
    private String imageGrey;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageColor() {
        return imageColor;
    }
    public void setImageColor(String imageColor) {
        this.imageColor = imageColor;
    }

    public String getImageGrey() {
        return imageGrey;
    }
    public void setImageGrey(String imageGrey) {
        this.imageGrey = imageGrey;
    }
}
