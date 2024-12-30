package com.demo.database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;

public class ApprovalPendencyProcedure {
    private static PropertyFile pf;
    private CommonDataPage cdp;
    private WebDriver driver;

    public static String mmyyyy;
    public static String userIdET;
    public static String databaseName;
    public static String logindatabaseName;
    public static String divName;
    public static String consumerMasterDbName;
    public static String DB_URL;
    public static String countOFAssessementApprovalCase = null;
    public static String acId = null;
    private static String DB_USER;
    private static String DB_PASSWORD;

    public ApprovalPendencyProcedure() {
        try {
            cdp = new CommonDataPage(driver);
            pf = new PropertyFile();
            initializeProperties();
            acId = cdp.getAcIdFromExcelFile();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize properties or AC_ID", e);
        }
    }

    private void initializeProperties() {
        pf.databaseCredentialsDetails();
        mmyyyy = pf.getProperty("mmyyyy");
        userIdET = pf.getProperty("userIdET");
        databaseName = pf.getProperty("databaseName");
        logindatabaseName = pf.getProperty("logindatabaseName");
        consumerMasterDbName = pf.getProperty("consumerMasterDbName");
        DB_URL = pf.getProperty("DB_URL");
        DB_USER = pf.getProperty("DB_USER");
        DB_PASSWORD = pf.getProperty("DB_PASSWORD");
        divName = pf.getProperty("divName");
    }

    public void getApprovalPendency(String zoneCode, String divs) {
        zoneCode = (zoneCode != null) ? zoneCode : "";
        divs = (divs != null) ? divs : "";

        // Construct the master table name
        String month = new SimpleDateFormat("MM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String masterTable = consumerMasterDbName + "..CONSUMER_MASTER_" + month + year;

        String strQuery = "SELECT \n" +
                "    ROW_NUMBER() OVER (ORDER BY ISNULL(PM.Priority_Code, 9), V.Case_No) AS SrNo,\n" +
                "    V.ID, V.Case_No, V.AC_ID, V.Visit_Date, V.Visit_By,\n" +
                "    ISNULL(IRREGULARITY_FOUND_TAG_MAIN, 'N') AS IRREGULARITY_FOUND_TAG_MAIN,\n" +
                "    'Main-' + ISNULL(IRREGULARITY_MAIN, '') + '; Pole-' + ISNULL(IRREGULARITY_POLE, '') AS IRREGULARITY,\n" +
                "    ISNULL(OTHER_IRREGULARITY_MAIN, '') AS OTHER_IRREGULARITY_MAIN,\n" +
                "    ISNULL(POLE_METER_EXISTS_TAG, 'N') AS POLE_METER_EXISTS_TAG,\n" +
                "    ISNULL(IRREGULARITY_FOUND_TAG_POLE, 'N') AS IRREGULARITY_FOUND_TAG_POLE,\n" +
                "    ISNULL(IRREGULARITY_POLE, '') AS IRREGULARITY_POLE,\n" +
                "    ISNULL(OTHER_IRREGULARITY_POLE, '') AS OTHER_IRREGULARITY_POLE,\n" +
                "    IRREGULARITY_EVIDENCE_PATH, CONNECTION_AVAILABLE,\n" +
                "    CASE WHEN V.AC_ID IS NULL THEN OM.NAME ELSE M.NAME END AS Name,\n" +
                "    CASE WHEN V.AC_ID IS NULL THEN OM.Address ELSE M.Address END AS Address,\n" +
                "    ISNULL(CASE WHEN V.AC_ID IS NULL THEN OM.MOBILE_NO ELSE M.MOBILE_NO END, 'NA') AS MOBILE_NO,\n" +
                "    CASE WHEN V.AC_ID IS NULL THEN 0 ELSE M.CONTACT_DEMAND END AS CONTACT_DEMAND,\n" +
                "    CASE WHEN V.AC_ID IS NULL THEN '-' ELSE M.CONTACT_DEMAND_UNIT END AS CONTACT_DEMAND_UNIT,\n" +
                "    L.ZPC_NAME, L.CIRCLE, L.DIV_NAME,\n" +
                "    ISNULL(Process, 'NA') AS Process,\n" +
                "    ISNULL(PM.PRIORITY_code, 9) AS PRIORITY_CODE,\n" +
                "    ISNULL(PM.PRIORITY_NAME, '') AS CasePriority\n" +
                "FROM " + databaseName + "..FIELD_VIGILANCE_TRANS V\n" +
                "LEFT JOIN " + databaseName + "..Priority_Trans PT ON V.CASE_NO = PT.CASE_NO\n" +
                "LEFT JOIN " + databaseName + "..Priority_Master PM ON PM.Priority_Code = PT.Priority_Code\n" +
                "LEFT JOIN " + masterTable + " M ON M.AC_ID = V.AC_ID\n" +
                "LEFT JOIN " + databaseName + "..CONSUMER_MASTER_OTHER OM ON OM.Case_No = V.Case_No\n" +
                "LEFT JOIN (" +
                "   SELECT DISTINCT Zone_Code, ZPC_NAME, Circle, Div_CD, Div_Name \n" +
                "   FROM " + logindatabaseName + "..PVVNL_LOGIN \n" +
                "   WHERE Zone_Code <> 'All' AND DIV_CD <> 'All') L\n" +
                "ON L.Div_CD = ISNULL(M.DIV, '999999999999')\n" +
                "LEFT JOIN " + databaseName + "..ASSESSMENT_PROPOSED_TRANS APT ON APT.CASE_NO = V.CASE_NO\n" +
                "WHERE APT.CASE_NO IS NULL AND V.Case_No IS NOT NULL\n" +
                "AND 1 = CASE WHEN V.AC_ID IS NOT NULL AND DIV_CD IN (SELECT value FROM STRING_SPLIT(?, ',')) THEN 1\n" +
                "             WHEN V.AC_ID IS NULL AND OM.DIV_NAME IN (SELECT Div_Name FROM " + logindatabaseName + "..PVVNL_LOGIN " +
                "             WHERE DIV_CD IN (SELECT value FROM STRING_SPLIT(?, ','))) THEN 1\n" +
                "             ELSE 0 END";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(strQuery)) {
            pstmt.setString(1, divs);
            pstmt.setString(2, divs);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("SrNo: " + rs.getInt("SrNo") +
                        ", Case_No: " + rs.getString("Case_No") +
                        ", Name: " + rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetApprovalPendency() {
        getApprovalPendency("ALI", "EUDC-ALIGARH");
    }
}
