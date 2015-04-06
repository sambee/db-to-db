package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;

import org.h2.mvstore.Page;

import sam.bee.oa.sql.database.model.PageModel;

public interface DatabaseService {

	/**
	 * get meta data.
	 * 
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getMetas( String tableName);

	/**
	 * get page infomation.
	 * 
	 * @param paraments
	 * @param start
	 * @param pageSize
	 * @return
	 */
	PageModel getPage(Map<String, Object> paraments, long start,
			long pageSize);

	/**
	 * get page info
	 * 
	 * @param tableName
	 * @param start
	 * @param pageSize
	 * @return
	 */
	PageModel getPage(String tableName, long start, long pageSize);

	/**
	 * Get all tables info.
	 * 
	 * @return
	 */
	List<Map<String, String>> getAllTables();

	void close();

	String dropTableSql(String expectType, String tableName);

	String createTableSql(String expectType, String tableName);

	void getData(String tableName, Callback callback);

	boolean saveData(String tableName, Map data);

	void executeSQL(String sql);

	String getDatabaseType();
}
