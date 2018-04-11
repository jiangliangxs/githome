package test.proxy.util3.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import test.proxy.util3.InvocationHandler;


public class TimeHandler implements InvocationHandler {
	
	private Object t;
	

	public TimeHandler(Object t) {
		super();
		this.t = t;
	}


	@Override
	public void invoke(Object o,Method m) {
		System.out.println(o.getClass().getName());
		long start = System.currentTimeMillis(); 
		try {
			m.invoke(t, new Object[]{});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis(); 
		System.out.println("time-proxy: " + (end - start));
	}

}
