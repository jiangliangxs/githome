package test.reflect.bean;

import zj.reflect.bean.FieldReNameAnn;

public class Field1_1 {
	@FieldReNameAnn(name="jun")
	private String name11="a21";
	@FieldReNameAnn(key="zhang12",name="jun")
	private String name12="a22";
}
