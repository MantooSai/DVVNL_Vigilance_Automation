package com.live.pom;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AssessmentApprovalPage {
    WebDriver driver;

    public AssessmentApprovalPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
   
    @FindBy(xpath = "//h1[contains(text(),'Assessment Approval')]")
    private WebElement toCheckaasessmentApproval;
    
 
    
    @FindBy(xpath = " //button[.='Submit(EE)']")
    private WebElement clickOnSubmitButton;

    @FindBy(xpath = "//p[contains(text(),'Select Zone')]")
    private WebElement clickOnZoneDropdownList;
    
    @FindBy(xpath = "//div[@class=' w-12 h-6  rounded-full bg-zinc-700']")
    private WebElement clickOnAssessmentProposed ;
    
    
    @FindBy(xpath = "//p[text()='Review Case Details']")
    private WebElement clickOnReviewCaseDetailsDropdownList;
    
    @FindBy(xpath = "//input[@name='remark']")
    private WebElement enterRemark;
    
    @FindBy(xpath = "//button[contains(text(),'DVVNL-')]")
    private WebElement clickOnCaseNoHyperLink;
    @FindBy(xpath = "//p[contains(text(),'Select Division(s)')]")
    private WebElement clickOnDivisionDropdownList;

    @FindBy(xpath = "//input[@id='react-select-2-input']")
    private WebElement selectZone;

    @FindBy(xpath = "//input[@id='react-select-3-input']")
    private WebElement selectDivison;

    @FindBy(xpath = "//p[.='Assessment Approvals: Pendency']")
    private WebElement clickOnAssessmentApprovalsPendencydownList;

    @FindBy(xpath = "//h1[@class='italic font-semibold']")
    private WebElement countOfTotalApprovedCase;

    @FindBy(xpath = "//input[@placeholder='Search...']")
    private WebElement searchingElement;

    @FindBy(xpath = "//button[@data-tooltip-id='Excel']")
    private WebElement downloadReportInExcelFormat;

    @FindBy(xpath = "//button[@data-tooltip-id='Copy']")
    private WebElement copytheReportInTextFormat;

    @FindBy(xpath = "(//table[@class='MuiTable-root css-1dh5pc2']/tbody/tr)[1]/td")
    private List<WebElement> allDetail;

    public void clickOnAssessmentProposed() {
    	clickOnAssessmentProposed.click();
    }
    
    public void clickOnSubmitButton() {
    	clickOnSubmitButton.click();
    }
    public String AfterClickOnSubmitButtonThenAlertMessage() {
    	clickOnSubmitButton.click();
    	Alert alert = driver.switchTo().alert();
    	String text = alert.getText();
    	alert.accept();
		return text;
    }
    public void clickOnReviewCaseDetailsDropdownList() {
    	clickOnReviewCaseDetailsDropdownList.click();
    }
    public void enterRemark( String remark) {
    	enterRemark.sendKeys(remark);
    }
    
     
    public boolean toCheckaasessmentApprovalPageisDisplayed() {
        return toCheckaasessmentApproval.isDisplayed();
    }

    public void clickOnZoneDropdownList() {
        clickOnZoneDropdownList.click();
    }

    public void clickOnDivisionDropdownList() {
        clickOnDivisionDropdownList.click();
    }



    public void clickOnAssessmentApprovalsPendencydownList() {
        clickOnAssessmentApprovalsPendencydownList.click();
    }

    public String countOfTotalAssessmentApprovedCase() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement pendingCasesElement = wait.until(ExpectedConditions.visibilityOf(countOfTotalApprovedCase));
        
        String text = pendingCasesElement.getText().trim();
        // Remove all non-numeric characters to extract the count
        String countOfApprovedCase = text.replaceAll("\\D+", "");
        
        return countOfApprovedCase; // You can change this to return Integer.parseInt(countOfApprovedCase) if needed.
    }
    
    public void searchingElement(String enterElement) {
    	searchingElement.sendKeys(enterElement);
    }
    
    public void downloadReportInExcelFormat() {
    	downloadReportInExcelFormat.click();
    }
    
    public void copytheReportInTextFormat() {
    	copytheReportInTextFormat.click();
    }
    
    public List<String> allDetails(){
    	ArrayList<String>allDetails=new ArrayList<String>();
    	for(int i=0; i<allDetail.size(); i++) {
    		allDetails.add(allDetail.get(i).getText());
    	}
		return allDetails;
    	
    }
    public void selectZone(String zone) throws InterruptedException {
	    clickOnZoneDropdownList.click();  // Click on the dropdown to reveal the options
	    Thread.sleep(2000);
	    selectZone.sendKeys(zone);  // Type the zone name
	    // Wait and select the option if it appears in the list
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement zoneOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + zone + "')]")));
	    zoneOption.click();  // Click to select the filtered option
	}
    
    public void selectDivision(String division) throws InterruptedException {
	    clickOnDivisionDropdownList.click();  // Click on the dropdown to reveal options
	    Thread.sleep(2000);
	    selectDivison.sendKeys(division);  // Type the division name
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement divisionOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + division + "')]")));
	    divisionOption.click();
	}
    public void  clickOnCaseNoHyperLink() throws InterruptedException {
    	clickOnCaseNoHyperLink.click();
    	Thread.sleep(2000);
    }
 
}
