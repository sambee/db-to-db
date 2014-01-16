package sam.bee.oa.sql.database;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface MethodExecutor {
	
	Object execute(Map params) throws Throwable;
}
