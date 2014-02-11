package org.osuosl.ocw.DataAccess;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SpeaksAtDAOImp implements ISpeaksAtDAO {

	private static DataBaseHelper dbh;
	
	private static final String EVENT_TABLE_NAME = "EVENT";
	private static final String SPEAKER_TABLE_NAME = "SPEAKER";
	
	private static final String SPEAKS_AT_TABLE_NAME = "SPEAKS_AT";


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
				SPEAKER_TABLE_NAME+" SP, "+EVENT_TABLE_NAME+" E WHERE E._id = "+id+" AND SA.speaker_id = SP._id", null);
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

}
