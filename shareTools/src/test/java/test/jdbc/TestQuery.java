package test.jdbc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.jdbc.util.JdbcUtil;
import zj.regex.util.RegexUtil;

public class TestQuery extends TestBase {
	@Before
	public void connection() throws Exception {
		connOracle();
	}
	@Test
	public void queryDDL() {
		
	}
	@Test
	public void queryDatas() {
		try {
			int sqlCount = 0;
			psQuery = conn.prepareStatement("select count(*) from TSYSINFO");
			rs = psQuery.executeQuery();
			if (rs.next()) {
				sqlCount = rs.getInt(1);
			}
			JdbcUtil.closeDB(rs);
			JdbcUtil.closeDB(psQuery);
			System.out.println("总量:" + sqlCount);
			if (sqlCount <= 0) {
				return;
			}
			// 计算总数
			int sqlRows = 5;
			int sqlPageCount = sqlCount / sqlRows;
			int sqlModPage = sqlCount % sqlRows;
			if (sqlModPage != 0) {
				sqlPageCount++;
			}
			String sqlPage = "SELECT TABLE_REN.VC_NAME  FROM (SELECT TABLE_REN_REF.*, ROWNUM RN FROM (SELECT * FROM TSYSINFO WHERE 1=1  ORDER BY VC_NAME,L_ID) TABLE_REN_REF WHERE ROWNUM <= {_startPage}) TABLE_REN WHERE TABLE_REN.RN >= {_endPage}";
			Map<String, String> nameValueMap = new HashMap<String, String>();
			for (int page = 1; page <= sqlPageCount; page++) {
				try {
					// infromix
					// sqlPage = "select skip " + (page * sqlRows - sqlRows) + " first " + sqlRows + " field1,field2 from tableName";
					// mysql
					// sqlPage = "select field1,field2 from tableName limit " + (page - 1) * sqlRows + "," + sqlRows;
					// sqlPage = "select id from auto_zhangjun limit " + (page - 1) * sqlRows + "," + sqlRows;
					// oracle
					// SELECT TABLE_REN.[*] FROM (SELECT TABLE_REN_REF[.*], ROWNUM RN FROM (SELECT * FROM [TABLE_NAME] WHERE 1=1 ORDER BY [*]) TABLE_REN_REF WHERE ROWNUM <= [15]--(pageEndIndex:结束索引-pageStartIndex:开始索引)=每页显示的条数) TABLE_REN WHERE TABLE_REN.RN >= [11]--pageStartIndex:开始索引[1开始]
					System.out.println(((page - 1) * sqlRows + 1) + "," + (page * sqlRows));
					// sqlPage = "SELECT TABLE_REN.VC_NAME  FROM (SELECT TABLE_REN_REF.*, ROWNUM RN FROM (SELECT * FROM TSYSINFO WHERE 1=1  ORDER BY VC_NAME,L_ID) TABLE_REN_REF WHERE ROWNUM <= " + (page * sqlRows) + ") TABLE_REN WHERE TABLE_REN.RN >= " + ((page - 1) * sqlRows + 1);
					nameValueMap.put("_startPage", JavaUtil.objToStr(((page - 1) * sqlRows + 1)));

					RegexUtil.fillString(sqlPage, nameValueMap);
					psQuery = conn.prepareStatement(sqlPage, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rs = psQuery.executeQuery();
					rs.last();
					System.out.println("第" + page + "页，总量：" + rs.getRow());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcUtil.closeDB(rs);
					JdbcUtil.closeDB(psQuery);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
