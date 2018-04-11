package test.proxy.util5;

public class SearchClass {
	public static void find(String tosubclassname) {
		try {
			Class tosubclass = Class.forName(tosubclassname);
			Package[] pcks = Package.getPackages();
			for (int i = 0; i < pcks.length; i++) {
				//find(pcks[i].getName(), tosubclass);
				System.out.println(pcks[i].getName());
			}
		} catch (ClassNotFoundException ex) {
			System.err.println("Class " + tosubclassname + " not found!");
		}
	}
}
