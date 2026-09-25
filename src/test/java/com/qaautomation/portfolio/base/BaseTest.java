package com.qaautomation.portfolio.base;

import com.qaautomation.portfolio.driver.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    // WebDriver shared by UI tests.
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {

        // Create the WebDriver for the selected browser.
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.createDriver(browser);

        // Maximize the browser locally.
        // In CI, the browser window size is configured in DriverFactory.
        if (System.getenv("CI") == null) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        // Attach a screenshot to Allure when a test fails.
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            byte[] screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Screenshot on Failure",
                    "image/png",
                    new java.io.ByteArrayInputStream(screenshot),
                    ".png"
            );
        }

        // Close the browser after each test.
        if (driver != null) {
            driver.quit();
        }
    }
}