package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.BusinessLogic.Track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TrackDAOImp implements ITrackDAO {

	private static DataBaseHelper dbh;


	private static final String KEY_ID = "_id";
	
	private static final String TRACKS_TABLE_NAME = "TRACKS";
	// Tracks table column names
	private static final String KEY_TRACK_ID = "track_id";
	private static final String KEY_TRACK_TITLE = "track_title";
	private static final String KEY_COLOR = "color";
	private static final String KEY_COLOR_TEXT = "color_text";


	public TrackDAOImp(Context ctx) {
		super();
		dbh = DataBaseHelper.getInstance(ctx.getApplicationContext());
	}

	

	public Long addTracks(ArrayList<Track> tracks) {
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = dbh.getWritableDatabase();

			db.beginTransaction();

			try{
				for(int j = 0; j < tracks.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_TRACK_ID, tracks.get(j).getId());
					values.put(KEY_TRACK_TITLE, tracks.get(j).getTitle());
					values.put(KEY_COLOR, tracks.get(j).getColor());
					values.put(KEY_COLOR_TEXT, tracks.get(j).getColor_text());

					//adding row
					i = db.insert(TRACKS_TABLE_NAME, null, values);
				}

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}

	

	public int updateTracks(ArrayList<Track> tracks) {
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = dbh.getWritableDatabase();
			db.beginTransaction();
			try{
				for(int j = 0; j < tracks.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_TRACK_ID, tracks.get(j).getId());
					values.put(KEY_TRACK_TITLE, tracks.get(j).getTitle());
					values.put(KEY_COLOR, tracks.get(j).getColor());
					values.put(KEY_COLOR_TEXT, tracks.get(j).getColor_text());


					// updating row
					i = db.update(TRACKS_TABLE_NAME, values, KEY_TRACK_ID + " = ?",
							new String[] { String.valueOf(tracks.get(j).getId())});
				}
				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}




	/**
	 * Retrieve a single row(Track) from the tracks database table
	 * @param id row of the track to be retrieved.
	 * @return Track at row == id.
	 */
	public TrackDTO getTrackRow(String id) {
		TrackDTO track = null;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM TRACKS WHERE _id = "+id, null);

				if (cursor.moveToFirst()){
					
					track = new TrackDTO(
							cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3) 
							);
					cursor.close();
				}
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return track;
	}
	
	
	/**
	 * Retrieve a single row(Track) from the tracks database table
	 * @param id row of the track to be retrieved.
	 * @return Track at row == id.
	 */
	public ArrayList<TrackDTO> getAllTracks() {
		ArrayList<TrackDTO> tracks = new ArrayList<TrackDTO>();
		TrackDTO track = null;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM "+TRACKS_TABLE_NAME, null);

				if (cursor.moveToFirst()){

					while (cursor.isAfterLast() == false) {


						track = new TrackDTO(
								cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3) 
								);

						tracks.add(track);
						
						cursor.moveToNext();
					}
				}
				cursor.close();
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tracks;
	}

	

	/**
	 * Checks if a row(Track) already exists with track_id == id.
	 * @param id track_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsTrack(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();

			try{
				Cursor cursor = db.rawQuery("select 1 from TRACKS where track_id=?", 
						new String[] { id });
				Boolean b = (cursor.getCount() > 0);
				exists = b? 1 : 0;
				cursor.close();
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{

		}

		return exists;
	}

	
	public long numRows() {
		return dbh.numRows(TRACKS_TABLE_NAME);
	}
	
	
	public void deleteAllRows() {
		dbh.deleteAllRows(TRACKS_TABLE_NAME);
	}
	

	/**
	 * Converts an array of Strings to a comma separated string
	 * @param array
	 * @return 
	 */
	public static String convertArrayToString(String[] array) {
		String str = "";
		for (int i = 0;i<array.length; i++) {
			str = str+array[i];
			// Do not append comma at the end of last element
			if(i<array.length-1){
				str = str+",";
			}
		}
		return str;
	}
	/**
	 * Converts a comma separated string of elements into an array of Strings
	 * @param str
	 * @return
	 */
	public static String[] convertStringToArray(String str) {
		String[] arr = str.split(",");
		return arr;
	}
}


