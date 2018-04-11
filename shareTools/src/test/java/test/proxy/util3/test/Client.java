package test.proxy.util3.test;

import test.proxy.util3.Proxy;

public class Client {
	public static void main(String[] args) throws Exception{
		Tank21 t = new Tank21();
//		System.out.println("日志包时间...");
//		TankTimeProxy ttp = new TankTimeProxy(t);
//		TankLogProxy tlp = new TankLogProxy(ttp);
//		Moveable m1 = tlp;
//		m1.move();
//		System.out.println("时间包日志...");
//		TankLogProxy tlp2 = new TankLogProxy(t);
//		TankTimeProxy ttp2 = new TankTimeProxy(tlp2);
//		Moveable m2 = ttp2;
//		m2.move();
		t.setT("t....");
		Moveable2 m3 = (Moveable2)Proxy.newProxyInstance(Moveable2.class,new TimeHandler(t));
		System.out.println(t.getT());
		m3.move();
		m3.stop();
		
	}

}
