package test.db.util;

/**
 * 概况 ：xml表结构配置类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ConfigConstant {
	/**
	 * 表集合(必须)
	 */
	public static final String ROOT = "Root";
	/**
	 * 创建表SQL文件存放地址(非必须,如果不指定值,则默认C:/[yyyyMMddHHmmssSSS].sql)
	 */
	public static final String CREATE_SQL_FILE = "createSqlFile";
	/**
	 * 表集合(必须)
	 */
	public static final String DB_TYPE = "dbType";
	/**
	 * 表集合(必须)
	 */
	public static final String TABLES = "Tables";
	/**
	 * 表(必须)
	 */
	public static final String TABLE = "Table";
	/**
	 * 表名(必须)
	 */
	public static final String TABLE_DB_NAME = "dbName";
	/**
	 * 表备注(非必须,字符以[单引']号隔开)
	 */
	public static final String TABLE_COMMENT = "comment";
	/**
	 * 列集合(必须)
	 */
	public static final String TABLE_COLUMNS = "Columns";
	/**
	 * 列(必须)
	 */
	public static final String TABLE_COLUMN = "Column";

	/**
	 * 数据库列名(必须)
	 */
	public static final String TABLE_COLUMN_DB_NAME = "dbName";
	/**
	 * 数据库类型(必须)
	 */
	public static final String TABLE_COLUMN_DB_TYPE = "dbType";
	/**
	 * 长度(非必须)
	 */
	public static final String TABLE_COLUMN_DB_LENGTH = "dbLength";
	/**
	 * 精度(非必须)
	 */
	public static final String TABLE_COLUMN_DB_PRECISION = "dbPrecision";
	/**
	 * 字段默认值(非必须,字符以[单引']号隔开)
	 */
	public static final String TABLE_COLUMN_DB_DEFAULT_VALUE = "dbDefaultValue";
	/**
	 * 是否强制不为NULL(非必须,默认:false)
	 */
	public static final String TABLE_COLUMN_DB_MANDATORY = "dbMandatory";
	/**
	 * java属性名(非必须,默认数据库字段以_分割变大写)
	 */
	public static final String TABLE_COLUMN_PROPERTY_NAME = "propertyName";
	/**
	 * java类型(非必须,默认系统自动判断)
	 */
	public static final String TABLE_COLUMN_PROPERTY_TYPE = "propertyType";
	/**
	 * 列备注(非必须,字符以[单引']号隔开)
	 */
	public static final String TABLE_COLUMN_COMMENT = "comment";
	/**
	 * 主键集合(非必须,以,以[逗号,]号分割)
	 */
	public static final String TABLE_COLUMN_DB_KEYS = "dbKeys";
}
