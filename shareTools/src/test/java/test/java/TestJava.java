package test.java;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import zj.date.bean.DateTime;
import zj.java.util.JavaUtil;
import zj.reflect.util.TypeUtil;
import zj.regex.util.RegexUtil;

import com.alibaba.fastjson.JSON;

public class TestJava {
	@Test
	public void 测试所有() {
//		List<String> stIds = Arrays.asList(JavaUtil.split("2002/2004/2008/2016/2022/2026/2034/2035/2043/2044/2051", "/"));
//		System.out.println(stIds.contains("2002"));
//		Boolean b = null;
//		if (b){
//			System.out.println("a");
//		}else{
//			System.out.println("b");
//		}
		Date d = new DateTime(new Date(),DateTime.HOUR_TO_SECOND);
		System.out.println(d);
	}
	@Test
	public void 测试值比较() {
		BigDecimal zdf = new BigDecimal(0.1000);
		System.out.println(zdf.compareTo(new BigDecimal(0.0990))==1 || zdf.compareTo(new BigDecimal(-0.0990))==-1);
		zdf = new BigDecimal(-0.1000);
		System.out.println(zdf.compareTo(new BigDecimal(0.0990))==1 || zdf.compareTo(new BigDecimal(-0.0990))==-1);
		zdf = new BigDecimal(0.0890);
		System.out.println(zdf.compareTo(new BigDecimal(0.0990))==1 || zdf.compareTo(new BigDecimal(-0.0990))==-1);
		zdf = new BigDecimal(-0.0890);
		System.out.println(zdf.compareTo(new BigDecimal(0.0990))==1 || zdf.compareTo(new BigDecimal(-0.0990))==-1);
		zdf = new BigDecimal(0.0991);
		System.out.println(zdf.compareTo(new BigDecimal(0.0990))==1 || zdf.compareTo(new BigDecimal(-0.0990))==-1);
		zdf = new BigDecimal(-0.0991);
		System.out.println(zdf.compareTo(new BigDecimal(0.0990))==1 || zdf.compareTo(new BigDecimal(-0.0990))==-1);
	}

	@Test
	public void 最大顺撤() {
		Map<String, String> a = new HashMap<String, String>();
		System.out.println(a.get(null));
		// 最大回撤: max(1 - 当日价值 / 当日之前账户最高价值)
		// BigDecimal[] b1 = new BigDecimal[] { new BigDecimal(11) };
		// BigDecimal[] b2 = new BigDecimal[] { new BigDecimal(11),new BigDecimal(22) };
		// BigDecimal[] b3 = new BigDecimal[] { new BigDecimal(11),new BigDecimal(22),new BigDecimal(33) };
		// BigDecimal[] bx = new BigDecimal[] { new BigDecimal(11),new BigDecimal(22),new BigDecimal(33) };
		// 第一天
		System.out.println(new BigDecimal(20011.34).setScale(0, BigDecimal.ROUND_DOWN));
		System.out.println(new BigDecimal(20011.34).setScale(0, BigDecimal.ROUND_UP));
		System.out.println(new BigDecimal(20011.5465).setScale(2, BigDecimal.ROUND_DOWN));
		System.out.println(new BigDecimal(20011.54).setScale(0, BigDecimal.ROUND_UP));
		System.out.println("->" + new BigDecimal(20011.11).setScale(0, BigDecimal.ROUND_UP));
		boolean b = new BigDecimal(20011.05).setScale(0, BigDecimal.ROUND_UP).equals(new BigDecimal(20011.05).setScale(0, BigDecimal.ROUND_DOWN));
		System.out.println("B:" + b);

		// 第二天
		boolean primeCostLtZero = false;
		BigDecimal primeCost = new BigDecimal(20011.00100100000000000000000000000000000001);
		System.out.println(primeCost + "" + "->" + (primeCost + "").contains("."));
		String[] sprimeCost = JavaUtil.split(primeCost.setScale(2, BigDecimal.ROUND_DOWN).toString(), ".");
		if (sprimeCost.length > 1) {
			primeCostLtZero = TypeUtil.Primitive.intValue(sprimeCost[1]) > 0;
		}
		System.out.println(primeCostLtZero);
		// 第三天
	}

