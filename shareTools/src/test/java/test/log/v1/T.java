package test.log.v1;

import org.apache.log4j.Logger;

public class T {
	private transient static final Logger log = Logger.getLogger(T.class);
	public static void m() {
		log.debug("t...debug");
		log.info("t...info");
		log.warn("t...warn");
		log.error("t...error");
		log.fatal("t...fatal");
	}
}
