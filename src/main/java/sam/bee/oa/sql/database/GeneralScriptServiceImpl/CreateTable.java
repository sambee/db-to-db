package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.freemarker.BaseSql;
import sam.bee.oa.sql.freemarker.DefaultSql;
import sam.bee.oa.sql.freemarker.ParaseException;

@SuppressWarnings({ "rawtypes" })
public class CreateTable extends BaseService implements MethodExecutor {

	String talbeName;
	List<Map<String, Object>> metas;
	String type;
	public CreateTable(String type, String talbeName, List<Map<String, Object>> metas){
		this.type = type;
		this.talbeName = talbeName;
		this.metas = metas;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		Map<String, Object> myParams = new HashMap<String, Object>();
		
		String tableName = "system_users";
		List<Map<String, Object>> fields = service.getMetas(type, tableName);
		List list = new ArrayList();
		myParams.put("tableName", tableName);
		myParams.put("fields", fields);
		return new DefaultSql().convert("create_tables." + type + ".sql", myParams, list, getClass());
	};
}
