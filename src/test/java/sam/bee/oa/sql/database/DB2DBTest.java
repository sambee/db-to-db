package sam.bee.oa.sql.database;

import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DB2DBTest {

	public void test(){
		DatabaseService service = (DatabaseService)DatabaseFactory.getService(DatabaseService.class);
		
		List names = service.getAllTables();
		
	}
}
