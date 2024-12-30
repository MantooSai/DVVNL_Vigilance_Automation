package com.vigilEye.allotPendintCase;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;



import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.baseClass.BaseClass2;
import com.baseClass.LoginPage;
import com.utility.PropertyFile;
import com.vigiEye.pom.EvidencePage;
import com.vigiEye.pom.Irregularities_Main_Meter_HomePage;
import com.vigiEye.pom.Irregularities_Pole_Meter_HomePage;


public class Irregularities_Field extends BaseClass2 {



	@BeforeMethod
	public void openBrowser() throws InterruptedException {
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
		driver.findElement(By.xpath("//button[.='View']")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
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
	    Irregularities_Main_Meter_HomePage imm = new Irregularities_Main_Meter_HomePage(driver);
	    PropertyFile pf=new PropertyFile();
	      pf.ConfigReaderMainMeter();
	    imm.clickOnIrregularitiesDropdownList();
	    logger.info("Clicked on Irregularities dropdown");
	      Robot r=new Robot();
	    imm.setTxtCT("CT");
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	   logger.info("Entered CT value");
	    imm.setTxtCT_PT(pf.getProperty("CT_PT_Chamber"));
	     r.keyPress(KeyEvent.VK_ENTER);
	     r.keyRelease(KeyEvent.VK_ENTER);  
	    logger.info("Entered CT/PT value");
	    imm.setTxtMeter(pf.getProperty("Meter"));
	     r.keyPress(KeyEvent.VK_ENTER);
	     r.keyRelease(KeyEvent.VK_ENTER);
	    logger.info("Entered Meter value");
	    imm.setTxtMeterBox(pf.getProperty("Meter_Box"));
	    r.keyPress(KeyEvent.VK_ENTER);
	     r.keyRelease(KeyEvent.VK_ENTER);
	    logger.info("Entered Meter Box value");
	   imm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
	    r.keyPress(KeyEvent.VK_ENTER);
	     r.keyRelease(KeyEvent.VK_ENTER);
	     Thread.sleep(2000);
	    logger.info("Entered Meter Seal value");
	    imm.setTxtPT(pf.getProperty("PT"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	     Thread.sleep(2000);
	    logger.info("Entered PT value");
	    imm.setTxtSearviceCable(pf.getProperty("Service_Cable"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	     Thread.sleep(2000);
	    logger.info("Entered Service Cable value");
	    imm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	     Thread.sleep(2000);
	    logger.info("Entered Other Irregularities value");
	    Thread.sleep(3000);
	    imm.clickOnIrregularitiesDropdownList();
	    logger.info("Clicked on Irregularities dropdown");
	    EvidencePage ep = new EvidencePage(driver);
	    Thread.sleep(3000);
	    ep.clickOnDropdownlist();
	    logger.info("Clicked on Evidence dropdown");
	    ep.uploadEvedence();
	    logger.info("Uploaded Evidence successfully");
	    ep.clickOnEvidencenextButton();
	    logger.info("Clicked on Evidence Next button");
	    ep.clickOnskipActionButton();
	    logger.info("Clicked on Skip Action button");
	    ep.clickOnActionnextButton();
	    logger.info("Clicked on Action Next button");
	    ep.clickOnSubmitButton();
	    logger.info("Clicked on Submit button");
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
	    Robot r = new Robot();
	    imm.setTxtCT("CT");
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered CT value");
	    imm.setTxtMf(pf.getProperty("MF"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered Mf value");
	    imm.setTxtMeter(pf.getProperty("Meter"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered Meter value");
	    imm.setTxtMeterBox(pf.getProperty("Meter_Box"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered Meter Box value");

	    imm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered Meter Seal value");

	    imm.setTxtPT(pf.getProperty("PT"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered PT value");

	    imm.setTxtServiceCable(pf.getProperty("Service_Cable"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
	    logger.info("Entered Service Cable value");

	    imm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
	    r.keyPress(KeyEvent.VK_ENTER);
	    r.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(2000);
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

	public void toverifyIrregularitiesfiledMainMeterAndPoleMeter() throws InterruptedException, AWTException {
		 Irregularities_Main_Meter_HomePage imm = new Irregularities_Main_Meter_HomePage(driver);
		    PropertyFile pf=new PropertyFile();
		      pf.ConfigReaderMainMeter();
		    imm.clickOnIrregularitiesDropdownList();
		    logger.info("Clicked on Irregularities dropdown");
		      Robot r=new Robot();
		    imm.setTxtCT("CT");
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		   logger.info("Entered CT value");
		    imm.setTxtCT_PT(pf.getProperty("CT_PT_Chamber"));
		     r.keyPress(KeyEvent.VK_ENTER);
		     r.keyRelease(KeyEvent.VK_ENTER);  
		    logger.info("Entered CT/PT value");
		    imm.setTxtMeter(pf.getProperty("Meter"));
		     r.keyPress(KeyEvent.VK_ENTER);
		     r.keyRelease(KeyEvent.VK_ENTER);
		    logger.info("Entered Meter value");
		    imm.setTxtMeterBox(pf.getProperty("Meter_Box"));
		    r.keyPress(KeyEvent.VK_ENTER);
		     r.keyRelease(KeyEvent.VK_ENTER);
		    logger.info("Entered Meter Box value");
		   imm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
		    r.keyPress(KeyEvent.VK_ENTER);
		     r.keyRelease(KeyEvent.VK_ENTER);
		     Thread.sleep(2000);
		    logger.info("Entered Meter Seal value");
		    imm.setTxtPT(pf.getProperty("PT"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		     Thread.sleep(2000);
		    logger.info("Entered PT value");
		    imm.setTxtSearviceCable(pf.getProperty("Service_Cable"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		     Thread.sleep(2000);
		    logger.info("Entered Service Cable value");
		    imm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		     Thread.sleep(2000);
		    logger.info("Entered Other Irregularities value");
		    Thread.sleep(3000);
		    imm.clickOnIrregularitiesDropdownList();
		    logger.info("Clicked on Irregularities dropdown");
		    EvidencePage ep = new EvidencePage(driver);
		    Thread.sleep(3000);
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
		    ipm.setTxtCT("CT");
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered CT value");
		    ipm.setTxtMf(pf.getProperty("MF"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered Mf value");
		    ipm.setTxtMeter(pf.getProperty("Meter"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered Meter value");
		    ipm.setTxtMeterBox(pf.getProperty("Meter_Box"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered Meter Box value");

		    ipm.setTxtMeterSeal(pf.getProperty("Meter_Seals"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered Meter Seal value");

		    ipm.setTxtPT(pf.getProperty("PT"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered PT value");

		    ipm.setTxtServiceCable(pf.getProperty("Service_Cable"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered Service Cable value");

		    ipm.setTxtOtherIrregularities(pf.getProperty("Other_Irregularity"));
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    Thread.sleep(2000);
		    logger.info("Entered Other Irregularities value");
		    Thread.sleep(3000);
		    ep.clickOnDropdownlist();
		    logger.info("Clicked on Evidence dropdown");
		    
		    ep.uploadEvedence();
		    logger.info("Uploaded Evidence successfully");

		    ep.clickOnEvidencenextButton();
		      logger.info("Clicked on Evidence Next button");
			  ep.clickOnskipActionButton(); logger.info("Clicked on Skip Action button");
			  
			  ep.clickOnActionnextButton(); logger.info("Clicked on Action Next button");
			  
			  ep.clickOnSubmitButton(); logger.info("Clicked on Submit button");
			
	}
}