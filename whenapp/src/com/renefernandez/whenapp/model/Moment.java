package com.renefernandez.whenapp.model;

import java.io.Serializable;
import java.util.Date;

import com.turbomanage.storm.api.Entity;

@Entity
public class Moment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4965065697188384250L;

	private long id;

	private String title;
	private Date date;
	private Double latitude;
	private Double longitude;
	
	private byte[] image;
	private byte[] thumbnail;

	public Moment() {

	}

	public Moment(String title, Date date, Double latitude, Double longitude) {
		super();
		this.title = title;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

}
