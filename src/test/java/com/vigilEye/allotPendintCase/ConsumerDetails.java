package com.vigilEye.allotPendintCase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.baseClass.BaseClass;
import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.vigilEye.allotPendintCaseDatabase.AllotPendingCaseDataBase;

public class ConsumerDetails extends BaseClass2 {
	@BeforeMethod
	public void openBrowser() throws InterruptedException  {
		driver.get(baseUrl);
		LoginPage lp= new LoginPage(driver);
		  lp.setUserName(userName);
		  lp.setPassword(password);
		  lp.clickLoginButton();
		  Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//*[contains(text(),'Vigil-Eye')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(@href, 'allot-pending-case')]")).click();
		String parentWindow = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
	}
	@AfterMethod
	public void close() {
		logger.info("Test completed");
	}
	@Test
	public void toverifyConsumerInformation() throws InterruptedException, ClassNotFoundException, SQLException {
		try {
			driver.findElement(By.xpath("//button[.='View']")).click();
			driver.findElement(By.xpath("(//div[@class='MuiAccordionSummary-expandIconWrapper css-1fx8m19'])[1]")).click();
			List<WebElement> consumerDetails = driver.findElements(By.xpath("//div[@class='grid sm:grid-cols-5 mb-2 p-4']//p"));
			ArrayList<String> ConsumerDetailsOnUI=new ArrayList<String>();
			for(int i=0; i<consumerDetails.size(); i++){
				Thread.sleep(1000);
				ConsumerDetailsOnUI.add(consumerDetails.get(i).getText());
			}
			//System.out.println(ConsumerDetailsOnUI);
			AllotPendingCaseDataBase ap=new AllotPendingCaseDataBase();
			List<String> consumerDetailsOnDB = ap.toverifyConsumerDetailsInDatabase();

			if (ConsumerDetailsOnUI.size() != consumerDetailsOnDB.size()) {
				Reporter.log("The ArrayLists are of different sizes.",true);
			} else {
				for (int i = 0; i < ConsumerDetailsOnUI.size(); i++) {
					if (ConsumerDetailsOnUI.get(i).equals(consumerDetailsOnDB.get(i))) {
						Reporter.log("Element " + i + " is equal: " + ConsumerDetailsOnUI.get(i),true);
					} else {
						Reporter.log("Element " + i + " is different: " + ConsumerDetailsOnUI.get(i) + " vs " + consumerDetailsOnDB.get(i),true);
					}
				}
			}
			//System.out.println(ConsumerDetailsOnUI);
			//System.out.println(consumerDetailsOnDB);

		} catch (Exception e) {
			Reporter.log("Test case failed: " + e.getMessage(),true);
			e.printStackTrace();

		}
	}
	@Test(priority = 1)
	public void toverifyMarkingDetails() throws InterruptedException, ClassNotFoundException, SQLException {
		try {
			driver.findElement(By.xpath("//button[.='View']")).click();
			logger.info("Click on view button ");
			driver.findElement(By.xpath("(//div[@class='MuiAccordionSummary-expandIconWrapper css-1fx8m19'])[2]")).click();
			logger.info("Click on marking Details dropdown ");
			List<WebElement> markingDetails = driver.findElements(By.xpath("(//tbody[@class='MuiTableBody-root css-1xnox0e'])[2]/tr/td"));
			ArrayList<String> markingDetailsOnUI=new ArrayList<String>();
			for(int i=0; i<markingDetails.size();i++) {
				markingDetailsOnUI.add(markingDetails.get(i).getText().trim());
			}
			logger.info("Saved all record from UI to ArrayList ");
			AllotPendingCaseDataBase ap=new AllotPendingCaseDataBase();
			List<String> markingDetailsOnDB = ap.toverifyMarkingDetailsInDatabase();
			logger.info("Saved all record from database to ArrayList ");
			if (markingDetailsOnUI.size() != markingDetailsOnDB.size()) {
				logger.info("The ArrayLists are of different sizes ");
				
			} else {
				for (int i = 0; i < markingDetailsOnDB.size(); i++) {
					if (markingDetailsOnUI.get(i).equals(markingDetailsOnDB.get(i))) {
						logger.info("Element "+i+"is equal: "+markingDetailsOnUI.get(i));
					} else {
						logger.info("Element "+i+"is different: "+markingDetailsOnUI.get(i)+ " vs " + markingDetailsOnDB.get(i));
					}
				}
			}
			//System.out.println(markingDetailsOnUI);
			//System.out.println(markingDetailsOnDB);

		} catch (Exception e) {
			logger.info("Test case failed: " + e.getMessage());
			e.printStackTrace();

		}
	}
}