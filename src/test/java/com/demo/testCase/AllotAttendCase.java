package com.demo.testCase;



import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;
import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.demo.database.AllotAttendCaseDataBase;
import com.demo.pom.ActionPage;
import com.demo.pom.AllotAttendCasePage;
import com.demo.pom.CaseDetailsPage;
import com.demo.pom.CommonDataPage;
import com.demo.pom.HomePage;
import com.demo.pom.Irregularities_Main_Meter_HomePage;
import com.demo.pom.Irregularities_Pole_Meter_HomePage;
import com.mchange.util.AssertException;




public class AllotAttendCase extends BaseClass {
	 HomePage hp;
   AllotAttendCasePage atc;
   CommonDataPage cdp;
   CaseDetailsPage caseDetails;
   Irregularities_Main_Meter_HomePage imm;
   Irregularities_Pole_Meter_HomePage ipm;
   ActionPage ap;
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
	    public void toVerifyAllotAttendCasesIsDisplaying() {
	        WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'ALLOT ATTEND')]"));
	        boolean isDisplayed = menu.isDisplayed();
	        Assert.assertTrue(isDisplayed, "Allot Attend Case is not Displaying");
	        logger.info("Allot Attend Case is Displaying");
	    }
	    
	    @Test(priority = 1)
	    public void toVerifyClickOnAllotAttendCaseMenu() {
	    	hp=new HomePage(driver);
	        try {
	            hp.clickOnAllotAttendMenu();
	            logger.info("Clicked on Allot Attend Menu");
	            String expectedTitle = "Vigil-Eye";  // Make sure this is the actual title
	            Assert.assertEquals(driver.getTitle(), expectedTitle);
	            logger.info("Successfully clicked on Allot Attend Case");

	        } catch (NoSuchElementException e) {
	            logger.error("Element not found: " + e.getMessage());
	            throw e;
	        } catch (Exception e) {
	            logger.error("Error during click action: " + e.getMessage());
	        }
	    }
	    
	    @Test(priority = 2)
		public void toverifyHeaderNameAllotAttendCase() {
	    	hp=new HomePage(driver);
			try {
				 hp.clickOnAllotAttendMenu();
				 logger.info("Click on allot Attend Menu");
				 CommonDataPage cdp=new CommonDataPage(driver);
				 List<String> headerNameAsPerDesign = cdp.headerName();
				 List<String> headerNameOnUI = cdp.headerName();
				Assert.assertEquals(headerNameAsPerDesign, headerNameOnUI);
				logger.info("Headers fetched from UI");
			} catch (Exception e) {
				logger.error("Error: " + e.getMessage());
			}
		}
	    
	    @Test(priority=3)
		public void toverifyCountOfAllotAttendCase() throws InterruptedException, ClassNotFoundException, SQLException {
	    	hp=new HomePage(driver);
	    	atc=new AllotAttendCasePage(driver);
			try {
				hp.clickOnAllotAttendMenu();
				logger.info("Clicked on Allot Attend Menu");
				String consumerOfAttendCaseInUI = atc.checkCountOfAllotAttendCases();
				AllotAttendCaseDataBase acd = new AllotAttendCaseDataBase();
				String consumerOfAttendCaseInDB = acd.toverifyCountAllotAttendCaseInDatabase();
				//System.out.println("Pending cases in DB: " + consumerOfpendingCaseInDB);
				//System.out.println("Pending cases in UI: " + consumerOfpendingCaseInUI);
				Assert.assertEquals(consumerOfAttendCaseInDB, consumerOfAttendCaseInUI);
				//Reporter.log("Allot Pending Case is fetched & test case passed",true);
				logger.info("Allot Attend Case is fetched & test case passed");  

			} catch (Exception e) {
				logger.error("Test case failed: " + e.getMessage());
				e.printStackTrace();
			}
		}
	    @Test(priority = 4)
		public void toverifySearchOptionIsWorkingAndFetchedDetailsFromTable() {
	    	hp=new HomePage(driver);
	    	 atc=new AllotAttendCasePage(driver);
	    	
			try {
				cdp=new CommonDataPage(driver);
				hp.clickOnAllotAttendMenu();
		            logger.info("Clicked on Allot Attend Menu");
		            String acId = cdp.getAcIdFromExcelFile();
		            logger.info("Get the case no from excel file");
				    atc.searchOption(acId);
				    logger.info("Search Attend Cases through Ac no");
				    Thread.sleep(1000);
				    List<String> alltAttendCasesDetailsOnUI = atc.AllotAttendPendingCaseDetails(); 
				    Thread.sleep(1000);
				    logger.info("Get the all record from UI:- "+ alltAttendCasesDetailsOnUI);
				AllotAttendCaseDataBase db = new AllotAttendCaseDataBase();
				List<String> allAttendCaseDetailsOnDB = db.toverifyAllotAttendCaseDetailsInDatabase();
				logger.info("Get the all record from DB:- "+ allAttendCaseDetailsOnDB);
			   if(alltAttendCasesDetailsOnUI.equals(allAttendCaseDetailsOnDB)) {
			    logger.info("Record is Fetched from Db to UI");
			   }
			   else {
				   Assert.fail();
				   logger.info("Record is not Fetched from Db to UI");
			   }
			} catch (Exception e) {
				logger.error("Test case failed: " + e.getMessage());
				e.printStackTrace();
			}
       
		}
	    @Test(priority = 5)
	 		public void toverifyAttendConsumerDetailsIsFetchedDetailsFromTable() {
	 	    	hp=new HomePage(driver);
	 	    	 atc=new AllotAttendCasePage(driver);
	 	    	caseDetails=new CaseDetailsPage(driver);
	 	    	cdp=new CommonDataPage(driver);
	 			try {
	 				hp.clickOnAllotAttendMenu();
		            logger.info("Clicked on Allot Attend Menu");
		            String acId = cdp.getAcIdFromExcelFile();
		            logger.info("Get the case no from excel file");
				    atc.searchOption(acId);
				    logger.info("Search Attend Cases through Ac no");
				     atc.clickOnViewLink();
				     logger.info("Click on View link");
				      caseDetails.clickOnConsumerInformationDropdownlist();
				      logger.info("Click on Consumer Infomation Dropdown list");
				     List<String> consumerInformationOnUI = caseDetails.fetchedConsumerInformation();  
				     logger.info("Get all Record from UI :- "+consumerInformationOnUI);
				 AllotAttendCaseDataBase allotAttendCaseOnDb=new AllotAttendCaseDataBase();
				List<String> consumerDetailsOnDB = allotAttendCaseOnDb.toverifyConsumerDetailsInDatabase();
				logger.info("Get all Record from DB :- "+consumerDetailsOnDB);
				 Assert.assertEquals(consumerInformationOnUI, consumerDetailsOnDB);
				 logger.info("Record is Fetched from Db to UI");
				     
	 			} catch (Exception e) {
					logger.error("Test case failed: " + e.getMessage());
					e.printStackTrace();
				}
				
	     
}
	    @Test(priority = 6)
	 		public void toverifyAttendMarkingDetailsIsFetchedDetailsFromTable() {
	 	    	hp=new HomePage(driver);
	 	    	 atc=new AllotAttendCasePage(driver);
	 	    	caseDetails=new CaseDetailsPage(driver);
	 	    	cdp=new CommonDataPage(driver);
	 			try {
	 				hp.clickOnAllotAttendMenu();
		            logger.info("Clicked on Allot Attend Menu");
		            String acId = cdp.getAcIdFromExcelFile();
		            logger.info("Get the case no from excel file");
				    atc.searchOption(acId);
				    //Thread.sleep(3000);
				    logger.info("Search Attend Cases through Ac no");
				     atc.clickOnViewLink();
				     logger.info("Click on View link");
				      caseDetails.clickOnMarkingDetailsDropdownlist();
				      logger.info("Click on Consumer Infomation Dropdown list");
				     List<String> markingInformationOnUI = caseDetails.fetchedMarkingDetails();
				     logger.info("Get all Record from UI :- "+markingInformationOnUI);
				     AllotAttendCaseDataBase allotAttendCaseOnDb=new AllotAttendCaseDataBase();
				     List<String> markingDetailsOnDB = allotAttendCaseOnDb.toverifyMarkingDetailsInDatabase();
				     logger.info("Get all Record from DB :- "+markingDetailsOnDB);
				     Assert.assertEquals(markingInformationOnUI, markingDetailsOnDB);
					 logger.info("Record is Fetched from Db to UI");
				     
	 			} catch (Exception e) {
					logger.error("Test case failed: " + e.getMessage());
					e.printStackTrace();
				}
	     
}
	    
	    @Test(priority = 7)
 		public void tofetchedFieldIrregularitiesforMainMeter() throws InterruptedException, EncryptedDocumentException, IOException, ClassNotFoundException, SQLException {
 	    	hp=new HomePage(driver);
 	    	 atc=new AllotAttendCasePage(driver);
 	    	caseDetails=new CaseDetailsPage(driver);
 	    	cdp=new CommonDataPage(driver);
 	    	imm=new Irregularities_Main_Meter_HomePage(driver);
 			try {
 				hp.clickOnAllotAttendMenu();
	            logger.info("Clicked on Allot Attend Menu");
	            String acId = cdp.getAcIdFromExcelFile();
	            logger.info("Get the case no from excel file");
			    atc.searchOption(acId);
			    logger.info("Search Attend Cases through Ac no");
			     atc.clickOnViewLink();
			     logger.info("Click on View link");
			     caseDetails.clickOnConsumerInformationDropdownlist();
			     Thread.sleep(2000);
			     logger.info("Click on Consumer Information Dropdown list");
			     caseDetails.clickOnConsumerInformationDropdownlist();
			     Thread.sleep(2000);
			     logger.info("Close on Consumer Information Dropdown list");
			     //caseDetails.clickOnNextButton();
			     Thread.sleep(2000);
			     imm.clickOnIrregularitiesDropdownList();
			     logger.info("Click on Irregularities Dropdown list");
			     List<String> filedIrregularitiesOnUI = imm.fieldIrregularitiesForMainMeter();
			     logger.info("Get all Record from UI :- "+filedIrregularitiesOnUI);
			     AllotAttendCaseDataBase acd=new AllotAttendCaseDataBase();
			     List<String> fieldIrregularitiesOnDB = acd.toverifyFieldIrregularitiesMainMeterInDatabase();
			     logger.info("Get all Record from DB :- "+fieldIrregularitiesOnDB);
			    Assert.assertEquals(filedIrregularitiesOnUI, fieldIrregularitiesOnDB);
			    logger.info("Record is Fetched from Db to UI");
			     
			} catch (Exception e) {
				logger.error("Test case failed: " + e.getMessage());
				e.printStackTrace();
			}
 			}
	    @Test(priority = 8)
 		public void tofetchedFieldIrregularitiesforPoleMeter() throws InterruptedException, EncryptedDocumentException, IOException, ClassNotFoundException, SQLException {
 	    	hp=new HomePage(driver);
 	    	 atc=new AllotAttendCasePage(driver);
 	    	caseDetails=new CaseDetailsPage(driver);
 	    	cdp=new CommonDataPage(driver);
 	    	ipm=new Irregularities_Pole_Meter_HomePage(driver);
 			try {
 				hp.clickOnAllotAttendMenu();
	            logger.info("Clicked on Allot Attend Menu");
	            String acId = cdp.getAcIdFromExcelFile();
	            logger.info("Get the case no from excel file");
			    atc.searchOption(acId);
			    logger.info("Search Attend Cases through Ac no");
		
			     atc.clickOnViewLink();
			     logger.info("Click on View link");
			     caseDetails.clickOnConsumerInformationDropdownlist();
			     Thread.sleep(2000);
			     logger.info("Click on Consumer Information Dropdown list");
			     caseDetails.clickOnConsumerInformationDropdownlist();
			     Thread.sleep(2000);
			     logger.info("Close on Consumer Information Dropdown list");
			     //caseDetails.clickOnNextButton();
			     Thread.sleep(2000);
			     ipm.clickOnIrregularitiesDropdownList();
			     logger.info("Click on Irregularities Dropdown list");
			     List<String> filedIrregularitiesOnUI = ipm.fieldIrregularitiesForPoleMeter();
			     logger.info("Get all Record from UI :- "+filedIrregularitiesOnUI);
			     AllotAttendCaseDataBase acd=new AllotAttendCaseDataBase();
			     List<String> fieldIrregularitiesOnDB = acd.toverifyFieldIrregularitiesPoleMeterInDatabase();
			     logger.info("Get all Record from UI :- "+filedIrregularitiesOnUI);
			     Assert.assertEquals(filedIrregularitiesOnUI, fieldIrregularitiesOnDB);
				    logger.info("Record is Fetched from Db to UI");
			   
			     
			} catch (Exception e) {
				logger.error("Test case failed: " + e.getMessage());
				e.printStackTrace();
			}
 			}
	    @Test(priority = 9)
 		public void toverifyWithoutTakeActionthenAlertMessageIsDisplayed() throws InterruptedException, EncryptedDocumentException, IOException, ClassNotFoundException, SQLException {
 	    	hp=new HomePage(driver);
 	    	 atc=new AllotAttendCasePage(driver);
  	    	caseDetails=new CaseDetailsPage(driver);
  	    	cdp=new CommonDataPage(driver);
  	    	ap=new ActionPage(driver);
  	    	
  			try {
  				hp.clickOnAllotAttendMenu();
 	            logger.info("Clicked on Allot Attend Menu");
 	           String acId = cdp.getAcIdFromExcelFile();
	            logger.info("Get the case no from excel file");
			    atc.searchOption(acId);
			    logger.info("Search Attend Cases through Ac no");
 			     atc.clickOnViewLink();
 			     logger.info("Click on View link");
 			     caseDetails.clickOnConsumerInformationDropdownlist();
 			     Thread.sleep(2000);
 			     logger.info("Click on Consumer Information Dropdown list");
 			     caseDetails.clickOnConsumerInformationDropdownlist();
 			     Thread.sleep(2000);
 			     logger.info("Close on Consumer Information Dropdown list");
 			     caseDetails.movetoActionPage();
 			     logger.info("Move to Action page");
 			    // ap.skipActionPerform();
 			    Thread.sleep(2000);
 			     ap.skipActionPerform();
 			    logger.info("Action perform");
 			   Thread.sleep(2000);
 			  // ap.ctChangeEvedenceImage();
 			   // logger.info("Upload  CT Change Evedence Image");
 			   // Thread.sleep(2000);
 			     ap.clicOnSubmitButton();
 			    Thread.sleep(3000);
 			    logger.info("click on submit button");
 			 
 			    Alert alert = driver.switchTo().alert();
 			    String alertText = alert.getText();
 			    alert.accept();
 			    Assert.assertEquals(alertText, "Please upload a file for at least one action");
 			    logger.info("Then alert message "+alertText+" is Displayed.");
			    
			    
  			} catch (Exception e) {
				logger.error("Test case failed: " + e.getMessage());
				e.printStackTrace();
			}

	    }
	    
	    @Test(priority = 10)
 		public void toverifythatTakeActionToAllotedAttendCases() throws InterruptedException, EncryptedDocumentException, IOException, ClassNotFoundException, SQLException {
 	    	hp=new HomePage(driver);
 	    	 atc=new AllotAttendCasePage(driver);
  	    	caseDetails=new CaseDetailsPage(driver);
  	    	cdp=new CommonDataPage(driver);
  	    	ap=new ActionPage(driver);
  	    	
  			try {
  				hp.clickOnAllotAttendMenu();
 	            logger.info("Clicked on Allot Attend Menu");
 	           String acId = cdp.getAcIdFromExcelFile();
	            logger.info("Get the case no from excel file");
			    atc.searchOption(acId);
			    logger.info("Search Attend Cases through Ac no");
 			     atc.clickOnViewLink();
 			     logger.info("Click on View link");
 			     caseDetails.clickOnConsumerInformationDropdownlist();
 			     Thread.sleep(2000);
 			     logger.info("Click on Consumer Information Dropdown list");
 			     caseDetails.clickOnConsumerInformationDropdownlist();
 			     Thread.sleep(2000);
 			     logger.info("Close on Consumer Information Dropdown list");
 			     caseDetails.movetoActionPage();
 			     logger.info("Move to Action page");
 			    Thread.sleep(2000);
 			    // ap.skipActionPerform();
 			     ap.skipActionPerform();
 			     logger.info("Action perform");
 			     Thread.sleep(2000);
 			     ap.ctChangeEvedenceImage();
 			     logger.info("Upload  CT Change Evedence Image");
 			     Thread.sleep(2000);
 			     ap.clicOnSubmitButton();
 			    Thread.sleep(2000);
 			    logger.info("click on submit button");
 			    Thread.sleep(2000);
 			    Alert alert = driver.switchTo().alert();
 			    String alertText = alert.getText();
 			    alert.accept();
 			    logger.info(alertText);
			    
  			} catch (Exception e) {
				logger.error("Test case failed: " + e.getMessage());
				e.printStackTrace();
			}

	    }
}