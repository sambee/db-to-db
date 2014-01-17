package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseDatabase;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllTables extends BaseDatabase implements MethodExecutor{

	@Override
	public Object execute(Map params) throws Throwable {
		return sql("get_all_tables.sql", params);
	}

	
	
}
