# QA Portfolio - Selenium Automation Framework

A QA automation framework built with Java and Selenium WebDriver for testing a full-stack portfolio web application.

The project demonstrates automated testing across multiple application layers, including UI, API, and database validation.

## Tech Stack

- Java 25
- Selenium WebDriver
- TestNG
- REST Assured
- PostgreSQL / JDBC
- Maven
- Allure Report
- Git / GitHub
- GitHub Actions

## Project Overview

This repository contains an automated testing framework created for a full-stack QA portfolio web application.

The framework includes:

- **UI Testing** — automated browser testing using Selenium WebDriver
- **API Testing** — REST API validation using REST Assured
- **Database Testing** — PostgreSQL validation using JDBC
- **Cross-Browser Testing** — UI test execution in Chrome, Firefox, and Edge
- **Test Reporting** — Allure reporting with test results, environment information, and failure screenshots

The framework follows the Page Object Model (POM) design pattern and uses reusable components for browser management, configuration, API requests, and database operations.

## Test Coverage

The automation framework currently includes:

- **25 UI regression tests** covering the Home and Project pages
- **Cross-browser execution** across Chrome, Firefox, and Edge
- **15 API test executions** covering application health, successful contact submission, required field validation, whitespace validation, and invalid email formats
- **Database integration tests** validating PostgreSQL connectivity and stored message data
- **Database validation for API and UI workflows** to verify that accepted data is persisted and rejected data is not stored
- **Automatic test data cleanup** to keep database tests independent and repeatable

## Project Structure

```text
src
├── main
│   ├── java/com/qaautomation/portfolio
│   │   ├── config        # Configuration management
│   │   ├── database      # PostgreSQL database utilities
│   │   ├── driver        # WebDriver creation and browser management
│   │   └── pages         # Page Object Model classes
│   └── resources
│       ├── config.properties           # Local configuration (not committed)
│       └── config.properties.example   # Configuration template
│
└── test
    ├── java/com/qaautomation/portfolio
    │   ├── api
    │   │   ├── spec      # Reusable REST Assured request specifications
    │   │   ├── BaseApiTest
    │   │   ├── ContactApiTest
    │   │   └── StatusApiTest
    │   ├── base
    │   │   └── BaseTest  # Common Selenium setup and teardown
    │   └── tests
    │       ├── DatabaseConnectionTest
    │       ├── HomePageTest
    │       └── ProjectPageTest
    │
    └── resources
        ├── test-suites
        │   ├── api-suite.xml
        │   ├── cross-browser-suite.xml
        │   ├── regression-suite.xml
        │   └── smoke-suite.xml
        └── environment.properties
```

## Test Suites

The framework uses TestNG XML suites to support different test execution strategies:

- **Smoke Suite** — runs a focused set of critical UI tests
- **Regression Suite** — runs the complete UI regression suite
- **Cross-Browser Suite** — runs the UI tests across Chrome, Firefox, and Edge
- **API Suite** — runs REST Assured API tests independently from UI tests

## Running the Tests

### Prerequisites

Before running the tests, make sure the following are installed and available:

- Java 25
- Maven
- Google Chrome, Mozilla Firefox, and/or Microsoft Edge
- PostgreSQL for database-related tests
- The portfolio web application running locally on `http://localhost:3000`

### Run the UI Regression Suite

The regression suite is configured as the default Maven test suite:

```bash
mvn clean test
```

### Run the Smoke Suite

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/smoke-suite.xml
```

### Run the API Suite

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/api-suite.xml
```

### Run the Cross-Browser Suite

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/cross-browser-suite.xml
```

## Configuration

The framework uses a local `config.properties` file for application and database configuration.

For security reasons, `config.properties` is excluded from version control. A configuration template is provided at:

```text
src/main/resources/config.properties.example
```

To configure the project locally:

1. Copy `config.properties.example`
2. Rename the copy to `config.properties`
3. Update the database credentials for your local PostgreSQL environment

Example:

```properties
baseUrl=http://localhost:3000
browser=chrome

dbUrl=jdbc:postgresql://localhost:5432/qa_portfolio
dbUser=postgres
dbPassword=your_database_password
```

Do not commit real database credentials to version control.

## Allure Reporting

The framework integrates Allure for test execution reporting.

Allure reports provide:

- Test execution status and duration
- TestNG suite results
- API features and stories
- Environment information
- Failure details
- Screenshots for failed UI tests

After running the tests, generate and open the Allure report with:

```bash
allure serve allure-results
```

The `allure-results` directory is generated locally and excluded from version control.

## Key Testing Scenarios

The framework covers practical quality scenarios, including:

- Navigation and content validation across the portfolio web application
- Contact form submission through the UI
- Contact API positive and negative validation
- Required field validation for missing, empty, and whitespace-only values
- Email format validation using data-driven tests
- PostgreSQL verification after successful UI and API submissions
- Verification that rejected API requests are not persisted in the database
- Self-contained database integration testing with automatic test data cleanup
- Cross-browser UI testing across Chrome, Firefox, and Edge

## Defects Found by Automation

Automated API testing identified validation defects in the contact endpoint during framework development.

### Whitespace Validation

Negative API tests revealed that whitespace-only values could pass required-field validation and be persisted in PostgreSQL.

The backend validation was updated to reject missing, empty, and whitespace-only values. Regression tests were then used to verify the fix.

### Email Format Validation

API automation also identified that incomplete email addresses such as `test@` were accepted because the original validation only checked for the presence of the `@` character.

The validation logic was improved, and additional data-driven test cases were added for invalid email formats.

Database assertions verify that rejected requests are not persisted.

## CI/CD

The project uses GitHub Actions for Continuous Integration.

The workflow is triggered automatically on pushes and pull requests to the `main` branch.

The current CI workflow:

1. Runs on a GitHub-hosted Ubuntu runner
2. Starts a PostgreSQL 16 service container
3. Sets up Java 25 with Maven dependency caching
4. Creates the test configuration from `config.properties.example`
5. Initializes the required PostgreSQL database schema
6. Performs a Maven build and compile check

```text
Push / Pull Request
        ↓
GitHub Actions
        ↓
Ubuntu Runner
        ↓
Java 25
        ↓
PostgreSQL 16
        ↓
Test Configuration
        ↓
Database Schema
        ↓
Maven Build
        ↓
Build Verification
```

The full Selenium regression suite currently runs locally because the application under test is hosted locally.

A future CI improvement would be to start the application on the GitHub runner and execute the full Selenium test suite as part of the pipeline.