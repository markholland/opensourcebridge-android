package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Speaker;


public interface ISpeakerDAO {

	public Long addSpeakers(ArrayList<Speaker> mSpeakers);
	public int updateSpeakers(ArrayList<Speaker> Speakers);
	public Speaker getSpeakerRow(String id);
	public ArrayList<Speaker> getAllSpeakers();
}
