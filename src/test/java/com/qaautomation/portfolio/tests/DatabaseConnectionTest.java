package com.qaautomation.portfolio.tests;

import com.qaautomation.portfolio.database.DatabaseHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    @Test
    public void databaseConnectionShouldBeSuccessful() throws SQLException {

        try (Connection connection = DatabaseHelper.getConnection()) {

            Assert.assertNotNull(
                    connection,
                    "Database connection should not be null"
            );

            Assert.assertFalse(
                    connection.isClosed(),
                    "Database connection should be open"
            );
        }
    }

    @Test
    public void messageShouldExistInDatabase() throws SQLException {

        // Use unique data for each test run.
        String uniqueId = String.valueOf(System.currentTimeMillis());

        String email = "dbtest" + uniqueId + "@example.com";
        String message = "Database test message " + uniqueId;

        try {
            String sql = "INSERT INTO messages (name, email, message) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseHelper.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, "Database Test");
                statement.setString(2, email);
                statement.setString(3, message);

                statement.executeUpdate();
            }

            boolean exists = DatabaseHelper.messageExists(email, message);

            Assert.assertTrue(
                    exists,
                    "Expected message was not found in the database"
            );

        } finally {
            // Remove test data from the database.
            DatabaseHelper.deleteMessage(email, message);
        }
    }
}