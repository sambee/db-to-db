package sam.bee.oa.sql.database.asscss;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.model.PageModel;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

public class AsscssDatabase {
	
	private static final Logger log = Logger.getLogger(AsscssDatabase.class);
	
	Database accessDB;
	public AsscssDatabase(File mdbFile) throws IOException{
		if(!mdbFile.exists()){
			accessDB = createDatabase(mdbFile);
		}
		else{
			accessDB = DatabaseBuilder.open(mdbFile);
		}
	}
	
	private Database createDatabase(File dbFile) throws IOException{
		return DatabaseBuilder.create(Database.FileFormat.V2000,dbFile);
	}
	
	
	private TableBuilder createTable(String tableName) {
        return new TableBuilder(tableName);
    }
  
	private int getType(String type){
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
	
	public void createAccessTable(String tableName, List<Map<String, Object>> fieldsInfo) throws IOException, SQLException{
		if(accessDB==null){
			throw new NullPointerException("Database base is not opened.");
		}
		TableBuilder tableBuilder = createTable(tableName);		
		for(Map<String,Object> f : fieldsInfo){
			log.info("Import table:" + tableName);
			tableBuilder.addColumn(new ColumnBuilder( (String)f.get("COL_NAME")).setSQLType(getType((String)f.get("COL_TYPE"))).toColumn());			
		}	
		tableBuilder.toTable(accessDB);
	}
	
	public void addRowFromMap(String tableName, Map<String, Object> data) throws IOException{
		accessDB.getTable(tableName).addRowFromMap(data);
	}
	
	public void updateRowFromMap(String tableName, String keyName,  String keyValue, Map<String, Object> rowMap) throws IOException{
		Table table = accessDB.getTable(tableName);
		Cursor cursor = CursorBuilder.createCursor(table);	
		while(cursor.moveToNextRow()){
			cursor.updateCurrentRowFromMap(rowMap);
		}
		
	}
	
	public void close() throws IOException{
		if(accessDB!=null){
			accessDB.close();
		}
	}
}
