package sam.bee.oa.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import static sambee.utils.ConfigUtils.*;
public class MssqlScript2Mssql implements IDatabaseAdapter{

	private static final Logger log = Logger.getLogger(MssqlScript2Mssql.class);
	
	
	

	private static void importFiles(String dbName, Set<File> files) throws IOException{
		for(File f : files){
			if("allTables.sql".equals(f.getName()))
			{
				
				importFile(dbName,f);
				f.delete();
				break;
			}			
		}
		for(File f : files){
				importFile(dbName,f);
				f.delete();
		}
	}
	
	private static void importFile(String dbName, File file) throws IOException{
		
		final DatabaseService db = (DatabaseService) ServiceFactory.getService("",DatabaseService.class);
		
		final DatabaseService service = (DatabaseService)ServiceFactory.getService("",DatabaseService.class);
		
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
		String line = "";
		  while ((line = bufReader.readLine()) != null) {  
			  service.importTable(dbName, line, null);
			
		}
		
		bufReader.close();
	}
	
	public static Set<File> uncompress(final File zipFile) throws IOException
	{
		 Set<File> files = new HashSet<File>();
		SevenZFile sevenZFile = new SevenZFile(zipFile);
		 SevenZArchiveEntry entry = sevenZFile.getNextEntry();
		    while(entry!=null){
		        System.out.println(entry.getName());
		        FileOutputStream out = new FileOutputStream(entry.getName());
		        byte[] content = new byte[(int) entry.getSize()];
		        sevenZFile.read(content, 0, content.length);
		        out.write(content);
		        out.close();
		        files.add(new File(entry.getName()));
		        entry = sevenZFile.getNextEntry();
		    }
		    sevenZFile.close();
		    
		 return files;
	}

	@Override
	public Object parse(Object... params) throws Exception {
		String[] args = (String[])params;
		
		final String src = "src.file";
		final String desc = "desc";
		
		try{
		if(args.length==0){
			System.err.println("please enter to \njava -jar db-to-db-1.0-SNAPSHOT.jar sam.bee.oa.db.MssqlScript2Mssql mssqlscript_to_mssql.properties");
			return null;
		}
		PropertyConfigurator.configure("log4j.properties");		
		log.info("Load configuration file:" + args[0]);
		
		Properties prop = new Properties();

		Map<String,String> config = loadConfig(args[0], getClass().getName());
	
		DatabaseFactory.getInstance().registerDatabase(desc, new BaseDatabase(new DatabaseConnection(desc, config)));
		
		File zipFile = new File(prop.getProperty(src));
		Set<File> files = uncompress(zipFile);
		importFiles(desc, files);
		log.info("All done.");
		}
		finally{
			try{
			DatabaseFactory.getInstance().close(desc);
			}catch(Exception ex){}
			
		}
		return null;
	}

}
