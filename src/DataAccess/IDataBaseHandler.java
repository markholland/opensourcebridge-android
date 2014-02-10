package DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.Event;
import org.osuosl.ocw.Speaker;
import org.osuosl.ocw.Track;

public interface IDataBaseHandler {
	public Long addEvents(ArrayList<Event> mEvents);
	public Long addSpeakers(ArrayList<Speaker> speakers);
	public Long addTracks(ArrayList<Track> tracks);
	
	public Long addStatusRow(String name, String value);
	
	public int updateEvents(ArrayList<Event> events);
	public int updateSpeakers(ArrayList<Speaker> speakers);
	public int updateTracks(ArrayList<Track> tracks);
	
	public int updateStatusRow(String name, String value);
	
	public Event getEventRow(String id);
	public Speaker getSpeakersRow(String id);
	public Track getTracksRow(String id);
	public Long getStatusRow(String name);
	
	public ArrayList<Event> getEvents();
	public ArrayList<Speaker> getSpeakers();
	public ArrayList<Track> getTracks();
	
	
	public long numRows(String table_name);
	public long deleteAllRowsInTable(String table_name);
	
	public int existsEvent(String id);
	public int existsSpeaker(String id);
	public int existsTrack(String id);
	public int existsStatusRow(String table);
	
	
}
