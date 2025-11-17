package service;
import org.example.database.DBConnection;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    static UserService userService;

    @BeforeAll
    static void init() {
        userService = new UserService();
        clearTestUser();
    }

    // Deletes test users BEFORE running tests
    private static void clearTestUser() {
        try {
            Connection conn = DBConnection.getInstance();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM users WHERE email = 'test@service.com' OR username='test_user'"
            );
            ps.executeUpdate();
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    void testRegisterUser() {
        boolean result = userService.register(
                "test_user",
                "test@service.com",
                "pass123",
                "999999"
        );
        assertTrue(result);
    }

    @Test
    @Order(2)
    void testVerifyOTP() {
        boolean result = userService.verifyOTP("test@service.com", "999999");
        assertTrue(result);
    }

    @Test
    @Order(3)
    void testLoginUser() {
        User u = userService.login("test@service.com", "pass123");
        assertNotNull(u);
        assertEquals("test_user", u.getUsername());
        assertTrue(u.isEmailVerified());
    }

    @Test
    @Order(4)
    void testGetUserByEmailOrUsername() {
        User u = userService.getUserByEmailOrUsername("test_user");
        assertNotNull(u);
        assertEquals("test_user", u.getUsername());
    }
}
