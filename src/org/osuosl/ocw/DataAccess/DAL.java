package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Event;
import org.osuosl.ocw.BusinessLogic.Speaker;
import org.osuosl.ocw.BusinessLogic.Track;

import android.content.Context;

public class DAL {
	
	private static DAL dal = null;
	private static Context context = null;

    // Singleton pattern
    public static DAL getSingletonDAL(Context ctx) {
        if(dal==null) 
            dal = new DAL(); 
        context = ctx;
        return dal;
    }

    private DAL()
    {
        // empty and private constructor
    }

	public  Long addEvents(ArrayList<EventDTO> events){
		return (new EventDAOImp(context)).addEvents(events);
  	}
	
	public  int updateEvents(ArrayList<EventDTO> events){
		return (new EventDAOImp(context)).updateEvents(events);
  	}
	
	public  Long addSpeakers(ArrayList<SpeakerDTO> speakersList){
		return (new SpeakerDAOImp(context)).addSpeakers(speakersList);
  	}
	
	public  int updateSpeakers(ArrayList<SpeakerDTO> speakersList){
		return (new SpeakerDAOImp(context)).updateSpeakers(speakersList);
  	}

  	public  Long addTracks(ArrayList<TrackDTO> tracks){
  		return (new TrackDAOImp(context)).addTracks(tracks);
  	}
  	
  	public  int updateTracks(ArrayList<TrackDTO> tracks){
  		return (new TrackDAOImp(context)).updateTracks(tracks);
  	}
  	
  	public  EventDTO getEvent(String event_id){
  		return (new EventDAOImp(context)).getEventRow(event_id);
  	}
  	
  	public ArrayList<EventDTO> getAllEvents() {
  		return (new EventDAOImp(context)).getAllEvents();
  	}
  	
  	public  SpeakerDTO getSpeaker(String speaker_id){
  		return (new SpeakerDAOImp(context)).getSpeakerRow(speaker_id);
  	}
  	
  	public ArrayList<SpeakerDTO> getAllSpeakers() {
  		return (new SpeakerDAOImp(context)).getAllSpeakers();
  	}
  		
  	public  TrackDTO getTrack(String track_id){
  		return (new TrackDAOImp(context)).getTrackRow(track_id);
  	}
  	
  	public ArrayList<TrackDTO> getAllTracks() {
  		return (new TrackDAOImp(context)).getAllTracks();
  	}
  	
  	public  int eventExists(String event_id){
  		return (new EventDAOImp(context)).existsEvent(event_id);
  	}
  	
  	public  int speakerExists(String speaker_id){
  		return (new SpeakerDAOImp(context)).existsSpeaker(speaker_id);
  	}
  	
  	public  int trackExists(String track_id){
  		return (new TrackDAOImp(context)).existsTrack(track_id);
  	}
  	
  	public ArrayList<Integer> getSpeaksAt(int event_id) { 
  		return (new SpeaksAtDAOImp(context).getAllSpeakers(event_id));
  	}
  	
  	public Long addSpeaksAt(SpeaksAtDTO saDTO) {
  		return (new SpeaksAtDAOImp(context).addSpeaksAt(saDTO));
  	}
  	
  	public int updateSpeaksAt(SpeaksAtDTO saDTO) { 
  		return (new SpeaksAtDAOImp(context).updateSpeaksAt(saDTO));
  	}
  	
  	public void deleteAll(Context context) {
  		new EventDAOImp(context).deleteAllRows();
  		new SpeakerDAOImp(context).deleteAllRows();
  		new TrackDAOImp(context).deleteAllRows();
  		new StatusDAOImp(context).deleteAllRows();
  		new SpeaksAtDAOImp(context).deleteAllRows();
  		
	}
  	
}
