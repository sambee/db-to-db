package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.freemarker.DefaultSql;

@SuppressWarnings({ "rawtypes" })
public class CreateTable extends BaseService implements MethodExecutor {

	String tableName;
	String outputType;
	String srcDbName;
	
	List<Map<String, Object>> metas;
	
	public CreateTable(String srcDbName, String outputType,String talbeName, List<Map<String, Object>> metas){
		this.srcDbName = srcDbName;
		this.outputType = outputType;
		this.tableName = talbeName;
		this.metas = metas;
	}

	@Override
	public Object execute(Map params) throws Throwable {		
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		Map<String, Object> myParams = new HashMap<String, Object>();
		List<Map<String, Object>> fields = service.getMetas(srcDbName, tableName);
		myParams.put("tableName", tableName);
		myParams.put("fields", fields);
		return new DefaultSql().convert("create_tables." + outputType + ".sql", myParams, new ArrayList(), getClass());
	};
}
