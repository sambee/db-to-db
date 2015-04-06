package sam.bee.oa.sql.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

 
public class BaseDatabase extends Observable{
	private static Logger log = Logger.getLogger(BaseDatabase.class);
	private final static long TIMEOUT = 30000;
	private DatabaseConnection conn = null;
	private  Thread monThread;
	public BaseDatabase(DatabaseConnection conn) throws SQLException, ClassNotFoundException{		
		setConn(conn);
		Monitor mon = new Monitor(TIMEOUT);
		monThread = new Thread(mon);
		monThread.start();
		addObserver(mon);
		
	}
	
	public boolean update(String sql)throws SQLException, ClassNotFoundException {
		Statement stmt = getConn().createStatement();
		//log.info(sql);
		boolean ret = stmt.execute(sql);		
		closeStatement(stmt);
		setChanged();
	    notifyObservers("UPDATE");
		return ret;
	}

	public synchronized ResultSet getResultSet(String sql) throws SQLException, ClassNotFoundException {
		setChanged();
	    notifyObservers();
		return getResultSet(sql, null);
	}
			
	public synchronized ResultSet getResultSet(String sql, Object[] args) throws SQLException, ClassNotFoundException {
		Statement stmt = getConn().createStatement();
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
	
	public Connection getConn() throws ClassNotFoundException, SQLException {
		return conn.getConnection();
	}

	public void setConn(DatabaseConnection conn)  {
		this.conn = conn;	    
	}
	
	public synchronized void closeCon(){
		try {
		if (conn != null && !conn.isClosed()){			
				conn.close();
				conn = null;
				if(monThread!=null){
					monThread.interrupt();
					monThread =null;
				}
				System.out.println("Database closed.");
				
		}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public String getType(){
       return conn.getType();
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
				//System.out.println("Will be exit applicaton:" + (timeout - (System.currentTimeMillis() - lastupdated)));
			} catch (InterruptedException e) {}
		}
		
		
	}
}