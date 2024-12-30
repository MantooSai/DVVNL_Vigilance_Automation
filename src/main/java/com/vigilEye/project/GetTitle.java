package com.vigilEye.project;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class GetTitle {
	public String baseUrl="http://45.114.143.206:9080/testing/shalu/latest/mri_analysis/";
	public String userName="Test@sai";
	public String password="Test@123";
	public static WebDriver driver;
	public static Logger logger;

  static {
	  System.setProperty("webdriver.chrome.driver","./driver/chromedriver.exe");
  }
@Test
	public void setUp() throws InterruptedException {
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
		driver=new ChromeDriver();
	     logger = Logger.getLogger("Vigilance");
	     PropertyConfigurator.configure("Log4j.properties");
	     driver.get(baseUrl);
	     driver.findElement(By.name("userid")).sendKeys(userName);
	     driver.findElement(By.name("password")).sendKeys(password);
	     driver.findElement(By.id("button")).click();
	     Thread.sleep(3000);
	     String title = driver.getTitle();
	     System.out.println(title);
	
	}

	}

