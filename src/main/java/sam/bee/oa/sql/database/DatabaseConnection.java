package sam.bee.oa.sql.database;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Date;

import org.apache.log4j.Logger;


public class DatabaseConnection {
	
		private static Logger logger = Logger.getLogger(DatabaseConnection.class);
		 
		public Connection getConnection() {
			try {
				InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
				Properties pro = new Properties();
				pro.load(in);
				in.close();
				Class.forName(pro.getProperty("src.jdbc.class"));
				Connection conn = DriverManager.getConnection(new String(pro.getProperty("src.jdbc.url").getBytes("ISO-8859-1"),"gbk"), pro.getProperty("src.jdbc.user"), pro.getProperty("src.jdbc.password"));
				return conn;
			} catch (Exception e) {
				logger.error(e);
			}
			return null;
		}
		
	
}