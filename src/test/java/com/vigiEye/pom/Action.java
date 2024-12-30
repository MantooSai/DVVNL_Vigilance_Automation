package com.vigiEye.pom;

import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Action {
	WebDriver driver;
	public Action(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//div[@class='flex  w-6 h-6 bg-white rounded-full  transition translate-x-0']")
	private WebElement skipAction;
	
	@FindBy(xpath = "(//button[@type='button'])[4]")
	private WebElement clickSubmitButton;
	
	
	@FindBy(xpath = "(//input[@type='file'])[1]")
	private WebElement ctChangeImage;
	
	@FindBy(xpath = "(//input[@type='file'])[2]")
	private WebElement CtPChamperImage;
	
	@FindBy(xpath = "(//input[@type='file'])[3]")
	private WebElement CtPtUnitImage;
	
	@FindBy(xpath = "(//input[@type='file'])[4]")
	private WebElement meterImage;
	
	@FindBy(xpath = "(//input[@type='file'])[5]")
	private WebElement meterCubicalImage;
	
	@FindBy(xpath = "(//input[@type='file'])[1]")
	private WebElement ptChangeImage;
	
	@FindBy(xpath = "(//input[@type='file'])[1]")
	private WebElement otherImage;
	
	public void skipActionPerform() {
		skipAction.click();
	}
	
	public void clicOnSubmitButton() {
		clickSubmitButton.click();
	}
	
	 public void ctChangeEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "CtChange.png").toString();
         ctChangeImage.sendKeys(filePath);
    }
	 public void ctPtUnitEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "CtPtUnit.png").toString();
         CtPtUnitImage.sendKeys(filePath);
    }
	 
	 public void ctPChamperEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "CtPChamper.png").toString();
         CtPChamperImage.sendKeys(filePath);
    }
	 
	 public void meterEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "Meter.png").toString();
         meterImage.sendKeys(filePath);
    }
	 
	 public void meterCubicalEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "MeterCubical.png").toString();
         meterCubicalImage.sendKeys(filePath);
    }
	 
	 public void ptChangeEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "PTChange.png").toString();
         ptChangeImage.sendKeys(filePath);
    }
	 
	 public void otherEvedenceImage() {
    	 String userDirectory = System.getProperty("user.dir");
         String filePath = Paths.get(userDirectory, "TestData", "ActionImage", "Other.png").toString();
         otherImage.sendKeys(filePath);
    }

}
