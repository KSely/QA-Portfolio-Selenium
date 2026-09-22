package com.qaautomation.portfolio.database;

import com.qaautomation.portfolio.config.ConfigReader;

import java.sql.*;

public class DatabaseHelper {

    // Database settings from config.properties.
    private static final String DB_URL = ConfigReader.getDbUrl();
    private static final String DB_USER = ConfigReader.getDbUser();
    private static final String DB_PASSWORD = ConfigReader.getDbPassword();

    // Open a database connection.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Check for a message with the given email and text.
    public static boolean messageExists(String email, String message) throws SQLException {

        String sql = "SELECT 1 FROM messages WHERE email = ? AND message = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, message);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Check for a message with the given text.
    public static boolean messageExistsByMessage(String message) throws SQLException {

        String sql = "SELECT 1 FROM messages WHERE message = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, message);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Check for a message with the given email.
    public static boolean messageExistsByEmail(String email) throws SQLException {

        String sql = "SELECT 1 FROM messages WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Delete the test message.
    public static void deleteMessage(String email, String message) throws SQLException {

        String sql = "DELETE FROM messages WHERE email = ? AND message = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, message);

            statement.executeUpdate();
        }
    }
}