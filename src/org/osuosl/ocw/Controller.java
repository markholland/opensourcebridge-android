package org.osuosl.ocw;

import java.util.ArrayList;
import java.util.HashMap;

import org.osuosl.ocw.BusinessLogic.Event;
import org.osuosl.ocw.BusinessLogic.Speaker;
import org.osuosl.ocw.BusinessLogic.Track;
import org.osuosl.ocw.DataAccess.DAL;
import org.osuosl.ocw.DataAccess.EventDTO;
import org.osuosl.ocw.DataAccess.SpeakerDTO;
import org.osuosl.ocw.DataAccess.SpeaksAtDTO;
import org.osuosl.ocw.DataAccess.TrackDTO;

import android.content.Context;

public class Controller {
	private static Controller cont = null;
	private DAL dal = null;
	private Context con;


	HashMap<Integer, Event> events;
	HashMap<Integer, Speaker> speakers;
	HashMap<Integer, Track> tracks;


	private Controller(Context c)  {

		dal = DAL.getSingletonDAL(c);
		con = c.getApplicationContext();

		events = new HashMap<Integer, Event>();
		speakers = new HashMap<Integer, Speaker>();
		tracks = new HashMap<Integer, Track>();

	}

	public static Controller getSingletonController(Context c)  {
		if(cont==null)
			cont = new Controller(c);

		return cont;
	}


	public void loadSchedule() {

		//get all tracks
		ArrayList<TrackDTO> DTOTracks = DAL.getSingletonDAL(con).getAllTracks();

		TrackDTO dtt = null;
		for(int i = 0; i < DTOTracks.size(); i++) {

			dtt= DTOTracks.get(i);

			tracks.put(dtt.getId(), new Track(dtt.getId()
					, dtt.getTitle()
					, dtt.getColor()
					, dtt.getColor_text()
					));

		}


		//get all speakers
		ArrayList<SpeakerDTO> DTOSpeakers = DAL.getSingletonDAL(con).getAllSpeakers();

		SpeakerDTO dts = null;
		for(int i = 0; i < DTOSpeakers.size(); i++) {

			dts = DTOSpeakers.get(i);

			speakers.put(dts.getId(), new Speaker(dts.getId()
					, dts.getFullname()
					, dts.getBiography()
					, dts.getAffiliation()
					, dts.getTwitter()
					, dts.getEmail()
					, dts.getWebsite()
					, dts.getBlog()
					, dts.getLinkedin()
					));

		}

		//get all events
		ArrayList<EventDTO> DTOEvents = DAL.getSingletonDAL(con).getAllEvents();

		EventDTO ets = null;
		for(int i = 0; i < DTOEvents.size(); i++) {

			ets = DTOEvents.get(i);

			events.put(ets.getId(), new Event(ets.getId()
					, ets.getTitle()
					, ets.getStart_time()
					, ets.getEnd_time()
					, ets.getDescription()
					, ets.getRoom_title()
					, tracks.get(ets.getId())
					));
		}

		//set track events

		ArrayList<Event> track_events = null;
		for(int i = 0; i < tracks.size(); i++) {

			track_events = new ArrayList<Event>();
			for(int j = 0; j < events.size(); j++) {

				if(events.containsKey(tracks.get(i).getId())) {
					track_events.add(events.get(j));
				}
			}			
			tracks.get(i).setEvents(track_events);
		}


		//set event presenters
		int presenter_id = -1;
		for(int i = 0; i < events.size(); i++) {

			presenter_id = DTOEvents.get(i).getPresenter_id();

			events.get(i).setPresenter(speakers.get(presenter_id));

		}

		//set speaker presenter events

		ArrayList<Event> isPresenter;
		for(int i = 0; i < speakers.size(); i++) {

			isPresenter = new ArrayList<Event>();
			for(int j = 0; j < events.size(); j++) {

				if(events.get(j).isPresenter(speakers.get(i))) {
					isPresenter.add(events.get(j));
				}

			}
			speakers.get(i).setIs_presenter(isPresenter);

		}

		//set event speakers

		ArrayList<Speaker> eventSpeakers;
		ArrayList<Integer> speakerIds;
		int sId = -1;
		for(int i = 0; i < events.size(); i++) {

			eventSpeakers = new ArrayList<Speaker>();
			speakerIds = DAL.getSingletonDAL(con).getSpeaksAt(events.get(i).getId());

			for(int j = 0; j < speakerIds.size(); j++) {

				sId = speakerIds.get(j);
				for(int k = 0; k < speakers.size(); k++) {

					if(speakers.containsKey(sId)) {
						eventSpeakers.add(speakers.get(k));
					}
				}
			}
			events.get(i).setSpeakers(eventSpeakers);

		}

		//set speaker events

		ArrayList<Event> speakerEvents;
		for(int i = 0; i < speakers.size(); i++) {

			speakerEvents = new ArrayList<Event>();
			sId = speakers.get(i).getId();
			for(int j = 0; j < events.size(); j++) {

				if(events.get(i).hasSpeaker(speakers.get(i))) {

					speakerEvents.add(events.get(j));
				}
			}

		}

	}


