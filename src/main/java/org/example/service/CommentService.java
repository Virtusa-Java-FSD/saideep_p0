package org.example.service;

import org.example.database.DBConnection;
import org.example.model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService {

    private final Connection conn;

    public CommentService() {
        this.conn = DBConnection.getInstance();
    }

    public boolean addComment(int postId, int userId, String content) {
        String sql = "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, userId);
            ps.setString(3, content);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding comment: " + e.getMessage());
            return false;
        }
    }

    public List<Comment> getComments(int postId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY created_at ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment c = new Comment(
                            rs.getInt("comment_id"),
                            rs.getInt("post_id"),
                            rs.getInt("user_id"),
                            rs.getString("content"),
                            rs.getString("created_at")
                    );
                    comments.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching comments: " + e.getMessage());
        }
        return comments;
    }
}
