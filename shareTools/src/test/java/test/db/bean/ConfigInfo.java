package test.db.bean;

import java.util.List;

/**
 * 所有配置信息<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ConfigInfo {
	private String dbType;
	private List<TableInfo> tables;

	/**
	 * 数据库类型(非必须,默认:mysql):mysql,oracle,postgresql,db2,informix,mssql
	 * 
	 * @return 数据库类型
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * 数据库类型(非必须,默认:mysql):mysql,oracle,postgresql,db2,informix,mssql
	 * 
	 * @param dbType
	 *            数据库类型
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * 表集合(必须)
	 * 
	 * @return 表集合
	 */
	public List<TableInfo> getTables() {
		return tables;
	}

	/**
	 * 表集合(必须)
	 * 
	 * @param tables
	 *            表集合
	 */
	public void setTables(List<TableInfo> tables) {
		this.tables = tables;
	}
}
