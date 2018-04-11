package test.xml.other;

public class CA2 extends CA1 {
	public CA2() {
		System.out.println("..CA2");
	}
	public CA2(String s) {
		super(s);
		System.out.println("..CA2"+s);
	}
}
