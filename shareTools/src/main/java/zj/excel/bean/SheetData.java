package zj.excel.bean;

import java.io.Serializable;

/**
 * 概况 ：Excel一个sheet的数据(行)<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class SheetData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Object value;

	/**
	 * 获取单元格数据
	 * 
	 * @return 单元格数据
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置单元格数据
	 * 
	 * @param value
	 *            单元格数据
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
