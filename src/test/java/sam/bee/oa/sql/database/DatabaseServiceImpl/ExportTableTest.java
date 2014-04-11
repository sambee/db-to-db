package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.Callback;
import static java.lang.System.out;
import static org.junit.Assert.*;

public class ExportTableTest {

	BufferedWriter file;
	String dbName = "mssql";
	private final static Logger log = Logger.getLogger(ExportTableTest.class);
	
	@Test
	public void gkams0220Tomegkams0220Test() throws Exception{		
		doAction("meamskf20140313", "mssql");
	}

//	@Test
//	public void amskf722Tomeamskf722Test() throws Exception{		
//		doAction("amskf722","h2");
//	}
//	
//	@Test
//	public void gkams0220Tomegkams0220_Mssql_Test() throws Exception{		
//		doAction("gkams0220", "mssql");
//		
//	}
//
//	@Test
//	public void amskf722Tomeamskf722_Mssql_Test() throws Exception{		
//		doAction("amskf722","mssql");
//	}
	
	private void doAction(String dbName, String outputType)throws Exception{
		
//		 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	     InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
	     Properties p = new Properties();
	     p.load(new BufferedReader(new InputStreamReader(in, "UTF-8")));
	       
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy_MM_dd");
		

		Callback callback = new Callback(){

			@Override
			public boolean execute(Object obj) throws Exception {
				//System.out.println(obj);
				file.write(String.valueOf(obj).replaceAll("\n|\r", ""));				
				file.write("\r\n");				
				return true;
			}
			
		};
		
		String date = sdf.format(new Date());
		
		String deployPath = p.getProperty("deploy.path") + "/" + outputType +"/" + "\\" + date;
		File f = new File(deployPath);
		if(!f.exists()){
			f.mkdirs();	
		}
		
		//chao yang
		for(Object table : p.keySet()){			
			if(!String.valueOf(table).startsWith("deploy.")){				

				file  = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(deployPath +  "\\" +dbName +  "_" + outputType  + "_" + table + "@" + date + ".sql"), "UTF-8"));				
				String actions = p.getProperty((String)table);
				boolean copyData = actions.contains("data");				
				service.exportTable(dbName, outputType, (String)table, "all", true, true, copyData, callback);
			}
			if(file!=null){
				file.close();
			}		
		}
		
		log.info("[DONE]" + deployPath);
	}
	
	
	
	
}
