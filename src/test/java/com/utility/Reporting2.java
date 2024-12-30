package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reporting2 extends TestListenerAdapter {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
        String repName = "Test-Report-" + timeStamp + ".html";

        // Specify location of the report
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport/" + repName);
        htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host name", "localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Mantoo kumar");

        // Report configuration
        htmlReporter.config().setDocumentTitle("Vigilance Test Project"); // Title of the report
        htmlReporter.config().setReportName("Vigilance Functional Test Automation Report"); // Name of the report
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); // Location of the chart
        htmlReporter.config().setTheme(Theme.DARK);
    }

    public void onTestSuccess(ITestResult tr) {
        logger = extent.createTest(tr.getName()); // Create a new entry in the report
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // Log test success
    }

    public void onTestFailure(ITestResult tr) {

        logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // Log test failure

        // Add timestamp to screenshot file name to ensure uniqueness
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator + tr.getName() + "_" + timeStamp + ".png";

        File f = new File(screenshotPath);

        if (f.exists()) {
            try {
                logger.fail("Screenshot is below:" + logger.addScreenCaptureFromPath(screenshotPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onTestSkipped(ITestResult tr) {
        logger = extent.createTest(tr.getName()); // Create a new entry in the report
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE)); // Log skipped test
    }

    public void onFinish(ITestContext testContext) {
        extent.flush(); // Write the report to file
    }
}
