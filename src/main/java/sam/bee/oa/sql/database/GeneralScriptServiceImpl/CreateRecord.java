package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.math.BigDecimal;
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
	String dbName; 
	String type;
	String tableName;
	Map<String, Object> valuesMap;	
	Map<String, Object> params;
	
	public CreateRecord(String dbName, String type, String talbeName, Map<String, Object> valuesMap, Map<String, Object> params){
		this.dbName = dbName;
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
				String objStr = (String)obj;
				obj = objStr.replaceAll("'", "''");
				types.add("string");
			}
			else if(obj  instanceof Date){
				types.add("datetime");
			}
			else if(obj  instanceof Integer || obj  instanceof Long || obj  instanceof Float || obj  instanceof Double || obj instanceof BigDecimal ){
				types.add("number");
			}
			else {
				if(obj!=null){
					throw new GeneralException("Parse object type error" + obj.getClass());
				}
				types.add("unknow");
			}
			
			values.add(obj);
			
		}
		
		map.put("tableName", tableName);
		map.put("fields", fields);
		map.put("values", values);
		map.put("types", types);
		return new DefaultSql().convert("insert_record." + type + ".sql", map, list, getClass());
	}
	
	

}
