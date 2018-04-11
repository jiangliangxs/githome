//package utils.sortUtils.test;
//
//import java.util.Iterator;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import utils.classList.SortProUtil;
//import utils.sortUtils.util3.Student;
//
//
//public class T {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		SortProUtil sad = new SortProUtil(Student.class,"dou");
//		//System.out.println(sad);
//		sad.setOtherName("id");
//		SortedSet<Student> set = new TreeSet<Student>(sad);
//		set.add(new Student(10, "ajhang8",1.2));
//		set.add(new Student(11, "ahang1",1.4));
//		set.add(new Student(12, "fhang2",1.5));
//		set.add(new Student(18, "zhang3",1.2));
//		set.add(new Student(14, "hhang",1.1));
//		set.add(new Student(15, "fhang6",1.8));
//		set.add(new Student(16, "dhang5",1.0));
//		set.add(new Student(10, "ahang33",1.2));
//		set.add(new Student(15, "zhang",1.5));
//		set.add(new Student(11, "zhang",1.3));
//		
//		Iterator<Student> it = set.iterator();
//		while(it.hasNext()){
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName() + "\t" + s.getDou());
//		}
//		
//	}
//
//}
