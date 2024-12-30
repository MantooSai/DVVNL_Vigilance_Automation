package com.demo.testCase;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;
import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.demo.database.AssessmentFeedingCaseDatabase;
import com.demo.pom.AssessmentFeedingPage;
import com.demo.pom.CaseDetailsPage;
import com.demo.pom.CommonDataPage;
import com.demo.pom.HomePage;
import com.demo.pom.Irregularities_Main_Meter_HomePage;
import com.demo.pom.Irregularities_Pole_Meter_HomePage;
import com.utility.PropertyFile;
import com.utility.RobotClass;



public class AssessmentFeedingCase  extends BaseClass{
	 HomePage hp;
	 CommonDataPage cdp;
	 AssessmentFeedingPage afp;
	 RobotClass rc;
	 CaseDetailsPage caseDetailsPage;
	 Irregularities_Main_Meter_HomePage imm;
	 Irregularities_Pole_Meter_HomePage ipm;
	 PropertyFile pf;
	 AssessmentFeedingCaseDatabase afcd;
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
	    public void toVerifyAssessmentFeedingIsDisplaying() {
	        WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'ASSESSMENT FEEDING')]"));
	        boolean isDisplayed = menu.isDisplayed();
	        Assert.assertTrue(isDisplayed, "ASSESSMENT FEEDING");
	        logger.info("Assessment Feeding is Displaying");
	    }
	    
	    @Test(priority = 1)
	    public void toVerifyClickOnAssessmentFeedingCaseMenu() {
	    	hp=new HomePage(driver);
	        try {
	            hp.clickOnAssessmentFeedingCaseMenu();
	            logger.info("Click on Assessment Feeding Case Menu");
	            String expectedTitle = "Vigil-Eye";  // Make sure this is the actual title
	            Assert.assertEquals(driver.getTitle(), expectedTitle);
	            logger.info("Successfully clicked on Assessment Feeding Menu");

	        } catch (NoSuchElementException e) {
	            logger.error("Element not found: " + e.getMessage());
	            throw e;
	        } catch (Exception e) {
	            logger.error("Error during click action: " + e.getMessage());
	        }
	    }
	    @Test(priority = 2)
	  		public void toverifyHeaderNameFeedingCaseDetails() {
	  	    	hp=new HomePage(driver);
	  	    	cdp=new CommonDataPage(driver);
	  	    	afp=new AssessmentFeedingPage(driver);
	  	    	rc=new RobotClass();
	  	    	
	  			try {
	  				 hp.clickOnAssessmentFeedingCaseMenu();
	  				 logger.info("Click on Assessment Feeding Case Menu");
	  				 List<String> headerNameAsPerDesign = cdp.headerNameOfAssessmentFeedingCase();
	  				 List<String> headerNameOnUI = cdp.headerName();
	  				Assert.assertEquals(headerNameAsPerDesign, headerNameOnUI);
	  				logger.info("Headers fetched from UI");
	  				// System.out.println(headerNameOnUI);
	  			} catch (Exception e) {
	  				logger.error("Error: " + e.getMessage());
	  			}
	    }
	    
	    @Test(priority = 3)
  		public void toveriyCountOfAssessmentApprovalCase() {
  	    	hp=new HomePage(driver);
  	    	cdp=new CommonDataPage(driver);
  	    	afp=new AssessmentFeedingPage(driver);
  	    	rc=new RobotClass();
  	    	afcd=new AssessmentFeedingCaseDatabase();
  			try {
  				hp.clickOnAssessmentFeedingCaseMenu();
  				//afp.countOfTotalAssessmentCase();
  				Thread.sleep(2000);
  				String assessmentFeedingOnUi = afp.countOfTotalAssessmentCase();
  				String assessmentFeedingOnDB = afcd.toverifyCountAssessmentFeedingCaseInDatabase();
  				Assert.assertEquals(assessmentFeedingOnUi, assessmentFeedingOnDB);
  				logger.info("Assessement feeding count is fetched.");
  			} catch (Exception e) {
  				logger.error("Error: " + e.getMessage());
  			}
  	
}
	    @Test(priority = 4)
  		public void toveriyfetchedAssessmentFeedingCaseDetailsFromTable() {
  	    	hp=new HomePage(driver);
  	    	cdp=new CommonDataPage(driver);
  	    	afp=new AssessmentFeedingPage(driver);
  	    	rc=new RobotClass();
  	    	caseDetailsPage=new CaseDetailsPage(driver);
  	    	imm=new Irregularities_Main_Meter_HomePage(driver);
  	     	ipm=new Irregularities_Pole_Meter_HomePage(driver);
  	     	pf=new PropertyFile();
  	     	afcd=new AssessmentFeedingCaseDatabase();
  	    	
  			try {
  				 hp.clickOnAssessmentFeedingCaseMenu();
  				 logger.info("Click on Assessment Feeding  Case Menu");
  				
  				Thread.sleep(2000);	
  				String acId = cdp.getAcIdFromExcelFile();
  				logger.info("Get Account id from Excel format");
  				afp.searchingElement(acId);
  				logger.info("Search Through acid in Assessment Feeding page");
  				List<String> allDetailsOnUI = afp.allDetails();
  				logger.info("Get all Record from  UI:- "+allDetailsOnUI);
  				List<String> assessmentFeedingDetailsDb = afcd.toverifyAllotAttendCaseDetailsInDatabase();
  				logger.info("Get all Record from  UI:- "+assessmentFeedingDetailsDb);
  				Assert.assertEquals(allDetailsOnUI, assessmentFeedingDetailsDb); 	
  				logger.info("Get all Record from  fetched from UI");
  				
 			} catch (Exception e) {
 				logger.error("Test case failed: " + e.getMessage());
 				e.printStackTrace();
 			}
        
 		}
  			 @Test(priority = 5)
  	  		public void AssessmentFeeding() {
  	  	    	hp=new HomePage(driver);
  	  	    	cdp=new CommonDataPage(driver);
  	  	    	afp=new AssessmentFeedingPage(driver);
  	  	    	rc=new RobotClass();
  	  	    	caseDetailsPage=new CaseDetailsPage(driver);
  	  	    	imm=new Irregularities_Main_Meter_HomePage(driver);
  	  	     	ipm=new Irregularities_Pole_Meter_HomePage(driver);
  	  	     	pf=new PropertyFile();
  	  	    	
  	  			try {
  	  				 hp.clickOnAssessmentFeedingCaseMenu();
  	  				 logger.info("Click on Assessment Feeding  Case Menu");
  	  				
  	  				Thread.sleep(2000);	
  	  				String acId = cdp.getAcIdFromExcelFile();
  	  				logger.info("Get Account id from Excel format");
  	  				afp.searchingElement(acId);
  	  			    logger.info("Search through AcId");
  	  				afp.clickOnCaseNoHyperLink();
  	  			    logger.info("Click ON Case no hyperLink");
  	  				Thread.sleep(2000);
  	  				afp.clickOnAssessmentDetailsDropdownList();
  	  			    logger.info("Click on Assessment Details Dropdownlist");
  	  				pf.AssessmentFeedingDetails();
  	  				afp.enterAssessmentUnit(pf.getProperty("assessmentUnit"));
  	  			    Thread.sleep(2000);
  	  			    logger.info("Enter assessment Unit");
  	  				afp.enterAssessmentAmount(pf.getProperty("assessmentAmount"));
  	  			    logger.info("Enter Assessment Amount");
  	  			    Thread.sleep(2000);
  	  			    String currentDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
  			        String day = currentDate.split("-")[0]; 
  		            String month = currentDate.split("-")[1];
  		            String year = currentDate.split("-")[2];
  		          afp.enterAssessmentDate(day);
  		          logger.info("Enter Day in Assessment Date field"); 
  		            afp.enterAssessmentDate(month);
  		          logger.info("Enter Month in Assessment Date field"); 
  	                rc.handleTabPress();
  	              afp.enterAssessmentDate(year);
  	               logger.info("Enter Year in Assessment Date field");  
  	  				afp.enterAssessmentRemark(pf.getProperty("remark"));
  	  			    Thread.sleep(2000);
  	  			    logger.info("Enter Assessment Remark");
  	  			    afp.uploadAssementEvedence();
  	  			    Thread.sleep(2000);
	  			    logger.info("Upload Assessment Evidance");
	  			    afp.clickOnSubmitButton();
	  			    logger.info("Click on Submit button");
	  			    Thread.sleep(2000);
	  		        Alert alert = driver.switchTo().alert();
	  		         String alertMessage = alert.getText();
	  			     assertEquals(alertMessage, "Data submitted successfully");
	  			     alert.accept();
	  				logger.info(alertMessage+" is Displayed!");
  	  			} catch (Exception e) {
  	  				logger.error("Error: " + e.getMessage());
  	  			}
}
  			@Test(priority = 6)
  			public void SavedAssessmentFeedingCaseDetailsInExcelFile() throws EncryptedDocumentException, ClassNotFoundException, SQLException, IOException {
  			    afcd = new AssessmentFeedingCaseDatabase();
  			    List<String> feedAssessmentDetailsOnDB = afcd.savedAssessmentFeedingDetailsInExcelFile();
  			      CommonDataPage cdp=new CommonDataPage(driver);
  			        for (String text : feedAssessmentDetailsOnDB) {
  			        	cdp.writeAssessmentFeedDetailsInExcelFile(text);
  			        }
  			       logger.info("Data successfully written to Excel file.");
  			    }
  			}

