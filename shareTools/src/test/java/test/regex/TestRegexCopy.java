package test.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.regex.util.RegexUtil;

public class TestRegexCopy {
	@Test
	public void m15() {
		String pile = null;
		String str = "";
//		pile = "[^abcd]+";
//		str = "windows 98 windows 200a0 awindows 2003";
//		System.out.println("负向预查,非获取匹配");
//		cm1(pile, str);
		
		pile = "<a[^>]+>";
		str = "666<add>555</b>444<abc>222<d>111<a>333";
		System.out.println("负向预查,非获取匹配");
		cm1(pile, str);
		
	}
	@Test
	public void m14() {
		// 待匹配的正则表达式
		String reg = "^http://hugh-wangp\\.iteye\\.com/(?!.*((weibo)|(link))).*$";

		System.out.println("http://hugh-wangp.iteye.com/".matches(reg));// 通过
		System.out.println("http://hugh-wangp.iteye.com/blog".matches(reg));// 通过
		System.out.println("http://hugh-wangp.iteye.com/blog/guest_book".matches(reg));// 通过
		System.out.println("http://hugh-wangp.iteye.com/weibo".matches(reg));// 不通过
		System.out.println("http://hugh-wangp.iteye.com/link".matches(reg));// 不通过

		String fieldName = " a b ";
		fieldName = fieldName.trim();
		System.out.println(fieldName);
	}

	@Test
	public void m13() {
		String sql = "aaaaa\n<ti\ntle>\n{\n{        title          =          我是标题啊        }<div style\n=\"text-align:{content2=测试标题2} center;\">中国人{\ncontent\n=测试标题}</div>\n</div>\n</html>";
		sql = "12122{ a ====== b }张{c=d}军{a2=c}121212{aa == b }12232323";
		System.out.println(sql);
		System.out.println("*****************************");
		Matcher m = RegexUtil.getMatcher(sql);
		while (m.find()) {
			System.out.println("===>" + m.groupCount());
			System.out.println("--->" + m.group(0).replaceAll("\\s*", ""));
		}
	}

	@Test
	public void m12() {
		String text = "A_SelectResuRltTestT";
		System.out.println(RegexUtil.convertJavaToDbField(text));
		// text = "abcdef";
		// Matcher m = RegexUtil.getMatcher("([A-Z])", text);
		// while (m.find()) {
		// System.out.println("-----");
		// for (int i = 0; i < m.groupCount(); i++) {
		// String s = m.group(i);
		// if (text.indexOf(s) == 0) {
		// continue;
		// }
		// text = text.replaceAll(s, "_" + s);
		// }
		// }
		// System.out.println(text);
	}

