package zj.reflect.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.log4j.Logger;

import zj.check.util.CheckUtil;
import zj.java.util.JavaUtil;
import zj.regex.util.RegexUtil;

/**
 * 类名 ：BeanUtil<br>
 * 概况 ：java Bean 单例工厂<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class BeanUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(BeanUtil.class);
	private static final Map<String, Object> CLASS_MAP = new ConcurrentHashMap<String, Object>();

	/**
	 * 通过类获取实体bean
	 * 
	 * @param clazz
	 * @return
	 */
	public synchronized static <T> T getBean(Class<T> clazz) {
		return getBean(clazz, null, null);
	}

	/**
	 * 通过类获取实体bean
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @param initargs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized static <T> T getBean(Class<T> clazz, Class<?>[] parameterTypes, Object[] initargs) {
		T classT = null;
		try {
			String className = clazz.getName();
			Object classBean = CLASS_MAP.get(className);
			if (classBean == null) {
				Class<?> newBeanClass = Class.forName(className);
				Constructor<?> cons = newBeanClass.getDeclaredConstructor(parameterTypes);
				cons.setAccessible(true);
				classBean = cons.newInstance(initargs);
				CLASS_MAP.put(className, classBean);
				log.debug("第一次获取类,设置实例至Map中:" + classBean);
			} else {
				log.debug("非第一次获取类:" + classBean);
			}
			log.debug("Map中的实例类个数:" + CLASS_MAP.size());
			// 返回类实例
			classT = (T) classBean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classT;
	}

	/**
	 * 获得对象属性的值
	 * 
	 * @param fieldName
	 *            字段名
	 * @param obj
	 *            对象
	 * @return 字段属性值
	 */
	public static Object getValue(String fieldName, Object obj) {
		try {
			if (obj == null) {
				return null;
			}
			return Ognl.getValue(fieldName, obj);
		} catch (OgnlException e) {
			log.debug("对象" + obj.getClass() + "没有" + fieldName + "属性!");
			// e.printStackTrace();
		}
		return null;
	}

	/** 给对象属性设值 */
	public static void setValue(String attributeName, Object obj, Object attributeValue) {
		try {
			Ognl.setValue(attributeName, obj, attributeValue);
		} catch (OgnlException e) {
			log.error("对象" + obj.getClass().getName() + "属性" + attributeName + "设置值:" + attributeValue + "失败!");
			e.printStackTrace();
		}
	}


	/**
	 * 把object对象中属性的值复制到map中
	 * <p>
	 * map中的key:属性名称,value:属性值
	 * 
	 * @param obj
	 *            原对象
	 * @param map
	 */
	public static Map<String, Object> copyObjToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> filedNames = getFileds(obj.getClass());
		if (filedNames.size() == 0) {
			return map;
		}
		for (String name : filedNames) {
			String key = name;
			Object value = getValue(name, obj);
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 把object对象中属性的值复制到map中
	 * <p>
	 * map中的key:属性名称,value:属性值
	 * 
	 * @param obj
	 *            原对象
	 * @param map
	 */
	public static Map<String, Object> copyObjToMapConvert(Object obj) {
		Map<String, Object> map = BeanUtil.copyObjToMap(obj);
		try {
			String sort = JavaUtil.objToStr(map.get("sort"));
			if (CheckUtil.isNotNull(sort)) {
				map.put("sort", RegexUtil.convertJavaToDbField(sort));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取对象中有set开头方法的属性名称
	 * 
	 * @param clz
	 *            Class对象
	 * @return 属性名称集合
	 */
	public static List<String> getFileds(Class<?> clz) {
		List<String> arrayList = new ArrayList<String>();
		Method methods[] = clz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.length() > 3 && methodName.substring(0, 3).toString().equals("set")) {
				String name = methodName.substring(3);
				arrayList.add(JavaUtil.firstLowerCase(name));
			}
		}
		return arrayList;
	}
}
