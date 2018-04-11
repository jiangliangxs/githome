package test.proxy.util3.test;


public class TankTimeProxy_delete implements Moveable {
	public TankTimeProxy_delete(Moveable t) {
		super();
		this.t = t;
	}

	Moveable t;


	@Override
	public void move() {
		long start = System.currentTimeMillis();
		t.move();
		long end = System.currentTimeMillis();
		System.out.println("time: " + (end - start));
	}

}
