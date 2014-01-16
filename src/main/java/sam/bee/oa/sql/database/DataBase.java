package sam.bee.oa.sql.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

 
public class DataBase {
	private static Logger logger = Logger.getLogger(DataBase.class);
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement ps =null;

	public DataBase() throws SQLException {
		DatabaseConnection dcn = new DatabaseConnection(); 
		conn = dcn.getConnection();
	    stmt = conn.createStatement();
	}

	public synchronized ResultSet getResultSet(String sql) throws SQLException {
		return getResultSet(sql, null);
	}
			
	public synchronized ResultSet getResultSet(String sql, Object[] args) throws SQLException {
		if(args==null || args.length==0){
			stmt.executeQuery(sql);
		}
		return stmt.executeQuery(sql); 
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public synchronized void closeCon() {
		if (rs != null) {
			try {
				rs.close();			
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}