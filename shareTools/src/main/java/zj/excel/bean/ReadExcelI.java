package zj.excel.bean;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 概况 ：读Excel接口参数<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public interface ReadExcelI extends ExcelI {
	/**
	 * 格式化单元格值
	 * 
	 * @param sheet
	 *            sheet对象
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param value
	 *            单元格值
	 * @return 格式化后的单元格值
	 */
	public Object readValue(final Sheet sheet, final int rowIndex, final int colIndex, final Object value);
}
