package sam.bee.oa.sql.database;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import oracle.sql.BLOB;
import oracle.sql.CLOB;
import org.apache.log4j.Logger;
import sam.bee.oa.sql.utils.JdbcConverter;


public class BaseDatabase {
	private static Logger log = Logger.getLogger(BaseDatabase.class);
	private DatabaseConfig config = null;

	private Connection connection;
	public BaseDatabase(DatabaseConfig config) throws SQLException, ClassNotFoundException{
		this.config = config;
	}
	
	public boolean update(String sql)throws SQLException, ClassNotFoundException {
		Statement stmt = getConnection().createStatement();
		boolean ret = true;

		try {
			log.info(sql);
			ret = stmt.execute(sql);
		}
		catch (Exception ex){
			log.error(ex.getMessage(), ex);
		}
		finally {
			if(stmt!=null){
				stmt.close();
			}
		}
		return ret;
	}

	public boolean update(String sql, Object[] args)throws SQLException, ClassNotFoundException {
		CallableStatement stmt = getConnection().prepareCall(sql);
		boolean ret = false;
		//log.info(sql);
		try {

			for(int index=0;index<args.length;index++){
				Object obj = args[index];
				int i =index+1;
				if(obj == null){
					stmt.setNull(i, Types.NULL);
				}
				else if(obj instanceof String){

					stmt.setString(i, (String) obj);
				}
				else if(obj instanceof Long){

					stmt.setLong(i, (Long) obj);
				}
				else if(obj instanceof Boolean){

					stmt.setBoolean(i, (Boolean) obj);
				}
				else if(obj instanceof BigDecimal){
					stmt.setBigDecimal(i, (BigDecimal) obj);
				}
				else if(obj instanceof BLOB){
					stmt.setBlob(i, (BLOB) obj);
				}
				else if(obj instanceof CLOB){
					stmt.setClob(i, (CLOB) obj);
				}
				else if(obj instanceof java.sql.Timestamp){
					stmt.setTimestamp(i, (java.sql.Timestamp) obj);
				}
				else if(obj instanceof oracle.sql.TIMESTAMP){
					oracle.sql.TIMESTAMP obj2= (oracle.sql.TIMESTAMP)obj;
					stmt.setTimestamp(i, obj2.timestampValue());
				}
				else if(obj instanceof org.h2.jdbc.JdbcClob){
					stmt.setClob(i, (org.h2.jdbc.JdbcClob)obj);
				}
				else if(obj instanceof org.h2.jdbc.JdbcBlob){
					stmt.setBlob(i, (org.h2.jdbc.JdbcBlob) obj);
				}
				else if(obj instanceof  java.util.Date){
					stmt.setDate(i, new java.sql.Date(((java.util.Date)obj).getTime()));
				}
				else {
					throw new RuntimeException("Unknown type： " + obj.getClass() );
				}
			}

			ret = stmt.execute();
		}
		finally {
			if(stmt!=null){
				stmt.close();
			}
		}
		return ret;
	}
//	private synchronized ResultSet getResultSet(String sql) throws SQLException, ClassNotFoundException {
//		return getResultSet(sql, null);
//	}

	public List<Map<String,Objects>> getMetas(String tableName, String sql) throws SQLException, ClassNotFoundException, IOException {

		DatabaseMetaData dm = getConnection().getMetaData();
		List<String> primaryKeys = getAllPrimaryKeys(dm, null, tableName);
		Statement stmt = getConnection().createStatement();
		List<Map<String, Objects>> list;
		try {
			ResultSet resultSet = stmt.executeQuery(sql);
			list  = getMetaList(resultSet, primaryKeys, tableName);
			resultSet.close();
		}
		finally {
			if(stmt!=null) {
				stmt.close();
			}
			reconnect();
		}
		return list;
	}

