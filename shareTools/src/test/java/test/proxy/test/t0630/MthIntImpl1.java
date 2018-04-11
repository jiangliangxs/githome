package test.proxy.test.t0630;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;
import test.proxy.util4.MethodInterceptorImpl;

public class MthIntImpl1 extends MethodInterceptorImpl{
	@Override
	protected void doAfter(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		System.out.println("TSon1类中的方法执行后进行拦截");
	}
}
