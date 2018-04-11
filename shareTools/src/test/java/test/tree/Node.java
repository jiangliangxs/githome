package test.tree;

import java.io.Serializable;

/**
 * 无限级节点模型
 */
public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节点id
	 */
	private String id;
	
	private Node pNode;
	
	

	public Node getpNode() {
		return pNode;
	}

	public void setpNode(Node pNode) {
		this.pNode = pNode;
	}

	/**
	 * 节点名称
	 */
	private String nodeName;

	/**
	 * 父节点id
	 */
	private String parentId;

	public Node() {
	}

	Node(String id, String parentId) {
		this.id = id;
		this.parentId = parentId;
	}

	Node(String id, String nodeName, String parentId) {
		this(id,nodeName,parentId,null);
	}
	Node(String id, String nodeName, String parentId, Node pNode) {
		this.id = id;
		this.nodeName = nodeName;
		this.parentId = parentId;
		this.pNode = pNode;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}