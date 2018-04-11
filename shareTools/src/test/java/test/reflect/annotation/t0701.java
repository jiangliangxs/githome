package test.reflect.annotation;

import java.lang.reflect.Method;

public class t0701 {
	/*
	 * 被注解的三个方法
	 */
	@Ann(id = 1, description = "hello method_1")
	public void method_1() {
	}

	@Ann(id = 2)
	public void method_2() {
	}

	@Ann(id = 3, description = "last method")
	public void method_3() {
	}

	/*
	 * 解析注解，将Test_1类 所有被注解方法 的信息打印出来
	 */
	public static void main(String[] args) {
		Method[] methods = t0701.class.getDeclaredMethods();
		for (Method method : methods) {
			/*
			 * 判断方法中是否有指定注解类型的注解
			 */
			boolean hasAnnotation = method.isAnnotationPresent(Ann.class);
			if (hasAnnotation) {
				/*
				 * 根据注解类型返回方法的指定类型注解
				 */
				Ann annotation = method.getAnnotation(Ann.class);
				System.out.println("Ann( method = " + method.getName()
						+ " , id = " + annotation.id() + " , description = "
						+ annotation.description() + " )");
			}
		}
	}

}
