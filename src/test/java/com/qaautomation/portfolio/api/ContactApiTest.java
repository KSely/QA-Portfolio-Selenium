package com.qaautomation.portfolio.api;

import com.qaautomation.portfolio.api.spec.RequestSpecFactory;
import com.qaautomation.portfolio.database.DatabaseHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

    @Epic("API Testing")
    @Feature("Contact API")
    public class ContactApiTest extends BaseApiTest {

        @DataProvider(name = "invalidEmails")
        public Object[][] invalidEmails() {
            return new Object[][]{
                    {"invalid-email"},
                    {"test@"},
                    {"@example.com"},
                    {"test@example"}
            };
        }

        @DataProvider(name = "invalidRequiredFieldValues")
        public Object[][] invalidRequiredFieldValues() {
            return new Object[][]{
                    {"name", ""},
                    {"name", "   "},
                    {"email", ""},
                    {"email", "   "},
                    {"message", ""},
                    {"message", "   "}
            };
        }

        /*
         * DATA-DRIVEN NEGATIVE TEST: Invalid required field values
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects required fields
         * when they contain either an empty string or whitespace only.
         *
         * Test data is provided by the "invalidRequiredFieldValues"
         * DataProvider.
         */
        @Story("Required Field Validation - Empty and Whitespace Values")
        @Test(dataProvider = "invalidRequiredFieldValues")
        public void contactEndpointShouldReturn400ForInvalidRequiredFieldValue(
                String fieldName, String invalidValue) throws SQLException {

            String uniqueId = String.valueOf(System.currentTimeMillis());

            String name = "API Test";
            String email = "negative" + uniqueId + "@example.com";
            String message = "Required field validation API test " + uniqueId;

            switch (fieldName) {
                case "name" -> name = invalidValue;
                case "email" -> email = invalidValue;
                case "message" -> message = invalidValue;
                default -> throw new IllegalArgumentException(
                        "Unsupported required field: " + fieldName
                );
            }

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", name)
                    .formParam("email", email)
                    .formParam("message", message)
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
            boolean exists = DatabaseHelper.messageExists(email, message);

            Assert.assertFalse(
                    exists,
                    "Rejected API message should not exist in the database"
            );
        }

        /*
         * DATA-DRIVEN NEGATIVE TEST: Invalid email formats
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects multiple
         * invalid email formats using a single reusable test method.
         *
         * Test data is provided by the "invalidEmails" DataProvider.
         * The test runs once for each invalid email value.
         */
        @Story("Email Format Validation - Invalid Formats")
        @Test(dataProvider = "invalidEmails")
        public void contactEndpointShouldReturn400ForInvalidEmailFormats(String email) throws SQLException {

            String uniqueId = String.valueOf(System.currentTimeMillis());
            String message = "Invalid email format data-driven test " + uniqueId;

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "API Test")
                    .formParam("email", email)
                    .formParam("message", message)
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("Invalid email address."));

            boolean exists = DatabaseHelper.messageExists(email, message);

            Assert.assertFalse(
                    exists,
                    "Rejected API message should not exist in the database"
            );
        }

    /*
     * POSITIVE TEST: Successful contact form submission
     *
     * Purpose:
     * Verifies the complete successful flow of the POST /contact endpoint.
     *
     * Test flow:
     * 1. Generate unique test data so every test run uses a different email and message.
     * 2. Send a POST request directly to the /contact API endpoint using REST Assured.
     * 3. Send the same form-urlencoded data that the real contact form sends:
     *    name, email, and message.
     * 4. Verify that the API returns HTTP 200.
     * 5. Verify the JSON response:
     *      success = true
     *      message = "Message sent successfully!"
     * 6. Query PostgreSQL using DatabaseHelper and verify that the submitted
     *    message was actually saved in the database.
     * 7. Delete the test record in the finally block so the database remains
     *    clean even if an assertion fails.
     *
     * This test validates the integration:
     * REST Assured -> Express API -> PostgreSQL -> JDBC verification.
     */
    @Story("Successful Contact Submission")
    @Test
    public void contactEndpointShouldSubmitMessageSuccessfully() throws SQLException {

        String uniqueId = String.valueOf(System.currentTimeMillis());

        String name = "API Test";
        String email = "apitest" + uniqueId + "@example.com";
        String message = "REST Assured test message " + uniqueId;

        try {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", name)
                    .formParam("email", email)
                    .formParam("message", message)
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(200)
                    .body("success", equalTo(true))
                    .body("message", equalTo("Message sent successfully!"));

            boolean exists = DatabaseHelper.messageExists(email, message);

            Assert.assertTrue(
                    exists,
                    "Submitted API message should exist in the database"
            );

        } finally {
            DatabaseHelper.deleteMessage(email, message);
        }
    }

        /*
         * NEGATIVE TEST: Missing required name
         *
         * Purpose:
         * Verifies backend validation when the required "name" field
         * is not included in the request.
         *
         * We send:
         *   email   -> valid
         *   message -> valid
         *   name    -> NOT sent
         *
         * Expected result:
         *   HTTP 400
         *   success = false
         *   message = "All fields are required."
         *
         * No database cleanup is required because the backend rejects
         * the request before the INSERT statement is executed.
         */
        @Story("Required Field Validation - Missing Name")
        @Test
        public void contactEndpointShouldReturn400WhenNameIsMissing() throws SQLException {

            String uniqueId = String.valueOf(System.currentTimeMillis());

            String email = "negative" + uniqueId + "@example.com";
            String message = "Missing name API test " + uniqueId;

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("email", email)
                    .formParam("message", message)
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));

            boolean exists = DatabaseHelper.messageExists(email, message);

            Assert.assertFalse(
                    exists,
                    "Rejected API message should not exist in the database"
            );
        }

    /*
     * NEGATIVE TEST: Missing required email
     *
     * Purpose:
     * Verifies backend validation when the required "email" field
     * is completely missing from the request.
     *
     * We send:
     *   name    -> valid
     *   message -> valid
     *   email   -> NOT sent
     *
     * Expected result:
     *   HTTP 400
     *   success = false
     *   message = "All fields are required."
     *
     * This test verifies required-field validation, while
     * email-format validation is covered separately by the
     * data-driven invalid email test.
     */
    @Story("Required Field Validation - Missing Email")
    @Test
    public void contactEndpointShouldReturn400WhenEmailIsMissing() throws SQLException {

        String uniqueId = String.valueOf(System.currentTimeMillis());

        String name = "API Test";
        String message = "Missing email API test " + uniqueId;

        given()
                .spec(RequestSpecFactory.contactRequestSpec())
                .formParam("name", name)
                .formParam("message", message)
                .when()
                .post("/contact")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("All fields are required."));
        boolean exists = DatabaseHelper.messageExistsByMessage(message);

        Assert.assertFalse(
                exists,
                "Rejected API message should not exist in the database"
        );

    }

    /*
     * NEGATIVE TEST: Missing required message
     *
     * Purpose:
     * Verifies backend validation when the required "message" field
     * is not included in the request.
     *
     * We send:
     *   name    -> valid
     *   email   -> valid
     *   message -> NOT sent
     *
     * Expected result:
     *   HTTP 400
     *   success = false
     *   message = "All fields are required."
     *
     * No database record should be created because validation fails
     * before the backend executes the INSERT statement.
     */
    @Story("Required Field Validation - Missing Message")
    @Test
    public void contactEndpointShouldReturn400WhenMessageIsMissing() throws SQLException {

        String uniqueId = String.valueOf(System.currentTimeMillis());

        String name = "API Test";
        String email = "negative" + uniqueId + "@example.com";

        given()
                .spec(RequestSpecFactory.contactRequestSpec())
                .formParam("name", name)
                .formParam("email", email)
                .when()
                .post("/contact")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("All fields are required."));

        boolean exists = DatabaseHelper.messageExistsByEmail(email);

        Assert.assertFalse(
                exists,
                "Rejected API message should not exist in the database"
        );
    }
}
