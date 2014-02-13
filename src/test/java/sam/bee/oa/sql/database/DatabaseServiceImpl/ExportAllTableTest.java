package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.h2.jdbc.JdbcSQLException;
import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;

public class ExportAllTableTest {

	String dbName = "mssql";
	
	@Test
	public void test() throws Exception{
		
		String descType = "";
		
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy_MM_dd");
	
		final FileOutputStream file = new FileOutputStream("create_tables" + sdf.format(new Date()) + ".sql");
		
		BaseDatabase db = DatabaseFactory.getDatabase(descType);


		List<Map<String, Object>> names = service.getAllTables(dbName);
		
		
		Callback callback = new Callback(){

			@Override
			public boolean execute(Object obj) throws Exception {
				System.out.println(obj);
				file.write(String.valueOf(obj).getBytes());
				return true;
			}
			
		};		
		
		for(Map<String, Object> name : names){
			try{
			String tableName = (String)name.get("name");
			System.out.println(tableName);
			service.exportTable(dbName, "h2",tableName , "all", true, true, true, callback);
			}
			catch(JdbcSQLException ex){
				System.err.println(ex.getMessage());
			}
			catch(SQLException ex){
				System.err.println(ex.getMessage());
			}
		}
		
		file.close();
		
		
	}
}
