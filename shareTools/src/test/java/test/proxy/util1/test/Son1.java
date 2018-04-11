package test.proxy.util1.test;

import java.lang.reflect.Method;

import test.proxy.util1.Parent;

public class Son1 extends Parent {

	public Son1(Object target) {
		super(target);
	}

	@Override
	protected void doBefore(Object proxy, Method method, Object[] args) {
		System.out.println("son1... do doBefore");
	}
}
