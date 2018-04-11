//package test.sort;
//
//import java.io.File;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import utils.sortUtils.util1.CompUtil;
//import utils.sortUtils.util3.Student;
//
//import com.jun.zhang.util.file.FileUtils;
//
//public class SetTest {
//
//	public static void main(String[] args) throws Exception {
//		// System.out.println("HashSet-----------");
//		// hset();
//		// System.out.println("**********************");
//		// System.out.println("TreeSet-----------");
//		// tset();
//		
//
////		tset2();
//		tset3();
//	}
//
//	public static void hset() {
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
//		while (it.hasNext()) {
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName());
//		}
//	}
//
//	public static void tset() {
//		SortedSet<Student> set = new TreeSet<Student>(Student.SN);
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
//		while (it.hasNext()) {
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName());
//		}
//	}
//
//	public static void tset3() throws Exception {
//		CompUtil<Tobj> cu = new CompUtil<Tobj>();
//		cu.attrName = "f";
////		cu.filterEqual=false;
//		cu.sort=CompUtil.ASC;
//		SortedSet<Tobj> set = new TreeSet<Tobj>(cu);
//		// Student.filterEquals = false;
//		List<String> lst = FileUtils.readFile2(
//				new File("E:\\测试资料\\文件\\old.txt"), "utf-8");
//		Tobj stus = null;
//		for (String rts : lst) {
//			stus = new Tobj();
//			stus.f = new File(rts);
//			set.add(stus);
//		}
//		String rs = "";
//		Iterator<Tobj> it = set.iterator();
//		boolean first = false;
//		while (it.hasNext()) {
//			Tobj s = it.next();
//			if (!"".equals(s.f)) {
//				if (first) {
//					rs += "\n";
//				}
//				first = true;
//				rs += s.f;
//			}
//		}
//		FileUtils.writeFile("E:\\测试资料\\文件\\new2.txt", rs);
//	}
//
//	public static void tset2() throws Exception {
//		SortedSet<Student> set = new TreeSet<Student>(Student.SN);
//		// Student.filterEquals = false;
//		List<String> lst = FileUtils.readFile2(
//				new File("E:\\测试资料\\文件\\old.txt"), "utf-8");
//		Student stus = null;
//		for (String rts : lst) {
//			stus = new Student(rts);
//			set.add(stus);
//		}
//		String rs = "";
//		Iterator<Student> it = set.iterator();
//		boolean first = false;
//		while (it.hasNext()) {
//			Student s = it.next();
//			if (!"".equals(s.getName())) {
//				System.out.println(FileUtils.getFileExtension(s.getName())
//						+ "-" + s.getName());
//				if (first) {
//					rs += "\n";
//				}
//				first = true;
//				rs += s.getName();
//			}
//		}
//		FileUtils.writeFile("E:\\测试资料\\文件\\new.txt", rs);
//	}
//
//}
