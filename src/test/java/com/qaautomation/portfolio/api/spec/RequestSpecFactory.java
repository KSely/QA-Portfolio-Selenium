package com.qaautomation.portfolio.api.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    public static RequestSpecification contactRequestSpec() {

        return new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded")
                .build();
    }
}
