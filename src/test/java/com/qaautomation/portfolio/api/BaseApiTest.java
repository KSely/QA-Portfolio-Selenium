package com.qaautomation.portfolio.api;

import com.qaautomation.portfolio.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    @BeforeClass
    public void setUpApi() {
        // Set the base URL for API requests.
        RestAssured.baseURI = ConfigReader.getBaseUrl();

        // Log request and response details if a test fails.
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }
}