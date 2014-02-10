package DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.Event;

public interface IEventDAO {

	public Long addEvents(ArrayList<Event> mEvents);
	public int updateEvents(ArrayList<Event> events);
	public Event getEventRow(String id);
	public ArrayList<Event> getEvents();
	public long numRows(String table_name);
	public long deleteAllRowsInTable(String table_name);
	
}
