package sam.bee.oa.sql.database;

@SuppressWarnings({ "rawtypes", "unchecked" })
public interface Callback {

	boolean execute(Object obj) throws Throwable;
}
