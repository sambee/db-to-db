package sam.bee.oa.sql.core;

import java.util.Map;

public interface DatabaseMethod {

	Object execute(Map<Object,Object> param);
}
