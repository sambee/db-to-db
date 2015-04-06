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
	List<Map<String, Object>> getMetas(String dbName, String tableName);

	/**
	 * get page infomation.
	 * 
	 * @param paraments
	 * @param start
	 * @param pageSize
	 * @return
	 */
	PageModel getPage(String dbName, Map<String, Object> paraments, long start,
			long pageSize);

	/**
	 * get page info
	 * 
	 * @param tableName
	 * @param start
	 * @param pageSize
	 * @return
	 */
	PageModel getPage(String dbName, String tableName, long start, long pageSize);

	/**
	 * Get all tables info.
	 * 
	 * @return
	 */
	List<Map<String, String>> getAllTables();

	/**
	 * 
	 * @param data source source name 
	 * @param output data source name
	 * @param table
	 * @param fields
	 * @param isCreateTable
	 * @param isDropTableIfExist
	 * @param isCopyData
	 * @param callback
	 * @throws Exception
	 */
	void exportTable(String srcDBName, String outputType, String table,
			String fields, boolean isCreateTable, boolean isDropTableIfExist,
			boolean isCopyData, Callback callback) throws Exception;

	void importTable(String dbName, String sql, Callback callback);

	void close();
}
