package sam.bee.oa.sql.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

public class InOpSqlTest {

	Logger log = Logger.getLogger(InOpSqlTest.class);
	class SqlTest extends BaseSql {

		public String test(String sql, Map map, List args) throws ParaseException {
			Map<String, Object> root = new HashMap<String, Object>();
			root.putAll(map);
			root.put("$in", new In(args));
			root.put("$", new $(args));
			return sql(sql, root, getClass());
		}

	}

	@Test
	public void test() throws ParaseException {

		SqlTest demo = new SqlTest();
		Map<String,Object> params = new HashMap<String,Object>();
		
		List<String> catories = new ArrayList<String>();
		catories.add("catory 1");
		catories.add("catory 2");
		catories.add("catory 3");
		
		params.put("catories", catories);
		params.put("stockId", "000616");
		List<Object> args = new LinkedList<Object>();
		String sql = demo.test("select_stock.sql", params, args);
		log.info(sql);
		
		for(int i=0;i<args.size(); i++){
			log.info("argument"+ + i +   " " + args.get(i));
		}
	}

}
