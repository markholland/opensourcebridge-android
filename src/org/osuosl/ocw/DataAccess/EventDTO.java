package org.osuosl.ocw.DataAccess;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDTO {
	
	private int id;
    private String title;
    private Date start_time;
    private Date end_time;
    private String description;
    private String room_title;
    private int track_id;
    private int presenter_id;
    
    
	public EventDTO(int id, String title, String start_time, String end_time,
			String description, String room_title, int track_id,
			int presenter_id) {
		super();
		this.id = id;
		this.title = title;
		
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
		
		try{
			this.start_time = formatter.parse(start_time);
			this.end_time = formatter.parse(end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.room_title = room_title;
		this.track_id = track_id;
		this.presenter_id = presenter_id;
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


	public Date getStart_time() {
		return start_time;
	}


	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}


	public Date getEnd_time() {
		return end_time;
	}


	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRoom_title() {
		return room_title;
	}


	public void setRoom_title(String room_title) {
		this.room_title = room_title;
	}


	public int getTrack_id() {
		return track_id;
	}


	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}


	public int getPresenter_id() {
		return presenter_id;
	}


	public void setPresenter_id(int presenter_id) {
		this.presenter_id = presenter_id;
	}
    
	
	
    
	

}
