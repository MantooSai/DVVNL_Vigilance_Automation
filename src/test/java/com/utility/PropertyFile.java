package com.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
	public Properties properties;
	public void ConfigReaderMainMeter() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/IrregularitiesMainMeter.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void ConfigReaderPoleMeter() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/IrregularitiesPoleMeter.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void AssessmentProposedRemark() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/AssessementApprovalDetails/AssessmentApprovedDetails.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void AssessmentFeedingDetails() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/AssessmentFeeding/AssessmentFeedingDetails.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void AssessmentRealisationDetails() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/AssessmentRealisation/AssessmentRealisationDetails.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void baseClassDetails() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/BaseClass/BaseClassDemo.property");
			//FileInputStream fileInput = new FileInputStream("./TestData/BaseClass/BaseClassLive.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void databaseCredentialsDetails() {

		try {
                              //Demo DataBase Credentials
			FileInputStream fileInput = new FileInputStream("./TestData/DatabaseCredentials/DatabaseDemo.property");
			                //Live DataBase Credentials
			//	FileInputStream fileInput = new FileInputStream("./TestData/DatabaseCredentials/DatabaseLive.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void realisationInputDetails() {

		try {

			FileInputStream fileInput = new FileInputStream("./TestData/RealisationInputDetails/RealisationInput.property");

			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getProperty(String key) {
	    return properties.getProperty(key);
	}
}
