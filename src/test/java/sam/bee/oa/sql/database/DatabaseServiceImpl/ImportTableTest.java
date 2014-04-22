package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.h2.H2Database;

public class ImportTableTest {

	
	@Test
	public void gkams0220Tomegkams0220Test() throws Exception{	
		
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
	     String date = sdf.format(new Date());
	     
		doAction("gkams0220","mejkams20140307", date);
		
	}

//	@Test
//	public void amskf722Tomeamskf722Test() throws Exception{	
//	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
//	     String date = sdf.format(new Date());
//		doAction("amskf722", "h2", date);
//	}
	
	private String getJdbcHeader(){		
		return "jdbc:h2:";
	}
	
	private void doAction(String dbName, String descDBName, String date)throws IOException, InterruptedException, SQLException{
	

	     InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
	     BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	     Properties p = new Properties();
	     p.load(br);
	     
		 String deployPath = p.getProperty("deploy.path");

	     
	     
		 //init jdbc
		 DatabaseConnection c = new DatabaseConnection(descDBName);
	     
		 String deployFile = deployPath + "/"+ c.getType() + "/" +  dbName + "_" + date;   
		 String jdbc = getJdbcHeader() + deployFile; 
		
	

		 if(c.getType().equals("h2")){
			 H2Database db = new H2Database(jdbc, c.getUser(), c.getPassword(),c.getType());
			 DatabaseFactory.getInstance().registerDatabase(descDBName, db); 
			 
			File dbFile = new File(deployFile);
			if(dbFile.exists()){
				dbFile.delete();
			}
		 }
		 else{
			 BaseDatabase db = new BaseDatabase(c.getConnection(),c.getType());
			 DatabaseFactory.getInstance().registerDatabase(descDBName, db);  
		 }
		 
		
		Callback callback = new Callback() {
			@Override
			public boolean execute(Object obj) throws Exception {
				return true;
			}

		};

		BufferedReader file = null;
		
		DatabaseService service = (DatabaseService) ServiceFactory
				.getService(DatabaseService.class);
		
		for (Object table : p.keySet()) {
			System.out.println("Import " + table);
			if (!String.valueOf(table).startsWith("deploy.")) {

				String sqlFile = deployPath + "/" + c.getType() +"/" + date+ "/" +dbName +  "_" + c.getType()  + "_" + table + "@" + date + ".sql";
				file = new BufferedReader(new FileReader(new File(sqlFile)));
				String sql;
				while ((sql = file.readLine()) != null) {
					service.importTable(c.getDBName(), sql, callback);
				}

			}
			if(file!=null)
				file.close();
		}

	}
}
