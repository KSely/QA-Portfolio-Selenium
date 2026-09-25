package com.qaautomation.portfolio.pages;

import com.qaautomation.portfolio.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectPage extends BasePage {

    private static final String URL = ConfigReader.getBaseUrl() + "/project";

    // Page elements.
    private final By overviewSection = By.id("overview");
    private final By architectureSection = By.id("architecture");
    private final By architectureNavLink = By.cssSelector("a[href='#architecture']");
    private final By techStackSection = By.id("tech-stack");
    private final By techStackNavLink = By.cssSelector("a[href='#tech-stack']");
    private final By qaStackSection = By.id("qa-stack");
    private final By qaStackNavLink = By.cssSelector("a[href='#qa-stack']");
    private final By testStrategySection = By.id("test-strategy");
    private final By testStrategyNavLink = By.cssSelector("a[href='#test-strategy']");
    private final By apiTestingSection = By.id("api-testing");
    private final By apiTestingNavLink = By.cssSelector("a[href='#api-testing']");
    private final By databaseTestingSection = By.id("database-testing");
    private final By databaseTestingNavLink = By.cssSelector("a[href='#database-testing']");

    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public void openProjectPage() {
        open(URL);
    }

    public void clickArchitectureLink() {
        driver.findElement(architectureNavLink).click();
    }

    public void clickTechStackLink() {
        driver.findElement(techStackNavLink).click();
    }

    public void clickQaStackLink() {
        driver.findElement(qaStackNavLink).click();
    }

    public void clickApiTestingLink() {
        driver.findElement(apiTestingNavLink).click();
    }

    public void clickDatabaseTestingLink() {
        driver.findElement(databaseTestingNavLink).click();
    }

    public void clickTestStrategyLink() {
        driver.findElement(testStrategyNavLink).click();
    }

    public boolean isOverviewSectionDisplayed() {
        return driver.findElement(overviewSection).isDisplayed();
    }

    public boolean isArchitectureSectionDisplayed() {
        return driver.findElement(architectureSection).isDisplayed();
    }

    public boolean isTechStackSectionDisplayed() {
        return driver.findElement(techStackSection).isDisplayed();
    }

    public boolean isQaStackSectionDisplayed() {
        return driver.findElement(qaStackSection).isDisplayed();
    }

    public boolean isTestStrategySectionDisplayed() {
        return driver.findElement(testStrategySection).isDisplayed();
    }

    public boolean isApiTestingSectionDisplayed() {
        return driver.findElement(apiTestingSection).isDisplayed();
    }

    public boolean isDatabaseTestingSectionDisplayed() {
        return driver.findElement(databaseTestingSection).isDisplayed();
    }

    public boolean isArchitectureUrlFragmentPresent() {
        return driver.getCurrentUrl().contains("#architecture");
    }

    public boolean isTechStackUrlFragmentPresent() {
        return driver.getCurrentUrl().contains("#tech-stack");
    }

    public boolean isQaStackUrlFragmentPresent() {
        return driver.getCurrentUrl().contains("#qa-stack");
    }

    public boolean isTestStrategyUrlFragmentPresent() {
        return driver.getCurrentUrl().contains("#test-strategy");
    }

    public boolean isApiTestingUrlFragmentPresent() {
        return driver.getCurrentUrl().contains("#api-testing");
    }

    public boolean isDatabaseTestingUrlFragmentPresent() {
        return driver.getCurrentUrl().contains("#database-testing");
    }
}