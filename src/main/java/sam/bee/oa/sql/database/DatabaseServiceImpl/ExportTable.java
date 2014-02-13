package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.GeneralScriptService;
import sam.bee.oa.sql.database.model.PageModel;


public class ExportTable extends BaseService implements MethodExecutor{

	
	boolean isCreateTable;
	boolean isDropTableIfExist;
	boolean isCopyData;
	String tableName;
	Callback callback;
	String srcDBName;
	String outputType;
	
	public ExportTable(
			String srcDBName,
			String outputType,
			String tableName, 
			String fields,  
			boolean isCreateTable, 
			boolean isDropTableIfExist, 
			boolean isCopyData,
			Callback callback
			
			){
		this.srcDBName = srcDBName;
		this.outputType = outputType;
		this.tableName = tableName;
		this.isCreateTable = isCreateTable;
		this.isDropTableIfExist = isDropTableIfExist;
		this.isDropTableIfExist = isDropTableIfExist;
		this.isCopyData = isCopyData;
		this.callback = callback;
	}
	

	@Override
	public Object execute(Map params) throws Throwable {
		
	
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		GeneralScriptService gen = (GeneralScriptService)ServiceFactory.getService(GeneralScriptService.class);
		
		if(isDropTableIfExist){
			String sql = gen.dropTable(outputType, tableName);
			if(callback!=null){
				callback.execute(sql);
			}
		}
		
		if(isCreateTable){		
			String sql = gen.createTable(srcDBName, outputType, tableName, service.getMetas(srcDBName, tableName));
			if(callback!=null){
				callback.execute(sql);
				//log.info(sql);
			}
		}
		
		if(isCopyData){
			PageModel page = service.getPage(srcDBName,tableName, 0, Integer.MAX_VALUE);
			for(Map<String, Object> valuesMap :page.getList()){
				String sql = gen.createRecord(srcDBName, outputType,  tableName, valuesMap, params);
				if(callback!=null){
					callback.execute(sql);
				}
			}
			
		}
		return null;
	}
	
}
