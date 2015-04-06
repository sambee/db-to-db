package sam.bee.oa.sql.database.DatabaseServiceImpl;

import sam.bee.oa.sql.core.MethodExecutor;
import sam.bee.oa.sql.database.BaseService;
import sam.bee.oa.sql.database.Callback;
import sam.bee.oa.sql.database.model.PageModel;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/6.
 */
public class GetData extends BaseService implements MethodExecutor {

    private String tableName;
    private Callback callback;
    public GetData(String tableName, Callback callback){
        this.tableName = tableName;
        this.callback = callback;
    }


    @Override
    public Object execute(Map params) throws Throwable {
        long start = 0;
        long pageSize =Integer.MAX_VALUE;
        PageModel page = null;
        do{
            try {
                page = getDatabaseService().getPage(tableName, start, pageSize);
            }
            catch (SQLException sqlEx){
                if(sqlEx.getErrorCode() == 1000){
                    getDB(dbName).reconnect();
                    page = getDatabaseService().getPage(tableName, start, pageSize);
                }
                else{
                    throw sqlEx;
                }
            }
//				log.info("PAGE SIZE:"+page.getPageSize());
//				log.info("PAGE START:"+page.getStart());

            for(Map<String, Object> valuesMap :page.getList()){

                if(callback!=null){
                    callback.execute(valuesMap);
                }
            }
            start = page.getStart()+page.getPageSize();
            pageSize = page.getPageSize();
        }while(start<page.getCount());

        return null;
    }
}
