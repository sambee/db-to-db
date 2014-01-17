package sam.bee.oa.sql.database.DatabaseServiceImpl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sam.bee.oa.sql.core.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllTablesTest {

	@Test
	public void test() {
		DatabaseService service = (DatabaseService)DatabaseFactory.getService(DatabaseService.class);
		
		List<Map<String, Object>> list = service.getAllTables();
		
		for(Map<String, Object> m : list){
			System.out.println(m);
		}
	
	}

}
