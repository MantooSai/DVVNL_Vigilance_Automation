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


public class AssessmentRealisationCaseDatabase {
	private PropertyFile pf; 
    CommonDataPage cdp;

	public static String mmyyyy ;
	public static String userNameEe ;
	public static String databaseName;
	public static String logindatabaseName ;
	public static String divName ;
	public static String consumerMasterDbName ;
	public static String DB_URL;
	public static String countOfRelisationCase = null;
	public static String acId=null;;
	private static  String DB_USER;
	private static  String DB_PASSWORD;
	
    public AssessmentRealisationCaseDatabase() {
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
    	userNameEe = pf.getProperty("userNameEe");
    	databaseName = pf.getProperty("databaseName");
    	logindatabaseName = pf.getProperty("logindatabaseName");
    	consumerMasterDbName = pf.getProperty("consumerMasterDbName");
    	DB_URL = pf.getProperty("DB_URL");
    	DB_USER = pf.getProperty("DB_USER");
    	DB_PASSWORD = pf.getProperty("DB_PASSWORD");
    	divName = pf.getProperty("divName");
        
    }

	// Initialize `acId` before the tests run

	public void initializeAcId() throws EncryptedDocumentException, IOException {
		WebDriver driver = null;
		CommonDataPage cdp = new CommonDataPage(driver);
		String  acId1 = cdp.getAcIdFromExcelFile();
		this.acId=acId1;
	}

