package test.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.regex.util.RegexUtil;

public class T {
	@Test
	public void m1_1() throws Exception {
		String sql = "";
		sql = "12345{a=1<在}6789{b====中国人}1{a={ad}}1";
		Map<String, String> nameValueMap = RegexUtil.fillString(sql);//new HashMap<String,String>();//
//		nameValueMap.put("a", "<=张军");
		String s = nameValueMap.get(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE+"a");
		System.out.println(s);
		nameValueMap.put("a", s);
		Map<String, String> newSqlMap = RegexUtil.fillString(sql, nameValueMap);
		System.out.println("newSQL:" + newSqlMap.get(RegexUtil.FillString.KEY_NEW_VALUE));
	}
	@Test
	public void m8() throws Exception {
		// String sql = "{a=b}张{c=d}军{a=c}";
		// Map<String, String> keyValues = new HashMap<String, String>();
		// Map<String, String> nameValueMap = RegexUtil.fillString(sql, keyValues);
		// nameValueMap.put("a", "123");
		// nameValueMap = RegexUtil.fillString(sql, nameValueMap);
		// System.out.println(nameValueMap.get(RegexUtil.FillString.KEY_NEW_VALUE));

		String templateHtmlPath = "E:/versionManager/sources/java/zj-model/integration/zjweb-utils-jdk1.7/WebRoot/test/template.html";
		String encoding = "UTF-8";
		String sql = "{a=b张{*{{{{a=b}}}";
		sql = "{a2=b张{*{{{{title2=b}}}}}}%<title>{title=我是标题啊}</title>\n</head>";
		// sql = "{a=b}张{c=d}军{a2=c}";
		// sql = "{list.code=基金代码}";

		// sql = FileUtils.readFileToString(new File(templateHtmlPath), encoding);
		// sql = "<!DOCTYPE html><!DOCTYPE html><html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta charset=\"utf-8\">\n<meta http-equiv=\"pragma\" content=\"no-cache\">\n<meta http-equiv=\"Cache-Control\" content=\"no-store, must-revalidate\">\n<title>{title=我是标题啊}</title>\n</head>\n<body>\n<div style=\"text-align: center;\">\n<div style=\"text-align: center;\">中国人{content=测试标题}</div>\n</div>\n</html><html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta charset=\"utf-8\">\n<meta http-equiv=\"pragma\" content=\"no-cache\">\n<meta http-equiv=\"Cache-Control\" content=\"no-store, must-revalidate\">\n<title>{title=我是标题啊}</title>\n</head>\n<body>\n<div style=\"text-align: center;\">\n<div style=\"text-align: center;\">中国人{content=测试标题}</div>\n</div>\n</html>";
		// // sql = "<!DOCTYPE html><!DOCTYPE html><html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta charset=\"utf-8\">\n<meta http-equiv=\"pragma\" content=\"no-cache\">\n<meta http-equiv=\"Cache-Control\" content=\"no-store, must-revalidate\">\n<title>{title=我是标题啊}</title>\n</head>\n<body>\n<div style=\"text-align: center;\">\n<div style=\"text-align: center;\">中国人{content=测试标题}</div>\n</div>\n</html><html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta charset=\"utf-8\">\n<meta http-equiv=\"pragma\" content=\"no-cache\">\n<meta http-equiv=\"Cache-Control\" content=\"no-store, must-revalidate\">\n<title>{title=我是标题啊}</title>\n</head>\n<body>\n中国人{content=测试标题}</div>";
		sql = "aaaaa\n<title>{\ntitle= 我是标题啊}<div style=\"text-align: center;\">中国人{content=测试标题}</div>\n</div>\n</html>";
		System.out.println(sql);

		Map<String, String> nameValueMap = RegexUtil.fillString(sql);
		System.out.println("size:" + nameValueMap.size());
		for (String key : nameValueMap.keySet()) {
			if (key.startsWith(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE)) {
				System.out.println(JavaUtil.split(key, RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE)[1]);
				System.out.println(nameValueMap.get(key));
				System.out.println("------------------------------");
			}
		}
		System.out.println("=============================");

		nameValueMap = new HashMap<String, String>();
		nameValueMap.put("title", "我是标题-张军");
		nameValueMap.put("content", "我是被替换的内容<script style='type/javascript'>alert('hehe-js');</script>");
		Map<String, String> sbMap = RegexUtil.fillString(sql, nameValueMap);
		String newStr = sbMap.get(RegexUtil.FillString.KEY_NEW_VALUE);
		System.out.println(newStr);
		// System.out.println(nameValueMap);
		// String places = nameValueMap.get(RegexUtil.FillString.KEY_PLACEHOLDER);
		// System.out.println(places + "," + places.equals(sql));
	}

