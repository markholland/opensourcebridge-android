package org.osuosl.ocw.DataAccess;

import java.util.Date;

public class TrackDTO {
	 	
	private int id;
	private String title;
	private int color;
	private int color_text;
	
	
	public TrackDTO(int id, String title, int color, int color_text) {
		super();
		this.id = id;
		this.title = title;
		this.color = color;
		this.color_text = color_text;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getColor() {
		return color;
	}


	public void setColor(int color) {
		this.color = color;
	}


	public int getColor_text() {
		return color_text;
	}


	public void setColor_text(int color_text) {
		this.color_text = color_text;
	}
	
	
	
	
}
