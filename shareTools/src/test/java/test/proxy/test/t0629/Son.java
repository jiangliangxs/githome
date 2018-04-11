package test.proxy.test.t0629;

import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class Son extends Parent implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private String username;
	private String password;

	public Son1 son12 = new Son1();

	/** default constructor */
	public Son() {
	}

	/** minimal constructor */
	public Son(String password) {
		this.password = password;
	}

	/** full constructor */
	public Son(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void setUsername(String username) {
		System.out.println("set username...");
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Son1 getSon12() {
		return son12;
	}

	public void setSon12(Son1 son12) {
		this.son12 = son12;
	}

	@InProgressMethod(excuteMethod = Constants.ExecuteCons.DOEXCUTE)
	public void initClear() {
		System.out.println(this.getPa() + "..................init............"
				+ this.password);
	}

}