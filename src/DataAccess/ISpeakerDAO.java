package DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.Event;
import org.osuosl.ocw.Speaker;

public interface ISpeakerDAO {

	public Long addSpeakers(ArrayList<Speaker> mSpeakers);
	public int updateSpeakers(ArrayList<Speaker> Speakers);
	public Event getSpeakerRow(String id);
	public ArrayList<Speaker> getAllSpeakers();
}
