package sam.bee.oa.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;


public class Mssql2MssqlScript {

	private final static String SRC_DTATABASE = "src"; 
	private final static String DESC_DTATABASE = "desc"; 
	private final static String DESC_DTATABASE_2 = "desc2"; 
	private final static String PROPERTIES_FILE = "mssql_to_mssql.properties";
	
	private final static String ALL_TABLES = "allTables.sql";
	private final static String LOG4J_PROPERTIES = "log4j.properties";
	private final static boolean CONTINUE_IF_ERROR = true;
	private final static String ZIP_FILE = "_all.7z";
	private static final Logger log = Logger.getLogger(Mssql2MssqlScript.class);
	
	public static void main(String[] args) throws Exception {
		
		File outFile = new File(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ZIP_FILE);
		
		PropertyConfigurator.configure(LOG4J_PROPERTIES);		
		Mssql2MssqlScript o = new Mssql2MssqlScript();
		o.initConfigurate(PROPERTIES_FILE);		
	
		o.generalAllCreateTableSript(ALL_TABLES, CONTINUE_IF_ERROR);		
		List<File> sqlFiles = o.exrportData(PROPERTIES_FILE, CONTINUE_IF_ERROR);
		sqlFiles.add(new File(ALL_TABLES));
		
		o.compress(sqlFiles, outFile);
		o.exrportTables(outFile);
		o.exrportData(outFile);
		
		
		log.info("----  Exit ------");
		System.exit(0);
	}
	
	private void exrportTables(File zipFile) throws IOException, SQLException, ClassNotFoundException{
		SevenZFile sevenZFile = new SevenZFile(zipFile);
		SevenZArchiveEntry entry;
		StringBuilder st = new StringBuilder();
		BaseDatabase database = DatabaseFactory.getInstance().getDatabase(DESC_DTATABASE_2);
		
		while((entry = sevenZFile.getNextEntry()) !=null){
			if(ALL_TABLES.equals(entry.getName())){
				byte[] content = new byte[(int) entry.getSize()];
		        sevenZFile.read(content, 0, content.length);
				st.append(new String(content));
			};
		}
		String[] sql = st.toString().split("\n");
		for(String s : sql){
			log.info("[IMPORT]" + s.replaceAll("(\r|\n)", ""));
			database.update(s);
			
		}
		
	}
	
	@SuppressWarnings("resource")
	private void exrportData(File zipFile)throws IOException, SQLException, ClassNotFoundException{
		SevenZFile sevenZFile = new SevenZFile(zipFile);
		SevenZArchiveEntry entry;
		
		BaseDatabase database = DatabaseFactory.getInstance().getDatabase(DESC_DTATABASE_2);
		
		while((entry = sevenZFile.getNextEntry()) !=null){
			if(!ALL_TABLES.equals(entry.getName())){
				
				byte[] content = new byte[1024];
				
				FileOutputStream out = new FileOutputStream(entry.getName());
		        int len;        
				while((len = sevenZFile.read(content, 0, content.length))!=-1){				
			        out.write(content, 0, len);  
				}
				out.close();  
				
				File file = new File(entry.getName());
				BufferedReader br = new  BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				
				String sql;
				
				while((sql = br.readLine())!=null){
					String s =sql.replaceAll("(\r|\n)", "");
					log.info("[IMPORT]" + s);
					database.update(s);
					
				}
				br.close();
				if(file.exists()){
					file.deleteOnExit();
				}
		
			};
		}
	
	}
	
	private void initConfigurate(String file) throws IOException, SQLException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);	
		DatabaseFactory.getInstance().registerDatabase(SRC_DTATABASE, new BaseDatabase(new DatabaseConnection(SRC_DTATABASE, prop)));
		DatabaseFactory.getInstance().registerDatabase(DESC_DTATABASE, new BaseDatabase(new DatabaseConnection(DESC_DTATABASE, prop)));
		DatabaseFactory.getInstance().registerDatabase(DESC_DTATABASE_2, new BaseDatabase(new DatabaseConnection(DESC_DTATABASE_2, prop)));
	}

	private List<String> getAllTables(String dbName){
		final List<String> tables = new Vector<String>();
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		List<Map<String, Object>> list = service.getAllTables(dbName);
		
		for(Map<String, Object> m : list){
			tables.add((String)m.get("name"));
		}
	
		java.util.Collections.sort(tables);
		return tables;
	}
	
	private void generalAllCreateTableSript(final String outfile, final boolean willConinueIfError) throws Exception{
		final OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outfile), "UTF-8");
		List<String> tables = getAllTables(SRC_DTATABASE);
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		for(String tableName : tables){
			try{
			service.exportTable(SRC_DTATABASE, "mssql", tableName, "all", true, true, false, getCallback(out));
			}
			catch(Exception ex){
				log.error("",ex);
			}
		}
		out.close();		
	}
	
	private List<File> exrportData(String p, final boolean willConinueIfError) throws Exception{
		
		List<File> files = new ArrayList<File>();
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
		Properties prop = new Properties();
		prop.load(fis);
		
		String descDBType = (String)prop.get(DESC_DTATABASE + ".jdbc.type");
		
		for (Object tableKey : prop.keySet()) {
			
			//System.out.println("Import " + table);
			if (String.valueOf(tableKey).startsWith("table.")) {
				String table = String.valueOf(tableKey).substring("table.".length());
				String actions = prop.getProperty((String)tableKey);
				boolean copyData = actions.contains("data");	
				File outFile = new File(table + ".sql");
				files.add(outFile);
				OutputStreamWriter wr = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
				service.exportTable(SRC_DTATABASE, descDBType, (String)table, "all", false, false, copyData, getCallback(wr));
				wr.close();
			}
			
		}
		return files;
	}
	
	private Callback getCallback(final OutputStreamWriter out){
		return new Callback() {

					@Override
					public boolean execute(Object obj) throws Throwable {
						log.info(obj);
						String s =String.valueOf(obj).replaceAll("(\r|\n)", "");
						out.write(String.valueOf(s) + "\n");
						return true;
					}
					
			};
	}
	
	/**
	 * {@link: http://stackoverflow.com/questions/21897286/how-to-extract-files-from-a-7-zip-stream-in-java-without-store-it-on-hard-disk}
	 * {@link: http://commons.apache.org/proper/commons-compress/examples.html}
	 * @param sourceFiles
	 * @param outFileName
	 * @return
	 * @throws IOException
	 */
	public boolean compress(final List<File> sourceFiles, final File outFile) throws IOException
	{
		SevenZOutputFile sevenZOutput = new SevenZOutputFile(outFile);
		for(File fileToArchive : sourceFiles){			
			SevenZArchiveEntry entry = sevenZOutput.createArchiveEntry(fileToArchive, fileToArchive.getName());
			sevenZOutput.putArchiveEntry(entry);
			
			FileInputStream in = new FileInputStream(fileToArchive);
			byte[] buf = new byte[1024];    
			int len;
			while((len=in.read(buf))!=-1){
				sevenZOutput.write(buf, 0, len);				
			}		
			in.close();
			sevenZOutput.closeArchiveEntry();
			if(fileToArchive.exists()){
				fileToArchive.delete();
			}
		}
		sevenZOutput.close();
        return true;
	}
}
