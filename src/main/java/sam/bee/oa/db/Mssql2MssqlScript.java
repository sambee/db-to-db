package sam.bee.oa.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;


public class Mssql2MssqlScript {

	private final static String SRC_DTATABASE = "src"; 

	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		File create_all_tables = new File("allTables.sql");
		Mssql2MssqlScript o = new Mssql2MssqlScript();
		o.initConfig();
		List<String> tables = o.getAllTables(SRC_DTATABASE);
		o.generalAllCreateTableSript(new FileOutputStream(create_all_tables), tables);
		o.exrportData();
		o.compress();
	}
	
	private void initConfig() throws IOException, SQLException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("mssql_to_access.properties");
		Properties prop = new Properties();
		prop.load(fis);	
		DatabaseFactory.getInstance().registerDatabase(SRC_DTATABASE, new BaseDatabase(new DatabaseConnection(SRC_DTATABASE, prop)));
	}

	private List<String> getAllTables(String dbName){
		final List<String> tables = new Vector<String>();
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		List<Map<String, Object>> list = service.getAllTables(dbName);
		
		for(Map<String, Object> m : list){
			tables.add((String)m.get("name"));
		}
	
		java.util.Collections.sort(tables);
		return tables;
	}
	
	private void generalAllCreateTableSript(OutputStream out, List<String> tables){
		for(String tableName : tables){
			
		}
	}
	
	private void exrportData(){
		
	}
	
	private void compress(){
		
	}
}
