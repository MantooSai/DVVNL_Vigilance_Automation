package com.practice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ParaBankAutomation {
	public static WebDriver driver;
    public static void main(String[] args) throws InterruptedException {
        // Set the path of the ChromeDriver executable
    	 WebDriverManager.chromedriver().setup();
         driver = new ChromeDriver();
        // Open the website
        driver.get("https://parabank.parasoft.com/parabank/admin.htm");

        // Maximize the window and set implicit wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);

        // Register a new user
        driver.findElement(By.xpath("//a[normalize-space()='Register']")).click();
        driver.findElement(By.xpath("//input[@id='customer.firstName']")).sendKeys("Abhijeet");
        driver.findElement(By.xpath("//input[@id='customer.lastName']")).sendKeys("t");
        driver.findElement(By.xpath("//input[@id='customer.address.street']")).sendKeys("panchi");
        driver.findElement(By.xpath("//input[@name='customer.address.city']")).sendKeys("Meerut");
        driver.findElement(By.id("customer.address.state")).sendKeys("uttarpardesh");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("245206");
        driver.findElement(By.xpath("//input[@name='customer.phoneNumber']")).sendKeys("5465444444");
        driver.findElement(By.xpath("//input[@name='customer.ssn']")).sendKeys("popoppo");
        driver.findElement(By.xpath("//input[@name='customer.username']")).sendKeys("a545687-*--)))");
        driver.findElement(By.id("customer.password")).sendKeys("123456");
        driver.findElement(By.id("repeatedPassword")).sendKeys("123456");

        // Click on the Register button to submit the form
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        // Open a new account
        driver.findElement(By.xpath("//a[@href='openaccount.htm']")).click();
        driver.findElement(By.xpath("(//select[@id='type']//option)[2]")).click();
        driver.findElement(By.xpath("//input[@value='Open New Account']")).click();

        // Go to account overview
        driver.findElement(By.xpath("//a[@href='overview.htm']")).click();

        // Access account details and perform a fund transfer
        driver.findElement(By.xpath("//a[@href='activity.htm?id=19116']")).click();  // Account activity link
        driver.findElement(By.xpath("//a[@href='transfer.htm']")).click();
        driver.findElement(By.xpath("//input[@id='amount']")).sendKeys("1000");
        driver.findElement(By.xpath("//select[@id='fromAccountId']//option[1]")).click();
        driver.findElement(By.xpath("//select[@id='toAccountId']//option[1]")).click();
        driver.findElement(By.xpath("//input[@value='Transfer']")).click();

        // Bill payment section
        driver.findElement(By.xpath("//a[@href='billpay.htm']")).click();
        driver.findElement(By.xpath("//input[@name='payee.name']")).sendKeys("opopop");
        driver.findElement(By.xpath("//input[@name='payee.address.street']")).sendKeys("pan");
        driver.findElement(By.xpath("//input[@name='payee.address.city']")).sendKeys("meerut");

        // Wait for some time before closing the browser (to observe the actions)
        Thread.sleep(2000);

        // Close the browser
        driver.quit();
    }
}
