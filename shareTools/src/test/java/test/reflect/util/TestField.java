package test.reflect.util;

import java.util.Map;

import org.junit.Test;

import test.reflect.bean.Field3;
import zj.reflect.util.FieldReNameUtil;

public class TestField {
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
