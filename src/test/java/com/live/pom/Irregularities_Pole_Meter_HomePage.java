package com.live.pom;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Irregularities_Pole_Meter_HomePage {
    WebDriver driver;

    public Irregularities_Pole_Meter_HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locating WebElements using XPaths
    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[8]")
    private WebElement txtCT;

    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[9]")
    private WebElement txtMeter;

    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[10]")
    private WebElement txtMeterBox;

    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[11]")
    private WebElement txtMeterSeal;

    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[12]")
    private WebElement txtMf;

    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[13]")
    private WebElement txtPT;

    @FindBy(xpath="(//div[@class=' css-19bb58m']/input)[14]")
    private WebElement txtServiceCable;

    @FindBy(xpath="//input[@name='other_irr_pole']")
    private WebElement txtOtherIrregularities;

    @FindBy(xpath="(//div[@class='flex  w-6 h-6 bg-white rounded-full  transition translate-x-full'])[2]")
    private WebElement toCheckIrregularitiesExist;
    
    @FindBy(xpath="//div[@class=' w-12 h-6  rounded-full bg-zinc-700']")
    private WebElement toCheckIrregularitiesfount;

  
    @FindBy(xpath="//p[contains(text(),'Pole Meter')]")
    private WebElement clickOnPoleMeterIrregularitiesDropdownList;
    
    
   	@FindBy(xpath = "(//div[@class='irregularities flex-col p-4'])[2]/ul/li")
   	private List<WebElement> fieldIrregularities;


    // Methods to interact with the WebElements
    public void setTxtCT(String ctText) {
        txtCT.sendKeys(ctText);
    }

    public void setTxtMf(String ct_ptText) {
    	txtMf.sendKeys(ct_ptText);
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

    public void setTxtServiceCable(String serviceCableText) {
        txtServiceCable.sendKeys(serviceCableText);
    }

    public void setTxtOtherIrregularities(String otherIrregularitiesText) {
        txtOtherIrregularities.sendKeys(otherIrregularitiesText);
    }

    public void clickToCheckIrregularitiesExist() {
        toCheckIrregularitiesExist.click();
    }
    public void clickToCheckIrregularitiesFound() {
      //  toCheckIrregularitiesfount.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",toCheckIrregularitiesfount);
    }
    
    public boolean clickToCheckIrregularitiesFoundIsEnabled() {
        boolean enable = toCheckIrregularitiesfount.isEnabled();
        return enable;
    }


    public void clickOnIrregularitiesDropdownList() {
 	   clickOnPoleMeterIrregularitiesDropdownList.click();
    }
    
    public List<String> fieldIrregularitiesForPoleMeter() throws InterruptedException {
    	Thread.sleep(2000);
    	ArrayList<String> fieldIrreg=new ArrayList<>();
    	for(int i=0; i <fieldIrregularities.size(); i++) {
    		fieldIrreg.add(fieldIrregularities.get(i).getText());
    	}
		return fieldIrreg;
    }
}