package zj.page.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import zj.check.util.CheckUtil;
import zj.page.bean.Datagrid;
import zj.sort.util.SortUtil;

/**
 * 处理查询结果后分页排序类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class PageUtil {
	/**
	 * list数据分页
	 * 
	 * @param pages 数据集合
	 * @param pageNo 当前页面（从1开始）
	 * @param pageRows 每页显示行数
	 * @param sortName 排序名
	 * @param sortOrder 排序类型[asc,desc]
	 * @return
	 */
	public static <T> Datagrid<T> getDatagrid(List<T> pages, int pageNo, int pageRows, String sortName, String sortOrder) {
		Datagrid<T> data = new Datagrid<T>();
		// 设置总记录数
		data.setRows(pages);
		if (pages != null && pages.size() > 0) {
			// 设置总行数
			data.setTotal(pages.size());
			// 临时总记录数
			Set<T> rowsOld = null;
			// SortedSet
			if (CheckUtil.isNull(sortName) || CheckUtil.isNull(sortOrder)) {
				// 如果没有指定排序,则无序
				rowsOld = new HashSet<T>();
			} else {
				// 排序
				SortUtil<T> cu = new SortUtil<T>();
				cu.setSortName(sortName);
				cu.setSortOrder(sortOrder);
				rowsOld = new TreeSet<T>(cu);
			}
			// 添加临时总记录
			rowsOld.addAll(pages);
			// 设置到临时总记录对象中
			data.setRowsOld(rowsOld);
			// 清空对象中的总记录
			data.getRows().clear();
			// 如果当前页面小于等于0,则赋值1
			if (pageNo <= 0)
				pageNo = 1;
			// 计算开始记录索引
			int startNum = (pageNo - 1) * pageRows + 1;
			// 记录结束记录索引
			int endNum = pageNo * pageRows + 1;
			// 循环临时记录对象的数据
			Iterator<T> it = rowsOld.iterator();
			int _i = 0;
			while (it.hasNext()) {
				// 获取临时记录中的对象(指向下一条记录,如果不调用,则不会指向下一条记录)
				T t = it.next();
				// 过虑开始结束记录索引中的数据
				if (_i >= startNum - 1 && _i < endNum - 1) {
					// 如果临时记录中的大小小于索引
					if (rowsOld.size() < _i + 1)
						break;
					data.getRows().add(t);
				}
				_i++;
			}
		}
		return data;
	}

	/**
	 * list数据分页
	 * 
	 * @param pages
	 * @param pageNo
	 * @param pageRows
	 * @return
	 */
	public static <T> Datagrid<T> getDatagrid(List<T> pages, int pageNo, int pageRows) {
		return getDatagrid(pages, pageNo, pageRows, null, null);
	}

	/**
	 * 计算页数
	 * 
	 * @param total
	 * @param rowsPerPage
	 * @return
	 */
	public static int pageCount(int total, int rowsPerPage) {
		int pageCount = 0;
		int modNum = 0;
		pageCount = total / rowsPerPage;
		modNum = total % rowsPerPage;
		if (modNum != 0) {
			pageCount++;
		}
		return pageCount;
	}
}
