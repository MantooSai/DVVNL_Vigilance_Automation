package com.vigilEye.allotPendintCaseDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.utility.DateFormat;
import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;


public class AllotPendingCaseDataBase {
	 private PropertyFile pf; 
	    CommonDataPage cdp;
	  //  public static String countOFpendingCase=null;
		public static String mmyyyy ;
		public static String userIdAT ;
		public static String databaseName;
		public static String logindatabaseName ;
		public static String consumerMasterDbName ;
		public static String DB_URL;
		public static String countOFpendingCase = null;
		public static String acId=null;;
		private static  String DB_USER;
		private static  String DB_PASSWORD;
	    public AllotPendingCaseDataBase() {
	        try {
	            pf = new PropertyFile();
	           
	            initializeProperties(); 
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to initialize PropertyFile", e);
	        }
	    }

	    private void initializeProperties() {
	    	pf.databaseCredentialsDetails();
	    	mmyyyy  = pf.getProperty("mmyyyy");
	    	userIdAT = pf.getProperty("userIdAT");
	    	databaseName = pf.getProperty("databaseName");
	    	logindatabaseName = pf.getProperty("logindatabaseName");
	    	consumerMasterDbName = pf.getProperty("consumerMasterDbName");
	    	DB_URL = pf.getProperty("DB_URL");
	    	DB_USER = pf.getProperty("DB_USER");
	    	DB_PASSWORD = pf.getProperty("DB_PASSWORD");
	        
	    }
    // Initialize `acId` before the tests run
   
    public void initializeAcId() throws EncryptedDocumentException, IOException {
    	 WebDriver driver = null;
   	  CommonDataPage cdp = new CommonDataPage(driver);
         String  acId1 = cdp.getAcIdFromExcelFile();
         this.acId=acId1;
    }

    @Test
    public String toverifyCountAllotPendingCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
    	AllotPendingCaseDataBase ap=new AllotPendingCaseDataBase();
    	ap.initializeAcId();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(
                 "SELECT COUNT(DISTINCT mt.Case_No) AS Pending_Case_Count " +
                 "FROM " + databaseName + "..MARKING_TRANS mt " +
                 "LEFT JOIN " + databaseName + "..Priority_Trans pt ON mt.Case_No = pt.Case_No " +
                 "LEFT JOIN " + databaseName + "..Marking_Validation_Master mvm ON pt.Priority_Code = mvm.Priority_Code " +
                 "LEFT JOIN " + consumerMasterDbName + "..CONSUMER_MASTER_" + mmyyyy + " CM ON CM.AC_ID=MT.AC_ID " +
                 "LEFT JOIN DVVNL_SAIADM_TEST..PVVNL_LOGIN PL ON PL.DIV_CD=CM.DIV " +
                 "WHERE mvm.Is_Enable = 'Y' AND CM.CONSUMER_STATUS IN('IN SERVICE','B') " +
                 "AND OPR_ID='" + userIdAT + "' " +
                 "AND mt.Case_No NOT IN (SELECT DISTINCT fvt.Case_No FROM DVVNL_VIGILANCE_TEST..FIELD_VIGILANCE_TRANS FVT) " +
                 "AND pt.PRIORITY_CODE='1'")) {
            
            ResultSet n = ps.executeQuery();
            if (n.next()) {
                countOFpendingCase = n.getString("Pending_Case_Count").trim();
            }
            return countOFpendingCase;
        }
    }

    @Test
    public List<String> toverifyAllotPendingCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
    	//  public void toverifyAllotPendingCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
    	AllotPendingCaseDataBase ap=new AllotPendingCaseDataBase();
    	ap.initializeAcId();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(
                 "SELECT top 1 cm.DIV_NAME, pt.CASE_NO, cm.AC_ID, cm.NAME, cm.ADDRESS, cm.CONTACT_DEMAND, pt.PRIORITY_CODE " +
                 "FROM " + consumerMasterDbName + "..CONSUMER_MASTER_" + mmyyyy + " cm " +
                 "LEFT JOIN " + databaseName + "..MARKING_TRANS mt ON cm.AC_ID = mt.AC_ID " +
                 "LEFT JOIN " + databaseName + "..PRIORITY_TRANS pt ON mt.CASE_NO = pt.CASE_NO " +
                 "WHERE cm.AC_ID = '"+acId+"'")) {
            
           // ps.setString(1, acId);
            ResultSet n = ps.executeQuery();
            List<String> allotPendingDetails = new ArrayList<>();
            //System.out.print(ps.toString());

            while (n.next()) {
                allotPendingDetails.add(n.getString("DIV_NAME").trim());
                allotPendingDetails.add(n.getString("CASE_NO").trim());
                allotPendingDetails.add(n.getString("AC_ID").trim());
                allotPendingDetails.add(n.getString("NAME").trim());
                allotPendingDetails.add(n.getString("ADDRESS").trim());
                allotPendingDetails.add(n.getString("CONTACT_DEMAND").trim());
                allotPendingDetails.add(n.getString("PRIORITY_CODE").trim());
            }
          return allotPendingDetails;
          // System.out.println(allotPendingDetails);
        }
    }

    @Test
    public List<String> toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
    	AllotPendingCaseDataBase ap=new AllotPendingCaseDataBase();
    	ap.initializeAcId();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(
                 "SELECT name, address, CONTACT_DEMAND, AC_ID, MOBILE_NO " +
                 "FROM " + consumerMasterDbName + "..consumer_master_" + mmyyyy + " " +
                 "WHERE ac_id ='"+acId+"'")) {
            
           // ps.setString(1, acId);
            ResultSet n = ps.executeQuery();
            List<String> consumerDetails = new ArrayList<>();

            while (n.next()) {
                consumerDetails.add(n.getString("name").trim());
                consumerDetails.add(n.getString("address").trim());
                consumerDetails.add(n.getString("CONTACT_DEMAND").trim());
                consumerDetails.add(n.getString("AC_ID").trim());
                consumerDetails.add(n.getString("MOBILE_NO").trim());
            }
            return consumerDetails;
        }
    }

    @Test
    public List<String> toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
    	AllotPendingCaseDataBase ap=new AllotPendingCaseDataBase();
    	ap.initializeAcId();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(
                 "SELECT rmk, rmk_date, RMK_SOURCE, RMK_BY, PRIORITY_CODE " +
                 "FROM " + databaseName + "..marking_trans WHERE ac_id = '"+acId+"'")) {

           // ps.setString(1, acId);
            ResultSet rs = ps.executeQuery();
            DateFormat df = new DateFormat();
            List<String> markingDetails = new ArrayList<>();

            while (rs.next()) {
                markingDetails.add(rs.getString("rmk").trim());
                String rmkDateStr = rs.getString("rmk_date").trim();
                String formattedRmkDate = df.date_dd_mm_yyyy_Format(rmkDateStr);
                markingDetails.add(formattedRmkDate);
                markingDetails.add(rs.getString("RMK_SOURCE").trim());
                markingDetails.add(rs.getString("RMK_BY").trim());
                markingDetails.add(rs.getString("PRIORITY_CODE").trim());
            }
            return markingDetails;
        }
    }
}
