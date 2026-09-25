package com.qaautomation.portfolio.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    // Create a driver for the selected browser.
    public WebDriver createDriver(String browser) {

        // Remove extra spaces and use lowercase.
        browser = browser.trim().toLowerCase();

        return switch (browser) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        };
    }

    private WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();

        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
        }

        return new ChromeDriver(options);
    }
}
