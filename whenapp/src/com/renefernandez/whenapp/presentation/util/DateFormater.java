package com.renefernandez.whenapp.presentation.util;

public class DateFormater {

	public static String getDateFormated(int year, int monthOfYear, int dayOfMonth) {
		String outputYear = String.valueOf(year);
		String outputMonth = String.valueOf(monthOfYear);
		String outputDay = String.valueOf(dayOfMonth);

		if (year < 999)
			outputYear = "0" + outputYear;
		if (monthOfYear < 10)
			outputMonth = "0" + outputMonth;
		if (dayOfMonth < 10)
			outputDay = "0" + outputDay;

		return(outputDay + "/" + outputMonth + "/" + outputYear);
	}
	
	public static String getTimeFormated(int hourOfDay, int minute) {
		String outputHour = String.valueOf(hourOfDay);
		String outputMinute = String.valueOf(minute);

		if (hourOfDay < 10)
			outputHour = "0" + outputHour;
		if (minute < 10)
			outputMinute = "0" + outputMinute;

		return(outputHour + ":" + outputMinute);
	}

}
