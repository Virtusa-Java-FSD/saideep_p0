package org.example.dao;

import org.example.database.DBConnection;
import org.example.util.LoggerConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowerDAOImpl implements FollowerDAO {

    @Override
    public boolean sendFollowRequest(int senderId, int receiverId) {
        String sql = "INSERT INTO follow_requests (sender_id, receiver_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error sendFollowRequest: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean acceptFollowRequest(int requestId) {
        String getSql = "SELECT sender_id, receiver_id FROM follow_requests WHERE request_id = ? AND status = 'pending'";
        String updateSql = "UPDATE follow_requests SET status = 'accepted' WHERE request_id = ?";
        String insertSql = "INSERT INTO followers (follower_id, following_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getInstance()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psGet = conn.prepareStatement(getSql)) {
                psGet.setInt(1, requestId);
                ResultSet rs = psGet.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }
                int senderId = rs.getInt("sender_id");
                int receiverId = rs.getInt("receiver_id");

                try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                    psUpdate.setInt(1, requestId);
                    psUpdate.executeUpdate();
                }
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setInt(1, senderId);
                    psInsert.setInt(2, receiverId);
                    psInsert.executeUpdate();
                }
                conn.commit();
                return true;

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error acceptFollowRequest: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean rejectFollowRequest(int requestId, int receiverId) {
        String sql = "UPDATE follow_requests SET status = 'rejected' WHERE request_id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error rejectFollowRequest: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getPendingRequests(int receiverId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT fr.request_id, u.username, fr.request_time FROM follow_requests fr JOIN users u ON fr.sender_id = u.user_id WHERE fr.receiver_id = ? AND fr.status = 'PENDING' ORDER BY fr.request_time DESC";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, receiverId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int requestId = rs.getInt("request_id");
                String sender = rs.getString("username");
                String time = rs.getString("request_time");
                list.add("RequestID: " + requestId + " | From: " + sender + " | At: " + time);
            }
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error getPendingRequests: {}", e.getMessage());
        }
        return list;
    }

    @Override
    public boolean followDirect(int followerId, int followingId) {
        String sql = "INSERT INTO followers (follower_id, following_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LoggerConfig.logger.warn("Duplicate or error followDirect: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean unfollow(int followerId, int followingId) {
        String sql = "DELETE FROM followers WHERE follower_id = ? AND following_id = ?";
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error unfollow: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getFollowers(int userId) {
        String sql = """
                SELECT f.follower_id, u.username
                FROM followers f
                JOIN users u ON f.follower_id = u.user_id
                WHERE f.following_id = ?
                """;
        List<String> list = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add("UserID:" + rs.getInt("follower_id") + " (" + rs.getString("username") + ")");
            }
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error getFollowers: {}", e.getMessage());
        }
        return list;
    }

    @Override
    public List<String> getFollowing(int userId) {
        String sql = """
                SELECT f.following_id, u.username
                FROM followers f
                JOIN users u ON f.following_id = u.user_id
                WHERE f.follower_id = ?
                """;
        List<String> list = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add("UserID:" + rs.getInt("following_id") + " (" + rs.getString("username") + ")");
            }
        } catch (SQLException e) {
            LoggerConfig.logger.error("Error getFollowing: {}", e.getMessage());
        }
        return list;
    }
}
