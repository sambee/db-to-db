package sam.bee.oa.db;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class H2ToMssql {
	private static final Logger log = Logger.getLogger(H2ToMssql.class);
	
	public static void main(String[] args) throws Exception {
		if(args.length==0){
			System.err.println("please enter to \njava -jar db-to-db-1.0-SNAPSHOT.jar sam.bee.oa.db.H2ToMssql");
		}
		log.info("Load configuration file:" + args[0]);
		
		FileInputStream fis = new FileInputStream(args[0]);
		Properties prop = new Properties();
		prop.load(fis);
		createTable("src");
		
	}
	
	public static void createTable(String srcName){
		
	}
}
