package com.live.pom;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RealisationInputPage {
	WebDriver driver;

    public RealisationInputPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'REALISATION INPUTS')]")
    private WebElement clickOnAssessmentRealisationMenu;
    
    @FindBy(xpath = "//input[@id='fromdate']")
    private WebElement enterFromDate;
    
    @FindBy(xpath = "//input[@id='uptodate']")
    private WebElement enterUptoDate;
    
    @FindBy(xpath = "//button[.='Submit']")
    private WebElement clickOnSubmitButton;
    	
     @FindBy(xpath = "(//table[@class='MuiTable-root css-1dh5pc2']//tbody/tr)[1]/td[5]/button")
     private WebElement countOfRealisedCaseDivisionWise;
    
     @FindBy(xpath = "//p[contains(text(),'Realisation Details')]")
     private WebElement realisationDetailsDropdownlist;
     
     @FindBy(xpath = "(//table[@class='MuiTable-root css-124f4w4'])[2]//tbody/tr/td")
      private List<WebElement> realisationInputDetails;
     
     public void clickOnAssessmentRealisationMenu() {
    	 clickOnAssessmentRealisationMenu.click();
     }
     
     public void enterFromDate(String fromDate) throws InterruptedException {
    	 enterFromDate.sendKeys(fromDate);
    	 Thread.sleep(2000);
     }
     
     public void enterUptoDate(String uptoDate) throws InterruptedException {
    	 enterUptoDate.sendKeys(uptoDate);
    	 Thread.sleep(2000);
     }
     
     public void clickOnSubmitButton() throws InterruptedException {
    	 clickOnSubmitButton.click();
    	 Thread.sleep(2000);
     }
     
     public void realisationDetailsDropdownlist() {
    	 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(realisationDetailsDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", realisationDetailsDropdownlist);
		    }
     }
}
