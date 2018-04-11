package test.proxy.test.t0629;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;
import test.proxy.util4.MethodInterceptorImpl;

public class MethodInterceptorImplSon2 extends MethodInterceptorImpl{
	@Override
	protected void doAfter(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		System.out.println("MethodInterceptorImplSon2 do after...");
	}
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		System.out.println("mp--->" + method.getName());
		return super.intercept(proxy, method, args, mp);
	}

}
