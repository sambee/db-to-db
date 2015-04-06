package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllTables extends BaseService implements MethodExecutor{

	void validate() throws Exception{
		if(StringUtils.isEmpty(dbName)){
			throw new ValidateException("database is empty or null.");
		}
		if(StringUtils.isEmpty(getDatabaseType(dbName))){
			throw new ValidateException("database type is empty or null.");
		}
	}
	@Override
	public Object execute(Map params) throws Throwable {
		validate();
		return sql(dbName, "get_all_tables." + getDatabaseType(dbName) +".sql", params, getClass());
	}

	
	
}
