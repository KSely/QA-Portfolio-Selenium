package com.qaautomation.portfolio.database;

import com.qaautomation.portfolio.config.ConfigReader;

import java.sql.*;

public class DatabaseHelper {

    private static final String DB_URL = ConfigReader.getDbUrl();
    private static final String DB_USER = ConfigReader.getDbUser();
    private static final String DB_PASSWORD = ConfigReader.getDbPassword();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

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
