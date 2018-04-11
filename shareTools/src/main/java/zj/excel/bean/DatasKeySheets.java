package zj.excel.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 概况 ：Excel所有数据集合<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class DatasKeySheets implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SheetDatas> sheetDatas;
	private int sheetCount;

	/**
	 * 返回sheet数量
	 * 
	 * @return 返回sheet数量
	 */
	public int getSheetCount() {
		return sheetCount;
	}

	/**
	 * 设置sheet数量
	 * 
	 * @param sheetCount
	 *            sheet数量
	 */
	public void setSheetCount(int sheetCount) {
		this.sheetCount = sheetCount;
	}

	/**
	 * 获取所有sheet数据
	 * 
	 * @return 所有sheet数据
	 */
	public List<SheetDatas> getSheetDatas() {
		return sheetDatas;
	}

	/**
	 * 设置所有sheet数据
	 * 
	 * @param sheetDatas 所有sheet数据
	 */
	public void setSheetDatas(List<SheetDatas> sheetDatas) {
		this.sheetDatas = sheetDatas;
	}

	/**
	 * 根据sheet索引获取excel数据对象
	 * 
	 * @param sheetIndex
	 *            sheet索引
	 * @return excel数据对象
	 */
	public SheetDatas getSheetDatasByIndex(int sheetIndex) {
		if (this.sheetDatas == null)
			return null;
		for (SheetDatas sd : this.sheetDatas) {
			if (sheetIndex == sd.getSheetIndex()) {
				return sd;
			}
		}
		return null;
	}

	/**
	 * 根据sheet名获取excel数据对象
	 * 
	 * @param sheetName
	 *            sheet名
	 * @return excel数据对象
	 */
	public SheetDatas getSheetDatasBySheetName(String sheetName) {
		if (this.sheetDatas == null || sheetName == null)
			return null;
		for (SheetDatas sd : this.sheetDatas) {
			if (sheetName.equalsIgnoreCase(sd.getSheetName())) {
				return sd;
			}
		}
		return null;
	}

	/**
	 * 根据sheet获取excel数据对象
	 * 
	 * @param sheet
	 *            sheet对象
	 * @return excel数据对象
	 */
	public SheetDatas getSheetDatasBySheet(Sheet sheet) {
		if (this.sheetDatas == null || sheet == null)
			return null;
		for (SheetDatas sd : this.sheetDatas) {
			if (sheet.equals(sd.getSheet())) {
				return sd;
			}
		}
		return null;
	}
}
