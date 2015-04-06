package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

import java.util.*;

/**
 * Created by Administrator on 2015/4/6.
 */
public class SaveData extends BaseService implements MethodExecutor {


    private String tableName;
    private Map<String, Object> data;

    public SaveData(String tableName, Map data) {
        this.tableName = tableName;
        this.data = data;
    }

    @Override
    public Object execute(Map params) throws Throwable {

        String sql = "INSERT INTO " + tableName + "(";
        String sql1 = " ";
        String sql2 = " ";


        ArrayList<Object> args = new ArrayList<Object>();

        boolean isFirst = true;
        for (String key : data.keySet()) {
            if (isFirst) {
                sql1 = key;
                sql2 = "? " + sql2;
                isFirst = false;
            } else {
                sql1 = sql1 +"," + key ;
                sql2 = sql2 + ",? ";

            }
            args.add(data.get(key));
        }

        String fullSQL = sql + sql1 + ") VALUES(" + sql2 + ")";
        log.info("EXECUTE :" + fullSQL);
        getDB(dbName).update(fullSQL, args.toArray());


        return true;

    }
}