	private List<Map<String,Objects>> getMetaList(ResultSet rs, List<String> primaryKeys, String tableName) throws SQLException, IOException, ClassNotFoundException {
		List<Map<String,Objects>> list = new ArrayList<Map<String,Objects>>();
		ResultSetMetaData rsmd = rs.getMetaData();


		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String colName = rsmd.getColumnName(i);
			int type = rsmd.getColumnType(i);
			String typeName = rsmd.getColumnTypeName(i);
			int colLen = rsmd.getColumnDisplaySize(i);
			int precision = rsmd.getPrecision(i);
			String schenaName  = rsmd.getSchemaName(i);
			int scale  = rsmd.getScale(i);
			String clsName = rsmd.getColumnClassName(i);
			String tableN = rsmd.getTableName(i);
			int isAllowNull = rsmd.isNullable(i);


			Map meta = new HashMap();
			meta.put("COL_NAME", colName);
			meta.put("COL_TYPE", typeName.toLowerCase());
			meta.put("COL_LEN", colLen);
			meta.put("COL_SCALE", scale);
			meta.put("COL_CLASS", clsName);
			meta.put("COL_NULLABLE", isAllowNull);


			DatabaseMetaData md = getConnection().getMetaData();
			ResultSet rs2 = md.getColumns(null, null,tableName, colName);
			String columnDefaultVal = null;
			while(rs2.next()){
				columnDefaultVal  =  rs2.getString("COLUMN_DEF");
				if(columnDefaultVal!=null){
					meta.put("COL_DEFAULT_VALUES", columnDefaultVal);
				}
			}

			if(rs2!=null){
				rs2.close();
			}
			if(primaryKeys.contains(colName)){
				meta.put("COL_PRIMARY", true);
			}
			list.add(meta);

		}

		rs.close();
//DatabaseMetaData dm = getDB(dbName).getConn().getMetaData( );
//		getDataBaseInformations(dm);
//		out.println("--------------------------------");
//		getAllTableList(dm, null);
//		out.println("--------------------------------");
//		getAllViewList(dm,null);
//		out.println("--------------------------------");
//		getAllSchemas(dm);
//		out.println("--------------------------------");
//		getAllPrimaryKeys(dm, null, "system_users");
//		out.println("--------------------------------");
//		getAllExportedKeys(dm, null, "system_identities");
//		out.println("--------------------------------");


