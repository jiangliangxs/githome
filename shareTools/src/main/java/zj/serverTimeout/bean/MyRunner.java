package zj.serverTimeout.bean;

import java.lang.reflect.Method;

import zj.reflect.util.MethodUtil;
/**
 * 
 * @author zhangjun
 *
 */
public class MyRunner extends Thread {

	private InArgs_MyRunner inargs_myrunner;
	private OutArgs outargs;

	public MyRunner(InArgs_MyRunner inargs_myrunner, OutArgs outargs) {
		super();
		this.inargs_myrunner = inargs_myrunner;
		this.outargs = outargs;
	}
	@SuppressWarnings("rawtypes")
	public void run() {
		if (inargs_myrunner.getMethodname() != null) {
			try {
				// Class clazz = Class.forName(arg[0]); // 得到类名
//				Class clazz = inargs_myrunner.getClassNewInstance().getClass();
				Class[] clazzs = null;
				if (inargs_myrunner.getArgs() != null && inargs_myrunner.getArgs().length > 0) {
					clazzs = new Class[inargs_myrunner.getArgs().length]; // 得到参数类型
					for (int i = 0, len = inargs_myrunner.getArgs().length; i < len; i++) {
						clazzs[i] = inargs_myrunner.getArgs()[i].getClass();
					}
				}
//				Method method = null;
//				try {
//					method = clazz.getDeclaredMethod(inargs_myrunner.getMethodname(), clazzs);// 根据参数查找方法，也有可能出错，由于反射要求严格，比如说，传一个子类对象过去，他不会找到父类方法的参数
//					outargs.setResult(method.invoke(inargs_myrunner.getClassNewInstance(), inargs_myrunner.getArgs()));
//					outargs.setReturnType(method.getReturnType());
//					outargs.setComplete(true);
//				} catch (Exception e) {
//					// e.printStackTrace();
//					Method[] methods = clazz.getDeclaredMethods();
//					for (int i = 0; i < methods.length; i++) {
//						Method currMethod = methods[i];
//						// 如果名字相同，参数数目相同，我们简单的认为就是我们的方法
//						if (currMethod.getName().equals(inargs_myrunner.getMethodname())
//								&& currMethod.getParameterTypes().length == inargs_myrunner.getArgs().length) {
//							method = currMethod;
//							try {
//								outargs.setResult(method.invoke(inargs_myrunner.getClassNewInstance(),
//										inargs_myrunner.getArgs()));
//								outargs.setReturnType(method.getReturnType());
//								outargs.setComplete(true);
//								break;
//							} catch (Exception e1) {
//								continue;
//							}
//						}
//					}
//				}
				try {
					Object[] results = MethodUtil.invokeDefineReturnResult(inargs_myrunner.getClassNewInstance(), inargs_myrunner.getMethodname(), clazzs, inargs_myrunner.getArgs(), true);
					outargs.setResult(results[1]);
					outargs.setReturnType(((Method)results[0]).getReturnType());
					outargs.setComplete(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public InArgs_MyRunner getInargs_myrunner() {
		return inargs_myrunner;
	}

	public void setInargs_myrunner(InArgs_MyRunner inargs_myrunner) {
		this.inargs_myrunner = inargs_myrunner;
	}

	public OutArgs getOutargs() {
		return outargs;
	}

	public void setOutargs(OutArgs outargs) {
		this.outargs = outargs;
	}

}
