package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;


public interface DatabaseService {
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getMetas(String tableName);

	/**
	 * 
	 * @param paraments
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> getPage(Map<String,Object> paraments, int start, int pageSize); 
	
	/**
	 * 
	 * @return
	 */
	List<Map<String, Object>> getAllTables();
	
	
}
