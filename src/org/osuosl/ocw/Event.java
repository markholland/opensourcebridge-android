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
	
    public int id;
	public Date start, end;
	public String description;
	public String title;
	public String url;
	public String location;
	public String brief;
	//public int event_id;
	public int track_id;
	public String track_title;
	public String speakers;
	public String[] speaker_ids;
	
	public Event(){
		id = -1;
		start = null;
		end = null;
		description = null;
		title = null;
		url = null;
		location = null;
		track_id = -1;
		track_title = null;
		speakers = null;
		speaker_ids = null;
	}
	
	public Event(int id, String title, String description, Date start_time,
  			Date end_time, String room_title, int track_id, String track_title, String[] speaker_ids){
		
		this.id = id;
		//this.event_id = event_id;
		this.title = title;
		this.description = description;
		this.start = start_time;
		this.end = end_time;
		this.location = room_title;
		this.track_id = track_id;
		this.track_title = track_title;
		this.speaker_ids = speaker_ids;
	}
	
	
	
	public Event(String title, String description, String start_time,
  			String end_time, String room_title, String track_id, String track_title, String[] speaker_ids){
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-'07:00'");
		
		//this.id = Integer.parseInt(id);
		//this.event_id = Integer.parseInt(event_id);
		this.title = title;
		this.description = description;
		try{
			this.start = formatter.parse(start_time);
			this.end = formatter.parse(end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.location = room_title;
		this.track_id = Integer.parseInt(track_id);
		this.track_title = track_title;
		this.speaker_ids = speaker_ids;
	}
	
	public void EventFromDatabase(String title, String description, String start_time,
  			String end_time, String room_title, String track_id, String track_title, String[] speaker_ids){
		
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
		
		//this.id = Integer.parseInt(id);
		//this.event_id = Integer.parseInt(event_id);
		this.title = title;
		this.description = description;
		try{
			this.start = formatter.parse(start_time);
			this.end = formatter.parse(end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.location = room_title;
		this.track_id = Integer.parseInt(track_id);
		this.track_title = track_title;
		this.speaker_ids = speaker_ids;
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
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

//	public int getEventId() {
//		return event_id;
//	}
//
//	public void setEventId(int EventId) {
//		this.event_id = EventId;
//	}

	public int getTrackId() {
		return track_id;
	}

	public void setTrackId(int trackId) {
		this.track_id = trackId;
	}

	public String getTrackTitle() {
		return track_title;
	}

	public void setTrackTitle(String track_title) {
		this.track_title = track_title;
	}
	
	public String[] getSpeaker_ids() {
		return speaker_ids;
	}

	public void setSpeaker_ids(String[] speaker_ids) {
		this.speaker_ids = speaker_ids;
	}

	
}
