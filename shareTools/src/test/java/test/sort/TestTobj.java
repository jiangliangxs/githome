//package utils.sortUtils.test;
//
//import java.io.File;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import utils.sortUtils.util1.CompUtil;
//
//
//public class TestTobj {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		CompUtil<Tobj> cu = new CompUtil<Tobj>();
//		cu.attrName="s";
//		cu.sort=CompUtil.DESC;
//		cu.filterEqual=false;
////		cu.sort=CompUtil.ASC;
//		SortedSet<Tobj> set = new TreeSet<Tobj>(cu);
//		Tobj tobj = new Tobj();
//		tobj.s="a";
//		tobj.i=10;
//		tobj.b=false;
//		tobj.f = new File("");
//		set.add(tobj);
//		
//		tobj = new Tobj();
//		tobj.s="d";
//		tobj.i=60;
//		tobj.b=false;
//		set.add(tobj);
//
//		tobj = new Tobj();
//		tobj.s="z";
//		tobj.i=30;
//		tobj.b=false;
//		set.add(tobj);
//		
//		tobj = new Tobj();
//		tobj.s="z";
//		tobj.i=30;
//		tobj.b=false;
//		set.add(tobj);
//		
//		
//		tobj = new Tobj();
//		tobj.s="a";
//		tobj.i=50;
//		tobj.b=false;
//		set.add(tobj);
//		
//		tobj = new Tobj();
//		tobj.s="z";
//		tobj.i=30;
//		tobj.b=false;
//		set.add(tobj);
//		
//		tobj = new Tobj();
//		tobj.s="a";
//		tobj.i=20;
//		tobj.b=true;
//		set.add(tobj);
//		
//		for (Tobj t1 : set){
//			System.out.println("String:" + t1.s + "-int:" + t1.i + "-boolean:" + t1.b);
//		}
//	}
//
//}
