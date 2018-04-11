package zj.io.service;

import java.util.Collection;


/**
 * 读文件
 * 
 * @author zhangjun
 * 
 */
public interface ReadLinesBatchCallI<T extends Collection<String>> {
	/**
	 * 返回批量数
	 * 
	 * @return
	 */
	public long getBatchSize();

	/**
	 * 设置批量数
	 * 
	 * @param batchSize
	 */
	public void setBatchSize(long batchSize);

	/**
	 * 回调集合
	 * 
	 * @param batchColl
	 */
	public void callBatchColl(T batchColl);

	/**
	 * 是否回调使用
	 * 
	 * @return
	 */
	public boolean isCallBatchColl();
	
	/**
	 * 返回T类型
	 * @return
	 */
	public T getObj();
}