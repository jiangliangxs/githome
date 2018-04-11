package zj.serverTimeout.bean;
/**
 * 
 * @author zhangjun
 *
 */
public class InArgs_MyTimer {
	private long needRequestTime; // 请求返回限制时间

	public InArgs_MyTimer() {

	}

	public InArgs_MyTimer(long needRequestTime) {
		this.needRequestTime = needRequestTime;
	}

	public long getNeedRequestTime() {
		return needRequestTime;
	}

	public void setNeedRequestTime(long needRequestTime) {
		this.needRequestTime = needRequestTime;
	}
}
