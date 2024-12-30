package com.live.database;

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
import org.testng.annotations.Test;

import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;

public class AssessmentApprovalCaseDataBase {

	private PropertyFile pf; 
    CommonDataPage cdp;

	public static String mmyyyy ;
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
	
    public AssessmentApprovalCaseDataBase() {
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
						"SELECT  Count(DISTINCT CM.AC_ID) as Approval_Case_Count FROM "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM\r\n"
						+ "INNER JOIN \r\n"
						+ ""+databaseName+"..MARKING_TRANS MT ON CM.AC_ID=MT.AC_ID\r\n"
						+ "LEFT JOIN\r\n"
						+ ""+databaseName+"..PRIORITY_TRANS PT ON MT.CASE_NO=PT.CASE_NO\r\n"
						+ "INNER JOIN\r\n"
						+ ""+databaseName+"..FIELD_VIGILANCE_TRANS FVT ON FVT.CASE_NO=PT.CASE_NO\r\n"
						+ "WHERE MT.PRIORITY_CODE='1' AND PT.PRIORITY_CODE='1' AND DIV_NAME='"+divName+"'\r\n"
						+ "AND FVT.CASE_NO NOT IN( SELECT CASE_NO FROM "+databaseName+"..ASSESSMENT_PROPOSED_TRANS)")) {

			ResultSet n = ps.executeQuery();
		
			if (n.next()) {
				countOFAssessementApprovalCase = n.getString("Approval_Case_Count").trim();
			}
			return countOFAssessementApprovalCase;
			//System.out.println(countOFAssessementApprovalCase);	
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
