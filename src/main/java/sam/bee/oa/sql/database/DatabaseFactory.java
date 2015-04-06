package sam.bee.oa.sql.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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
public class DatabaseFactory {

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

		return db;
	}
	
	public void registerDatabase(String dbName, BaseDatabase db ){	
		getDBS().put(dbName, db);		
		
	}

    public void registerDatabase(String dbName, Map config ) throws SQLException, ClassNotFoundException {
        getDBS().put(dbName, new BaseDatabase(new DatabaseConfig(dbName, config)));

    }

	private Map<String, BaseDatabase> getDBS(){
		if (dbs == null) {
			dbs = new HashMap<String, BaseDatabase>();
		}
		return dbs;
	}


	

}


