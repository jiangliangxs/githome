package test.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import zj.java.util.JavaUtil;
import zj.jdbc.util.JdbcUtil;

public class TestUtil extends TestBase {
	@Before
	public void connection() throws Exception {
		connMysql();
	}

	@Test
	public void saveDate() {
		try {
			psInsert = conn.prepareStatement("insert into auto_zhangjun(id,title,title_num,content,money,publish_date) values(?,?,?,?,?,?)");
			for (int i = 0; i < 50000; i++) {
				psInsert.setObject(1, JavaUtil.getUUID());
				psInsert.setObject(2, "标题" + i);
				psInsert.setObject(3, i);
				psInsert.setObject(4, "内容" + i);
				psInsert.setObject(5, new BigDecimal(i));
				psInsert.setObject(6, new Date());
				psInsert.addBatch();
			}
			psInsert.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryData2() {
		try {
			psQuery = conn.prepareStatement("select * from auto_zhangjun");
			rs = psQuery.executeQuery();
			rs.last();
			int count = rs.getRow();
			System.out.println("总量:" + count);
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println(rs.getObject("id"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryData() {
		try {
			psQuery = conn.prepareStatement("select * from auto_zhangjun");
			rs = psQuery.executeQuery();
			rs.last();
			int count = rs.getRow();
			System.out.println("总量:" + count);
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println(rs.getObject("id"));
				break;
			}
			testClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryDatas() {
		try {
			int sqlCount = 0;
			psQuery = conn.prepareStatement("select count(id) from auto_zhangjun");
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
			int sqlRows = 22000;
			int sqlPageCount = sqlCount / sqlRows;
			int sqlModPage = sqlCount % sqlRows;
			if (sqlModPage != 0) {
				sqlPageCount++;
			}
			for (int page = 1; page <= sqlPageCount; page++) {
				try {
					String sqlPage = "";
					// infromix
					// sqlPage = "select skip " + (page * sqlRows - sqlRows) + " first " + sqlRows + " field1,field2 from tableName";
					// mysql
					// sqlPage = "select field1,field2 from tableName limit " + (page - 1) * sqlRows + "," + sqlRows;
					// sqlPage = "select id from auto_zhangjun limit " + (page - 1) * sqlRows + "," + sqlRows;
					// oracle
					// SELECT TABLE_REN.[*] FROM (SELECT TABLE_REN_REF[.*], ROWNUM RN FROM (SELECT * FROM [TABLE_NAME] WHERE 1=1 ORDER BY [*]) TABLE_REN_REF WHERE ROWNUM <= [15]--(pageEndIndex:结束索引-pageStartIndex:开始索引)=每页显示的条数) TABLE_REN WHERE TABLE_REN.RN >= [11]--pageStartIndex:开始索引[1开始]
					System.out.println(((page - 1) * sqlRows + 1) + "," + (page * sqlRows));
					sqlPage = "SELECT TABLE_REN.VC_NAME  FROM (SELECT TABLE_REN_REF.*, ROWNUM RN FROM (SELECT * FROM TSYSINFO WHERE 1=1  ORDER BY VC_NAME,L_ID) TABLE_REN_REF WHERE ROWNUM <= " + (page * sqlRows) + ") TABLE_REN WHERE TABLE_REN.RN >= " + ((page - 1) * sqlRows + 1);
					psQuery = conn.prepareStatement(sqlPage, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rs = psQuery.executeQuery();
					rs.last();
					System.out.println("第" + page + "页，总量：" + rs.getRow());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testClose() throws Exception {
		System.out.println("关闭ps前,rs是否关闭:" + rs.isClosed());
		psQuery.close();
		System.out.println("关闭ps后,rs是否关闭:" + rs.isClosed());
		// System.out.println("关闭rs前,ps是否关闭:" + ps.isClosed());
		// rs.close();
		// System.out.println("关闭rs后,ps是否关闭:" + ps.isClosed());
		// System.out.println("关闭conn前,rs是否关闭:" + rs.isClosed());
		// System.out.println("关闭conn前,ps是否关闭:" + ps.isClosed());
		// conn.close();
		// System.out.println("关闭conn后,rs是否关闭:" + rs.isClosed());
		// System.out.println("关闭conn后,ps是否关闭:" + ps.isClosed());
	}
}
