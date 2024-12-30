package com.demo.testCase;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.utility.RobotClass;
import com.vigiEye.pom.HomePage;
import com.vigiEye.pom.MarkedCaseInputPage;
import com.vigilEye.allotPendintCaseDatabase.MarkedCaseInputDatabase;



public class MarkedCaseInput extends BaseClass2 {

	// Remove the instantiation of mcp here
	MarkedCaseInputPage mcp;
	//CommonDataPage cdp;
	HomePage hp;
	RobotClass rc;

	@BeforeMethod
	public void openBrowser() throws InterruptedException {
		  driver.get(baseUrl);
	        logger.info("Open Url");
	        LoginPage lp = new LoginPage(driver);
	        lp.setUserName(userNameAt);
	        logger.info("Entered UserId");
	        lp.setPassword(passwordAt);
	        logger.info("Entered Password");
	        lp.clickLoginButton();
	        logger.info("Clicked on login button");

	        hp = new HomePage(driver); // Ensure HomePage is initialized before usage
	        hp.clickOnVigilEyeDropdownlist();
	        logger.info("Clicked on Vigil-Eye Menu");

	}

	@AfterMethod
	public void close() {
		driver.quit();
		logger.info("Test completed");
	}

	@Test
	 public void toVerifyMarkedCasesIsDisplaying() {
        WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'MARKED CASE INPUTS')]"));
        boolean isDisplayed = menu.isDisplayed();
        Assert.assertTrue(isDisplayed, "MARKED CASE INPUTS");
        logger.info("Marked Case Input is Displaying");
    }
	// To check without selecting zone, circle and division and click on submit button then alert message is disspalyed.
	@Test(priority = 1)
	public void toverifyWithoutSelectZoneThenAlertMessageIsDisplayed() throws InterruptedException {
		hp=new HomePage(driver);
		mcp=new MarkedCaseInputPage(driver);
        try {
            hp.clickOnMarkedCaseInputMenu();
           logger.info("Clicked on Allot Pending Menu");
		   mcp.clickOnSubmitButton();
		   logger.info("Clicked on Submit button");
		//cdp=new CommonDataPage(driver);
		assertEquals(mcp.getZoneAlertMessage(),"Required Zone"); 
		 logger.info("Alert message is fetched " +mcp.getZoneAlertMessage());
		assertEquals(mcp.getCircleAlertMessage(),"Required Circle");
		 logger.info("Alert message is fetched "+mcp.getCircleAlertMessage());
		assertEquals(mcp.getDivisionAlertMessage(),"Required Division");
		 logger.info("Alert message is fetched "+mcp.getDivisionAlertMessage());
		assertEquals(mcp.getfromDateAlertMessage(),"Required");
		 logger.info("Alert message is fetched "+mcp.getfromDateAlertMessage());
		assertEquals(mcp.getUptoDateAlertMessage(),"Required");
		 logger.info("Alert message is fetched "+mcp.getUptoDateAlertMessage());
        } catch (NoSuchElementException e) {
            logger.error("Element not found: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during click action: " + e.getMessage());
        }
	}

	@Test(priority = 2)
	public void tocheckCountOfMarkedCaseInput() throws InterruptedException, ParseException, ClassNotFoundException, SQLException {
		hp=new HomePage(driver);
		mcp=new MarkedCaseInputPage(driver);
		rc = new RobotClass();
        try {
            hp.clickOnMarkedCaseInputMenu();
           logger.info("Clicked on Allot Pending Menu");
		mcp.selectZone(zoneName);
		logger.info("Select Zone from dropdown list");
		mcp.selectCircle(circleName);
		logger.info("Select Circle from dropdown list");
		mcp.selectDivision(divisionName);
		rc.handleKeyPress();
		//Thread.sleep(1000);
		logger.info("Select Division from dropdown list");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		String previousMonthDate = sdf.format(calendar.getTime());
		String fromday = previousMonthDate.split("-")[0];
		String frommonth = previousMonthDate.split("-")[1];
		String fromyear = previousMonthDate.split("-")[2];
		mcp.enterFromDate(fromday);
		logger.info("Enter Day in from Date field"); 
		mcp.enterFromDate(frommonth);
		logger.info("Enter Month in from Date field"); 
		rc.handleTabPress();
		mcp.enterFromDate(fromyear);
		logger.info("Enter Year in from Date field");
		String currentDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		String day = currentDate.split("-")[0]; 
		String month = currentDate.split("-")[1];
		String year = currentDate.split("-")[2];
		mcp.enterUptoDate(day);
		logger.info("Enter Day in upto Date field"); 
		mcp.enterUptoDate(month);
		logger.info("Enter Month in upto Date field"); 
		rc.handleTabPress();
		mcp.enterUptoDate(year);
		logger.info("Enter Year in upto Date field"); 
		mcp.clickOnSubmitButton();
		logger.info("Click on submit button");
		//System.out.println(mcp.markedCasesListDetails());
		List<String> countOfMarkedCaseInputInUI = mcp.markedCasesListDetails();
		MarkedCaseInputDatabase CountOfMarkedCaseDb=new MarkedCaseInputDatabase();
		List<String> countOfMarkedCaseinputDb = CountOfMarkedCaseDb.countOfMarkedCaseInputInDatabase();

		   if (countOfMarkedCaseInputInUI.size() != countOfMarkedCaseinputDb.size()) {
		        logger.info("The ArrayLists are of different sizes. UI: " + countOfMarkedCaseInputInUI.size() + ", DB: " + countOfMarkedCaseinputDb.size());
		    } else {
		        for (int i = 0; i < countOfMarkedCaseinputDb.size(); i++) {
		            String uiElement = countOfMarkedCaseInputInUI.get(i);
		            String dbElement = countOfMarkedCaseinputDb.get(i);
		            
		            if (uiElement.equals(dbElement)) {
		                logger.info("Element " + i + " is equal: " + uiElement);
		            } else {
		                logger.info("Element " + i + " is different: UI = " + uiElement + " vs DB = " + dbElement);
		            }
		        }
		    }
        } catch (NoSuchElementException e) {
            logger.error("Element not found: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during click action: " + e.getMessage());
        }
		}
	
	@Test(priority = 3)
	public void clickOnTotalMarkedCaseHyperlink() throws InterruptedException {
		mcp=new MarkedCaseInputPage(driver);
		//cdp=new CommonDataPage(driver);
		mcp.selectZone(zoneName);
		logger.info("Select Zone from dropdown list");
		mcp.selectCircle(circleName);
		logger.info("Select Circle from dropdown list");
		mcp.selectDivision(divisionName);
		logger.info("Select Division from dropdown list");
		Thread.sleep(3000);
		mcp.enterFromDate(fromDate);  // Enter from date in specified format
		logger.info("Enter from date in date format");
		Thread.sleep(3000);
		mcp.enterUptoDate(uptoDate);  
		Thread.sleep(3000);
		logger.info("Enter UptoDate in date format");
		mcp.clickOnSubmitButton();
		Thread.sleep(2000);
		logger.info("Click on submit button");
		mcp.clickOnTotalMarkedCasesHyperLink();
		Thread.sleep(3000);
		logger.info("Clicked total marked case HyperLink");
	    List<String> totalMarkedCaseDetailsUI = mcp.totalMarkedCasesDetails();
	    System.out.println(totalMarkedCaseDetailsUI);
	    
		
	}
	}








