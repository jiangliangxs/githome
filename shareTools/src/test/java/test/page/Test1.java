package test.page;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Test1 {
	public static void main(String[] args) {
		Set<String> s = new TreeSet<String>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		s.add("a");
		s.add("b");
		s.add("c");
		s.add("c1");
		s.add("c2");
		s.add("c3");
		Iterator<String> it = s.iterator();
		int i = 1;
		while (it.hasNext()) {
			if (i++ > 2) {
				System.out.println(it.next());
			} else {
//				System.out.println("-------" + it.next());
			}
		}
		System.out.println(i);
	}
}
