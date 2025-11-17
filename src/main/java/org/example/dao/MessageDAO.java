package org.example.dao;

import org.example.model.Message;
import java.util.List;

public interface MessageDAO {
    boolean sendMessage(String senderUsername, String receiverUsername, String content);
    List<Message> getMessagesBetween(String user1, String user2);
}
