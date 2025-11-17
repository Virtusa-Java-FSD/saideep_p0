package org.example.model;

public class Post {

    private int postId;
    private int userId;
    private String content;
    private String createdAt;

    public Post(int postId, int userId, String content, String createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Post() {}

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post ID: " + postId +
                " | User ID: " + userId +
                " | Content: " + content +
                " | Created At: " + createdAt;
    }

    public void setUsername(String username) {
    }
}
