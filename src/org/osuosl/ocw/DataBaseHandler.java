package org.osuosl.ocw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHandler extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "ocw";
	private static final int DATABASE_VERSION = 1;
	
	// Contacts table name
    private static final String TABLE_NAME = "Testing"; //OCW_DATA
 
    // Contacts Table Columns names
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_VALUE = "VALUE";

	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		// you can use an alternate constructor to specify a database location 
		// (such as a folder on the sd card)
		// you must ensure that this folder is available and you have permission
		// to write to it
		//super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

	}
	
	 // Update single value
 	public int updateValue(DataHandler dh){
 		SQLiteDatabase db = null;
 		int i = 0;
 		
 		try {
			db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_NAME, dh.getName());
			values.put(KEY_VALUE, dh.getValue());
			
			// updating row
			i = db.update(TABLE_NAME, values, KEY_ID + " = ?",
					new String[] { String.valueOf(dh.getId())});
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
