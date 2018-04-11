package test.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.regex.util.RegexSqlUtil;

import com.alibaba.fastjson.JSON;

public class TestOther {
	@Test
	public void testOther6() {
		String jsonValues = "{\"export\":{\"invokeMethod\":\"method\",\"valuesField\":\"type|name|assets|sumExpense|reward|cleanExpense\"},\"table\":[{\"title\":\"序号\",\"field\":\"id\",\"dbField\":\"id\",\"width\":700,\"sortable\":true},{\"title\":\"密码\",\"field\":\"password\",\"dbField\":\"password\",\"width\":300,\"sortable\":true},{\"title\":\"客户名称\",\"field\":\"name\",\"dbField\":\"userName\",\"width\":700,\"sortable\":true},{\"title\":\"创建时间\",\"field\":\"createdDate\",\"width\":700,\"sortable\":false}],\"includeQueryPage\":\"\",\"innerPage\":\"inc-report-inner1.jsp\",\"query\":{\"other\":{\"buttonWidth\":\"16%\"},\"list\":[[{\"field\":\"name\",\"title\":\"账套名称\",\"fieldType\":\"input\",\"textWidth\":\"10%\",\"valueWidth\":\"18%\"},{\"field\":\"createdDate\",\"title\":\"创建时间\",\"fieldType\":\"date\"},{\"field\":\"isDelete\",\"title\":\"删除状态\",\"fieldType\":\"combobox\"}],[{\"field\":\"name1\",\"title\":\"其它姓名\",\"fieldType\":\"input\"},{\"field\":\"createdDate1\",\"title\":\"其它时间\",\"fieldType\":\"date\",\"required\":true},{}]]}}";
		jsonValues = "{\"table\":[{\"title\":\"序号\",\"field\":\"id\",\"dbField\":\"id\",\"width\":700,\"sortable\":true},{\"title\":\"密码\",\"field\":\"password\",\"dbField\":\"password\",\"width\":300,\"sortable\":true},{\"title\":\"客户名称\",\"field\":\"name\",\"dbField\":\"userName\",\"width\":700,\"sortable\":true},{\"title\":\"创建时间\",\"field\":\"createdDate\",\"width\":700,\"sortable\":false}],\"includeQueryPage\":\"\",\"innerPage\":\"inc-report-inner1.jsp\",\"query\":{\"other\":{\"buttonWidth\":\"16%\"},\"list\":[[{\"field\":\"name\",\"title\":\"账套名称\",\"fieldType\":\"input\",\"textWidth\":\"10%\",\"valueWidth\":\"18%\"},{\"field\":\"createdDate\",\"title\":\"创建时间\",\"fieldType\":\"date\"},{\"field\":\"isDelete\",\"title\":\"删除状态\",\"fieldType\":\"combobox\"}],[{\"field\":\"name1\",\"title\":\"其它姓名\",\"fieldType\":\"input\"},{\"field\":\"createdDate1\",\"title\":\"其它时间\",\"fieldType\":\"date\",\"required\":true},{}]]}}";
		System.out.println(jsonValues);
		System.out.println(FilenameUtils.getExtension("abc.xls"));
	}

	@Test
	public void testOther5() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("a", "");
		System.out.println(params.containsKey("a"));
	}

	@Test
	public void testOther4() {
		String sql = null;//"SELECT TABLE_REN.VC_NAME  FROM (SELECT TABLE_REN_REF.*, ROWNUM RN FROM (SELECT * FROM TSYSINFO WHERE 1=1  {sort.null = order by {sort.values}}) TABLE_REN_REF WHERE ROWNUM <= {page.end}) TABLE_REN WHERE TABLE_REN.RN >= {page.start}";
		sql = "select count(*)  {where.null.createDateS= and userName alike '%{where.value.createDateS}%'} from iqc_basic_user  where 1=1 {where.null.name= and userName like '%{where.value.name}%'} {where.null.name= and userName like '%{where.value.name}%'}";
		Map<String, String> params = new HashMap<String, String>();
//		params.put(RegexSqlUtil.SqlConstant.KEY_SQL, sql);
//		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "createDateS");
//		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "2012");
//		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_SORT_VALUES, "name desc,createDate asc");
//		String newSql = RegexSqlUtil.replaceSql(params);
//		System.out.println("1:" + newSql);
		String newSql = sql;
		params.put(RegexSqlUtil.SqlConstant.KEY_SQL, newSql);
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "name");
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "张军");

		params.put(RegexSqlUtil.SqlConstant.KEY_PAGE_START, "100");
		params.put(RegexSqlUtil.SqlConstant.KEY_PAGE_END, "888");
		newSql = RegexSqlUtil.replaceSql(params);
		System.out.println("2:" + newSql);
