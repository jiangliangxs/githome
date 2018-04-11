package test.jdbc;

import java.io.Serializable;
import java.util.Map;

/**
 * 映射信息
 * 
 * @author zhangjun
 * 
 */
public class MappingInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fromTableName;
	private String toTableName;
	private String toColumnId;
	private String fromWhere;
	private Map<String, String> fromToColumns;
	
	public String getToColumnId() {
		return toColumnId;
	}
	public void setToColumnId(String toColumnId) {
		this.toColumnId = toColumnId;
	}
	public String getFromTableName() {
		return fromTableName;
	}
	public void setFromTableName(String fromTableName) {
		this.fromTableName = fromTableName;
	}
	public String getToTableName() {
		return toTableName;
	}
	public void setToTableName(String toTableName) {
		this.toTableName = toTableName;
	}
	public String getFromWhere() {
		return fromWhere;
	}
	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}
	public Map<String, String> getFromToColumns() {
		return fromToColumns;
	}
	public void setFromToColumns(Map<String, String> fromToColumns) {
		this.fromToColumns = fromToColumns;
	}
	
}
