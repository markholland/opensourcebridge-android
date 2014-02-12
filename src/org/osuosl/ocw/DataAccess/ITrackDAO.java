package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Track;


public interface ITrackDAO {

	public Long addTracks(ArrayList<TrackDTO> mTracks);
	public int updateTracks(ArrayList<TrackDTO> Tracks);
	public TrackDTO getTrackRow(String id);
	public ArrayList<TrackDTO> getAllTracks();
	public long numRows();
	public void deleteAllRows();
}
