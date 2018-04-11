package zj.excel.bean;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 概况 ：读Excel接口实现类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class SimpleReadExcelImpl implements ReadExcelI {
	private static final long serialVersionUID = 1L;

	public boolean filterRow(final Sheet sheet, final int rowIndex) {
		return false;
	}

	public Object readValue(final Sheet sheet, final int rowIndex, final int colIndex, final Object value) {
		return value;
	}

	public int getStartRowIndex() {
		return 0;
	}

	public int getStartColumnIndex() {
		return 0;
	}

	public int getEndRowIndex() {
		return -1;
	}

	public int getEndColumnIndex() {
		return -1;
	}
}