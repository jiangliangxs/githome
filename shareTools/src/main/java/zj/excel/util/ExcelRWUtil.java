package zj.excel.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSON;

import zj.check.util.CheckUtil;
import zj.date.util.DateUtil;
import zj.excel.bean.DatasKeySheets;
import zj.excel.bean.Excel;
import zj.excel.bean.ExcelI;
import zj.excel.bean.ExcelTemplate;
import zj.excel.bean.FormulaTemplate;
import zj.excel.bean.ReadExcelI;
import zj.excel.bean.SheetData;
import zj.excel.bean.SheetDatas;
import zj.excel.bean.WriteExcelI;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;
import zj.reflect.util.FieldUtil;
import zj.reflect.util.TypeUtil;
import zj.regex.util.RegexUtil;

/**
 * 概况 ：Excel/xls/xlsx<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>} <br>
 *         * <br>
 *         --------------------------读取excel数据------------------------------<br>
 * 
 *         <pre>
 * try {
 * 	Excel readExcel = new Excel();
 * 	readExcel.setFilePath(&quot;E:/document/zj-utils/excel/写入excel&quot; + &quot;1&quot; + &quot;.xls&quot;);
 * 	// readExcel.setSheetValue(new String[] { &quot;Sheet1&quot; });
 * 	DatasKeySheets datasKeySheets = null;
 * 	// RE re = new RE();
 * 	// datasKeySheets = ExcelUtil.readExcel(readExcel,re);
 * 	datasKeySheets = ExcelUtil.readExcel(readExcel);
 * 	List&lt;SheetDatas&gt; sheetDatas = datasKeySheets.getSheetDatas();
 * 	for (SheetDatas datas : sheetDatas) {
 * 		// =======================
 * 		System.out.println(&quot;sheet:&quot; + datas.getSheetIndex() + &quot;\t\t&quot; + datas.getSheetName() + &quot;\t\t&quot; + datas.getSheet());
 * 		// 循环sheet
 * 		// 获取某个sheet的行列数据
 * 		List&lt;List&lt;SheetData&gt;&gt; datasLst = datas.getRowsDataLst();
 * 		if (datasLst != null) {
 * 			// 循环某个sheet的行数据
 * 			for (List&lt;SheetData&gt; dataLst : datasLst) {
 * 				// 循环某个sheet的列数据
 * 				for (SheetData data : dataLst) {
 * 					if (data != null) {
 * 						System.out.print(data.getValue() + &quot;\t\t&quot;);
 * 					}
 * 				}
 * 				System.out.println();
 * 			}
 * 		}
 * 	}
 * } catch (Exception e) {
 * 	e.printStackTrace();
 * }
 * </pre>
 * 
 *         <pre>
 * <br>------------------------------导出excel数据------------------------------<br>
 * try {
 * 		// 设置数据
 * 		for (int ii = 0; ii < 10; ii++) {
 * 			List<SheetDatas> sheetDatas = new ArrayList<SheetDatas>();
 * 			for (int k = 0; k < 10; k++) {
 * 				// 所有行列数据对象
 * 				SheetDatas sheetData = new SheetDatas();
 * 				sheetData.setSheetName("sheet名." + k);
 * 				sheetDatas.add(sheetData);
 * 				// 所有行数据
 * 				List<List<SheetData>> rowsDataLst = new ArrayList<List<SheetData>>();
 * 				// 设置所有行数据
 * 				sheetData.setRowsDataLst(rowsDataLst);
 * 				// 所有列数据
 * 				List<SheetData> columnsDataLst = null;
 * 				// 所有单元格数据
 * 				SheetData data = null;
 * 				// 实例化所有行列数据
 * 				for (int i = 0; i < 10000; i++) {
 * 					// 设置第i行数据
 * 					columnsDataLst = new ArrayList<SheetData>();
 * 					rowsDataLst.add(columnsDataLst);
 * 					// 添加第j行数据
 * 					for (int j = 0; j < 10; j++) {
 * 						data = new SheetData();
 * 						if (j == 5) {
 * 							data.setValue(i * j);
 * 						} else {
 * 							data.setValue("行" + i + "列" + j);
 * 						}
 * 						columnsDataLst.add(data);
 * 					}
 * 				}
 * 			}
 * 			// 导出excel设置
 * 			Excel excel = new Excel();
 * 			excel.setFilePath("E:/document/zj-utils/excel/写入excel" + ii + ".xls");
 * 			ExcelUtil.writeExcel(sheetDatas, excel);
 * 		}
 * 	} catch (Exception e) {
 * 		e.printStackTrace();
 * 	}
 * </pre>
 */
