package org.example.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import org.example.util.EmailConfig;

public class EmailService {

    public static void sendOTP(String recipientEmail, String username, String otp) {
        String subject = "Your MediaApp Verification OTP";
        String body = "Hi " + username + ",\n\n"
                + "Your MediaApp verification OTP is:\n\n"
                + otp + "\n\n"
                + "Please enter this OTP in the application to verify your account.\n\n"
                + "Best Regards,\n"
                + "MediaApp Team";

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", EmailConfig.HOST);
            props.put("mail.smtp.port", EmailConfig.PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "false");

            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            EmailConfig.USERNAME,
                            EmailConfig.PASSWORD
                    );
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfig.USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("📧 OTP sent successfully to " + recipientEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send OTP email to " + recipientEmail);
        }
    }
}
