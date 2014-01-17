package sam.bee.oa.sql.core;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface MethodExecutor {
	
	Object execute(Map params) throws Throwable;
}
