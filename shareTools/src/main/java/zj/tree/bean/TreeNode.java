package zj.tree.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zj.reflect.util.FieldUtil;
/**
 * 类名 ：TreeUtil<br>
 * 概况 ：树工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class TreeNode<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idName;
	private String pidName;
	private boolean isPidT;
	public TreeNode(String idName, String pidName) {
		this(idName, pidName, false);
	}
	public TreeNode(String idName, String pidName, boolean isPidT) {
		this.idName = idName;
		this.isPidT = isPidT;
		this.pidName = pidName;
	}
	/** 返回所有子节点的集合 **/
	private List<String> rtnListId = new ArrayList<String>();
	/** 返回所有子节点的集合 **/
	private List<T> rtnListNode = new ArrayList<T>();

	public List<String> getRtnListId() {
		return rtnListId;
	}

	public List<T> getRtnListNode() {
		return rtnListNode;
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param id
	 *            传入的父节点ID
	 * @return String
	 */
	public void setChildNodes(List<T> list, String id) throws Exception {
		setChildNodes(list, id, true);
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param id
	 *            传入的父节点ID
	 * @return String
	 */
	public void setChildNodes(List<T> list, String id, boolean self) throws Exception {
		if (list == null || list.size() == 0 || id == null || id.trim().equals(""))
			return;
		if (self)
			for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
				T node = iterator.next();
				String nodeId = getIdFieldValue(node);
				if (id.equals(nodeId)) {
					rtnListId.add(nodeId);
					rtnListNode.add(node);
					break;
				}
			}
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T node = iterator.next();
			String nodePid = getPidFieldValue(node);
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (id.equals(nodePid)) {
				recursionFn(list, node);
			}
			// // 二、遍历所有的父节点下的所有子节点
			// if ("".equals(node.getParentId())) {
			// recursionFn(list, node);
			// }
		}
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param pid
	 *            传入的父节点ID
	 * @return String
	 */
	public void setChildNodesPid(List<T> list, String pid) throws Exception {
		setChildNodesPid(list, pid, true);
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param pid
	 *            传入的父节点ID
	 * @param self
	 *            是否包含自己
	 * @return String
	 */
	public void setChildNodesPid(List<T> list, String pid, boolean self) throws Exception {
		/** 返回所有子节点的集合 **/
		List<String> rtnListIds = new ArrayList<String>();
		/** 返回所有子节点的集合 **/
		List<T> rtnListNodes = new ArrayList<T>();
		if (list == null || list.size() == 0 || pid == null || pid.trim().equals(""))
			return;
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T node = iterator.next();
			String nodePId = getPidFieldValue(node);
			if ((pid == null && nodePId == null) || (pid != null && pid.equals(nodePId))) {
				rtnListId.clear();
				rtnListNode.clear();
				String nodeId = getIdFieldValue(node);
				setChildNodes(list, nodeId, self);
				rtnListIds.addAll(rtnListId);
				rtnListNodes.addAll(rtnListNode);
			}
		}
		rtnListId.clear();
		rtnListNode.clear();
		rtnListId.addAll(rtnListIds);
		rtnListNode.addAll(rtnListNodes);
	}

	/**
	 * 递归调用
	 * 
	 * @param list
	 * @param node
	 * @throws Exception
	 */
	private void recursionFn(List<T> list, T node) throws Exception {
		List<T> childList = getChildList(list, node);// 得到子节点列表
		String nodeId = getIdFieldValue(node);
		if (hasChild(list, node)) {// 判断是否有子节点
			rtnListId.add(nodeId);
			rtnListNode.add(node);
			Iterator<T> it = childList.iterator();
			while (it.hasNext()) {
				T n = it.next();
				recursionFn(list, n);
			}
		} else {
			rtnListId.add(nodeId);
			rtnListNode.add(node);
		}
	}

	// 判断是否有子节点
	public boolean hasChild(List<T> list, T node) throws Exception {
		return getChildList(list, node).size() > 0 ? true : false;
	}

	// 得到子节点列表
	public List<T> getChildList(List<T> list, T node) throws Exception {
		return getChildList(list, node, false);
	}

	// 得到子节点列表
	public List<T> getChildList(List<T> list, T node, boolean delChild) throws Exception {
		List<T> nodeList = new ArrayList<T>();
		List<T> newNodeList = new ArrayList<T>();
		newNodeList.addAll(list);
		Iterator<T> it = newNodeList.iterator();
		String nodeId = getIdFieldValue(node);
		while (it.hasNext()) {
			T n = (T) it.next();
			String nodePid = getPidFieldValue(n);
			if (nodeId.equals(nodePid)) {
				nodeList.add(n);
				if (delChild) {
					list.remove(n);
				}
			}
		}
		return nodeList;
	}

	// 得到子节点列表
	public List<T> getChildList(List<T> list, String id) throws Exception {
		return getChildList(list, id, false);
	}

	// 得到子节点列表
	public List<T> getChildList(List<T> list, String id, boolean delChild) throws Exception {
		List<T> nodeList = new ArrayList<T>();
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			T n = (T) it.next();
			String nodeId = getIdFieldValue(n);
			if (nodeId.equals(id)) {
				return getChildList(list, n, delChild);
			}
		}
		return nodeList;
	}

	/**
	 * 转对象为字符串
	 * 
	 * @param o
	 * @return
	 */
	private String objToStr(Object o) {
		return o == null ? "" : String.valueOf(o);
	}

	/**
	 * 获取属性值ID
	 * 
	 * @param t
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private String getIdFieldValue(T t) throws Exception {
		return objToStr(FieldUtil.get(t, this.idName, true));
	}

	/**
	 * 获取属性值PID
	 * 
	 * @param t
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private String getPidFieldValue(T t) throws Exception {
		Object o = FieldUtil.get(t, this.pidName, true);
		if (this.isPidT) {
			if (o == null)
				return "";
			return objToStr(FieldUtil.get(o, this.idName, true));
		} else {
			return objToStr(o);
		}
	}

}