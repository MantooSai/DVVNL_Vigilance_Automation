package com.demo.database;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.demo.pom.CommonDataPage;
import com.utility.PropertyFile;



public class CommonDatabase {
	private PropertyFile pf; 
    CommonDataPage cdp;
    WebDriver driver;
	
	public static String userNameEe ;
	public static String databaseName;
	public static String logindatabaseName ;
	public static String divName ;
	public static String consumerMasterDbName ;
	public static String DB_URL;
	public static String countOfRelisationCase = null;
	public static String acId=null;
	public static String caseNo=null;
	private static  String DB_USER;
	private static  String DB_PASSWORD;
	LocalDate currentDate = LocalDate.now();
	public  String mmyyyy=currentDate.format(DateTimeFormatter.ofPattern("MMYYYY")); 
    public CommonDatabase() {
        try {
        	cdp=new CommonDataPage( driver);
            pf = new PropertyFile();
            initializeProperties(); 
            acId=cdp.getAcIdFromExcelFile();
            caseNo= cdp.getCaseNoFromExcelFile();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize PropertyFile", e);
        }
    }

    private void initializeProperties() {
    	pf.databaseCredentialsDetails();
    	userNameEe = pf.getProperty("userNameEe");
    	databaseName = pf.getProperty("databaseName");
    	logindatabaseName = pf.getProperty("logindatabaseName");
    	consumerMasterDbName = pf.getProperty("consumerMasterDbName");
    	DB_URL = pf.getProperty("DB_URL");
    	DB_USER = pf.getProperty("DB_USER");
    	DB_PASSWORD = pf.getProperty("DB_PASSWORD");
    	divName = pf.getProperty("divName");
        
    }

	
	

	@Test
	public List<String> toverifyConsumerInformationInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyConsumerInformationInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select Name,ADDRESS,CONTACT_DEMAND,AC_ID,MOBILE_NO from "+consumerMasterDbName+"..consumer_master_"+mmyyyy+" where ac_id='"+acId+"'")) {

			ResultSet n = ps.executeQuery();
			//System.out.println(acId);
			ArrayList<String> consumerInforamtion=new ArrayList<String>();
			if (n.next()) {
				consumerInforamtion.add(n.getString("Name"));
				consumerInforamtion.add(n.getString("ADDRESS"));
				consumerInforamtion.add(n.getString("CONTACT_DEMAND"));
				consumerInforamtion.add(n.getString("AC_ID"));
				consumerInforamtion.add(n.getString("MOBILE_NO"));
			}
			return consumerInforamtion;
			//System.out.println(consumerInforamtion);	
			}
	}
	
	@Test
