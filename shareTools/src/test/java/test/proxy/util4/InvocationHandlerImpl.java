/*
 * 张军项目模板
 * Copyright (c) 2011张军版权所有 All Rights Reserved.
 * 版权制作
 *   ver 1.0 : 2011.11.08 首次版本
 */
package test.proxy.util4;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;
/**
 * 系统名：张军项目模板<br>
 * 类名 ：InvocationHandlerImpl<br>
 * 概况 ：InvocationHandlerImpl类<br>
 *
 * @version 1.00 （2011/11/08）
 * @author SHNKCS 张军 {@link <a
 *         href=http://user.qzone.qq.com/360901061>张军QQ空间</a>}
 */
public class InvocationHandlerImpl implements InvocationHandler {

	private Object target;

	public InvocationHandlerImpl(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Method[] methods = target.getClass().getDeclaredMethods();
		Object result = null;
		for (int i = 0; i < methods.length; i++) {
			if (!equals(method.getName(), methods[i].getName()))
				continue;
			Annotation ann = methods[i].getAnnotation(InProgressMethod.class);
			if (ann != null) {
				InProgressMethod rt = (InProgressMethod) ann;
				Method[] annMethods = ann.annotationType().getDeclaredMethods();
				for (Method annMethod : annMethods) {
					String annName = annMethod.getName();
					String annValue = rt.excuteMethod();
					if (equals(annName, "excuteMethod")) {
						if (equals(annValue,
								Constants.ExecuteCons.DOBEFORE)) {
							doBefore();
							result = method.invoke(target, args);
						} else if (equals(annValue,
								Constants.ExecuteCons.DOAFTER)) {
							result = method.invoke(target, args);
							doAfter();
						} else if (equals(annValue,
								Constants.ExecuteCons.DOEXCUTE)) {
							doBefore();
							result = method.invoke(target, args);
							doAfter();
						} else {
							result = method.invoke(target, args);
						}
					}
				}
			} else {
				result = method.invoke(target, args);
			}
		}
		return result;
	}

	protected void doBefore() {
		System.out.println("这里是你要在执行拦截[调用方法前]所做的业务操作");
	}

	protected void doAfter() {
		System.out.println("这里是你要在执行拦截[调用方法后]所做的业务操作");
	}
	
	  private static boolean equals(String target1, String target2)
	    {
	        return target1 != null ? target1.equals(target2) : target2 == null;
	    }
}
