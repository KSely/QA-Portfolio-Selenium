package com.qaautomation.portfolio.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

    @Epic("API Testing")
    @Feature("Status API")
    public class StatusApiTest extends BaseApiTest {

    /*
     * POSITIVE TEST: Application health check
     *
     * Purpose:
     * Verifies that the GET /api/status endpoint is available
     * and returns the expected application status information.
     *
     * Test flow:
     * 1. Read the application base URL from config.properties
     *    using ConfigReader.
     * 2. Send a GET request directly to the /api/status endpoint
     *    using REST Assured.
     * 3. Verify that the API returns HTTP 200.
     * 4. Verify the JSON response:
     *      status = "ok"
     *      message = "QA Automation Portfolio backend is running"
     *
     * This test confirms that the backend application is running
     * and that the status endpoint returns the expected API contract.
     */

    @Story("Application Health Check")
    @Test
    public void statusEndpointShouldReturn200() {

        given()
                .when()
                .get("/api/status")
                .then()
                .statusCode(200)
                .body("status", equalTo("ok"))
                .body("message", equalTo("QA Automation Portfolio backend is running"));
    }
}
