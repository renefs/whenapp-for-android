package com.renefernandez.whenapp.model;

import java.io.Serializable;
import java.util.Date;

import com.turbomanage.storm.api.Entity;

/**
 * Entidad Moment. Se almacena en la base de datos y utiliza anotaciones del ORM
 * Storm-Gen.
 * 
 * @author rene
 * 
 */
@Entity
public class Moment implements Serializable {

	/**
	 * 
	 */
	private transient static final long serialVersionUID = -4965065697188384250L;

	private long id;

	private String title;
	private Date date;
	private Double latitude;
	private Double longitude;

	private String imagePath;
	private String videoPath;

	public Moment() {

	}

	public Moment(String title) {
		super();
		this.title = title;
	}

	public Moment withDate(Date date) {
		this.date = date;
		return this;
	}

	public Moment withLocation(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		return this;
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

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
