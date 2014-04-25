package sam.bee.database.access;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.oa.sql.database.asscss.AsscssDatabase;


public class CreateDatabaseTest {

	@SuppressWarnings("serial")
	@Test
	public void testCreateDatabase() throws IOException, SQLException{
		
		File dbFile = new File("mssql_to_access.mdb");
		if(dbFile.exists()){
			dbFile.delete();
		}
		assert(!dbFile.exists());
		//create access file.
		AsscssDatabase asscssDatabase = new AsscssDatabase(dbFile);

		assert(dbFile.exists());
			
		//add a table.
		asscssDatabase.createAccessTable("TABLE1",  new ArrayList<Map<String, Object>>(){{
			add(new  HashMap<String,Object>(){{ 
				put("COL_NAME", "test1");
				put("COL_TYPE", "varchar");
			}});
			add(new  HashMap<String,Object>(){{ 
				put("COL_NAME", "test2");
				put("COL_TYPE", "varchar");
			}});
			
		}});
		
		asscssDatabase.addRowFromMap("TABLE1", new  HashMap<String,Object>(){{ 
			put("test1", "value1");
			put("test2", "2");
		}});
		
		asscssDatabase.updateRowFromMap("TABLE1", "test1", "value1", new  HashMap<String,Object>(){{ 
			put("test1", "test4");
			put("test2", "56");
		}});
	}
}
