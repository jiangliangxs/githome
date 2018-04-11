//package utils.sortUtils.test;
//
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import utils.sortUtils.util3.Student;
//
//public class SetTest3 {
//	public static void main(String[] args) {
//		System.out.println("HashSet-----------");
//		hset();
//		System.out.println("**********************");
//		System.out.println("TreeSet-----------");
//		tset();
//		
//	}
//	
//	public static void hset(){
//		Set<Student> set = new HashSet<Student>();
//		set.add(new Student(10, "zhang"));
//		set.add(new Student(11, "ahang1"));
//		set.add(new Student(12, "fhang2"));
//		set.add(new Student(13, "zhang3"));
//		set.add(new Student(14, "hhang"));
//		set.add(new Student(15, "fhang6"));
//		set.add(new Student(16, "dhang5"));
//		set.add(new Student(10, "zhang"));
//		
//		Iterator<Student> it = set.iterator();
//		while(it.hasNext()){
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName());
//		}
//	}
//	
//	public static void tset(){
//		SortedSet<Student> set = new TreeSet<Student>(Student.SN);
//		Student.filterEquals=false;
//		set.add(new Student(10, "zhang"));
//		set.add(new Student(11, "ahang1"));
//		set.add(new Student(12, "fhang2"));
//		set.add(new Student(18, "zhang3"));
//		set.add(new Student(14, "hhang"));
//		set.add(new Student(15, "fhang6"));
//		set.add(new Student(16, "dhang5"));
//		set.add(new Student(10, "zhang"));
//		set.add(new Student(15, "zhang"));
//		set.add(new Student(11, "zhang"));
//		
//		Iterator<Student> it = set.iterator();
//		while(it.hasNext()){
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName());
//		}
//	}
//
//}
//
