package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.GeneralScriptService;
import sam.bee.oa.sql.database.model.PageModel;


public class ExportTable extends BaseService implements MethodExecutor{

	String srcType;
	String descType;
	boolean isCreateTable;
	boolean isDropTableIfExist;
	boolean isCopyData;
	String tableName;
		
	public ExportTable(
			String type, 
			String descType,
			String tableName, 
			String fields,  
			boolean isCreateTable, 
			boolean isDropTableIfExist, 
			boolean isCopyData
			
			){
		this.srcType = type;
		this.tableName = tableName;
		this.isCreateTable = isCreateTable;
		this.isDropTableIfExist = isDropTableIfExist;
		this.isDropTableIfExist = isDropTableIfExist;
		this.isCopyData = isCopyData;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		
		BaseDatabase db = DatabaseFactory.getDatabase(descType);
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		GeneralScriptService gen = (GeneralScriptService)ServiceFactory.getService(GeneralScriptService.class);
		
		if(isDropTableIfExist){
			String sql = gen.dropTable(descType, tableName);
			//System.out.println(sql);
			db.update(sql);
		}
		
		if(isCreateTable){		
			String sql = gen.createTable(descType, tableName, service.getMetas(srcType, tableName));
			//System.out.println(sql);
			db.update(sql);	
		}
		
		if(isCopyData){
			PageModel page = service.getPage(srcType, "system_components", 0, Integer.MAX_VALUE);
			for(Map<String, Object> valuesMap :page.getList()){
				String sql = gen.createRecord(descType, tableName, valuesMap, params);
				System.out.println(sql);
			}
			
		}
		
		db.closeCon();
		return null;
	}
	
}
