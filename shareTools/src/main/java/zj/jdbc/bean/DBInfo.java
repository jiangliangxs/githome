package zj.jdbc.bean;

import java.io.Serializable;

import zj.jdbc.util.JdbcUtil;
/**
 * 类名 ：DBInfo<br>
 * 概况 ：DB信息<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class DBInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**数据库驱动名**/
	private String driverName;
	/**数据库名**/
	private String dbName;
	/**数据库用户名**/
	private String userName;
	/**数据库密码**/
	private String password;
	/**数据库IP**/
	private String ip;
	/**数据库Port**/
	private String port;
	/**数据库类型**/
	private String dbType;
	/**数据库服务名**/
	private String serverName;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private String url;
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
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
	 * 
	 * @param dbType
	 * @param dbInfo
	 * @return
	 * @throws Exception
	 */
	public void chgDBInfo() throws Exception {
		if (JdbcUtil.DB_TYPE_MYSQL.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("com.mysql.jdbc.Driver");
			}
			if (chkNull(getIp())) {
				setIp("127.0.0.1");
			}
			if (chkNull(getPort())) {
				setPort("3306");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:mysql://" + getIp() + ":3306/" + getDbName());
			}
		} else if (JdbcUtil.DB_TYPE_ORACLE.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("oracle.jdbc.driver.OracleDriver");
			}
			if (chkNull(getIp())) {
				setIp("127.0.0.1");
			}
			if (chkNull(getPort())) {
				setPort("1521");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:oracle:thin:@" + getIp() + ":1521:" + getDbName());
			}
		} else if (JdbcUtil.DB_TYPE_MSSQL.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			}
			if (chkNull(getIp())) {
				setIp("127.0.0.1");
			}
			if (chkNull(getPort())) {
				setPort("1433");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:sqlserver://" + getIp() + ":1433;DatabaseName=" + getDbName());
			}
		} else if (JdbcUtil.DB_TYPE_SYBASE.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("com.sybase.jdbc2.jdbc.SybDriver");
			}
			if (chkNull(getIp())) {
				setIp("127.0.0.1");
			}
			if (chkNull(getPort())) {
				setPort("2638");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:sybase:Tds:" + getIp() + "2638?ServiceName=" + getDbName());
			}
		} else if (JdbcUtil.DB_TYPE_DB2.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("com.ibm.db2.jcc.DB2Driver");
			}
			if (chkNull(getIp())) {
				setIp("127.0.0.1");
			}
			if (chkNull(getPort())) {
				setPort("50000");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:db2://" + getIp() + ":50000/" + getDbName());
			}
		} else if (JdbcUtil.DB_TYPE_SQLITE.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("org.sqlite.JDBC");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:sqlite:./" + getDbName());
			}
		} else if (JdbcUtil.DB_TYPE_INFORMIX.equalsIgnoreCase(getDbType())) {
			if (chkNull(getDriverName())) {
				setDriverName("com.informix.jdbc.IfxDriver");
			}
			if (chkNull(getIp())) {
				setIp("127.0.0.1");
			}
			if (chkNull(getPort())) {
				setPort("9088");
			}
			if (chkNull(getServerName())) {
				setServerName("informix");
			}
			if (chkNull(getUrl())) {
				setUrl("jdbc:informix-sqli://" + getIp() + ":9088/" + getDbName() + ":informixserver=" + getServerName());
			}
		}
	}

}
