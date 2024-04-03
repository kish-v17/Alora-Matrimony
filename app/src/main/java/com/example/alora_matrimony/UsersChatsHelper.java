package com.example.alora_matrimony;

public class UsersChatsHelper {
    private String id;
    private String name;
    private String profileImageUrl;
    private String recentMessage;
    private String time;

    public UsersChatsHelper() {
        // Default constructor required for Firebase
    }

    // Constructor with required fields
    public UsersChatsHelper(String id, String name, String profileImageUrl, String recentMessage, String time) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.recentMessage = recentMessage;
        this.time = time;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(String recentMessage) {
        this.recentMessage = recentMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
