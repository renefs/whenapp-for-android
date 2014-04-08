package com.renefernandez.whenapp.model;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

public class Moment {

	private long id;
	
	private String title;
	private Date date;
	private Double latitude;
	private Double longitude;
	
	public Moment(){
		
	}

	public Moment(String title, Date date, Double latitude,
			Double longitude) {
		super();
		this.title = title;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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


	
	
	
}
