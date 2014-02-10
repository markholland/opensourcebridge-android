package DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StatusDAOImp implements IStatusDAO {

	private static final String STATUS_TABLE_NAME = "STATUS";

	private static DataBaseHelper dbh;

	private static final String KEY_ID = "_id";
	// Status table column names
	private static final String KEY_STATUS_NAME = "name";
	private static final String KEY_STATUS_VALUE = "value";


	public StatusDAOImp(Context ctx) {
		super();
		dbh = DataBaseHelper.getInstance(ctx.getApplicationContext());
	}

	
	/**
	 * Initialize a status table value
	 * @param table
	 * @param value
	 * @return
	 */
	public Long addStatusRow(String name, String value) {
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = dbh.getWritableDatabase();
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
			e.printStackTrace();
		}
		
		return i;
	}



	/**
	 * Update the current time in ms that the table was last updated
	 * @param table
	 * @param value
	 * @return
	 */
	public int updateStatusRow(String name, String value) {
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = dbh.getWritableDatabase();
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
			e.printStackTrace();
		}
		
		return i;

	}



	/**
	 * Returns when a table was last updated.
	 */
	public Long getStatusRow(String name) {
		Long value = -1l;
		SQLiteDatabase db = null;

		try {
			db = dbh.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.rawQuery("SELECT * FROM STATUS WHERE name = '"+name+"'", null);

				if (cursor.moveToFirst()){
					
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
			e.printStackTrace();
		}

		finally{

		}

		return value;
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
			db = dbh.getReadableDatabase();

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
			e.printStackTrace();
		}
		
		return exists;
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

