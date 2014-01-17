package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;

public interface GeneralScriptService {
	
	String dropTable(String type, String tableName);
	
	String createTable(String type, String tableName, List<Map<String, Object>> metas);
	
}
