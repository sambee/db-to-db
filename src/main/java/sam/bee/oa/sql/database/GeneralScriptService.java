package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;

public interface GeneralScriptService {
	
	String dropTable(String dbName, String tableName);
	
	String createTable(String dbName, String outputType, String tableName, List<Map<String, Object>> metas);
	
	String createRecord(String dbName, String type, String talbeName, Map<String, Object> valuesMap, Map<String, Object> params);

	

}
