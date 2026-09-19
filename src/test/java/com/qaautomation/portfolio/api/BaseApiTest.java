package com.qaautomation.portfolio.api;

import com.qaautomation.portfolio.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.BeforeClass;


/*
 * Base class for API tests.
 *
 * Purpose:
 * Provides common REST Assured configuration that is shared
 * by all API test classes in the automation framework.
 *
 * API test classes such as ContactApiTest and StatusApiTest
 * extend this class so that common configuration does not need
 * to be repeated in every individual test.
 *
 * Configuration performed before API tests:
 *
 * 1. Base URI configuration
 *    The application base URL is read from config.properties
 *    through ConfigReader and assigned to RestAssured.baseURI.
 *
 *    Example:
 *    If baseUrl = http://localhost:3000
 *
 *    then a request such as:
 *        get("/api/status")
 *
 *    is automatically sent to:
 *        http://localhost:3000/api/status
 *
 *    This keeps environment-specific configuration outside
 *    of the test classes and avoids hardcoding URLs in tests.
 *
 * 2. Request and response logging on validation failure
 *    REST Assured is configured to log full request and response
 *    details only when a response validation fails.
 *
 *    This provides useful diagnostic information such as:
 *    - HTTP method and request URI
 *    - request parameters and headers
 *    - response status code
 *    - response headers
 *    - response body
 *
 *    Successful tests do not produce unnecessary detailed logs,
 *    keeping the test output cleaner.
 *
 * TestNG lifecycle:
 * The @BeforeClass method runs before the test methods in each
 * API test class that extends BaseApiTest.
 */
public class BaseApiTest {
    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = ConfigReader.getBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }
}
