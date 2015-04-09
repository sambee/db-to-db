package sam.bee.oa.sql.database;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.freemarker.BaseSql;
import sam.bee.oa.sql.freemarker.ParaseException;
import sam.bee.oa.sql.utils.JdbcConverter;



public abstract class BaseService<T> {

	protected final static Logger log = Logger.getLogger(BaseService.class);
    protected String dbName;

    public void setDatabaseName(String dbName){
        this.dbName = dbName;
    }

	public DatabaseService getDatabaseService(){
		return ServiceFactory.getService(dbName, DatabaseService.class);
	}

	public GeneralScriptService getGeneralScriptService(){
		return ServiceFactory.getService(dbName, GeneralScriptService.class);
	}

	protected class SQLEntity{
		public String sql;
		public List<Object> params;
	} 
	
	class Sql extends BaseSql {

		public String convert(String sql, Map<String, Object> paramenters, List<Object> list, Class owner) throws ParaseException {
			Map<String, Object> root = new HashMap<String, Object>();
			root.putAll(paramenters);
			root.put("$in", new sam.bee.oa.sql.freemarker.In(list));
			root.put("$", new sam.bee.oa.sql.freemarker.$(list));
			return sql(sql, root, owner);
		}

	}
	
	public BaseDatabase getDB(String dbName) throws SQLException, IOException{
        BaseDatabase db = DatabaseFactory.getInstance().getDatabase(dbName);
		if(db==null){
			db = DatabaseFactory.getInstance().getDatabase(dbName);
		}
		if(db==null){
			throw new NullPointerException("Can not found the data base configuation:"+ dbName);
		}
		return db;
	}
    
    protected SQLEntity getSqlEntity(String template, Map params, Class owner) throws ParaseException{
    	SQLEntity ety = new SQLEntity();  
    	LinkedList<Object> list =new LinkedList<Object>();
    	ety.sql = new Sql().convert(template, params, list, owner);
    	ety.params = list;
    	return ety;
    }
    
    private void closeRs(ResultSet rs){
    	if(rs!=null){
    	try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	}
    }
    
    /**
     * 
     * @param templateName
     * @param params
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> sql(String dbName, String templateName, Map params, Class owner) throws Exception{
    	SQLEntity ety =getSqlEntity(templateName, params, owner);
    	//log.info(ety.sql);
		Object[] args = ety.params.toArray(new Object[ety.params.size()]);
    	return getDB(dbName).getList(ety.sql, args);

    }
  
    	
    protected String getDatabaseType() throws Exception{
    	if(getDB(dbName).getType() == null){
    		throw new NullPointerException("Can not get the db type:" + dbName);
    	}
    	return getDB(dbName).getType();
    	
    }

}
