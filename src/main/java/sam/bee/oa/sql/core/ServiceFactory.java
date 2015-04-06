package sam.bee.oa.sql.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sam.bee.oa.sql.database.BaseService;

import static sam.bee.oa.sql.utils.StringUtil.toJavaNaming;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ServiceFactory {

	private static LinkedHashMap<String, LinkedHashMap<Class, Object>> sessions = new LinkedHashMap();


	private static Object createProxy(Class targetClass, String session) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new ServiceMethodInterceptor(targetClass, session));
		return enhancer.create();
	}
	
	public static <T> T getService(String session, Class<T> cls){
		
		Object obj = null;
		if(sessions == null){
            sessions = new LinkedHashMap();
		}
        LinkedHashMap<Class, Object> services =  sessions.get(session);
        if(services == null){
            services = new LinkedHashMap<Class, Object>();
            sessions.put(session , services);
        }
		obj = services.get(cls);
		if(obj==null){
			obj = (T)createProxy(cls, session);
			services.put(cls, obj);
		}
		
		return (T)obj;
	}

}

@SuppressWarnings({"rawtypes", "unchecked"})
class ServiceMethodInterceptor implements MethodInterceptor {

	Class serviceClass;
    String session;
	public ServiceMethodInterceptor(Class cls, String session){
		this.serviceClass = cls;

        this.session = session;
	}
	
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		//System.out.println(">>>MethodInterceptor start...");
		if("toString".equals(method.getName())){
			return serviceClass.getName();
		}
		String pkg = serviceClass.getName() + "Impl." + toJavaNaming(method.getName());
		if(pkg!=null){
			
			Class clsObj = Class.forName(pkg);
//			Class[] argCls = new Class[args.length];
//			for(int i=0;i<argCls.length; i++){				
//				argCls[i] = args[i].getClass();
//			}
			

			Constructor constructor = clsObj.getConstructor(method.getParameterTypes());
			MethodExecutor obj = (MethodExecutor)constructor.newInstance(args);
            ((BaseService) obj).setDatabaseName(session);
			Map params = new HashMap(args.length);
			params.put("args", args);
			//System.out.println(">>>MethodInterceptor ending...");

			return obj.execute(params);		
		}
		Object result = methodProxy.invokeSuper(object, args);
		//System.out.println(">>>MethodInterceptor ending...");
		return "here";
	}
}
