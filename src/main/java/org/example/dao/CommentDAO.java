package org.example.dao;

import org.example.model.Comment;
import java.util.List;

public interface CommentDAO {
    boolean addComment(int postId, int userId, String content);
    List<Comment> getCommentsForPost(int postId);
}
