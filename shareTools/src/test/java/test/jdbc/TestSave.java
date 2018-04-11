package test.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import zj.jdbc.bean.DBInfo;
import zj.jdbc.util.JdbcUtil;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class TestSave {
	public void m2(){
		
	}
	public static void main(String[] args) throws Exception {
		Connection conn = null;
		DBInfo dbInfo = null;
		// // mysql
		// dbInfo = new DBInfo();
		// dbInfo.setDbType(JdbcUtil.DB_TYPE_MYSQL);
		// dbInfo.setUserName("zhangjun");
		// dbInfo.setPassword("zhangjun");
		// dbInfo.setDbName("sshe-init");
		// conn = JdbcUtil.getConnInstance(dbInfo);
		// System.out.println("mysql:" + conn);
		// // oracle
		// dbInfo = new DBInfo();
		// dbInfo.setDbType(JdbcUtil.DB_TYPE_ORACLE);
		// dbInfo.setUserName("zhangjun");
		// dbInfo.setPassword("qc_jhxyk");
		// dbInfo.setDbName("orcl");
		// dbInfo.setIp("212.111.111.120");
		// conn = JdbcUtil.getConnInstance(dbInfo);
		// System.out.println("oracle:" + conn);
		// // informix
		// dbInfo = new DBInfo();
		// dbInfo.setDbType(JdbcUtil.DB_TYPE_INFORMIX);
		// dbInfo.setUserName("informix");
		// dbInfo.setPassword("informix");
		// dbInfo.setDbName("iqc");
		// dbInfo.setIp("212.111.111.81");
		// conn = JdbcUtil.getConnInstance(dbInfo);
		// System.out.println("informix:" + conn);
		// // db2
		// dbInfo = new DBInfo();
		// dbInfo.setDbType(JdbcUtil.DB_TYPE_DB2);
		// dbInfo.setUserName("db2inst1");
		// dbInfo.setPassword("db2inst1");
		// dbInfo.setDbName("iqc");
		// dbInfo.setIp("212.111.111.144");
		// conn = JdbcUtil.getConnInstance(dbInfo);
		// System.out.println("db2:" + conn);
		// sql server
		dbInfo = new DBInfo();
		dbInfo.setDbType(JdbcUtil.DB_TYPE_MSSQL);
		dbInfo.setUserName("sa");
		dbInfo.setPassword("sa");
		dbInfo.setDbName("zhangjun");
		dbInfo.setIp("212.111.111.109");
		conn = JdbcUtil.getConnInstance(dbInfo);
		SQLServerDriver sd = new SQLServerDriver();
		System.out.println("sql server:" + conn + "-sd.getMajorVersion():" + sd.getMajorVersion() + "-sd.getMinorVersion():" + sd.getMinorVersion());
	}

	/**
	 * 值转换
	 * 
	 * @param code
	 * @param rs
	 * @param columnName
	 * @return
	 */
	public static Object getCastValue(ColumnInfo columnInfo, ResultSet rs) {
		Object tv = null;
		try {
			switch (columnInfo.DATA_TYPE) {
			case Types.DATE:
				tv = rs.getTimestamp(columnInfo.NAME);
				break;
			default:
				tv = rs.getObject(columnInfo.NAME);
			}
		} catch (SQLException e) {
			tv = null;
			e.printStackTrace();
		}
		return tv;
	}
}
