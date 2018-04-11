package zj.serverTimeout.bean;
/**
 * 
 * @author zhangjun
 *
 */
public class OutArgs {

	private long needRequestTime; // 请求返回限制时间
	private long useredRequestTime; // 请求使用的时间
	private boolean complete; // 后台程序是否完成
	private boolean programe; // 主程序是否完成
	private Object result; // 返回值
	@SuppressWarnings("rawtypes")
	private Class returnType; // 返回类型

	public OutArgs() {

	}

	@SuppressWarnings("rawtypes")
	public OutArgs(long needRequestTime, long useredRequestTime,
			boolean programe, Object result, Class returnType) {
		this.needRequestTime = needRequestTime;
		this.useredRequestTime = useredRequestTime;
		this.programe = programe;
		this.result = result;
		this.returnType = returnType;
	}

	public boolean isPrograme() {
		return programe;
	}

	public void setPrograme(boolean programe) {
		this.programe = programe;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@SuppressWarnings("rawtypes")
	public Class getReturnType() {
		return returnType;
	}

	@SuppressWarnings("rawtypes")
	public void setReturnType(Class returnType) {
		this.returnType = returnType;
	}

	public long getNeedRequestTime() {
		return needRequestTime;
	}

	public void setNeedRequestTime(long needRequestTime) {
		this.needRequestTime = needRequestTime;
	}

	public long getUseredRequestTime() {
		return useredRequestTime;
	}

	public void setUseredRequestTime(long useredRequestTime) {
		this.useredRequestTime = useredRequestTime;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

}
