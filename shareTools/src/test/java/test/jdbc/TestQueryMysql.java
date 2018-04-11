package test.jdbc;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;
import zj.jdbc.util.JdbcUtil;
import zj.regex.util.RegexSqlUtil;

public class TestQueryMysql extends TestBase {
	@Before
	public void connection() throws Exception {
		connMysql();
	}

	@Test
	public void queryDDL() {
		try {
			// String[] types = new String[] { "TABLE" };
			// conn.getCatalog()
			// this.conn.getMetaData().getUserName()
			// ResultSet tableSet = this.conn.getMetaData().getTables(null, null, JavaUtil.toUpperCase("email_config_name"), types);
			String tableName = JavaUtil.toUpperCase("email_config_name");
			ResultSet rs = this.conn.getMetaData().getColumns(null, null, tableName, "%");
			System.out.println("所有列信息");
			while (rs.next()) {
				String columnName = JavaUtil.toUpperCase(rs.getString("COLUMN_NAME"));// 列名;
				int dataType = rs.getInt("DATA_TYPE");// 类型
				String typeName = JavaUtil.toUpperCase(rs.getString("TYPE_NAME"));// 类型名称
				System.out.println(columnName + "-" + dataType + "-" + typeName);
			}
			JdbcUtil.closeDB(rs);
			rs = this.conn.getMetaData().getPrimaryKeys(null, null, tableName);
			System.out.println("所有主键");
			while (rs.next()) {
				String columnName = JavaUtil.toUpperCase(rs.getString("COLUMN_NAME"));// 列名;
				System.out.println(columnName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void queryDatas() {
		try {
			psQuery = conn.prepareStatement("select code,name,name_head_pinyin from forum_stock");
			rs = psQuery.executeQuery();
			rs.last();
			int count = rs.getRow();
			System.out.println("总量:" + count);
			rs.beforeFirst();
			int ii = 0;
			String content = "";
			while (rs.next()) {
				content += "_t.push({val:\""+rs.getObject("code")+"\",val2:\""+rs.getObject("name")+"\",val3:\""+rs.getObject("name_head_pinyin")+"\"});\n";
//				System.out.println(content);
//				if (ii++==2){
//					break;
//				}
			}
			FileUtil.write(new File("D:\\a.txt"), content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void queryData() {
		try {
			psQuery = conn.prepareStatement("select * from report_sql where id='reportsql-be387517114045afb118f0bb3606624a'");
			rs = psQuery.executeQuery();
			rs.last();
			int count = rs.getRow();
			System.out.println("总量:" + count);
			rs.beforeFirst();
			Map<String, String> params = new HashMap<String, String>();
			while (rs.next()) {
				System.out.println(rs.getObject("Sql_Count_Content"));
				String sqlCount = JavaUtil.objToStr(rs.getObject("Sql_Count_Content"));
				params.put(RegexSqlUtil.SqlConstant.KEY_SQL, sqlCount);
				params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_NAME, "endMakeDate");
				params.put(RegexSqlUtil.SqlConstant.KEY_PLACE_WHERE_VALUE, "2016-01-14");
				sqlCount = RegexSqlUtil.replaceSql(params);
				System.out.println("1:" + sqlCount);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
