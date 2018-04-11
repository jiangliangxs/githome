package zj.io.service;

import java.util.Collection;

/**
 * 读文件
 * 
 * @author zhangjun
 * 
 */
public interface ReadLinesCallI<T extends Collection<String>> {
	/**
	 * 
	 * @param line
	 *            行数据
	 * @param lineNum
	 *            行号
	 * @return true:过虑,false:不过虑
	 */
	public boolean filter(String line, long lineNum);

	/**
	 * 
	 * @param line
	 *            行数据
	 * @param lineNum
	 *            行号
	 * @return true:中断,false:不中断
	 */
	public boolean interrupt(String line, long lineNum);

	/**
	 * 改变行值
	 * 
	 * @param line
	 *            行数据
	 * @param lineNum
	 *            行号
	 * @return
	 */
	public String changeLine(String line, long lineNum);

	/**
	 * 读取开始行(从1开始)
	 * 
	 * @return
	 */
	public long getStartLineNum();

	/**
	 * 读取开始行(从1开始)
	 * 
	 * @return
	 */
	public long getEndLineNum();

	/**
	 * 读取开始行(从1开始)
	 * 
	 * @return
	 */
	public void setStartLineNum(long lineNum);

	/**
	 * 读取开始行(从1开始)
	 * 
	 * @return
	 */
	public void setEndLineNum(long lineNum);

	/**
	 * 返回T类型
	 * 
	 * @return
	 */
	public T getObj();
}