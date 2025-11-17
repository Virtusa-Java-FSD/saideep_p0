package org.example.model;

public class Comment {

    private int commentId;
    private int postId;
    private int userId;
    private String content;
    private String createdAt;
    private String username;

    public Comment(int commentId, int postId, int userId, String content, String createdAt, String username) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.username = username;
    }

    public Comment() {}

    public Comment(int commentId, int postId, int userId, String content, String createdAt) {}

    public int getCommentId() {
        return commentId;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
