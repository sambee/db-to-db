package sam.bee.oa.sql.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 *
 * DatabaseConnection.java
 *
 * @author Sam Wong
 *  QQ: 1557299538
 * @create: 2014年2月13日  
 * 
 * Modification
 * -------------------------------------------
 */
public class DatabaseConnection {
	
		private static Logger logger = Logger.getLogger(DatabaseConnection.class);
		Properties prop;
		String dbName;
		
		public DatabaseConnection(String dbName) throws IOException{
			InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
			prop = new Properties();
			prop.load(in);
			in.close();
			this.dbName = dbName;
		}
		
		public Connection getConnection() {
			try {
			
				Class.forName(prop.getProperty(dbName + ".jdbc.class"));
				Connection conn = DriverManager.getConnection(getJDBC(), getUser(), getPassword());
				return conn;
			} catch (Exception e) {
				logger.error("",e);
			}
			return null;
		}
		
	
	 public String getProperty(String key){
		 return prop.getProperty(key);
	 }
	 
	 public String getJDBC(){
		 return prop.getProperty(dbName + ".jdbc.url");
	 }
	 
	 public String getUser(){
		 return prop.getProperty(dbName + ".jdbc.user");
	 }
	 
	 public String getPassword(){
		 return prop.getProperty(dbName + ".jdbc.password");
	 }
	 
	 public String getType(){
		 return prop.getProperty(dbName + ".jdbc.type");
	 }
	 
}