package test.swing;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Comparator;

public class CompUtilExport<T> implements Comparator<Object> {
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
					equal=1;
				}
			}
		} catch (Exception e) {
		}
		return equal;
	}
}
