package com.live.database;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.utility.DateFormat;
import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;


public class AllotAttendCaseDataBase {
	   private PropertyFile pf; 
	    CommonDataPage cdp;

		public static String mmyyyy ;
		public static String userIdAT ;
		public static String databaseName;
		public static String logindatabaseName ;
		public static String consumerMasterDbName ;
		public static String DB_URL;
		public static String countOFAttendCase = null;
		public static String acId=null;;
		private static  String DB_USER;
		private static  String DB_PASSWORD;
		
	    public AllotAttendCaseDataBase() {
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


	public void initializeAcId() throws EncryptedDocumentException, IOException {
		WebDriver driver = null;
		CommonDataPage cdp = new CommonDataPage(driver);
		String  acId1 = cdp.getAcIdFromExcelFile();
		this.acId=acId1;
	}

	@Test
	public String toverifyCountAllotAttendCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AllotAttendCaseDataBase allotAttendCase=new AllotAttendCaseDataBase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT Count(CASE_NO) as Attend_Case_Count " +
								"FROM " + databaseName + "..FIELD_VIGILANCE_TRANS FV " +
								"INNER JOIN " + consumerMasterDbName + "..CONSUMER_MASTER_" + mmyyyy + " CM ON CM.AC_ID=FV.AC_ID " +
								"LEFT JOIN " + logindatabaseName + "..PVVNL_LOGIN PL ON PL.DIV_CD=CM.DIV " +
								"WHERE CASE_NO IN (SELECT DISTINCT CASE_NO FROM " + databaseName + "..MARKING_TRANS " +
								"WHERE Rmk_Source NOT IN ('VIGILANCE APP (Voluntary Attend)', 'VIGILANCE APP (Other Attend)', '') " +
								"AND PRIORITY_CODE='1' AND CASE_NO IN (SELECT DISTINCT CASE_NO FROM " + databaseName + "..PRIORITY_TRANS WHERE PRIORITY_CODE='1')) " +
								"AND CASE_NO NOT IN (SELECT DISTINCT CASE_NO FROM " + databaseName + "..FIELD_ACTION_TRANS) " +
								"AND OPR_ID='" + userIdAT + "'")) {

			ResultSet n = ps.executeQuery();
			if (n.next()) {
				countOFAttendCase = n.getString("Attend_Case_Count").trim();
			}
			return countOFAttendCase;
		}
	}

	@Test
	public List<String> toverifyAllotAttendCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyAllotAttendCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AllotAttendCaseDataBase allotAttendCase=new AllotAttendCaseDataBase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT TOP 1 CM.DIV_NAME, FV.AC_ID, FV.CASE_NO, NAME, ADDRESS, CONTACT_DEMAND, PRIORITY_CODE " +
								"FROM " + databaseName + "..FIELD_VIGILANCE_TRANS FV " +
								"INNER JOIN " + consumerMasterDbName + "..CONSUMER_MASTER_" + mmyyyy + " CM ON CM.AC_ID=FV.AC_ID  " +
								"INNER JOIN " + logindatabaseName + "..PVVNL_LOGIN PL ON PL.DIV_CD=CM.DIV " +
								"LEFT JOIN " + databaseName + "..PRIORITY_TRANS PT ON PT.CASE_NO=FV.CASE_NO " +
								"WHERE FV.CASE_NO IN (SELECT DISTINCT CASE_NO FROM " + databaseName + "..MARKING_TRANS " +
								"WHERE Rmk_Source NOT IN ('VIGILANCE APP (Voluntary Attend)', 'VIGILANCE APP (Other Attend)', '') " +
								"AND PRIORITY_CODE='1' AND CASE_NO IN (SELECT DISTINCT CASE_NO FROM " + databaseName + "..PRIORITY_TRANS WHERE PRIORITY_CODE='1')) " +
								"AND FV.CASE_NO NOT IN (SELECT DISTINCT CASE_NO FROM " + databaseName + "..FIELD_ACTION_TRANS) " +
								"AND OPR_ID='" + userIdAT + "' and  cm.ac_id='" + acId + "' ORDER BY FV.VISIT_DATE DESC")) {

			ResultSet n = ps.executeQuery();
			List<String> allotAttendDetails = new ArrayList<>();
			if (n.next()) {
				allotAttendDetails.add(n.getString("DIV_NAME"));
				allotAttendDetails.add(n.getString("CASE_NO"));
				allotAttendDetails.add(n.getString("AC_ID"));
				allotAttendDetails.add(n.getString("NAME"));
				allotAttendDetails.add(n.getString("ADDRESS"));
				allotAttendDetails.add(n.getString("CONTACT_DEMAND"));
				allotAttendDetails.add(n.getString("PRIORITY_CODE"));
			}
			return allotAttendDetails;
			//System.out.print(allotAttendDetails);
		}
	}

	@Test
	public List<String> toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		// public void toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AllotAttendCaseDataBase allotAttendCase=new AllotAttendCaseDataBase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT name, address, CONTACT_DEMAND, AC_ID, MOBILE_NO " +
								"FROM DVVNL_MRI_DATA_TEST..consumer_master_" + mmyyyy + " " +
								"WHERE ac_id='" + acId + "'")) {

			ResultSet n = ps.executeQuery();
			ArrayList<String> ConsumerDetails = new ArrayList<>();
			while (n.next()) {
				ConsumerDetails.add(n.getString("name").trim());
				ConsumerDetails.add(n.getString("address").trim());
				ConsumerDetails.add(n.getString("CONTACT_DEMAND"));
				ConsumerDetails.add(n.getString("AC_ID"));
				ConsumerDetails.add(n.getString("MOBILE_NO"));
			}
			//System.out.println(ConsumerDetails);
			return ConsumerDetails;
		}
	}

	@Test
	public List<String> toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AllotAttendCaseDataBase allotAttendCase=new AllotAttendCaseDataBase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT rmk, rmk_date, RMK_SOURCE, RMK_BY, PRIORITY_CODE " +
								"FROM " + databaseName + "..marking_trans " +
								"WHERE ac_id = '" + acId + "'")) {

			ResultSet rs = ps.executeQuery();
			DateFormat df = new DateFormat();
			ArrayList<String> markingDetails = new ArrayList<>();

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

	@Test
	public List<String> toverifyFieldIrregularitiesMainMeterInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyFieldIrregularitiesMainMeterInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AllotAttendCaseDataBase allotAttendCase=new AllotAttendCaseDataBase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"  Select top 1 irregularity_main from  "+databaseName+"..FIELD_VIGILANCE_TRANS where AC_ID='" + acId + "' order by VISIT_DATE desc")) {

			ResultSet rs = ps.executeQuery();
			
			ArrayList<String> irregularityMain = new ArrayList<>();

			while (rs.next()) {
				String text=rs.getString("irregularity_main").trim();
				String[] irregularities = text.split(",");
				for (String irregularity : irregularities) {
					irregularityMain.add(irregularity.trim());
					// System.out.println(text);
				}

			}
			return irregularityMain;
		}


	}
	@Test
	public List<String> toverifyFieldIrregularitiesPoleMeterInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyFieldIrregularitiesPoleMeterInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AllotAttendCaseDataBase allotAttendCase=new AllotAttendCaseDataBase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"  Select top 1 irregularity_pole from  "+databaseName+"..FIELD_VIGILANCE_TRANS where AC_ID='" + acId + "' order by VISIT_DATE desc")) {

			ResultSet rs = ps.executeQuery();
			
			ArrayList<String> irregularityPole = new ArrayList<>();

			while (rs.next()) {
				String text=rs.getString("irregularity_pole").trim();
				String[] irregularities = text.split(",");
				for (String irregularity : irregularities) {
					irregularityPole.add(irregularity.trim());
					
				}

			}
			return irregularityPole;
			// System.out.println(irregularityPole);
		}


	}
}



