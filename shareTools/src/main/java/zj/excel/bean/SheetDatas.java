package zj.excel.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 概况 ：Excel一个sheet的数据(行列)<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class SheetDatas implements Serializable {
	private static final long serialVersionUID = 1L;
	private int sheetIndex = -1;
	private String sheetName = "Sheet1";;
	private Sheet sheet;
	private List<List<SheetData>> rowsDataLst;
	private List<Map<String,SheetData>> rowsDataLstMap;

	/**
	 * 获取sheet索引
	 * 
	 * @return sheet索引
	 */
	public int getSheetIndex() {
		return sheetIndex;
	}

	/**
	 * 设置sheet索引
	 * 
	 * @param sheetIndex
	 *            sheet索引
	 */
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	/**
	 * 获取sheet名
	 * 
	 * @return sheet名
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * 设置sheet名
	 * 
	 * @param sheetName
	 *            sheet名
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 获取sheet对象
	 * 
	 * @return sheet对象
	 */
	public Sheet getSheet() {
		return sheet;
	}

	/**
	 * 设置sheet对象
	 * 
	 * @param sheet
	 *            sheet对象
	 */
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * 所有行列数据
	 * 
	 * @return 所有行列数据
	 */
	public List<List<SheetData>> getRowsDataLst() {
		return rowsDataLst;
	}

	/**
	 * 设置所有行列数据
	 * 
	 * @param rowsDataLst
	 *            所有行列数据
	 */
	public void setRowsDataLst(List<List<SheetData>> rowsDataLst) {
		this.rowsDataLst = rowsDataLst;
	}

	/**
	 * 获取所有行列数据带map
	 * 
	 * @return 所有行列数据带map
	 */
	public List<Map<String,SheetData>> getRowsDataLstMap() {
		return rowsDataLstMap;
	}

	/**
	 * 设置所有行列数据带map
	 * 
	 * @param rowsDataLstMap
	 *            所有行列数据带map
	 */
	public void setRowsDataLstMap(List<Map<String,SheetData>> rowsDataLstMap) {
		this.rowsDataLstMap = rowsDataLstMap;
	}

}
