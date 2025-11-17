package org.example.model;

public class UserProfile {

    private int profileId;
    private int userId;
    private String fullName;
    private String bio;
    private String location;
    private String profilePic;

    public UserProfile() {}

    public UserProfile(int profileId, int userId, String fullName, String bio, String location, String profilePic) {
        this.profileId = profileId;
        this.userId = userId;
        this.fullName = fullName;
        this.bio = bio;
        this.location = location;
        this.profilePic = profilePic;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "profileId=" + profileId +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}
