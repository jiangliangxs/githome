package test.message;

import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Test;

import zj.message.util.MessageConstantsUtil;
import zj.message.util.MessageI18nUtil;
import zj.message.util.MessageUtil;

public class TestMessage {
	@Test
	public void constants() {
		try {
//			Map<String, String> CONSTANT_KEY_VALUE = new ConcurrentHashMap<String,String>();
//			CONSTANT_KEY_VALUE = Collections.synchronizedMap(new HashMap<String,String>());
////			CONSTANT_KEY_VALUE.put("a", null);
//			CONSTANT_KEY_VALUE.put(null, "");
			System.out.println(MessageConstantsUtil.getConstantValueByMemory("aaaa"));
			System.out.println(MessageConstantsUtil.getConstantValueByMemory("aaaa", true));
			System.out.println(MessageConstantsUtil.getConstantValueByMemory("cons.cache.file.path", true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void m9() {
		MessageI18nUtil.setLocale(new Locale("en_US"));
		System.out.println(MessageI18nUtil.getString("info.1"));
		System.out.println(MessageI18nUtil.getStringByMemory("info.2"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(MessageI18nUtil.getString("info.1"));
		System.out.println(MessageI18nUtil.getStringByMemory("info.2"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(MessageI18nUtil.getString("info.1"));
		System.out.println(MessageI18nUtil.getStringByMemory("info.2"));
	}
	@Test
	public void m8() {
		System.out.println(MessageConstantsUtil.getConstantValue("cons.test.1"));
		System.out.println(MessageConstantsUtil.getConstantValueByMemory("cons.test.2"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(MessageConstantsUtil.getConstantValue("cons.test.1"));
		System.out.println(MessageConstantsUtil.getConstantValueByMemory("cons.test.2"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(MessageConstantsUtil.getConstantValue("cons.test.1"));
		System.out.println(MessageConstantsUtil.getConstantValueByMemory("cons.test.2"));
	}
	@Test
	public void m7() {
		MessageUtil.setConstantKeyValueToMemory();
		System.out.println(MessageUtil.getConstant("cons.test.1"));
		MessageUtil.RELOAD_READ=true;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(MessageUtil.getConstant("cons.test.1"));
	}

	@Test
	public void m6() {
		System.out.println(MessageFormat.format("a{0}bc", "123"));
		System.out.println(MessageFormat.format("a''{0}''bc", "123"));
	}

	@Test
	public void m5() {
		String s = null;
		// s = MessageUtil.getStringByParams("test.1", "张军");
		// System.out.println(s);
		// try {
		// Thread.sleep(4000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// // MessageUtil.setStringKeyValuesToMemory();
		// s = MessageUtil.getStringByParams("test.1", "张军2");
		// System.out.println(s);

		s = MessageUtil.getConstantValueByMemoryParams("cons.test.2", "张军");
		System.out.println(s);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// MessageUtil.setConstantKeyValueToMemory();

		s = MessageUtil.getConstantValueByMemoryParams("cons.test.2", "张军2");
		System.out.println(s);
	}

	@Test
	public void m4() {
		// String s = MessageUtil.getStringByParams("test.1", "张军");
		// System.out.println(s);
		// MessageUtil.setStringKeyValuesToMemory();
		// s = MessageUtil.getStringByParams("test.1", "张军2");
		// System.out.println(s);

		MessageUtil.setStringKeyValuesToMemory();
		System.out.println("->" + MessageUtil.getStringByMemory("globalPage.enus"));
		MessageUtil.setConstantKeyValueToMemory();
		System.out.println("初使化常量文件中的值成功");
		MessageUtil.setStringKeyValuesToMemory();
		System.out.println("初使化资源文件中的值成功");
		System.out.println(MessageUtil.getConstantValueByMemory("cons.global.auth.excluded.patterns"));
		System.out.println("->" + MessageUtil.getStringByMemory("globalPage.enus"));
		MessageUtil.debugString();
		MessageUtil.setStringKeyValuesToMemory();
		MessageUtil.debugString();
		System.out.println("->" + MessageUtil.getStringByMemory("globalPage.enus"));
	}

	@Test
	public void m3() {
		System.out.println(MessageUtil.getConstant("cons.cache.file.path"));
		String s = MessageUtil.getString("app.info.1", true);
		System.out.println("app.info.1--" + s);
	}

	@Test
	public void m1() {
		MessageUtil.setConstantKeyValueToMemory();
		MessageUtil.debugString();
		System.out.println("===================================");
		MessageUtil.setStringKeyValuesToMemory();
		MessageUtil.debugString();
	}

	@Test
	public void m2() {
		String s = MessageUtil.getString("app.info.1");
		System.out.println("app.info.1--" + s);
		MessageUtil.debugString();
		s = MessageUtil.getStringByParams("app.test.1", "张军啊1");
		System.out.println("app.test.1--" + s);
		MessageUtil.debugString();
		s = MessageUtil.getStringByParams("app.test.1", "张军啊2");
		System.out.println("app.test.1--" + s);
		MessageUtil.debugString();
		s = MessageUtil.getStringByClassParams("activitiePage.fst", ActivitieAction.class, "张军啊3");
		System.out.println("activitiePage.fst--" + s);
		MessageUtil.debugString();
	}
}
