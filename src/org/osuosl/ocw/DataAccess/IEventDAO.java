package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Event;


public interface IEventDAO {

	public Long addEvents(ArrayList<Event> mEvents);
	public int updateEvents(ArrayList<Event> events);
	public EventDTO getEventRow(String id);
	public ArrayList<EventDTO> getAllEvents();
	public long numRows();
	public void deleteAllRows();
	
}
