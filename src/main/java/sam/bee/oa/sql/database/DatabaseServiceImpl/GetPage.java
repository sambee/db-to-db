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
	long start; 
	long pageSize;
	String dbName;
	private static final int MAX_ROW = 1000;
	public GetPage(String dbName, Map<String,Object> paraments, long start, long pageSize){
		this.dbName = dbName;
		this.paraments = paraments;
		this.start = start;
		this.pageSize = pageSize;		
	}
	
	@SuppressWarnings("unchecked")
	public GetPage(String dbName, String tableName, long start, long pageSize){
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
			Object ret = countObjs.get(0).get("RET");
			Long count = 0L;
			if(ret instanceof Integer){
				count = Long.valueOf((Integer)ret);
			}
			if(ret instanceof Long){
				count = Long.valueOf((Long)ret);
			}
			
			
			if(pageSize>MAX_ROW){
				pageSize = MAX_ROW;
				paraments.put("pageSize", pageSize);
			}
			else if(pageSize>count){
				paraments.put("pageSize", count);
			}
			else{
				paraments.put("pageSize", pageSize);
			}
			page.setCount(count);

			List<Map<String,Object>> list = sql(dbName, "get_data." + getDatabaseType(dbName) + ".sql", paraments, getClass());
			
			page.setStart(start);
			page.setList(list);
			page.setCount(count);
			page.setPageSize(pageSize);
		}
		
		return page;
	}
	
}
