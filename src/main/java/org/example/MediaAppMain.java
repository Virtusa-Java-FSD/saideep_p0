package org.example;

import org.example.model.User;
import org.example.service.*;
import org.example.util.LoggerConfig;
import java.util.Scanner;
public class MediaAppMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = new UserService();
        PostService postService = new PostService();
        CommentService commentService = new CommentService();
        FollowerService followerService = new FollowerService();
        ActivityLogger mongoLogger = new ActivityLogger();

        while (true) {

            LoggerConfig.logger.info("===== MEDIA APP STARTED =====");
            mongoLogger.info("Application started", "SYSTEM");

            System.out.println("\n===== MEDIA APP =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                LoggerConfig.logger.warn("Invalid input for main choice");
                mongoLogger.warn("Invalid main menu input", "SYSTEM");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    String otp = AuthService.generateOTP();
                    boolean registered = userService.register(username, email, password, otp);

                    if (registered) {
                        EmailService.sendOTP(email, username, otp);
                        LoggerConfig.logger.info("OTP sent to {}", email);
                        mongoLogger.info("OTP sent to " + email, username);

                        System.out.print("Enter the OTP: ");
                        String enteredOtp = sc.nextLine();

                        if (userService.verifyOTP(email, enteredOtp)) {
                            LoggerConfig.logger.info("Email verified successfully for {}", email);
                            mongoLogger.info("Email verified successfully", username);
                        } else {
                            LoggerConfig.logger.warn("Incorrect OTP entered for {}", email);
                            mongoLogger.warn("Incorrect OTP entered", username);
                        }
                    } else {
                        LoggerConfig.logger.error("Registration failed for {}", email);
                        mongoLogger.error("Registration failed for email: " + email, username);
                    }
                    break;

                case 2:
                    System.out.print("Enter email: ");
                    String loginEmail = sc.nextLine();

                    System.out.print("Enter password: ");
                    String loginPass = sc.nextLine();

                    User user = userService.login(loginEmail, loginPass);

                    if (user != null && user.isEmailVerified()) {

                        LoggerConfig.logger.info("User logged in: {}", user.getUsername());
                        mongoLogger.info("User logged in", user.getUsername());

                        boolean loggedIn = true;

                        while (loggedIn) {
                            System.out.println("\n===== MEDIA APP MENU =====");
                            System.out.println("1. Create Post");
                            System.out.println("2. View All Posts");
                            System.out.println("3. Comment on Post");
                            System.out.println("4. Send Follow Request");
                            System.out.println("5. View Followers");
                            System.out.println("6. View Following");
                            System.out.println("7. View Follow Requests");
                            System.out.println("8. Accept Follow Request");
                            System.out.println("9. Reject Follow Request");
                            System.out.println("10. Unfollow User");
                            System.out.println("11. Logout");
                            System.out.print("Enter choice: ");

                            int ch2;
                            try {
                                ch2 = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                LoggerConfig.logger.warn("Invalid input inside logged-in menu");
                                mongoLogger.warn("Invalid choice in logged-in menu", user.getUsername());
                                continue;
                            }

                            switch (ch2) {

                                case 1:
                                    System.out.print("Enter post content: ");
                                    String content = sc.nextLine();

                                    if (postService.createPost(user.getUserId(), content)) {
                                        LoggerConfig.logger.info("Post created by {}", user.getUsername());
                                        mongoLogger.info("Post created", user.getUsername());
                                    } else {
                                        LoggerConfig.logger.error("Failed to create post for {}", user.getUsername());
                                        mongoLogger.error("Post creation failed", user.getUsername());
                                    }
                                    break;

                                case 2:
                                    var posts = postService.getAllPosts();
                                    if (posts.isEmpty()) {
                                        LoggerConfig.logger.info("No posts found");
                                        mongoLogger.info("Viewed posts: none found", user.getUsername());
                                    } else {
                                        posts.forEach(System.out::println);
                                        mongoLogger.info("Viewed all posts", user.getUsername());
                                    }
                                    break;

                                case 3:
                                    System.out.print("Enter Post ID: ");
                                    int pid = Integer.parseInt(sc.nextLine());
                                    System.out.print("Enter comment: ");
                                    String cmt = sc.nextLine();

                                    if (commentService.addComment(pid, user.getUserId(), cmt)) {
                                        LoggerConfig.logger.info("Comment added by {} on post {}", user.getUsername(), pid);
                                        mongoLogger.info("Comment added on post " + pid, user.getUsername());
                                    } else {
                                        LoggerConfig.logger.error("Failed to add comment on post {}", pid);
                                        mongoLogger.error("Failed to comment on post " + pid, user.getUsername());
                                    }
                                    break;

                                case 4:
                                    System.out.print("Enter username/email to follow: ");
                                    String recv = sc.nextLine();

                                    boolean sent = followerService.sendFollowRequest(user.getUsername(), recv);

                                    if (sent) {
                                        LoggerConfig.logger.info("Follow request sent: {} -> {}", user.getUsername(), recv);
                                        mongoLogger.info("Follow request sent to " + recv, user.getUsername());
                                    } else {
                                        LoggerConfig.logger.error("Failed to send follow request: {} -> {}", user.getUsername(), recv);
                                        mongoLogger.error("Follow request FAILED to " + recv, user.getUsername());
                                    }
                                    break;

                                case 5:
                                    var followers = followerService.getFollowers(user.getUsername());
                                    if (followers.isEmpty()) {
                                        LoggerConfig.logger.info("{} has no followers", user.getUsername());
                                        mongoLogger.info("Viewed followers: none found", user.getUsername());
                                    } else {
                                        followers.forEach(System.out::println);
                                        mongoLogger.info("Viewed followers", user.getUsername());
                                    }
                                    break;

                                case 6:
                                    var following = followerService.getFollowing(user.getUsername());
                                    if (following.isEmpty()) {
                                        LoggerConfig.logger.info("{} is not following anyone", user.getUsername());
                                        mongoLogger.info("Viewed following: none found", user.getUsername());
                                    } else {
                                        following.forEach(System.out::println);
                                        mongoLogger.info("Viewed following list", user.getUsername());
                                    }
                                    break;

                                case 7:
                                    var pending = followerService.getPendingRequests(user.getUsername());
                                    if (pending.isEmpty()) {
                                        LoggerConfig.logger.info("No pending follow requests");
                                        mongoLogger.info("Viewed follow requests: none", user.getUsername());
                                    } else {
                                        pending.forEach(System.out::println);
                                        mongoLogger.info("Viewed pending follow requests", user.getUsername());
                                    }
                                    break;

                                case 8:
                                    System.out.print("Enter Request ID to Accept: ");
                                    int reqA = Integer.parseInt(sc.nextLine());

                                    if (followerService.acceptRequest(reqA)) {
                                        LoggerConfig.logger.info("Follow request accepted: {}", reqA);
                                        mongoLogger.info("Accepted follow request " + reqA, user.getUsername());
                                    } else {
                                        LoggerConfig.logger.error("Failed to accept follow request: {}", reqA);
                                        mongoLogger.error("Failed to accept request " + reqA, user.getUsername());
                                    }
                                    break;

                                case 9:
                                    System.out.print("Enter Request ID to Reject: ");
                                    int reqR = Integer.parseInt(sc.nextLine());

                                    if (followerService.rejectRequest(reqR)) {
                                        LoggerConfig.logger.info("Follow request rejected: {}", reqR);
                                        mongoLogger.info("Rejected follow request " + reqR, user.getUsername());
                                    } else {
                                        LoggerConfig.logger.error("Failed to reject follow request: {}", reqR);
                                        mongoLogger.error("Failed to reject request " + reqR, user.getUsername());
                                    }
                                    break;

                                case 10:
                                    System.out.print("Enter username/email to unfollow: ");
                                    String un = sc.nextLine();

                                    if (followerService.unfollow(user.getUsername(), un)) {
                                        LoggerConfig.logger.info("{} unfollowed {}", user.getUsername(), un);
                                        mongoLogger.info("Unfollowed " + un, user.getUsername());
                                    } else {
                                        LoggerConfig.logger.error("Failed to unfollow {}", un);
                                        mongoLogger.error("Unfollow failed for " + un, user.getUsername());
                                    }
                                    break;

                                case 11:
                                    loggedIn = false;
                                    LoggerConfig.logger.info("User logged out: {}", user.getUsername());
                                    mongoLogger.info("User logged out", user.getUsername());
                                    break;

                                default:
                                    LoggerConfig.logger.warn("Invalid logged-in menu choice");
                                    mongoLogger.warn("Invalid logged-in menu input", user.getUsername());
                            }
                        }

                    } else {
                        LoggerConfig.logger.error("Login failed for {}", loginEmail);
                        mongoLogger.error("Login failed for " + loginEmail, "UNKNOWN");
                    }
                    break;

                case 3:
                    LoggerConfig.logger.info("Application exited.");
                    mongoLogger.info("Application exited", "SYSTEM");
                    return;

                default:
                    LoggerConfig.logger.warn("Invalid choice in main menu");
                    mongoLogger.warn("Invalid main menu choice", "SYSTEM");
            }
        }
    }
}
