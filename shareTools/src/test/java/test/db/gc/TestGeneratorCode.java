package test.db.gc;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.junit.Test;

import zj.jdbc.bean.DBInfo;
import zj.jdbc.util.JdbcUtil;
import zj.serverTimeout.util.ServiceConnect;

public class TestGeneratorCode {
	private transient static final Logger log = Logger.getLogger(TestGeneratorCode.class);
	private Connection conn;
	@Test
	public void connDb() {
		DBInfo dbInfo = new DBInfo();
		try {
			dbInfo.setUserName("zhangjun");
			dbInfo.setPassword("zhangjun");
			dbInfo.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=UTF-8");
			dbInfo.setDbType(JdbcUtil.DB_TYPE_MYSQL);
			log.debug("数据库类型:" + dbInfo.getDbType());
			log.debug("数据库URL:" + dbInfo.getUrl());
			log.debug("数据库用户名:" + dbInfo.getUserName());
			log.debug("数据库密码:" + dbInfo.getPassword());
			conn = (Connection) ServiceConnect.getServiceValue(new JdbcUtil(), "getConnInstance", new Object[] { dbInfo }, 5000);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(conn);
		JdbcUtil.closeDB(conn);
	}
}
