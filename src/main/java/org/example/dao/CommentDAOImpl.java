package org.example.dao;
import org.example.database.DBConnection;
import org.example.model.Comment;
import org.example.util.LoggerConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CommentDAOImpl implements CommentDAO {
    @Override
    public boolean addComment(int postId, int userId, String content) {
        String sql = "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, userId);
            ps.setString(3, content);
            int rows = ps.executeUpdate();
            LoggerConfig.logger.info("Comment added on Post ID {} by User ID {}", postId, userId);
            return rows > 0;
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error adding comment: {}", e.getMessage());
            return false;
        }
    }
    @Override
    public List<Comment> getCommentsForPost(int postId) {
        List<Comment> list = new ArrayList<>();

        String sql = """
            SELECT c.comment_id, c.post_id, c.user_id, c.content, c.created_at, u.username
            FROM comments c
            JOIN users u ON c.user_id = u.user_id
            WHERE c.post_id = ?
            ORDER BY c.created_at ASC
        """;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comment c = new Comment(
                        rs.getInt("comment_id"),
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("username")
                );
                list.add(c);
            }

            LoggerConfig.logger.info("Fetched {} comments for post {}", list.size(), postId);

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error fetching comments for post {}: {}", postId, e.getMessage());
        }

        return list;
    }
}
