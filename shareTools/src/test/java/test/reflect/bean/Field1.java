package test.reflect.bean;

import zj.reflect.bean.FieldReNameAnn;

public class Field1 {
	@FieldReNameAnn(key="zhang11",name="jun")
	public static final String name11="a11";
	@FieldReNameAnn(key="zhang12",name="jun")
	public static final String name12="a12";
	@FieldReNameAnn()
	public static final String name13="a13";
}
