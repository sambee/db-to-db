package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.h2.jdbc.JdbcSQLException;
import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;

public class GenernalCreateAllTableSqlTest {
	

	
	@Test
	public void test() throws Exception{

		String outputType = "mssql";
		String dbName = "amskf722";
		
		InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
		Properties p = new Properties();
		p.load(new BufferedReader(new InputStreamReader(in, "UTF-8")));		
		String date =  new java.text.SimpleDateFormat("yyyy_MM_dd").format(new Date());
		String deployPath = p.getProperty("deploy.path") + "/" + outputType + "\\" + date;
		
		File path = new File(deployPath);
		if(!path.exists()){
			path.mkdirs();
		}
		final FileOutputStream file = new FileOutputStream(deployPath + "create_tables.sql");
		generalCreateTablesSqlSript(file, dbName, outputType);
		System.out.println("Create File at:"+ deployPath);
		
	}
	
	public void generalCreateTablesSqlSript(final OutputStream out, String dbName, String type) throws Exception{

		BaseDatabase db = DatabaseFactory.getInstance().getDatabase(dbName);

		
		DatabaseService service = (DatabaseService)ServiceFactory.getService("",DatabaseService.class);


		List<Map<String, String>> names = service.getAllTables();
		
		
		Callback callback = new Callback(){

			@Override
			public boolean execute(Map<String, Object> aData) throws Exception {
				System.out.println(aData);
				out.write(String.valueOf(aData).getBytes());
				return true;
			}
			
		};		
		
//		for(Map<String, String> name : names){
//			try{
//			String tableName = (String)name.get("name");
//			System.out.println(tableName);
//			service.exportTable(dbName, type,	tableName , "all", true, true, false, callback);
//			}
//			catch(JdbcSQLException ex){
//				System.err.println(ex.getMessage());
//			}
//			catch(SQLException ex){
//				System.err.println(ex.getMessage());
//			}
//		}
		
		out.close();
	}
}
