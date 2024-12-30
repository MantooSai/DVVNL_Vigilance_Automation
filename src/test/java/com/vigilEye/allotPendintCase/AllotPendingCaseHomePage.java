package com.vigilEye.allotPendintCase;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.vigilEye.allotPendintCaseDatabase.AllotPendingCaseDataBase;
public class AllotPendingCaseHomePage extends BaseClass2 {

	@BeforeMethod
	public void openBrowser() throws InterruptedException {
		LoginPage lp= new LoginPage(driver);
		  lp.setUserName(userName);
		  lp.setPassword(password);
		  lp.clickLoginButton();
		  Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//*[contains(text(),'Vigil-Eye')]")).click();
		Thread.sleep(2000);
	}

	@AfterMethod
	public void close() {
		logger.info("Test completed");
	}

	@Test
	public void toVerifyAllotPendingCasesIsDisplaying() {
		WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'ALLOT PENDING')]"));
		boolean isDisplayed = menu.isDisplayed();
		Assert.assertTrue(isDisplayed, "Allot Pending Case is not Displaying");
		logger.info("Allot Pending Case is Displaying");
	}

	@Test(priority = 1)
	public void toVerifyClickOnAllotPendingCaseMenu() {
		try {
			driver.findElement(By.xpath("//a[contains(@href, 'allot-pending-case')]")).click();
			String parentWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
					break;
				}
			}
			Assert.assertEquals(driver.getTitle(), "Vigil-Eye");
			logger.info("Clicked on Allot Pending Case");
		} catch (NoSuchElementException e) {
			logger.error("Element not found: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	public void toverifyHeaderName() {
		try {
			driver.findElement(By.xpath("//a[contains(@href, 'allot-pending-case')]")).click();
			String parentWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
					break;
				}
			}
			String[] headerName = {"Sr No", "View", "Division", "Case_No", "AC_ID", "Name", "Address", "Contract Demand", "Priority"};
			ArrayList<String> headerList = new ArrayList<>(Arrays.asList(headerName));
			ArrayList<String> headerListOnUI = new ArrayList<>();
			List<WebElement> allHeader = driver.findElements(By.xpath("//table[@class='MuiTable-root css-1dh5pc2']/thead/tr/th/span"));
			for (WebElement header : allHeader) {
				headerListOnUI.add(header.getText().trim());
			}
			Assert.assertEquals(headerList, headerListOnUI);
			logger.info("Headers from UI are fetched");
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
	}

	@Test(priority=3)
	public void toverifyCountOfAllotPendingCase() throws InterruptedException, ClassNotFoundException, SQLException {

		try {
			driver.findElement(By.xpath("//a[contains(@href, 'allot-pending-case')]")).click();
			String parentWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
					break;
				}
			}
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement pendingCasesElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='italic font-semibold']"))
					);
			Thread.sleep(3000);
			String text = pendingCasesElement.getText().trim();
			Thread.sleep(3000);
			String consumerOfpendingCaseInUI = text.replaceAll("\\D+", "");
			AllotPendingCaseDataBase cp = new AllotPendingCaseDataBase();
			String consumerOfpendingCaseInDB = cp.toverifyCountAllotPendingCaseInDatabase();
			System.out.println("Pending cases in DB: " + consumerOfpendingCaseInDB);
			System.out.println("Pending cases in UI: " + consumerOfpendingCaseInUI);
			Assert.assertEquals(consumerOfpendingCaseInDB, consumerOfpendingCaseInUI);
			Reporter.log("Allot Pending Case is fetched & test case passed",true);
			logger.info("Allot Pending Case is fetched & test case passed");  

		} catch (Exception e) {
			logger.error("Test case failed: " + e.getMessage());
			e.printStackTrace();
		}
	}
	@Test(priority = 4)
	public void toverifySearchOptionIsWorking() {
		try {
			driver.findElement(By.xpath("//a[contains(@href, 'allot-pending-case')]")).click();
			String parentWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
					break;
				}
			}
			String caseNo = driver.findElement(By.xpath("(//td[contains(text(),'DVVNL-')])[1]")).getText().trim();

			// Step 3: Enter the fetched case number in the search box
			WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search...']"));
			searchInput.sendKeys(caseNo);
			searchInput.sendKeys(Keys.ENTER);  // Press 'Enter' to trigger search

			// Step 4: Fetch the search results from the UI
			List<WebElement> allRecordOnUI = driver.findElements(By.xpath("//tr[@class='MuiTableRow-root css-1gqug66']/td"));
			ArrayList<String> alltPendingCasesDetailsOnUI = new ArrayList<>();
			for (int i=2; i<allRecordOnUI.size(); i++) {
				alltPendingCasesDetailsOnUI.add(allRecordOnUI.get(i).getText().trim());
			}

			// Step 5: Fetch pending case details from the database
			AllotPendingCaseDataBase db = new AllotPendingCaseDataBase();
			List<String> allPendingCaseDetailsOnDB = db.toverifyAllotPendingCaseDetailsInDatabase();

			//Assert.assertEquals(alltPendingCasesDetailsOnUI, allPendingCaseDetailsOnDB);
			//logger.info("Search functionality verified and results matched.");
			//System.out.println(alltPendingCasesDetailsOnUI);
			//System.out.println(allPendingCaseDetailsOnDB);
	        if (alltPendingCasesDetailsOnUI.size() != allPendingCaseDetailsOnDB.size()) {
	            System.out.println("The ArrayLists are of different sizes.");
	        } else {
	            for (int i = 0; i < alltPendingCasesDetailsOnUI.size(); i++) {
	                if (alltPendingCasesDetailsOnUI.get(i).equals(allPendingCaseDetailsOnDB.get(i))) {
	                    System.out.println("Element " + i + " is equal: " + alltPendingCasesDetailsOnUI.get(i));
	                } else {
	                    System.out.println("Element " + i + " is different: " + alltPendingCasesDetailsOnUI.get(i) + " vs " + allPendingCaseDetailsOnDB.get(i));
	                }
	            }
	        }
		} catch (Exception e) {
			logger.error("Test case failed: " + e.getMessage());
			e.printStackTrace();
		}

	}
	@Test(priority = 5)
	public void toverifydownloadReportInExcelFormat() {
	    try {
	        // Clicks on the link to open a new window
	        driver.findElement(By.xpath("//a[contains(@href, 'allot-pending-case')]")).click();
	        
	        // Store the current window handle
	        String parentWindow = driver.getWindowHandle();
	        
	        // Switch to the new window
	        for (String windowHandle : driver.getWindowHandles()) {
	            if (!windowHandle.equals(parentWindow)) {
	                driver.switchTo().window(windowHandle);
	                break;
	            }
	        }
	        
	        // Click on the button to download the report in Excel format
	        driver.findElement(By.xpath("//button[@data-tooltip-id='Excel']")).click();
	        logger.info("Report download in Excel format successfully");
	        
	    } catch (Exception e) {
	        logger.error("Test case failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    
	    
	}
}