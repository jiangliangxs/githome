package test.io;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.junit.Test;

public class TestResource {
	@Test
	public void resources() {
		System.out.println(TestResource.class.getResource(""));
		System.out.println(TestResource.class.getResource("/"));
		System.out.println(TestResource.class.getResource("cycle-red.ico"));
		ProtectionDomain domain = TestResource.class.getProtectionDomain();
		CodeSource source = null;
		URL result = null;
		if (domain != null)
			source = domain.getCodeSource();// 获得code source
		if (source != null)
			result = source.getLocation();// 获得URL
		System.out.println(result);
	}
}
