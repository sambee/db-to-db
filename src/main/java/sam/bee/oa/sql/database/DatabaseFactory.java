package sam.bee.oa.sql.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import sam.bee.oa.sql.database.h2.H2Database;


public class DatabaseFactory {

	static Map<String, Monitor> dbs;	
	public static BaseDatabase getDatabase(String dbName) throws SQLException, IOException {

		if ("h2".equals(dbName)) {
			Monitor mon;
			final long timeout = 5000;
			
			if(dbs==null){
				dbs = new HashMap<String, Monitor>();
				
			}
			
			mon = dbs.get(dbName);
			if(mon==null){
				DatabaseConnection c = new DatabaseConnection(dbName);			
				H2Database db = new H2Database(c.getJDBC(), c.getUser(), c.getPassword(), c.getType());
				
				mon = new Monitor(dbName, db, timeout);
				System.out.println("create database connection:" +  c.getJDBC());
				dbs.put(dbName, mon);			
				new Thread(mon).start();
				
			}			
			return mon.getBaseData();
		}
		else{			
			DatabaseConnection db = new DatabaseConnection(dbName);
			return new BaseDatabase(db.getConnection(), db.getType());
		}

	}
}

class Monitor implements Runnable{
	BaseDatabase db;
	private long timeout;
	private long lastupdated;
	private String type;
	
	
	public Monitor(String type, BaseDatabase db, long timeout){
		this.db = db;
		this.type = type;
		this.timeout = timeout;
		update();
	}

	public BaseDatabase getBaseData(){
		return db;
	}
	
	public void update(){
		this.lastupdated = System.currentTimeMillis();
	}
	
	@Override
	public void run() {
		while(System.currentTimeMillis() - lastupdated < timeout){
			try {
				synchronized(db){
					if(db==null || db.getConn()==null || db.getConn().isClosed()){
						break;
					}
				}
					
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		synchronized(DatabaseFactory.dbs){
			DatabaseFactory.dbs.remove(type);
		}	
		db.closeCon();
		System.out.println( type + " closed");
	}
}
