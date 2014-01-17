package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;


public interface DatabaseService {
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getMetas(String type, String tableName);

	/**
	 * 
	 * @param paraments
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> getPage(String type, Map<String,Object> paraments, int start, int pageSize); 
	
	/**
	 * Get all tables info.
	 * @return
	 */
	List<Map<String, Object>> getAllTables(String type);

	
	/**
	 * 
	 * @param type
	 * @param table
	 * @param isCreateTable
	 * @param isDropTableIfExist
	 * @param isCopyData
	 */
	void exportTable(String type, String table, String fields,  boolean isCreateTable, boolean isDropTableIfExist, boolean isCopyData);
	

}
