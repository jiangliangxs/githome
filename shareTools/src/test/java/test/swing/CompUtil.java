package test.swing;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Comparator;

public class CompUtil<T> implements Comparator<Object> {
	public static String ASC = "asc";
	public static String DESC = "desc";
	public boolean filterEqual = true;
	public String attrName = null;
	public String sort = ASC;
	private Class<?> cla = null;
	private Object t1 = null;
	private Object t2 = null;
	private Field f1 = null;
	private Field f2 = null;
	private Object o1 = null;
	private Object o2 = null;
	//升序
	private int equal = 1;

	private void setAttr() throws Exception {
		f1 = cla.getDeclaredField(attrName);
		f2 = cla.getDeclaredField(attrName);
		f1.setAccessible(true);
		f2.setAccessible(true);
		o1 = f1.get(t1);
		o2 = f2.get(t2);
	}

	private void setEqual() throws Exception {
		setAttr();
		if (o1 instanceof File) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = ((File) o1).compareTo((File) o2);
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = ((File) o2).compareTo((File) o1);
			}
		} else if (o1 instanceof Boolean) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = Boolean.valueOf(String.valueOf(o1)).compareTo(
						Boolean.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = Boolean.valueOf(String.valueOf(o2)).compareTo(
						Boolean.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof Byte) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = Byte.valueOf(Byte.parseByte(String.valueOf(o1))).compareTo(Byte.valueOf(Byte.parseByte(String.valueOf(o2))));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = Byte.valueOf(Byte.parseByte(String.valueOf(o2))).compareTo(Byte.valueOf(Byte.parseByte(String.valueOf(o1))));
			}
		} else if (o1 instanceof Double) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = Double.valueOf(String.valueOf(o1)).compareTo(Double.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = Double.valueOf(String.valueOf(o2)).compareTo(Double.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof Float) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = Float.compare(Float.parseFloat(String.valueOf(o1)),
						Float.parseFloat(String.valueOf(o2)));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = Float.compare(Float.parseFloat(String.valueOf(o2)),
						Float.parseFloat(String.valueOf(o1)));
			}
		} else if (o1 instanceof Integer) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = Integer.parseInt(String.valueOf(o1))
						- Integer.parseInt(String.valueOf(o2));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = Integer.parseInt(String.valueOf(o2))
						- Integer.parseInt(String.valueOf(o1));
			}
		} else if (o1 instanceof Long) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = Long.valueOf(String.valueOf(o1)).compareTo(Long.valueOf(String.valueOf(o2)));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = Long.valueOf(String.valueOf(o2)).compareTo(Long.valueOf(String.valueOf(o1)));
			}
		} else if (o1 instanceof String) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = String.valueOf(o1).compareTo(String.valueOf(o2));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = String.valueOf(o2).compareTo(String.valueOf(o1));
			}
		
		} else if (o1 instanceof BigInteger) {
			if (ASC.equals(sort.toLowerCase())) {
				equal = BigInteger.valueOf(Long.parseLong(String.valueOf(o1))).compareTo(BigInteger.valueOf(Long.parseLong(String.valueOf(o2))));
			} else if (DESC.equals(sort.toLowerCase())) {
				equal = BigInteger.valueOf(Long.parseLong(String.valueOf(o2))).compareTo(BigInteger.valueOf(Long.parseLong(String.valueOf(o1))));
			}
		} 
	}

	@Override
	public int compare(Object t1, Object t2) {
		this.t1 = t1;
		this.t2 = t2;
		cla = t1.getClass();
		try {
			setEqual();
			if (!filterEqual){
				if (equal==0){
					System.out.println("o1:" + o1 + "-o2:" + o2 + "-equal:" + equal);
					equal=1;
				}
			}
		} catch (Exception e) {
			System.out.println("排序出错了:" + e.getMessage());
		}
		return equal;
	}
}
