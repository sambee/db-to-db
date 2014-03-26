package sam.bee.oa.sql.database.model;

import java.util.List;
import java.util.Map;

public class PageModel {

	private long count;
	private long start = -1;
	private long pageSize = 0;
	
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	List<Map<String, Object>> list;
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
