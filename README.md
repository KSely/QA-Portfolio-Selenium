# QA Portfolio - Selenium Automation Framework

A QA automation framework built with Java and Selenium WebDriver for testing a full-stack portfolio web application.

The project demonstrates automated testing across multiple application layers, including UI, API, database validation, cross-browser testing, test reporting, and CI/CD integration.

The Application Under Test (AUT) is maintained in a separate repository:

[QA-Automation-Portfolio](https://github.com/KSely/QA-Automation-Portfolio)

---

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

---

## Project Overview

This repository contains an automated testing framework created for a full-stack QA portfolio web application.

The framework includes:

- **UI Testing** — automated browser testing using Selenium WebDriver
- **API Testing** — REST API validation using REST Assured
- **Database Testing** — PostgreSQL validation using JDBC
- **Cross-Browser Testing** — UI test configuration for Chrome, Firefox, and Edge
- **Smoke Testing** — focused validation of critical application functionality
- **Regression Testing** — broader UI functional coverage
- **Test Reporting** — Allure reporting with test results, environment information, and failure screenshots
- **CI/CD** — GitHub Actions execution in an isolated test environment

The framework follows the Page Object Model (POM) design pattern and uses reusable components for browser management, configuration, API requests, and database operations.

---

## Test Coverage

The framework currently includes separate UI, API, and database test suites.

### UI Regression

- **25 UI regression tests**
- Covers the Home and Project pages
- Includes navigation, page content, and application behavior validation
- Default `mvn clean test` execution runs the configured regression suite

Latest local regression execution:

```text
Tests run: 25
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

### API Testing

- **15 API test executions**
- Application health validation
- Successful contact submission
- Required-field validation
- Empty-value validation
- Whitespace-only validation
- Invalid email validation
- Positive and negative response validation
- Database persistence and non-persistence verification where applicable

### Database Testing

Database integration tests cover:

- PostgreSQL connectivity
- Direct JDBC queries
- Contact message verification
- Test data creation
- Persistence validation
- Automatic cleanup

### Cross-Browser Testing

The UI framework is configured to support:

- Chrome
- Firefox
- Edge

The cross-browser suite is separate from the default `mvn clean test` regression execution.

This distinction is important:

```text
Default regression run
    ↓
25 UI test executions in the configured browser

Cross-browser suite
    ↓
Same UI coverage executed through multiple browser configurations
```

Therefore, the number shown by Maven after `mvn clean test` should not be compared directly with Playwright's configured multi-browser execution count.

---

## Project Structure

```text
src
├── main
│   ├── java/com/qaautomation/portfolio
│   │   ├── config        # Configuration management
│   │   ├── database      # PostgreSQL database utilities
│   │   ├── driver        # WebDriver creation and browser management
│   │   └── pages         # Page Object Model classes
│   │
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
    │   │
    │   ├── base
    │   │   └── BaseTest  # Common Selenium setup and teardown
    │   │
    │   └── tests
    │       ├── DatabaseConnectionTest
    │       ├── HomePageTest
    │       └── ProjectPageTest
    │
    └── resources
        ├── test-suites
        │   ├── api-suite.xml
        │   ├── cross-browser-suite.xml
        │   ├── database-suite.xml
        │   ├── regression-suite.xml
        │   └── smoke-suite.xml
        │
        └── environment.properties
```

---

## Test Suites

The framework uses TestNG XML suites to support different execution strategies.

### Smoke Suite

Runs a focused set of critical UI tests.

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/smoke-suite.xml
```

### Regression Suite

Runs the default UI regression suite.

```bash
mvn clean test
```

The current default local regression execution contains:

```text
25 UI tests
```

### API Suite

Runs REST Assured API tests independently from UI tests.

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/api-suite.xml
```

### Database Suite

Runs PostgreSQL integration and database validation tests.

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/database-suite.xml
```

### Cross-Browser Suite

Runs the UI test coverage using Chrome, Firefox, and Edge configurations.

```bash
mvn clean test -Dsuitexmlfile=src/test/resources/test-suites/cross-browser-suite.xml
```

The cross-browser suite is intentionally separate from the default regression command.

---

## Running the Tests

### Prerequisites

Before running the tests, make sure the following are installed and available:

- Java 25
- Maven
- Google Chrome, Mozilla Firefox, and/or Microsoft Edge
- PostgreSQL service running for database-related tests
- The portfolio web application running locally on:

```text
http://localhost:3000
```

PostgreSQL can run as a Windows service in the background. pgAdmin does not need to be open.

To verify the PostgreSQL Windows service:

```powershell
Get-Service *postgres*
```

A running service should display a status similar to:

```text
Running
```

---

## Run the UI Regression Suite

The regression suite is configured as the default Maven test suite:

```bash
mvn clean test
```

Example successful execution:

```text
Tests run: 25
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

## Configuration

The framework uses a local `config.properties` file for application and database configuration.

For security reasons, `config.properties` is excluded from version control.

A configuration template is provided at:

```text
src/main/resources/config.properties.example
```

To configure the project locally:

1. Copy `config.properties.example`
2. Rename the copy to `config.properties`
3. Update the application and database settings for the local environment

Example:

```properties
baseUrl=http://localhost:3000
browser=chrome

dbUrl=jdbc:postgresql://localhost:5432/qa_portfolio
dbUser=postgres
dbPassword=your_database_password
```

Do not commit real database credentials to version control.

---

## Browser Management

WebDriver creation is centralized in the framework's driver layer.

The framework supports:

- Chrome
- Firefox
- Edge

Browser selection is controlled through configuration and TestNG suite settings.

Selenium Manager can resolve browser drivers automatically for supported local browser installations.

---

## Page Object Model

The Selenium UI framework uses the Page Object Model to separate reusable page interactions from test logic.

```text
Tests
  ↓
Page Objects
  ↓
Selenium WebDriver
  ↓
Browser
  ↓
Application Under Test
```

Page objects centralize:

- Element locators
- Navigation
- User interactions
- Reusable page behavior

This improves readability, maintainability, and locator reuse.

---

## UI Testing

The current regression suite contains 25 UI tests.

Coverage includes:

- Home page availability
- Page title validation
- Main content validation
- Navigation
- Project Details page
- Project navigation links
- Automation-related project sections
- Contact-related UI workflows
- Smoke and regression scenarios

The UI framework also supports cross-browser execution using Chrome, Firefox, and Edge.

---

## API Testing

REST Assured is used for direct backend testing.

### `GET /api/status`

Status API tests validate:

- Successful response
- HTTP status
- JSON response body
- Backend status
- Response message

Example expected response:

```json
{
  "status": "ok",
  "message": "QA Automation Portfolio backend is running"
}
```

### `POST /contact`

Contact API testing covers:

- Successful submission
- Required fields
- Empty values
- Whitespace-only values
- Invalid email formats
- HTTP response status
- JSON response body
- Accepted-data persistence
- Rejected-data non-persistence

Data-driven testing is used for validation scenarios where appropriate.

---

## Database Testing

The framework connects directly to PostgreSQL using JDBC.

Database validation includes:

- PostgreSQL connection verification
- Direct SQL queries
- Contact message persistence
- Accepted-data verification
- Rejected-data non-persistence
- Controlled test data
- Cleanup after execution

This allows the framework to verify behavior beyond the UI or API response.

Example validation flow:

```text
UI or API Request
       ↓
Express Backend
       ↓
PostgreSQL
       ↓
JDBC Verification
```

---

## Test Data Management

Tests that create application or database data use controlled test values.

Where applicable, generated test data is removed after verification.

This helps:

- Keep tests repeatable
- Prevent data collisions
- Reduce leftover test records
- Keep executions independent

---

## Defects Found by Automation

Automated API testing identified validation defects in the contact endpoint during framework development.

### Whitespace Validation

Negative API tests revealed that whitespace-only values could pass required-field validation and be persisted in PostgreSQL.

The backend validation was updated to reject:

- Missing values
- Empty values
- Whitespace-only values

Regression tests were then used to verify the corrected behavior.

### Email Format Validation

API automation also identified that incomplete email addresses such as:

```text
test@
```

were accepted because the original validation only checked for the presence of the `@` character.

The validation logic was improved, and additional data-driven test cases were added for invalid email formats.

Database assertions verify that rejected requests are not persisted.

### Additional Application Defects

Additional application defects, including DEF-001 and DEF-002, were investigated and verified through the Playwright regression framework.

Their full lifecycle is documented in the Application Under Test repository.

[QA Documentation](https://github.com/KSely/QA-Automation-Portfolio/tree/main/docs/qa)

This Selenium repository does not claim automated DEF-001 or DEF-002 regression coverage unless corresponding Selenium tests are added in the future.

---

## Allure Reporting

The framework integrates Allure for test execution reporting.

Allure reports provide:

- Test execution status
- Test duration
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

---

## Failure Diagnostics

The framework captures diagnostic information to help investigate failed automated tests.

For UI failures, screenshots are attached to Allure results where configured.

Maven Surefire results are generated under:

```text
target/surefire-reports/
```

Allure raw results are generated under:

```text
allure-results/
```

These generated test artifacts are excluded from normal source commits.

---

## CI/CD

The project uses GitHub Actions for Continuous Integration.

The workflow is triggered automatically on:

- Pushes to the `main` branch
- Pull requests targeting the `main` branch

The CI pipeline:

1. Checks out the Selenium automation repository
2. Checks out the portfolio application under test
3. Starts a PostgreSQL 16 service container
4. Sets up Node.js and installs application dependencies
5. Sets up Java 25 with Maven dependency caching
6. Creates the application and test configuration from example files
7. Initializes the PostgreSQL database schema
8. Starts the portfolio application on the GitHub Actions runner
9. Verifies that the application is available through `/api/status`
10. Builds the Selenium automation project
11. Runs the API test suite
12. Runs the database test suite
13. Runs the headless UI smoke test suite
14. Runs the headless UI regression test suite
15. Uploads Allure test results as a GitHub Actions artifact when the workflow fails

Workflow:

```text
Push / Pull Request
        ↓
GitHub Actions
        ↓
Ubuntu Runner
        ↓
PostgreSQL 16
        ↓
Start Application Under Test
        ↓
Application Health Check
        ↓
Maven Build
        ↓
API Tests
        ↓
Database Tests
        ↓
UI Smoke Tests
        ↓
UI Regression Tests
        ↓
Allure Results on Failure
```

UI tests run headlessly in CI using a fixed desktop browser window size to provide consistent behavior across GitHub-hosted runners.

This setup allows the automated test workflow to run independently on GitHub infrastructure without requiring the application or PostgreSQL database to be running on a local development machine.

---

## Related Repositories

### Application Under Test

Full-stack Node.js / Express / PostgreSQL application tested by this framework.

[QA-Automation-Portfolio](https://github.com/KSely/QA-Automation-Portfolio)

### Playwright Automation

Independent JavaScript Playwright automation framework for the same AUT, including UI, API, database, cross-browser, and defect regression coverage.

[QA-Portfolio-Playwright](https://github.com/KSely/QA-Portfolio-Playwright)

### JMeter Performance Testing

Independent Apache JMeter performance testing project for the same AUT.

[QA-Portfolio-Performance](https://github.com/KSely/QA-Portfolio-Performance)

---

## Framework Highlights

This project demonstrates practical experience with:

- Java test automation
- Selenium WebDriver
- TestNG
- Page Object Model
- REST Assured
- API testing
- Positive and negative testing
- Data-driven validation
- PostgreSQL integration
- JDBC database verification
- UI-to-database validation
- Cross-browser test configuration
- Chrome, Firefox, and Edge
- Smoke testing
- Regression testing
- Test data cleanup
- Allure reporting
- Failure screenshots
- Maven
- Git
- GitHub
- GitHub Actions CI/CD

---

## Purpose

This project was created as a practical QA automation portfolio demonstrating how Selenium, REST Assured, TestNG, JDBC, and supporting tools can be used to test a full-stack application across the UI, API, and database layers.

The framework focuses on:

- Maintainable test architecture
- Reusable components
- UI automation
- API validation
- Database verification
- Cross-browser configuration
- Test independence
- Test reporting
- CI/CD integration

The repository is publicly available for review by potential employers and recruiters.

No open-source license is currently provided for this repository.