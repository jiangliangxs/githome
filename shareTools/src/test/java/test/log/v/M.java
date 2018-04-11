package test.log.v;

import org.junit.Test;

public class M {
	@Test
	public void m2(){
		for (int i = 0; i < 1; i ++){
			test.log.v1.T.m();
//			v2.T.m();
		}
	}
	@Test
	public void m1(){
		for (int i = 0; i < 1000; i ++){
			test.log.com.pa.pb.T.m();
//			test.log.com.pa.T.m();
		}
	}
}
