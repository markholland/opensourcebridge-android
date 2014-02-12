package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Speaker;


public interface ISpeakerDAO {

	public Long addSpeakers(ArrayList<SpeakerDTO> mSpeakers);
	public int updateSpeakers(ArrayList<SpeakerDTO> Speakers);
	public SpeakerDTO getSpeakerRow(String id);
	public ArrayList<SpeakerDTO> getAllSpeakers();
	public long numRows();
	public void deleteAllRows();
}
