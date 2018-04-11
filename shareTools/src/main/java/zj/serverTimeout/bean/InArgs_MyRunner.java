package zj.serverTimeout.bean;
/**
 * 
 * @author zhangjun
 *
 */
public class InArgs_MyRunner {
	private Object classNewInstance; // 使用的对象
	private String methodname; // 调用的方法名
	private Object[] args; // 调用的参数
	//private String breakMethodname; // 调用的中止方法名
	//private Object[] breakArgs; // 调用中止方法的参数

	public InArgs_MyRunner() {
	}

	public InArgs_MyRunner(Object classNewInstance, String methodname, Object[] args//,
			/*String breakMethodname, Object[] breakArgs*/) {
		this.classNewInstance = classNewInstance;
		this.methodname = methodname;
		this.args = args;
	//	this.breakMethodname = breakMethodname;
		//this.breakArgs = breakArgs;
	}

	public Object getClassNewInstance() {
		return classNewInstance;
	}

	public void setClassNewInstance(Object classNewInstance) {
		this.classNewInstance = classNewInstance;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

/*	public String getBreakMethodname() {
		return breakMethodname;
	}

	public void setBreakMethodname(String breakMethodname) {
		this.breakMethodname = breakMethodname;
	}

	public Object[] getBreakArgs() {
		return breakArgs;
	}

	public void setBreakArgs(Object[] breakArgs) {
		this.breakArgs = breakArgs;
	}*/

}
