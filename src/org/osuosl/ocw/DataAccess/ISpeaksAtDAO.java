package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

public interface ISpeaksAtDAO {
	
	public ArrayList<Integer> getAllSpeakers(int event_id);
	public Long addSpeaksAt(SpeaksAtDTO saDTO);
	public int updateSpeaksAt(SpeaksAtDTO saDTO);

}
