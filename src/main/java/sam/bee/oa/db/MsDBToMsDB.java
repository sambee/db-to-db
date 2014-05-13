package sam.bee.oa.db;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;


public class MsDBToMsDB {

	private static final Logger log = Logger.getLogger(MsDBToMsDB.class);
	
	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");		
		//load configuration 1.
		//InputStream in = ClassLoader.getSystemResourceAsStream("35_To_178.properties");
		FileInputStream fis = new FileInputStream("35_To_178.properties");
		Properties prop = new Properties();
		prop.load(fis);
		
		String src = "src";
		String desc = "desc";
		
		DatabaseConnection srcDB = new DatabaseConnection(src, prop);
		DatabaseFactory.getInstance().registerDatabase(src, new BaseDatabase(new DatabaseConnection(src, prop)));
		
		DatabaseConnection descDB = new DatabaseConnection(desc, prop);
		DatabaseFactory.getInstance().registerDatabase(desc, new BaseDatabase(new DatabaseConnection(desc, prop)));
	
		
		doAction(src, desc, descDB.getType(),  prop);

	}
	
	private static void doAction(String srcName, final String descDB, String descDBType, final Properties prop )throws Exception{

		final DatabaseService db = (DatabaseService) ServiceFactory.getService(DatabaseService.class);
		
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		Callback callback = new Callback() {

			@Override
			public boolean execute(Object sql) throws Exception {
				log.info(sql);
				db.importTable(descDB, (String)sql, null);
				return true;
			}

		};
	
		
		
		for (Object tableKey : prop.keySet()) {
			if (String.valueOf(tableKey).startsWith("table.")) {
				String table = String.valueOf(tableKey).substring("table.".length());
				String actions = prop.getProperty((String)tableKey);
				boolean copyData = actions.contains("data");	
				service.exportTable(srcName, descDBType, (String)table, "all", true, true, copyData, callback);
			}
			
		}
		
		log.info("---------------- DONE ---------------");
		System.exit(0);

	}

}
