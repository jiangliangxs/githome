package test.serverTimeout;


public abstract class T_3<T extends TT> extends TT3{
	public void t_3_m3(T tp) {
		try {
			System.out.println("休眠2秒..."+tp);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
