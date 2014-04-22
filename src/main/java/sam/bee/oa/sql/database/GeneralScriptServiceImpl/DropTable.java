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
	
	String outputType;
	String tableName;
	
	public DropTable(String outputType, String tableName){
		this.outputType = outputType;
		this.tableName = tableName;
		
	}

	@Override
	public Object execute(Map params) throws Throwable {
		isValidate(outputType, tableName);
		Map<String, Object> myParams = new HashMap<String, Object>();
		myParams.put("tableName", tableName);
		List list = new ArrayList();
		return new DefaultSql().convert("drop_table." + outputType + ".sql", myParams, list, getClass());
	}
	
	private void isValidate(String outputType, String tableName){
		if(outputType == null || outputType.length()==0){
			throw new NullPointerException("tableName");
		}
		if(tableName == null || tableName.length()==0){
			throw new NullPointerException("tableName");
		}
		
	}
	 

	
}
