package sam.bee.oa.sql.database.GeneralScriptServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class H2ToMssqlAdapter {

	public List<Map<String, Object>> paraseFields(List<Map<String, Object>> list){
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		
		for(int i =0; i<list.size(); i++){
			Map<String, Object> m = list.get(i);
			if("timestamp".equals(m.get("COL_TYPE"))){
				m.put("COL_TYPE", "datetime");
			}
			if("clob".equals(m.get("COL_TYPE"))){
				m.put("COL_TYPE", "image");
			}
			if("blob".equals(m.get("COL_TYPE"))){
				m.put("COL_TYPE", "image");
			}
			if("datetime".equals(m.get("COL_TYPE")) && m.get("COL_DEFAULT_VALUES")!=null){
				m.put("COL_DEFAULT_VALUES", "getDate()");
			}
			System.out.println(m);
			ret.add(m);
		}
		list.clear();
		return ret;
	}
}
