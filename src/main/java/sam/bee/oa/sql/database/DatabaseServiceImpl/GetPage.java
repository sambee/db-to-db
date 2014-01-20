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
	String type;
	
	public GetPage(String type, Map<String,Object> paraments, int start, int pageSize){
		this.paraments = paraments;
		this.start = start;
		this.pageSize = pageSize;
		this.type = type;
	}
	
	public GetPage(String type, String tableName, int start, int pageSize){
		this.paraments = new HashMap();
		paraments.put("tableName", tableName);
		this.start = start;
		this.pageSize = pageSize;
		this.type = type;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		paraments.put("pageStart", start);
		paraments.put("pageSize", pageSize);
		List<Map<String,Object>> count = sql("get_count." + type + ".sql", paraments, getClass());
		
		PageModel page = new PageModel();
		if(count.size()>0){
			page.setCount((Integer)count.get(0).get("ret"));
			List<Map<String,Object>> list = sql("get_data." + type + ".sql", paraments, getClass());
			
			page.setList(list);
		}
		
		return page;
	}
	
}
