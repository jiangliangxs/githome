package test.jdbc;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import zj.date.util.DateUtil;
import zj.java.util.JavaUtil;
import zj.jdbc.util.JdbcUtil;
import zj.reflect.util.TypeUtil;

public class TestMsth extends TestBase {
	@Before
	public void connection() throws Exception {
		connMysql();
	}
	@Test
	public void saveReportSeason() {
		
	}
	@Test
	public void saveHoliday() {
		try {
			psInsert = conn.prepareStatement("insert into ebiz_holiday(id,holiday_date,holiday_desc) values(?,?,?)");
			Date cur = DateUtil.parseDate("2016-01-01");
			for (int i = 0; i < 366; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String w = DateUtil.dateParse(thisDate, "w");
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				if ("星期六".equals(w) || "星期日".equals(w)) {
					System.out.println("日期：" + sd + "," + w);
					addOrUpdate(sd, w);
				}
			}
			cur = DateUtil.parseDate("2016-01-01");
			for (int i = 0; i < 3; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "元旦");
			}
			cur = DateUtil.parseDate("2016-02-07");
			for (int i = 0; i < 7; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "春节");
			}
			// addOrUpdate("20160404", "清明节");
			cur = DateUtil.parseDate("2016-04-02");
			for (int i = 0; i < 3; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "清明节");
			}
			cur = DateUtil.parseDate("2016-04-30");
			for (int i = 0; i < 3; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "五一劳动节");
			}
			cur = DateUtil.parseDate("2016-06-09");
			for (int i = 0; i < 3; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "端午节");
			}
			cur = DateUtil.parseDate("2016-09-15");
			for (int i = 0; i < 3; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "中秋节");
			}
			cur = DateUtil.parseDate("2016-10-01");
			for (int i = 0; i < 7; i++) {
				Date thisDate = DateUtil.addDay(cur, i);
				String sd = DateUtil.dateParse(thisDate, "yyyyMMdd");
				addOrUpdate(sd, "十一国庆节");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addOrUpdate(String holiday_date, String holiday_desc) throws Exception {
		psQuery = conn.prepareStatement("select count(id) from ebiz_holiday where holiday_date = ?");
		psQuery.setObject(1, holiday_date);
		rs = psQuery.executeQuery();
		// 移动数据
		rs.next();
		int count = TypeUtil.Primitive.intValue(rs.getObject(1));
		if (count > 0) {
			// 更新
			psUpdate = conn.prepareStatement("update ebiz_holiday set holiday_desc=? where holiday_date = ?");
			psUpdate.setObject(1, holiday_desc);
			psUpdate.setObject(2, holiday_date);
			psUpdate.executeUpdate();
			System.out.println("数据更新成功：" + holiday_date + "," + holiday_desc);
		} else {
			// 插入
			psInsert.setObject(1, JavaUtil.getUUID());
			psInsert.setObject(2, holiday_date);
			psInsert.setObject(3, holiday_desc);
			psInsert.executeUpdate();
			System.out.println("数据插入成功：" + holiday_date + "," + holiday_desc);
		}
		JdbcUtil.closeDB(rs, psUpdate);
	}
}
