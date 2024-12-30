package com.demo.database;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.pom.CommonDataPage;
import com.utility.DateFormat;
import com.utility.PropertyFile;



public class AssessmentFeedingCaseDatabase {
	WebDriver driver;
	private PropertyFile pf; 
    CommonDataPage cdp;

	
	public static String userIdET ;
	public static String databaseName;
	public static String logindatabaseName ;
	public static String divName ;
	public static String consumerMasterDbName ;
	public static String DB_URL;
	public static String countOfFeedCase = null;
	public static String acId=null;
	private static  String DB_USER;
	private static  String DB_PASSWORD;
	LocalDate currentDate = LocalDate.now();
	 public  String mmyyyy=currentDate.format(DateTimeFormatter.ofPattern("MMYYYY")); 
    public AssessmentFeedingCaseDatabase() {
    	
        try {
        	cdp=new CommonDataPage( driver);
            pf = new PropertyFile();
            initializeProperties(); 
            acId=cdp.getAcIdFromExcelFile();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize PropertyFile", e);
        }
    }

    private void initializeProperties() throws EncryptedDocumentException, IOException {
    	
    	//acId=cdp.getAcIdFromExcelFile();
    	pf.databaseCredentialsDetails();
    	
    	userIdET = pf.getProperty("userIdET");
    	databaseName = pf.getProperty("databaseName");
    	logindatabaseName = pf.getProperty("logindatabaseName");
    	consumerMasterDbName = pf.getProperty("consumerMasterDbName");
    	DB_URL = pf.getProperty("DB_URL");
    	DB_USER = pf.getProperty("DB_USER");
    	DB_PASSWORD = pf.getProperty("DB_PASSWORD");
    	divName = pf.getProperty("divName");
        
    }

