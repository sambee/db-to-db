package sam.bee.oa.sql.database;

import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes"})
public interface GeneralScriptService {
	String createTable(String tableName, List<Map<String, Object>> metas);
}