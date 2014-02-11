package org.osuosl.ocw.DataAccess;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.osuosl.ocw.BusinessLogic.Event;
import org.osuosl.ocw.BusinessLogic.Speaker;
import org.osuosl.ocw.BusinessLogic.Track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EventDAOImp implements IEventDAO{

	
	private static DataBaseHelper dbh;
	private Context ctx;
	
	private static final String EVENT_TABLE_NAME = "EVENT";
	private static final String SPEAKS_AT_TABLE_NAME = "SPEAKS_AT";
	// Schedule table column names
	private static final String KEY_EVENT_ID = "_id";
	private static final String KEY_TITLE = "event_title";
	private static final String KEY_START = "start_time";
	private static final String KEY_END = "end_time";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_ROOM_TITLE = "room_title";
	private static final String KEY_TRACK_ID = "track_id";
	private static final String KEY_SPEAKER_ID = "speaker_id";
	private static final String KEY_PRESENTER = "presenter";

	// Queries
	private static final String[] GET_SCHEDULE_ROW = new String[]{KEY_EVENT_ID, KEY_TITLE, KEY_START, KEY_END, KEY_DESCRIPTION, KEY_ROOM_TITLE, KEY_TRACK_ID, KEY_PRESENTER};


	public EventDAOImp(Context ctx) {
		super();
    	dbh = DataBaseHelper.getInstance(ctx.getApplicationContext());
    	this.ctx = ctx;
	}
	
	
	public Long addEvents(ArrayList<Event> events){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = dbh.getWritableDatabase();
			db.beginTransaction();
			try{
				// for each event
				for(int j = 0; j < events.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_EVENT_ID, events.get(j).getId());
					values.put(KEY_TITLE, events.get(j).getTitle());
					values.put(KEY_START, events.get(j).getStart_time().toString());
					values.put(KEY_END, events.get(j).getEnd_time().toString());
					values.put(KEY_DESCRIPTION, events.get(j).getDescription());
					values.put(KEY_ROOM_TITLE, events.get(j).getRoom_title());
					values.put(KEY_TRACK_ID, events.get(j).getTrack().getId());

					values.put(KEY_PRESENTER, events.get(j).getPresenter().getId());

					// adding row
					i = db.insert(EVENT_TABLE_NAME, null, values);
					
					Iterator<Speaker> speakers = events.get(j).getSpeakers();
					ContentValues speakersValues = new ContentValues();
					//for each speaker at this event
					while(speakers.hasNext()) {
						
						speakersValues.put(KEY_EVENT_ID, events.get(j).getId());
						speakersValues.put(KEY_SPEAKER_ID, speakers.next().getId());
						
						i = db.insert(SPEAKS_AT_TABLE_NAME, null, values);
						
					}

					
				}
				db.setTransactionSuccessful();

			} catch(Exception e){
				db.endTransaction();
				
			}

			db.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return i;
	}
	
	
	
	public int updateEvents(ArrayList<Event> events){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = dbh.getWritableDatabase();

			db.beginTransaction();
			try{
				for(int j = 0; j < events.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_EVENT_ID, events.get(j).getId());
					values.put(KEY_TITLE, events.get(j).getTitle());
					values.put(KEY_START, events.get(j).getStart_time().toString());
					values.put(KEY_END, events.get(j).getEnd_time().toString());
					values.put(KEY_DESCRIPTION, events.get(j).getDescription());
					values.put(KEY_ROOM_TITLE, events.get(j).getRoom_title());
					values.put(KEY_TRACK_ID, events.get(j).getTrack().getId());

					values.put(KEY_PRESENTER, events.get(j).getPresenter().getId());

					// updating row
					i = db.update(EVENT_TABLE_NAME, values, KEY_EVENT_ID + " = ?",
							new String[] { String.valueOf(events.get(j).getId())});
					
					
					Iterator<Speaker> speakers = events.get(j).getSpeakers();
					ContentValues speakersValues = new ContentValues();
					//for each speaker at this event
					while(speakers.hasNext()) {
						
						speakersValues.put(KEY_EVENT_ID, events.get(j).getId());
						speakersValues.put(KEY_SPEAKER_ID, speakers.next().getId());
						
						i = db.update(SPEAKS_AT_TABLE_NAME, speakersValues, KEY_EVENT_ID + " = ?",
								new String[] { String.valueOf(events.get(j).getId())});
						
					}
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
	 * Retrieve a single row(Event) from the schedule database table.
	 * @param id row to be retrieved.
	 * @return Event at row == id.
	 */
	public EventDTO getEventRow(String id) {
		EventDTO event = null;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.query(EVENT_TABLE_NAME, GET_SCHEDULE_ROW, KEY_EVENT_ID + "=?",
						new String[] { id }, null, null, null, null);
				if (cursor.moveToFirst()) {
					
					int event_id = cursor.getInt(0);
					String event_title = cursor.getString(1);
					String start_time = cursor.getString(2);
					String end_time = cursor.getString(3);
					
					String description = cursor.getString(4);
					String room_title = cursor.getString(5);
					int track_id = cursor.getInt(6);
					
					
					int presenter_id = cursor.getInt(7);
					
					
					
					event = new EventDTO(
							event_id, event_title, start_time, end_time, description, room_title, track_id, presenter_id);
					

				}
				cursor.close();
				db.setTransactionSuccessful();
			}catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return event;
	}

	/**
	 * Retrieve all rows(Event) in a single transaction.
	 * @param id row to be retrieved.
	 * @return Event at row == id.
	 */
	public ArrayList<EventDTO> getAllEvents() {
		ArrayList<EventDTO> events = new ArrayList<EventDTO>();
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM "+EVENT_TABLE_NAME, null);
				if (cursor.moveToFirst()){

					while (cursor.isAfterLast() == false) {

						int event_id = cursor.getInt(0);
						String event_title = cursor.getString(1);
						String start_time = cursor.getString(2);
						String end_time = cursor.getString(3);
						String description = cursor.getString(4);
						String room_title = cursor.getString(5);
						int track_id = cursor.getInt(6);
						int presenter_id = cursor.getInt(7);
						
						EventDTO event = new EventDTO(
								event_id, event_title, start_time, end_time, description, room_title, track_id, presenter_id);
						events.add(event);
						
						cursor.moveToNext();
					}
				}
				cursor.close();
				db.setTransactionSuccessful();
			}catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return events;
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
			db = dbh.getReadableDatabase();

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
			e.printStackTrace();
		}
		
		return exists;
	}


	public long numRows() {
		return dbh.numRows(EVENT_TABLE_NAME);
	}
	
	public void deleteAllRows() {
		dbh.deleteAllRows(EVENT_TABLE_NAME);
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

