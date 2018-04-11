package test.db.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import test.db.bean.ColumnInfo;
import test.db.bean.ConfigInfo;
import test.db.bean.TableInfo;
import test.db.gc.TestXml;
import zj.check.util.CheckUtil;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;
import zj.jdbc.util.JdbcUtil;
import zj.reflect.util.TypeUtil;
import zj.xml.util.XmlUtil;

public class GenerateUtil {
	private transient static final Logger logger = Logger.getLogger(TestXml.class);

	/**
	 * 初使化配置文件
	 * 
	 * @return 配置对象
	 * @throws Exception
	 */
	@Test
	public static final ConfigInfo initConfig() throws Exception {
		return initConfig(GenerateUtil.class.getResource("/ftl/config.xml").getFile());
	}

	/**
	 * 初使化配置文件
	 * 
	 * @param xmlPath
	 *            xml路径
	 * @return 配置对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public static final ConfigInfo initConfig(String xmlPath) throws Exception {
		ConfigInfo configInfo = new ConfigInfo();
		logger.debug("转换xml为对象");
		File xmlFile = new File(xmlPath);
		String xml = null;
		xml = FileUtil.readString(xmlFile);
		// 解析xml文档
		Map<String, Object> xmlMap = XmlUtil.xmlToMapByXStream(xml, true);
		// 取得所有表根目录
		Map<String, Object> rootMap = JavaUtil.getValueForMap(xmlMap, ConfigConstant.ROOT);
		// 取得所有表集合
		Object tablesObj = rootMap.get(ConfigConstant.TABLES);
		// 表结构
		List<Map<String, Object>> tablesList = new ArrayList<Map<String, Object>>();
		if (tablesObj instanceof Map) {
			// 表示一个表
			Map<String, Object> tablesMap = (Map<String, Object>) tablesObj;
			// 添加至表集合中
			tablesList.add(JavaUtil.getValueForMap(tablesMap, ConfigConstant.TABLE));
		} else if (tablesObj instanceof List) {
			// 表示多张表
			List<Object> tablesTemp = (List<Object>) tablesObj;
			for (Object tablesMap : tablesTemp) {
				if (tablesMap instanceof Map) {
					// 添加至表集合中
					tablesList.add((Map<String, Object>) tablesMap);
				} else {
					logger.warn("数据格式不对:value-[" + tablesMap + "]");
				}
			}
		} else {
			// 数据格式不对
			logger.warn("数据格式不对:value-[" + tablesObj + "]");
		}
		// 取得数据库类型
		String dbType = JavaUtil.objToStr(rootMap.get(ConfigConstant.DB_TYPE));
		if (CheckUtil.isNull(dbType)) {
			// 默认mysql数据库
			dbType = JdbcUtil.DB_TYPE_MYSQL;
		}
		// 配置信息:设置数据库类型
		configInfo.setDbType(dbType);
		List<TableInfo> tables = new ArrayList<TableInfo>();
		// 配置信息:设置表集合信息
		configInfo.setTables(tables);
		// 循环所有表集合
		for (Map<String, Object> tableMap : tablesList) {
			// 表中的列结构
			List<Map<String, Object>> columnsList = new ArrayList<Map<String, Object>>();
			// 取得列集合
			Object columnsObj = tableMap.get(ConfigConstant.TABLE_COLUMNS);
			if (columnsObj instanceof Map) {
				// 表示一个列
				Map<String, Object> columnsMap = (Map<String, Object>) columnsObj;
				// 添加至表中的列集合中
				columnsList.add(JavaUtil.getValueForMap(columnsMap, ConfigConstant.TABLE_COLUMN));
			} else if (columnsObj instanceof List) {
				// 表示表中的多个列
				List<Object> columnsTemp = (List<Object>) columnsObj;
				for (Object columnsMap : columnsTemp) {
					if (columnsMap instanceof Map) {
						// 添加至表中的列集合中
						columnsList.add((Map<String, Object>) columnsMap);
					} else {
						logger.warn("数据格式不对:value-[" + columnsMap + "]");
					}
				}
			} else {
				// 数据格式不对
				logger.warn("数据格式不对:value-[" + columnsObj + "]");
			}
			// 配置信息:实例化表信息对象
			TableInfo tableInfo = new TableInfo();
			// 取得表名
			String tableName = JavaUtil.objToStr(tableMap.get(ConfigConstant.TABLE_DB_NAME));
			// 配置信息:表名
			tableInfo.setDbName(tableName);
			List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
			// 配置信息:列信息集合
			tableInfo.setColumns(columns);
			// 循环所有表中的列集合
			// 循环所有表集合
			for (int a1 = 0; a1 < columnsList.size(); a1++) {
				Map<String, Object> columnMap = columnsList.get(a1);
				// 配置信息:实例化列信息对象
				ColumnInfo columnInfo = new ColumnInfo();
				// 配置信息:数据库列名
				String columnName = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_NAME));
				columnInfo.setDbName(columnName);
				// 配置信息:数据库类型
				String columnType = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_TYPE));
				columnInfo.setDbType(columnType);
				// 配置信息:长度
				String columnLength = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_LENGTH));
				columnInfo.setDbLength(columnLength);
				// 配置信息:精度
				String columnPrecision = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_PRECISION));
				columnInfo.setDbPrecision(columnPrecision);
				// 配置信息:字段默认值
				String columnDefaultValue = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_DEFAULT_VALUE));
				columnInfo.setDbDefaultValue(columnDefaultValue);
				// 配置信息:字段非空
				String columnMandatory = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_MANDATORY));
				columnInfo.setDbMandatory(TypeUtil.Primitive.booleanValue(columnMandatory));
				// 配置信息:列备注
				String columnComment = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_COMMENT));
				columnInfo.setComment(columnComment);
				// 配置信息:属性名
				String propertyName = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_PROPERTY_NAME));
				if (CheckUtil.isNull(propertyName)) {
					propertyName = JavaUtil.upperCaseSplit(columnName.toLowerCase(), "_", false);
				}
				columnInfo.setPropertyName(propertyName);
				// 配置信息:属性类型
				String propertyType = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_PROPERTY_TYPE));
				if (CheckUtil.isNull(propertyType)) {
					propertyType = "String";
				}
				columnInfo.setPropertyType(propertyType);
				// 配置信息:添加列信息对象
				columns.add(columnInfo);
			}
			String tableKeys = JavaUtil.objToStr(tableMap.get(ConfigConstant.TABLE_COLUMN_DB_KEYS));
			// 配置信息:表主键
			tableInfo.setDbKeys(tableKeys);
			String tableComment = JavaUtil.objToStr(tableMap.get(ConfigConstant.TABLE_COMMENT));
			// 配置信息:表备注
			tableInfo.setComment(tableComment);
			// 配置信息:添加表信息对象
			tables.add(tableInfo);
		}
		System.out.println(xmlMap);
		return configInfo;
	}
}
