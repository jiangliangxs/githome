package test.page;
//package com.zjsystem.framework.page;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//
//import zj.check.util.CheckUtil;
//import zj.sort.util.SortUtil;
//
///**
// * 系统名：张军版权所有<br>
// * 类名 ：PageDataGrid<br>
// * 概况 ：处理查询结果后排序类<br>
// * 
// * @version 1.00 （2011.12.02）
// * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
// */
//public class PageDataGrid<T> implements Serializable {
//
//	private static final long serialVersionUID = 1l;
//
//	public PageDataGrid(int total, List<T> rows) {
//		this.total = total;
//		this.rows = rows;
//	}
//
//	public PageDataGrid(PageRequest page, List<T> pages) {
//		this.rows = pages;
//		if (page != null && pages != null && pages.size() > 0) {
//			this.total = pages.size();
//			// SortedSet
//			if (CheckUtil.isNull(page.getSort()) || CheckUtil.isNull(page.getOrder())) {
//				this.rowsOld = new HashSet<T>();
//			} else {
//				SortUtil<T> cu = new SortUtil<T>();
//				cu.setSortName(page.getSort());
//				cu.setSortOrder(page.getOrder());
//				this.rowsOld = new TreeSet<T>(cu);
//			}
//			this.rowsOld.addAll(pages);
//			this.rows.clear();
//			int pageNo = page.getPage();
//			if (pageNo <= 0)
//				pageNo = 1;
//			int rows = page.getRows();
//			int startNum = (pageNo - 1) * rows + 1;
//			int endNum = pageNo * rows + 1;
//			Iterator<T> it = this.rowsOld.iterator();
//			int _i = 0;
//			while (it.hasNext()) {
//				T t = it.next();
//				if (_i >= startNum - 1 && _i < endNum - 1) {
//					if (this.rowsOld.size() < _i + 1)
//						break;
//					this.rows.add(t);
//				}
//				_i++;
//			}
//		}
//	}
//
//	public PageDataGrid(List<T> pages, int pageNo, int pageRows, String sortName, String sortOrder) {
//		this.rows = pages;
//		if (pages != null && pages.size() > 0) {
//			this.total = pages.size();
//			// SortedSet
//			if (CheckUtil.isNull(sortName) || CheckUtil.isNull(sortOrder)) {
//				this.rowsOld = new HashSet<T>();
//			} else {
//				SortUtil<T> cu = new SortUtil<T>();
//				cu.setSortName(sortName);
//				cu.setSortOrder(sortOrder);
//				this.rowsOld = new TreeSet<T>(cu);
//			}
//			this.rowsOld.addAll(pages);
//			this.rows.clear();
//			if (pageNo <= 0)
//				pageNo = 1;
//			int startNum = (pageNo - 1) * pageRows + 1;
//			int endNum = pageNo * pageRows + 1;
//			Iterator<T> it = this.rowsOld.iterator();
//			int _i = 0;
//			while (it.hasNext()) {
//				T t = it.next();
//				if (_i >= startNum - 1 && _i < endNum - 1) {
//					if (this.rowsOld.size() < _i + 1)
//						break;
//					this.rows.add(t);
//				}
//				_i++;
//			}
//		}
//	}
//
//	public PageDataGrid(List<T> pages, int pageNo, int pageRows) {
//		this(pages, pageNo, pageRows, null, null);
//	}
//
//	public PageDataGrid() {
//	}
//
//	private int total = 0;
//	private List<T> rows = new ArrayList<T>();
//	private Set<T> rowsOld;
//
//	public Set<T> getRowsOld() {
//		return rowsOld;
//	}
//
//	public void setRowsOld(Set<T> rowsOld) {
//		this.rowsOld = rowsOld;
//	}
//
//	public int getTotal() {
//		return total;
//	}
//
//	public void setTotal(int total) {
//		this.total = total;
//	}
//
//	public List<T> getRows() {
//		return rows;
//	}
//
//	public void setRows(List<T> rows) {
//		this.rows = rows;
//	}
// }
