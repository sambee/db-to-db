package sam.bee.oa.sql.database;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sam.bee.oa.sql.freemarker.BaseSql;
import sam.bee.oa.sql.freemarker.ParaseException;
import sam.bee.oa.sql.utils.JdbcConverter;



public abstract class BaseService {


	private BaseDatabase db = null;
	
	class SQLEntity{
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
	
	public BaseDatabase getDB() throws SQLException, IOException{
		if(db==null){
			db = DatabaseFactory.getDatabase("mssql");
		}
		return db;
	}
    
	public BaseDatabase set(BaseDatabase db){
		if(db==null){
			this.db = db;
			
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
    public List<Map<String,Object>> sql(String templateName, Map params, Class owner) throws Exception{
    	SQLEntity ety =getSqlEntity(templateName, params, owner);
    	System.out.println(ety.sql);
    	ResultSet rs = getDB().getResultSet(ety.sql, ety.params.toArray(new Object[ety.params.size()]));
    	ArrayList<Map<String,Object>> list =null;
    	try{
    		list = JdbcConverter.resultSetToList(rs);
    	}
    	finally{
    		closeRs(rs);
    		
    	}
    	return list;
    }
  
    	
    	
}
