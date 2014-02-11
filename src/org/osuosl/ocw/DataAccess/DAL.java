package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Event;
import org.osuosl.ocw.BusinessLogic.Speaker;
import org.osuosl.ocw.BusinessLogic.Track;

import android.content.Context;

public class DAL {
	
	private static DAL dal = null;

    // Singleton pattern
    public static DAL getSingletonDAL() {
        if(dal==null)
            dal = new DAL(); return dal;
    }

    private DAL()
    {
        // empty and private constructor
    }

	public  Long addEvents(ArrayList<Event> events, Context context){
		return (new EventDAOImp(context)).addEvents(events);
  	}
	
	public  Long addSpeakers(ArrayList<Speaker> speakersList, Context context){
		return (new SpeakerDAOImp(context)).addSpeakers(speakersList);
  	}

  	public  Long addTracks(ArrayList<Track> tracks, Context context){
  		return (new TrackDAOImp(context)).addTracks(tracks);
  	}
  	
  	public  EventDTO getSchedule(String event_id, Context context){
  		return (new EventDAOImp(context)).getEventRow(event_id);
  	}
  	
  	public ArrayList<EventDTO> getAllEvents(Context context) {
  		return (new EventDAOImp(context)).getAllEvents();
  	}
  	
  	public  SpeakerDTO getSpeaker(String speaker_id, Context context){
  		return (new SpeakerDAOImp(context)).getSpeakerRow(speaker_id);
  	}
  	
  	public ArrayList<SpeakerDTO> getAllSpeakers(Context context) {
  		return (new SpeakerDAOImp(context)).getAllSpeakers();
  	}
  		
  	public  TrackDTO getTrack(String track_id, Context context){
  		return (new TrackDAOImp(context)).getTrackRow(track_id);
  	}
  	
  	public ArrayList<TrackDTO> getAllTracks(Context context) {
  		return (new TrackDAOImp(context)).getAllTracks();
  	}
  	
  	public  int eventExists(String event_id, Context context){
  		return (new EventDAOImp(context)).existsEvent(event_id);
  	}
  	
  	public  int speakerExists(String speaker_id, Context context){
  		return (new SpeakerDAOImp(context)).existsSpeaker(speaker_id);
  	}
  	
  	public  int trackExists(String track_id, Context context){
  		return (new TrackDAOImp(context)).existsTrack(track_id);
  	}
}
