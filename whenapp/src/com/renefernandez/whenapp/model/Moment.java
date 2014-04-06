package com.renefernandez.whenapp.model;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

public class Moment {

	private String title;
	private Date date;
	private LatLng coordinates;
	
	
	public Moment(String title, Date date, LatLng coordinates) {
		super();
		this.title = title;
		this.date = date;
		this.coordinates = coordinates;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public LatLng getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
	
	
}
