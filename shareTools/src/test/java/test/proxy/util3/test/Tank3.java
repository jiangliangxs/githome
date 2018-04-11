package test.proxy.util3.test;


public class Tank3 implements Moveable {
	public Tank3(Tank t) {
		super();
		this.t = t;
	}

	Tank t;


	@Override
	public void move() {
		long start = System.currentTimeMillis();
		t.move();
		long end = System.currentTimeMillis();
		System.out.println("time: " + (end - start));
	}

}
