package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.DatabaseFactory;
import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.freemarker.BaseSql;
import sam.bee.oa.sql.freemarker.DefaultSql;
import sam.bee.oa.sql.freemarker.ParaseException;

@SuppressWarnings({ "rawtypes" })
public class CreateTable extends BaseDatabase implements MethodExecutor {

	String talbeName;
	List<Map<String, Object>> metas;
	
	public CreateTable(String talbeName, List<Map<String, Object>> metas){
		this.talbeName = talbeName;
		this.metas = metas;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		
		DatabaseService service = (DatabaseService)DatabaseFactory.getService(DatabaseService.class);
		
		Map<String, Object> myParams = new HashMap<String, Object>();
		
		String tableName = "system_users";
		List<Map<String, Object>> fields = service.getMetas(tableName);
		List list = new ArrayList();
		myParams.put("tableName", tableName);
		myParams.put("fields", fields);
		return new DefaultSql().convert("create_tables.sql", myParams, list, getClass());
	};
}
