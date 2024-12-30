package com.live.pom;

import java.nio.file.Paths;
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

public class AssessmentFeedingPage {
    WebDriver driver;

    public AssessmentFeedingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
   
    @FindBy(xpath = "//h1[contains(text(),'Assessment Feeding')]")
    private WebElement toCheckaasessmentFeeding;
    
 
    
    @FindBy(xpath = "//button[.='Submit']")
    private WebElement clickOnSubmitButton;

    
    @FindBy(xpath = "//div[@class=' w-12 h-6  rounded-full bg-zinc-700']")
    private WebElement clickOnAssessmentProposed ;
    
    
    @FindBy(xpath = "//p[text()='Assessment Details']")
    private WebElement clickOnAssessmentDetailsDropdownList;
    
 
    
    @FindBy(xpath = "//button[contains(text(),'DVVNL-')]")
    private WebElement clickOnCaseNoHyperLink;
    
    @FindBy(xpath = "//input[@name='ass_unit']")
    private WebElement assessmentUnit;
    
    @FindBy(xpath = "//input[@name='ass_amount']")
    private WebElement assessmentAmount;
    
    @FindBy(xpath = "//input[@name='ass_date']")
    private WebElement assessmentDate;
 
    @FindBy(xpath = "//input[@name='remark']")
    private WebElement assessmentRemark;
    
  
    
    @FindBy(xpath = "//input[@id='upload_evidence']")
    private WebElement uploadAssessmentEvidance;

    @FindBy(xpath = "//p[.='Assessment Feeding: Pendency']")
    private WebElement clickOnAssessmentApprovalsPendencydownList;

    @FindBy(xpath = "//h1[@class='italic font-semibold']")
    private WebElement countOfTotalAssessementCase;

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
    
    public void enterAssessmentUnit(String assementUnit) {
    	assessmentUnit.sendKeys(assementUnit);
    }
    public void enterAssessmentAmount(String assementAmt) {
    	assessmentAmount.sendKeys(assementAmt);
    }
    public void enterAssessmentDate(String assementDate) {
    	assessmentDate.sendKeys(assementDate);
    }
    public void enterAssessmentRemark(String assementRemark) {
    	assessmentRemark.sendKeys(assementRemark);
    }
    
    public void uploadAssementEvedence() {
   	 String userDirectory = System.getProperty("user.dir");
        String filePath = Paths.get(userDirectory, "TestData", "AssessmentFeeding", "sheetal.pdf").toString();
        uploadAssessmentEvidance.sendKeys(filePath);
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
    public void clickOnAssessmentDetailsDropdownList() {
    	clickOnAssessmentDetailsDropdownList.click();
    }
   
 
     
    public boolean toCheckaasessmentFeedingPageisDisplayed() {
        return toCheckaasessmentFeeding.isDisplayed();
    }

    public void clickOnAssessmentApprovalsPendencydownList() {
        clickOnAssessmentApprovalsPendencydownList.click();
    }

    public String countOfTotalAssessmentCase() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement pendingCasesElement = wait.until(ExpectedConditions.visibilityOf(countOfTotalAssessementCase));
        
        String text = pendingCasesElement.getText().trim();
        // Remove all non-numeric characters to extract the count
        String countOfAssesmentCase = text.replaceAll("\\D+", "");
         Thread.sleep(2000);
        return countOfAssesmentCase; // You can change this to return Integer.parseInt(countOfApprovedCase) if needed.
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
    	for(int i=1; i<allDetail.size(); i++) {
    		allDetails.add(allDetail.get(i).getText());
    	}
		return allDetails;
    	
    }
    
    public void  clickOnCaseNoHyperLink() throws InterruptedException {
    	clickOnCaseNoHyperLink.click();
    	Thread.sleep(2000);
    }
 
}
