package com.qaautomation.portfolio.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // Store values from config.properties.
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream =
                     ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static String getDbUrl() {
        return properties.getProperty("dbUrl");
    }

    public static String getDbUser() {
        return properties.getProperty("dbUser");
    }

    public static String getDbPassword() {
        return properties.getProperty("dbPassword");
    }
}