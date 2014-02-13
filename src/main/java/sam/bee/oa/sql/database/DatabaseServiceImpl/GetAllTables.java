package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllTables extends BaseService implements MethodExecutor{

	String dbName;
	
	public GetAllTables(String dbName){
		this.dbName = dbName;
	}
	
	@Override
	public Object execute(Map params) throws Throwable {
		return sql(dbName, "get_all_tables." + getDatabaseType(dbName) +".sql", params, getClass());
	}

	
	
}
