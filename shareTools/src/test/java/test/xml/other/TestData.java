package test.xml.other;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import zj.check.util.CheckUtil;
import zj.date.util.DateUtil;

public class TestData {
	class ManCostDeptGroup {
		public String ymonth;
		public String sdate;
		public String edate;
	}
	@Test
	public void m1() throws Exception {
		getManCostDeptGroups("2016-01-25","2016-05-05");
	}
	public List<ManCostDeptGroup> getManCostDeptGroups(String sds,String sde) throws Exception {
//		String sds = "2016-01-25";
//		String sde = "2016-05-05";
		Date ds = DateUtil.parseDate(sds);
		Date de = DateUtil.parseDate(sde);
		long diffd = DateUtil.diffDay(ds, de);
		List<ManCostDeptGroup> ms = new ArrayList<ManCostDeptGroup>();
		for (int i = 0; i <= diffd; i++) {
			Date currentDate = DateUtil.addDay(ds, i);
			String ymonth = DateUtil.dateParse(currentDate, "yyyy-MM");
			String day = DateUtil.dateParse(currentDate, "yyyy-MM-dd");
			// 判断是否添加
			ManCostDeptGroup m = null;
			for (ManCostDeptGroup mi : ms) {
				if (ymonth.equals(mi.ymonth)) {
					// 存在
					m = mi;
				}
			}
			if (m == null) {
				m = new ManCostDeptGroup();
				ms.add(m);
			}
			if (CheckUtil.isNull(m.sdate)) {
				m.sdate = day;
			}
			m.ymonth = ymonth;
			m.edate = day;
			System.out.println(i + "=" + DateUtil.dateParse(currentDate, "yyyy-MM-dd"));
		}
		System.out.println("------------------");
		for (ManCostDeptGroup mi : ms) {
			System.out.println(mi.ymonth + "==" + mi.sdate + "==" + mi.edate);
		}
		return ms;
	}

	public static Map<String, Object> getMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("c1", "1c1");
		m2.put("c2", "1c2");
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("d1", "1d1");
		m3.put("d2", "1d2");
		m2.put("e1", m3);
		List<String> list = new ArrayList<String>();
		list.add("l1");
		list.add("l2");
		list.add("l3");

		m.put("b1", m2);
		m.put("a1", "1a1");
		m.put("a2", "1a2");
		m.put("a3", new Student());
		m.put("f1", list);
		return m;
	}

	/**
	 * 核保xml数据
	 * 
	 * @return
	 */
	public static String getXML() {
		String xml = "";
		// 声明部分
		xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		// 报文根-开始
		xml += "<Package>";
		// 报文头Header-开始

		// 基本信息（Header）
		xml += "<Header>";
		xml += "<RequestType>01</RequestType>";
		xml += "<SendTime>2012-01-02 10:11:12</SendTime>";
		xml += "<ThirdSerial>11111</ThirdSerial>";
		xml += "</Header>";

		// 报文头Header-结束
		// 报文体Request-开始
		xml += "<Request>";

		// 订单信息（Order）
		xml += "<Order>";
		xml += "<OrderId>8185888</OrderId>";
		xml += "<TotalPremium>108.83</TotalPremium>";
		xml += "<InsBeginDate>2013-01-02 10:11:12</InsBeginDate>";
		xml += "<ApplyNum>30</ApplyNum>";
		xml += "</Order>";

		// 险种信息(Item)
		xml += "<Item>";
		xml += "<RiskCode>513401</RiskCode>";
		xml += "<Premium>108.83</Premium>";
		xml += "</Item>";

		// 投保人信息（Holder）
		xml += "<Holder>";
		xml += "<HolderName>张军</HolderName>";
		xml += "<HolderUID>123</HolderUID>";
		xml += "<HolderCardType>0</HolderCardType>";
		xml += "<HolderCardNo>210401198010219289</HolderCardNo>";
		xml += "<HolderBirthday>1989-09-08</HolderBirthday>";
		xml += "<HolderSex>0</HolderSex>";
		xml += "<HolderMobile>15888888888</HolderMobile>";
		xml += "<HolderAddress>上海</HolderAddress>";
		xml += "<HolderZip>234000</HolderZip>";
		xml += "</Holder>";

		// 被保人信息（InsuredInfo）
		xml += "<InsuredInfo>";
		xml += "<IsHolder>1</IsHolder>";
		xml += "</InsuredInfo>";

		// 被保人详细信息列表（InsuredList）（多条Insured）
		xml += "<InsuredList>";
		xml += "</InsuredList>";

		// 受益人信息（Benefit）
		xml += "<Benefit>";
		xml += "<IsLegal>1</IsLegal>";
		xml += "</Benefit>";

		// 受益人详细信息列表BenefitList(多条)
		xml += "<BenefitList>";
		xml += "</BenefitList>";

		// 其他信息（OtherInfo）
		xml += "<OtherInfo>";
		xml += "</OtherInfo>";

		xml += "</Request>";
		// 报文体Request-结束
		xml += "</Package>";
		// 报文根-结束
		System.out.println("xml原始值:\n" + xml);
		return xml;
	}
}
