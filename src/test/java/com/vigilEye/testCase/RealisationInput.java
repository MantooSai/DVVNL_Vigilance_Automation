package com.vigilEye.testCase;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;
import com.baseClass.LoginPage;
import com.utility.PropertyFile;
import com.utility.RobotClass;
import com.vigiEye.pom.AssessmentRealisationPage;
import com.vigiEye.pom.CaseDetailsPage;
import com.vigiEye.pom.CommonDataPage;
import com.vigiEye.pom.HomePage;
import com.vigiEye.pom.Irregularities_Main_Meter_HomePage;
import com.vigiEye.pom.Irregularities_Pole_Meter_HomePage;
import com.vigiEye.pom.RealisationInputPage;
import com.vigilEye.allotPendintCaseDatabase.AssessmentRealisationCaseDatabase;
import com.vigilEye.allotPendintCaseDatabase.CommonDatabase;

public class RealisationInput extends BaseClass{
	 HomePage hp;
	 CommonDataPage cdp;
	 AssessmentRealisationPage arp;
	 RobotClass rc;
	 CaseDetailsPage caseDetailsPage;
	 Irregularities_Main_Meter_HomePage imm;
	 Irregularities_Pole_Meter_HomePage ipm;
	 PropertyFile pf;
	 AssessmentRealisationCaseDatabase arcd;
	 AssessmentRealisationCaseDatabase assessmentRealistion;
	 RealisationInputPage rip;
	 CommonDatabase cd;
	    @BeforeMethod
	    public void openBrowser() throws InterruptedException {
	        driver.get(baseUrl);
	        logger.info("Open Url");
	        LoginPage lp = new LoginPage(driver);
	        lp.setUserName(userNameEe);
	        logger.info("Entered UserId");
	        lp.setPassword(passwordEe);
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
	    public void toVerifyRealisationInputIsDisplaying() {
	    	rip=new RealisationInputPage(driver);
	    	 WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'REALISATION INPUTS')]"));
	        boolean isDisplayed = menu.isDisplayed();
	        Assert.assertTrue(isDisplayed, "REALISATION INPUTS");
	        logger.info("REALISATION INPUTS is Displaying");
	    }
	    
	    @Test(priority = 1)
	    public void toVerifyClickOnRealisaitionInputCaseMenu() {
	    	hp=new HomePage(driver);
	        try {
	            hp.clickOnRealisationInputCaseMenu();
	            logger.info("Click on  Realisation Input Case Menu");
	            String expectedTitle = "Vigil-Eye";  // Make sure this is the actual title
	            Assert.assertEquals(driver.getTitle(), expectedTitle);
	            logger.info("Successfully clicked on  Realisation Input Menu");

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
	  	    	cdp=new CommonDataPage(driver);
	  	    	arp=new AssessmentRealisationPage(driver);
	  	    	rc=new RobotClass();
	  	    	
	  			try {
	  				 hp.clickOnAssessmentRealisationlCaseMenu();
	  				 logger.info("Click on Assessment Feeding Case Menu");
	  				 List<String> headerNameAsPerDesign = cdp.headerNameOfAssessmentRealisationCase();
	  				 List<String> headerNameOnUI = cdp.headerName();
	  				Assert.assertEquals(headerNameAsPerDesign, headerNameOnUI);
	  				logger.info("Headers fetched from UI");
	  				// System.out.println(headerNameOnUI);
	  			} catch (Exception e) {
	  				logger.error("Error: " + e.getMessage());
	  			}
	    }
	    
	    @Test(priority = 3)
	    public void toVerifyCountOfRealisationInputCase() {
	        try {
	            // Initialize necessary pages and utilities
	            hp = new HomePage(driver);
	            cdp = new CommonDataPage(driver);
	            rip = new RealisationInputPage(driver);
	            rc = new RobotClass();
	            pf = new PropertyFile();

	            // Interact with UI
	            hp.clickOnRealisationInputCaseMenu();
	            logger.info("Clicked on Realisation Input Case Menu");
                Robot r=new Robot();
	            // Load property values
	            pf.realisationInputDetails();
	            String fromDateDay = pf.getProperty("fromdd");
	            String fromDateMonth = pf.getProperty("frommmm");
	           // r.keyPress(KeyEvent.VK_TAB);
	            String fromDateYear = pf.getProperty("fromyyyy");
	           // r.keyRelease(KeyEvent.VK_TAB);
	            String uptoDateDay = pf.getProperty("uptodd");
	            String uptoDateMonth = pf.getProperty("uptommm");
	           // r.keyPress(KeyEvent.VK_TAB);
	            String uptoDateYear = pf.getProperty("uptoyyyy");
	           // r.keyRelease(KeyEvent.VK_TAB);

	            if (fromDateDay == null || fromDateMonth == null || fromDateYear == null ||
	                uptoDateDay == null || uptoDateMonth == null || uptoDateYear == null) {
	                throw new IllegalStateException("Date properties are missing in the properties file.");
	            }

	            // Enter date ranges
	            rip.enterFromDate(fromDateDay);
	            rip.enterFromDate(fromDateMonth);
	            r.keyPress(KeyEvent.VK_TAB);
	            rip.enterFromDate(fromDateYear);
	            logger.info("Entered From Date: " + fromDateDay + "/" + fromDateMonth + "/" + fromDateYear);

	            rip.enterUptoDate(uptoDateDay);
	            rip.enterUptoDate(uptoDateMonth);
	            r.keyPress(KeyEvent.VK_TAB);
	            rip.enterUptoDate(uptoDateYear);
	            r.keyRelease(KeyEvent.VK_TAB);
	            logger.info("Entered Upto Date: " + uptoDateDay + "/" + uptoDateMonth + "/" + uptoDateYear);

	            // Submit the form
	            rip.clickOnSubmitButton();
	            logger.info("Clicked on Submit button");

	            // Fetch and log count
	            String countOfRealisationCaseOnUI = cdp.countOfTotalAssessmentCase();
	            logger.info("Total count of Realisation Cases on UI: " + countOfRealisationCaseOnUI);

	            // Uncomment the following if database validation is required
	            // String countOfRealisationCaseOnDB = assessmentRealistion.toverifyCountAssessmentRealisationCaseInDatabase();
	            // assertEquals(countOfRealisationCaseOnUI, countOfRealisationCaseOnDB, "Mismatch between UI and DB count.");
	            // logger.info("Verified Assessment Realisation Count between UI and Database.");

	        } catch (Exception e) {
	            logger.error("Error occurred: " + e.getMessage(), e);
	            throw new RuntimeException(e);
	        }
	    }

	    @Test(priority = 4)
  		public void toveriyfetchedAssessmentRealisationCaseDetailsFromTable() {
  	    	hp=new HomePage(driver);
  	    	cdp=new CommonDataPage(driver);
  	    	arp=new AssessmentRealisationPage(driver);
  	    	rc=new RobotClass();
  	    	caseDetailsPage=new CaseDetailsPage(driver);
  	    	imm=new Irregularities_Main_Meter_HomePage(driver);
  	     	ipm=new Irregularities_Pole_Meter_HomePage(driver);
  	     	pf=new PropertyFile();
  	        assessmentRealistion=new AssessmentRealisationCaseDatabase();
  			try {
  				hp.clickOnAssessmentRealisationlCaseMenu();
  				logger.info("Click on Assessment Realisation  Case Menu");
  				Thread.sleep(2000);	
  				String acId = cdp.getAcIdFromExcelFile();
  				logger.info("Get Account id from Excel format");
  				arp.searchingElement(acId);
  			    logger.info("Search Through acid in Assessment Realisation page");
  				List<String> assessmentRealisationDetailsOnUI = arp.allDetails();
  				List<String> assessmentRealisationDetailsOnDB = assessmentRealistion.toverifyAssessmentRealisationCaseDetailsInDatabase();
  				System.out.println(assessmentRealisationDetailsOnUI);
  				System.out.println(assessmentRealisationDetailsOnDB);
  				if (assessmentRealisationDetailsOnUI.size() != assessmentRealisationDetailsOnDB.size()) {
 		        	logger.info("The ArrayLists are of different sizes.");
 		        } else {
 		            for (int i = 0; i < assessmentRealisationDetailsOnUI.size(); i++) {
 		                if (assessmentRealisationDetailsOnUI.get(i).equals(assessmentRealisationDetailsOnDB.get(i))) {
 		                	logger.info("Element " + i + " is equal: " + assessmentRealisationDetailsOnUI.get(i));
 		                } else {
 		                	logger.info("Element " + i + " is different: " + assessmentRealisationDetailsOnUI.get(i) + " vs " + assessmentRealisationDetailsOnDB.get(i));
 		                }
 		            }
 		        }
 		        
 			} catch (Exception e) {
 				logger.error("Test case failed: " + e.getMessage());
 				e.printStackTrace();
 			}
        
 		}
  			
	    @Test(priority = 5)
	    public void allRealisationDetailsDetails() throws InterruptedException, EncryptedDocumentException, ClassNotFoundException, SQLException, IOException, ParseException {
	        hp = new HomePage(driver);
	        cdp = new CommonDataPage(driver);
	        arp = new AssessmentRealisationPage(driver);
	        rc = new RobotClass();
	        caseDetailsPage = new CaseDetailsPage(driver);
	        imm = new Irregularities_Main_Meter_HomePage(driver);
	        ipm = new Irregularities_Pole_Meter_HomePage(driver);
	        pf = new PropertyFile();
	        cd = new CommonDatabase();

	        try {
	            hp.clickOnAssessmentRealisationlCaseMenu();
	            logger.info("Click on Assessment Realisation Case Menu");
	            Thread.sleep(2000);

	            arp.clickOnCaseNoHyperLink();
	            logger.info("Click ON Case no hyperLink");
	            Thread.sleep(2000);
	            String acId = cdp.getAcIdFromExcelFile();
  				logger.info("Get Account id from Excel format");
  				arp.searchingElement(acId);
  				logger.info("Search Through acid in Assessment Realisation page");
	        

	            // Fetch Consumer Information and Verify
	          try {
	                cdp.consumerInformationDropdownlist();
	                Thread.sleep(1000);
	                List<String> consumerInformationOnUI = cdp.consumerInformationDetails();
	                Thread.sleep(1000);
	                logger.info("Get Consumer Information from UI: " + consumerInformationOnUI);
	                List<String> consumerInformationOnDB = cd.toverifyConsumerInformationInDatabase();
	                logger.info("Get Consumer Information from DB: " + consumerInformationOnDB);
	                Thread.sleep(1000);
	                assertEquals(consumerInformationOnUI, consumerInformationOnDB);
	                logger.info("Consumer Information verified successfully");
	                cdp.consumerInformationDropdownlist();
	            } catch (Exception e) {
	                logger.error("Error in Consumer Information verification: " + e.getMessage());
	            }
	           

	            // Fetch Marking Details and Verify
	            try {
	                
	                cdp.markingDetailsDropdownlist();
	                Thread.sleep(1000);
	                List<String> markingDetailsOnUI = cdp.markingDetails();
	                Thread.sleep(1000);
	                logger.info("Get Marking Details from UI: " + markingDetailsOnUI);
	                List<String> markingDetailsOnDB = cd.toverifyMarkingDetailsInDatabase();
	                Thread.sleep(1000);
	                logger.info("Get Marking Details from DB: " + markingDetailsOnDB);
	                assertEquals(markingDetailsOnUI, markingDetailsOnDB);
	                Thread.sleep(1000);
	                logger.info("Marking Details verified successfully");
	                cdp.markingDetailsDropdownlist();
	            } catch (Exception e) {
	                logger.error("Error in Marking Details verification: " + e.getMessage());
	            }
	            
	            // Fetch Irregularities Main and Verify
	          
	            try {
	                cdp.irregularitiesMainMeterDropdownlist();
	                Thread.sleep(1000);
	                List<String> irregularitiesMainDetailsOnUI = cdp.irregularitiesMainMeterDetails();
	                Thread.sleep(1000);
	                logger.info("Get irregularities Main Meter Details from UI: " + irregularitiesMainDetailsOnUI);
	                List<String> irregularitiesMainDetailsOnUIDetailsOnDB = cd.toverifyIrregulatriesMainMeterDetailsInDatabase();
	                Thread.sleep(1000);
	                logger.info("Get irregularities Main Meter Details from DB: " + irregularitiesMainDetailsOnUIDetailsOnDB);
	                assertEquals(irregularitiesMainDetailsOnUI, irregularitiesMainDetailsOnUIDetailsOnDB);
	                Thread.sleep(1000);
	                logger.info("irregularities Main Details verified successfully");
	                cdp.irregularitiesMainMeterDropdownlist();
	            } catch (Exception e) {
	                logger.error("Error in irregularities Main Details verification: " + e.getMessage());
	            }
	    
	               Thread.sleep(1000);        // Fetch Irregularities Pole and Verify
	           try {
	                cdp.irregularitiesPoleMeterDropdownlist();
	                Thread.sleep(1000);
	                List<String> irregularitiesPoleDetailsOnUI = cdp.irregularitiesPoleMeterDetails();
	                Thread.sleep(1000);
	                logger.info("Get irregularities Pole Meter Details from UI: " + irregularitiesPoleDetailsOnUI);
	                List<String> irregularitiesPoleDetailsOnUIDetailsOnDB = cd.toverifyIrregulatriesPoleMeterDetailsInDatabase();
	                Thread.sleep(1000);
	                logger.info("Get irregularities Pole Meter Details from DB: " + irregularitiesPoleDetailsOnUIDetailsOnDB);
	         
	                assertEquals(irregularitiesPoleDetailsOnUI, irregularitiesPoleDetailsOnUIDetailsOnDB);
	                Thread.sleep(1000);
	                logger.info("irregularities Pole Details verified successfully");
	                cdp.irregularitiesPoleMeterDropdownlist();
	            } catch (Exception e) {
	               logger.error("Error in irregularities Pole Details verification: " + e.getMessage());
	            }
	            Thread.sleep(1000);
	            // Fetch Action and Verify
	            try {
	                cdp.actionDropdownlist();
	                Thread.sleep(1000);
	                List<String> actiondetailsOnUI = cdp.actionDetails();
	                Thread.sleep(1000);
	                logger.info("Get Action Details from UI: " + actiondetailsOnUI);
	                List<String> actiondetailsOnDB = cd.toverifyActionDetailsInDatabase();
	                Thread.sleep(1000);
	                logger.info("Get Action Details from DB: " + actiondetailsOnDB);
	                assertEquals(actiondetailsOnUI, actiondetailsOnDB);
	                Thread.sleep(1000);
	                logger.info("Action Details verified successfully");
	                cdp.actionDropdownlist();
	            } catch (Exception e) {
	                logger.error("Error in Action Details verification: " + e.getMessage());
	            }
	            Thread.sleep(1000);
	            // Fetch Case Review Details and Verify
	            try {
	                cdp.caseReviewDropdownlist();
	                Thread.sleep(1000);
	                List<String> caseReviewDetailsOnUI = cdp.caseReviewDetails();
	                Thread.sleep(1000);
	                logger.info("Get Case Review Details from UI: " + caseReviewDetailsOnUI);
	                List<String> caseReviewDetailsOnDB = cd.toverifyCaseReviewDetailsInDatabase();
	                Thread.sleep(1000);
	                logger.info("Get Case Review  Details from DB: " + caseReviewDetailsOnDB);
	                assertEquals(caseReviewDetailsOnUI, caseReviewDetailsOnDB);
	                Thread.sleep(1000);
	                logger.info("Case Review Details verified successfully");
	                cdp.caseReviewDropdownlist();
	            } catch (Exception e) {
	                logger.error("Error in Case Review Details verification: " + e.getMessage());
	            }
	            Thread.sleep(1000);
	            // Fetch Assessment Details and Verify
	            try {
	                cdp.assessmentDetailsDropdownlist();
	                Thread.sleep(1000);
	                List<String> assessmentDetailsOnUI = cdp.assessmentDetails();
	                Thread.sleep(1000);
	                logger.info("Get Assessment Details from UI: " + assessmentDetailsOnUI);
	                List<String> assessmentDetailsOnDB = cd.toverifyAssessmentDetailsInDatabase();
	                Thread.sleep(1000);
	                logger.info("Get Assessment  Details from DB: " + assessmentDetailsOnDB);
	                assertEquals(assessmentDetailsOnUI, assessmentDetailsOnDB);
	                Thread.sleep(1000);
	                logger.info("Assessment Details verified successfully");
	                cdp.assessmentDetailsDropdownlist();
	            } catch (Exception e) {
	                logger.error("Error in Assessment Details verification: " + e.getMessage());
	            }  
	        } catch (Exception e) {
	            logger.error("Error in Test Execution: " + e.getMessage());
	        }
	    }

  			 @Test(priority = 6)
  	  		public void provideAssessmentRealistionCaseDetails() {
  	  	    	hp=new HomePage(driver);
  	  	    	cdp=new CommonDataPage(driver);
  	  	       arp=new AssessmentRealisationPage(driver);
  	  	    	rc=new RobotClass();
  	  	    	caseDetailsPage=new CaseDetailsPage(driver);
  	  	    	imm=new Irregularities_Main_Meter_HomePage(driver);
  	  	     	ipm=new Irregularities_Pole_Meter_HomePage(driver);
  	  	     	pf=new PropertyFile();
  	  	    	
  	  			try {
  	  				 hp.clickOnAssessmentRealisationlCaseMenu();
  	  				 logger.info("Click on Assessment Realisation Case Menu");
  	  				Thread.sleep(2000);	
					
					 String acId = cdp.getAcIdFromExcelFile();
				     logger.info("Get Account id from Excel format"); 
				     arp.searchingElement(acId);
					 logger.info("Search through AcId"); 
					
  	  			    arp.clickOnCaseNoHyperLink();
  	  			    logger.info("Click ON Case no hyperLink");
  	  				Thread.sleep(2000);
  	  				arp.clickOnRealisationDetailsDropdownList();
  	  			    logger.info("Click on Realisation Details Dropdownlist");
  	  				pf.AssessmentRealisationDetails();
  	  				arp.enterPayAmountInRealisationDetails(pf.getProperty("paymentAmount"));
  	  			    Thread.sleep(2000);
  	  			    logger.info("Enter Payment Amount");
  	  				arp.enterDateInRealisationDetails(pf.getProperty("paymentDate"));
  	  			    logger.info("Enter Payment Date");
  	  			    Thread.sleep(2000);
  	  				arp.enterReceiptNoInRealisationDetails(pf.getProperty("receiptNo"));
  	  			    Thread.sleep(2000);
  	  			    logger.info("Enter Receipt No");
  	  				arp.enterRemarkInRealisationDetails(pf.getProperty("remark"));
  	  			    Thread.sleep(2000);
	  			    logger.info("Enter Remark");
	  			    Thread.sleep(2000);
	  			    arp.clickOnSubmitButton();
	  			    logger.info("Click on Submit button");
	  			    String alertMessage = arp.AfterClickOnSubmitButtonThenAlertMessage();
	  			    assertEquals(alertMessage, "data uploaded successfully");
	  				logger.info(alertMessage+" is Displayed!");
  	  			} catch (Exception e) {
  	  				logger.error("Error: " + e.getMessage());
  	  			}
}
  			@Test(priority = 7)
  			public void SavedAssessmentRealisationCaseDetailsInExcelFile() throws EncryptedDocumentException, ClassNotFoundException, SQLException, IOException {
  				arcd = new AssessmentRealisationCaseDatabase();
  			     List<String> realisationAssessmentDetailsOnDB = arcd.savedAssessmentRealisationDetailsInExcelFile();
  			      CommonDataPage cdp=new CommonDataPage(driver);
  			      
  			        for (String text : realisationAssessmentDetailsOnDB) {
  			        	cdp.writeAssessmentRealisationDetailsInExcelFile(text);
  			        }
  			       logger.info("Data successfully written to Excel file.");
  			       
  			  
  			    }
  			}



