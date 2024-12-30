package com.vigilEye.test;



import java.awt.AWTException;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;
import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.utility.PropertyFile;
import com.utility.RobotClass;
import com.vigiEye.pom.AllotPendingCasePage;
import com.vigiEye.pom.CaseDetailsPage;
import com.vigiEye.pom.EvidencePage;
import com.vigiEye.pom.HomePage;
import com.vigiEye.pom.Irregularities_Main_Meter_HomePage;
import com.vigiEye.pom.Irregularities_Pole_Meter_HomePage;




public class Irregularities_Field extends BaseClass {

	 HomePage hp;
	 AllotPendingCasePage apc;
	  Irregularities_Main_Meter_HomePage imm;
	  RobotClass rc;
	  CaseDetailsPage cdp;

	    @BeforeMethod
	    public void openBrowser() throws InterruptedException {
	        driver.get(baseUrl);
	        logger.info("Enter Url!");
	        LoginPage lp = new LoginPage(driver);
	        lp.setUserName(userNameAt);
	        logger.info("Enter UserName! ");
	        lp.setPassword(passwordAt);
	        logger.info("Enter Password! ");
	        lp.clickLoginButton();
	        logger.info("Click on login Button! ");
	        hp = new HomePage(driver); // Ensure HomePage is initialized
	        hp.clickOnVigilEyeDropdownlist();
	        logger.info("click on VigilEye dropdown list ");
	        Thread.sleep(2000); // Consider replacing this with a wait until the element is visible
	        hp.clickOnAllotPendingMenu();
	        logger.info("Click on AllotPending Menu ");
	        apc=new AllotPendingCasePage(driver);
	       // Thread.sleep(4000);
		     apc.clickOnViewLink();
		     logger.info("Click on view hyperlink ");
		     cdp=new CaseDetailsPage(driver);
		     cdp.clickOnConsumerInformationDropdownlist();
		     Thread.sleep(4000);
		     logger.info("Consumer Inforation is showing ");
		     apc.clickOnMoveToFieldIrregularitiesPage();
		     logger.info("Move to Field Irregularities Page ");
		     Thread.sleep(3000);
		//driver.findElement(By.xpath("//div[@class='MuiAccordionSummary-expandIconWrapper Mui-expanded css-1fx8m19']")).click();
	}
	@AfterMethod
	public void close() {
		driver.quit();
		logger.info("Test completed");
	}
	@Test
	public void toverifyAlertMessage() {
		driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		alert.accept();
		Assert.assertEquals(alertMessage, "you have not filled main meter irregularity or irregularity evidance");
		logger.info(alertMessage+" Alert message is fetched.");
		System.out.println(alertMessage+" Alert message is Fetched");	
	}
	@Test(priority = 1)
	public void toFillTheIrregularitiesOfMainMeter() throws AWTException, InterruptedException {
	     imm = new Irregularities_Main_Meter_HomePage(driver);
	    PropertyFile pf=new PropertyFile();
	      pf.ConfigReaderMainMeter();
	    imm.clickOnIrregularitiesDropdownList();
	    logger.info("Clicked on Irregularities dropdown");
	     rc=new RobotClass();
	    imm.setTxtCT("CT");
	      rc.handleKeyPress();
	   logger.info("Entered CT value");
	    imm.setTxtCT_PT(pf.getProperty("CT_PT_Chamber"));
	    rc.handleKeyPress();
	    logger.info("Entered CT/PT value");
	    imm.setTxtMeter(pf.getProperty("Meter"));
	    rc.handleKeyPress();
	    logger.info("Entered Meter value");
	    imm.setTxtMeterBox(pf.getProperty("Meter_Box"));
	    rc.handleKeyPress();
	    logger.info("Entered Meter Box value");
	   imm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
	   rc.handleKeyPress();
	    
	    logger.info("Entered Meter Seal value");
	    imm.setTxtPT(pf.getProperty("PT"));
	    rc.handleKeyPress();
	
	    logger.info("Entered PT value");
	    imm.setTxtSearviceCable(pf.getProperty("Service_Cable"));
	    rc.handleKeyPress();
	    logger.info("Entered Service Cable value");
	    imm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
	    rc.handleKeyPress();
	    logger.info("Entered Other Irregularities value");
	    Thread.sleep(3000);
	    imm.clickOnIrregularitiesDropdownList();
	    logger.info("Clicked on Irregularities dropdown");
	    EvidencePage ep = new EvidencePage(driver);
	    Thread.sleep(3000);
	    ep.clickOnDropdownlist();
	    logger.info("Clicked on Evidence dropdown");
	    ep.uploadEvedence();
	    Thread.sleep(2000);
	    logger.info("Uploaded Evidence successfully");
	    ep.clickOnEvidencenextButton();
	    Thread.sleep(2000);
	    logger.info("Clicked on Evidence Next button");
	   ep.clickOnskipActionButton();
	 logger.info("Clicked on Skip Action button");
	    ep.clickOnActionnextButton();
	    logger.info("Clicked on Action Next button");
	    Thread.sleep(2000);
	    ep.clickOnSubmitButton();
	    Thread.sleep(3000);
	    logger.info("Clicked on Submit button");
	  /*  Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		alert.accept();                    //Please upload a file for at least one action
		Assert.assertEquals(alertMessage, "Please upload a file for at least one action");
		logger.info("Alert message is fetched.");
		*/
	}
	@Test(priority = 2)
	public void toverifyFillIrregularitiesOfPoleMeterWithoutFieldMainMeterIrreggularities() throws AWTException, InterruptedException {
	    Irregularities_Pole_Meter_HomePage imm = new Irregularities_Pole_Meter_HomePage(driver);
	    PropertyFile pf = new PropertyFile();
	    pf.ConfigReaderPoleMeter(); // Assuming this loads property file
	    imm.clickOnIrregularitiesDropdownList();
	    logger.info("Clicked on Irregularities for Pole meter dropdown");
	    //imm.clickToCheckIrregularitiesExist();
	    imm.clickToCheckIrregularitiesFound();
	    logger.info("Clicked on Irregularities found button");
	     rc = new RobotClass();
	    imm.setTxtCT("CT");
	    rc.handleKeyPress();
	    logger.info("Entered CT value");
	    imm.setTxtMf(pf.getProperty("MF"));
	    rc.handleKeyPress();
	    logger.info("Entered Mf value");
	    imm.setTxtMeter(pf.getProperty("Meter"));
	    rc.handleKeyPress();
	    logger.info("Entered Meter value");
	    imm.setTxtMeterBox(pf.getProperty("Meter_Box"));
	    rc.handleKeyPress();
	    logger.info("Entered Meter Box value");

	    imm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
	    rc.handleKeyPress();
	    logger.info("Entered Meter Seal value");

	    imm.setTxtPT(pf.getProperty("PT"));
	    rc.handleKeyPress();
	    logger.info("Entered PT value");

	    imm.setTxtServiceCable(pf.getProperty("Service_Cable"));
	    rc.handleKeyPress();
	    logger.info("Entered Service Cable value");

	    imm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
	    rc.handleKeyPress();
	    logger.info("Entered Other Irregularities value");

	    EvidencePage ep = new EvidencePage(driver);
	    Thread.sleep(3000);
	    ep.clickOnDropdownlist();
	    logger.info("Clicked on Evidence dropdown");
	    
	    ep.uploadEvedence();
	    logger.info("Uploaded Evidence successfully");

	    ep.clickOnEvidencenextButton();
	    logger.info("Clicked on Evidence Next button");
	    Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();
		alert.accept();
		Assert.assertEquals(alertMessage, "you have not filled main meter irregularity or irregularity evidance");
		logger.info("Alert message is fetched.");

	}
	@Test(priority = 3)

