package org.osuosl.ocw;

public class Track {

	private int track_id;
	private String track_title;
	private String color;
	private String color_text;
	
	
	public Track() {
		
		this.track_id = -1;
		this.track_title = null;
		this.color = null;
		this.color_text = null;
	}
	
	public Track(int track_id, String track_title, String color, String color_text) {
		
		this.track_id = track_id;
		this.track_title = track_title;
		this.color = color;
		this.color_text = color_text;
	}
	
	public Track(String track_id, String track_title, String color, String color_text) {
		
		this.track_id = Integer.parseInt(track_id);
		this.track_title = track_title;
		this.color = color;
		this.color_text = color_text;
	}

	public int getTrack_id() {
		return track_id;
	}

	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}

	public String getTrack_title() {
		return track_title;
	}

	public void setTrack_title(String track_title) {
		this.track_title = track_title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor_text() {
		return color_text;
	}

	public void setColor_text(String color_text) {
		this.color_text = color_text;
	}
	
	
}
