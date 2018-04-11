package zj.jdbc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import zj.jdbc.bean.DBInfo;
/**
 * 类名 ：JdbcUtil<br>
 * 概况 ：JDBC工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class JdbcUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	// driver database
	// connection database
	// closing database
	public static Map<String, DBInfo> dbMap;
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_POSTGRESQL = "postgresql";
	public static final String DB_TYPE_ORACLE = "oracle";
	public static final String DB_TYPE_DB2 = "db2";
	public static final String DB_TYPE_MSSQL = "mssql";
	public static final String DB_TYPE_H2 = "h2";
	public static final String DB_TYPE_DERBY = "derby";
	public static final String DB_TYPE_SQLITE = "sqlite";
	public static final String DB_TYPE_MSACCESS = "msaccess";
	public static final String DB_TYPE_SYBASE = "sybase";
	public static final String DB_TYPE_INFORMIX = "informix";
	
	public static final String DB_TYPE_MYSQL_URL = "jdbc:mysql://<hostname>[<:3306>]/<dbname>";
	public static final String DB_TYPE_ORACLE_URL = "jdbc:oracle:thin:@<server>[:<1521>]:<database_name>";
	public static final String DB_TYPE_INFORMIX_URL = "jdbc:informix-sqli://<hostname>[<:9088>]/<dbname>:informixserver=<servername>";
	public static final String DB_TYPE_DB2_URL = "jdbc:db2://<hostname>[<:50000>]/<dbname>";
	static {
		dbMap = new HashMap<String, DBInfo>();

	}
	/**
	 * 
	 * @param s
	 * @return
	 */
	private static boolean chkNull(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * 获取连接
	 * @param dbInfo
	 * @return
	 * @throws Exception
	 */
	public static synchronized Connection getConnInstance(DBInfo dbInfo) throws Exception {
		java.util.Properties props = new java.util.Properties();
		props.put("user", dbInfo.getUserName());
		props.put("password", dbInfo.getPassword());
		if (DB_TYPE_MYSQL.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("com.mysql.jdbc.Driver");
			}
			if (chkNull(dbInfo.getIp())) {
				dbInfo.setIp("127.0.0.1");
			}
			if (chkNull(dbInfo.getPort())) {
				dbInfo.setPort("3306");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:mysql://" + dbInfo.getIp() + ":3306/" + dbInfo.getDbName());
			}
			//添加会很慢
			//props.put("useInformationSchema", "true");
		} else if (DB_TYPE_ORACLE.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("oracle.jdbc.driver.OracleDriver");
			}
			if (chkNull(dbInfo.getIp())) {
				dbInfo.setIp("127.0.0.1");
			}
			if (chkNull(dbInfo.getPort())) {
				dbInfo.setPort("1521");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:oracle:thin:@" + dbInfo.getIp() + ":1521:" + dbInfo.getDbName());
			}
			props.put("remarksReporting", "true");
		} else if (DB_TYPE_MSSQL.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			}
			if (chkNull(dbInfo.getIp())) {
				dbInfo.setIp("127.0.0.1");
			}
			if (chkNull(dbInfo.getPort())) {
				dbInfo.setPort("1433");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:sqlserver://" + dbInfo.getIp() + ":1433;DatabaseName=" + dbInfo.getDbName());
			}
		} else if (DB_TYPE_SYBASE.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("com.sybase.jdbc2.jdbc.SybDriver");
			}
			if (chkNull(dbInfo.getIp())) {
				dbInfo.setIp("127.0.0.1");
			}
			if (chkNull(dbInfo.getPort())) {
				dbInfo.setPort("2638");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:sybase:Tds:" + dbInfo.getIp() + "2638?ServiceName=" + dbInfo.getDbName());
			}
		} else if (DB_TYPE_DB2.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("com.ibm.db2.jcc.DB2Driver");
			}
			if (chkNull(dbInfo.getIp())) {
				dbInfo.setIp("127.0.0.1");
			}
			if (chkNull(dbInfo.getPort())) {
				dbInfo.setPort("50000");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:db2://" + dbInfo.getIp() + ":50000/" + dbInfo.getDbName());
			}
		} else if (DB_TYPE_SQLITE.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("org.sqlite.JDBC");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:sqlite:./" + dbInfo.getDbName());
			}
		} else if (DB_TYPE_INFORMIX.equalsIgnoreCase(dbInfo.getDbType())) {
			if (chkNull(dbInfo.getDriverName())) {
				dbInfo.setDriverName("com.informix.jdbc.IfxDriver");
			}
			if (chkNull(dbInfo.getIp())) {
				dbInfo.setIp("127.0.0.1");
			}
			if (chkNull(dbInfo.getPort())) {
				dbInfo.setPort("9088");
			}
			if (chkNull(dbInfo.getServerName())) {
				dbInfo.setServerName("informix");
			}
			if (chkNull(dbInfo.getUrl())) {
				dbInfo.setUrl("jdbc:informix-sqli://" + dbInfo.getIp() + ":9088/" + dbInfo.getDbName() + ":informixserver=" + dbInfo.getServerName());
			}
		}
		// 加载驱动（loading driver)
		// Driver driver = new oracle.jdbc.driver.OracleDriver();//2种
		// DriverManager.registerDriver(driver);//3种设置环境变量
		Class.forName(dbInfo.getDriverName());// 1种
		return DriverManager.getConnection(dbInfo.getUrl(), props);
	}

	/**
	 * 
	 * @param dbType
	 * @param dbInfo
	 * @return
	 * @throws Exception
	 */
	public static synchronized Connection getConnInstanceChg(DBInfo dbInfo) throws Exception {
		dbInfo.chgDBInfo();
		java.util.Properties props = new java.util.Properties();
		props.put("user", dbInfo.getUserName());
		props.put("password", dbInfo.getPassword());
		if (DB_TYPE_ORACLE.equalsIgnoreCase(dbInfo.getDbType())) {
			props.put("remarksReporting", "true");
		}else if (DB_TYPE_MYSQL.equalsIgnoreCase(dbInfo.getDbType())) {
			//添加会很慢
			//props.put("useInformationSchema", "true");
		}
		// 加载驱动（loading driver)
		// Driver driver = new oracle.jdbc.driver.OracleDriver();//2种
		// DriverManager.registerDriver(driver);//3种设置环境变量
		Class.forName(dbInfo.getDriverName());// 1种
		return DriverManager.getConnection(dbInfo.getUrl(), props);
	}

	/**
	 *  关闭流
	 * 
	 * @param obj
	 */
	public static void closeDB(Object... obj) {
		if (obj == null)
			return;
		for (int i = 0; i < obj.length; i++) {
			try {
				if (obj[i] instanceof Statement) {
					((Statement) obj[i]).close();
				} else if (obj[i] instanceof ResultSet) {
					((ResultSet) obj[i]).close();
				} else if (obj[i] instanceof Connection) {
					((Connection) obj[i]).close();
				}
			} catch (Exception e) {
			}
		}
	}

	public static String prompt(String message) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String command = "";
		try {
			command = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return command;
	}

	public static void printRs(ResultSet rs) {
		if (rs == null)
			return;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					sb.append(rsmd.getColumnName(i));
					sb.append(" = " + rs.getString(i) + "		");
				}
				sb.append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取类型名
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String getTypeName(int type) throws Exception {
		Field[] fields = Types.class.getDeclaredFields();
		for (Field field : fields) {
			Object o = field.get(null);
			if (o instanceof Integer) {
				if (Integer.parseInt(String.valueOf(o)) == type) {
					return field.getName();
				}
			}
		}
		return "";
	}

}
