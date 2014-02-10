package org.osuosl.ocw.DataAccess;

public interface IDataBaseHelper {
	public long numRows(String TABLE_NAME);
	public void deleteAllRows(String TABLE_NAME);
}
