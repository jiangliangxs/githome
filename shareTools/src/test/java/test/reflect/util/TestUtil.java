package test.reflect.util;

import java.util.Map;

import org.junit.Test;

import test.reflect.bean.Field3;
import zj.reflect.util.BeanUtil;
import zj.reflect.util.FieldReNameUtil;

public class TestUtil {
	@Test
	public void m5(){
		Field3 f1 = BeanUtil.getBean(Field3.class);
		System.out.println(f1);
	}
	@Test
	public void m4(){
		Field3 f1 = new Field3();
		System.out.println(BeanUtil.copyObjToMap(f1));;
	}
	@Test
	public void m2(){
		Field3 f1 = new Field3();
		BeanUtil.copyObjToMap(f1);
	}
	@Test
	public void m1(){
		Field3 f1 = new Field3();
		try {
			Map<String, Map<String, Object>> m = FieldReNameUtil.getReNameMap(f1);
			System.out.println(m.entrySet());
			for (String key : m.keySet()){
				System.out.println("key:"+key+";value:"+m.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
