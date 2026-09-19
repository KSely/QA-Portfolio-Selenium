package com.qaautomation.portfolio.pages;

import com.qaautomation.portfolio.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    private static final String URL = ConfigReader.getBaseUrl();

    private final By skillsButton = By.id("skills-button");
    private final By projectButton = By.id("project-button");
    private final By heroImage = By.id("hero-image");

    private final By contactForm = By.id("contact-form");
    private final By nameInput = By.id("name");
    private final By emailInput = By.id("email");
    private final By messageInput = By.id("message");
    private final By sendMessageButton = By.id("send-message-button");
    private final By successMessage = By.id("success-message");

    private final By projectDetailsButton = By.id("project-details-button");

    private final By skillsSection = By.id("skills");
    private final By projectSection = By.id("project");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        open(URL);
    }
    public void clickSkillsButton() {
        driver.findElement(skillsButton).click();
    }

    public boolean isSkillsSectionDisplayed() {
        return driver.findElement(skillsSection).isDisplayed();
    }

    public void clickProjectButton() {
        driver.findElement(projectButton).click();
    }

    public boolean isProjectSectionDisplayed() {
        return driver.findElement(projectSection).isDisplayed();
    }

    public void clickProjectDetailsButton() {
        click(projectButton);
        click(projectDetailsButton);
    }

    public void enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public String getNameValue() {
        return driver.findElement(nameInput).getAttribute("value");
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public String getEmailValue() {
        return driver.findElement(emailInput).getAttribute("value");
    }

    public boolean isHeroImageDisplayed() {
        return driver.findElement(heroImage).isDisplayed();
    }

    public void enterMessage(String message) {
        driver.findElement(messageInput).sendKeys(message);
    }

    public String getMessageValue() {
        return driver.findElement(messageInput).getAttribute("value");
    }

    public void fillContactForm(String name, String email, String message) {
        enterName(name);
        enterEmail(email);
        enterMessage(message);
    }

    public void clickSendMessageButton() {
        click(sendMessageButton);
    }

    public boolean isSuccessMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public boolean isNameFieldRequired() {
        return driver.findElement(nameInput).getAttribute("required") != null;
    }

    public boolean isNameValueMissing() {
        WebElement nameField = driver.findElement(nameInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        return (Boolean) js.executeScript(
                "return arguments[0].validity.valueMissing;", nameField);
    }

    public boolean isEmailTypeMismatch() {
        WebElement emailField = driver.findElement(emailInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        return (Boolean) js.executeScript(
                "return arguments[0].validity.typeMismatch;", emailField);
    }

    public boolean isEmailValueMissing() {
        WebElement emailField = driver.findElement(emailInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object result = js.executeScript(
                "return arguments[0].validity.valueMissing;", emailField);
        return Boolean.TRUE.equals(result);
    }

    public boolean isMessageValueMissing() {
        WebElement messageField = driver.findElement(messageInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object result = js.executeScript(
                "return arguments[0].validity.valueMissing;",
                messageField
        );

        return Boolean.TRUE.equals(result);
    }
}
