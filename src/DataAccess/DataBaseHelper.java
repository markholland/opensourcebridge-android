package DataAccess;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHelper extends SQLiteAssetHelper implements IDataBaseHelper {

	private static final String DATABASE_NAME = "ocw";  //Name of the .db file stored on the device
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
	 * Returns the number of rows a table has.
	 * @param table_name The table to be queried.
	 * @return The number of rows that the table "table_name" has.
	 */
	public long numRows(String table_name) {
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
			e.printStackTrace();
		}
		return numRows;

	}
	
	public void deleteAllRows(String TABLE_NAME) {
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();
			db.delete(TABLE_NAME, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
}
