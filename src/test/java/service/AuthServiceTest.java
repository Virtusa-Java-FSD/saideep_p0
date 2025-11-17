package service;

import org.example.service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    void testOtpLengthIsSix() {
        String otp = AuthService.generateOTP();
        assertEquals(6, otp.length(), "OTP must be 6 digits long");
    }

    @Test
    void testOtpIsNumeric() {
        String otp = AuthService.generateOTP();
        assertTrue(otp.matches("\\d{6}"), "OTP must contain only digits");
    }

    @Test
    void testOtpShouldNotAlwaysBeSame() {
        String otp1 = AuthService.generateOTP();
        String otp2 = AuthService.generateOTP();
        assertNotEquals(otp1, otp2, "Two OTPs should not always match");
    }

    @Test
    void testPasswordHashNotNull() {
        String hash = AuthService.hashPassword("secret123");
        assertNotNull(hash, "Hashed password should not be null");
    }

    @Test
    void testPasswordHashIsSha256Length() {
        String hash = AuthService.hashPassword("secret123");
        assertEquals(64, hash.length(), "SHA-256 hash must be 64 chars hex");
    }

    @Test
    void testPasswordHashConsistency() {
        String hash1 = AuthService.hashPassword("mypassword");
        String hash2 = AuthService.hashPassword("mypassword");
        assertEquals(hash1, hash2, "Hashing same password must produce same result");
    }

    @Test
    void testDifferentPasswordsProduceDifferentHashes() {
        String hash1 = AuthService.hashPassword("password1");
        String hash2 = AuthService.hashPassword("password2");
        assertNotEquals(hash1, hash2, "Different passwords must produce different hashes");
    }
}