	@Test
	public void m11() {
		String url = "http://127.0.0.1:8080/eservice/mobile/third/receive/01/THIRD-MSTH-014/d50c?5ea07e4434f9c494b7404dbc4ba4";
		// url = "abc";
		// 包含02或03
		String regex = "http://127.0.0.1:8080/eservice/mobile/third/receive/((02)|(03))/THIRD-MSTH-001/d50c5ea07e4434f9c494b7404dbc4ba4";
		regex = "http://127.0.0.1:8080/eservice/mobile/third/receive/0([2-3]|5)/THIRD-MSTH-001/.*\\?.*";
		// 渠道:01-02,05
		// 请求类型:THIRD-MSTH-001到THIRD-MSTH-003,THIRD-MSTH-005到THIRD-MSTH-007,THIRD-MSTH-009,THIRD-MSTH-011到THIRD-MSTH-013
		regex = ".*/mobile/third/receive/01/THIRD-MSTH-0((0[[1-3][5-7]9])|(1[1-3]))/.*";
		regex = ".*/mobile/third/receive/(0[145])/THIRD-MSTH-.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			System.out.println(matcher.group(0));
		}
		matcher.reset();
		System.out.println(matcher.matches());
	}

	@Test
	public void m10() {
		String s = "select * from ?a?";
		System.out.println(s.replaceAll("[?]a[?]", "中国人"));
		System.out.println(s.replaceAll("\\?a\\?", "中国人"));
	}

	@Test
	public void m9() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "张军");
		params.put("idCard", "34222419890720051X");
		String regex = "^\\d{17}(\\d{1}|x|X)$";
		// regex = "[\u4e00-\u9fa5]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(JavaUtil.objToStr(params.get("idCard")));
		System.out.println(matcher.matches());
	}

	@Test
	public void m8() {
		System.out.println("platform.framework.dao");
		System.out.println("test.model".endsWith("."));
		Pattern p = Pattern.compile("(.*)\\.\\w*");
		Matcher m = p.matcher("platform.framework.dao");
		// Pattern p = Pattern.compile("Base(.*)Mapper");
		// Matcher m = p.matcher("BaseUserMapper");
		if (m.find()) {
			System.out.println(m.group(1));
		}
		// System.out.println(m.group(1));
		// while (m.find()) {
		// for (int i = 0; i <= m.groupCount(); i++) {
		// System.out.println("索引(" + i + ")→" + m.group(i));
		// }
		// }
		// System.out.println("m.matches():" + m.matches());
		// if (m.matches()) {
		// System.out.println(m.group(0));// 注意,group(0)总是返回整个匹配字串
		// System.out.println(m.group(1)); // 0
		// System.out.println(m.group(2)); // A
		// }
	}

	public static void cm1(String pile, String str) {
		Matcher m = null;
		m = Pattern.compile(pile).matcher(str);
		// 调用m.find()，则继续找下个
		// System.out.println("刚好匹配到整串 结果→" + m.matches() + "→匹配到子串结果→" + m.find());
		System.out.println("刚好匹配到整串 结果→" + m.matches());
		// if (m.matches()) {
		// System.out.println("m.start()→m.end()→m.group()" + m.start() + "→" + m.end() + "→" + m.group());
		// }
		System.out.println("分组()号个数→" + m.groupCount() + "→正则表达式:" + pile + "→字符串:" + str);
		int index = 0;
		m.reset();
		while (m.find()) {
			System.out.println("循环次数→" + (++index));
			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println("索引(" + i + ")→" + m.group(i));
			}
		}
		System.out.println("---------------------------------------------");
	}

	@Test
	public void m7() {
		String pile = null;
		pile = "\\{{1,1}([^{*]*=[^}*]*)*\\}{1,1}";
		Pattern p = Pattern.compile(pile);
		Matcher m = p.matcher("{12={}}3{11A}abc{12=A}def}");
		int index = 0;
		while (m.find()) {
			System.out.println("循环次数:" + (++index));
			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println("索引(" + i + "):" + m.group(i));
			}
		}
	}

	@Test
	public void m6() {
		Pattern p = Pattern.compile("(\\d*)([A-C])");
		Matcher m = p.matcher("10A");
		System.out.println("m.matches():" + m.matches());
		if (m.matches()) {
			System.out.println(m.group(0));// 注意,group(0)总是返回整个匹配字串
			System.out.println(m.group(1)); // 0
			System.out.println(m.group(2)); // A
		}
	}

	@Test
	public void m5() {
		// Auto-generated method stub
		String str = "Hello,World! in Java.";
		Pattern pattern = Pattern.compile("W(or)(ld!)");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			System.out.println("Group 0:" + matcher.group(0));// 得到第0组——整个匹配
			System.out.println("Group 1:" + matcher.group(1));// 得到第一组匹配——与(or)匹配的
			System.out.println("Group 2:" + matcher.group(2));// 得到第二组匹配——与(ld!)匹配的，组也就是子表达式
			System.out.println("Start 0:" + matcher.start(0) + " End 0:" + matcher.end(0));// 总匹配的索引
			System.out.println("Start 1:" + matcher.start(1) + " End 1:" + matcher.end(1));// 第一组匹配的索引
			System.out.println("Start 2:" + matcher.start(2) + " End 2:" + matcher.end(2));// 第二组匹配的索引
			System.out.println(str.substring(matcher.start(0), matcher.end(1)));// 从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
		}
	}

	@Test
	public void m4() {
		String s = "{s}";
		System.out.println(s.substring(1, s.length() - 1));
	}

	@Test
	public void m3() throws Exception {
		String hql = "a{a1=e},b{1=[0]}";
		// hql = "a{{1==={a}==2!}}9{a1=0}b{11=22}c";
		hql = "a{{{}1==={a}==2!}}9{a1=0}b{11=22}c";
		Map<String, String> map = RegexUtil.fillString(hql);
		for (String key : map.keySet()) {
			System.out.println("key:" + key + ",value:" + map.get(key));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("1", "张军");
		Map<String, String> map2 = RegexUtil.fillString(hql, params);
		System.out.println("=================================");
		for (String key : map2.keySet()) {
			System.out.println("key:" + key + ",value:" + map2.get(key));
		}
	}

	@Test
	public void m2_1() {
		String pile = null;
		String str = "";
		pile = "[\\$+\\{+]*";
		str = "12${3";
		System.out.println(pile + "→" + str + "-------替换字符串");
		System.out.println(Pattern.compile(pile).matcher(str).matches());
	}

	@Test
	public void m2() {
		String pile = null;
		String str = "";
		// pile = "\\{([^{*]*=[^}*]*)*\\}";
		// pile = "\\{([^{*]*=[^}*]*)*\\}";
		// pile = "\\{([^\\{|^\\}*]*=[^\\{|^\\}*]*)*\\}";
		pile = "\\$\\{([^\\$\\{]*=[^\\}\\$]*)*\\}\\$";
		// str = "12345{a${{1=2}$}}000}6789{b=={1${=2$}}==}11{a1=333}000";
		// str = "12345{a=!\\+子}6789{b====}11";
		str = "${abc=dd}$99999${abc2=dd2}$";
		System.out.println(pile + "→" + str + "-------替换字符串");
		cm1(pile, str);
	}

	@Test
	public void m1_1() {
		String pile = null;
		String str = "";

		// pile = "[\\^{\\-+}]";
		// str = "2013-a9+9{^a=b}-adad{a2=b2}2323";
		// cm1(pile, str);

		// pile = "java6|java7";
		// str = "java6";
		// str = "java7";
		// str = "java7 java6";
		// str = "java6 java7";
		// cm1(pile, str);
		//
		pile = "java(6|7)-(\\1)";
		str = "java6";
		str = "java7-7";
		str = "java7-6";
		// str = "java7 java6";
		// str = "java6 java7";
		cm1(pile, str);
		//
		// pile = "java(6|7)";
		// str = "java6";
		// str = "java7";
		// str = "java7 java6";
		// str = "java6 java7";
		// cm1(pile, str);
		//
		// pile = "java[6|7]";
		// str = "java6";
		// str = "java7";
		// str = "java7 java6";
		// str = "java6 java7";
		// cm1(pile, str);
		//
		// pile = "java6|7";
		// str = "java6";
		// str = "java7";
		// str = "java7 java6";
		// str = "java6 java7";
		// cm1(pile, str);
		//
		// pile = "^(\\d?)(\\d?)(\\d?)(\\d?)$";
		// str = "2013";
		// cm1(pile, str);
		//
		// pile = "(\\d?)(\\d?)(\\d?)(\\d?)$";
		// str = "2013";
		// cm1(pile, str);
		//
		// pile = "\\d?\\d?\\d?\\d?$";
		// str = "2013";
		// cm1(pile, str);

		// pile = "^(.*[{](.*)[=](.*)[}])(\\d?)(\\d?)(\\d?)$";
		// str = "2013a99{a=b}adad{a2=b2}2323";
		// cm1(pile, str);
	}

	@Test
	public void 普通表达式() {
		String pile = null;
		String str = "";
		pile = "(windows 98|2000|2003)";
		str = "windows 2000 windows 1003 windows 98 windows 2003";
		System.out.println("普通表达式");
		cm1(pile, str);
	}

	@Test
	public void 后向引用_获取匹配() {
		String pile = null;
		String str = "";
		// 在分组中获取98|2000|2003
		pile = "(windows (98|2000|2003))";
		str = "windows 2000 windows 1003 windows 98 windows 2003";
		System.out.println("后向引用,获取匹配");
		cm1(pile, str);
	}

	@Test
	public void 非获取匹配() {
		String pile = null;
		String str = "";
		// 在分组中不获取98|2000|2003
		pile = "(windows (?:98|2000|2003))";
		str = "windows 2000 windows 1003 windows 98 windows 2003";
		System.out.println("非获取匹配");
		cm1(pile, str);
	}

	// @Test
	// public void (){
	// String pile = null;
	// String str = "";
	// }
	// @Test
	// public void (){
	// String pile = null;
	// String str = "";
	// }@Test
	// public void (){
	// String pile = null;
	// String str = "";
	// }@Test
	// public void (){
	// String pile = null;
	// String str = "";
	// }@Test
	// public void (){
	// String pile = null;
	// String str = "";
	// }
	@Test
	public void 正向预查_非获取匹配() {
		String pile = null;
		String str = "";
		pile = "(windows (?=98|2000))";
		str = "windows 2000 windows 98 windows 2003 windows 2000";
		System.out.println("正向预查,非获取匹配");
		cm1(pile, str);

	}

	@Test
	public void m1() {
		String pile = null;
		String str = "";
		pile = "^(\\d{4})(/|-| )(\\d{1,2})(\\2)([0-9]{1,2}) (\\d{1,2})(:|)(\\d{1,2})(\\7)(\\d{1,2})$";
		str = "2013/01/01 02:23:34";
		str = "2013 01 01 022334";
		System.out.println("验证日期【正确格式为：yyyyMMddhhmmss或yyyy-MM-dd hh:mm:ss或yyyy/MM/dd hh:mm:ss或yyyy MM dd hh:mm:ss】");
		cm1(pile, str);
		pile = "(\\d{4})(/|-| )(\\d{1,2})(\\2)([0-9]{1,2}) (\\d{1,2})(:|)(\\d{1,2})(\\7)(\\d{1,2})";
		str = "中国人2013/01/01 02:23:34";
		str = "2013/01/01中国人02:23:34";
		System.out.println("验证日期【正确格式为：yyyyMMddhhmmss或yyyy-MM-dd hh:mm:ss或yyyy/MM/dd hh:mm:ss或yyyy MM dd hh:mm:ss】");
		cm1(pile, str);
		pile = "^(0?[1-9]|1[0-2])$";
		str = "10";
		System.out.println("【验证一年的12个月:正确格式为：】【01-12或1-12】");
		cm1(pile, str);
		pile = "(windows 98|2000|2003)";
		str = "windows 98 windows 2000 windows 2003";
		str = "windows 2000 windows 98 windows 2003";
		System.out.println("普通表达式");
		cm1(pile, str);
		pile = "(windows (98|2000|2003))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("后向引用,获取匹配");
		cm1(pile, str);
		pile = "(windows (?:98|2000|2003))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("非获取匹配");
		cm1(pile, str);
		pile = "(windows (?=98|2000))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("正向预查,非获取匹配");
		cm1(pile, str);
		pile = "(windows (?!98|2000))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("负向预查,非获取匹配");
		cm1(pile, str);
		// 反向预查与正向预查很相似，子模式仅仅作为条件限制，不作为结果输出，唯一的不同是，正向预查匹配子模式前面的结果作为匹配结果，而反向预查匹配子模式后面的结果作为匹配结果。
		pile = "((?<=windows) (98|2000))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("负向预查,获取匹配,子模式条件限制");
		cm1(pile, str);
		pile = "((?<=windows) (?:98|2000))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("负向预查,非获取匹配,子模式条件限制");
		cm1(pile, str);
		pile = "((?<!windows) (98|2000))";
		str = "a 981 b 20002 c 2000 d 2000 windows 98 windows 2000";
		System.out.println("负反向预查,获取匹配,子模式条件限制");
		cm1(pile, str);
		pile = "((?<!windows) (?:98|2000))";
		str = "a 981 b 20002 c 2000 d 2000 windows 98 windows 2000";
		System.out.println("负反向预查,非获取匹配,子模式条件限制");
		cm1(pile, str);
	}
}
