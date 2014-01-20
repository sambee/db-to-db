package sam.bee.oa.sql.database.model;

import java.util.List;
import java.util.Map;

public class PageModel {

	private long count;
	private long currentIndex = -1;	
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
	public long getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(long currentIndex) {
		this.currentIndex = currentIndex;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
