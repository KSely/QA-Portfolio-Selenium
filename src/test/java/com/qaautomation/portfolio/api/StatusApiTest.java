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