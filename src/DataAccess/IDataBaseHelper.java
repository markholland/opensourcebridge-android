package DataAccess;

import android.content.ContentValues;
import android.database.Cursor;

public interface IDataBaseHelper {
	
	public long insertRow(String TABLE_NAME, ContentValues values);
	public long updateRow(String TABLE_NAME, int id, ContentValues values);
	public Cursor getCursor(String TABLE_NAME, String query);
	public long deleteAllRowsFromTable(String TABLE_NAME);
	
}
