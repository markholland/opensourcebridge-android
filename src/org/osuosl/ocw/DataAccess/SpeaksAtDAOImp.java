package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SpeaksAtDAOImp implements ISpeaksAtDAO {

	private static DataBaseHelper dbh;

	private static final String EVENT_TABLE_NAME = "EVENT";
	private static final String SPEAKER_TABLE_NAME = "SPEAKER";

	private static final String SPEAKS_AT_TABLE_NAME = "SPEAKS_AT";

	private static final String KEY_EVENT = "_id";
	private static final String KEY_SPEAKER = "speaker_id";

	public SpeaksAtDAOImp(Context ctx) {
		super();
		dbh = DataBaseHelper.getInstance(ctx.getApplicationContext());

	}

	public ArrayList<Integer> getAllSpeakers(int id) {
		ArrayList<Integer> speakerIds = new ArrayList<Integer>();
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM "+SPEAKS_AT_TABLE_NAME+" SA, "+
						SPEAKER_TABLE_NAME+" SP, "+EVENT_TABLE_NAME+" E WHERE E._id = "+id+" AND SA."+KEY_SPEAKER+" = SP._id", null);
				if (cursor.moveToFirst()) {

					while (cursor.isAfterLast() == false) {
						speakerIds.add(cursor.getInt(1));
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


		return speakerIds;
	}

	public Long addSpeaksAt(SpeaksAtDTO saDTO) {
		Long i = 0l;
		SQLiteDatabase db = null;
		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_EVENT, saDTO.getEvent_id());
				values.put(KEY_SPEAKER, saDTO.getSpeaker_id());

				i = db.insert(SPEAKS_AT_TABLE_NAME, null, values);


				db.setTransactionSuccessful();
			}catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;

	}


	public int updateSpeaksAt(SpeaksAtDTO saDTO) {
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = dbh.getWritableDatabase();

			db.beginTransaction();
			try{
				ContentValues values = new ContentValues();
				values.put(KEY_EVENT, saDTO.getEvent_id());
				values.put(KEY_SPEAKER, saDTO.getSpeaker_id());
				// updating row
				i = db.update(SPEAKS_AT_TABLE_NAME, values, KEY_EVENT + " = ?",
						new String[] { String.valueOf(saDTO.getEvent_id())});

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
	
	
	public long numRows() {
		return dbh.numRows(SPEAKS_AT_TABLE_NAME);
	}
	
	public void deleteAllRows() {
		dbh.deleteAllRows(SPEAKS_AT_TABLE_NAME);
	}

}
