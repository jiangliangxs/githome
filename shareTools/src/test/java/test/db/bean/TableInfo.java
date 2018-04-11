package test.db.bean;

import java.util.List;

/**
 * 表信息<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class TableInfo {
	private String dbName;
	private String comment;
	private String dbKeys;
	private List<ColumnInfo> columns;

	/**
	 * 表名(必须)
	 * 
	 * @return 表名
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * 表名(必须)
	 * 
	 * @param dbName
	 *            表名
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * 表备注(非必须,字符以[单引']号隔开)
	 * 
	 * @return 表备注
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 表备注(非必须,字符以[单引']号隔开)
	 * 
	 * @param comment
	 *            表备注
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 主键集合(非必须,以[逗号,]号分割)
	 * 
	 * @return 主键集合
	 */
	public String getDbKeys() {
		return dbKeys;
	}

	/**
	 * 主键集合(非必须,以[逗号,]号分割)
	 * 
	 * @param dbKeys
	 *            主键集合
	 */
	public void setDbKeys(String dbKeys) {
		this.dbKeys = dbKeys;
	}

	/**
	 * 列集合(必须)
	 * 
	 * @return 列集合
	 */
	public List<ColumnInfo> getColumns() {
		return columns;
	}

	/**
	 * 列集合(必须)
	 * 
	 * @param columns
	 *            列集合
	 */
	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

}
