package test.db.gc;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import test.db.bean.ColumnInfo;
import test.db.bean.ConfigInfo;
import test.db.bean.TableInfo;
import test.db.util.ConfigConstant;
import test.db.util.GenerateUtil;

import zj.check.util.CheckUtil;
import zj.date.util.DateUtil;
import zj.freemarker.util.FreemarkerUtil;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;
import zj.jdbc.util.JdbcUtil;
import zj.reflect.util.TypeUtil;
import zj.regex.util.RegexUtil;
import zj.xml.util.XmlUtil;

public class TestXml {
	private transient static final Logger logger = Logger.getLogger(TestXml.class);
	Map<String, Object> rootMap = null;
	String srcDirPath = null;
	String tableName = null;
	String entityName = null;
	String basePackage = null;
	String description = null;
	String author = null;
	String date = null;

	String basePackagePath = null;

	@Before
	public void setUp() throws Exception {
		rootMap = new HashMap<String, Object>();
		srcDirPath = "E:/versionManager/sources/java/zj-model/integration/zj-utils-jdk1.6/src/main/java/";
		srcDirPath = "E:/versionManager/sources/java/zj-web/ebiz/msth-finance/src";
		tableName = "zj_test_table";
		entityName = "ZjTestTable";
		basePackage = ".project.msthdb.";
		description = "测试生成代码管理";
		author = "张军";
		date = DateUtil.dateParse(new Date(), "yyyy-MM-dd HH:mm:ss SSS p w");

		// 转换值
		// 转换表名
		if (CheckUtil.isNull(tableName)) {
			tableName = "TEST_TABLE";
		}
		// 表名转大写
		tableName = tableName.toUpperCase();
		// 转换实体类名
		if (CheckUtil.isNull(entityName)) {
			entityName = JavaUtil.upperCaseSplit(tableName.toLowerCase(), "_", true);
			if (CheckUtil.isNull(entityName)) {
				entityName = "TestTable";
			}
		}
		// 删除前面的.
		if (basePackage.startsWith(".")) {
			basePackage = basePackage.substring(1);
		}
		// 删除后面的.
		if (basePackage.endsWith(".")) {
			basePackage = basePackage.substring(0, basePackage.length() - 1);
		}
		// 设置包路径
		basePackagePath = basePackage.replaceAll("\\.", "/");
		// 设置表名
		rootMap.put("tableName", tableName);
		// 设置实体名
		rootMap.put("entityName", entityName);
		// 设置包
		rootMap.put("basePackage", basePackage);
		// 设置描述
		rootMap.put("description", description);
		// 设置作者
		rootMap.put("author", author);
		// 设置日期
		rootMap.put("date", date);

	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception {
		System.out.println();
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("doc", freemarker.ext.dom.NodeModel.parse(new File(this.getClass().getResource("/ftl/book.xml").getFile())));
		// FreemarkerUtil.printConsole("01.ftl", root);
	}

	@Test
	public void createCodes() throws Exception {
		// 初使化配置信息
		ConfigInfo config = GenerateUtil.initConfig();
		List<TableInfo> tables = config.getTables();
		for (TableInfo table : tables) {
			System.out.println(table);
			List<ColumnInfo> columns = table.getColumns();
			// 设置表中的列属性
			System.out.println(columns);
			rootMap.put("columns", columns);
			createPage();
			break;

		}
		System.out.println(config);
	}

	/**
	 * 创建页面实体
	 * 
	 * @throws Exception
	 */
	public void createPage() throws Exception {
		// FreemarkerUtil.printConsole("Page.ftl", rootMap);
		// srcDirPath项目源文件目录
		// FreemarkerUtil.writeFile("Page.ftl", rootMap, new File(srcDirPath, basePackagePath + "/pageModel/" + entityName + "Page.java"));
	}

	/**
	 * 创建控制器
	 * 
	 * @throws Exception
	 */
	public void createAction() throws Exception {
		// FreemarkerUtil.printConsole("Action.ftl", rootMap);
		// srcDirPath项目源文件目录
		// FreemarkerUtil.writeFile("Action.ftl", rootMap, new File(srcDirPath, "project/msthdb/action/Action.java"));
	}

	@Test
	public void m() throws Exception {
		String template = FileUtil.readString(new File("E:/versionManager/sources/java/zj-model/integration/zj-utils-jdk1.6/template/TestTable.java"));
		Map<String, String> nameValue = new HashMap<String, String>();
		StringBuffer modelProperty = new StringBuffer(200);
		modelProperty.append("/**姓名**/");
		modelProperty.append("private String name;");
		modelProperty.append("/**姓名**/");
		modelProperty.append("private String name;");
		nameValue.put("modelProperty", modelProperty.toString());
		// 替换模板字符串
		Map<String, String> result = RegexUtil.fillString(template, nameValue);
		// 获取替换模板字符串
		String newCode = result.get(RegexUtil.FillString.KEY_NEW_VALUE);
		logger.debug(template);
		logger.debug("----------------------------------");
		logger.debug(newCode);
	}

	@Test
	public void t1() throws Exception {
		System.out.println(new File(FreemarkerUtil.class.getResource("/ftl/config.xml").getFile()).getAbsolutePath());
		// 写文件内容
		// FileUtil.write("D:/a/b/c/d/t.txt", "abc");
		// logger.debug(JavaUtil.upperCaseSplit("affff_beeeee_cdddd", "_", true));
		ConfigInfo config = GenerateUtil.initConfig("E:/versionManager/sources/java/zj-model/integration/zj-utils-jdk1.6/template/tables.xml");
		logger.debug(config);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void unmarshal() throws Exception {
		logger.debug("转换xml为对象");
		File xmlFile = new File("E:/versionManager/sources/java/zj-model/integration/zj-utils-jdk1.6/template/tables.xml");
		String xml = null;
		xml = FileUtil.readString(xmlFile);
		logger.debug(xml);
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
		logger.debug("dbType:" + dbType);
		StringBuffer sqlSBAll = new StringBuffer(200);
		StringBuffer sqlSB = new StringBuffer(200);
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
			// 删除缓冲区
			sqlSB.delete(0, sqlSB.length());
			// 取得表名
			String tableName = JavaUtil.objToStr(tableMap.get(ConfigConstant.TABLE_DB_NAME));
			logger.debug("表名:" + tableName);
			if (JdbcUtil.DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
				// oracle数据库
			} else {
				// mysql数据库
				sqlSB.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
			}
			// 创建表前括号
			sqlSB.append("(");
			// 循环所有表中的列集合
			// 循环所有表集合
			for (int a1 = 0; a1 < columnsList.size(); a1++) {
				Map<String, Object> columnMap = columnsList.get(a1);
				String columnName = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_NAME));
				String columnType = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_TYPE));
				String columnLength = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_LENGTH));
				if (a1 != 0) {
					// 非第一个字段添加逗号
					sqlSB.append(",");
				}
				// 创建字段名+类型sql
				sqlSB.append(columnName).append(" ").append(columnType);
				if (CheckUtil.isNotNull(columnLength)) {
					// 创建字段长度
					sqlSB.append("(").append(columnLength);
					String columnPrecision = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_PRECISION));
					if (CheckUtil.isNotNull(columnPrecision)) {
						// //创建字段精度,添加,号
						sqlSB.append(",").append(columnPrecision);
					}
					sqlSB.append(")");
				}
				// 创建字段默认值
				String columnDefaultValue = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_DEFAULT_VALUE));
				if (CheckUtil.isNotNull(columnDefaultValue)) {
					// 强制不为null
					sqlSB.append(" DEFAULT ").append(columnDefaultValue);
				}
				// 创建字段非空
				String columnMandatory = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_DB_MANDATORY));
				if (CheckUtil.isNotNull(columnMandatory) && TypeUtil.Primitive.booleanValue(columnMandatory)) {
					// 强制不为null
					sqlSB.append(" NOT NULL ");
				}
				// 创建备注字段
				String columnComment = JavaUtil.objToStr(columnMap.get(ConfigConstant.TABLE_COLUMN_COMMENT));
				if (CheckUtil.isNotNull(columnComment)) {
					sqlSB.append(" COMMENT ").append(columnComment);
				}
			}
			// 创建表主键
			String tableKeys = JavaUtil.objToStr(tableMap.get(ConfigConstant.TABLE_COLUMN_DB_KEYS));
			if (CheckUtil.isNotNull(tableKeys)) {
				sqlSB.append(",PRIMARY KEY (").append(tableKeys).append(")");
			}
			// 创建表后括号
			sqlSB.append(")");
			// 创建表编码
			sqlSB.append(" ENGINE=INNODB DEFAULT CHARSET=UTF8");
			// 创建表备注
			// 创建备注字段
			String tableComment = JavaUtil.objToStr(tableMap.get(ConfigConstant.TABLE_COMMENT));
			if (CheckUtil.isNotNull(tableComment)) {
				sqlSB.append(" COMMENT=").append(tableComment);
			}
			logger.debug("创建表SQL:" + sqlSB.toString());
			if (CheckUtil.isNotNull(sqlSBAll.toString())) {
				sqlSBAll.append(";").append(IOUtils.LINE_SEPARATOR);
			}
			sqlSBAll.append(sqlSB.toString());
		}
		sqlSBAll.append(";");
		// 存储sql文件地址
		logger.debug("创建所有表SQL:\n" + sqlSBAll.toString());
		// 取得sql文件地址进行存储
		String createSqlFile = JavaUtil.objToStr(rootMap.get(ConfigConstant.CREATE_SQL_FILE));
		if (CheckUtil.isNotNull(createSqlFile)) {
			// 写文件内容
			FileUtil.write(createSqlFile, sqlSBAll.toString());
		}
		logger.debug(xmlMap);
	}
}
