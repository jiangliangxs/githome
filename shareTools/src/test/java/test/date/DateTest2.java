package test.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangfj @email yjia2009@yahoo.cn
 * @date 2012-4-19 下午10:01:13 对日期进行处理
 */
public class DateTest2 {

	public static Date getSystemTime() {
		return getSystemTime("1");
	}

	public static String getStrSystemTime() {
		return getStrSystemTime("1");
	}

	/**
	 * 获取系统时间
	 * 
	 * @param type
	 *            : 1 获取日期 2 获取详细时间
	 * @return 日期类型时间
	 * @throws ParseException
	 */
	public static Date getSystemTime(String type) {
		String Pattern = "yyyy-MM-dd";
		if (type.equals("1")) {
			Pattern = "yyyy-MM-dd";
		}
		if (type.equals("2")) {
			Pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Pattern);
		Calendar calendar = Calendar.getInstance();
		sdf.format(calendar.getTime());
		Date date = null;
		try {
			date = sdf.parse(sdf.format(calendar.getTime()));
		} catch (ParseException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取系统时间
	 * 
	 * @param type
	 *            : 1 获取日期 2 获取详细时间
	 * @return 字符串类型时间
	 * @throws ParseException
	 */
	public static String getStrSystemTime(String type) {
		String Pattern = "yyyy-MM-dd";
		if (type.equals("1")) {
			Pattern = "yyyy-MM-dd";
		}
		if (type.equals("2")) {
			Pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Pattern);
		Calendar calendar = Calendar.getInstance();
		formatter.format(calendar.getTime());
		String strdate = null;
		strdate = formatter.format(calendar.getTime());
		return strdate;
	}

	/**
	 * 获取日期相差天数
	 * 
	 * @param
	 * @return 日期类型时间
	 * @throws ParseException
	 */
	public static Long getDiffDay(String beginDate, String endDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Long checkday = 0l;
		// 开始结束相差天数
		try {
			checkday = (formatter.parse(endDate).getTime() - formatter.parse(beginDate).getTime()) / (1000 * 24 * 60 * 60);
		} catch (ParseException e) {
			//  Auto-generated catch block
			e.printStackTrace();
			checkday = null;
		}
		return checkday;
	}

	public static Long getDiffDay(Date beginDate, Date endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strBeginDate = format.format(beginDate);

		String strEndDate = format.format(endDate);
		return getDiffDay(strBeginDate, strEndDate);
	}

	/**
	 * 获取日期相月数
	 * 
	 * @param
	 * @return 日期类型时间
	 * @throws ParseException
	 */
	public static int getDiffMonth(String beginDate, String endDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dbeginDate = null;
		Date dendDate = null;
		try {
			dbeginDate = formatter.parse(beginDate);
			dendDate = formatter.parse(endDate);
		} catch (ParseException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
		return getDiffMonth(dbeginDate, dendDate);
	}

	public static int getDiffMonth(Date beginDate, Date endDate) {
		Calendar calbegin = Calendar.getInstance();
		Calendar calend = Calendar.getInstance();
		calbegin.setTime(beginDate);
		calend.setTime(endDate);
		int m_begin = calbegin.get(Calendar.MONTH) + 1; // 获得合同开始日期月份
		int m_end = calend.get(Calendar.MONTH) + 1;
		// 获得合同结束日期月份
		int checkmonth = m_end - m_begin + (calend.get(Calendar.YEAR) - calbegin.get(Calendar.YEAR)) * 12;
		// 获得合同结束日期于开始的相差月份
		return checkmonth;
	}
	public static void main(String[] args) {
		long l = DateTest2.getDiffMonth("2004-05-08 12:00:00", "2004-06-04 11:59:59");
		System.out.println(l);
	}
}
