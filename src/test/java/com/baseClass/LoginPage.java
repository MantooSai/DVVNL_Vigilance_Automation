package com.baseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    // Constructor to initialize WebDriver and PageFactory elements
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // WebElements for login
    @FindBy(name = "userid")
    @CacheLookup
    WebElement userid;

    @FindBy(name = "password")
    @CacheLookup
    WebElement password;

    @FindBy(id = "button")
    @CacheLookup
    WebElement loginButton;

    // Methods to interact with login page elements
    public void setUserName(String uname) {
        userid.sendKeys(uname);
        
    }

    public void setPassword(String pwd) {
        password.sendKeys(pwd);
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}
