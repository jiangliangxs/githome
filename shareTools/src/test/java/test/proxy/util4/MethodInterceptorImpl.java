/*
 * 张军项目模板
 * Copyright (c) 2011张军版权所有 All Rights Reserved.
 * 版权制作
 *   ver 1.0 : 2011.11.08 首次版本
 */
package test.proxy.util4;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;
/**
 * 系统名：张军项目模板<br>
 * 类名 ：MethodInterceptorImpl<br>
 * 概况 ：MethodInterceptorImpl类<br>
 *
 * @version 1.00 （2011/11/08）
 * @author SHNKCS 张军 {@link <a
 *         href=http://user.qzone.qq.com/360901061>张军QQ空间</a>}
 */
public class MethodInterceptorImpl implements MethodInterceptor {
	protected Object result = null;

	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		//System.out.println("....." + proxy.getClass().getSuperclass().getName());
		Annotation ann = method.getAnnotation(InProgressMethod.class);
		if (ann != null) {
			InProgressMethod rt = (InProgressMethod) ann;
			Method[] annMethods = ann.annotationType().getDeclaredMethods();
			for (Method annMethod : annMethods) {
				String annName = annMethod.getName();
				String annValue = rt.excuteMethod();
				if ("excuteMethod".equals(annName)) {
					if (Constants.ExecuteCons.DOBEFORE.equals(annValue)) {
						doBefore(proxy, method, args, mp);
						result = mp.invokeSuper(proxy, args);
					} else if (Constants.ExecuteCons.DOAFTER.equals(annValue)) {
						result = mp.invokeSuper(proxy, args);
						doAfter(proxy, method, args, mp);
					} else if (Constants.ExecuteCons.DOEXCUTE.equals(annValue)) {
						doBefore(proxy, method, args, mp);
						result = mp.invokeSuper(proxy, args);
						doAfter(proxy, method, args, mp);
					} else {
						result = mp.invokeSuper(proxy, args);
					}
					break;
				}
			}
			// Field field =
			// proxy.getClass().getSuperclass().getDeclaredField("testValue");
			// field.setAccessible(true);
			// Object obj = field.get(proxy);
			// System.out.println(obj);
		} else {
			result = mp.invokeSuper(proxy, args);
		}
		return result;
	}

	protected void doBefore(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		Class<?> curClass = proxy.getClass().getSuperclass();
		Method curMethod = method;
		Class<?>[] claAry = method.getParameterTypes();
		StringBuffer sbCls = new StringBuffer();
		for (int i = 0; i < claAry.length; i++) {
			Class<?> cls = claAry[i];
			if (i != 0)
				sbCls.append(",");
			sbCls.append(cls.getSimpleName());
		}
		System.out.println("调用【" + curClass.getSimpleName() + "】类中的【"
				+ method.getReturnType().getSimpleName() + " "
				+ curMethod.getName() + "(" + sbCls.toString() + ")】方法前进行拦截");
	}

	protected void doAfter(Object proxy, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		Class<?> curClass = proxy.getClass().getSuperclass();
		Method curMethod = method;
		Class<?>[] claAry = method.getParameterTypes();
		StringBuffer sbCls = new StringBuffer();
		for (int i = 0; i < claAry.length; i++) {
			Class<?> cls = claAry[i];
			if (i != 0)
				sbCls.append(",");
			sbCls.append(cls.getSimpleName());
		}
		System.out.println("调用【" + curClass.getSimpleName() + "】类中的【"
				+ method.getReturnType().getSimpleName() + " "
				+ curMethod.getName() + "(" + sbCls.toString() + ")】方法后进行拦截");
	}
}
