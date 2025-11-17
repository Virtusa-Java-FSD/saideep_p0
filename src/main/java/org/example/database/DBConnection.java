package org.example.database;

import org.example.util.LoggerConfig;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/media_app";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static Connection connection;

    private DBConnection() {}
    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/media_app?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "1234"
                );

                LoggerConfig.logger.info("MySQL Connected");
            }
        } catch (Exception e) {
            LoggerConfig.logger.error("Database connection error: " + e.getMessage());
        }

        return connection;
    }

}
