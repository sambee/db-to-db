package sam.bee.oa.sql.freemarker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultSql extends BaseSql{

	public String convert(String sql, Map<String, Object> paramenters, List<Object> list, Class owner) throws ParaseException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.putAll(paramenters);
		root.put("$in", new sam.bee.oa.sql.freemarker.In(list));
		root.put("$", new sam.bee.oa.sql.freemarker.$(list));
		return sql(sql, root, owner);
	}
}
