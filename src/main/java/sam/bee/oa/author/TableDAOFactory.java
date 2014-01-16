package sam.bee.oa.author;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import java.lang.reflect.Method;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TableDAOFactory {
	private static TableDao tDao = new TableDao();

	public static TableDao getInstance() {
		return tDao;
	}

	public static TableDao getAuthInstance(AuthProxy authProxy) {
		Enhancer en = new Enhancer(); // Enhancer用来生成一个原有类的子类
		// 进行代理
		en.setSuperclass(TableDao.class);
		// 设置织入逻辑
		en.setCallback(authProxy);
		// 生成代理实例
		return (TableDao) en.create();
	}
	
	public static TableDao getAuthInstanceByFilter(AuthProxy authProxy){  
        Enhancer en = new Enhancer();  
        en.setSuperclass(TableDao.class);  
        en.setCallbacks(new Callback[]{authProxy,NoOp.INSTANCE});  //设置两个方法拦截器
        en.setCallbackFilter(new AuthProxyFilter());  
        return (TableDao)en.create();  
    }
}
