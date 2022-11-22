package com.example.a310finalproj;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class fieldVerificationUtil {

    // empty -> false
    // invalid email format -> false
    // is not university domain ".edu" -> false
    // valid email format with ".edu" -> true
    public boolean isValidEmail(String entry){
        if(entry.isEmpty()){
            return false;
        }
        return entry.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.+-]+\\.edu$");

    }

    // empty -> false
    // not number -> false
    // negative -> false
    // positive -> true
    public boolean isValidIntEntry(String entry){
        int test;
        if(entry.isEmpty()){
            return false;
        }
        try{
            test = Integer.parseInt(entry);
        }
        catch(Exception e){
            return false;
        }
        if(test < 0){
            return false;
        }
        return true;

    }

    // empty -> false
    // not number -> false
    // negative -> false
    // positive -> true
    public boolean isValidDoubleEntry(String entry){
        double test;
        if(entry.isEmpty()){
            return false;
        }
        try{
            test = Double.parseDouble(entry);
        }
        catch(Exception e){
            return false;
        }
        if(test < 0){
            return false;
        }
        return true;
    }

    // empty -> false
    // in the past -> false
    // invalid format -> false
    // valid date more than a year in the future -> false
    // valid date in future -> true
    public boolean isValidDeadline(String entry){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/yyyy", Locale.ENGLISH);
        Date deadline;
        try{
            deadline = formatter.parse(entry);
        }
        catch(Exception e){
            return false;
        }
        if(deadline == null) {
            return false;
        }
        if (deadline.before(new Date())) {
            return false;
        }

        Date future = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(future);
        cal.add(Calendar.YEAR, 1); // Where n is int
        future = cal.getTime();
        if(deadline.after(future)){
            return false;
        }
        return true;
    }

    // empty -> false
    // no match to address form -> false
    // matches address form -> true
    public boolean isValidAddress(String address){
        if(address.isEmpty()){
            return false;
        }
        return address.matches(
                "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
    }


}
