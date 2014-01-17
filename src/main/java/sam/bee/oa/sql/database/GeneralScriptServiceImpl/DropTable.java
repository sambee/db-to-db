package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.freemarker.DefaultSql;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DropTable extends BaseService implements MethodExecutor{
	
	String type;
	String tableName;
	
	public DropTable(String type, String tableName){
		this.type = type;
		this.tableName = tableName;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		
		Map<String, Object> myParams = new HashMap<String, Object>();
		myParams.put("tableName", tableName);
		List list = new ArrayList();
		return new DefaultSql().convert("drop_tables." + type + ".sql", myParams, list, getClass());
	}
	
	 

	
}
