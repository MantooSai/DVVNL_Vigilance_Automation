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

public class AssessmentApprovalCaseDataBase {

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
	public AssessmentApprovalCaseDataBase() {
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
		AssessmentApprovalCaseDataBase assessmentApprovalCaseDataBase=new AssessmentApprovalCaseDataBase();
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
	public List<String> toverifyAssessmentApprovalCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//	public void toverifyAssessmentApprovalCaseDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentApprovalCaseDataBase assessmentApprovalCaseDataBase=new AssessmentApprovalCaseDataBase();
		assessmentApprovalCaseDataBase.initializeAcId();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"SELECT DISTINCT \r\n"
								+ "    CM.ZONE,\r\n"
								+ "    CM.CIRCLE,\r\n"
								+ "    CM.DIV_NAME,\r\n"
								+ "    PM.PRIORITY_NAME,\r\n"
								+ "    PT.CASE_NO,\r\n"
								+ "    CM.AC_ID,\r\n"
								+ "    NAME,\r\n"
								+ "    ADDRESS,\r\n"
								+ "    MOBILE_NO,\r\n"
								+ "    CONTACT_DEMAND,\r\n"
								+ "    CONTACT_DEMAND_UNIT,\r\n"
								+ "    PROCESS,\r\n"
								+ "    IRREGULARITY_FOUND_TAG_MAIN,\r\n"
								+ "    VISIT_BY,\r\n"
								+ "    VISIT_DATE\r\n"
								+ "FROM \r\n"
								+ "    "+databaseName+"..FIELD_VIGILANCE_TRANS FVT\r\n"
								+ "INNER JOIN \r\n"
								+ "    "+databaseName+"..FIELD_ACTION_TRANS FAT \r\n"
								+ "    ON FAT.CASE_NO = FVT.CASE_NO\r\n"
								+ "INNER JOIN \r\n"
								+ "    "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM \r\n"
								+ "    ON CM.AC_ID = FVT.AC_ID\r\n"
								+ "INNER JOIN \r\n"
								+ "    "+databaseName+"..PRIORITY_TRANS PT \r\n"
								+ "    ON PT.CASE_NO = FAT.CASE_NO\r\n"
								+ "INNER JOIN \r\n"
								+ "    "+databaseName+"..PRIORITY_Master PM \r\n"
								+ "    ON PM.PRIORITY_CODE = PT.PRIORITY_CODE\r\n"
								+ "INNER JOIN \r\n"
								+ "    "+logindatabaseName+"..PVVNL_LOGIN PL \r\n"
								+ "    ON PL.DIV_CD = CM.DIV\r\n"
								+ "WHERE \r\n"
								+ "    CM.DIV_NAME = '"+divName+"' \r\n"
								+ "    AND PT.PRIORITY_CODE = '1' \r\n"
								+ "    AND CM.AC_ID = '"+acId+"' \r\n"
								+ "    AND PL.OPR_ID = '"+userIdET+"'\r\n"
								+ "    AND FAT.CASE_NO NOT IN (\r\n"
								+ "        SELECT AP.CASE_NO \r\n"
								+ "        FROM "+databaseName+"..ASSESSMENT_PROPOSED_TRANS AP\r\n"
								+ "    );\r\n"
								+ "")) {

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
			return assessmentApprovalCaseDetails;
			//System.out.println(assessmentApprovalCaseDetails);	
		}
	}

	@Test
	public List<String> assessmentProposedDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void assessmentProposedDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		AssessmentApprovalCaseDataBase assessmentApprovalCaseDataBase=new AssessmentApprovalCaseDataBase();
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
