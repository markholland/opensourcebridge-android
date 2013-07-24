package org.osuosl.ocw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHandler extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "ocw";
	private static final int DATABASE_VERSION = 1;
	
	// Schedule table name
    private static final String SCHEDULE_TABLE_NAME = "SCHEDULE";
    private static final String SPEAKERS_TABLE_NAME = "SPEAKERS";
    
    // Schedule Table Columns names
    private static final String KEY_SCHEDULE_ID = "id";
    private static final String KEY_EVENT_ID = "event_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_START = "start_time";
    private static final String KEY_END = "end_time";
    private static final String KEY_ROOM_TITLE = "room_title";
    private static final String KEY_TRACK_ID = "track_id";
    private static final String KEY_TRACK_TITLE = "track_title";
    
    // Speakers Table Columns names
    private static final String KEY_SPEAKER_ID = "id";
    private static final String KEY_NAME = "fullname";
    private static final String KEY_BIO = "biography";
    private static final String KEY_TWITTER = "twitter";
    private static final String KEY_IDENTICA = "identica";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_BLOG = "blog_url";
    private static final String KEY_AFFILIATION = "affiliation";
    
    
    

	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		// you can use an alternate constructor to specify a database location 
		// (such as a folder on the sd card)
		// you must ensure that this folder is available and you have permission
		// to write to it
		//super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

	}
	
	public Long addScheduleRow(Event ev){
		SQLiteDatabase db = null;
 		Long i = 0l;
		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_EVENT_ID, ev.getEventId());
			values.put(KEY_TITLE, ev.getTitle());
			values.put(KEY_DESCRIPTION, ev.getDescription());
			values.put(KEY_START, ev.getStart().toString());
			values.put(KEY_END, ev.getEnd().toString());
			values.put(KEY_ROOM_TITLE, ev.getLocation());
			values.put(KEY_TRACK_ID, ev.getTrackId());
			values.put(KEY_TITLE, ev.getTitle());
			
			// updating row
			i = db.insert(SCHEDULE_TABLE_NAME, null, values);//String.valueOf(ev.getId())});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		finally{
        	db.close();
        }
 		return i;
	}
	
	public Long addSpeakersRow(Speaker sp){
		SQLiteDatabase db = null;
 		Long i = 0l;
 		
 		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_SPEAKER_ID, sp.getId());
			values.put(KEY_NAME, sp.getName());
			values.put(KEY_BIO, sp.getBiography());
			values.put(KEY_TWITTER, sp.getTwitter());
			values.put(KEY_IDENTICA, sp.getIdentica());
			values.put(KEY_WEBSITE, sp.getWebsite());
			values.put(KEY_BLOG, sp.getBlog());
			values.put(KEY_AFFILIATION, sp.getAffiliation());
			
			// updating row
			i = db.insert(SPEAKERS_TABLE_NAME, null, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		finally{
        	db.close();
        }
 		return i;
	}
 	
 	public int updateScheduleRow(Event ev){
 		SQLiteDatabase db = null;
 		int i = 0;
 		
 		
 		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_EVENT_ID, ev.getEventId());
			values.put(KEY_TITLE, ev.getTitle());
			values.put(KEY_DESCRIPTION, ev.getDescription());
			values.put(KEY_START, ev.getStart().toString());
			values.put(KEY_END, ev.getEnd().toString());
			values.put(KEY_ROOM_TITLE, ev.getLocation());
			values.put(KEY_TRACK_ID, ev.getTrackId());
			values.put(KEY_TITLE, ev.getTitle());
			
			// updating row
			i = db.update(SCHEDULE_TABLE_NAME, values, KEY_SCHEDULE_ID + " = ?",
					new String[] { String.valueOf(ev.getId())});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		finally{
        	db.close();
        }
 		return i;
 	}
 	
 	public int updateSpeakerRow(Speaker sp){
 		SQLiteDatabase db = null;
 		int i = 0;
 		
 		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_SPEAKER_ID, sp.getId());
			values.put(KEY_NAME, sp.getName());
			values.put(KEY_BIO, sp.getBiography());
			values.put(KEY_TWITTER, sp.getTwitter());
			values.put(KEY_IDENTICA, sp.getIdentica());
			values.put(KEY_WEBSITE, sp.getWebsite());
			values.put(KEY_BLOG, sp.getBlog());
			values.put(KEY_AFFILIATION, sp.getAffiliation());
			
			// updating row
			i = db.update(SPEAKERS_TABLE_NAME, values, KEY_SPEAKER_ID + " = ?",
					new String[] { String.valueOf(sp.getId())});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		finally{
        	db.close();
        }
 		return i;
 	}
 
    // Getting single schedule row
    Event getScheduleRow(String title) {
    	Event ev = null;
    	SQLiteDatabase db = null;
        try {
			db = this.getReadableDatabase();
			
 
			Cursor cursor = db.query(SCHEDULE_TABLE_NAME, new String[] { KEY_EVENT_ID,
					KEY_TITLE, KEY_DESCRIPTION, KEY_START, KEY_END, KEY_ROOM_TITLE, KEY_TRACK_ID, 
					KEY_TRACK_TITLE }, KEY_TITLE + "=?",
					new String[] { title }, null, null, null, null);
			if (cursor != null){
			    cursor.moveToFirst();
			    Log.d("DATABASE",""+cursor.getCount());
 
			    ev = new Event(
			        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
			        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
			        );
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        finally{
        	db.close();
        }
        
        return ev;
    }
    
    // Getting single speakers row
    Speaker getSpeakersRow(String fullname) {
    	Speaker sp = null;
    	SQLiteDatabase db = null;
        try {
			db = this.getReadableDatabase();
			
 
			Cursor cursor = db.query(SPEAKERS_TABLE_NAME, new String[] { KEY_SPEAKER_ID, KEY_NAME,
			        KEY_BIO, KEY_TWITTER, KEY_IDENTICA, KEY_WEBSITE, KEY_BLOG, KEY_AFFILIATION, }, KEY_NAME + "=?",
			        new String[] { fullname }, null, null, null, null);
			if (cursor != null){
			    cursor.moveToFirst();
			    
 
			    sp = new Speaker(
			        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
			        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
			        );
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        finally{
        	db.close();
        }
        
        return sp;
    }
}
