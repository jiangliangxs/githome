package test.reflect.bean;


public class Constant{
	/** 含义 */
	private String note;
	private String remark;
	private ConstantPk pk;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ConstantPk getPk() {
		return pk;
	}

	public void setPk(ConstantPk pk) {
		this.pk = pk;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
