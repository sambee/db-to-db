package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;

import java.util.Map;

/**
 * Created by Administrator on 2015/4/6.
 */
public class SaveData extends BaseService implements MethodExecutor {


    private String tableName;
    private Map data;

    public SaveData(String tableName,Map data){
        this.tableName = tableName;
        this.data = data;
    }
    @Override
    public Object execute(Map params) throws Throwable {



        return true;

    }
}
