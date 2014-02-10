package DataAccess;

public class EventDAOImp implements IEventDAO{

	// Table names
	private static final String SCHEDULE_TABLE_NAME = "SCHEDULE";
	private static final String KEY_ID = "_id";
	// Schedule table column names
	private static final String KEY_EVENT_ID = "event_id";
	private static final String KEY_TITLE = "event_title";
	private static final String KEY_START = "start_time";
	private static final String KEY_END = "end_time";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_ROOM_TITLE = "room_title";
	private static final String KEY_TRACK_ID = "track_id";
	private static final String KEY_SPEAKER_IDS = "speaker_ids";
	private static final String KEY_PRESENTER = "presenter";

	// Queries
	private static final String[] GET_SCHEDULE_ROW = new String[]{KEY_EVENT_ID, KEY_TITLE, KEY_START, KEY_END, KEY_DESCRIPTION, KEY_ROOM_TITLE, KEY_TRACK_ID, KEY_SPEAKER_IDS, KEY_PRESENTER};


	
	public Long addEvents(ArrayList<Event> mEvents){
		SQLiteDatabase db = null;
		Long i = 0l;

		try {
			db = this.getWritableDatabase();
			db.beginTransaction();
			try{
				for(int j = 0; j < mEvents.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_EVENT_ID, mEvents.get(j).getEvent_id());
					values.put(KEY_TITLE, mEvents.get(j).getEvent_title());
					values.put(KEY_START, mEvents.get(j).getStart_time().toString());
					values.put(KEY_END, mEvents.get(j).getEnd_time().toString());
					values.put(KEY_DESCRIPTION, mEvents.get(j).getDescription());
					values.put(KEY_ROOM_TITLE, mEvents.get(j).getRoom_title());
					values.put(KEY_TRACK_ID, mEvents.get(j).getTrack_id());

					String speakerIds = "";
					if(mEvents.get(j).getSpeaker_ids() != null){
						speakerIds = convertArrayToString(mEvents.get(j).getSpeaker_ids());
						values.put(KEY_SPEAKER_IDS, speakerIds);
					}

					values.put(KEY_PRESENTER, mEvents.get(j).getPresenter());

					// adding row
					i = db.insert(SCHEDULE_TABLE_NAME, null, values);
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
			db.close();
		}
		return i;
	}
	
	
	
	
	
	public int updateEvents(ArrayList<Event> events){
		SQLiteDatabase db = null;
		int i = 0;

		try {
			db = this.getWritableDatabase();

			db.beginTransaction();
			try{
				for(int j = 0; j < events.size(); j++){
					ContentValues values = new ContentValues();
					values.put(KEY_EVENT_ID, events.get(j).getEvent_id());
					values.put(KEY_TITLE, events.get(j).getEvent_title());
					values.put(KEY_START, events.get(j).getStart_time().toString());
					values.put(KEY_END, events.get(j).getEnd_time().toString());
					values.put(KEY_DESCRIPTION, events.get(j).getDescription());
					values.put(KEY_ROOM_TITLE, events.get(j).getRoom_title());
					values.put(KEY_TRACK_ID, events.get(j).getTrack_id());

					String speakerIds = "";
					if(events.get(j).getSpeaker_ids() != null){
						speakerIds = convertArrayToString(events.get(j).getSpeaker_ids());
						values.put(KEY_SPEAKER_IDS, speakerIds);
					}

					values.put(KEY_PRESENTER, events.get(j).getPresenter());

					// updating row
					i = db.update(SCHEDULE_TABLE_NAME, values, KEY_EVENT_ID + " = ?",
							new String[] { String.valueOf(events.get(j).getEvent_id())});
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
			db.close();
		}
		return i;
	}

	

	/**
	 * Retrieve a single row(Event) from the schedule database table.
	 * @param id row to be retrieved.
	 * @return Event at row == id.
	 */
	public Event getEventRow(String id) {
		Event event = null;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{

				Cursor cursor = db.query(SCHEDULE_TABLE_NAME, GET_SCHEDULE_ROW, KEY_ID + "=?",
						new String[] { id }, null, null, null, null);
				if (cursor != null){
					cursor.moveToFirst();
					
					String event_id = cursor.getString(0);
					String event_title = cursor.getString(1);
					String start_time = cursor.getString(2);
					String end_time = cursor.getString(3);
					String description = cursor.getString(4);
					String room_title = cursor.getString(5);
					String track_id = cursor.getString(6);
					String sIds = cursor.getString(7);
					String presenter = cursor.getString(8);
					cursor.close();
					String[] speakerids = {};
					if(sIds != null)
						speakerids  = sIds.split(",");

					event = new Event(
							event_id, event_title, start_time, end_time, description, room_title, track_id, speakerids, presenter);

				}
				db.setTransactionSuccessful();
			}catch(Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return event;
	}

	
	
	/**
	 * Returns the number of rows a table has.
	 * @param table_name The table to be queried.
	 * @return The number of rows that the table "table_name" has.
	 */
	public long numRows(String table_name){
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

		finally{
			db.close();
		}

		return numRows;

	}
	
	public long deleteAllRowsInTable(String table_name){
		SQLiteDatabase db = null;
		int i = 0;
		try {
			db = this.getReadableDatabase();
			db.beginTransaction();
			try{
				String deleteSQL = "DELETE FROM " + table_name;
				db.execSQL(deleteSQL);
				db.setTransactionSuccessful();
			} catch (Exception e){
				db.endTransaction();
				throw e;
			}
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			db.close();
		}

		return i;
	}

	/**
	 * Checks if a row(Event) already exists with event_id == id.
	 * @param id event_id to be checked if already exists in the database.
	 * @return 1 if exists, 0 if doesn't exist, -1 if error checking if exists.
	 */
	public int existsEvent(String id) {
		int exists = -1;
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();

			db.beginTransaction();
			try{
				Cursor cursor = db.rawQuery("select 1 from SCHEDULE where event_id=?", 
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

		finally{
			db.close();
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

