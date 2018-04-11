package test.jdbc;

import java.io.Serializable;

/**
 * 列信息
 * 
 * @author zhangjun
 * 
 */
public class ColumnInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 自动生成 **/
	public String UUID;
	/** 列名 **/
	public String NAME;
	/** 数据类型 **/
	public int DATA_TYPE;
}
