package org.osuosl.ocw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHandler extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "ocw";     //Name of the .db file stored on the device
	private static final int DATABASE_VERSION = 1;		   

	// Table names
	private static final String SCHEDULE_TABLE_NAME = "SCHEDULE";
	private static final String SPEAKERS_TABLE_NAME = "SPEAKERS";
	private static final String TRACKS_TABLE_NAME = "TRACKS";
	private static final String STATUS_TABLE_NAME = "STATUS";

	private static final String KEY_ID = "_id";
	// Schedule table column names
	private static final String KEY_EVENT_ID = "event_id";
	private static final String KEY_TITLE = "event_title";
	private static final String KEY_START = "start_time";
	private static final String KEY_END = "end_time";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_ROOM_TITLE = "room_title";
	private static final String KEY_TRACK_ID = "track_id";
	private static final String KEY_SPEAKER_IDS = "speaker_ids";
	private static final String KEY_PRESENTER = "presenter";
	
	// Speakers table column names
	private static final String KEY_SPEAKER_ID = "speaker_id";
	private static final String KEY_NAME = "fullname";
	private static final String KEY_BIO = "biography";
	private static final String KEY_AFFILIATION = "affiliation";
	private static final String KEY_TWITTER = "twitter";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_WEBSITE = "website";
	private static final String KEY_BLOG = "blog";
	private static final String KEY_LINKEDIN = "linkedin";
	
	// Tracks table column names
	// track_id already defined in event block
	private static final String KEY_TRACK_TITLE = "track_title";
	private static final String KEY_COLOR = "color";
	private static final String KEY_COLOR_TEXT = "color_text";
	
	// Status table column names
	private static final String KEY_STATUS_NAME = "name";
	private static final String KEY_STATUS_VALUE = "value";
	
	// Queries
	private static final String[] GET_SCHEDULE_ROW = new String[]{KEY_EVENT_ID, KEY_TITLE, KEY_START, KEY_END, KEY_DESCRIPTION, KEY_ROOM_TITLE, KEY_TRACK_ID, KEY_SPEAKER_IDS, KEY_PRESENTER};
	


	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Adds a new row(Event) to the schedule database table.
	 * @param event Event to be added.
	 * @return Result of inserting the row.
	 */
	public Long addScheduleRow(Event event){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_EVENT_ID, event.getEvent_id());
				values.put(KEY_TITLE, event.getEvent_title());
				values.put(KEY_START, event.getStart_time().toString());
				values.put(KEY_END, event.getEnd_time().toString());
				values.put(KEY_DESCRIPTION, event.getDescription());
				values.put(KEY_ROOM_TITLE, event.getRoom_title());
				values.put(KEY_TRACK_ID, event.getTrack_id());
				
				String speakerIds = "";
				if(event.getSpeaker_ids() != null){
					speakerIds = convertArrayToString(event.getSpeaker_ids());
					values.put(KEY_SPEAKER_IDS, speakerIds);
				}
				
				values.put(KEY_PRESENTER, event.getPresenter());
				
				// adding row
				i = db.insert(SCHEDULE_TABLE_NAME, null, values);

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}

			db.endTransaction();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}

	/**
	 * Adds a new row(Speaker) to the speakers database table.
	 * @param speaker Speaker to add.
	 * @return Result of inserting the row.
	 */
	public Long addSpeakersRow(Speaker speaker){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();

			try{
				ContentValues values = new ContentValues();
				values.put(KEY_SPEAKER_ID, speaker.getSpeaker_id());
				values.put(KEY_NAME, speaker.getFullname());
				values.put(KEY_BIO, speaker.getBiography());
				values.put(KEY_AFFILIATION, speaker.getAffiliation());
				values.put(KEY_TWITTER, speaker.getTwitter());
				values.put(KEY_EMAIL, speaker.getEmail());
				values.put(KEY_WEBSITE, speaker.getWebsite());
				values.put(KEY_BLOG, speaker.getBlog());
				values.put(KEY_LINKEDIN, speaker.getLinkedin());

				// adding row
				i = db.insert(SPEAKERS_TABLE_NAME, null, values);

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	/**
	 * Adds a new row(Track) to the tracks database table.
	 * @param track Track to add.
	 * @return Result of inserting the row.
	 */
	public Long addTrackRow(Track track){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();

			try{
				ContentValues values = new ContentValues();
				values.put(KEY_TRACK_ID, track.getTrack_id());
				values.put(KEY_TRACK_TITLE, track.getTrack_title());
				values.put(KEY_COLOR, track.getColor());
				values.put(KEY_COLOR_TEXT, track.getColor_text());
				
				//adding row
				i = db.insert(TRACKS_TABLE_NAME, null, values);

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	/**
	 * Initialize a status table value
	 * @param table
	 * @param value
	 * @return
	 */
	public Long addStatusRow(String name, String value){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_STATUS_NAME, name);
				values.put(KEY_STATUS_VALUE, value);

				// adding row
				i = db.insert(STATUS_TABLE_NAME, null, values);

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}

			db.endTransaction();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}

	/**
	 * Updates an existing row(Event) in the schedule database table.
	 * @param event Event that has been updated with its updated values.
	 * @return Result of updating the row.
	 */
	public int updateScheduleRow(Event event){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_EVENT_ID, event.getEvent_id());
				values.put(KEY_TITLE, event.getEvent_title());
				values.put(KEY_START, event.getStart_time().toString());
				values.put(KEY_END, event.getEnd_time().toString());
				values.put(KEY_DESCRIPTION, event.getDescription());
				values.put(KEY_ROOM_TITLE, event.getRoom_title());
				values.put(KEY_TRACK_ID, event.getTrack_id());
				
				String speakerIds = "";
				if(event.getSpeaker_ids() != null){
					speakerIds = convertArrayToString(event.getSpeaker_ids());
					values.put(KEY_SPEAKER_IDS, speakerIds);
				}
				
				values.put(KEY_PRESENTER, event.getPresenter());

				// updating row
				i = db.update(SCHEDULE_TABLE_NAME, values, KEY_EVENT_ID + " = ?",
						new String[] { String.valueOf(event.getEvent_id())});

				db.setTransactionSuccessful();


			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}

	/**
	 * Updates an existing row(Speaker) in the speakers database table.
	 * @param speaker Speaker that has been updated with its updated values.
	 * @return Result of updating the row.
	 */
	public int updateSpeakersRow(Speaker speaker){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_SPEAKER_ID, speaker.getSpeaker_id());
				values.put(KEY_NAME, speaker.getFullname());
				values.put(KEY_BIO, speaker.getBiography());
				values.put(KEY_AFFILIATION, speaker.getAffiliation());
				values.put(KEY_TWITTER, speaker.getTwitter());
				values.put(KEY_EMAIL, speaker.getEmail());
				values.put(KEY_WEBSITE, speaker.getWebsite());
				values.put(KEY_BLOG, speaker.getBlog());
				values.put(KEY_LINKEDIN, speaker.getLinkedin());
				
				// updating row
				i = db.update(SPEAKERS_TABLE_NAME, values, KEY_SPEAKER_ID + " = ?",
						new String[] { String.valueOf(speaker.getSpeaker_id())});
				db.setTransactionSuccessful();
				
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	/**
	 * Updates an existing row(Track) in the tracks database table.
	 * @param track Track that has been updated with its updated values.
	 * @return Result of updating the row.
	 */
	public int updateTracksRow(Track track){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_TRACK_ID, track.getTrack_id());
				values.put(KEY_TRACK_TITLE, track.getTrack_title());
				values.put(KEY_COLOR, track.getColor());
				values.put(KEY_COLOR_TEXT, track.getColor_text());
				

				// updating row
				i = db.update(TRACKS_TABLE_NAME, values, KEY_TRACK_ID + " = ?",
						new String[] { String.valueOf(track.getTrack_id())});

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
	}
	
	
	
	/**
	 * Update the current time in ms that the table was last updated
	 * @param table
	 * @param value
	 * @return
	 */
	public int updateStatusRow(String name, String value){
		SQLiteDatabase db = null;
		int i = 0;
		
		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_STATUS_NAME, name);
				values.put(KEY_STATUS_VALUE, value);
				
				// updating row
				i = db.update(STATUS_TABLE_NAME, values, KEY_STATUS_NAME + " = ?",
						new String[] { name });

				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}
		return i;
		
	}
	
	

	/**
	 * Retrieve a single row(Event) from the schedule database table.
	 * @param id row to be retrieved.
	 * @return Event at row == id.
	 */
	public Event getScheduleRow(String id) {
		Event event = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.query(SCHEDULE_TABLE_NAME, GET_SCHEDULE_ROW, KEY_ID + "=?",
						new String[] { id }, null, null, null, null);
				if (cursor != null){
					cursor.moveToFirst();
					// Log.d("DATABASE",""+cursor.getCount());

					
					String event_title = cursor.getString(1);
					String start_time = cursor.getString(2);
					String end_time = cursor.getString(3);
					String description = cursor.getString(4);
					String room_title = cursor.getString(5);
					String track_id = cursor.getString(6);
					String sIds = cursor.getString(7);
					String presenter = cursor.getString(8);
					cursor.close();
					// TODO
					String[] speakerids = {};
					if(sIds != null)
						speakerids  = sIds.split(",");

					event = new Event(
							id, event_title, start_time, end_time, description, room_title, track_id, speakerids, presenter);

				}
				db.setTransactionSuccessful();
			}catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return event;
	}

	/**
	 * Retrieve a single row(Speaker) from the speakers database table
	 * @param id row to be retrieved.
	 * @return Speaker at row == id.
	 */
	public Speaker getSpeakersRow(String id) {
		Speaker speaker = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM SPEAKERS WHERE _id = "+id, null);

				if (cursor != null){
					cursor.moveToFirst();


					speaker = new Speaker(
							cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
							cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
							cursor.getString(9));
					cursor.close();
				}
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return speaker;
	}
	
	/**
	 * Retrieve a single row(Track) from the tracks database table
	 * @param id row of the track to be retrieved.
	 * @return Track at row == id.
	 */
	public Track getTracksRow(String id) {
		Track track = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM TRACKS WHERE _id = "+id, null);

				if (cursor != null){
					cursor.moveToFirst();

					track = new Track(
							cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4) 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return track;
	}
	
	/**
	 * Returns when a table was last updated.
	 */
	public Long getStatusRow(String name) {
		Long value = -1l;
		SQLiteDatabase db = null;
		
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM STATUS WHERE name = '"+name+"'", null);

				if (cursor != null){
					cursor.moveToFirst();

					value = Long.parseLong(cursor.getString(2));
					
					cursor.close();
				}
				db.setTransactionSuccessful();
			} catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return value;
	}
	
	
	/**
	 * Returns the number of rows a table has.
	 * @param table_name The table to be queried.
	 * @return The number of rows that the table "table_name" has.
	 */
	public long numRows(String table_name){
		long numRows = 0l;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();
			db.beginTransaction();
			try{
				numRows = DatabaseUtils.queryNumEntries(db, table_name);
				db.setTransactionSuccessful();
			} catch (Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return numRows;

	}

	/**
	 * Checks if a row(Event) already exists with event_id == id.
	 * @param id event_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsEvent(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{
				Cursor cursor = db.rawQuery("select 1 from SCHEDULE where event_id=?", 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}

	/**
	 * Checks if a row(Speaker) already exists with speaker_id == id.
	 * @param id speaker_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsSpeaker(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();

			try{
				Cursor cursor = db.rawQuery("select 1 from SPEAKERS where speaker_id=?", 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
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
			db = this.getReadableDatabase();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}
	
	/**
	 * Returns whether a last updated time exists for a table
	 * @param table
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsStatusRow(String table) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();

			try{
				Cursor cursor = db.rawQuery("select 1 from STATUS where name=?", 
						new String[] { table });
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return exists;
	}
	
	/**
	 * Converts an array of Strings to a comma separated string
	 * @param array
	 * @return 
	 */
	public static String convertArrayToString(String[] array){
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
	public static String[] convertStringToArray(String str){
		String[] arr = str.split(",");
		return arr;
	}
}
