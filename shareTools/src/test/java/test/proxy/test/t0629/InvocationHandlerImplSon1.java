package test.proxy.test.t0629;

import test.proxy.util4.InvocationHandlerImpl;

public class InvocationHandlerImplSon1 extends InvocationHandlerImpl{

	public InvocationHandlerImplSon1(Object target) {
		super(target);
	}
	
	@Override
	protected void doBefore() {
		System.out.println("InvocationHandlerImplSon1 do before...");
	}

}
