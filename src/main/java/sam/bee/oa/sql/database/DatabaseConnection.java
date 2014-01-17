package sam.bee.oa.sql.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;


public class DatabaseConnection {
	
		private static Logger logger = Logger.getLogger(DatabaseConnection.class);
		Properties prop;
		String type;
		public DatabaseConnection(String type) throws IOException{
			InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
			prop = new Properties();
			prop.load(in);
			in.close();
			this.type = type;
		}
		
		public Connection getConnection() {
			try {
				
				Class.forName(prop.getProperty(type + ".jdbc.class"));
				Connection conn = DriverManager.getConnection(getJDBC(), getUser(), getPassword());
				return conn;
			} catch (Exception e) {
				logger.error(e);
			}
			return null;
		}
		
	
	 public String getProperty(String key){
		 return prop.getProperty(key);
	 }
	 
	 public String getJDBC(){
		 return prop.getProperty(type + ".jdbc.url");
	 }
	 
	 public String getUser(){
		 return prop.getProperty(type + ".jdbc.user");
	 }
	 
	 public String getPassword(){
		 return prop.getProperty(type + ".jdbc.password");
	 }
	 
}