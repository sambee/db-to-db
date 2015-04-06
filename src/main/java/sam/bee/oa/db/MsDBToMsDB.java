package sam.bee.oa.db;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import static sambee.utils.ConfigUtils.*;

public class MsDBToMsDB implements IDatabaseAdapter{

	private static final Logger log = Logger.getLogger(MsDBToMsDB.class);
	

	
	private static void doAction(String srcName, final String descDB, String descDBType, final Map<String,String> config )throws Exception{

		final DatabaseService db = (DatabaseService) ServiceFactory.getService("", DatabaseService.class);
		
		DatabaseService service = (DatabaseService)ServiceFactory.getService("", DatabaseService.class);
		
		Callback callback = new Callback() {

			@Override
			public boolean execute(Object sql) throws Exception {
				log.info(sql);
				db.importTable(descDB, (String)sql, null);
				return true;
			}

		};
	
		
		
		for (Object tableKey : config.keySet()) {
			if (String.valueOf(tableKey).startsWith("table.")) {
				String table = String.valueOf(tableKey).substring("table.".length());
				String actions = config.get(tableKey);
				boolean copyData = actions.contains("data");	
				service.exportTable(srcName, descDBType, (String)table, "all", true, true, copyData, callback);
			}
			
		}
		
		log.info("---------------- DONE ---------------");
		System.exit(0);

	}



	@Override
	public Object parse(Object... params) throws Exception {
		String[] args = (String[])params;
		
		PropertyConfigurator.configure("log4j.properties");		
		//load configuration 1.
		//InputStream in = ClassLoader.getSystemResourceAsStream("35_To_178.properties");
//		FileInputStream fis = new FileInputStream("35_To_178.properties");
//		Properties prop = new Properties();
//		prop.load(fis);
		
		Map<String,String> config = loadConfig(args[0], getClass().getName());
		String src = "src";
		String desc = "desc";
		
		DatabaseConnection srcDB = new DatabaseConnection(src, config);
		DatabaseFactory.getInstance().registerDatabase(src, new BaseDatabase(new DatabaseConnection(src, config)));
		
		DatabaseConnection descDB = new DatabaseConnection(desc, config);
		DatabaseFactory.getInstance().registerDatabase(desc, new BaseDatabase(new DatabaseConnection(desc, config)));
	
		
		doAction(src, desc, descDB.getType(),  config);
		return null;
	}

}
