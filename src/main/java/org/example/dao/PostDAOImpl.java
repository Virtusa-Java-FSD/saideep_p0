package org.example.dao;

import org.example.database.DBConnection;
import org.example.model.Post;
import org.example.util.LoggerConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAOImpl implements PostDAO {

    @Override
    public boolean createPost(int userId, String content) {
        String sql = "INSERT INTO posts (user_id, content) VALUES (?, ?)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, content);

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error creating post: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        String sql = "SELECT p.post_id, p.user_id, p.content, p.created_at, u.username " +
                "FROM posts p JOIN users u ON p.user_id = u.user_id " +
                "ORDER BY p.created_at DESC";

        List<Post> list = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("post_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setUsername(rs.getString("username"));
                p.setContent(rs.getString("content"));
                p.setCreatedAt(String.valueOf(rs.getTimestamp("created_at")));

                list.add(p);
            }

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error fetching posts: {}", e.getMessage());
        }

        return list;
    }
}
