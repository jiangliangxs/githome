package test.reflect.generics;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Tclass2 {
	public static <T> List<T> getRecords(Class<T> c, Selector s)
			throws Exception {
		// Use Selector to select rows
		List<T> list = new ArrayList<T>();
		Set<SelectionKey> set = s.selectedKeys();
		Iterator<SelectionKey> it = set.iterator();
		while (/** iterate over results */
		it.hasNext()) {
			T row = c.newInstance(); // use reflection to set fields from result
			list.add(row);
		}
		return list;
	}
}
