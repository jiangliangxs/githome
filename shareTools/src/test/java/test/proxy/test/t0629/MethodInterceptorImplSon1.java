package test.proxy.test.t0629;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;
import test.proxy.util4.MethodInterceptorImpl;

public class MethodInterceptorImplSon1 extends MethodInterceptorImpl {
	@Override
	protected void doBefore(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		System.out.println("MethodInterceptorImplSon1 do before...");
//		super.doBefore(proxy, method, args, mp);
	}

}
