package org.osuosl.ocw;

import java.util.ArrayList;
import java.util.HashMap;

import org.osuosl.ocw.BusinessLogic.Event;
import org.osuosl.ocw.BusinessLogic.Speaker;
import org.osuosl.ocw.BusinessLogic.Track;
import org.osuosl.ocw.DataAccess.DAL;
import org.osuosl.ocw.DataAccess.EventDTO;
import org.osuosl.ocw.DataAccess.SpeakerDTO;
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

		dal = DAL.getSingletonDAL();
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


	public void initSchedule() {

		//get all tracks
		ArrayList<TrackDTO> DTOTracks = DAL.getSingletonDAL().getAllTracks(con);

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
		ArrayList<SpeakerDTO> DTOSpeakers = DAL.getSingletonDAL().getAllSpeakers(con);

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
		ArrayList<EventDTO> DTOEvents = DAL.getSingletonDAL().getAllEvents(con);

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
			speakerIds = DAL.getSingletonDAL().getSpeaksAt(con, events.get(i).getId());

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
	
	public ArrayList<Speaker> getSpeakers() {
		return new ArrayList<Speaker>(speakers.values());
	}



}
