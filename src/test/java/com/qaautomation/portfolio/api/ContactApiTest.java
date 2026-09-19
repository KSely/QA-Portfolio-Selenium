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
        public void contactEndpointShouldReturn400ForInvalidEmailFormats(String email) {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "API Test")
                    .formParam("email", email)
                    .formParam("message", "Invalid email format data-driven test")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("Invalid email address."));
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
     * 3. Send the same form-urlencoded data that the real contact form sends: name, email, and message.
     * 4. Verify that the API returns HTTP 200.
     * 5. Verify the JSON response:
     *      success = true
     *      message = "Message sent successfully!"
     * 6. Query PostgreSQL using DatabaseHelper and verify that the submitted message was actually saved in the database.
     * 7. Delete the test record in the finally block so the database remains clean even if an assertion fails.
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
    public void contactEndpointShouldReturn400WhenNameIsMissing() {

        given()
                .spec(RequestSpecFactory.contactRequestSpec())
                .formParam("email", "negative@example.com")
                .formParam("message", "Negative API test")
                .when()
                .post("/contact")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("All fields are required."));
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
    public void contactEndpointShouldReturn400WhenEmailIsMissing() {

        given()
                .spec(RequestSpecFactory.contactRequestSpec())
                .formParam("name", "API Test")
                .formParam("message", "Missing email API test")
                .when()
                .post("/contact")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("All fields are required."));
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
    public void contactEndpointShouldReturn400WhenMessageIsMissing() {

        given()
                .spec(RequestSpecFactory.contactRequestSpec())
                .formParam("name", "API Test")
                .formParam("email", "negative@example.com")
                .when()
                .post("/contact")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("All fields are required."));
    }
        /*
         * NEGATIVE TEST: Empty name field
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects a request
         * when the name parameter is present but contains an empty value.
         *
         * Test flow:
         * 1. Send a POST request to /contact.
         * 2. Include all required form parameters.
         * 3. Set the name parameter to an empty string.
         * 4. Verify that the API returns HTTP 400.
         * 5. Verify that success is false.
         * 6. Verify that the API returns the expected validation message.
         *
         * This test is different from the missing-name test:
         * - Missing name: the "name" parameter is not sent at all.
         * - Empty name: the "name" parameter is sent, but its value is "".
         *
         * The backend should reject both cases before inserting
         * any data into the database.
         */
        @Story("Required Field Validation - Empty Name")
        @Test
        public void contactEndpointShouldReturn400WhenNameIsEmpty() {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "")
                    .formParam("email", "negative@example.com")
                    .formParam("message", "Empty name API test")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
        }

        /*
         * NEGATIVE TEST: Empty email field
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects a request
         * when the email parameter is present but contains an empty value.
         *
         * Test flow:
         * 1. Send a POST request to /contact.
         * 2. Include all required form parameters.
         * 3. Set the email parameter to an empty string.
         * 4. Verify that the API returns HTTP 400.
         * 5. Verify that success is false.
         * 6. Verify that the API returns the expected validation message.
         *
         * This test is different from the missing-email test:
         * - Missing email: the "email" parameter is not sent at all.
         * - Empty email: the "email" parameter is sent, but its value is "".
         *
         * Because the required-field validation is performed before
         * email-format validation, an empty email should return
         * "All fields are required."
         *
         * No database cleanup is required because the request should
         * be rejected before any INSERT operation is performed.
         */
        @Story("Required Field Validation - Empty Email")
        @Test
        public void contactEndpointShouldReturn400WhenEmailIsEmpty() {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "API Test")
                    .formParam("email", "")
                    .formParam("message", "Empty email API test")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
        }

        /*
         * NEGATIVE TEST: Empty message field
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects a request
         * when the message parameter is present but contains an empty value.
         *
         * Test flow:
         * 1. Send a POST request to /contact.
         * 2. Include all required form parameters.
         * 3. Set the message parameter to an empty string.
         * 4. Verify that the API returns HTTP 400.
         * 5. Verify that success is false.
         * 6. Verify that the API returns the expected validation message.
         *
         * This test is different from the missing-message test:
         * - Missing message: the "message" parameter is not sent at all.
         * - Empty message: the "message" parameter is sent, but its value is "".
         *
         * No database cleanup is required because the request should
         * be rejected before any INSERT operation is performed.
         */
        @Story("Required Field Validation - Empty Message")
        @Test
        public void contactEndpointShouldReturn400WhenMessageIsEmpty() {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "API Test")
                    .formParam("email", "negative@example.com")
                    .formParam("message", "")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
        }

        /*
         * NEGATIVE TEST: Name contains only whitespace
         *
         * Purpose:
         * Verifies how the POST /contact endpoint handles a name
         * that contains only whitespace characters.
         *
         * A whitespace-only value is technically a non-empty string,
         * but from a validation perspective it should normally be treated
         * as an empty required field.
         *
         * Test flow:
         * 1. Send a POST request to /contact.
         * 2. Set the name parameter to spaces only.
         * 3. Provide valid email and message values.
         * 4. Expect the API to reject the request with HTTP 400.
         * 5. Verify that success is false.
         * 6. Verify the required-field validation message.
         *
         * This test helps identify whether the backend trims input
         * before performing required-field validation.
         */
        @Story("Required Field Validation - Whitespace Name")
        @Test
        public void contactEndpointShouldReturn400WhenNameContainsOnlyWhitespace() {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "   ")
                    .formParam("email", "negative@example.com")
                    .formParam("message", "Whitespace name API test")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
        }

        /*
         * NEGATIVE TEST: Email contains only whitespace
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects an email
         * that contains only whitespace characters.
         *
         * A whitespace-only value should be treated as an empty
         * required field after input validation.
         *
         * Test flow:
         * 1. Send a POST request to /contact.
         * 2. Provide a valid name and message.
         * 3. Set the email parameter to spaces only.
         * 4. Expect HTTP 400.
         * 5. Verify that success is false.
         * 6. Verify the required-field validation message.
         */
        @Story("Required Field Validation - Whitespace Email")
        @Test
        public void contactEndpointShouldReturn400WhenEmailContainsOnlyWhitespace() {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "API Test")
                    .formParam("email", "   ")
                    .formParam("message", "Whitespace email API test")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
        }

        /*
         * NEGATIVE TEST: Message contains only whitespace
         *
         * Purpose:
         * Verifies that the POST /contact endpoint rejects a message
         * that contains only whitespace characters.
         *
         * A whitespace-only value should be treated as an empty
         * required field after input validation.
         *
         * Test flow:
         * 1. Send a POST request to /contact.
         * 2. Provide a valid name and email.
         * 3. Set the message parameter to spaces only.
         * 4. Expect HTTP 400.
         * 5. Verify that success is false.
         * 6. Verify the required-field validation message.
         */
        @Story("Required Field Validation - Whitespace Message")
        @Test
        public void contactEndpointShouldReturn400WhenMessageContainsOnlyWhitespace() {

            given()
                    .spec(RequestSpecFactory.contactRequestSpec())
                    .formParam("name", "API Test")
                    .formParam("email", "negative@example.com")
                    .formParam("message", "   ")
                    .when()
                    .post("/contact")
                    .then()
                    .statusCode(400)
                    .body("success", equalTo(false))
                    .body("message", equalTo("All fields are required."));
        }
}
