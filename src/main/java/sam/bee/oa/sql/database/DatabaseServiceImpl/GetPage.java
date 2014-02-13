package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.model.PageModel;

@SuppressWarnings("rawtypes")
public class GetPage extends BaseService implements MethodExecutor {

	Map<String,Object> paraments;
	int start; 
	int pageSize;
	String dbName;
	
	public GetPage(String dbName, Map<String,Object> paraments, int start, int pageSize){
		this.dbName = dbName;
		this.paraments = paraments;
		this.start = start;
		this.pageSize = pageSize;		
	}
	
	@SuppressWarnings("unchecked")
	public GetPage(String dbName, String tableName, int start, int pageSize){
		this.dbName = dbName;
		this.paraments = new HashMap();
		paraments.put("tableName", tableName);
		this.start = start;
		this.pageSize = pageSize;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		paraments.put("pageStart", start);
		
		List<Map<String,Object>> countObjs = sql(dbName, "get_count." + getDatabaseType(dbName) + ".sql", paraments, getClass());

		PageModel page = new PageModel();
		if(countObjs.size()>0){
			Integer count = Integer.valueOf((Integer)countObjs.get(0).get("ret"));
			if(pageSize>count){
				paraments.put("pageSize", count);
			}
			else{
				paraments.put("pageSize", pageSize);
			}
			page.setCount(count);
			List<Map<String,Object>> list = sql(dbName, "get_data." + getDatabaseType(dbName) + ".sql", paraments, getClass());
			
			page.setList(list);
		}
		
		return page;
	}
	
}
