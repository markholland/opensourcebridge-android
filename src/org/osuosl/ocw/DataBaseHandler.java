package org.osuosl.ocw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	
//	 // Update single value
// 	public int updateValue(DataHandler dh){
// 		SQLiteDatabase db = null;
// 		int i = 0;
// 		
// 		try {
//			db = this.getWritableDatabase();
//			
//			ContentValues values = new ContentValues();
//			values.put(KEY_NAME, dh.getName());
//			values.put(KEY_VALUE, dh.getValue());
//			
//			// updating row
//			i = db.update(TABLE_NAME, values, KEY_ID + " = ?",
//					new String[] { String.valueOf(dh.getId())});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
// 		
// 		finally{
//        	db.close();
//        }
// 		
// 		return i;
// 	}
 	
 	public int updateScheduleRow(Event ev){
 		SQLiteDatabase db = null;
 		int i = 0;
 		
 		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_EVENT_ID, ev.getEventId());
			values.put(KEY_TITLE, ev.getTitle());
			values.put(KEY_DESCRIPTION, ev.getDescription());
			values.put(KEY_START, ev.getStart());
			values.put(KEY_END, ev.getEnd());
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
 
    // Getting single contact
    DataHandler getValue(String value) {
    	DataHandler dh = null;
    	SQLiteDatabase db = null;
        try {
			db = this.getReadableDatabase();
			
 
			Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
			        KEY_NAME, KEY_VALUE }, KEY_NAME + "=?",
			        new String[] { value }, null, null, null, null);
			if (cursor != null){
			    cursor.moveToFirst();
			    
 
			    dh = new DataHandler(
			        cursor.getString(1), cursor.getString(2));
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        finally{
        	db.close();
        }
        
        return dh;
    }
}
