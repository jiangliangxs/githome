package test.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import zj.date.util.DateUtil;
import zj.excel.bean.DatasKeySheets;
import zj.excel.bean.Excel;
import zj.excel.bean.ExcelTemplate;
import zj.excel.bean.FormulaTemplate;
import zj.excel.bean.SheetData;
import zj.excel.bean.SheetDatas;
import zj.excel.bean.SimpleReadExcelImpl;
import zj.excel.util.ConstantForEnum;
import zj.excel.util.ExcelRWUtil;
import zj.java.util.JavaUtil;

import com.alibaba.fastjson.JSON;

public class TestExcel {
	@Test
	public void 读取excel文件所有sheet名() {
		try {
			Excel excel = new Excel();
			excel.setFilePath(TestExcel.class.getResource("files/account.xls").getFile());
			List<String> sheetNames = ExcelRWUtil.readExcelSheetNames(excel);
			System.out.println("======读取数据结果如下==========");
			for (String sheetName : sheetNames) {
				System.out.println(sheetName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 读取行列数据_读取所有行列() {
		try {
			Excel excel = new Excel();
			excel.setFilePath(TestExcel.class.getResource("files/account.xls").getFile());
			excel.setSheetValue("交易确认信息");
			// 可以不实现内部类
			DatasKeySheets datasKeySheets = ExcelRWUtil.readExcel(excel, new SimpleReadExcelImpl() {
				private static final long serialVersionUID = 1L;

				@Override
				public int getStartRowIndex() {
					return 3;// 第4行
				}

				@Override
				public int getEndRowIndex() {
					return 5;// 第6行
				}

				@Override
				public int getStartColumnIndex() {
					return 2;// 第3列
				}

				@Override
				public int getEndColumnIndex() {
					return 4;// 第5列
				}
			});
			System.out.println("======读取数据结果如下==========");
			List<SheetDatas> sheetDatas = datasKeySheets.getSheetDatas();
			if (sheetDatas != null && sheetDatas.size() > 0) {
				SheetDatas sheetData = sheetDatas.get(0);
				List<List<SheetData>> datasLst = sheetData.getRowsDataLst();
				if (datasLst != null && datasLst.size() > 0) {
					for (int a = 0; a < datasLst.size(); a++) {
						List<SheetData> dataLst = datasLst.get(a);
						// 循环某个sheet的列数据
						for (SheetData data : dataLst) {
							if (data != null) {
								System.out.print(data.getValue() + "\t\t");
							}
						}
						System.out.println();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 读取行列数据_根据标题() {
		try {
			List<String> titleList = new ArrayList<String>();
			titleList.add("客户名称");
			titleList.add("账户状态");
			titleList.add("账户类型");
			Excel readExcel = new Excel();
			readExcel.setFilePath(TestExcel.class.getResource("files/account.xls").getFile());
			readExcel.setAddTitleKeyRow(false);
			readExcel.setTitleKeys(titleList);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			Map<String, Object> titleKeyMap = new HashMap<String, Object>();
			titleKeyMap.put("valueCount", 2);
			paramsMap.put("账户类型", titleKeyMap);
			String jsonParams = JSON.toJSONString(paramsMap);
			readExcel.setJsonParams(jsonParams);
			readExcel.setTitleKeys(titleList);
			readExcel.setSheetValue(0);
			readExcel.setReadTitleKeys(true);
			DatasKeySheets datasKeySheets = ExcelRWUtil.readExcel(readExcel);
			List<SheetDatas> sheetDatas = datasKeySheets.getSheetDatas();
			System.out.println("==============根据key读取结果如下==============");
			for (SheetDatas datas : sheetDatas) {
				List<Map<String, SheetData>> listMap = datas.getRowsDataLstMap();
				if (listMap != null) {
					for (Map<String, SheetData> values : listMap) {
						for (String valueKey : values.keySet()) {
							System.out.print(valueKey + ":" + values.get(valueKey).getValue() + "\t\t");
						}
						System.out.println();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 读取单元格索引() {
		try {
			Excel readExcel = new Excel();
			readExcel.setFilePath(TestExcel.class.getResource("files/account.xls").getFile());
			readExcel.setSheetValue("交易确认信息");
			Map<ConstantForEnum.CellValueKey, Object> params = new HashMap<ConstantForEnum.CellValueKey, Object>();
			params.put(ConstantForEnum.CellValueKey.VALUE, "c2");
			readExcel.setReadMemoryWb(true);
			Map<ConstantForEnum.CellValueKey, Integer> returnValue = ExcelRWUtil.readCellValueIndex(readExcel, params);
			System.out.println("returnValue:" + returnValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 根据模板导出_循环数据中带公式() {
		ExcelTemplate writeTemplateExcel = new ExcelTemplate();
		try {
			// 模板中的sheet名---->输出的excel的sheet名为“模板中的sheet名”
			// 模板中的sheet名*输出excel的sheet名---->输出的excel的sheet名为“输出excel的sheet名”
			writeTemplateExcel.setSheetValue("循环数据中带公式*输出报表");
			writeTemplateExcel.setFilePath(TestExcel.class.getResource("files/template.xls").getFile());
			writeTemplateExcel.setReFilePath("E:/must-backup/project-finished/zhangjun/博文项目/测试java工具/" + new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒").format(new Date()) + ".xls");
			// writeTemplateExcel.putProperty("list", "auto.insert.rows", "true");
			writeTemplateExcel.put("title", "测试标题-张军");
			writeTemplateExcel.put("e3", "在集合中的值");
			List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
			int size = 500;
			for (int i = 0; i < size; i++) {
				Map<String, Object> value = new HashMap<String, Object>();
				value.put("b3", JavaUtil.RandomUtil.getInt(100, 1000) * 5.876);
				value.put("d3", JavaUtil.RandomUtil.getInt(100, 1000) * 5.876);
				value.put("f3", DateUtil.addDay(new Date(), i));
				value.put("jjjc", "a" + i);
				value.put("jjdm", "b" + i);
				value.put("scjz", "c" + i);
				value.put("jyrq", "d" + i);
				values.add(value);
			}
			writeTemplateExcel.putCollection("values", values);
			FormulaTemplate bft = new FormulaTemplate();
			bft.value = "SUM(B3:B" + (size + 2) + ")";
			writeTemplateExcel.put("formualB3", bft);
			FormulaTemplate dft = new FormulaTemplate();
			dft.value = "SUM(D3:D" + (size + 2) + ")";
			writeTemplateExcel.put("formualD3", dft);
			ExcelRWUtil.writeTemplateExcel(writeTemplateExcel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 读取行列数据_判断空行() {

	}

	@Test
	public void 根据模板导出_多个sheet() {

	}

	@Test
	public void 根据模板导出_合并单元格() {

	}

}
