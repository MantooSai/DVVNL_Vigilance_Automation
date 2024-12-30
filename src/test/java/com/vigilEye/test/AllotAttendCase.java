package com.vigilEye.test;



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
import com.mchange.util.AssertException;
import com.vigiEye.pom.ActionPage;
import com.vigiEye.pom.AllotAttendCasePage;
import com.vigiEye.pom.CaseDetailsPage;
import com.vigiEye.pom.CommonDataPage;
import com.vigiEye.pom.HomePage;
import com.vigiEye.pom.Irregularities_Main_Meter_HomePage;
import com.vigiEye.pom.Irregularities_Pole_Meter_HomePage;
import com.vigilEye.allotPendintCaseDatabase.AllotAttendCaseDataBase;



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
	    	driver.close();
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
		public void toverifyHeaderName() {
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
		            String caseNo = cdp.getCaseNoFromExcelFile();
		            logger.info("Get the case no from excel file");
				// Step 3: Enter the fetched case number in the search box
				    atc.searchOption(caseNo);
				logger.info("Search Attend Cases through Case no");
				List<WebElement> allRecordOnUI = driver.findElements(By.xpath("//tr[@class='MuiTableRow-root css-1gqug66']/td"));
				ArrayList<String> alltAttendCasesDetailsOnUI = new ArrayList<>();
				for (int i=2; i<allRecordOnUI.size(); i++) {
					alltAttendCasesDetailsOnUI.add(allRecordOnUI.get(i).getText().trim());
				}
				logger.info("Get the all record from UI");
				AllotAttendCaseDataBase db = new AllotAttendCaseDataBase();
				List<String> allAttendCaseDetailsOnDB = db.toverifyAllotAttendCaseDetailsInDatabase();
				logger.info("Get the all record from table");
		        if (alltAttendCasesDetailsOnUI.size() != allAttendCaseDetailsOnDB.size()) {
		        	logger.info("The ArrayLists are of different sizes.");
		        } else {
		            for (int i = 0; i < alltAttendCasesDetailsOnUI.size(); i++) {
		                if (alltAttendCasesDetailsOnUI.get(i).equals(allAttendCaseDetailsOnDB.get(i))) {
		                	logger.info("Element " + i + " is equal: " + alltAttendCasesDetailsOnUI.get(i));
		                } else {
		                	logger.info("Element " + i + " is different: " + alltAttendCasesDetailsOnUI.get(i) + " vs " + allAttendCaseDetailsOnDB.get(i));
		                }
		            }
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
		            // Step 3: Enter the fetched case number in the search box
				    atc.searchOption(acId);
				    logger.info("Search Attend Cases through Case no");
				     atc.clickOnViewLink();
				     logger.info("Click on View link");
				      caseDetails.clickOnConsumerInformationDropdownlist();
				      logger.info("Click on Consumer Infomation Dropdown list");
				     List<String> consumerInformationOnUI = caseDetails.fetchedConsumerInformation();   
				 AllotAttendCaseDataBase allotAttendCaseOnDb=new AllotAttendCaseDataBase();
				List<String> consumerDetailsOnDB = allotAttendCaseOnDb.toverifyConsumerDetailsInDatabase();
				     if (consumerInformationOnUI.size() != consumerDetailsOnDB.size()) {
                         logger.info("The ArrayLists are of different sizes.");
                     } else {
                         for (int i = 0; i < consumerInformationOnUI.size(); i++) {
                       if (consumerInformationOnUI.get(i).equals(consumerDetailsOnDB.get(i))) {
                                 logger.info("Element " + i + " is equal: " + consumerInformationOnUI.get(i));
                             } else {
                                 logger.info("Element " + i + " is different: " + consumerInformationOnUI.get(i) + " vs " + consumerDetailsOnDB.get(i));
                             }
				            }
				        }
				   
				     
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
		            String caseNo = cdp.getCaseNoFromExcelFile();
		            logger.info("Get the case no from excel file");
		            // Step 3: Enter the fetched case number in the search box
				    atc.searchOption(caseNo);
				    logger.info("Search Attend Cases through Case no");
				     atc.clickOnViewLink();
				     logger.info("Click on View link");
				      caseDetails.clickOnMarkingDetailsDropdownlist();
				      logger.info("Click on Consumer Infomation Dropdown list");
				     List<String> markingInformationOnUI = caseDetails.fetchedMarkingDetails();
				     AllotAttendCaseDataBase allotAttendCaseOnDb=new AllotAttendCaseDataBase();
				     List<String> markingDetailsOnDB = allotAttendCaseOnDb.toverifyMarkingDetailsInDatabase();
				     if (markingInformationOnUI.size() != markingDetailsOnDB.size()) {
				        	logger.info("The ArrayLists are of different sizes.");
				        } else {
				            for (int i = 0; i < markingInformationOnUI.size(); i++) {
				                if (markingInformationOnUI.get(i).equals(markingDetailsOnDB.get(i))) {
				                	logger.info("Element " + i + " is equal: " + markingInformationOnUI.get(i));
				                } else {
				                	logger.info("Element " + i + " is different: " + markingInformationOnUI.get(i) + " vs " + markingDetailsOnDB.get(i));
				                }
				            }
				        }
				     
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
	            String caseNo = cdp.getCaseNoFromExcelFile();
	            logger.info("Get the case no from excel file");
	            // Step 3: Enter the fetched case number in the search box
			    atc.searchOption(caseNo);
			    logger.info("Search Attend Cases through Case no");
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
			     List<String> filedIrregularitiesOnUi = imm.fieldIrregularitiesForMainMeter();
			     AllotAttendCaseDataBase acd=new AllotAttendCaseDataBase();
			     List<String> fieldIrregularitiesOnDb = acd.toverifyFieldIrregularitiesMainMeterInDatabase();
			     if (filedIrregularitiesOnUi.size() != fieldIrregularitiesOnDb.size()) {
			        	logger.info("The ArrayLists are of different sizes.");
			        } else {
			            for (int i = 0; i < filedIrregularitiesOnUi.size(); i++) {
			                if (filedIrregularitiesOnUi.get(i).equals(fieldIrregularitiesOnDb.get(i))) {
			                	logger.info("Element " + i + " is equal: " + filedIrregularitiesOnUi.get(i));
			                } else {
			                	logger.info("Element " + i + " is different: " + filedIrregularitiesOnUi.get(i) + " vs " + fieldIrregularitiesOnDb.get(i));
			                }
			            }
			        }
			     
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
	            String caseNo = cdp.getCaseNoFromExcelFile();
	            logger.info("Get the case no from excel file");
	            // Step 3: Enter the fetched case number in the search box
			    atc.searchOption(caseNo);
			    logger.info("Search Attend Cases through Case no");
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
			     List<String> filedIrregularitiesOnUi = ipm.fieldIrregularitiesForPoleMeter();
			     AllotAttendCaseDataBase acd=new AllotAttendCaseDataBase();
			     List<String> fieldIrregularitiesOnDb = acd.toverifyFieldIrregularitiesPoleMeterInDatabase();
			     if (filedIrregularitiesOnUi.size() != fieldIrregularitiesOnDb.size()) {
			        	logger.info("The ArrayLists are of different sizes.");
			        } else {
			            for (int i = 0; i < filedIrregularitiesOnUi.size(); i++) {
			                if (filedIrregularitiesOnUi.get(i).equals(fieldIrregularitiesOnDb.get(i))) {
			                	logger.info("Element " + i + " is equal: " + filedIrregularitiesOnUi.get(i));
			                } else {
			                	logger.info("Element " + i + " is different: " + filedIrregularitiesOnUi.get(i) + " vs " + fieldIrregularitiesOnDb.get(i));
			                }
			            }
			        }
			     
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
 	            String caseNo = cdp.getCaseNoFromExcelFile();
 	            logger.info("Get the case no from excel file");
 	            // Step 3: Enter the fetched case number in the search box
 			    atc.searchOption(caseNo);
 			    logger.info("Search Attend Cases through Case no");
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
 	            String caseNo = cdp.getCaseNoFromExcelFile();
 	            logger.info("Get the case no from excel file");
 	            // Step 3: Enter the fetched case number in the search box
 			    atc.searchOption(caseNo);
 			    logger.info("Search Attend Cases through Case no");
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