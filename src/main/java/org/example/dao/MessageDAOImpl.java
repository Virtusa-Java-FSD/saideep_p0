package org.example.dao;

import org.example.database.DBConnection;
import org.example.model.Message;
import org.example.util.LoggerConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    private final Connection conn;

    public MessageDAOImpl() {
        this.conn = DBConnection.getInstance();
    }

    @Override
    public boolean sendMessage(String senderUsername, String receiverUsername, String content) {
        try {
            String getIdSQL = "SELECT user_id FROM users WHERE username = ?";

            PreparedStatement ps1 = conn.prepareStatement(getIdSQL);
            ps1.setString(1, senderUsername);
            ResultSet rs1 = ps1.executeQuery();
            if (!rs1.next()) {
                LoggerConfig.logger.warn("Sender not found: {}", senderUsername);
                return false;
            }
            int senderId = rs1.getInt("user_id");

            PreparedStatement ps2 = conn.prepareStatement(getIdSQL);
            ps2.setString(1, receiverUsername);
            ResultSet rs2 = ps2.executeQuery();
            if (!rs2.next()) {
                LoggerConfig.logger.warn("Receiver not found: {}", receiverUsername);
                return false;
            }
            int receiverId = rs2.getInt("user_id");

            String insertSQL = "INSERT INTO messages (sender_id, receiver_id, content) VALUES (?, ?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(insertSQL);
            ps3.setInt(1, senderId);
            ps3.setInt(2, receiverId);
            ps3.setString(3, content);

            boolean result = ps3.executeUpdate() > 0;
            if (result) {
                LoggerConfig.logger.info("Message sent from {} to {}", senderUsername, receiverUsername);
            }
            return result;

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error in sendMessage: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public List<Message> getMessagesBetween(String user1, String user2) {
        List<Message> messages = new ArrayList<>();
        try {
            String sql = """
                    SELECT m.*, u1.username AS sender_name, u2.username AS receiver_name
                    FROM messages m
                    JOIN users u1 ON m.sender_id = u1.user_id
                    JOIN users u2 ON m.receiver_id = u2.user_id
                    WHERE (u1.username = ? AND u2.username = ?)
                       OR (u1.username = ? AND u2.username = ?)
                    ORDER BY m.sent_at
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.setString(3, user2);
            ps.setString(4, user1);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message();
                msg.setMessageId(rs.getInt("message_id"));
                msg.setSenderId(rs.getInt("sender_id"));
                msg.setReceiverId(rs.getInt("receiver_id"));
                msg.setContent(rs.getString("content"));
                msg.setSentAt(rs.getTimestamp("sent_at"));
                msg.setSenderName(rs.getString("sender_name"));
                msg.setReceiverName(rs.getString("receiver_name"));
                messages.add(msg);
            }

            LoggerConfig.logger.info("Fetched {} messages between {} and {}", messages.size(), user1, user2);

        } catch (SQLException e) {
            LoggerConfig.logger.error("Error getMessagesBetween: {}", e.getMessage());
        }
        return messages;
    }
}
