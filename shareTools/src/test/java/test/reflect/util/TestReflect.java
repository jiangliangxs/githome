package test.reflect.util;

import java.util.Map;

import org.junit.Test;

import zj.reflect.util.AutowiredUtil;

public class TestReflect {
	@Test
	public void test_setBeanFromMap() throws Exception{
		Bean1 b1 = new Bean1();
		b1.setS1("s1...");
		b1.p1 = "p1...";
		b1.setI1(1);
		b1.setB1(true);
		Map<String, Object> map = AutowiredUtil.getMapFromBean(b1);
		System.out.println("map:"+map);
		if (map!=null){
			for (Object o : map.keySet()){
				System.out.println("o:"+o +"=v:"+map.get(o));
			}
		}
		
	}
}