//		newSql = RegexSqlUtil.replaceSql(RegexSqlUtil.replaceSql(newSql));
		System.out.println("-:" + newSql);
	}

	@Test
	public void testOther3() {
		String sql = "select * from table where 1=1 {where.null.name = and name >= '{where.value.name=品}'} {where.null.createDateS= and createDate >= to_date('{where.value.createDateS=品}','yyyy-MM-dd HH:mi:ss')} {sort.null = order by {sort.values}}";
		Map<String, String> params = new HashMap<String, String>();
		params.put(RegexSqlUtil.SqlConstant.KEY_SQL, sql);
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "createDateS");
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "2012");
		String newSql = RegexSqlUtil.replaceSql(params);
		System.out.println("1:" + newSql);
		params.put(RegexSqlUtil.SqlConstant.KEY_SQL, newSql);
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "name");
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "张军");
		params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_SORT_VALUES, "name desc,createDate asc");
		newSql = RegexSqlUtil.replaceSql(params);
		System.out.println("2:" + newSql);
		newSql = RegexSqlUtil.replaceSql(RegexSqlUtil.replaceSql(newSql));
		System.out.println("-:" + newSql);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOther2() {
		String jsonValues = "{\"includeQueryPage\":\"/WEB-INF/page/framework/head.jsp\",\"query\":{\"other\":{\"buttonWidth\":\"16%\"},\"list\":[[{\"field\":\"name\",\"title\":\"账套名称\",\"fieldType\":\"input\",\"textWidth\":\"10%\",\"valueWidth\":\"18%\"},{\"field\":\"createdDate\",\"title\":\"创建时间\",\"fieldType\":\"date\"},{\"field\":\"isDelete\",\"title\":\"删除状态\",\"fieldType\":\"combobox\"}],[{\"field\":\"name1\",\"title\":\"其它姓名\",\"fieldType\":\"input\"},{\"field\":\"createdDate1\",\"title\":\"其它时间\",\"fieldType\":\"date\",\"required\":false},{}]]}}";
		Map<String, Object> params = JSON.parseObject(jsonValues, Map.class);
		System.out.println(params);
		for (String key : params.keySet()) {
			System.out.println(key);
			System.out.println(params.get(key));
			if ("query".equalsIgnoreCase(key)) {
				List<List<Map<String, Object>>> queryLists = null;
				// queryLists = JSON.parseObject(JavaUtil.objToStr(params.get(key)), List.class);
				queryLists = (List<List<Map<String, Object>>>) params.get(key);
				for (List<Map<String, Object>> queryList : queryLists) {
					// 循环行
					System.out.println("循环行。。。");
					for (Map<String, Object> queryMap : queryList) {
						String field = JavaUtil.objToStr(queryMap.get("field"));
						String fieldType = JavaUtil.objToStr(queryMap.get("fieldType"));
						System.out.println(field + "," + fieldType);
					}
				}

			}

		}

	}

	@Test
	public void testOther() {
		List<Map<String, Object>> titleNames = new ArrayList<Map<String, Object>>();
		Map<String, Object> titleName = new HashMap<String, Object>();
		titleName.put("title", "序号");
		titleName.put("field", "id");
		titleName.put("width", 700);
		titleName.put("sortable", false);
		titleNames.add(titleName);

		titleName = new HashMap<String, Object>();
		titleName.put("title", "客户名称");
		titleName.put("field", "name");
		titleName.put("width", 700);
		titleName.put("sortable", false);
		titleNames.add(titleName);

		titleName = new HashMap<String, Object>();
		titleName.put("title", "创建时间");
		titleName.put("field", "createdDate");
		titleName.put("width", 700);
		titleName.put("sortable", false);
		titleNames.add(titleName);

		System.out.println(JSON.toJSONString(titleNames));
		System.out.println(JSON.toJSON(titleNames));

	}
}
