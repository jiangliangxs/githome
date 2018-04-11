package test.regex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.reflect.util.TypeUtil;
import zj.regex.util.RegexSqlUtil;
import zj.regex.util.RegexUtil;

public class TestRegex {
	@Test
	public void 测试其它() {
		String currentSql = "select t.vc_kmmc from TTMP_H_GZB t where t.l_bh = 300001  and t.l_ztbh = {where.value.l_id} and t.l_sfqr = 1 and t.d_ywrq = date '{where.value.date}'";
		Map<String, String> params = new HashMap<String, String>();
		params.put(RegexSqlUtil.SqlConstant.KEY_SQL, currentSql);
		try {
			// 先替换日期
			params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "date");
			params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "2012");
			currentSql = RegexSqlUtil.replaceSql(params);
			System.out.println(currentSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			params.put(RegexSqlUtil.SqlConstant.KEY_SQL, currentSql);
			params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "l_id");
			// 循环替换代码
			for (int i = 100; i < 105; i++) {
				params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "" + i);
				currentSql = RegexSqlUtil.replaceSql(params);
				System.out.println(currentSql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void 字符数字相加() {
		String __c = "A";
		String __i = "1,2";
		String[] __cs = JavaUtil.split(__c, ",");
		String[] __is = JavaUtil.split(__i, ",");
		// 目前只写到AA-ZZ区间
		// 获取字母的值
		if (__cs.length > 0) {
			String sc = __cs[0];
			// 获取字母左边的值(取得第一个字母)
			char leftC = sc.substring(0, 1).toCharArray()[0];
			// 获取字母右边的值(取得倒数第一个字母)
			char rightC = sc.substring(sc.length() - 1).toCharArray()[0];
			// 获取字母的步长
			int stepC = 0;
			if (__cs.length > 1) {
				stepC = TypeUtil.Primitive.intValue(__cs[1]);
			}
			System.out.println(leftC + "," + rightC + "," + stepC);
			// 根据集合变化

		}

	}

	@Test
	public void 测试字符数字相加() {
		// boolean b = RegexUtil.getMatcherResult("\\d+年保监会年报", "2014年保监会年报");
		// System.out.println(b);
		// SUM({__c=A,2}{__i=1,2}
		char startC = 'A';
		int stepC = 1;
		int size = 100;
		System.out.println("--------------------------");
		int tempStepC = stepC;
		for (int i = 0; i < size; i++) {
			// 相加结果
			char tempEndC = (char) (startC + tempStepC);
			String tempEndCStr = JavaUtil.objToStr(tempEndC);
			if ("Z".equalsIgnoreCase(tempEndCStr)) {
				// 如果到最后一个标识
				// 重新开始
				startC = 'A';
				tempStepC = stepC;
			} else {
				tempEndCStr = String.valueOf(startC) + String.valueOf(tempEndCStr);
				tempStepC++;
			}
			System.out.println(tempEndCStr);
		}
		System.out.println("--------------------------");
		char endC = (char) (startC + 25);
		System.out.println(endC);
		System.out.println((char) ('A' + 1));
		System.out.println((char) ('A' + '1'));
		// A...Z
		// AA...AZ
		// BA...BZ
		// CA...CZ
	}

	@Test
	public void 测试所有表达式结果() {
		try {
			String excel = "";
			excel = "SUM({__c=A,2}{__i=1,2}";
			Map<String, String> nameValueMap = new HashMap<String, String>();
			nameValueMap.put("x", "xxx");
			nameValueMap.put("letter.1", "A");
			Set<String> replaceOriginalValueKeys = new HashSet<String>();
			replaceOriginalValueKeys.add("letter.2");
			Map<String, String> nameValueMapResult = RegexUtil.fillString(excel, nameValueMap);
			for (int i = 0; i < 10; i++) {
				// 假设有10个元素

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 测试所有表达式() {
		try {
			String excel = "SUM({__FormulaCharA.1=A}{__FormulaNumberA.1=1}:{letterB.2=B}{__FormulaNumberB.1=1})";
			excel = "SUM(C{__FormulaNumberA.1=1}:E{__FormulaNumberA.1=1})-SUM(F{__FormulaNumberA.1=1}:H{__FormulaNumberA.1=1})";
			Map<String, String> nameValueMap = new HashMap<String, String>();
			nameValueMap.put("x", "xxx");
			nameValueMap.put("letter.1", "A");
			Set<String> replaceOriginalValueKeys = new HashSet<String>();
			replaceOriginalValueKeys.add("letter.2");
			Map<String, String> nameValueMapResult = RegexUtil.fillString(excel, nameValueMap);
			System.out.println("获取新字符串值的key:" + nameValueMapResult.get(RegexUtil.FillString.KEY_NEW_VALUE));
			System.out.println("获取原字符串值的key:" + nameValueMapResult.get(RegexUtil.FillString.KEY_ORIGINAL_VALUE));
			String key_placeholder_names = nameValueMapResult.get(RegexUtil.FillString.KEY_PLACEHOLDER_NAMES);
			System.out.println("所有占位符名:" + key_placeholder_names);
			String[] key_placeholder_namesAry = JavaUtil.split(key_placeholder_names, RegexUtil.FillString.KEY_PLACEHOLDER_NAMES_SPLIT);
			for (String key : key_placeholder_namesAry) {
				System.out.println("占位符名:" + key);
				System.out.println("占位符名-获取新字符串占位符对应的值:" + nameValueMapResult.get(RegexUtil.FillString.KEY_NEW_PLACEHOLDER_VALUE + key));
				System.out.println("占位符名-获取原字符串占位符对应的值:" + nameValueMapResult.get(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE + key));
			}
			String key_placeholder = nameValueMapResult.get(RegexUtil.FillString.KEY_PLACEHOLDER);
			System.out.println("所有占位符:" + key_placeholder);
			String[] key_placeholdersAry = JavaUtil.split(key_placeholder, RegexUtil.FillString.KEY_PLACEHOLDER_SPLIT);
			for (String key : key_placeholdersAry) {
				System.out.println("占位符:" + key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 测试表达式() {
		String text = "function  (a))))))         \n{return a'ab';}";
		String function = "^function[ ]?\\(.*?\\)[ \n\t]*\\{.*?\\}$";
		text = " \t\n\tfunction  \n(a,b,c,d){}";
		function = "^function[ \n\t]*\\(.*?\\)";
		// 以function开头
		function = "^[ \n\t]*function[ \n\t]*\\(.*?\\)[ \n\t]*\\{.*?\\}$";
		System.out.println(text);
		boolean b = RegexUtil.getMatcherResult(function, text);
		System.out.println(b);
	}

	@Test
	public void 测试临时() {
		cm1("(*=)", "<!--list#l-->");
	}

	@Test
	public void 匹配exp_不捕获匹配的文本_也不给此分组分配组号() {
		System.out.println(RegexUtil.convertJavaToDbField("exDividendDate"));
	}

	@Test
	public void 匹配exp前面的位置() {
		System.out.println(RegexUtil.getMatcherResult("/basic/permissions/user!prePareConfig\\.action", "/basic/permissions/user!prePareConfig.action"));
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
	public void 负向预查_非获取匹配() {
		String pile = null;
		String str = "";
		pile = "(windows (?!98|2000))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("负向预查,非获取匹配");
		cm1(pile, str);
	}

	@Test
	public void 零宽断言_1() {
		// (?=exp)也叫零宽度正预测先行断言，它断言自身出现的位置的后面能匹配表达式exp。
		// 比如\b\w+(?=ing\b)，匹配以ing结尾的单词的前面部分(除了ing以外的部分)，
		// 如查找I'm singing while you're dancing.时，它会匹配sing和danc。
		String pile = null;
		String str = "";

		// pile = "\\b\\w+(?=ing\\b)";
		// str = "I'm singing while you're dancing.";
		// System.out.println("(?=exp)也叫零宽度正预测先行断言");
		// cm1(pile, str);

		pile = "((?<=windows) (98|2000))";
		str = "windows 98 windows 2000 windows 2003";
		System.out.println("负向预查,获取匹配,子模式条件限制");
		cm1(pile, str);
	}

	@Test
	public void 零宽断言_2() {
		String pile = null;
		String str = "";
		// (?<=exp)也叫零宽度正回顾后发断言，它断言自身出现的位置的前面能匹配表达式exp。
		// 比如(?<=\bre)\w+\b会匹配以re开头的单词的后半部分(除了re以外的部分)，
		// 例如在查找reading a book时，它匹配ading。

		pile = "(?<=\\{).*=.*(?=\\})";
		pile = "\\{([^\\{\\}]*)\\}";
		str = "12{hello{{{{{abc=c}}}}}122{ a\n ====== b }张{c=d}军{a2=c}121212{aa= b }12232323";
		System.out.println("(?<=exp)也叫零宽度正回顾后发断言");
		cm1(pile, str);

		// 假如你想要给一个很长的数字中每三位间加一个逗号(当然是从右边加起了)，
		// 你可以这样查找需要在前面和里面添加逗号的部分：((?<=\d)\d{3})+\b，
		// 用它对1234567890进行查找时结果是234567890。

		// pile = "((?<=\\d)\\d{3})+\\b";
		// str = "1234567890";
		// System.out.println("(?<=exp)也叫零宽度正回顾后发断言");
		// cm1(pile, str);

		// pile = "((?<=windows) (?:98|2000))";
		// str = "windows 981 windows 2000 windows 2003";
		// System.out.println("负向预查,非获取匹配,子模式条件限制");
		// cm1(pile, str);
	}

	@Test
	public void 负向零宽断言_1() {
		String pile = null;
		String str = "";

		// 前面我们提到过怎么查找不是某个字符或不在某个字符类里的字符的方法(反义)。但是如果我们只是想要确保某个字符没有出现，但并不想去匹配它时怎么办？例如，如果我们想查找这样的单词--它里面出现了字母q,但是q后面跟的不是字母u,我们可以尝试这样：
		// \b\w*q[^u]\w*\b匹配包含后面不是字母u的字母q的单词。但是如果多做测试(或者你思维足够敏锐，直接就观察出来了)，你会发现，如果q出现在单词的结尾的话，像Iraq,Benq，这个表达式就会出错。这是因为[^u]总要匹配一个字符，所以如果q是单词的最后一个字符的话，
		// 后面的[^u]将会匹配q后面的单词分隔符(可能是空格，或者是句号或其它的什么)，后面的\w*\b将会匹配下一个单词，于是\b\w*q[^u]\w*\b就能匹配整个Iraq fighting。负向零宽断言能解决这样的问题，因为它只匹配一个位置，并不消费任何字符。
		// 现在，我们可以这样来解决这个问题：\b\w*q(?!u)\w*\b。
		// 零宽度负预测先行断言(?!exp)，断言此位置的后面不能匹配表达式exp。例如：\d{3}(?!\d)匹配三位数字，而且这三位数字的后面不能是数字；\b((?!abc)\w)+\b匹配不包含连续字符串abc的单词。
		// 同理，我们可以用(?<!exp),零宽度负回顾后发断言来断言此位置的前面不能匹配表达式exp：(?<![a-z])\d{7}匹配前面不是小写字母的七位数字。
		// 请详细分析表达式(?<=<(\w+)>).*(?=<\/\1>)，这个表达式最能表现零宽断言的真正用途。
		// 一个更复杂的例子：(?<=<(\w+)>).*(?=<\/\1>)匹配不包含属性的简单HTML标签内里的内容。(?<=<(\w+)>)指定了这样的前缀：被尖括号括起来的单词(比如可能是<b>)，然后是.*(任意的字符串),最后是一个后缀(?=<\/\1>)。注意后缀里的\/，
		// 它用到了前面提过的字符转义；\1则是一个反向引用，引用的正是捕获的第一组，前面的(\w+)匹配的内容，这样如果前缀实际上是<b>的话，后缀就是</b>了。整个表达式匹配的是<b>和</b>之间的内容(再次提醒，不包括前缀和后缀本身)。

		pile = "((?<!windows) (98|2000))";
		str = "a 981 b 20002 c 2000 d 2000 windows 98 windows 2000";
		System.out.println("负反向预查,获取匹配,子模式条件限制");
		cm1(pile, str);
	}

	@Test
	public void 负反向预查_非获取匹配_子模式条件限制() {
		String pile = null;
		String str = "";
		pile = "((?<!windows) (?:98|2000))";
		str = "a 981 b 20002 c 2000 d 2000 windows 98 windows 2000";
		System.out.println("负反向预查,非获取匹配,子模式条件限制");
		cm1(pile, str);
	}

	@Test
	public void checkDiv() {
		String pile = null;
		String str = "";
		pile = "\\{(?: *[^\\{\\s]* *= *[^\\}\\s]* *)\\}";
		str = "aaaaa\n<ti\ntle>\n{\n{        title          =          我是标题啊        }<div style\n=\"text-align:{content2=测试标题2} center;\">中国人{\ncontent\n=测试标题}</div>\n</div>\n</html>";
		System.out.println("查询DIV");
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
