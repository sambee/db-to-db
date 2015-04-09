package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.model.PageModel;

import java.sql.*;
import java.util.*;
import java.util.Date;

@SuppressWarnings("rawtypes")
public class GetAllData extends BaseService<DatabaseService> implements MethodExecutor {

	String tableName;
	Callback callback;
	private static final int MAX_ROW = 1000;
	public GetAllData(String tableName, Callback callback){
		this.tableName = tableName;
		this.callback = callback;
	}

	@Override
	public Object execute(Map params) throws Throwable {

		if(callback!=null){
			PreparedStatement preparedStatement = null;

			try {
				preparedStatement=getDB(dbName).getConnection().prepareStatement("SELECT * FROM "+tableName );
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					callback.execute(toMap(resultSet));
				}
			}
			finally {
				if(preparedStatement!=null){
					preparedStatement.close();
				}
			}

		}
		return null;
	}

	Map toMap(ResultSet rs) throws Exception {
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		Map<String,Object> rowData  = new HashMap<String,Object>(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			Object v = rs.getObject(i);

			if (v != null && (v.getClass() == Date.class || v.getClass() == java.sql.Date.class)) {
				Timestamp ts = rs.getTimestamp(i);
				v = new java.util.Date(ts.getTime());
			} else if (v != null && v.getClass() == Clob.class) {
				v = clob2String((Clob) v);
			}
			rowData.put(md.getColumnName(i), v);
		}
		return rowData;
	}

	public String clob2String(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	}
	
}
