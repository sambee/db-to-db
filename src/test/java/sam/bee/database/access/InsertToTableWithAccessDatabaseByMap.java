package sam.bee.database.access;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;


public class InsertToTableWithAccessDatabaseByMap {

	   public static void main(String argv[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {

		   File dbFile = new File("F:\\2.mdb");
		   Database db = DatabaseBuilder.open(dbFile);
		   Table table =db.getTable("ams_work_file"); 
		   
		   byte[] args ="TEST".getBytes();
		   
		   for(int i=0; i<5; i++){
			   
			   Map map = new HashMap();
			   map.put("id", i+"9858a123"); 
			   map.put("c", args); 
			   map.put("abcdefg", i+"");
			   table.addRowFromMap(map);
		   }
		   
		   db.close();
		   
          System.out.println("---- END ----");
          System.exit(0);

	   }
}
