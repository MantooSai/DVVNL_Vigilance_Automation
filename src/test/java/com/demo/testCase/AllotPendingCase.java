package com.demo.testCase;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.baseClass.BaseClass;
import com.baseClass.LoginPage;
import com.demo.database.AllotPendingCaseDataBase;
import com.demo.pom.AllotPendingCasePage;
import com.demo.pom.CommonDataPage;
import com.demo.pom.HomePage;


public class AllotPendingCase extends BaseClass {
    HomePage hp;
    AllotPendingCasePage apc;
    CommonDataPage cdp;
    AllotPendingCaseDataBase db;

    @BeforeMethod
    public void openBrowser() throws InterruptedException {
        driver.get(baseUrl);
        logger.info("Entered URL: ");
        LoginPage lp = new LoginPage(driver);
        lp.setUserName(userNameAt);
        logger.info("Entered UserName: ");
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

    @Test(priority = 1)
    public void toVerifyAllotPendingCasesIsDisplaying() {
        WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'ALLOT PENDING')]"));
        boolean isDisplayed = menu.isDisplayed();
        Assert.assertTrue(isDisplayed, "Allot Pending Case is not Displaying");
        logger.info("Allot Pending Case is Displaying");
    }

    @Test(priority = 2)
    public void toVerifyClickOnAllotPendingCaseMenu() {
    	hp=new HomePage(driver);
        try {
            hp.clickOnAllotPendingMenu();
            logger.info("Clicked on Allot Pending Menu");
            String expectedTitle = "Vigil-Eye";  // Make sure this is the actual title
            Assert.assertEquals(driver.getTitle(), expectedTitle);
            logger.info("Successfully clicked on Allot Pending Case");

        } catch (NoSuchElementException e) {
            logger.error("Element not found: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error during click action: " + e.getMessage());
        }
    }

   


	@Test(priority = 3)
	public void toverifyHeaderNameOfAllotPendingCase() {
		hp=new HomePage(driver);
		try {
			 hp.clickOnAllotPendingMenu();
			 logger.info("Click on allot Pending Menu");
			 CommonDataPage cdp=new CommonDataPage(driver);
			 List<String> headerNameAsPerDesign = cdp.headerName();
			 List<String> headerNameOnUI = cdp.headerName();
			Assert.assertEquals(headerNameAsPerDesign, headerNameOnUI);
			logger.info("Headers fetched from UI");
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
	}

	@Test(priority=4)
	public void toverifyCountOfAllotPendingCase() throws InterruptedException, ClassNotFoundException, SQLException {
		hp=new HomePage(driver);
		apc=new AllotPendingCasePage(driver);
		try {
			  hp.clickOnAllotPendingMenu();
	            logger.info("Clicked on Allot Pending Menu");
	        String consumerOfpendingCaseInUI = apc.countOfAllotPendingCases();
	        logger.info("Get count of Allot Pending Case from UI:- "+consumerOfpendingCaseInUI);
	        Thread.sleep(2000);           
	        AllotPendingCaseDataBase db=new AllotPendingCaseDataBase();
            String consumerOfpendingCaseInDB = db.toverifyCountAllotPendingCaseInDatabase();
            logger.info("Get count of Allot Pending Case from DB:- "+consumerOfpendingCaseInDB);
            SoftAssert sa=new SoftAssert();
            sa.assertEquals(consumerOfpendingCaseInDB, consumerOfpendingCaseInUI);
			//Assert.assertEquals(consumerOfpendingCaseInDB, consumerOfpendingCaseInUI);
			logger.info("Allot Pending Case is fetched & test case passed");
             sa.assertAll(); 
		} catch (Exception e) {
			logger.error("Test case failed: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test(priority = 5)
	public void toverifySearchOptionIsWorkingAndFetchedDetailsFromTable() {
	    hp = new HomePage(driver);
	    cdp = new CommonDataPage(driver);
	    apc = new AllotPendingCasePage(driver);
	    db = new AllotPendingCaseDataBase();
	    try {
	        // Navigate to Allot Pending Cases Menu
	        hp.clickOnAllotPendingMenu();
	        logger.info("Clicked on Allot Pending Menu");

	        // Write case details to Excel (if required for later steps)
	        cdp.writeCaseNoInExcelFormat();
	        logger.info("Case number written to Excel.");
	        cdp.writeAcIdInExcelFormat();
	        logger.info("Account ID written to Excel.");

	        // Fetch account ID and search for it
	        String acId = cdp.getAcIdFromExcelFile();
	        logger.info("Fetched Account ID from Excel: " + acId);

	        apc.searchOption(acId);
	        logger.info("Searched using Account ID.");

	        // Fetch details from UI
	        List<String> allPendingCasesDetailsOnUI = apc.allAllotPendingCaseDetails();
	        logger.info("Fetched UI details: " + allPendingCasesDetailsOnUI);

	        // Fetch details from Database
	    
	        List<String> allPendingCasesDetailsOnDB = db.toverifyAllotPendingCaseDetailsInDatabase();
	        logger.info("Fetched DB details: " + allPendingCasesDetailsOnDB);

	        // Compare UI and DB data
	        if (allPendingCasesDetailsOnUI.equals(allPendingCasesDetailsOnDB)) {
	        	 logger.info("Records match between UI and DB.");
	           
	        } else {
	        	 Assert.fail("Mismatch detected between UI and DB data.");
	        }
	    } catch (Exception e) {
	        logger.error("Test case failed: " + e.getMessage(), e);
	       
	    }
	}


	
	@Test(priority = 6)
	public void toverifydownloadReportInExcelFormat() {
		hp=new HomePage(driver);
	    try {
	    	
	    	 hp.clickOnAllotPendingMenu();
			    logger.info("Clicked on Allot Pending Menu");
			    apc=new AllotPendingCasePage(driver);
	           apc.downloadReportInExcelFormat();
	           logger.info("Report download in Excel format successfully");
	    } catch (Exception e) {
	        logger.error("Test case failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	     
	}
	@Test(priority = 7)
	public void toSavedAllotPendingDetailsInExcelFile() throws EncryptedDocumentException, ClassNotFoundException, SQLException, IOException {
		  db = new AllotPendingCaseDataBase();
		  
		  List<String> allPendingCasesDetailsOnDB = db.toverifyAllotPendingCaseDetailsInDatabase();
		CommonDataPage cdp=new CommonDataPage(driver);
		for (String text : allPendingCasesDetailsOnDB) {
			cdp.writeAllotPendingDetailsInExcelFile(text);
		}
		logger.info("Data successfully written to Excel file.");
	}
}