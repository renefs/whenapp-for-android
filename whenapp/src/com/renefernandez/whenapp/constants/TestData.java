package com.renefernandez.whenapp.constants;

import java.util.Date;

import com.renefernandez.whenapp.model.Moment;

public class TestData {
	 
    /** Array of countries used to display in CountryListFragment */
    public static String name[] = new String[] {
        "India",
        "Pakistan",
        "Sri Lanka",
        "China",
        "Bangladesh",
        "Nepal",
        "Afghanistan",
        "North Korea",
        "South Korea",
        "Japan",
        "Bhutan"
    };    
    
    public static Moment[] getTestMoments(){
    	
    	Moment[] moments = new Moment[] {
    			
    	new Moment("Moment 1 has a very large text").withDate(new Date()).withLocation(43.35560534, -5.850938559),
    	new Moment("Moment 2").withDate(new Date()).withLocation(43.36560534, -5.850938559),
    	new Moment("Moment 3").withDate(new Date()).withLocation(43.37560534, -5.850938559)		
    			
    	};   	
    	
    	return moments;
    	
    }
    
}
