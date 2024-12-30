package com.demo.pom;

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

public class Irregularities_Main_Meter_HomePage {
	public WebDriver driver;

	// Constructor to initialize the driver and elements
	public Irregularities_Main_Meter_HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// WebElements with private access
	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[1]")
	private WebElement txtCT;

	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[2]")
	private WebElement txtCT_PT;

	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[3]")
	private WebElement txtMeter;

	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[4]")
	private WebElement txtMeterBox;

	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[5]")
	private WebElement txtMeterSeal;

	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[6]")
	private WebElement txtPT;

	@FindBy(xpath="(//div[@class=' css-19bb58m']/input)[7]")
	private WebElement txtSearviceCable;

	@FindBy(xpath="//input[@id='other_irr_main']")
	private WebElement txtOtherIrregularities;

	@FindBy(xpath="(//div[@class=' w-12 h-6  rounded-full bg-indigo-600'])[1]/div")
	private WebElement toCheckIrregularities;

	@FindBy(xpath="//p[contains(text(),'Main Meter')]")
	private WebElement clickOnIrregularitiesDropdownList;

	@FindBy(xpath = "(//div[@class='irregularities flex-col p-4'])[1]/ul/li")
	private List<WebElement> fieldIrregularities;

	// Setters to send values to fields
	public void setTxtCT(String ctText) {
		txtCT.sendKeys(ctText);
	}

	public void setTxtCT_PT(String ct_ptText) {
		txtCT_PT.sendKeys(ct_ptText);
	}

	public void setTxtMeter(String meterText) {
		txtMeter.sendKeys(meterText);
	}

	public void setTxtMeterBox(String meterBoxText) {
		txtMeterBox.sendKeys(meterBoxText);
	}

	public void setTxtMeterSeal(String meterSealText) {
		txtMeterSeal.sendKeys(meterSealText);
	}

	public void setTxtPT(String ptText) {
		txtPT.sendKeys(ptText);
	}

	public void setTxtSearviceCable(String searviceCableText) {
		txtSearviceCable.sendKeys(searviceCableText);
	}

	public void setTxtOtherIrregularities(String otherIrregularitiesText) {
		txtOtherIrregularities.sendKeys(otherIrregularitiesText);
	}

	public void clickToCheckIrregularities() {
		toCheckIrregularities.click();
	}
	
	public void clickOnIrregularitiesDropdownList() {
	    clickOnIrregularitiesDropdownList.click();
	}

	public List<String> fieldIrregularitiesForMainMeter() throws InterruptedException {
		Thread.sleep(2000);
		ArrayList<String> fieldIrreg=new ArrayList<>();
		for(int i=0; i <fieldIrregularities.size(); i++) {
			fieldIrreg.add(fieldIrregularities.get(i).getText());
		}
		return fieldIrreg;
	}
}
