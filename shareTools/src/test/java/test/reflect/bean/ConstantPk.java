package test.reflect.bean;


public class ConstantPk implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1l;
	/** 代码 */
	private String code;
	/** 类型 */
	private String constantType;

	public ConstantPk() {
	}

	public ConstantPk(String constantType, String code) {
		this.constantType = constantType;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConstantType() {
		return constantType;
	}

	public void setConstantType(String constantType) {
		this.constantType = constantType;
	}

}
