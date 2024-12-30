package com.baseClass;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.utility.PropertyFile;
import com.utility.ReadConfig;
import com.vigiEye.pom.CommonDataPage;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.io.File;

public class BaseClass {
	public static WebDriver driver;
	public static Logger logger;
	private PropertyFile pf; 
	CommonDataPage cdp;
	public String baseUrl;
	public String userName;
	public String password;
	public String userNameAt;
	public String passwordAt;
	public String userNameEt;
	public String passwordEt;
	public String userNameEe;
	public String passwordEe;
	public String zoneName;
	public String circleName;
	public String divisionName;
	public String fromDate;
	public String uptoDate;
	public BaseClass() {
		try {
			pf = new PropertyFile();

			initializeProperties(); 
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize PropertyFile", e);
		}
	}

	// Method to initialize all properties
	private void initializeProperties() {
		pf.baseClassDetails();
		baseUrl = pf.getProperty("baseUrl");
		userName = pf.getProperty("userName");
		password = pf.getProperty("password");
		userNameAt = pf.getProperty("userNameAt");
		passwordAt = pf.getProperty("passwordAt");
		userNameEt = pf.getProperty("userNameEt");
		passwordEt = pf.getProperty("passwordEt");
		userNameEe = pf.getProperty("userNameEe");
		passwordEe = pf.getProperty("passwordEe");
		zoneName = pf.getProperty("zoneName");
		circleName = pf.getProperty("circleName");
		divisionName = pf.getProperty("divisionName");
		fromDate = pf.getProperty("fromDate");
		uptoDate = pf.getProperty("uptoDate");
	}



	//@Parameters("browser")
	@BeforeMethod
	public void setup()throws IOException {
		try {
			// Setting up ChromeDriver using WebDriverManager
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

			// Maximize window and set default timeouts
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			// Initialize logger
			logger = Logger.getLogger("Vigilance");
			PropertyConfigurator.configure("Log4j.properties");
			logger.info("WebDriver and Logger initialized successfully");

		} catch (Exception e) {
			// Log any errors during setup
			if (logger != null) {
				logger.error("Error during WebDriver setup", e);
			} else {
				System.err.println("Error during WebDriver setup: " + e.getMessage());
			}
			throw new RuntimeException(e);
		}
	}


	public void captureScreen(WebDriver driver, String tname) throws IOException {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + timestamp + ".png");
		FileUtils.copyFile(source, target);
		logger.info("Screenshot taken");
	}

	// Capture screenshot in case of failure in test

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			logger.info("Browser closed and WebDriver terminated");
		}
	}


}