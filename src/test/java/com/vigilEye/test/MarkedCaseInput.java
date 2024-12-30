package com.vigilEye.test;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.text.ParseException;

import java.util.List;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.vigiEye.pom.HomePage;
import com.vigiEye.pom.MarkedCaseInputPage;
import com.vigilEye.allotPendintCaseDatabase.MarkedCaseInputDatabase;



public class MarkedCaseInput extends BaseClass2 {

	// Remove the instantiation of mcp here
	MarkedCaseInputPage mcp;
	//CommonDataPage cdp;
	HomePage hp;

	@BeforeMethod
	public void openBrowser() throws InterruptedException {
		driver.get(baseUrl);
		logger.info("Enter Url!");
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(userName);
		logger.info("Enter UserName!");
		lp.setPassword(password);
		logger.info("Enter Password!");
		lp.clickLoginButton();
		logger.info("Click on Login button");
		Thread.sleep(2000);

		hp=new HomePage(driver);
		hp.clickOnVigilEyeDropdownlist();
		logger.info("Click on VigilEye Dropdown list");
		hp.clickOnMarkedCaseInputMenu();
		logger.info("Click on Marked Case Input Menu");
	}

	@AfterMethod
	public void close() {
		driver.quit();
		logger.info("Test completed");
	}

	@Test
	public void toVerifyMarkedCaseInputMenuIsClicking() {
		mcp=new MarkedCaseInputPage(driver);
		assertEquals(mcp.tocheckMarkedCaseInputMenuIsClickOn(), true);
		logger.info("Mark Case Input menu is Clicking successfully");
	}

	@Test(priority = 1)
	public void toverifyWithoutSelectZoneThenAlertMessageIsDisplayed() throws InterruptedException {
		mcp=new MarkedCaseInputPage(driver);
		mcp.clickOnSubmitButton();
		//cdp=new CommonDataPage(driver);
		assertEquals(mcp.getZoneAlertMessage(),"Required Zone"); 
		assertEquals(mcp.getCircleAlertMessage(),"Required Circle");
		assertEquals(mcp.getDivisionAlertMessage(),"Required Division");
		assertEquals(mcp.getfromDateAlertMessage(),"Required");
		assertEquals(mcp.getUptoDateAlertMessage(),"Required");
		logger.info("Without select Zone,Circle and Division then Alert Message  is displayed" );								
	}

	@Test(priority = 2)
	public void tocheckCountOfMarkedCaseInput() throws InterruptedException, ParseException, ClassNotFoundException, SQLException {
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








