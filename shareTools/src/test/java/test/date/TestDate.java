package test.date;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

import org.junit.Test;

import zj.date.util.DateUtil;
import zj.java.util.JavaUtil;
import zj.reflect.util.TypeUtil;

public class TestDate {
	@Test
	public void m2() {
		String s = "03:05:08";//30508
		String e = "15:02:09";//150209
//		System.out.println(03:05:08<=发布时间(12:00:23)<=15:02:09);
//		System.out.println(30508<=发布时间(120023)<=150209);
		System.out.println(TypeUtil.Primitive.intValue(JavaUtil.conversionStringToNumber(s)));
		System.out.println(TypeUtil.Primitive.intValue(JavaUtil.conversionStringToNumber(e)));
		System.out.println(DateUtil.parseDate("2015-01-02").after(new Date()));
	}
	@Test
	public void m1() {
		String id = "1001";
		String sdate = "2011-12-11";
		Date date = DateUtil.parseDate(sdate);
		String year = DateUtil.dateParse(date, "yyyy");
		String month = DateUtil.dateParse(date, "MM");
		Date startMonth = DateUtil.parseDate(year + "-01-01");
		String sql = "select sum(en_sz) as nums from ttmp_h_gzb t where t.l_ztbh=''{0}'' and t.vc_kmdm like ''委托资产净值%'' and t.d_ywrq=date''{1}''";
		BigDecimal sumValue = BigDecimal.ZERO;
		int sumIndex = 0;
		while (true) {
			try {
				String markDate = DateUtil.dateParse(DateUtil.addDay(startMonth, -1), "yyyy-MM-dd");
				System.out.println(MessageFormat.format(sql, id, markDate));
				sumIndex ++;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭连接
			}
			if (DateUtil.dateParse(startMonth, "yyyyMM").equals(year + month)) {
				try {
					System.out.println(MessageFormat.format(sql, id, sdate));
					sumIndex ++;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 关闭连接
				}
				break;
			}
			startMonth = DateUtil.addMonth(startMonth, 1);
		}
		sumValue = new BigDecimal("347.234");
		System.out.println(sumValue.divide(new BigDecimal(sumIndex), 4, BigDecimal.ROUND_HALF_UP));
	}
}
