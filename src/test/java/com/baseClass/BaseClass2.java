package com.baseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;

public class BaseClass2 {
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
    public BaseClass2() {
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

    @BeforeMethod
    public void setUp() {
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

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            if (logger != null) {
                logger.info("Browser closed and WebDriver terminated");
            }
        }
    }
}
