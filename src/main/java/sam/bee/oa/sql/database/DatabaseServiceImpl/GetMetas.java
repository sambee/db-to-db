package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

@SuppressWarnings({"unchecked","rawtypes"})
public class GetMetas extends BaseService implements MethodExecutor {

	String tableName;
	public GetMetas(String tableName){
		this.tableName = tableName;
	}

	
	@Override
	public Object execute(Map params) throws Throwable{

		BaseService.SQLEntity entry =  getSqlEntity("get_metas." + getDatabaseType() + ".sql", new HashMap() {{
			put("tableName", tableName);
		}}, getClass());

		return getDB(dbName).getMetas(tableName, entry.sql);
	}











}
