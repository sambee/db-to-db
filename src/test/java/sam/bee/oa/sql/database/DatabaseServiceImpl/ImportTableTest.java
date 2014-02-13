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
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseService;

public class ImportTableTest {

	@Test
	public void test() throws IOException, InterruptedException {
		doAction("mssql");
		doAction("jkams");
	}
	
	private void doAction(String dbName)throws IOException, InterruptedException{
		DatabaseService db = (DatabaseService) ServiceFactory
				.getService(DatabaseService.class);

		InputStream in = ClassLoader.getSystemResourceAsStream("gen_database.properties");
		Properties p = new Properties();
		p.load(in);
		
		
		//DELETE OLD FILE.
		InputStream in2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");;
		Properties p2 = new Properties();
		p2.load(in2);
		String jdbc = p2.getProperty("h2.jdbc.url");		
		String dbFilePath =jdbc.substring(jdbc.lastIndexOf("//")+2) + ".h2.db";
		File dbFile = new File(dbFilePath);
		if(dbFile.exists()){
			dbFile.delete();
		}
		
		Callback callback = new Callback() {

			@Override
			public boolean execute(Object obj) throws Exception {
				//System.out.println(obj);
				return true;
			}

		};

		String deployPath = p.getProperty("deploy.path");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy_MM_dd");

		String date = sdf.format(new Date());

		BufferedReader file = null;

		for (Object table : p.keySet()) {
			System.out.println("Import " + table);
			if (!String.valueOf(table).startsWith("deploy.")) {

				String filePath = deployPath + "\\" + date+ "\\" + dbName + "_h2@" + table + "@" + date + ".sql";

				file = new BufferedReader(new FileReader(new File(filePath)));
				String sql;
				while ((sql = file.readLine()) != null) {
					db.importTable("h2", sql, callback);
				}

			}
			if(file!=null)
				file.close();
		}
		
		//It need to wait for 5s that can rename the file name. 
		boolean isRename = false;
		String path = dbFile.getParent();
		String name = dbFile.getName();
		dbFile = new File(dbFilePath);
		String rename = path + "/" + date + dbName + "_" + name;
		
		int i=0;
		while(i++<10 && !isRename){
			File f = new File(rename);
			if(f.exists()){
				f.delete();
			}			
			Thread.sleep(5000);
			isRename = dbFile.renameTo(new File(rename));
			System.out.println("[RENAME]"+ rename + "="+ isRename);
		}
		org.junit.Assert.assertTrue("Rename the file="+rename , isRename);
	}
}
