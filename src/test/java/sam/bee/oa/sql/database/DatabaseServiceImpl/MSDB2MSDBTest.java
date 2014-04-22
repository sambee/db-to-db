package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseService;



public class MSDB2MSDBTest {
	
	private static final Logger log = Logger.getLogger(MSDB2MSDBTest.class);
	@Test
	public void gkams0220Tomegkams0220Test() throws Exception{		
		doAction("ams178", "amskf411");
		
	}
//
//	@Test
//	public void amskf722Tomeamskf722Test() throws Exception{		
//		doAction("amskf722", "ams178");
//	}
//	
//	@Test
//	public void amskf722Tomeamskf722Test() throws Exception{		
//		doAction("jkams", "mejk0320");
//	}
	
	private void doAction(String srcName, final String descDB)throws Exception{

		final DatabaseService db = (DatabaseService) ServiceFactory.getService(DatabaseService.class);
		
		//load configuration 1.
		InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
		Properties p = new Properties();
		p.load(in);
		
		//load configuration 2.
		InputStream in2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");;
		Properties p2 = new Properties();
		p2.load(in2);
		
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		Callback callback = new Callback() {

			@Override
			public boolean execute(Object sql) throws Exception {
				log.info(sql);
				db.importTable(descDB, (String)sql, null);
				return true;
			}

		};
	
		String descDBType =  p2.getProperty(srcName + ".jdbc.type");
		
		for (Object table : p.keySet()) {
			//System.out.println("Import " + table);
			if (!String.valueOf(table).startsWith("deploy.")) {
				
				String actions = p.getProperty((String)table);
				boolean copyData = actions.contains("data");	
				service.exportTable(srcName, descDBType, (String)table, "all", true, true, copyData, callback);
			}
			
		}		

		log.info("------------------- DONE ------------------------------");
	}
}
