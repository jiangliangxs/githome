package zj.reflect.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import zj.reflect.bean.AutowiredBeanFormMap;
import zj.reflect.bean.AutowiredMapFormBean;

/**
 * 类名 ：AutowiredUtil<br>
 * 概况 ：注入工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class AutowiredUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 从Map注入Bean
	 * 
	 * @param objInstance
	 * @param map
	 */
	public static void setBeanFromMap(Map<String, Object> map, Object objInstance) throws Exception {
		if (map == null || objInstance == null) {
			throw new Exception("map或objInstance未赋值");
		} else {
			Map<Method, Object> methodsMap = MethodUtil.getMethodsMap(objInstance);
			for (Method method : methodsMap.keySet()) {
				// 取得方法上的注解
				if (method.isAnnotationPresent(AutowiredBeanFormMap.class)) {
					// 取得注解类
					AutowiredBeanFormMap ann = method.getAnnotation(AutowiredBeanFormMap.class);
					if (ann != null && (method.isAccessible() || Modifier.isPublic(method.getModifiers()))) {
						Object methodValue = null;
						// 如果有注解并且可访问或者是公共方法
						// 获取方法名
						String methodName = method.getName();
						// 获取属性的注解key
						String keyValue = ann.key();
						if (keyValue == null || keyValue.trim().equals("")) {
							// 如果方法以set开头
							if (methodName.startsWith("set")) {
								if (methodName.length() > 3) {
									// 如果非set方法
									String fieldName = methodName.substring(3);
									// 获取方法对应的属性名
									fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
									// 根据属性名获取map中的值
									methodValue = map.get(fieldName);
									if (methodValue != null) {
										// 如果map中的值存在,则设置方法值
										MethodUtil.invoke(objInstance, methodName, new Class[] { methodValue.getClass() }, new Object[] { methodValue });
									}
								} else {
									// 如果是set方法
									// 获取map中的值
									methodValue = map.get(methodName);
									if (methodValue != null) {
										// 如果map中的值存在,则设置方法值
										MethodUtil.invoke(objInstance, methodName, new Class[] { methodValue.getClass() }, new Object[] { methodValue });
									}
								}
							} else {
								// 获取map中的值
								methodValue = map.get(methodName);
								if (methodValue != null) {
									// 如果map中的值存在,则设置方法值
									MethodUtil.invoke(objInstance, methodName, new Class[] { methodValue.getClass() }, new Object[] { methodValue });
								}
							}
						} else {
							// 如果注解的key存在
							// 根据注解的key获取map中的值
							methodValue = map.get(keyValue);
							// 设置map中的值到类的方法中
							MethodUtil.invoke(objInstance, methodName, new Class[] { methodValue.getClass() }, new Object[] { methodValue });
						}
					}
				}
			}
			// 取得属性值
			Map<Field, Object> fieldsMap = FieldUtil.getFieldsMap(objInstance, true);
			for (Field field : fieldsMap.keySet()) {
				// 取得属性上的注解
				if (field.isAnnotationPresent(AutowiredBeanFormMap.class)) {
					// 取得注解类
					AutowiredBeanFormMap ann = field.getAnnotation(AutowiredBeanFormMap.class);
					if (ann != null) {
						// 获取注解中的key
						String keyValue = ann.key();
						String fieldName = field.getName();
						if (keyValue == null || keyValue.trim().equals("")) {
							// 根据属性名获取map中的值
							FieldUtil.set(objInstance, fieldName, true, map.get(fieldName));
						} else {
							// 根据注解的key获取map中的值
							FieldUtil.set(objInstance, fieldName, true, map.get(keyValue));
						}
					}
				}
			}
		}
	}

	/**
	 * 从Bean注入到Map
	 * 
	 * @param objInstance
	 * @param map
	 */
	public static Map<String, Object> getMapFromBean(Object objInstance) throws Exception {
		Map<String, Object> map = null;
		if (objInstance == null) {
			throw new Exception("objInstance未赋值");
		} else {
			map = new HashMap<String, Object>();
			Map<Method, Object> methodsMap = MethodUtil.getMethodsMap(objInstance);
			for (Method method : methodsMap.keySet()) {
				Object methodValue = methodsMap.get(method);
				// 取得方法上的注解
				if (method.isAnnotationPresent(AutowiredMapFormBean.class)) {
					// 取得方法上的注解
					AutowiredMapFormBean ann = method.getAnnotation(AutowiredMapFormBean.class);
					if (ann != null && Modifier.isPublic(method.getModifiers())) {
						// 如果注解存在
						String keyValue = ann.key();
						if (keyValue == null || keyValue.trim().equals("")) {
							String methodName = method.getName();
							if (methodName.startsWith("get")) {
								if (methodName.startsWith("get") && methodName.length() > 3) {
									String fieldName = methodName.substring(3);
									fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
									map.put(fieldName, methodValue);
								} else {
									map.put(methodName, methodValue);
								}
							} else {
								map.put(methodName, methodValue);
							}
						} else {
							map.put(keyValue, methodValue);
						}
					}
				}
			}
			Map<Field, Object> fieldsMap = FieldUtil.getFieldsMap(objInstance, true);
			for (Field field : fieldsMap.keySet()) {
				Object fieldValue = fieldsMap.get(field);
				if (field.isAnnotationPresent(AutowiredMapFormBean.class)) {
					AutowiredMapFormBean ann = field.getAnnotation(AutowiredMapFormBean.class);
					if (ann != null) {
						String keyValue = ann.key();
						if (keyValue == null || keyValue.trim().equals("")) {
							String fieldName = field.getName();
							map.put(fieldName, fieldValue);
						} else {
							map.put(keyValue, fieldValue);
						}
					}
				}
			}
		}
		return map;
	}
}
