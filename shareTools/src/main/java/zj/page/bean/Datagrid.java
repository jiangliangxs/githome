package zj.page.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 处理查询结果后排序类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class Datagrid<T> implements Serializable {
	private static final long serialVersionUID = 1l;

	public Datagrid(int total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public Datagrid() {
	}

	private int total = 0;
	private List<T> rows = new ArrayList<T>();
	private Set<T> rowsOld;

	public Set<T> getRowsOld() {
		return rowsOld;
	}

	public void setRowsOld(Set<T> rowsOld) {
		this.rowsOld = rowsOld;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
