package zj.reflect.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import zj.java.util.JavaUtil;

/**
 * 属性工具类
 * 
 * @author zhangjun
 * 
 */
public class FieldUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(FieldUtil.class);

	/**
	 * 只要该类及继承类有public属性即可获取属性值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param classes
	 * @param objects
	 * @return
	 */
	public static Object get(Object objInstance, String fieldName) throws Exception {
		return get(objInstance, fieldName, false, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public属性即可获取属性值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param accessibleFlg
	 * @return
	 */
	public static Object get(Object objInstance, String fieldName, boolean accessibleFlg) throws Exception {
		Object value = null;
		String[] fieldNames = JavaUtil.aryTrimValue(JavaUtil.split(fieldName, "."));
		if (fieldNames.length == 0) {
			throw new Exception("属性名不能为空");
		} else if (fieldNames.length == 1) {
			value = get(objInstance, fieldName, accessibleFlg, objInstance.getClass());
		} else {
			for (String name : fieldNames) {
				value = get(objInstance, name, accessibleFlg, objInstance.getClass());
				if (value == null) {
					throw new Exception("源对象属性名:【" + name + "】不能为NULL");
				} else {
					// objInstance对象已改变了
					objInstance = value;
				}
			}
		}
		return value;
		// return get(objInstance, fieldName, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public属性即可获取属性值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param accessibleFlg
	 * @param clazz
	 * @return
	 */
	private static Object get(Object objInstance, String fieldName, boolean accessibleFlg, Class<?> clazz) throws Exception {
		Object objValue = null;
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(accessibleFlg);
			if (field.isAccessible() || (Modifier.isPublic(clazz.getModifiers()) && Modifier.isPublic(field.getModifiers()))) {
				// 如果class没有设置成public,则无法获取属性值
				objValue = field.get(objInstance);
			} else {
				throw new Exception("非public访问类或属性无法调用值【类名:" + clazz + ",访问属性:" + Modifier.toString(clazz.getModifiers()) + ",属性名:" + fieldName + ",访问属性:" + Modifier.toString(field.getModifiers()) + "】");
			}
		} catch (NoSuchFieldException em) {
			if ("java.lang.Object".equals(clazz.getName())) {
				log.debug("已经达到顶层类,退出");
				throw em;
			} else {
				log.debug("回调父类获取属性值:" + em.getMessage());
				return get(objInstance, fieldName, accessibleFlg, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
		return objValue;
	}

	/**
	 * 只要该类及继承类有public属性即可给属性设置值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param value
	 */
	public static void set(Object objInstance, String fieldName, Object value) throws Exception {
		set(objInstance, fieldName, false, value, objInstance.getClass());
	}

	/**
	 * 只要该类及继承类有public属性即可给属性设置值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param accessibleFlg
	 * @param value
	 */
	public static void set(Object objInstance, String fieldName, boolean accessibleFlg, Object value) throws Exception {
		String[] fieldNames = JavaUtil.aryTrimValue(JavaUtil.split(fieldName, "."));
		if (fieldNames.length == 0) {
			throw new Exception("属性名不能为空");
		} else if (fieldNames.length == 1) {
			set(objInstance, fieldName, accessibleFlg, value, objInstance.getClass());
		} else {
			String newFieldNames = "";
			String newFileName = "";
			for (int i = 0; i < fieldNames.length; i++) {
				if (i == fieldNames.length - 1) {
					newFileName = fieldNames[i];
					break;
				}
				if (!"".equals(newFieldNames)) {
					newFieldNames += ".";
				}
				newFieldNames += fieldNames[i];
			}
			// 当前objInstance对象
			Object newFieldNamesValue = FieldUtil.get(objInstance, newFieldNames, accessibleFlg);
			set(newFieldNamesValue, newFileName, accessibleFlg, value, newFieldNamesValue.getClass());
		}
	}

	/**
	 * 只要该类及继承类有public属性即可给属性设置值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param accessibleFlg
	 * @param value
	 * @param clazz
	 */
	private static void set(Object objInstance, String fieldName, boolean accessibleFlg, Object value, Class<?> clazz) throws Exception {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(accessibleFlg);
			if (field.isAccessible() || (Modifier.isPublic(clazz.getModifiers()) && Modifier.isPublic(field.getModifiers()))) {
				value = TypeConverter.convertValue(field.getType(), value);
				field.set(objInstance, value);
			} else {
				throw new Exception("非public访问类或属性无法设置值【类名:" + clazz + ",访问属性:" + Modifier.toString(clazz.getModifiers()) + ",属性名:" + fieldName + ",访问属性:" + Modifier.toString(field.getModifiers()) + "】");
			}
		} catch (NoSuchFieldException em) {
			if ("java.lang.Object".equals(clazz.getName())) {
				log.debug("已经达到顶层类,退出");
				return;
			} else {
				log.debug("回调父类设置属性值:" + em.getMessage());
				set(objInstance, fieldName, accessibleFlg, value, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 实例化属性
	 * @param objInstance
	 * @param fieldName
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void fieldNewInstance(Object objInstance,String fieldName) throws Exception{
		Map<String, Object> p = setFieldToMap(objInstance, fieldName, true);
		Map<Field, Object> params = (Map<Field, Object>) p.get("fieldMap");
		List<Field> fields = (List<Field>) p.get("fieldList");
		Object tempObj = null;
		for (Field field : fields) {
			if (params.get(field) != null) {
				continue;
			}
			Class<?> cla = field.getType();
			if (cla.isPrimitive()) {
				continue;
			}
			if (cla == String.class) {
				continue;
			}
			cla.getConstructor().setAccessible(true);
			field.setAccessible(true);
			if (tempObj == null) {
				tempObj = objInstance;
			}
			Object o = cla.newInstance();
			field.set(tempObj, o);
			tempObj = o;
		}
	}
	/**
	 * 只要该类及继承类有public属性即可给属性设置值
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param accessibleFlg
	 * @param value
	 */
	public static Map<String, Object> setFieldToMap(Object objInstance, String fieldName, boolean accessibleFlg) throws Exception {
		Map<String, Object> paramsMap= new HashMap<String, Object>();
		Map<Field, Object> params = new HashMap<Field, Object>();
		paramsMap.put("fieldMap", params);
		List<Field> fields = new ArrayList<Field>();
		paramsMap.put("fieldMap", params);
		paramsMap.put("fieldList", fields);
		String[] fieldNames = JavaUtil.aryTrimValue(JavaUtil.split(fieldName, "."));
		if (fieldNames.length == 0) {
			throw new Exception("属性名不能为空");
		} else if (fieldNames.length == 1) {
			fields.add(setFieldToMap(params, objInstance, fieldName, accessibleFlg, objInstance.getClass()));
		} else {
			for (int i = 0; i < fieldNames.length; i++) {
				Field field = setFieldToMap(params, objInstance, fieldNames[i], accessibleFlg, objInstance.getClass());
				if (field == null) {
					break;
				}
				fields.add(field);
				if (field.getType().isPrimitive()) {
					break;
				}
				Object value = params.get(field);
				if (value == null) {
					Class<?> cla = field.getType();
					cla.getConstructor().setAccessible(accessibleFlg);
					objInstance = cla.newInstance();
				}
			}
		}
		return paramsMap;
	}

	/**
	 * 
	 * @param objInstance
	 * @param fieldName
	 * @param accessibleFlg
	 * @param value
	 * @param clazz
	 */
	private static Field setFieldToMap(Map<Field, Object> params, Object objInstance, String fieldName, boolean accessibleFlg, Class<?> clazz) throws Exception {
		Object value = null;
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			value = get(objInstance, fieldName, accessibleFlg, objInstance.getClass());
			params.put(field, value);
		} catch (NoSuchFieldException em) {
			if ("java.lang.Object".equals(clazz.getName())) {
				log.debug("已经达到顶层类,退出");
				throw em;
			} else {
				log.debug("回调父类设置属性值:" + em.getMessage());
				return setFieldToMap(params, objInstance, fieldName, accessibleFlg, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
		return field;
	}

	/**
	 * 赋当前类的所有值
	 * 
	 * @param objInstance
	 * @return
	 */
	public static Map<Field, Object> getFieldsMap(Object objInstance) throws Exception {
		return getFieldsMap(objInstance, false);
	}

	/**
	 * 赋当前类的所有值
	 * 
	 * @param objInstance
	 * @param accessibleFlg
	 * @return
	 */
	public static Map<Field, Object> getFieldsMap(Object objInstance, boolean accessibleFlg) throws Exception {
		Map<Field, Object> params = new HashMap<Field, Object>();
		return getFieldsMap(params, objInstance, accessibleFlg, objInstance.getClass());
	}

	/**
	 * 赋当前类的所有值
	 * 
	 * @param objInstance
	 * @param accessibleFlg
	 * @param clazz
	 * @return
	 */
	private static Map<Field, Object> getFieldsMap(Map<Field, Object> params, Object objInstance, boolean accessibleFlg, Class<?> clazz) throws Exception {
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(accessibleFlg);
				if (field.isAccessible() || (Modifier.isPublic(clazz.getModifiers()) && Modifier.isPublic(field.getModifiers()))) {
					params.put(field, field.get(objInstance));
				} else {
					log.error("非public访问类或属性无法设置值【类名:" + clazz + ",访问属性:" + Modifier.toString(clazz.getModifiers()) + ",属性名:" + field.getName() + ",访问属性:" + Modifier.toString(field.getModifiers()) + "】");
				}
			}
			if ("java.lang.Object".equals(clazz.getName())) {
				return params;
			} else {
				return getFieldsMap(params, objInstance, accessibleFlg, clazz.getSuperclass());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static void printClassFieldsValue(Object objInstance) throws Exception {
		if (objInstance == null)
			return;
		Map<Field, Object> maps = FieldUtil.getFieldsMap(objInstance, true);
		for (Field field : maps.keySet()) {
			System.out.println(field.getName() + "=" + maps.get(field));
		}
	}
}
