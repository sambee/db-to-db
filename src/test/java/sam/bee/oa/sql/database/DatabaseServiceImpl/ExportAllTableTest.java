package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.List;
import java.util.Map;

import org.h2.jdbc.JdbcSQLException;
import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;

public class ExportAllTableTest {

	@Test
	public void test() throws Exception{
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		List<Map<String, Object>> names = service.getAllTables("mssql");
		for(Map<String, Object> name : names){
			try{
			String tableName = (String)name.get("name");
			System.out.println(tableName);
			service.exportTable("mssql","h2",tableName , "all", true, true, true);
			}
			catch(JdbcSQLException ex){
				System.err.println(ex.getMessage());
			}
		}
		
		
	}
}
