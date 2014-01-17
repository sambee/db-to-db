package sam.bee.oa.sql.freemarker;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sam.bee.oa.sql.freemarker.BaseSql;
import sam.bee.oa.sql.freemarker.ParaseException;

public class SimpleSqlTest {


	@Test
	public void test() throws ParaseException {
		class SqlTest extends BaseSql{
			
			public String test(String... args) throws ParaseException{
				Map<String,Object> params = new HashMap<String, Object>();
				for(int i=0; i<args.length; i++){
					params.put("param"+i, "param"+i);
				}
				return sql("select_stock.sql", params, getClass());
			}
			
		} 
		
//		fail("Not yet implemented");
		SqlTest demo  = new SqlTest();
		String sql = demo.test("test 1");
		System.out.println(sql);
	}

}
