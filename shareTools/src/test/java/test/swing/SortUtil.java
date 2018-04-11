package test.swing;

import java.io.Serializable;
import java.util.Comparator;

public class SortUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Comparator<Object> FILENAME = new SortName();
	public static final Comparator<Object> FILEEXTENSION = new SortExtension();
	public static boolean filterEquals = true;
	private String extension;
	private String name;
	private String name_extension;

	public String getName_extension() {
		return name_extension;
	}

	public void setName_extension(String nameExtension) {
		name_extension = nameExtension;
	}
	
	public SortUtil(String name_extension) {
		super();
		this.name_extension = name_extension;
	}

	public SortUtil(String name_extension, int type) {
		super();
		switch (type) {
		case 0:
			this.name = name_extension;
			break;
		case 1:
			this.extension = name_extension;
			break;
		}
	}

	public SortUtil(String name, String extension) {
		super();
		this.name = name;
		this.extension = extension;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() ^ 0xabcdef;
	}

	// 文件名排序
	static class SortName implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			SortUtil s1 = (SortUtil) o1;
			SortUtil s2 = (SortUtil) o2;
			int equal = s1.getName().compareTo(s2.getName());
			if (!filterEquals) {
				if (equal == 0) {
					equal = 1;
				}
			}
			return equal;
		}

	}

	// 扩展名排序
	static class SortExtension implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			SortUtil s1 = (SortUtil) o1;
			SortUtil s2 = (SortUtil) o2;
			int equal = s1.getExtension().compareTo(s2.getExtension());
			if (!filterEquals) {
				if (equal == 0) {
					equal = 1;
				}
			}
			return equal;
		}
	}

}
