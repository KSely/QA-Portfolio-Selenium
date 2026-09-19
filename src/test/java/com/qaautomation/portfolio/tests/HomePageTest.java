package com.qaautomation.portfolio.tests;

import com.qaautomation.portfolio.base.BaseTest;
import com.qaautomation.portfolio.database.DatabaseHelper;
import com.qaautomation.portfolio.pages.HomePage;
import com.qaautomation.portfolio.pages.ProjectPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

@Epic("Portfolio Web Application")
@Feature("Home Page")
public class HomePageTest extends BaseTest {

    private HomePage homePage;
    private ProjectPage projectPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        homePage = new HomePage(driver);
        projectPage = new ProjectPage(driver);
    }

    @Story("Section Navigation")
    @Test(groups = "smoke")
    public void skillsSectionShouldBeDisplayedAfterClickingSkillsButton() {
        homePage.openHomePage();
        homePage.clickSkillsButton();

        Assert.assertTrue(homePage.isSkillsSectionDisplayed());
    }

    @Story("Section Navigation")
    @Test
    public void projectSectionShouldBeDisplayedAfterClickingProjectButton() {
        homePage.openHomePage();
        homePage.clickProjectButton();

        Assert.assertTrue(homePage.isProjectSectionDisplayed());
    }

    @Story("Project Page Navigation")
    @Test(groups = "smoke")
    public void projectDetailsButtonShouldOpenProjectPage() {
        homePage.openHomePage();
        homePage.clickProjectDetailsButton();

        Assert.assertTrue(projectPage.isOverviewSectionDisplayed());
    }

    @Story("Contact Form Input")
    @Test
    public void nameFieldShouldAcceptText() {
        homePage.openHomePage();
        homePage.enterName("Test");
        Assert.assertEquals(homePage.getNameValue(), "Test");
    }

    @Story("Contact Form Input")
    @Test
    public void emailFieldShouldAcceptText() {
        homePage.openHomePage();
        homePage.enterEmail("test@example.com");
        Assert.assertEquals(homePage.getEmailValue(), "test@example.com");
    }

    @Story("Contact Form Input")
    @Test
    public void messageFieldShouldAcceptText() {
        homePage.openHomePage();
        homePage.enterMessage("Test message");
        Assert.assertEquals(homePage.getMessageValue(), "Test message");
    }

    @Story("Successful Contact Form Submission")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = "smoke")
    public void contactFormShouldSubmitSuccessfully() throws SQLException {

        String uniqueId = String.valueOf(System.currentTimeMillis());

        String name = "Test";
        String email = "test" + uniqueId + "@example.com";
        String message = "Test message from Selenium " + uniqueId;

        try {
        homePage.openHomePage();
        homePage.fillContactForm(name, email, message);
        homePage.clickSendMessageButton();

        Assert.assertTrue(homePage.isSuccessMessageDisplayed());
        Assert.assertTrue(DatabaseHelper.messageExists(email, message),
                "Submitted message should exist in the database"
        );
        } finally {
            DatabaseHelper.deleteMessage(email, message);
        }
    }

    //negative test
    @Story("Contact Form Validation")
    @Test
    public void nameFieldShouldBeRequired() {
        homePage.openHomePage();
        Assert.assertTrue(homePage.isNameFieldRequired());
    }

    //negative test
    @Story("Contact Form Validation")
    @Test
    public void contactFormShouldNotSubmitWhenNameIsEmpty() {
        homePage.openHomePage();
        homePage.enterEmail("test@example.com");
        homePage.enterMessage("Test message");
        homePage.clickSendMessageButton();
        Assert.assertTrue(homePage.isNameValueMissing());
    }

    //negative test
    @Story("Contact Form Validation")
    @Test
    public void contactFormShouldNotSubmitWithInvalidEmail() {
        homePage.openHomePage();
        homePage.enterName("Test");
        homePage.enterEmail("kateexample.com");
        homePage.enterMessage("Test message");
        homePage.clickSendMessageButton();
        Assert.assertTrue(homePage.isEmailTypeMismatch());
    }

    //negative test
    @Story("Contact Form Validation")
    @Test
    public void contactFormShouldNotSubmitWhenEmailIsEmpty() {
        homePage.openHomePage();
        homePage.enterName("Test");
        homePage.enterMessage("Test message");
        homePage.clickSendMessageButton();
        Assert.assertTrue(homePage.isEmailValueMissing());
    }

    // negative test
    @Story("Contact Form Validation")
    @Test
    public void contactFormShouldNotSubmitWhenMessageIsEmpty() {
        homePage.openHomePage();
        homePage.enterName("Test");
        homePage.enterEmail("test@example.com");
        homePage.clickSendMessageButton();
        Assert.assertTrue(homePage.isMessageValueMissing());
    }

}
