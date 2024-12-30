package com.demo.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;

public class AssessmentApprovalCaseDataBase2 {

	private PropertyFile pf; 
	CommonDataPage cdp;
	WebDriver driver;
	
	public static String userIdET ;
	public static String databaseName;
	public static String logindatabaseName ;
	public static String divName ;
	public static String consumerMasterDbName ;
	public static String DB_URL;
	public static String countOFAssessementApprovalCase = null;
	public static String acId=null;;
	private static  String DB_USER;
	private static  String DB_PASSWORD;
	LocalDate currentDate = LocalDate.now();
	 public  String mmyyyy=currentDate.format(DateTimeFormatter.ofPattern("MMYYYY")); 
	public AssessmentApprovalCaseDataBase2() {
		try {
			cdp=new CommonDataPage( driver);
			pf = new PropertyFile();
			initializeProperties(); 
			acId=cdp.getAcIdFromExcelFile();
			//caseNo= cdp.getCaseNoFromExcelFile();
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize PropertyFile", e);
		}
	}

	private void initializeProperties() {
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


	public void initializeAcId() throws EncryptedDocumentException, IOException {
		WebDriver driver = null;
		CommonDataPage cdp = new CommonDataPage(driver);
		String  acId1 = cdp.getAcIdFromExcelFile();
		this.acId=acId1;
	}
	@Test
	public String toverifyCountAssessmentApprovalCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyCountAssessmentApprovalCaseInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentApprovalCaseDataBase2 assessmentApprovalCaseDataBase=new AssessmentApprovalCaseDataBase2();
		assessmentApprovalCaseDataBase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT count(DISTINCT PT.CASE_NO) as Approval_Case_Count\r\n"
								+ "FROM "+databaseName+"..FIELD_VIGILANCE_TRANS FVT\r\n"
								+ "INNER JOIN\r\n"
								+ ""+databaseName+"..FIELD_ACTION_TRANS FAT\r\n"
								+ "ON FAT.CASE_NO=FVT.CASE_NO\r\n"
								+ "INNER JOIN "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM\r\n"
								+ "ON CM.AC_ID=FVT.AC_ID\r\n"
								+ "INNER JOIN "+databaseName+"..PRIORITY_TRANS PT\r\n"
								+ "ON PT.CASE_NO=FAT.CASE_NO\r\n"
								+ "INNER JOIN "+logindatabaseName+"..PVVNL_LOGIN PL ON PL.DIV_CD=CM.DIV\r\n"
								+ "WHERE CM.DIV_NAME='"+divName+"' AND PRIORITY_CODE='1' AND OPR_ID='"+userIdET+"'\r\n"
								+ "AND FAT.CASE_NO NOT IN(SELECT AP.CASE_NO FROM "+databaseName+"..ASSESSMENT_PROPOSED_TRANS AP)")) {

			ResultSet n = ps.executeQuery();

			if (n.next()) {
				countOFAssessementApprovalCase = n.getString("Approval_Case_Count").trim();
			}
			return countOFAssessementApprovalCase;
			//System.out.println(countOFAssessementApprovalCase);	
		}
	}

	@Test
	//public List<String> toverifyAssessmentApprovalCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
			public void toverifyAssessmentApprovalCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentApprovalCaseDataBase2 assessmentApprovalCaseDataBase=new AssessmentApprovalCaseDataBase2();
		assessmentApprovalCaseDataBase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT \\n\" +\r\n"
						+ "                \"    ROW_NUMBER() OVER (ORDER BY ISNULL(PM.Priority_Code, 9), V.Case_No) AS SrNo,\\n\" +\r\n"
						+ "                \"    V.ID, V.Case_No, V.AC_ID, V.Visit_Date, V.Visit_By,\\n\" +\r\n"
						+ "                \"    ISNULL(IRREGULARITY_FOUND_TAG_MAIN, 'N') AS IRREGULARITY_FOUND_TAG_MAIN,\\n\" +\r\n"
						+ "                \"    'Main-' + ISNULL(IRREGULARITY_MAIN, '') + '; Pole-' + ISNULL(IRREGULARITY_POLE, '') AS IRREGULARITY,\\n\" +\r\n"
						+ "                \"    ISNULL(OTHER_IRREGULARITY_MAIN, '') AS OTHER_IRREGULARITY_MAIN,\\n\" +\r\n"
						+ "                \"    ISNULL(POLE_METER_EXISTS_TAG, 'N') AS POLE_METER_EXISTS_TAG,\\n\" +\r\n"
						+ "                \"    ISNULL(IRREGULARITY_FOUND_TAG_POLE, 'N') AS IRREGULARITY_FOUND_TAG_POLE,\\n\" +\r\n"
						+ "                \"    ISNULL(IRREGULARITY_POLE, '') AS IRREGULARITY_POLE,\\n\" +\r\n"
						+ "                \"    ISNULL(OTHER_IRREGULARITY_POLE, '') AS OTHER_IRREGULARITY_POLE,\\n\" +\r\n"
						+ "                \"    IRREGULARITY_EVIDENCE_PATH, CONNECTION_AVAILABLE,\\n\" +\r\n"
						+ "                \"    CASE WHEN V.AC_ID IS NULL THEN OM.NAME ELSE M.NAME END AS Name,\\n\" +\r\n"
						+ "                \"    CASE WHEN V.AC_ID IS NULL THEN OM.Address ELSE M.Address END AS Address,\\n\" +\r\n"
						+ "                \"    ISNULL(CASE WHEN V.AC_ID IS NULL THEN OM.MOBILE_NO ELSE M.MOBILE_NO END, 'NA') AS MOBILE_NO,\\n\" +\r\n"
						+ "                \"    CASE WHEN V.AC_ID IS NULL THEN 0 ELSE M.CONTACT_DEMAND END AS CONTACT_DEMAND,\\n\" +\r\n"
						+ "                \"    CASE WHEN V.AC_ID IS NULL THEN '-' ELSE M.CONTACT_DEMAND_UNIT END AS CONTACT_DEMAND_UNIT,\\n\" +\r\n"
						+ "                \"    L.ZPC_NAME, L.CIRCLE, L.DIV_NAME,\\n\" +\r\n"
						+ "                \"    ISNULL(Process, 'NA') AS Process,\\n\" +\r\n"
						+ "                \"    ISNULL(PM.PRIORITY_code, 9) AS PRIORITY_CODE,\\n\" +\r\n"
						+ "                \"    ISNULL(PM.PRIORITY_NAME, '') AS CasePriority\\n\" +\r\n"
						+ "                \"FROM " + databaseName + "..FIELD_VIGILANCE_TRANS V\\n\" +\r\n"
						+ "                \"LEFT JOIN " + databaseName + "..Priority_Trans PT ON V.CASE_NO = PT.CASE_NO\\n\" +\r\n"
						+ "                \"LEFT JOIN " + databaseName + "..Priority_Master PM ON PM.Priority_Code = PT.Priority_Code\\n\" +\r\n"
						+ "                \"LEFT JOIN " + consumerMasterDbName + "..cconsumer_Master_"+mmyyyy+" M ON M.AC_ID = V.AC_ID\\n\" +\r\n"
						+ "                \"LEFT JOIN " + databaseName + "..CONSUMER_MASTER_OTHER OM ON OM.Case_No = V.Case_No\\n\" +\r\n"
						+ "                \"LEFT JOIN (\" +\r\n"
						+ "                \"   SELECT DISTINCT Zone_Code, ZPC_NAME, Circle, Div_CD, Div_Name \\n\" +\r\n"
						+ "                \"   FROM " + logindatabaseName + "..PVVNL_LOGIN \\n\" +\r\n"
						+ "                \"   WHERE Zone_Code <> 'All' AND DIV_CD <> 'All') L\\n\" +\r\n"
						+ "                \"ON L.Div_CD = ISNULL(M.DIV, '999999999999')\\n\" +\r\n"
						+ "                \"LEFT JOIN " + databaseName + "..ASSESSMENT_PROPOSED_TRANS APT ON APT.CASE_NO = V.CASE_NO\\n\" +\r\n"
						+ "                \"WHERE APT.CASE_NO IS NULL AND V.Case_No IS NOT NULL\\n\" +\r\n"
						+ "                \"AND 1 = CASE WHEN V.AC_ID IS NOT NULL AND DIV_CD IN (SELECT value FROM STRING_SPLIT(?, ',')) THEN 1\\n\" +\r\n"
						+ "                \"             WHEN V.AC_ID IS NULL AND OM.DIV_NAME IN (SELECT Div_Name FROM " + logindatabaseName + "..PVVNL_LOGIN \" +\r\n"
						+ "                \"             WHERE DIV_CD IN (SELECT value FROM STRING_SPLIT(?, ','))) THEN 1\\n\" +\r\n"
						+ "                \"             ELSE 0 END")) {

			ResultSet n = ps.executeQuery();
			//System.out.println(ps.toString());
			ArrayList<String> assessmentApprovalCaseDetails=new ArrayList<String>();
			if (n.next()) {
				assessmentApprovalCaseDetails.add(n.getString("ZONE"));
				assessmentApprovalCaseDetails.add(n.getString("CIRCLE"));
				assessmentApprovalCaseDetails.add(n.getString("DIV_NAME"));
				assessmentApprovalCaseDetails.add(n.getString("PRIORITY_NAME"));
				assessmentApprovalCaseDetails.add(n.getString("CASE_NO"));
				assessmentApprovalCaseDetails.add(n.getString("AC_ID"));
				assessmentApprovalCaseDetails.add(n.getString("NAME"));
				assessmentApprovalCaseDetails.add(n.getString("ADDRESS"));
				assessmentApprovalCaseDetails.add(n.getString("MOBILE_NO"));
				assessmentApprovalCaseDetails.add(n.getString("CONTACT_DEMAND"));
				assessmentApprovalCaseDetails.add(n.getString("CONTACT_DEMAND_UNIT"));
				assessmentApprovalCaseDetails.add(n.getString("PROCESS"));
				assessmentApprovalCaseDetails.add(n.getString("IRREGULARITY_FOUND_TAG_MAIN"));
				assessmentApprovalCaseDetails.add(n.getString("VISIT_BY"));
				DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
				String dateTimeStr = n.getString("VISIT_DATE");
				// Parse the string into a LocalDateTime object
				LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);

				// Define the desired output format
				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

				// Format the LocalDateTime into the desired format
				String formattedDate = dateTime.format(outputFormatter);   
				assessmentApprovalCaseDetails.add(formattedDate);

			}
			//return assessmentApprovalCaseDetails;
			System.out.println(assessmentApprovalCaseDetails);	
		}
	}

	@Test
	public List<String> assessmentProposedDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void assessmentProposedDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentApprovalCaseDataBase2 assessmentApprovalCaseDataBase=new AssessmentApprovalCaseDataBase2();
		assessmentApprovalCaseDataBase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select TOP 1 * from DVVNL_VIGILANCE_TEST..ASSESSMENT_PROPOSED_TRANS WHERE AC_ID='"+acId+"'order by REVIEW_DATE desc \r\n"
								+ "")) {

			ResultSet n = ps.executeQuery();
			ArrayList<String>countOFAssessementApprovalCase=new ArrayList<String>();
			if (n.next()) {
				countOFAssessementApprovalCase.add(n.getString("CASE_NO"));
				countOFAssessementApprovalCase.add (n.getString("AC_ID"));
				countOFAssessementApprovalCase.add( n.getString("ASSESSMENT_PROPOSED_TAG"));
				countOFAssessementApprovalCase.add( n.getString("REMARK"));
				countOFAssessementApprovalCase.add( n.getString("REVIEW_BY"));
				countOFAssessementApprovalCase.add(n.getString("REVIEW_DATE"));
			}
			return countOFAssessementApprovalCase;
			//System.out.println(countOFAssessementApprovalCase);	
		}
	}

}
