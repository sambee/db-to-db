package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.Map;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseFactory;

public class ImportTable extends BaseService implements MethodExecutor {

	
	String sql;	

	String dbName;
	Callback callback;
	public ImportTable(String dbName, String sql, Callback callback){
		this.dbName = dbName;
		this.sql = sql;	
		this.callback = callback;
	}
	

	@Override
	public Object execute(Map params) throws Throwable {
		BaseDatabase db = DatabaseFactory.getDatabase(dbName);
		db.update(sql);
		if(callback!=null){
			callback.execute(sql);
		}
		return null;
	}
	

}
