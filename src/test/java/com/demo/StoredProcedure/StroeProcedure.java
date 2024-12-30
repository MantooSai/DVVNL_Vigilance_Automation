package com.demo.StoredProcedure;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.utility.PropertyFile;
import com.vigiEye.pom.CommonDataPage;

public class StroeProcedure {
	 private PropertyFile pf; 
	    CommonDataPage cdp;
	    WebDriver driver;
	    public static String userIdAT ;
	    public static String acId=null;
		public static String caseNo=null;
		
	
	 public Map<String, String> assessmentAttendCase(String zoneName, String divisionName, String acId) {
	        // Create a Map to hold zone and division details
	        Map<String, String> caseDetails = new HashMap<>();
	        
	        // Add zone and division names to the map
	        caseDetails.put("zone", zoneName);
	        caseDetails.put("division", divisionName);
	        caseDetails.put("acId", divisionName);
	        
	        // Return the map
	        return caseDetails;
}
}