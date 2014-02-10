package DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHelper extends SQLiteAssetHelper implements IDataBaseHelper {

	private static final String DATABASE_NAME = "ocw";     //Name of the .db file stored on the device
	private static final int DATABASE_VERSION = 1;		   

	private static DataBaseHelper mInstance;


	public static synchronized DataBaseHelper getInstance(Context ctx) {

		// Use the application context, which will ensure that you 
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (mInstance == null) {
			mInstance = new DataBaseHelper(ctx.getApplicationContext());
		}
		return mInstance;
	}

	public DataBaseHelper(Context context) {
		super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Inserts into a row of TABLE_NAME with the ContentValues values
	 * @param TABLE_NAME
	 * @param values
	 * @return Error code from insertion
	 */
	public long insertRow(String TABLE_NAME, ContentValues values) {
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();

			i = db.insert(TABLE_NAME, null, values);
			
			db.setTransactionSuccessful();
		} catch(Exception e) {
			db.endTransaction();
		}
		db.endTransaction();

		return i;
	}


	public long updateRow(String TABLE_NAME, int id,  ContentValues values) {
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();

			i = db.update(TABLE_NAME, values, "_id" + " = ?", new String[] { String.valueOf(id)});
			
			db.setTransactionSuccessful();
		} catch(Exception e) {
			db.endTransaction();
		}
		db.endTransaction();

		return i;
	}


	public Cursor getCursor(String TABLE_NAME, String query) {

		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();
			db.beginTransaction();
			
			cursor = db.rawQuery(query,null,null);
			
			db.setTransactionSuccessful();
		}catch(Exception e){
			db.endTransaction();
		}
		db.endTransaction();

		return cursor;
	}
	
	
	public long deleteAllRowsFromTable(String TABLE_NAME) {
		SQLiteDatabase db = null;
		int i = 0;
		try {
			db = this.getReadableDatabase();
			db.beginTransaction();

			String deleteSQL = "DELETE FROM " +TABLE_NAME;
			db.execSQL(deleteSQL);
			
			db.setTransactionSuccessful();

			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;
	}





















}
