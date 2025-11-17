package org.example.dao;

import org.example.database.DBConnection;
import org.example.model.User;
import org.example.util.LoggerConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean registerUser(String username, String email, String password, String otp) {
        String sql = "INSERT INTO users (username, email, password_hash, verification_token, email_verified) " +
                "VALUES (?, ?, ?, ?, false)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, otp);

            boolean success = ps.executeUpdate() > 0;

            if (success)
                LoggerConfig.logger.info("User registered successfully: {}", email);
            else
                LoggerConfig.logger.warn("User registration may have failed: {}", email);

            return success;

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error registerUser: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        String sql = "SELECT verification_token FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String correct = rs.getString("verification_token");
                if (correct != null && correct.equals(otp)) {
                    LoggerConfig.logger.info("OTP verified successfully for {}", email);
                    return updateEmailVerified(email);
                } else {
                    LoggerConfig.logger.warn("OTP does not match for {}", email);
                }
            }

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error verifyOTP: {}", e.getMessage());
        }
        return false;
    }

    private boolean updateEmailVerified(String email) {
        String sql = "UPDATE users SET email_verified = true WHERE email = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            boolean updated = ps.executeUpdate() > 0;

            if (updated)
                LoggerConfig.logger.info("Email verified updated in database for {}", email);
            else
                LoggerConfig.logger.warn("Failed to update email verification for {}", email);

            return updated;

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error updateEmailVerified: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ? AND email_verified = true";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LoggerConfig.logger.info("Login successful for {}", email);
                return extractUser(rs);
            } else {
                LoggerConfig.logger.warn("Login failed (incorrect credentials or unverified email): {}", email);
            }

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error loginUser: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByEmailOrUsername(String value) {
        String sql = "SELECT * FROM users WHERE email = ? OR username = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);
            ps.setString(2, value);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LoggerConfig.logger.info("User fetched by email/username: {}", value);
                return extractUser(rs);
            } else {
                LoggerConfig.logger.warn("User not found with value: {}", value);
            }

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error getUserByEmailOrUsername: {}", e.getMessage());
        }
        return null;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));

        try {
            u.setPasswordHash(rs.getString("password_hash"));
        } catch (SQLException ignored) { }

        try {
            u.setVerificationToken(rs.getString("verification_token"));
        } catch (SQLException ignored) { }

        try {
            u.setEmailVerified(rs.getBoolean("email_verified"));
        } catch (SQLException ignored) { }

        return u;
    }
}
