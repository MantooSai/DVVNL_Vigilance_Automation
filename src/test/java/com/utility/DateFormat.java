package com.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat {

    public String date_dd_mm_yyyy_Format(String inputDate) {
        // Define the input date format (with time and milliseconds)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        
        // Define the output date format (only date: dd-MM-yyyy)
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Parse the input date string into a LocalDate object
        LocalDate rmkDate = LocalDate.parse(inputDate, inputFormatter);
        
        // Format the date to the desired format
        String formattedRmkDate = rmkDate.format(outputFormatter);
        
        return formattedRmkDate; 
    }
}
