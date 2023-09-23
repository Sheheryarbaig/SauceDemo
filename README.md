# BDD Cucumber Framework - README -Created  BY SHEHERYAR ALI BAIG

This README file provides an overview and instructions for using the BDD Cucumber Framework for automated testing. The framework is designed for testing scenarios using Cucumber, Java, Selenium WebDriver, and automated API testing using JSON files. It follows a Behavior-Driven Development (BDD) approach.

## Prerequisites

Before running the tests, ensure you have the following installed:

1. Java Development Kit (JDK) - version 18 or Above
2. Apache Maven

## Setup Instructions

1. Clone the repository: `git clone https://github.com/Sheheryarbaig/SauceDemo.git`
2. Navigate to the project root directory: `cd SauceDemo`

## Running Tests

To run the automated tests, follow these steps:
# Adding Test Configuration in IntelliJ IDEA

To add a TestNG configuration in IntelliJ IDEA, follow these steps:

1. **Click on "Configuration":** This step may vary depending on the version of IntelliJ IDEA you are using. Typically, you can find the "Configuration" menu in the top-right corner of the window. It looks like a dropdown with a green play button.

2. **Click "Edit Configurations...":** This option allows you to manage your run configurations.

3. **Click on the "+" Sign:** This "+" sign is used to create a new run configuration.

4. **Select "TestNG":** In the list of available configurations, find and select "TestNG."

5. **Select "Suite" as the Test Kind:** When you select "TestNG," additional options will appear. Choose "Suite" as the Test Kind.

6. **Rename "Unnamed" to "Runner":** By default, the configuration may be named "Unnamed." Click on it to edit the name, and rename it to "Runner" or any other meaningful name.

7. **Click "Apply" and "OK":** Once you've configured the settings as described above, click the "Apply" button to save your changes, and then click "OK" to close the configuration dialog.

Your TestNG configuration is now set up and named "Runner." You can use this configuration to run your TestNG tests in IntelliJ IDEA. Make sure you have TestNG and your project dependencies properly configured for testing in IntelliJ IDEA before using this configuration.

## Automated UI Tests

1. Ensure you have all the necessary test data and locator files in the `src/test/Resources/Properties/TestData` and `src/test/Resources/Properties/Locators` directories, respectively.
2. Navigate to the `src/test/java/com.cucumber.stepdefinitions` directory.
3. Run the `Runner` class (Right-click and select "Run Runner" or use Maven).


## Test Data

Test data for the automated UI tests is available in the `src/test/Resources/Properties/TestData` directory. You can modify the test data files as needed for different test scenarios.

## Reporting

The framework generates Cucumber HTML reports after test execution. You can find the reports in the `target/executionReports` directory.

## Execution Video (Optional)

If you want to record videos of test executions, the framework saves them in the `src/test/Resources/ExecutionVideo` directory.


# Troubleshooting Guide

If you encounter the following errors while working on your project, here are some instructions on how to resolve them:

## Error 1: Remote Stacktrace

```
remote stacktrace: Backtrace:
	GetHandleVerifier [0x00F6A813+48355]
	(No symbol) [0x00EFC4B1]
	...
	BaseThreadInitThunk [0x75557BA9+25]
	RtlInitializeExceptionChain [0x770CB79B+107]
	RtlClearBits [0x770CB71F+191]
	at java.base/jdk.internal.reflect.DirectConstructorHandleAccessor.newInstance(DirectConstructorHandleAccessor.java:67)
```

**Solution:** This error indicates a problem with your Selenium WebDriver or ChromeDriver version. To resolve this issue, follow these steps:

1. Navigate to the `C:\Users\YOURUSERNAME\.cache\selenium\chromedriver\win32\114.0.5735.90` directory.

2. Download ChromeDriver version 117 or above from [this link](https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/117.0.5938.92/win64/chrome-win64.zip).

3. Replace the existing ChromeDriver in the directory with the downloaded one.

## Error 2: Directory "D:\SauceDemo\src\main\java\Resources\ExecutionVideo" does not exist

**Solution:** This error suggests that the specified directory does not exist. To resolve this issue, create the missing directory as follows:

1. Open your project folder.

2. Navigate to the following path: `D:\SauceDemo\src\main\java\Resources`.

3. Create a new folder named `ExecutionVideo` in the `Resources` directory.

## Error 3: The SDK is not specified for module Faremwork

**Solution:** This error occurs when the SDK is not specified for your project module. To resolve this issue, follow these steps:

1. Open your project in your development environment (e.g., IntelliJ IDEA).

2. Go to `File -> Project Structure`.

3. In the Project Structure dialog, under "Project," ensure that you have selected the appropriate SDK version (e.g., SDK 18 or above) for your project module (in this case, "Faremwork").

4. Click "Apply" and then "OK" to save your changes.

By following these instructions, you should be able to troubleshoot and resolve the specified errors in your project. If you encounter any additional issues or need further assistance, feel free to seek help from your development team or community forums.