	@Test
	public void m7() throws Exception {
		String sql = "{a=b张{*{{{{a=b}}}a}{c=d}";
		// sql = "{a2=b张{*{{{{title2=b}}}}}}%<title>{title=我是标题啊}</title>\n</head>";
		sql = "{a === === b}张{c=d}军{a2=c}";
		sql = "{22222222{a=33333{aa=aa1}1}111{b=2}{c=3}}";
		Map<String, String> nameValueMap = RegexUtil.fillString(sql);
		for (String key : nameValueMap.keySet()) {
			if (key.startsWith(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE)) {
				System.out.println("key:"+JavaUtil.split(key, RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE)[1]);
				System.out.println("value:"+nameValueMap.get(key));
				System.out.println("------------------------------");
			}
		}
		System.out.println("=============================");
		System.out.println("size:" + nameValueMap.size());
		System.out.println(nameValueMap.get(RegexUtil.FillString.KEY_NEW_VALUE));
	}

	@Test
	public void m6() throws Exception {
		String sql = "a{a1=1}ba{a2=2}c{a3={a4=2}}d{a5={a5=6{a5}}}55{a2=3}6";
		String pile = "\\{([^\\{]*=[^\\}]*)*\\}";
		pile = "\\{([^\\{\\}]*)\\}";
		// str = "and INUM>''{INUM最大值=本地系统最大值}''  and INUM like ''{INUM前缀=604766,600418}'' and userField1=''1'' and CONVERT(varchar(10) ,StartedAt,120 )>=''{录音开始时间=2014-03-01}'' and CONVERT(varchar(10) ,StartedAt,120 )<={录音结束时间=CONVERT(varchar(10) ,DATEADD(day, -1, getDate() ),120 )}";
		Matcher m = Pattern.compile(pile).matcher(sql);
		while (m.find()) {
			System.out.println(m.groupCount()+"-"+m.group(0));
		}
	}

	@Test
	public void m5() throws Exception {
		// 获取值
		String sql = "select {\"hello={a=b}\":\"world\"}* from {表名=z*{\"hello={a=b}\":\"world\"}hangjun} inner join {表名1==zhangjun1} {on====1} t.id=t1.id {条件=where 1=1 and (column1='hello' or column2='world')}";
		Map<String, String> keyValues = new HashMap<String, String>();
		// keyValues.put("表名", "ebiz_user as a");
		// keyValues.put("表名1", "ebiz_order as b");
		// keyValues.put("on", "a.id=b.user_id");
		// keyValues.put("条件", "where (a.name='张军' or a.idno='1234567') and b.contno='88880001'");
		Map<String, String> nameValueMap = RegexUtil.fillString(sql, keyValues);
		System.out.println("新占位符键值对:");
		for (String key : nameValueMap.keySet()) {
			if (key.startsWith(RegexUtil.FillString.KEY_NEW_PLACEHOLDER_VALUE)) {
				System.out.println("key:" + key + "," + "value:" + nameValueMap.get(key));
				System.out.println("-------------------------");
			}
		}
		System.out.println("旧占位符键值对:");
		for (String key : nameValueMap.keySet()) {
			if (key.startsWith(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE)) {
				System.out.println("key:" + key + "," + "value:" + nameValueMap.get(key));
				System.out.println("-------------------------");
			}
		}
		System.out.println("旧字符串:");
		System.out.println(nameValueMap.get(RegexUtil.FillString.KEY_ORIGINAL_VALUE));
		System.out.println("新字符串:");
		System.out.println(nameValueMap.get(RegexUtil.FillString.KEY_NEW_VALUE));

		System.out.println(nameValueMap);
	}

