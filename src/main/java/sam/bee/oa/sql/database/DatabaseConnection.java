package sam.bee.oa.sql.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
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
	String driverClass;
	Connection conn;


	public DatabaseConnection(String type, String driverClass, String jdbc,
			String userName, String password, String dbName) {
		this.dbName = dbName;
		this.jdbc = jdbc;
		this.userName = userName;
		this.password = password;
	}

	public DatabaseConnection(String dbName, Map<String,String> config) {
		this.dbName = dbName;
		this.password = config.get( "password");
		this.userName = config.get( "user");
		this.jdbc = config.get( "jdbc");
		this.driverClass = config.get( "driver");

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

	public String getDBName() {
		return this.dbName;
	}
	
	public boolean isClosed() throws SQLException{
		return conn!=null && conn.isClosed();
				
	}
	
	public void close() throws SQLException{
		if(conn!=null){
			conn.close();
			conn = null;	
		}
	}

    public String getType(){
        if(driverClass==null){
            return null;
        }
        if(driverClass.contains("h2")){
            return "h2";
        }
        if(driverClass.contains("miracle")){
            return "miracle";
        }
        if(driverClass.contains("oracle")){
            return "oracle";
        }
        if(driverClass.contains("mysql")){
            return "mysql";
        }
        return null;
    }
}