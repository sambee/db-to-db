package sam.bee.oa.sql.database.DatabaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.model.PageModel;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.matchers.JUnitMatchers.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetPageTest {
	String dbName = "mssql";
	@Test
	public void test() {
		DatabaseService db = (DatabaseService)ServiceFactory.getService("",DatabaseService.class);
	
		Map paraments = new HashMap();
		paraments.put("tableName", "ams_sys_query");
		PageModel page = db.getPage(dbName, paraments, 0, 20);
		
		List<Map<String, Object>> list  = page.getList();
		System.out.println(list.size());
		assertThat(list.size(), greaterThan(0));
		
		System.out.println(page.getCount());
		assertThat(page.getCount(), greaterThan(0L));
		
		Map map = page.getList().get(0);
		System.out.println(String.valueOf(map.get("SQL_")).replaceAll("\n|\r", ""));
	}
}
