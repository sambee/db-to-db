package sam.bee.oa.sql.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

 
public class BaseDatabase extends Observable{
	private static Logger log = Logger.getLogger(BaseDatabase.class);
	private final static long timeout = 10000;
	private Connection conn = null;
	private String type;

	public BaseDatabase(){
		
	}
	
	public BaseDatabase(Connection conn, String type) throws SQLException{
		this.type = type;
		setConn(conn);
		Monitor mon = new Monitor(timeout);
		addObserver(mon);
		new Thread(mon).start();
	}
	
	public boolean update(String sql)throws SQLException {
		Statement stmt = conn.createStatement();
		//log.info(sql);
		boolean ret = stmt.execute(sql);		
		closeStatement(stmt);
		setChanged();
	    notifyObservers("UPDATE");
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
		notifyObservers("UPDATE");
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

class Monitor implements Runnable,Observer{
	private final static Logger log = Logger.getLogger(Monitor.class);

	private long timeout;
	private long lastupdated;


	public Monitor(long timeout) {
		this.timeout = timeout;	
		this.lastupdated = System.currentTimeMillis();	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof BaseDatabase && "UPDATE".equals(arg)){
			this.lastupdated = System.currentTimeMillis();	
		}
	}

	@Override
	public void run() {

		
		while (System.currentTimeMillis() - lastupdated < timeout) {

			try {
				Thread.sleep(5000);
				System.out.println("Will be exit applicaton:" + (timeout - (System.currentTimeMillis() - lastupdated)));
			} catch (InterruptedException e) {}
		}
		
		
	}
}