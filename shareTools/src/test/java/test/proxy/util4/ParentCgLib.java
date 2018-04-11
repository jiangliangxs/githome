/*
 * 张军项目模板
 * Copyright (c) 2011张军版权所有 All Rights Reserved.
 * 版权制作
 *   ver 1.0 : 2011.11.08 首次版本
 */
package test.proxy.util4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.Callback;
/**
 * 系统名：张军项目模板<br>
 * 类名 ：ParentCgLib<br>
 * 概况 ：ParentCgLib类<br>
 *
 * @version 1.00 （2011/11/08）
 * @author SHNKCS 张军 {@link <a
 *         href=http://user.qzone.qq.com/360901061>张军QQ空间</a>}
 */
@SuppressWarnings("unchecked")
public class ParentCgLib {

	public <T> T getCgLibInstance(T superObj) throws Exception {
		return getCgLibInstance(superObj, null, null,
				new MethodInterceptorImpl());
	}

	public <T> T getCgLibInstance(T superObj, Callback callback)
			throws Exception {
		return getCgLibInstance(superObj, null, null, callback);
	}

	public <T> T getCgLibInstance(T superObj, Class<?>[] argumentTypes,
			Object[] arguments, Callback callback) throws Exception {
		Class<?> superClass = null;
		boolean isClass = false;
		if (superObj instanceof Class<?>) {
			superClass = (Class<?>) superObj;
			isClass = true;
		} else {
			superClass = superObj.getClass();
			isClass = false;
		}
		net.sf.cglib.proxy.Enhancer en = new net.sf.cglib.proxy.Enhancer();
		en.setSuperclass(superClass);
		en.setCallback(callback);
		Object objInstance = null;
		if (argumentTypes != null && arguments != null
				&& argumentTypes.length == arguments.length) {
			objInstance = en.create(argumentTypes, arguments);
		} else {
			objInstance = en.create();
		}
		if (!isClass)
			objInstance = rtnSetFieldsObj(superObj, objInstance);
		return (T) objInstance;
	}

	private <T>Object rtnSetFieldsObj(T superObj, Object objInstance)
			throws Exception {
		Class<?> superClass = superObj.getClass();
		Field[] fields = superClass.getDeclaredFields();
		setFields(superObj, objInstance, fields);
		while (!"java.lang.Object".equals((superClass = superClass
				.getSuperclass()).getName())) {
			fields = superClass.getDeclaredFields();
			setFields(superObj, objInstance, fields);
		}
		return objInstance;
	}

	private <T>void setFields(T superObj, Object objInstance, Field[] fields)
			throws Exception {
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isFinal(mod)) {
				continue;
			}
			field.setAccessible(true);
			field.set(objInstance, field.get(superObj));
		}
	}
}