		return list;
	}


	/**
	 * 获得一个表的主键信息
	 */
	private List<String> getAllPrimaryKeys(DatabaseMetaData dbMetaData, String schemaName, String tableName) {

		List<String> list = new ArrayList<String>(0);

		try{
			ResultSet rs = dbMetaData.getPrimaryKeys(null, schemaName, tableName);

			while (rs.next()){
				String columnName = rs.getString("COLUMN_NAME");//列名
				//short keySeq = rs.getShort("KEY_SEQ");//序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
				//String pkName = rs.getString("PK_NAME"); //主键名称
				//System.out.println(columnName + "-" + keySeq + "-" + pkName);
				list.add(columnName);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得数据库的一些相关信息
	 */
	public void getDataBaseInformations(DatabaseMetaData dbMetaData) {
		try {
			System.out.println("数据库已知的用户: "+ dbMetaData.getUserName());
			System.out.println("数据库的系统函数的逗号分隔列表: "+ dbMetaData.getSystemFunctions());
			System.out.println("数据库的时间和日期函数的逗号分隔列表: "+ dbMetaData.getTimeDateFunctions());
			System.out.println("数据库的字符串函数的逗号分隔列表: "+ dbMetaData.getStringFunctions());
			System.out.println("数据库供应商用于 'schema' 的首选术语: "+ dbMetaData.getSchemaTerm());
			System.out.println("数据库URL: " + dbMetaData.getURL());
			System.out.println("是否允许只读:" + dbMetaData.isReadOnly());
			System.out.println("数据库的产品名称:" + dbMetaData.getDatabaseProductName());
			System.out.println("数据库的版本:" + dbMetaData.getDatabaseProductVersion());
			System.out.println("驱动程序的名称:" + dbMetaData.getDriverName());
			System.out.println("驱动程序的版本:" + dbMetaData.getDriverVersion());


			System.out.println();
			System.out.println("数据库中使用的表类型");
			ResultSet rs = dbMetaData.getTableTypes();
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			rs.close();
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获得该用户下面的所有表
	 */
	public void getAllTableList(DatabaseMetaData dbMetaData, String schemaName) {
		try {
			// table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
			String[] types = { "TABLE" };
			ResultSet rs = dbMetaData.getTables(null, schemaName, "%", types);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME"); 	//表名
				String tableType = rs.getString("TABLE_TYPE"); 	//表类型
				String remarks = rs.getString("REMARKS"); 		//表备注
				System.out.println(tableName + "-" + tableType + "-" + remarks);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获得该用户下面的所有视图
	 */
	public void getAllViewList(DatabaseMetaData dbMetaData, String schemaName) {
		try{
			String[] types = { "VIEW" };
			ResultSet rs = dbMetaData.getTables(null, schemaName, "%", types);
			while (rs.next()){
				String viewName = rs.getString("TABLE_NAME"); //视图名
				String viewType = rs.getString("TABLE_TYPE"); //视图类型
				String remarks = rs.getString("REMARKS");		//视图备注
				System.out.println(viewName + "-" + viewType + "-" + remarks);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得数据库中所有方案名称
	 */
	public void getAllSchemas(DatabaseMetaData dbMetaData){
		try{
			ResultSet rs = dbMetaData.getSchemas();
			while (rs.next()){
				String tableSchem = rs.getString("TABLE_SCHEM");
				System.out.println(tableSchem);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}


	/**
	 * 获得表或视图中的所有列信息
	 */
	public void getTableColumns(DatabaseMetaData dbMetaData, String schemaName, String tableName) {

		try{

			ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
			while (rs.next()){
				String tableCat = rs.getString("TABLE_CAT");//表目录（可能为空）
				String tableSchemaName = rs.getString("TABLE_SCHEM");//表的架构（可能为空）
				String tableName_ = rs.getString("TABLE_NAME");//表名
				String columnName = rs.getString("COLUMN_NAME");//列名
				int dataType = rs.getInt("DATA_TYPE"); //对应的java.sql.Types类型
				String dataTypeName = rs.getString("TYPE_NAME");//java.sql.Types类型   名称
				int columnSize = rs.getInt("COLUMN_SIZE");//列大小
				int decimalDigits = rs.getInt("DECIMAL_DIGITS");//小数位数
				int numPrecRadix = rs.getInt("NUM_PREC_RADIX");//基数（通常是10或2）
				int nullAble = rs.getInt("NULLABLE");//是否允许为null
				String remarks = rs.getString("REMARKS");//列描述
				String columnDef = rs.getString("COLUMN_DEF");//默认值
				int sqlDataType = rs.getInt("SQL_DATA_TYPE");//sql数据类型
				int sqlDatetimeSub = rs.getInt("SQL_DATETIME_SUB");   //SQL日期时间分?
				int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");   //char类型的列中的最大字节数
				int ordinalPosition = rs.getInt("ORDINAL_POSITION");  //表中列的索引（从1开始）

				/**
				 * ISO规则用来确定某一列的为空性。
				 * 是---如果该参数可以包括空值;
				 * 无---如果参数不能包含空值
				 * 空字符串---如果参数为空性是未知的
				 */
				String isNullAble = rs.getString("IS_NULLABLE");

				/**
				 * 指示此列是否是自动递增
				 * 是---如果该列是自动递增
				 * 无---如果不是自动递增列
				 * 空字串---如果不能确定它是否
				 * 列是自动递增的参数是未知
				 */
				String isAutoincrement = rs.getString("IS_AUTOINCREMENT");

				System.out.println(tableCat + "-" + tableSchemaName + "-" + tableName_ + "-" + columnName + "-"
						+ dataType + "-" + dataTypeName + "-" + columnSize + "-" + decimalDigits + "-" + numPrecRadix
						+ "-" + nullAble + "-" + remarks + "-" + columnDef + "-" + sqlDataType + "-" + sqlDatetimeSub
						+ charOctetLength + "-" + ordinalPosition + "-" + isNullAble + "-" + isAutoincrement + "-");
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}




	/**
	 * 获得一个表的索引信息
	 */
	private void getIndexInfo(DatabaseMetaData dbMetaData, String schemaName, String tableName) {
		try{
			ResultSet rs = dbMetaData.getIndexInfo(null, schemaName, tableName, true, true);
			while (rs.next()){
				boolean nonUnique = rs.getBoolean("NON_UNIQUE");//非唯一索引(Can index values be non-unique. false when TYPE is  tableIndexStatistic   )
				String indexQualifier = rs.getString("INDEX_QUALIFIER");//索引目录（可能为空）
				String indexName = rs.getString("INDEX_NAME");//索引的名称
				short type = rs.getShort("TYPE");//索引类型
				short ordinalPosition = rs.getShort("ORDINAL_POSITION");//在索引列顺序号
				String columnName = rs.getString("COLUMN_NAME");//列名
				String ascOrDesc = rs.getString("ASC_OR_DESC");//列排序顺序:升序还是降序
				int cardinality = rs.getInt("CARDINALITY");   //基数
				System.out.println(nonUnique + "-" + indexQualifier + "-" + indexName + "-" + type + "-" + ordinalPosition + "-" + columnName + "-" + ascOrDesc + "-" + cardinality);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * 获得一个表的外键信息
	 */
	private void getAllExportedKeys(DatabaseMetaData dbMetaData, String schemaName, String tableName) {

		try{
			ResultSet rs = dbMetaData.getExportedKeys(null, schemaName, tableName);
			while (rs.next()){
				String pkTableCat = rs.getString("PKTABLE_CAT");//主键表的目录（可能为空）
				String pkTableSchem = rs.getString("PKTABLE_SCHEM");//主键表的架构（可能为空）
				String pkTableName = rs.getString("PKTABLE_NAME");//主键表名
				String pkColumnName = rs.getString("PKCOLUMN_NAME");//主键列名
				String fkTableCat = rs.getString("FKTABLE_CAT");//外键的表的目录（可能为空）出口（可能为null）
				String fkTableSchem = rs.getString("FKTABLE_SCHEM");//外键表的架构（可能为空）出口（可能为空）
				String fkTableName = rs.getString("FKTABLE_NAME");//外键表名
				String fkColumnName = rs.getString("FKCOLUMN_NAME"); //外键列名
				short keySeq = rs.getShort("KEY_SEQ");//序列号（外键内值1表示第一列的外键，值2代表在第二列的外键）。

				/**
				 * hat happens to foreign key when primary is updated:
				 * importedNoAction - do not allow update of primary key if it has been imported
				 * importedKeyCascade - change imported key to agree with primary key update
				 * importedKeySetNull - change imported key to NULL if its primary key has been updated
				 * importedKeySetDefault - change imported key to default values if its primary key has been updated
				 * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
				 */
				short updateRule = rs.getShort("UPDATE_RULE");

				/**
				 * What happens to the foreign key when primary is deleted.
				 * importedKeyNoAction - do not allow delete of primary key if it has been imported
				 * importedKeyCascade - delete rows that import a deleted key
				 * importedKeySetNull - change imported key to NULL if its primary key has been deleted
				 * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
				 * importedKeySetDefault - change imported key to default if its primary key has been deleted
				 */
				short delRule = rs.getShort("DELETE_RULE");
				String fkName = rs.getString("FK_NAME");//外键的名称（可能为空）
				String pkName = rs.getString("PK_NAME");//主键的名称（可能为空）

				/**
				 * can the evaluation of foreign key constraints be deferred until commit
				 * importedKeyInitiallyDeferred - see SQL92 for definition
				 * importedKeyInitiallyImmediate - see SQL92 for definition
				 * importedKeyNotDeferrable - see SQL92 for definition
				 */
				short deferRability = rs.getShort("DEFERRABILITY");

				System.out.println(pkTableCat + "-" + pkTableSchem + "-" + pkTableName + "-" + pkColumnName + "-"
						+ fkTableCat + "-" + fkTableSchem + "-" + fkTableName + "-" + fkColumnName + "-" + keySeq + "-"
						+ updateRule + "-" + delRule + "-" + fkName + "-" + pkName + "-" + deferRability);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

//		metaData.getDataBaseInformations();
//		metaData.getAllTableList(null);
//		metaData.getAllViewList(null);
//		metaData.getAllSchemas();
//		metaData.getTableColumns(null, "zsc_admin");
//		metaData.getIndexInfo(null, "zsc_admin");
//		metaData.getAllPrimaryKeys(null, "zsc_admin");
//		metaData.getAllExportedKeys(null, "zsc_admin");


	}



	public List<Map<String,Object>> getList(String sql, Object[] args) throws Exception {
		Statement stmt = getConnection().createStatement();
		if (args == null || args.length == 0) {
			stmt.executeQuery(sql);
		}
		;
		ResultSet resultSet  =  stmt.executeQuery(sql);
		ArrayList<Map<String,Object>> list  = JdbcConverter.resultSetToList(resultSet);
		stmt.close();
		return list;
	}

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		if(connection==null){
			Class.forName(config.getDriverClass());
			connection = DriverManager.getConnection(config.getJDBC(), config.getUserName(), config.getPassword());
		}
		return connection;
	}
	

	public String getType(){
       return config.getType();
	}

	public Connection reconnect() throws ClassNotFoundException, SQLException {
		if(connection!=null) {
			connection.close();
		}
		Class.forName(config.getDriverClass());
		connection = DriverManager.getConnection(config.getJDBC(), config.getUserName(), config.getPassword());
		return connection;
	}
}
