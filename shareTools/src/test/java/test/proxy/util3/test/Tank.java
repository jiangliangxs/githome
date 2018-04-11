package test.proxy.util3.test;

import java.util.Random;


public class Tank implements Moveable {

	private String t;

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	@Override
	public void move() {
		// long start = System.currentTimeMillis();
		System.out.println("Tank Moving...");
		try {
			Thread.sleep(new Random().nextInt(10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// long end = System.currentTimeMillis();
		// System.out.println("time: " + (end - start));
	}


}
