package test.proxy.test.t0630;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;
import test.proxy.util4.MethodInterceptorImpl;

public class MthIntImpl1_1 extends MethodInterceptorImpl{
	@Override
	protected void doBefore(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		System.out.println("TSon1_1类中的方法执行前进行拦截");
	}
}
