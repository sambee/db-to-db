package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.freemarker.DefaultSql;

@SuppressWarnings({ "rawtypes" })
public class CreateTable extends BaseService implements MethodExecutor {

	String expectedOutputType;
	String outputType;
	String tableName;

	List<Map<String, Object>> metas;
	
	public CreateTable(String expectedOutputType,String talbeName, List<Map<String, Object>> metas){
		this.expectedOutputType = expectedOutputType;
		this.outputType = outputType;
		this.tableName = talbeName;
		this.metas = metas;
	}

	@Override
	public Object execute(Map params) throws Throwable {		
		DatabaseService service = (DatabaseService)ServiceFactory.getService("", DatabaseService.class);

		BaseDatabase srcDB = DatabaseFactory.getInstance().getDatabase(dbName);
		Map<String, Object> myParams = new HashMap<String, Object>();
		List<Map<String, Object>> fields = service.getMetas(tableName);
		
		if("mssql".equals(outputType) && "h2".equals(srcDB.getType())){
			fields = new H2ToMssqlAdapter().paraseFields(fields);
		}
		myParams.put("tableName", tableName);
		myParams.put("fields", fields);
		return new DefaultSql().convert("create_tables." + outputType + ".sql", myParams, new ArrayList(), getClass());
	};
}
