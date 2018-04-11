package test.design;

public class MySingleton1 extends SonMySingleton1{
	private static MySingleton1 singleton;

	private MySingleton1() {
	}

	public static MySingleton1 getInstance() {
		synchronized (MySingleton1.class) {
			if (singleton == null) {
				singleton = new MySingleton1();
			}
			return singleton;
		}
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
