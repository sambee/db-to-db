package sam.bee.oa.sql.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InOpSqlTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ParaseException {
		class SqlTest extends BaseSql{
			
			public String test(String... args) throws ParaseException{
				Map<String,Object> params = new HashMap<String, Object>();
				List list = new ArrayList();
				list.add("test1");
				list.add("test2");
				params.put("$in", new In());				
				params.put("inTest", list);	
				return sql("select_stock.sql", params);
			}
			
		} 
		
//		fail("Not yet implemented");
		SqlTest demo  = new SqlTest();
		String sql = demo.test("inTest");
		System.out.println(sql);
	}

}
