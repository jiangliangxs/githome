package zj.excel.bean;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 概况 ：写Excel接口参数<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public interface WriteExcelI extends ExcelI {
	/**
	 * 设置单元格合并
	 * 
	 * @param sheet
	 *            sheet对象
	 * @param datasLst
	 *            所有行列数据
	 */
	public void mergedRegion(Sheet sheet, List<List<SheetData>> datasLst);

	/**
	 * 格式化值
	 * 
	 * @param sheet
	 *            sheet对象
	 * @param row
	 *            行对象
	 * @param cell
	 *            单元格对象
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param value
	 *            单元格值
	 */
	public void setCellValue(Sheet sheet, Row row, Cell cell, int rowIndex, int colIndex, Object value);
}
