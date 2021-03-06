package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.model.PageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class GetPage extends BaseService implements MethodExecutor {

	Map<String,Object> paraments;
	long start; 
	long pageSize;

	private static final int MAX_ROW = 1000;
	public GetPage(Map<String,Object> paraments, long start, long pageSize){
		this.paraments = paraments;
		this.start = start;
		this.pageSize = pageSize;		
	}
	
	@SuppressWarnings("unchecked")
	public GetPage(String tableName, long start, long pageSize){
		this.paraments = new HashMap();
		paraments.put("tableName", tableName);
		this.start = start;
		this.pageSize = pageSize;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		paraments.put("pageStart", start);

		String sql = "get_count." + getDatabaseType() + ".sql";
		SQLEntity entity = getSqlEntity(sql, paraments, getClass());

		List<Map<String,Object>> countObjs =  sql(dbName, sql, paraments, getClass());

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
			log.info("Data Count " + count);

			List<Map<String,Object>>  list;
			if(count>0){
				list = sql(dbName, "get_data." + getDatabaseType() + ".sql", paraments, getClass());
			}
			else{
				list =  new ArrayList<Map<String, Object>>();
			}

			
			page.setStart(start);
			page.setList(list);
			page.setCount(count);
			page.setPageSize(pageSize);
		}
		
		return page;
	}
	
}
