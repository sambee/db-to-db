package sam.bee.oa.sql.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

 
public class BaseDatabase {
	private static Logger logger = Logger.getLogger(BaseDatabase.class);
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement ps =null;


	public BaseDatabase(){
		
	}
	
	public BaseDatabase(Connection conn) throws SQLException{
		setConn(conn);
	}
	
	public boolean update(String sql)throws SQLException {
		return stmt.execute(sql);
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

	public Connection setConn(Connection conn) throws SQLException {
		this.conn = conn;
	    stmt = conn.createStatement();
	    return conn;
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