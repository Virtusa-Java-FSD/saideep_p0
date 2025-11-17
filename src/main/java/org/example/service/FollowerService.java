package org.example.service;

import org.example.dao.FollowerDAO;
import org.example.dao.FollowerDAOImpl;
import org.example.model.User;
import org.example.util.LoggerConfig;
import java.util.List;

public class FollowerService {

    private final FollowerDAO dao = new FollowerDAOImpl();
    private final UserService userService = new UserService();

    public boolean sendFollowRequest(String senderUsername, String receiverUsername) {
        User sender = userService.getUserByEmailOrUsername(senderUsername);
        User receiver = userService.getUserByEmailOrUsername(receiverUsername);

        if (sender == null) {
            LoggerConfig.logger.warn("Sender not found: {}", senderUsername);
            return false;
        }

        if (receiver == null) {
            LoggerConfig.logger.warn("Receiver not found: {}", receiverUsername);
            return false;
        }

        if (sender.getUserId() == receiver.getUserId()) {
            LoggerConfig.logger.warn("User {} attempted to follow themselves.", senderUsername);
            return false;
        }

        boolean result = dao.sendFollowRequest(sender.getUserId(), receiver.getUserId());
        if (result)
            LoggerConfig.logger.info("Follow request sent: {} -> {}", senderUsername, receiverUsername);
        else
            LoggerConfig.logger.error("Failed to send follow request: {} -> {}", senderUsername, receiverUsername);

        return result;
    }

    public boolean acceptRequest(int requestId) {
        boolean result = dao.acceptFollowRequest(requestId);

        if (result)
            LoggerConfig.logger.info("Follow request accepted (ID: {})", requestId);
        else
            LoggerConfig.logger.error("Failed to accept follow request (ID: {})", requestId);

        return result;
    }

    public boolean rejectRequest(int requestId) {
        int receiverId = 0;
        boolean result = dao.rejectFollowRequest(requestId, receiverId);

        if (result)
            LoggerConfig.logger.info("Follow request rejected (ID: {})", requestId);
        else
            LoggerConfig.logger.error("Failed to reject follow request (ID: {})", requestId);

        return result;
    }

    public List<String> getPendingRequests(String receiverUsername) {
        User receiver = userService.getUserByEmailOrUsername(receiverUsername);

        if (receiver == null) {
            LoggerConfig.logger.warn("Attempt to view pending requests for non-existing user: {}", receiverUsername);
            return List.of();
        }

        List<String> requests = dao.getPendingRequests(receiver.getUserId());
        LoggerConfig.logger.info("Fetched {} pending follow requests for {}", requests.size(), receiverUsername);

        return requests;
    }

    public boolean unfollow(String followerUsername, String followingUsername) {
        User follower = userService.getUserByEmailOrUsername(followerUsername);
        User following = userService.getUserByEmailOrUsername(followingUsername);

        if (follower == null || following == null) {
            LoggerConfig.logger.warn("Invalid unfollow attempt: {} -> {}", followerUsername, followingUsername);
            return false;
        }

        boolean result = dao.unfollow(follower.getUserId(), following.getUserId());

        if (result)
            LoggerConfig.logger.info("{} unfollowed {}", followerUsername, followingUsername);
        else
            LoggerConfig.logger.error("Unfollow failed: {} -> {}", followerUsername, followingUsername);

        return result;
    }

    public List<String> getFollowers(String username) {
        User u = userService.getUserByEmailOrUsername(username);

        if (u == null) {
            LoggerConfig.logger.warn("User not found when fetching followers: {}", username);
            return List.of();
        }

        List<String> list = dao.getFollowers(u.getUserId());
        LoggerConfig.logger.info("Fetched {} followers for {}", list.size(), username);

        return list;
    }

    public List<String> getFollowing(String username) {
        User u = userService.getUserByEmailOrUsername(username);

        if (u == null) {
            LoggerConfig.logger.warn("User not found when fetching following list: {}", username);
            return List.of();
        }

        List<String> list = dao.getFollowing(u.getUserId());
        LoggerConfig.logger.info("Fetched {} following users for {}", list.size(), username);

        return list;
    }

    public boolean rejectFollowRequest(int senderId, int receiverId) {
        boolean result = dao.rejectFollowRequest(senderId, receiverId);

        if (result)
            LoggerConfig.logger.info("Follow request rejected: {} -> {}", senderId, receiverId);
        else
            LoggerConfig.logger.error("Failed to reject follow request: {} -> {}", senderId, receiverId);

        return result;
    }

}
