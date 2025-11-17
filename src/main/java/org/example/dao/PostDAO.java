package org.example.dao;

import org.example.model.Post;
import java.util.List;

public interface PostDAO {
    boolean createPost(int userId, String content);
    List<Post> getAllPosts();
}
