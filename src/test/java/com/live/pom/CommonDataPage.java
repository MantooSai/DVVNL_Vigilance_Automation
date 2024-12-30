package com.live.pom;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonDataPage {
	private static final String actionSkipDetailsPath = "./TestData/Action/ActionSkipDetails.xlsx";
	private static final String actionDetailsPath = "./TestData/Action/ActionDetails.xlsx";
	WebDriver driver;
	public String caseNo = null;
	public static String acId=null;
	public String actionRequiredTag= null;
	public String action= null;
	public String otheraction= null;
	public String evidencePath= null;
	public String evidenceName= null;
	public String ActionBy= null;

	public CommonDataPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@name='selectZone']")
    private WebElement selectZone;
	
	@FindBy(xpath = "//input[@name='selectZone']")
    private WebElement selectCircle;
	
	@FindBy(xpath = "//input[@name='selectZone']")
    private WebElement selectDivision;
	
	@FindBy(xpath = "//p[contains(text(),'Select Zone')]")
    private WebElement clickOnZoneDropdownList;
	
	@FindBy(xpath = "//p[contains(text(),'Select Circle')]")
    private WebElement clickOnCircleDropdownList; 
	
	@FindBy(xpath = "//p[contains(text(),'Select Division')]")
    private WebElement clickOnDivisionDropdownList; 
	
	@FindBy(xpath = "//p[contains(text(),'Consumer Information')]")
	private WebElement consumerInformationDropdownlist;
	
	@FindBy(xpath = "//div[@class='grid sm:grid-cols-5 mb-2 p-4']//p")
	private List<WebElement> consumerInformationDetails;
	
	
	@FindBy(xpath = "//p[contains(text(),'Main Meter')]")
	private WebElement irregularitiesMainMeterDropdownlist;
	
	
	@FindBy(xpath = "(//div[@class='irregularities flex-col p-4'])[1]//li")
	private List<WebElement> irregularitiesMainMeterDetails;
	
    @FindBy(xpath = "//button[contains(text(),'DVVNL-')]")
    private WebElement clickOnCaseNoHyperLink;
     
    
    @FindBy(xpath = "//input[@placeholder='Search...']")
    private WebElement searchingElement;
    
    @FindBy(xpath = "//h1[@class='italic font-semibold']")
    private WebElement totalCount;
    
    @FindBy(xpath = "(//table[@class='MuiTable-root css-1dh5pc2']/tbody/tr)[1]/td")
    private List<WebElement> allDetail;
	
	
	
	@FindBy(xpath = "//p[contains(text(),'Pole Meter')]")
	private WebElement irregularitiesPoleMeterDropdownlist;
	
	@FindBy(xpath = "(//div[@class='irregularities flex-col p-4'])[2]//li")
	private List<WebElement> irregularitiesPoleMeterDetails;
	
	@FindBy(xpath = "//p[contains(text(),'Marking')]")
	private WebElement markingDetailsDropdownlist;
	
			
	@FindBy(xpath = "(//table[@class='MuiTable-root css-124f4w4']//tbody/tr)[1]/td")
	private List<WebElement> markingDetails;
	
	@FindBy(xpath = "//p[contains(text(),'Action')]")
	private WebElement actionDropdownlist;
	
	@FindBy(xpath = "//div[@class='action flex-col p-4']//li")
	private List<WebElement> actionDetails;
	
	@FindBy(xpath = "//p[contains(text(),'Case Review')]")
	private WebElement caseReviewDropdownlist;
	
	@FindBy(xpath = "//div[@class='flex flex-col p-4']//p")
	private List<WebElement> caseReviewDetails;
	
	@FindBy(xpath = "//p[contains(text(),'Assessment Details')]")
	private WebElement assessmentDetailsDropdownlist;
	
	@FindBy(xpath = "//div[@class='grid  sm:grid-cols-2 p-2 sm:p-4 ml-4 ']//p")
	private List<WebElement> assessmentDetails;
	
	@FindBy(xpath = "//p[contains(text(),'Realisation Details (to be provided)')]")
	private WebElement realisationDetailsDropdownlist;
	
	@FindBy(xpath = "//p[contains(text(),'Previous Payment')]")
	private WebElement previousPaymentDropdownlist;
	
	@FindBy(xpath = "//table[@class='MuiTable-root css-1dh5pc2']//span")
	private List<WebElement> headerName;

	@FindBy(xpath = "(//table[@class='MuiTable-root css-1dh5pc2']//tr)[2]/td[5]")
	private WebElement writeAcIdInExcelFormat;

	@FindBy(xpath = "(//td[contains(text(),'DVVNL-')])[1]")
	private WebElement writeCaseNoInExcelFormat;
	
	
	public void clickOnZoneDropdownList() {
		clickOnZoneDropdownList.click();
	}
	
	public void clickOnDivisionDropdownList() {
		clickOnDivisionDropdownList.click();
	}
	
	public void clickOnCircleDropdownList() {
		clickOnCircleDropdownList.click();
	}
	
	   public String countOfTotalAssessmentCase() throws InterruptedException {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement pendingCasesElement = wait.until(ExpectedConditions.visibilityOf(totalCount));
	        
	        String text = pendingCasesElement.getText().trim();
	        // Remove all non-numeric characters to extract the count
	        String countOfAssesmentCase = text.replaceAll("\\D+", "");
	         Thread.sleep(2000);
	        return countOfAssesmentCase; // You can change this to return Integer.parseInt(countOfApprovedCase) if needed.
	    }
	
    public void searchingElement(String enterElement) {
    	searchingElement.sendKeys(enterElement);
    }
	
	 public void  clickOnCaseNoHyperLink() throws InterruptedException {
	    	clickOnCaseNoHyperLink.click();
	    	Thread.sleep(2000);
	   }
	
	public void consumerInformationDropdownlist() {
		 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(consumerInformationDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", consumerInformationDropdownlist);
		    }
	}
	public List<String> consumerInformationDetails() {
		ArrayList<String>consumerDetails=new ArrayList<String>();
		for(int i=0; i<consumerInformationDetails.size(); i++) {
			consumerDetails.add(consumerInformationDetails.get(i).getText());	
		}
		return consumerDetails;
	}
	public void irregularitiesMainMeterDropdownlist() {
		 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(irregularitiesMainMeterDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", irregularitiesMainMeterDropdownlist);
		    }
	}
	public List<String> irregularitiesMainMeterDetails() {
		ArrayList<String>irregularitiesMainDetails=new ArrayList<String>();
		for(int i=0; i<irregularitiesMainMeterDetails.size(); i++) {
			irregularitiesMainDetails.add(irregularitiesMainMeterDetails.get(i).getText());	
		}
		return irregularitiesMainDetails;
	}
	public void irregularitiesPoleMeterDropdownlist() {
		 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(irregularitiesPoleMeterDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", irregularitiesPoleMeterDropdownlist);
		    }
	}
	public List<String> irregularitiesPoleMeterDetails() {
		ArrayList<String>irregularitiesPoleDetails=new ArrayList<String>();
		for(int i=0; i<irregularitiesPoleMeterDetails.size(); i++) {
			irregularitiesPoleDetails.add(irregularitiesPoleMeterDetails.get(i).getText());	
		}
		return irregularitiesPoleDetails;
	}
	
	public void markingDetailsDropdownlist() {
		  try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(markingDetailsDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", markingDetailsDropdownlist);
		    }
	}
	public List<String> markingDetails() {
		ArrayList<String>markingDetail=new ArrayList<String>();
		for(int i=0; i<markingDetails.size(); i++) {
			markingDetail.add(markingDetails.get(i).getText());	
		}
		return markingDetail;
	}
	
	public void actionDropdownlist() {
		  try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(actionDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionDropdownlist);
		    }
	}
	public List<String> actionDetails() {
		ArrayList<String>actionDetail=new ArrayList<String>();
		for(int i=0; i<actionDetails.size(); i++) {
			actionDetail.add(actionDetails.get(i).getText());	
		}
		return actionDetail;
	}
	
    public List<String> allDetails(){
    	ArrayList<String>allDetails=new ArrayList<String>();
    	for(int i=1; i<allDetail.size(); i++) {
    		allDetails.add(allDetail.get(i).getText());
    	}
		return allDetails;
    }
	public void caseReviewDropdownlist() {
		 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(caseReviewDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", caseReviewDropdownlist);
		    }
	}
	public List<String> caseReviewDetails() {
		ArrayList<String>caseReviewDetail=new ArrayList<String>();
		for(int i=0; i<caseReviewDetails.size(); i++) {
			caseReviewDetail.add(caseReviewDetails.get(i).getText());	
		}
		return caseReviewDetail;
	}

	
	
	public void assessmentDetailsDropdownlist() {
		 try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(assessmentDetailsDropdownlist));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		        element.click();
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", assessmentDetailsDropdownlist);
		    }
	}
	public List<String> assessmentDetails() {
		ArrayList<String>assessmentDetail=new ArrayList<String>();
		for(int i=0; i<assessmentDetails.size(); i++) {
			assessmentDetail.add(assessmentDetails.get(i).getText());	
		}
		return assessmentDetail;
	}
	
	public List<String> headerName() {
		ArrayList<String> headerNameDetails = new ArrayList<>();
		for (int i =0; i < headerName.size(); i++) {
			headerNameDetails.add(headerName.get(i).getText().trim());
		}
		return headerNameDetails;
	}

	public List<String> headerNameOfAllotAttendCase() {
		String[] headerName = {"Sr No", "View", "Division", "Case_No", "AC_ID", "Name", "Address", "Contract Demand", "Priority"};
		ArrayList<String> headerList = new ArrayList<>(Arrays.asList(headerName));
		return headerList;
	}

	public List<String> headerNameOfAssessmentFeedingCase() {
		String[] headerName = {"Sr No","Zone","Circle","Division", "Priority", "Case_No", "ACC ID", "Name", "Address", "Supply Type", "Irregularities","Actions","Assessment Proposed","Feed Remark",};
		ArrayList<String> headerList = new ArrayList<>(Arrays.asList(headerName));
		return headerList;
	}

	public List<String> headerNameOfAssessmentRealisationCase() {
		String[] headerName = {"Sr No","Zone","Circle","Division","Priority", "Case_No", "ACC_ID", "Name", "Address", "Supply Type", "Irregularities","Actions","Assessment Proposed","Assessment Unit","Assessment Amount","Realisation Amount","Balance Amount","Feed Remark"};
		ArrayList<String> headerList = new ArrayList<>(Arrays.asList(headerName));
		return headerList;
	}
	public List<String> headerNameOfAssessmentPendingCase() {
		String[] headerName = {"Sr No", "View", "Division", "Case_No", "AC_ID", "Name", "Address", "Contract Demand", "Priority"};
		ArrayList<String> headerList = new ArrayList<>(Arrays.asList(headerName));
		return headerList;
	}

	public List<String> headerNameOfAssesmentApprovedCase() {
		String[] headerName = {"Sr No","Zone", "Circle", "Division", "Priority", "Case_No", "ACC_ID", "Name", "Address", "Contact No", "Contact Demand", "Contact Unit", "Process", "Irregularity Found", "Visit By", "Visit Date"};
		ArrayList<String> headerList = new ArrayList<>(Arrays.asList(headerName));
		return headerList;
	}

	
	public void selectZone(String zone) throws InterruptedException {
	    clickOnZoneDropdownList.click();  // Click on the dropdown to reveal the options
	   // selectZone.sendKeys(zone);  // Type the zone name
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selectZone));
	    element.sendKeys(zone);
	    Thread.sleep(2000);


	}

	public void selectCircle(String circle) {
	   clickOnCircleDropdownList.click();  // Click on the dropdown to reveal options
	    //selectCircle.sendKeys(circle);  // Type the circle name
	   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selectCircle));
	    element.sendKeys(circle);
	}

	public void selectDivision(String division) {
	    clickOnDivisionDropdownList.click();  // Click on the dropdown to reveal options
	    selectDivision.sendKeys(division);  // Type the division name
	   // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    //WebElement divisionOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + division + "')]")));
	    //divisionOption.click();
	}
	public String getCaseNoFromExcelFile() throws EncryptedDocumentException,
	IOException { // System.out.println("Method getCaseNoFromExcelFile called");
		// Debug logging 
		FileInputStream fis = new   FileInputStream("./TestData/CaseNo/CaseNumber.xlsx");
		Workbook wb =WorkbookFactory.create(fis);

		caseNo =wb.getSheet("Sheet1").getRow(1).getCell(1).getStringCellValue(); return
				caseNo;
		}


	public void writeAcIdInExcelFormat() throws EncryptedDocumentException, IOException, InterruptedException {
		// System.out.println("Method getCaseNoFromExcelFile called");  // Debug logging

		acId = writeAcIdInExcelFormat.getText();
		try (FileInputStream fis = new FileInputStream("./TestData/CaseNo/CaseNumber.xlsx");
				Workbook wb = WorkbookFactory.create(fis);
				FileOutputStream fos = new FileOutputStream("./TestData/CaseNo/CaseNumber.xlsx")) {

			Sheet sheet = wb.getSheet("Sheet1");

			// Check if row 2 exists; if not, create it
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Check if cell 2 exists; if not, create it
			Cell cell = row.getCell(1);
			if (cell == null) {
				cell = row.createCell(1);
			}

			cell.setCellValue(acId);  // Write the value to the cell
			wb.write(fos);  // Save changes to the file

		}
	}
	public void writeAssessmentProposedDetailsInExcelFile(String text) throws EncryptedDocumentException, IOException {
		String filePath = "./TestData/AssessementApprovalDetails/AssessmentApprovalCaseDetails.xlsx";

		// Open the workbook and sheet
		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fis)) {

			// Access or create the sheet
			Sheet sheet = wb.getSheet("Sheet1");
			if (sheet == null) {
				sheet = wb.createSheet("Sheet1");
			}

			// Access or create row 1
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Find the next empty cell in the row
			int cell = row.getLastCellNum(); // Returns the number of cells used, or -1 if none
			if (cell == -1) {
				cell = 0; // Start from the first cell if the row is empty
			}

			// Write the text to the next available cell
			Cell currentCell = row.createCell(cell);
			currentCell.setCellValue(text);

			// Save changes to the file
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				wb.write(fos);
			}


		}
	}

	public void writeAssessmentFeedDetailsInExcelFile(String text) throws EncryptedDocumentException, IOException {
		String filePath = "./TestData/AssessmentFeeding/AssessmentFeedDetailsRecord.xlsx";

		// Open the workbook and sheet
		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fis)) {

			// Access or create the sheet
			Sheet sheet = wb.getSheet("Sheet1");
			if (sheet == null) {
				sheet = wb.createSheet("Sheet1");
			}

			// Access or create row 1
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Find the next empty cell in the row
			int cell = row.getLastCellNum(); // Returns the number of cells used, or -1 if none
			if (cell == -1) {
				cell = 0; // Start from the first cell if the row is empty
			}

			// Write the text to the next available cell
			Cell currentCell = row.createCell(cell);
			currentCell.setCellValue(text);

			// Save changes to the file
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				wb.write(fos);
			}


		}
	}

	public void writeAssessmentRealisationDetailsInExcelFile(String text) throws EncryptedDocumentException, IOException {
		String filePath = "./TestData/AssessmentRealisation/AssessmentRealisationDetails.xlsx";

		// Open the workbook and sheet
		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fis)) {

			// Access or create the sheet
			Sheet sheet = wb.getSheet("Sheet1");
			if (sheet == null) {
				sheet = wb.createSheet("Sheet1");
			}

			// Access or create row 1
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Find the next empty cell in the row
			int cell = row.getLastCellNum(); // Returns the number of cells used, or -1 if none
			if (cell == -1) {
				cell = 0; // Start from the first cell if the row is empty
			}

			// Write the text to the next available cell
			Cell currentCell = row.createCell(cell);
			currentCell.setCellValue(text);

			// Save changes to the file
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				wb.write(fos);
			}


		}
	}




	public String getAcIdFromExcelFile() throws EncryptedDocumentException,
	IOException { 
		// System.out.println("Method getCaseNoFromExcelFile called");
		FileInputStream fis = new FileInputStream("./TestData/CaseNo/CaseNumber.xlsx");
		Workbook wb =WorkbookFactory.create(fis);

		acId=wb.getSheet("Sheet1").getRow(1).getCell(1).getStringCellValue(); 
		return acId;

	}


	public void writeCaseNoInExcelFormat() throws EncryptedDocumentException, IOException {
		// System.out.println("Method writeCaseNoInExcelFormat called");  // Debug logging
		caseNo = writeCaseNoInExcelFormat.getText();

		try (FileInputStream fis = new FileInputStream("./TestData/CaseNo/CaseNumber.xlsx");
				Workbook wb = WorkbookFactory.create(fis);
				FileOutputStream fos = new FileOutputStream("./TestData/CaseNo/CaseNumber.xlsx")) {

			Sheet sheet = wb.getSheet("Sheet1");

			// Check if row 2 exists; if not, create it
			Row row = sheet.getRow(2);
			if (row == null) {
				row = sheet.createRow(2);
			}

			// Check if cell 2 exists; if not, create it
			Cell cell = row.getCell(2);
			if (cell == null) {
				cell = row.createCell(2);
			}

			cell.setCellValue(caseNo);  // Write the value to the cell
			wb.write(fos);  // Save changes to the file
		}
	}

	/*
	 * public String getCaseNoFromExcelFile() throws EncryptedDocumentException,
	 * IOException { // System.out.println("Method getCaseNoFromExcelFile called");
	 * // Debug logging FileInputStream fis = new
	 * FileInputStream("./TestData/Action/ActionSkipDetails.xlsx"); Workbook wb =
	 * WorkbookFactory.create(fis); caseNo
	 * =wb.getSheet("Sheet1").getRow(1).getCell(0).getStringCellValue(); return
	 * caseNo; }
	 * 
	 * public String getAcIdFromExcelFile() throws EncryptedDocumentException,
	 * IOException { // System.out.println("Method getCaseNoFromExcelFile called");
	 * // Debug logging FileInputStream fis = new
	 * FileInputStream("./TestData/Action/ActionSkipDetails.xlsx"); Workbook wb =
	 * WorkbookFactory.create(fis);
	 * acId=wb.getSheet("Sheet1").getRow(1).getCell(1).getStringCellValue(); return
	 * acId; }
	 */

	public List<String> getActionSkipAllDetailsFromExcelFile() throws EncryptedDocumentException, IOException {
		List<String> data = new ArrayList<>();

		FileInputStream fis = new FileInputStream(actionSkipDetailsPath);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet("Sheet1");

		// Loop through rows, starting from the second row (index 1) to avoid header row
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);

			// Extract data from specific cells and add to list
			caseNo = row.getCell(0).getStringCellValue();
			acId = row.getCell(1).getStringCellValue();
			actionRequiredTag = row.getCell(2).getStringCellValue();
			action = row.getCell(3).getStringCellValue();
			otheraction = row.getCell(4).getStringCellValue();
			evidencePath = row.getCell(5).getStringCellValue();
			evidenceName = row.getCell(6).getStringCellValue();
			ActionBy = row.getCell(7).getStringCellValue();

			data.add(caseNo);             
			data.add(acId);
			data.add(actionRequiredTag);
			data.add(action);
			data.add(otheraction);
			data.add(evidencePath);
			data.add(evidenceName);
			data.add(ActionBy);

		}

		fis.close();
		return data;
	}


	public List<String> getActionDetailsFromExcelFile() throws EncryptedDocumentException, IOException {
		List<String> data = new ArrayList<>();

		FileInputStream fis = new FileInputStream(actionDetailsPath);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet("Sheet1");

		// Loop through rows, starting from the second row (index 1) to avoid header row
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);

			// Extract data from specific cells and add to list
			String caseNo = row.getCell(0).getStringCellValue();
			String AcId = row.getCell(1).getStringCellValue();
			String actionRequiredTag = row.getCell(2).getStringCellValue();
			String action = row.getCell(3).getStringCellValue();
			String otheraction = row.getCell(4).getStringCellValue();
			String evidencePath = row.getCell(5).getStringCellValue();
			String evidenceName = row.getCell(6).getStringCellValue();
			String ActionBy = row.getCell(7).getStringCellValue();

			data.add(caseNo);             
			data.add(AcId);
			data.add(actionRequiredTag);
			data.add(action);
			data.add(otheraction);
			data.add(evidencePath);
			data.add(evidenceName);
			data.add(ActionBy);

		}

		fis.close();
		return data;
	}
	public void writeAllSkipActionDetailsFromExcelFormat() throws EncryptedDocumentException, IOException {
		// System.out.println("Method writeCaseNoInExcelFormat called");  // Debug logging
		caseNo = writeCaseNoInExcelFormat.getText();

		try (FileInputStream fis = new FileInputStream(actionSkipDetailsPath);
				Workbook wb = WorkbookFactory.create(fis);
				FileOutputStream fos = new FileOutputStream(actionSkipDetailsPath)) {

			Sheet sheet = wb.getSheet("Sheet1");

			// Check if row 2 exists; if not, create it
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Check if cell 2 exists; if not, create it
			Cell cell = row.getCell(0);
			if (cell == null) {
				cell = row.createCell(0);
			}

			cell.setCellValue(caseNo);  // Write the value to the cell
			wb.write(fos);  // Save changes to the file


		}
		try (FileInputStream fis = new FileInputStream(actionSkipDetailsPath);
				Workbook wb = WorkbookFactory.create(fis);
				FileOutputStream fos = new FileOutputStream(actionSkipDetailsPath)) {

			Sheet sheet = wb.getSheet("Sheet1");

			// Check if row 2 exists; if not, create it
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Check if cell 2 exists; if not, create it
			Cell cell = row.getCell(1);
			if (cell == null) {
				cell = row.createCell(1);
			}

			cell.setCellValue(caseNo);  // Write the value to the cell
			wb.write(fos);  // Save changes to the file


		}

		try (FileInputStream fis = new FileInputStream(actionSkipDetailsPath);
				Workbook wb = WorkbookFactory.create(fis);
				FileOutputStream fos = new FileOutputStream(actionSkipDetailsPath)) {

			Sheet sheet = wb.getSheet("Sheet1");

			// Check if row 2 exists; if not, create it
			Row row = sheet.getRow(1);
			if (row == null) {
				row = sheet.createRow(1);
			}

			// Check if cell 2 exists; if not, create it
			Cell cell = row.getCell(2);
			if (cell == null) {
				cell = row.createCell(2);
			}

			cell.setCellValue(caseNo);  // Write the value to the cell
			wb.write(fos);  // Save changes to the file

		}
		}
		public String dateFormat() {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = new Date();
			String formattedDate = formatter.format(date);
			return formattedDate;
		}
	
		   

}



