package sam.bee.oa.sql.utils;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import flexjson.*;


public class JdbcConverter {

	public static ArrayList<Map<String,Object>> resultSetToList(ResultSet rs) throws Exception {
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> rowData;
		
		while (rs.next()) {
			rowData = new HashMap<String,Object>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				Object v = rs.getObject(i);

				if (v != null && (v.getClass() == Date.class || v.getClass() == java.sql.Date.class)) {
					Timestamp ts = rs.getTimestamp(i);
					v = new java.util.Date(ts.getTime());
				} else if (v != null && v.getClass() == Clob.class) {
					v = clob2String((Clob) v);
				}
				rowData.put(md.getColumnName(i), v);
			}
			list.add(rowData);
		}
		return list;
	}
	
	public static String clob2String(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	}
	
//	public static String Encode(Object obj) {
//		if(obj == null || obj.toString().equals("null")) return null;
//		if(obj != null && obj.getClass() == String.class){
//			return obj.toString();
//		}
//		JSONSerializer serializer = new JSONSerializer();
//		serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Date.class);
//		serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Timestamp.class);
//		return serializer.deepSerialize(obj);
//	}
//	
//	public static Object Decode(String json) {
//		if (StringUtil.isNullOrEmpty(json)) return "";
//		JSONDeserializer deserializer = new JSONDeserializer();
//		deserializer.use(String.class, new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"));		
//		Object obj = deserializer.deserialize(json);
//		if(obj != null && obj.getClass() == String.class){
//			return Decode(obj.toString());
//		}		
//		return obj;
//	}
}
