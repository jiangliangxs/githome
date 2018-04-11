package test.reflect.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
public class t0702 {

	/*
	 * 解析注解，将Test_1类 所有被注解方法 的信息打印出来
	 */
	public static void main(String[] args) throws Exception{
		Field field = C1.class.getDeclaredField("c2");
		System.out.println();
		Annotation ann = field.getAnnotation(Ann1.class);
		field.setAccessible(true);
		System.out.println("" + ann + field.getType());
	}

}

class C1{
	@Ann1
	protected C2 c2;
	public void m1(){
		System.out.println("C1->m1()");
	}
}

class C2{
	public void m1(){
		System.out.println("C2->m1()");
	}
}
