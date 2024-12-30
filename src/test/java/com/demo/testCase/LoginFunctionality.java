package com.demo.testCase;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;
import com.baseClass.LoginPage;
import com.utility.XLUtils;

public class LoginFunctionality extends BaseClass {

    private static final String expectedAlertMessage = "PLEASE INSERT CORRECT USERTYPE OR USERID OR PASSWORD";
    private static final String expectedTitle = "MRI ANALYSIS";

    @Test(dataProvider = "LoginData")
    public void loginDDT(String user, String pwd) throws IOException {
        driver.get(baseUrl);
        logger.info("Navigated to login page.");

        LoginPage lp = new LoginPage(driver);
        lp.setUserName(user);
        logger.info("Username provided: " + user);
        lp.setPassword(pwd);
        logger.info("Password provided. "+ pwd);
        lp.clickLoginButton();

        String actualAlertMessage = expectedAlertMessageDisplayed();
        //To check Negative Test Cases
        if (actualAlertMessage != null && actualAlertMessage.equals(expectedAlertMessage)) {
            logger.info("Negative test case passed for user: " + user);
            Assert.assertTrue(true, "Expected alert message displayed: " + expectedAlertMessage);
        } else {
            logger.info("Login passed for user: " + user);
            Assert.assertTrue(true, "Login successful for user: " + user);
          //To check valid User id and Valid Password 
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(expectedTitle)) {
                logger.info("Page title verified: " + expectedTitle);
            } else {
                captureScreen(driver, "loginTest");
                Assert.fail("Page title mismatch: Expected '" + expectedTitle + "', but found '" + actualTitle + "'.");
            }
        }
    }

    // To check alert Message
    private String expectedAlertMessageDisplayed() {
        try {
            return driver.findElement(By.xpath("//font[.='PLEASE INSERT CORRECT USERTYPE OR USERID OR PASSWORD']")).getText();
        } catch (NoSuchElementException e) {
            logger.info("Alert message not displayed.");
            return null;
        }
    }

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        String path = System.getProperty("user.dir") + "/TestData/LoginData/LoginTestData.xlsx";

        int rownum = XLUtils.getRowCount(path, "Sheet1");
        int colcount = XLUtils.getCellCount(path, "Sheet1", 1);

        String[][] logindata = new String[rownum][colcount];

        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colcount; j++) {
                logindata[i - 1][j] = XLUtils.getCellData(path, "Sheet1", i, j);
            }
        }
        return logindata;
    }
}
