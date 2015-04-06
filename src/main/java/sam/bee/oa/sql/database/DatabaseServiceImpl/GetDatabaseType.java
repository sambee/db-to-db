package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

import java.util.Map;

/**
 * Created by Administrator on 2015/4/6.
 */
public class GetDatabaseType extends BaseService implements MethodExecutor {


    @Override
    public Object execute(Map params) throws Throwable{

        return getDB(dbName).getType();
    }
}
