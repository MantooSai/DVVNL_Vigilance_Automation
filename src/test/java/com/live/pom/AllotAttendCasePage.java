package com.live.pom;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllotAttendCasePage {
	WebDriver driver;
    // Constructor
    public AllotAttendCasePage(WebDriver driver) {
    	this.driver = driver;
        PageFactory.initElements(driver, this);
    }

  
    // WebElements
    @FindBy(xpath = "//h1[@class='italic font-semibold']")
    private WebElement checkCountOfAllotAttendCases;
    
    @FindBy(xpath = "//tr[@class='MuiTableRow-root css-1gqug66']/td")
    private List<WebElement> allAllotAttendCaseDetails;

    @FindBy(xpath = "//input[@placeholder='Search...']")
    private WebElement checkSearchOption;

    @FindBy(xpath = "//button[@data-tooltip-id='Excel']")
    private WebElement downloadAllotAttendCasesInExcelFormat;

    @FindBy(xpath = "//input[@placeholder='Sr No']")
    private WebElement searchThroughSrNo;

    @FindBy(xpath = "//input[@placeholder='View']")
    private WebElement searchThroughView;

    @FindBy(xpath = "//input[@placeholder='Case_No']")
    private WebElement searchThroughCaseNo;

    @FindBy(xpath = "//input[@placeholder='AC_ID']")
    private WebElement searchThroughAC_ID;

    @FindBy(xpath = "//input[@placeholder='Name']")
    private WebElement searchThroughName;

    @FindBy(xpath = "//input[@placeholder='Address']")
    private WebElement searchThroughAddress;

    @FindBy(xpath = "//input[@placeholder='Priority']")
    private WebElement searchThroughPriority;

    @FindBy(xpath = "//input[@placeholder='Contract Demand']")
    private WebElement searchThroughContractDemand;

    @FindBy(xpath = "(//button[text()='View'])[1]")
    private WebElement clickOnViewLink;

    @FindBy(xpath = "(//button[@type='button'])[2]")
    private WebElement movetoFieldIrregularitiesPage;

    // Method to get the count of allot pending cases
    public String countOfAllotAttendCases() {
        return checkCountOfAllotAttendCases.getText();
    }

    // Method to search using search option input
    public void searchOption(String searchText) {
        checkSearchOption.sendKeys(searchText);
    }

    // Methods to search through different fields
    public void searchThroughSrNo(String searchThroughSrNoText) {
        searchThroughSrNo.sendKeys(searchThroughSrNoText);
    }

    public void searchThroughView(String searchThroughViewText) {
        searchThroughView.sendKeys(searchThroughViewText);
    }

    public void searchThroughCaseNo(String searchThroughCaseNoText) {
        searchThroughCaseNo.sendKeys(searchThroughCaseNoText);
    }

    public void searchThroughAC_ID(String searchThroughAC_IDText) {
        searchThroughAC_ID.sendKeys(searchThroughAC_IDText);
    }

    public void searchThroughName(String searchThroughNameText) {
        searchThroughName.sendKeys(searchThroughNameText);
    }

    public void searchThroughAddress(String searchThroughAddressText) {
        searchThroughAddress.sendKeys(searchThroughAddressText);
    }

    public void searchThroughPriority(String searchThroughPriorityText) {
        searchThroughPriority.sendKeys(searchThroughPriorityText);
    }

    public void searchThroughContractDemand(String searchThroughContractDemandText) {
        searchThroughContractDemand.sendKeys(searchThroughContractDemandText);
    }

    // Method to download report in Excel format
    public void downloadReportInExcelFormat() {
        downloadAllotAttendCasesInExcelFormat.click();
    }

    // Method to click on View link
    public void clickOnViewLink() {
        clickOnViewLink.click();
    }

    // Method to navigate to Field Irregularities page
    public void clickOnMoveToFieldIrregularitiesPage() {
        movetoFieldIrregularitiesPage.click();
    }
    
    public List<String> allAllotPendingCaseDetails() {
    	 ArrayList<String> alltPendingCasesDetailsOnUI = new ArrayList<>();
		    for (int i = 2; i < allAllotAttendCaseDetails.size(); i++) {
		        alltPendingCasesDetailsOnUI.add(allAllotAttendCaseDetails.get(i).getText().trim());
		    }
		    return alltPendingCasesDetailsOnUI;
    }
    
    public String checkCountOfAllotAttendCases() throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement attendCasesElement = wait.until(
				ExpectedConditions.visibilityOf(checkCountOfAllotAttendCases)
				);
		Thread.sleep(3000);
		String text = attendCasesElement.getText().trim();
		Thread.sleep(3000);
		String consumerOfAttendCaseInUI = text.replaceAll("\\D+", "");
		return consumerOfAttendCaseInUI;
    }
}


