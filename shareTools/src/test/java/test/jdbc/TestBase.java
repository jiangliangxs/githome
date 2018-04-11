package test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;

import zj.jdbc.bean.DBInfo;
import zj.jdbc.util.JdbcUtil;

/**
 * 数据库连接
 * 
 * @author zhangjun
 * 
 */
public class TestBase {
	protected Connection conn = null;
	protected PreparedStatement psInsert = null;
	protected PreparedStatement psDelete = null;
	protected PreparedStatement psUpdate = null;
	protected PreparedStatement psQuery = null;
	protected ResultSet rs = null;

	// @Before
	// public void connection() throws Exception {
	// connMysql();
	// // connOracle();
	// }
	@After
	public void closeDB() {
		JdbcUtil.closeDB(rs);
		JdbcUtil.closeDB(psUpdate);
		JdbcUtil.closeDB(psInsert);
		JdbcUtil.closeDB(psDelete);
		JdbcUtil.closeDB(psUpdate);
		JdbcUtil.closeDB(psQuery);
		JdbcUtil.closeDB(conn);
	}

	public void connMysql() throws Exception {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbType(JdbcUtil.DB_TYPE_MYSQL);

		dbInfo.setUserName("abc");
		dbInfo.setPassword("12345");
		dbInfo.setDbName("msthforum");
		dbInfo.setIp("127.0.0.1");

		// dbInfo.setUserName("root");
		// dbInfo.setPassword("zhangjun");
		// dbInfo.setDbName("msthdb_prepare");
		// dbInfo.setIp("127.0.0.1");

		conn = JdbcUtil.getConnInstance(dbInfo);
		System.out.println("连接数据库对象:" + conn);
	}

	public void connOracle() throws Exception {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbType(JdbcUtil.DB_TYPE_ORACLE);

		dbInfo.setUserName("abc");
		dbInfo.setPassword("12345");
		dbInfo.setDbName("orcl");
		dbInfo.setIp("127.0.0.1");

		conn = JdbcUtil.getConnInstance(dbInfo);
		System.out.println("连接数据库对象:" + conn);
	}
}