public final class ExcelRWUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExcelRWUtil.class.getName());

	private ExcelRWUtil() {
	}

	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	public static final DatasKeySheets readExcel(final Excel excel) throws Exception {
		return readExcel(excel, null);
	}

	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @param excelI
	 *            excelI接口
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static final DatasKeySheets readExcel(final Excel excel, final ExcelI excelI) throws Exception {
		excel.initWorkbook();
		Workbook wb = excel.getWb();
		// key:index/sheet/sheetName
		DatasKeySheets datasKeySheets = new DatasKeySheets();
		List<SheetDatas> sheetDatas = new ArrayList<SheetDatas>();
		Sheet sheet = null;
		int sheetCount = wb.getNumberOfSheets();
		// 获得sheet工作簿Sheet
		Object sheetValue = excel.getSheetValue();
		if (sheetValue == null) {
			// 循环所有sheet
			for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
				// 获得sheet工作簿Sheet
				sheet = wb.getSheetAt(sheetIndex);
				SheetDatas datas = new SheetDatas();
				datas.setSheetIndex(sheetIndex);
				datas.setSheetName(sheet.getSheetName());
				datas.setSheet(sheet);
				sheetDatas.add(datas);
				if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
					if (excel.isReadTitleKeys()) {
						readTitleData(excel, sheet, datas, excelI);
					} else {
						readNormalData(excel, sheet, datas, excelI);
					}
				} else {
					readTitleData(excel, sheet, datas, excelI);
				}
			}
		} else {
			if (sheetValue instanceof String) {
				String sheetName = String.valueOf(sheetValue);
				sheet = wb.getSheet(sheetName);
				if (sheet != null) {
					SheetDatas datas = new SheetDatas();
					datas.setSheetIndex(wb.getSheetIndex(sheet));
					datas.setSheetName(sheet.getSheetName());
					datas.setSheet(sheet);
					sheetDatas.add(datas);
					if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
						if (excel.isReadTitleKeys()) {
							readTitleData(excel, sheet, datas, excelI);
						} else {
							readNormalData(excel, sheet, datas, excelI);
						}
					} else {
						readTitleData(excel, sheet, datas, excelI);
					}
				}
			} else if (sheetValue instanceof Integer) {
				Integer sheetIndex = Integer.parseInt(String.valueOf(sheetValue));
				sheet = wb.getSheetAt(sheetIndex);
				if (sheet != null) {
					SheetDatas datas = new SheetDatas();
					datas.setSheetIndex(sheetIndex);
					datas.setSheetName(sheet.getSheetName());
					datas.setSheet(sheet);
					sheetDatas.add(datas);
					if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
						if (excel.isReadTitleKeys()) {
							readTitleData(excel, sheet, datas, excelI);
						} else {
							readNormalData(excel, sheet, datas, excelI);
						}
					} else {
						readTitleData(excel, sheet, datas, excelI);
					}
				}
			} else if (sheetValue instanceof String[]) {
				String[] sheetNames = (String[]) sheetValue;
				for (String sheetName : sheetNames) {
					sheet = wb.getSheet(sheetName);
					if (sheet != null) {
						SheetDatas datas = new SheetDatas();
						datas.setSheetIndex(wb.getSheetIndex(sheet));
						datas.setSheetName(sheet.getSheetName());
						datas.setSheet(sheet);
						sheetDatas.add(datas);
						if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
							if (excel.isReadTitleKeys()) {
								readTitleData(excel, sheet, datas, excelI);
							} else {
								readNormalData(excel, sheet, datas, excelI);
							}
						} else {
							readTitleData(excel, sheet, datas, excelI);
						}
					}
				}
			} else if (sheetValue instanceof int[]) {
				int[] sheetIndexs = (int[]) sheetValue;
				for (int a = 0; a < sheetIndexs.length; a++) {
					sheet = wb.getSheetAt(a);
					if (sheet != null) {
						SheetDatas datas = new SheetDatas();
						datas.setSheetIndex(a);
						datas.setSheetName(sheet.getSheetName());
						datas.setSheet(sheet);
						sheetDatas.add(datas);
						if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
							if (excel.isReadTitleKeys()) {
								readTitleData(excel, sheet, datas, excelI);
							} else {
								readNormalData(excel, sheet, datas, excelI);
							}
						} else {
							readTitleData(excel, sheet, datas, excelI);
						}
					}
				}
			} else if (sheetValue instanceof Integer[]) {
				Integer[] sheetIndexs = (Integer[]) sheetValue;
				for (int a = 0; a < sheetIndexs.length; a++) {
					sheet = wb.getSheetAt(a);
					if (sheet != null) {
						SheetDatas datas = new SheetDatas();
						datas.setSheetIndex(a);
						datas.setSheetName(sheet.getSheetName());
						datas.setSheet(sheet);
						sheetDatas.add(datas);
						if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
							if (excel.isReadTitleKeys()) {
								readTitleData(excel, sheet, datas, excelI);
							} else {
								readNormalData(excel, sheet, datas, excelI);
							}
						} else {
							readTitleData(excel, sheet, datas, excelI);
						}
					}
				}
			} else if (sheetValue instanceof Collection) {
				Collection sheetCollection = (Collection) sheetValue;
				Iterator it = sheetCollection.iterator();
				while (it.hasNext()) {
					Object ito = it.next();
					if (ito instanceof Integer) {
						int sheetIndex = Integer.parseInt(String.valueOf(ito));
						sheet = wb.getSheetAt(sheetIndex);
						if (sheet != null) {
							SheetDatas datas = new SheetDatas();
							datas.setSheetIndex(sheetIndex);
							datas.setSheetName(sheet.getSheetName());
							datas.setSheet(sheet);
							sheetDatas.add(datas);
							if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
								if (excel.isReadTitleKeys()) {
									readTitleData(excel, sheet, datas, excelI);
								} else {
									readNormalData(excel, sheet, datas, excelI);
								}
							} else {
								readTitleData(excel, sheet, datas, excelI);
							}
						}
					} else if (ito instanceof String) {
						String sheetName = String.valueOf(ito);
						sheet = wb.getSheet(sheetName);
						if (sheet != null) {
							SheetDatas datas = new SheetDatas();
							datas.setSheetIndex(wb.getSheetIndex(sheet));
							datas.setSheetName(sheet.getSheetName());
							datas.setSheet(sheet);
							sheetDatas.add(datas);
							if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
								if (excel.isReadTitleKeys()) {
									readTitleData(excel, sheet, datas, excelI);
								} else {
									readNormalData(excel, sheet, datas, excelI);
								}
							} else {
								readTitleData(excel, sheet, datas, excelI);
							}
						}
					}
				}
			} else {
				throw new Exception("不支持类型:" + sheetValue);
			}
		}
		datasKeySheets.setSheetDatas(sheetDatas);
		datasKeySheets.setSheetCount(sheetCount);
		return datasKeySheets;
	}

	/**
	 * 读取sheet行列集合(有标题)
	 * 
	 * @param sheet
	 * @param datas
	 * @param excelI
	 * @throws Exception
	 */
	public static final void readTitleData(final Excel excel, final Sheet sheet, final SheetDatas datas, final ExcelI excelI) throws Exception {
		ReadExcelI rexcelI = null;
		if (excelI != null) {
			rexcelI = (ReadExcelI) excelI;
		}
		Map<String, Object> jsonMap = null;
		if (CheckUtil.isNotNull(excel.getJsonParams())) {
			jsonMap = JSON.parseObject(excel.getJsonParams());
			if (jsonMap == null) {
				jsonMap = new HashMap<String, Object>();
			}
		}
		// 设置标题
		if (excel.getTitleKeys() == null || excel.getTitleKeys().size() == 0) {
			if (excel.getTitleRow() < 0) {
				excel.setTitleRow(0);
			}
			Row rowTitle = sheet.getRow(excel.getTitleRow());
			if (null != rowTitle) {
				List<String> titleKeys = new ArrayList<String>();
				for (int columnIndex = rexcelI == null ? 0 : rexcelI.getStartColumnIndex(); columnIndex < rowTitle.getLastCellNum(); columnIndex++) {
					// 取得对应的列值
					Cell cellTitle = rowTitle.getCell(columnIndex);
					// 获得指定单元格中的数据
					Object valueTitle = readCellString(cellTitle);
					valueTitle = rexcelI == null ? valueTitle : rexcelI.readValue(sheet, excel.getTitleRow(), columnIndex, valueTitle);
					// 默认当前单元格式值
					titleKeys.add(JavaUtil.objToStr(valueTitle));
				}
				excel.setTitleKeys(titleKeys);
				excel.setTempTitleKeys(titleKeys);
			} else {
				return;
			}
		} else {
			// 设置旧的titleKeys
			excel.setTempTitleKeys(excel.getTitleKeys());
			if (!excel.isContainTitleKeys()) {
				if (excel.getTitleRow() < 0) {
					// 如果包含标题键，则继续下一个执行，否则抽取其它列
					// 定位列索引
					// 默认不重复读取
					excel.setReadMemoryWb(false);
					Map<ConstantForEnum.CellValueKey, Object> params = new HashMap<ConstantForEnum.CellValueKey, Object>();
					String firstValue = excel.getTitleKeys().get(0);
					params.put(ConstantForEnum.CellValueKey.VALUE, firstValue);
					params.put(ConstantForEnum.CellValueKey.VALUE_ROW, excel.getTitleRow());
					// 目前只支持查询第一个
					Map<String, Object> titleKeyMap = JavaUtil.getValueForMap(jsonMap, firstValue);
					Object valueCountObj = titleKeyMap.get("valueCount");
					if (valueCountObj != null) {
						params.put(ConstantForEnum.CellValueKey.VALUE_COUNT, TypeUtil.Primitive.intValue(valueCountObj));
					}
					Object valueCountMatchObj = titleKeyMap.get("valueCountMatch");
					if (valueCountMatchObj != null) {
						params.put(ConstantForEnum.CellValueKey.VALUE_COUNT_MATCH, TypeUtil.Primitive.booleanValue(valueCountMatchObj));
					}
					Map<ConstantForEnum.CellValueKey, Integer> returnValue = readCellValueIndex(excel, params);
					int readColumnIndex = returnValue.get(ConstantForEnum.CellValueKey.COLUMN_INDEX);
					if (readColumnIndex == -1 && excel.isTitleRowNullContinue()) {
						// 如果没有找到值，如果继续查询
						// 没有找到值,不按默认传的行进行查询
						params.remove(ConstantForEnum.CellValueKey.VALUE_ROW);
						returnValue = readCellValueIndex(excel, params);
						readColumnIndex = returnValue.get(ConstantForEnum.CellValueKey.COLUMN_INDEX);
					}
					if (readColumnIndex != -1) {
						int readRowIndex = returnValue.get(ConstantForEnum.CellValueKey.ROW_INDEX);
						// 定位标题行
						excel.setTitleRow(readRowIndex);
					}
				}
				if (excel.getTitleRow() < 0) {
					excel.setTitleRow(0);
				}
				// 改变titleRow
				Row rowTitle = sheet.getRow(excel.getTitleRow());
				if (null != rowTitle) {
					List<String> titleKeys = new ArrayList<String>();
					for (int columnIndex = rexcelI == null ? 0 : rexcelI.getStartColumnIndex(); columnIndex < rowTitle.getLastCellNum(); columnIndex++) {
						// 取得对应的列值
						Cell cellTitle = rowTitle.getCell(columnIndex);
						// 获得指定单元格中的数据
						Object valueTitle = readCellString(cellTitle);
						valueTitle = rexcelI == null ? valueTitle : rexcelI.readValue(sheet, excel.getTitleRow(), columnIndex, valueTitle);
						String svalueTitle = JavaUtil.objToStr(valueTitle);
						if (!excel.getTitleKeys().contains(svalueTitle)) {
							// 默认当前单元格式值
							titleKeys.add(svalueTitle);
						}
					}
					excel.setTempTitleKeys(titleKeys);
				}
			}
		}
		// 行列数据带主键
		List<Map<String, SheetData>> rowsDataLstMap = new ArrayList<Map<String, SheetData>>();
		int endRowIndex = rexcelI == null ? ExcelI.END_ROW_INDEX : rexcelI.getEndRowIndex();
		// 循环sheet中的所有行数据
		// 临时列索引
		List<Integer> columnIndexs = new ArrayList<Integer>();
		for (int rowIndex = rexcelI == null ? ExcelI.START_ROW_INDEX : rexcelI.getStartRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			// 是否结束
			if (endRowIndex != -1 && rowIndex > endRowIndex)
				break;
			if (rexcelI != null && rexcelI.filterRow(sheet, rowIndex)) {
				// ("sheetName:" + sheet.getSheetName() + ",第" + rowIndex + "行数据已被过虑");
				continue;
			}
			// 获得行对象
			Row row = sheet.getRow(rowIndex);
			if (null != row) {
				// 行数据
				Map<String, SheetData> valueMap = new HashMap<String, SheetData>();
				// 获得本行中单元格的个数
				// 判断是否一行全部为""或null
				boolean notNull = excel.isAddRowNull();
				// 遍历列cell
				int realKeyIndex = 0;
				for (int keyIndex = 0; keyIndex < excel.getTempTitleKeys().size(); keyIndex++) {
					int columnIndexTemp = -1;
					realKeyIndex++;
					String titleKey = excel.getTempTitleKeys().get(keyIndex);
					int startIndex = titleKey.indexOf(".");
					int keyColumnIndexTemp = -1;
					if (startIndex != -1) {
						String startS = titleKey.substring(0, startIndex);
						if (CheckUtil.isPlusNum(startS)) {
							keyColumnIndexTemp = TypeUtil.Primitive.intValue(startS);
							titleKey = titleKey.substring(startIndex + 1);
						}
					}
					if (keyColumnIndexTemp == -1) {
						if (columnIndexs.size() > keyIndex) {
							columnIndexTemp = columnIndexs.get(keyIndex);
						}
						// 如果没有找到索引,则自动读取
						if (columnIndexTemp == -1) {
							// 定位列索引
							// 默认不重复读取
							excel.setReadMemoryWb(false);
							Map<ConstantForEnum.CellValueKey, Object> params = new HashMap<ConstantForEnum.CellValueKey, Object>();
							params.put(ConstantForEnum.CellValueKey.VALUE, titleKey);
							params.put(ConstantForEnum.CellValueKey.VALUE_ROW, excel.getTitleRow());
							// 目前只支持查询第一个
							Map<String, Object> titleKeyMap = JavaUtil.getValueForMap(jsonMap, titleKey);
							Object valueCountObj = titleKeyMap.get("valueCount");
							if (valueCountObj != null) {
								params.put(ConstantForEnum.CellValueKey.VALUE_COUNT, TypeUtil.Primitive.intValue(valueCountObj));
							}
							Object valueCountMatchObj = titleKeyMap.get("valueCountMatch");
							if (valueCountMatchObj != null) {
								params.put(ConstantForEnum.CellValueKey.VALUE_COUNT_MATCH, TypeUtil.Primitive.booleanValue(valueCountMatchObj));
							}
							Map<ConstantForEnum.CellValueKey, Integer> returnValue = readCellValueIndex(excel, params);
							int readColumnIndex = returnValue.get(ConstantForEnum.CellValueKey.COLUMN_INDEX);
							if (readColumnIndex == -1) {
								// 继续下一个列查询
								realKeyIndex--;
								break;
							} else {
								int readRowIndex = returnValue.get(ConstantForEnum.CellValueKey.ROW_INDEX);
								// 定位标题行
								excel.setTitleRow(readRowIndex);
								columnIndexTemp = readColumnIndex;
							}
						}
					} else {
						columnIndexTemp = keyColumnIndexTemp;
					}
					if (columnIndexs.size() != realKeyIndex) {
						columnIndexs.add(columnIndexTemp);
					}
					// 取得对应的列值
					Cell cell = row.getCell(columnIndexTemp);
					// 获得指定单元格中的数据
					Object value = readCellString(cell);
					value = rexcelI == null ? value : rexcelI.readValue(sheet, rowIndex, columnIndexTemp, value);
					if (!notNull && CheckUtil.isNotNull(JavaUtil.objToStr(value))) {
						notNull = true;
					}
					SheetData data = new SheetData();
					data.setValue(value);
					valueMap.put(titleKey, data);
				}

				if (notNull) {
					if ((rowIndex == excel.getTitleRow() && excel.isAddTitleKeyRow()) || rowIndex != excel.getTitleRow()) {
						// 如果非当前行，则添加
						rowsDataLstMap.add(valueMap);
					}
				}
			}
		}
		// 删除判断最后行是否一行全部为""或null
		List<Map<String, SheetData>> addRowsNull = new ArrayList<Map<String, SheetData>>();
		boolean notNull = false;
		for (int b = rowsDataLstMap.size() - 1; b >= 0; b--) {
			Map<String, SheetData> datasNull = rowsDataLstMap.get(b);
			for (String keyNull : datasNull.keySet()) {
				SheetData data = datasNull.get(keyNull);
				Object value = data.getValue();
				if (!notNull && CheckUtil.isNotNull(JavaUtil.objToStr(value))) {
					notNull = true;
					break;
				}
			}
			if (notNull) {
				break;
			}
			addRowsNull.add(datasNull);
		}
		for (Map<String, SheetData> datasNull : addRowsNull) {
			rowsDataLstMap.remove(datasNull);
		}
		datas.setRowsDataLstMap(rowsDataLstMap);
	}

	@Deprecated
	public static final void readTitleDataOld(final Excel excel, final Sheet sheet, final SheetDatas datas, final ExcelI excelI) throws Exception {
		ReadExcelI rexcelI = null;
		if (excelI != null) {
			rexcelI = (ReadExcelI) excelI;
		}
		// 行列数据带主键
		List<Map<String, SheetData>> rowsDataLstMap = new ArrayList<Map<String, SheetData>>();
		int endRowIndex = rexcelI == null ? ExcelI.END_ROW_INDEX : rexcelI.getEndRowIndex();
		int endColumnIndex = rexcelI == null ? ExcelI.END_COLUMN_INDEX : rexcelI.getEndColumnIndex();
		// 获得行对象
		if (excel.getTitleRow() < 0) {
			excel.setTitleRow(0);
		}
		Row rowTitle = sheet.getRow(excel.getTitleRow());
		// 循环sheet中的所有行数据
		for (int rowIndex = rexcelI == null ? ExcelI.START_ROW_INDEX : rexcelI.getStartRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			// 是否结束
			if (endRowIndex != -1 && rowIndex > endRowIndex)
				break;
			if (rexcelI != null && rexcelI.filterRow(sheet, rowIndex)) {
				// ("sheetName:" + sheet.getSheetName() + ",第" + rowIndex + "行数据已被过虑");
				continue;
			}
			// 获得行对象
			Row row = sheet.getRow(rowIndex);
			if (null != row) {
				// 行数据
				Map<String, SheetData> valueMap = new HashMap<String, SheetData>();
				// // 添加标题索引值
				// if (excel.getTitleKeys() != null && rowIndex == ExcelI.START_ROW_INDEX) {
				// for (int ci = 0; ci < rowTitle.getLastCellNum(); ci++) {
				// String titleKey = null;
				// if (excel.getTitleKeys().size() > ci) {
				// titleKey = excel.getTitleKeys().get(ci);
				// } else {
				// // 默认当前单元格式值
				// Cell cell = row.getCell(ci);
				// Object value = readCellString(cell);
				// value = rexcelI == null ? value : rexcelI.readValue(sheet, rowIndex, ci, value);
				// titleKey = JavaUtil.objToStr(value);
				// }
				// rowsDataMap.put(titleKey, new ArrayList<Map<String,SheetData>>());
				// }
				// }
				// 获得本行中单元格的个数
				// 判断是否一行全部为""或null
				boolean notNull = excel.isAddRowNull();
				// 遍历列cell
				for (int columnIndex = rexcelI == null ? 0 : rexcelI.getStartColumnIndex(); columnIndex < row.getLastCellNum(); columnIndex++) {
					// 是否结束
					if (endColumnIndex != -1 && columnIndex > endColumnIndex)
						break;
					SheetData data = new SheetData();
					// 添加标题索引值
					// 如果是当前行，设置excel.isAddTitleKeyRow()是否过虑标题行(true:不过虑，false:过虑[默认])
					if ((rowIndex == excel.getTitleRow() && excel.isAddTitleKeyRow()) || rowIndex != excel.getTitleRow()) {
						// 如果是标题行并且添加；或者当前行不是标题行，则进入
						String titleKey = null;
						if (excel.getTitleKeys() != null) {
							if (excel.getTitleKeys().size() > columnIndex) {
								// 取得列索引对应的列值
								titleKey = excel.getTitleKeys().get(columnIndex);
							} else {
								if (excel.isContainTitleKeys()) {
									// 如果包含标题键，则继续下一个执行，否则抽取其它列
									continue;
								}
							}
						}
						// 默认当前列
						int columnIndexTemp = columnIndex;
						if (CheckUtil.isNotNull(titleKey)) {
							// 取得列名对应的值
							int startIndex = titleKey.indexOf(".");
							if (startIndex != -1) {
								String startS = titleKey.substring(0, startIndex);
								if (CheckUtil.isPlusNum(startS)) {
									columnIndexTemp = TypeUtil.Primitive.intValue(startS);
									titleKey = titleKey.substring(startIndex + 1);
								}
							}
						}
						// 如果key为空
						if (CheckUtil.isNull(titleKey)) {
							if (excel.isContainTitleKeys()) {
								// 如果包含标题键，则继续下一个执行，否则抽取其它列
								continue;
							}
							// 取得对应的列值
							Cell cellTitle = rowTitle.getCell(columnIndexTemp);
							// 获得指定单元格中的数据
							Object valueTitle = readCellString(cellTitle);
							valueTitle = rexcelI == null ? valueTitle : rexcelI.readValue(sheet, rowIndex, columnIndexTemp, valueTitle);
							// 默认当前单元格式值
							titleKey = JavaUtil.objToStr(valueTitle);
						}
						// 取得对应的列值
						Cell cell = row.getCell(columnIndexTemp);
						// 获得指定单元格中的数据
						Object value = readCellString(cell);
						value = rexcelI == null ? value : rexcelI.readValue(sheet, rowIndex, columnIndexTemp, value);
						if (!notNull && CheckUtil.isNotNull(JavaUtil.objToStr(value))) {
							notNull = true;
						}
						data.setValue(value);
						valueMap.put(titleKey, data);
					}
				}
				if (notNull) {
					if ((rowIndex == excel.getTitleRow() && excel.isAddTitleKeyRow()) || rowIndex != excel.getTitleRow()) {
						// 如果非当前行，则添加
						rowsDataLstMap.add(valueMap);
					}
				}
			}
		}
		// 删除判断最后行是否一行全部为""或null
		List<Map<String, SheetData>> addRowsNull = new ArrayList<Map<String, SheetData>>();
		boolean notNull = false;
		for (int b = rowsDataLstMap.size() - 1; b >= 0; b--) {
			Map<String, SheetData> datasNull = rowsDataLstMap.get(b);
			for (String keyNull : datasNull.keySet()) {
				SheetData data = datasNull.get(keyNull);
				Object value = data.getValue();
				if (!notNull && CheckUtil.isNotNull(JavaUtil.objToStr(value))) {
					notNull = true;
					break;
				}
			}
			if (notNull) {
				break;
			}
			addRowsNull.add(datasNull);
		}
		for (Map<String, SheetData> datasNull : addRowsNull) {
			rowsDataLstMap.remove(datasNull);
		}
		datas.setRowsDataLstMap(rowsDataLstMap);
	}

	/**
	 * 读取sheet行列集合(正常)
	 * 
	 * @param sheet
	 * @param datas
	 * @param excelI
	 * @throws Exception
	 */
	public static final void readNormalData(final Excel excel, final Sheet sheet, final SheetDatas datas, final ExcelI excelI) throws Exception {
		ReadExcelI rexcelI = null;
		if (excelI != null) {
			rexcelI = (ReadExcelI) excelI;
		}
		// 行列数据
		List<List<SheetData>> rowsDataLst = new ArrayList<List<SheetData>>();
		int endRowIndex = rexcelI == null ? ExcelI.END_ROW_INDEX : rexcelI.getEndRowIndex();
		int endColumnIndex = rexcelI == null ? ExcelI.END_COLUMN_INDEX : rexcelI.getEndColumnIndex();
		// 循环sheet中的所有行数据
		for (int rowIndex = rexcelI == null ? ExcelI.START_ROW_INDEX : rexcelI.getStartRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			// 是否结束
			if (endRowIndex != -1 && rowIndex > endRowIndex)
				break;
			if (rexcelI != null && rexcelI.filterRow(sheet, rowIndex)) {
				// ("sheetName:" + sheet.getSheetName() + ",第" + rowIndex + "行数据已被过虑");
				continue;
			}
			// 获得行对象
			Row row = sheet.getRow(rowIndex);
			// 判断是否一行全部为""或null
			boolean notNull = excel.isAddRowNull();
			if (null != row) {
				// 行数据
				List<SheetData> dataLst = new ArrayList<SheetData>();
				// // 添加标题索引值
				// if (excel.getTitleKeys() != null && rowIndex == ExcelI.START_ROW_INDEX) {
				// for (int ci = 0; ci < rowTitle.getLastCellNum(); ci++) {
				// String titleKey = null;
				// if (excel.getTitleKeys().size() > ci) {
				// titleKey = excel.getTitleKeys().get(ci);
				// } else {
				// // 默认当前单元格式值
				// Cell cell = row.getCell(ci);
				// Object value = readCellString(cell);
				// value = rexcelI == null ? value : rexcelI.readValue(sheet, rowIndex, ci, value);
				// titleKey = JavaUtil.objToStr(value);
				// }
				// rowsDataMap.put(titleKey, new ArrayList<Map<String,SheetData>>());
				// }
				// }
				// 获得本行中单元格的个数
				// 遍历列cell
				for (int columnIndex = rexcelI == null ? 0 : rexcelI.getStartColumnIndex(); columnIndex < row.getLastCellNum(); columnIndex++) {
					// 是否结束
					if (endColumnIndex != -1 && columnIndex > endColumnIndex)
						break;
					SheetData data = new SheetData();
					Cell cell = row.getCell(columnIndex);
					// 获得指定单元格中的数据
					Object value = readCellString(cell);
					value = rexcelI == null ? value : rexcelI.readValue(sheet, rowIndex, columnIndex, value);
					if (!notNull && CheckUtil.isNotNull(JavaUtil.objToStr(value))) {
						notNull = true;
					}
					data.setValue(value);
					dataLst.add(data);
				}
				if (notNull) {
					rowsDataLst.add(dataLst);
				}
			} else {
				List<SheetData> dataLst = new ArrayList<SheetData>();
				if (notNull) {
					rowsDataLst.add(dataLst);
				}
			}
		}
		// 删除判断最后行是否一行全部为""或null
		List<List<SheetData>> addRowsNull = new ArrayList<List<SheetData>>();
		boolean notNull = false;
		for (int b = rowsDataLst.size() - 1; b >= 0; b--) {
			List<SheetData> datasNull = rowsDataLst.get(b);
			for (SheetData data : datasNull) {
				Object value = data.getValue();
				if (!notNull && CheckUtil.isNotNull(JavaUtil.objToStr(value))) {
					notNull = true;
					break;
				}
			}
			if (notNull) {
				break;
			}
			addRowsNull.add(datasNull);
		}
		for (List<SheetData> datasNull : addRowsNull) {
			rowsDataLst.remove(datasNull);
		}
		datas.setRowsDataLst(rowsDataLst);
	}

	// #####################################################################
	/**
	 * Excel写入
	 * 
	 * @see zj.excel.util.ExcelRWUtil#writeTemplateExcel(ExcelTemplate)
	 * @param sheetDatas
	 *            所有行列数据对象
	 * @param excel
	 *            excel对象
	 * @throws Exception
	 */
	@Deprecated
	public static final void writeExcel(final List<SheetDatas> sheetDatas, final Excel excel) throws Exception {
		writeExcel(sheetDatas, excel, null);
	}

	/**
	 * Excel写入
	 * 
	 * @see zj.excel.util.ExcelRWUtil#writeTemplateExcel(ExcelTemplate, ExcelI)
	 * @param sheetDatas
	 *            数据集合
	 * @param excel
	 *            对象
	 * @param excelI
	 *            接口
	 * @throws Exception
	 */
	@Deprecated
	public static final void writeExcel(final List<SheetDatas> sheetDatas, final Excel excel, final ExcelI excelI) throws Exception {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			excel.initWorkbook();
			WriteExcelI wexcelI = null;
			if (excelI != null) {
				wexcelI = (WriteExcelI) excelI;
			}
			Workbook wb = (Workbook) excel.getWb();
			for (SheetDatas datas : sheetDatas) {
				String sheetName = datas.getSheetName();
				if (sheetName == null || sheetName.equals("")) {
					sheetName = "Sheet1";
					// continue;
				}
				// 循环sheet
				Sheet sheet = wb.getSheet(sheetName);
				if (sheet == null) {
					wb.createSheet(sheetName);
					sheet = wb.getSheet(sheetName);
				} else {
					// 当前sheet.key已经存在
				}
				// 获取某个sheet的数据
				// 获取某个sheet的行列数据
				List<List<SheetData>> datasLst = datas.getRowsDataLst();
				CellStyle cellStyle = wb.createCellStyle();
				Font font = wb.createFont();
				if (datasLst != null) {
					int startRowIndex = wexcelI == null ? ExcelI.START_ROW_INDEX : wexcelI.getStartRowIndex();
					int endRowIndex = wexcelI == null ? ExcelI.END_ROW_INDEX : wexcelI.getEndRowIndex();
					int endColumnIndex = wexcelI == null ? ExcelI.END_COLUMN_INDEX : wexcelI.getEndColumnIndex();
					// 循环某个sheet的行数据
					for (int rowIndex = 0; rowIndex < datasLst.size(); rowIndex++) {
						// 是否结束
						if (endRowIndex != -1 && startRowIndex > endRowIndex)
							break;
						if (wexcelI != null && wexcelI.filterRow(sheet, startRowIndex)) {
							// //("sheetName:" + sheetName + ",第" + i + "行数据已被过虑");
							continue;
						}
						List<SheetData> dataLst = datasLst.get(rowIndex);
						Row row = sheet.getRow(startRowIndex);
						if (row == null) {
							row = sheet.createRow(startRowIndex);
						}
						int startColumnIndex = wexcelI == null ? 0 : wexcelI.getStartColumnIndex();
						// 循环某个sheet的列数据
						for (int columnIndex = 0; columnIndex < dataLst.size(); columnIndex++) {
							// 是否结束
							if (endColumnIndex != -1 && startColumnIndex > endColumnIndex)
								break;
							SheetData data = dataLst.get(columnIndex);
							Object value = null;
							if (data == null) {
								// //("sheetName:" + sheetName + ",SheetData数据为NULL");
							} else {
								value = data.getValue();
							}
							// 目前不过虑值不过虑行
							Cell cell = row.getCell(startColumnIndex);
							if (cell == null) {
								cell = row.createCell(startColumnIndex);
							}
							ExcelUtil.setCellValue(sheet, row, cell, startRowIndex, startColumnIndex, value);
							ExcelUtil.cellStyle(cellStyle);
							ExcelUtil.font(font);
							if (wexcelI == null) {
							} else {
								wexcelI.setCellValue(sheet, row, cell, startRowIndex, startColumnIndex, value);
							}
							cellStyle.setFont(font);
							cell.setCellStyle(cellStyle);
							startColumnIndex++;
						}
						ExcelUtil.rowExcel(row, startRowIndex);
						startRowIndex++;
					}
					// mergedRegion(sheet, datasLst);
					if (wexcelI != null) {
						wexcelI.mergedRegion(sheet, datasLst);
					}
				}
			}
			File file = new File(excel.getFilePath());
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			wb.write(bos);
			bos.flush();
			fos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	// #####################################################################
	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	public static final void writeTemplateExcel(final ExcelTemplate excel) throws Exception {
		writeTemplateExcel(excel, null);
	}

	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	public static final synchronized void threadWriteTemplateExcel(final ExcelTemplate excel) throws Exception {
		threadWriteTemplateExcel(excel, null);
	}

	/**
	 * 获取导出的excel的sheet,复制新模板sheet
	 * 
	 * @param excel
	 * @return
	 * @throws Exception
	 */
	private static Set<Sheet> getExportSheets(final ExcelTemplate excel) throws Exception {
		Set<Sheet> sheets = new HashSet<Sheet>();
		excel.initWorkbook();
		Workbook wb = excel.getWb();
		// key:index/sheet/sheetName
		Sheet sheet = null;
		int sheetCount = wb.getNumberOfSheets();
		// 获得sheet工作簿Sheet
		Object sheetValue = excel.getSheetValue();
		// if (sheetValue == null) {
		// // 循环所有sheet
		// for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
		// // 获得sheet工作簿Sheet
		// sheet = wb.getSheetAt(sheetIndex);
		// sheets.add(sheet);
		// }
		// } else {
		// if (sheetValue instanceof String) {
		// String sheetName = String.valueOf(sheetValue);
		// // 取得
		// sheet = wb.getSheet(sheetName);
		// sheets.add(sheet);
		// } else if (sheetValue instanceof Integer) {
		// Integer sheetIndex = Integer.parseInt(String.valueOf(sheetValue));
		// sheet = wb.getSheetAt(sheetIndex);
		// sheets.add(sheet);
		// } else if (sheetValue instanceof String[]) {
		// String[] sheetNames = (String[]) sheetValue;
		// for (String sheetName : sheetNames) {
		// sheet = wb.getSheet(sheetName);
		// //wb.setSheetName(wb.getSheetIndex(sheet), sheetName + "--zhangjun");
		// sheets.add(sheet);
		// }
		// } else if (sheetValue instanceof int[]) {
		// int[] sheetIndexs = (int[]) sheetValue;
		// for (int a = 0; a < sheetIndexs.length; a++) {
		// sheet = wb.getSheetAt(a);
		// sheets.add(sheet);
		// }
		// } else if (sheetValue instanceof Integer[]) {
		// Integer[] sheetIndexs = (Integer[]) sheetValue;
		// for (int a = 0; a < sheetIndexs.length; a++) {
		// sheet = wb.getSheetAt(a);
		// sheets.add(sheet);
		// }
		// } else if (sheetValue instanceof Collection) {
		// Collection sheetCollection = (Collection) sheetValue;
		// Iterator it = sheetCollection.iterator();
		// while (it.hasNext()) {
		// Object ito = it.next();
		// if (ito instanceof Integer) {
		// int sheetIndex = Integer.parseInt(String.valueOf(ito));
		// sheet = wb.getSheetAt(sheetIndex);
		// sheets.add(sheet);
		// } else if (ito instanceof String) {
		// String sheetName = String.valueOf(ito);
		// sheet = wb.getSheet(sheetName);
		// sheets.add(sheet);
		// }
		// }
		// } else {
		// throw new Exception("不支持类型:" + sheetValue);
		// }
		// }
		if (sheetValue == null) {
			// 循环所有sheet
			for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
				// 获得sheet工作簿Sheet
				sheet = wb.getSheetAt(sheetIndex);
				addWriteSheetVariable(excel, sheets, sheet);
			}
		} else {
			if (sheetValue instanceof String) {
				String sheetNames = String.valueOf(sheetValue);
				sheet = getExportCloneSheet(wb, excel, sheetNames);
				addWriteSheetVariable(excel, sheets, sheet);
			} else if (sheetValue instanceof Integer) {
				Integer sheetIndex = Integer.parseInt(String.valueOf(sheetValue));
				sheet = wb.getSheetAt(sheetIndex);
				addWriteSheetVariable(excel, sheets, sheet);
			} else if (sheetValue instanceof String[]) {
				String[] sheetNameAry = (String[]) sheetValue;
				for (String sheetNames : sheetNameAry) {
					sheet = getExportCloneSheet(wb, excel, sheetNames);
					addWriteSheetVariable(excel, sheets, sheet);
				}
			} else if (sheetValue instanceof int[]) {
				int[] sheetIndexs = (int[]) sheetValue;
				for (int a = 0; a < sheetIndexs.length; a++) {
					sheet = wb.getSheetAt(a);
					addWriteSheetVariable(excel, sheets, sheet);
				}
			} else if (sheetValue instanceof Integer[]) {
				Integer[] sheetIndexs = (Integer[]) sheetValue;
				for (int a = 0; a < sheetIndexs.length; a++) {
					sheet = wb.getSheetAt(a);
					addWriteSheetVariable(excel, sheets, sheet);
				}
			} else if (sheetValue instanceof Collection) {
				Collection<?> sheetCollection = (Collection<?>) sheetValue;
				Iterator<?> it = sheetCollection.iterator();
				while (it.hasNext()) {
					Object ito = it.next();
					if (ito instanceof Integer) {
						int sheetIndex = Integer.parseInt(String.valueOf(ito));
						sheet = wb.getSheetAt(sheetIndex);
						addWriteSheetVariable(excel, sheets, sheet);
					} else if (ito instanceof String) {
						String sheetNames = String.valueOf(ito);
						sheet = getExportCloneSheet(wb, excel, sheetNames);
						addWriteSheetVariable(excel, sheets, sheet);
					}
				}
			} else {
				throw new Exception("不支持类型:" + sheetValue);
			}
		}
		return sheets;
	}

	private static void addWriteSheetVariable(final ExcelTemplate excel, Set<Sheet> sheets, Sheet sheet) {
		if (sheet != null) {
			sheets.add(sheet);
			excel.addedSheets.add(sheet.getSheetName());
		}
	}

	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @param excelI
	 *            excelI接口
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	public static final void writeTemplateExcel(final ExcelTemplate excel, final ExcelI excelI) throws Exception {
		final long start = System.currentTimeMillis();
		Set<Sheet> sheets = getExportSheets(excel);
		writeSheet(excel, excelI, start, sheets);
	}

	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @param excelI
	 *            excelI接口
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	public static final synchronized void threadWriteTemplateExcel(final ExcelTemplate excel, final ExcelI excelI) throws Exception {
		final long start = System.currentTimeMillis();
		Set<Sheet> sheets = getExportSheets(excel);
		threadWriteSheet(excel, excelI, start, sheets);
	}

	/**
	 * 非线程写入
	 * 
	 * @param excel
	 * @param excelI
	 * @param start
	 * @param wb
	 * @param sheets
	 * @throws Exception
	 */
	private static void writeSheet(final ExcelTemplate excel, final ExcelI excelI, final long start, Set<Sheet> sheets) throws Exception {
		if (sheets.size() > 0) {
			// boolean writeFile = false;
			// for (Sheet tempSheet : sheets) {
			// boolean tempWriteFile = readTemplateSheet(tempSheet, excel, excelI);
			// if (!writeFile) {
			// writeFile = tempWriteFile;
			// }
			// }
			// if (writeFile) {
			// writeFile(excel);
			// } else {
			// logger.debug("未找到模板内容与传入内容表达式关系");
			// }
			for (Sheet tempSheet : sheets) {
				tempSheet.setForceFormulaRecalculation(true);
				readTemplateSheet(tempSheet, excel, excelI);
			}
			if (excel.isAutoWriteExcel()) {
				writeTemplateFile(excel);
			}
		}
		final long end = System.currentTimeMillis();
		final long x = (end - start) / 1000;
		final long y = (end - start) % 1000;
		logger.warn("根据excel模板导出执行时间:" + (end - start) + "毫秒;" + x + "." + (y < 100 ? "0" + y : y) + "秒");
	}

	/**
	 * 线程写入
	 * 
	 * @param excel
	 * @param excelI
	 * @param start
	 * @param wb
	 * @param sheets
	 */
	private static void threadWriteSheet(final ExcelTemplate excel, final ExcelI excelI, final long start, Set<Sheet> sheets) {
		if (sheets.size() > 0) {
			final Workbook wb = excel.getWb();
			List<Sheet> removeSheets = new ArrayList<Sheet>();
			// 删除无用的sheet
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				// 获得sheet工作簿Sheet
				Sheet sheet = wb.getSheetAt(sheetIndex);
				if (!sheets.contains(sheet)) {
					removeSheets.add(sheet);
				}
			}
			for (Sheet removeSheet : removeSheets) {
				wb.removeSheetAt(wb.getSheetIndex(removeSheet));
			}
			final ExecutorService ee = Executors.newCachedThreadPool();
			final CountDownLatch latch = new CountDownLatch(sheets.size());
			for (final Sheet tempSheet : sheets) {
				ee.execute(new Runnable() {
					@Override
					public void run() {
						try {
							readTemplateSheet(tempSheet, excel, excelI);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// try {
						// TimeUnit.SECONDS.sleep(new Random().nextInt(1));
						// } catch (InterruptedException e1) {
						// e1.printStackTrace();
						// }
						latch.countDown();
						// boolean tempWriteFile = readTemplateSheet(tempSheet, excel, excelI);
						// if (!writeFile) {
						// writeFile = tempWriteFile;
						// }
					}
				});
			}

			ee.execute(new Runnable() {
				@Override
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						if (excel.isAutoWriteExcel()) {
							writeTemplateFile(excel);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					long end = System.currentTimeMillis();
					long x = (end - start) / 1000;
					long y = (end - start) % 1000;
					logger.warn("根据excel模板导出执行时间:" + (end - start) + "毫秒;" + x + "." + (y < 100 ? "0" + y : y) + "秒");
				}
			});
			ee.shutdown();
		}
	}

	/**
	 * 获取sheet对象
	 * 
	 * @param wb
	 * @param sheetNames
	 * @return
	 */
	private static Sheet getExportCloneSheet(final Workbook wb, final ExcelTemplate excel, final String sheetNames) {
		String sheetNamesAry[] = JavaUtil.split(sheetNames, "*");
		String sheetName = "";
		String newSheetName = "";
		if (sheetNamesAry.length > 0) {
			sheetName = sheetNamesAry[0];
		}
		if (sheetNamesAry.length > 1) {
			newSheetName = sheetNamesAry[1];
		}
		Sheet sheet = wb.getSheet(sheetName);
		if (sheet != null) {
			// 重点：针对输出多个模板sheet时，模板sheet名称取值一定要唯一，由于使用过sheet之后会重新重命名原来的模板名，否则添加多个sheet无法进行判断
			if (CheckUtil.isNull(newSheetName)) {
				// 没有重命名，克隆后，此时无法进行一个sheet模板输出多个sheet,因为在输出时已经把模板sheet重写了
			} else {
				if (sheetName.equals(newSheetName)) {
				} else {
					// 重命名
					int index = wb.getSheetIndex(sheet);
					// 被克隆对象
					Sheet cloneSheet = wb.cloneSheet(index);
					// 重命名（克隆后，还原以前的模板名）
					wb.setSheetName(index, newSheetName);
					// 重命名复制模板sheet的名字
					int cloneIndex = wb.getSheetIndex(cloneSheet);
					wb.setSheetName(cloneIndex, sheetName);
					excel.templateSheets.add(cloneSheet.getSheetName());
				}
			}
		}
		return sheet;
	}

	/**
	 * 读取sheet行列集合
	 * 
	 * @param sheet
	 * @param excel
	 * @param excelI
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static final synchronized boolean readTemplateSheet(final Sheet sheet, final ExcelTemplate excel, final ExcelI excelI) throws Exception {
		// 设置写文件状态:true[写],false[不写]
		boolean writeFile = false;
		Set<String> nameCollKeys = new HashSet<String>();
		ReadExcelI rexcelI = null;
		if (excelI != null) {
			rexcelI = (ReadExcelI) excelI;
		}

		int endRowIndex = rexcelI == null ? ExcelI.END_ROW_INDEX : rexcelI.getEndRowIndex();
		int endColumnIndex = rexcelI == null ? ExcelI.END_COLUMN_INDEX : rexcelI.getEndColumnIndex();
		// 获得sheet总行数
		// 循环sheet中的所有行数据
		for (int rowIndex = rexcelI == null ? ExcelI.START_ROW_INDEX : rexcelI.getStartRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			// 是否结束
			if (endRowIndex != -1 && rowIndex > endRowIndex)
				break;
			if (rexcelI != null && rexcelI.filterRow(sheet, rowIndex)) {
				// ("sheetName:" + sheet.getSheetName() + ",第" + rowIndex + "行数据已被过虑");
				continue;
			}
			// 行数据
			// 获得行对象
			Row row = sheet.getRow(rowIndex);
			if (null != row) {
				// 获得本行中单元格的个数
				// 遍历列cell
				for (int columnIndex = rexcelI == null ? 0 : rexcelI.getStartColumnIndex(); columnIndex < row.getLastCellNum(); columnIndex++) {
					// 是否结束
					if (endColumnIndex != -1 && columnIndex > endColumnIndex)
						break;
					Cell cell = row.getCell(columnIndex);
					// 获得指定单元格中的数据
					Object value = readCellString(cell);
					value = rexcelI == null ? value : rexcelI.readValue(sheet, rowIndex, columnIndex, value);
					if (value instanceof String) {
						// 只获取String的字符串
						String svalue = "";
						// 取得表达式串
						Map<String, String> valueMap = RegexUtil.fillString(svalue = JavaUtil.objToStr(value));
						// 取得所有占位符名
						String names = valueMap.get(RegexUtil.FillString.KEY_PLACEHOLDER_NAMES);
						if (CheckUtil.isNotNull(names)) {
							// 取得占位符
							String places = valueMap.get(RegexUtil.FillString.KEY_PLACEHOLDER);
							// 如果是单纯的表达式,则可能是日期格式,数字,或其它格式,需要格式化
							boolean isOne = svalue.equals(places);
							// 多个占位符数组
							String[] nameAry = names.split(RegexUtil.FillString.KEY_PLACEHOLDER_NAMES_SPLIT);
							if (nameAry.length > 0) {
								// 循环所有占位符名
								for (String name : nameAry) {
									if ("".equals(name)) {
										continue;
									}
									// 记录索引
									int index = -1;
									if ((index = name.indexOf(".")) == -1) {
										// 根据占位符取得占位符值
										Object ovalue = excel.getNameValueMap().get(name);
										if (ovalue instanceof FormulaTemplate) {
											FormulaTemplate ft = (FormulaTemplate) ovalue;
											// 改变单元格值
											ExcelUtil.setCellValue(sheet, row, cell, rowIndex, columnIndex, ovalue, null);
											// 赋值改变后的值
											svalue = ft.value;
											// 设置写文件状态
											writeFile = true;
										} else {
											String placeValue = JavaUtil.objToStr(ovalue);
											if (placeValue == null || "".equals(placeValue)) {
												continue;
											}
											// 实例替换占位符值的集体
											Map<String, String> sNameValueMapTemp = new HashMap<String, String>();
											// 设置占位符名对应的占位符值
											sNameValueMapTemp.put(name, placeValue);
											// 获取替换结果
											Map<String, String> resultNameValueMap = RegexUtil.fillString(svalue, sNameValueMapTemp);
											// 获取替换后的字符串
											String newValue = resultNameValueMap.get(RegexUtil.FillString.KEY_NEW_VALUE);
											// 改变单元格值
											ExcelUtil.setCellValue(sheet, row, cell, rowIndex, columnIndex, newValue, null);
											// 赋值改变后的值
											svalue = newValue;
											// 设置写文件状态
											writeFile = true;
										}
									} else {
										int firstC = 0;
										int lastC = 0;
										int firstR = 0;
										int lastR = 0;
										int flr = 0;
										// 取得占位符前缀
										String prefixName = name.substring(0, index);
										// 取得属性名
										String propertyName = name.substring(index + 1);
										// 获取集合引用对象
										String nameKey = excel.getCollectionKey(prefixName);
										// 取得nameKey对应的集体数据
										Object nameObj = excel.getNameValueMap().get(nameKey);
										if (nameObj != null) {
											// 是否是合并单元格
											boolean isMerged = false;
											// 设置临时行变量
											int rowIndexTemp = rowIndex;
											CellRangeAddress ca = getMergedRegion(sheet, rowIndex, columnIndex);
											if (ca != null) {
												// 获得合并单元格的起始行, 结束行, 起始列, 结束列, 行数
												firstC = ca.getFirstColumn();
												lastC = ca.getLastColumn();
												firstR = ca.getFirstRow();
												lastR = ca.getLastRow();
												isMerged = true;
												flr = lastR - firstR;
											}
											if (nameObj instanceof Collection) {
												// 如果值是集合对象
												Collection<Object> coll = (Collection<Object>) nameObj;
												if (coll != null && coll.size() > 0) {
													// 设置此属性是否是插入行
													String sAutoInsertRows = excel.getPropertyKey(prefixName, ExcelTemplate.PROPERTY_AUTO_INSERT_ROWS);
													boolean autoInsertRows = true;
													if (CheckUtil.isNotNull(sAutoInsertRows)) {
														Object oAutoInsertRows = excel.getNameValueMap().get(sAutoInsertRows);
														if (oAutoInsertRows != null) {
															autoInsertRows = TypeUtil.Primitive.booleanValue(oAutoInsertRows);
														}
													}
													if (autoInsertRows) {
														String nameCollKey = nameKey + ":" + rowIndex;
														if (!nameCollKeys.contains(nameCollKey)) {
															//添加新行时拷贝原有值(默认)
															ExcelUtil.insertRows(sheet, rowIndex, coll.size() - 1, true);
															nameCollKeys.add(nameCollKey);
														}
													}
													String tempSvalue = svalue;
													int collIndex = 0;
													int collSize = coll.size();
													// 如果存在
													int firstIndex = 1;
													for (Object obj : coll) {
														// 获取当前单元格式行
														Row rowTemp = sheet.getRow(rowIndexTemp);
														if (rowTemp == null) {
															// 如果不存在,则实例化
															rowTemp = sheet.createRow(rowIndexTemp);
														}
														// 获取当前单元格式列
														Cell cellTemp = rowTemp.getCell(columnIndex);
														if (cellTemp == null) {
															// 如果不存在,则实例化
															cellTemp = rowTemp.createCell(columnIndex);
														}
														// 取得属性值
														Object fieldObjValue = null;
														try {
															if (obj instanceof Map) {
																Map<String, Object> objMap = (Map<String, Object>) obj;
																fieldObjValue = objMap.get(propertyName);
															} else {
																fieldObjValue = FieldUtil.get(obj, propertyName, true);
															}
														} catch (Exception e) {
															fieldObjValue = "";
															logger.error("获取对象值异常,默认空", e);
														}
														if (!isOne) {
															// 非String
															String convertStr = "";
															try {
																if (fieldObjValue instanceof String) {
																	convertStr = JavaUtil.objToStr(fieldObjValue);
																} else if (fieldObjValue instanceof Date || fieldObjValue instanceof Calendar || fieldObjValue instanceof Timestamp) {
																	convertStr = DateUtil.dateParse(fieldObjValue, "yyyy-MM-dd HH:mm:ss");
																	// 日期格式化成字符串
																} else if (fieldObjValue instanceof Boolean) {
																	convertStr = JavaUtil.objToStr(fieldObjValue);
																} else if (fieldObjValue instanceof Number) {
																	// 数字
																	convertStr = JavaUtil.objToStr(fieldObjValue);
																} else {
																	convertStr = JavaUtil.objToStr(fieldObjValue);
																}
															} catch (Exception e) {
																convertStr = "";
																logger.error("转换值出错,默认空", e);
															}
															// 如果值是String类型
															// 实例替换占位符值的集体
															Map<String, String> sNameValueMapTemp = new HashMap<String, String>();
															// 设置占位符名对应的占位符值
															sNameValueMapTemp.put(name, convertStr);
															// 获取替换结果
															Map<String, String> resultNameValueMap = RegexUtil.fillString(tempSvalue, sNameValueMapTemp);
															// 获取替换后的字符串
															fieldObjValue = resultNameValueMap.get(RegexUtil.FillString.KEY_NEW_VALUE);
															if (firstIndex == 1) {
																firstIndex++;
																// 赋值改变后的值
																svalue = JavaUtil.objToStr(fieldObjValue);
															}
														}
														// 复制单元格
														ExcelUtil.copyCell(cell, cellTemp, false, true, true);
														ExcelUtil.setCellValue(sheet, rowTemp, cellTemp, rowIndexTemp, columnIndex, fieldObjValue, null);
														if (isMerged) {
															// XXX 复制合并单元格(需要改)
															for (int r = firstR; r <= lastR; r++) {
																// 获取当前单元格式行
																Row rowTempStyle = sheet.getRow(r);
																if (rowTempStyle == null) {
																	// 如果不存在,则实例化
																	rowTempStyle = sheet.createRow(r);
																}
																for (int c = firstC; c <= lastC; c++) {
																	// 获取当前单元格式列
																	Cell cellTempStyle = rowTempStyle.getCell(c);
																	if (cellTempStyle == null) {
																		// 如果不存在,则实例化
																		cellTempStyle = rowTempStyle.createCell(c);
																	}
																	ExcelUtil.copyCell(cell, cellTempStyle, false, true, true);
																	// ExcelUtil.copyCellStyle(cell.getCellStyle(), cellTempStyle.getCellStyle());
																}
															}
														}
														if (collIndex < collSize - 1) {
															// 如果最后一条数据,则无需合并单元格
															if (isMerged) {
																// 行循环
																// 继续下一行
																firstR = lastR + 1;
																lastR = firstR + flr;
																// 合并单元格
																ExcelUtil.mergerRegion(sheet, firstR, lastR, firstC, lastC);
																rowIndexTemp = firstR;
															} else {
																// 继续下一行
																rowIndexTemp++;
															}
														}
														collIndex++;
													}
													// 设置写文件状态
													writeFile = true;
												} else {
													ExcelUtil.setCellValue(sheet, row, cell, rowIndex, columnIndex, null, null);
													// 设置写文件状态
													writeFile = true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return writeFile;
	}

	/**
	 * 读取excel某个单元格值的索引号
	 * 
	 * @param excel
	 *            excel对象
	 * @param params
	 *            参数
	 * @return 单元格值
	 * @author 张军
	 * @date 2015-11-03 21:59:00
	 * @modifiyNote
	 * @version 1.0
	 * @throws Exception
	 */
	public static final Map<ConstantForEnum.CellValueKey, Integer> readCellValueIndex(final Excel excel, Map<ConstantForEnum.CellValueKey, Object> params) throws Exception {
		Map<ConstantForEnum.CellValueKey, Integer> returnMap = new HashMap<ConstantForEnum.CellValueKey, Integer>();
		returnMap.put(ConstantForEnum.CellValueKey.ROW_INDEX, -1);
		returnMap.put(ConstantForEnum.CellValueKey.COLUMN_INDEX, -1);
		String value = JavaUtil.trim(JavaUtil.objToStr(params.get(ConstantForEnum.CellValueKey.VALUE)));
		Object valueCountObj = params.get(ConstantForEnum.CellValueKey.VALUE_COUNT);
		boolean valueCountMatch = TypeUtil.Primitive.booleanValue(params.get(ConstantForEnum.CellValueKey.VALUE_COUNT_MATCH));
		Object valueRowObj = params.get(ConstantForEnum.CellValueKey.VALUE_ROW);
		Object valueColumnObj = params.get(ConstantForEnum.CellValueKey.VALUE_COLUMN);
		int valueRow = -1;
		int valueColumn = -1;
		if (valueRowObj != null) {
			valueRow = TypeUtil.Primitive.intValue(valueRowObj);
		}
		if (valueColumnObj != null) {
			valueColumn = TypeUtil.Primitive.intValue(valueColumnObj);
		}
		int valueCount = 1;
		if (valueCountObj != null) {
			valueCount = TypeUtil.Primitive.intValue(valueCountObj);
		}
		if (CheckUtil.isNull(value)) {
			logger.debug("查询值不能为空");
			return returnMap;
		}

		int valueCountDefault = 0;
		int tempRowCount = -1;
		int tempColumnCount = -1;
		Sheet sheet = getSheet(excel);
		// 如果未指定查询行,则查询所有行，否则只查询一行,从valueRow开始到valueRow+1结束
		int startRows = -1;
		int rows = -1;
		if (valueRow == -1) {
			startRows = 0;
			rows = sheet.getLastRowNum();
		} else {
			startRows = valueRow;
			rows = valueRow + 1;
		}
		for (int i = startRows; i < rows; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				logger.debug("第[" + i + "]行对象为null,继续下个行对象查询");
				continue;
			}
			int startColumns = -1;
			int columns = -1;
			if (valueColumn == -1) {
				startColumns = 0;
				columns = row.getLastCellNum();
			} else {
				startColumns = valueColumn;
				columns = valueColumn + 1;
			}
			breakFor: for (int j = startColumns; j < columns; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					logger.debug("第[" + i + "]行第[" + j + "]列单元格对象为null,继续下个单元格查询");
					continue;
				}
				Object obj = readCellString(cell);
				String cellValue = JavaUtil.trim(JavaUtil.objToStr(obj));
				if (valueCountDefault != valueCount) {
					// 如果未找到指定的匹配索引，则继续查找
					if (value.equals(cellValue)) {
						tempRowCount = i;
						tempColumnCount = j;
						valueCountDefault++;
					}
				}
				if (tempRowCount != -1 && tempColumnCount != -1) {
					// 获取行列索引，判断行列第几个
					if (valueCountDefault == valueCount) {
						logger.debug("找到位置了行[" + tempRowCount + "]列[" + tempColumnCount + "]第[" + valueCount + "]个值");
						break breakFor;
					}
				}
			}
		}
		if (tempRowCount == -1 || tempColumnCount == -1) {
			logger.debug("没有找到位置了行[" + tempRowCount + "]列[" + tempColumnCount + "]第[" + valueCount + "]个值");
			return returnMap;
		} else {
			if (valueCountMatch && valueCountDefault != valueCount) {
				logger.debug("行必须匹配,没有找到位置了行[" + tempRowCount + "]列[" + tempColumnCount + "][最多第(" + valueCountDefault + ")个与配置第(" + valueCount + ")个值不同]");
				return returnMap;
			}
			returnMap.put(ConstantForEnum.CellValueKey.ROW_INDEX, tempRowCount);
			returnMap.put(ConstantForEnum.CellValueKey.COLUMN_INDEX, tempColumnCount);
		}
		return returnMap;
	}

	/**
	 * 读取excel某个单元格值
	 * 
	 * @param excel
	 *            excel对象
	 * @param params
	 *            参数
	 * @return 单元格值
	 * @author 张军
	 * @date 2015-11-03 21:59:00
	 * @modifiyNote
	 * @version 1.0
	 * @throws Exception
	 */
	public static final Object readCellValue(final Excel excel, Map<ConstantForEnum.CellValueKey, Object> params) throws Exception {
		Object rowIndexObj = params.get(ConstantForEnum.CellValueKey.ROW_INDEX);
		Object columnIndexObj = params.get(ConstantForEnum.CellValueKey.COLUMN_INDEX);
		if (rowIndexObj == null || columnIndexObj == null) {
			Sheet sheet = getSheet(excel);
			Map<ConstantForEnum.CellValueKey, Object> paramsRow = new HashMap<ConstantForEnum.CellValueKey, Object>();
			paramsRow.put(ConstantForEnum.CellValueKey.VALUE, params.get(ConstantForEnum.CellValueKey.ROW_VALUE));
			paramsRow.put(ConstantForEnum.CellValueKey.VALUE_COUNT, params.get(ConstantForEnum.CellValueKey.ROW_COUNT));
			paramsRow.put(ConstantForEnum.CellValueKey.VALUE_COUNT_MATCH, params.get(ConstantForEnum.CellValueKey.ROW_COUNT_MATCH));
			Map<ConstantForEnum.CellValueKey, Integer> returnRowIndex = ExcelRWUtil.readCellValueIndex(excel, paramsRow);
			int tempRowCount = returnRowIndex.get(ConstantForEnum.CellValueKey.ROW_INDEX);
			if (tempRowCount == -1) {
				return null;
			}
			Map<ConstantForEnum.CellValueKey, Object> paramsColumn = new HashMap<ConstantForEnum.CellValueKey, Object>();
			paramsColumn.put(ConstantForEnum.CellValueKey.VALUE, params.get(ConstantForEnum.CellValueKey.COLUMN_VALUE));
			paramsColumn.put(ConstantForEnum.CellValueKey.VALUE_COUNT, params.get(ConstantForEnum.CellValueKey.COLUMN_COUNT));
			paramsColumn.put(ConstantForEnum.CellValueKey.VALUE_COUNT_MATCH, params.get(ConstantForEnum.CellValueKey.COLUMN_COUNT_MATCH));
			Map<ConstantForEnum.CellValueKey, Integer> returnColumnIndex = ExcelRWUtil.readCellValueIndex(excel, paramsColumn);
			int tempColumnCount = returnColumnIndex.get(ConstantForEnum.CellValueKey.COLUMN_INDEX);
			if (tempColumnCount == -1) {
				return null;
			}
			return readCellString(sheet.getRow(tempRowCount).getCell(tempColumnCount));
			// String rowValue = JavaUtil.trim(JavaUtil.objToStr(params.get(ConstantForEnum.CellValueKey.ROW_VALUE)));
			// String columnValue = JavaUtil.trim(JavaUtil.objToStr(params.get(ConstantForEnum.CellValueKey.COLUMN_VALUE)));
			// Object rowCountObj = params.get(ConstantForEnum.CellValueKey.ROW_COUNT);
			// Object columnCountObj = params.get(ConstantForEnum.CellValueKey.COLUMN_COUNT);
			// boolean rowCountMatch = TypeUtil.Primitive.booleanValue(params.get(ConstantForEnum.CellValueKey.ROW_COUNT_MATCH));
			// boolean columnCountMatch = TypeUtil.Primitive.booleanValue(params.get(ConstantForEnum.CellValueKey.COLUMN_COUNT_MATCH));
			// int rowCount = 1;
			// int columnCount = 1;
			// if (rowCountObj != null) {
			// rowCount = TypeUtil.Primitive.intValue(rowCountObj);
			// }
			// if (columnCountObj != null) {
			// columnCount = TypeUtil.Primitive.intValue(columnCountObj);
			// }
			// if (CheckUtil.isNull(rowValue) || CheckUtil.isNull(columnValue)) {
			// logger.debug("行[" + rowValue + "]或列[" + columnValue + "]值不能为空");
			// return null;
			// }
			// int rowCountDefault = 0;
			// int columnCountDefault = 0;
			// int tempRowCount = -1;
			// int tempColumnCount = -1;
			// Sheet sheet = getSheet(excel);
			// for (int i = 0; i < sheet.getLastRowNum(); i++) {
			// Row row = sheet.getRow(i);
			// if (row == null) {
			// logger.debug("第[" + i + "]行对象为null,继续下个行对象查询");
			// continue;
			// }
			// breakFor: for (int j = 0; j < row.getLastCellNum(); j++) {
			// Cell cell = row.getCell(j);
			// if (cell == null) {
			// logger.debug("第[" + i + "]行第[" + j + "]列单元格对象为null,继续下个单元格查询");
			// continue;
			// }
			// Object obj = readCellString(cell);
			// String value = JavaUtil.trim(JavaUtil.objToStr(obj));
			// if (rowCountDefault != rowCount) {
			// // 如果未找到指定的匹配行索引，则继续查找
			// if (rowValue.equals(value)) {
			// tempRowCount = i;
			// rowCountDefault++;
			// }
			// }
			// if (columnCountDefault != columnCount) {
			// // 如果未找到指定的匹配列索引，则继续查找
			// if (columnValue.equals(value)) {
			// tempColumnCount = j;
			// columnCountDefault++;
			// }
			// }
			// if (tempRowCount != -1 && tempColumnCount != -1) {
			// // 获取行列索引，判断行列第几个
			// if (rowCountDefault == rowCount && columnCountDefault == columnCount) {
			// logger.debug("找到位置了行[" + tempRowCount + "]第[" + rowCount + "]个值，列[" + tempColumnCount + "]第[" + columnCount + "]个值");
			// break breakFor;
			// }
			// }
			// }
			// }
			// if (tempRowCount == -1 || tempColumnCount == -1) {
			// logger.debug("没有找到位置了行[" + tempRowCount + "]第[" + rowCount + "]个值，列[" + tempColumnCount + "]第[" + columnCount + "]个值");
			// return null;
			// } else {
			// if (rowCountMatch && rowCountDefault != rowCount) {
			// logger.debug("行必须匹配,没有找到位置了行[" + tempRowCount + "][最多第(" + rowCountDefault + ")个与配置第(" + rowCount + ")个值不同]，列[" + tempColumnCount + "]第[" + columnCount + "]个值");
			// return null;
			// }
			// if (columnCountMatch && columnCountDefault != columnCount) {
			// logger.debug("列必须匹配,没有找到位置了行[" + tempRowCount + "]第[" + rowCount + "]个值，列[" + tempColumnCount + "][最多第(" + columnCountDefault + ")个与配置第(" + columnCount + ")个值不同]");
			// return null;
			// }
			// return readCellString(sheet.getRow(tempRowCount).getCell(tempColumnCount));
			// }
		} else {
			int rowIndex = TypeUtil.Primitive.intValue(rowIndexObj);
			int columnIndex = TypeUtil.Primitive.intValue(columnIndexObj);
			if (rowIndex == -1 || columnIndex == -1) {
				logger.debug("行[" + rowIndex + "]或列[" + columnIndex + "]索引值不能小于0");
				return null;
			}
			Sheet sheet = getSheet(excel);
			Row row = sheet.getRow(TypeUtil.Primitive.intValue(rowIndexObj));
			if (row == null) {
				logger.debug("第[" + rowIndex + "]行对象为null");
				return null;
			}
			Cell cell = row.getCell(columnIndex);
			if (cell == null) {
				logger.debug("第[" + rowIndex + "]行第[" + columnIndex + "]列单元格对象为null");
				return null;
			}
			return readCellString(cell);
		}
	}

	/**
	 * 获取sheet对象
	 * 
	 * @return sheet对象
	 * @author 张军
	 * @date 2015-11-03 21:59:00
	 * @modifiyNote
	 * @version 1.0
	 * @throws Exception
	 */
	private static Sheet getSheet(final Excel excel) throws Exception {
		excel.initWorkbook();
		Workbook wb = excel.getWb();
		// key:index/sheet/sheetName
		Sheet sheet = null;
		// 获得sheet工作簿Sheet
		Object sheetValue = excel.getSheetValue();
		if (sheetValue == null) {
			sheet = wb.getSheetAt(0);
		} else {
			if (sheetValue instanceof String) {
				String sheetName = String.valueOf(sheetValue);
				sheet = wb.getSheet(sheetName);
			} else if (sheetValue instanceof Integer) {
				Integer sheetCount = Integer.parseInt(String.valueOf(sheetValue));
				sheet = wb.getSheetAt(sheetCount);
			}
		}
		return sheet;
	}

	/**
	 * 写文件
	 */
	public static final void writeTemplateFile(final ExcelTemplate excel) throws Exception {
		Workbook wb = excel.getWb();
		try {
			List<Sheet> removeSheets = new ArrayList<Sheet>();
			// for (String name : excel.addedSheets) {
			// System.out.println(name);
			// }
			// System.out.println("==================");
			// for (String name : excel.templateSheets) {
			// System.out.println(name);
			// }
			// System.out.println("---------------------");
			// 删除无用的sheet
			if (excel.isRemoveOtherSheets()) {
				for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
					// 获得sheet工作簿Sheet
					Sheet sheet = wb.getSheetAt(sheetIndex);
					String name = sheet.getSheetName();
					// 除了模板和新增sheet,其它全部移除
					if (!(excel.addedSheets.contains(name) || excel.templateSheets.contains(name))) {
						removeSheets.add(sheet);
					}
				}
				for (Sheet removeSheet : removeSheets) {
					// System.out.println(removeSheet.getSheetName());
					// 删除其它sheet及模板sheet
					wb.removeSheetAt(wb.getSheetIndex(removeSheet));
				}
			}
			if (excel.isRemoveTemplatesSheets()) {
				// 移除模板sheet
				for (String name : excel.templateSheets) {
					Sheet removeSheet = wb.getSheet(name);
					// System.out.println(removeSheet.getSheetName());
					wb.removeSheetAt(wb.getSheetIndex(removeSheet));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedOutputStream bos = null;
		try {
			String[] paths = FileUtil.getFileNameExtension(excel.getReFilePath());
			File file = null;
			if ("false".equals(paths[3])) {
				// 文件夹
				file = new File(paths[0]);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
			// 文件
			file = new File(paths[0] + paths[1] + paths[2]);
			bos = new BufferedOutputStream(new FileOutputStream(file));
			// 设置默认活动的sheet为第一个
			// wb.setSelectedTab(0);
			wb.setActiveSheet(0);
			wb.write(bos);
			bos.flush();
			logger.debug("写入模板文件成功");
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (wb != null) {
				wb.close();
				wb = null;
			}
		}
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return null:没有合并,非null,有合并
	 */
	public static final CellRangeAddress getMergedRegion(final Sheet sheet, final int row, final int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return range;
				}
			}
		}
		return null;
	}

	/**
	 * 获取一个cell的数据类型
	 * 
	 * @param cell
	 * @return
	 */
	public static final Object readCellString(final Cell cell) {
		Object result = null;
		if (cell != null) {
			// 单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
			int cellType = cell.getCellType();
			try {
				switch (cellType) {
				case Cell.CELL_TYPE_STRING:
					result = cell.getRichStringCellValue().getString();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
						result = cell.getDateCellValue();
					} else {
						result = cell.getNumericCellValue();
					}
					break;
				case Cell.CELL_TYPE_FORMULA:
					try {
						result = cell.getNumericCellValue();
					} catch (Exception e) {
						logger.error("获取excel公式结果出错:[" + cell.getRowIndex() + "][" + cell.getColumnIndex() + "]", e);
						// 返回公式
						result = "=" + cell.getCellFormula();
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					result = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					result = null;
					break;
				case Cell.CELL_TYPE_ERROR:
					result = null;
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// 读取文本
				logger.error("读取格式行列[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "]出错，改成读取文本：" + e.getMessage());
				try {
					result = cell.getRichStringCellValue().getString();
				} catch (Exception e1) {
					logger.error("改成读取文本出错，返回null：" + e1.getMessage());
					return null;
				}
			}
		}
		return JavaUtil.getIsNumValue(result);
	}
	
	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	public static final List<String> readExcelSheetNames(final Excel excel) throws Exception {
		List<String> sheetNames = new ArrayList<String>();
		excel.initWorkbook();
		Workbook wb = excel.getWb();
		int sheetCount = wb.getNumberOfSheets();
		// 循环所有sheet
		for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
			// 获得sheet工作簿Sheet
			Sheet sheet = wb.getSheetAt(sheetIndex);
			sheetNames.add(sheet.getSheetName());
		}
		return sheetNames;
	}
}