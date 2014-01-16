package sam.bee.oa.aop;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Java动态代理和Cglib
 *
 * AopTest.java
 *
 * @author SamWong
 *  QQ: 1557299538
 * @create: 2014年1月13日  
 * 
 * Modification
 * -------------------------------------------
 */
public class AopTest {
	
	public static void main(String[] args) {

		Before before = new Before() {
			public void before() {
				System.out.println("...before...");
			}
		};

		After after = new After() {
			public void after() {
				System.out.println("...after...");
			}
		};

		Hello hello = null;

		// 普通方法执行
		System.out.println("-------------普通执行-------------");
		hello = new HelloEnglish();
		hello.sayHello("bao110908");
		hello.sayHi("bao110908");
		System.out.println();

		// 切入方法执行前（前置增强）
		System.out.println("-------------前置增强-------------");
		hello = HelloAopManager.getHelloProxy(new HelloEnglish(), before);
		hello.sayHello("bao110908");
		hello.sayHi("bao110908"); // sayHi 方法没有标注 @Enhancement 所以不会进行代码切入
		System.out.println();

		// 切入方法执行后（后置增强）
		System.out.println("-------------后置增强-------------");
		hello = HelloAopManager.getHelloProxy(new HelloEnglish(), after);
		hello.sayHello("bao110908");
		hello.sayHi("bao110908");
		System.out.println();

		// 切入方法执行前和执行后（环绕增强）
		System.out.println("-------------环绕增强-------------");
		hello = HelloAopManager
				.getHelloProxy(new HelloEnglish(), before, after);
		hello.sayHello("bao110908");
		hello.sayHi("bao110908");
		System.out.println();
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface Enhancement {
}

interface Hello {
	@Enhancement
	public void sayHello(String name);

	public void sayHi(String name);
}

class HelloChinese implements Hello {
	public void sayHello(String name) {
		System.out.println(name + "，您好");
	}

	public void sayHi(String name) {
		System.out.println("哈啰，" + name);
	}
}

class HelloEnglish implements Hello {
	public void sayHello(String name) {
		System.out.println("Hello, " + name);
	}

	public void sayHi(String name) {
		System.out.println("hi, " + name);
	}
}

class HelloAopManager {
	private HelloAopManager() {
	}

	public static Hello getHelloProxy(Hello hello, Before before) {
		return getHelloProxy(hello, before, null);
	}

	public static Hello getHelloProxy(Hello hello, After after) {
		return getHelloProxy(hello, null, after);
	}

	public static Hello getHelloProxy(Hello hello, Before before, After after) {
		HelloHandler handler = new HelloHandler();
		if (before != null) {
			handler.setBefore(before);
		}
		if (after != null) {
			handler.setAfter(after);
		}
		return handler.bind(hello);
	}
}

/**
 * 前置增强接口
 */
interface Before {
	public void before();
}

/**
 * 后置增强接口
 */
interface After {
	public void after();
}

class HelloHandler implements InvocationHandler {

	/**
	 * 需要进行代理的实例
	 */
	private Hello hello = null;

	/**
	 * 前置增强
	 */
	private Before before = null;

	/**
	 * 后置增强
	 */
	private After after = null;

	/**
	 * InvocationHandler 接口的实现方法，进行动态代理
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// 看看接口中方法是否标注了需要 Enhancement
		boolean b = method.isAnnotationPresent(Enhancement.class);
		if (!b) {
			// 没有标注的话，按原方法执行
			return method.invoke(hello, args);
		}
		// 有标注的话，进行方法的前置和后置增强
		if (before != null) {
			before.before();
		}
		Object obj = method.invoke(hello, args);
		if (after != null) {
			after.after();
		}
		return obj;
	}

	/**
	 * 将传入的 Hello 与 InvocationHandler 进行绑定，以获得代理类的实例
	 * 
	 * @param hello
	 * @return
	 */
	public Hello bind(Hello hello) {
		this.hello = hello;
		Hello helloProxy = (Hello) Proxy.newProxyInstance(hello.getClass()
				.getClassLoader(), hello.getClass().getInterfaces(), this);// 这句用接口引用指向，不会报错。如果是实现类强制转换就会抛异常
		return helloProxy;
	}

	public void setAfter(After after) {
		this.after = after;
	}

	public void setBefore(Before before) {
		this.before = before;
	}
}