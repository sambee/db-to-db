package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.List;

import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;


public class ExportTableTest {

	@Test
	public void test(){
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
//		List names = service.getAllTables();
		service.exportTable("h2", "system_users", "all", true, true, true);
		
	}
}
