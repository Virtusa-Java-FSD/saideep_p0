package org.example.model;

public class MongoUserProfile {

    private int userId;
    private String username;
    private String email;
    private boolean emailVerified;

    public MongoUserProfile(int userId, String username, String email, boolean emailVerified) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.emailVerified = emailVerified;
    }

    public int getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public boolean isEmailVerified() {
        return emailVerified;
    }
}
