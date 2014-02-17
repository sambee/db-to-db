package sam.bee.oa.sql.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import sam.bee.oa.sql.database.h2.H2Database;

/**
 * 
 * 
 * DatabaseFactory.java
 * 
 * @author Sam Wong QQ: 1557299538
 * @create: 2014年2月13日
 * 
 *          Modification -------------------------------------------
 */
public class DatabaseFactory{

	static Map<String, Monitor> dbs;
	private final static Logger log = Logger.getLogger(DatabaseFactory.class);

	public static BaseDatabase getDatabase(String dbName) throws SQLException,
			IOException {

		if (dbName == null) {
			throw new NullPointerException("Database name is null point");
		}
		DatabaseConnection c = new DatabaseConnection(dbName);

		Monitor mon;
		final long timeout = 300000;

		if (dbs == null) {
			dbs = new HashMap<String, Monitor>();
		}

		mon = dbs.get(dbName);
		if (mon == null) {

			BaseDatabase db;
			if ("h2".equals(c.getType())) {
				log.info("create h2 database connection:" + c.getJDBC());
				db = new H2Database(c.getJDBC(), c.getUser(), c.getPassword(),c.getType());
				mon = new Monitor(dbName, db, timeout);
				db.addObserver(mon);
				dbs.put(dbName, mon);
				new Thread(mon).start();
			} else if ("mssql".equals(c.getType())) {
				log.info("create mssql database connection:" + c.getJDBC());
				db = new BaseDatabase(c.getConnection(), c.getType());
				mon = new Monitor(dbName, db, timeout);
				db.addObserver(mon);
				dbs.put(dbName, mon);
				new Thread(mon).start();
			} else {
				throw new SQLException("Can not get the data base name"
						+ dbName);
			}
		}
		return mon.getBaseData();
	}


}

class Monitor implements Runnable,Observer{
	private final static Logger log = Logger.getLogger(Monitor.class);
	BaseDatabase db;
	private long timeout;
	private long lastupdated;
	private String type;

	public Monitor(String type, BaseDatabase db, long timeout) {
		this.db = db;
		this.type = type;
		this.timeout = timeout;	
		this.lastupdated = System.currentTimeMillis();	
	}

	public BaseDatabase getBaseData() {
		return db;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.lastupdated = System.currentTimeMillis();	
		
	}

	@Override
	public void run() {

		try {
			while (System.currentTimeMillis() - lastupdated < timeout) {				
				synchronized (db) {
					if (db == null || db.getConn() == null || db.getConn().isClosed()) {
						break;
					}
					// db.getResultSet("select 1");
				}

				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		synchronized (DatabaseFactory.dbs) {
			DatabaseFactory.dbs.remove(type);
			log.info(type + " timeout " + timeout + "ms");
		}
		db.closeCon();
		log.info(type + " closed");
	}
}
