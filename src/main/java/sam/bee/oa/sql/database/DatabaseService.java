package sam.bee.oa.sql.database;

import java.sql.SQLException;
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
	PageModel getPage(String tableName, long start, long pageSize) throws Exception;

	/**
	 * Get all tables info.
	 * 
	 * @return
	 */
	List<Map<String, String>> getAllTables();

	/**
	 *
	 */
	void close();

	/**
	 *
	 * @param expectType
	 * @param tableName
	 * @return
	 */
	String dropTableSql(String expectType, String tableName);

	/**
	 *
	 * @param expectType
	 * @param tableName
	 * @return
	 */
	String createTableSql(String expectType, String tableName);

	/**
	 *
	 * @param tableName
	 * @param callback
	 */
	void getData(String tableName, Callback callback);

	/**
	 *
	 * @param tableName
	 * @param data
	 * @return
	 */
	boolean saveData(String tableName, Map data);

	/**
	 *
	 * @param sql
	 */
	void executeSQL(String sql);

	/**
	 *
	 * @return
	 */
	String getDatabaseType();
}
