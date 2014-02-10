package DataAccess;

public interface IStatusDAO {

	public Long addStatusRow(String name, String value);
	public int updateStatusRow(String name, String value);
	public Long getStatusRow(String name);
	public int existsStatusRow(String table);
	
}
