package test.reflect.bean;

import zj.reflect.bean.FieldReNameAnn;

public class Field3 extends Field1_1 {
	@FieldReNameAnn(key = "zhang21", name = "jun")
	private String name21 = "a21";
	@FieldReNameAnn(key = "zhang22", name = "jun")
	private String name22 = "a22";

	public String getName21() {
		return name21;
	}

	public void setName21(String name21) {
		this.name21 = name21;
	}

	public String getName22() {
		return name22;
	}

	public void setName22(String name22) {
		this.name22 = name22;
	}

}