	@Test
	public String toverifyCountAssessmentFeedingCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyCountAssessmentFeedingCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentFeedingCaseDatabase assessmentFeedingCase=new AssessmentFeedingCaseDatabase();
		assessmentFeedingCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT COUNT(DISTINCT AP.CASE_NO) AS TOTAL  FROM "+consumerMasterDbName+"..CONSUMER_MASTER_122024 CM\r\n"
						+ "INNER JOIN \r\n"
						+ ""+databaseName+"..MARKING_TRANS MT ON CM.AC_ID=MT.AC_ID\r\n"
						+ "LEFT JOIN\r\n"
						+ ""+databaseName+"..PRIORITY_TRANS PT ON MT.CASE_NO=PT.CASE_NO\r\n"
						+ "INNER JOIN\r\n"
						+ ""+databaseName+"..FIELD_ACTION_TRANS FVT ON FVT.CASE_NO=PT.CASE_NO\r\n"
						+ "INNER JOIN "+databaseName+"..ASSESSMENT_PROPOSED_TRANS AP ON AP.CASE_NO=PT.CASE_NO\r\n"
						+ "WHERE MT.PRIORITY_CODE='1' AND PT.PRIORITY_CODE='1' AND DIV_NAME='"+divName+"'\r\n"
						+ "and pt.CASE_NO not in(select CASE_NO from "+databaseName+"..ASSESSMENT_TRANS)")) {

			ResultSet n = ps.executeQuery();
			//System.out.println(acId);
			if (n.next()) {
				countOfFeedCase = n.getString("TOTAL").trim();
			}
			return countOfFeedCase;
			//System.out.println(countOfFeedCase);
		}
	}

	@Test
	 public List<String> toverifyAllotAttendCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyAllotAttendCaseDetailsInDatabase() throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentFeedingCaseDatabase AssessmentFedingCase=new AssessmentFeedingCaseDatabase();
		AssessmentFedingCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT DISTINCT AP.CASE_NO,ZONE,CIRCLE,DIV_NAME,pm.PRIORITY_NAME,cm.AC_ID,cm.NAME,cm.ADDRESS,cm.SUPPLY_TYPE,CONCAT(FAT.IRREGULARITY_MAIN, ',', FAT.IRREGULARITY_POLE) AS IRREGULARITY,FVT.ACTION,AP.ASSESSMENT_PROPOSED_TAG,AP.REMARK\r\n"
						+ "\r\n"
						+ "FROM "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM\r\n"
						+ "INNER JOIN \r\n"
						+ ""+databaseName+"..MARKING_TRANS MT ON CM.AC_ID=MT.AC_ID\r\n"
						+ "LEFT JOIN\r\n"
						+ ""+databaseName+"..PRIORITY_TRANS PT ON MT.CASE_NO=PT.CASE_NO\r\n"
						+ "INNER JOIN\r\n"
						+ ""+databaseName+"..PRIORITY_MASTER pm  ON pm.PRIORITY_CODE=pt.PRIORITY_CODE\r\n"
						+ "INNER JOIN\r\n"
						+ ""+databaseName+"..FIELD_VIGILANCE_TRANS fAT ON fAT.CASE_NO=PT.CASE_NO\r\n"
						+ "INNER JOIN\r\n"
						+ ""+databaseName+"..FIELD_ACTION_TRANS FVT ON FVT.CASE_NO=PT.CASE_NO\r\n"
						+ "INNER JOIN "+databaseName+"..ASSESSMENT_PROPOSED_TRANS AP ON AP.CASE_NO=PT.CASE_NO\r\n"
						+ "WHERE MT.PRIORITY_CODE='1' AND PT.PRIORITY_CODE='1' AND DIV_NAME='"+divName+"'\r\n"
						+ "and pt.CASE_NO not in(select CASE_NO from "+databaseName+"..ASSESSMENT_TRANS)")) {

			ResultSet n = ps.executeQuery();
			List<String> AssessmentFeedingDetails = new ArrayList<>();
			if (n.next()) {
				AssessmentFeedingDetails.add(n.getString("ZONE").trim());
				AssessmentFeedingDetails.add(n.getString("CIRCLE").trim());
				AssessmentFeedingDetails.add(n.getString("DIV_NAME").trim());
				AssessmentFeedingDetails.add(n.getString("PRIORITY_NAME").trim());
				AssessmentFeedingDetails.add(n.getString("CASE_NO").trim());
				AssessmentFeedingDetails.add(n.getString("ac_id").trim());
				AssessmentFeedingDetails.add(n.getString("NAME").trim());
				AssessmentFeedingDetails.add(n.getString("address").trim());
				AssessmentFeedingDetails.add(n.getString("supply_type").trim());
				AssessmentFeedingDetails.add(n.getString("IRREGULARITY").trim());
				AssessmentFeedingDetails.add(n.getString("ACTION").trim());
				AssessmentFeedingDetails.add(n.getString("ASSESSMENT_PROPOSED_TAG").trim());
				AssessmentFeedingDetails.add(n.getString("REMARK").trim());

			}
			return AssessmentFeedingDetails;
			// System.out.print(AssessmentFeedingDetails);
		}
	}

	@Test
	//public List<String> toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		 public void toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentFeedingCaseDatabase allotAttendCase=new AssessmentFeedingCaseDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT name, address, CONTACT_DEMAND, AC_ID, MOBILE_NO " +
								"FROM DVVNL_MRI_DATA_TEST..consumer_master_" + mmyyyy + " " +
								"WHERE ac_id='" + acId + "'")) {

			ResultSet n = ps.executeQuery();
			System.out.println(acId);
			ArrayList<String> ConsumerDetails = new ArrayList<>();
			while (n.next()) {
				ConsumerDetails.add(n.getString("name").trim());
				ConsumerDetails.add(n.getString("address").trim());
				ConsumerDetails.add(n.getString("CONTACT_DEMAND").trim());
				ConsumerDetails.add(n.getString("AC_ID").trim());
				ConsumerDetails.add(n.getString("MOBILE_NO").trim());
			}
			System.out.println(ConsumerDetails);
			//return ConsumerDetails;
		}
	}

	@Test
	//public List<String> toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		public void toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentFeedingCaseDatabase allotAttendCase=new AssessmentFeedingCaseDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT rmk, rmk_date, RMK_SOURCE, RMK_BY, PRIORITY_CODE " +
								"FROM " + databaseName + "..marking_trans " +
								"WHERE ac_id = '" + acId + "'")) {

			ResultSet rs = ps.executeQuery();
			DateFormat df = new DateFormat();
			ArrayList<String> markingDetails = new ArrayList<>();
			System.out.println(acId);
			while (rs.next()) {
				markingDetails.add(rs.getString("rmk").trim());
				String rmkDateStr = rs.getString("rmk_date").trim();
				String formattedRmkDate = df.date_dd_mm_yyyy_Format(rmkDateStr);
				markingDetails.add(formattedRmkDate);
				markingDetails.add(rs.getString("RMK_SOURCE").trim());
				markingDetails.add(rs.getString("RMK_BY").trim());
				markingDetails.add(rs.getString("PRIORITY_CODE").trim());
			}
			//return markingDetails;
			System.out.println();
		}
	}

	@Test
	public List<String> toverifyFieldIrregularitiesMainMeterInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyFieldIrregularitiesMainMeterInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentFeedingCaseDatabase allotAttendCase=new AssessmentFeedingCaseDatabase();
		allotAttendCase.initializeProperties();
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
		AssessmentFeedingCaseDatabase allotAttendCase=new AssessmentFeedingCaseDatabase();
		allotAttendCase.initializeProperties();
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

		@Test
	 public List<String> savedAssessmentFeedingDetailsInExcelFile() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
			//public void savedAssessmentFeedingDetailsInExcelFile() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			AssessmentFeedingCaseDatabase allotAttendCase=new AssessmentFeedingCaseDatabase();
			allotAttendCase.initializeProperties();
			try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					PreparedStatement ps = con.prepareStatement(
							" SELECT top 1 * FROM  "+databaseName+"..assessment_trans where AC_ID='"+acId+"' order by feed_date desc")) {
				ResultSet rs = ps.executeQuery();
				ArrayList<String> feedAssessmentDetails = new ArrayList<>();
				while (rs.next()) {
					feedAssessmentDetails.add(rs.getString("CASE_NO").trim());
					feedAssessmentDetails.add(rs.getString("AC_ID").trim());
					feedAssessmentDetails.add(rs.getString("ASSESSMENT_UNIT").trim());
					feedAssessmentDetails.add(rs.getString("ASSESSMENT_AMOUNT").trim());
					feedAssessmentDetails.add(rs.getString("ASSESSMENT_DATE").trim());
					feedAssessmentDetails.add(rs.getString("EVIDENCE_PATH").trim());
					feedAssessmentDetails.add(rs.getString("EVIDENCE_NAME").trim());
					feedAssessmentDetails.add(rs.getString("REMARK").trim());
					feedAssessmentDetails.add(rs.getString("FEED_BY").trim());
					feedAssessmentDetails.add(rs.getString("FEED_DATE").trim());
				}
				return feedAssessmentDetails;
				//System.out.println(feedAssessmentDetails);
		}


	}
}




