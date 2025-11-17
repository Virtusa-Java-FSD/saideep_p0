package org.example.service;

import org.example.dao.MessageDAO;
import org.example.dao.MessageDAOImpl;
import org.example.model.Message;

import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDAOImpl();

    public boolean send(String sender, String receiver, String message) {
        return messageDAO.sendMessage(sender, receiver, message);
    }

    public List<Message> getConversation(String user1, String user2) {
        return messageDAO.getMessagesBetween(user1, user2);
    }
}
