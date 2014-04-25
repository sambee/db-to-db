package sam.bee.oa.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.healthmarketscience.jackcess.Table;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.asscss.AsscssDatabase;
import sam.bee.oa.sql.database.model.PageModel;

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
	
	
	private static List<Map<String, Object>> getSrcInfo(AsscssDatabase accessDB, DatabaseService db, String srcDB, String tableName){
		return 	db.getMetas(srcDB, tableName);
	}
	
	public static void importData(AsscssDatabase accessDB,DatabaseService service, String srcDBName, String tableName)throws Exception{
		
		long start = 0;
		long pageSize =Integer.MAX_VALUE;
		PageModel page;
		
		do{
			page = service.getPage(srcDBName,tableName, start, pageSize);	
			start = page.getStart()+page.getPageSize();
			pageSize = page.getPageSize(); 
			System.out.println(String.format("Table: %s, Start:%d, size:%d, count:%d, data count:%d", tableName,start, pageSize, page.getCount(), page.getList().size()));
			
			List<Map<String, Object>> data = page.getList();
			for(Map<String, Object> d : data){
				accessDB.addRowFromMap(tableName, d);
			}
			
		}while(start<page.getCount());
	}
	
	private static void msdbToAsscesDB(DatabaseService service, Properties prop) throws Exception{
		
		
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
		DatabaseFactory.getInstance().registerDatabase(src, new BaseDatabase(srcDB));
		
		//create access file.
		AsscssDatabase accessDB = new AsscssDatabase(dbFile);

		for (Object tableKey : prop.keySet()) {
			if (String.valueOf(tableKey).startsWith("table.")) {
				String tableName = String.valueOf(tableKey).substring("table.".length());
				 List<Map<String, Object>> fieldsInfo = getSrcInfo(accessDB, service, src, tableName);
				 accessDB.createAccessTable(tableName, fieldsInfo);
				 importData(accessDB, service, src, tableName);
			}
		}
		
		accessDB.close();
	}

}
