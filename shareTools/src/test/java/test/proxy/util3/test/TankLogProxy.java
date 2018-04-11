package test.proxy.util3.test;


public class TankLogProxy implements Moveable {
	public TankLogProxy(Moveable t) {
		super();
		this.t = t;
	}

	Moveable t;


	@Override
	public void move() {
		System.out.println("tank start");
		t.move();
		System.out.println("tank stop");
	}

}
