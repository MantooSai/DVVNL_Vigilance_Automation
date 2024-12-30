package com.utility;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting implements ITestListener {
    private static final Logger logger = Logger.getLogger(Reporting.class);
    private ExtentReports extent;
    private ExtentTest test;
    private WebDriver driver;

    @Override
    public void onStart(ITestContext context) {
        String reportName = "Test-Report-" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport" + reportName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Project Name", "Dvvnl10 Vigilance");
        extent.setSystemInfo("Tester", "Mantoo Kumar");
        extent.setSystemInfo("Environment", "QA");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test Failed: " + result.getMethod().getMethodName());
        Object testClass = result.getInstance();
        driver = ((com.baseClass.BaseClass) testClass).driver;

        if (driver != null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String screenshotName = result.getMethod().getMethodName() + "_" + timestamp + ".png";

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(System.getProperty("user.dir") + "/Screenshots/" + screenshotName);

            try {
                FileUtils.copyFile(source, destination);
                test.addScreenCaptureFromPath(destination.getAbsolutePath(), "Screenshot on Failure");
                logger.info("Screenshot captured for failed test case: " + screenshotName);
            } catch (IOException e) {
                logger.error("Failed to save screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        logger.info("Test execution completed. Report generated.");
    }
}
