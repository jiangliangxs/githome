package test.proxy.util1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import test.proxy.util1.test.Business1;

public class InterfaceFactory {
	public static Object getInterface(Object target, ClassLoader loader,
			InvocationHandler handler, Class<?>... classes) {
		return Proxy.newProxyInstance(loader, classes, handler);
	}
	public static Business1 getBusiness(Object target, InvocationHandler handler) {
		return (Business1) Proxy.newProxyInstance(Business1.class
				.getClassLoader(), new Class[] { Business1.class }, handler);
	}
}
