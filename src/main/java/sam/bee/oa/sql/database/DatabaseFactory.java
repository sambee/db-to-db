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
public class DatabaseFactory implements Observer{

	private Map<String, BaseDatabase> dbs;
	private final static Logger log = Logger.getLogger(DatabaseFactory.class);
	
	private static DatabaseFactory factory = new DatabaseFactory();
	
	private DatabaseFactory(){}
	public static DatabaseFactory getInstance(){
		return factory;
	}
	
	public BaseDatabase getDatabase(String dbName) throws SQLException,
			IOException {

		if (dbName == null) {
			throw new NullPointerException("Database name is null point");
		}
			
		BaseDatabase db = getDBS().get(dbName);
		if (db == null) {
			throw new SQLException(String.format("please regsiter data base %s first.", dbName));
		}

//			if(c.getType() == null){
//				throw new SQLException("Can not get the database type:"
//						+ dbName);
//			}
//			else if ("h2".equals(c.getType())) {
//				log.info("create h2 database connection:" + c.getJDBC());
//				db = new H2Database(c.getJDBC(), c.getUser(), c.getPassword(),c.getType());
//				db.addObserver(this);
//				registerDatabase(dbName, db);
//			
//			
//			} else if ("mssql".equals(c.getType())) {
//				log.info("create mssql database connection:" + c.getJDBC());
//				db = new BaseDatabase(c.getConnection(), c.getType());
//				registerDatabase(dbName, db);
//			
//			} else {
//				throw new SQLException("Can not get the data base name:"
//						+ dbName);
//			}
//		}
		return db;
	}
	
	public void registerDatabase(String dbName, BaseDatabase db ){	
		getDBS().put(dbName, db);		
		
	}

    public void registerDatabase(String dbName, Map config ) throws SQLException, ClassNotFoundException {
        getDBS().put(dbName, new BaseDatabase(new DatabaseConnection(dbName, config)));

    }

	private Map<String, BaseDatabase> getDBS(){
		if (dbs == null) {
			dbs = new HashMap<String, BaseDatabase>();
		}
		return dbs;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof BaseDatabase && "CLOSE".equals(arg)){
			if(dbs !=null){
				for (String dbName : dbs.keySet()){
					BaseDatabase db = dbs.get(dbName);
					if(db  == o){
						dbs.remove(o);
						System.err.println("Closed database is " + dbName);
					}
				}
			}
		}
		
	}
	

	public void close(String dbName){
		BaseDatabase db = dbs.get(dbName);
		if(db!=null){
			db.closeCon();
			dbs.remove(dbName);
		}
	}
}


