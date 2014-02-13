package sam.bee.oa.sql.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

import org.apache.log4j.Logger;

 
public class BaseDatabase extends Observable{
	private static Logger logger = Logger.getLogger(BaseDatabase.class);
	
	private Connection conn = null;
	private String type;

	public BaseDatabase(){
		
	}
	
	public BaseDatabase(Connection conn, String type) throws SQLException{
		this.type = type;
		setConn(conn);
	}
	
	public boolean update(String sql)throws SQLException {
		Statement stmt = conn.createStatement();
		boolean ret = stmt.execute(sql);		
		closeStatement(stmt);
		setChanged();
	    notifyObservers();
		return ret;
	}

	public synchronized ResultSet getResultSet(String sql) throws SQLException {
		setChanged();
	    notifyObservers();
		return getResultSet(sql, null);
	}
			
	public synchronized ResultSet getResultSet(String sql, Object[] args) throws SQLException {
		Statement stmt = conn.createStatement();
		if(args==null || args.length==0){
			stmt.executeQuery(sql);
		}
		setChanged();
	    notifyObservers();
		return stmt.executeQuery(sql); 
	}

	public void closeStatement(Statement stmt) throws SQLException{
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConn() {
		return conn;
	}

	public Connection setConn(Connection conn)  {
		this.conn = conn;	    
	    return conn;
	}
	
	public synchronized void closeCon(){
		try {
		if (conn != null && !conn.isClosed()){			
				conn.close();
				conn = null;
		}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
}