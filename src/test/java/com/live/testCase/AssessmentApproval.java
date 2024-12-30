package com.live.testCase;

import static org.testng.Assert.assertEquals;

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

import com.baseClass.BaseClass;
import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.utility.PropertyFile;
import com.utility.RobotClass;
import com.vigiEye.pom.AssessmentApprovalPage;
import com.vigiEye.pom.CaseDetailsPage;
import com.vigiEye.pom.CommonDataPage;
import com.vigiEye.pom.HomePage;
import com.vigiEye.pom.Irregularities_Main_Meter_HomePage;
import com.vigiEye.pom.Irregularities_Pole_Meter_HomePage;
import com.vigilEye.allotPendintCaseDatabase.AssessmentApprovalCaseDataBase;
import com.vigilEye.allotPendintCaseDatabase.AssessmentFeedingCaseDatabase;


public class AssessmentApproval  extends BaseClass{
	HomePage hp;
	CommonDataPage cdp;
	AssessmentApprovalPage aap;
	RobotClass rc;
	CaseDetailsPage caseDetailsPage;
	Irregularities_Main_Meter_HomePage imm;
	Irregularities_Pole_Meter_HomePage ipm;
	PropertyFile pf;
	AssessmentApprovalCaseDataBase apcd;
	@BeforeMethod
	public void openBrowser() throws InterruptedException {
		driver.get(baseUrl);
		logger.info("Open Url");
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(userNameEt);
		logger.info("Entered UserId");
		lp.setPassword(passwordEt);
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
	public void toVerifyAssessmentApprovalIsDisplaying() {
		WebElement menu = driver.findElement(By.xpath("//*[contains(text(),'ASSESSMENT APPROVAL')]"));
		boolean isDisplayed = menu.isDisplayed();
		Assert.assertTrue(isDisplayed, "ASSESSMENT APPROVAL");
		logger.info("Assessment Approval is Displaying");
	}

	@Test(priority = 1)
	public void toVerifyClickOnAssessmentApprovalCaseMenu() {
		hp=new HomePage(driver);
		try {
			hp.clickOnAssessmentApprovalCaseMenu();
			logger.info("Click on Assessment Approval Case Menu");
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
		cdp=new CommonDataPage(driver);
		aap=new AssessmentApprovalPage(driver);
		rc=new RobotClass();
		try {
			hp.clickOnAssessmentApprovalCaseMenu();
			logger.info("Click on Assessment Approval Case Menu");
			aap.selectZone(zoneName);
			//rc.handleKeyPress();
			logger.info("Select Zone from dropdown list");
			aap.selectDivision(divisionName);
			//rc.handleKeyPress();
			logger.info("Select Circle from dropdown list");
			aap.clickOnAssessmentApprovalsPendencydownList();
			logger.info("Click on Assessment Approvals Pendency Dropdown list");
			List<String> headerNameAsPerDesign = cdp.headerNameOfAssesmentApprovedCase();
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
		aap=new AssessmentApprovalPage(driver);
		rc=new RobotClass();
		apcd=new AssessmentApprovalCaseDataBase();
		try {
			hp.clickOnAssessmentApprovalCaseMenu();
			logger.info("Click on Assessment Approval Case Menu");
			aap.selectZone(zoneName);
			//rc.handleKeyPress();
			logger.info("Select Zone from dropdown list");
			aap.selectDivision(divisionName);
			//rc.handleKeyPress();
			logger.info("Select Circle from dropdown list");
			aap.clickOnAssessmentApprovalsPendencydownList();
			logger.info("Click on Assessment Approvals Pendency Dropdown list");
			Thread.sleep(2000);	
			String totalCountOnUI = aap.countOfTotalAssessmentApprovedCase();
			String totalCountOnDB = apcd.toverifyCountAssessmentApprovalCaseInDatabase();
			Assert.assertEquals(totalCountOnUI, totalCountOnDB);
			logger.info("Total Count of Assessment aproval case is fetched");
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}

	}
	@Test(priority = 4)
	public void toveriyWithoutEnterRemarkAndClickOnSubmitButtonThenAlertMessageIsDisplaying() {
		hp=new HomePage(driver);
		cdp=new CommonDataPage(driver);
		aap=new AssessmentApprovalPage(driver);
		rc=new RobotClass();
		caseDetailsPage=new CaseDetailsPage(driver);
		imm=new Irregularities_Main_Meter_HomePage(driver);
		ipm=new Irregularities_Pole_Meter_HomePage(driver);
		pf=new PropertyFile();

		try {
			hp.clickOnAssessmentApprovalCaseMenu();
			logger.info("Click on Assessment Approval Case Menu");
			aap.selectZone(zoneName);
			//rc.handleKeyPress();
			logger.info("Select Zone from dropdown list");
			aap.selectDivision(divisionName);
			//rc.handleKeyPress();
			logger.info("Select Circle from dropdown list");
			aap.clickOnAssessmentApprovalsPendencydownList();
			logger.info("Click on Assessment Approvals Pendency Dropdown list");
			Thread.sleep(2000);	
			String acId = cdp.getAcIdFromExcelFile();
			logger.info("Get Account id from Excel format");
			aap.searchingElement(acId);
			logger.info("Search Through acid in Assessment Approval page");
			aap.clickOnCaseNoHyperLink();
			logger.info("Click on CaseNo HyperLink in  Assessment Approval page");
			aap.clickOnReviewCaseDetailsDropdownList();
			logger.info("Click on Review Case Details Dropdownlist");
			aap.clickOnAssessmentProposed();
			logger.info("Click on Assessment Proposed");
			// aap.enterRemark(pf.getProperty("remark"));
			//logger.info("Enter Remark ");
			String alertMessage = aap.AfterClickOnSubmitButtonThenAlertMessage();
			assertEquals(alertMessage, "Please Fill Assessment Proposed Remark");
			logger.info(alertMessage+" is Displayed!");



		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}

	}
	@Test(priority = 5)
	public void toveriyEnterRemarkAndClickOnSubmitButtonThenDataIsSavedInTable() {
		hp=new HomePage(driver);
		cdp=new CommonDataPage(driver);
		aap=new AssessmentApprovalPage(driver);
		rc=new RobotClass();
		caseDetailsPage=new CaseDetailsPage(driver);
		imm=new Irregularities_Main_Meter_HomePage(driver);
		ipm=new Irregularities_Pole_Meter_HomePage(driver);
		pf=new PropertyFile();

		try {
			hp.clickOnAssessmentApprovalCaseMenu();
			logger.info("Click on Assessment Approval Case Menu");
			aap.selectZone(zoneName);
			//rc.handleKeyPress();
			logger.info("Select Zone from dropdown list");
			aap.selectDivision(divisionName);
			//rc.handleKeyPress();
			logger.info("Select Circle from dropdown list");
			aap.clickOnAssessmentApprovalsPendencydownList();
			logger.info("Click on Assessment Approvals Pendency Dropdown list");
			Thread.sleep(2000);	
			String acId = cdp.getAcIdFromExcelFile();
			logger.info("Get Account id from Excel format");
			aap.searchingElement(acId);
			logger.info("Search Through acid in Assessment Approval page");
			aap.clickOnCaseNoHyperLink();
			logger.info("Click on CaseNo HyperLink in  Assessment Approval page");

			aap.clickOnReviewCaseDetailsDropdownList();
			logger.info("Click on Review Case Details Dropdownlist");
			aap.clickOnAssessmentProposed();
			logger.info("Click on Assessment Proposed");
			pf.AssessmentProposedRemark();
			aap.enterRemark(pf.getProperty("remark"));
			logger.info("Enter Remark ");
			Thread.sleep(2000);
			aap.clickOnSubmitButton();
			String alertMessage = aap.AfterClickOnSubmitButtonThenAlertMessage();
			assertEquals(alertMessage, "data uploaded successfully");
			logger.info(alertMessage+" is Displayed!");

		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
	}
	@Test(priority = 6)
	public void toSavedAssessmentProposedDetailsInExcelFile() throws EncryptedDocumentException, ClassNotFoundException, SQLException, IOException {
		apcd=new AssessmentApprovalCaseDataBase();
		List<String> AssessmentProposedDetailsOnDB = apcd.assessmentProposedDetailsInDatabase();
		CommonDataPage cdp=new CommonDataPage(driver);
		for (String text : AssessmentProposedDetailsOnDB) {
			cdp.writeAssessmentProposedDetailsInExcelFile(text);
		}
		logger.info("Data successfully written to Excel file.");
	}

}