	@Test
	public void 比较() {
		for (int i = 0; i < 10; i++) {
			if (i < 5) {
				System.out.println("a");
			} else if (i < 8) {
				System.out.println("b");
			} else {
				System.out.println("c");
			}
		}
	}

	@Test
	public void 计算() {
		// System.out.println(Math.pow(5, 2));
		// System.out.println(Math.sqrt(Math.pow(5, 2)));
		// System.out.println(new BigDecimal(2162100 * 18.50f));
		// System.out.println(new BigDecimal(2162100).multiply(new BigDecimal(18.50)));
		// System.out.println(new BigDecimal(2162100).multiply(new BigDecimal(18.50)).setScale(0));
		// System.out.println(new BigDecimal(2162100).divide(new BigDecimal(10000), 0, BigDecimal.ROUND_DOWN));
		// System.out.println(new BigDecimal(2162100).divide(new BigDecimal(10000), 0, BigDecimal.ROUND_HALF_DOWN));
		// System.out.println("----------------");
		// System.out.println(new BigDecimal("2.5678").setScale(2, BigDecimal.ROUND_UP));
		// System.out.println(new BigDecimal("2.5678").setScale(2, BigDecimal.ROUND_HALF_UP));
		// System.out.println(new BigDecimal("2.5678").setScale(2, BigDecimal.ROUND_DOWN));
		// System.out.println(new BigDecimal("2.5678").setScale(2, BigDecimal.ROUND_HALF_DOWN));
		// System.out.println("----------------");
		// System.out.println(new BigDecimal("2.5638").setScale(2, BigDecimal.ROUND_UP));
		// System.out.println(new BigDecimal("2.5638").setScale(2, BigDecimal.ROUND_HALF_UP));
		// System.out.println(new BigDecimal("2.5638").setScale(2, BigDecimal.ROUND_DOWN));
		// System.out.println(new BigDecimal("2.5638").setScale(2, BigDecimal.ROUND_HALF_DOWN));
		int c = new BigDecimal("0.0990").setScale(4, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0.0990).setScale(4, BigDecimal.ROUND_HALF_UP));
		System.out.println(c);
	}

	@Test
	public void 去除空格() {
		try {
			System.out.println(JavaUtil.ltrim(JavaUtil.rtrim("　43.26", "　"), "　"));
			String s = "FundInfo_{date=yyyy-MM-dd}";
			Map<String, String> nameValue = new HashMap<String, String>();
			Map<String, String> returnMap = RegexUtil.fillString(s);
			System.out.println(returnMap.get(RegexUtil.FillString.KEY_PLACEHOLDER));
			// nameValue.put("date", DateUtil.dateParse(new Date,""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void Excel公式() {
		try {
			// String excel = "SUM({letter=A}1:B2)";
			// Map<String,String> nameValueMap = new HashMap<String,String>();
			// Map<String,String> nameValueMapResult = RegexUtil.fillString(excel, nameValueMap);
			// for (String key : nameValueMapResult.keySet()){
			//
			// System.out.println(key+":"+nameValueMapResult.get(key));
			// }
			// SUM(C1:D1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 格式化特殊值() {
		try {
			Object feye = 1.198801199E7;
			feye = 1212122;
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
			Double d = Double.valueOf(df.format(feye));
			feye = df.format(d);
			System.out.println(feye);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 测试其它() {
		System.out.println("a* b* c".replaceAll("\\*| ", ""));
		System.out.println(JavaUtil.conversionStringToNumber("aa串aa-1c串cc4串bbb串.串2ddd3串eee"));
		System.out.println(JavaUtil.conversionStringToNumber("-5.68%"));
		System.out.println(JavaUtil.conversionStringToNumber("--+2.2"));
		System.out.println("咨询费用$$微信$$开口率$$常规活动".split("\\$\\$").length);
		double sqrt = Math.sqrt(8d);
		System.out.println(sqrt);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void JSON转换2() {
		System.out.println("————————————Object|Map——>JSON串——————————");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "json");
		map.put("bool", Boolean.TRUE);
		map.put("int", new Integer(1));
		map.put("arr", new String[] { "a", "b" });
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("formatter", "function(i){ return this.arr[i]; }");
		map.put("func", map2);

		map = JSON.parseObject(JSON.toJSONString(map), Map.class);
		System.out.println(map);
		JSONObject jsonObject1 = JSONObject.fromObject(map);
		System.out.println(jsonObject1);
		// 输出格式： {"func":function(i){ return this.arr[i]; },"arr":["a","b"],"int":1,"bool":true,"name":"json"}
		// JSONObject jsonObject2 = JSONObject.fromObject(new MyBean());
		// System.out.println(jsonObject2);
		// 输出格式： {"func1":function(i){ return this.options[i]; },"func2":function(i){ return this.options[i]; },"name":"json","options":["a","f"],"pojoId":1}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void JSON转换() {
		try {
			// function(value){return value;}
			String json = "{\"新汇A\":{\"code\":\"新汇\",\"name\":\"我是自定产品名\"},\"新汇b\":{\"code\":\"新汇\",\"name\":\"\"}}";
			System.out.println(json);
			json = "{}";
			Map<String, Object> map = JSON.parseObject(json, Map.class);
			System.out.println(map);
			Map<String, Object> mapInner = JavaUtil.getValueForMap(map, "新汇A");
			System.out.println("mapInner:" + mapInner);
			System.out.println("code:" + mapInner.get("code"));
			System.out.println("name:" + mapInner.get("name"));
			Map<String, String> mapStr = JSON.parseObject(json, Map.class);
			System.out.println(mapStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 兔子() {
		double d = Math.pow(-0.0077, 2);
		BigDecimal b = new BigDecimal(d).setScale(8, BigDecimal.ROUND_HALF_UP);
		System.out.println(b);
		System.out.println(b.multiply(new BigDecimal(10000)).setScale(4, BigDecimal.ROUND_HALF_UP));

		System.out.println(new BigDecimal(0.8854).doubleValue());

		// // int n = 3;
		// // int i = 1;
		// // 3=2,4=3,5=4,n=n-1
		// // 3=(n+1)
		// // i+(3-n)
		// // n=3=2
		// // n=4=3
		// // n=5=4
		// // n=6=6
		// // n=7=9
		// // n=8=11
		// // n=9=14
		// // (n-3)+(2*1-1)+2
		// // 1,3,5,7,9,11
		// // 6=1
		// // 7=3
		// // 8=4
		// int a = (int) Math.pow(2, 3);
		// System.out.println(a);
		// double d = Math.pow(-0.0077, 2);
		// // d = Math.pow(2, 3);
		// // d = Math.pow(10, -1);
		// // d = Math.pow(85, 2);
		// System.out.println(d);
		// System.out.println(new BigDecimal(d).divide(new BigDecimal(100000000),8,BigDecimal.ROUND_HALF_UP));
		// // DecimalFormat df = new DecimalFormat("#.########");
		// // Double d2 = Double.parseDouble(df.format(d));
		// // System.out.println(d2);
		// System.out.println(new BigDecimal(d).setScale(8, BigDecimal.ROUND_HALF_UP));
		// for (double i = 0d;i < 0.2; i ++){
		// System.out.println(i);
		// }
	}

	@Test
	public void 测试BigDecimal3() {
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		b1 = new BigDecimal("555.5");
		b2 = b1;
		System.out.println(b1.toString());
		System.out.println(b2.toString());
		System.out.println("------------");
		b1 = b1.add(new BigDecimal(2.5));
		System.out.println(b1.toString());
		System.out.println(b2.toString());
		System.out.println("------------");
		b2 = b2.add(new BigDecimal(3.5));
		System.out.println(b1.toString());
		System.out.println(b2.toString());
	}

	@Test
	public void 测试BigDecimal2() {
		BigDecimal b1 = new BigDecimal("10");
		BigDecimal b2 = new BigDecimal("3");
		BigDecimal b6 = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP);
		BigDecimal b11 = new BigDecimal("1");
		BigDecimal b7 = b11.divide(b6, 4, BigDecimal.ROUND_HALF_UP);
		System.out.println(b6.toString());
		System.out.println(b7.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
	}

	@Test
	public void 测试BigDecimal() {
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		b1 = new BigDecimal("555.5");
		b2 = new BigDecimal("5.5");
		// System.out.println(b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		BigDecimal b3 = b1.add(b2);
		BigDecimal b4 = b1.subtract(b2);
		BigDecimal b5 = b1.multiply(b2);
		BigDecimal b6 = b1.divide(b2);
		System.out.println("b1:" + b1.toString());
		System.out.println("b2:" + b2.toString());
		System.out.println("b1.add(b2)相加:" + b3.toString());
		System.out.println("b1.subtract(b2)相减:" + b4.toString());
		System.out.println("b1.multiply(b2)相乘:" + b5.toString());
		System.out.println("b1.divide(b2)相除取整,除尽时可以不设置小数位数:" + b6.toString());
		b1 = new BigDecimal("0.06");
		b2 = new BigDecimal("3.83");
		b6 = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP);
		System.out.println("b1.divide(b2)相除取整,除不尽时必须设置小数位数b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP):" + b6.toString());
		b6 = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP);
		System.out.println("b1.divide(b2)相除取整,除不尽时必须设置小数位数b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP):" + b6.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		// b6 = b1.setScale(3, BigDecimal.ROUND_HALF_UP).divide(b2);
		// System.out.println("b1.divide(b2)相除取整,除不尽时必须设置小数位数(被除数保留3位小数):" + b6.toString());
		b1 = new BigDecimal("10");
		b2 = new BigDecimal("3");
		BigDecimal b7 = b1.remainder(b2);
		System.out.println("b1.remainder(b2)取余:" + b7.toString());

		b1 = new BigDecimal("10.898");
		BigDecimal b8 = b1.setScale(2, BigDecimal.ROUND_DOWN);
		System.out.println("b1.setScale(2, BigDecimal.ROUND_DOWN)向下取数:" + b8.toString());
	}

	@Test
	public void 测试小数() {
		System.out.println(String.format("%.2f", 2.876f));
	}

	@Test
	public void 测试分割() {
		String onclick = "go('4')";
		String[] onclicks = JavaUtil.split(onclick, "'");
		if (onclicks.length > 1) {
			System.out.println(onclicks[1]);
		}
	}

	@Test
	public void 基本类型空间比较() {
		// 5种整形的包装类Byte,Short,Integer,Long,Character的对象，

		// 在值小于127时可以使用常量池

		Integer i1 = 127;

		Integer i2 = 127;

		System.out.println(i1 == i2);// 输出true

		// 值大于127时，不会从常量池中取对象

		Integer i3 = 128;

		Integer i4 = 128;

		System.out.println(i3 == i4);// 输出false

		// Boolean类也实现了常量池技术

		Boolean bool1 = true;

		Boolean bool2 = true;

		System.out.println(bool1 == bool2);// 输出true

		// 浮点类型的包装类没有实现常量池技术

		Double d1 = 1.0;

		Double d2 = 1.0;

		System.out.println(d1 == d2);// 输出false
	}

	@Test
	public void 字符串比较() {
		// s1,s2分别位于堆中不同空间

		String s1 = new String("hello");

		String s2 = new String("hello");

		System.out.println(s1 == s2);// 输出false

		// s3,s4位于池中同一空间

		String s3 = "hello";

		String s4 = "hello";

		System.out.println(s3 == s4);// 输出true
	}

	@Test
	public void m5() {
		System.out.println("cons.operateType.minus.positions".replaceAll("\\.", "_"));
		value(true, true);
	}

	public void value(Object value) {
		System.out.println("1->" + value);
	}

	public void value(boolean b, Object... values) {
		System.out.println("2->" + values);
	}

	@Test
	public void m4() {
		System.out.println(TestJava.class.getResource(""));
	}

	@Test
	public void m3() {
		for (int i = 0; i < 100; i++) {
			System.out.println(JavaUtil.RandomUtil.getInt(2, 5));
		}
	}

	@Test
	public void m2() {
		System.out.println(TypeUtil.Primitive.booleanValue(""));
	}

	@Test
	public void m1() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("新汇A", "新汇");
		map.put("新汇B", "新汇");
		map.put("新汇二号A", "新汇二号");
		map.put("新汇二号B", "新汇二号");
		String json = JSON.toJSONString(map);
		System.out.println(json);
		System.out.println(map.get("新汇B"));
	}
}
