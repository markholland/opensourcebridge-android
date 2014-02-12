package org.osuosl.ocw.DataAccess;

public class SpeaksAtDTO {

	private int event_id;
	private int speaker_id;
	
	
	public SpeaksAtDTO(int event_id, int speaker_id) {
		super();
		this.event_id = event_id;
		this.speaker_id = speaker_id;
	}


	public int getEvent_id() {
		return event_id;
	}


	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}


	public int getSpeaker_id() {
		return speaker_id;
	}


	public void setSpeaker_id(int speaker_id) {
		this.speaker_id = speaker_id;
	}
	
	
	
	
}
