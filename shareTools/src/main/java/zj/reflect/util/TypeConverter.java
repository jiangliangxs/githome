package zj.reflect.util;

import java.lang.reflect.Array;

import zj.java.util.JavaUtil;

/**
 * 类名 ：TypeConverter<br>
 * 概况 ：类型转换类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class TypeConverter {
	/**
	 * 转换值
	 * 
	 * @param value
	 * @param toType
	 * @param preventNulls
	 *            :true取得默认值
	 * @return
	 */
	public static Object convertValue(Class<?> toType, Object value, boolean preventNulls) throws Exception {
		Object result = null;
		if (value == null) {
			if (preventNulls) {
				if (toType == Boolean.class) {
					result = Boolean.FALSE;
				} else if (Number.class.isAssignableFrom(toType)) {
					result = TypeUtil.Primitive.getPrimitiveDefaultValue(toType);
				}
			} else {
				if (toType.isPrimitive()) {
					result = TypeUtil.Primitive.getPrimitiveDefaultValue(toType);
				}
			}
		} else {
			if (toType.isAssignableFrom(value.getClass()))
				return value;
			/* If array -> array then convert components of array individually */
			if (value.getClass().isArray()) {
				if (toType.isArray()) {
					Class<?> componentType = toType.getComponentType();
					result = Array.newInstance(componentType, Array.getLength(value));
					for (int i = 0, icount = Array.getLength(value); i < icount; i++) {
						Array.set(result, i, convertValue(componentType,Array.get(value, i)));
					}
				} else {
					return convertValue(toType,Array.get(value, 0));
				}
			} else {
				if (toType.isArray()) {
					if (toType.getComponentType() == Character.TYPE) {
						result = JavaUtil.objToStr(value).toCharArray();
					} else if (toType.getComponentType() == Object.class) {
						return new Object[] { value };
					}
				} else {
					result = TypeUtil.checkTypeValue(toType, value);
				}
			}
		}
		if (result == null && preventNulls)
			return value;
		if (value != null && result == null) {
			throw new IllegalArgumentException("Unable to convert type " + value.getClass().getName() + " of " + value + " to type of " + toType.getName());
		}
		return result;
	}

	/**
	 * 
	 * @param value
	 * @param toType
	 * @return
	 */
	public static Object convertValue(Class<?> toType, Object value) throws Exception {
		return convertValue(toType, value, false);
	}
}
