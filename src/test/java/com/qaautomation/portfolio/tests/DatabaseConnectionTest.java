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

        boolean exists = DatabaseHelper.messageExists(
                "test@example.com",
                "Test message from Selenium"
        );

        Assert.assertTrue(
                exists,
                "Expected message was not found in the database"
        );
    }

}
