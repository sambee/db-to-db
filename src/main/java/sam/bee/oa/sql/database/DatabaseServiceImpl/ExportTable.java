package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.GeneralScriptService;
import sam.bee.oa.sql.database.model.PageModel;


public class ExportTable extends BaseService implements MethodExecutor{

	String dbName;
	String descType;
	boolean isCreateTable;
	boolean isDropTableIfExist;
	boolean isCopyData;
	String tableName;
	Callback callback;
	
	public ExportTable(
			String dbName, 
			String descType,
			String tableName, 
			String fields,  
			boolean isCreateTable, 
			boolean isDropTableIfExist, 
			boolean isCopyData,
			Callback callback
			
			){
		this.dbName = dbName;
		this.descType = descType;
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
			String sql = gen.dropTable(descType, tableName);
			if(callback!=null){
				callback.execute(sql);
			}
		}
		
		if(isCreateTable){		
			String sql = gen.createTable(descType, tableName, service.getMetas(dbName, tableName));
			if(callback!=null){
				callback.execute(sql);
			}
		}
		
		if(isCopyData){
			PageModel page = service.getPage(dbName, tableName, 0, Integer.MAX_VALUE);
			for(Map<String, Object> valuesMap :page.getList()){
				String sql = gen.createRecord(descType, tableName, valuesMap, params);
				if(callback!=null){
					callback.execute(sql);
				}
			}
			
		}
		return null;
	}
	
}
