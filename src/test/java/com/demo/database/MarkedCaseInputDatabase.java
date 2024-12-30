package com.demo.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.demo.pom.CommonDataPage;
import com.utility.PropertyFile;

public class MarkedCaseInputDatabase {
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
    public MarkedCaseInputDatabase() {
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
	//public List<String> countOfMarkedCaseInputInDatabase() throws SQLException, ClassNotFoundException {
		public void countOfMarkedCaseInputInDatabase() throws SQLException, ClassNotFoundException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Create Connection
		String url="jdbc:sqlserver://45.114.143.206;"+";encrypt=true;trustServerCertificate=true";
		String user="mantoo";
      
		String password="mantoo@123#";
		Connection con = DriverManager.getConnection(url,user,password);
		PreparedStatement ps = con.prepareStatement("SELECT  \r\n"
				+ "    PL.ZPC_NAME,\r\n"
				+ "    PL.Circle,\r\n"
				+ "    PL.DIV_NAME,\r\n"
				+ "	row_number() over(partition by  PL.Circle order by PL.Circle) as circle_wise_count,\r\n"
				+ "	count(distinct mt.CASE_NO)total_count_per_circle\r\n"
				+ "FROM\r\n"
				+ "   "+databaseName+"..MARKING_TRANS MT\r\n"
				+ "LEFT JOIN\r\n"
				+ "    "+databaseName+"..PRIORITY_TRANS PT \r\n"
				+ "	ON MT.Case_No = PT.Case_No\r\n"
				+ "LEFT JOIN\r\n"
				+ "    "+databaseName+"..MARKING_VALIDATION_MASTER MVM \r\n"
				+ "   ON PT.Priority_Code = MVM.Priority_Code\r\n"
				+ " LEFT JOIN\r\n"
				+ "    "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM \r\n"
				+ "	ON MT.Ac_Id = CM.Ac_Id\r\n"
				+ " LEFT JOIN\r\n"
				+ "    DVVNL_SAIADM_TEST..Pvvnl_Login PL \r\n"
				+ "	ON CM.Div = PL.Div_CD\r\n"
				+ "WHERE\r\n"
				+ "    MVM.Is_Enable = 'Y' AND RMK_DATE BETWEEN '20-11-2024' AND '20-12-2024' and  PL.DIV_NAME='"+divName+"'  and pl.OPR_ID='"+userNameEe+"'\r\n"
				+ "GROUP BY\r\n"
				+ "   PL.ZPC_NAME, PL.Circle, PL.DIV_NAME\r\n"
				+ "Having count(distinct mt.CASE_NO)>1\r\n"
				+ "ORDER BY total_count_per_circle DESC");


		ResultSet n = ps.executeQuery();
		// System.out.println(ps);
		
		ArrayList<String> markedCaseInputCountDetails=new ArrayList<String>();

		//DecimalFormat f = new DecimalFormat("#.##");
		while(n.next())
		{ 
			markedCaseInputCountDetails.add(n.getString("ZPC_NAME").trim());
			markedCaseInputCountDetails.add(n.getString("Circle").trim());
			markedCaseInputCountDetails.add(n.getString("DIV_NAME").trim());
			markedCaseInputCountDetails.add(n.getString("total_count_per_circle").trim());

		}

		//return markedCaseInputCountDetails;
		System.out.println(markedCaseInputCountDetails);
	}
	
	@Test
	public List<String> markedInputcaseDetailsInDatabase() throws SQLException, ClassNotFoundException {
		//public void countOfMarkedCaseInputInDatabase() throws SQLException, ClassNotFoundException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Create Connection
		String url="jdbc:sqlserver://45.114.143.206;"+";encrypt=true;trustServerCertificate=true";
		String user="mantoo";

		String password="mantoo@123#";
		Connection con = DriverManager.getConnection(url,user,password);
		PreparedStatement ps = con.prepareStatement("SELECT  \r\n"
				+ "    PL.ZPC_NAME,\r\n"
				+ "    PL.Circle,\r\n"
				+ "    PL.DIV_NAME,\r\n"
				+ "	row_number() over(partition by  PL.Circle order by PL.Circle) as circle_wise_count,\r\n"
				+ "	count(distinct mt.CASE_NO)total_count_per_circle\r\n"
				+ "FROM\r\n"
				+ "   "+databaseName+"..MARKING_TRANS MT\r\n"
				+ "LEFT JOIN\r\n"
				+ "    "+databaseName+"..PRIORITY_TRANS PT \r\n"
				+ "	ON MT.Case_No = PT.Case_No\r\n"
				+ "LEFT JOIN\r\n"
				+ "    "+databaseName+"..MARKING_VALIDATION_MASTER MVM \r\n"
				+ "   ON PT.Priority_Code = MVM.Priority_Code\r\n"
				+ " LEFT JOIN\r\n"
				+ "    "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM \r\n"
				+ "	ON MT.Ac_Id = CM.Ac_Id\r\n"
				+ " LEFT JOIN\r\n"
				+ "    DVVNL_SAIADM_TEST..Pvvnl_Login PL \r\n"
				+ "	ON CM.Div = PL.Div_CD\r\n"
				+ "WHERE\r\n"
				+ "    MVM.Is_Enable = 'Y' AND RMK_DATE BETWEEN '20-11-2024' AND '20-12-2024' and PL.DIV_NAME='"+divName+"'  and pl.OPR_ID='"+userNameEe+"'\r\n"
				+ "GROUP BY\r\n"
				+ "   PL.ZPC_NAME, PL.Circle, PL.DIV_NAME\r\n"
				+ "Having count(distinct mt.CASE_NO)>1\r\n"
				+ "ORDER BY total_count_per_circle DESC");


		ResultSet n = ps.executeQuery();
		//System.out.println(ps);
		
		ArrayList<String> markedCaseInputCountDetails=new ArrayList<String>();

		//DecimalFormat f = new DecimalFormat("#.##");
		while(n.next())
		{ 
			markedCaseInputCountDetails.add(n.getString("ZPC_NAME").trim());
			markedCaseInputCountDetails.add(n.getString("Circle").trim());
			markedCaseInputCountDetails.add(n.getString("DIV_NAME").trim());
			markedCaseInputCountDetails.add(n.getString("total_count_per_circle").trim());

		}

		return markedCaseInputCountDetails;
		//System.out.println(markedCaseInputCountDetails);
	}
	
	
}

