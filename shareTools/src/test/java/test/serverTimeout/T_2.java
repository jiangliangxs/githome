package test.serverTimeout;

import zj.serverTimeout.bean.TimeOutException;
import zj.serverTimeout.util.ServiceConnect;

public class T_2 extends T_3<TT2>{
	public void t_2_m3(TT2 tp) {
		try {
			System.out.println("休眠2秒..."+tp);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new T_2().test();
	}
	
	public void test(){
		try {
			Object o = ServiceConnect.getServiceValue(this, "t_3_m3",new Object[]{new TT2()}, 11112000);
			System.out.println("o:"+o);
		} catch (TimeOutException e) {
			e.printStackTrace();
		}
	}
}
