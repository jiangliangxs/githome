package test.date;

import java.util.Date;

import org.junit.Test;

import zj.date.util.DateUtil;

public class T {
	@Test
	public void m2(){
		System.out.println("20130101022334".length());
		System.out.println(DateUtil.parseDate("20130101022334"));
	}
	@Test
	public void m1(){
		try {
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd Hh:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd hH:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd hh:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd Hh24:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd hH24:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd hh24:mm:ss"));
			System.out.println("1=================");
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd H:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd h:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd H12:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd h12:mm:ss"));
			System.out.println("2=================");
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd H24:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd h24:mm:ss"));
			System.out.println("3=================");
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd Hh12:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd hH12:mm:ss"));
			System.out.println(DateUtil.dateParse(DateUtil.parseDate("2013-10-11 16:14:15"), "yyyy-MM-dd hh12:mm:ss"));
			System.out.println("4=================");
			//String curDate = "2011-1-26";
			Date date = new Date();
			//date = DateUtil.parseDate(curDate);
			int day = Integer.parseInt(DateUtil.dateParse(date, "dd"));
			String strsDate = null;
			String streDate = null;
			if (day>25){
				strsDate = DateUtil.dateParse(date, "yyyy-MM")+"-26 00:00:00";
				streDate = DateUtil.dateParse(DateUtil.addMonth(date, 1), "yyyy-MM")+"-25 23:59:59";
			}else{
				strsDate = DateUtil.dateParse(DateUtil.addMonth(date, -1), "yyyy-MM")+"-26 00:00:00";
				streDate = DateUtil.dateParse(date, "yyyy-MM")+"-25 23:59:59";
			}
			System.out.println(strsDate);
			System.out.println(streDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