	public void toverifyIrregularitiesfiledMainMeterAndPoleMeter() throws InterruptedException, AWTException, IOException {
		 Irregularities_Main_Meter_HomePage imm = new Irregularities_Main_Meter_HomePage(driver);
		    PropertyFile pf=new PropertyFile();
		      pf.ConfigReaderMainMeter();
		    imm.clickOnIrregularitiesDropdownList();
		    logger.info("Clicked on Irregularities dropdown");
		     rc=new RobotClass();
		    imm.setTxtCT("CT");
		    rc.handleKeyPress();
		   logger.info("Entered CT value");
		  imm.setTxtCT_PT(pf.getProperty("CT_PT_Chamber"));
		    rc.handleKeyPress();
		    logger.info("Entered CT/PT value");
		    imm.setTxtMeter(pf.getProperty("Meter"));
		    rc.handleKeyPress();
		    logger.info("Entered Meter value");
		    
		    imm.setTxtMeterBox(pf.getProperty("Meter_Box"));
		    rc.handleKeyPress();
		    logger.info("Entered Meter Box value");
		   imm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
		   rc.handleKeyPress();
		    logger.info("Entered Meter Seal value");
		    imm.setTxtPT(pf.getProperty("PT"));
		    rc.handleKeyPress();
		    logger.info("Entered PT value");
		    imm.setTxtSearviceCable(pf.getProperty("Service_Cable"));
		    rc.handleKeyPress();
		    logger.info("Entered Service Cable value");
		    imm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
		    rc.handleKeyPress();
		    logger.info("Entered Other Irregularities value");
		    imm.clickOnIrregularitiesDropdownList();
		    logger.info("Clicked on Irregularities dropdown");
		    EvidencePage ep = new EvidencePage(driver);
		    Thread.sleep(2000);
	//      =================Irregularities field for Pole Meter==========================================================
		    Irregularities_Pole_Meter_HomePage ipm = new Irregularities_Pole_Meter_HomePage(driver);
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    Thread.sleep(2000);
		    js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		    ipm.clickOnIrregularitiesDropdownList();
		    logger.info("Clicked on Irregularities for Pole meter dropdown");
		    Thread.sleep(2000);
		    ipm.clickToCheckIrregularitiesFound();
		    Thread.sleep(2000);
		    logger.info("Clicked on Irregularities found button");
		    Thread.sleep(2000);
		    pf.ConfigReaderPoleMeter();
		    ipm.setTxtCT("CT");
		    rc.handleKeyPress();
		    logger.info("Entered CT value");
		    ipm.setTxtMf(pf.getProperty("MF"));
		    rc.handleKeyPress();
		    logger.info("Entered Mf value");
		    ipm.setTxtMeter(pf.getProperty("Meter"));
		    rc.handleKeyPress();
		    logger.info("Entered Meter value");
		    ipm.setTxtMeterBox(pf.getProperty("Meter_Box"));
		    rc.handleKeyPress();
		    logger.info("Entered Meter Box value");
		    ipm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
		    rc.handleKeyPress();
		    logger.info("Entered Meter Seal value");
		    ipm.setTxtPT(pf.getProperty("PT"));
		    rc.handleKeyPress();
		    logger.info("Entered PT value");
		    ipm.setTxtServiceCable(pf.getProperty("Service_Cable"));
		    rc.handleKeyPress();
		    logger.info("Entered Service Cable value");
		    ipm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
		    rc.handleKeyPress();
		   
		    logger.info("Entered Other Irregularities value");
		    
		   //EvidencePage ep = new EvidencePage(driver);
		    ep.clickOnDropdownlist();
		    logger.info("Clicked on Evidence dropdown");
		    ep.uploadEvedence();
		   // rc.handleKeyPress();
		   //Runtime.getRuntime().exec("C:\\Users\\yash_saini\\Desktop\\AutoIt\\fileUpload.exe");
		    // Thread.sleep(15000);
		    // Robot r=new Robot();
		    //rc.handleKeyPress();
		    logger.info("Uploaded Evidence successfully");
		    ep.clickOnEvidencenextButton();
		      logger.info("Clicked on Evidence Next button");
			  ep.clickOnskipActionButton();
			  Thread.sleep(2000);
			  logger.info("Clicked on Skip Action button");
			  ep.clickOnActionnextButton();
			  Thread.sleep(2000);
			  logger.info("Clicked on Action Next button"); 
			  ep.clickOnSubmitButton();
			  Thread.sleep(2000);
			  logger.info("Clicked on Submit button");
			
				  Alert alert = driver.switchTo().alert(); 
			     String text = alert.getText();
				 alert.accept(); 
				 logger.info(text);
				
			
	}
}