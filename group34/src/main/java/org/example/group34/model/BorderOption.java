package org.example.group34.model;

public class BorderOption {
    private String color; // "blue", "red", "green", "silver", "gold"
    private boolean unlocked;

    public BorderOption(String color, boolean unlocked) {
        this.color = color;
        this.unlocked = unlocked;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
