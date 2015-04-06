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
import java.util.*;

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
	
	private void doAction(String dbName, String descDBName, String date)throws IOException, InterruptedException, SQLException, ClassNotFoundException{
	

	     InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
	     BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	     Properties p = new Properties();
	     p.load(br);
	     
		 String deployPath = p.getProperty("deploy.path");


        Map map = new HashMap();
        Enumeration e =  p.propertyNames();
       while(e.hasMoreElements()){
           String name = (String) e.nextElement();
            map.put(name, p.getProperty(name));
       }
		 //init jdbc
		 DatabaseConnection descConn = new DatabaseConnection(descDBName, map);
	     
		 String deployFile = deployPath + "/"+ descConn.getType() + "/" +  dbName + "_" + date;   
		 String jdbc = getJdbcHeader() + deployFile; 
		
	

		 if(descConn.getType().equals("h2")){
			 H2Database db = new H2Database(descConn);
			 DatabaseFactory.getInstance().registerDatabase(descDBName, db); 
			 
			File dbFile = new File(deployFile);
			if(dbFile.exists()){
				dbFile.delete();
			}
		 }
		 else{
			 BaseDatabase db = new BaseDatabase(descConn);
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
				.getService("",DatabaseService.class);
		
		for (Object table : p.keySet()) {
			System.out.println("Import " + table);
			if (!String.valueOf(table).startsWith("deploy.")) {

				String sqlFile = deployPath + "/" + descConn.getType() +"/" + date+ "/" +dbName +  "_" + descConn.getType()  + "_" + table + "@" + date + ".sql";
				file = new BufferedReader(new FileReader(new File(sqlFile)));
				String sql;
				while ((sql = file.readLine()) != null) {
					service.importTable(descConn.getDBName(), sql, callback);
				}

			}
			if(file!=null)
				file.close();
		}

	}
}
