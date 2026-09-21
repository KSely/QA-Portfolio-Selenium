package com.qaautomation.portfolio.tests;

import com.qaautomation.portfolio.database.DatabaseHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    /*
     * DATABASE CONNECTION TEST
     *
     * Purpose:
     * Verifies that the automation framework can successfully connect
     * to the PostgreSQL database using the configuration provided
     * through DatabaseHelper.
     *
     * Test flow:
     * 1. Request a database connection from DatabaseHelper.
     * 2. Verify that the returned Connection object is not null.
     * 3. Verify that the connection is open and available for use.
     * 4. Automatically close the connection using try-with-resources.
     *
     * This test validates the database connectivity layer used by
     * other integration tests in the automation framework.
     */
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


    /*
     * DATABASE RECORD VALIDATION TEST
     *
     * Purpose:
     * Verifies that DatabaseHelper can locate a specific message
     * stored in the PostgreSQL messages table.
     *
     * Test strategy:
     * Unique test data is generated for each execution so the test
     * does not depend on records created by previous test runs.
     *
     * Planned test flow:
     * 1. Generate a unique email and message.
     * 2. Insert the test record into PostgreSQL.
     * 3. Query the database through DatabaseHelper.messageExists().
     * 4. Verify that the inserted record can be found.
     * 5. Delete the test record so the database remains clean.
     *
     * The INSERT and cleanup steps will be added next to make this
     * test fully self-contained and independent of pre-existing data.
     */
    @Test
    public void messageShouldExistInDatabase() throws SQLException {

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
            DatabaseHelper.deleteMessage(email, message);
        }
    }
}
