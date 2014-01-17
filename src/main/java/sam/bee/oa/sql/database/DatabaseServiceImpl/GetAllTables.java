package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllTables extends BaseService implements MethodExecutor{

	String type;
	public GetAllTables(String type){
		this.type = type;
	}
	
	@Override
	public Object execute(Map params) throws Throwable {
		return sql("get_all_tables." + type +".sql", params);
	}

	
	
}
