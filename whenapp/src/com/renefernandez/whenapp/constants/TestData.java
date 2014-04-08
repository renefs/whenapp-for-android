package com.renefernandez.whenapp.constants;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
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
    			
    	new Moment("Moment 1 has a very large text", new Date(), 43.35560534, -5.850938559),
    	new Moment("Moment 2", new Date(), 43.36560534, -5.850938559),
    	new Moment("Moment 3", new Date(), 43.37560534, -5.850938559)		
    			
    	};   	
    	
    	return moments;
    	
    }
    
}
