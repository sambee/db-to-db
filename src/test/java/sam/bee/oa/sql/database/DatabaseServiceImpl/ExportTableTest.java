package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.Callback;
import static java.lang.System.out;
import static org.junit.Assert.*;

public class ExportTableTest {

	BufferedWriter file;
	
	@Test
	public void test() throws Exception{		
		//doAction("mssql");
		doAction("gkams");		
	}
	
	private void doAction(String dbName)throws Exception{
		
//		 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	     InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
	     Properties p = new Properties();
	     p.load(in);
	       
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy_MM_dd");
		
		String deployPath = p.getProperty("deploy.path");
		

		Callback callback = new Callback(){

			@Override
			public boolean execute(Object obj) throws Exception {
				System.out.println(obj);
				file.write(String.valueOf(obj).replaceAll("\n|\r", ""));				
				file.write("\r\n");
				return true;
			}
			
		};
		
		String date = sdf.format(new Date());
		
		String path = deployPath + "\\" + date;
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();	
		}
		
		//chao yang
		for(Object table : p.keySet()){			
			if(!String.valueOf(table).startsWith("deploy.")){
				

				file  = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(deployPath + "\\"  + date +  "\\" +dbName + "_h2@" +  table + "@" + date + ".sql"), "UTF-8"));				
				String actions = p.getProperty((String)table);
				boolean copyData = actions.contains("data");				
				service.exportTable(dbName,"h2", (String)table, "all", true, true, copyData, callback);
			}
			if(file!=null){
				file.close();
			}		
		}		
		
	
		
		System.out.println("[DONE]" + path);
	}
	
}
