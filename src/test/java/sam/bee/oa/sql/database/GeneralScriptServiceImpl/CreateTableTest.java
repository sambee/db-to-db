package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

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
import sam.bee.oa.sql.database.GeneralScriptService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CreateTableTest {

	String dbName = "mssql";
	String outputType = "h2";
	@Test
	public void test() {
		
//		GeneralScriptService service = (GeneralScriptService)ServiceFactory.getService("",GeneralScriptService.class);
//
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		String sql  = service.createTable(dbName, outputType, "system_users", list);
//		System.out.println(sql);
	}

}
