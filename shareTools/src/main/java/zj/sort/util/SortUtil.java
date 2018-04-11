package zj.sort.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;

import zj.reflect.util.FieldUtil;

/**
 * 类名 ：SortUtil<br>
 * 概况 ：排序类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class SortUtil<T> implements Comparator<T> {
	private transient static final Logger log = Logger.getLogger(SortUtil.class);
	/**
	 * 升序
	 */
	public static String ASC = "asc";
	/**
	 * 降序
	 */
	public static String DESC = "desc";
	/**
	 * 是否过虑相同值
	 */
	private boolean filterEqual;
	/**
	 * 排序属性名
	 */
	private String sortName;
	/**
	 * 默认升序
	 */
	private String sortOrder = ASC;
	/**
	 * 排序类
	 */
	private T t1 = null;
	/**
	 * 排序类
	 */
	private T t2 = null;
	/**
	 * 属性值
	 */
	private Object o1 = null;
	/**
	 * 属性值
	 */
	private Object o2 = null;
	// 升序
	private int equal = 1;

	private void setAttr() throws Exception {
		o1 = this.sortName == null || "".equals(this.sortName) ? this.t1 : FieldUtil.get(t1, this.sortName, true);
		o2 = this.sortName == null || "".equals(this.sortName) ? this.t2 : FieldUtil.get(t2, this.sortName, true);
	}

	private void setEqual() throws Exception {
		setAttr();
		if (o1 instanceof File) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = ((File) o1).compareTo((File) o2);
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = ((File) o2).compareTo((File) o1);
			}
		} else if (o1 instanceof Boolean) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = Boolean.valueOf(String.valueOf(o1)).compareTo(Boolean.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = Boolean.valueOf(String.valueOf(o2)).compareTo(Boolean.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof Byte) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = Byte.valueOf(Byte.parseByte(String.valueOf(o1))).compareTo(Byte.valueOf(Byte.parseByte(String.valueOf(o2))));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = Byte.valueOf(Byte.parseByte(String.valueOf(o2))).compareTo(Byte.valueOf(Byte.parseByte(String.valueOf(o1))));
			}
		} else if (o1 instanceof Double) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = Double.valueOf(String.valueOf(o1)).compareTo(Double.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = Double.valueOf(String.valueOf(o2)).compareTo(Double.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof Float) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = Float.compare(Float.parseFloat(String.valueOf(o1)), Float.parseFloat(String.valueOf(o2)));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = Float.compare(Float.parseFloat(String.valueOf(o2)), Float.parseFloat(String.valueOf(o1)));
			}
		} else if (o1 instanceof Integer) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = Integer.valueOf(String.valueOf(o1)).compareTo(Integer.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = Integer.valueOf(String.valueOf(o2)).compareTo(Integer.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof Long) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = Long.valueOf(String.valueOf(o1)).compareTo(Long.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = Long.valueOf(String.valueOf(o2)).compareTo(Long.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof String) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = String.valueOf(o1).compareTo(String.valueOf(o2));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = String.valueOf(o2).compareTo(String.valueOf(o1));
			}
		} else if (o1 instanceof BigInteger) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = BigInteger.valueOf(Long.parseLong(String.valueOf(o1))).compareTo(BigInteger.valueOf(Long.parseLong(String.valueOf(o2))));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = BigInteger.valueOf(Long.parseLong(String.valueOf(o2))).compareTo(BigInteger.valueOf(Long.parseLong(String.valueOf(o1))));
			}
		} else if (o1 instanceof BigDecimal) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				equal = new BigDecimal(String.valueOf(o1)).compareTo(new BigDecimal(String.valueOf(o2)));
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				equal = new BigDecimal(String.valueOf(o2)).compareTo(new BigDecimal(String.valueOf(o1)));
			}
		} else if (o1 instanceof Date) {
			if (ASC.equals(this.sortOrder.toLowerCase())) {
				Date d1 = (Date) o1;
				Date d2 = (Date) o2;
				equal = d1.compareTo(d2);
			} else if (DESC.equals(this.sortOrder.toLowerCase())) {
				Date d1 = (Date) o1;
				Date d2 = (Date) o2;
				equal = d2.compareTo(d1);
			}
		} else {
			log.warn("不支持" + this.t1.getClass() + "排序类型");
		}
	}

	@Override
	public int compare(T t1, T t2) {
		this.t1 = t1;
		this.t2 = t2;
		try {
			setEqual();
			if (!this.filterEqual) {
				if (equal == 0) {
					equal = 1;
					log.debug("o1:" + o1 + "-o2:" + o2 + "-equal:" + equal);
				}
			}
		} catch (Exception e) {
			// 出错默认升序
			log.error(e.getMessage());
			equal = 1;
		}
		return equal;
	}

	public boolean isFilterEqual() {
		return filterEqual;
	}

	public void setFilterEqual(boolean filterEqual) {
		this.filterEqual = filterEqual;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
