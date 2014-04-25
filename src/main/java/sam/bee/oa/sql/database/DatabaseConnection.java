package sam.bee.oa.sql.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	
	String dbName;
	String jdbc;
	String userName;
	String password;
	String type;
	String driverClass;
	Connection conn;
	public DatabaseConnection(String type, String driverClass, String jdbc,
			String userName, String password, String dbName) {
		this.dbName = dbName;
		this.jdbc = jdbc;
		this.userName = userName;
		this.password = password;
		this.type = type;
	}

	public DatabaseConnection(String dbName, Properties p) {
		this.dbName = dbName;
		this.password = p.getProperty(dbName + ".jdbc.password");
		this.userName = p.getProperty(dbName + ".jdbc.user");
		this.jdbc = p.getProperty(dbName + ".jdbc.url");
		this.type = p.getProperty(dbName + ".jdbc.type");
		this.driverClass = p.getProperty(dbName + ".jdbc.class");

	}

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {		
		if(conn==null){
			Class.forName(driverClass);
			conn = DriverManager.getConnection(getJDBC(), getUserName(), getPassword());
		}
		return conn;
	}

	 
	public String getJDBC() {
		return jdbc;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this. type = type;
	}
	
	public String getDBName() {
		return this.dbName;
	}
	
	public boolean isClosed() throws SQLException{
		return conn!=null && conn.isClosed();
				
	}
	
	public void close() throws SQLException{
		conn.close();
		conn = null;
	}
	 
}