package org.example.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class CapchaService {

    public static String generateCaptcha(String filePath) {
        int width = 160, height = 60;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            captchaText.append(chars.charAt(random.nextInt(chars.length())));
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString(captchaText.toString(), 25, 45);
        g2.dispose();

        try {
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("CAPTCHA generated: " + captchaText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return captchaText.toString();
    }
}
