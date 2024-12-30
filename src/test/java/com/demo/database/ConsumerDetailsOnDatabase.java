package com.demo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.testng.annotations.Test;

public class ConsumerDetailsOnDatabase {
	
	public static String mmyyyy="112024";
	public static String userIdAT="AT@DIV211211";
	public static String databaseName="DVVNL_VIGILANCE_TEST";
	public static String consumerMasterDbName="Dvvnl_mri_data_test";
	public static String countOFpendingCase=null;
	public static String acId="9350067268";


	@Test
	public String toverifyCountAllotPendingCaseInDatabase() throws SQLException, ClassNotFoundException {
		//public void toverifyCountAllotPendingCaseInDatabase() throws SQLException, ClassNotFoundException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Create Connection
		String url="jdbc:sqlserver://45.114.143.206;"+";encrypt=true;trustServerCertificate=true";
		String user="mantoo";

		String password="mantoo@123#";
		Connection con = DriverManager.getConnection(url,user,password);
		PreparedStatement ps = con.prepareStatement("SELECT COUNT(DISTINCT mt.Case_No) AS Pending_Case_Count\r\n"
				+ "FROM \r\n"
				+ "    "+databaseName+"..MARKING_TRANS mt\r\n"
				+ "LEFT JOIN \r\n"
				+ "    "+databaseName+"..Priority_Trans pt ON mt.Case_No = pt.Case_No\r\n"
				+ "LEFT JOIN \r\n"
				+ "    "+databaseName+"..Marking_Validation_Master mvm ON pt.Priority_Code = mvm.Priority_Code\r\n"
				+ "LEFT JOIN \r\n"
				+ "    "+consumerMasterDbName+"..CONSUMER_MASTER_"+mmyyyy+" CM ON CM.AC_ID=MT.AC_ID\r\n"
				+ "LEFT JOIN  \r\n"
				+ "     DVVNL_SAIADM_TEST..PVVNL_LOGIN PL ON PL.DIV_CD=CM.DIV\r\n"
				+ "WHERE mvm.Is_Enable = 'Y' AND CM.CONSUMER_STATUS IN('IN SERVICE','B') AND OPR_ID='"+userIdAT+"'\r\n"
				+ " AND mt.Case_No NOT IN (SELECT DISTINCT fvt.Case_No FROM DVVNL_VIGILANCE_TEST..FIELD_VIGILANCE_TRANS FVT)");


		ResultSet n = ps.executeQuery();
		// System.out.println(ps);

		//DecimalFormat f = new DecimalFormat("#.##");
		while(n.next())
		{ 
			countOFpendingCase = n.getString("Pending_Case_Count").trim();
			this. countOFpendingCase= countOFpendingCase;

		}

	   return countOFpendingCase;
	//System.out.println(countOFpendingCase);
	}
}
