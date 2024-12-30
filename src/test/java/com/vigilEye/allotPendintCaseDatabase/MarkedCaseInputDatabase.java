package com.vigilEye.allotPendintCaseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class MarkedCaseInputDatabase {
	public static String mmyyyy="112024";
	public static String userId="Test@sai";
	public static String databaseName="DVVNL_VIGILANCE_TEST";
	public static String consumerMasterDbName="Dvvnl_mri_data_test";
	public static String zoneName="aligarh_zone";
	public static String circleName="EDC-I ALIGARH";
	public static String divisionName="EDD-I ALIGARH";
	public static String rmkFromDate="2024-08-09";
	public static String rmkUptoDate="2024-11-06";

	@Test
	public List<String> countOfMarkedCaseInputInDatabase() throws SQLException, ClassNotFoundException {
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
				+ "    MVM.Is_Enable = 'Y' AND RMK_DATE BETWEEN '"+rmkFromDate+"' AND '"+rmkUptoDate+"' and PL.ZPC_NAME='"+zoneName+"'and PL.DIV_NAME='"+divisionName+"' and PL.CIRCLE='"+circleName+"' and pl.OPR_ID='"+userId+"'\r\n"
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

		return markedCaseInputCountDetails;
		//System.out.println(markedCaseInputCountDetails);
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
				+ "    MVM.Is_Enable = 'Y' AND RMK_DATE BETWEEN '"+rmkFromDate+"' AND '"+rmkUptoDate+"' and PL.ZPC_NAME='"+zoneName+"'and PL.DIV_NAME='"+divisionName+"' and PL.CIRCLE='"+circleName+"' and pl.OPR_ID='"+userId+"'\r\n"
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

