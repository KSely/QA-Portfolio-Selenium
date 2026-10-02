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
- REST Assured JSON Schema Validator 5.5.6
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
- **Cross-Browser Testing** — UI test execution in Chrome, Firefox, and Edge
- **Smoke Testing** — focused validation of critical application functionality
- **Regression Testing** — broader UI functional coverage
- **Test Reporting** — Allure reporting with test results, environment information, and failure screenshots
- **CI/CD** — GitHub Actions execution in an isolated test environment

The framework follows the Page Object Model (POM) design pattern and uses reusable components for browser management, configuration, API requests, and database operations.

---

## Test Coverage

The framework currently includes separate UI, API, database, smoke, regression, and cross-browser test suites.

### UI Regression

The default regression suite contains:

- **25 UI regression tests**
- Home page coverage
- Project page coverage
- Navigation validation
- Content validation
- Application behavior validation

The default local regression command is:

```bash
mvn clean test
```

Latest verified local regression execution:

```text
Tests run: 25
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

### API Testing

The API suite contains:

- **15 API test executions**
- Application health validation
- Successful contact submission
- Required-field validation
- Empty-value validation
- Whitespace-only validation
- Invalid email validation
- Positive and negative response validation
- JSON Schema-based API contract validation
- Database persistence and non-persistence verification where applicable

Latest verified API suite execution:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

### Database Testing

The database suite contains:

- **2 database test executions**
- PostgreSQL connectivity validation
- Direct JDBC queries
- Contact message verification
- Stored data validation
- Controlled test data
- Automatic cleanup

Latest verified database suite execution:

```text
Tests run: 2
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

### Cross-Browser Testing

The Selenium UI framework supports:

- Chrome
- Firefox
- Edge

The dedicated cross-browser suite executes the same 25 UI regression tests across all three supported browsers.

```text
25 UI tests × 3 browsers = 75 executions
```

Latest verified cross-browser execution:

```text
Tests run: 75
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The cross-browser suite is separate from the default `mvn clean test` regression execution.

This distinction is important:

```text
Default regression execution
        ↓
25 UI test executions
        ↓
Configured browser

Cross-browser execution
        ↓
25 UI tests
        ↓
Chrome + Firefox + Edge
        ↓
75 total executions
```

Therefore, the Maven result from the default regression suite should not be compared directly with a multi-browser execution count.

---

## Verified Test Execution Summary

The following test executions have been verified locally:

| Test Suite | Test Executions | Result |
|---|---:|---|
| UI Regression | 25 | Passed |
| API | 15 | Passed |
| Database | 2 | Passed |
| Cross-Browser | 75 | Passed |

The cross-browser count represents repeated execution of the 25 UI regression tests across Chrome, Firefox, and Edge.

It should not be interpreted as 75 unique UI test cases.

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
        ├── schemas
        │   ├── contact-response.schema.json
        │   └── status-response.schema.json
        │
        └── environment.properties
```

---

## Test Suites

The framework uses TestNG XML suites to support different test execution strategies.

### Smoke Suite

Runs a focused set of critical UI tests.

```bash
mvn clean test "-Dsuitexmlfile=src/test/resources/test-suites/smoke-suite.xml"
```

---

### Regression Suite

Runs the default UI regression suite.

```bash
mvn clean test
```

Latest verified execution:

```text
25 passed
```

---

### API Suite

Runs REST Assured API tests independently from UI tests.

```bash
mvn clean test "-Dsuitexmlfile=src/test/resources/test-suites/api-suite.xml"
```

Latest verified execution:

```text
15 passed
```

---

### Database Suite

Runs PostgreSQL integration and database validation tests.

```bash
mvn clean test "-Dsuitexmlfile=src/test/resources/test-suites/database-suite.xml"
```

Latest verified execution:

```text
2 passed
```

---

### Cross-Browser Suite

Runs the UI regression coverage using Chrome, Firefox, and Edge.

```bash
mvn clean test "-Dsuitexmlfile=src/test/resources/test-suites/cross-browser-suite.xml"
```

Latest verified execution:

```text
75 passed
```

The 75 executions represent:

