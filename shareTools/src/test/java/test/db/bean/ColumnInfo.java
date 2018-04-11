package test.db.bean;

import zj.check.util.CheckUtil;

/**
 * 表中的列信息<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ColumnInfo {
	private String dbName;
	private String dbType;
	private String dbLength;
	private String dbPrecision;
	private String propertyName;
	private String propertyType;
	private String comment;
	private String dbDefaultValue;
	private boolean dbMandatory;

	/**
	 * 数据库列名(必须)
	 * 
	 * @return 数据库列名
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * 数据库列名(必须)
	 * 
	 * @param dbName
	 *            数据库列名
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * 数据库类型(必须)
	 * 
	 * @return 数据库类型
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * 数据库类型(必须)
	 * 
	 * @param dbType
	 *            数据库类型
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * 长度(非必须)
	 * 
	 * @return 长度
	 */
	public String getDbLength() {
		return dbLength;
	}

	/**
	 * 长度(非必须)
	 * 
	 * @param dbLength
	 *            长度
	 */
	public void setDbLength(String dbLength) {
		this.dbLength = dbLength;
	}

	/**
	 * 精度(非必须)
	 * 
	 * @return 精度
	 */
	public String getDbPrecision() {
		return dbPrecision;
	}

	/**
	 * 精度(非必须)
	 * 
	 * @param dbPrecision
	 *            精度
	 */
	public void setDbPrecision(String dbPrecision) {
		this.dbPrecision = dbPrecision;
	}

	/**
	 * java属性名(非必须,默认数据库字段以_分割变大写)
	 * 
	 * @return java属性名
	 */
	public String getPropertyName() {
		if (CheckUtil.isNull(propertyName)) {

		}
		return propertyName;
	}

	/**
	 * java属性名(非必须,默认数据库字段以_分割变大写)
	 * 
	 * @param propertyName
	 *            java属性名
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * java类型(非必须,默认系统自动判断)
	 * 
	 * @return java类型
	 */
	public String getPropertyType() {
		return propertyType;
	}

	/**
	 * java类型(非必须,默认系统自动判断)
	 * 
	 * @param propertyType
	 *            java类型
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	/**
	 * 列备注(非必须,字符以[单引']号隔开)
	 * 
	 * @return 列备注
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 列备注(非必须,字符以[单引']号隔开)
	 * 
	 * @param comment
	 *            列备注
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 字段默认值(非必须,字符以[单引']号隔开)
	 * 
	 * @return 字段默认值
	 */
	public String getDbDefaultValue() {
		return dbDefaultValue;
	}

	/**
	 * 字段默认值(非必须,字符以[单引']号隔开)
	 * 
	 * @param dbDefaultValue
	 *            字段默认值
	 */
	public void setDbDefaultValue(String dbDefaultValue) {
		this.dbDefaultValue = dbDefaultValue;
	}

	/**
	 * 是否强制不为NULL(非必须,默认:false)
	 * 
	 * @return 是否强制不为NULL
	 */
	public boolean isDbMandatory() {
		return dbMandatory;
	}

	/**
	 * 是否强制不为NULL(非必须,默认:false)
	 * 
	 * @param dbMandatory
	 *            是否强制不为NULL
	 */
	public void setDbMandatory(boolean dbMandatory) {
		this.dbMandatory = dbMandatory;
	}
}
