package zj.excel.bean;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 概况 ：写Excel接口实现类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class SimpleWriteExcelImpl implements WriteExcelI {
	private static final long serialVersionUID = 1L;

	public boolean filterRow(Sheet sheet, int rowIndex) {
		return false;
	}

	public void mergedRegion(Sheet sheet, List<List<SheetData>> datasLst) {
	}

	public void setCellValue(Sheet sheet, Row row, Cell cell, int rowIndex, int colIndex, Object value) {
	}

	public int getStartRowIndex() {
		return START_ROW_INDEX;
	}

	public int getStartColumnIndex() {
		return START_COLUMN_INDEX;
	}

	public int getEndRowIndex() {
		return END_ROW_INDEX;
	}

	public int getEndColumnIndex() {
		return END_COLUMN_INDEX;
	}

}