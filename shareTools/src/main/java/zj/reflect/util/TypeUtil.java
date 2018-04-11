package zj.reflect.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import zj.date.util.DateUtil;
import zj.java.util.JavaUtil;
import zj.reflect.service.NumericTypesI;

/**
 * 类型转换器工具类
 * 
 * @author zhangjun
 * 
 */
public class TypeUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TypeUtil.class);

	/**
	 * 基本类型属性(int,byte,char,float,double,boolean,short)
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class Primitive {
		/** 基本属性默认值 **/
		public static final Map<Class<?>, Object> _PRIMITIVE_DEFAULTS = Collections.synchronizedMap(new HashMap<Class<?>, Object>());
		static {
			// 基本类型 默认值
			// byte 0
			// short 0
			// int 0
			// long 0L
			// float 0.0f
			// double 0.0d
			// char '\u0000'
			// boolean false
			_PRIMITIVE_DEFAULTS.put(Boolean.TYPE, Boolean.FALSE);
			_PRIMITIVE_DEFAULTS.put(Boolean.class, Boolean.FALSE);
			_PRIMITIVE_DEFAULTS.put(Byte.TYPE, new Byte((byte) 0));
			_PRIMITIVE_DEFAULTS.put(Byte.class, new Byte((byte) 0));
			_PRIMITIVE_DEFAULTS.put(Short.TYPE, new Short((short) 0));
			_PRIMITIVE_DEFAULTS.put(Short.class, new Short((short) 0));
			_PRIMITIVE_DEFAULTS.put(Character.TYPE, new Character((char) 0));
			_PRIMITIVE_DEFAULTS.put(Character.class, new Character((char) 0));
			_PRIMITIVE_DEFAULTS.put(Integer.TYPE, new Integer(0));
			_PRIMITIVE_DEFAULTS.put(Integer.class, new Integer(0));
			_PRIMITIVE_DEFAULTS.put(Long.TYPE, new Long(0L));
			_PRIMITIVE_DEFAULTS.put(Long.class, new Long(0L));
			_PRIMITIVE_DEFAULTS.put(Float.TYPE, new Float(0.0f));
			_PRIMITIVE_DEFAULTS.put(Float.class, new Float(0.0f));
			_PRIMITIVE_DEFAULTS.put(Double.TYPE, new Double(0.0));
			_PRIMITIVE_DEFAULTS.put(Double.class, new Double(0.0));
			_PRIMITIVE_DEFAULTS.put(BigInteger.class, new BigInteger("0"));
			_PRIMITIVE_DEFAULTS.put(BigDecimal.class, new BigDecimal(0.0));
		}

		/**
		 * 出错返回0
		 * 
		 * @param value
		 * @return
		 */
		public static long longValueNoCatchError(final Object value) {
			if (value == null)
				return 0L;
			Class<?> c = value.getClass();
			if (c.getSuperclass() == Number.class)
				return ((Number) value).longValue();
			if (c == Boolean.class)
				return ((Boolean) value).booleanValue() ? 1 : 0;
			if (c == Character.class)
				return Long.valueOf(((Character) value).charValue()).longValue();
			return Long.parseLong(JavaUtil.trim(JavaUtil.objToStr(value)));
		}

		/**
		 * 出错返回0
		 * 
		 * @param value
		 * @return
		 */
		public static long longValue(Object value) {
			try {
				return longValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换long", e);
				return 0L;
			}
		}

		/**
		 * 出错返回0.0
		 * 
		 * @param value
		 * @return
		 */
		public static double doubleValueNoCatchError(Object value) {
			if (value == null)
				return 0.0;
			Class<?> c = value.getClass();
			if (c.getSuperclass() == Number.class)
				return ((Number) value).doubleValue();
			if (c == Boolean.class)
				return ((Boolean) value).booleanValue() ? 1 : 0;
			if (c == Character.class)
				return Double.valueOf(((Character) value).charValue()).doubleValue();
			String s = JavaUtil.trim(JavaUtil.objToStr(value));
			return (s.length() == 0) ? 0.0 : Double.parseDouble(s);
		}

		/**
		 * 出错返回0.0
		 * 
		 * @param value
		 * @return
		 */
		public static double doubleValue(Object value) {
			try {
				return doubleValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换double", e);
				return 0.0;
			}
		}

		/**
		 * 出错返回0.0
		 * 
		 * @param value
		 * @return
		 */
		public static float floatValueNoCatchError(Object value) {
			return new Float(doubleValueNoCatchError(value)).floatValue();
		}

		/**
		 * 出错返回0.0
		 * 
		 * @param value
		 * @return
		 */
		public static float floatValue(Object value) {
			try {
				return floatValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换float", e);
				return 0.0f;
			}
		}

		/**
		 * 出错返回0
		 * 
		 * @param value
		 * @return
		 */
		public static BigInteger bigIntValueNoCatchError(Object value) {
			if (value == null)
				return BigInteger.valueOf(0L);
			Class<?> c = value.getClass();
			if (c == BigInteger.class)
				return (BigInteger) value;
			if (c == BigDecimal.class)
				return ((BigDecimal) value).toBigInteger();
			if (c.getSuperclass() == Number.class)
				return BigInteger.valueOf(((Number) value).longValue());
			if (c == Boolean.class)
				return BigInteger.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
			if (c == Character.class)
				return BigInteger.valueOf(((Character) value).charValue());
			return new BigInteger(JavaUtil.trim(JavaUtil.objToStr(value)));
		}

		/**
		 * 出错返回0
		 * 
		 * @param value
		 * @return
		 */
		public static BigInteger bigIntValue(Object value) {
			try {
				return bigIntValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换bingInt", e);
				return BigInteger.valueOf(0L);
			}
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static BigDecimal bigDecValueNoCatchError(Object value) {
			if (value == null)
				return BigDecimal.valueOf(0L);
			Class<?> c = value.getClass();
			if (c == BigDecimal.class)
				return (BigDecimal) value;
			if (c == BigInteger.class)
				return new BigDecimal((BigInteger) value);
			if (c == Boolean.class)
				return BigDecimal.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
			if (c == Character.class)
				return BigDecimal.valueOf(((Character) value).charValue());
			return new BigDecimal(JavaUtil.trim(JavaUtil.objToStr(value)));
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static BigDecimal bigDecValue(Object value) {
			try {
				return bigDecValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换bigDec", e);
				return BigDecimal.valueOf(0L);
			}
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static int intValueNoCatchError(Object value) {
			if (value == null)
				return 0;
			if (Number.class.isInstance(value)) {
				return ((Number) value).intValue();
			}
			Class<?> c = value.getClass();
			if (c == Character.class)
				return Integer.valueOf(((Character) value).charValue()).intValue();
			String str = String.class.isInstance(value) ? (String) value : value.toString();
			return Integer.parseInt(str);
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static int intValue(Object value) {
			try {
				return intValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换int", e);
				return 0;
			}
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static boolean booleanValueNoCatchError(Object value) {
			if (value == null)
				return false;
			Class<?> c = value.getClass();
			if (c == Boolean.class)
				return ((Boolean) value).booleanValue();
			if (c == Character.class)
				return ((Character) value).charValue() != 0;
			if (value instanceof Number)
				return ((Number) value).doubleValue() != 0;
			if (value instanceof String)
				return "1".equalsIgnoreCase(value.toString()) || "true".equalsIgnoreCase(value.toString()) || "y".equalsIgnoreCase(value.toString()) || "yes".equalsIgnoreCase(value.toString()) ? true : false;
			return false;
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static boolean booleanValue(Object value) {
			try {
				return booleanValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换boolean", e);
				return false;
			}
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static byte byteValueNoCatchError(Object value) {
			return (byte) longValue(value);
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static byte byteValue(Object value) {
			try {
				return byteValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换byte", e);
				return 0;
			}
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static char charValueNoCatchError(Object value) {
			return (char) longValue(value);
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static char charValue(Object value) {
			try {
				return charValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换char", e);
				return '\u0000';
			}
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static short shortValueNoCatchError(Object value) {
			return (short) longValue(value);
		}

		/**
		 * 
		 * @param value
		 * @return
		 */
		public static short shortValue(Object value) {
			try {
				return shortValueNoCatchError(value);
			} catch (Exception e) {
				logger.error("转换short", e);
				return 0;
			}
		}

		/**
		 * 基本类型值
		 * 
		 * @param toType
		 * @param value
		 * @return 如果抛异常说明没有验证成功,否则返回验证的类型值
		 */
		public static Object checkTypeValue(Class<?> toType, Object value) throws Exception {
			if (value == null)
				return null;
			if (toType.isInstance(value)) {
				return value;
			}
			if ((toType == Integer.class) || (toType == Integer.TYPE))
				return intValueNoCatchError(value);
			if ((toType == Double.class) || (toType == Double.TYPE))
				return doubleValueNoCatchError(value);
			if ((toType == Boolean.class) || (toType == Boolean.TYPE))
				return booleanValueNoCatchError(value);
			if ((toType == Byte.class) || (toType == Byte.TYPE))
				return byteValueNoCatchError(value);
			if ((toType == Character.class) || (toType == Character.TYPE))
				return charValueNoCatchError(value);
			if ((toType == Short.class) || (toType == Short.TYPE))
				return shortValueNoCatchError(value);
			if ((toType == Long.class) || (toType == Long.TYPE))
				return longValueNoCatchError(value);
			if ((toType == Float.class) || (toType == Float.TYPE))
				return floatValueNoCatchError(value);
			if (toType == BigInteger.class)
				return bigIntValueNoCatchError(value);
			if (toType == BigDecimal.class)
				return bigDecValueNoCatchError(value);
			if (value instanceof Number)
				return doubleValueNoCatchError(value);
			throw new IllegalArgumentException("Unable to convert primitive type " + value.getClass().getName() + " of " + value + " to type of " + toType.getName());
		}

		/**
		 * 取得基本类型
		 * 
		 * @param value
		 * @return
		 */
		public static int getNumericType(Object value) {
			if (value == null)
				return NumericTypesI.NONNUMERIC;
			Class<?> c = value.getClass();
			if (c == Integer.class)
				return NumericTypesI.INT;
			if (c == Double.class)
				return NumericTypesI.DOUBLE;
			if (c == Boolean.class)
				return NumericTypesI.BOOL;
			if (c == Byte.class)
				return NumericTypesI.BYTE;
			if (c == Character.class)
				return NumericTypesI.CHAR;
			if (c == Short.class)
				return NumericTypesI.SHORT;
			if (c == Long.class)
				return NumericTypesI.LONG;
			if (c == Float.class)
				return NumericTypesI.FLOAT;
			if (c == BigInteger.class)
				return NumericTypesI.BIGINT;
			if (c == BigDecimal.class)
				return NumericTypesI.BIGDEC;
			return NumericTypesI.NONNUMERIC;
		}

		/**
		 * 取得基本类型,转换成String
		 * 
		 * @see 包括String类型
		 * @see 包括date类型:转换成yyyy-MM-dd HH:mm:ss
		 * @param value
		 * @return
		 */
		public static String getNumericValue(Object value) {
			return getNumericValue(value, null);
		}

		/**
		 * 取得基本类型,转换成String
		 * 
		 * @see 包括String类型
		 * @see 包括date类型:转换成dateFormat
		 * @param value
		 * @param format
		 * @return
		 */
		public static String getNumericValue(Object value, Map<String, Object> format) {
			if (value == null)
				return null;
			Class<?> c = value.getClass();
			if (c == String.class)
				return value.toString();
			if (c == Integer.class)
				return "" + intValueNoCatchError(value);
			if (c == Double.class)
				return "" + doubleValueNoCatchError(value);
			if (c == Boolean.class)
				return "" + booleanValueNoCatchError(value);
			if (c == Byte.class)
				return "" + byteValueNoCatchError(value);
			if (c == Character.class)
				return "" + charValueNoCatchError(value);
			if (c == Short.class)
				return "" + shortValueNoCatchError(value);
			if (c == Long.class)
				return "" + longValueNoCatchError(value);
			if (c == Float.class)
				return "" + floatValueNoCatchError(value);
			if (c == BigInteger.class)
				return "" + bigIntValueNoCatchError(value);
			if (c == BigDecimal.class)
				return "" + bigDecValueNoCatchError(value);
			if (c == Date.class) {
				String dateFormat = (dateFormat = (format == null ? null : JavaUtil.objToStr(format.get("date")))) == null ? "yyyy-MM-dd HH:mm:ss" : dateFormat;
				return DateUtil.dateParse(value, dateFormat);
			}
			return null;
		}

		/**
		 * 取得默认值
		 * 
		 * @param forClass
		 * @return
		 */
		public static Object getPrimitiveDefaultValue(Class<?> forClass) {
			return _PRIMITIVE_DEFAULTS.get(forClass);
		}
	}

	/**
	 * 其它类型属性
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class Other {
		/**
		 * 基本类型值(包含string)
		 * 
		 * @param toType
		 * @param value
		 * @return 如果抛异常说明没有验证成功,否则返回验证的类型值
		 */
		public static Object checkTypeValue(Class<?> toType, Object value) throws Exception {
			if (value == null)
				return null;
			if (toType.isInstance(value))
				return value;
			if (toType == String.class)
				return JavaUtil.trim(JavaUtil.objToStr(value));
			if (toType == Date.class)
				return DateUtil.parseDate(JavaUtil.trim(JavaUtil.objToStr(value)));
			throw new IllegalArgumentException("Unable to convert primitive type " + value.getClass().getName() + " of " + value + " to type of " + toType.getName());
		}
	}

	/**
	 * 所有类型值
	 * 
	 * @param toType
	 * @param value
	 * @return 如果抛异常说明没有验证成功,否则返回验证的类型值
	 */
	public static Object checkTypeValue(Class<?> toType, Object value) throws Exception {
		Object result = null;
		try {
			result = TypeUtil.Primitive.checkTypeValue(toType, value);
		} catch (Exception e) {
			try {
				result = TypeUtil.Other.checkTypeValue(toType, value);
			} catch (Exception e2) {
				throw new IllegalArgumentException("Unable to convert primitive type " + value.getClass().getName() + " of " + value + " to type of " + toType.getName());
			}
		}
		return result;
	}
}