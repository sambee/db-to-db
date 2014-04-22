package sam.bee.oa.sql.database.asscss;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import sam.bee.oa.db.MsDBToAccessDB;
import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseConnection;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.model.PageModel;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AsscssDatabase {
	
	private static final Logger log = Logger.getLogger(AsscssDatabase.class);
	
	private static TableBuilder createTable(String tableName) {
        return new TableBuilder(tableName);
    }
  
	public int getType(String type){
		if("varchar".equals(type)){
			return Types.LONGVARCHAR;
		}
		if("nvarchar".equals(type)){
			return Types.LONGVARCHAR;
		}
		if("ntext".equals(type)){
			return Types.BLOB;
		}
		if("numeric".equals(type)){
			return Types.INTEGER;
		}		
		if("datetime".equals(type)){
			return Types.TIMESTAMP;
		}
		if("decimal".equals(type)){
			return Types.INTEGER;
		}
		if("int".equals(type)){
			return Types.INTEGER;
		}
		throw new RuntimeException("Unkonw " + type + " sql  type."  );
	}
	
	
	public List<Map<String, Object>> getSrcInfo(DatabaseService db, String srcDB, String tableName){
		return 	db.getMetas(srcDB, tableName);
	}
	
	public void createAccessTable(Database accessDB, String tableName, List<Map<String, Object>> fieldsInfo) throws IOException, SQLException{
		TableBuilder tableBuilder = createTable(tableName);		
		for(Map<String,Object> f : fieldsInfo){
			log.info("Import table:" + tableName);
			tableBuilder.addColumn(new ColumnBuilder( (String)f.get("COL_NAME")).setSQLType(getType((String)f.get("COL_TYPE"))).toColumn());			
		}	
		tableBuilder.toTable(accessDB);
	}
	
	public void importData(Database accessDB , DatabaseService db, String srcDBName, String tableName)throws Exception{
		
		long start = 0;
		long pageSize =Integer.MAX_VALUE;
		PageModel page;
		Table table = accessDB.getTable(tableName);
		do{
			page = db.getPage(srcDBName,tableName, start, pageSize);	
			start = page.getStart()+page.getPageSize();
			pageSize = page.getPageSize(); 
			System.out.println(String.format("Table: %s, Start:%d, size:%d, count:%d, data count:%d", tableName,start, pageSize, page.getCount(), page.getList().size()));
			
			List<Map<String, Object>> data = page.getList();
			for(Map<String, Object> d : data){
				table.addRowFromMap(d);
			}
			
		}while(start<page.getCount());
	}
}
