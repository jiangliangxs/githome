package test.proxy.util1;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;

public abstract class Parent implements InvocationHandler {
	private Object target;

	public Parent(Object target) {
		this.target = target;
	}

	/**
	 * 结果是很明显的，在invoke方法中调用proxy中的方法会再一次引发invoke方法，这就陷入了死循环，最终结果当然是栈溢出的。
	 * 
	 * 可以在invoke方法中调用proxy.getClass(), 程序可以正常运行。但如果调用hashCode()方法同样会导致栈溢出。
	 * 
	 * 通过上面的试验，可以得出一些初步结论，invoke 接口中的proxy参数不能用于调用所实现接口的方法。奇怪的是hashCode()和getClass ()方法都是从Object中继承下来的方法，为什么一个可以另一个不可以呢 ？带首疑问到doc文档看一下Object中这两个方法，发现getClass()是定义为final的，而 hashCode()不是。难道是这个原因，于是找到一个非final方法，如equals试了一下，真的又会导致栈溢出；找另一个final方法如 wait(),试了一下，invoke又不拦截了。final 难道就是关键之处？
	 * 
	 * 还有一个问题就是proxy有什么用？既然proxy可以调用getClass()方法，我们就可以得到proxy的Class类象， 从而可以获得关于proxy代理实例的所有类信息，如方法列表，Annotation等，这就为我们提供的一个分析proxy的有力工具，如通过分析 Annotation分析方法的声明式事务需求。我想传入proxy参数应该是这样一个用意吧。
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Method[] methods = target.getClass().getDeclaredMethods();
		Object result = null;
		for (int i = 0; i < methods.length; i++) {
			if (!method.getName().equals(methods[i].getName()))
				continue;
			Annotation ann = methods[i].getAnnotation(InProgressMethod.class);
			if (ann != null) {
				InProgressMethod rt = (InProgressMethod) ann;
				Method[] annMethods = ann.annotationType().getDeclaredMethods();
				for (Method annMethod : annMethods) {
					String annName = annMethod.getName();
					String annValue = rt.excuteMethod();
					if ("excuteMethod".equals(annName)) {
						if (Constants.ExecuteCons.DOBEFORE.equals(annValue)) {
							doBefore(proxy, method, args);
							result = method.invoke(target, args);
						} else if (Constants.ExecuteCons.DOAFTER.equals(annValue)) {
							result = method.invoke(target, args);
							doAfter(proxy, method, args);
						} else if (Constants.ExecuteCons.DOEXCUTE.equals(annValue)) {
							doBefore(proxy, method, args);
							result = method.invoke(target, args);
							doAfter(proxy, method, args);
						} else {
							result = method.invoke(target, args);
						}
						break;
					}
				}
			} else {
				result = method.invoke(target, args);
			}
		}
		return result;
	}

	protected void doBefore(Object proxy, Method method, Object[] args) {
		Class<?> curClass = target.getClass();
		Method curMethod = method;
		Class<?>[] claAry = method.getParameterTypes();
		StringBuffer sbCls = new StringBuffer();
		for (int i = 0; i < claAry.length; i++) {
			Class<?> cls = claAry[i];
			if (i != 0)
				sbCls.append(",");
			sbCls.append(cls.getSimpleName());
		}
		System.out.println("调用【" + curClass.getSimpleName() + "】类中的【" + method.getReturnType().getSimpleName() + " " + curMethod.getName() + "(" + sbCls.toString() + ")】方法前进行拦截");
	}

	protected void doAfter(Object proxy, Method method, Object[] args) {
		Class<?> curClass = target.getClass();
		Method curMethod = method;
		Class<?>[] claAry = method.getParameterTypes();
		StringBuffer sbCls = new StringBuffer();
		for (int i = 0; i < claAry.length; i++) {
			Class<?> cls = claAry[i];
			if (i != 0)
				sbCls.append(",");
			sbCls.append(cls.getSimpleName());
		}
		System.out.println("调用【" + curClass.getSimpleName() + "】类中的【" + method.getReturnType().getSimpleName() + " " + curMethod.getName() + "(" + sbCls.toString() + ")】方法后进行拦截");
	}
}
