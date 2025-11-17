package org.example.service;

import org.example.dao.PostDAO;
import org.example.dao.PostDAOImpl;
import org.example.model.Post;

import java.util.List;

public class PostService {

    private final PostDAO postDAO = new PostDAOImpl();

    public boolean createPost(int userId, String content) {
        return postDAO.createPost(userId, content);
    }

    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }
}
