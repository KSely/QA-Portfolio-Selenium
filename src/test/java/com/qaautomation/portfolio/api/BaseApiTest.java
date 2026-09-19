package com.qaautomation.portfolio.api;

import com.qaautomation.portfolio.config.ConfigReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {
    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = ConfigReader.getBaseUrl();
    }
}
