package sam.bee.oa.sql.database.DatabaseServiceImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllTablesTest {

	String dbName = "mssql";
	@Test
	public void test() {
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		
		List<Map<String, Object>> list = service.getAllTables(dbName);
		
		List<String> stringList = new ArrayList();
		for(Map<String, Object> m : list){
			stringList.add(m.get("name")+ "=create");
		}
	
		java.util.Collections.sort(stringList);
		for(int i=0;i<stringList.size();i++){
			System.out.println(stringList.get(i));
		}
		
	}

}
