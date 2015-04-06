package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.mvstore.Page;
import org.junit.Test;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.GeneralScriptService;
import sam.bee.oa.sql.database.model.PageModel;

public class CreateRecordTest {
	
	String dbName = "mssql";
	String outputType = "h2";
	
	@Test
	public void test() {
		
//		GeneralScriptService gen = (GeneralScriptService)ServiceFactory.getService("",GeneralScriptService.class);
//		DatabaseService db = (DatabaseService)ServiceFactory.getService("",DatabaseService.class);
//
//		PageModel page = db.getPage(dbName, "system_components", 0, 20);
//
////		Map<String, Object> values = new HashMap<String,Object>();
//		for(Map values :page.getList()){
//
//			String sql  = gen.createRecord(dbName, outputType , "system_users", values, null);
//			System.out.println(sql);
//		}
		
	}
	
	

}