public List<String> toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		//public void toverifyMarkingDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select top 1 RMK,RMK_DATE,RMK_SOURCE,RMK_BY,PRIORITY_CODE from "+databaseName+"..marking_trans where CASE_NO='"+caseNo+"' order by RMK_DATE desc\r\n"
						+ "")) {
      
			ResultSet n = ps.executeQuery();
			
		  // System.out.println(acId);
		  // System.out.println(caseNo);
			ArrayList<String> markingDetails=new ArrayList<String>();
			if (n.next()) {
				markingDetails.add(n.getString("RMK"));
				markingDetails.add(dateFormat(n.getString("RMK_DATE")));
				markingDetails.add(n.getString("RMK_SOURCE"));
				markingDetails.add(n.getString("RMK_BY"));
				markingDetails.add(n.getString("PRIORITY_CODE"));
			}
		return markingDetails;
		//System.out.println(markingDetails);	
			}
	}
	
	@Test
	public List<String> toverifyIrregulatriesMainMeterDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//	public void toverifyIrregulatriesMainMeterDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select IRREGULARITY_MAIN from "+databaseName+"..FIELD_VIGILANCE_TRANS where AC_ID='"+acId+"'\r\n"
						+ "")) {
      
			ResultSet n = ps.executeQuery();
			
			
			ArrayList<String> markingDetails=new ArrayList<String>();
			if (n.next()) {
				markingDetails.addAll(splitRecord(n.getString("IRREGULARITY_MAIN")));
			}
			return markingDetails;
			//System.out.println(markingDetails);	
			}
	}
	@Test
	public List<String> toverifyIrregulatriesPoleMeterDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyIrregulatriesPoleMeterDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select IRREGULARITY_POLE from "+databaseName+"..FIELD_VIGILANCE_TRANS where AC_ID='"+acId+"'\r\n"
						+ "")) {
      
			ResultSet n = ps.executeQuery();
			
			
			ArrayList<String> markingDetails=new ArrayList<String>();
			if (n.next()) {
				markingDetails.addAll(splitRecord(n.getString("IRREGULARITY_POLE")));
			}
			return markingDetails;
			//System.out.println(markingDetails);	
			}
	}
	@Test
	 public List<String> toverifyActionDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyActionDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select ACTION,EVIDENCE_NAME from "+databaseName+"..FIELD_ACTION_TRANS where AC_ID='"+acId+"'\r\n"
						+ "")) {
			ResultSet n = ps.executeQuery();
			ArrayList<String> actionDetails=new ArrayList<String>();
			if (n.next()) {
				actionDetails.addAll(splitRecord(n.getString("Action")));
				//actionDetails.addAll(splitRecord(n.getString("EVIDENCE_NAME")));
			}
			return actionDetails;
			//System.out.println(actionDetails);	
			}
	}
	@Test
	public List<String> toverifyCaseReviewDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException {
		//public void toverifyCaseReviewDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select ASSESSMENT_PROPOSED_TAG, REMARK from "+databaseName+"..ASSESSMENT_PROPOSED_TRANS where ac_id='"+acId+"'")) {
			ResultSet n = ps.executeQuery();
			ArrayList<String> caseReview=new ArrayList<String>();
			if (n.next()) {
				caseReview.add(n.getString("ASSESSMENT_PROPOSED_TAG").trim());
				caseReview.add(n.getString("REMARK").trim());
			}
			return caseReview;
			//System.out.println(caseReview);	
			}
	}
	@Test
	public List<String> toverifyAssessmentDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
	//public void toverifyAssessmentDetailsInDatabase() throws SQLException, ClassNotFoundException, EncryptedDocumentException, IOException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		CommonDatabase allotAttendCase=new CommonDatabase();
		allotAttendCase.initializeProperties();
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = con.prepareStatement(
						"Select ASSESSMENT_UNIT,ASSESSMENT_AMOUNT,ASSESSMENT_DATE,REMARK,EVIDENCE_NAME from "+databaseName+"..ASSESSMENT_TRANS where ac_id='"+acId+"'\r\n"
						+ "")) {
			ResultSet n = ps.executeQuery();
			ArrayList<String> assessmentDetails=new ArrayList<String>();
			if (n.next()) {
				assessmentDetails.add(n.getString("ASSESSMENT_UNIT").trim());
				assessmentDetails.add(n.getString("ASSESSMENT_AMOUNT").trim());
				assessmentDetails.add(dateFormat(n.getString("ASSESSMENT_DATE")));
				assessmentDetails.add(n.getString("REMARK").trim());
				//assessmentDetails.add(n.getString("EVIDENCE_NAME").trim());
				
			}
			return assessmentDetails;
			//System.out.println(assessmentDetails);	
			}
	}
	public String dateFormat(String dateStr) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = inputFormat.parse(dateStr);
	   String formattedDate = outputFormat.format(date);
	   return formattedDate;
	}
	
	public List<String> splitRecord(String records){
		  String[] recordArray = records.split(",");
		  ArrayList<String> irregulatries=new ArrayList<String>();
		  for(String record: recordArray) {
			  irregulatries.add(record.trim());
		  }
		   return irregulatries;
	}
	

}
