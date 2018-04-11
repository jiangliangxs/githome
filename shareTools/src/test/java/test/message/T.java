package test.message;

import zj.message.util.MessageUtil;


public class T {
	public static void main(String[] args) {
		MessageUtil.setStringKeyValuesToMemory();
		MessageUtil.debugString();
		String s = MessageUtil.getConstant("a.b.c");
		System.out.println(s);
		Object ss = null;
		System.out.println((String)ss);
		m1();
	}

	private static void m1() {
		String s = MessageUtil.getConstant("cons.global.i18n.resouces");
		System.out.println(s);
		s = MessageUtil.getStringByParams("activitiePage.fst", ActivitieAction.class, "张军");
		System.out.println(s);
		s = MessageUtil.getString("info.1");
		System.out.println(s);
		s = MessageUtil.getStringByParams("test.1", "张军");
		System.out.println(s);
		MessageUtil.RELOAD_READ = true;
		s = MessageUtil.getStringByParams("test.1", "张军");
		System.out.println(s);
		s = MessageUtil.getConstant("cons.test.1");
		System.out.println(s);

		System.out.println("======================");
		s = MessageUtil.getString("app.info.1");
		System.out.println(s);
		s = MessageUtil.getStringByParams("app.test.1", "张军");
		System.out.println(s);
		
		s = MessageUtil.getConstantByParams("cons.test.2", "张军1");
		System.out.println(s);
		s = MessageUtil.getConstantValueByMemoryParams("cons.test.2", "张军2");
		System.out.println(s);
		//s = MessageUtil.getConstantByParams("cons.test.3", JavaUtil.split("a,b", ","));
		System.out.println(s);
	}
}
