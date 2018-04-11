package test.reflect.bean;

import zj.reflect.bean.FieldReNameAnn;

public class Field2 extends Field1{
	@FieldReNameAnn(key="zhang21",name="jun")
	public static final String name21="a21";
	@FieldReNameAnn(key="zhang22",name="jun")
	public static final String name22="a22";
	@FieldReNameAnn()
	public static final String name23="a23";
}
