package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;

import org.h2.mvstore.Page;

import sam.bee.oa.sql.database.model.PageModel;


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
	PageModel getPage(String type, Map<String,Object> paraments, int start, int pageSize); 
	
	/**
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
	 * @param type
	 * @param table
	 * @param isCreateTable
	 * @param isDropTableIfExist
	 * @param isCopyData
	 */
	void exportTable(String srcType, String descType, String table, String fields,  boolean isCreateTable, boolean isDropTableIfExist, boolean isCopyData) throws Exception;
	

}
