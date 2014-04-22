package sam.bee.oa.sql.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import static sam.bee.oa.sql.utils.StringUtil.toJavaNaming;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ServiceFactory {

	private static LinkedHashMap<Class, Object> services = new LinkedHashMap<Class, Object>();
	
	private static Object createProxy(Class targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new ServiceMethodInterceptor(targetClass));
		return enhancer.create();
	}
	
	public static <T> T getService(Class<T> cls){
		
		Object obj = null;
		if(services == null){
			services = new LinkedHashMap<Class, Object>();
		}
		
		obj = services.get(cls);
		if(obj==null){
			obj = (T)createProxy(cls);
			services.put(cls, obj);
		}
		
		return (T)obj;
	}

}

@SuppressWarnings({"rawtypes", "unchecked"})
class ServiceMethodInterceptor implements MethodInterceptor {

	Class serviceClass;
	public ServiceMethodInterceptor(Class cls){
		this.serviceClass = cls;
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
