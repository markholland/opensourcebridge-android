package DataAccess;

import android.content.ContentValues;
import android.database.Cursor;

public interface IDataBaseHelper {
	
	public long insertRow(String TABLE_NAME, ContentValues values);
	public long updateRow(String TABLE_NAME, ContentValues values);
	public Cursor getRow(String TABLE_NAME, String query);
	
}
