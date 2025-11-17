package org.example.dao;

import java.util.List;

public interface FollowerDAO {
    boolean sendFollowRequest(int senderId, int receiverId);
    boolean acceptFollowRequest(int requestId);
    boolean rejectFollowRequest(int requestId, int receiverId);
    List<String> getPendingRequests(int receiverId);
    boolean followDirect(int followerId, int followingId);
    boolean unfollow(int followerId, int followingId);
    List<String> getFollowers(int userId);
    List<String> getFollowing(int userId);
}
