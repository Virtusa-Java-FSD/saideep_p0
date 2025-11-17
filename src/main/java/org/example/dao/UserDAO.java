package org.example.dao;

import org.example.model.User;
import java.util.List;

public interface UserDAO {

    boolean registerUser(String username, String email, String password, String otp);

    boolean verifyOTP(String email, String otp);

    User loginUser(String email, String password);

    User getUserByEmailOrUsername(String value);
}
