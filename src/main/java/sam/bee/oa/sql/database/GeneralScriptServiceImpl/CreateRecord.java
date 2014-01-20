package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.freemarker.DefaultSql;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CreateRecord extends BaseService implements MethodExecutor {

	String type;
	String tableName;
	Map<String, Object> valuesMap;	
	Map<String, Object> params;
	
	public CreateRecord(String type, String talbeName, Map<String, Object> valuesMap, Map<String, Object> params){
		this.type = type;
		this.tableName = talbeName;
		this.valuesMap = valuesMap;
		this.params = params;
	}
	
	
	@Override
	public Object execute(Map params) throws Throwable {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list = new ArrayList<Object>();
		List<String> fields = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		List<String> types = new ArrayList<String>();
		
		for (String key : valuesMap.keySet()) {
			fields.add(key);
			Object obj = valuesMap.get(key);
			if(obj  instanceof String){
				types.add("string");
			}
			else if(obj  instanceof Date){
				types.add("datetime");
			}
			else if(obj  instanceof Integer || obj  instanceof Long ){
				types.add("number");
			}
			else {
				types.add("unknow");
			}
			
			values.add(valuesMap.get(key));
			
		}
		
		map.put("tableName", tableName);
		map.put("fields", fields);
		map.put("values", values);
		map.put("types", types);
		return new DefaultSql().convert("insert_record." + type + ".sql", map, list, getClass());
	}
	
	

}
