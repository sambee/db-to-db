package sam.bee.oa.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 目标对象
 */
class Target {

	public String execute() {
		String message = "----------test()----------";
		System.out.println(message);
		return message;
	}
}

/**
 * 拦截器
 */
class MyMethodInterceptor implements MethodInterceptor {

	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println(">>>MethodInterceptor start...");
		Object result = methodProxy.invokeSuper(object, args);
		System.out.println(">>>MethodInterceptor ending...");
		return "here";
	}
}

public class CglibTest {
	public static void main(String rags[]) {
		Target proxyTarget = (Target) createProxy(Target.class);// 强制转换为实现类，不会抛出异常
		String res = proxyTarget.execute();
		System.out.println(res);
	}

	public static Object createProxy(Class targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new MyMethodInterceptor());
		return enhancer.create();
	}

}

