package zj.io.service;

import java.util.Collection;

public abstract class ReadLinesBatchCallAbsImpl<T extends Collection<String>> implements ReadLinesBatchCallI<T> {
	private long batchSize;
	public long getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(long batchSize) {
		this.batchSize = batchSize;
	}

	public void callBatchColl(T batchColl) {
	}

	public boolean isCallBatchColl() {
		return false;
	}

}