	public void m4() throws Exception {
		// 获取值
		String sql = "<>*?select * from {表名=*zhangjun} inner join {表名1==zhangjun1} {on====1} t.id=t1.id {条件=where 1=1 and (column1='hello' or column2='world')}";
		// sql = "and INUM>''{INUM最大值=本地系统最大值}''  and INUM like ''{INUM前缀=604766,600418}'' and userField1=''1'' and CONVERT(varchar(10) ,StartedAt,120 )>=''{录音开始时间=2014-03-01}'' and CONVERT(varchar(10) ,StartedAt,120 )<={录音结束时间=CONVERT(varchar(10) ,DATEADD(day, -1, getDate() ),120 )}";
		Map<String, String> nameValueMap = RegexUtil.fillString(sql);
		System.out.println("返回值:" + nameValueMap.entrySet());
		// 设置值
		nameValueMap = new HashMap<String, String>();
		nameValueMap.put("表名", "张军替换表名");
		nameValueMap.put("表名1", "张军啊1 as t1");
		nameValueMap.put("条件", "t1.c1='hehe' and (t2.c2='hehe2' or t3.c3='中国人')");
		Map<String, String> newSqlMap = RegexUtil.fillString(sql, nameValueMap);
		System.out.println("原字符串值:" + newSqlMap.get(RegexUtil.FillString.KEY_ORIGINAL_VALUE));
		System.out.println("新字符串值:" + newSqlMap.get(RegexUtil.FillString.KEY_NEW_VALUE));
		System.out.println("原字符串占位符值:" + newSqlMap.get(RegexUtil.FillString.KEY_ORIGINAL_PLACEHOLDER_VALUE + "表名1"));
		System.out.println("新字符串占位符值:" + newSqlMap.get(RegexUtil.FillString.KEY_NEW_PLACEHOLDER_VALUE + "表名1"));
	}

	public void m3() throws Exception {
		// 获取值
		String sql = "select * from {表名[=]zhangjun} inner join {表名1==zhangjun1} {on====1} t.id=t1.id {条件=where 1=1 and (column1='hello' or column2='world')}";
		// sql = "and INUM>''{INUM最大值=本地系统最大值}''  and INUM like ''{INUM前缀=604766,600418}'' and userField1=''1'' and CONVERT(varchar(10) ,StartedAt,120 )>=''{录音开始时间=2014-03-01}'' and CONVERT(varchar(10) ,StartedAt,120 )<={录音结束时间=CONVERT(varchar(10) ,DATEADD(day, -1, getDate() ),120 )}";
		Map<String, String> nameValueMap = RegexUtil.fillString(sql);
		System.out.println("nameValueMap:" + nameValueMap + "\n" + nameValueMap.get("表名") + "\n" + nameValueMap.get("on"));
		// 设置值
		nameValueMap = new HashMap<String, String>();
		nameValueMap.put("表名", "张军啊 as t");
		nameValueMap.put("表名1", "张军啊1 as t1");
		nameValueMap.put("条件", "t1.c1='hehe' and (t2.c2='hehe2' or t3.c3='中国人')");
		Map<String, String> newSqlMap = RegexUtil.fillString(sql, nameValueMap);
		System.out.println("newSQL:" + newSqlMap.get("{newStr}"));
		System.out.println("newSQL:" + newSqlMap.get("{msg}"));
	}

	public void m2() throws Exception {
		String sql = "12345{a=a1}6789{b= =00==[888]}11";
		sql = "12345{a=在}6789{b====中国人}11";
		Map<String, String> nameValueMap = RegexUtil.fillString(sql);
		System.out.println("nameValueMap:" + nameValueMap + "\n" + nameValueMap.get("{msg}") + "\n" + nameValueMap.get("b"));
		Map<String, String> newSqlMap = RegexUtil.fillString(sql, nameValueMap);
		System.out.println("newSQL:" + newSqlMap.get("{newStr}"));
		System.out.println("newSQL:" + newSqlMap.get("{msg}"));
	}
}
