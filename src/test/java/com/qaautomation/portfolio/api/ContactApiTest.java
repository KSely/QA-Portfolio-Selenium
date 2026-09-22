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

    @Story("Email Format Validation - Invalid Formats")
    @Test(dataProvider = "invalidEmails")
    public void contactEndpointShouldReturn400ForInvalidEmailFormats(
            String email) throws SQLException {

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

    @Story("Successful Contact Submission")
    @Test
    public void contactEndpointShouldSubmitMessageSuccessfully() throws SQLException {

        // Use unique data for each test run.
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
            // Remove test data from the database.
            DatabaseHelper.deleteMessage(email, message);
        }
    }

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