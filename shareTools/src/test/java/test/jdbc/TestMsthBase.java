package test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

import zj.jdbc.bean.DBInfo;
import zj.jdbc.util.JdbcUtil;

public class TestMsthBase {
	protected Connection src_conn = null;
	protected PreparedStatement src_psInsert = null;
	protected PreparedStatement src_psDelete = null;
	protected PreparedStatement src_psUpdate = null;
	protected PreparedStatement src_psQuery = null;
	protected ResultSet src_rs = null;

	protected Connection dest_conn = null;
	protected PreparedStatement dest_psInsert = null;
	protected PreparedStatement dest_psDelete = null;
	protected PreparedStatement dest_psUpdate = null;
	protected PreparedStatement dest_psQuery = null;
	protected ResultSet dest_rs = null;

	public void connOracle() throws Exception {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbType(JdbcUtil.DB_TYPE_ORACLE);

		dbInfo.setUserName("zhangjun");
		dbInfo.setPassword("123456");
		dbInfo.setDbName("test");
		dbInfo.setIp("127.0.0.1");

		src_conn = JdbcUtil.getConnInstance(dbInfo);
		System.out.println("连接数据库对象:" + src_conn);
	}

	public void connMysql() throws Exception {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbType(JdbcUtil.DB_TYPE_MYSQL);

		dbInfo.setUserName("zhangjun");
		dbInfo.setPassword("123456");
		dbInfo.setDbName("test");
		dbInfo.setIp("127.0.0.1");
		dest_conn = JdbcUtil.getConnInstance(dbInfo);
		System.out.println("连接数据库对象:" + dest_conn);
	}

	@Before
	public void connection() throws Exception {
		connOracle();
		connMysql();
	}

	@Test
	public void saveReportSeason() {

	}
}
