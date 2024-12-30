package com.vigilEye.testCase;

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

    private static final String ERROR_MESSAGE = "PLEASE INSERT CORRECT USERTYPE OR USERID OR PASSWORD";
    private static final String EXPECTED_TITLE = "MRI ANALYSIS";

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

        String errorMessage = isErrorMessageDisplayed();
        if (errorMessage != null && errorMessage.equals(ERROR_MESSAGE)) {
            logger.info("Negative test case passed for user: " + user);
            Assert.assertTrue(true, "Expected error message displayed: " + ERROR_MESSAGE);
        } else {
            logger.info("Login passed for user: " + user);
            Assert.assertTrue(true, "Login successful for user: " + user);

            String actualTitle = driver.getTitle();
            if (actualTitle.equals(EXPECTED_TITLE)) {
                logger.info("Page title verified: " + EXPECTED_TITLE);
            } else {
                captureScreen(driver, "loginTest");
                Assert.fail("Page title mismatch: Expected '" + EXPECTED_TITLE + "', but found '" + actualTitle + "'.");
            }
        }
    }

    // Helper method to check for error message
    private String isErrorMessageDisplayed() {
        try {
            return driver.findElement(By.xpath("//font[.='PLEASE INSERT CORRECT USERTYPE OR USERID OR PASSWORD']")).getText();
        } catch (NoSuchElementException e) {
            logger.info("Error message not displayed.");
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
