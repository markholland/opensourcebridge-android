package DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	
	
}
