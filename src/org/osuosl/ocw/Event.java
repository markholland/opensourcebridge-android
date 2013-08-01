package org.osuosl.ocw;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;


public class Event {
	// TODO Fetch tracks from OCW.
	private static final int TRACK_BUSINESS = 20;
	private static final int TRACK_CHEMISTRY = 21;
	private static final int TRACK_COOKING = 22;
	private static final int TRACK_CULTURE = 23;
	private static final int TRACK_HACKS = 24;
    private static final int TRACK_BOF = 25;
	
    private int event_id;
    private String event_title;
    private Date start_time;
    private Date end_time;
    private String description;
    private String room_title;
    private int track_id;
    private String[] speaker_ids;
    private String presenter;
	
	public Event(){
		this.event_id = -1;
		this.event_title = null;
		this.start_time = null;
		this.end_time = null;
		this.description = null;
		this.event_title = null;
		this.room_title = null;
		this.track_id = -1;
		this.speaker_ids = null;
		this.presenter = null;
	}
	
	public Event(int event_id, String event_title, Date start_time,
  			Date end_time, String description, String room_title, int track_id, String[] speaker_ids,
  			String presenter){
		
		this.event_id = event_id;
		this.event_title = event_title;
		this.description = description;
		this.start_time = start_time;
		this.end_time = end_time;
		this.room_title = room_title;
		this.track_id = track_id;
		this.speaker_ids = speaker_ids;
		this.presenter = presenter;
	}
	
	
	
	public Event(String event_id, String event_title, String description, String start_time,
  			String end_time, String room_title, String track_id, String[] speaker_ids,
  			String presenter){
		
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
		
		this.event_id = Integer.parseInt(event_id);
		this.event_title = event_title;
		this.description = description;
		try{
			this.start_time = formatter.parse(start_time);
			this.end_time = formatter.parse(end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.room_title = room_title;
		this.track_id = Integer.parseInt(track_id);
		this.speaker_ids = speaker_ids;
		this.presenter = presenter;
	}
	
	/*public String getTrackName() {
		// TODO Fetch tracks from OCW.
		switch(track) {
		case TRACK_BUSINESS:
			return "Business";
		case TRACK_CHEMISTRY:
			return "Chemistry";
		case TRACK_COOKING:
			return "Cooking";
		case TRACK_CULTURE:
			return "Culture";
		case TRACK_HACKS:
			return "Hacks";
        case TRACK_BOF:
            return "BOF";
		default:
			return "";
		}
	}*/
	
	

	/**
	 * @return the resource id for the track color
	 */
	public int getTrackColor() {
		// TODO Fetch tracks from OCW.
		switch(track_id) {
		case TRACK_BUSINESS:
			return R.color.track_business;
		case TRACK_CHEMISTRY:
			return R.color.track_chemistry;
		case TRACK_COOKING:
			return R.color.track_cooking;
		case TRACK_CULTURE:
			return R.color.track_culture;
		case TRACK_HACKS:
			return R.color.track_hacks;
        case TRACK_BOF:
            return R.color.track_bof;
		default:
			return R.color.track_other;
		}
	}
	
	/**
    * @return the resource id for the track color darker shade
    */
    public int getTrackColorDark() {
       // TODO Fetch tracks from OCW.
	   switch(track_id) {
	   case TRACK_BUSINESS:
	           return R.color.track_business_dark;
	   case TRACK_CHEMISTRY:
	           return R.color.track_chemistry_dark;
	   case TRACK_COOKING:
	           return R.color.track_cooking_dark;
	   case TRACK_CULTURE:
	           return R.color.track_culture_dark;
	   case TRACK_HACKS:
	           return R.color.track_hacks_dark;
       case TRACK_BOF:
               return R.color.track_bof_dark;
	   default:
	           return R.color.track_other_dark;
	   }
    }

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
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

	public String[] getSpeaker_ids() {
		return speaker_ids;
	}

	public void setSpeaker_ids(String[] speaker_ids) {
		this.speaker_ids = speaker_ids;
	}

	public String getPresenter() {
		return presenter;
	}

	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}
    


	
}
