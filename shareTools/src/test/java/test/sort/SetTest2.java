//package utils.sortUtils.test;
//
//import java.util.Iterator;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import utils.sortUtils.util3.DetailRec;
//
//public class SetTest2 {
//	public static void main(String[] args) {
//		tset();
//
//	}
//
//	public static void tset() {
//		SortedSet<DetailRec> set = new TreeSet<DetailRec>(DetailRec.S_MONDAY_A);
//		DetailRec d1 = new DetailRec();
//		d1.setTypeNo("品番00001");
//		d1.setName("商品名称:00001");
//		d1.setPrice(8000);
//		d1.setValueRate(100.001);
//		d1.setMonday(1);
//		d1.setTuesday(2);
//		d1.setWednesday(3);
//		d1.setThursday(4);
//		d1.setFriday(5);
//		d1.setSaturday(6);
//		d1.setSunday(7);
//		set.add(d1);
//
//		DetailRec d4 = new DetailRec();
//		d4.setTypeNo("品番00000");
//		d4.setName("商品名称:00000");
//		d4.setPrice(7001);
//		d4.setValueRate(91.001);
//		d4.setMonday(10);
//		d4.setTuesday(20);
//		d4.setWednesday(30);
//		d4.setThursday(40);
//		d4.setFriday(50);
//		d4.setSaturday(60);
//		d4.setSunday(70);
//		set.add(d4);
//
//		DetailRec d2 = new DetailRec();
//		d2.setTypeNo("品番00002");
//		d2.setName("商品名称:00002");
//		d2.setPrice(8001);
//		d2.setValueRate(101.001);
//		d2.setMonday(100);
//		d2.setTuesday(200);
//		d2.setWednesday(300);
//		d2.setThursday(400);
//		d2.setFriday(500);
//		d2.setSaturday(600);
//		d2.setSunday(700);
//		set.add(d2);
//
//		DetailRec d3 = new DetailRec();
//		d3.setTypeNo("品番00003");
//		d3.setName("商品名称:00003");
//		d3.setPrice(7001);
//		d3.setValueRate(91.001);
//		d3.setMonday(10);
//		d3.setTuesday(20);
//		d3.setWednesday(30);
//		d3.setThursday(40);
//		d3.setFriday(50);
//		d3.setSaturday(60);
//		d3.setSunday(70);
//		set.add(d3);
//
//		Iterator<DetailRec> it = set.iterator();
//		System.out.println("品番" + "\t\t" + "商品名称" + "\t\t" + "売価(円)" + "\t\t"
//				+ "値入率(％)" + "\t" + "月（％）" + "\t" + "火（％）" + "水（％）" + "\t"
//				+ "木（％）" + "\t" + "金（％）" + "\t" + "土（％）" + "\t" + "日（％）");
//		while (it.hasNext()) {
//			DetailRec dr = it.next();
//			System.out.println(dr.getTypeNo() + "\t" + dr.getName() + "\t"
//					+ dr.getPrice() + "\t\t" + dr.getValueRate() + "\t\t"
//					+ dr.getMonday() + "\t" + dr.getTuesday() + "\t"
//					+ dr.getWednesday() + "\t" + dr.getThursday() + "\t"
//					+ dr.getFriday() + "\t" + dr.getSaturday() + "\t"
//					+ dr.getSunday());
//		}
//	}
//
//}
