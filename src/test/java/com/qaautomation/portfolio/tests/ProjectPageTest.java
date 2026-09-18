package com.qaautomation.portfolio.tests;

import com.qaautomation.portfolio.base.BaseTest;
import com.qaautomation.portfolio.pages.ProjectPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Portfolio Web Application")
@Feature("Project Page")
public class ProjectPageTest extends BaseTest {

    private ProjectPage projectPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        projectPage = new ProjectPage(driver);
    }

    @Story("Section Visibility")
    @Test(groups = "smoke")
    public void overviewSectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isOverviewSectionDisplayed());
    }

    @Story("Section Visibility")
    @Test
    public void architectureSectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isArchitectureSectionDisplayed());
    }

    @Story("Section Visibility")
    @Test
    public void techStackSectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isTechStackSectionDisplayed());
    }

    @Story("Section Visibility")
    @Test
    public void qaStackSectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isQaStackSectionDisplayed());
    }

    @Story("Section Visibility")
    @Test
    public void testStrategySectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isTestStrategySectionDisplayed());
    }

    @Story("Section Visibility")
    @Test
    public void apiTestingSectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isApiTestingSectionDisplayed());
    }

    @Story("Section Visibility")
    @Test
    public void databaseTestingSectionShouldBeDisplayed() {
        projectPage.openProjectPage();

        Assert.assertTrue(projectPage.isDatabaseTestingSectionDisplayed());
    }

    //check http://localhost:3000/project#architecture UrlFragment
    @Story("Section Navigation")
    @Test
    public void architectureLinkShouldNavigateToArchitectureSection() {
        projectPage.openProjectPage();
        projectPage.clickArchitectureLink();
        Assert.assertTrue(projectPage.isArchitectureSectionDisplayed());
        Assert.assertTrue(projectPage.isArchitectureUrlFragmentPresent());
    }

    @Story("Section Navigation")
    @Test
    public void techStackLinkShouldNavigateToTechStackSection() {
        projectPage.openProjectPage();
        projectPage.clickTechStackLink();

        Assert.assertTrue(projectPage.isTechStackSectionDisplayed());
        Assert.assertTrue(projectPage.isTechStackUrlFragmentPresent());
    }

    @Story("Section Navigation")
    @Test
    public void qaStackLinkShouldNavigateToQaStackSection() {
        projectPage.openProjectPage();
        projectPage.clickQaStackLink();

        Assert.assertTrue(projectPage.isQaStackSectionDisplayed());
        Assert.assertTrue(projectPage.isQaStackUrlFragmentPresent());
    }

    @Story("Section Navigation")
    @Test
    public void testStrategyLinkShouldNavigateToTestStrategySection() {
        projectPage.openProjectPage();
        projectPage.clickTestStrategyLink();

        Assert.assertTrue(projectPage.isTestStrategySectionDisplayed());
        Assert.assertTrue(projectPage.isTestStrategyUrlFragmentPresent());
    }

    @Story("Section Navigation")
    @Test
    public void apiTestingLinkShouldNavigateToApiTestingSection() {
        projectPage.openProjectPage();
        projectPage.clickApiTestingLink();

        Assert.assertTrue(projectPage.isApiTestingSectionDisplayed());
        Assert.assertTrue(projectPage.isApiTestingUrlFragmentPresent());
    }

    @Story("Section Navigation")
    @Test
    public void databaseTestingLinkShouldNavigateToDatabaseTestingSection() {
        projectPage.openProjectPage();
        projectPage.clickDatabaseTestingLink();

        Assert.assertTrue(projectPage.isDatabaseTestingSectionDisplayed());
        Assert.assertTrue(projectPage.isDatabaseTestingUrlFragmentPresent());
    }

}
