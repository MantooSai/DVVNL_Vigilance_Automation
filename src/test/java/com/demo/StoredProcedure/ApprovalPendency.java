package com.demo.StoredProcedure;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.annotations.Test;

public class ApprovalPendency {

    public void getApprovalPendency(String zoneCode, String divs) {
        // Default values if parameters are null
        zoneCode = (zoneCode != null) ? zoneCode : "";
        divs = (divs != null) ? divs : "";

        // Database connection parameters
        String url = "jdbc:sqlserver://45.114.143.206;databaseName=DVVNL_VIGILANCE_TEST;encrypt=true;trustServerCertificate=true;";
        String username = "mantoo";
        String password = "mantoo@123#";

        // Dynamic SQL Query
        LocalDate currentDate = LocalDate.now();
        String masterTable = "DVVNL_MRI_DATA_Test..CONSUMER_MASTER_" +
                             currentDate.format(DateTimeFormatter.ofPattern("MM")) +
                             currentDate.format(DateTimeFormatter.ofPattern("yyyy"));

        String sqlQuery = "SELECT row_number() OVER (ORDER BY ISNULL(PM.Priority_Code, 9), V.Case_No) SrNo, " +
                "V.ID, V.Case_No, V.AC_ID, V.Visit_Date, V.Visit_By, " +
                "ISNULL(IRREGULARITY_FOUND_TAG_MAIN, 'N') IRREGULARITY_FOUND_TAG_MAIN, " +
                "'Main-' + ISNULL(IRREGULARITY_MAIN, '') + '; Pole-' + ISNULL(IRREGULARITY_POLE, '') IRREGULARITY, " +
                "ISNULL(OTHER_IRREGULARITY_MAIN, '') OTHER_IRREGULARITY_MAIN, " +
                "ISNULL(POLE_METER_EXISTS_TAG, 'N') POLE_METER_EXISTS_TAG, " +
                "ISNULL(IRREGULARITY_FOUND_TAG_POLE, 'N') IRREGULARITY_FOUND_TAG_POLE, " +
                "ISNULL(IRREGULARITY_POLE, '') IRREGULARITY_POLE, " +
                "ISNULL(OTHER_IRREGULARITY_POLE, '') OTHER_IRREGULARITY_POLE, " +
                "IRREGULARITY_EVIDENCE_PATH, CONNECTION_AVAILABLE, " +
                "CASE WHEN V.AC_ID IS NULL THEN OM.NAME ELSE M.NAME END Name, " +
                "CASE WHEN V.AC_ID IS NULL THEN OM.Address ELSE M.Address END Address, " +
                "ISNULL(CASE WHEN V.AC_ID IS NULL THEN OM.MOBILE_NO ELSE M.MOBILE_NO END, 'NA') MOBILE_NO, " +
                "CASE WHEN V.AC_ID IS NULL THEN 0 ELSE M.CONTACT_DEMAND END CONTACT_DEMAND, " +
                "CASE WHEN V.AC_ID IS NULL THEN '-' ELSE M.CONTACT_DEMAND_UNIT END CONTACT_DEMAND_UNIT, " +
                "L.ZPC_NAME, L.CIRCLE, L.DIV_NAME, ISNULL(Process,'NA') Process, " +
                "ISNULL(PM.PRIORITY_code, 9) PRIORITY_CODE, ISNULL(PM.PRIORITY_NAME, '') CasePriority " +
                "FROM FIELD_VIGILANCE_TRANS V " +
                "LEFT JOIN Priority_Trans PT ON V.CASE_NO=PT.CASE_NO " +
                "LEFT JOIN Priority_Master PM ON PM.Priority_Code=PT.Priority_Code " +
                "LEFT JOIN " + masterTable + " M ON M.AC_ID=V.AC_ID " +
                "LEFT JOIN CONSUMER_MASTER_OTHER OM ON OM.Case_No=V.Case_No " +
                "LEFT JOIN (SELECT DISTINCT Zone_Code, ZPC_NAME, Circle, Div_CD, Div_Name " +
                "FROM DVVNL_SAIADM_test..PVVNL_LOGIN WHERE Zone_Code <> 'All' AND DIV_CD <> 'All') L " +
                "ON L.Div_CD=ISNULL(M.DIV, '999999999999') " +
                "LEFT JOIN ASSESSMENT_PROPOSED_TRANS APT ON APT.CASE_NO=V.CASE_NO " +
                "WHERE APT.CASE_NO IS NULL AND V.Case_No IS NOT NULL " +
                "AND 1 = CASE " +
                "WHEN V.AC_ID IS NOT NULL AND CAST(DIV_CD AS NVARCHAR) IN (" + formatDivs(divs) + ") THEN 1 " +
                "WHEN V.AC_ID IS NULL AND OM.DIV_NAME IN (" +
                "SELECT Div_Name FROM DVVNL_SAIADM_test..PVVNL_LOGIN WHERE CAST(DIV_CD AS NVARCHAR) IN (" + formatDivs(divs) + ")) THEN 1 " +
                "ELSE 0 END " +
                "ORDER BY ISNULL(PM.Priority_Code, 9), V.Case_No";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("SrNo: " + resultSet.getInt("SrNo"));
                System.out.println("Case_No: " + resultSet.getString("Case_No"));
                System.out.println("Visit_By: " + resultSet.getString("Visit_By"));
                // Print other required fields...
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String formatDivs(String divs) {
        if (divs.isEmpty()) return "''";
        String[] divArray = divs.split(",");
        StringBuilder formattedDivs = new StringBuilder();
        for (String div : divArray) {
            formattedDivs.append("'").append(div.trim()).append("',");
        }
        // Remove trailing comma
        return formattedDivs.substring(0, formattedDivs.length() - 1);
    }

    @Test
    public void testApprovalPendency() {
        ApprovalPendency ap = new ApprovalPendency();
        ap.getApprovalPendency("AGRI", "DIV233422,63");
    }
}
