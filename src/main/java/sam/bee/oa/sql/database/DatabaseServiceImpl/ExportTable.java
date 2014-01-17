package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.GeneralScriptService;


public class ExportTable extends BaseService implements MethodExecutor{

	String type;
	boolean isCreateTable;
	boolean isDropTableIfExist;
	boolean isCopyData;
	String tableName;
		
	public ExportTable(String type, String tableName, String fields,  boolean isCreateTable, boolean isDropTableIfExist, boolean isCopyData){
		this.type = type;
		this.tableName = tableName;
		this.isCreateTable = isCreateTable;
		this.isDropTableIfExist = isDropTableIfExist;
		this.isDropTableIfExist = isDropTableIfExist;
		this.isCopyData = isCopyData;
	}

	@Override
	public Object execute(Map params) throws Throwable {
		
		BaseDatabase db = DatabaseFactory.getDatabase("h2");
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		GeneralScriptService gen = (GeneralScriptService)ServiceFactory.getService(GeneralScriptService.class);
		if(isCreateTable){			
			String sql = gen.createTable(tableName, service.getMetas(tableName));
			db.update(sql);	
		}
		
		db.closeCon();
		return null;
	}
	
}
