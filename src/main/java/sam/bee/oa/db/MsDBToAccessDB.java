package sam.bee.oa.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.asscss.AsscssDatabase;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

public class MsDBToAccessDB {
	private static final Logger log = Logger.getLogger(MsDBToAccessDB.class);
	
	public static void main(String[] args) throws Exception {
		final DatabaseService service = (DatabaseService) ServiceFactory.getService(DatabaseService.class);
		
		try{
		FileInputStream fis = new FileInputStream("mssql_to_access.properties");
		Properties prop = new Properties();
		prop.load(fis);
		
		msdbToAsscesDB(service, prop);
//		validate(service, prop);
		}
		catch(Exception e){
			log.error("",e);
		}
		System.exit(0);
	}
//	
//	private static void validate(DatabaseService service, Properties prop) throws IOException{
//		String src = "src";
//		String desc = "desc";
//		
//		String url = (String)prop.get(desc + ".jdbc.url");
//		String mdb = url.substring(url.lastIndexOf("/")+1);
//		File dbFile = new File(mdb);
//		assert(dbFile.exists());
//		Database db = new DatabaseBuilder(dbFile).setFileFormat(Database.FileFormat.V2000).open();
//		
//		Table table = db.getTable("AMS_SYS_PRINT_TEMPLATE");
//		for (Row row : table) {
//			String data = (String)row.get("REPORT_TEMPLATE");
//			if(data!=null){
//				System.out.println(data.length());
//			}
//			
//		}
//	}
	
	
	private static void msdbToAsscesDB(DatabaseService service, Properties prop) throws Exception{
		
		AsscssDatabase access = new AsscssDatabase();
		String src = "src";
		String desc = "desc";
		
		String url = (String)prop.get(desc + ".jdbc.url");
		String mdb = url.substring(url.lastIndexOf("/")+1);
		File dbFile = new File(mdb);
		if(dbFile.exists()){
			boolean isDone = dbFile.delete();
			if(!isDone){
				log.error("Delete file failure:" + mdb );
				return ;
			}
			else{
				log.info("Deleted File:" + mdb );
			}
			
		}
		
		//Register src database.
		DatabaseConnection srcDB = new DatabaseConnection(src, prop);
		DatabaseFactory.getInstance().registerDatabase(src, new BaseDatabase(srcDB.getConnection(), srcDB.getType()));
		
		//create access file.
		File accessFile = new File(System.currentTimeMillis() + ".mdb");
		Database accessDB = DatabaseBuilder.create(Database.FileFormat.V2000,dbFile);

		for (Object tableKey : prop.keySet()) {
			if (String.valueOf(tableKey).startsWith("table.")) {
				String tableName = String.valueOf(tableKey).substring("table.".length());
				 List<Map<String, Object>> fieldsInfo = access.getSrcInfo(service, src, tableName);
				 access.createAccessTable(accessDB, tableName, fieldsInfo);
				 access.importData(accessDB, service, src, tableName);
			}
		}
		
		accessDB.close();
	}

}