```text
25 UI tests × 3 browsers
```

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

PostgreSQL can run as a Windows service in the background.

pgAdmin does not need to be open for the Selenium tests to connect to PostgreSQL.

To verify the PostgreSQL Windows service:

```powershell
Get-Service *postgres*
```

A running service should display:

```text
Running
```

---

## Start the Application Under Test

Before running local Selenium tests, start the portfolio application from the AUT repository:

```bash
npm start
```

The application should be available at:

```text
http://localhost:3000
```

The Selenium automation framework connects to the running application.

The application does not start PostgreSQL itself. PostgreSQL runs independently as a database service.

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

The current default regression suite contains:

```text
25 UI tests
```

Coverage includes:

- Home page availability
- Page title validation
- Main content validation
- Navigation
- Project Details page
- Project navigation links
- Automation-related project sections
- Contact-related UI workflows
- Smoke scenarios
- Regression scenarios

The same UI regression coverage can also be executed across Chrome, Firefox, and Edge using the dedicated cross-browser suite.

---

## API Testing

REST Assured is used for direct backend testing. The REST Assured JSON Schema Validator adds JSON Schema Validation for API contract validation while preserving the existing functional assertions.

The current API suite contains:

```text
15 test executions
```

The API tests validate:

- HTTP status codes
- Exact response values and messages
- Database persistence or non-persistence where applicable
- JSON response structure
- Required properties
- Property data types

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

### JSON Schema Validation

REST Assured uses `matchesJsonSchemaInClasspath(...)` to validate response bodies against JSON Schema files loaded from `src/test/resources/schemas/`. Schema validation adds response-structure and data-type checks; existing REST Assured `body(...)` assertions continue to verify exact business values and messages.

The implemented schemas are:

- `status-response.schema.json`
  - Validates a root object with required `status` and `message` properties.
  - Requires both properties to be strings.
- `contact-response.schema.json`
  - Validates a root object with required `success` and `message` properties.
  - Requires `success` to be a boolean and `message` to be a string.

Schema validation currently covers these existing API scenarios:

- Successful `GET /api/status`
- Successful `POST /contact`
- Empty, missing, and whitespace-only required-field rejections
- Invalid-email rejections

No schema-only tests were added. JSON Schema Validation is an additional assertion dimension, and the API suite remains at **15 test executions**.

---

## Database Testing

The framework connects directly to PostgreSQL using JDBC.

The dedicated database suite currently contains:

```text
2 test executions
```

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

---

### Email Format Validation

API automation also identified that incomplete email addresses such as:

```text
test@
```

were accepted because the original validation only checked for the presence of the `@` character.

The validation logic was improved, and additional data-driven test cases were added for invalid email formats.

Database assertions verify that rejected requests are not persisted.

---

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
11. Runs the API test suite, including REST Assured JSON Schema validation
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

JSON Schema Validation runs automatically within the existing API test stage. No separate schema-validation workflow or test stage was created. The existing GitHub Actions workflow completed successfully with these schema assertions included.

This setup allows the automated test workflow to run independently on GitHub infrastructure without requiring the application or PostgreSQL database to be running on a local development machine.

---

## Selenium and Playwright Execution Counts

The Selenium and Playwright repositories use different execution strategies.

### Selenium

The default Selenium regression suite runs:

```text
25 UI tests
```

The dedicated Selenium cross-browser suite runs:

```text
25 UI tests × 3 browsers
= 75 test executions
```

Supported Selenium browsers:

```text
Chrome
Firefox
Edge
```

### Playwright

The Playwright framework has its own independently maintained test suite and browser configuration.

Because the frameworks contain different tests and execute them using different browser strategies, their final execution counts should not be compared as if they represent the same number of unique test cases.

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
- JSON Schema Validation
- REST Assured JSON Schema Validator
- JSON Schema-based API contract validation
- Positive and negative testing
- Data-driven validation
- PostgreSQL integration
- JDBC database verification
- UI-to-database validation
- Cross-browser testing
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
- Cross-browser testing
- Test independence
- Test reporting
- CI/CD integration

The repository is publicly available for review by potential employers and recruiters.

No open-source license is currently provided for this repository.
