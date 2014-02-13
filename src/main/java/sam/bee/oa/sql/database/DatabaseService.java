package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;

import org.h2.mvstore.Page;

import sam.bee.oa.sql.database.model.PageModel;


public interface DatabaseService {
	
	/**
	 * get meta data.
	 * 
	 * @param database name
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
	PageModel getPage(Map<String,Object> paraments, int start, int pageSize); 
	
	
	/**
	 * get page info
	 * 
	 * @param type
	 * @param tableName
	 * @param start
	 * @param pageSize
	 * @return
	 */
	PageModel getPage(String type, String tableName, int start, int pageSize);
	
	
	/**
	 * Get all tables info.
	 * @return
	 */
	List<Map<String, Object>> getAllTables(String type);

	
	/**
	 * 
	 * @param srcType
	 * @param descType
	 * @param table
	 * @param fields
	 * @param isCreateTable
	 * @param isDropTableIfExist
	 * @param isCopyData
	 * @param callback
	 * @throws Exception
	 */
	void exportTable(String dbName, 
			String descType, 
			String table, 
			String fields,  
			boolean isCreateTable, 
			boolean isDropTableIfExist, 
			boolean isCopyData, 
			Callback callback) throws Exception;
	
	
	void importTable(String dbType, String sql, Callback callback);

}
