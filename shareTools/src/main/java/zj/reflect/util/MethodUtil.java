package zj.reflect.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 类名 ：MethodUtil<br>
 * 概况 ：方法工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class MethodUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(MethodUtil.class);

	/**
	 * 只要该类及继承类有public方法即可调用当前方法
	 * 
	 * @param objInstance
	 * @param methodName
	 * @return
	 */
	public static Object invoke(Object objInstance, String methodName) throws Exception {
		return invoke(objInstance, methodName, false);
	}

	/**
	 * 只要该类及继承类有public方法即可调用当前方法
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param accessibleFlg
	 * @return
	 */
	public static Object invoke(Object objInstance, String methodName, boolean accessibleFlg) throws Exception {
		return invoke(objInstance, methodName, null, null, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public方法即可调用
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param classes
	 * @param objects
	 * @return
	 */
	public static Object invoke(Object objInstance, String methodName, Class<?>[] classes, Object[] objects) throws Exception {
		return invoke(objInstance, methodName, classes, objects, false, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public方法即可调用
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param classes
	 * @param objects
	 * @param accessibleFlg
	 * @return
	 */
	public static Object invoke(Object objInstance, String methodName, Class<?>[] classes, Object[] objects, boolean accessibleFlg) throws Exception {
		return invoke(objInstance, methodName, classes, objects, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public方法即可调用
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param classes
	 * @param objects
	 * @param accessibleFlg
	 * @param clazz
	 * @return
	 */
	private static Object invoke(Object objInstance, String methodName, Class<?>[] classes, Object[] objects, boolean accessibleFlg, Class<?> clazz) throws Exception {
		if (objInstance == null)
			return null;
		Object objValue = null;
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, classes);
			method.setAccessible(accessibleFlg);
			if (method.isAccessible() || (Modifier.isPublic(clazz.getModifiers()) && Modifier.isPublic(method.getModifiers()))) {
				objValue = method.invoke(objInstance, objects);
			} else {
				throw new Exception("非public访问类或属性无法调用值【类名:" + clazz + ",访问属性:" + Modifier.toString(clazz.getModifiers()) + ",属性名:" + method.getName() + ",访问属性:" + Modifier.toString(method.getModifiers()) + "】");
			}
		} catch (NoSuchMethodException em) {
			if ("java.lang.Object".equals(clazz.getName())) {
				log.info("已经达到顶层类,退出");
				throw em;
			} else {
				log.info("回调父类方法:" + em.getMessage());
				return invoke(objInstance, methodName, classes, objects, accessibleFlg, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
		return objValue;
	}

	/**
	 * 赋当前类的所有方法值到Map
	 * 
	 * @param objInstance
	 * @return
	 */
	public static Map<Method, Object> getMethodsMap(Object objInstance) throws Exception {
		return getMethodsMap(objInstance, false);
	}

	/**
	 * 赋当前类的所有方法值到Map
	 * 
	 * @param objInstance
	 * @param accessibleFlg
	 * @return
	 */
	public static Map<Method, Object> getMethodsMap(Object objInstance, boolean accessibleFlg) throws Exception {
		Map<Method, Object> params = new HashMap<Method, Object>();
		return getMethodsMap(params, objInstance, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 赋当前类的所有方法值到Map
	 * 
	 * @param objInstance
	 * @param accessibleFlg
	 * @param clazz
	 * @return
	 */
	private static Map<Method, Object> getMethodsMap(Map<Method, Object> params, Object objInstance, boolean accessibleFlg, Class<?> clazz) throws Exception {
		try {
			// 获取当前类的所有方法
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				// 设置访问属性
				method.setAccessible(accessibleFlg);
				// 如果方法可访问或者是public
				if (method.isAccessible() || (Modifier.isPublic(clazz.getModifiers()) && Modifier.isPublic(method.getModifiers()))) {
					// 获取方法的返回类型
					Class<?> clsRtnType = method.getReturnType();
					// 获取返回类型的名
					String rtnTypeName = clsRtnType.getName();
					// 获取方法的参数类型
					Class<?>[] clsParamType = method.getParameterTypes();
					// 如果返回类型为void
					if ("void".equals(rtnTypeName)) {
						// 设置方法到map
						params.put(method, null);
					} else {
						// 如果无参数类型
						if (clsParamType.length > 0) {
							continue;
						} else {
							// 获取方法返回的值
							Object rtnValue = method.invoke(objInstance);
							// 设置方法返回的值
							params.put(method, rtnValue == null ? null : rtnValue);
						}
					}
				} else {
					String errorMsg = "非public访问类或属性无法设置值【类名:" + clazz + ",访问属性:" + Modifier.toString(clazz.getModifiers()) + ",属性名:" + method.getName() + ",访问属性:" + Modifier.toString(method.getModifiers()) + "】";
					log.error(errorMsg);
				}
			}
			if ("java.lang.Object".equals(clazz.getName())) {
				// 如果类是顶层类直接返回
				log.info("已经达到顶层类,退出");
				return params;
			} else {
				// 回调方法
				return getMethodsMap(params, objInstance, accessibleFlg, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 只要该类及继承类有public方法即可调用当前方法
	 * 
	 * @param objInstance
	 * @param methodName
	 * @return
	 */
	public static Object[] invokeDefineReturnResult(Object objInstance, String methodName) throws Exception {
		return invokeDefineReturnResult(objInstance, methodName, false);
	}

	/**
	 * 只要该类及继承类有public方法即可调用当前方法
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param accessibleFlg
	 * @return
	 */
	public static Object[] invokeDefineReturnResult(Object objInstance, String methodName, boolean accessibleFlg) throws Exception {
		return invokeDefineReturnResult(objInstance, methodName, null, null, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public方法即可调用
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param classes
	 * @param objects
	 * @return
	 */
	public static Object[] invokeDefineReturnResult(Object objInstance, String methodName, Class<?>[] classes, Object[] objects) throws Exception {
		return invokeDefineReturnResult(objInstance, methodName, classes, objects, false, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public方法即可调用
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param classes
	 * @param objects
	 * @param accessibleFlg
	 * @return
	 */
	public static Object[] invokeDefineReturnResult(Object objInstance, String methodName, Class<?>[] classes, Object[] objects, boolean accessibleFlg) throws Exception {
		return invokeDefineReturnResult(objInstance, methodName, classes, objects, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public方法即可调用
	 * 
	 * @param objInstance
	 * @param methodName
	 * @param classes
	 * @param objects
	 * @param accessibleFlg
	 * @param clazz
	 * @return
	 */
	private static Object[] invokeDefineReturnResult(Object objInstance, String methodName, Class<?>[] classes, Object[] objects, boolean accessibleFlg, Class<?> clazz) throws Exception {
		if (objInstance == null)
			return null;
		Object objValue = null;
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, classes);
			method.setAccessible(accessibleFlg);
			if (method.isAccessible() || (Modifier.isPublic(clazz.getModifiers()) && Modifier.isPublic(method.getModifiers()))) {
				objValue = method.invoke(objInstance, objects);
			} else {
				throw new Exception("非public访问类或属性无法调用值【类名:" + clazz + ",访问属性:" + Modifier.toString(clazz.getModifiers()) + ",属性名:" + method.getName() + ",访问属性:" + Modifier.toString(method.getModifiers()) + "】");
			}
		} catch (NoSuchMethodException em) {
			if ("java.lang.Object".equals(clazz.getName())) {
				log.info("已经达到顶层类,退出");
				throw em;
			} else {
				log.info("回调父类方法:" + em.getMessage());
				return invokeDefineReturnResult(objInstance, methodName, classes, objects, accessibleFlg, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
		return new Object[] { method, objValue };
	}
}
