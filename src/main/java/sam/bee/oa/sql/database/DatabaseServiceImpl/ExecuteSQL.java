package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

import java.util.Map;

/**
 * Created by Administrator on 2015/4/6.
 */
public class ExecuteSQL extends BaseService implements MethodExecutor {


    private String sql;

    public ExecuteSQL(String sql){
        this.sql = sql;
    }
    @Override
    public Object execute(Map params) throws Throwable {

        return null;

    }
}
