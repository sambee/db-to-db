package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;
import static java.lang.System.out;
import static org.junit.Assert.*;

public class ExportTableTest {

	@Test
	public void test() throws Exception{
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
//		List names = service.getAllTables();
		service.exportTable("mssql","h2", "system_users", "all", true, true, true);
		
	}
}