	@Test
	public String toverifyCountAssessmentRealisationCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyCountAssessmentRealisationCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentRealisationCaseDatabase allotAttendCase=new AssessmentRealisationCaseDatabase();
		allotAttendCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"\r\n"
						+ "WITH RealisationCaseCounts AS (\r\n"
						+ "    SELECT \r\n"
						+ "        CASE_NO, \r\n"
						+ "        SUM(PAYMENT_AMOUNT) AS TotalPaymentAmount\r\n"
						+ "    FROM DVVNL_VIGILANCE..REALISATION_TRANS\r\n"
						+ "    GROUP BY CASE_NO\r\n"
						+ "),\r\n"
						+ "RankedResults AS (\r\n"
						+ "        SELECT ZONE, PL.CIRCLE, PL.DIV_NAME, PT.PRIORITY_CODE AS HIGH, PT.CASE_NO, CM.AC_ID, NAME, ADDRESS,SUPPLY_TYPE, \r\n"
						+ "        ASSESSMENT_PROPOSED_TAG, ASSESSMENT_UNIT, ASSESSMENT_AMOUNT, ISNULL(RCC.TotalPaymentAmount, 0) AS PAYMENT_AMOUNT, \r\n"
						+ "        ASSESSMENT_AMOUNT - TotalPaymentAmount AS BALANCE_AMOUNT, AP.REMARK,\r\n"
						+ "        ROW_NUMBER() OVER (PARTITION BY PT.CASE_NO ORDER BY PT.CASE_NO) AS RowNum\r\n"
						+ "    FROM "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM\r\n"
						+ "    INNER JOIN "+logindatabaseName+"..PVVNL_LOGIN PL ON PL.DIV_CD = CM.DIV\r\n"
						+ "    INNER JOIN "+databaseName+"..MARKING_TRANS MT ON CM.AC_ID = MT.AC_ID\r\n"
						+ "    INNER JOIN "+databaseName+"..PRIORITY_TRANS PT ON MT.CASE_NO = PT.CASE_NO\r\n"
						+ "    INNER JOIN "+databaseName+"..FIELD_VIGILANCE_TRANS FVT ON FVT.CASE_NO = PT.CASE_NO\r\n"
						+ "    INNER JOIN "+databaseName+"..ASSESSMENT_PROPOSED_TRANS AP ON AP.CASE_NO = PT.CASE_NO\r\n"
						+ "    INNER JOIN "+databaseName+"..ASSESSMENT_TRANS ATT ON ATT.CASE_NO = PT.CASE_NO\r\n"
						+ "    LEFT JOIN RealisationCaseCounts RCC ON PT.CASE_NO = RCC.CASE_NO\r\n"
						+ "    WHERE PT.PRIORITY_CODE = '1' AND OPR_ID='"+userNameEe+"' AND DESG='EE'\r\n"
						+ "      AND (RCC.TotalPaymentAmount IS NULL OR ASSESSMENT_AMOUNT > RCC.TotalPaymentAmount )\r\n"
						+ ")\r\n"
						+ "SELECT \r\n"
						+ "count(*) as totalRealisationCase\r\n"
						+ "FROM RankedResults\r\n"
						+ "WHERE RowNum = 1")) {

			ResultSet n = ps.executeQuery();
			if (n.next()) {
				countOfRelisationCase = n.getString("totalRealisationCase").trim();
			}
			return countOfRelisationCase;
			//System.out.println(countOfRelisationCase);	
			}
	}

	@Test
	public List<String> toverifyAssessmentRealisationCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyAssessmentRealisationCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentRealisationCaseDatabase AssessmentRealsiationCase=new AssessmentRealisationCaseDatabase();
		AssessmentRealsiationCase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"WITH RealisationCaseCounts AS (\r\n"
						+ "    SELECT \r\n"
						+ "        CASE_NO, \r\n"
						+ "        SUM(PAYMENT_AMOUNT) AS TotalPaymentAmount\r\n"
						+ "    FROM "+databaseName+"..REALISATION_TRANS\r\n"
						+ "    GROUP BY CASE_NO\r\n"
						+ "),\r\n"
						+ "RankedResults AS (\r\n"
						+ "        SELECT ZONE, PL.CIRCLE, PL.DIV_NAME, Pm.PRIORITY_NAME , PT.CASE_NO, CM.AC_ID, NAME, ADDRESS,SUPPLY_TYPE,CONCAT(FVT.IRREGULARITY_MAIN, ',', FVT.IRREGULARITY_POLE) AS IRREGULARITY,FAT.ACTION,\r\n"
						+ "        ASSESSMENT_PROPOSED_TAG, ASSESSMENT_UNIT, ASSESSMENT_AMOUNT, ISNULL(RCC.TotalPaymentAmount, 0) AS PAYMENT_AMOUNT, \r\n"
						+ "       ( ASSESSMENT_AMOUNT - ISNULL(RCC.TotalPaymentAmount, 0)) AS BALANCE_AMOUNT, AP.REMARK,\r\n"
						+ "        ROW_NUMBER() OVER (PARTITION BY PT.CASE_NO ORDER BY PT.CASE_NO) AS RowNum\r\n"
						+ "    FROM "+consumerMasterDbName+"..CONSUMER_MASTER_122024 CM\r\n"
						+ "    INNER JOIN "+logindatabaseName+"..PVVNL_LOGIN PL ON PL.DIV_CD = CM.DIV\r\n"
						+ "    INNER JOIN "+databaseName+"..MARKING_TRANS MT ON CM.AC_ID = MT.AC_ID\r\n"
						+ "    INNER JOIN "+databaseName+"..PRIORITY_TRANS PT ON MT.CASE_NO = PT.CASE_NO\r\n"
						+ "	  INNER JOIN "+databaseName+"..PRIORITY_MASTER Pm ON pm.PRIORITY_CODE = PT.PRIORITY_CODE\r\n"
						+ "    INNER JOIN "+databaseName+"..FIELD_VIGILANCE_TRANS FVT ON FVT.CASE_NO = PT.CASE_NO\r\n"
						+ "	   INNER JOIN "+databaseName+"..FIELD_ACTION_TRANS FAT ON FAT.CASE_NO = PT.CASE_NO\r\n"
						+ "    INNER JOIN "+databaseName+"..ASSESSMENT_PROPOSED_TRANS AP ON AP.CASE_NO = PT.CASE_NO\r\n"
						+ "    INNER JOIN "+databaseName+"..ASSESSMENT_TRANS ATT ON ATT.CASE_NO = PT.CASE_NO\r\n"
						+ "    LEFT JOIN RealisationCaseCounts RCC ON PT.CASE_NO = RCC.CASE_NO\r\n"
						+ "    WHERE PT.PRIORITY_CODE = '1' AND OPR_ID='EE@Sai' AND DESG='EE'\r\n"
						+ "      AND (RCC.TotalPaymentAmount IS NULL OR ASSESSMENT_AMOUNT > RCC.TotalPaymentAmount )\r\n"
						+ ")\r\n"
						+ "SELECT \r\n"
						+ "ZONE, CIRCLE, DIV_NAME, PRIORITY_NAME, CASE_NO, AC_ID, NAME, ADDRESS,SUPPLY_TYPE,IRREGULARITY,ACTION, ASSESSMENT_PROPOSED_TAG, ASSESSMENT_UNIT, \r\n"
						+ "    ASSESSMENT_AMOUNT, PAYMENT_AMOUNT, BALANCE_AMOUNT, REMARK\r\n"
						+ "FROM RankedResults\r\n"
						+ "WHERE RowNum = 1 and ac_id='7823332000'\r\n"
						+ "ORDER BY CASE_NO;\r\n"
						+ "")) {
			

			ResultSet n = ps.executeQuery();
			List<String> AssessmentRealsiationCaseDetails = new ArrayList<>();
			if (n.next()) {
				AssessmentRealsiationCaseDetails.add(n.getString("ZONE").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("CIRCLE").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("DIV_NAME").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("PRIORITY_NAME").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("CASE_NO").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("ac_id").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("NAME").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("address").trim());
				AssessmentRealsiationCaseDetails.add(n.getString("supply_type"));
				AssessmentRealsiationCaseDetails.add(n.getString("IRREGULARITY"));
				AssessmentRealsiationCaseDetails.add(n.getString("ACTION"));
				AssessmentRealsiationCaseDetails.add(n.getString("ASSESSMENT_PROPOSED_TAG"));
				AssessmentRealsiationCaseDetails.add(n.getString("ASSESSMENT_UNIT"));
				AssessmentRealsiationCaseDetails.add(n.getString("ASSESSMENT_AMOUNT"));
				AssessmentRealsiationCaseDetails.add(n.getString("PAYMENT_AMOUNT"));
				AssessmentRealsiationCaseDetails.add(n.getString("BALANCE_AMOUNT"));
				AssessmentRealsiationCaseDetails.add(n.getString("REMARK").trim());

			}
		return AssessmentRealsiationCaseDetails;
		//System.out.print(AssessmentRealsiationCaseDetails);
		}
	}

	@Test
	public List<String> toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		// public void toverifyConsumerDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentRealisationCaseDatabase allotAttendCase=new AssessmentRealisationCaseDatabase();
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
				ConsumerDetails.add(n.getString("CONTACT_DEMAND").trim());
				ConsumerDetails.add(n.getString("AC_ID").trim());
				ConsumerDetails.add(n.getString("MOBILE_NO").trim());
			}
			//System.out.println(ConsumerDetails);
			return ConsumerDetails;
		}
	}

	@Test
	public List<String> toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentRealisationCaseDatabase allotAttendCase=new AssessmentRealisationCaseDatabase();
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
		AssessmentRealisationCaseDatabase allotAttendCase=new AssessmentRealisationCaseDatabase();
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
		AssessmentRealisationCaseDatabase allotAttendCase=new AssessmentRealisationCaseDatabase();
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

		@Test
		public List<String> savedAssessmentRealisationDetailsInExcelFile() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
			//public void savedAssessmentRealisationDetailsInExcelFile() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			AssessmentRealisationCaseDatabase allotAttendCase=new AssessmentRealisationCaseDatabase();
			allotAttendCase.initializeAcId();
			try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					PreparedStatement ps = con.prepareStatement(
							"SELECT top 1 * FROM "+databaseName+"..realisation_trans  where AC_ID='"+acId+"' order by FEED_DATE desc\r\n"
							+ "")) {
				ResultSet rs = ps.executeQuery();
				ArrayList<String> realistionAssessmentDetails = new ArrayList<>();
				while (rs.next()) {
					realistionAssessmentDetails.add(rs.getString("CASE_NO").trim());
					realistionAssessmentDetails.add(rs.getString("AC_ID").trim());
					realistionAssessmentDetails.add(rs.getString("PAYMENT_AMOUNT").trim());
					realistionAssessmentDetails.add(rs.getString("PAYMENT_DATE").trim());
					realistionAssessmentDetails.add(rs.getString("RECEIPT_NO").trim());
					realistionAssessmentDetails.add(rs.getString("REMARK").trim());
					realistionAssessmentDetails.add(rs.getString("FEED_BY").trim());
					realistionAssessmentDetails.add(rs.getString("FEED_DATE").trim());
				}
				return realistionAssessmentDetails;
				//System.out.println(realistionAssessmentDetails);
		}


	}
}




