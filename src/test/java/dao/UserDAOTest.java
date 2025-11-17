package dao;

import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.database.DBConnection;
import org.example.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {

    private static UserDAO userDAO;

    @BeforeAll
    static void setup() {
        userDAO = new UserDAOImpl();
    }

    @BeforeEach
    void cleanTable() throws SQLException {
        Connection conn = DBConnection.getInstance();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM users");
        ps.executeUpdate();
    }

    @Test
    @Order(1)
    void testRegisterUser() {
        boolean result = userDAO.registerUser(
                "testuser",
                "test@example.com",
                "pass123",
                "1111"
        );
        Assertions.assertTrue(result, "User should register successfully");
    }

    @Test
    @Order(2)
    void testDuplicateEmailFails() {
        userDAO.registerUser("user1", "same@gmail.com", "pass1", "1234");
        boolean result = userDAO.registerUser("user2", "same@gmail.com", "pass2", "5678");
        Assertions.assertFalse(result, "Duplicate email must fail");
    }

    @Test
    @Order(3)
    void testVerifyOTP() {
        userDAO.registerUser("otpuser", "otp@gmail.com", "1234", "9999");

        boolean verified = userDAO.verifyOTP("otp@gmail.com", "9999");

        Assertions.assertTrue(verified, "OTP should verify correctly");
    }

    @Test
    @Order(4)
    void testLoginUser() {
        userDAO.registerUser("loginu", "login@gmail.com", "pass", "4444");
        userDAO.verifyOTP("login@gmail.com", "4444");

        User u = userDAO.loginUser("login@gmail.com", "pass");

        Assertions.assertNotNull(u, "Login should return a user");
        Assertions.assertEquals("loginu", u.getUsername());
    }
    @Test
    @Order(5)
    void testGetUserByEmailOrUsername() {
        userDAO.registerUser("abc", "abc@mail.com", "p", "5555");

        User u1 = userDAO.getUserByEmailOrUsername("abc");
        User u2 = userDAO.getUserByEmailOrUsername("abc@mail.com");

        Assertions.assertNotNull(u1);
        Assertions.assertNotNull(u2);
    }
}
