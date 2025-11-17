package org.example.service;

import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.dao.MongoUserProfileDAO;
import org.example.dao.MongoUserProfileDAOImpl;
import org.example.model.MongoUserProfile;
import org.example.model.User;

public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final MongoUserProfileDAO mongoProfileDAO = new MongoUserProfileDAOImpl();

    public boolean register(String username, String email, String password, String otp) {
        boolean ok = userDAO.registerUser(username, email, password, otp);

        if (ok) {
            User newUser = userDAO.getUserByEmailOrUsername(email);
            if (newUser != null) {
                MongoUserProfile profile = new MongoUserProfile(
                        newUser.getUserId(),
                        newUser.getUsername(),
                        newUser.getEmail(),
                        false
                );
                mongoProfileDAO.saveUserProfile(profile);
            }
        }
        return ok;
    }

    public boolean verifyOTP(String email, String otp) {
        return userDAO.verifyOTP(email, otp);
    }

    public User login(String email, String password) {
        return userDAO.loginUser(email, password);
    }

    public User getUserByEmailOrUsername(String value) {
        return userDAO.getUserByEmailOrUsername(value);
    }
}
