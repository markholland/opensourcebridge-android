package org.osuosl.ocw;

import java.util.ArrayList;

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


	ArrayList<Event> events;
	ArrayList<Speaker> speakers;
	ArrayList<Track> tracks;


	private Controller(Context c)  {

		dal = DAL.getSingletonDAL();
		con = c.getApplicationContext();

		events = new ArrayList<Event>();
		speakers = new ArrayList<Speaker>();
		tracks = new ArrayList<Track>();

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

			tracks.add(new Track(dtt.getId()
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

			speakers.add(new Speaker(dts.getId()
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

			events.add(new Event(ets.getId()
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
				
				if(events.get(j).getTrack().getId() == tracks.get(i).getId()) {
					track_events.add(events.get(j));
				}
			}			
			tracks.get(i).setEvents(track_events);
		}


		//set event presenters








		//set speaker presenter events







		//set event speakers






		//set speaker events










	}



}