	// methods with dal

	public void saveEvents() {

		ArrayList<EventDTO> eventsDTO = new ArrayList<EventDTO>();
		EventDTO eDTO = null;
		SpeaksAtDTO saDTO = null;
		
		// Add all events
		for (Event e : events.values()) {

			eDTO = new EventDTO(e.getId()
					, e.getTitle()
					, e.getStart_time().toString()
					, e.getEnd_time().toString()
					, e.getDescription()
					, e.getRoom_title()
					, e.getTrack().getId()
					, e.getPresenter().getId()
					);
			
			eventsDTO.add(eDTO);

			// Add all speaks at relations for each event
			for(int i = 0; i < e.getSpeakers().size(); i++){

				saDTO = new SpeaksAtDTO(e.getId(), e.getSpeakers().get(i).getId());
				dal.addSpeaksAt(saDTO);
			
			}
		}
		dal.addEvents(eventsDTO);
	}
	
	
	public void updateSavedEvents() {

		ArrayList<EventDTO> eventsDTO = new ArrayList<EventDTO>();
		EventDTO eDTO = null;
		SpeaksAtDTO saDTO = null;
		
		// Add all events
		for (Event e : events.values()) {

			eDTO = new EventDTO(e.getId()
					, e.getTitle()
					, e.getStart_time().toString()
					, e.getEnd_time().toString()
					, e.getDescription()
					, e.getRoom_title()
					, e.getTrack().getId()
					, e.getPresenter().getId()
					);
			
			eventsDTO.add(eDTO);

			// Add all speaks at relations for each event
			for(int i = 0; i < e.getSpeakers().size(); i++){

				saDTO = new SpeaksAtDTO(e.getId(), e.getSpeakers().get(i).getId());
				dal.addSpeaksAt(saDTO);
			
			}
		}
		dal.updateEvents(eventsDTO);
	}
	
	
	public void saveSpeakers() {
		
		ArrayList<SpeakerDTO> speakersDTO = new ArrayList<SpeakerDTO>();
		SpeakerDTO sDTO = null;
		
		for (Speaker s : speakers.values()) {
			
			sDTO = new SpeakerDTO(s.getId()
					, s.getFullname()
					, s.getBiography()
					, s.getAffiliation()
					, s.getTwitter()
					, s.getEmail()
					, s.getWebsite()
					, s.getBlog()
					, s.getLinkedin()
					);
			
			speakersDTO.add(sDTO);
		}
		dal.addSpeakers(speakersDTO);
		
	}
	
	
	public void saveTracks() {
		
		ArrayList<TrackDTO> tracksDTO = new ArrayList<TrackDTO>();
		TrackDTO tDTO = null;
		
		for (Track t : tracks.values()) {
			
			tDTO = new TrackDTO(t.getId()
					, t.getTitle()
					, t.getColor()
					, t.getColor_text()
					);
			
			tracksDTO.add(tDTO);
		}
		dal.addTracks(tracksDTO);
	}
	
	public void deleteAllData() {
		events = new HashMap<Integer, Event>();
		speakers = new HashMap<Integer, Speaker>();
		tracks = new HashMap<Integer, Track>();
		
		dal.deleteAll(con);
	
	}
	

	public HashMap<Integer, Event> getEvents() {
		return events;
	}

	public void addEvent(Event e) {
		this.events.put(e.getId(), e);
	}
	
	public Event getEvent(int key) {
		return events.get(key);
	}
	
	public ArrayList<Speaker> getSpeakers() {
		return new ArrayList<Speaker>(speakers.values());
	}
	
	public void addSpeaker(Speaker sp) { 
		this.speakers.put(sp.getId(), sp);
	}
	
	public Speaker getSpeaker(int key) {
		return speakers.get(key);
	}

	public HashMap<Integer, Track> getTracks() {
		return tracks;
	}

	public void addTrack(Track tr) {
		this.tracks.put(tr.getId(), tr);
	}
	
	public Track getTrack(int key) {
		return tracks.get(key);
	}



}
