package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Track;


public interface ITrackDAO {

	public Long addTracks(ArrayList<Track> mTracks);
	public int updateTracks(ArrayList<Track> Tracks);
	public Track getTrackRow(String id);
	public ArrayList<Track> getAllTracks();
}
