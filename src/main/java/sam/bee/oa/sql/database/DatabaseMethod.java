package sam.bee.oa.sql.database;

import java.util.Map;

public interface DatabaseMethod {

	Object execute(Map<Object,Object> param);
}
