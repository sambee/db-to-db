package sam.bee.oa.db;

import static sambee.utils.ConfigUtils.loadConfig;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
public class H2ToMssql implements IDatabaseAdapter{
	private static final Logger log = Logger.getLogger(H2ToMssql.class);
	

	
	public static void createTable(final String srcName, final String descName, final String outputType) throws Exception{
		final DatabaseService db = (DatabaseService) ServiceFactory.getService(srcName, DatabaseService.class);
		
		final DatabaseService service = (DatabaseService)ServiceFactory.getService(srcName, DatabaseService.class);

		List<Map<String, String>> tables = service.getAllTables();
		
		
		for(Map<String, String> m : tables){
			service.exportTable(srcName, outputType, (String)m.get("TABLE_NAME"), "all", true, true, false, new Callback(){

				@Override
				public boolean execute(Object obj) throws Throwable {
					log.info(obj);
					service.importTable(descName, (String)obj, null);
					return true;
				}
				
			});	
		}
		
		log.info("=========== Create Table ==========");
		
	}
	
	public static void importTable(final String srcName, final String descName,final String outputType) throws Exception{
		
		final DatabaseService db = (DatabaseService) ServiceFactory.getService(srcName, DatabaseService.class);
		
		final DatabaseService service = (DatabaseService)ServiceFactory.getService(srcName, DatabaseService.class);
		
		List<Map<String, String>> tables = service.getAllTables();
		
		
		for(Map<String, String> m : tables){
			service.exportTable(srcName, outputType, (String)m.get("TABLE_NAME"), "all", false, false, true, new Callback(){

				@Override
				public boolean execute(Object obj) throws Throwable {
					log.info(obj);
					service.importTable(descName, (String)obj, null);
					return true;
				}
				
			});	
		}
		log.info("=========== import data ==========");
		
		
		
	}

	@Override
	public Object parse(Object... params) throws Exception {
		String[] args = (String[])params;
		final String src = "src";
		final String desc = "desc";
		
		try{
		if(args.length==0){ 
			System.err.println("please enter to \njava -jar db-to-db-1.0-SNAPSHOT.jar sam.bee.oa.db.H2ToMssql h2_to_mssql.properties");
			return null;
		}
		PropertyConfigurator.configure("log4j.properties");		
		log.info("Load configuration file:" + args[0]);
		
//		FileInputStream fis = new FileInputStream(args[0]);
//		Properties prop = new Properties();
//		prop.load(fis);
		Map<String,String> config = loadConfig(args[0], getClass().getName());
		String descType = config.get("desc.jdbc.type");
		
		DatabaseFactory.getInstance().registerDatabase(src, new BaseDatabase(new DatabaseConnection(src, config)));
		DatabaseFactory.getInstance().registerDatabase(desc, new BaseDatabase(new DatabaseConnection(desc, config)));

		createTable(src, desc, descType);
		importTable(src, desc, descType);
		
		log.info("All done.");
		}
		finally{
			DatabaseFactory.getInstance().close(src);
			DatabaseFactory.getInstance().close(desc);
		}
		return null;
	}
}
