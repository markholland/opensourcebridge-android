package DataAccess;

import java.util.ArrayList;

import org.osuosl.ocw.Speaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SpeakerDAOImp implements ISpeakerDAO {

	private static final String SPEAKERS_TABLE_NAME = "SPEAKERS";

	private static DataBaseHelper dbh;

	private static final String KEY_ID = "_id";
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


	public SpeakerDAOImp(Context ctx) {
		super();
		dbh = DataBaseHelper.getInstance(ctx.getApplicationContext());
	}



	public Long addSpeakers(ArrayList<Speaker> speakers){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = dbh.getWritableDatabase();

			db.beginTransaction();
			try{

				for(int j = 0; j < speakers.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_SPEAKER_ID, speakers.get(j).getSpeaker_id());
					values.put(KEY_NAME, speakers.get(j).getFullname());
					values.put(KEY_BIO, speakers.get(j).getBiography());
					values.put(KEY_AFFILIATION, speakers.get(j).getAffiliation());
					values.put(KEY_TWITTER, speakers.get(j).getTwitter());
					values.put(KEY_EMAIL, speakers.get(j).getEmail());
					values.put(KEY_WEBSITE, speakers.get(j).getWebsite());
					values.put(KEY_BLOG, speakers.get(j).getBlog());
					values.put(KEY_LINKEDIN, speakers.get(j).getLinkedin());
					//adding row
					i = db.insert(SPEAKERS_TABLE_NAME, null, values);
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



	public int updateSpeakers(ArrayList<Speaker> speakers){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = dbh.getWritableDatabase();
			db.beginTransaction();
			try{
				for(int j = 0; j < speakers.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_SPEAKER_ID, speakers.get(j).getSpeaker_id());
					values.put(KEY_NAME, speakers.get(j).getFullname());
					values.put(KEY_BIO, speakers.get(j).getBiography());
					values.put(KEY_AFFILIATION, speakers.get(j).getAffiliation());
					values.put(KEY_TWITTER, speakers.get(j).getTwitter());
					values.put(KEY_EMAIL, speakers.get(j).getEmail());
					values.put(KEY_WEBSITE, speakers.get(j).getWebsite());
					values.put(KEY_BLOG, speakers.get(j).getBlog());
					values.put(KEY_LINKEDIN, speakers.get(j).getLinkedin());

					// updating row
					i = db.update(SPEAKERS_TABLE_NAME, values, KEY_SPEAKER_ID + " = ?",
							new String[] { String.valueOf(speakers.get(j).getSpeaker_id())});
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
	 * Retrieve a single row(Speaker) from the speakers database table
	 * @param id row to be retrieved.
	 * @return Speaker at row == id.
	 */
	public Speaker getSpeakerRow(String id) {
		Speaker speaker = null;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM SPEAKERS WHERE _id = "+id, null);

				if (cursor.moveToFirst()){
					
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
			e.printStackTrace();
		}
		
		return speaker;
	}

	
	/**
	 * Retrieve a single row(Speaker) from the speakers database table
	 * @param id row to be retrieved.
	 * @return Speaker at row == id.
	 */
	public ArrayList<Speaker> getAllSpeakers() {
		ArrayList<Speaker> speakers = new ArrayList<Speaker>();
		Speaker speaker = null;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM "+SPEAKERS_TABLE_NAME, null, null);
				if (cursor.moveToFirst()){
					
					while (cursor.isAfterLast() == false) {	
					
					speaker = new Speaker(
							cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
							cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
							cursor.getString(9));
					
					speakers.add(speaker);
					
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
		
		return speakers;
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
			db = dbh.getReadableDatabase();

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
			e.printStackTrace();
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
