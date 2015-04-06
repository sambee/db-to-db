package sam.bee.oa.sql.database;

import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public interface Callback {

	boolean execute(Map<String, Object> aData) throws Throwable;
}
